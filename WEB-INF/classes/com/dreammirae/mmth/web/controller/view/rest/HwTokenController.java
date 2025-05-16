/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.vo.HwTokenVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.HwTokenService;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/web/manager/hwtoken/rest"}, method = {RequestMethod.POST})
/*     */ public class HwTokenController
/*     */   extends ViewController<HwTokenVO, String> {
/*     */   @Autowired
/*     */   private HwTokenService service;
/*     */   
/*     */   @RequestMapping({"/upload/{tokenType}"})
/*     */   public RestResponse upload(@PathVariable("tokenType") String tokenType, @RequestParam("csvFile") MultipartFile csvFile, HttpSession session) {
/*  31 */     RestResponse resp = new RestResponse();
/*     */     
/*     */     try {
/*  34 */       getViewService().upload(csvFile, tokenType, null, getSessionAdmin(session));
/*  35 */       getPagination(resp, 1, null, null, null);
/*  36 */       getViewStats(resp);
/*     */     }
/*  38 */     catch (I18nMessageException e) {
/*  39 */       setI18nErrorMessage(resp, e);
/*     */     } 
/*     */     
/*  42 */     return resp;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/alldelete"})
/*     */   public RestResponse alldelete(HttpSession session) {
/*  48 */     RestResponse resp = new RestResponse();
/*     */     
/*     */     try {
/*  51 */       getViewService().alldelete(getSessionAdmin(session));
/*  52 */       getPagination(resp, 1, null, null, null);
/*  53 */       getViewStats(resp);
/*     */     }
/*  55 */     catch (I18nMessageException e) {
/*  56 */       setI18nErrorMessage(resp, e);
/*     */     } 
/*     */     
/*  59 */     return resp;
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
/*     */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  77 */     getPagination(resp, 1, null, null, null);
/*  78 */     getViewStats(resp);
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
/*  91 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
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
/*     */   protected void saveImp(RestResponse resp, HwTokenVO vo, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 106 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
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
/* 120 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
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
/*     */   protected HwTokenService getViewService() {
/* 133 */     if (this.service == null) {
/* 134 */       this.service = new HwTokenService();
/*     */     }
/*     */     
/* 137 */     return this.service;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\HwTokenController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */