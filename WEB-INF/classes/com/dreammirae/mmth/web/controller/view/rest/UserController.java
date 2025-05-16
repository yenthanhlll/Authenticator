/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.db.dao.HwTokenDao;
/*     */ import com.dreammirae.mmth.db.dao.ServiceLogDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.dao.UserDeviceDao;
/*     */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.vo.HwTokenVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*     */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*     */ import com.dreammirae.mmth.web.service.UserService;
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
/*     */ @RestController
/*     */ @RequestMapping(value = {"/web/manager/user/rest"}, method = {RequestMethod.POST})
/*     */ public class UserController
/*     */   extends ViewController<UserVO, Integer>
/*     */ {
/*     */   @Autowired
/*     */   private UserService service;
/*     */   @Autowired
/*     */   private ServiceLogDao serviceLogDao;
/*     */   @Autowired
/*     */   private UserDeviceDao userDeviceDao;
/*     */   @Autowired
/*     */   private HwTokenDao hwTokenDao;
/*     */   
/*     */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  57 */     getPagination(resp, 1, null, null, null);
/*     */     
/*  59 */     if (!ExternalWebApiTypes.GLOBAL_WIBEE.equals(SystemSettingsDao.getWebApiPolicy())) {
/*  60 */       getViewStats(resp);
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
/*     */   protected void detailsImp(RestResponse resp, Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*     */     try {
/*  76 */       UserVO user = (UserVO)getViewService().getDetails(id);
/*  77 */       HwTokenVO hwvo = new HwTokenVO();
/*  78 */       hwvo.setUserId(user.getId());
/*  79 */       hwvo.setUsername(user.getUsername());
/*  80 */       resp.addData("details", user);
/*  81 */       resp.addData("devices", this.userDeviceDao.getUserDevice(user));
/*  82 */       resp.addData("biotpLogs", this.serviceLogDao.getServiceLogForOperation(user, AuthMethodTypes.BIOTP, 10));
/*  83 */       resp.addData("fidoLogs", this.serviceLogDao.getServiceLogForOperation(user, AuthMethodTypes.FIDO, 10));
/*  84 */       resp.addData("matrixLogs", this.serviceLogDao.getServiceLogForOperation(user, AuthMethodTypes.MATRIX, 10));
/*  85 */       resp.addData("hwotpLogs", this.serviceLogDao.getServiceLogForOperation(user, AuthMethodTypes.HWOTP, 10));
/*  86 */       resp.addData("hwtokens", this.hwTokenDao.getOneByObj(hwvo));
/*     */     }
/*  88 */     catch (I18nMessageException e) {
/*  89 */       setI18nErrorMessage(resp, e);
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
/*     */   protected void saveImp(RestResponse resp, UserVO vo, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 105 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
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
/*     */   protected void deleteImp(RestResponse resp, Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 119 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
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
/*     */   protected UserService getViewService() {
/* 132 */     if (this.service == null) {
/* 133 */       this.service = new UserService();
/*     */     }
/*     */     
/* 136 */     return this.service;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\UserController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */