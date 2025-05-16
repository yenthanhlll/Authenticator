/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.biotp;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*    */ import com.dreammirae.mmth.authentication.service.VerifyOTPService;
/*    */ import com.dreammirae.mmth.db.dao.authentication.BiotpUserServiceDao;
/*    */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ @Service("biotpVerifyOTPService")
/*    */ public class BiotpVerifyOTPService
/*    */   extends VerifyOTPService
/*    */ {
/*    */   private BiotpUserServiceDao biotpUserServiceDao;
/*    */   
/*    */   public AuthMethodTypes getAuthMethodTypes() {
/* 17 */     return AuthMethodTypes.BIOTP;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected UserServiceDao getUserServiceDao() {
/* 23 */     if (this.biotpUserServiceDao == null) {
/* 24 */       this.biotpUserServiceDao = new BiotpUserServiceDao();
/*    */     }
/*    */     
/* 27 */     return (UserServiceDao)this.biotpUserServiceDao;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\biotp\BiotpVerifyOTPService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */