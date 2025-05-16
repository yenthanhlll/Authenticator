/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.biotp;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ProductAuthType;
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.authentication.service.UAFResponseService;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.UserAuthFailCountDao;
/*     */ import com.dreammirae.mmth.db.dao.UserMultiAuthTypeInfoDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.transport.SendUAFResponse;
/*     */ import com.dreammirae.mmth.fido.transport.ServerResponse;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.vo.CheckFailCntVO;
/*     */ import com.dreammirae.mmth.vo.UserAuthFailCountVO;
/*     */ import com.dreammirae.mmth.vo.UserMultiAuthTypeVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.bean.MiraeAssetVietnamLogData;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service("authTypeValifyService")
/*     */ public class AuthTypeValifyService
/*     */   extends UAFResponseService
/*     */ {
/*  41 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.biotp.AuthTypeValifyService.class);
/*     */   
/*     */   protected static final String LOG_FORMAT_ERROR_MSG = "@@@ ERR MSG IN PROC :: ";
/*     */   
/*     */   @Autowired
/*     */   private UserMultiAuthTypeInfoDao UserMultiAuthTypeInfoDao;
/*     */   @Autowired
/*     */   private UserAuthFailCountDao authFailCntDao;
/*     */   @Autowired
/*     */   private BiotpUserServiceDao biotpUserServiceDao;
/*  51 */   private int OTP_AUTH_ERROR_MAX_COUNT = 10;
/*     */ 
/*     */   
/*     */   public AuthenticationResponseLocator save(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/*  55 */     UserVO user = getUserServiceDao().returnUser(reqLoc.getUsername());
/*  56 */     int code = AuthMethodTypes.BIOTP.getCode();
/*  57 */     if (user == null) {
/*     */       
/*  59 */       user = UserVO.createNewInstance(reqLoc.getUsername(), reqLoc.getProductType(), null);
/*  60 */       getUserServiceDao().saveUser(user);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  65 */     if (ProductAuthType.getAuthType(reqLoc.getProductAuthType()) == ProductAuthType.PATTERN && 
/*  66 */       ProductType.getProductType(reqLoc.getProductType()) != ProductType.BIOTP) {
/*  67 */       respLoc.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*  68 */       return respLoc;
/*     */     } 
/*     */ 
/*     */     
/*  72 */     if (ProductAuthType.getAuthType(reqLoc.getProductAuthType()) == ProductAuthType.PIN && 
/*  73 */       ProductType.getProductType(reqLoc.getProductType()) == ProductType.NONE)
/*     */     {
/*  75 */       code = AuthMethodTypes.PIN.getCode();
/*     */     }
/*     */ 
/*     */     
/*  79 */     if (reqLoc.getProductType() == ProductType.BIOTP.getCode())
/*     */     {
/*  81 */       if (user.getProductType() != ProductType.BIOTP) {
/*     */         
/*  83 */         respLoc.setReturnCode(ReturnCodes.DEVICE_UNAUTHORIED);
/*  84 */         return respLoc;
/*     */       } 
/*     */     }
/*     */     
/*  88 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData(reqLoc.getMacAddress(), reqLoc.getIp());
/*  89 */     if (reqLoc.getMacAddress() == null && reqLoc.getIp() == 0L)
/*     */     {
/*  91 */       logData = null;
/*     */     }
/*     */     
/*     */     try {
/*  95 */       UserMultiAuthTypeVO userAuthTypeVO = UserMultiAuthTypeVO.createNewInstance(user.getId(), reqLoc.getProductType(), reqLoc.getAuthData(), reqLoc.getProductAuthType());
/*     */       
/*  97 */       if (reqLoc.getNewAuthData() != null) {
/*     */         
/*  99 */         int totalfailCnt = this.UserMultiAuthTypeInfoDao.getOneByProductType(userAuthTypeVO);
/* 100 */         UserMultiAuthTypeVO oldUserAuthType = this.UserMultiAuthTypeInfoDao.getOneByObj(userAuthTypeVO);
/* 101 */         if (oldUserAuthType == null) {
/*     */           
/* 103 */           respLoc.setReturnCode(ReturnCodes.NOT_FOUND_AUTH_TYPE);
/* 104 */           return respLoc;
/*     */         } 
/*     */         
/* 107 */         if (ProductType.getProductType(reqLoc.getProductType()) == ProductType.BIOTP && (
/* 108 */           ProductAuthType.getAuthType(reqLoc.getProductAuthType()) == ProductAuthType.PIN || 
/* 109 */           ProductAuthType.getAuthType(reqLoc.getProductAuthType()) == ProductAuthType.PATTERN))
/*     */         {
/* 111 */           this.OTP_AUTH_ERROR_MAX_COUNT = SystemSettingsDao.getInt("integrate.biotpMaxFailCount");
/*     */         }
/*     */         
/* 114 */         if (this.OTP_AUTH_ERROR_MAX_COUNT <= totalfailCnt) {
/*     */           
/* 116 */           respLoc.setReturnCode(ReturnCodes.EXCEEDED_AUTH_ERROR);
/* 117 */           return respLoc;
/*     */         } 
/*     */         
/* 120 */         String result = validationAuthData(oldUserAuthType, reqLoc.getAuthData(), this.OTP_AUTH_ERROR_MAX_COUNT, totalfailCnt);
/*     */         
/* 122 */         if (StringUtils.equals("success", result)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 127 */           respLoc.setReturnCode(ReturnCodes.OK);
/*     */         } else {
/*     */           
/* 130 */           respLoc.setReturnCode(ReturnCodes.AUTH_DATA_VALIDATAE_FAIL);
/* 131 */           respLoc.setAuthFailCnt(Integer.valueOf(totalfailCnt + 1));
/* 132 */           return respLoc;
/*     */         } 
/*     */         
/* 135 */         userAuthTypeVO.setAuthData(Base64Utils.encode(reqLoc.getNewAuthData()));
/*     */       } 
/*     */       
/* 138 */       this.UserMultiAuthTypeInfoDao.save(userAuthTypeVO);
/* 139 */       updatePinPatternCount(user);
/* 140 */       respLoc.setReturnCode(ReturnCodes.OK);
/* 141 */       if (ProductType.NONE.getCode() == reqLoc.getProductType() && ProductAuthType.PIN.getCode() == reqLoc.getProductAuthType())
/*     */       {
/* 143 */         getLogOperationTypes("REG", reqLoc).addServiceLog(AuthMethodTypes.getAuthMethodTypes(code), user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null, null, null, null, null, null, (ICustomLogData)logData);
/*     */       }
/*     */     }
/* 146 */     catch (ReturnCodeException e) {
/* 147 */       LOG.error(String.format("@@@ ERR MSG IN PROC :: " + e.getMessage(), new Object[] { e }));
/* 148 */       if (ProductType.NONE.getCode() == reqLoc.getProductType() && ProductAuthType.PIN.getCode() == reqLoc.getProductAuthType())
/*     */       {
/* 150 */         getLogOperationTypes("REG", reqLoc).addServiceLog(AuthMethodTypes.getAuthMethodTypes(code), user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), null, null, null, null, null, null, (ICustomLogData)logData);
/*     */       }
/* 152 */       respLoc.setReturnCode(e.getReturnCode());
/* 153 */     } catch (Exception e) {
/* 154 */       LOG.error(String.format("@@@ ERR MSG IN PROC :: " + e.getMessage(), new Object[] { e }));
/* 155 */       respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/* 156 */       if (ProductType.NONE.getCode() == reqLoc.getProductType() && ProductAuthType.PIN.getCode() == reqLoc.getProductAuthType())
/*     */       {
/* 158 */         getLogOperationTypes("REG", reqLoc).addServiceLog(AuthMethodTypes.getAuthMethodTypes(code), user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, null, null, null, null, null, null, (ICustomLogData)logData);
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return respLoc;
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationResponseLocator authenticate(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/* 167 */     UserMultiAuthTypeVO info = new UserMultiAuthTypeVO();
/* 168 */     int code = AuthMethodTypes.BIOTP.getCode();
/*     */ 
/*     */     
/* 171 */     if (ProductAuthType.getAuthType(reqLoc.getProductAuthType()) == ProductAuthType.PIN && 
/* 172 */       ProductType.getProductType(reqLoc.getProductType()) == ProductType.NONE) {
/*     */       
/* 174 */       this.OTP_AUTH_ERROR_MAX_COUNT = SystemSettingsDao.getInt("integrate.pinMaxFailCount");
/* 175 */       code = AuthMethodTypes.PIN.getCode();
/*     */     }
/* 177 */     else if (ProductType.getProductType(reqLoc.getProductType()) == ProductType.BIOTP && (
/* 178 */       ProductAuthType.getAuthType(reqLoc.getProductAuthType()) == ProductAuthType.PIN || 
/* 179 */       ProductAuthType.getAuthType(reqLoc.getProductAuthType()) == ProductAuthType.PATTERN)) {
/*     */       
/* 181 */       this.OTP_AUTH_ERROR_MAX_COUNT = SystemSettingsDao.getInt("integrate.biotpMaxFailCount");
/*     */     }
/*     */     else {
/*     */       
/* 185 */       respLoc.setReturnCode(ReturnCodes.NOT_FOUND_AUTH_TYPE);
/* 186 */       return respLoc;
/*     */     } 
/*     */     
/* 189 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData(reqLoc.getMacAddress(), reqLoc.getIp());
/* 190 */     if (reqLoc.getMacAddress() == null && reqLoc.getIp() == 0L)
/*     */     {
/* 192 */       logData = null;
/*     */     }
/*     */     
/* 195 */     UserVO user = getUserServiceDao().returnUser(reqLoc.getUsername());
/*     */     try {
/* 197 */       if (user == null) {
/*     */         
/* 199 */         respLoc.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/* 200 */         return respLoc;
/*     */       } 
/* 202 */       info.setUserId(user.getId());
/* 203 */       info.setProductAuthType(ProductAuthType.getAuthType(reqLoc.getProductAuthType()));
/* 204 */       info.setProductType(ProductType.getProductType(reqLoc.getProductType()));
/* 205 */       int totalfailCnt = this.UserMultiAuthTypeInfoDao.getOneByProductType(info);
/*     */       
/* 207 */       UserMultiAuthTypeVO authTypeVO = this.UserMultiAuthTypeInfoDao.getOneByObj(info);
/* 208 */       if (authTypeVO == null) {
/*     */         
/* 210 */         respLoc.setReturnCode(ReturnCodes.NOT_FOUND_AUTH_TYPE);
/* 211 */         return respLoc;
/*     */       } 
/* 213 */       LogActionTypes logActionType = LogActionTypes.SUCCESS;
/* 214 */       UserAuthFailCountVO failCntVO = new UserAuthFailCountVO();
/* 215 */       failCntVO.setAuthTypeId(authTypeVO.getId());
/*     */       
/* 217 */       if (totalfailCnt >= this.OTP_AUTH_ERROR_MAX_COUNT) {
/*     */         
/* 219 */         respLoc.setReturnCode(ReturnCodes.EXCEEDED_AUTH_ERROR);
/* 220 */         return respLoc;
/*     */       } 
/*     */       
/* 223 */       if (authTypeVO.getAuthData() != null) {
/*     */         
/* 225 */         String result = validationAuthData(authTypeVO, reqLoc.getAuthData(), this.OTP_AUTH_ERROR_MAX_COUNT, totalfailCnt);
/* 226 */         if (StringUtils.equals("success", result)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 231 */           respLoc.setReturnCode(ReturnCodes.OK);
/*     */         } else {
/*     */           
/* 234 */           respLoc.setReturnCode(ReturnCodes.AUTH_FAILED);
/* 235 */           respLoc.setAuthFailCnt(Integer.valueOf(totalfailCnt + 1));
/* 236 */           logActionType = LogActionTypes.FAIL;
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 250 */         respLoc.setReturnCode(ReturnCodes.AUTH_FAILED);
/* 251 */         logActionType = LogActionTypes.FAIL;
/*     */       } 
/*     */ 
/*     */       
/* 255 */       getLogOperationTypes("AUTH", reqLoc).addServiceLog(AuthMethodTypes.getAuthMethodTypes(code), user.getUsername(), OpRequstTypes.HTTP_API, logActionType, respLoc.getReturnCode(), reqLoc.getDeviceId(), reqLoc.getDeviceType(), null, null, null, null, (ICustomLogData)logData);
/*     */     }
/* 257 */     catch (ReturnCodeException e) {
/* 258 */       LOG.error(String.format("@@@ ERR MSG IN PROC :: " + e.getMessage(), new Object[] { e }));
/* 259 */       getLogOperationTypes("AUTH", reqLoc).addServiceLog(AuthMethodTypes.getAuthMethodTypes(code), user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, e.getReturnCode(), reqLoc.getDeviceId(), reqLoc.getDeviceType(), null, null, null, null, (ICustomLogData)logData);
/* 260 */       respLoc.setReturnCode(e.getReturnCode());
/* 261 */     } catch (Exception e) {
/* 262 */       LOG.error(String.format("@@@ ERR MSG IN PROC :: " + e.getMessage(), new Object[] { e }));
/* 263 */       getLogOperationTypes("AUTH", reqLoc).addServiceLog(AuthMethodTypes.getAuthMethodTypes(code), user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, reqLoc.getDeviceId(), reqLoc.getDeviceType(), null, null, null, null, (ICustomLogData)logData);
/* 264 */       respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/* 266 */     return respLoc;
/*     */   }
/*     */   
/*     */   private String validationAuthData(UserMultiAuthTypeVO authTypeVO, String newAuthData, int maxAuthCnt, int totalfailCnt) {
/* 270 */     String result = "fail";
/* 271 */     UserAuthFailCountVO failCntVO = new UserAuthFailCountVO();
/* 272 */     failCntVO.setAuthTypeId(authTypeVO.getId());
/* 273 */     String decAuthData = Base64Utils.decode(authTypeVO.getAuthData());
/* 274 */     if (StringUtils.equals(newAuthData, decAuthData)) {
/*     */       
/* 276 */       failCntVO.setFailCnt(0);
/* 277 */       result = "success";
/*     */     } else {
/*     */       
/* 280 */       failCntVO.setFailCnt(totalfailCnt + 1);
/* 281 */       failCntVO.setTsLastAuthFail(System.currentTimeMillis());
/*     */     } 
/* 283 */     this.authFailCntDao.save(failCntVO);
/*     */     
/* 285 */     return result;
/*     */   }
/*     */   
/*     */   private LogOperationTypes getLogOperationTypes(String methodType, AuthenticationRequestLocator reqLoc) {
/* 289 */     if (StringUtils.equals(methodType, "REG"))
/*     */     {
/* 291 */       return LogOperationTypes.REG;
/*     */     }
/*     */ 
/*     */     
/* 295 */     if (reqLoc.getProductType() == ProductType.BIOTP.getCode() && reqLoc.getProductAuthType() == ProductAuthType.PATTERN.getCode()) {
/* 296 */       return LogOperationTypes.AUTH_PATTERN;
/*     */     }
/*     */ 
/*     */     
/* 300 */     return LogOperationTypes.AUTH_PIN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationResponseLocator resetAuthFailCnt(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/* 306 */     UserMultiAuthTypeVO authTypeInfo = new UserMultiAuthTypeVO();
/* 307 */     UserVO user = getUserServiceDao().returnUser(reqLoc.getUsername());
/*     */     
/*     */     try {
/* 310 */       if (user == null) {
/*     */         
/* 312 */         respLoc.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/* 313 */         return respLoc;
/*     */       } 
/*     */       
/* 316 */       authTypeInfo.setUserId(user.getId());
/* 317 */       authTypeInfo.setProductAuthType(ProductAuthType.getAuthType(reqLoc.getProductAuthType()));
/* 318 */       authTypeInfo.setProductType(ProductType.getProductType(reqLoc.getProductType()));
/*     */       
/* 320 */       UserMultiAuthTypeVO authTypeVO = this.UserMultiAuthTypeInfoDao.getOneByObj(authTypeInfo);
/* 321 */       if (authTypeVO == null) {
/*     */         
/* 323 */         respLoc.setReturnCode(ReturnCodes.NOT_FOUND_AUTH_TYPE);
/* 324 */         return respLoc;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 330 */       updatePinPatternCount(user);
/*     */       
/* 332 */       respLoc.setReturnCode(ReturnCodes.OK);
/* 333 */       return respLoc;
/*     */     }
/* 335 */     catch (ReturnCodeException e) {
/* 336 */       LOG.error(String.format("@@@ ERR MSG IN PROC :: " + e.getMessage(), new Object[] { e }));
/* 337 */       respLoc.setReturnCode(e.getReturnCode());
/*     */ 
/*     */       
/* 340 */       return respLoc;
/*     */     } 
/*     */   }
/*     */   public AuthenticationResponseLocator resetAuthData(AuthenticationResponseLocator respLoc, AuthenticationRequestLocator reqLoc) {
/* 344 */     UserVO user = getUserServiceDao().returnUser(reqLoc.getUsername());
/* 345 */     if (user == null) {
/*     */ 
/*     */       
/* 348 */       respLoc.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/* 349 */       return respLoc;
/*     */     } 
/*     */     
/* 352 */     UserMultiAuthTypeVO authTypeInfo = new UserMultiAuthTypeVO();
/* 353 */     authTypeInfo.setUserId(user.getId());
/* 354 */     this.UserMultiAuthTypeInfoDao.delete(authTypeInfo);
/* 355 */     respLoc.setReturnCode(ReturnCodes.OK);
/* 356 */     return respLoc;
/*     */   }
/*     */   
/*     */   public CheckFailCntVO checkFailCount(AuthenticationRequestLocator reqLoc) {
/* 360 */     UserVO user = getUserServiceDao().returnUser(reqLoc.getUsername());
/* 361 */     CheckFailCntVO failCntVO = new CheckFailCntVO();
/*     */     
/* 363 */     if (user == null) {
/*     */ 
/*     */       
/* 366 */       failCntVO.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/* 367 */       return failCntVO;
/*     */     } 
/*     */     
/* 370 */     if (user.getStatus() == UserStatus.DISCARD || user.getStatus() == UserStatus.NOT_AVAILABLE) {
/*     */ 
/*     */       
/* 373 */       failCntVO.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/* 374 */       return failCntVO;
/*     */     } 
/*     */     
/* 377 */     UserMultiAuthTypeVO authTypeInfo = new UserMultiAuthTypeVO();
/* 378 */     authTypeInfo.setUserId(user.getId());
/* 379 */     authTypeInfo.setProductType(ProductType.getProductType(reqLoc.getProductType()));
/*     */     
/* 381 */     List<UserMultiAuthTypeVO> list = this.UserMultiAuthTypeInfoDao.getContentListByObj(authTypeInfo);
/* 382 */     for (UserMultiAuthTypeVO UserMultiAuthTypeVO : list) {
/*     */       
/* 384 */       failCntVO.setUsername(Base64Utils.encodeUrl(user.getUsername()));
/* 385 */       if (UserMultiAuthTypeVO.getProductAuthType() == ProductAuthType.PIN) {
/*     */         
/* 387 */         failCntVO.setPinFailCnt(UserMultiAuthTypeVO.getFailCnt());
/*     */         
/*     */         continue;
/*     */       } 
/* 391 */       failCntVO.setPatternFailCnt(UserMultiAuthTypeVO.getFailCnt());
/*     */     } 
/*     */     
/* 394 */     failCntVO.setReturnCode(ReturnCodes.OK);
/* 395 */     return failCntVO;
/*     */   }
/*     */   
/*     */   public void updatePinPatternCount(UserVO user) {
/* 399 */     UserMultiAuthTypeVO authTypeInfo = new UserMultiAuthTypeVO();
/* 400 */     authTypeInfo.setUserId(user.getId());
/* 401 */     authTypeInfo.setProductType(ProductType.getProductType(user.getProductType().getCode()));
/* 402 */     getUserMultiAuthTypeInfoDao().updateFailCntByProductType(authTypeInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected UserServiceDao getUserServiceDao() {
/* 408 */     if (this.biotpUserServiceDao == null) {
/* 409 */       this.biotpUserServiceDao = new BiotpUserServiceDao();
/*     */     }
/* 411 */     return (UserServiceDao)this.biotpUserServiceDao;
/*     */   }
/*     */ 
/*     */   
/*     */   private UserMultiAuthTypeInfoDao getUserMultiAuthTypeInfoDao() {
/* 416 */     if (this.UserMultiAuthTypeInfoDao == null) {
/* 417 */       this.UserMultiAuthTypeInfoDao = new UserMultiAuthTypeInfoDao();
/*     */     }
/* 419 */     return this.UserMultiAuthTypeInfoDao;
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthMethodTypes() {
/* 424 */     return null;
/*     */   }
/*     */   
/*     */   protected void doProcResponseImp(Operation op, ServerResponse serverResponse, SendUAFResponse sendUAFResponse) {}
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\biotp\AuthTypeValifyService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */