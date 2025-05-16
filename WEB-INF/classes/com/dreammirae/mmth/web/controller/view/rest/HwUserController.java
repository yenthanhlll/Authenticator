/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.HwTokenDao;
/*     */ import com.dreammirae.mmth.db.dao.ServiceLogDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.vo.HwTokenVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.HwUserService;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RestController;
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
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/web/manager/hwuser/rest"}, method = {RequestMethod.POST})
/*     */ public class HwUserController
/*     */   extends ViewController<HwTokenVO, String>
/*     */ {
/*     */   @Autowired
/*     */   private HwUserService service;
/*     */   @Autowired
/*     */   private ServiceLogDao serviceLogDao;
/*     */   @Autowired
/*     */   private HwTokenDao hwTokenDao;
/*     */   
/*     */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  53 */     getPagination(resp, 1, null, null, null);
/*     */     
/*  55 */     if (!ExternalWebApiTypes.GLOBAL_WIBEE.equals(SystemSettingsDao.getWebApiPolicy())) {
/*  56 */       getViewStats(resp);
/*     */     }
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
/*     */   protected void detailsImp(RestResponse resp, String id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*     */     try {
/*  72 */       HwTokenVO token = (HwTokenVO)getViewService().getDetails(id);
/*  73 */       resp.addData("details", token);
/*     */       
/*  75 */       resp.addData("devices", "");
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  80 */     catch (I18nMessageException e) {
/*  81 */       setI18nErrorMessage(resp, e);
/*     */     } 
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
/*  97 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
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
/*     */   protected void deleteImp(RestResponse resp, String id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 111 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
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
/*     */   protected HwUserService getViewService() {
/* 124 */     if (this.service == null) {
/* 125 */       this.service = new HwUserService();
/*     */     }
/*     */     
/* 128 */     return this.service;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\HwUserController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */