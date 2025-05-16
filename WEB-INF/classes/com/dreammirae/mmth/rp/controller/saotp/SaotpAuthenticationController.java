/*    */ package WEB-INF.classes.com.dreammirae.mmth.rp.controller.saotp;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*    */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*    */ import com.dreammirae.mmth.authentication.service.IUAFRequestService;
/*    */ import com.dreammirae.mmth.authentication.service.IUAFResponseService;
/*    */ import com.dreammirae.mmth.authentication.service.IVerifyOTPService;
/*    */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*    */ import com.dreammirae.mmth.rp.controller.AuthenticationController;
/*    */ 
/*    */ public class SaotpAuthenticationController
/*    */   extends AuthenticationController
/*    */ {
/*    */   protected AuthMethodTypes getAuthMethodType() {
/* 15 */     return AuthMethodTypes.SAOTP;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected IUAFRequestService getUAFRequestService() {
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected IUAFResponseService getUAFResponseService() {
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected IVerifyOTPService getVerifyOTPService() {
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected IssueCodeService getIssueCodeService() {
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected IGeneralService getGeneralService() {
/* 45 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\controller\saotp\SaotpAuthenticationController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */