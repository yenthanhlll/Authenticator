/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.policy.AuthManagementScope;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*     */ import com.dreammirae.mmth.db.dao.ExternalServiceItemDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.IAuthenticationManagerDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.IUserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.fido.transport.GetUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.ReturnUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.context.RpContext;
/*     */ import com.dreammirae.mmth.util.bean.HashMapWrapper;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.AuthManagerVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
/*     */ import com.dreammirae.mmth.vo.types.DeviceIssueranceStatus;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.dreammirae.mmth.vo.types.UserDeviceStatus;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ public abstract class UAFRequestService implements IUAFRequestService {
/*  38 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.UAFRequestService.class);
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String LOG_FORMAT_ERROR_MSG = "@@@ ERR MSG IN PROC :: ";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String RP_KEY_TID = "tid";
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private IssueCodeService issueCodeService;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateUAFRequest(Operation op, ReturnUAFRequest result, GetUAFRequest getUafRequest) {
/*     */     try {
/*  58 */       RpContext rpContext = getUafRequest.getContext();
/*     */       
/*  60 */       String username = rpContext.getUsername();
/*  61 */       String deviceId = rpContext.getDeviceId();
/*  62 */       DeviceTypes deviceType = DeviceTypes.getDeviceType(rpContext.getDeviceType());
/*  63 */       String pkgName = rpContext.getPkgName();
/*  64 */       String model = rpContext.getModel();
/*  65 */       String alias = rpContext.getAlias(model);
/*     */       
/*  67 */       if (rpContext.containsKey("tid")) {
/*  68 */         validateExternalRequest((String)rpContext.get("tid", String.class));
/*     */       }
/*     */       
/*  71 */       UserServiceLocator serviceLocator = new UserServiceLocator(getAuthMethodTypes());
/*     */       
/*  73 */       getUserServiceLocator(serviceLocator, op, username, deviceId, deviceType, pkgName);
/*  74 */       validateUserServiceData(serviceLocator, op, username, deviceId, deviceType, pkgName, model, alias);
/*     */ 
/*     */       
/*  77 */       if (Operation.Reg.equals(getUafRequest.getOp()) && 
/*  78 */         getAuthMethodTypes().enabledIssueCode()) {
/*  79 */         RpContext respResult = new RpContext();
/*  80 */         result.setContext(respResult);
/*     */         
/*     */         try {
/*  83 */           getIssueCodeService().verifyIssueCode((HashMapWrapper)respResult, getAuthMethodTypes(), username, rpContext.getIssueCode(), deviceId, deviceType, pkgName);
/*  84 */         } catch (ReturnCodeException e) {
/*     */           
/*  86 */           throw new FidoUafStatusCodeException(e.getReturnCode().getStatusCodes(), e.getMessage());
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  92 */       doProcUAFRequestImp(result, getUafRequest, serviceLocator);
/*     */     }
/*  94 */     catch (FidoUafStatusCodeException e) {
/*  95 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage());
/*  96 */       result.setStatusCode(e.getStatusCode());
/*  97 */     } catch (ReturnCodeException e) {
/*  98 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage());
/*  99 */       result.setStatusCode(e.getReturnCode().getStatusCodes());
/* 100 */     } catch (Exception e) {
/* 101 */       LOG.error("@@@ ERR MSG IN PROC :: " + e.getMessage());
/* 102 */       result.setStatusCode(ReturnCodes.INTERNAL_SERVER_ERROR.getStatusCodes());
/* 103 */     } catch (Throwable e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void doProcUAFRequestImp(ReturnUAFRequest paramReturnUAFRequest, GetUAFRequest paramGetUAFRequest, UserServiceLocator paramUserServiceLocator);
/*     */ 
/*     */   
/*     */   protected abstract IUserServiceDao getUserServiceDao();
/*     */ 
/*     */   
/*     */   protected abstract void validateUserServiceDataImp(UserServiceLocator paramUserServiceLocator, Operation paramOperation, String paramString1, String paramString2, DeviceTypes paramDeviceTypes, String paramString3);
/*     */   
/*     */   private void getUserServiceLocator(UserServiceLocator serviceLocator, Operation op, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/* 116 */     if (Operation.Reg.equals(op)) {
/* 117 */       getUserServiceDao().setUserServiceForReg(serviceLocator, username, deviceId, deviceType, pkgUnique);
/* 118 */     } else if (Operation.Auth.equals(op)) {
/* 119 */       getUserServiceDao().setUserServiceForAuth(serviceLocator, username, deviceId, deviceType, pkgUnique);
/*     */     } else {
/* 121 */       getUserServiceDao().setUserServiceForDereg(serviceLocator, username, deviceId, deviceType, pkgUnique);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateUserServiceData(UserServiceLocator serviceLocator, Operation op, String username, String deviceId, DeviceTypes deviceType, String pkgUnique, String model, String alias) {
/* 127 */     AppAgentVO appAgent = serviceLocator.getAppAgent();
/*     */     
/* 129 */     if (appAgent == null) {
/* 130 */       throw new ReturnCodeException(ReturnCodes.UNKNOWN_APP_AGENT, "The appAgent [pkgName=" + deviceType.getOsType().name() + ":" + pkgUnique + "] has been not acceptable.");
/*     */     }
/*     */     
/* 133 */     if (Operation.Reg.equals(op)) {
/* 134 */       validateUserServiceDataForReg(serviceLocator, appAgent, username, deviceId, deviceType, pkgUnique, model, alias);
/* 135 */     } else if (Operation.Auth.equals(op)) {
/* 136 */       validateUserServiceDataForAuth(serviceLocator, username, deviceId, deviceType, pkgUnique);
/*     */     } else {
/* 138 */       validateUserServiceDataForDereg(serviceLocator, username, deviceId, deviceType, pkgUnique);
/*     */     } 
/*     */     
/* 141 */     validateUserServiceDataImp(serviceLocator, op, username, deviceId, deviceType, pkgUnique);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateUserServiceDataForReg(UserServiceLocator locator, AppAgentVO appAgent, String username, String deviceId, DeviceTypes deviceType, String pkgUnique, String model, String alias) {
/* 148 */     UserVO user = locator.getUser();
/*     */     
/* 150 */     if (user == null) {
/* 151 */       user = UserVO.createNewInstance(username, ProductType.NONE.getCode(), "-");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 156 */     if (user.getStatus() == UserStatus.AVAILABLE)
/*     */     {
/* 158 */       throw new ReturnCodeException(ReturnCodes.EXIST_ALREADY, "Please close the connected autentication type. AuthMethodType:" + getAuthMethodTypes().name() + " :: Operation:" + Operation.Reg
/* 159 */           .name() + " [username=" + username + ", deviceId=" + deviceId + ", deviceType=" + deviceType.name() + ", pkgName=" + appAgent.getPkgUnique() + "]");
/*     */     }
/*     */     
/* 162 */     if (user.getDisabled().toBoolean()) {
/* 163 */       throw new ReturnCodeException(ReturnCodes.USER_FORBIDDEN, "This request has been aborted because the user has been locked. AuthMethodType:" + getAuthMethodTypes().name() + " :: Operation:" + Operation.Reg
/* 164 */           .name() + " [username=" + username + ", deviceId=" + deviceId + ", deviceType=" + deviceType.name() + ", pkgName=" + appAgent.getPkgUnique() + "]");
/*     */     }
/*     */     
/* 167 */     UserDeviceVO userDevice = locator.getUserDevice();
/*     */ 
/*     */     
/* 170 */     if (userDevice == null) {
/* 171 */       userDevice = UserDeviceVO.createNewInstance(user, deviceId, deviceType, model, alias, getAuthMethodTypes(), DeviceIssueranceStatus.NEW_USER_REGISTER);
/* 172 */     } else if (UserDeviceStatus.LOST_STOLEN.equals(userDevice.getStatus())) {
/* 173 */       throw new ReturnCodeException(ReturnCodes.DEVICE_FORBIDDEN, "This request has been aborted because the user has been locked. AuthMethodType:" + getAuthMethodTypes().name() + " :: Operation:" + Operation.Reg
/* 174 */           .name() + " [username=" + username + ", deviceId=" + deviceId + ", deviceType=" + deviceType.name() + ", pkgName=" + appAgent.getPkgUnique() + "]");
/*     */     } 
/*     */     
/* 177 */     DeviceAppAgentVO deviceAppAgent = locator.getDeviceAppAgent();
/*     */ 
/*     */     
/* 180 */     if (deviceAppAgent != null) {
/* 181 */       if (DeviceAppAgentStatus.AVAIABLE.equals(deviceAppAgent.getStatus())) {
/* 182 */         throw new ReturnCodeException(ReturnCodes.EXIST_ALREADY, "The user already exists with AuthMethodType:" + getAuthMethodTypes().name() + " [username=" + username + ", deviceId=" + deviceId + ", deviceType=" + deviceType
/* 183 */             .name() + ", pkgName=" + appAgent.getPkgUnique() + "]");
/*     */       }
/*     */     } else {
/* 186 */       deviceAppAgent = DeviceAppAgentVO.createNewInstance(appAgent, userDevice, getAuthMethodTypes());
/*     */     } 
/*     */     
/* 189 */     AuthManagerVO authManager = locator.getAuthManager();
/*     */     
/* 191 */     if (authManager == null) {
/* 192 */       authManager = AuthManagerVO.createNewAuthManager(userDevice, getAuthMethodTypes());
/*     */     }
/*     */     
/* 195 */     if (-1 == user.getId() || user.getRegCumulation(getAuthMethodTypes()) < 1) {
/*     */       
/* 197 */       userDevice.setLatestIssueStatus(getAuthMethodTypes(), DeviceIssueranceStatus.NEW_USER_REGISTER);
/* 198 */       userDevice.setAppRegCnt(0);
/* 199 */       deviceAppAgent.setStatus(DeviceAppAgentStatus.NOT_AVAILABLE);
/* 200 */       user.setProductType(ProductType.BIOTP);
/* 201 */       locator.setAuthManager(authManager);
/* 202 */       locator.setUser(user);
/* 203 */       locator.setUserDevice(userDevice);
/* 204 */       locator.setDeviceAppAgent(deviceAppAgent);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 210 */     if (!UserStatus.AVAILABLE.equals(user.getStatus())) {
/* 211 */       if (-1 == userDevice.getId() || DeviceIssueranceStatus.NEW_USER_REGISTER
/* 212 */         .equals(userDevice.getLatestIssueStatus(getAuthMethodTypes())) || DeviceIssueranceStatus.ADD_DEVICE_REGISTER
/* 213 */         .equals(userDevice.getLatestIssueStatus(getAuthMethodTypes()))) {
/*     */         
/* 215 */         userDevice.setLatestIssueStatus(getAuthMethodTypes(), DeviceIssueranceStatus.ADD_DEVICE_REGISTER);
/*     */       } else {
/*     */         
/* 218 */         userDevice.setLatestIssueStatus(getAuthMethodTypes(), DeviceIssueranceStatus.RE_REGISTER);
/*     */       } 
/*     */       
/* 221 */       authManager.setAuthFailCnt(0);
/* 222 */       user.setProductType(ProductType.BIOTP);
/* 223 */       locator.setAuthManager(authManager);
/* 224 */       locator.setUser(user);
/* 225 */       locator.setUserDevice(userDevice);
/* 226 */       locator.setDeviceAppAgent(deviceAppAgent);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 234 */     if (-1 == userDevice.getId() || DeviceIssueranceStatus.NEW_USER_REGISTER
/* 235 */       .equals(userDevice.getLatestIssueStatus(getAuthMethodTypes())) || DeviceIssueranceStatus.ADD_DEVICE_REGISTER
/* 236 */       .equals(userDevice.getLatestIssueStatus(getAuthMethodTypes()))) {
/*     */ 
/*     */       
/* 239 */       int authActiveCnt = getUserServiceDao().returnRegisteredDeviceAppCnt(user, getAuthMethodTypes());
/* 240 */       if (authActiveCnt > 0) {
/* 241 */         getAuthMethodTypes().validateMultiDevice(user);
/*     */       }
/*     */       
/* 244 */       userDevice.setLatestIssueStatus(getAuthMethodTypes(), DeviceIssueranceStatus.ADD_DEVICE_REGISTER);
/*     */     }
/*     */     else {
/*     */       
/* 248 */       List<DeviceAppAgentVO> registeredApps = getUserServiceDao().returnDeviceApps(userDevice);
/*     */       
/* 250 */       boolean hasAuthType = false;
/* 251 */       for (DeviceAppAgentVO vo : registeredApps) {
/* 252 */         if (getAuthMethodTypes().equals(vo.getAuthType())) {
/* 253 */           hasAuthType = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 258 */       if (!hasAuthType)
/*     */       {
/*     */         
/* 261 */         userDevice.setLatestIssueStatus(getAuthMethodTypes(), DeviceIssueranceStatus.RE_REGISTER);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 266 */     locator.setAuthManager(authManager);
/* 267 */     locator.setUser(user);
/* 268 */     locator.setUserDevice(userDevice);
/* 269 */     locator.setDeviceAppAgent(deviceAppAgent);
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateUserServiceDataForAuth(UserServiceLocator serviceLocator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/* 274 */     validateUser(serviceLocator.getUser(), username);
/* 275 */     validateUserDevice(serviceLocator.getUser(), serviceLocator.getUserDevice(), deviceId);
/* 276 */     validateDeviceAppAgent(serviceLocator.getUser(), serviceLocator.getUserDevice(), serviceLocator.getAppAgent(), serviceLocator.getDeviceAppAgent());
/*     */     
/* 278 */     AuthManagementScope authScope = SystemSettingsDao.getAuthManagementScope();
/* 279 */     authScope.validateAuthentication((IAuthenticationManagerDao)getUserServiceDao(), serviceLocator.getUser(), serviceLocator.getUserDevice(), getAuthMethodTypes());
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateUserServiceDataForDereg(UserServiceLocator serviceLocator, String username, String deviceId, DeviceTypes deviceType, String pkgUnique) {
/* 284 */     validateUser(serviceLocator.getUser(), username);
/* 285 */     validateUserDevice(serviceLocator.getUser(), serviceLocator.getUserDevice(), deviceId);
/* 286 */     validateDeviceAppAgent(serviceLocator.getUser(), serviceLocator.getUserDevice(), serviceLocator.getAppAgent(), serviceLocator.getDeviceAppAgent());
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateUser(UserVO user, String username) {
/* 291 */     if (user == null) {
/* 292 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no user with " + username);
/*     */     }
/*     */     
/* 295 */     if (user.getDisabled().toBoolean()) {
/* 296 */       throw new ReturnCodeException(ReturnCodes.USER_FORBIDDEN, "The user has been locked with username=" + username);
/*     */     }
/*     */ 
/*     */     
/* 300 */     if (!UserStatus.AVAILABLE.equals(user.getStatus())) {
/* 301 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no available registraions with username=" + username);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateUserDevice(UserVO user, UserDeviceVO userDevice, String deviceId) {
/* 309 */     if (userDevice == null) {
/* 310 */       throw new ReturnCodeException(ReturnCodes.DEVICE_UNAUTHORIED, "There has no available registraions with username=" + user
/* 311 */           .getUsername() + " and deviceId=" + deviceId);
/*     */     }
/*     */     
/* 314 */     if (userDevice.getDisabled().toBoolean()) {
/* 315 */       throw new ReturnCodeException(ReturnCodes.DEVICE_FORBIDDEN, "The device has been locked with username=" + user
/* 316 */           .getUsername() + ", and deviceId=" + deviceId);
/*     */     }
/*     */     
/* 319 */     if (UserDeviceStatus.LOST_STOLEN.equals(userDevice.getStatus()))
/* 320 */       throw new ReturnCodeException(ReturnCodes.DEVICE_FORBIDDEN, "The device has been locked with username=" + user.getUsername() + ", and deviceId=" + deviceId); 
/* 321 */     if (!UserDeviceStatus.AVAILABLE.equals(userDevice.getStatus())) {
/* 322 */       throw new ReturnCodeException(ReturnCodes.DEVICE_UNAUTHORIED, "There has no available registraions with username=" + user
/* 323 */           .getUsername());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateDeviceAppAgent(UserVO user, UserDeviceVO userDevice, AppAgentVO appAgent, DeviceAppAgentVO deviceAppAgent) {
/* 330 */     if (deviceAppAgent == null) {
/* 331 */       throw new ReturnCodeException(ReturnCodes.DEVICE_APP_UNAUTHORIED, "There has no available registraions with username=" + user
/* 332 */           .getUsername() + ", deviceId=" + userDevice
/* 333 */           .getDeviceId() + " and pkgName=" + appAgent.getPkgUnique());
/*     */     }
/*     */     
/* 336 */     if (!DeviceAppAgentStatus.AVAIABLE.equals(deviceAppAgent.getStatus())) {
/* 337 */       throw new ReturnCodeException(ReturnCodes.DEVICE_APP_UNAUTHORIED, "There has no available registraions with username=" + user
/* 338 */           .getUsername() + ", deviceId=" + userDevice
/* 339 */           .getDeviceId() + " and pkgName=" + appAgent.getPkgUnique());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateExternalRequest(String tid) {
/* 346 */     if (!ExternalWebApiTypes.MIRAE_ASSET_VT.equals(SystemSettingsDao.getWebApiPolicy())) {
/*     */       return;
/*     */     }
/*     */     
/* 350 */     ExternalServiceItemVO vo = (new ExternalServiceItemDao()).getOneByTransaction(tid);
/*     */     
/* 352 */     if (vo == null) {
/* 353 */       throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, "There has no external request for tid=" + tid);
/*     */     }
/*     */     
/* 356 */     if (vo.getTsExpired() < System.currentTimeMillis()) {
/* 357 */       throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, "The external reques for tid=" + tid + " is expired.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IssueCodeService getIssueCodeService() {
/* 364 */     if (this.issueCodeService == null) {
/* 365 */       this.issueCodeService = new IssueCodeService();
/*     */     }
/*     */     
/* 368 */     return this.issueCodeService;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\UAFRequestService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */