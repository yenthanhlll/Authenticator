/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.fido.metadata.MetadataStatement;
/*     */ import com.dreammirae.mmth.fido.metadata.VerificationMethodDescriptor;
/*     */ import com.dreammirae.mmth.fido.registry.UserVerificationMethods;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*     */ import com.fasterxml.jackson.annotation.JsonIgnore;
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
/*     */ public class FidoMetadataVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*     */   private static final int LEN_MAX_ALIAS = 40;
/*     */   private static final int LEN_MAX_DESC = 170;
/*  30 */   private int id = -1;
/*     */   
/*     */   private String aaid;
/*     */   
/*     */   private String alias;
/*     */   
/*     */   private DisabledStatus disabled;
/*     */   
/*     */   private UserVerificationMethods userVerification;
/*     */   
/*  40 */   private String description = "-";
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private Long tsReg;
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private Long tsUpdated;
/*     */ 
/*     */   
/*     */   private String metadataAsJson;
/*     */ 
/*     */   
/*     */   @JsonIgnore
/*     */   private MetadataStatement metadata;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static final int version = 1;
/*     */ 
/*     */   
/*     */   public int getId() {
/*  63 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  71 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAaid() {
/*  78 */     return this.aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAaid(String aaid) {
/*  86 */     this.aaid = aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlias() {
/*  93 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlias(String alias) {
/* 101 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisabledStatus getDisabled() {
/* 108 */     return this.disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisabled(DisabledStatus disabled) {
/* 116 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserVerificationMethods getUserVerification() {
/* 123 */     return this.userVerification;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserVerification(UserVerificationMethods userVerification) {
/* 131 */     this.userVerification = userVerification;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 138 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/* 146 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTsReg() {
/* 153 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(Long tsReg) {
/* 161 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTsUpdated() {
/* 168 */     return this.tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsUpdated(Long tsUpdated) {
/* 176 */     this.tsUpdated = tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMetadataAsJson() {
/* 183 */     return this.metadataAsJson;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMetadataAsJson(String metadataAsJson) {
/* 191 */     this.metadataAsJson = metadataAsJson;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetadataStatement getMetadata() {
/* 198 */     return this.metadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMetadata(MetadataStatement metadata) {
/* 206 */     this.metadata = metadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public DisabledStatus getDisabledDesc() {
/* 215 */     return this.disabled;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public UserVerificationMethods getUserVerificationDesc() {
/* 220 */     return this.userVerification;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.FidoMetadataVO createAdministrationVO(MetadataStatement metadata, String alias, String metadataAsJson) {
/* 224 */     com.dreammirae.mmth.vo.FidoMetadataVO vo = new com.dreammirae.mmth.vo.FidoMetadataVO();
/* 225 */     vo.setAaid(metadata.getAaid());
/* 226 */     vo.setAlias(alias);
/* 227 */     VerificationMethodDescriptor[][] descriptor = metadata.getUserVerificationDetails();
/* 228 */     UserVerificationMethods method = UserVerificationMethods.getUserVerificationMethods(descriptor[0][0].getUserVerification());
/* 229 */     vo.setUserVerification(method);
/* 230 */     vo.setDisabled(DisabledStatus.ENABLED);
/* 231 */     vo.setMetadata(metadata);
/* 232 */     vo.setMetadataAsJson(metadataAsJson);
/* 233 */     return vo;
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
/*     */   public void validate(RestResponse resp) {
/* 249 */     if (StringUtils.isEmpty(this.alias)) {
/* 250 */       resp.addContextMessage("alias", new I18nMessage("validate.required"));
/* 251 */     } else if (this.alias.length() > 40) {
/* 252 */       resp.addContextMessage("alias", new I18nMessage("validate.maxChar", new Object[] { Integer.valueOf(40) }));
/*     */     } 
/*     */ 
/*     */     
/* 256 */     if (!StringUtils.isEmpty(this.description) && 
/* 257 */       this.description.length() > 170) {
/* 258 */       resp.addContextMessage("description", new I18nMessage("validate.maxChar", new Object[] { Integer.valueOf(170) }));
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
/*     */   public String toString() {
/* 270 */     StringBuilder builder = new StringBuilder();
/* 271 */     builder.append("FidoMetadataVO [id=").append(this.id).append(", aaid=").append(this.aaid).append(", alias=").append(this.alias).append(", diabled=").append(this.disabled)
/* 272 */       .append(", userVerification=").append(this.userVerification).append(", description=").append(this.description).append(", tsReg=").append(this.tsReg).append(", tsUpdated=")
/* 273 */       .append(this.tsUpdated).append(", metadataAsJson=").append(this.metadataAsJson).append("]");
/* 274 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 284 */     out.writeInt(1);
/* 285 */     SerializationUtils.writeSafeUTF(out, this.metadataAsJson);
/* 286 */     SerializationUtils.writeSafeObject(out, this.metadata);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 290 */     int ver = in.readInt();
/* 291 */     if (ver == 1) {
/* 292 */       this.metadataAsJson = SerializationUtils.readSafeUTF(in);
/* 293 */       this.metadata = (MetadataStatement)SerializationUtils.readSafeObject(in);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\FidoMetadataVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */