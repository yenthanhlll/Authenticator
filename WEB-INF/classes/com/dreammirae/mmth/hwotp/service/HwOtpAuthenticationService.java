/*     */ package WEB-INF.classes.com.dreammirae.mmth.hwotp.service;
/*     */ 
/*     */ import com.dreammirae.hwotp.HwOTP;
/*     */ import com.dreammirae.hwotp.MrOtpResult;
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ProductAuthType;
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.service.biotp.AuthTypeValifyService;
/*     */ import com.dreammirae.mmth.db.dao.HwTokenDao;
/*     */ import com.dreammirae.mmth.db.dao.HwTokenPolicyDao;
/*     */ import com.dreammirae.mmth.db.dao.IssuanceHistoryDao;
/*     */ import com.dreammirae.mmth.db.dao.TokenDao;
/*     */ import com.dreammirae.mmth.db.dao.UserMultiAuthTypeInfoDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.HwTokenPolicyVO;
/*     */ import com.dreammirae.mmth.vo.HwTokenVO;
/*     */ import com.dreammirae.mmth.vo.IssuanceHistoryVO;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserMultiAuthTypeVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.HwOtpResp;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.bean.MiraeAssetVietnamLogData;
/*     */ import com.dreammirae.mmth.vo.types.DeviceIssueranceStatus;
/*     */ import com.dreammirae.mmth.vo.types.HwTokenTypes;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
/*     */ import com.dreammirae.timeotp.OTP_MIRAE;
/*     */ import com.google.gson.Gson;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class HwOtpAuthenticationService
/*     */ {
/*     */   @Autowired
/*     */   private HwTokenDao hwTokenDao;
/*     */   @Autowired
/*     */   private HwTokenPolicyDao hwTokenPolicyDao;
/*     */   @Autowired
/*     */   private BiotpUserServiceDao userServiceDao;
/*     */   @Autowired
/*     */   private UserMultiAuthTypeInfoDao UserMultiAuthTypeInfoDao;
/*     */   @Autowired
/*     */   private TokenDao tokenDao;
/*     */   @Autowired
/*     */   private AuthTypeValifyService authTypeValifyService;
/*  65 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.hwotp.service.HwOtpAuthenticationService.class);
/*     */   protected static final String LOG_FORMAT_ERROR_MSG = "@@ ERROR MSG :: %s";
/*  67 */   public String tranHashHex = "EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F";
/*     */ 
/*     */ 
/*     */   
/*  71 */   private final String TYPE_ADVANCED_OTP = "03";
/*  72 */   private final String DEFAULT_POLICY_PK = "1";
/*     */   
/*     */   public String issueOtp(String user, String tokenId, long ip, String macAddress, String deviceId, String comment, String branchId, String accountName) {
/*  75 */     String json = null;
/*  76 */     HwOtpResp resp = new HwOtpResp();
/*  77 */     Gson gson = new Gson();
/*  78 */     String decUserName = Base64Utils.decode(user);
/*     */     
/*  80 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData();
/*  81 */     if (!StringUtils.isEmpty(macAddress))
/*  82 */       logData.setMacAddress(Base64Utils.decode(macAddress)); 
/*  83 */     if (ip != 0L) {
/*  84 */       logData.setIp(ip);
/*     */     }
/*  86 */     if (StringUtils.isEmpty(tokenId)) {
/*  87 */       resp.setReturnCode(ReturnCodes.ISSUE_TOKEN_ID_NONE.getCode());
/*  88 */       json = gson.toJson(resp);
/*  89 */       return json;
/*     */     } 
/*     */     
/*  92 */     if (StringUtils.isEmpty(user)) {
/*  93 */       resp.setReturnCode(ReturnCodes.ISSUE_USER_ID_ERROR.getCode());
/*  94 */       json = gson.toJson(resp);
/*  95 */       return json;
/*     */     } 
/*  97 */     UserVO userInfo = getUserServiceDao().returnUser(decUserName);
/*  98 */     if (userInfo == null) {
/*     */       
/* 100 */       userInfo = UserVO.createNewInstance(decUserName, ProductType.HWOTP.getCode(), accountName);
/* 101 */       this.userServiceDao.saveUser(userInfo);
/*     */       
/* 103 */       userInfo = getUserServiceDao().returnUser(decUserName);
/*     */     
/*     */     }
/* 106 */     else if (userInfo.getStatus() == UserStatus.AVAILABLE) {
/*     */ 
/*     */       
/* 109 */       if (userInfo.getProductType() == ProductType.BIOTP) {
/* 110 */         WebApiRequestParam param = new WebApiRequestParam();
/*     */         
/* 112 */         UserServiceLocator resultLoc = new UserServiceLocator(AuthMethodTypes.BIOTP);
/* 113 */         getUserServiceDao().forceDeregByUser(userInfo, param.createCustomLogData(), resultLoc);
/*     */ 
/*     */         
/* 116 */         getAuthTypeValifyService().updatePinPatternCount(userInfo);
/*     */       }
/* 118 */       else if (userInfo.getProductType() == ProductType.MATRIX) {
/* 119 */         userInfo.setStatus(UserStatus.DISCARD);
/* 120 */         getUserServiceDao().updateUserAuthType(userInfo);
/* 121 */         LogOperationTypes.DEREG.addServiceLog(AuthMethodTypes.MATRIX, userInfo.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null, null, null, null, (ICustomLogData)logData);
/*     */       } else {
/*     */         
/* 124 */         resp.setReturnCode(ReturnCodes.ISSUE_NOT_AVAILABLE.getCode());
/* 125 */         json = gson.toJson(resp);
/* 126 */         LogOperationTypes.REG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.ISSUE_NOT_AVAILABLE, deviceId, null, null, null, Base64Utils.decode(tokenId), null, (ICustomLogData)logData);
/* 127 */         return json;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 132 */     HwTokenVO vo = this.hwTokenDao.getOneByToken(Base64Utils.decode(tokenId));
/* 133 */     if (vo == null) {
/* 134 */       resp.setReturnCode(ReturnCodes.ISSUE_KEY_DATA_NONE.getCode());
/* 135 */       json = gson.toJson(resp);
/* 136 */       LogOperationTypes.REG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.ISSUE_KEY_DATA_NONE, deviceId, null, null, null, Base64Utils.decode(tokenId), null, (ICustomLogData)logData);
/* 137 */       return json;
/*     */     } 
/*     */     
/* 140 */     if (vo.getStatus() != TokenStatus.AVAILABLE) {
/* 141 */       resp.setReturnCode(ReturnCodes.ISSUE_NOT_AVAILABLE.getCode());
/* 142 */       json = gson.toJson(resp);
/* 143 */       LogOperationTypes.REG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.ISSUE_NOT_AVAILABLE, deviceId, null, null, null, Base64Utils.decode(tokenId), null, (ICustomLogData)logData);
/* 144 */       return json;
/*     */     } 
/*     */     
/* 147 */     if (this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId())) != null) {
/* 148 */       resp.setReturnCode(ReturnCodes.ISSUE_USER_ID_ERROR.getCode());
/* 149 */       json = gson.toJson(resp);
/* 150 */       LogOperationTypes.REG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.ISSUE_USER_ID_ERROR, deviceId, null, null, null, Base64Utils.decode(tokenId), null, (ICustomLogData)logData);
/* 151 */       return json;
/*     */     } 
/*     */     
/* 154 */     vo.setUserId(userInfo.getId());
/* 155 */     vo.setBranchId(branchId);
/* 156 */     vo.setComment((comment != null) ? Base64Utils.encode(comment) : null);
/* 157 */     vo.setUsername(decUserName);
/* 158 */     this.hwTokenDao.updateOccupied(vo);
/*     */     
/* 160 */     resp.setReturnCode(ReturnCodes.OK.getCode());
/* 161 */     json = gson.toJson(resp);
/* 162 */     LogOperationTypes.REG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, null, null, null, Base64Utils.decode(tokenId), null, (ICustomLogData)logData);
/*     */     
/* 164 */     userInfo.setProductType(ProductType.HWOTP);
/* 165 */     userInfo.setStatus(UserStatus.AVAILABLE);
/* 166 */     getUserServiceDao().updateUserAuthType(userInfo);
/*     */     
/* 168 */     DeviceIssueranceStatus issueStatus = DeviceIssueranceStatus.NEW_USER_REGISTER;
/* 169 */     IssuanceHistoryVO issuance = issueStatus.getIssuanceTypes().createIssuanceHistoryVO(userInfo, new UserDeviceVO(), new DeviceAppAgentVO(), null, Base64Utils.decode(tokenId));
/* 170 */     IssuanceHistoryDao.saveIssurance(issuance);
/* 171 */     return json;
/*     */   }
/*     */   
/*     */   private AuthTypeValifyService getAuthTypeValifyService() {
/* 175 */     if (this.authTypeValifyService == null) {
/* 176 */       this.authTypeValifyService = new AuthTypeValifyService();
/*     */     }
/* 178 */     return this.authTypeValifyService;
/*     */   }
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
/*     */   
/*     */   public String getChallenge(String user) {
/* 303 */     String challenge = "";
/* 304 */     MrOtpResult mr = null;
/* 305 */     String json = null;
/* 306 */     HwOtpResp resp = new HwOtpResp();
/* 307 */     Gson gson = new Gson();
/*     */     
/* 309 */     UserVO userInfo = getUserServiceDao().returnUser(Base64Utils.decode(user));
/* 310 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId()));
/*     */     
/* 312 */     if (vo == null) {
/* 313 */       resp.setReturnCode(ReturnCodes.NOT_ISSUED.getCode());
/* 314 */       json = gson.toJson(resp);
/* 315 */       return json;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 324 */     mr = HwOTP.genChallenge(vo.getTokenId(), vo.getTokenKey(), this.tranHashHex);
/*     */     
/* 326 */     if (mr.getRcCode() == 0) {
/* 327 */       challenge = mr.getChallenge();
/* 328 */       vo.setTokenKey(mr.getTokenKey());
/* 329 */       this.hwTokenDao.updateKey(vo);
/* 330 */       resp.setReturnCode(ReturnCodes.OK.getCode());
/* 331 */       resp.setChallenge(challenge);
/* 332 */       json = gson.toJson(resp);
/*     */     } else {
/*     */       
/* 335 */       resp.setReturnCode(String.format("%04d", new Object[] { Integer.valueOf(mr.getRcCode()) }));
/* 336 */       json = gson.toJson(resp);
/*     */     } 
/*     */     
/* 339 */     return json;
/*     */   }
/*     */ 
/*     */   
/*     */   public String verify(String user, String otp, long ip, String macAddress, String deviceId, String sessionCode) {
/* 344 */     MrOtpResult mr = null;
/* 345 */     String returnCode = null;
/*     */     
/* 347 */     String json = null;
/* 348 */     HwOtpResp resp = new HwOtpResp();
/* 349 */     Gson gson = new Gson();
/*     */     
/* 351 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData();
/* 352 */     if (!StringUtils.isEmpty(macAddress))
/* 353 */       logData.setMacAddress(Base64Utils.decode(macAddress)); 
/* 354 */     if (ip != 0L) {
/* 355 */       logData.setIp(ip);
/*     */     }
/* 357 */     UserVO userInfo = getUserServiceDao().returnUser(Base64Utils.decode(user));
/* 358 */     if (userInfo == null) {
/*     */       
/* 360 */       resp.setReturnCode(ReturnCodes.ISSUE_USER_ID_ERROR.getCode());
/* 361 */       json = gson.toJson(resp);
/* 362 */       return json;
/*     */     } 
/*     */     
/* 365 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId()));
/*     */     
/* 367 */     String decUserName = Base64Utils.decode(user);
/* 368 */     if (vo == null) {
/* 369 */       resp.setReturnCode(ReturnCodes.NOT_ISSUED.getCode());
/* 370 */       json = gson.toJson(resp);
/* 371 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.NOT_ISSUED, deviceId, null, null, null, null, null, (ICustomLogData)logData);
/* 372 */       return json;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 377 */     int unlockCnt = vo.getUnlockCnt();
/*     */     
/* 379 */     HwTokenPolicyVO policy = policyInfo();
/*     */     
/* 381 */     if (unlockCnt >= policy.getMaxAuthFailCnt().intValue()) {
/* 382 */       resp.setReturnCode(ReturnCodes.EXCEEDED_AUTH_ERROR.getCode());
/* 383 */       json = gson.toJson(resp);
/* 384 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.EXCEEDED_AUTH_ERROR, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 385 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 389 */     if (vo.getLost().getCode().equals("Y")) {
/* 390 */       resp.setReturnCode(ReturnCodes.LOST.getCode());
/* 391 */       json = gson.toJson(resp);
/* 392 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.LOST, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 393 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 397 */     if (vo.getStatus().getCode().equals(TokenStatus.AVAILABLE.getCode())) {
/* 398 */       resp.setReturnCode(ReturnCodes.NOT_ISSUED.getCode());
/* 399 */       json = gson.toJson(resp);
/* 400 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.NOT_ISSUED, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 401 */       return json;
/*     */     } 
/*     */     
/* 404 */     if (vo.getStatus().getCode().equals(TokenStatus.DISCARD.getCode())) {
/* 405 */       resp.setReturnCode(ReturnCodes.DISUSED.getCode());
/* 406 */       json = gson.toJson(resp);
/* 407 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.DISUSED, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 408 */       return json;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     if (vo.getType().equals("03")) {
/* 420 */       mr = HwOTP.verify(HwTokenTypes.ADVANCED_OTP.getCode(), vo.getTokenId(), vo.getTokenKey(), Base64Utils.decode(otp), this.tranHashHex);
/*     */     } else {
/*     */       
/* 423 */       mr = HwOTP.verify(HwTokenTypes.MIRAE_OTP.getCode(), vo.getTokenId(), vo.getTokenKey(), Base64Utils.decode(otp), null);
/*     */     } 
/*     */ 
/*     */     
/* 427 */     returnCode = String.format("%04d", new Object[] { Integer.valueOf(mr.getRcCode()) });
/*     */ 
/*     */     
/* 430 */     if (returnCode.equals(ReturnCodes.AUTH_FAILED.getCode()) || returnCode.equals(ReturnCodes.AUTH_FAILED_REUSE.getCode()) || returnCode
/* 431 */       .equals(ReturnCodes.CORRECTION_REQUIRE.getCode())) {
/* 432 */       vo.setUnlockCnt(++unlockCnt);
/* 433 */       resp.setUnlockCnt(Integer.valueOf(unlockCnt));
/*     */     }
/* 435 */     else if (returnCode.equals(ReturnCodes.OK.getCode())) {
/* 436 */       vo.setUnlockCnt(0);
/*     */     } 
/* 438 */     vo.setTokenKey(mr.getTokenKey());
/* 439 */     this.hwTokenDao.updateKey(vo);
/*     */     
/* 441 */     resp.setReturnCode(returnCode);
/*     */     
/* 443 */     if (!StringUtils.isBlank(sessionCode)) {
/*     */       
/* 445 */       resp.setUserName(userInfo.getUsername());
/* 446 */       resp.setMultiLoginYN(userInfo.getMultiLoginYN());
/* 447 */       resp.setProductTypeCode(Integer.valueOf(userInfo.getProductType().getCode()));
/* 448 */       resp.setStatus(userInfo.getStatus().getCode());
/*     */     } 
/*     */     
/* 451 */     json = gson.toJson(resp);
/*     */     
/* 453 */     if (StringUtils.equals(returnCode, ReturnCodes.OK.getCode())) {
/* 454 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 455 */     } else if (StringUtils.equals(returnCode, ReturnCodes.AUTH_FAILED_REUSE.getCode())) {
/* 456 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.AUTH_FAILED, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 457 */     } else if (StringUtils.equals(returnCode, ReturnCodes.AUTH_CHALLENGE_EXPIRED.getCode())) {
/* 458 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.AUTH_FAILED, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 459 */     } else if (StringUtils.equals(returnCode, ReturnCodes.CORRECTION_REQUIRE.getCode())) {
/* 460 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.AUTH_FAILED, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 461 */     } else if (!StringUtils.equals(returnCode, ReturnCodes.OTP_RESPONSE_TYPE_ERROR.getCode())) {
/*     */ 
/*     */       
/* 464 */       LogOperationTypes.OTP_AUTH.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.AUTH_FAILED, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/*     */     } 
/* 466 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String disuseOtp(String user, String tokenId, long ip, String macAddress, String deviceId) {
/* 474 */     String json = null;
/* 475 */     HwOtpResp resp = new HwOtpResp();
/* 476 */     Gson gson = new Gson();
/* 477 */     String decUserName = Base64Utils.decode(user);
/*     */     
/* 479 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData();
/* 480 */     if (!StringUtils.isEmpty(macAddress))
/* 481 */       logData.setMacAddress(Base64Utils.decode(macAddress)); 
/* 482 */     if (ip != 0L) {
/* 483 */       logData.setIp(ip);
/*     */     }
/* 485 */     if (!StringUtils.isEmpty(tokenId)) {
/* 486 */       tokenId = Base64Utils.decode(tokenId);
/*     */     }
/*     */     
/* 489 */     if (StringUtils.isEmpty(user)) {
/* 490 */       resp.setReturnCode(ReturnCodes.DISUSE_USER_ID_NONE.getCode());
/* 491 */       json = gson.toJson(resp);
/* 492 */       LogOperationTypes.DEREG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.DISUSE_USER_ID_NONE, deviceId, null, null, null, tokenId, null, (ICustomLogData)logData);
/* 493 */       return json;
/*     */     } 
/*     */     
/* 496 */     UserVO userInfo = getUserServiceDao().returnUser(decUserName);
/* 497 */     if (userInfo == null) {
/*     */       
/* 499 */       resp.setReturnCode(ReturnCodes.DISUSE_USER_ID_NONE.getCode());
/* 500 */       json = gson.toJson(resp);
/* 501 */       return json;
/*     */     } 
/*     */     
/* 504 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId()));
/*     */     
/* 506 */     if (vo == null) {
/* 507 */       resp.setReturnCode(ReturnCodes.DISUSE_KEY_DATA_NONE.getCode());
/* 508 */       json = gson.toJson(resp);
/* 509 */       LogOperationTypes.DEREG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.DISUSE_KEY_DATA_NONE, deviceId, null, null, null, null, null, (ICustomLogData)logData);
/* 510 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 514 */     if (tokenId != null && !vo.getTokenId().equals(tokenId)) {
/* 515 */       resp.setReturnCode(ReturnCodes.DISUSE_TOKEN_ID_NONE.getCode());
/* 516 */       json = gson.toJson(resp);
/* 517 */       LogOperationTypes.DEREG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.DISUSE_TOKEN_ID_NONE, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 518 */       return json;
/*     */     } 
/*     */     
/* 521 */     if (vo.getStatus() != TokenStatus.OCCUPIED) {
/* 522 */       resp.setReturnCode(ReturnCodes.DISUSE_NOT_AVAILABLE.getCode());
/* 523 */       json = gson.toJson(resp);
/* 524 */       LogOperationTypes.DEREG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.DISUSE_NOT_AVAILABLE, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 525 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 529 */     vo.setUserId(0);
/* 530 */     vo.setUsername(null);
/* 531 */     vo.setStatus(TokenStatus.DISCARD);
/* 532 */     this.hwTokenDao.updateDiscard(vo);
/*     */     
/* 534 */     resp.setReturnCode(ReturnCodes.OK.getCode());
/* 535 */     json = gson.toJson(resp);
/*     */ 
/*     */     
/* 538 */     userInfo.setStatus(UserStatus.DISCARD);
/* 539 */     getUserServiceDao().updateUserAuthType(userInfo);
/*     */     
/* 541 */     LogOperationTypes.DEREG.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 542 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String resetUnlockCnt(String user) {
/* 548 */     String json = null;
/* 549 */     HwOtpResp resp = new HwOtpResp();
/* 550 */     Gson gson = new Gson();
/*     */     
/* 552 */     UserVO userInfo = getUserServiceDao().returnUser(Base64Utils.decode(user));
/* 553 */     if (userInfo == null) {
/*     */       
/* 555 */       resp.setReturnCode(ReturnCodes.SUSPEND_USER_ID_NONE.getCode());
/* 556 */       json = gson.toJson(resp);
/* 557 */       return json;
/*     */     } 
/*     */     
/* 560 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId()));
/*     */     
/* 562 */     if (vo == null) {
/* 563 */       resp.setReturnCode(ReturnCodes.ISSUE_KEY_DATA_NONE.getCode());
/* 564 */       json = gson.toJson(resp);
/* 565 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 569 */     if (vo.getStatus() != TokenStatus.OCCUPIED) {
/* 570 */       resp.setReturnCode(ReturnCodes.NOT_ISSUED.getCode());
/* 571 */       json = gson.toJson(resp);
/* 572 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 576 */     if (vo.getLost().getCode().equals(YNStatus.Y.getCode())) {
/* 577 */       resp.setReturnCode(ReturnCodes.LOST.getCode());
/* 578 */       json = gson.toJson(resp);
/* 579 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 583 */     vo.setUnlockCnt(0);
/* 584 */     this.hwTokenDao.updateUnLockCnt(vo);
/*     */     
/* 586 */     resp.setReturnCode(ReturnCodes.OK.getCode());
/* 587 */     json = gson.toJson(resp);
/* 588 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String genUnlockcode(String user) {
/* 594 */     MrOtpResult mr = null;
/* 595 */     String unlockCode = null;
/*     */     
/* 597 */     String json = null;
/* 598 */     HwOtpResp resp = new HwOtpResp();
/* 599 */     Gson gson = new Gson();
/*     */     
/* 601 */     UserVO userInfo = getUserServiceDao().returnUser(Base64Utils.decode(user));
/* 602 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId()));
/* 603 */     if (vo == null) {
/* 604 */       resp.setReturnCode(ReturnCodes.ISSUE_KEY_DATA_NONE.getCode());
/* 605 */       json = gson.toJson(resp);
/* 606 */       return json;
/*     */     } 
/*     */     
/* 609 */     mr = HwOTP.unlock(HwTokenTypes.MIRAE_OTP.getCode(), vo.getTokenId(), vo.getTokenKey());
/*     */     
/* 611 */     if (mr.getRcCode() == 0) {
/* 612 */       unlockCode = mr.getUnlock();
/* 613 */       vo.setTokenKey(mr.getTokenKey());
/* 614 */       this.hwTokenDao.updateKey(vo);
/* 615 */       resp.setReturnCode(ReturnCodes.OK.getCode());
/* 616 */       resp.setUnlockCode(unlockCode);
/* 617 */       json = gson.toJson(resp);
/*     */     } else {
/*     */       
/* 620 */       resp.setReturnCode(String.format("%04d", new Object[] { Integer.valueOf(mr.getRcCode()) }));
/* 621 */       json = gson.toJson(resp);
/*     */     } 
/*     */     
/* 624 */     return json;
/*     */   }
/*     */ 
/*     */   
/*     */   public String setLost(String user, long ip, String macAddress, String deviceId) {
/* 629 */     String json = null;
/* 630 */     HwOtpResp resp = new HwOtpResp();
/* 631 */     Gson gson = new Gson();
/*     */     
/* 633 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData();
/* 634 */     if (!StringUtils.isEmpty(macAddress))
/* 635 */       logData.setMacAddress(Base64Utils.decode(macAddress)); 
/* 636 */     if (ip != 0L) {
/* 637 */       logData.setIp(ip);
/*     */     }
/* 639 */     long tsLost = System.currentTimeMillis();
/*     */     
/* 641 */     UserVO userInfo = getUserServiceDao().returnUser(Base64Utils.decode(user));
/* 642 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId()));
/*     */     
/* 644 */     String decUserName = Base64Utils.decode(user);
/* 645 */     if (vo == null) {
/* 646 */       resp.setReturnCode(ReturnCodes.ISSUE_KEY_DATA_NONE.getCode());
/* 647 */       json = gson.toJson(resp);
/* 648 */       LogOperationTypes.LOST.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.ISSUE_KEY_DATA_NONE, deviceId, null, null, null, null, null, (ICustomLogData)logData);
/* 649 */       return json;
/*     */     } 
/*     */     
/* 652 */     if (vo.getStatus() != TokenStatus.OCCUPIED) {
/* 653 */       resp.setReturnCode(ReturnCodes.NOT_ISSUED.getCode());
/* 654 */       json = gson.toJson(resp);
/* 655 */       LogOperationTypes.LOST.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.NOT_ISSUED, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 656 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 660 */     if (vo.getLost().getCode().equals(YNStatus.Y.getCode())) {
/* 661 */       resp.setReturnCode(ReturnCodes.LOST.getCode());
/* 662 */       json = gson.toJson(resp);
/* 663 */       LogOperationTypes.LOST.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.LOST, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 664 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 668 */     vo.setLost(YNStatus.Y);
/* 669 */     vo.setTsLost(tsLost);
/* 670 */     this.hwTokenDao.updateLost(vo);
/*     */     
/* 672 */     resp.setReturnCode(ReturnCodes.OK.getCode());
/* 673 */     json = gson.toJson(resp);
/*     */     
/* 675 */     userInfo.setProductType(ProductType.HWOTP);
/* 676 */     userInfo.setStatus(UserStatus.LOST_STOLEN);
/* 677 */     getUserServiceDao().updateUserAuthType(userInfo);
/*     */     
/* 679 */     LogOperationTypes.LOST.addServiceLog(AuthMethodTypes.HWOTP, decUserName, OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 680 */     return json;
/*     */   }
/*     */ 
/*     */   
/*     */   public String clearLost(String user, long ip, String macAddress, String deviceId) {
/* 685 */     String json = null;
/* 686 */     HwOtpResp resp = new HwOtpResp();
/* 687 */     Gson gson = new Gson();
/*     */     
/* 689 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData();
/* 690 */     if (!StringUtils.isEmpty(macAddress))
/* 691 */       logData.setMacAddress(Base64Utils.decode(macAddress)); 
/* 692 */     if (ip != 0L) {
/* 693 */       logData.setIp(ip);
/*     */     }
/* 695 */     UserVO userInfo = getUserServiceDao().returnUser(Base64Utils.decode(user));
/* 696 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId()));
/*     */     
/* 698 */     if (vo == null) {
/* 699 */       resp.setReturnCode(ReturnCodes.ISSUE_KEY_DATA_NONE.getCode());
/* 700 */       json = gson.toJson(resp);
/* 701 */       LogOperationTypes.RECOVERY.addServiceLog(AuthMethodTypes.HWOTP, Base64Utils.decode(user), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.ISSUE_KEY_DATA_NONE, deviceId, null, null, null, null, null, (ICustomLogData)logData);
/* 702 */       return json;
/*     */     } 
/*     */ 
/*     */     
/* 706 */     if (vo.getLost().getCode().equals(YNStatus.N.getCode())) {
/* 707 */       resp.setReturnCode(ReturnCodes.LOST_NOT_AVAILABLE.getCode());
/* 708 */       LogOperationTypes.RECOVERY.addServiceLog(AuthMethodTypes.HWOTP, Base64Utils.decode(user), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.LOST_NOT_AVAILABLE, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 709 */       json = gson.toJson(resp);
/* 710 */       return json;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 715 */     vo.setLost(YNStatus.N);
/* 716 */     vo.setTsLost(0L);
/* 717 */     this.hwTokenDao.updateLost(vo);
/*     */     
/* 719 */     resp.setReturnCode(ReturnCodes.OK.getCode());
/* 720 */     json = gson.toJson(resp);
/*     */     
/* 722 */     userInfo.setProductType(ProductType.HWOTP);
/* 723 */     userInfo.setStatus(UserStatus.AVAILABLE);
/* 724 */     getUserServiceDao().updateUserAuthType(userInfo);
/* 725 */     LogOperationTypes.RECOVERY.addServiceLog(AuthMethodTypes.HWOTP, Base64Utils.decode(user), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, null, null, null, vo.getTokenId(), null, (ICustomLogData)logData);
/* 726 */     return json;
/*     */   }
/*     */   
/*     */   public String syncUser(String user, String otp) {
/* 730 */     MrOtpResult mr = null;
/* 731 */     String json = null;
/* 732 */     HwOtpResp resp = new HwOtpResp();
/* 733 */     Gson gson = new Gson();
/*     */     
/* 735 */     UserVO userInfo = getUserServiceDao().returnUser(Base64Utils.decode(user));
/* 736 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId()));
/*     */     
/* 738 */     if (vo == null) {
/* 739 */       resp.setReturnCode(ReturnCodes.NOT_ISSUED.getCode());
/* 740 */       json = gson.toJson(resp);
/* 741 */       return json;
/*     */     } 
/* 743 */     int unlockCnt = vo.getUnlockCnt();
/*     */     
/* 745 */     HwTokenPolicyVO policy = policyInfo();
/*     */     
/* 747 */     if (unlockCnt >= policy.getMaxAuthFailCnt().intValue()) {
/* 748 */       resp.setReturnCode(ReturnCodes.EXCEEDED_AUTH_ERROR.getCode());
/* 749 */       json = gson.toJson(resp);
/* 750 */       return json;
/*     */     } 
/*     */     
/* 753 */     if (vo.getLost().getCode().equals("Y")) {
/* 754 */       resp.setReturnCode(ReturnCodes.LOST.getCode());
/* 755 */       json = gson.toJson(resp);
/* 756 */       return json;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 765 */     mr = HwOTP.syncUser(vo.getTokenId(), vo.getTokenKey(), Base64Utils.decode(otp));
/*     */     
/* 767 */     String returnCode = String.format("%04d", new Object[] { Integer.valueOf(mr.getRcCode()) });
/*     */     
/* 769 */     if (StringUtils.equals(ReturnCodes.EXIST_ALREADY.getMessageKey(), returnCode)) {
/* 770 */       resp.setReturnCode(ReturnCodes.EXIST_ALREADY.getCode());
/* 771 */       return gson.toJson(resp);
/*     */     } 
/*     */     
/* 774 */     if (mr.getRcCode() == 0) {
/*     */       
/* 776 */       vo.setTokenKey(mr.getTokenKey());
/* 777 */       this.hwTokenDao.updateKey(vo);
/* 778 */       resp.setReturnCode(ReturnCodes.OK.getCode());
/* 779 */       json = gson.toJson(resp);
/*     */     } 
/*     */ 
/*     */     
/* 783 */     vo.setTokenKey(mr.getTokenKey());
/* 784 */     this.hwTokenDao.updateKey(vo);
/* 785 */     resp.setReturnCode(returnCode);
/*     */     
/* 787 */     return gson.toJson(resp);
/*     */   }
/*     */   
/*     */   public String syncAdmin(String user, String otp, String nextotp) {
/* 791 */     MrOtpResult mr = null;
/* 792 */     String json = null;
/* 793 */     HwOtpResp resp = new HwOtpResp();
/* 794 */     Gson gson = new Gson();
/*     */     
/* 796 */     UserVO userInfo = getUserServiceDao().returnUser(Base64Utils.decode(user));
/* 797 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userInfo.getId()));
/*     */     
/* 799 */     if (vo == null) {
/* 800 */       resp.setReturnCode(ReturnCodes.NOT_ISSUED.getCode());
/* 801 */       json = gson.toJson(resp);
/* 802 */       return json;
/*     */     } 
/* 804 */     int unlockCnt = vo.getUnlockCnt();
/*     */     
/* 806 */     HwTokenPolicyVO policy = policyInfo();
/*     */     
/* 808 */     if (unlockCnt >= policy.getMaxAuthFailCnt().intValue()) {
/* 809 */       resp.setReturnCode(ReturnCodes.EXCEEDED_AUTH_ERROR.getCode());
/* 810 */       json = gson.toJson(resp);
/* 811 */       return json;
/*     */     } 
/*     */     
/* 814 */     if (vo.getLost().getCode().equals("Y")) {
/* 815 */       resp.setReturnCode(ReturnCodes.LOST.getCode());
/* 816 */       json = gson.toJson(resp);
/* 817 */       return json;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 826 */     mr = HwOTP.syncAdmin(vo.getTokenId(), vo.getTokenKey(), Base64Utils.decode(otp), Base64Utils.decode(nextotp));
/*     */     
/* 828 */     if (mr.getRcCode() == 0) {
/* 829 */       vo.setTokenKey(mr.getTokenKey());
/* 830 */       this.hwTokenDao.updateKey(vo);
/* 831 */       resp.setReturnCode(ReturnCodes.OK.getCode());
/*     */     } else {
/*     */       
/* 834 */       resp.setReturnCode(String.valueOf(mr.getRcCode()));
/*     */     } 
/*     */     
/* 837 */     json = gson.toJson(resp);
/* 838 */     return json;
/*     */   }
/*     */   
/*     */   public String checkUser(String user) {
/* 842 */     String userJson = null;
/* 843 */     UserVO userVO = this.userServiceDao.returnUser(Base64Utils.decode(user));
/* 844 */     if (userVO == null)
/*     */     {
/* 846 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 853 */     Gson gson = new Gson();
/* 854 */     HwTokenVO vo = this.hwTokenDao.getOneByObj(new HwTokenVO(userVO.getId()));
/* 855 */     if (userVO.getProductType() == ProductType.HWOTP) {
/*     */       
/* 857 */       if (vo == null) {
/* 858 */         return null;
/*     */       }
/*     */       
/* 861 */       UserMultiAuthTypeVO authTypeVO = new UserMultiAuthTypeVO();
/* 862 */       authTypeVO.setUserId(userVO.getId());
/* 863 */       authTypeVO.setProductType(ProductType.NONE);
/* 864 */       authTypeVO.setProductAuthType(ProductAuthType.PIN);
/* 865 */       int failCnt = this.UserMultiAuthTypeInfoDao.getOneByProductType(authTypeVO);
/* 866 */       vo.setPinFailCnt(failCnt);
/* 867 */       if (vo.getLost().equals(YNStatus.Y)) {
/* 868 */         vo.setStatus(TokenStatus.LOST);
/*     */       }
/* 870 */       userJson = gson.toJson(vo);
/*     */     }
/* 872 */     else if (userVO.getProductType() == ProductType.BIOTP) {
/* 873 */       TokenVO token = new TokenVO();
/* 874 */       token.setUsername(userVO.getUsername());
/* 875 */       TokenVO biotpToken = this.tokenDao.getOneByObj(token);
/* 876 */       if (biotpToken == null) {
/* 877 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 881 */       UserMultiAuthTypeVO authTypeVO = new UserMultiAuthTypeVO();
/* 882 */       authTypeVO.setUserId(userVO.getId());
/* 883 */       authTypeVO.setProductType(ProductType.BIOTP);
/*     */       
/* 885 */       int pinFailCnt = this.UserMultiAuthTypeInfoDao.getOneByProductType(authTypeVO);
/* 886 */       biotpToken.setAuthFailCnt(pinFailCnt);
/*     */       
/* 888 */       userJson = gson.toJson(biotpToken);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 893 */     return userJson;
/*     */   }
/*     */ 
/*     */   
/*     */   public String checkUser(String user, String tokenId) {
/* 898 */     String userJson = null;
/* 899 */     if (!StringUtils.isEmpty(tokenId)) {
/* 900 */       tokenId = Base64Utils.decode(tokenId);
/*     */     }
/* 902 */     HwTokenVO vo = this.hwTokenDao.getOneByToken(tokenId);
/*     */     
/* 904 */     Gson gson = new Gson();
/*     */     
/* 906 */     if (vo == null) {
/* 907 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 918 */     userJson = gson.toJson(vo);
/*     */     
/* 920 */     return userJson;
/*     */   }
/*     */ 
/*     */   
/*     */   public String checkToken(String tokenId) {
/* 925 */     String tokenJson = null;
/* 926 */     if (!StringUtils.isEmpty(tokenId)) {
/* 927 */       tokenId = Base64Utils.decode(tokenId);
/*     */     }
/* 929 */     HwTokenVO vo = this.hwTokenDao.getOneByToken(tokenId);
/*     */     
/* 931 */     Gson gson = new Gson();
/*     */     
/* 933 */     if (vo == null) {
/* 934 */       return null;
/*     */     }
/*     */     
/* 937 */     tokenJson = gson.toJson(vo);
/*     */     
/* 939 */     return tokenJson;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setPolicy(HwTokenPolicyVO policy) {
/* 944 */     boolean result = OTP_MIRAE.policy(policy.getNormAuthTmSkew().intValue(), policy.getUserSyncTmSkew().intValue(), policy.getAdminSyncTmSkew().intValue(), policy
/* 945 */         .getInitAuthTmSkew().intValue(), 10, 60);
/*     */     
/* 947 */     if (result) {
/* 948 */       this.hwTokenPolicyDao.save(policy);
/*     */     }
/*     */     
/* 951 */     return result;
/*     */   }
/*     */   
/*     */   public HwTokenPolicyVO policyInfo() {
/* 955 */     HwTokenPolicyVO result = this.hwTokenPolicyDao.getOneByPK("1");
/* 956 */     return result;
/*     */   }
/*     */   
/*     */   public boolean deletePolicy(HwTokenPolicyVO policy) {
/* 960 */     this.hwTokenPolicyDao.deleteByPk(policy.getName());
/* 961 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private UserServiceDao getUserServiceDao() {
/* 966 */     if (this.userServiceDao == null) {
/* 967 */       this.userServiceDao = new BiotpUserServiceDao();
/*     */     }
/* 969 */     return (UserServiceDao)this.userServiceDao;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\hwotp\service\HwOtpAuthenticationService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */