/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.fido;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.service.IExteranlReqInterworker;
/*     */ import com.dreammirae.mmth.authentication.service.UAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.fido.FidoReqInterworker;
/*     */ import com.dreammirae.mmth.authentication.service.supporter.FidoRequestMessageGenerator;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.FidoUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.IUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.StatusCodes;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.ReqMessageCallback;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.ReqMessageSupporter;
/*     */ import com.dreammirae.mmth.fido.transport.GetUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.ReturnUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.UAFMessage;
/*     */ import com.dreammirae.mmth.fido.transport.context.RpContext;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
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
/*     */ 
/*     */ @Service("fidoAuthenticationService")
/*     */ public class FidoUAFRequestService
/*     */   extends UAFRequestService
/*     */ {
/*  45 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.fido.FidoUAFRequestService.class);
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private FidoUserServiceDao registerDao;
/*     */ 
/*     */   
/*     */   protected void doProcUAFRequestImp(ReturnUAFRequest result, GetUAFRequest getUafRequest, UserServiceLocator serviceLocator) {
/*  53 */     RpContext rpCtx = getUafRequest.getContext();
/*  54 */     String username = rpCtx.getUsername();
/*  55 */     String deviceId = rpCtx.getDeviceId();
/*  56 */     DeviceTypes deviceType = DeviceTypes.getDeviceType(rpCtx.getDeviceType());
/*  57 */     String pkgName = rpCtx.getPkgName();
/*     */ 
/*     */     
/*     */     try {
/*  61 */       if (Operation.Auth.equals(getUafRequest.getOp()) && 
/*  62 */         enabledExternalApi()) {
/*  63 */         getExteranlReqInterworker().reqAuthInterworker(serviceLocator.getUser(), getUafRequest.getContext());
/*     */       }
/*     */ 
/*     */       
/*  67 */       FidoReqMessageCallback callback = new FidoReqMessageCallback(this, result, getUafRequest, serviceLocator);
/*  68 */       FidoRequestMessageSupporter supporter = new FidoRequestMessageSupporter(this, serviceLocator);
/*  69 */       UAFMessage message = (new FidoRequestMessageGenerator()).generateRequestMessage((ReqMessageSupporter)supporter, (ReqMessageCallback)callback, getUafRequest, serviceLocator);
/*  70 */       result.setOp(getUafRequest.getOp());
/*  71 */       result.setUafRequest(message);
/*  72 */       result.setStatusCode(StatusCodes.CODE_1200);
/*  73 */       result.setLifetimeMillis(Long.valueOf(SystemSettingsDao.getPeriods("integrate.authLifetimePeriodType", "integrate.authLifetimePeriods")));
/*     */       
/*  75 */       if (Operation.Dereg.equals(getUafRequest.getOp())) {
/*  76 */         if (serviceLocator.getFidoRegistrations() != null && !serviceLocator.getFidoRegistrations().isEmpty()) {
/*  77 */           for (FidoRegistrationVO vo : serviceLocator.getFidoRegistrations()) {
/*  78 */             LogOperationTypes.DEREG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_REQ, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, deviceType, pkgName, vo.getAaid(), null, null);
/*     */           }
/*     */         }
/*     */       } else {
/*  82 */         getLogOperationTypes(getUafRequest.getOp()).addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_REQ, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, deviceType, pkgName, null, null, null);
/*     */       }
/*     */     
/*  85 */     } catch (FidoUafStatusCodeException e) {
/*  86 */       result.setStatusCode(e.getStatusCode());
/*  87 */       getLogOperationTypes(getUafRequest.getOp()).addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_REQ, LogActionTypes.FAIL, ReturnCodes.getReturnCode(e.getStatusCode()), deviceId, deviceType, pkgName, null, null, e.getMessage());
/*  88 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/*  89 */     } catch (ReturnCodeException e) {
/*  90 */       result.setStatusCode(e.getReturnCode().getStatusCodes());
/*  91 */       getLogOperationTypes(getUafRequest.getOp()).addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_REQ, LogActionTypes.FAIL, e.getReturnCode(), deviceId, deviceType, pkgName, null, null, e.getMessage());
/*  92 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthMethodTypes() {
/* 100 */     return AuthMethodTypes.FIDO;
/*     */   }
/*     */ 
/*     */   
/*     */   private FidoUserServiceDao getFidoUserServiceRegisterDao() {
/* 105 */     if (this.registerDao == null) {
/* 106 */       this.registerDao = new FidoUserServiceDao();
/*     */     }
/*     */     
/* 109 */     return this.registerDao;
/*     */   }
/*     */ 
/*     */   
/*     */   protected UserServiceDao getUserServiceDao() {
/* 114 */     if (this.registerDao == null) {
/* 115 */       this.registerDao = new FidoUserServiceDao();
/*     */     }
/*     */     
/* 118 */     return (UserServiceDao)this.registerDao;
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
/*     */   private LogOperationTypes getLogOperationTypes(Operation op) {
/* 199 */     if (Operation.Reg.equals(op))
/* 200 */       return LogOperationTypes.REG; 
/* 201 */     if (Operation.Auth.equals(op)) {
/* 202 */       return LogOperationTypes.FIDO_AUTH;
/*     */     }
/* 204 */     return LogOperationTypes.DEREG;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enabledExternalApi() {
/* 210 */     return ExternalWebApiTypes.GPTWR.equals(SystemSettingsDao.getWebApiPolicy());
/*     */   }
/*     */   
/*     */   public IExteranlReqInterworker getExteranlReqInterworker() {
/* 214 */     return (IExteranlReqInterworker)new FidoReqInterworker();
/*     */   }
/*     */   
/*     */   protected void validateUserServiceDataImp(UserServiceLocator serviceLocator, Operation op, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {}
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\fido\FidoUAFRequestService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */