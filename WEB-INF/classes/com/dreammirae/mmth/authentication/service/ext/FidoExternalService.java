/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.ext;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*     */ import com.dreammirae.mmth.authentication.service.fido.FidoRespInterworker;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.ExternalServiceItemDao;
/*     */ import com.dreammirae.mmth.db.dao.FidoMetadataDao;
/*     */ import com.dreammirae.mmth.db.dao.authentication.FidoUserServiceDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.ExtRequestStatus;
/*     */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.AuthRequestContents;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ @Service("fidoExternalService")
/*     */ public class FidoExternalService
/*     */ {
/*  27 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.ext.FidoExternalService.class);
/*     */   
/*     */   @Autowired
/*     */   private FidoUserServiceDao fidoUserServiceDao;
/*     */   
/*     */   @Autowired
/*     */   private ExternalServiceItemDao externalServiceItemDao;
/*     */ 
/*     */   
/*     */   public void procCheckUser(AuthenticationResponseLocator resp, String userName) {
/*  37 */     resp.setAaids((String[])FidoMetadataDao.getMetadataStatements().keySet().toArray((Object[])new String[0]));
/*     */ 
/*     */     
/*     */     try {
/*  41 */       UserVO user = getUser(userName);
/*     */       
/*  43 */       ExternalServiceItemVO vo = getExternalServiceItemDao().getOneByUser(user);
/*     */       
/*  45 */       if (vo == null || !ExtRequestStatus.REQ.equals(vo.getStatus())) {
/*  46 */         throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, "No valid authentication request message. with user=" + userName);
/*     */       }
/*     */ 
/*     */       
/*  50 */       if (vo.getTsExpired() < System.currentTimeMillis()) {
/*  51 */         throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, "The request is expired with user=" + userName);
/*     */       }
/*     */ 
/*     */       
/*  55 */       AuthRequestContents contents = vo.getRequestContents();
/*  56 */       contents.setTsRemaining(contents.getTsExpired() - System.currentTimeMillis());
/*  57 */       resp.setAuthReqContents(contents);
/*     */       
/*  59 */       resp.setReturnCode(ReturnCodes.OK);
/*     */     }
/*  61 */     catch (ReturnCodeException e) {
/*  62 */       resp.setReturnCode(e.getReturnCode());
/*  63 */       resp.setErrorMessage(e.getMessage());
/*  64 */       LOG.error(e.getMessage());
/*  65 */     } catch (Exception e) {
/*  66 */       LOG.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void procCancelUser(AuthenticationResponseLocator resp, String userName) {
/*     */     try {
/*  73 */       UserVO user = getUser(userName);
/*     */       
/*  75 */       (new FidoRespInterworker()).respUserCancleInterworker(user, null, null);
/*  76 */       resp.setReturnCode(ReturnCodes.OK);
/*     */     }
/*  78 */     catch (ReturnCodeException e) {
/*  79 */       resp.setReturnCode(e.getReturnCode());
/*  80 */       resp.setErrorMessage(e.getMessage());
/*  81 */       LOG.error(e.getMessage());
/*  82 */     } catch (Exception e) {
/*  83 */       LOG.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void procNotifyDereg(AuthenticationResponseLocator resp, String userName) {
/*     */     try {
/*  91 */       UserVO user = getFidoUserServiceDao().returnUser(userName);
/*     */       
/*  93 */       if (user == null) {
/*  94 */         throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no user with userName=" + userName);
/*     */       }
/*     */ 
/*     */       
/*  98 */       UserServiceLocator loc = new UserServiceLocator(AuthMethodTypes.FIDO);
/*     */       
/* 100 */       getFidoUserServiceDao().forceDeregByUser(user, null, loc);
/*     */       
/* 102 */       (new FidoRespInterworker()).respDeregNotifiedInterworker(user.getUsername());
/*     */       
/* 104 */       resp.setReturnCode(ReturnCodes.OK);
/*     */     }
/* 106 */     catch (ReturnCodeException e) {
/* 107 */       resp.setReturnCode(e.getReturnCode());
/* 108 */       resp.setErrorMessage(e.getMessage());
/* 109 */       LOG.error(e.getMessage());
/* 110 */     } catch (Exception e) {
/* 111 */       LOG.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private FidoUserServiceDao getFidoUserServiceDao() {
/* 116 */     if (this.fidoUserServiceDao == null) {
/* 117 */       this.fidoUserServiceDao = new FidoUserServiceDao();
/*     */     }
/*     */     
/* 120 */     return this.fidoUserServiceDao;
/*     */   }
/*     */   
/*     */   private ExternalServiceItemDao getExternalServiceItemDao() {
/* 124 */     if (this.externalServiceItemDao == null) {
/* 125 */       this.externalServiceItemDao = new ExternalServiceItemDao();
/*     */     }
/*     */     
/* 128 */     return this.externalServiceItemDao;
/*     */   }
/*     */ 
/*     */   
/*     */   private UserVO getUser(String userName) {
/* 133 */     UserVO user = getFidoUserServiceDao().returnUser(userName);
/*     */     
/* 135 */     if (user == null) {
/* 136 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no user with userName=" + userName);
/*     */     }
/*     */     
/* 139 */     if (!UserStatus.AVAILABLE.equals(user.getStatus())) {
/* 140 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no valid FIDO registration for this user :: userName = " + userName);
/*     */     }
/*     */ 
/*     */     
/* 144 */     return user;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 148 */     System.out.println(Commons.displayDateTime(1519089935586L));
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\ext\FidoExternalService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */