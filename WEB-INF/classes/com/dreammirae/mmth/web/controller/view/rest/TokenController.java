/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.TokenService;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/web/manager/token/rest"}, method = {RequestMethod.POST})
/*     */ public class TokenController
/*     */   extends ViewController<TokenVO, String> {
/*     */   @Autowired
/*     */   private TokenService service;
/*     */   
/*     */   @RequestMapping({"/upload"})
/*     */   public RestResponse upload(@RequestParam("ttkFile") MultipartFile ttkFile, @RequestParam("pin") String pin, HttpSession session) {
/*  30 */     RestResponse resp = new RestResponse();
/*     */     
/*     */     try {
/*  33 */       getViewService().upload(ttkFile, pin, getSessionAdmin(session));
/*  34 */       getPagination(resp, 1, null, null, null);
/*  35 */       getViewStats(resp);
/*     */     }
/*  37 */     catch (I18nMessageException e) {
/*  38 */       setI18nErrorMessage(resp, e);
/*     */     } 
/*     */     
/*  41 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  60 */     getPagination(resp, 1, null, null, null);
/*  61 */     getViewStats(resp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void detailsImp(RestResponse resp, String tokenId, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  74 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveImp(RestResponse resp, TokenVO vo, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  89 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deleteImp(RestResponse resp, String tokenId, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 103 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TokenService getViewService() {
/* 116 */     if (this.service == null) {
/* 117 */       this.service = new TokenService();
/*     */     }
/*     */     
/* 120 */     return this.service;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\TokenController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */