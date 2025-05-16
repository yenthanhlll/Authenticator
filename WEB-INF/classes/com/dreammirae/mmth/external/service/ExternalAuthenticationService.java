/*     */ package WEB-INF.classes.com.dreammirae.mmth.external.service;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationInfos;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.policy.AuthManagementScope;
/*     */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*     */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*     */ import com.dreammirae.mmth.authentication.service.QRCodeGenerator;
/*     */ import com.dreammirae.mmth.authentication.service.VerifyOTPService;
/*     */ import com.dreammirae.mmth.authentication.service.biotp.AuthTypeValifyService;
/*     */ import com.dreammirae.mmth.authentication.service.biotp.BiotpVerifyOTPService;
/*     */ import com.dreammirae.mmth.db.dao.ExternalServiceItemDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.TokenDao;
/*     */ import com.dreammirae.mmth.db.dao.UserDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.IAuthenticationManagerDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.ExtRequestStatus;
/*     */ import com.dreammirae.mmth.external.bean.IssueCodeApiData;
/*     */ import com.dreammirae.mmth.external.bean.UserStatusBean;
/*     */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*     */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*     */ import com.dreammirae.mmth.external.service.BiotpAuthenticationService;
/*     */ import com.dreammirae.mmth.external.service.FidoAuthenticationService;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.TokenStats;
/*     */ import com.dreammirae.mmth.vo.types.ChallengeStatus;
/*     */ import com.dreammirae.mmth.vo.types.ChallengeTypes;
/*     */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.vo.types.UserDeviceStatus;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ExternalAuthenticationService
/*     */ {
/*     */   @Autowired
/*     */   private IssueCodeService issueCodeService;
/*     */   @Autowired
/*     */   private AuthTypeValifyService authTypeValifyService;
/*  63 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.external.service.ExternalAuthenticationService.class);
/*     */   
/*     */   protected static final String LOG_FORMAT_ERROR_MSG = "@@ ERROR MSG :: %s";
/*     */   
/*     */   public void getIssueCode(WebApiResponse result, WebApiRequestParam params) throws ReturnCodeException {
/*  68 */     if (!getAuthMethodTypes().enabledIssueCode()) {
/*  69 */       throw new ReturnCodeException(ReturnCodes.FORBIDDEN_REQUEST_ISSUE_CODE, "IssueCode is not required in this auth type [" + getAuthMethodTypes().name() + "].");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  74 */       UserVO user = getUser(params.getUserName());
/*  75 */       IssueCodeApiData data = getIssueCodeService().getIssueCodeData(user, getAuthMethodTypes());
/*     */       
/*  77 */       if (data == null) {
/*  78 */         result.setReturnCode(ReturnCodes.DISCARD_ISSUE_CODE);
/*     */         
/*     */         return;
/*     */       } 
/*  82 */       if (data.getIssueCodeExpiredYn().booleanValue() || data.getIssueCodeFailExceedYn().booleanValue()) {
/*  83 */         result.setReturnCode(ReturnCodes.DISCARD_ISSUE_CODE);
/*     */       } else {
/*  85 */         result.setReturnCode(ReturnCodes.OK);
/*     */       } 
/*     */       
/*  88 */       result.setIssueCode(data.getIssueCode());
/*  89 */       result.setIssueCodeRegDateTime(data.getIssueCodeRegTs());
/*  90 */       result.setIssueCodeExpired(data.getIssueCodeExpired());
/*  91 */       result.setIssueCodeFailCnt(data.getIssueCodeFailCnt());
/*  92 */       result.setIssueCodeExpiredYn(data.getIssueCodeExpiredYn());
/*  93 */       result.setIssueCodeFailExceedYn(data.getIssueCodeFailExceedYn());
/*     */     }
/*  95 */     catch (ReturnCodeException e) {
/*  96 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/*  97 */       result.setReturnCode(e.getReturnCode());
/*  98 */     } catch (Exception e) {
/*  99 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 100 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doResetAuthFail(WebApiResponse result, WebApiRequestParam params) {
/*     */     try {
/* 107 */       UserVO user = getUser(params.getUserName());
/*     */       
/* 109 */       List<DeviceAppAgentVO> deviceApps = getUserServiceDao().returnDeviceApps(user, getAuthMethodTypes());
/*     */       
/* 111 */       DeviceAppAgentVO included = null;
/*     */       
/* 113 */       for (DeviceAppAgentVO vo : deviceApps) {
/*     */         
/* 115 */         if (DeviceAppAgentStatus.AVAIABLE.equals(vo.getStatus())) {
/*     */           
/* 117 */           TokenRegistrationVO tokenRegi = getUserServiceDao().returnTokenRegistration(vo);
/* 118 */           if (tokenRegi != null && tokenRegi.getLost().toBoolean()) {
/* 119 */             throw new ReturnCodeException(ReturnCodes.LOST, "The registration status is lost. for user=" + user.getUsername() + ", authType = " + getAuthMethodTypes());
/*     */           }
/* 121 */           included = vo;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 126 */       if (included == null) {
/* 127 */         throw new ReturnCodeException(ReturnCodes.DEVICE_APP_UNAUTHORIED, "There has no available registration for user=" + user.getUsername() + ", authType = " + getAuthMethodTypes());
/*     */       }
/*     */       
/* 130 */       getUserServiceDao().resetAuthFailByUser(user);
/*     */       
/* 132 */       getAuthTypeValifyService().updatePinPatternCount(user);
/* 133 */       result.setReturnCode(ReturnCodes.OK);
/* 134 */       LogOperationTypes.RESET_AUTH_FAIL.addServiceLog(getAuthMethodTypes(), params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, included, null, params.createCustomLogData());
/* 135 */     } catch (ReturnCodeException e) {
/* 136 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 137 */       result.setReturnCode(e.getReturnCode());
/* 138 */       LogOperationTypes.RESET_AUTH_FAIL.addServiceLog(getAuthMethodTypes(), params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), e.getMessage(), params.createCustomLogData());
/* 139 */     } catch (Exception e) {
/* 140 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 141 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 142 */       LogOperationTypes.RESET_AUTH_FAIL.addServiceLog(getAuthMethodTypes(), params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, e.getMessage(), params.createCustomLogData());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doForceDereg(WebApiResponse result, WebApiRequestParam param) {
/*     */     try {
/* 149 */       UserVO user = getUser(param.getUserName());
/* 150 */       if (!user.getProductType().equals(ProductType.BIOTP)) {
/* 151 */         result.setReturnCode(ReturnCodes.DEVICE_UNAUTHORIED);
/*     */         return;
/*     */       } 
/* 154 */       UserServiceLocator resultLoc = new UserServiceLocator(getAuthMethodTypes());
/* 155 */       getUserServiceDao().forceDeregByUser(user, param.createCustomLogData(), resultLoc);
/* 156 */       result.setReturnCode(ReturnCodes.OK);
/*     */ 
/*     */       
/* 159 */       getAuthTypeValifyService().updatePinPatternCount(user);
/* 160 */     } catch (ReturnCodeException e) {
/* 161 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 162 */       result.setReturnCode(e.getReturnCode());
/* 163 */     } catch (Exception e) {
/* 164 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 165 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void getUserStatus(WebApiResponse resp, String username) {
/*     */     try {
/* 172 */       UserVO user = getUserServiceDao().returnUserWithRelation(username);
/*     */       
/* 174 */       if (user == null) {
/* 175 */         throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no user with " + username);
/*     */       }
/*     */       
/* 178 */       if (user.getDisabled().toBoolean()) {
/* 179 */         throw new ReturnCodeException(ReturnCodes.USER_FORBIDDEN, "The user has been locked with username=" + username);
/*     */       }
/*     */       
/* 182 */       if (!getAuthMethodTypes().isAllowedMultiDevice())
/*     */       {
/*     */         
/* 185 */         setUserStatusInSingle(resp, user);
/*     */       }
/*     */       
/* 188 */       resp.setReturnCode(ReturnCodes.OK);
/* 189 */     } catch (ReturnCodeException e) {
/* 190 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 191 */       resp.setReturnCode(e.getReturnCode());
/* 192 */     } catch (Exception e) {
/* 193 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 194 */       resp.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getTokenStatus(WebApiResponse result) {
/*     */     try {
/* 202 */       TokenDao dao = new TokenDao();
/*     */       
/* 204 */       TokenVO vo = new TokenVO();
/* 205 */       vo.setStatus(TokenStatus.AVAILABLE);
/* 206 */       Integer available = Integer.valueOf(dao.getViewConentCount(vo, null, null));
/* 207 */       vo.setStatus(TokenStatus.OCCUPIED);
/* 208 */       Integer occupied = Integer.valueOf(dao.getViewConentCount(vo, null, null));
/* 209 */       vo.setStatus(TokenStatus.DISCARD);
/* 210 */       Integer discard = Integer.valueOf(dao.getViewConentCount(vo, null, null));
/*     */       
/* 212 */       Integer total = Integer.valueOf(available.intValue() + occupied.intValue() + discard.intValue());
/*     */       
/* 214 */       result.setReturnCode(ReturnCodes.OK);
/* 215 */       TokenStats stat = new TokenStats();
/* 216 */       stat.setTotal(total.intValue());
/* 217 */       stat.setAvailable(available.intValue());
/* 218 */       stat.setOccupied(occupied.intValue());
/* 219 */       stat.setDiscard(discard.intValue());
/*     */       
/* 221 */       result.setTokenStats(stat);
/*     */     }
/* 223 */     catch (ReturnCodeException e) {
/* 224 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 225 */       result.setReturnCode(e.getReturnCode());
/* 226 */     } catch (Exception e) {
/* 227 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 228 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getUserLogs(WebApiResponse resp, WebApiRequestParam params) {
/*     */     try {
/* 236 */       UserVO user = getUser(params.getUserName());
/*     */       
/* 238 */       List<ServiceLogVO> serviceLogs = getUserServiceDao().returnLatestServiceLogs(user, params.getLimit().intValue());
/*     */       
/* 240 */       if (serviceLogs != null && !serviceLogs.isEmpty()) {
/* 241 */         resp.setServiceLogs(serviceLogs);
/* 242 */         resp.setReturnCode(ReturnCodes.OK);
/*     */       } else {
/* 244 */         resp.setReturnCode(ReturnCodes.NOT_APPLICABLE);
/*     */       }
/*     */     
/* 247 */     } catch (ReturnCodeException e) {
/* 248 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 249 */       resp.setReturnCode(e.getReturnCode());
/* 250 */     } catch (Exception e) {
/* 251 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 252 */       resp.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkAuthenticationResult(WebApiResponse result, WebApiRequestParam params) {
/*     */     try {
/* 259 */       String tid = params.getTid();
/* 260 */       ServerChallengeVO vo = validateServerChallenge(tid);
/* 261 */       getUserStatus(result, vo.getUsername());
/* 262 */       result.setReturnCode(ReturnCodes.OK);
/*     */     }
/* 264 */     catch (ReturnCodeException e) {
/* 265 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 266 */       result.setReturnCode(e.getReturnCode());
/* 267 */     } catch (Exception e) {
/* 268 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 269 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void raiseLostOtp(WebApiResponse result, WebApiRequestParam params) {
/*     */     try {
/* 276 */       UserVO user = getUser(params.getUserName());
/*     */       
/* 278 */       List<UserDeviceVO> userDevices = getUserDevices(user);
/*     */       
/* 280 */       boolean hasUpdate = false;
/* 281 */       int regCnt = 0;
/* 282 */       for (UserDeviceVO userDevice : userDevices) {
/* 283 */         if (UserDeviceStatus.AVAILABLE.equals(userDevice.getStatus())) {
/*     */           
/* 285 */           List<DeviceAppAgentVO> deviceApps = getUserServiceDao().returnDeviceApps(userDevice, getAuthMethodTypes());
/*     */           
/* 287 */           for (DeviceAppAgentVO vo : deviceApps) {
/* 288 */             regCnt++;
/*     */             
/* 290 */             TokenRegistrationVO tokenRegi = getUserServiceDao().returnTokenRegistration(vo);
/*     */             
/* 292 */             if (!tokenRegi.getLost().toBoolean()) {
/* 293 */               getUserServiceDao().raiseLostToken(tokenRegi);
/* 294 */               LogOperationTypes.LOST.addServiceLog(getAuthMethodTypes(), params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, userDevice.getDeviceId(), userDevice.getDeviceType(), null, null, tokenRegi.getTokenId(), null, params.createCustomLogData());
/* 295 */               hasUpdate = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 301 */       if (hasUpdate) {
/*     */         
/* 303 */         result.setReturnCode(ReturnCodes.OK);
/* 304 */       } else if (regCnt < 1) {
/* 305 */         LogOperationTypes.LOST.addServiceLog(getAuthMethodTypes(), params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.NO_AUTH_METHOD, null, params.createCustomLogData());
/* 306 */         result.setReturnCode(ReturnCodes.NO_AUTH_METHOD);
/*     */       } else {
/* 308 */         LogOperationTypes.LOST.addServiceLog(getAuthMethodTypes(), params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.NOT_APPLICABLE, null, params.createCustomLogData());
/* 309 */         result.setReturnCode(ReturnCodes.NOT_APPLICABLE);
/*     */       }
/*     */     
/* 312 */     } catch (ReturnCodeException e) {
/* 313 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 314 */       result.setReturnCode(e.getReturnCode());
/* 315 */       LogOperationTypes.LOST.addServiceLog(getAuthMethodTypes(), params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), e.getMessage(), params.createCustomLogData());
/* 316 */     } catch (Exception e) {
/* 317 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 318 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 319 */       LogOperationTypes.LOST.addServiceLog(getAuthMethodTypes(), params.getUserName(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, e.getMessage(), params.createCustomLogData());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reqForCheckDevice(WebApiResponse result, WebApiRequestParam param) {
/*     */     try {
/* 327 */       UserVO user = getUser(param.getUserName());
/*     */       
/* 329 */       UserDeviceVO userDevice = getUserServiceDao().returnUserDevice(user, param.getDeviceId(), param.getDeviceType());
/*     */       
/* 331 */       if (userDevice == null) {
/* 332 */         throw new ReturnCodeException(ReturnCodes.NO_AUTH_METHOD, "There has no device registration for user=" + user.getUsername() + ", deviceId=" + param.getDeviceId());
/*     */       }
/*     */       
/* 335 */       if (!UserDeviceStatus.AVAILABLE.equals(userDevice.getStatus())) {
/* 336 */         throw new ReturnCodeException(ReturnCodes.NO_AUTH_METHOD, "There has no available device registration for user=" + user.getUsername() + ", deviceId=" + param.getDeviceId());
/*     */       }
/*     */       
/* 339 */       List<DeviceAppAgentVO> deviceApps = getUserServiceDao().returnDeviceApps(userDevice, getAuthMethodTypes());
/*     */       
/* 341 */       if (deviceApps.isEmpty()) {
/* 342 */         throw new ReturnCodeException(ReturnCodes.NO_AUTH_METHOD, "There has no available device registration for user=" + user.getUsername() + ", deviceId=" + param.getDeviceId());
/*     */       }
/*     */       
/* 345 */       boolean hasRegistration = false;
/* 346 */       for (DeviceAppAgentVO vo : deviceApps) {
/*     */         
/* 348 */         if (DeviceAppAgentStatus.AVAIABLE.equals(vo.getStatus()) && getAuthMethodTypes().equals(vo.getAuthType())) {
/*     */           
/* 350 */           TokenRegistrationVO token = getUserServiceDao().returnTokenRegistration(vo);
/*     */           
/* 352 */           if (token == null) {
/* 353 */             throw new ReturnCodeException(ReturnCodes.NO_AUTH_METHOD, "There has no available device registration for user=" + user.getUsername() + ", deviceId=" + param.getDeviceId());
/*     */           }
/*     */           
/* 356 */           if (token.getLost().toBoolean()) {
/* 357 */             throw new ReturnCodeException(ReturnCodes.LOST, "The token is lost.");
/*     */           }
/*     */           
/* 360 */           hasRegistration = true;
/*     */         } 
/*     */       } 
/*     */       
/* 364 */       if (hasRegistration) {
/* 365 */         result.setReturnCode(ReturnCodes.OK);
/*     */       } else {
/* 367 */         result.setReturnCode(ReturnCodes.NO_AUTH_METHOD);
/*     */       }
/*     */     
/* 370 */     } catch (ReturnCodeException e) {
/* 371 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 372 */       result.setReturnCode(e.getReturnCode());
/* 373 */     } catch (Exception e) {
/* 374 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 375 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reqForReIssueCode(WebApiResponse result, WebApiRequestParam param) {
/*     */     try {
/* 383 */       UserVO user = getUser(param.getUserName());
/*     */ 
/*     */       
/* 386 */       getVerifyOTPService().verifyCommonOTPForIssueCode(result, user, param.getOtp());
/*     */       
/* 388 */       UserServiceLocator resultLoc = new UserServiceLocator(getAuthMethodTypes());
/*     */       
/* 390 */       getUserServiceDao().forceDeregByUser(user, null, resultLoc);
/*     */ 
/*     */       
/* 393 */       getIssueCodeService().generateReIssueCode(getAuthMethodTypes(), result, param);
/*     */       
/* 395 */       result.setReturnCode(ReturnCodes.OK);
/* 396 */     } catch (ReturnCodeException e) {
/* 397 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 398 */       result.setReturnCode(e.getReturnCode());
/* 399 */     } catch (Exception e) {
/* 400 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 401 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reqForGenQRCode(WebApiResponse result, WebApiRequestParam param) {
/*     */     try {
/* 408 */       if (StringUtils.isEmpty(param.getQrSessionId())) {
/* 409 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'qrSessionId' is required.'");
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 414 */         String tid = SysEnvCommon.generateTID();
/*     */ 
/*     */         
/* 417 */         long lifetimeDuration = SystemSettingsDao.getPeriods("externalApi.transactionLifeTimePeriodType", "externalApi.transactionLifeTimePeriods");
/* 418 */         long tsExpired = System.currentTimeMillis() + lifetimeDuration;
/*     */         
/* 420 */         String qrData = null;
/*     */         
/*     */         try {
/* 423 */           int dimension = SystemSettingsDao.getInt("externalApi.qrCodeDimension");
/* 424 */           qrData = QRCodeGenerator.generateQRCodeBinary(tid, tsExpired, dimension);
/* 425 */         } catch (Exception e) {
/* 426 */           throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, e);
/*     */         } 
/*     */ 
/*     */         
/* 430 */         ExternalServiceItemVO extItem = new ExternalServiceItemVO();
/* 431 */         extItem.setUserId(-1);
/* 432 */         extItem.setTransactionId(tid);
/* 433 */         extItem.setStatus(ExtRequestStatus.REQ);
/* 434 */         extItem.setExternalIdentifier(param.getQrSessionId());
/* 435 */         extItem.setTransactionInfoData(null);
/* 436 */         extItem.setTsReg(System.currentTimeMillis());
/* 437 */         extItem.setItemType(3);
/* 438 */         extItem.setTsExpired(tsExpired);
/*     */         
/* 440 */         (new ExternalServiceItemDao()).save(extItem);
/*     */         
/* 442 */         result.setQrData(qrData);
/* 443 */         result.setExpiredDuration(Integer.valueOf((int)lifetimeDuration));
/* 444 */         result.setTid(tid);
/*     */       }
/* 446 */       catch (ReturnCodeException e) {
/* 447 */         LOG.error("@@ ERROR MSG :: %s" + e.getMessage(), (Throwable)e);
/* 448 */         result.setReturnCode(e.getReturnCode());
/* 449 */       } catch (Exception e) {
/* 450 */         LOG.error("@@ ERROR MSG :: %s" + e.getMessage(), e);
/* 451 */         result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 464 */       result.setReturnCode(ReturnCodes.OK);
/* 465 */     } catch (ReturnCodeException e) {
/* 466 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 467 */       result.setReturnCode(e.getReturnCode());
/* 468 */     } catch (Exception e) {
/* 469 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 470 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setUserStatusInSingle(WebApiResponse resp, UserVO user) {
/* 476 */     int regCnt = user.getRegCumulation(getAuthMethodTypes());
/*     */     
/* 478 */     UserStatusBean bean = new UserStatusBean();
/*     */ 
/*     */     
/* 481 */     if (regCnt < 1) {
/* 482 */       setIssueCodeData(resp, bean, user);
/*     */       
/* 484 */       if ("2".equals(bean.getStatus())) {
/* 485 */         bean.setStatus("9");
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 492 */     List<DeviceAppAgentVO> registered = getUserServiceDao().returnDeviceApps(user, getAuthMethodTypes());
/*     */ 
/*     */     
/* 495 */     if (registered == null || registered.isEmpty()) {
/* 496 */       setIssueCodeData(resp, bean, user);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 501 */     DeviceAppAgentVO curDeviceApp = null;
/* 502 */     for (DeviceAppAgentVO vo : registered) {
/* 503 */       curDeviceApp = vo;
/* 504 */       if (DeviceAppAgentStatus.AVAIABLE.equals(vo.getStatus())) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 510 */     if (curDeviceApp == null) {
/* 511 */       bean.setStatus("2");
/* 512 */     } else if (DeviceAppAgentStatus.NOT_AVAILABLE.equals(curDeviceApp.getStatus())) {
/* 513 */       setIssueCodeData(resp, bean, user);
/*     */     } else {
/* 515 */       if (!AuthMethodTypes.FIDO.equals(curDeviceApp.getAuthType())) {
/* 516 */         TokenRegistrationVO token = getUserServiceDao().returnTokenRegistration(curDeviceApp);
/* 517 */         bean.setOtpSerialNumber(token.getTokenId());
/* 518 */         bean.setLostYn(Boolean.valueOf(token.getLost().toBoolean()));
/*     */       } 
/*     */       
/* 521 */       bean.setStatus("1");
/* 522 */       bean.setIssueDateTime(Long.valueOf(curDeviceApp.getTsDone()));
/*     */       
/* 524 */       UserDeviceVO userDevice = getUserServiceDao().returnUserDevice(curDeviceApp);
/*     */       
/* 526 */       if (userDevice != null) {
/* 527 */         bean.setDeviceOS(userDevice.getDeviceType().getOsType().getCode());
/* 528 */         bean.setDeviceModel(userDevice.getModel());
/*     */       } 
/*     */       
/* 531 */       AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 532 */       AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, getAuthMethodTypes());
/* 533 */       resp.setAuthInfo(info);
/*     */       
/* 535 */       if (user.getAnnotation() != null && 
/* 536 */         AuthMethodTypes.BIOTP.equals(getAuthMethodTypes())) {
/* 537 */         bean.setCountryCode(user.getAnnotation().getCountryCode());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 542 */     resp.setUserStatus(bean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setIssueCodeData(WebApiResponse resp, UserStatusBean bean, UserVO user) {
/* 548 */     if (getAuthMethodTypes().enabledIssueCode()) {
/*     */       
/* 550 */       IssueCodeApiData issueCodeData = getIssueCodeService().getIssueCodeData(user, getAuthMethodTypes());
/*     */       
/* 552 */       if (issueCodeData == null) {
/* 553 */         bean.setStatus("2");
/*     */       } else {
/* 555 */         bean.setStatus("0");
/* 556 */         resp.setIssueCodeData(issueCodeData);
/* 557 */         if (user.getAnnotation() != null && 
/* 558 */           AuthMethodTypes.BIOTP.equals(getAuthMethodTypes())) {
/* 559 */           bean.setCountryCode(user.getAnnotation().getCountryCode());
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 565 */       bean.setStatus("0");
/*     */     } 
/*     */     
/* 568 */     resp.setUserStatus(bean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void regUser(WebApiResponse result, WebApiRequestParam[] param) {
/*     */     try {
/* 575 */       for (WebApiRequestParam user : param) {
/* 576 */         UserVO userVO = getUserServiceDao().returnUser(user.getUserName());
/* 577 */         if (userVO == null) {
/* 578 */           userVO = UserVO.createNewInstance(user.getUserName(), Integer.parseInt(user.getProductTypeCode()), user.getAccountName());
/* 579 */           getUserDao().save(userVO);
/*     */         } 
/*     */       } 
/* 582 */       result.setReturnCode(ReturnCodes.OK);
/* 583 */     } catch (ReturnCodeException e) {
/* 584 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 585 */       result.setReturnCode(e.getReturnCode());
/* 586 */     } catch (Exception e) {
/* 587 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 588 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getUserInfo(WebApiResponse result, WebApiRequestParam param) {
/*     */     try {
/* 596 */       UserVO user = getUser(param.getUserName());
/* 597 */       result.setUserName(param.getUserName());
/* 598 */       if (!StringUtils.isEmpty(user.getAccountName()))
/* 599 */         result.setAccountName(Base64Utils.decode(user.getAccountName())); 
/* 600 */       result.setProductType(Integer.valueOf(user.getProductType().getCode()));
/* 601 */       result.setMultiLoginYN(user.getMultiLoginYN());
/* 602 */       result.setStatus(user.getStatus().getCode());
/* 603 */       result.setReturnCode(ReturnCodes.OK);
/*     */     }
/* 605 */     catch (ReturnCodeException e) {
/* 606 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 607 */       result.setReturnCode(e.getReturnCode());
/* 608 */     } catch (Exception e) {
/* 609 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 610 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMultiLoginPolicy(WebApiResponse result, WebApiRequestParam param) {
/*     */     try {
/* 620 */       UserVO user = getUser(param.getUserName());
/* 621 */       user.setMultiLoginYN(param.getMultiLoginYN());
/* 622 */       getUserServiceDao().updateUserPolicy(user);
/*     */       
/* 624 */       result.setReturnCode(ReturnCodes.OK);
/*     */     }
/* 626 */     catch (ReturnCodeException e) {
/* 627 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 628 */       result.setReturnCode(e.getReturnCode());
/* 629 */     } catch (Exception e) {
/* 630 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 631 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteUser(WebApiResponse result, WebApiRequestParam[] param) {
/* 639 */     int paramLength = param.length;
/*     */     
/*     */     try {
/* 642 */       if (paramLength == 1) {
/*     */         
/* 644 */         UserVO user = getUser(param[0].getUserName());
/* 645 */         getUserDao().delete(user);
/*     */       } else {
/*     */         
/* 648 */         List<UserVO> users = new ArrayList<>(paramLength);
/* 649 */         for (int i = 0; i < param.length; i++) {
/* 650 */           UserVO user = getUser(param[i].getUserName());
/* 651 */           users.add(user);
/*     */         } 
/* 653 */         getUserDao().deleteAsBatch(users);
/* 654 */         users.clear();
/*     */       } 
/* 656 */       result.setReturnCode(ReturnCodes.OK);
/*     */     
/*     */     }
/* 659 */     catch (ReturnCodeException e) {
/* 660 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 661 */       result.setReturnCode(e.getReturnCode());
/* 662 */     } catch (Exception e) {
/* 663 */       LOG.error(String.format("@@ ERROR MSG :: %s", new Object[] { e.getMessage() }));
/* 664 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerChallengeVO validateServerChallenge(String tid) {
/* 671 */     ServerChallengeVO vo = getUserServiceDao().returnServerChallengeByTid(tid);
/*     */     
/* 673 */     if (vo == null) {
/* 674 */       throw new ReturnCodeException(ReturnCodes.NOT_FOUND_SERVER_CHALLENGE, "There is no SERVER CHALLENGE with this tid = " + tid);
/*     */     }
/* 676 */     if (!ChallengeStatus.OTP_REQ.equals(vo.getStatus())) {
/* 677 */       throw new ReturnCodeException(ReturnCodes.AUTHENTICATION_INCOMPLETED, "User's authentication has not completed. tid=" + tid);
/*     */     }
/*     */     
/* 680 */     if (!ChallengeTypes.AUTH.equals(vo.getChallengeType())) {
/* 681 */       throw new ReturnCodeException(ReturnCodes.AUTHENTICATION_RESULT_INVALID, "User's authentication has been expired. tid=" + tid);
/*     */     }
/*     */     
/* 684 */     long periods = SystemSettingsDao.getPeriods("integrate.checkAuthResultLifetimePeriodType", "integrate.checkAuthResultLifetimePeriods");
/*     */     
/* 686 */     if (vo.getTsDone() + periods < System.currentTimeMillis()) {
/* 687 */       throw new ReturnCodeException(ReturnCodes.AUTHENTICATION_RESULT_INVALID, "User's authentication has been expired. tid=" + tid);
/*     */     }
/*     */     
/* 690 */     return vo;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<UserDeviceVO> getUserDevices(UserVO user) {
/* 695 */     List<UserDeviceVO> userDevices = getUserServiceDao().returnUserDevices(user);
/*     */ 
/*     */     
/* 698 */     if (userDevices == null || userDevices.isEmpty()) {
/* 699 */       throw new ReturnCodeException(ReturnCodes.DEVICE_UNAUTHORIED, "There has no userDevice with " + user.getUsername());
/*     */     }
/*     */     
/* 702 */     return userDevices;
/*     */   }
/*     */ 
/*     */   
/*     */   protected UserVO getUser(String username) {
/* 707 */     UserVO.validateUsername(username);
/*     */     
/* 709 */     UserVO user = getUserServiceDao().returnUser(username);
/*     */     
/* 711 */     if (user == null) {
/* 712 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no user with " + username);
/*     */     }
/*     */     
/* 715 */     if (user.getDisabled().toBoolean()) {
/* 716 */       throw new ReturnCodeException(ReturnCodes.USER_FORBIDDEN, "The user has been locked with username=" + username);
/*     */     }
/*     */     
/* 719 */     return user;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract UserDao getUserDao();
/*     */ 
/*     */   
/*     */   protected abstract UserServiceDao getUserServiceDao();
/*     */   
/*     */   protected abstract IGeneralService getGeneralService();
/*     */   
/*     */   protected abstract AuthMethodTypes getAuthMethodTypes();
/*     */   
/*     */   private IssueCodeService getIssueCodeService() {
/* 733 */     if (this.issueCodeService == null) {
/* 734 */       this.issueCodeService = new IssueCodeService();
/*     */     }
/* 736 */     return this.issueCodeService;
/*     */   }
/*     */   private AuthTypeValifyService getAuthTypeValifyService() {
/* 739 */     if (this.authTypeValifyService == null) {
/* 740 */       this.authTypeValifyService = new AuthTypeValifyService();
/*     */     }
/* 742 */     return this.authTypeValifyService;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.external.service.ExternalAuthenticationService getServiceInstance(AuthMethodTypes type) {
/* 746 */     if (AuthMethodTypes.BIOTP.equals(type)) {
/* 747 */       return (com.dreammirae.mmth.external.service.ExternalAuthenticationService)new BiotpAuthenticationService();
/*     */     }
/* 749 */     return (com.dreammirae.mmth.external.service.ExternalAuthenticationService)new FidoAuthenticationService();
/*     */   }
/*     */ 
/*     */   
/*     */   public VerifyOTPService getVerifyOTPService() {
/* 754 */     if (AuthMethodTypes.BIOTP.equals(getAuthMethodTypes())) {
/* 755 */       return (VerifyOTPService)new BiotpVerifyOTPService();
/*     */     }
/* 757 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\service\ExternalAuthenticationService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */