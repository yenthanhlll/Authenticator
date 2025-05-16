/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport.json;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.transport.SaotpRequestParam;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SaotpRequestParamSerializer
/*     */   implements JsonSerializer<SaotpRequestParam>, JsonDeserializer<SaotpRequestParam>
/*     */ {
/*     */   public SaotpRequestParam deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
/*  26 */     SaotpRequestParam param = new SaotpRequestParam();
/*     */     
/*  28 */     JsonObject obj = json.getAsJsonObject();
/*     */     
/*  30 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)obj.entrySet()) {
/*     */       
/*  32 */       String encVal = ((JsonElement)entry.getValue()).getAsString();
/*     */       
/*  34 */       if ("otpPub".equals(entry.getKey())) {
/*  35 */         param.setOtpPub(Base64Utils.decodeRaw(encVal)); continue;
/*  36 */       }  if ("rndSeedKey".equals(entry.getKey())) {
/*  37 */         param.setRndSeedKey(Base64Utils.decodeRaw(encVal)); continue;
/*  38 */       }  if ("rndChallenge".equals(entry.getKey())) {
/*  39 */         param.setRndChallenge(Base64Utils.decodeRaw(encVal)); continue;
/*     */       } 
/*  41 */       String value = Base64Utils.decode(encVal);
/*     */       
/*  43 */       if (SysEnvCommon.applyEnvCaseInsensitive())
/*     */       {
/*  45 */         if (SysEnvCommon.isCaseInsensitiveKey(entry.getKey())) {
/*  46 */           value = value.toLowerCase();
/*     */         }
/*     */       }
/*     */       
/*  50 */       if ("userName".equals(entry.getKey())) {
/*  51 */         param.setUserName(value); continue;
/*  52 */       }  if ("deviceType".equals(entry.getKey())) {
/*  53 */         param.setDeviceType(DeviceTypes.getDeviceType(value)); continue;
/*  54 */       }  if ("deviceId".equals(entry.getKey())) {
/*  55 */         param.setDeviceId(value); continue;
/*  56 */       }  if ("serverPin".equals(entry.getKey())) {
/*  57 */         param.setServerPin(value); continue;
/*  58 */       }  if ("issueCode".equals(entry.getKey())) {
/*  59 */         param.setIssueCode(value); continue;
/*  60 */       }  if ("tranInfo".equals(entry.getKey())) {
/*  61 */         param.setTranInfo(value); continue;
/*  62 */       }  if ("model".equals(entry.getKey())) {
/*  63 */         param.setModel(value); continue;
/*  64 */       }  if ("alias".equals(entry.getKey())) {
/*  65 */         param.setAlias(value);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  71 */     return param;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement serialize(SaotpRequestParam bean, Type type, JsonSerializationContext ctx) {
/*  76 */     JsonObject jsonObject = new JsonObject();
/*     */     
/*  78 */     if (bean.getUserName() != null) {
/*  79 */       jsonObject.addProperty("userName", Base64Utils.encode(bean.getUserName()));
/*     */     }
/*     */     
/*  82 */     if (bean.getDeviceType() != null) {
/*  83 */       jsonObject.addProperty("deviceType", Base64Utils.encode(bean.getDeviceType().getCode()));
/*     */     }
/*     */     
/*  86 */     if (bean.getDeviceId() != null) {
/*  87 */       jsonObject.addProperty("deviceId", Base64Utils.encode(bean.getDeviceId()));
/*     */     }
/*     */     
/*  90 */     if (bean.getServerPin() != null) {
/*  91 */       jsonObject.addProperty("serverPin", Base64Utils.encode(bean.getServerPin()));
/*     */     }
/*     */     
/*  94 */     if (bean.getOtpPub() != null) {
/*  95 */       jsonObject.addProperty("otpPub", Base64Utils.encode(bean.getOtpPub()));
/*     */     }
/*     */     
/*  98 */     if (bean.getRndSeedKey() != null) {
/*  99 */       jsonObject.addProperty("rndSeedKey", Base64Utils.encode(bean.getRndSeedKey()));
/*     */     }
/*     */     
/* 102 */     if (bean.getRndChallenge() != null) {
/* 103 */       jsonObject.addProperty("rndChallenge", Base64Utils.encode(bean.getRndChallenge()));
/*     */     }
/*     */     
/* 106 */     if (bean.getIssueCode() != null) {
/* 107 */       jsonObject.addProperty("issueCode", Base64Utils.encode(bean.getIssueCode()));
/*     */     }
/*     */     
/* 110 */     if (bean.getTranInfo() != null) {
/* 111 */       jsonObject.addProperty("tranInfo", Base64Utils.encode(bean.getTranInfo()));
/*     */     }
/*     */     
/* 114 */     if (bean.getModel() != null) {
/* 115 */       jsonObject.addProperty("model", Base64Utils.encode(bean.getModel()));
/*     */     }
/*     */     
/* 118 */     if (bean.getAlias() != null) {
/* 119 */       jsonObject.addProperty("alias", Base64Utils.encode(bean.getAlias()));
/*     */     }
/*     */     
/* 122 */     return (JsonElement)jsonObject;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\json\SaotpRequestParamSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */