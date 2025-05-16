/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.vo.AuditAlarmVO;
/*    */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*    */ import com.dreammirae.mmth.vo.bean.IViewStatsLocator;
/*    */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*    */ import com.dreammirae.mmth.vo.types.AckCodes;
/*    */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*    */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*    */ import com.dreammirae.mmth.web.service.AuditAlarmService;
/*    */ import com.dreammirae.mmth.web.service.ViewService;
/*    */ import java.util.List;
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
/*    */ @RestController
/*    */ @RequestMapping(value = {"/web/manager/audit/rest"}, method = {RequestMethod.POST})
/*    */ public class AuditAlarmController
/*    */   extends ViewController<AuditAlarmVO, Long>
/*    */ {
/*    */   @Autowired
/*    */   private AuditAlarmService service;
/*    */   
/*    */   @RequestMapping({"/acknowledgeAlarms"})
/*    */   public RestResponse acknowledgeAlarms(@RequestParam("ids[]") long[] ids, HttpSession session) {
/* 36 */     RestResponse resp = new RestResponse();
/*    */     
/*    */     try {
/* 39 */       IViewStatsLocator loc = getViewService().acknowledgeAlarms(ids, getSessionAdmin(session));
/* 40 */       resp.addData("noAckCnt", loc);
/* 41 */       resp.addData("latest", getViewService().getLatestList(10));
/*    */     }
/* 43 */     catch (I18nMessageException e) {
/* 44 */       setI18nErrorMessage(resp, e);
/*    */     } 
/*    */     
/* 47 */     return resp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 55 */     Long tsTo = Long.valueOf(System.currentTimeMillis());
/* 56 */     Long tsFrom = Long.valueOf(SystemSettingsDao.getLong("application.since"));
/*    */ 
/*    */     
/* 59 */     AuditAlarmVO vo = new AuditAlarmVO();
/* 60 */     vo.setAck(AckCodes.NONE);
/*    */     
/* 62 */     getPagination(resp, 1, (IRestValidator)vo, tsFrom, tsTo);
/*    */     
/* 64 */     List<AuditAlarmVO> list = getViewService().getLatestList(10);
/* 65 */     if (list != null && !list.isEmpty()) {
/* 66 */       resp.addData("latest", list);
/*    */     }
/*    */     
/* 69 */     IViewStatsLocator loc = getViewService().getViewStatsLocator();
/* 70 */     resp.addData("noAckCnt", loc);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void detailsImp(RestResponse resp, Long id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 76 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveImp(RestResponse resp, AuditAlarmVO vo, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 82 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void deleteImp(RestResponse resp, Long id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 88 */     resp.addGeneralMessage(new I18nMessage("validate.notSupprted"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AuditAlarmService getViewService() {
/* 95 */     if (this.service == null) {
/* 96 */       this.service = new AuditAlarmService();
/*    */     }
/* 98 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\AuditAlarmController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */