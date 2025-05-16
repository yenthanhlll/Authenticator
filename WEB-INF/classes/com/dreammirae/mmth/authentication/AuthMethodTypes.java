/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.policy.MultiDevicePolicy;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*     */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.SettingEnabled;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum AuthMethodTypes
/*     */ {
/*  22 */   FIDO(1, "AuthMethodTypes.FIDO"),
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
/*  59 */   SAOTP(2, "AuthMethodTypes.SAOTP"),
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
/*  96 */   BIOTP(4, "AuthMethodTypes.BIOTP"),
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
/* 134 */   MATRIX(8, "AuthMethodTypes.MATRIX"),
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
/* 179 */   PIN(22, "AuthMethodTypes.PIN"),
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
/* 224 */   HWOTP(50, "AuthMethodTypes.HWOTP"),
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
/* 268 */   MIRAEASSET(100, "AuthMethodTypes.MIRAEASSET");
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
/*     */   private final int code;
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
/*     */   private final String messageKey;
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
/*     */   AuthMethodTypes(int code, String messageKey) {
/* 318 */     this.code = code;
/* 319 */     this.messageKey = messageKey;
/*     */   }
/*     */   
/*     */   public int getCode() {
/* 323 */     return this.code;
/*     */   }
/*     */   
/*     */   public String getMessageKey() {
/* 327 */     return this.messageKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateMultiDevice(UserVO user) throws ReturnCodeException {
/* 332 */     if (user == null) {
/* 333 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The username is required.");
/*     */     }
/*     */     
/* 336 */     if (UserStatus.AVAILABLE.equals(user.getStatus())) {
/*     */       
/* 338 */       if (MultiDevicePolicy.DISALLOWED_ALL.equals(getMultiDevicePolicy()))
/* 339 */         throw new ReturnCodeException(ReturnCodes.EXIST_ALREADY, "The user [" + user
/* 340 */             .getUsername() + "] is allowed only one device. User must deregister previous registrations.  before registering any device."); 
/* 341 */       if (MultiDevicePolicy.INDIVIDUAL.equals(getMultiDevicePolicy()) && 
/* 342 */         !user.getMultiDeviceEnabled().toBoolean()) {
/* 343 */         throw new ReturnCodeException(ReturnCodes.EXIST_ALREADY, "The user [" + user
/* 344 */             .getUsername() + "] is allowed only one device. User must deregister previous registrations.  before registering any device.");
/*     */       }
/*     */     } 
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
/*     */   public boolean isAllowedMultiDevice() {
/* 365 */     MultiDevicePolicy policy = getMultiDevicePolicy();
/* 366 */     return MultiDevicePolicy.ALLOWED_ALL.equals(policy);
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.authentication.AuthMethodTypes getAuthMethodTypes(int code) {
/* 370 */     for (com.dreammirae.mmth.authentication.AuthMethodTypes type : values()) {
/* 371 */       if (type.code == code) {
/* 372 */         return type;
/*     */       }
/*     */     } 
/*     */     
/* 376 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<KeyValuePair<String, String>> getMessageKeyPair(com.dreammirae.mmth.authentication.AuthMethodTypes... excludes) {
/* 381 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*     */     
/* 383 */     for (com.dreammirae.mmth.authentication.AuthMethodTypes type : values()) {
/*     */       
/* 385 */       com.dreammirae.mmth.authentication.AuthMethodTypes[] arrayOfAuthMethodTypes = excludes; int i = arrayOfAuthMethodTypes.length; byte b = 0; while (true) { if (b < i) { com.dreammirae.mmth.authentication.AuthMethodTypes exclude = arrayOfAuthMethodTypes[b];
/* 386 */           if (type.equals(exclude))
/*     */             break; 
/*     */           b++;
/*     */           continue; }
/*     */         
/* 391 */         list.add(new KeyValuePair(type.name(), type.getMessageKey())); break; }
/*     */     
/*     */     } 
/* 394 */     return list;
/*     */   }
/*     */   
/*     */   private static boolean isEnabled(String key) {
/* 398 */     SettingEnabled enabled = SystemSettingsDao.getSettingEnabled(key);
/* 399 */     return enabled.toBoolean();
/*     */   }
/*     */   
/*     */   private static long getPeriods(String typeKey, String periodKey) {
/* 403 */     return SystemSettingsDao.getPeriods(typeKey, periodKey);
/*     */   }
/*     */   
/*     */   public abstract boolean enabledIssueCode();
/*     */   
/*     */   public abstract MultiDevicePolicy getMultiDevicePolicy();
/*     */   
/*     */   public abstract boolean enabledMulitAppAgent();
/*     */   
/*     */   public abstract boolean enabledExpired();
/*     */   
/*     */   public abstract long getTsExpired(long paramLong);
/*     */   
/*     */   public abstract boolean enabledDeregAndOverriding();
/*     */   
/*     */   public abstract IUserServiceMessageParser getUserServiceMessageParser();
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\AuthMethodTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */