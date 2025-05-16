/*     */ package WEB-INF.classes.com.dreammirae.mmth.parser.json.ext;
/*     */ 
/*     */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebApiRequestParamSerializer
/*     */   implements JsonSerializer<WebApiRequestParam>, JsonDeserializer<WebApiRequestParam>
/*     */ {
/*     */   private static final String KEY_USERNAME = "userName";
/*     */   private static final String KEY_TID = "tid";
/*     */   private static final String KEY_OTP = "otp";
/*     */   private static final String KEY_TRAN_INFO = "tranInfo";
/*     */   private static final String KEY_LIMIT = "limit";
/*     */   private static final String KEY_DEVICE_ID = "deviceId";
/*     */   private static final String KEY_DEVICE_TYPE = "deviceType";
/*     */   private static final String KEY_QR_SESSION_ID = "qrSessionId";
/*     */   private static final String KEY_PRODUCT_TYPE_CODE = "productTypeCode";
/*     */   private static final String KEY_MULTI_LOGIN_ENABLED = "multiLoginYN";
/*     */   private static final String KEY_MACADDRESS = "macAddress";
/*     */   private static final String KEY_IP = "ip";
/*     */   private static final String KEY_ACCOUNT_NAME = "accountName";
/*     */   
/*     */   public WebApiRequestParam deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/*  36 */     JsonObject jsonObject = json.getAsJsonObject();
/*     */     
/*  38 */     WebApiRequestParam param = new WebApiRequestParam();
/*     */     
/*  40 */     if (jsonObject.has("userName")) {
/*  41 */       param.setUserName(decode(jsonObject, "userName"));
/*     */     }
/*     */     
/*  44 */     param.setTid(decode(jsonObject, "tid"));
/*  45 */     param.setOtp(decode(jsonObject, "otp"));
/*     */ 
/*     */     
/*  48 */     String tranInfo = decode(jsonObject, "tranInfo");
/*  49 */     if (!StringUtils.isEmpty(tranInfo)) {
/*  50 */       param.setTranInfo(tranInfo.toUpperCase());
/*     */     }
/*     */     
/*  53 */     if (jsonObject.has("limit")) {
/*  54 */       param.setLimit(Integer.valueOf(jsonObject.get("limit").getAsInt()));
/*     */     }
/*     */     
/*  57 */     if (jsonObject.has("deviceId")) {
/*  58 */       String deviceId = decode(jsonObject, "deviceId");
/*  59 */       if (!StringUtils.isEmpty(deviceId)) {
/*  60 */         param.setDeviceId(deviceId.toUpperCase());
/*     */       }
/*     */     } 
/*     */     
/*  64 */     if (jsonObject.has("deviceType")) {
/*  65 */       String deviceType = jsonObject.get("deviceType").getAsString();
/*  66 */       param.setDeviceType(DeviceTypes.getDeviceTypeByName(deviceType));
/*     */     } 
/*     */     
/*  69 */     if (jsonObject.has("qrSessionId")) {
/*  70 */       String qrSessionId = decode(jsonObject, "qrSessionId");
/*  71 */       param.setQrSessionId(qrSessionId);
/*     */     } 
/*     */     
/*  74 */     if (jsonObject.has("productTypeCode")) {
/*  75 */       String productTypeCode = decode(jsonObject, "productTypeCode");
/*  76 */       param.setProductTypeCode(productTypeCode);
/*     */     } 
/*     */     
/*  79 */     if (jsonObject.has("multiLoginYN")) {
/*  80 */       String multiLoginYN = decode(jsonObject, "multiLoginYN");
/*  81 */       param.setMultiLoginYN(multiLoginYN);
/*     */     } 
/*     */     
/*  84 */     if (jsonObject.has("macAddress")) {
/*  85 */       String macAddress = decode(jsonObject, "macAddress");
/*  86 */       param.setMacAddress(macAddress);
/*     */     } 
/*     */     
/*  89 */     if (jsonObject.has("ip")) {
/*  90 */       long ip = jsonObject.get("ip").getAsLong();
/*  91 */       param.setIp(ip);
/*     */     } 
/*     */     
/*  94 */     if (jsonObject.has("accountName")) {
/*  95 */       String accountName = jsonObject.get("accountName").getAsString();
/*  96 */       param.setAccountName(accountName);
/*     */     } 
/*     */     
/*  99 */     return param;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement serialize(WebApiRequestParam param, Type typeOfSrc, JsonSerializationContext context) {
/* 104 */     JsonObject jsonObject = new JsonObject();
/*     */     
/* 106 */     if (!StringUtils.isEmpty(param.getUserName())) {
/* 107 */       jsonObject.addProperty("userName", Base64Utils.encodeUrl(param.getUserName()));
/*     */     }
/*     */     
/* 110 */     if (!StringUtils.isEmpty(param.getTid())) {
/* 111 */       jsonObject.addProperty("tid", Base64Utils.encodeUrl(param.getTid()));
/*     */     }
/*     */     
/* 114 */     if (!StringUtils.isEmpty(param.getOtp())) {
/* 115 */       jsonObject.addProperty("otp", Base64Utils.encodeUrl(param.getOtp()));
/*     */     }
/*     */     
/* 118 */     if (!StringUtils.isEmpty(param.getTranInfo())) {
/* 119 */       jsonObject.addProperty("tranInfo", Base64Utils.encodeUrl(param.getTranInfo()));
/*     */     }
/*     */     
/* 122 */     if (param.getLimit() != null) {
/* 123 */       jsonObject.addProperty("limit", param.getLimit());
/*     */     }
/*     */     
/* 126 */     if (!StringUtils.isEmpty(param.getDeviceId())) {
/* 127 */       jsonObject.addProperty("deviceId", Base64Utils.encodeUrl(param.getDeviceId()));
/*     */     }
/*     */     
/* 130 */     if (param.getDeviceType() != null) {
/* 131 */       jsonObject.addProperty("deviceType", param.getDeviceType().getCode());
/*     */     }
/*     */     
/* 134 */     if (!StringUtils.isEmpty(param.getQrSessionId())) {
/* 135 */       jsonObject.addProperty("qrSessionId", Base64Utils.encodeUrl(param.getQrSessionId()));
/*     */     }
/*     */     
/* 138 */     if (!StringUtils.isEmpty(param.getProductTypeCode())) {
/* 139 */       jsonObject.addProperty("productTypeCode", Base64Utils.encodeUrl(param.getProductTypeCode()));
/*     */     }
/*     */     
/* 142 */     if (!StringUtils.isEmpty(param.getMultiLoginYN())) {
/* 143 */       jsonObject.addProperty("multiLoginYN", Base64Utils.encodeUrl(param.getMultiLoginYN()));
/*     */     }
/*     */     
/* 146 */     if (!StringUtils.isEmpty(param.getMacAddress())) {
/* 147 */       jsonObject.addProperty("macAddress", Base64Utils.encodeUrl(param.getMacAddress()));
/*     */     }
/*     */     
/* 150 */     if (param.getIp() != 0L) {
/* 151 */       jsonObject.addProperty("ip", Long.valueOf(param.getIp()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     return (JsonElement)jsonObject;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String decode(JsonObject jsonObject, String key) {
/* 163 */     if (jsonObject.has(key)) {
/* 164 */       String str = jsonObject.get(key).getAsString();
/* 165 */       return Base64Utils.decode(str);
/*     */     } 
/*     */     
/* 168 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\ext\WebApiRequestParamSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */