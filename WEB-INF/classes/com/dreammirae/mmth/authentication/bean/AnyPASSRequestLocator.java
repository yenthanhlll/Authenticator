/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnyPASSRequestLocator
/*     */   extends AuthenticationRequestLocator
/*     */ {
/*     */   public void jsonSerialize(JsonObject jsonObject, Type type, JsonSerializationContext ctx) {
/*  20 */     if (!StringUtils.isEmpty(getUsername())) {
/*  21 */       jsonObject.addProperty("userName", getUsername());
/*     */     }
/*     */ 
/*     */     
/*  25 */     if (!StringUtils.isEmpty(getTid())) {
/*  26 */       jsonObject.addProperty("tid", getTid());
/*     */     }
/*     */ 
/*     */     
/*  30 */     if (!StringUtils.isEmpty(getOtp())) {
/*  31 */       jsonObject.addProperty("otp", getOtp());
/*     */     }
/*     */ 
/*     */     
/*  35 */     if (!StringUtils.isEmpty(getDeviceId())) {
/*  36 */       jsonObject.addProperty("deviceId", getDeviceId());
/*     */     }
/*     */ 
/*     */     
/*  40 */     if (getDeviceType() != null) {
/*  41 */       jsonObject.addProperty("deviceType", getDeviceType().getCode());
/*     */     }
/*     */ 
/*     */     
/*  45 */     if (!StringUtils.isEmpty(getPakageName())) {
/*  46 */       jsonObject.addProperty("pkgName", getPakageName());
/*     */     }
/*     */ 
/*     */     
/*  50 */     if (!StringUtils.isEmpty(getModel())) {
/*  51 */       jsonObject.addProperty("model", getModel());
/*     */     }
/*     */ 
/*     */     
/*  55 */     if (!StringUtils.isEmpty(getAlias())) {
/*  56 */       jsonObject.addProperty("alias", getAlias());
/*     */     }
/*     */ 
/*     */     
/*  60 */     if (!StringUtils.isEmpty(getAaid())) {
/*  61 */       jsonObject.addProperty("aaid", getAaid());
/*     */     }
/*     */ 
/*     */     
/*  65 */     if (!StringUtils.isEmpty(getTranInfo())) {
/*  66 */       jsonObject.addProperty("tranInfo", getTranInfo());
/*     */     }
/*     */ 
/*     */     
/*  70 */     if (!StringUtils.isEmpty(getFcmTokenId())) {
/*  71 */       jsonObject.addProperty("fcmTokenId", getFcmTokenId());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonDeserialization(JsonObject json, JsonDeserializationContext ctx) {
/*  79 */     setUsername(getString("userName", json));
/*     */ 
/*     */     
/*  82 */     setTid(getString("tid", json));
/*     */ 
/*     */     
/*  85 */     setOtp(getString("otp", json));
/*     */ 
/*     */     
/*  88 */     setDeviceId(getString("deviceId", json));
/*     */ 
/*     */     
/*  91 */     String deviceType = getString("deviceType", json);
/*  92 */     if (!StringUtils.isEmpty(deviceType)) {
/*  93 */       setDeviceType(DeviceTypes.getDeviceTypeByName(deviceType));
/*     */     }
/*     */ 
/*     */     
/*  97 */     setPakageName(getString("pkgName", json));
/*     */ 
/*     */     
/* 100 */     setModel(getString("model", json));
/*     */ 
/*     */     
/* 103 */     setAlias(getString("alias", json));
/*     */ 
/*     */     
/* 106 */     setAaid(getString("aaid", json));
/*     */ 
/*     */     
/* 109 */     setTranInfo(getString("tranInfo", json));
/*     */ 
/*     */     
/* 112 */     setFcmTokenId(getString("fcmTokenId", json));
/*     */   }
/*     */ 
/*     */   
/*     */   private String getString(String key, JsonObject json) {
/* 117 */     if (json.has(key)) {
/* 118 */       String value = json.get(key).getAsString();
/* 119 */       if (!StringUtils.isEmpty(value)) {
/* 120 */         return value.trim();
/*     */       }
/*     */     } 
/*     */     
/* 124 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\bean\AnyPASSRequestLocator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */