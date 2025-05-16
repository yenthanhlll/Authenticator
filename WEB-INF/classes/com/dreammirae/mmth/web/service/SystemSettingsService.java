/*      */ package WEB-INF.classes.com.dreammirae.mmth.web.service;
/*      */ 
/*      */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*      */ import com.dreammirae.mmth.authentication.policy.AuthFailActionPolicy;
/*      */ import com.dreammirae.mmth.authentication.policy.AuthManagementScope;
/*      */ import com.dreammirae.mmth.authentication.policy.BiotpPayloadParser;
/*      */ import com.dreammirae.mmth.authentication.policy.FidoPayloadParser;
/*      */ import com.dreammirae.mmth.authentication.policy.MultiDevicePolicy;
/*      */ import com.dreammirae.mmth.authentication.policy.SaotpPayloadParser;
/*      */ import com.dreammirae.mmth.authentication.policy.TokenSharePolicy;
/*      */ import com.dreammirae.mmth.config.Commons;
/*      */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*      */ import com.dreammirae.mmth.exception.InternalDBException;
/*      */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*      */ import com.dreammirae.mmth.fido.handler.supporter.AppIDPolicy;
/*      */ import com.dreammirae.mmth.hwotp.service.HwOtpAuthenticationService;
/*      */ import com.dreammirae.mmth.misc.I18nMessage;
/*      */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*      */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*      */ import com.dreammirae.mmth.runtime.code.TimePeriodTypes;
/*      */ import com.dreammirae.mmth.util.IPv4AddressUtils;
/*      */ import com.dreammirae.mmth.util.StringUtils;
/*      */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*      */ import com.dreammirae.mmth.vo.AdministratorVO;
/*      */ import com.dreammirae.mmth.vo.HwTokenPolicyVO;
/*      */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*      */ import com.dreammirae.mmth.vo.types.SettingEnabled;
/*      */ import java.io.File;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.commons.io.LineIterator;
/*      */ import org.quartz.CronExpression;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.stereotype.Service;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Service("systemSettingsService")
/*      */ public class SystemSettingsService
/*      */ {
/*      */   private static final String DELIMITER_PERIODS_CTX_KEY = "_";
/*      */   @Autowired
/*      */   private HwOtpAuthenticationService hwotpService;
/*      */   private static final String DELIMITER_DB_KEY = "\\.";
/*      */   private static final String DELIMITER_CONTEXT_KEY = "_";
/*      */   private static final String DELIMITER_DB_KEY_DOT = ".";
/*      */   
/*      */   public void getApplicationSettings(RestResponse resp) {
/*   62 */     setSettingData(resp, SystemSettingsDao.ApplicationSettingKeys.VIEW_KEYS);
/*   63 */     resp.addContextData("application_currentLocalAddress", Arrays.toString((Object[])IPv4AddressUtils.myIps()));
/*   64 */     resp.addContextData("application_since", Commons.displayDateTime(SystemSettingsDao.getLong("application.since")));
/*      */   }
/*      */   
/*      */   public void getIntegrateSettings(RestResponse resp) {
/*   68 */     setSettingData(resp, SystemSettingsDao.IntegrateSettingKeys.VIEW_KEYS);
/*      */   }
/*      */   
/*      */   public void getFidoSettings(RestResponse resp) {
/*   72 */     setSettingData(resp, SystemSettingsDao.FidoAuthSettingKeys.VIEW_KEYS);
/*      */   }
/*      */   
/*      */   public void getBiotpSettings(RestResponse resp) {
/*   76 */     setSettingData(resp, SystemSettingsDao.BiotpAuthSettingKeys.VIEW_KEYS);
/*      */   }
/*      */   
/*      */   public void getHwOTPSettings(RestResponse resp) {
/*   80 */     HwTokenPolicyVO policy = this.hwotpService.policyInfo();
/*   81 */     if (policy == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*   86 */     resp.addContextData("name", policy.getName());
/*   87 */     resp.addContextData("normAuthTmSkew", policy.getNormAuthTmSkew());
/*   88 */     resp.addContextData("adminSyncTmSkew", policy.getAdminSyncTmSkew());
/*   89 */     resp.addContextData("userSyncTmSkew", policy.getUserSyncTmSkew());
/*   90 */     resp.addContextData("chgDate(", Long.valueOf(policy.getChgDate()));
/*   91 */     resp.addContextData("maxAuthFailCnt", policy.getMaxAuthFailCnt());
/*   92 */     resp.addContextData("initAuthTmSkew", policy.getInitAuthTmSkew());
/*      */   }
/*      */   
/*      */   public void getSaotpSettings(RestResponse resp) {
/*   96 */     setSettingData(resp, SystemSettingsDao.SaotpAuthSettingKeys.VIEW_KEYS);
/*      */   }
/*      */   
/*      */   public void getAdvancedSettings(RestResponse resp) {
/*  100 */     setSettingData(resp, SystemSettingsDao.AdvancedSettingKeys.VIEW_KEYS);
/*      */   }
/*      */   
/*      */   public void getExteranlApiSettings(RestResponse resp) {
/*  104 */     setSettingData(resp, SystemSettingsDao.ExternalApiSettingKeys.VIEW_KEYS);
/*      */   }
/*      */   
/*      */   public void getCountryCodeSettings(RestResponse resp) {
/*  108 */     resp.addData(dbkeyToCtxKey("customKeys.countryCode"), SystemSettingsDao.getCountryCodeRegistered());
/*  109 */     resp.addData("countryCodes_all", getAllCountryCodes());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveApplicationSettings(RestResponse resp, Map<String, String> settingValues, AdministratorVO sessionAdmin) {
/*  115 */     validateApplicationFields(resp, settingValues);
/*      */     
/*  117 */     if (resp.getHasMessages()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  123 */       saveSimpleValue(settingValues, sessionAdmin, "application.instanceName", "systemSettings.application.instanceName");
/*      */ 
/*      */       
/*  126 */       saveSimpleValue(settingValues, sessionAdmin, "application.sysLanguage", "systemSettings.application.sysLanguage");
/*      */ 
/*      */       
/*  129 */       saveSimpleValue(settingValues, sessionAdmin, "application.ipWhitelist", "systemSettings.application.ipWhitelist");
/*      */ 
/*      */       
/*  132 */       String value = settingValues.get(dbkeyToCtxKey("application.sessionTimeout"));
/*      */       
/*  134 */       if (StringUtils.isEmpty(value)) {
/*      */         return;
/*      */       }
/*      */       
/*  138 */       String prevVal = SystemSettingsDao.getValue("application.sessionTimeout");
/*      */       
/*  140 */       if (!prevVal.equals(value)) {
/*  141 */         SystemSettingsDao.setValue("application.sessionTimeout", value);
/*  142 */         AuditAlarmTypes.SYSTEM_SETTING.raiseAlarm(sessionAdmin, 2050, AlarmLevels.URGENT, new Object[] { sessionAdmin.getUsername(), new I18nMessage("systemSettings.application.sessionTimeout"), prevVal, new I18nMessage(TimePeriodTypes.MINUTE
/*  143 */                 .getMessageKey()), value, new I18nMessage(TimePeriodTypes.MINUTE
/*  144 */                 .getMessageKey()) });
/*      */       }
/*      */     
/*  147 */     } catch (InternalDBException e) {
/*  148 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveIntegrateSettings(RestResponse resp, Map<String, String> settingValues, AdministratorVO sessionAdmin) {
/*  154 */     validateIntegrateSettings(resp, settingValues);
/*      */     
/*  156 */     if (resp.getHasMessages()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  162 */       savePeriods(settingValues, sessionAdmin, "integrate.authLifetimePeriodType", "integrate.authLifetimePeriods", "systemSettings.integrate.authLifetimePeriods");
/*      */ 
/*      */       
/*  165 */       savePolicyValue(settingValues, sessionAdmin, "integrate.authMgmtScope", "systemSettings.integrate.authMgmtScope");
/*      */ 
/*      */       
/*  168 */       saveSimpleValue(settingValues, sessionAdmin, "integrate.authMaxFailCount", "systemSettings.integrate.authMaxFailCount");
/*      */       
/*  170 */       saveSimpleValue(settingValues, sessionAdmin, "integrate.pinMaxFailCount", "systemSettings.integrate.pinMaxFailCount");
/*      */       
/*  172 */       saveSimpleValue(settingValues, sessionAdmin, "integrate.biotpMaxFailCount", "systemSettings.integrate.biotpMaxFailCount");
/*      */ 
/*      */       
/*  175 */       savePeriods(settingValues, sessionAdmin, "integrate.checkAuthResultLifetimePeriodType", "integrate.checkAuthResultLifetimePeriods", "systemSettings.integrate.checkAuthResultLifetimePeriods");
/*      */ 
/*      */       
/*  178 */       saveSimpleValue(settingValues, sessionAdmin, "integrate.rpServerUri", "systemSettings.integrate.rpServerUri");
/*      */ 
/*      */       
/*  181 */       saveSimpleValue(settingValues, sessionAdmin, "integrate.trustedFacetUrl", "systemSettings.integrate.trustedFacetUrl");
/*      */ 
/*      */       
/*  184 */       savePeriods(settingValues, sessionAdmin, "integrate.issueCodeLifetimePeriodType", "integrate.issueCodeLifetimePeriods", "systemSettings.integrate.issueCodeLifetimePeriods");
/*      */ 
/*      */       
/*  187 */       saveSimpleValue(settingValues, sessionAdmin, "integrate.issueCodeMaxFailCount", "systemSettings.integrate.issueCodeMaxFailCount");
/*      */ 
/*      */       
/*  190 */       saveSimpleValue(settingValues, sessionAdmin, "integrate.tokenThresholdCnt", "systemSettings.integrate.tokenThresholdCnt");
/*      */     }
/*  192 */     catch (InternalDBException e) {
/*  193 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void saveFidoSettings(RestResponse resp, Map<String, String> settingValues, AdministratorVO sessionAdmin) {
/*  198 */     validateFidoSettings(resp, settingValues);
/*      */     
/*  200 */     if (resp.getHasMessages()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  207 */       saveServicePolicyValue(settingValues, sessionAdmin, "fido.appIdPolicy", "systemSettings.fido.appIdPolicy", AuthMethodTypes.FIDO);
/*      */ 
/*      */       
/*  210 */       saveServicePolicyValue(settingValues, sessionAdmin, "fido.valdiateChannelBindingEnabled", "systemSettings.fido.valdiateChannelBindingEnabled", AuthMethodTypes.FIDO);
/*      */ 
/*      */       
/*  213 */       saveServicePolicyValue(settingValues, sessionAdmin, "fido.multiDevicePolicy", "systemSettings.multiDevicePolicy", AuthMethodTypes.FIDO);
/*      */ 
/*      */       
/*  216 */       saveServicePolicyValue(settingValues, sessionAdmin, "fido.multiAppEnabled", "systemSettings.multiAppEnabled", AuthMethodTypes.FIDO);
/*      */ 
/*      */       
/*  219 */       saveServicePolicyValue(settingValues, sessionAdmin, "fido.authFailActionPolicy", "systemSettings.authFailActionPolicy", AuthMethodTypes.FIDO);
/*      */ 
/*      */       
/*  222 */       saveServicePolicyValue(settingValues, sessionAdmin, "fido.overrideAutoDeregEnabled", "systemSettings.overrideAutoDeregEnabled", AuthMethodTypes.FIDO);
/*      */ 
/*      */       
/*  225 */       saveServicePolicyValue(settingValues, sessionAdmin, "fido.issueCodeEnabled", "systemSettings.issueCodeEnabled", AuthMethodTypes.FIDO);
/*      */ 
/*      */       
/*  228 */       saveServicePolicyValue(settingValues, sessionAdmin, "fido.issuranceExpiredEnabled", "systemSettings.issuranceExpiredEnabled", AuthMethodTypes.FIDO);
/*      */ 
/*      */       
/*  231 */       savePeriods(settingValues, sessionAdmin, "fido.issuranceExpiredPeriodType", "fido.issuranceExpiredPeriods", "systemSettings.issuranceExpiredPeriods");
/*      */     }
/*  233 */     catch (InternalDBException e) {
/*  234 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveBiotpSettings(RestResponse resp, Map<String, String> settingValues, AdministratorVO sessionAdmin) {
/*  240 */     validateBiotpSettings(resp, settingValues);
/*      */     
/*  242 */     if (resp.getHasMessages()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  249 */       saveServicePolicyValue(settingValues, sessionAdmin, "biotp.appIdPolicy", "systemSettings.biotp.appIdPolicy", AuthMethodTypes.BIOTP);
/*      */ 
/*      */       
/*  252 */       saveServicePolicyValue(settingValues, sessionAdmin, "biotp.valdiateChannelBindingEnabled", "systemSettings.biotp.valdiateChannelBindingEnabled", AuthMethodTypes.BIOTP);
/*      */ 
/*      */       
/*  255 */       saveServicePolicyValue(settingValues, sessionAdmin, "biotp.multiDevicePolicy", "systemSettings.multiDevicePolicy", AuthMethodTypes.BIOTP);
/*      */ 
/*      */       
/*  258 */       saveServicePolicyValue(settingValues, sessionAdmin, "biotp.multiAppEnabled", "systemSettings.multiAppEnabled", AuthMethodTypes.BIOTP);
/*      */ 
/*      */       
/*  261 */       saveServicePolicyValue(settingValues, sessionAdmin, "biotp.authFailActionPolicy", "systemSettings.authFailActionPolicy", AuthMethodTypes.BIOTP);
/*      */ 
/*      */       
/*  264 */       saveServicePolicyValue(settingValues, sessionAdmin, "biotp.overrideAutoDeregEnabled", "systemSettings.overrideAutoDeregEnabled", AuthMethodTypes.BIOTP);
/*      */ 
/*      */       
/*  267 */       saveServicePolicyValue(settingValues, sessionAdmin, "biotp.issueCodeEnabled", "systemSettings.issueCodeEnabled", AuthMethodTypes.BIOTP);
/*      */ 
/*      */       
/*  270 */       saveServicePolicyValue(settingValues, sessionAdmin, "biotp.tokenSharePolicy", "systemSettings.biotp.tokenSharePolicy", AuthMethodTypes.BIOTP);
/*      */ 
/*      */       
/*  273 */       saveServicePolicyValue(settingValues, sessionAdmin, "biotp.issuranceExpiredEnabled", "systemSettings.issuranceExpiredEnabled", AuthMethodTypes.BIOTP);
/*      */ 
/*      */       
/*  276 */       savePeriods(settingValues, sessionAdmin, "biotp.issuranceExpiredPeriodType", "biotp.issuranceExpiredPeriods", "systemSettings.issuranceExpiredPeriods");
/*      */     }
/*  278 */     catch (InternalDBException e) {
/*  279 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveHwOTPSettings(RestResponse resp, Map<String, String> settingValues, AdministratorVO sessionAdmin) {
/*  285 */     HwTokenPolicyVO policy = new HwTokenPolicyVO();
/*  286 */     policy.setName(settingValues.get("name"));
/*  287 */     policy.setAdminSyncTmSkew(Integer.valueOf(settingValues.get("adminSyncTmSkew")));
/*  288 */     policy.setUserSyncTmSkew(Integer.valueOf(settingValues.get("userSyncTmSkew")));
/*  289 */     policy.setNormAuthTmSkew(Integer.valueOf(settingValues.get("normAuthTmSkew")));
/*  290 */     policy.setInitAuthTmSkew(Integer.valueOf(settingValues.get("initAuthTmSkew")));
/*  291 */     policy.setMaxAuthFailCnt(Integer.valueOf(settingValues.get("maxAuthFailCnt")));
/*      */     
/*  293 */     boolean result = this.hwotpService.setPolicy(policy);
/*      */     
/*  295 */     if (!result) {
/*  296 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { Boolean.valueOf(result) }));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveSaotpStetings(RestResponse resp, Map<String, String> settingValues, AdministratorVO sessionAdmin) {
/*  302 */     validateSaotpSettings(resp, settingValues);
/*      */     
/*  304 */     if (resp.getHasMessages()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  311 */       saveServicePolicyValue(settingValues, sessionAdmin, "saotp.serverPinEnabled", "systemSettings.saotp.serverPinEnabled", AuthMethodTypes.SAOTP);
/*      */ 
/*      */       
/*  314 */       saveSimpleValue(settingValues, sessionAdmin, "saotp.serverPinMaxFailCnt", "systemSettings.saotp.serverPinMaxFailCnt");
/*      */ 
/*      */       
/*  317 */       saveServicePolicyValue(settingValues, sessionAdmin, "saotp.multiDevicePolicy", "systemSettings.multiDevicePolicy", AuthMethodTypes.SAOTP);
/*      */ 
/*      */       
/*  320 */       saveServicePolicyValue(settingValues, sessionAdmin, "saotp.multiAppEnabled", "systemSettings.multiAppEnabled", AuthMethodTypes.SAOTP);
/*      */ 
/*      */       
/*  323 */       saveServicePolicyValue(settingValues, sessionAdmin, "saotp.authFailActionPolicy", "systemSettings.authFailActionPolicy", AuthMethodTypes.SAOTP);
/*      */ 
/*      */       
/*  326 */       saveServicePolicyValue(settingValues, sessionAdmin, "saotp.overrideAutoDeregEnabled", "systemSettings.overrideAutoDeregEnabled", AuthMethodTypes.SAOTP);
/*      */ 
/*      */       
/*  329 */       saveServicePolicyValue(settingValues, sessionAdmin, "saotp.issueCodeEnabled", "systemSettings.issueCodeEnabled", AuthMethodTypes.SAOTP);
/*      */ 
/*      */       
/*  332 */       saveServicePolicyValue(settingValues, sessionAdmin, "saotp.tokenSharePolicy", "systemSettings.biotp.tokenSharePolicy", AuthMethodTypes.SAOTP);
/*      */ 
/*      */       
/*  335 */       saveServicePolicyValue(settingValues, sessionAdmin, "saotp.issuranceExpiredEnabled", "systemSettings.issuranceExpiredEnabled", AuthMethodTypes.SAOTP);
/*      */ 
/*      */       
/*  338 */       savePeriods(settingValues, sessionAdmin, "saotp.issuranceExpiredPeriodType", "saotp.issuranceExpiredPeriods", "systemSettings.issuranceExpiredPeriods");
/*      */     }
/*  340 */     catch (InternalDBException e) {
/*  341 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveExternalApiSettings(RestResponse resp, Map<String, String> settingValues, AdministratorVO sessionAdmin) {
/*  347 */     validateExternalApiSettings(resp, settingValues);
/*      */     
/*  349 */     if (resp.getHasMessages()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  355 */       saveSimpleValue(settingValues, sessionAdmin, "externalApi.serviceType", "systemSettings.externalApi.serviceType");
/*      */ 
/*      */       
/*  358 */       saveSimpleValue(settingValues, sessionAdmin, "externalApi.serivceAddress", "systemSettings.externalApi.serivceAddress");
/*      */ 
/*      */       
/*  361 */       saveSimpleValue(settingValues, sessionAdmin, "externalApi.transactionLifeTimePeriods", "systemSettings.externalApi.transactionLifeTimePeriods");
/*      */     }
/*  363 */     catch (InternalDBException e) {
/*  364 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveAdvancedSettings(RestResponse resp, Map<String, String> settingValues, AdministratorVO sessionAdmin) {
/*  370 */     validateAdvancedSettings(resp, settingValues);
/*      */     
/*  372 */     if (resp.getHasMessages()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  379 */       savePolicyValue(settingValues, sessionAdmin, "advanced.fidoEnabled", "systemSettings.advanced.fidoEnabled");
/*      */ 
/*      */       
/*  382 */       savePolicyValue(settingValues, sessionAdmin, "advanced.biotpEnabled", "systemSettings.advanced.biotpEnabled");
/*      */ 
/*      */       
/*  385 */       savePolicyValue(settingValues, sessionAdmin, "advanced.saotpEnabled", "systemSettings.advanced.saotpEnabled");
/*      */ 
/*      */       
/*  388 */       saveSimpleValue(settingValues, sessionAdmin, "advanced.payloadParserFido", "systemSettings.advanced.payloadParserFido");
/*      */ 
/*      */       
/*  391 */       saveSimpleValue(settingValues, sessionAdmin, "advanced.payloadParserBiotp", "systemSettings.advanced.payloadParserBiotp");
/*      */ 
/*      */       
/*  394 */       saveSimpleValue(settingValues, sessionAdmin, "advanced.payloadParserSaotp", "systemSettings.advanced.payloadParserSaotp");
/*      */ 
/*      */       
/*  397 */       savePolicyValue(settingValues, sessionAdmin, "advanced.multiAuthMethodsEnabled", "systemSettings.advanced.multiAuthMethodsEnabled");
/*      */ 
/*      */       
/*  400 */       saveSimpleValue(settingValues, sessionAdmin, "advanced.otpKeybased", "systemSettings.advanced.otpKeybased");
/*      */ 
/*      */       
/*  403 */       savePolicyValue(settingValues, sessionAdmin, "advanced.serverPinPolicy", "systemSettings.advanced.serverPinPolicy");
/*      */ 
/*      */       
/*  406 */       saveSimpleValue(settingValues, sessionAdmin, "advanced.serverPinValidationParser", "systemSettings.advanced.serverPinValidationParser");
/*      */ 
/*      */       
/*  409 */       savePolicyValue(settingValues, sessionAdmin, "advanced.purgeDataEnabled", "systemSettings.advanced.purgeDataEnabled");
/*      */ 
/*      */       
/*  412 */       savePolicyValue(settingValues, sessionAdmin, "advanced.publisherEnabled", "systemSettings.advanced.publisherEnabled");
/*      */ 
/*      */       
/*  415 */       saveSimpleValue(settingValues, sessionAdmin, "advanced.publisherTarget", "systemSettings.advanced.publisherTarget");
/*      */ 
/*      */       
/*  418 */       saveSimpleValue(settingValues, sessionAdmin, "advanced.purgeCronExpression", "systemSettings.advanced.purgeCronExpression");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  423 */       String value = settingValues.get(dbkeyToCtxKey("advanced.purgeDataLifecyle"));
/*      */       
/*  425 */       if (StringUtils.isEmpty(value)) {
/*      */         return;
/*      */       }
/*      */       
/*  429 */       String prevVal = SystemSettingsDao.getValue("advanced.purgeDataLifecyle");
/*      */ 
/*      */       
/*  432 */       if (!prevVal.equals(value)) {
/*  433 */         SystemSettingsDao.setValue("advanced.purgeDataLifecyle", value);
/*  434 */         AuditAlarmTypes.SYSTEM_SETTING.raiseAlarm(sessionAdmin, 2050, AlarmLevels.URGENT, new Object[] { sessionAdmin.getUsername(), new I18nMessage("systemSettings.advanced.purgeDataLifecyle"), prevVal, new I18nMessage(TimePeriodTypes.YEAR
/*  435 */                 .getMessageKey()), value, new I18nMessage(TimePeriodTypes.YEAR
/*  436 */                 .getMessageKey()) });
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  441 */     catch (InternalDBException e) {
/*  442 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveCountryCodeSettings(RestResponse resp, String countryCodes, AdministratorVO sessionAdmin) {
/*  448 */     if (resp.getHasMessages()) {
/*      */       return;
/*      */     }
/*      */     
/*  452 */     if (StringUtils.isEmpty(countryCodes)) {
/*  453 */       resp.addContextMessage(dbkeyToCtxKey("customKeys.countryCode"), new I18nMessage("validate.required"));
/*      */     }
/*      */     
/*  456 */     String[] codes = countryCodes.split(",");
/*  457 */     List<KeyValuePair<String, I18nMessage>> allCodes = getAllCountryCodes(); String[] arrayOfString1; int i; byte b;
/*  458 */     for (arrayOfString1 = codes, i = arrayOfString1.length, b = 0; b < i; ) { String str = arrayOfString1[b];
/*  459 */       for (KeyValuePair<String, I18nMessage> keyValuePair : allCodes) {
/*  460 */         if (((String)keyValuePair.getKey()).equals(str)) {
/*      */           b++;
/*      */         }
/*      */       } 
/*      */       
/*  465 */       resp.addContextMessage(dbkeyToCtxKey("customKeys.countryCode"), new I18nMessage("common.desc.invalid"));
/*      */       
/*      */       return; }
/*      */ 
/*      */     
/*      */     try {
/*  471 */       String prevVal = SystemSettingsDao.getValue("customKeys.countryCode");
/*      */       
/*  473 */       if (!prevVal.equals(countryCodes)) {
/*  474 */         SystemSettingsDao.setValue("customKeys.countryCode", countryCodes);
/*  475 */         AuditAlarmTypes.SYSTEM_SETTING.raiseAlarm(sessionAdmin, 2049, AlarmLevels.URGENT, new Object[] { sessionAdmin
/*  476 */               .getUsername(), new I18nMessage("systemSettings.customKeys.countryCode"), prevVal, countryCodes });
/*      */       } 
/*      */ 
/*      */       
/*  480 */       getCountryCodeSettings(resp);
/*      */     }
/*  482 */     catch (InternalDBException e) {
/*  483 */       resp.addGeneralMessage(new I18nMessage("validate.internalError", new Object[] { e.getMessage() }));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void validateAdvancedSettings(RestResponse resp, Map<String, String> settingValues) {
/*  488 */     boolean checked = false;
/*  489 */     for (Map.Entry<String, String> pair : settingValues.entrySet()) {
/*  490 */       validateRequired(resp, pair.getKey(), pair.getValue());
/*  491 */       if (resp.getHasMessages()) {
/*      */         continue;
/*      */       }
/*      */       
/*  495 */       String dbKey = ctxToDB(pair.getKey());
/*      */ 
/*      */       
/*  498 */       if ("advanced.fidoEnabled".equals(dbKey) || "advanced.biotpEnabled"
/*  499 */         .equals(dbKey) || "advanced.saotpEnabled"
/*  500 */         .equals(dbKey) || "advanced.multiAuthMethodsEnabled"
/*  501 */         .equals(dbKey) || "advanced.publisherEnabled"
/*  502 */         .equals(dbKey) || "advanced.purgeDataEnabled"
/*  503 */         .equals(dbKey)) {
/*      */         
/*  505 */         if (SettingEnabled.valueByName(pair.getValue()) == null) {
/*  506 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */         }
/*  508 */       } else if ("advanced.payloadParserFido".equals(dbKey)) {
/*  509 */         if (FidoPayloadParser.valueByName(pair.getValue()) == null) {
/*  510 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */         }
/*  512 */       } else if ("advanced.payloadParserBiotp".equals(dbKey)) {
/*  513 */         if (BiotpPayloadParser.valueByName(pair.getValue()) == null) {
/*  514 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */         }
/*  516 */       } else if ("advanced.payloadParserSaotp".equals(dbKey)) {
/*  517 */         if (SaotpPayloadParser.valueByName(pair.getValue()) == null) {
/*  518 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */         }
/*  520 */       } else if ("advanced.purgeDataLifecyle".equals(dbKey)) {
/*  521 */         if (!((String)pair.getValue()).matches("^[0-9]+$")) {
/*  522 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */         }
/*  524 */       } else if ("advanced.purgeCronExpression".equals(dbKey)) {
/*      */         try {
/*  526 */           new CronExpression(pair.getValue());
/*  527 */         } catch (Exception e) {
/*  528 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.notCronExp"));
/*      */         } 
/*  530 */       } else if ("advanced.publisherTarget".equals(dbKey) && 
/*  531 */         !StringUtils.isEmpty(pair.getValue())) {
/*  532 */         String[] addrs = ((String)pair.getValue()).split(",");
/*      */         
/*  534 */         for (String str : addrs) {
/*      */           try {
/*  536 */             URL url = new URL(str);
/*  537 */             if (!"http".equals(url.getProtocol()) && !"https".equals(url.getProtocol())) {
/*  538 */               resp.addContextMessage(pair.getKey(), new I18nMessage("validate.address"));
/*      */             }
/*  540 */           } catch (Exception e) {
/*  541 */             resp.addContextMessage(pair.getKey(), new I18nMessage("validate.address"));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  550 */       if ("advanced.fidoEnabled".equals(dbKey)) {
/*      */         
/*  552 */         SettingEnabled fido = SettingEnabled.valueByName(pair.getValue());
/*      */         
/*  554 */         if (fido != null && fido.toBoolean())
/*  555 */           checked = true;  continue;
/*      */       } 
/*  557 */       if ("advanced.biotpEnabled".equals(dbKey)) {
/*      */         
/*  559 */         SettingEnabled biotp = SettingEnabled.valueByName(pair.getValue());
/*      */         
/*  561 */         if (biotp != null && biotp.toBoolean())
/*  562 */           checked = true;  continue;
/*      */       } 
/*  564 */       if ("advanced.saotpEnabled".equals(dbKey)) {
/*      */         
/*  566 */         SettingEnabled saotp = SettingEnabled.valueByName(pair.getValue());
/*      */         
/*  568 */         if (saotp != null && saotp.toBoolean()) {
/*  569 */           checked = true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  574 */     if (resp.getHasMessages()) {
/*      */       return;
/*      */     }
/*      */     
/*  578 */     if (!checked) {
/*  579 */       resp.addContextMessage(dbkeyToCtxKey("advanced.fidoEnabled"), new I18nMessage("common.desc.invalidMethodEnabled"));
/*  580 */       resp.addContextMessage(dbkeyToCtxKey("advanced.biotpEnabled"), new I18nMessage("common.desc.invalidMethodEnabled"));
/*  581 */       resp.addContextMessage(dbkeyToCtxKey("advanced.saotpEnabled"), new I18nMessage("common.desc.invalidMethodEnabled"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateExternalApiSettings(RestResponse resp, Map<String, String> settingValues) {
/*  588 */     for (Map.Entry<String, String> pair : settingValues.entrySet()) {
/*  589 */       validateRequired(resp, pair.getKey(), pair.getValue());
/*  590 */       if (resp.getHasMessages()) {
/*      */         continue;
/*      */       }
/*      */       
/*  594 */       String dbKey = ctxToDB(pair.getKey());
/*      */       
/*  596 */       if ("externalApi.serviceType".equals(dbKey)) {
/*      */         try {
/*  598 */           ExternalWebApiTypes.valueOf(pair.getValue());
/*  599 */         } catch (Exception e) {
/*  600 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */         }  continue;
/*      */       } 
/*  603 */       if ("externalApi.serviceSharedKey".equals(dbKey))
/*      */         continue; 
/*  605 */       if ("externalApi.serivceAddress".equals(dbKey)) {
/*      */         try {
/*  607 */           new URL(pair.getValue());
/*  608 */         } catch (Exception e) {
/*  609 */           resp.addContextMessage(pair.getKey(), new I18nMessage("validate.address"));
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateSaotpSettings(RestResponse resp, Map<String, String> settingValues) {
/*  619 */     for (Map.Entry<String, String> pair : settingValues.entrySet()) {
/*  620 */       validateRequired(resp, pair.getKey(), pair.getValue());
/*  621 */       if (resp.getHasMessages()) {
/*      */         continue;
/*      */       }
/*      */       
/*  625 */       String dbKey = ctxToDB(pair.getKey());
/*      */       
/*  627 */       if ("saotp.serverPinMaxFailCnt".equals(dbKey)) {
/*      */         try {
/*  629 */           Integer num = Integer.valueOf(Integer.parseInt(pair.getValue()));
/*  630 */           if (num.intValue() < 1 || num.intValue() > 10) {
/*  631 */             resp.addContextMessage(pair.getKey(), new I18nMessage("validate.argToArg", new Object[] { Integer.valueOf(1), Integer.valueOf(10) }));
/*      */           }
/*  633 */         } catch (NumberFormatException e) {
/*  634 */           resp.addContextMessage(pair.getKey(), new I18nMessage("validate.argToArg", new Object[] { Integer.valueOf(1), Integer.valueOf(10) }));
/*      */         }  continue;
/*  636 */       }  if ("saotp.serverPinEnabled".equals(dbKey) || "saotp.multiAppEnabled"
/*  637 */         .equals(dbKey) || "saotp.issueCodeEnabled"
/*  638 */         .equals(dbKey) || "saotp.issuranceExpiredEnabled"
/*  639 */         .equals(dbKey)) {
/*      */         
/*  641 */         if (SettingEnabled.valueByName(pair.getValue()) == null)
/*  642 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid")); 
/*      */         continue;
/*      */       } 
/*  645 */       if ("saotp.multiDevicePolicy".equals(dbKey)) {
/*  646 */         if (MultiDevicePolicy.valueByName(pair.getValue()) == null)
/*  647 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  649 */       if ("saotp.authFailActionPolicy".equals(dbKey)) {
/*  650 */         if (AuthFailActionPolicy.valueByName(pair.getValue()) == null)
/*  651 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  653 */       if ("saotp.tokenSharePolicy".equals(dbKey)) {
/*  654 */         if (TokenSharePolicy.valueByName(pair.getValue()) == null)
/*  655 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  657 */       if (("saotp.issuranceExpiredPeriods".equals(dbKey) || "saotp.serverPinMaxFailCnt".equals(dbKey)) && 
/*  658 */         !((String)pair.getValue()).matches("^[0-9]+$")) {
/*  659 */         resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  665 */     validatePeriods(resp, "saotp.issuranceExpiredPeriodType", "saotp.issuranceExpiredPeriods", settingValues);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateBiotpSettings(RestResponse resp, Map<String, String> settingValues) {
/*  671 */     for (Map.Entry<String, String> pair : settingValues.entrySet()) {
/*  672 */       validateRequired(resp, pair.getKey(), pair.getValue());
/*      */       
/*  674 */       if (resp.getHasMessages()) {
/*      */         continue;
/*      */       }
/*      */       
/*  678 */       String dbKey = ctxToDB(pair.getKey());
/*      */       
/*  680 */       if ("biotp.appIdPolicy".equals(dbKey)) {
/*      */         
/*      */         try {
/*  683 */           AppIDPolicy.valueOf(pair.getValue());
/*  684 */         } catch (Exception e) {
/*  685 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */         }  continue;
/*      */       } 
/*  688 */       if ("biotp.valdiateChannelBindingEnabled".equals(dbKey) || "biotp.multiAppEnabled"
/*  689 */         .equals(dbKey) || "biotp.issueCodeEnabled"
/*  690 */         .equals(dbKey) || "biotp.issuranceExpiredEnabled"
/*  691 */         .equals(dbKey)) {
/*      */         
/*  693 */         if (SettingEnabled.valueByName(pair.getValue()) == null)
/*  694 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  696 */       if ("biotp.multiDevicePolicy".equals(dbKey)) {
/*  697 */         if (MultiDevicePolicy.valueByName(pair.getValue()) == null)
/*  698 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  700 */       if ("biotp.authFailActionPolicy".equals(dbKey)) {
/*  701 */         if (AuthFailActionPolicy.valueByName(pair.getValue()) == null)
/*  702 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  704 */       if ("biotp.tokenSharePolicy".equals(dbKey)) {
/*  705 */         if (TokenSharePolicy.valueByName(pair.getValue()) == null)
/*  706 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  708 */       if ("biotp.issuranceExpiredPeriods".equals(dbKey) && 
/*  709 */         !((String)pair.getValue()).matches("^[0-9]+$")) {
/*  710 */         resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  715 */     validatePeriods(resp, "biotp.issuranceExpiredPeriodType", "biotp.issuranceExpiredPeriods", settingValues);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateFidoSettings(RestResponse resp, Map<String, String> settingValues) {
/*  724 */     for (Map.Entry<String, String> pair : settingValues.entrySet()) {
/*  725 */       validateRequired(resp, pair.getKey(), pair.getValue());
/*  726 */       if (resp.getHasMessages()) {
/*      */         continue;
/*      */       }
/*      */       
/*  730 */       String dbKey = ctxToDB(pair.getKey());
/*      */       
/*  732 */       if ("fido.appIdPolicy".equals(dbKey)) {
/*      */         try {
/*  734 */           AppIDPolicy.valueOf(pair.getValue());
/*  735 */         } catch (Exception e) {
/*  736 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */         }  continue;
/*      */       } 
/*  739 */       if ("fido.valdiateChannelBindingEnabled".equals(dbKey) || "fido.multiAppEnabled"
/*  740 */         .equals(dbKey) || "fido.issueCodeEnabled"
/*  741 */         .equals(dbKey) || "fido.issuranceExpiredEnabled"
/*  742 */         .equals(dbKey)) {
/*      */         
/*  744 */         if (SettingEnabled.valueByName(pair.getValue()) == null)
/*  745 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  747 */       if ("fido.multiDevicePolicy".equals(dbKey)) {
/*  748 */         if (MultiDevicePolicy.valueByName(pair.getValue()) == null)
/*  749 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  751 */       if ("fido.authFailActionPolicy".equals(dbKey)) {
/*  752 */         if (AuthFailActionPolicy.valueByName(pair.getValue()) == null)
/*  753 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  755 */       if ("fido.issuranceExpiredPeriods".equals(dbKey) && 
/*  756 */         !((String)pair.getValue()).matches("^[0-9]+$")) {
/*  757 */         resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  762 */     validatePeriods(resp, "fido.issuranceExpiredPeriodType", "fido.issuranceExpiredPeriods", settingValues);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateIntegrateSettings(RestResponse resp, Map<String, String> settingValues) {
/*  768 */     for (Map.Entry<String, String> pair : settingValues.entrySet()) {
/*  769 */       validateRequired(resp, pair.getKey(), pair.getValue());
/*  770 */       if (resp.getHasMessages()) {
/*      */         continue;
/*      */       }
/*      */       
/*  774 */       String dbKey = ctxToDB(pair.getKey());
/*      */       
/*  776 */       if ("integrate.authMaxFailCount".equals(dbKey) || "integrate.issueCodeMaxFailCount".equals(dbKey)) {
/*  777 */         if (!((String)pair.getValue()).matches("^[0-9]+$"))
/*  778 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  780 */       if ("integrate.authMgmtScope".equals(dbKey)) {
/*  781 */         if (AuthManagementScope.valueByName(pair.getValue()) == null)
/*  782 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));  continue;
/*      */       } 
/*  784 */       if ("integrate.rpServerUri".equals(dbKey) || "integrate.trustedFacetUrl".equals(dbKey)) {
/*      */         try {
/*  786 */           URL url = new URL(pair.getValue());
/*  787 */           if (!"http".equals(url.getProtocol()) && !"https".equals(url.getProtocol())) {
/*  788 */             resp.addContextMessage(pair.getKey(), new I18nMessage("validate.address"));
/*      */           }
/*  790 */         } catch (Exception e) {
/*  791 */           resp.addContextMessage(pair.getKey(), new I18nMessage("validate.address"));
/*      */         }  continue;
/*  793 */       }  if ("integrate.tokenThresholdCnt".equals(dbKey)) {
/*      */         try {
/*  795 */           Integer num = Integer.valueOf(Integer.parseInt(pair.getValue()));
/*  796 */           if (num.intValue() < 1000 || num.intValue() > 50000) {
/*  797 */             resp.addContextMessage(pair.getKey(), new I18nMessage("validate.argToArg", new Object[] { Integer.valueOf(1000), Integer.valueOf(5000) }));
/*      */           }
/*  799 */         } catch (NumberFormatException e) {
/*  800 */           resp.addContextMessage(pair.getKey(), new I18nMessage("validate.argToArg", new Object[] { Integer.valueOf(1000), Integer.valueOf(5000) }));
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  805 */     validatePeriods(resp, "integrate.authLifetimePeriodType", "integrate.authLifetimePeriods", settingValues);
/*  806 */     validatePeriods(resp, "integrate.issueCodeLifetimePeriodType", "integrate.issueCodeLifetimePeriods", settingValues);
/*  807 */     validatePeriods(resp, "integrate.checkAuthResultLifetimePeriodType", "integrate.checkAuthResultLifetimePeriods", settingValues);
/*      */   }
/*      */ 
/*      */   
/*      */   private void validateApplicationFields(RestResponse resp, Map<String, String> settingValues) {
/*  812 */     for (Map.Entry<String, String> pair : settingValues.entrySet()) {
/*      */       
/*  814 */       validateRequired(resp, pair.getKey(), pair.getValue());
/*      */       
/*  816 */       if (resp.getHasMessages()) {
/*      */         continue;
/*      */       }
/*      */       
/*  820 */       String dbKey = ctxToDB(pair.getKey());
/*      */       
/*  822 */       if ("application.instanceName".equals(dbKey) && (
/*  823 */         (String)pair.getValue()).length() > 50) {
/*  824 */         resp.addContextMessage(pair.getKey(), new I18nMessage("validate.maxChar", new Object[] { Integer.valueOf(50) }));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  829 */       if ("application.sysLanguage".equals(dbKey)) {
/*      */         try {
/*  831 */           new Locale(pair.getValue());
/*  832 */         } catch (Exception e) {
/*  833 */           resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */         } 
/*      */       }
/*      */       
/*  837 */       if ("application.sessionTimeout".equals(dbKey) && 
/*  838 */         !((String)pair.getValue()).matches("^[0-9]+$")) {
/*  839 */         resp.addContextMessage(pair.getKey(), new I18nMessage("common.desc.invalid"));
/*      */       }
/*      */ 
/*      */       
/*  843 */       if ("application.ipWhitelist".equals(dbKey)) {
/*      */         
/*  845 */         String[] ips = ((String)pair.getValue()).trim().split(",");
/*  846 */         StringBuilder sb = new StringBuilder(64);
/*  847 */         for (int i = 0; i < ips.length; i++) {
/*  848 */           ips[i] = ips[i].trim();
/*  849 */           String message = IPv4AddressUtils.checkIpMask(ips[i]);
/*  850 */           if (message != null) {
/*  851 */             resp.addContextMessage(pair.getKey(), new I18nMessage("validate.ipaddress", new Object[] { message }));
/*      */             
/*      */             return;
/*      */           } 
/*  855 */           if (i != 0) {
/*  856 */             sb.append(",");
/*      */           }
/*      */           
/*  859 */           sb.append(ips[i]);
/*      */         } 
/*      */         
/*  862 */         settingValues.put(pair.getKey(), sb.toString());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void validatePeriods(RestResponse resp, String typeKey, String periodsKey, Map<String, String> settingValues) {
/*  869 */     String ctxKeyType = dbkeyToCtxKey(typeKey);
/*  870 */     String ctxKeyPeriods = dbkeyToCtxKey(periodsKey);
/*  871 */     String typeVal = settingValues.get(ctxKeyType);
/*  872 */     String periodsVal = settingValues.get(ctxKeyPeriods);
/*      */     
/*  874 */     if (StringUtils.isEmpty(typeVal) && StringUtils.isEmpty(periodsVal)) {
/*      */       return;
/*      */     }
/*      */     
/*  878 */     if (StringUtils.isEmpty(typeVal) || StringUtils.isEmpty(periodsVal)) {
/*  879 */       resp.addContextMessage(ctxKeyType, new I18nMessage("common.desc.requiredPeriods"));
/*      */       
/*      */       return;
/*      */     } 
/*  883 */     if (TimePeriodTypes.valueByName(typeVal) == null) {
/*  884 */       resp.addContextMessage(ctxKeyType, new I18nMessage("common.desc.invalid"));
/*      */     }
/*      */     
/*  887 */     if (!periodsVal.matches("^[0-9]+$")) {
/*  888 */       resp.addContextMessage(ctxKeyPeriods + "_" + typeVal, new I18nMessage("common.desc.invalid"));
/*      */     }
/*      */     
/*  891 */     String prevType = SystemSettingsDao.getValue(typeKey);
/*  892 */     String prevperiods = SystemSettingsDao.getValue(periodsKey);
/*      */     
/*  894 */     boolean diff = false;
/*  895 */     if (!prevType.equals(typeVal)) {
/*  896 */       diff = true;
/*      */     }
/*  898 */     if (!prevperiods.equals(periodsVal)) {
/*  899 */       diff = true;
/*      */     }
/*      */     
/*  902 */     if (!diff) {
/*  903 */       settingValues.remove(ctxKeyType);
/*  904 */       settingValues.remove(ctxKeyPeriods);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void validateRequired(RestResponse resp, String key, String value) {
/*  909 */     if (StringUtils.isEmpty(value)) {
/*  910 */       resp.addContextMessage(key, new I18nMessage("common.desc.required"));
/*      */     }
/*      */   }
/*      */   
/*      */   private void savePolicyValue(Map<String, String> settingValues, AdministratorVO sessionAdmin, String key, String auditFieldKey) {
/*      */     Object valueParam, prevParam;
/*  916 */     String value = settingValues.get(dbkeyToCtxKey(key));
/*      */     
/*  918 */     if (StringUtils.isEmpty(value)) {
/*      */       return;
/*      */     }
/*      */     
/*  922 */     String prevVal = SystemSettingsDao.getValue(key);
/*      */     
/*  924 */     if (prevVal.equals(value)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  930 */     if ("integrate.authMgmtScope".equals(key)) {
/*  931 */       AuthManagementScope scope = AuthManagementScope.valueByName(value);
/*  932 */       valueParam = new I18nMessage(scope.getMessageKey());
/*  933 */       prevParam = new I18nMessage(AuthManagementScope.valueByName(prevVal).getMessageKey());
/*  934 */     } else if ("fido.multiDevicePolicy".equals(key) || "biotp.multiDevicePolicy".equals(key) || "saotp.multiDevicePolicy".equals(key)) {
/*  935 */       valueParam = new I18nMessage(MultiDevicePolicy.valueByName(value).getMessageKey());
/*  936 */       prevParam = new I18nMessage(MultiDevicePolicy.valueByName(prevVal).getMessageKey());
/*  937 */     } else if ("fido.authFailActionPolicy".equals(key) || "biotp.authFailActionPolicy".equals(key) || "saotp.authFailActionPolicy".equals(key)) {
/*  938 */       valueParam = new I18nMessage(AuthFailActionPolicy.valueByName(value).getMessageKey());
/*  939 */       prevParam = new I18nMessage(AuthFailActionPolicy.valueByName(prevVal).getMessageKey());
/*  940 */     } else if ("biotp.tokenSharePolicy".equals(key) || "saotp.tokenSharePolicy".equals(key)) {
/*  941 */       valueParam = new I18nMessage(TokenSharePolicy.valueByName(value).getMessageKey());
/*  942 */       prevParam = new I18nMessage(TokenSharePolicy.valueByName(prevVal).getMessageKey());
/*      */     }
/*  944 */     else if (SettingEnabled.valueByName(value) != null) {
/*  945 */       valueParam = new I18nMessage(SettingEnabled.valueByName(value).getMessageKey());
/*  946 */       prevParam = new I18nMessage(SettingEnabled.valueByName(prevVal).getMessageKey());
/*      */     } else {
/*      */       
/*  949 */       valueParam = value;
/*  950 */       prevParam = prevVal;
/*      */     } 
/*      */     
/*  953 */     SystemSettingsDao.setValue(key, value);
/*  954 */     AuditAlarmTypes.SYSTEM_SETTING.raiseAlarm(sessionAdmin, 2051, AlarmLevels.URGENT, new Object[] { sessionAdmin
/*  955 */           .getUsername(), new I18nMessage(auditFieldKey), prevParam, valueParam });
/*      */   }
/*      */   private void saveServicePolicyValue(Map<String, String> settingValues, AdministratorVO sessionAdmin, String key, String auditFieldKey, AuthMethodTypes authType) {
/*      */     Object valueParam, prevParam;
/*  959 */     String value = settingValues.get(dbkeyToCtxKey(key));
/*      */     
/*  961 */     if (StringUtils.isEmpty(value)) {
/*      */       return;
/*      */     }
/*      */     
/*  965 */     String prevVal = SystemSettingsDao.getValue(key);
/*      */     
/*  967 */     if (prevVal.equals(value)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  973 */     if ("fido.multiDevicePolicy".equals(key) || "biotp.multiDevicePolicy".equals(key) || "saotp.multiDevicePolicy".equals(key)) {
/*  974 */       valueParam = new I18nMessage(MultiDevicePolicy.valueByName(value).getMessageKey());
/*  975 */       prevParam = new I18nMessage(MultiDevicePolicy.valueByName(prevVal).getMessageKey());
/*  976 */     } else if ("fido.authFailActionPolicy".equals(key) || "biotp.authFailActionPolicy".equals(key) || "saotp.authFailActionPolicy".equals(key)) {
/*  977 */       valueParam = new I18nMessage(AuthFailActionPolicy.valueByName(value).getMessageKey());
/*  978 */       prevParam = new I18nMessage(AuthFailActionPolicy.valueByName(prevVal).getMessageKey());
/*  979 */     } else if ("biotp.tokenSharePolicy".equals(key) || "saotp.tokenSharePolicy".equals(key)) {
/*  980 */       valueParam = new I18nMessage(TokenSharePolicy.valueByName(value).getMessageKey());
/*  981 */       prevParam = new I18nMessage(TokenSharePolicy.valueByName(prevVal).getMessageKey());
/*      */     }
/*  983 */     else if (SettingEnabled.valueByName(value) != null) {
/*  984 */       valueParam = new I18nMessage(SettingEnabled.valueByName(value).getMessageKey());
/*  985 */       prevParam = new I18nMessage(SettingEnabled.valueByName(prevVal).getMessageKey());
/*      */     } else {
/*      */       
/*  988 */       valueParam = value;
/*  989 */       prevParam = prevVal;
/*      */     } 
/*      */     
/*  992 */     SystemSettingsDao.setValue(key, value);
/*  993 */     AuditAlarmTypes.SYSTEM_SETTING.raiseAlarm(sessionAdmin, 2052, AlarmLevels.URGENT, new Object[] { sessionAdmin
/*  994 */           .getUsername(), new I18nMessage(authType.getMessageKey()), new I18nMessage(auditFieldKey), prevParam, valueParam });
/*      */   }
/*      */   
/*      */   private void saveSimpleValue(Map<String, String> settingValues, AdministratorVO sessionAdmin, String key, String auditFieldKey) {
/*  998 */     String value = settingValues.get(dbkeyToCtxKey(key));
/*      */     
/* 1000 */     if (StringUtils.isEmpty(value)) {
/*      */       return;
/*      */     }
/*      */     
/* 1004 */     value = value.trim();
/*      */     
/* 1006 */     String prevVal = SystemSettingsDao.getValue(key);
/*      */     
/* 1008 */     if (prevVal.equals(value)) {
/*      */       return;
/*      */     }
/*      */     
/* 1012 */     SystemSettingsDao.setValue(key, value);
/* 1013 */     AuditAlarmTypes.SYSTEM_SETTING.raiseAlarm(sessionAdmin, 2049, AlarmLevels.URGENT, new Object[] { sessionAdmin
/* 1014 */           .getUsername(), new I18nMessage(auditFieldKey), prevVal, value });
/*      */   }
/*      */   
/*      */   private void savePeriods(Map<String, String> settingValues, AdministratorVO sessionAdmin, String typeKey, String periodsKey, String auditFieldKey) {
/* 1018 */     TimePeriodTypes typeVal = TimePeriodTypes.valueByName(settingValues.get(dbkeyToCtxKey(typeKey)));
/* 1019 */     if (typeVal == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1023 */     String periodVal = settingValues.get(dbkeyToCtxKey(periodsKey));
/*      */     
/* 1025 */     if (StringUtils.isEmpty(periodVal)) {
/*      */       return;
/*      */     }
/*      */     
/* 1029 */     TimePeriodTypes prevType = TimePeriodTypes.valueByName(SystemSettingsDao.getValue(typeKey));
/* 1030 */     String prevPeriods = SystemSettingsDao.getValue(periodsKey);
/*      */     
/* 1032 */     SystemSettingsDao.setValue(typeKey, typeVal.name());
/* 1033 */     SystemSettingsDao.setValue(periodsKey, periodVal);
/*      */     
/* 1035 */     AuditAlarmTypes.SYSTEM_SETTING.raiseAlarm(sessionAdmin, 2050, AlarmLevels.URGENT, new Object[] { sessionAdmin
/* 1036 */           .getUsername(), new I18nMessage(auditFieldKey), prevPeriods, new I18nMessage(prevType
/* 1037 */             .getMessageKey()), periodVal, new I18nMessage(typeVal
/* 1038 */             .getMessageKey()) });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSettingData(RestResponse resp, String[] settingNames) {
/* 1044 */     for (String settingName : settingNames) {
/* 1045 */       String settingValue = SystemSettingsDao.getValue(settingName);
/* 1046 */       resp.addContextData(dbkeyToCtxKey(settingName), settingValue);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String dbkeyToCtxKey(String dbKey) {
/* 1056 */     return dbKey.replaceAll("\\.", "_");
/*      */   }
/*      */   
/*      */   private static String ctxToDB(String ctx) {
/* 1060 */     return ctx.replaceAll("_", ".");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1065 */   private static List<KeyValuePair<String, I18nMessage>> country_code_all = null;
/*      */   
/*      */   private static List<KeyValuePair<String, I18nMessage>> getAllCountryCodes() {
/* 1068 */     if (country_code_all == null) {
/* 1069 */       List<KeyValuePair<String, I18nMessage>> list = new ArrayList<>(238);
/*      */       
/*      */       try {
/* 1072 */         URL url = com.dreammirae.mmth.web.service.SystemSettingsService.class.getClassLoader().getResource("countryCode/ISO_3166_1_ko.properties");
/* 1073 */         File f = new File(url.toURI());
/* 1074 */         LineIterator it = FileUtils.lineIterator(f, Commons.UTF8_CS.name());
/* 1075 */         while (it.hasNext()) {
/* 1076 */           String line = it.nextLine();
/* 1077 */           String code = line.substring(0, 2);
/* 1078 */           list.add(new KeyValuePair(code, new I18nMessage(code)));
/*      */         } 
/* 1080 */         LineIterator.closeQuietly(it);
/* 1081 */         country_code_all = Collections.unmodifiableList(list);
/* 1082 */       } catch (Exception e) {
/* 1083 */         e.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/* 1087 */     return country_code_all;
/*      */   }
/*      */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\service\SystemSettingsService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */