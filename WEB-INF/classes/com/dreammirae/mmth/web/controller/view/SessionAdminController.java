/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.AdministratorDao;
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*    */ import com.dreammirae.mmth.vo.AdministratorVO;
/*    */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*    */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @RestController
/*    */ @RequestMapping(value = {"/web/manager/sessionAdmin/rest"}, method = {RequestMethod.POST})
/*    */ public class SessionAdminController
/*    */ {
/*    */   @Autowired
/*    */   private AdministratorDao dao;
/*    */   
/*    */   @RequestMapping({"/adminChangePassword"})
/*    */   public RestResponse adminChangePassword(@RequestParam("admin-password-prev") String prevPassword, @RequestParam("admin-password") String adminPassword, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 33 */     RestResponse resp = new RestResponse();
/*    */     
/* 35 */     if (!adminPassword.matches("^[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{5,120}$")) {
/* 36 */       resp.addContextMessage("admin-password", new I18nMessage("validate.regex.password"));
/* 37 */       return resp;
/*    */     } 
/*    */     
/* 40 */     AdministratorVO sessionAdmin = ViewController.getSessionAdmin(session);
/*    */     
/* 42 */     AdministratorVO vo = this.dao.getOneByObj(sessionAdmin);
/*    */     
/* 44 */     if (!SysEnvCommon.passwordMatches(prevPassword, vo.getPassword())) {
/* 45 */       resp.addContextMessage("admin-password-prev", new I18nMessage("administrator.validate.password.missmatch"));
/* 46 */       return resp;
/*    */     } 
/*    */ 
/*    */     
/* 50 */     vo.setPassword(SysEnvCommon.encPassword(adminPassword));
/*    */     
/*    */     try {
/* 53 */       this.dao.save(vo);
/* 54 */     } catch (Exception e) {
/* 55 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }));
/*    */     } 
/*    */     
/* 58 */     return resp;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\SessionAdminController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */