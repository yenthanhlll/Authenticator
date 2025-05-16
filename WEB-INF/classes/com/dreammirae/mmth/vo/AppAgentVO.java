/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AppAgentVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*     */   private static final int LEN_MAX_ALIAS = 40;
/*     */   private static final int LEN_MAX_DESC = 170;
/*     */   private static final int LEN_MAX_PKG_NAME = 255;
/*  64 */   private int id = -1;
/*     */ 
/*     */   
/*     */   private String pkgUnique;
/*     */ 
/*     */   
/*     */   private String alias;
/*     */ 
/*     */   
/*     */   private DisabledStatus disabled;
/*     */ 
/*     */   
/*     */   private AgentOsTypes osType;
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsReg;
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsUpdated;
/*     */ 
/*     */   
/*     */   private String description;
/*     */   
/*     */   private String fcmAuthenticationKey;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static final int version = 1;
/*     */ 
/*     */   
/*     */   public int getId() {
/*  97 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/* 104 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPkgUnique() {
/* 111 */     return this.pkgUnique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPkgUnique(String pkgUnique) {
/* 118 */     this.pkgUnique = pkgUnique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlias() {
/* 125 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlias(String alias) {
/* 132 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisabledStatus getDisabled() {
/* 139 */     return this.disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisabled(DisabledStatus disabled) {
/* 146 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AgentOsTypes getOsType() {
/* 153 */     return this.osType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOsType(AgentOsTypes osType) {
/* 160 */     this.osType = osType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 167 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 174 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsUpdated() {
/* 181 */     return this.tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsUpdated(long tsUpdated) {
/* 188 */     this.tsUpdated = tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 195 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/* 202 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFcmAuthenticationKey() {
/* 209 */     return this.fcmAuthenticationKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFcmAuthenticationKey(String fcmAuthenticationKey) {
/* 216 */     this.fcmAuthenticationKey = fcmAuthenticationKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public DisabledStatus getDisabledDesc() {
/* 225 */     return this.disabled;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AgentOsTypes getOsTypeDesc() {
/* 230 */     return this.osType;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.AppAgentVO createAppAgentVO(String pkgUnique, AgentOsTypes osType, String alias, String fcmAuthenticationKey) {
/* 234 */     com.dreammirae.mmth.vo.AppAgentVO vo = new com.dreammirae.mmth.vo.AppAgentVO();
/* 235 */     vo.setPkgUnique(pkgUnique);
/* 236 */     vo.setOsType(osType);
/* 237 */     vo.setAlias(alias);
/* 238 */     vo.setFcmAuthenticationKey(fcmAuthenticationKey);
/* 239 */     vo.setDisabled(DisabledStatus.ENABLED);
/* 240 */     vo.setDescription("-");
/* 241 */     return vo;
/*     */   }
/*     */   
/*     */   public static void validatePkgUnique(String pkgUnique) throws ReturnCodeException {
/* 245 */     if (StringUtils.isEmpty(pkgUnique)) {
/* 246 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.requiredParam", new Object[] { "pkgName" }));
/*     */     }
/* 248 */     if (pkgUnique.length() > 255)
/* 249 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.invalidParam", new Object[] { "pkgName", new I18nMessage("validate.maxChar", new Object[] {
/* 250 */                   Integer.valueOf(255) }) })); 
/* 251 */     if (!pkgUnique.matches("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z0-9_]+)+[a-zA-Z0-9_]$")) {
/* 252 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.invalidParam", new Object[] { "pkgName", new I18nMessage("validate.regex.pkgUnique") }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(RestResponse resp) {
/* 260 */     if (StringUtils.isEmpty(this.pkgUnique)) {
/* 261 */       resp.addContextMessage("pkgUnique", new I18nMessage("validate.required"));
/* 262 */     } else if (this.pkgUnique.length() > 255) {
/* 263 */       resp.addContextMessage("pkgUnique", new I18nMessage("validate.maxChar", new Object[] { Integer.valueOf(255) }));
/* 264 */     } else if (!this.pkgUnique.matches("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z0-9_]+)+[a-zA-Z0-9_]$")) {
/* 265 */       resp.addContextMessage("pkgUnique", new I18nMessage("validate.regex.pkgUnique"));
/*     */     } 
/*     */     
/* 268 */     if (StringUtils.isEmpty(this.alias)) {
/* 269 */       resp.addContextMessage("alias", new I18nMessage("validate.required"));
/* 270 */     } else if (this.alias.length() > 40) {
/* 271 */       resp.addContextMessage("alias", new I18nMessage("validate.maxChar", new Object[] { Integer.valueOf(40) }));
/*     */     } 
/*     */     
/* 274 */     if (!StringUtils.isEmpty(this.description) && 
/* 275 */       this.description.length() > 40) {
/* 276 */       resp.addContextMessage("description", new I18nMessage("validate.maxChar", new Object[] { Integer.valueOf(170) }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 287 */     StringBuilder builder = new StringBuilder();
/* 288 */     builder.append("AppAgentVO [id=").append(this.id).append(", pkgUnique=").append(this.pkgUnique).append(", alias=")
/* 289 */       .append(this.alias).append(", disabled=").append(this.disabled).append(", osType=").append(this.osType)
/* 290 */       .append(", tsReg=").append(this.tsReg).append(", tsUpdated=").append(this.tsUpdated)
/* 291 */       .append(", fcmAuthenticationKey=").append(this.fcmAuthenticationKey).append("]");
/* 292 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 303 */     out.writeInt(1);
/* 304 */     SerializationUtils.writeSafeUTF(out, this.fcmAuthenticationKey);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 308 */     int ver = in.readInt();
/* 309 */     if (ver == 1)
/* 310 */       this.fcmAuthenticationKey = SerializationUtils.readSafeUTF(in); 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\AppAgentVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */