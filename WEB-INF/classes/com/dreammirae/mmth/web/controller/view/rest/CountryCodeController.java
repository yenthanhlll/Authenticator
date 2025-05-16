/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.db.dao.CountryCodeDao;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.dreammirae.mmth.vo.bean.CountryStatsLocator;
/*    */ import com.dreammirae.mmth.vo.bean.Pagination;
/*    */ import com.dreammirae.mmth.vo.bean.RestResponse;
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
/*    */ 
/*    */ 
/*    */ @RestController
/*    */ @RequestMapping(value = {"/web/manager/countrycode/rest"}, method = {RequestMethod.POST})
/*    */ public class CountryCodeController
/*    */ {
/*    */   @Autowired
/*    */   private CountryCodeDao countryCodeDao;
/*    */   
/*    */   @RequestMapping({"/init"})
/*    */   public RestResponse init(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/* 33 */     RestResponse resp = new RestResponse();
/* 34 */     setStatsData(resp, Long.valueOf(SystemSettingsDao.getLong("application.since")), Long.valueOf(System.currentTimeMillis()));
/* 35 */     return resp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @RequestMapping({"/search"})
/*    */   public RestResponse pageContents(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
/* 42 */     RestResponse resp = new RestResponse();
/* 43 */     Long tsStart = Long.valueOf(StringUtils.isEmpty(startDate) ? SystemSettingsDao.getLong("application.since") : Commons.DISPLAY_DATE_FORMATTER.parseDateTime(startDate).getMillis());
/* 44 */     Long tsEnd = Long.valueOf(StringUtils.isEmpty(endDate) ? System.currentTimeMillis() : (Commons.DISPLAY_DATE_FORMATTER.parseDateTime(endDate).plusDays(1).getMillis() - 1L));
/* 45 */     setStatsData(resp, tsStart, tsEnd);
/* 46 */     return resp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void setStatsData(RestResponse resp, Long tsStart, Long tsEnd) {
/* 52 */     List<CountryStatsLocator> result = this.countryCodeDao.getStatsByCountry(tsStart, tsEnd);
/* 53 */     Pagination<CountryStatsLocator> page = new Pagination(result.size(), 0);
/* 54 */     page.setContents(result);
/* 55 */     page.setTsFrom(tsStart);
/* 56 */     page.setTsTo(tsEnd);
/* 57 */     resp.addData("result", page);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\CountryCodeController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */