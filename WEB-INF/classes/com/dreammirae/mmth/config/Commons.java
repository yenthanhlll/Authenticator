/*     */ package WEB-INF.classes.com.dreammirae.mmth.config;
/*     */ 
/*     */ import com.dreammirae.mmth.config.ContextWrapper;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.misc.MessageUtils;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.runtime.code.TimePeriodTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.bean.ExportCodes;
/*     */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Timer;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.format.DateTimeFormat;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ import org.springframework.context.MessageSource;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Commons
/*     */ {
/*     */   public static ContextWrapper ctx;
/*     */   public static Timer timer;
/*     */   public static final String NULL_TIME = "000000";
/*  35 */   public static final Charset UTF8_CS = SysEnvCommon.UTF8_CS;
/*  36 */   public static final Charset ISO_8859_1_CS = Charset.forName("ISO-8859-1");
/*     */   
/*     */   public static final int NEW_ID = -1;
/*     */   public static final long NEW_ID_LONG = -1L;
/*     */   public static final String BOOL_Y = "Y";
/*     */   public static final String BOOL_N = "N";
/*  42 */   public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern("yyyyMM");
/*  43 */   public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd");
/*  44 */   public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HHmmss");
/*  45 */   public static final DateTimeFormatter DATE_HOUR_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHH");
/*     */ 
/*     */   
/*  48 */   public static final DateTimeFormatter DISPLAY_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
/*  49 */   public static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
/*     */ 
/*     */   
/*     */   private static final String NULL_DATE = "00000000";
/*     */ 
/*     */   
/*     */   private static final String XID_DELIMITER = "_";
/*     */   
/*     */   public static final long TORELANCE_TS = 180000L;
/*     */   
/*  59 */   private static final ExportCodes TIME_PERIOD_TYPE_CODES = new ExportCodes(9);
/*     */   static {
/*  61 */     TIME_PERIOD_TYPE_CODES.addElement(TimePeriodTypes.MILLIS.getKey(), TimePeriodTypes.MILLIS.name(), "common.tp.MILLIS");
/*  62 */     TIME_PERIOD_TYPE_CODES.addElement(TimePeriodTypes.SECOND.getKey(), TimePeriodTypes.SECOND.name(), "common.tp.SECOND");
/*  63 */     TIME_PERIOD_TYPE_CODES.addElement(TimePeriodTypes.MINUTE.getKey(), TimePeriodTypes.MINUTE.name(), "common.tp.MINUTE");
/*  64 */     TIME_PERIOD_TYPE_CODES.addElement(TimePeriodTypes.HOUR.getKey(), TimePeriodTypes.HOUR.name(), "common.tp.HOUR");
/*  65 */     TIME_PERIOD_TYPE_CODES.addElement(TimePeriodTypes.DAY.getKey(), TimePeriodTypes.DAY.name(), "common.tp.DAY");
/*  66 */     TIME_PERIOD_TYPE_CODES.addElement(TimePeriodTypes.WEEK.getKey(), TimePeriodTypes.WEEK.name(), "common.tp.WEEK");
/*  67 */     TIME_PERIOD_TYPE_CODES.addElement(TimePeriodTypes.MONTH.getKey(), TimePeriodTypes.MONTH.name(), "common.tp.MONTH");
/*  68 */     TIME_PERIOD_TYPE_CODES.addElement(TimePeriodTypes.YEAR.getKey(), TimePeriodTypes.YEAR.name(), "common.tp.YEAR");
/*  69 */     TIME_PERIOD_TYPE_CODES.addElement(TimePeriodTypes.CRON.getKey(), TimePeriodTypes.CRON.name(), "common.tp.CRON");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final List<KeyValuePair<String, String>> LANG_SUPPORTED = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public static ResourceBundle getEnvProperties() {
/*     */     try {
/*  79 */       return ResourceBundle.getBundle("conf/env");
/*  80 */     } catch (MissingResourceException e) {
/*  81 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceBundle getResourceBundle(String bundleName) {
/*  87 */     Assert.notNull(bundleName);
/*     */     
/*     */     try {
/*  90 */       return ResourceBundle.getBundle(bundleName);
/*  91 */     } catch (MissingResourceException e) {
/*  92 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<KeyValuePair<String, String>> getSupportedLangs() {
/*  98 */     if (LANG_SUPPORTED.isEmpty()) {
/*  99 */       ResourceBundle bundle = getResourceBundle("i18n");
/*     */       
/* 101 */       for (String key : bundle.keySet()) {
/* 102 */         String value = bundle.getString(key);
/* 103 */         String utfVal = new String(value.getBytes(ISO_8859_1_CS), UTF8_CS);
/*     */ 
/*     */         
/* 106 */         LANG_SUPPORTED.add(new KeyValuePair(key, utfVal));
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return LANG_SUPPORTED;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getValue(ResourceBundle bundle, String key, String defaultValue) {
/* 115 */     if (bundle == null) {
/* 116 */       return defaultValue;
/*     */     }
/*     */     
/* 119 */     if (!bundle.containsKey(key)) {
/* 120 */       return defaultValue;
/*     */     }
/* 122 */     String val = bundle.getString(key);
/*     */     
/* 124 */     if (StringUtils.isEmpty(val)) {
/* 125 */       return defaultValue;
/*     */     }
/*     */     
/* 128 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDecryptValue(ResourceBundle bundle, String key, String defaultValue) {
/* 134 */     if (bundle == null) {
/* 135 */       return defaultValue;
/*     */     }
/*     */     
/* 138 */     if (!bundle.containsKey(key)) {
/* 139 */       return defaultValue;
/*     */     }
/* 141 */     String val = bundle.getString(key);
/*     */     
/* 143 */     if (StringUtils.isEmpty(val)) {
/* 144 */       return defaultValue;
/*     */     }
/*     */     
/* 147 */     return SysEnvCommon.decryptText(val);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFormatMonth(long ts) {
/* 152 */     return MONTH_FORMATTER.print(ts);
/*     */   }
/*     */   
/*     */   public static String getFormatMonth(DateTime dt) {
/* 156 */     return MONTH_FORMATTER.print((ReadableInstant)dt);
/*     */   }
/*     */   
/*     */   public static String getFormatDate(long ts) {
/* 160 */     if (ts == 0L) {
/* 161 */       return "00000000";
/*     */     }
/*     */     
/* 164 */     return DATE_FORMATTER.print(ts);
/*     */   }
/*     */   
/*     */   public static String getFormatDate(DateTime dt) {
/* 168 */     if (dt == null) {
/* 169 */       return "00000000";
/*     */     }
/* 171 */     return DATE_FORMATTER.print((ReadableInstant)dt);
/*     */   }
/*     */   
/*     */   public static String getFormatDateHour(long ts) {
/* 175 */     return DATE_HOUR_FORMATTER.print(ts);
/*     */   }
/*     */   
/*     */   public static String getFormatDateHour(DateTime dt) {
/* 179 */     return DATE_HOUR_FORMATTER.print((ReadableInstant)dt);
/*     */   }
/*     */   
/*     */   public static String getFormatTime(long ts) {
/* 183 */     if (ts == 0L) {
/* 184 */       return "000000";
/*     */     }
/*     */     
/* 187 */     return TIME_FORMATTER.print(ts);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFormatTime(DateTime dt) {
/* 192 */     if (dt == null) {
/* 193 */       return "000000";
/*     */     }
/*     */     
/* 196 */     return TIME_FORMATTER.print((ReadableInstant)dt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMessage(MessageSource source, String messageKey, Object[] args) {
/* 203 */     return source.getMessage(messageKey, args, Locale.getDefault());
/*     */   }
/*     */   
/*     */   public static String getSysMessage(I18nMessage message) {
/* 207 */     return MessageUtils.getMessage(message, SystemSettingsDao.getSystemLocale());
/*     */   }
/*     */   
/*     */   public static long getTorelanceLifeTimeTs() {
/* 211 */     return getTorelanceLifeTimeTs(180000L);
/*     */   }
/*     */   
/*     */   public static long getStartTsFromCurrent() {
/* 215 */     return getStartTsFromCurrent(System.currentTimeMillis());
/*     */   }
/*     */   
/*     */   public static long getTorelanceLifeTimeTs(long lifetimeDuration) {
/* 219 */     return System.currentTimeMillis() + lifetimeDuration;
/*     */   }
/*     */   
/*     */   public static long getStartTsFromCurrent(long lifetimeDuration) {
/* 223 */     return System.currentTimeMillis() - lifetimeDuration;
/*     */   }
/*     */   
/*     */   public static ExportCodes getTimePeriodExportCodes() {
/* 227 */     return TIME_PERIOD_TYPE_CODES;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String generateRandomString(String prefix) {
/* 232 */     byte[] current = SysEnvCommon.currentMillisBytes();
/* 233 */     byte[] random = SysEnvCommon.randomBytes(2);
/*     */     
/* 235 */     current[6] = random[0];
/* 236 */     current[7] = random[1];
/*     */ 
/*     */     
/* 239 */     return StringUtils.concat(new Object[] { prefix, "_" + HexUtils.toHexString(current) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static long calcExpired(long tsBase, long duration) {
/* 244 */     DateTime dt = new DateTime(tsBase + duration);
/*     */ 
/*     */     
/* 247 */     dt = dt.withMillisOfDay(0).minus(1L);
/*     */     
/* 249 */     return dt.getMillis();
/*     */   }
/*     */   
/*     */   public static String displayDateTime(long timestamp) {
/* 253 */     return (new DateTime(timestamp)).toString(DISPLAY_DATE_TIME_FORMATTER);
/*     */   }
/*     */   
/*     */   public static String displayDate(long timestamp) {
/* 257 */     return (new DateTime(timestamp)).toString(DISPLAY_DATE_FORMATTER);
/*     */   }
/*     */   
/*     */   public static long displayDateTime(String timestamp) {
/* 261 */     return DISPLAY_DATE_TIME_FORMATTER.parseMillis(timestamp);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\config\Commons.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */