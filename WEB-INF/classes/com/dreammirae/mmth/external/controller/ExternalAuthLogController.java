/*     */ package WEB-INF.classes.com.dreammirae.mmth.external.controller;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFResponseService;
/*     */ import com.dreammirae.mmth.authentication.service.IVerifyOTPService;
/*     */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*     */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*     */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*     */ import com.dreammirae.mmth.rp.controller.AuthenticationController;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.bean.MiraeAssetVietnamLogData;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ @RestController("externalAuthLogController")
/*     */ @RequestMapping(value = {"/rpserver/webapi/authlog"}, consumes = {"application/rp+json; charset=utf-8"}, produces = {"application/rp+json; charset=utf-8"})
/*     */ public class ExternalAuthLogController
/*     */   extends AuthenticationController
/*     */ {
/*     */   @Autowired
/*     */   private BiotpUserServiceDao biotpUserServiceDao;
/*  42 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.external.controller.ExternalAuthLogController.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/Insert"})
/*     */   public String insertAuthLog(@RequestBody String payload, HttpServletRequest httpRequest) {
/*  51 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/*  54 */     AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*  55 */     LOG.info(genReceivedReqLogMsg(payload, httpRequest));
/*     */     
/*  57 */     String respPayload = null;
/*  58 */     long startMillis = System.currentTimeMillis();
/*  59 */     int authMethodTypeCode = getAuthMethodTypes(reqLoc.getProductType());
/*     */ 
/*     */ 
/*     */     
/*  63 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*  64 */     UserVO user = getUserServiceDao().returnUser(reqLoc.getUsername());
/*  65 */     if (user == null) {
/*     */       
/*  67 */       respLoc.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/*  68 */       return GsonUtils.gson().toJson(respLoc);
/*     */     } 
/*     */ 
/*     */     
/*  72 */     if (LogOperationTypes.AUTH_BIOMETRIC.getCode() == reqLoc.getAuthLogType()) {
/*     */       
/*  74 */       if (ProductType.NONE.equals(ProductType.getProductType(reqLoc.getProductType())) || ProductType.HWOTP
/*  75 */         .equals(ProductType.getProductType(reqLoc.getProductType())) || ProductType.MATRIX
/*  76 */         .equals(ProductType.getProductType(reqLoc.getProductType()))) {
/*  77 */         respLoc.setReturnCode(ReturnCodes.INVALID_PARAMS);
/*  78 */         return GsonUtils.gson().toJson(respLoc);
/*     */       } 
/*     */       
/*  81 */       if (ProductType.MATRIX.equals(user.getProductType()) || ProductType.HWOTP
/*  82 */         .equals(user.getProductType()) || ProductType.NONE
/*  83 */         .equals(user.getProductType())) {
/*  84 */         respLoc.setReturnCode(ReturnCodes.NO_AUTH_METHOD);
/*  85 */         return GsonUtils.gson().toJson(respLoc);
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData(reqLoc.getMacAddress(), reqLoc.getIp());
/*     */     
/*  91 */     LogOperationTypes.getRegOperationType(reqLoc.getAuthLogType()).addServiceLog(AuthMethodTypes.getAuthMethodTypes(authMethodTypeCode), user
/*  92 */         .getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, reqLoc.getDeviceId(), reqLoc.getDeviceType(), null, null, null, null, (ICustomLogData)logData);
/*     */     
/*  94 */     respLoc.setReturnCode(ReturnCodes.OK);
/*  95 */     respLoc.setMultiLoginYN(user.getMultiLoginYN());
/*  96 */     respLoc.setProductTypeCode(user.getProductType().getCode());
/*  97 */     respLoc.setStatus(user.getStatus().getCode());
/*  98 */     respLoc.setSessionCode(reqLoc.getSessionCode());
/*  99 */     respLoc.setUserName(reqLoc.getUsername());
/*     */     
/* 101 */     respPayload = GsonUtils.gson().toJson(respLoc);
/*     */     
/* 103 */     LOG.info(genSendRespLogMsg(respPayload, startMillis, httpRequest));
/*     */     
/* 105 */     return respPayload;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/MATRIX"})
/*     */   public String matrixLog(@RequestBody String payload, HttpServletRequest httpRequest) {
/* 111 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */     
/* 113 */     AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*     */     
/* 115 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 117 */     if (reqLoc.getProductType() != ProductType.MATRIX.getCode()) {
/* 118 */       throw new ReturnCodeException(ReturnCodes.INPUT_PARAMETER_ERROR, "");
/*     */     }
/*     */     
/* 121 */     if (reqLoc.getAuthLogType() == LogOperationTypes.REG.getCode()) {
/*     */       
/* 123 */       respLoc = register(reqLoc, respLoc);
/* 124 */     } else if (reqLoc.getAuthLogType() == LogOperationTypes.AUTH.getCode()) {
/* 125 */       respLoc = authenticator(reqLoc, respLoc);
/*     */     } else {
/* 127 */       respLoc = deregister(reqLoc, respLoc);
/*     */     } 
/*     */     
/* 130 */     String respPayload = GsonUtils.gson().toJson(respLoc);
/* 131 */     return respPayload;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AuthenticationResponseLocator register(AuthenticationRequestLocator reqLoc, AuthenticationResponseLocator respLoc) {
/* 141 */     UserVO user = getUserServiceDao().returnUser(reqLoc.getUsername());
/*     */ 
/*     */     
/* 144 */     if (user == null) {
/*     */ 
/*     */       
/* 147 */       respLoc.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/* 148 */       return respLoc;
/*     */     } 
/*     */ 
/*     */     
/* 152 */     if (user.getProductType() == ProductType.BIOTP || user.getProductType() == ProductType.HWOTP) {
/*     */       
/* 154 */       respLoc.setReturnCode(ReturnCodes.EXIST_ALREADY);
/* 155 */       return respLoc;
/*     */     } 
/*     */     
/* 158 */     if (user.getStatus() == UserStatus.AVAILABLE) {
/*     */       
/* 160 */       respLoc.setReturnCode(ReturnCodes.EXIST_ALREADY);
/* 161 */       return respLoc;
/*     */     } 
/*     */     
/* 164 */     user.setStatus(UserStatus.AVAILABLE);
/* 165 */     user.setProductType(ProductType.MATRIX);
/* 166 */     getUserServiceDao().updateUserAuthType(user);
/*     */     
/* 168 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData(reqLoc.getMacAddress(), reqLoc.getIp());
/* 169 */     LogOperationTypes.REG.addServiceLog(AuthMethodTypes.MATRIX, user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null, null, null, null, (ICustomLogData)logData);
/*     */     
/* 171 */     respLoc.setReturnCode(ReturnCodes.OK);
/*     */     
/* 173 */     return respLoc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AuthenticationResponseLocator authenticator(AuthenticationRequestLocator reqLoc, AuthenticationResponseLocator respLoc) {
/* 182 */     UserVO user = getUserServiceDao().returnUser(reqLoc.getUsername());
/* 183 */     if (user == null) {
/*     */ 
/*     */       
/* 186 */       respLoc.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/* 187 */       return respLoc;
/*     */     } 
/*     */     
/* 190 */     if (user.getStatus() != UserStatus.AVAILABLE) {
/*     */ 
/*     */       
/* 193 */       respLoc.setReturnCode(ReturnCodes.USER_FORBIDDEN);
/* 194 */       return respLoc;
/*     */     } 
/*     */     
/* 197 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData(reqLoc.getMacAddress(), reqLoc.getIp());
/* 198 */     LogOperationTypes.AUTH.addServiceLog(AuthMethodTypes.MATRIX, user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null, null, null, null, (ICustomLogData)logData);
/*     */     
/* 200 */     respLoc.setReturnCode(ReturnCodes.OK);
/* 201 */     return respLoc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AuthenticationResponseLocator deregister(AuthenticationRequestLocator reqLoc, AuthenticationResponseLocator respLoc) {
/* 210 */     UserVO user = getUserServiceDao().returnUser(reqLoc.getUsername());
/* 211 */     if (user == null) {
/*     */       
/* 213 */       respLoc.setReturnCode(ReturnCodes.USER_UNAUTHORIED);
/* 214 */       return respLoc;
/*     */     } 
/*     */     
/* 217 */     user.setStatus(UserStatus.DISCARD);
/* 218 */     getUserServiceDao().updateUserAuthType(user);
/*     */     
/* 220 */     MiraeAssetVietnamLogData logData = new MiraeAssetVietnamLogData(reqLoc.getMacAddress(), reqLoc.getIp());
/* 221 */     LogOperationTypes.DEREG.addServiceLog(AuthMethodTypes.MATRIX, user.getUsername(), OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, null, null, null, null, (ICustomLogData)logData);
/*     */     
/* 223 */     respLoc.setReturnCode(ReturnCodes.OK);
/*     */     
/* 225 */     return respLoc;
/*     */   }
/*     */   
/*     */   private int getAuthMethodTypes(int productType) {
/* 229 */     int authMethodTypeCode = 0;
/* 230 */     if (productType == ProductType.BIOTP.getCode()) {
/* 231 */       return AuthMethodTypes.BIOTP.getCode();
/*     */     }
/* 233 */     if (productType == ProductType.MATRIX.getCode()) {
/* 234 */       return AuthMethodTypes.MATRIX.getCode();
/*     */     }
/* 236 */     if (productType == ProductType.MIRAEASSET.getCode()) {
/* 237 */       return AuthMethodTypes.MIRAEASSET.getCode();
/*     */     }
/* 239 */     if (productType == ProductType.HWOTP.getCode()) {
/* 240 */       return AuthMethodTypes.HWOTP.getCode();
/*     */     }
/*     */     
/* 243 */     return authMethodTypeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   private UserServiceDao getUserServiceDao() {
/* 248 */     if (this.biotpUserServiceDao == null) {
/* 249 */       this.biotpUserServiceDao = new BiotpUserServiceDao();
/*     */     }
/* 251 */     return (UserServiceDao)this.biotpUserServiceDao;
/*     */   }
/*     */   
/*     */   protected AuthMethodTypes getAuthMethodType() {
/* 255 */     return AuthMethodTypes.BIOTP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IUAFRequestService getUAFRequestService() {
/* 260 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IUAFResponseService getUAFResponseService() {
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IVerifyOTPService getVerifyOTPService() {
/* 270 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IGeneralService getGeneralService() {
/* 275 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IssueCodeService getIssueCodeService() {
/* 280 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\controller\ExternalAuthLogController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */