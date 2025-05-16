/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport;
/*     */ 
/*     */ import org.joda.time.format.DateTimeFormat;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TransportKeys
/*     */ {
/*  14 */   public static final DateTimeFormatter DISPLAY_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String RETURN_CODE = "returnCode";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String USERNAME = "userName";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEVICE_ID = "deviceId";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEVICE_TYPE = "deviceType";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String MODEL = "model";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String APP_DISTINCT_CODE = "appDistinctCode";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String ALIAS = "alias";
/*     */ 
/*     */   
/*     */   public static final String OTP_PUB = "otpPub";
/*     */ 
/*     */   
/*     */   public static final String RND_SEED_KEY = "rndSeedKey";
/*     */ 
/*     */   
/*     */   public static final String RND_CHALLENGE = "rndChallenge";
/*     */ 
/*     */   
/*     */   public static final String TRAN_INFO = "tranInfo";
/*     */ 
/*     */   
/*     */   public static final String ENC_KEY = "encKey";
/*     */ 
/*     */   
/*     */   public static final String ENC_DATA = "encData";
/*     */ 
/*     */   
/*     */   public static final String ENC_TID = "encTid";
/*     */ 
/*     */   
/*     */   public static final String ENC_RNDSEEDKEY = "encRndSeedKey";
/*     */ 
/*     */   
/*     */   public static final String ENC_TOKEN = "encToken";
/*     */ 
/*     */   
/*     */   public static final String REFERENCES = "references";
/*     */ 
/*     */   
/*     */   public static final String SERVER_PIN = "serverPin";
/*     */ 
/*     */   
/*     */   public static final String NEW_SERVER_PIN = "newServerPin";
/*     */ 
/*     */   
/*     */   public static final String OTP = "otp";
/*     */ 
/*     */   
/*     */   public static final String TID = "tid";
/*     */ 
/*     */   
/*     */   public static final String ISSUE_CODE = "issueCode";
/*     */ 
/*     */   
/*     */   public static final String Y = "Y";
/*     */ 
/*     */   
/*     */   public static final String N = "N";
/*     */ 
/*     */   
/*     */   public static final String DEVICE_CNT = "deviceCnt";
/*     */ 
/*     */   
/*     */   public static final String DEVICES = "devices";
/*     */ 
/*     */   
/*     */   public static final String DISABLED = "disabled";
/*     */ 
/*     */   
/*     */   public static final String REG_DATE_TIME = "regDateTime";
/*     */ 
/*     */   
/*     */   public static final String UPDATED_DATE_TIME = "updatedDateTime";
/*     */ 
/*     */   
/*     */   public static final String EXPIRED_DATE_TIME = "expiredDateTime";
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toDateTime(long ts) {
/* 116 */     return DISPLAY_DATE_TIME_FORMATTER.print(ts);
/*     */   }
/*     */   
/*     */   public static long fromDateTime(String dateTime) {
/* 120 */     return DISPLAY_DATE_TIME_FORMATTER.parseMillis(dateTime);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\TransportKeys.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */