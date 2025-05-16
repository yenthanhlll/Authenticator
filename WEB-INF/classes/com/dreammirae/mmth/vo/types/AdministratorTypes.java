/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*     */ 
/*     */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.springframework.security.core.GrantedAuthority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum AdministratorTypes
/*     */ {
/*  14 */   DEV("1", "AdministratorTypes.DEV"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  21 */   SUPER("2", "AdministratorTypes.SUPER"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   ADMIN("3", "AdministratorTypes.ADMIN"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   PERMITTED("4", "AdministratorTypes.PERMITTED"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   OPTEAM("5", "AdministratorTypes.OPTEAM"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   CSTEAM("6", "AdministratorTypes.CSTEAM");
/*     */ 
/*     */   
/*     */   private final String code;
/*     */ 
/*     */   
/*     */   private final String messageKey;
/*     */ 
/*     */ 
/*     */   
/*     */   AdministratorTypes(String code, String messageKey) {
/*  60 */     this.code = code;
/*  61 */     this.messageKey = messageKey;
/*     */   }
/*     */   
/*     */   public String getCode() {
/*  65 */     return this.code;
/*     */   }
/*     */   
/*     */   public String getMessageKey() {
/*  69 */     return this.messageKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.vo.types.AdministratorTypes getAdministratorType(String code) {
/*  74 */     for (com.dreammirae.mmth.vo.types.AdministratorTypes type : values()) {
/*  75 */       if (type.code.equals(code)) {
/*  76 */         return type;
/*     */       }
/*     */     } 
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<KeyValuePair<String, String>> getMessageKeyPair(com.dreammirae.mmth.vo.types.AdministratorTypes... excludes) {
/*  84 */     List<KeyValuePair<String, String>> list = new ArrayList<>(4);
/*     */     
/*  86 */     for (com.dreammirae.mmth.vo.types.AdministratorTypes type : values()) {
/*     */       
/*  88 */       com.dreammirae.mmth.vo.types.AdministratorTypes[] arrayOfAdministratorTypes = excludes; int i = arrayOfAdministratorTypes.length; byte b = 0; while (true) { if (b < i) { com.dreammirae.mmth.vo.types.AdministratorTypes ex = arrayOfAdministratorTypes[b];
/*  89 */           if (type.equals(ex))
/*     */             break; 
/*     */           b++;
/*     */           continue; }
/*     */         
/*  94 */         list.add(new KeyValuePair(type.name(), type.getMessageKey())); break; }
/*     */     
/*     */     } 
/*  97 */     return list;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.types.AdministratorTypes getAdministratorTypeByName(String name) {
/* 101 */     for (com.dreammirae.mmth.vo.types.AdministratorTypes type : values()) {
/* 102 */       if (type.name().equalsIgnoreCase(name)) {
/* 103 */         return type;
/*     */       }
/*     */     } 
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   public static String[] exportCodes() {
/* 110 */     return new String[] { DEV.name(), SUPER.name(), ADMIN.name(), PERMITTED.name() };
/*     */   }
/*     */   
/*     */   public abstract Collection<? extends GrantedAuthority> getAuthorities();
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\AdministratorTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */