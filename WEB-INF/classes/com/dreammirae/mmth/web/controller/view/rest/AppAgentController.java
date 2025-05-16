/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.bean.AppAgentPolicy;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*     */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*     */ import com.dreammirae.mmth.web.service.AppAgentService;
/*     */ import com.dreammirae.mmth.web.service.ViewService;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/web/manager/appagent/rest"}, method = {RequestMethod.POST})
/*     */ public class AppAgentController
/*     */   extends ViewController<AppAgentVO, Integer>
/*     */ {
/*     */   @Autowired
/*     */   private AppAgentService service;
/*     */   
/*     */   @RequestMapping({"/policy"})
/*     */   public RestResponse policy() {
/*  31 */     RestResponse resp = new RestResponse();
/*     */     
/*  33 */     AppAgentPolicy policy = AppAgentDao.getAcceptAppAgent();
/*     */     
/*  35 */     if (policy != null && policy.getPolicy() != null && !policy.getPolicy().isEmpty()) {
/*  36 */       resp.addData("policy", policy);
/*     */     }
/*     */     
/*  39 */     return resp;
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
/*     */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  56 */     getPagination(resp, 1, null, null, null);
/*  57 */     getViewStats(resp);
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
/*     */   protected void detailsImp(RestResponse resp, Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  70 */     if (-1 == id.intValue()) {
/*  71 */       AppAgentVO vo = new AppAgentVO();
/*  72 */       vo.setOsType(AgentOsTypes.ANDROID);
/*  73 */       vo.setDisabled(DisabledStatus.ENABLED);
/*  74 */       resp.addData("details", vo);
/*     */       
/*     */       return;
/*     */     } 
/*  78 */     getDetails(resp, id);
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
/*     */   protected void saveImp(RestResponse resp, AppAgentVO vo, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  93 */     doSave(resp, (IRestValidator)vo, session);
/*     */     
/*  95 */     if (resp.getHasMessages()) {
/*     */       return;
/*     */     }
/*     */     
/*  99 */     getPagination(resp, 1, null, null, null);
/* 100 */     getViewStats(resp);
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
/*     */   protected void deleteImp(RestResponse resp, Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 115 */     doDelete(resp, id, session);
/*     */     
/* 117 */     getPagination(resp, 1, null, null, null);
/* 118 */     getViewStats(resp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AppAgentService getViewService() {
/* 129 */     if (this.service == null) {
/* 130 */       this.service = new AppAgentService();
/*     */     }
/*     */     
/* 133 */     return this.service;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\AppAgentController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */