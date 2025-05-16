/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.AdministratorTypes;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*     */ import com.fasterxml.jackson.annotation.JsonIgnore;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import org.springframework.security.core.GrantedAuthority;
/*     */ import org.springframework.security.core.userdetails.UserDetails;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdministratorVO
/*     */   implements Serializable, UserDetails, IRestValidator
/*     */ {
/*     */   public static final String DEFAULT_HOME_URL = "/web/manager/user";
/*     */   public static final String BRANCH_HOME_URL = "/web/manager/branch";
/*  70 */   private int id = -1;
/*     */   
/*     */   private String username;
/*     */   
/*     */   @JsonIgnore
/*     */   private String password;
/*     */   
/*     */   private AdministratorTypes adminType;
/*     */   
/*     */   private DisabledStatus disabled;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsLastLogin;
/*     */   
/*     */   private String lastRemoteAddr;
/*     */   
/*     */   private String homeUrl;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsReg;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsUpdated;
/*     */   
/*     */   private Locale locale;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static final int version = 3;
/*     */ 
/*     */   
/*     */   public AdministratorVO() {}
/*     */   
/*     */   public AdministratorVO(String username) {
/* 104 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 111 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/* 119 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/* 126 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/* 134 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPassword() {
/* 141 */     return this.password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPassword(String password) {
/* 149 */     this.password = password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AdministratorTypes getAdminType() {
/* 156 */     return this.adminType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAdminType(AdministratorTypes adminType) {
/* 164 */     this.adminType = adminType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisabledStatus getDisabled() {
/* 171 */     return this.disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisabled(DisabledStatus disabled) {
/* 179 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsLastLogin() {
/* 186 */     return this.tsLastLogin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsLastLogin(long tsLastLogin) {
/* 194 */     this.tsLastLogin = tsLastLogin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLastRemoteAddr() {
/* 201 */     return this.lastRemoteAddr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastRemoteAddr(String lastRemoteAddr) {
/* 209 */     this.lastRemoteAddr = lastRemoteAddr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHomeUrl() {
/* 216 */     return this.homeUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHomeUrl(String homeUrl) {
/* 224 */     this.homeUrl = homeUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 231 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 239 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsUpdated() {
/* 246 */     return this.tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsUpdated(long tsUpdated) {
/* 254 */     this.tsUpdated = tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 262 */     return this.locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocale(Locale locale) {
/* 269 */     this.locale = locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public DisabledStatus getDisabledDesc() {
/* 279 */     return this.disabled;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AdministratorTypes getAdminTypeDesc() {
/* 284 */     return this.adminType;
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
/*     */   public static com.dreammirae.mmth.vo.AdministratorVO createAdministrationVO(String username, String password, AdministratorTypes adminType) {
/* 297 */     com.dreammirae.mmth.vo.AdministratorVO vo = new com.dreammirae.mmth.vo.AdministratorVO();
/* 298 */     vo.setUsername(username);
/* 299 */     vo.setPassword(password);
/* 300 */     vo.setAdminType(adminType);
/* 301 */     vo.setDisabled(DisabledStatus.ENABLED);
/* 302 */     vo.setLastRemoteAddr("-");
/* 303 */     vo.setLocale(SystemSettingsDao.getSystemLocale());
/*     */     
/* 305 */     if (AdministratorTypes.DEV.equals(adminType)) {
/* 306 */       vo.setHomeUrl("/web/manager/systemSettings");
/* 307 */     } else if (AdministratorTypes.OPTEAM.equals(adminType)) {
/* 308 */       vo.setHomeUrl("/web/manager/branch");
/* 309 */     } else if (AdministratorTypes.CSTEAM.equals(adminType)) {
/* 310 */       vo.setHomeUrl("/web/manager/branch");
/*     */     } else {
/* 312 */       vo.setHomeUrl("/web/manager/user");
/*     */     } 
/*     */     
/* 315 */     return vo;
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
/*     */   public void validate(RestResponse resp) {
/* 330 */     if (StringUtils.isEmpty(this.username)) {
/* 331 */       resp.addContextMessage("username", new I18nMessage("validate.required"));
/* 332 */     } else if (!this.username.matches("^[a-zA-Z0-9]{1}[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{3,120}$")) {
/* 333 */       resp.addContextMessage("username", new I18nMessage("validate.regex.username"));
/*     */ 
/*     */     
/*     */     }
/* 337 */     else if (-1 == this.id) {
/* 338 */       if (StringUtils.isEmpty(this.password)) {
/* 339 */         resp.addContextMessage("password", new I18nMessage("validate.required"));
/* 340 */       } else if (!this.password.matches("^[a-zA-Z0-9\\!\\@\\#\\?\\-\\_\\.]{5,120}$")) {
/* 341 */         resp.addContextMessage("password", new I18nMessage("validate.regex.password"));
/* 342 */       } else if (this.password.equalsIgnoreCase(this.username)) {
/* 343 */         resp.addContextMessage("password", new I18nMessage("validate.duplicateArgAndArg", new Object[] { new I18nMessage("administrator.label.password"), new I18nMessage("administrator.label.username") }));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 349 */     if (this.adminType == null) {
/* 350 */       this.adminType = AdministratorTypes.ADMIN;
/*     */     }
/* 352 */     if (this.disabled == null) {
/* 353 */       this.disabled = DisabledStatus.ENABLED;
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
/*     */   public Collection<? extends GrantedAuthority> getAuthorities() {
/* 370 */     return this.adminType.getAuthorities();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAccountNonExpired() {
/* 381 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAccountNonLocked() {
/* 392 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCredentialsNonExpired() {
/* 403 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 414 */     return DisabledStatus.ENABLED.equals(this.disabled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 423 */     StringBuilder builder = new StringBuilder();
/* 424 */     builder.append("AdministratorVO [id=").append(this.id).append(", username=").append(this.username).append(", password=").append(this.password).append(", adminType=").append(this.adminType).append(", disabled=")
/* 425 */       .append(this.disabled).append(", tsLastLogin=").append(this.tsLastLogin).append(", lastRemoteAddr=").append(this.lastRemoteAddr).append(", homeUrl=").append(this.homeUrl).append(", tsReg=").append(this.tsReg)
/* 426 */       .append(", tsUpdated=").append(this.tsUpdated).append(", locale=").append(this.locale).append("]");
/* 427 */     return builder.toString();
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
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 439 */     out.writeInt(3);
/*     */ 
/*     */     
/* 442 */     SerializationUtils.writeSafeObject(out, this.locale);
/*     */ 
/*     */     
/* 445 */     SerializationUtils.writeSafeUTF(out, this.username);
/* 446 */     SerializationUtils.writeSafeUTF(out, this.adminType.getCode());
/* 447 */     SerializationUtils.writeSafeUTF(out, this.disabled.getCode());
/* 448 */     SerializationUtils.writeSafeUTF(out, this.lastRemoteAddr);
/* 449 */     SerializationUtils.writeSafeUTF(out, this.homeUrl);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 454 */     int ver = in.readInt();
/* 455 */     if (ver == 1) {
/* 456 */       this.locale = SystemSettingsDao.getSystemLocale();
/* 457 */     } else if (ver == 2) {
/* 458 */       this.locale = (Locale)SerializationUtils.readSafeObject(in);
/* 459 */     } else if (ver == 3) {
/* 460 */       this.locale = (Locale)SerializationUtils.readSafeObject(in);
/* 461 */       this.username = SerializationUtils.readSafeUTF(in);
/* 462 */       this.adminType = AdministratorTypes.getAdministratorType(SerializationUtils.readSafeUTF(in, AdministratorTypes.ADMIN.getCode()));
/* 463 */       this.disabled = DisabledStatus.getDisabledStatus(SerializationUtils.readSafeUTF(in, DisabledStatus.ENABLED.getCode()));
/* 464 */       this.lastRemoteAddr = SerializationUtils.readSafeUTF(in);
/* 465 */       this.homeUrl = SerializationUtils.readSafeUTF(in);
/*     */     } 
/*     */     
/* 468 */     if (this.locale == null)
/* 469 */       this.locale = Locale.KOREAN; 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\AdministratorVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */