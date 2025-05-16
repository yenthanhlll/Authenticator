/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.AdministratorVO;
/*    */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*    */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*    */ import com.dreammirae.mmth.vo.types.AdministratorTypes;
/*    */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*    */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*    */ import com.dreammirae.mmth.web.service.AdministratorService;
/*    */ import com.dreammirae.mmth.web.service.ViewService;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ @RestController
/*    */ @RequestMapping(value = {"/web/manager/administrator/rest"}, method = {RequestMethod.POST})
/*    */ public class AdministratorController
/*    */   extends ViewController<AdministratorVO, Integer> {
/*    */   @Autowired
/*    */   private AdministratorService service;
/*    */   
/*    */   @RequestMapping({"/forceChangePassword"})
/*    */   public RestResponse forceChangePassword(@RequestParam("id") int id, @RequestParam("passwordChanged") String passwordChanged, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 29 */     RestResponse resp = new RestResponse();
/*    */     try {
/* 31 */       getViewService().forceChangePassword(id, passwordChanged, getSessionAdmin(session));
/* 32 */     } catch (I18nMessageException e) {
/* 33 */       setI18nErrorMessage(resp, e);
/*    */     } 
/*    */     
/* 36 */     return resp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 45 */     getPagination(resp, 1, null, null, null);
/* 46 */     getViewStats(resp);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void detailsImp(RestResponse resp, Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 52 */     if (-1 == id.intValue()) {
/* 53 */       AdministratorVO vo = AdministratorVO.createAdministrationVO(null, null, AdministratorTypes.ADMIN);
/* 54 */       resp.addData("details", vo);
/*    */       
/*    */       return;
/*    */     } 
/* 58 */     getDetails(resp, id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveImp(RestResponse resp, AdministratorVO vo, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 65 */     doSave(resp, (IRestValidator)vo, session);
/*    */     
/* 67 */     if (resp.getHasMessages()) {
/*    */       return;
/*    */     }
/*    */     
/* 71 */     getPagination(resp, 1, null, null, null);
/* 72 */     getViewStats(resp);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void deleteImp(RestResponse resp, Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 79 */     doDelete(resp, id, session);
/*    */     
/* 81 */     if (resp.getHasMessages()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 86 */     getPagination(resp, 1, null, null, null);
/* 87 */     getViewStats(resp);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AdministratorService getViewService() {
/* 93 */     if (this.service == null) {
/* 94 */       this.service = new AdministratorService();
/*    */     }
/*    */     
/* 97 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\AdministratorController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */