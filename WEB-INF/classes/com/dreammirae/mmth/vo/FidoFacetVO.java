/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.fido.FidoUAFUtils;
/*     */ import com.dreammirae.mmth.fido.uaf.Version;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
/*     */ public class FidoFacetVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*     */   private static final int LEN_MAX_FACET_ID = 512;
/*     */   private static final int LEN_MAX_ALIAS = 40;
/*     */   private static final int LEN_MAX_DESC = 170;
/*  58 */   private int id = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   private String facetId;
/*     */ 
/*     */ 
/*     */   
/*     */   private int majorVer;
/*     */ 
/*     */   
/*     */   private int minorVer;
/*     */ 
/*     */   
/*     */   private String alias;
/*     */ 
/*     */   
/*     */   private DisabledStatus disabled;
/*     */ 
/*     */   
/*     */   private String description;
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
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  94 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/* 101 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFacetId() {
/* 108 */     return this.facetId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacetId(String facetId) {
/* 115 */     this.facetId = facetId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMajorVer() {
/* 122 */     return this.majorVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMajorVer(int majorVer) {
/* 129 */     this.majorVer = majorVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinorVer() {
/* 136 */     return this.minorVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinorVer(int minorVer) {
/* 143 */     this.minorVer = minorVer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlias() {
/* 150 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlias(String alias) {
/* 157 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisabledStatus getDisabled() {
/* 164 */     return this.disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisabled(DisabledStatus disabled) {
/* 171 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 178 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/* 185 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 192 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 199 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsUpdated() {
/* 206 */     return this.tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsUpdated(long tsUpdated) {
/* 213 */     this.tsUpdated = tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public DisabledStatus getDisabledDesc() {
/* 222 */     return this.disabled;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.FidoFacetVO createAdministrationVO(String facetId, String alias) {
/* 226 */     com.dreammirae.mmth.vo.FidoFacetVO vo = new com.dreammirae.mmth.vo.FidoFacetVO();
/* 227 */     vo.setFacetId(facetId);
/* 228 */     vo.setAlias(alias);
/* 229 */     vo.setMajorVer(Version.getCurrentVersion().getMajor().shortValue());
/* 230 */     vo.setMinorVer(Version.getCurrentVersion().getMinor().shortValue());
/* 231 */     vo.setDisabled(DisabledStatus.ENABLED);
/* 232 */     vo.setDescription("-");
/*     */     
/* 234 */     return vo;
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
/*     */   public void validate(RestResponse resp) {
/* 248 */     if (StringUtils.isEmpty(this.facetId)) {
/* 249 */       resp.addContextMessage("facetId", new I18nMessage("validate.required"));
/* 250 */     } else if (this.facetId.length() > 512) {
/* 251 */       resp.addContextMessage("facetId", new I18nMessage("validate.maxChar", new Object[] { Integer.valueOf(512) }));
/* 252 */     } else if (!FidoUAFUtils.validateFacetId(this.facetId)) {
/* 253 */       resp.addContextMessage("facetId", new I18nMessage("facet.validate.facetId"));
/*     */     } 
/*     */ 
/*     */     
/* 257 */     if (StringUtils.isEmpty(this.alias)) {
/* 258 */       resp.addContextMessage("alias", new I18nMessage("validate.required"));
/* 259 */     } else if (this.alias.length() > 40) {
/* 260 */       resp.addContextMessage("alias", new I18nMessage("validate.maxChar", new Object[] { Integer.valueOf(40) }));
/*     */     } 
/*     */ 
/*     */     
/* 264 */     if (!StringUtils.isEmpty(this.description) && 
/* 265 */       this.description.length() > 170) {
/* 266 */       resp.addContextMessage("description", new I18nMessage("validate.maxChar", new Object[] { Integer.valueOf(170) }));
/*     */     }
/*     */ 
/*     */     
/* 270 */     if (this.majorVer != Version.getCurrentVersion().getMajor().shortValue()) {
/* 271 */       this.majorVer = Version.getCurrentVersion().getMajor().shortValue();
/*     */     }
/*     */     
/* 274 */     if (this.minorVer != Version.getCurrentVersion().getMinor().shortValue()) {
/* 275 */       this.minorVer = Version.getCurrentVersion().getMajor().shortValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 285 */     StringBuilder builder = new StringBuilder();
/* 286 */     builder.append("FidoFacetVO [id=").append(this.id).append(", facetId=").append(this.facetId).append(", majorVer=")
/* 287 */       .append(this.majorVer).append(", minorVer=").append(this.minorVer).append(", alias=").append(this.alias)
/* 288 */       .append(", disabled=").append(this.disabled).append(", description=").append(this.description).append(", tsReg=")
/* 289 */       .append(this.tsReg).append(", tsUpdated=").append(this.tsUpdated).append("]");
/* 290 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\FidoFacetVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */