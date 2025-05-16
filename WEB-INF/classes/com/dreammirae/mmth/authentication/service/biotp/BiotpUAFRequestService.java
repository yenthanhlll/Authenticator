/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.biotp;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.service.UAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.supporter.FidoRequestMessageGenerator;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.IUserServiceDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
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
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
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
/*     */ 
/*     */ @Service("biotpUAFRequestService")
/*     */ public class BiotpUAFRequestService
/*     */   extends UAFRequestService
/*     */ {
/*  45 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.biotp.BiotpUAFRequestService.class);
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private BiotpUserServiceDao biotpUserServiceDao;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doProcUAFRequestImp(ReturnUAFRequest result, GetUAFRequest getUafRequest, UserServiceLocator serviceLocator) {
/*  54 */     RpContext rpCtx = getUafRequest.getContext();
/*  55 */     String username = rpCtx.getUsername();
/*  56 */     String deviceId = rpCtx.getDeviceId();
/*  57 */     DeviceTypes deviceType = DeviceTypes.getDeviceType(rpCtx.getDeviceType());
/*  58 */     String pkgName = rpCtx.getPkgName();
/*     */ 
/*     */     
/*     */     try {
/*  62 */       BiotpReqMessageCallback callback = new BiotpReqMessageCallback(this, result, getUafRequest, serviceLocator);
/*  63 */       BiotpRequestMessageSupporter supporter = new BiotpRequestMessageSupporter(this, serviceLocator);
/*  64 */       UAFMessage message = (new FidoRequestMessageGenerator()).generateRequestMessage((ReqMessageSupporter)supporter, (ReqMessageCallback)callback, getUafRequest, serviceLocator);
/*  65 */       result.setOp(getUafRequest.getOp());
/*  66 */       result.setUafRequest(message);
/*  67 */       result.setStatusCode(StatusCodes.CODE_1200);
/*  68 */       result.setLifetimeMillis(Long.valueOf(SystemSettingsDao.getPeriods("integrate.authLifetimePeriodType", "integrate.authLifetimePeriods")));
/*     */       
/*  70 */       if (Operation.Dereg.equals(getUafRequest.getOp())) {
/*  71 */         if (serviceLocator.getFidoRegistrations() != null && !serviceLocator.getFidoRegistrations().isEmpty()) {
/*  72 */           for (FidoRegistrationVO vo : serviceLocator.getFidoRegistrations()) {
/*  73 */             String tokenId = null;
/*  74 */             if (serviceLocator.getTokenRegi() != null) {
/*  75 */               tokenId = serviceLocator.getTokenRegi().getTokenId();
/*     */             }
/*     */             
/*  78 */             if (getUafRequest.getContext().containsKey("isLocalFailedRequest")) {
/*  79 */               LogOperationTypes.FORCE_DEREG_FOR_LOCAL_FAILED.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.HTTP_API, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, deviceType, pkgName, vo.getAaid(), tokenId, null); continue;
/*     */             } 
/*  81 */             LogOperationTypes.DEREG.addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_REQ, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, deviceType, pkgName, vo.getAaid(), tokenId, null);
/*     */           } 
/*     */         }
/*     */         
/*  85 */         appendCustomDataToRpContext(result, getUafRequest, serviceLocator);
/*     */       } else {
/*  87 */         getLogOperationTypes(getUafRequest.getOp()).addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_REQ, LogActionTypes.SUCCESS, ReturnCodes.OK, deviceId, deviceType, pkgName, null, null, null);
/*     */       }
/*     */     
/*  90 */     } catch (FidoUafStatusCodeException e) {
/*  91 */       result.setStatusCode(e.getStatusCode());
/*  92 */       getLogOperationTypes(getUafRequest.getOp()).addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_REQ, LogActionTypes.FAIL, ReturnCodes.getReturnCode(e.getStatusCode()), deviceId, deviceType, pkgName, null, null, e.getMessage());
/*  93 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/*  94 */     } catch (ReturnCodeException e) {
/*  95 */       result.setStatusCode(e.getReturnCode().getStatusCodes());
/*  96 */       getLogOperationTypes(getUafRequest.getOp()).addServiceLog(getAuthMethodTypes(), username, OpRequstTypes.FIDO_UAF_REQ, LogActionTypes.FAIL, e.getReturnCode(), deviceId, deviceType, pkgName, null, null, e.getMessage());
/*  97 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage(), (Throwable)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void appendCustomDataToRpContext(ReturnUAFRequest result, GetUAFRequest getUafRequest, UserServiceLocator serviceLocator) {
/* 103 */     RpContext ctx = new RpContext();
/* 104 */     UserDeviceVO userDevice = serviceLocator.getUserDevice();
/*     */     
/* 106 */     if (userDevice != null) {
/* 107 */       ctx.set("deviceId", userDevice.getDeviceId());
/* 108 */       if (userDevice.getDeviceType() != null)
/* 109 */         ctx.set("deviceType", userDevice.getDeviceType().getCode()); 
/* 110 */       ctx.set("model", userDevice.getModel());
/* 111 */       ctx.set("alias", userDevice.getAlias());
/*     */     } 
/*     */     
/* 114 */     if (serviceLocator.getAppAgent() != null) {
/* 115 */       ctx.set("pkgName", serviceLocator.getAppAgent().getPkgUnique());
/*     */     }
/*     */     
/* 118 */     if (serviceLocator.getTokenRegi() != null) {
/* 119 */       ctx.set("tokenId", serviceLocator.getTokenRegi().getTokenId());
/*     */     }
/*     */     
/* 122 */     getUafRequest.setContext(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthMethodTypes() {
/* 127 */     return AuthMethodTypes.BIOTP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected UserServiceDao getUserServiceDao() {
/* 133 */     if (this.biotpUserServiceDao == null) {
/* 134 */       this.biotpUserServiceDao = new BiotpUserServiceDao();
/*     */     }
/*     */     
/* 137 */     return (UserServiceDao)this.biotpUserServiceDao;
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
/* 218 */     if (Operation.Reg.equals(op))
/* 219 */       return LogOperationTypes.REG; 
/* 220 */     if (Operation.Auth.equals(op)) {
/* 221 */       return LogOperationTypes.AUTH;
/*     */     }
/* 223 */     return LogOperationTypes.DEREG;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateUserServiceDataImp(UserServiceLocator serviceLocator, Operation op, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/* 230 */     if (Operation.Auth.equals(op)) {
/* 231 */       TokenRegistrationVO tokenRegi = getUserServiceDao().returnTokenRegistration(serviceLocator.getDeviceAppAgent());
/* 232 */       if (tokenRegi.getLost().toBoolean())
/* 233 */         throw new FidoUafStatusCodeException(StatusCodes.CODE_1016, "The token status is lost."); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\biotp\BiotpUAFRequestService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */