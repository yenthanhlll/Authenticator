/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.bean.AuthRequestContents;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ public class AnyPASSResponseLocator
/*     */   extends AuthenticationResponseLocator
/*     */ {
/*     */   public void jsonSerialize(JsonObject jsonObject, Type type, JsonSerializationContext ctx) {
/*  20 */     if (getReturnCode() != null) {
/*  21 */       jsonObject.add("returnCode", ctx.serialize(getReturnCode()));
/*     */     }
/*     */     
/*  24 */     if (!StringUtils.isEmpty(getIssueCode())) {
/*  25 */       jsonObject.addProperty("issueCode", Base64Utils.encodeUrl(getIssueCode()));
/*     */     }
/*     */     
/*  28 */     if (getIssueCodeExpired() != null) {
/*  29 */       jsonObject.addProperty("issueCodeExpired", Commons.displayDateTime(getIssueCodeExpired().longValue()));
/*     */     }
/*     */     
/*  32 */     if (getIssueCodeFailCnt() != null) {
/*  33 */       jsonObject.addProperty("issueCodeFailCnt", getIssueCodeFailCnt());
/*     */     }
/*     */     
/*  36 */     if (getAuthFailCnt() != null) {
/*  37 */       jsonObject.addProperty("authFailCnt", getAuthFailCnt());
/*     */     }
/*     */     
/*  40 */     if (!StringUtils.isEmpty(getOtpSn())) {
/*  41 */       jsonObject.addProperty("otpSn", getOtpSn());
/*     */     }
/*     */     
/*  44 */     if (getTsLatestIssuance() != null) {
/*  45 */       jsonObject.addProperty("tsLatestIssuance", Commons.displayDateTime(getTsLatestIssuance().longValue()));
/*     */     }
/*     */     
/*  48 */     if (!StringUtils.isEmpty(getErrorMessage())) {
/*  49 */       jsonObject.addProperty("errorMessage", getErrorMessage());
/*     */     }
/*     */     
/*  52 */     if (getAaids() != null && (getAaids()).length > 0) {
/*  53 */       JsonArray arr = new JsonArray();
/*  54 */       for (String string : getAaids()) {
/*  55 */         arr.add(string);
/*     */       }
/*  57 */       jsonObject.add("aaid", (JsonElement)arr);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (getAuthReqContents() != null) {
/*  64 */       jsonObject.add("authReqContents", ctx.serialize(getAuthReqContents()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonDeserialization(JsonObject json, JsonDeserializationContext ctx) {
/*  70 */     if (json.has("authReqContents")) {
/*  71 */       ReturnCodes returnCode = (ReturnCodes)ctx.deserialize(json.get("returnCode"), ReturnCodes.class);
/*  72 */       setReturnCode(returnCode);
/*     */     } 
/*     */     
/*  75 */     setIssueCode(decodeString("issueCode", json));
/*     */     
/*  77 */     String value = decodeString("issueCodeExpired", json);
/*  78 */     if (StringUtils.isEmpty(value)) {
/*  79 */       setIssueCodeExpired(Long.valueOf(Commons.displayDateTime(value)));
/*     */     }
/*     */     
/*  82 */     if (json.has("issueCodeFailCnt")) {
/*  83 */       setIssueCodeFailCnt(Integer.valueOf(json.get("issueCodeFailCnt").getAsInt()));
/*     */     }
/*     */     
/*  86 */     if (json.has("authFailCnt")) {
/*  87 */       setAuthFailCnt(Integer.valueOf(json.get("authFailCnt").getAsInt()));
/*     */     }
/*     */     
/*  90 */     setOtpSn(decodeString("otpSn", json));
/*     */     
/*  92 */     value = decodeString("tsLatestIssuance", json);
/*  93 */     if (StringUtils.isEmpty(value)) {
/*  94 */       setTsLatestIssuance(Long.valueOf(Commons.displayDateTime(value)));
/*     */     }
/*     */     
/*  97 */     setErrorMessage(decodeString("errorMessage", json));
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (json.has("authReqContents")) {
/* 102 */       AuthRequestContents contents = (AuthRequestContents)ctx.deserialize(json.get("authReqContents"), AuthRequestContents.class);
/* 103 */       setAuthReqContents(contents);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String decodeString(String key, JsonObject json) {
/* 109 */     if (json.has(key)) {
/* 110 */       String value = json.get(key).getAsString();
/*     */       
/* 112 */       if (!StringUtils.isEmpty(value)) {
/* 113 */         return value.trim();
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\bean\AnyPASSResponseLocator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */