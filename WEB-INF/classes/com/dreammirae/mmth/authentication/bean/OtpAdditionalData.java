/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.fido.transport.additional.AdditionalData;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OtpAdditionalData
/*     */   extends AdditionalData
/*     */ {
/*     */   private static final String ENC_KEY = "encKey";
/*     */   private static final String ENC_OTP = "encOtp";
/*     */   private static final String ENC_DATA = "encData";
/*     */   private static final String ENC_TID = "encTid";
/*     */   private static final String ENC_RNDSEEDKEY = "encRndSeedKey";
/*     */   private static final String ENC_TOKEN = "encToken";
/*     */   private static final String REFERENCES = "references";
/*     */   private static final String EXPIRED_DATE_TIME = "expiredDateTime";
/*     */   private static final String TS_EXPIRED_OTP = "tsExpiredOTP";
/*     */   private byte[] encKey;
/*     */   private byte[] encOtp;
/*     */   private byte[] encData;
/*     */   private byte[] encTid;
/*     */   private byte[] encRndSeedKey;
/*     */   private byte[] encToken;
/*     */   private String[] references;
/*     */   private String expiredDateTime;
/*     */   private Long tsExpiredOTP;
/*     */   
/*     */   public byte[] getEncKey() {
/*  50 */     return this.encKey;
/*     */   }
/*     */   
/*     */   public void setEncKey(byte[] encKey) {
/*  54 */     this.encKey = encKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncOtp() {
/*  61 */     return this.encOtp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncOtp(byte[] encOtp) {
/*  69 */     this.encOtp = encOtp;
/*     */   }
/*     */   
/*     */   public byte[] getEncData() {
/*  73 */     return this.encData;
/*     */   }
/*     */   
/*     */   public void setEncData(byte[] encData) {
/*  77 */     this.encData = encData;
/*     */   }
/*     */   
/*     */   public byte[] getEncTid() {
/*  81 */     return this.encTid;
/*     */   }
/*     */   
/*     */   public void setEncTid(byte[] encTid) {
/*  85 */     this.encTid = encTid;
/*     */   }
/*     */   
/*     */   public byte[] getEncRndSeedKey() {
/*  89 */     return this.encRndSeedKey;
/*     */   }
/*     */   
/*     */   public void setEncRndSeedKey(byte[] encRndSeedKey) {
/*  93 */     this.encRndSeedKey = encRndSeedKey;
/*     */   }
/*     */   
/*     */   public byte[] getEncToken() {
/*  97 */     return this.encToken;
/*     */   }
/*     */   
/*     */   public void setEncToken(byte[] encToken) {
/* 101 */     this.encToken = encToken;
/*     */   }
/*     */   
/*     */   public String[] getReferences() {
/* 105 */     return this.references;
/*     */   }
/*     */   
/*     */   public void setReferences(String[] references) {
/* 109 */     this.references = references;
/*     */   }
/*     */   
/*     */   public String getExpiredDateTime() {
/* 113 */     return this.expiredDateTime;
/*     */   }
/*     */   
/*     */   public void setExpiredDateTime(String expiredDateTime) {
/* 117 */     this.expiredDateTime = expiredDateTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTsExpiredOTP() {
/* 124 */     return this.tsExpiredOTP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsExpiredOTP(Long tsExpiredOTP) {
/* 131 */     this.tsExpiredOTP = tsExpiredOTP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jsonSerializationImp(JsonObject parent, JsonSerializationContext ctx) {
/* 137 */     if (this.encKey != null) {
/* 138 */       parent.addProperty("encKey", Base64Utils.encodeUrl(this.encKey));
/*     */     }
/*     */     
/* 141 */     if (this.encOtp != null) {
/* 142 */       parent.addProperty("encOtp", Base64Utils.encodeUrl(this.encOtp));
/*     */     }
/*     */     
/* 145 */     if (this.encData != null) {
/* 146 */       parent.addProperty("encData", Base64Utils.encodeUrl(this.encData));
/*     */     }
/*     */     
/* 149 */     if (this.encTid != null) {
/* 150 */       parent.addProperty("encTid", Base64Utils.encodeUrl(this.encTid));
/*     */     }
/*     */     
/* 153 */     if (this.encRndSeedKey != null) {
/* 154 */       parent.addProperty("encRndSeedKey", Base64Utils.encodeUrl(this.encRndSeedKey));
/*     */     }
/*     */     
/* 157 */     if (this.encToken != null) {
/* 158 */       parent.addProperty("encToken", Base64Utils.encodeUrl(this.encToken));
/*     */     }
/*     */     
/* 161 */     if (this.references != null) {
/*     */       
/* 163 */       JsonArray array = new JsonArray();
/*     */       
/* 165 */       for (String str : this.references) {
/* 166 */         array.add(Base64Utils.encodeUrl(str));
/*     */       }
/*     */       
/* 169 */       parent.add("references", (JsonElement)array);
/*     */     } 
/*     */     
/* 172 */     if (this.expiredDateTime != null) {
/* 173 */       parent.addProperty("expiredDateTime", Base64Utils.encodeUrl(this.expiredDateTime));
/*     */     }
/*     */     
/* 176 */     if (this.tsExpiredOTP != null) {
/* 177 */       parent.addProperty("tsExpiredOTP", Long.valueOf(this.tsExpiredOTP.longValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jsonDeserializationImp(JsonObject additionalData, JsonDeserializationContext ctx) {
/* 184 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)additionalData.entrySet()) {
/* 185 */       String key = entry.getKey();
/* 186 */       JsonElement value = entry.getValue();
/*     */       
/* 188 */       if ("encKey".equals(key)) {
/* 189 */         this.encKey = Base64Utils.decodeRaw(value.getAsString()); continue;
/* 190 */       }  if ("encOtp".equals(key)) {
/* 191 */         this.encOtp = Base64Utils.decodeRaw(value.getAsString()); continue;
/* 192 */       }  if ("encData".equals(key)) {
/* 193 */         this.encData = Base64Utils.decodeRaw(value.getAsString()); continue;
/* 194 */       }  if ("encTid".equals(key)) {
/* 195 */         this.encTid = Base64Utils.decodeRaw(value.getAsString()); continue;
/* 196 */       }  if ("encRndSeedKey".equals(key)) {
/* 197 */         this.encRndSeedKey = Base64Utils.decodeRaw(value.getAsString()); continue;
/* 198 */       }  if ("encToken".equals(key)) {
/* 199 */         this.encToken = Base64Utils.decodeRaw(value.getAsString()); continue;
/* 200 */       }  if ("references".equals(key)) {
/* 201 */         JsonArray array = value.getAsJsonArray();
/*     */         
/* 203 */         int len = array.size();
/* 204 */         String[] ref = new String[len];
/* 205 */         for (int i = 0; i < len; i++) {
/* 206 */           ref[i] = Base64Utils.decode(array.get(i).getAsString());
/*     */         }
/*     */         
/* 209 */         this.references = ref; continue;
/* 210 */       }  if ("expiredDateTime".equals(key)) {
/* 211 */         this.expiredDateTime = Base64Utils.decode(value.getAsString()); continue;
/* 212 */       }  if ("tsExpiredOTP".equals(key)) {
/* 213 */         this.tsExpiredOTP = Long.valueOf(value.getAsLong());
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
/*     */   public String toString() {
/* 225 */     StringBuilder builder = new StringBuilder();
/* 226 */     builder.append("OtpAdditionalData [encKey=").append(HexUtils.toHexString(this.encKey)).append(", encData=").append(HexUtils.toHexString(this.encData)).append(", encTid=").append(HexUtils.toHexString(this.encTid))
/* 227 */       .append(", encRndSeedKey=").append(HexUtils.toHexString(this.encRndSeedKey)).append(", encToken=").append(HexUtils.toHexString(this.encToken)).append(", references=").append(Arrays.toString((Object[])this.references))
/* 228 */       .append(", expiredDateTime=").append(this.expiredDateTime).append("]");
/* 229 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\bean\OtpAdditionalData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */