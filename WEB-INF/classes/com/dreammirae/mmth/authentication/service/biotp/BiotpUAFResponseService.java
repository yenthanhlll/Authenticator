/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.biotp;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.ExtensionDataLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.OtpAdditionalData;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.service.OTPDataService;
/*     */ import com.dreammirae.mmth.authentication.service.UAFResponseService;
/*     */ import com.dreammirae.mmth.authentication.service.supporter.FidoResponseMessageValidator;
/*     */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.StatusCodes;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.fido.handler.bean.IServerDataLocator;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.RespMessageCallback;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.RespMessageSupporter;
/*     */ import com.dreammirae.mmth.fido.transport.SendUAFResponse;
/*     */ import com.dreammirae.mmth.fido.transport.ServerResponse;
/*     */ import com.dreammirae.mmth.fido.transport.UAFMessage;
/*     */ import com.dreammirae.mmth.fido.transport.additional.AdditionalData;
/*     */ import com.dreammirae.mmth.fido.transport.context.RpContext;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.util.notary.RSASSA_PSS_SHA256;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.dreammirae.mmth.vo.types.MethodStatus;
/*     */ import org.bouncycastle.crypto.CryptoException;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service("biotpUAFResponseService")
/*     */ public class BiotpUAFResponseService
/*     */   extends UAFResponseService
/*     */ {
/*  53 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.biotp.BiotpUAFResponseService.class);
/*     */   
/*     */   @Autowired
/*     */   private BiotpUserServiceDao biotpUserServiceDao;
/*     */   
/*     */   @Autowired
/*     */   private OTPDataService otpDataService;
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthMethodTypes() {
/*  63 */     return AuthMethodTypes.BIOTP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doProcResponseImp(Operation op, ServerResponse serverResponse, SendUAFResponse sendUAFResponse) {
/*     */     try {
/*  72 */       validateContext(op, sendUAFResponse.getContext());
/*     */       
/*  74 */       BiotpRespMessageCallback callback = new BiotpRespMessageCallback(this, serverResponse, sendUAFResponse);
/*     */       
/*  76 */       (new FidoResponseMessageValidator()).validateResponseMessage(op, (RespMessageSupporter)new BiotpRespMessageValidateSupporter(this), (RespMessageCallback)callback, sendUAFResponse);
/*     */       
/*  78 */       serverResponse.setStatusCode(StatusCodes.CODE_1200);
/*     */     
/*     */     }
/*  81 */     catch (FidoUafStatusCodeException e) {
/*  82 */       serverResponse.setStatusCode(e.getStatusCode());
/*  83 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/*  84 */     } catch (ReturnCodeException e) {
/*  85 */       serverResponse.setStatusCode(e.getReturnCode().getStatusCodes());
/*  86 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void appendCustomDataToRpContext(ServerResponse result, SendUAFResponse sendUAFResponse, UserServiceLocator serviceLocator) {
/*  91 */     RpContext ctx = new RpContext();
/*  92 */     UserDeviceVO userDevice = serviceLocator.getUserDevice();
/*     */     
/*  94 */     if (userDevice != null) {
/*  95 */       ctx.set("deviceId", userDevice.getDeviceId());
/*  96 */       if (userDevice.getDeviceType() != null)
/*  97 */         ctx.set("deviceType", userDevice.getDeviceType().getCode()); 
/*  98 */       ctx.set("model", userDevice.getModel());
/*  99 */       ctx.set("alias", userDevice.getAlias());
/*     */     } 
/*     */     
/* 102 */     if (serviceLocator.getAppAgent() != null) {
/* 103 */       ctx.set("pkgName", serviceLocator.getAppAgent().getPkgUnique());
/*     */     }
/*     */     
/* 106 */     if (serviceLocator.getTokenRegi() != null) {
/* 107 */       ctx.set("tokenId", serviceLocator.getTokenRegi().getTokenId());
/*     */     }
/*     */     
/* 110 */     sendUAFResponse.setContext(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void procRegistrationResponse(ServerResponse serverResponse, SendUAFResponse sendUAFResponse, FidoRegistrationVO newFidoRegi, IServerDataLocator serverData, ExtensionDataLocator extLoc) {
/* 116 */     RpContext rpContext = sendUAFResponse.getContext();
/* 117 */     String username = serverData.getUsername();
/* 118 */     String deviceId = null;
/* 119 */     DeviceTypes deviceType = null;
/* 120 */     String pkgName = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 125 */       if (extLoc.getOtpPublicKey() == null) {
/* 126 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "OTP Public key is null value. please check 'ext' fields.");
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 131 */         RSASSA_PSS_SHA256.getRawPublicKey(extLoc.getOtpPublicKey());
/* 132 */       } catch (CryptoException e) {
/* 133 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "OTP Public key is invaild. otpPubkey=" + HexUtils.toHexString(extLoc.getOtpPublicKey()) + ". cause=" + e.getMessage(), e);
/*     */       } 
/*     */       
/* 136 */       ServerChallengeVO challenge = returnServerChallenge(serverData);
/*     */       
/* 138 */       deviceId = rpContext.getDeviceId();
/* 139 */       deviceType = DeviceTypes.getDeviceType(rpContext.getDeviceType());
/* 140 */       pkgName = rpContext.getPkgName();
/*     */       
/* 142 */       UserServiceLocator serviceLocator = getPreRegistrations(getAuthMethodTypes(), username, deviceId, deviceType, pkgName);
/* 143 */       serviceLocator.setChallenge(challenge);
/* 144 */       serviceLocator.setFidoRegi(newFidoRegi);
/*     */ 
/*     */       
/* 147 */       if (extLoc.getRandomSeedKey() != null) {
/* 148 */         byte[] rndSeedKey = OTPDataService.toDecRndSeedKeyWithPubKey(extLoc.getRandomSeedKey(), extLoc.getOtpPublicKey());
/* 149 */         extLoc.setRandomSeedKey(rndSeedKey);
/*     */       } 
/*     */       
/* 152 */       serviceLocator.setExtLoc(extLoc);
/*     */       
/* 154 */       serviceLocator.getUser().setmOtpIssueStatus(MethodStatus.AVAILABLE);
/* 155 */       serviceLocator.getUser().setTsMOtpStatusUpdated(System.currentTimeMillis());
/*     */       
/* 157 */       getUserServiceDao().saveRegResponse(serviceLocator);
/*     */       
/* 159 */       OtpAdditionalData otpData = new OtpAdditionalData();
/*     */       
/* 161 */       getOtpDataService().makeNewOtpDataAdd(otpData, Operation.Reg, serviceLocator.getTokenRegi(), challenge);
/*     */       
/* 163 */       UAFMessage message = new UAFMessage();
/* 164 */       message.setAdditionalData((AdditionalData)otpData);
/* 165 */       serverResponse.setNewUAFRequest(message);
/*     */       
/* 167 */       LogOperationTypes.REG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, deviceType, pkgName, newFidoRegi.getAaid(), serviceLocator.getTokenRegi().getTokenId(), null);
/*     */       
/* 169 */       appendCustomDataToRpContext(serverResponse, sendUAFResponse, serviceLocator);
/*     */       
/* 171 */       LOG.info("== Success to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Reg.name());
/*     */     }
/* 173 */     catch (FidoUafStatusCodeException e) {
/* 174 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Reg.name(), e.getMessage());
/* 175 */       LogOperationTypes.REG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, ReturnCodes.getReturnCode(e.getStatusCode()), deviceId, deviceType, pkgName, newFidoRegi.getAaid(), null, e.getMessage());
/* 176 */       throw e;
/* 177 */     } catch (ReturnCodeException e) {
/* 178 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Reg.name(), e.getMessage());
/* 179 */       LogOperationTypes.REG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, e.getReturnCode(), deviceId, deviceType, pkgName, newFidoRegi.getAaid(), null, e.getMessage());
/* 180 */       throw e;
/* 181 */     } catch (Exception e) {
/* 182 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Reg.name(), e.getMessage());
/* 183 */       LogOperationTypes.REG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, deviceId, deviceType, pkgName, newFidoRegi.getAaid(), null, e.getMessage());
/* 184 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void procAuthenticationResponse(ServerResponse serverResponse, FidoRegistrationVO fidoRegi, IServerDataLocator serverData, String tranHash, ExtensionDataLocator extLoc) {
/* 190 */     String username = serverData.getUsername();
/* 191 */     DeviceAppAgentVO deviceAppAgent = getUserServiceDao().returnDeviceAppAgent(fidoRegi);
/* 192 */     UserDeviceVO userDevice = getUserServiceDao().returnUserDevice(deviceAppAgent);
/*     */     
/*     */     try {
/* 195 */       ServerChallengeVO challenge = returnServerChallenge(serverData);
/*     */       
/* 197 */       if (!StringUtils.isEmpty(tranHash)) {
/*     */         
/* 199 */         boolean updated = getBiotpUserServiceDao().checkIfHasFidoTC(fidoRegi, tranHash);
/*     */         
/* 201 */         if (!updated) {
/* 202 */           throw new FidoUafStatusCodeException(StatusCodes.CODE_1491, "TC infomation is invalid.");
/*     */         }
/*     */       } 
/*     */       
/* 206 */       TokenRegistrationVO tokenRegi = getTokenRegistration(deviceAppAgent);
/*     */       
/* 208 */       if (tokenRegi.getLost().toBoolean()) {
/* 209 */         throw new FidoUafStatusCodeException(StatusCodes.CODE_1016, "The token status is lost.");
/*     */       }
/*     */       
/* 212 */       UserServiceLocator serviceLocator = new UserServiceLocator(AuthMethodTypes.BIOTP);
/* 213 */       serviceLocator.setDeviceAppAgent(deviceAppAgent);
/* 214 */       serviceLocator.setFidoRegi(fidoRegi);
/* 215 */       challenge.setTsDone(System.currentTimeMillis());
/* 216 */       serviceLocator.setChallenge(challenge);
/*     */       
/* 218 */       if (extLoc != null && 
/* 219 */         extLoc.getRandomChallenge() != null) {
/* 220 */         tokenRegi.setRndChallenge(extLoc.getRandomChallenge());
/*     */       }
/*     */ 
/*     */       
/* 224 */       getUserServiceDao().saveAuthResponse(serviceLocator);
/*     */       
/* 226 */       OtpAdditionalData otpData = new OtpAdditionalData();
/* 227 */       getOtpDataService().makeNewOtpDataAdd(otpData, Operation.Auth, tokenRegi, challenge);
/* 228 */       UAFMessage uafMessage = new UAFMessage();
/* 229 */       uafMessage.setAdditionalData((AdditionalData)otpData);
/* 230 */       serverResponse.setNewUAFRequest(uafMessage);
/*     */       
/* 232 */       LOG.info("== Success to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Auth.name());
/* 233 */       LogOperationTypes.AUTH.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.SUCCESS, ReturnCodes.OK, userDevice.getDeviceId(), userDevice.getDeviceType(), null, fidoRegi.getAaid(), tokenRegi.getTokenId(), null, null);
/*     */     }
/* 235 */     catch (FidoUafStatusCodeException e) {
/* 236 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Auth.name(), e.getMessage());
/* 237 */       LogOperationTypes.AUTH.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, ReturnCodes.getReturnCode(e.getStatusCode()), userDevice.getDeviceId(), userDevice.getDeviceType(), fidoRegi.getAaid(), null, null, e.getMessage());
/* 238 */       throw e;
/* 239 */     } catch (ReturnCodeException e) {
/* 240 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Auth.name(), e.getMessage());
/* 241 */       LogOperationTypes.AUTH.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, e.getReturnCode(), userDevice.getDeviceId(), userDevice.getDeviceType(), fidoRegi.getAaid(), null, null, e.getMessage());
/* 242 */       throw e;
/* 243 */     } catch (Exception e) {
/* 244 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Auth.name(), e.getMessage());
/* 245 */       LogOperationTypes.AUTH.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, null, null, null, fidoRegi.getAaid(), null, e.getMessage());
/* 246 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateContext(Operation op, RpContext ctx) {
/* 253 */     if (!Operation.Reg.equals(op)) {
/*     */       return;
/*     */     }
/*     */     
/* 257 */     if (ctx == null) {
/* 258 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'context' field is required when op='Reg'.");
/*     */     }
/*     */     
/* 261 */     if (!ctx.containsKey("deviceId")) {
/* 262 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'context:[deviceId]' field is required when op='Reg'.");
/*     */     }
/*     */     
/* 265 */     if (!ctx.containsKey("deviceType")) {
/* 266 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'context:[deviceType]' field is required when op='Reg'.");
/*     */     }
/*     */     
/* 269 */     if (!ctx.containsKey("pkgName")) {
/* 270 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'context:[pkgName]' field is required when op='Reg'.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected UserServiceDao getUserServiceDao() {
/* 277 */     return (UserServiceDao)getBiotpUserServiceDao();
/*     */   }
/*     */   
/*     */   public BiotpUserServiceDao getBiotpUserServiceDao() {
/* 281 */     if (this.biotpUserServiceDao == null) {
/* 282 */       this.biotpUserServiceDao = new BiotpUserServiceDao();
/*     */     }
/* 284 */     return this.biotpUserServiceDao;
/*     */   }
/*     */ 
/*     */   
/*     */   public OTPDataService getOtpDataService() {
/* 289 */     if (this.otpDataService == null) {
/* 290 */       this.otpDataService = new OTPDataService();
/*     */     }
/*     */     
/* 293 */     return this.otpDataService;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\biotp\BiotpUAFResponseService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */