/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.dreammirae.mmth.vo.IssuanceHistoryVO;
/*    */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*    */ import com.dreammirae.mmth.vo.bean.Pagination;
/*    */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*    */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*    */ import com.dreammirae.mmth.web.exception.I18nMessageException;
/*    */ import com.dreammirae.mmth.web.service.IssuanceHistoryService;
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
/*    */ @RestController
/*    */ @RequestMapping(value = {"/web/manager/issuance/rest"}, method = {RequestMethod.POST})
/*    */ public class IssuanceHistoryController
/*    */ {
/*    */   @Autowired
/*    */   private IssuanceHistoryService service;
/*    */   
/*    */   @RequestMapping({"/init"})
/*    */   public RestResponse init(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 32 */     RestResponse resp = new RestResponse();
/* 33 */     initImp(resp, session, request, response);
/* 34 */     return resp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RequestMapping({"/pageContents"})
/*    */   public RestResponse pageContents(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate, IssuanceHistoryVO searchParams) {
/* 43 */     RestResponse resp = new RestResponse();
/*    */     
/* 45 */     Long tsFrom = StringUtils.isEmpty(startDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(startDate).getMillis());
/* 46 */     Long tsTo = StringUtils.isEmpty(endDate) ? null : Long.valueOf(Commons.DISPLAY_DATE_FORMATTER.parseDateTime(endDate).plusDays(1).getMillis() - 1L);
/*    */     
/* 48 */     getPagination(resp, pageNum, searchParams, tsFrom, tsTo);
/*    */     
/* 50 */     return resp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initImp(RestResponse resp, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 56 */     Long tsTo = Long.valueOf(System.currentTimeMillis());
/* 57 */     Long tsFrom = Long.valueOf(SystemSettingsDao.getLong("application.since"));
/*    */     
/* 59 */     getPagination(resp, 1, null, tsFrom, tsTo);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void getPagination(RestResponse resp, int pageNum, IssuanceHistoryVO params, Long tsFrom, Long tsTo) {
/*    */     try {
/* 65 */       Pagination<IssuanceHistoryVO> pagination = getViewService().getPagination(pageNum, (IRestValidator)params, tsFrom, tsTo);
/* 66 */       resp.addData("pageContents", pagination);
/* 67 */     } catch (I18nMessageException e) {
/* 68 */       ViewController.setI18nErrorMessage(resp, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected IssuanceHistoryService getViewService() {
/* 74 */     if (this.service == null) {
/* 75 */       this.service = new IssuanceHistoryService();
/*    */     }
/* 77 */     return this.service;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\IssuanceHistoryController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */