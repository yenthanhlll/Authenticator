/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFResponseService;
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.fido.handler.bean.IServerDataLocator;
/*     */ import com.dreammirae.mmth.fido.transport.SendUAFResponse;
/*     */ import com.dreammirae.mmth.fido.transport.ServerResponse;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.ChallengeStatus;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public abstract class UAFResponseService
/*     */   implements IUAFResponseService {
/*  28 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.UAFResponseService.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String LOG_FORMAT_ERROR_MSG = "@@@ ERR MSG IN PROC :: ";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyingUAFResponse(Operation op, ServerResponse result, SendUAFResponse sendUAFResponse) {
/*     */     try {
/*  40 */       doProcResponseImp(op, result, sendUAFResponse);
/*  41 */     } catch (FidoUafStatusCodeException e) {
/*  42 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage());
/*  43 */       result.setStatusCode(e.getStatusCode());
/*  44 */     } catch (ReturnCodeException e) {
/*  45 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage());
/*  46 */       result.setStatusCode(e.getReturnCode().getStatusCodes());
/*  47 */     } catch (Exception e) {
/*  48 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage());
/*  49 */       result.setStatusCode(ReturnCodes.INTERNAL_SERVER_ERROR.getStatusCodes());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UserServiceLocator getPreRegistrations(AuthMethodTypes authType, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/*  58 */     AppAgentVO appAgent = AppAgentDao.getAcceptableAppAgent(deviceType.getOsType(), pkgUnique);
/*     */     
/*  60 */     if (appAgent == null) {
/*  61 */       throw new ReturnCodeException(ReturnCodes.UNKNOWN_APP_AGENT, "The appAgent [pkgName=" + deviceType.getOsType().name() + ":" + pkgUnique + "] had been not acceptable.");
/*     */     }
/*     */     
/*  64 */     UserVO user = getUserServiceDao().returnUser(username);
/*     */     
/*  66 */     if (user == null) {
/*  67 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no user with " + username);
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (user.getDisabled().toBoolean()) {
/*  72 */       throw new ReturnCodeException(ReturnCodes.USER_FORBIDDEN, "The user has been locked with username=" + username);
/*     */     }
/*     */     
/*  75 */     UserDeviceVO userDevice = getUserServiceDao().returnUserDevice(user, deviceId, deviceType);
/*     */     
/*  77 */     if (userDevice == null) {
/*  78 */       throw new ReturnCodeException(ReturnCodes.DEVICE_APP_UNAUTHORIED, "There has no available registraions with username=" + username + " and deviceId=" + deviceId);
/*     */     }
/*     */     
/*  81 */     if (userDevice.getDisabled().toBoolean()) {
/*  82 */       throw new ReturnCodeException(ReturnCodes.DEVICE_FORBIDDEN, "The device has been locked with username=" + username + ", and deviceId=" + deviceId);
/*     */     }
/*     */     
/*  85 */     DeviceAppAgentVO deviceAppAgent = getUserServiceDao().returnDeviceAppAgent(userDevice, appAgent);
/*     */     
/*  87 */     if (deviceAppAgent == null) {
/*  88 */       throw new ReturnCodeException(ReturnCodes.DEVICE_APP_UNAUTHORIED, "There has no available registraions with username=" + username + ", deviceId=" + deviceId + " and pkgName=" + pkgUnique);
/*     */     }
/*     */     
/*  91 */     UserServiceLocator locator = new UserServiceLocator(authType);
/*  92 */     locator.setUser(user);
/*  93 */     userDevice.setUser(user);
/*  94 */     locator.setUserDevice(userDevice);
/*  95 */     locator.setAppAgent(appAgent);
/*  96 */     deviceAppAgent.setAppAgent(appAgent);
/*  97 */     deviceAppAgent.setUserDevice(userDevice);
/*  98 */     locator.setDeviceAppAgent(deviceAppAgent);
/*     */     
/* 100 */     return locator;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerChallengeVO returnServerChallenge(IServerDataLocator serverData) throws ReturnCodeException {
/* 105 */     ServerChallengeVO vo = getUserServiceDao().returnServerChallengeByUsername(serverData.getUsername());
/*     */     
/* 107 */     if (vo == null) {
/* 108 */       throw new ReturnCodeException(ReturnCodes.REQUEST_INVALID, "Server challenge is null.");
/*     */     }
/*     */     
/* 111 */     if (!serverData.getChallenge().equals(vo.getChallenge())) {
/* 112 */       throw new ReturnCodeException(ReturnCodes.REQUEST_INVALID, "Server challenge is invalid.");
/*     */     }
/*     */ 
/*     */     
/* 116 */     if (vo.getTsLifeTime() < System.currentTimeMillis()) {
/* 117 */       throw new ReturnCodeException(ReturnCodes.REQUEST_TIMEOUT, "The response is expired.");
/*     */     }
/*     */ 
/*     */     
/* 121 */     if (!ChallengeStatus.FIDO_REQ.equals(vo.getStatus())) {
/* 122 */       throw new ReturnCodeException(ReturnCodes.REQUEST_INVALID, "Server challenge is invalid.");
/*     */     }
/*     */     
/* 125 */     if (vo.getTsLifeTime() != serverData.getLifeTimeTs()) {
/* 126 */       throw new ReturnCodeException(ReturnCodes.REQUEST_INVALID, "Server challenge is invalid.");
/*     */     }
/*     */     
/* 129 */     return vo;
/*     */   }
/*     */   
/*     */   protected TokenRegistrationVO getTokenRegistration(DeviceAppAgentVO deviceAppAgent) {
/* 133 */     TokenRegistrationVO vo = getUserServiceDao().returnTokenRegistration(deviceAppAgent);
/*     */     
/* 135 */     if (vo == null) {
/* 136 */       throw new ReturnCodeException(ReturnCodes.DEVICE_APP_UNAUTHORIED, "not founded device app.");
/*     */     }
/*     */     
/* 139 */     return vo;
/*     */   }
/*     */   
/*     */   protected abstract void doProcResponseImp(Operation paramOperation, ServerResponse paramServerResponse, SendUAFResponse paramSendUAFResponse);
/*     */   
/*     */   protected abstract UserServiceDao getUserServiceDao();
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\UAFResponseService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */