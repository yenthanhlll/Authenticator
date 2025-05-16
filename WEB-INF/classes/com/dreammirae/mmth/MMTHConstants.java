/*     */ package WEB-INF.classes.com.dreammirae.mmth;
/*     */ 
/*     */ import com.dreammirae.mmth.fido.registry.UserVerificationMethods;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MMTHConstants
/*     */ {
/*     */   public static final String SESSION_KEY_CURRENT_ADMIN = "MMTH.ADMIN";
/*     */   public static final String SESSION_KEY_CURRENT_LOGON_DATETIME = "MMTH.LOGON.DATETIME";
/*     */   public static final String SESSION_KEY_CURRENT_ADMIN_TYPE = "MMTH.ADMIN.TYPE";
/*     */   public static final String SESSION_KEY_CURRENT_ADMIN_REG_DT = "MMTH.ADMIN.REG";
/*     */   public static final String SESSION_KEY_CURRENT_ADMIN_UPDATE_DT = "MMTH.ADMIN.UPDATE";
/*     */   public static final String SESSION_KEY_CURRENT_ADMIN_USERNAME = "MMTH.ADMIN.USERNAME";
/*     */   public static final String SESSION_KEY_CURRENT_ADMIN_HOME_URL = "MMTH.ADMIN.HOME_URL";
/*     */   public static final String SESSION_KEY_CURRENT_ADMIN_LAST_REMOTE_ADDR = "MMTH.ADMIN.LAST_REMOTE_ADDR";
/*     */   public static final String DIR_TTK_TEMP = "/ttk";
/*  26 */   public static final String[] ACCEPTED_TTK_EXTS = new String[] { "ttk", "TTK" };
/*     */   
/*     */   public static final String ASCII = "US-ASCII";
/*  29 */   public static final Charset ASCII_CS = Charset.forName("US-ASCII");
/*     */   
/*     */   public static final String ISO_8859_1 = "ISO-8859-1";
/*  32 */   public static final Charset ISO_8859_1_CS = Charset.forName("ISO-8859-1");
/*     */   
/*     */   public static final String MS949 = "MS949";
/*  35 */   public static final Charset MS949_CS = Charset.forName("MS949");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String BLANK = "-";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MAX_LEN_VIEW_ALIAS = 40;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MAX_LEN_VIEW_DESC = 170;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MAX_LEN_ALIAS_BYTES = 128;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MAX_LEN_DESC_BYTES = 1024;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String REGEX_KOREAN = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String REGEX_PKGNAME = "^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z0-9_]+)+[a-zA-Z0-9_]$";
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static final Pattern REGEX_PKGNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z0-9_]+)+[a-zA-Z0-9_]$");
/*     */   
/*     */   public static final String REGEX_ANY_NUMBER = "^[0-9]+$";
/*     */   
/*  78 */   public static final Pattern REGEX_ANY_NUMBER_PATTERN = Pattern.compile("^[0-9]+$");
/*     */   
/*     */   public static final String REGEX_OTP_NUMBER = "^[0-9]{6}$";
/*     */   
/*  82 */   public static final Pattern REGEX_OTP_PATTERN = Pattern.compile("^[0-9]{6}$");
/*     */   
/*     */   public static final String REGEX_ISSUECODE_NUMBER = "^[0-9]{5}$";
/*     */   
/*  86 */   public static final Pattern REGEX_ISSUE_CODE_PATTERN = Pattern.compile("^[0-9]{5}$");
/*     */   
/*     */   public static final String REGEX_HEX = "^[a-fA-F0-9]+$";
/*     */   
/*  90 */   public static final Pattern REGEX_HEX_PATTERN = Pattern.compile("^[a-fA-F0-9]+$");
/*     */   
/*     */   public static final String REGEX_HEX_LEN_64 = "^[a-fA-F0-9]{64}$";
/*     */   
/*  94 */   public static final Pattern REGEX_HEX_LEN_64_PATTERN = Pattern.compile("^[a-fA-F0-9]{64}$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String REGEX_USERNAME = "^[a-zA-Z0-9]{1}[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{3,120}$";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static final Pattern REGEX_USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{1}[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{3,120}$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String REGEX_PASSWORD = "^[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{5,120}$";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public static final Pattern REGEX_PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{5,120}$");
/*     */ 
/*     */   
/*     */   public static final String REGEX_DEVICE_ID = "^[A-Z0-9]{64}$";
/*     */   
/* 125 */   public static final Pattern REGEX_DEVICE_ID_PATTERN = Pattern.compile("^[A-Z0-9]{64}$");
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int LEN_KOREAN_UNIT_UTF8 = 3;
/*     */ 
/*     */   
/*     */   public static final int LEN_TID = 32;
/*     */ 
/*     */   
/*     */   public static final int MAX_BATCH_SIZE = 128;
/*     */ 
/*     */   
/* 138 */   public static final Map<UserVerificationMethods, String> USER_VERIFICATION_METHODS_MSG_KEYS = new HashMap<>();
/*     */ 
/*     */   
/*     */   static {
/* 142 */     for (UserVerificationMethods uvm : UserVerificationMethods.values())
/* 143 */       USER_VERIFICATION_METHODS_MSG_KEYS.put(uvm, "UserVerificationMethods." + uvm.name()); 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\MMTHConstants.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */