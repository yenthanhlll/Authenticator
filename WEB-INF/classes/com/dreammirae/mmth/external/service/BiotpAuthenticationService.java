/*    */ package WEB-INF.classes.com.dreammirae.mmth.external.service;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*    */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*    */ import com.dreammirae.mmth.authentication.service.biotp.BiotpGeneralService;
/*    */ import com.dreammirae.mmth.db.dao.UserDao;
/*    */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*    */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*    */ import com.dreammirae.mmth.external.service.ExternalAuthenticationService;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiotpAuthenticationService
/*    */   extends ExternalAuthenticationService
/*    */ {
/*    */   @Autowired
/*    */   private BiotpUserServiceDao dao;
/*    */   @Autowired
/*    */   private UserDao userdao;
/*    */   @Autowired
/*    */   private BiotpGeneralService bitopGeneralService;
/*    */   
/*    */   protected UserServiceDao getUserServiceDao() {
/* 28 */     if (this.dao == null) {
/* 29 */       this.dao = new BiotpUserServiceDao();
/*    */     }
/* 31 */     return (UserServiceDao)this.dao;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AuthMethodTypes getAuthMethodTypes() {
/* 36 */     return AuthMethodTypes.BIOTP;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected IGeneralService getGeneralService() {
/* 42 */     if (this.bitopGeneralService == null) {
/* 43 */       this.bitopGeneralService = new BiotpGeneralService();
/*    */     }
/*    */     
/* 46 */     return (IGeneralService)this.bitopGeneralService;
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserDao getUserDao() {
/* 51 */     if (this.userdao == null) {
/* 52 */       this.userdao = new UserDao();
/*    */     }
/* 54 */     return this.userdao;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\service\BiotpAuthenticationService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */