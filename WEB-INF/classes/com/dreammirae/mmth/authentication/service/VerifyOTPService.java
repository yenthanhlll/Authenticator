/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service;
/*     */ import com.dreammirae.mmth.MMTHConstants;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationInfos;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.policy.AuthManagementScope;
/*     */ import com.dreammirae.mmth.authentication.service.IVerifyOTPService;
/*     */ import com.dreammirae.mmth.authentication.service.OTPDataService;
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.IAuthenticationManagerDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.util.notary.SHA;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.bean.MiraeAssetVietnamLogData;
/*     */ import com.dreammirae.mmth.vo.types.ChallengeStatus;
/*     */ import com.dreammirae.mmth.vo.types.ChallengeTypes;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ public abstract class VerifyOTPService implements IVerifyOTPService {
/*  38 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.VerifyOTPService.class);
/*     */   
/*     */   protected static final String LOG_FORMAT_ERROR_MSG = "@@ ERR MSG IN PROC :: ";
/*     */   
/*     */   @Autowired
/*     */   private OTPDataService otpDataService;
/*     */ 
/*     */   
/*     */   public void verifyingOTP(WebApiResponse response, String otp, String tid, String username, String macAddress, long ip) throws ReturnCodeException {
/*     */     try {
/*  48 */       ServerChallengeVO challenge = null;
/*  49 */       UserVO user = null;
/*     */       
/*  51 */       if (StringUtils.isEmpty(username)) {
/*  52 */         challenge = getServerChallege(tid, username);
/*  53 */         user = validateAndGetUser(challenge.getUsername());
/*     */       } else {
/*  55 */         user = validateAndGetUser(username);
/*  56 */         challenge = getServerChallege(tid, username);
/*     */       } 
/*     */       
/*  59 */       UserServiceLocator loc = new UserServiceLocator(getAuthMethodTypes());
/*  60 */       if (macAddress != null)
/*     */       {
/*  62 */         loc.setMacAddress(macAddress);
/*     */       }
/*     */       
/*  65 */       if (ip != 0L)
/*     */       {
/*  67 */         loc.setIp(ip);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*  72 */         verifyCommonOTP(loc, challenge, user, otp);
/*  73 */         response.setReturnCode(ReturnCodes.OK);
/*     */       }
/*  75 */       catch (ReturnCodeException e) {
/*     */         
/*  77 */         if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/*  78 */           .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/*  79 */           .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/*  80 */           .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/*  81 */           .equals(e.getReturnCode())) {
/*     */           
/*  83 */           AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/*  84 */           AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, getAuthMethodTypes());
/*     */           
/*  86 */           response.setAuthInfo(info);
/*     */         } 
/*     */         
/*  89 */         throw e;
/*     */       }
/*     */     
/*  92 */     } catch (ReturnCodeException e) {
/*  93 */       response.setReturnCode(e.getReturnCode());
/*  94 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/*  95 */     } catch (Exception e) {
/*  96 */       response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*  97 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyingOTP(AuthenticationResponseLocator response, AuthenticationRequestLocator requestLoc, boolean encOTP) {
/*     */     try {
/* 107 */       String otp = requestLoc.getOtp();
/*     */       
/* 109 */       if (StringUtils.isEmpty(otp)) {
/* 110 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The parameter 'otp' is required.");
/*     */       }
/*     */       
/* 113 */       if (encOTP) {
/* 114 */         if (!MMTHConstants.REGEX_HEX_PATTERN.matcher(otp).matches()) {
/* 115 */           throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'OTP' Must be hexadecimal stirng.");
/*     */         }
/*     */       }
/* 118 */       else if (!MMTHConstants.REGEX_OTP_PATTERN.matcher(otp).matches()) {
/* 119 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'OTP' Must be a 6-digit number.");
/*     */       } 
/*     */ 
/*     */       
/* 123 */       if (StringUtils.isEmpty(requestLoc.getUsername()) && StringUtils.isEmpty(requestLoc.getTid())) {
/* 124 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'userName' or 'tid' must be required.");
/*     */       }
/*     */       
/* 127 */       ServerChallengeVO challenge = null;
/* 128 */       UserVO user = null;
/*     */       
/* 130 */       if (StringUtils.isEmpty(requestLoc.getUsername())) {
/* 131 */         challenge = getServerChallege(requestLoc.getTid(), requestLoc.getUsername());
/* 132 */         user = validateAndGetUser(challenge.getUsername());
/*     */       } else {
/* 134 */         user = validateAndGetUser(requestLoc.getUsername());
/* 135 */         challenge = getServerChallege(requestLoc.getTid(), requestLoc.getUsername());
/*     */       } 
/*     */       
/* 138 */       UserServiceLocator loc = new UserServiceLocator(getAuthMethodTypes());
/*     */ 
/*     */       
/*     */       try {
/* 142 */         if (encOTP) {
/* 143 */           verifyCommonEncOTP(loc, challenge, user, otp);
/*     */         } else {
/* 145 */           verifyCommonOTP(loc, challenge, user, otp);
/*     */         } 
/*     */         
/* 148 */         TokenRegistrationVO tokenRegi = loc.getTokenRegi();
/*     */         
/* 150 */         if (tokenRegi != null && !StringUtils.isEmpty(requestLoc.getOtpSn()))
/*     */         {
/*     */           
/* 153 */           if (!requestLoc.getOtpSn().equalsIgnoreCase(tokenRegi.getTokenId())) {
/* 154 */             throw new ReturnCodeException(ReturnCodes.OTP_SN_NOT_FOUND, "OTP serial numbers are not equal.");
/*     */           }
/*     */         }
/*     */         
/* 158 */         UserDeviceVO ud = getUserServiceDao().returnUserDevice(loc.getDeviceAppAgent());
/* 159 */         AppAgentVO appAgent = AppAgentDao.getAcceptableAppAgent(loc.getDeviceAppAgent().getAgentId());
/* 160 */         response.setDeviceId(ud.getDeviceId());
/* 161 */         response.setDeviceType(ud.getDeviceType());
/* 162 */         response.setModel(ud.getModel());
/* 163 */         response.setOtpSn(tokenRegi.getTokenId());
/* 164 */         response.setPkgName(appAgent.getPkgUnique());
/*     */         
/* 166 */         if (requestLoc.getSessionCode() != null && !StringUtils.isEmpty(requestLoc.getSessionCode()))
/*     */         {
/* 168 */           response.setUserName(loc.getUser().getUsername());
/* 169 */           response.setSessionCode(requestLoc.getSessionCode());
/* 170 */           response.setMultiLoginYN(loc.getUser().getMultiLoginYN());
/* 171 */           response.setProductTypeCode(loc.getUser().getProductType().getCode());
/* 172 */           response.setStatus(loc.getUser().getStatus().getCode());
/*     */         }
/*     */       
/* 175 */       } catch (ReturnCodeException e) {
/* 176 */         if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/* 177 */           .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/* 178 */           .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 179 */           .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/* 180 */           .equals(e.getReturnCode())) {
/*     */           
/* 182 */           AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 183 */           AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, getAuthMethodTypes());
/*     */           
/* 185 */           response.setAuthFailCnt(Integer.valueOf(info.getFailCnt()));
/*     */           
/* 187 */           if (loc.getDeviceAppAgent() != null) {
/* 188 */             UserDeviceVO ud = getUserServiceDao().returnUserDevice(loc.getDeviceAppAgent());
/* 189 */             TokenRegistrationVO tokenRegi = loc.getTokenRegi();
/* 190 */             AppAgentVO appAgent = AppAgentDao.getAcceptableAppAgent(loc.getDeviceAppAgent().getAgentId());
/* 191 */             response.setDeviceId(ud.getDeviceId());
/* 192 */             response.setDeviceType(ud.getDeviceType());
/* 193 */             response.setModel(ud.getModel());
/* 194 */             response.setOtpSn(tokenRegi.getTokenId());
/* 195 */             response.setPkgName(appAgent.getPkgUnique());
/*     */           } 
/*     */         } 
/*     */         
/* 199 */         throw e;
/*     */       }
/*     */     
/* 202 */     } catch (ReturnCodeException e) {
/* 203 */       throw e;
/* 204 */     } catch (Exception e) {
/* 205 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyingEncOTP(WebApiResponse response, String otp, String tid, String username) throws ReturnCodeException {
/*     */     try {
/* 214 */       if (StringUtils.isEmpty(otp)) {
/* 215 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The parameter 'otp' is required.");
/*     */       }
/*     */       
/* 218 */       if (!MMTHConstants.REGEX_HEX_PATTERN.matcher(otp).matches()) {
/* 219 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'OTP' Must be hexastring.");
/*     */       }
/*     */       
/* 222 */       ServerChallengeVO challenge = null;
/* 223 */       UserVO user = null;
/*     */       
/* 225 */       if (StringUtils.isEmpty(username)) {
/* 226 */         challenge = getServerChallege(tid, username);
/* 227 */         user = validateAndGetUser(challenge.getUsername());
/*     */       } else {
/* 229 */         user = validateAndGetUser(username);
/* 230 */         challenge = getServerChallege(tid, username);
/*     */       } 
/*     */       
/*     */       try {
/* 234 */         UserServiceLocator loc = new UserServiceLocator(getAuthMethodTypes());
/* 235 */         verifyCommonEncOTP(loc, challenge, user, otp);
/* 236 */         response.setReturnCode(ReturnCodes.OK);
/*     */       }
/* 238 */       catch (ReturnCodeException e) {
/*     */         
/* 240 */         if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/* 241 */           .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/* 242 */           .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 243 */           .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/* 244 */           .equals(e.getReturnCode())) {
/*     */           
/* 246 */           AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 247 */           AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, getAuthMethodTypes());
/*     */           
/* 249 */           response.setAuthInfo(info);
/*     */         } 
/*     */         
/* 252 */         throw e;
/*     */       }
/*     */     
/*     */     }
/* 256 */     catch (ReturnCodeException e) {
/* 257 */       throw e;
/* 258 */     } catch (Exception e) {
/* 259 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyingTranOTP(WebApiResponse response, String otp, String tranInfo, String tid, String username) throws ReturnCodeException {
/*     */     try {
/* 269 */       ServerChallengeVO challenge = null;
/* 270 */       UserVO user = null;
/*     */       
/* 272 */       if (StringUtils.isEmpty(username)) {
/* 273 */         challenge = getServerChallege(tid, username);
/* 274 */         user = validateAndGetUser(challenge.getUsername());
/*     */       } else {
/* 276 */         user = validateAndGetUser(username);
/* 277 */         challenge = getServerChallege(tid, username);
/*     */       } 
/*     */       
/* 280 */       UserServiceLocator loc = new UserServiceLocator(getAuthMethodTypes());
/*     */       
/*     */       try {
/* 283 */         verifyTranInfoOTP(loc, challenge, user, otp, tranInfo);
/* 284 */         response.setReturnCode(ReturnCodes.OK);
/*     */       }
/* 286 */       catch (ReturnCodeException e) {
/*     */         
/* 288 */         if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/* 289 */           .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/* 290 */           .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 291 */           .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/* 292 */           .equals(e.getReturnCode())) {
/* 293 */           AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 294 */           AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, getAuthMethodTypes());
/*     */           
/* 296 */           response.setAuthInfo(info);
/*     */         } 
/*     */         
/* 299 */         throw e;
/*     */       }
/*     */     
/* 302 */     } catch (ReturnCodeException e) {
/* 303 */       response.setReturnCode(e.getReturnCode());
/* 304 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/* 305 */     } catch (Exception e) {
/* 306 */       response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 307 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyingTranOTP(AuthenticationResponseLocator response, AuthenticationRequestLocator requestLoc, boolean encOTP) {
/* 314 */     if (StringUtils.isEmpty(requestLoc.getOtp())) {
/* 315 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The parameter 'otp' is required.");
/*     */     }
/*     */     
/* 318 */     String otp = requestLoc.getOtp();
/*     */     
/* 320 */     if (encOTP) {
/* 321 */       if (!MMTHConstants.REGEX_HEX_PATTERN.matcher(otp).matches()) {
/* 322 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'OTP' Must be hexadecimal stirng.");
/*     */       }
/*     */     }
/* 325 */     else if (!MMTHConstants.REGEX_OTP_PATTERN.matcher(otp).matches()) {
/* 326 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'OTP' Must be a 6-digit number.");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 331 */     if (StringUtils.isEmpty(requestLoc.getTranInfo())) {
/* 332 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The parameter 'tranInfo' is required.");
/*     */     }
/*     */     
/* 335 */     ServerChallengeVO challenge = null;
/* 336 */     UserVO user = null;
/*     */     
/* 338 */     if (StringUtils.isEmpty(requestLoc.getUsername())) {
/* 339 */       challenge = getServerChallege(requestLoc.getTid(), requestLoc.getUsername());
/* 340 */       user = validateAndGetUser(challenge.getUsername());
/*     */     } else {
/* 342 */       user = validateAndGetUser(requestLoc.getUsername());
/* 343 */       challenge = getServerChallege(requestLoc.getTid(), requestLoc.getUsername());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 348 */       UserServiceLocator loc = new UserServiceLocator(getAuthMethodTypes());
/*     */ 
/*     */       
/*     */       try {
/* 352 */         if (encOTP) {
/* 353 */           verifyTranInfoEncOTP(loc, challenge, user, otp, requestLoc.getTranInfo());
/*     */         } else {
/* 355 */           verifyTranInfoOTP(loc, challenge, user, otp, requestLoc.getTranInfo());
/*     */         } 
/*     */         
/* 358 */         TokenRegistrationVO tokenRegi = loc.getTokenRegi();
/*     */         
/* 360 */         if (tokenRegi != null && !StringUtils.isEmpty(requestLoc.getOtpSn()))
/*     */         {
/*     */           
/* 363 */           if (!requestLoc.getOtpSn().equalsIgnoreCase(tokenRegi.getTokenId())) {
/* 364 */             throw new ReturnCodeException(ReturnCodes.OTP_SN_NOT_FOUND, "OTP serial numbers are not equal.");
/*     */           }
/*     */         }
/*     */         
/* 368 */         if (loc.getDeviceAppAgent() != null) {
/* 369 */           UserDeviceVO ud = getUserServiceDao().returnUserDevice(loc.getDeviceAppAgent());
/* 370 */           AppAgentVO appAgent = AppAgentDao.getAcceptableAppAgent(loc.getDeviceAppAgent().getAgentId());
/* 371 */           response.setDeviceId(ud.getDeviceId());
/* 372 */           response.setDeviceType(ud.getDeviceType());
/* 373 */           response.setModel(ud.getModel());
/* 374 */           response.setOtpSn(tokenRegi.getTokenId());
/* 375 */           response.setPkgName(appAgent.getPkgUnique());
/*     */         }
/*     */       
/* 378 */       } catch (ReturnCodeException e) {
/* 379 */         if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION.equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST.equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 380 */           .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED.equals(e.getReturnCode())) {
/*     */           
/* 382 */           AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 383 */           AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, getAuthMethodTypes());
/*     */           
/* 385 */           response.setAuthFailCnt(Integer.valueOf(info.getFailCnt()));
/*     */           
/* 387 */           if (loc.getDeviceAppAgent() != null) {
/* 388 */             UserDeviceVO ud = getUserServiceDao().returnUserDevice(loc.getDeviceAppAgent());
/* 389 */             TokenRegistrationVO tokenRegi = loc.getTokenRegi();
/* 390 */             AppAgentVO appAgent = AppAgentDao.getAcceptableAppAgent(loc.getDeviceAppAgent().getAgentId());
/* 391 */             response.setDeviceId(ud.getDeviceId());
/* 392 */             response.setDeviceType(ud.getDeviceType());
/* 393 */             response.setModel(ud.getModel());
/* 394 */             response.setOtpSn(tokenRegi.getTokenId());
/* 395 */             response.setPkgName(appAgent.getPkgUnique());
/*     */           } 
/*     */         } 
/*     */         
/* 399 */         throw e;
/*     */       } 
/* 401 */     } catch (ReturnCodeException e) {
/* 402 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/* 403 */       throw e;
/* 404 */     } catch (Exception e) {
/* 405 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/* 406 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyingTranEncOTP(WebApiResponse response, String otp, String tranInfo, String tid, String username) throws ReturnCodeException {
/*     */     try {
/* 414 */       ServerChallengeVO challenge = null;
/* 415 */       UserVO user = null;
/*     */       
/* 417 */       if (StringUtils.isEmpty(username)) {
/* 418 */         challenge = getServerChallege(tid, username);
/* 419 */         user = validateAndGetUser(challenge.getUsername());
/*     */       } else {
/* 421 */         user = validateAndGetUser(username);
/* 422 */         challenge = getServerChallege(tid, username);
/*     */       } 
/*     */       
/* 425 */       UserServiceLocator loc = new UserServiceLocator(getAuthMethodTypes());
/*     */ 
/*     */       
/*     */       try {
/* 429 */         verifyTranInfoEncOTP(loc, challenge, user, otp, tranInfo);
/* 430 */         response.setReturnCode(ReturnCodes.OK);
/*     */       }
/* 432 */       catch (ReturnCodeException e) {
/*     */         
/* 434 */         if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/* 435 */           .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/* 436 */           .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 437 */           .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/* 438 */           .equals(e.getReturnCode())) {
/* 439 */           AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 440 */           AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, getAuthMethodTypes());
/*     */           
/* 442 */           response.setAuthInfo(info);
/*     */         } 
/*     */         
/* 445 */         throw e;
/*     */       }
/*     */     
/* 448 */     } catch (ReturnCodeException e) {
/* 449 */       response.setReturnCode(e.getReturnCode());
/* 450 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/* 451 */     } catch (Exception e) {
/* 452 */       response.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 453 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void verifyCommonOTPForIssueCode(WebApiResponse response, UserVO user, String otp) {
/* 459 */     ServerChallengeVO challenge = getServerChallege(null, user.getUsername());
/*     */ 
/*     */     
/* 462 */     UserServiceLocator loc = new UserServiceLocator(getAuthMethodTypes());
/*     */     try {
/* 464 */       verifyCommonOTP(loc, challenge, user, otp);
/* 465 */     } catch (ReturnCodeException e) {
/* 466 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/* 467 */       if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/* 468 */         .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/* 469 */         .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 470 */         .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/* 471 */         .equals(e.getReturnCode())) {
/* 472 */         AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 473 */         AuthenticationInfos info = scope.getLatestAuthManagerByUser((IAuthenticationManagerDao)getUserServiceDao(), user, getAuthMethodTypes());
/*     */         
/* 475 */         response.setAuthInfo(info);
/*     */       } 
/*     */       
/* 478 */       throw e;
/* 479 */     } catch (Exception e) {
/* 480 */       LOG.error("@@ ERR MSG IN PROC :: " + e.getMessage());
/* 481 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void verifyCommonOTP(UserServiceLocator loc, ServerChallengeVO challenge, UserVO user, String otp) {
/* 487 */     DeviceAppAgentVO deviceAppAgent = getDeviceAppAgentVO(challenge);
/* 488 */     TokenRegistrationVO tokenRegi = getTokenRegistration(deviceAppAgent);
/* 489 */     loc.setDeviceAppAgent(deviceAppAgent);
/* 490 */     loc.setTokenRegi(tokenRegi);
/* 491 */     validateAuthentication(user, deviceAppAgent);
/* 492 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData(loc.getMacAddress(), loc.getIp());
/*     */     
/* 494 */     UserDeviceVO userDevice = getUserServiceDao().returnUserDevice(deviceAppAgent);
/*     */     
/*     */     try {
/* 497 */       validateServerChallenge(challenge, ChallengeTypes.AUTH, null);
/*     */       
/* 499 */       loc.setChallenge(challenge);
/* 500 */       loc.setUser(user);
/*     */       
/* 502 */       getOtpDataService().verifyOTP(tokenRegi, challenge, otp);
/*     */       
/* 504 */       challenge.setStatus(ChallengeStatus.DONE);
/* 505 */       challenge.setTsDone(System.currentTimeMillis());
/*     */       
/* 507 */       getUserServiceDao().saveOTPResponse(loc);
/* 508 */       LogOperationTypes.OTP_AUTH.addServiceLog(getAuthMethodTypes(), challenge.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, userDevice.getDeviceId(), userDevice.getDeviceType(), null, null, tokenRegi.getTokenId(), null, (ICustomLogData)logData);
/*     */     }
/* 510 */     catch (ReturnCodeException e) {
/*     */       
/* 512 */       if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/* 513 */         .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/* 514 */         .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 515 */         .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/* 516 */         .equals(e.getReturnCode())) {
/* 517 */         getUserServiceDao().authenticationFailed(deviceAppAgent);
/*     */       }
/*     */       
/* 520 */       LogOperationTypes.OTP_AUTH.addServiceLog(getAuthMethodTypes(), challenge.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), userDevice.getDeviceId(), userDevice.getDeviceType(), null, null, tokenRegi.getTokenId(), null, (ICustomLogData)logData);
/* 521 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void verifyCommonEncOTP(UserServiceLocator loc, ServerChallengeVO challenge, UserVO user, String otp) {
/* 527 */     DeviceAppAgentVO deviceAppAgent = getDeviceAppAgentVO(challenge);
/* 528 */     TokenRegistrationVO tokenRegi = getTokenRegistration(deviceAppAgent);
/*     */     
/* 530 */     loc.setUser(user);
/* 531 */     loc.setDeviceAppAgent(deviceAppAgent);
/* 532 */     loc.setTokenRegi(tokenRegi);
/*     */     
/* 534 */     validateAuthentication(user, deviceAppAgent);
/*     */ 
/*     */     
/*     */     try {
/* 538 */       validateServerChallenge(challenge, ChallengeTypes.AUTH, null);
/*     */       
/* 540 */       loc.setChallenge(challenge);
/*     */ 
/*     */       
/* 543 */       getOtpDataService().verifyEncOTP(tokenRegi, challenge, otp);
/*     */       
/* 545 */       challenge.setStatus(ChallengeStatus.DONE);
/* 546 */       challenge.setTsDone(System.currentTimeMillis());
/*     */ 
/*     */       
/* 549 */       getUserServiceDao().saveOTPResponse(loc);
/*     */       
/* 551 */       LogOperationTypes.OTP_AUTH.addServiceLog(getAuthMethodTypes(), challenge.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null, tokenRegi.getTokenId(), deviceAppAgent, null);
/*     */     }
/* 553 */     catch (ReturnCodeException e) {
/*     */       
/* 555 */       if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/* 556 */         .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/* 557 */         .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 558 */         .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/* 559 */         .equals(e.getReturnCode())) {
/* 560 */         getUserServiceDao().authenticationFailed(deviceAppAgent);
/*     */       }
/*     */       
/* 563 */       LogOperationTypes.OTP_AUTH.addServiceLog(getAuthMethodTypes(), challenge.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), null, tokenRegi.getTokenId(), deviceAppAgent, null);
/* 564 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void verifyTranInfoOTP(UserServiceLocator loc, ServerChallengeVO challenge, UserVO user, String otp, String tranInfo) {
/* 570 */     DeviceAppAgentVO deviceAppAgent = getDeviceAppAgentVO(challenge);
/* 571 */     TokenRegistrationVO tokenRegi = getTokenRegistration(deviceAppAgent);
/* 572 */     loc.setDeviceAppAgent(deviceAppAgent);
/* 573 */     loc.setTokenRegi(tokenRegi);
/*     */     
/* 575 */     loc.setUser(user);
/* 576 */     validateAuthentication(user, deviceAppAgent);
/*     */     
/*     */     try {
/* 579 */       validateServerChallenge(challenge, ChallengeTypes.AUTH_WITH_TRAN_INFO, tranInfo);
/*     */       
/* 581 */       getOtpDataService().verifyOTP(tokenRegi, challenge, otp);
/*     */       
/* 583 */       challenge.setStatus(ChallengeStatus.DONE);
/* 584 */       challenge.setTsDone(System.currentTimeMillis());
/* 585 */       loc.setChallenge(challenge);
/*     */ 
/*     */       
/* 588 */       getUserServiceDao().saveOTPResponse(loc);
/* 589 */       LogOperationTypes.OTP_AUTH.addServiceLog(getAuthMethodTypes(), challenge.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null, tokenRegi.getTokenId(), deviceAppAgent, null);
/*     */     }
/* 591 */     catch (ReturnCodeException e) {
/*     */       
/* 593 */       if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/* 594 */         .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/* 595 */         .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 596 */         .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/* 597 */         .equals(e.getReturnCode())) {
/* 598 */         getUserServiceDao().authenticationFailed(deviceAppAgent);
/*     */       }
/*     */       
/* 601 */       LogOperationTypes.OTP_AUTH.addServiceLog(getAuthMethodTypes(), challenge.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), null, tokenRegi.getTokenId(), deviceAppAgent, null);
/* 602 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void verifyTranInfoEncOTP(UserServiceLocator loc, ServerChallengeVO challenge, UserVO user, String otp, String tranInfo) {
/* 608 */     DeviceAppAgentVO deviceAppAgent = getDeviceAppAgentVO(challenge);
/* 609 */     TokenRegistrationVO tokenRegi = getTokenRegistration(deviceAppAgent);
/*     */     
/* 611 */     loc.setUser(user);
/* 612 */     loc.setDeviceAppAgent(deviceAppAgent);
/* 613 */     loc.setTokenRegi(tokenRegi);
/*     */     
/* 615 */     validateAuthentication(user, deviceAppAgent);
/*     */     
/*     */     try {
/* 618 */       validateServerChallenge(challenge, ChallengeTypes.AUTH_WITH_TRAN_INFO, tranInfo);
/*     */       
/* 620 */       getOtpDataService().verifyEncOTP(tokenRegi, challenge, otp);
/*     */       
/* 622 */       challenge.setStatus(ChallengeStatus.DONE);
/* 623 */       challenge.setTsDone(System.currentTimeMillis());
/*     */       
/* 625 */       loc.setChallenge(challenge);
/*     */       
/* 627 */       getUserServiceDao().saveOTPResponse(loc);
/* 628 */       LogOperationTypes.OTP_AUTH.addServiceLog(getAuthMethodTypes(), challenge.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null, tokenRegi.getTokenId(), deviceAppAgent, null);
/* 629 */     } catch (ReturnCodeException e) {
/*     */       
/* 631 */       if (ReturnCodes.AUTH_FAILED.equals(e.getReturnCode()) || ReturnCodes.NOT_FIDO_AUTHENTICATION
/* 632 */         .equals(e.getReturnCode()) || ReturnCodes.EXPIRED_AUTH_REQUEST
/* 633 */         .equals(e.getReturnCode()) || ReturnCodes.INVALID_AUTH_TRAN_INFO
/* 634 */         .equals(e.getReturnCode()) || ReturnCodes.AUTH_CHALLENGE_EXPIRED
/* 635 */         .equals(e.getReturnCode())) {
/* 636 */         getUserServiceDao().authenticationFailed(deviceAppAgent);
/*     */       }
/* 638 */       LogOperationTypes.OTP_AUTH.addServiceLog(getAuthMethodTypes(), challenge.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), null, tokenRegi.getTokenId(), deviceAppAgent, null);
/* 639 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void validateAuthentication(UserVO user, DeviceAppAgentVO deviceAppAgent) {
/* 644 */     AuthManagementScope scope = SystemSettingsDao.getAuthManagementScope();
/* 645 */     scope.validateAuthentication((IAuthenticationManagerDao)getUserServiceDao(), user, deviceAppAgent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TokenRegistrationVO getTokenRegistration(DeviceAppAgentVO deviceAppAgent) {
/* 650 */     TokenRegistrationVO tokenRegi = getUserServiceDao().returnTokenRegistration(deviceAppAgent);
/*     */     
/* 652 */     if (tokenRegi == null) {
/* 653 */       throw new ReturnCodeException(ReturnCodes.DEVICE_APP_UNAUTHORIED, "Cannot find token. deviceAppAgent=" + deviceAppAgent.getId());
/*     */     }
/*     */     
/* 656 */     if (tokenRegi.getLost().toBoolean()) {
/* 657 */       throw new ReturnCodeException(ReturnCodes.LOST, "The token status is lost. tokenId=" + tokenRegi.getTokenId());
/*     */     }
/*     */     
/* 660 */     return tokenRegi;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DeviceAppAgentVO getDeviceAppAgentVO(ServerChallengeVO vo) {
/* 666 */     if (vo == null) {
/* 667 */       throw new ReturnCodeException(ReturnCodes.NOT_FIDO_AUTHENTICATION, "It seems to do not execute previous requests. The user don't have ServerChallenge information.");
/*     */     }
/*     */     
/* 670 */     DeviceAppAgentVO deviceAppAgent = getUserServiceDao().returnDeviceAppAgent(vo);
/*     */     
/* 672 */     if (deviceAppAgent == null) {
/* 673 */       throw new ReturnCodeException(ReturnCodes.DEVICE_APP_UNAUTHORIED, "Cannot find this device app agent, please register this device app agent.");
/*     */     }
/*     */     
/* 676 */     return deviceAppAgent;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerChallengeVO getServerChallege(String tid, String username) {
/* 681 */     ServerChallengeVO challenge = null;
/*     */     
/* 683 */     if (!StringUtils.isEmpty(tid)) {
/* 684 */       challenge = getUserServiceDao().returnServerChallengeByTid(tid);
/* 685 */     } else if (!StringUtils.isEmpty(username)) {
/* 686 */       challenge = getUserServiceDao().returnServerChallengeByUsername(username);
/*     */     } else {
/* 688 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The parameter 'tid' or 'username' must be included.");
/*     */     } 
/*     */     
/* 691 */     if (challenge == null) {
/* 692 */       throw new ReturnCodeException(ReturnCodes.NOT_FIDO_AUTHENTICATION, "It seems to do not execute previous requests. The user don't have ServerChallenge information.");
/*     */     }
/*     */     
/* 695 */     return challenge;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void validateServerChallenge(ServerChallengeVO challenge, ChallengeTypes challengeType, String tranInfo) {
/* 700 */     if (challenge == null) {
/* 701 */       throw new ReturnCodeException(ReturnCodes.NOT_FIDO_AUTHENTICATION, "It seems to do not execute previous requests. The user don't have ServerChallenge information.");
/*     */     }
/*     */     
/* 704 */     if (ChallengeStatus.FIDO_REQ.equals(challenge.getStatus())) {
/* 705 */       throw new ReturnCodeException(ReturnCodes.NOT_FIDO_AUTHENTICATION, "It seems to do not execute previous FIDO Authentication requests. ServerChallenge info is invalid.");
/*     */     }
/*     */     
/* 708 */     if (ChallengeStatus.DONE.equals(challenge.getStatus())) {
/* 709 */       throw new ReturnCodeException(ReturnCodes.NOT_FIDO_AUTHENTICATION, "It seems to do not execute previous requests. OTP verification request is expired.");
/*     */     }
/*     */     
/* 712 */     if (challenge.getTsLifeTime() < System.currentTimeMillis()) {
/* 713 */       throw new ReturnCodeException(ReturnCodes.AUTH_CHALLENGE_EXPIRED, "The challenge lifetime has expired.");
/*     */     }
/*     */     
/* 716 */     if (ChallengeTypes.REG.equals(challenge.getChallengeType())) {
/*     */       return;
/*     */     }
/*     */     
/* 720 */     if (!challengeType.equals(challenge.getChallengeType())) {
/* 721 */       throw new ReturnCodeException(ReturnCodes.MISSMATCH_AUTH_REQUEST, "Invalid request API for OTP verification.");
/*     */     }
/*     */     
/* 724 */     if (ChallengeTypes.AUTH_WITH_TRAN_INFO.equals(challengeType)) {
/*     */       
/* 726 */       if (StringUtils.isEmpty(tranInfo)) {
/* 727 */         throw new ReturnCodeException(ReturnCodes.INVALID_AUTH_TRAN_INFO, "'tranInfo' could not be null or empty.");
/*     */       }
/*     */       
/* 730 */       String hashTranInfo = HexUtils.toHexString(SHA.sha256Raw(tranInfo));
/*     */       
/* 732 */       if (!challenge.getTranContent().equals(hashTranInfo)) {
/* 733 */         throw new ReturnCodeException(ReturnCodes.INVALID_AUTH_TRAN_INFO, "The tranInfo is invalid..");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected UserVO validateAndGetUser(String username) {
/* 740 */     UserVO.validateUsername(username);
/*     */     
/* 742 */     UserVO user = getUserServiceDao().returnUser(username);
/*     */     
/* 744 */     if (user == null) {
/* 745 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no user with " + username);
/*     */     }
/*     */     
/* 748 */     if (user.getDisabled().toBoolean()) {
/* 749 */       throw new ReturnCodeException(ReturnCodes.USER_FORBIDDEN, "The user has been locked with username=" + username);
/*     */     }
/*     */     
/* 752 */     if (!UserStatus.AVAILABLE.equals(user.getStatus())) {
/* 753 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no available registraions with username=" + username);
/*     */     }
/*     */     
/* 756 */     int cnt = user.getRegCumulation(getAuthMethodTypes());
/*     */     
/* 758 */     if (cnt < 1) {
/* 759 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no available registraions with username=" + username);
/*     */     }
/*     */     
/* 762 */     return user;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract UserServiceDao getUserServiceDao();
/*     */ 
/*     */   
/*     */   protected OTPDataService getOtpDataService() {
/* 771 */     if (this.otpDataService == null) {
/* 772 */       this.otpDataService = new OTPDataService();
/*     */     }
/*     */     
/* 775 */     return this.otpDataService;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\VerifyOTPService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */