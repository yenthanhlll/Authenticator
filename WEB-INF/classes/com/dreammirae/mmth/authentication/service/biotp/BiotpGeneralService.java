/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.biotp;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationInfos;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.policy.AuthManagementScope;
/*     */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*     */ import com.dreammirae.mmth.authentication.service.QRCodeGenerator;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.db.dao.ExternalServiceItemDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.IAuthenticationManagerDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.ExtRequestStatus;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.util.notary.AES;
/*     */ import com.dreammirae.mmth.util.notary.SHA;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.TransactionInfoData;
/*     */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.vo.types.UserDeviceStatus;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service("biotpGeneralService")
/*     */ public class BiotpGeneralService
/*     */   implements IGeneralService
/*     */ {
/*  53 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.biotp.BiotpGeneralService.class);
/*     */   
/*     */   protected static final String LOG_FORMAT_ERROR_MSG = "@@@ ERR MSG IN PROC :: ";
/*     */   
/*     */   protected static final int LEN_SYMM = 16;
/*     */   
/*     */   protected static final int LEN_IV = 16;
/*     */   
/*     */   private static final int USER_VERIFICATION_FLAG_DIVIDER = 100;
/*     */   @Autowired
/*     */   private BiotpUserServiceDao biotpUserServiceDao;
/*     */   
/*     */   public void doForceDereg(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/*     */     try {
/*  67 */       UserVO user = getUser(reqLoc.getUsername());
/*     */       
/*  69 */       UserServiceLocator loc = new UserServiceLocator(AuthMethodTypes.BIOTP);
/*  70 */       getUserServiceDao().forceDeregByUser(user, null, loc);
/*     */       
/*  72 */       if (loc.getTokenRegi() != null) {
/*  73 */         respLoc.setOtpSn(loc.getTokenRegi().getTokenId());
/*     */         
/*  75 */         UserDeviceVO ud = loc.getUserDevice();
/*  76 */         if (ud != null) {
/*  77 */           respLoc.setDeviceId(ud.getDeviceId());
/*  78 */           respLoc.setDeviceType(ud.getDeviceType());
/*  79 */           respLoc.setModel(ud.getModel());
/*  80 */           respLoc.setAlias(ud.getAlias());
/*     */         } 
/*     */         
/*  83 */         DeviceAppAgentVO deviceApp = loc.getDeviceAppAgent();
/*  84 */         if (deviceApp != null) {
/*  85 */           AppAgentVO vo = (new AppAgentDao()).getOneByPK(Integer.valueOf(deviceApp.getAgentId()));
/*  86 */           if (vo != null) {
/*  87 */             respLoc.setPkgName(vo.getPkgUnique());
/*     */           }
/*     */         } 
/*  90 */         respLoc.setReturnCode(ReturnCodes.OK);
/*     */       } else {
/*  92 */         respLoc.setReturnCode(ReturnCodes.NO_AUTH_METHOD);
/*  93 */         LogOperationTypes.FORCE_DEREG.addServiceLog(AuthMethodTypes.BIOTP, reqLoc.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.NO_AUTH_METHOD, "There has no registration to delete.");
/*     */       }
/*     */     
/*  96 */     } catch (ReturnCodeException e) {
/*  97 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/*  98 */       LogOperationTypes.FORCE_DEREG.addServiceLog(AuthMethodTypes.BIOTP, reqLoc.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), e.getMessage());
/*  99 */       respLoc.setReturnCode(e.getReturnCode());
/* 100 */     } catch (Exception e) {
/* 101 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), e);
/* 102 */       LogOperationTypes.FORCE_DEREG.addServiceLog(AuthMethodTypes.BIOTP, reqLoc.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, e.getMessage());
/* 103 */       respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void getUserRegistrationInfo(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/*     */     try {
/* 110 */       UserVO user = getUser(reqLoc.getUsername());
/*     */       
/* 112 */       if (user == null) {
/* 113 */         respLoc.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/*     */         
/*     */         return;
/*     */       } 
/* 117 */       List<DeviceAppAgentVO> deviceApps = getUserServiceDao().returnDeviceApps(user);
/*     */       
/* 119 */       if (deviceApps.isEmpty()) {
/* 120 */         respLoc.setReturnCode(ReturnCodes.NO_AUTH_METHOD);
/*     */         
/*     */         return;
/*     */       } 
/* 124 */       DeviceAppAgentVO deviceApp = null;
/*     */ 
/*     */       
/* 127 */       for (DeviceAppAgentVO deviceAppAgentVO : deviceApps) {
/* 128 */         if (DeviceAppAgentStatus.AVAIABLE.equals(deviceAppAgentVO.getStatus())) {
/* 129 */           deviceApp = deviceAppAgentVO;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 135 */       if (deviceApp == null) {
/* 136 */         respLoc.setReturnCode(ReturnCodes.NO_AUTH_METHOD);
/*     */         
/*     */         return;
/*     */       } 
/* 140 */       UserDeviceVO ud = getUserServiceDao().returnUserDevice(deviceApp);
/*     */ 
/*     */       
/* 143 */       if (ud == null) {
/* 144 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/* 148 */       TokenRegistrationVO tokenRegi = getUserServiceDao().returnTokenRegistration(deviceApp);
/*     */       
/* 150 */       if (tokenRegi == null) {
/* 151 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/* 155 */       respLoc.setOtpSn(tokenRegi.getTokenId());
/* 156 */       respLoc.setDeviceId(ud.getDeviceId());
/* 157 */       respLoc.setDeviceType(ud.getDeviceType());
/* 158 */       respLoc.setModel(ud.getModel());
/* 159 */       respLoc.setAlias(ud.getAlias());
/* 160 */       AppAgentVO vo = (new AppAgentDao()).getOneByPK(Integer.valueOf(deviceApp.getAgentId()));
/* 161 */       if (vo != null) {
/* 162 */         respLoc.setPkgName(vo.getPkgUnique());
/*     */       }
/*     */       
/* 165 */       AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 166 */       AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, AuthMethodTypes.BIOTP);
/* 167 */       respLoc.setAuthFailCnt(Integer.valueOf(info.getFailCnt()));
/* 168 */       respLoc.setReturnCode(ReturnCodes.OK);
/* 169 */     } catch (ReturnCodeException e) {
/* 170 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/* 171 */       respLoc.setReturnCode(e.getReturnCode());
/* 172 */     } catch (Exception e) {
/* 173 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), e);
/* 174 */       respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTransactionInfo(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/*     */     try {
/* 182 */       UserVO user = getUser(reqLoc.getUsername());
/*     */       
/* 184 */       TokenRegistrationVO tokenRegi = getTokenRegistration(reqLoc.getOtpSn(), user.getUsername());
/*     */       
/* 186 */       DeviceAppAgentVO deviceAppAgent = getDeviceAppAgent(tokenRegi, user);
/*     */       
/* 188 */       UserDeviceVO userDevice = getUserDevice(deviceAppAgent, user);
/*     */ 
/*     */       
/* 191 */       TransactionInfoData trinfo = new TransactionInfoData();
/* 192 */       trinfo.setTokenId(reqLoc.getOtpSn());
/* 193 */       trinfo.setUserName(reqLoc.getUsername());
/* 194 */       trinfo.setDeviceId(userDevice.getDeviceId());
/* 195 */       trinfo.setTranInfoYn(reqLoc.getTranInfoYn());
/* 196 */       trinfo.setTranInfo(reqLoc.getTranInfo());
/* 197 */       trinfo.setTranInfoOrgEnc(generateEncTransactionInfo(reqLoc.getTranInfoOrg(), user.getUsername(), userDevice.getDeviceId()));
/*     */       
/* 199 */       ExternalServiceItemVO extItem = new ExternalServiceItemVO();
/* 200 */       extItem.setUserId(user.getId());
/* 201 */       extItem.setTransactionId(SysEnvCommon.generateTID());
/* 202 */       extItem.setStatus(ExtRequestStatus.REQ);
/* 203 */       extItem.setExternalIdentifier(extItem.getTransactionId());
/* 204 */       extItem.setTransactionInfoData(trinfo);
/* 205 */       extItem.setTsReg(System.currentTimeMillis());
/* 206 */       extItem.setItemType(2);
/* 207 */       long lifetimeDuration = SystemSettingsDao.getPeriods("externalApi.transactionLifeTimePeriodType", "externalApi.transactionLifeTimePeriods");
/* 208 */       extItem.setTsExpired(System.currentTimeMillis() + lifetimeDuration);
/*     */       
/* 210 */       (new ExternalServiceItemDao()).save(extItem);
/*     */       
/* 212 */       AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 213 */       AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, AuthMethodTypes.BIOTP);
/* 214 */       respLoc.setAuthFailCnt(Integer.valueOf(info.getFailCnt()));
/* 215 */       if (info.getFailCnt() < SystemSettingsDao.getInt("integrate.authMaxFailCount")) {
/* 216 */         respLoc.setReturnCode(ReturnCodes.OK);
/*     */       } else {
/* 218 */         respLoc.setReturnCode(ReturnCodes.EXCEEDED_AUTH_ERROR);
/*     */       } 
/* 220 */     } catch (ReturnCodeException e) {
/* 221 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/* 222 */       respLoc.setReturnCode(e.getReturnCode());
/* 223 */     } catch (Exception e) {
/* 224 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), e);
/* 225 */       respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getTransactionInfo(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/*     */     try {
/* 233 */       UserVO user = getUser(reqLoc.getUsername());
/*     */ 
/*     */       
/* 236 */       UserDeviceVO ud = getUserServiceDao().returnUserDevice(user, reqLoc.getDeviceId(), reqLoc.getDeviceType());
/*     */       
/* 238 */       if (ud == null || !UserDeviceStatus.AVAILABLE.equals(ud.getStatus())) {
/* 239 */         throw new ReturnCodeException(ReturnCodes.NO_AUTH_METHOD, "This is not available devices.. username =" + user.getUsername() + ", deviceId = " + reqLoc.getDeviceId() + ", deviceType = " + reqLoc.getDeviceType());
/*     */       }
/*     */       
/* 242 */       respLoc.setUvTFCnt(Integer.valueOf(ud.getUserVerificationFailCnt()));
/*     */       
/* 244 */       ExternalServiceItemDao dao = new ExternalServiceItemDao();
/* 245 */       ExternalServiceItemVO trItem = dao.getOneByUser(user);
/*     */       
/* 247 */       if (trItem == null) {
/* 248 */         throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, "There has no authentication request.. username=" + user.getUsername());
/*     */       }
/*     */       
/* 251 */       if (trItem.getTsExpired() < System.currentTimeMillis()) {
/* 252 */         dao.deleteByUser(user);
/* 253 */         throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, "There has no authentication request.. username=" + user.getUsername());
/*     */       } 
/*     */       
/* 256 */       TransactionInfoData trinfo = trItem.getTransactionInfoData();
/* 257 */       respLoc.setTranInfoYn(trinfo.getTranInfoYn());
/* 258 */       respLoc.setTranInfoEnc(trinfo.getTranInfoOrgEnc());
/*     */       
/* 260 */       AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 261 */       AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, AuthMethodTypes.BIOTP);
/* 262 */       respLoc.setAuthFailCnt(Integer.valueOf(info.getFailCnt()));
/*     */       
/* 264 */       if (info.getFailCnt() < SystemSettingsDao.getInt("integrate.authMaxFailCount")) {
/* 265 */         respLoc.setReturnCode(ReturnCodes.OK);
/*     */       } else {
/* 267 */         respLoc.setReturnCode(ReturnCodes.EXCEEDED_AUTH_ERROR);
/*     */       } 
/* 269 */     } catch (ReturnCodeException e) {
/* 270 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/* 271 */       respLoc.setReturnCode(e.getReturnCode());
/* 272 */     } catch (Exception e) {
/* 273 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), e);
/* 274 */       respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUserVerificationFlag(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/*     */     try {
/* 281 */       UserVO user = getUser(reqLoc.getUsername());
/*     */       
/* 283 */       UserDeviceVO ud = getUserServiceDao().returnUserDevice(user, reqLoc.getDeviceId(), reqLoc.getDeviceType());
/*     */       
/* 285 */       if (ud == null || !UserDeviceStatus.AVAILABLE.equals(ud.getStatus())) {
/* 286 */         throw new ReturnCodeException(ReturnCodes.NO_AUTH_METHOD, "This is not available devices.. username =" + user
/* 287 */             .getUsername() + ", deviceId = " + reqLoc
/* 288 */             .getDeviceId() + ", deviceType = " + reqLoc.getDeviceType());
/*     */       }
/*     */       
/* 291 */       if (reqLoc.getUserVerificationFlag().intValue() == 0) {
/* 292 */         ud.resetUserVerificationFailCnt();
/*     */       } else {
/* 294 */         ud.addUserVerificationFlag(reqLoc.getUserVerificationFlag().intValue());
/*     */       } 
/*     */       
/* 297 */       getUserServiceDao().saveUserDevice(ud);
/*     */       
/* 299 */       respLoc.setUvTFCnt(Integer.valueOf(ud.getUserVerificationFailCnt()));
/* 300 */       respLoc.setReturnCode(ReturnCodes.OK);
/*     */ 
/*     */ 
/*     */       
/* 304 */       int failCntFlag = ud.getUserVerificationFailCnt();
/*     */ 
/*     */       
/* 307 */       int pinFailCnt = failCntFlag % 100;
/*     */       
/* 309 */       failCntFlag /= 100;
/* 310 */       int patternCnt = failCntFlag % 100;
/*     */       
/* 312 */       LogOperationTypes.UPDATE_USER_VERIFICATION_FLAG.addServiceLog(getAuthMethodTypes(), reqLoc
/* 313 */           .getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, reqLoc.getDeviceId(), reqLoc.getDeviceType(), null, null, null, 
/* 314 */           Commons.getSysMessage(new I18nMessage("LogOperationTypes.UPDATE_USER_VERIFICATION_FLAG.desc", new Object[] { Integer.valueOf(patternCnt), Integer.valueOf(pinFailCnt) })));
/*     */     }
/* 316 */     catch (ReturnCodeException e) {
/* 317 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/* 318 */       respLoc.setReturnCode(e.getReturnCode());
/* 319 */       LogOperationTypes.UPDATE_USER_VERIFICATION_FLAG.addServiceLog(getAuthMethodTypes(), reqLoc
/* 320 */           .getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), reqLoc.getDeviceId(), reqLoc.getDeviceType(), null, null, null, e.getMessage());
/* 321 */     } catch (Exception e) {
/* 322 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), e);
/* 323 */       respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 324 */       LogOperationTypes.UPDATE_USER_VERIFICATION_FLAG.addServiceLog(getAuthMethodTypes(), reqLoc
/* 325 */           .getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, reqLoc.getDeviceId(), reqLoc.getDeviceType(), null, null, null, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void lostOtp(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkAuthResult(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateQRCode(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/* 343 */     if (StringUtils.isEmpty(reqLoc.getQrSessionId())) {
/* 344 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'qrSessionId' is required.'");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 349 */       String tid = SysEnvCommon.generateTID();
/*     */ 
/*     */       
/* 352 */       long lifetimeDuration = SystemSettingsDao.getPeriods("externalApi.transactionLifeTimePeriodType", "externalApi.transactionLifeTimePeriods");
/* 353 */       long tsExpired = System.currentTimeMillis() + lifetimeDuration;
/*     */       
/* 355 */       String qrData = generateQRCodeImageBinary(tid, tsExpired);
/*     */ 
/*     */       
/* 358 */       ExternalServiceItemVO extItem = new ExternalServiceItemVO();
/* 359 */       extItem.setUserId(-1);
/* 360 */       extItem.setTransactionId(tid);
/* 361 */       extItem.setStatus(ExtRequestStatus.REQ);
/* 362 */       extItem.setExternalIdentifier(reqLoc.getQrSessionId());
/* 363 */       extItem.setTransactionInfoData(null);
/* 364 */       extItem.setTsReg(System.currentTimeMillis());
/* 365 */       extItem.setItemType(3);
/* 366 */       extItem.setTsExpired(tsExpired);
/*     */       
/* 368 */       (new ExternalServiceItemDao()).save(extItem);
/*     */       
/* 370 */       respLoc.setQrData(qrData);
/* 371 */       respLoc.setExpiredDuration(Integer.valueOf((int)lifetimeDuration));
/* 372 */       respLoc.setTid(tid);
/*     */     }
/* 374 */     catch (ReturnCodeException e) {
/* 375 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/* 376 */       respLoc.setReturnCode(e.getReturnCode());
/* 377 */     } catch (Exception e) {
/* 378 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), e);
/* 379 */       respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] generateEncTransactionInfo(byte[] tranInfoOrg, String userName, String deviceId) {
/* 388 */     if (tranInfoOrg == null) {
/* 389 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "TRAN_INFO_ORG could not be null.");
/*     */     }
/*     */     
/*     */     try {
/* 393 */       byte[] hashUserName = SHA.sha256Raw(userName);
/* 394 */       byte[] deviceIdRaw = HexUtils.fromHexString(deviceId);
/*     */       
/* 396 */       byte[] symmetricKey = new byte[16];
/* 397 */       System.arraycopy(hashUserName, 0, symmetricKey, 0, 16);
/* 398 */       byte[] aes_iv = new byte[16];
/* 399 */       System.arraycopy(deviceIdRaw, 16, aes_iv, 0, 16);
/*     */ 
/*     */ 
/*     */       
/* 403 */       byte[] enc = AES.aesCbc(symmetricKey, aes_iv, tranInfoOrg, 1);
/*     */       
/* 405 */       return enc;
/* 406 */     } catch (Exception e) {
/* 407 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] decTransactionInfo(byte[] tranInfoOrg, String userName, String deviceId) {
/* 414 */     if (tranInfoOrg == null) {
/* 415 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "TRAN_INFO_ORG could not be null.");
/*     */     }
/*     */     
/*     */     try {
/* 419 */       byte[] hashUserName = SHA.sha256Raw(userName);
/* 420 */       byte[] deviceIdRaw = HexUtils.fromHexString(deviceId);
/*     */       
/* 422 */       byte[] symmetricKey = new byte[16];
/* 423 */       System.arraycopy(hashUserName, 0, symmetricKey, 0, 16);
/* 424 */       byte[] aes_iv = new byte[16];
/* 425 */       System.arraycopy(deviceIdRaw, 16, aes_iv, 0, 16);
/*     */       
/* 427 */       byte[] enc = AES.aesCbc(symmetricKey, aes_iv, tranInfoOrg, 2);
/*     */       
/* 429 */       return enc;
/* 430 */     } catch (Exception e) {
/* 431 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected UserDeviceVO getUserDevice(DeviceAppAgentVO deviceAppAgent, UserVO user) {
/* 437 */     UserDeviceVO vo = getUserServiceDao().returnUserDevice(deviceAppAgent);
/*     */     
/* 439 */     if (vo == null || !UserDeviceStatus.AVAILABLE.equals(vo.getStatus())) {
/* 440 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "The device is not registered with user(username=" + user.getUsername() + ").");
/*     */     }
/*     */     
/* 443 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DeviceAppAgentVO getDeviceAppAgent(TokenRegistrationVO tokenRegi, UserVO user) {
/* 449 */     DeviceAppAgentVO vo = getUserServiceDao().returnDeviceAppAgent(tokenRegi.getDeviceAgentId());
/*     */     
/* 451 */     if (vo == null || !DeviceAppAgentStatus.AVAIABLE.equals(vo.getStatus())) {
/* 452 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "The otp token(sn=" + tokenRegi.getTokenId() + ") is not mapping with user(username=" + user.getUsername() + ").");
/*     */     }
/*     */     
/* 455 */     return vo;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TokenRegistrationVO getTokenRegistration(String tokenId, String username) {
/* 460 */     TokenRegistrationVO tokenRegi = getUserServiceDao().returnTokenRegistration(tokenId);
/*     */     
/* 462 */     if (tokenRegi == null || tokenRegi.getDeviceAgentId() == -1 || !username.equalsIgnoreCase(tokenRegi.getUsername())) {
/* 463 */       throw new ReturnCodeException(ReturnCodes.OTP_SN_NOT_FOUND, "The otp token(sn=" + tokenId + ") is not mapping with user(username=" + username + ").");
/*     */     }
/*     */     
/* 466 */     if (!TokenStatus.OCCUPIED.equals(tokenRegi.getTokenStatus())) {
/* 467 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "The otp token(sn=" + tokenId + ") is not available with user(username=" + username + ").");
/*     */     }
/*     */     
/* 470 */     if (tokenRegi.getLost().toBoolean()) {
/* 471 */       throw new ReturnCodeException(ReturnCodes.LOST, "The otp token(sn=" + tokenId + ") is lost.");
/*     */     }
/*     */     
/* 474 */     return tokenRegi;
/*     */   }
/*     */ 
/*     */   
/*     */   protected UserVO getUser(String username) {
/* 479 */     UserVO.validateUsername(username);
/*     */     
/* 481 */     UserVO user = getUserServiceDao().returnUser(username);
/*     */     
/* 483 */     if (user == null) {
/* 484 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no user with " + username);
/*     */     }
/*     */     
/* 487 */     if (user.getDisabled().toBoolean()) {
/* 488 */       throw new ReturnCodeException(ReturnCodes.USER_FORBIDDEN, "The user has been locked with username=" + username);
/*     */     }
/*     */     
/* 491 */     return user;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthMethodTypes() {
/* 497 */     return AuthMethodTypes.BIOTP;
/*     */   }
/*     */ 
/*     */   
/*     */   private UserServiceDao getUserServiceDao() {
/* 502 */     if (this.biotpUserServiceDao == null) {
/* 503 */       this.biotpUserServiceDao = new BiotpUserServiceDao();
/*     */     }
/* 505 */     return (UserServiceDao)this.biotpUserServiceDao;
/*     */   }
/*     */   
/*     */   private static String generateQRCodeImageBinary(String tid, long tsExpired) {
/*     */     try {
/* 510 */       int dimension = SystemSettingsDao.getInt("externalApi.qrCodeDimension");
/* 511 */       return QRCodeGenerator.generateQRCodeBinary(tid, tsExpired, dimension);
/* 512 */     } catch (Exception e) {
/* 513 */       throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\biotp\BiotpGeneralService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */