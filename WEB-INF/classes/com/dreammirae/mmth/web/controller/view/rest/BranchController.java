/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.controller.view.rest;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.DataPurgeDao;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.bean.DataPurgeTableStats;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.web.controller.view.rest.ViewController;
/*     */ import com.dreammirae.mmth.web.service.BranchService;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.joda.time.DateTime;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping(value = {"/web/manager/branch/rest"}, method = {RequestMethod.POST})
/*     */ public class BranchController
/*     */ {
/*     */   public static final String CLAZZIFY_MENU_HWOTP_MANAGER = "HWOTP_MANAGER_MENU";
/*     */   public static final String CLAZZIFY_MENU_HWOTP_LOST = "HWOTP_LOST_MENU";
/*     */   public static final String CLAZZIFY_MENU_HWOTP_RESET_COUNT = "HWOTP_RESET_COUNT_MENU";
/*     */   public static final String CLAZZIFY_MENU_HWOTP_SYNC = "HWOTP_SYNC_MENU";
/*     */   public static final String CLAZZIFY_MENU_HWOTP_INFO = "HWOTP_INFO_MENU";
/*     */   @Autowired
/*     */   private BranchService service;
/*     */   
/*     */   @RequestMapping({"/init"})
/*     */   public RestResponse init() {
/*  41 */     return getSettingValues("HWOTP_MANAGER_MENU");
/*     */   }
/*     */   
/*     */   @RequestMapping({"/get/{clazzifySettings}"})
/*     */   public RestResponse getSettingValues(@PathVariable("clazzifySettings") String clazzifySettings) {
/*  46 */     RestResponse resp = new RestResponse();
/*  47 */     getSettingValueByClassified(resp, clazzifySettings);
/*  48 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/save/{clazzifySettings}"})
/*     */   public RestResponse saveSettingValues(@PathVariable("clazzifySettings") String clazzifySettings, @RequestParam Map<String, String> settingValues, HttpSession session) {
/*  55 */     RestResponse resp = new RestResponse();
/*     */     
/*  57 */     AdministratorVO sessionAdmin = ViewController.getSessionAdmin(session);
/*     */     
/*  59 */     if ("HWOTP_MANAGER_MENU".equals(clazzifySettings)) {
/*  60 */       this.service.saveApplicationSettings(resp, settingValues, sessionAdmin);
/*  61 */     } else if ("HWOTP_LOST_MENU".equals(clazzifySettings)) {
/*  62 */       this.service.saveIntegrateSettings(resp, settingValues, sessionAdmin);
/*     */     }
/*  64 */     else if ("HWOTP_RESET_COUNT_MENU".equals(clazzifySettings)) {
/*  65 */       this.service.saveAdvancedSettings(resp, settingValues, sessionAdmin);
/*     */       
/*  67 */       if (!resp.getHasMessages()) {
/*  68 */         resp.addGeneralMessage(new I18nMessage("common.desc.settingChangedSaved"));
/*     */       }
/*     */     } else {
/*  71 */       if ("HWOTP_SYNC_MENU".equals(clazzifySettings)) {
/*     */         
/*  73 */         this.service.saveCountryCodeSettings(resp, settingValues.get("customKeys_countryCode"), sessionAdmin);
/*     */         
/*  75 */         return resp;
/*     */       } 
/*  77 */       resp.addGeneralMessage(new I18nMessage("systemSettings.validate.invalidRequest"));
/*     */     } 
/*     */     
/*  80 */     if (resp.getHasMessages()) {
/*  81 */       return resp;
/*     */     }
/*     */     
/*  84 */     getSettingValueByClassified(resp, clazzifySettings);
/*     */     
/*  86 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/dataPurgeStats"})
/*     */   public RestResponse getDataPurgeStats(@RequestParam(value = "months", defaultValue = "3") Integer months, HttpSession session) {
/*  91 */     DateTime dt = (new DateTime()).withDayOfMonth(1).withMillisOfDay(0);
/*  92 */     DateTime baseDateTime = dt.minusMonths(months.intValue());
/*     */     
/*  94 */     DataPurgeTableStats stats = new DataPurgeTableStats();
/*  95 */     stats.setTsBase(Long.valueOf(baseDateTime.getMillis()));
/*     */ 
/*     */     
/*  98 */     DataPurgeDao.readPurgingData(stats, baseDateTime.getMillis());
/*  99 */     RestResponse response = new RestResponse();
/* 100 */     response.addData("purgeDataContents", stats);
/*     */ 
/*     */     
/* 103 */     return response;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/doDataPurge"})
/*     */   public RestResponse getDoDataPurge(@RequestParam(value = "months", defaultValue = "3") Integer months, HttpSession session) {
/* 108 */     DateTime dt = (new DateTime()).withDayOfMonth(1).withMillisOfDay(0);
/* 109 */     DateTime baseDateTime = dt.minusMonths(months.intValue());
/*     */     
/* 111 */     DataPurgeTableStats stats = new DataPurgeTableStats();
/* 112 */     stats.setTsBase(Long.valueOf(baseDateTime.getMillis()));
/*     */ 
/*     */     
/* 115 */     DataPurgeDao.doDataPurge(stats, baseDateTime.getMillis());
/* 116 */     RestResponse response = new RestResponse();
/* 117 */     response.addData("purgeDataContents", stats);
/*     */     
/* 119 */     AdministratorVO sessionAdmin = ViewController.getSessionAdmin(session);
/*     */     
/* 121 */     AuditAlarmTypes.SYSTEM_SETTING.raiseAlarm(sessionAdmin, 2053, AlarmLevels.URGENT, new Object[] { sessionAdmin
/* 122 */           .getUsername(), Commons.displayDateTime(baseDateTime.getMillis()), stats.getDeletedRows() });
/*     */     
/* 124 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void getSettingValueByClassified(RestResponse resp, String clazzifySettings) {
/* 130 */     if ("HWOTP_MANAGER_MENU".equals(clazzifySettings)) {
/* 131 */       this.service.getApplicationSettings(resp);
/* 132 */     } else if ("HWOTP_LOST_MENU".equals(clazzifySettings)) {
/* 133 */       this.service.getIntegrateSettings(resp);
/* 134 */     } else if ("HWOTP_SYNC_MENU".equals(clazzifySettings)) {
/* 135 */       this.service.getCountryCodeSettings(resp);
/* 136 */     } else if ("HWOTP_RESET_COUNT_MENU".equals(clazzifySettings)) {
/* 137 */       this.service.getCountryCodeSettings(resp);
/* 138 */     } else if ("HWOTP_INFO_MENU".equals(clazzifySettings)) {
/* 139 */       this.service.getCountryCodeSettings(resp);
/*     */     } else {
/* 141 */       resp.addGeneralMessage(new I18nMessage("systemSettings.validate.invalidRequest"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\BranchController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */