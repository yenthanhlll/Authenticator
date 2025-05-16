/*     */ package WEB-INF.classes.com.dreammirae.mmth.external.service.gptwr;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.db.dao.ExternalServiceItemDao;
/*     */ import com.dreammirae.mmth.db.dao.UserDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.FidoUserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.GptwrRequestParam;
/*     */ import com.dreammirae.mmth.external.bean.GptwrWebApiResponse;
/*     */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*     */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*     */ import com.dreammirae.mmth.vo.UserAnnotationVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.FcmPushContents;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service("gptwrExtApiService")
/*     */ public class GptwrExtApiFidoService
/*     */ {
/*  33 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.external.service.gptwr.GptwrExtApiFidoService.class);
/*     */   
/*     */   @Autowired
/*     */   private UserDao userDao;
/*     */   
/*     */   @Autowired
/*     */   private FidoUserServiceDao serviceDao;
/*     */   
/*     */   @Autowired
/*     */   private IssueCodeService issueCodeService;
/*     */   
/*     */   @Autowired
/*     */   private ExternalServiceItemDao extServiceItemDao;
/*     */ 
/*     */   
/*     */   public void procRegisterUser(GptwrWebApiResponse resp, String userName, String displayName, String extUnique, String customerCode) throws ReturnCodeException {
/*     */     try {
/*  50 */       UserVO user = this.serviceDao.returnUser(userName);
/*     */       
/*  52 */       if (user != null) {
/*     */         
/*  54 */         if (UserStatus.AVAILABLE.equals(user.getStatus())) {
/*  55 */           throw new ReturnCodeException(ReturnCodes.EXIST_ALREADY, "The user[" + userName + "] exists already.");
/*     */         }
/*     */         
/*  58 */         user.setStatus(UserStatus.NOT_AVAILABLE);
/*     */       } else {
/*     */         
/*  61 */         user = UserVO.createNewInstance(userName);
/*     */       } 
/*  63 */       UserAnnotationVO annotation = new UserAnnotationVO();
/*  64 */       annotation.setUserId(user.getId());
/*  65 */       annotation.setUsername(userName);
/*  66 */       annotation.setDisplayName(displayName);
/*  67 */       annotation.setExtUnique(extUnique);
/*  68 */       annotation.setCustomerCode(customerCode);
/*  69 */       user.setAnnotation(annotation);
/*  70 */       this.userDao.save(user);
/*  71 */       resp.setReturnCode(ReturnCodes.OK);
/*  72 */     } catch (ReturnCodeException e) {
/*  73 */       LOG.error("Error Occured..", (Throwable)e);
/*  74 */       throw e;
/*  75 */     } catch (Exception e) {
/*  76 */       LOG.error("Error Occured..", e);
/*  77 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, "Internal server error occured....");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void procRemoveUser(GptwrWebApiResponse resp, String userName) throws ReturnCodeException {
/*     */     try {
/*  85 */       UserVO user = this.serviceDao.returnUser(userName);
/*     */       
/*  87 */       if (user == null) {
/*  88 */         resp.setReturnCode(ReturnCodes.OK);
/*     */         
/*     */         return;
/*     */       } 
/*  92 */       UserServiceLocator loc = new UserServiceLocator(AuthMethodTypes.FIDO);
/*  93 */       this.serviceDao.forceDeregByUser(user, null, loc);
/*  94 */       user.setStatus(UserStatus.DISCARD);
/*  95 */       this.serviceDao.saveUser(user);
/*  96 */       resp.setReturnCode(ReturnCodes.OK);
/*     */     }
/*  98 */     catch (Exception e) {
/*  99 */       LOG.error("Error Occured..", e);
/* 100 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, "Internal server error occured....");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void procForceDereg(GptwrWebApiResponse resp, String userName) throws ReturnCodeException {
/*     */     try {
/* 109 */       UserVO user = this.serviceDao.returnUser(userName);
/*     */       
/* 111 */       if (user == null) {
/* 112 */         resp.setReturnCode(ReturnCodes.OK);
/*     */         
/*     */         return;
/*     */       } 
/* 116 */       UserServiceLocator loc = new UserServiceLocator(AuthMethodTypes.FIDO);
/*     */       
/* 118 */       this.serviceDao.forceDeregByUser(user, null, loc);
/* 119 */       resp.setReturnCode(ReturnCodes.OK);
/*     */     }
/* 121 */     catch (Exception e) {
/* 122 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, "Internal server error occured....");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void procGenIssueCode(GptwrWebApiResponse resp, GptwrRequestParam params) throws ReturnCodeException {
/*     */     try {
/* 129 */       this.issueCodeService.generateIssueCode(AuthMethodTypes.FIDO, (WebApiResponse)resp, (WebApiRequestParam)params);
/* 130 */     } catch (ReturnCodeException e) {
/* 131 */       LOG.error("Error Occured..", (Throwable)e);
/* 132 */       throw e;
/* 133 */     } catch (Exception e) {
/* 134 */       LOG.error("Error Occured..", e);
/* 135 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, "Internal server error occured....");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void procNotification(GptwrWebApiResponse resp, ExternalServiceItemVO item) throws ReturnCodeException {
/*     */     try {
/* 144 */       UserVO user = this.serviceDao.returnUser(item.getUserName());
/*     */       
/* 146 */       if (user == null) {
/* 147 */         throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "The user[" + item.getUserName() + "] does not exist.");
/*     */       }
/*     */       
/* 150 */       if (user.getDisabled().toBoolean()) {
/* 151 */         throw new ReturnCodeException(ReturnCodes.USER_FORBIDDEN, "The user[" + item.getUserName() + "] has been locked.");
/*     */       }
/*     */       
/* 154 */       if (!UserStatus.AVAILABLE.equals(user.getStatus())) {
/* 155 */         throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "The valid registration for user[" + item.getUserName() + "] does not exist.");
/*     */       }
/*     */       
/* 158 */       if (item.isUsePush() && 
/* 159 */         user.getReprAppAgentId() == -1) {
/* 160 */         throw new ReturnCodeException(ReturnCodes.FCM_TOKEN_NONE, "The user[" + item.getUserName() + "] has no FCM token Id..");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 165 */       DeviceAppAgentVO deviceAppAgent = this.serviceDao.returnDeviceAppAgent(user.getReprAppAgentId());
/*     */       
/* 167 */       if (deviceAppAgent == null) {
/* 168 */         throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "The valid registration for user[" + item.getUserName() + "] does not exist.");
/*     */       }
/*     */       
/* 171 */       if (StringUtils.isEmpty(deviceAppAgent.getRegistrationId())) {
/* 172 */         throw new ReturnCodeException(ReturnCodes.FCM_TOKEN_NONE, "The valid registration for user[" + item.getUserName() + "] does not exist.");
/*     */       }
/*     */       
/* 175 */       AppAgentVO appAgent = AppAgentDao.getAcceptableAppAgent(deviceAppAgent.getAgentId());
/*     */ 
/*     */       
/* 178 */       item.setUserId(user.getId());
/*     */       
/* 180 */       this.extServiceItemDao.save(item);
/*     */       
/* 182 */       if (item.isUsePush()) {
/* 183 */         FcmPushContents contents = FcmPushContents.createEnterprisePushManagerVO(user, deviceAppAgent, appAgent, item, FcmPushContents.PushTypes.AUTH);
/* 184 */         Commons.ctx.getExternalServiceManager().addFCMPushMsg(contents);
/*     */       } 
/*     */       
/* 187 */       resp.setReturnCode(ReturnCodes.OK);
/*     */     }
/* 189 */     catch (ReturnCodeException e) {
/* 190 */       LOG.error("Error Occured..", (Throwable)e);
/* 191 */       throw e;
/* 192 */     } catch (Exception e) {
/* 193 */       LOG.error("Error Occured..", e);
/* 194 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, "Internal server error occured....");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\service\gptwr\GptwrExtApiFidoService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */