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
/*     */ import com.dreammirae.mmth.web.service.SystemSettingsService;
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
/*     */ @RequestMapping(value = {"/web/manager/systemSettings/rest"}, method = {RequestMethod.POST})
/*     */ public class SystemSettingsController
/*     */ {
/*     */   public static final String CLAZZIFY_SETTINGS_APPLICATION = "APPLICATION";
/*     */   public static final String CLAZZIFY_SETTINGS_INTEGRATE_SETTINGS = "INTEGRATE_SETTINGS";
/*     */   public static final String CLAZZIFY_SETTINGS_FIDO_SETTINGS = "FIDO_SETTINGS";
/*     */   public static final String CLAZZIFY_SETTINGS_BIOTP_SETTINGS = "BIOTP_SETTINGS";
/*     */   public static final String CLAZZIFY_SETTINGS_HWOTP_SETTINGS = "HWOTP_SETTINGS";
/*     */   public static final String CLAZZIFY_SETTINGS_SAOTP_SETTINGS = "SAOTP_SETTINGS";
/*     */   public static final String CLAZZIFY_SETTINGS_EXTERNAL_API_SETTINGS = "EXTERNAL_API_SETTINGS";
/*     */   public static final String CLAZZIFY_SETTINGS_ADVANCED_SETTINGS = "ADVANCED_SETTINGS";
/*     */   public static final String CLAZZIFY_SETTINGS_COUNTRYCODE_SETTINGS = "COUNTRYCODE_SETTINGS";
/*     */   @Autowired
/*     */   private SystemSettingsService service;
/*     */   
/*     */   @RequestMapping({"/init"})
/*     */   public RestResponse init() {
/*  45 */     return getSettingValues("APPLICATION");
/*     */   }
/*     */   
/*     */   @RequestMapping({"/get/{clazzifySettings}"})
/*     */   public RestResponse getSettingValues(@PathVariable("clazzifySettings") String clazzifySettings) {
/*  50 */     RestResponse resp = new RestResponse();
/*  51 */     getSettingValueByClassified(resp, clazzifySettings);
/*  52 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/save/{clazzifySettings}"})
/*     */   public RestResponse saveSettingValues(@PathVariable("clazzifySettings") String clazzifySettings, @RequestParam Map<String, String> settingValues, HttpSession session) {
/*  59 */     RestResponse resp = new RestResponse();
/*     */     
/*  61 */     AdministratorVO sessionAdmin = ViewController.getSessionAdmin(session);
/*     */     
/*  63 */     if ("APPLICATION".equals(clazzifySettings)) {
/*  64 */       this.service.saveApplicationSettings(resp, settingValues, sessionAdmin);
/*  65 */     } else if ("INTEGRATE_SETTINGS".equals(clazzifySettings)) {
/*  66 */       this.service.saveIntegrateSettings(resp, settingValues, sessionAdmin);
/*  67 */     } else if ("FIDO_SETTINGS".equals(clazzifySettings)) {
/*  68 */       this.service.saveFidoSettings(resp, settingValues, sessionAdmin);
/*  69 */     } else if ("BIOTP_SETTINGS".equals(clazzifySettings)) {
/*  70 */       this.service.saveBiotpSettings(resp, settingValues, sessionAdmin);
/*  71 */     } else if ("HWOTP_SETTINGS".equals(clazzifySettings)) {
/*  72 */       this.service.saveHwOTPSettings(resp, settingValues, sessionAdmin);
/*  73 */     } else if ("SAOTP_SETTINGS".equals(clazzifySettings)) {
/*  74 */       this.service.saveSaotpStetings(resp, settingValues, sessionAdmin);
/*  75 */     } else if ("EXTERNAL_API_SETTINGS".equals(clazzifySettings)) {
/*  76 */       this.service.saveExternalApiSettings(resp, settingValues, sessionAdmin);
/*     */       
/*  78 */       if (!resp.getHasMessages()) {
/*  79 */         resp.addGeneralMessage(new I18nMessage("common.desc.settingChangedSaved"));
/*     */       }
/*     */     }
/*  82 */     else if ("ADVANCED_SETTINGS".equals(clazzifySettings)) {
/*  83 */       this.service.saveAdvancedSettings(resp, settingValues, sessionAdmin);
/*     */       
/*  85 */       if (!resp.getHasMessages()) {
/*  86 */         resp.addGeneralMessage(new I18nMessage("common.desc.settingChangedSaved"));
/*     */       }
/*     */     } else {
/*  89 */       if ("COUNTRYCODE_SETTINGS".equals(clazzifySettings)) {
/*     */         
/*  91 */         this.service.saveCountryCodeSettings(resp, settingValues.get("customKeys_countryCode"), sessionAdmin);
/*     */         
/*  93 */         return resp;
/*     */       } 
/*  95 */       resp.addGeneralMessage(new I18nMessage("systemSettings.validate.invalidRequest"));
/*     */     } 
/*     */     
/*  98 */     if (resp.getHasMessages()) {
/*  99 */       return resp;
/*     */     }
/*     */     
/* 102 */     getSettingValueByClassified(resp, clazzifySettings);
/*     */     
/* 104 */     return resp;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/dataPurgeStats"})
/*     */   public RestResponse getDataPurgeStats(@RequestParam(value = "months", defaultValue = "3") Integer months, HttpSession session) {
/* 109 */     DateTime dt = (new DateTime()).withDayOfMonth(1).withMillisOfDay(0);
/* 110 */     DateTime baseDateTime = dt.minusMonths(months.intValue());
/*     */     
/* 112 */     DataPurgeTableStats stats = new DataPurgeTableStats();
/* 113 */     stats.setTsBase(Long.valueOf(baseDateTime.getMillis()));
/*     */ 
/*     */     
/* 116 */     DataPurgeDao.readPurgingData(stats, baseDateTime.getMillis());
/* 117 */     RestResponse response = new RestResponse();
/* 118 */     response.addData("purgeDataContents", stats);
/*     */ 
/*     */     
/* 121 */     return response;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/doDataPurge"})
/*     */   public RestResponse getDoDataPurge(@RequestParam(value = "months", defaultValue = "3") Integer months, HttpSession session) {
/* 126 */     DateTime dt = (new DateTime()).withDayOfMonth(1).withMillisOfDay(0);
/* 127 */     DateTime baseDateTime = dt.minusMonths(months.intValue());
/*     */     
/* 129 */     DataPurgeTableStats stats = new DataPurgeTableStats();
/* 130 */     stats.setTsBase(Long.valueOf(baseDateTime.getMillis()));
/*     */ 
/*     */     
/* 133 */     DataPurgeDao.doDataPurge(stats, baseDateTime.getMillis());
/* 134 */     RestResponse response = new RestResponse();
/* 135 */     response.addData("purgeDataContents", stats);
/*     */     
/* 137 */     AdministratorVO sessionAdmin = ViewController.getSessionAdmin(session);
/*     */     
/* 139 */     AuditAlarmTypes.SYSTEM_SETTING.raiseAlarm(sessionAdmin, 2053, AlarmLevels.URGENT, new Object[] { sessionAdmin
/* 140 */           .getUsername(), Commons.displayDateTime(baseDateTime.getMillis()), stats.getDeletedRows() });
/*     */     
/* 142 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void getSettingValueByClassified(RestResponse resp, String clazzifySettings) {
/* 148 */     if ("APPLICATION".equals(clazzifySettings)) {
/* 149 */       this.service.getApplicationSettings(resp);
/* 150 */     } else if ("INTEGRATE_SETTINGS".equals(clazzifySettings)) {
/* 151 */       this.service.getIntegrateSettings(resp);
/* 152 */     } else if ("FIDO_SETTINGS".equals(clazzifySettings)) {
/* 153 */       this.service.getFidoSettings(resp);
/* 154 */     } else if ("BIOTP_SETTINGS".equals(clazzifySettings)) {
/* 155 */       this.service.getBiotpSettings(resp);
/* 156 */     } else if ("HWOTP_SETTINGS".equals(clazzifySettings)) {
/* 157 */       this.service.getHwOTPSettings(resp);
/* 158 */     } else if ("SAOTP_SETTINGS".equals(clazzifySettings)) {
/* 159 */       this.service.getSaotpSettings(resp);
/* 160 */     } else if ("ADVANCED_SETTINGS".equals(clazzifySettings)) {
/* 161 */       this.service.getAdvancedSettings(resp);
/* 162 */     } else if ("EXTERNAL_API_SETTINGS".equals(clazzifySettings)) {
/* 163 */       this.service.getExteranlApiSettings(resp);
/* 164 */     } else if ("COUNTRYCODE_SETTINGS".equals(clazzifySettings)) {
/* 165 */       this.service.getCountryCodeSettings(resp);
/*     */     } else {
/* 167 */       resp.addGeneralMessage(new I18nMessage("systemSettings.validate.invalidRequest"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\view\rest\SystemSettingsController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */