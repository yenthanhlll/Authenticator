/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.fido;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.FidoJWTBody;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.service.IExteranlRespInterworker;
/*     */ import com.dreammirae.mmth.authentication.service.UAFResponseService;
/*     */ import com.dreammirae.mmth.authentication.service.fido.FidoRespInterworker;
/*     */ import com.dreammirae.mmth.authentication.service.supporter.FidoResponseMessageValidator;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.FidoUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.StatusCodes;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.fido.handler.bean.IServerDataLocator;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.RespMessageCallback;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.RespMessageSupporter;
/*     */ import com.dreammirae.mmth.fido.transport.SendUAFResponse;
/*     */ import com.dreammirae.mmth.fido.transport.ServerResponse;
/*     */ import com.dreammirae.mmth.fido.transport.Token;
/*     */ import com.dreammirae.mmth.fido.transport.TokenType;
/*     */ import com.dreammirae.mmth.fido.transport.context.RpContext;
/*     */ import com.dreammirae.mmth.fido.transport.jwt.JWTBody;
/*     */ import com.dreammirae.mmth.fido.transport.jwt.JWTCreator;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.types.ChallengeStatus;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
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
/*     */ @Service("fidoUAFResponseService")
/*     */ public class FidoUAFResponseService
/*     */   extends UAFResponseService
/*     */ {
/*  52 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.fido.FidoUAFResponseService.class);
/*     */   
/*     */   @Autowired
/*     */   private FidoUserServiceDao userServiceDao;
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthMethodTypes() {
/*  59 */     return AuthMethodTypes.FIDO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doProcResponseImp(Operation op, ServerResponse serverResponse, SendUAFResponse sendUAFResponse) {
/*     */     try {
/*  67 */       validateContext(op, sendUAFResponse.getContext());
/*     */       
/*  69 */       FidoRespMessageCallback callback = new FidoRespMessageCallback(this, serverResponse, sendUAFResponse);
/*     */       
/*  71 */       (new FidoResponseMessageValidator()).validateResponseMessage(op, (RespMessageSupporter)new FidoRespMessageValidateSupporter(this), (RespMessageCallback)callback, sendUAFResponse);
/*     */ 
/*     */       
/*  74 */       serverResponse.setStatusCode(StatusCodes.CODE_1200);
/*  75 */     } catch (FidoUafStatusCodeException e) {
/*  76 */       serverResponse.setStatusCode(e.getStatusCode());
/*  77 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/*  78 */     } catch (ReturnCodeException e) {
/*  79 */       serverResponse.setStatusCode(e.getReturnCode().getStatusCodes());
/*  80 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void procRegistrationResponse(SendUAFResponse sendUAFResponse, FidoRegistrationVO newFidoRegi, IServerDataLocator serverData) {
/*  87 */     RpContext rpContext = sendUAFResponse.getContext();
/*  88 */     String username = serverData.getUsername();
/*  89 */     String deviceId = null;
/*  90 */     DeviceTypes deviceType = null;
/*  91 */     String pkgName = null;
/*     */     
/*     */     try {
/*  94 */       ServerChallengeVO challenge = returnServerChallenge(serverData);
/*     */       
/*  96 */       deviceId = rpContext.getDeviceId();
/*  97 */       deviceType = DeviceTypes.getDeviceType(rpContext.getDeviceType());
/*  98 */       pkgName = rpContext.getPkgName();
/*     */       
/* 100 */       UserServiceLocator serviceLocator = getPreRegistrations(getAuthMethodTypes(), username, deviceId, deviceType, pkgName);
/*     */       
/* 102 */       serviceLocator.setChallenge(challenge);
/* 103 */       serviceLocator.setFidoRegi(newFidoRegi);
/*     */       
/* 105 */       getUserServiceDao().saveRegResponse(serviceLocator);
/*     */       
/* 107 */       LogOperationTypes.REG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, deviceType, pkgName, newFidoRegi
/* 108 */           .getAaid(), null, null);
/*     */ 
/*     */       
/* 111 */       if (enabledExternalApi()) {
/* 112 */         getExteranlRespInterworker().respRegInterworker(serviceLocator.getUser(), challenge, newFidoRegi);
/*     */       }
/*     */       
/* 115 */       LOG.info("== Success to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Reg.name());
/*     */     }
/* 117 */     catch (FidoUafStatusCodeException e) {
/* 118 */       LogOperationTypes.REG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, 
/* 119 */           ReturnCodes.getReturnCode(e.getStatusCode()), deviceId, deviceType, pkgName, newFidoRegi
/* 120 */           .getAaid(), null, e.getMessage());
/* 121 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Reg.name(), e.getMessage());
/* 122 */       throw e;
/* 123 */     } catch (ReturnCodeException e) {
/* 124 */       LogOperationTypes.REG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, e
/* 125 */           .getReturnCode(), deviceId, deviceType, pkgName, newFidoRegi.getAaid(), null, e
/* 126 */           .getMessage());
/* 127 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Reg.name(), e.getMessage());
/* 128 */       throw e;
/* 129 */     } catch (Exception e) {
/* 130 */       LogOperationTypes.REG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, deviceId, deviceType, pkgName, newFidoRegi
/*     */           
/* 132 */           .getAaid(), null, e.getMessage());
/* 133 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Reg.name(), e.getMessage());
/* 134 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void procAuthenticationResponse(ServerResponse serverResponse, FidoRegistrationVO fidoRegi, IServerDataLocator serverData, String tranHash) {
/* 142 */     String username = serverData.getUsername();
/*     */ 
/*     */     
/*     */     try {
/* 146 */       ServerChallengeVO challenge = returnServerChallenge(serverData);
/*     */       
/* 148 */       if (!StringUtils.isEmpty(tranHash)) {
/*     */         
/* 150 */         boolean updated = getFidoUserServiceDao().checkIfHasFidoTC(fidoRegi, tranHash);
/*     */         
/* 152 */         if (!updated) {
/* 153 */           throw new FidoUafStatusCodeException(StatusCodes.CODE_1491, "TC infomation is invalid.");
/*     */         }
/*     */       } 
/*     */       
/* 157 */       DeviceAppAgentVO deviceAppAgent = getUserServiceDao().returnDeviceAppAgent(fidoRegi);
/*     */       
/* 159 */       UserServiceLocator serviceLocator = new UserServiceLocator(AuthMethodTypes.FIDO);
/* 160 */       serviceLocator.setDeviceAppAgent(deviceAppAgent);
/* 161 */       serviceLocator.setFidoRegi(fidoRegi);
/* 162 */       challenge.setStatus(ChallengeStatus.DONE);
/* 163 */       challenge.setTsDone(System.currentTimeMillis());
/* 164 */       serviceLocator.setChallenge(challenge);
/*     */       
/* 166 */       getUserServiceDao().saveAuthResponse(serviceLocator);
/*     */       
/* 168 */       serverResponse.setAdditionalTokens(generateJWToken(challenge));
/*     */       
/* 170 */       LogOperationTypes.FIDO_AUTH.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.SUCCESS, ReturnCodes.OK, fidoRegi
/* 171 */           .getAaid(), null, deviceAppAgent, null);
/*     */       
/* 173 */       if (enabledExternalApi()) {
/* 174 */         getExteranlRespInterworker().respAuthInterworker(getUserServiceDao().returnUser(username), challenge, fidoRegi);
/*     */       }
/*     */ 
/*     */       
/* 178 */       LOG.info("== Success to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Auth.name());
/*     */     }
/* 180 */     catch (FidoUafStatusCodeException e) {
/*     */       
/* 182 */       LogOperationTypes.FIDO_AUTH.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, 
/* 183 */           ReturnCodes.getReturnCode(e.getStatusCode()), null, null, null, fidoRegi
/* 184 */           .getAaid(), null, e.getMessage());
/*     */       
/* 186 */       if (enabledExternalApi()) {
/* 187 */         getExteranlRespInterworker().respAuthFailInterworker(getUserServiceDao().returnUser(username), null, fidoRegi);
/*     */       }
/*     */       
/* 190 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Auth.name(), e.getMessage());
/* 191 */       throw e;
/* 192 */     } catch (ReturnCodeException e) {
/* 193 */       LogOperationTypes.FIDO_AUTH.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, e
/* 194 */           .getReturnCode(), null, null, null, fidoRegi.getAaid(), null, e.getMessage());
/*     */       
/* 196 */       if (enabledExternalApi()) {
/* 197 */         getExteranlRespInterworker().respAuthFailInterworker(getUserServiceDao().returnUser(username), null, fidoRegi);
/*     */       }
/*     */       
/* 200 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Auth.name(), e.getMessage());
/* 201 */       throw e;
/* 202 */     } catch (Exception e) {
/*     */       
/* 204 */       LogOperationTypes.FIDO_AUTH.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_RESP, LogActionTypes.FAIL, ReturnCodes.INTERNAL_SERVER_ERROR, null, null, null, fidoRegi
/* 205 */           .getAaid(), null, e
/* 206 */           .getMessage());
/*     */       
/* 208 */       if (enabledExternalApi()) {
/* 209 */         getExteranlRespInterworker().respAuthFailInterworker(getUserServiceDao().returnUser(username), null, fidoRegi);
/*     */       }
/*     */       
/* 212 */       LOG.error("== Failed to verifying UAFRespnse.. username=" + username + ", op=" + Operation.Auth.name(), e.getMessage());
/* 213 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateContext(Operation op, RpContext ctx) {
/* 220 */     if (!Operation.Reg.equals(op)) {
/*     */       return;
/*     */     }
/*     */     
/* 224 */     if (ctx == null) {
/* 225 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'context' field is required when op='Reg'.");
/*     */     }
/*     */     
/* 228 */     if (!ctx.containsKey("deviceId")) {
/* 229 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'context:[deviceId]' field is required when op='Reg'.");
/*     */     }
/*     */ 
/*     */     
/* 233 */     if (!ctx.containsKey("deviceType")) {
/* 234 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'context:[deviceType]' field is required when op='Reg'.");
/*     */     }
/*     */ 
/*     */     
/* 238 */     if (!ctx.containsKey("pkgName")) {
/* 239 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The 'context:[pkgName]' field is required when op='Reg'.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UserServiceDao getUserServiceDao() {
/* 248 */     if (this.userServiceDao == null) {
/* 249 */       this.userServiceDao = new FidoUserServiceDao();
/*     */     }
/* 251 */     return (UserServiceDao)this.userServiceDao;
/*     */   }
/*     */ 
/*     */   
/*     */   protected FidoUserServiceDao getFidoUserServiceDao() {
/* 256 */     if (this.userServiceDao == null) {
/* 257 */       this.userServiceDao = new FidoUserServiceDao();
/*     */     }
/* 259 */     return this.userServiceDao;
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
/*     */   public boolean enabledExternalApi() {
/* 323 */     return ExternalWebApiTypes.GPTWR.equals(SystemSettingsDao.getWebApiPolicy());
/*     */   }
/*     */   
/*     */   public IExteranlRespInterworker getExteranlRespInterworker() {
/* 327 */     return (IExteranlRespInterworker)new FidoRespInterworker();
/*     */   }
/*     */ 
/*     */   
/*     */   private Token[] generateJWToken(ServerChallengeVO vo) {
/* 332 */     FidoJWTBody body = new FidoJWTBody(vo.getTransactionId());
/*     */     
/* 334 */     Token token = new Token();
/* 335 */     token.setType(TokenType.JWT);
/* 336 */     token.setValue(JWTCreator.getJWT((JWTBody)body));
/*     */     
/* 338 */     return new Token[] { token };
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\fido\FidoUAFResponseService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */