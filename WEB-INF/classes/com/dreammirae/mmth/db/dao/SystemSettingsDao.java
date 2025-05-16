/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.policy.AuthFailActionPolicy;
/*     */ import com.dreammirae.mmth.authentication.policy.AuthManagementScope;
/*     */ import com.dreammirae.mmth.authentication.policy.BiotpPayloadParser;
/*     */ import com.dreammirae.mmth.authentication.policy.FidoPayloadParser;
/*     */ import com.dreammirae.mmth.authentication.policy.MultiDevicePolicy;
/*     */ import com.dreammirae.mmth.authentication.policy.OTPKeyBased;
/*     */ import com.dreammirae.mmth.authentication.policy.SaotpPayloadParser;
/*     */ import com.dreammirae.mmth.authentication.policy.ServerPinPolicy;
/*     */ import com.dreammirae.mmth.authentication.policy.ServerPinValidationParser;
/*     */ import com.dreammirae.mmth.authentication.policy.TokenSharePolicy;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLSystemSettings;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*     */ import com.dreammirae.mmth.fido.handler.supporter.AppIDPolicy;
/*     */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*     */ import com.dreammirae.mmth.runtime.code.TimePeriodTypes;
/*     */ import com.dreammirae.mmth.runtime.publish.ReceiveSyncCacheListener;
/*     */ import com.dreammirae.mmth.runtime.service.ChannelBindingTimerTask;
/*     */ import com.dreammirae.mmth.vo.types.SettingEnabled;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
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
/*     */ public class SystemSettingsDao
/*     */   extends BaseDao
/*     */   implements ReceiveSyncCacheListener
/*     */ {
/* 265 */   public static final String[] SYS_KEYS = new String[] { "application.instanceName", "application.sysLanguage", "application.sessionTimeout", "application.ipWhitelist", "integrate.authLifetimePeriods", "integrate.authLifetimePeriodType", "integrate.authMgmtScope", "integrate.authMaxFailCount", "integrate.biotpMaxFailCount", "integrate.pinMaxFailCount", "integrate.rpServerUri", "integrate.trustedFacetUrl", "integrate.issueCodeLifetimePeriodType", "integrate.issueCodeLifetimePeriods", "integrate.issueCodeMaxFailCount", "integrate.checkAuthResultLifetimePeriodType", "integrate.checkAuthResultLifetimePeriods", "fido.appIdPolicy", "fido.valdiateChannelBindingEnabled", "fido.multiDevicePolicy", "fido.multiDeviceIndividualDefault", "fido.multiAppEnabled", "fido.authFailActionPolicy", "fido.overrideAutoDeregEnabled", "fido.issueCodeEnabled", "fido.issuranceExpiredEnabled", "fido.issuranceExpiredPeriods", "fido.issuranceExpiredPeriodType", "biotp.appIdPolicy", "biotp.valdiateChannelBindingEnabled", "biotp.multiDevicePolicy", "biotp.multiDeviceIndividualDefault", "biotp.multiAppEnabled", "biotp.authFailActionPolicy", "biotp.overrideAutoDeregEnabled", "biotp.issueCodeEnabled", "biotp.tokenSharePolicy", "biotp.issuranceExpiredEnabled", "biotp.issuranceExpiredPeriods", "biotp.issuranceExpiredPeriodType", "advanced.fidoEnabled", "advanced.biotpEnabled", "advanced.saotpEnabled", "advanced.payloadParserFido", "advanced.payloadParserBiotp", "advanced.payloadParserSaotp", "advanced.multiAuthMethodsEnabled", "advanced.otpKeybased", "advanced.serverPinPolicy", "advanced.serverPinValidationParser", "advanced.publisherEnabled", "advanced.publisherTarget", "advanced.purgeDataEnabled", "advanced.purgeCronExpression", "advanced.purgeDataLifecyle", "externalApi.serviceType", "externalApi.serviceSharedKey", "externalApi.serivceAddress", "externalApi.transactionLifeTimePeriods", "externalApi.qrCodeDimension" };
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
/*     */   public static final String COUNTRY_CODES = "customKeys.countryCode";
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
/*     */   public static SettingEnabled getSettingEnabled(String settingName) {
/* 299 */     String value = getValue(settingName);
/* 300 */     return SettingEnabled.valueByName(value);
/*     */   }
/*     */   
/*     */   public static void setSettingEnabled(String settingName, SettingEnabled enabled) {
/* 304 */     setValue(settingName, enabled.name());
/*     */   }
/*     */   
/*     */   public static MultiDevicePolicy getMultiDevicePolicy(String settingName) {
/* 308 */     String value = getValue(settingName);
/* 309 */     return MultiDevicePolicy.valueByName(value);
/*     */   }
/*     */   
/*     */   public static FidoPayloadParser getFidoPayloadParser() {
/* 313 */     String value = getValue("advanced.payloadParserFido");
/* 314 */     return FidoPayloadParser.valueByName(value);
/*     */   }
/*     */   
/*     */   public static BiotpPayloadParser getBiotpPayloadParser() {
/* 318 */     String value = getValue("advanced.payloadParserBiotp");
/* 319 */     return BiotpPayloadParser.valueByName(value);
/*     */   }
/*     */   
/*     */   public static SaotpPayloadParser getSaotpPayloadParser() {
/* 323 */     String value = getValue("advanced.payloadParserSaotp");
/* 324 */     return SaotpPayloadParser.valueByName(value);
/*     */   }
/*     */   
/*     */   public static AppIDPolicy getAppIDPolicy() {
/* 328 */     String value = getValue("fido.appIdPolicy");
/* 329 */     return AppIDPolicy.valueByName(value);
/*     */   }
/*     */   
/*     */   public static AuthManagementScope getAuthManagementScope() {
/* 333 */     String value = getValue("integrate.authMgmtScope");
/* 334 */     return AuthManagementScope.valueByName(value);
/*     */   }
/*     */   
/*     */   public static ExternalWebApiTypes getWebApiPolicy() {
/* 338 */     String value = getValue("externalApi.serviceType");
/*     */     
/* 340 */     if (StringUtils.isEmpty(value)) {
/* 341 */       return ExternalWebApiTypes.NONE;
/*     */     }
/*     */     
/* 344 */     return ExternalWebApiTypes.valueByName(value);
/*     */   }
/*     */   
/*     */   public static int getMaxAuthErrorCnt() {
/* 348 */     int value = getInt("integrate.authMaxFailCount");
/* 349 */     return value;
/*     */   }
/*     */   
/*     */   public static int getPinMaxAuthErrorCnt() {
/* 353 */     int value = getInt("integrate.pinMaxFailCount");
/* 354 */     return value;
/*     */   }
/*     */   
/*     */   public static int getPatternMaxAuthErrorCnt() {
/* 358 */     int value = getInt("integrate.biotpMaxFailCount");
/* 359 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale getSystemLocale() {
/* 366 */     String val = getValue("application.sysLanguage");
/*     */     try {
/* 368 */       return new Locale(val);
/* 369 */     } catch (Exception e) {
/*     */ 
/*     */ 
/*     */       
/* 373 */       return Locale.KOREAN;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getValue(String key) {
/* 381 */     String defaultVal = getDefaultValue(key);
/* 382 */     return getValue(key, defaultVal);
/*     */   }
/*     */   
/*     */   public static String getValue(String key, String defaultValue) {
/* 386 */     return get(key, defaultValue);
/*     */   }
/*     */   
/*     */   public static void setValue(String key, String value) {
/* 390 */     save(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(String key) {
/* 397 */     String defaultVal = getDefaultValue(key);
/*     */     
/* 399 */     String result = get(key, defaultVal);
/*     */     try {
/* 401 */       return Integer.parseInt(result);
/* 402 */     } catch (NumberFormatException e) {
/* 403 */       return Integer.parseInt(defaultVal);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setInt(String key, int value) {
/* 408 */     save(key, String.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getDouble(String key) {
/* 416 */     String defaultVal = getDefaultValue(key);
/* 417 */     String result = get(key, defaultVal);
/*     */     try {
/* 419 */       return Double.parseDouble(result);
/* 420 */     } catch (NumberFormatException e) {
/* 421 */       return Double.parseDouble(defaultVal);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setDouble(String key, double value) {
/* 426 */     save(key, String.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getLong(String key) {
/* 434 */     String defaultVal = getDefaultValue(key);
/*     */     
/* 436 */     String result = get(key, defaultVal);
/*     */     try {
/* 438 */       return Long.parseLong(result);
/* 439 */     } catch (NumberFormatException e) {
/* 440 */       return Long.parseLong(defaultVal);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setLong(String key, long value) {
/* 445 */     save(key, String.valueOf(value));
/*     */   }
/*     */   
/*     */   public static long getPeriods(String typeKey, String periodKey) {
/* 449 */     TimePeriodTypes type = TimePeriodTypes.valueByName(getValue(typeKey));
/*     */     
/* 451 */     if (type == null) {
/* 452 */       throw new InternalDBException("Period type is not defined. key = " + typeKey);
/*     */     }
/*     */     
/* 455 */     int periods = getInt(periodKey);
/*     */     
/* 457 */     return type.defaultMillisOfPeriods(periods);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 462 */   private static final Map<String, String> CACHE = new HashMap<>(256);
/*     */ 
/*     */   
/*     */   private static String get(String settingName, String defaultValue) {
/* 466 */     String result = CACHE.get(settingName);
/*     */     
/* 468 */     if (StringUtils.isEmpty(result)) {
/* 469 */       result = (new com.dreammirae.mmth.db.dao.SystemSettingsDao()).queryForString("SELECT settingValue FROM MMTH_SystemSettings WHERE settingName=?", DMLSystemSettings.toSelectParam(settingName), defaultValue);
/* 470 */       CACHE.put(settingName, result);
/*     */     } 
/*     */     
/* 473 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void save(String settingName, String settingValue) {
/* 479 */     if (StringUtils.isEmpty(settingName) || StringUtils.isEmpty(settingValue)) {
/*     */       return;
/*     */     }
/*     */     
/* 483 */     String previousValue = CACHE.get(settingName);
/*     */     
/* 485 */     if (previousValue != null && previousValue.equals(settingValue)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 490 */     com.dreammirae.mmth.db.dao.SystemSettingsDao dao = new com.dreammirae.mmth.db.dao.SystemSettingsDao();
/*     */ 
/*     */     
/* 493 */     dao.doInTransaction((TransactionCallbackWithoutResult)new Object(settingName, dao, settingValue));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeAfterAction(String settingName, String settingValue) {
/* 517 */     if ("integrate.rpServerUri".equals(settingName) || "fido.valdiateChannelBindingEnabled"
/* 518 */       .equals(settingName) || "biotp.valdiateChannelBindingEnabled"
/* 519 */       .equals(settingName)) {
/* 520 */       ChannelBindingTimerTask.refeshChannelBinding();
/*     */     }
/*     */     
/* 523 */     if ("advanced.publisherEnabled".equals(settingName) || "advanced.publisherTarget"
/* 524 */       .equals(settingName))
/*     */     {
/* 526 */       if (Commons.ctx.getSyncCachePublisher() != null) {
/* 527 */         Commons.ctx.getSyncCachePublisher().refreshPublisher();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 532 */     if (Commons.ctx.getSyncCachePublisher() != null) {
/* 533 */       Commons.ctx.getSyncCachePublisher().syncCacheChanged(SyncCacheTypes.SYSTEM_ENV);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 538 */   private static final Map<String, String> DEFAULT_VALUES = new HashMap<>();
/*     */   
/*     */   private static final String SPLIT_DELEMITER = ",";
/*     */ 
/*     */   
/*     */   static {
/* 544 */     DEFAULT_VALUES.put("application.version", "1.0");
/* 545 */     DEFAULT_VALUES.put("application.databaseSchemaVersion", "2.0.6");
/* 546 */     DEFAULT_VALUES.put("application.instanceName", "Mirae Technology BIOTP Service");
/* 547 */     DEFAULT_VALUES.put("application.sysLanguage", Locale.ENGLISH.getLanguage());
/* 548 */     DEFAULT_VALUES.put("application.sessionTimeout", Integer.toString(10));
/* 549 */     DEFAULT_VALUES.put("application.ipWhitelist", "*.*.*.*");
/*     */ 
/*     */ 
/*     */     
/* 553 */     DEFAULT_VALUES.put("integrate.rpServerUri", "https://127.0.0.1:8443");
/* 554 */     DEFAULT_VALUES.put("integrate.trustedFacetUrl", "https://127.0.0.1:8443/rpserver/httpapi/public/facets");
/* 555 */     DEFAULT_VALUES.put("integrate.authLifetimePeriods", Integer.toString(2));
/* 556 */     DEFAULT_VALUES.put("integrate.authLifetimePeriodType", TimePeriodTypes.MINUTE.name());
/* 557 */     DEFAULT_VALUES.put("integrate.authMgmtScope", AuthManagementScope.BY_USER.name());
/* 558 */     DEFAULT_VALUES.put("integrate.authMaxFailCount", Integer.toString(10));
/* 559 */     DEFAULT_VALUES.put("integrate.pinMaxFailCount", Integer.toString(10));
/* 560 */     DEFAULT_VALUES.put("integrate.biotpMaxFailCount", Integer.toString(10));
/* 561 */     DEFAULT_VALUES.put("integrate.issueCodeLifetimePeriods", Integer.toString(7));
/* 562 */     DEFAULT_VALUES.put("integrate.issueCodeLifetimePeriodType", TimePeriodTypes.DAY.name());
/* 563 */     DEFAULT_VALUES.put("integrate.issueCodeMaxFailCount", Integer.toString(10));
/* 564 */     DEFAULT_VALUES.put("integrate.checkAuthResultLifetimePeriodType", TimePeriodTypes.MINUTE.name());
/* 565 */     DEFAULT_VALUES.put("integrate.checkAuthResultLifetimePeriods", Integer.toString(2));
/* 566 */     DEFAULT_VALUES.put("integrate.tokenThresholdCnt", Integer.toString(10000));
/*     */ 
/*     */ 
/*     */     
/* 570 */     DEFAULT_VALUES.put("fido.appIdPolicy", AppIDPolicy.EMPTY.name());
/* 571 */     DEFAULT_VALUES.put("fido.valdiateChannelBindingEnabled", SettingEnabled.DISABLED.name());
/* 572 */     DEFAULT_VALUES.put("fido.multiDevicePolicy", MultiDevicePolicy.DISALLOWED_ALL.name());
/* 573 */     DEFAULT_VALUES.put("fido.multiDeviceIndividualDefault", SettingEnabled.DISABLED.name());
/* 574 */     DEFAULT_VALUES.put("fido.multiAppEnabled", SettingEnabled.DISABLED.name());
/* 575 */     DEFAULT_VALUES.put("fido.authFailActionPolicy", AuthFailActionPolicy.NONE.name());
/* 576 */     DEFAULT_VALUES.put("fido.overrideAutoDeregEnabled", SettingEnabled.DISABLED.name());
/* 577 */     DEFAULT_VALUES.put("fido.issueCodeEnabled", SettingEnabled.DISABLED.name());
/* 578 */     DEFAULT_VALUES.put("fido.issuranceExpiredEnabled", SettingEnabled.DISABLED.name());
/* 579 */     DEFAULT_VALUES.put("fido.issuranceExpiredPeriods", Integer.toString(5));
/* 580 */     DEFAULT_VALUES.put("fido.issuranceExpiredPeriodType", TimePeriodTypes.YEAR.name());
/*     */ 
/*     */     
/* 583 */     DEFAULT_VALUES.put("biotp.appIdPolicy", AppIDPolicy.EMPTY.name());
/* 584 */     DEFAULT_VALUES.put("biotp.valdiateChannelBindingEnabled", SettingEnabled.DISABLED.name());
/* 585 */     DEFAULT_VALUES.put("biotp.multiDevicePolicy", MultiDevicePolicy.DISALLOWED_ALL.name());
/* 586 */     DEFAULT_VALUES.put("biotp.multiDeviceIndividualDefault", SettingEnabled.DISABLED.name());
/* 587 */     DEFAULT_VALUES.put("biotp.multiAppEnabled", SettingEnabled.DISABLED.name());
/* 588 */     DEFAULT_VALUES.put("biotp.authFailActionPolicy", AuthFailActionPolicy.NONE.name());
/* 589 */     DEFAULT_VALUES.put("biotp.overrideAutoDeregEnabled", SettingEnabled.DISABLED.name());
/* 590 */     DEFAULT_VALUES.put("biotp.issueCodeEnabled", SettingEnabled.ENABLED.name());
/* 591 */     DEFAULT_VALUES.put("biotp.issuranceExpiredEnabled", SettingEnabled.DISABLED.name());
/* 592 */     DEFAULT_VALUES.put("biotp.issuranceExpiredPeriods", Integer.toString(5));
/* 593 */     DEFAULT_VALUES.put("biotp.issuranceExpiredPeriodType", TimePeriodTypes.YEAR.name());
/* 594 */     DEFAULT_VALUES.put("biotp.tokenSharePolicy", TokenSharePolicy.BY_USER.name());
/*     */ 
/*     */     
/* 597 */     DEFAULT_VALUES.put("saotp.serverPinEnabled", SettingEnabled.ENABLED.name());
/* 598 */     DEFAULT_VALUES.put("saotp.serverPinMaxFailCnt", Integer.toString(5));
/* 599 */     DEFAULT_VALUES.put("saotp.multiDevicePolicy", MultiDevicePolicy.DISALLOWED_ALL.name());
/* 600 */     DEFAULT_VALUES.put("saotp.multiDeviceIndividualDefault", SettingEnabled.DISABLED.name());
/* 601 */     DEFAULT_VALUES.put("saotp.multiAppEnabled", SettingEnabled.DISABLED.name());
/* 602 */     DEFAULT_VALUES.put("saotp.authFailActionPolicy", AuthFailActionPolicy.NONE.name());
/* 603 */     DEFAULT_VALUES.put("saotp.overrideAutoDeregEnabled", SettingEnabled.DISABLED.name());
/* 604 */     DEFAULT_VALUES.put("saotp.issueCodeEnabled", SettingEnabled.ENABLED.name());
/* 605 */     DEFAULT_VALUES.put("saotp.issuranceExpiredEnabled", SettingEnabled.DISABLED.name());
/* 606 */     DEFAULT_VALUES.put("saotp.issuranceExpiredPeriods", Integer.toString(5));
/* 607 */     DEFAULT_VALUES.put("saotp.issuranceExpiredPeriodType", TimePeriodTypes.YEAR.name());
/* 608 */     DEFAULT_VALUES.put("saotp.tokenSharePolicy", TokenSharePolicy.BY_USER.name());
/*     */ 
/*     */     
/* 611 */     DEFAULT_VALUES.put("advanced.multiAuthMethodsEnabled", SettingEnabled.DISABLED.name());
/* 612 */     DEFAULT_VALUES.put("advanced.fidoEnabled", SettingEnabled.DISABLED.name());
/* 613 */     DEFAULT_VALUES.put("advanced.biotpEnabled", SettingEnabled.ENABLED.name());
/* 614 */     DEFAULT_VALUES.put("advanced.saotpEnabled", SettingEnabled.DISABLED.name());
/* 615 */     DEFAULT_VALUES.put("advanced.purgeDataEnabled", SettingEnabled.DISABLED.name());
/* 616 */     DEFAULT_VALUES.put("advanced.purgeCronExpression", "0 0 2 1 1 ? *");
/* 617 */     DEFAULT_VALUES.put("advanced.purgeDataLifecyle", Integer.toString(3));
/* 618 */     DEFAULT_VALUES.put("advanced.payloadParserFido", FidoPayloadParser.BASIC.name());
/* 619 */     DEFAULT_VALUES.put("advanced.payloadParserBiotp", BiotpPayloadParser.BASIC.name());
/* 620 */     DEFAULT_VALUES.put("advanced.payloadParserSaotp", SaotpPayloadParser.BASIC.name());
/* 621 */     DEFAULT_VALUES.put("advanced.otpKeybased", OTPKeyBased.PKI.name());
/* 622 */     DEFAULT_VALUES.put("advanced.serverPinPolicy", ServerPinPolicy.USER_DEFINED.name());
/* 623 */     DEFAULT_VALUES.put("advanced.serverPinValidationParser", ServerPinValidationParser.BASIC.name());
/* 624 */     DEFAULT_VALUES.put("advanced.publisherEnabled", SettingEnabled.DISABLED.name());
/* 625 */     DEFAULT_VALUES.put("advanced.publisherTarget", "http://127.0.0.1:8080");
/*     */ 
/*     */     
/* 628 */     DEFAULT_VALUES.put("externalApi.serviceType", ExternalWebApiTypes.MIRAE_ASSET_VT.name());
/* 629 */     DEFAULT_VALUES.put("externalApi.serviceSharedKey", "0C1AEB0DCD1D011BCE940BCF2C9778144D031");
/* 630 */     DEFAULT_VALUES.put("externalApi.serivceAddress", "https://192.168.1.124:8443");
/* 631 */     DEFAULT_VALUES.put("externalApi.transactionLifeTimePeriods", Integer.toString(10));
/* 632 */     DEFAULT_VALUES.put("externalApi.transactionLifeTimePeriodType", TimePeriodTypes.MINUTE.name());
/* 633 */     DEFAULT_VALUES.put("externalApi.qrCodeDimension", Integer.toString(250));
/*     */ 
/*     */     
/* 636 */     DEFAULT_VALUES.put("customKeys.countryCode", "AU,BH,BD,BR,KH,CN,HK,IN,ID,IR,JP,MY,MM,PH,PL,RU,SG,GB,US");
/*     */   }
/*     */   
/*     */   private static String getDefaultValue(String key) {
/* 640 */     return DEFAULT_VALUES.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void receivedSyncCacheChanged(SyncCacheTypes type) {
/* 645 */     if (SyncCacheTypes.SYSTEM_ENV.equals(type)) {
/* 646 */       CACHE.clear();
/* 647 */       ChannelBindingTimerTask.refeshChannelBinding();
/* 648 */       Commons.ctx.getSyncCachePublisher().refreshPublisher();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> getCountryCodeRegistered() {
/* 655 */     String str = getValue("customKeys.countryCode");
/*     */     
/* 657 */     String[] arr = str.split(",");
/* 658 */     List<String> registered = new ArrayList<>();
/*     */     
/* 660 */     for (String code : arr) {
/* 661 */       registered.add(code);
/*     */     }
/*     */     
/* 664 */     return registered;
/*     */   }
/*     */   
/*     */   public static String[] getArrayValue(String key) {
/* 668 */     String str = getValue(key);
/*     */     
/* 670 */     if (StringUtils.isEmpty(str)) {
/* 671 */       return null;
/*     */     }
/*     */     
/* 674 */     String[] arr = str.split(",");
/*     */     
/* 676 */     return arr;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\SystemSettingsDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */