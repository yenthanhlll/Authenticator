/*    */ package WEB-INF.classes.com.dreammirae.mmth.external.service;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.db.dao.UserDao;
/*    */ import com.dreammirae.mmth.db.dao.authentication.FidoUserServiceDao;
/*    */ import com.dreammirae.mmth.db.dao.authentication.UserServiceDao;
/*    */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*    */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*    */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*    */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*    */ import com.dreammirae.mmth.external.service.ExternalAuthenticationService;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ 
/*    */ public class FidoAuthenticationService
/*    */   extends ExternalAuthenticationService
/*    */ {
/*    */   @Autowired
/*    */   private FidoUserServiceDao dao;
/*    */   @Autowired
/*    */   private UserDao userdao;
/*    */   
/*    */   public void getTokenStatus(WebApiResponse result) {
/* 27 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This api is not suppored.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void doResetAuthFail(WebApiResponse result, WebApiRequestParam params) {
/* 32 */     if (ExternalWebApiTypes.GLOBAL_WIBEE.equals(SystemSettingsDao.getWebApiPolicy())) {
/* 33 */       throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This api is not suppored.");
/*    */     }
/*    */     
/* 36 */     super.doResetAuthFail(result, params);
/*    */   }
/*    */ 
/*    */   
/*    */   public void raiseLostOtp(WebApiResponse result, WebApiRequestParam params) {
/* 41 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This api is not suppored.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void getIssueCode(WebApiResponse result, WebApiRequestParam params) throws ReturnCodeException {
/* 46 */     if (ExternalWebApiTypes.GLOBAL_WIBEE.equals(SystemSettingsDao.getWebApiPolicy())) {
/* 47 */       throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This api is not suppored.");
/*    */     }
/* 49 */     super.getIssueCode(result, params);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reqForReIssueCode(WebApiResponse result, WebApiRequestParam param) {
/* 54 */     if (ExternalWebApiTypes.GLOBAL_WIBEE.equals(SystemSettingsDao.getWebApiPolicy())) {
/* 55 */       throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This api is not suppored.");
/*    */     }
/*    */     
/* 58 */     super.reqForReIssueCode(result, param);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected UserServiceDao getUserServiceDao() {
/* 64 */     if (this.dao == null) {
/* 65 */       this.dao = new FidoUserServiceDao();
/*    */     }
/* 67 */     return (UserServiceDao)this.dao;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AuthMethodTypes getAuthMethodTypes() {
/* 72 */     return AuthMethodTypes.FIDO;
/*    */   }
/*    */ 
/*    */   
/*    */   protected IGeneralService getGeneralService() {
/* 77 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserDao getUserDao() {
/* 82 */     if (this.userdao == null) {
/* 83 */       this.userdao = new UserDao();
/*    */     }
/* 85 */     return this.userdao;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\service\FidoAuthenticationService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */