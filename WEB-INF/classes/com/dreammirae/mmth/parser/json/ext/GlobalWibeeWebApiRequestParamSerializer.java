/*     */ package WEB-INF.classes.com.dreammirae.mmth.parser.json.ext;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.GlobalWibeeRequestParam;
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
/*     */ 
/*     */ 
/*     */ public class GlobalWibeeWebApiRequestParamSerializer
/*     */   implements JsonSerializer<GlobalWibeeRequestParam>, JsonDeserializer<GlobalWibeeRequestParam>
/*     */ {
/*     */   private static final String KEY_USERNAME = "userName";
/*     */   private static final String KEY_TID = "tid";
/*     */   private static final String KEY_OTP = "otp";
/*     */   private static final String KEY_TRAN_INFO = "tranInfo";
/*     */   private static final String KEY_LIMIT = "limit";
/*     */   private static final String KEY_COUNTRY_CODE = "countryCode";
/*     */   private static final String KEY_REG_EMP_NO = "regEmpNo";
/*     */   private static final String KEY_DEVICE_ID = "deviceId";
/*     */   private static final String KEY_DEVICE_TYPE = "deviceType";
/*     */   private static final String REGEX_COUNTRY_CODE = "^[A-Z]{2}$";
/*     */   
/*     */   public GlobalWibeeRequestParam deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/*  37 */     JsonObject jsonObject = json.getAsJsonObject();
/*     */     
/*  39 */     GlobalWibeeRequestParam param = new GlobalWibeeRequestParam();
/*     */     
/*  41 */     param.setUserName(decode(jsonObject, "userName"));
/*  42 */     param.setTid(decode(jsonObject, "tid"));
/*  43 */     String otp = decode(jsonObject, "otp");
/*  44 */     if (!StringUtils.isEmpty(otp)) {
/*  45 */       param.setOtp(otp.toUpperCase());
/*     */     }
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
/*  66 */       param.setDeviceType(DeviceTypes.getDeviceType(deviceType));
/*     */     } 
/*     */     
/*  69 */     if (jsonObject.has("countryCode")) {
/*  70 */       String str = jsonObject.get("countryCode").getAsString();
/*  71 */       if (!StringUtils.isEmpty(str)) {
/*  72 */         str = str.toUpperCase();
/*     */         
/*  74 */         if (!str.matches("^[A-Z]{2}$")) {
/*  75 */           throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'countryCode' is must be [A-Z] 2-digit characters.");
/*     */         }
/*     */         
/*  78 */         param.setCountryCode(str.toUpperCase());
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     if (jsonObject.has("regEmpNo")) {
/*  83 */       param.setRegEmpNo(decode(jsonObject, "regEmpNo"));
/*     */     }
/*     */     
/*  86 */     return param;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement serialize(GlobalWibeeRequestParam param, Type typeOfSrc, JsonSerializationContext context) {
/*  91 */     JsonObject jsonObject = new JsonObject();
/*     */     
/*  93 */     if (!StringUtils.isEmpty(param.getUserName())) {
/*  94 */       jsonObject.addProperty("userName", Base64Utils.encodeUrl(param.getUserName()));
/*     */     }
/*     */     
/*  97 */     if (!StringUtils.isEmpty(param.getTid())) {
/*  98 */       jsonObject.addProperty("tid", Base64Utils.encodeUrl(param.getTid()));
/*     */     }
/*     */     
/* 101 */     if (!StringUtils.isEmpty(param.getOtp())) {
/* 102 */       jsonObject.addProperty("otp", Base64Utils.encodeUrl(param.getOtp()));
/*     */     }
/*     */     
/* 105 */     if (!StringUtils.isEmpty(param.getTranInfo())) {
/* 106 */       jsonObject.addProperty("tranInfo", Base64Utils.encodeUrl(param.getTranInfo()));
/*     */     }
/*     */     
/* 109 */     if (param.getLimit() != null) {
/* 110 */       jsonObject.addProperty("limit", param.getLimit());
/*     */     }
/*     */     
/* 113 */     if (!StringUtils.isEmpty(param.getCountryCode())) {
/* 114 */       jsonObject.addProperty("countryCode", param.getCountryCode().toUpperCase());
/*     */     }
/*     */     
/* 117 */     if (!StringUtils.isEmpty(param.getDeviceId())) {
/* 118 */       jsonObject.addProperty("deviceId", Base64Utils.encodeUrl(param.getDeviceId()));
/*     */     }
/*     */     
/* 121 */     if (param.getDeviceType() != null) {
/* 122 */       jsonObject.addProperty("deviceType", param.getDeviceType().getCode());
/*     */     }
/*     */     
/* 125 */     if (!StringUtils.isEmpty(param.getRegEmpNo())) {
/* 126 */       jsonObject.addProperty("regEmpNo", param.getRegEmpNo());
/*     */     }
/*     */     
/* 129 */     return (JsonElement)jsonObject;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String decode(JsonObject jsonObject, String key) {
/* 134 */     if (jsonObject.has(key)) {
/* 135 */       String str = jsonObject.get(key).getAsString();
/*     */       
/* 137 */       if (!StringUtils.isEmpty(str)) {
/* 138 */         return Base64Utils.decode(str);
/*     */       }
/*     */     } 
/*     */     
/* 142 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\ext\GlobalWibeeWebApiRequestParamSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */