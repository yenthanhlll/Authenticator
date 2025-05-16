/*     */ package WEB-INF.classes.com.dreammirae.mmth.parser.json.ext;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationInfos;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.external.bean.IssueCodeApiData;
/*     */ import com.dreammirae.mmth.external.bean.UserStatusBean;
/*     */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.bean.TokenStats;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
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
/*     */ public class WebApiResponseSerializer
/*     */   implements JsonSerializer<WebApiResponse>, JsonDeserializer<WebApiResponse>
/*     */ {
/*     */   private static final String RETURN_CODE = "returnCode";
/*     */   private static final String ISSUE_CODE = "issueCode";
/*     */   private static final String ISSUE_CODE_EXPIRED = "issueCodeExpired";
/*     */   private static final String ISSUE_CODE_FAIL_CNT = "issueCodeFailCnt";
/*     */   private static final String ISSUE_CODE_EXPIRED_YN = "issueCodeExpiredYn";
/*     */   private static final String ISSUE_CODE_FAIL_EXCEED_YN = "issueCodeFailExceedYn";
/*     */   private static final String AUTH_FAIL_CNT = "authFailCnt";
/*     */   private static final String ADDITIONAL_DATA = "data";
/*     */   private static final String LOGS = "logs";
/*     */   private static final String STATUS = "status";
/*     */   private static final String LOST_YN = "lostYn";
/*     */   private static final String OTP_SERIAL_NUMBER = "otpSerialNumber";
/*     */   private static final String COUNTRY_CODE = "countryCode";
/*     */   private static final String DEVICE_OS = "deviceOS";
/*     */   private static final String DEVICE_MODEL = "deviceModel";
/*     */   private static final String ISSUE_DATETIME = "issueDateTime";
/*     */   private static final String TOTAL = "total";
/*     */   private static final String AVAILABLE = "available";
/*     */   private static final String OCCUPIED = "occupied";
/*     */   private static final String DISCARD = "discard";
/*     */   private static final String OP_TYPE = "opType";
/*     */   private static final String REG_DATETIME = "regDateTime";
/*     */   private static final String EXPIRED_DURATION = "expiredDuration";
/*     */   private static final String QR_DATA = "qrData";
/*     */   private static final String TID = "tid";
/*     */   private static final String USER_NAME = "userName";
/*     */   private static final String PRODUCT_TYPE = "productType";
/*     */   private static final String MULTI_LOGIN_ENABLED = "multiLoginYN";
/*     */   private static final String USER_STATUS = "userStatus";
/*     */   private static final String ACCOUNTNAME = "accountName";
/*     */   
/*     */   public WebApiResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) throws JsonParseException {
/*  83 */     JsonObject jsonObject = json.getAsJsonObject();
/*     */     
/*  85 */     WebApiResponse resp = new WebApiResponse();
/*     */     
/*  87 */     if (jsonObject.has("returnCode")) {
/*  88 */       ReturnCodes returnCode = (ReturnCodes)ctx.deserialize(jsonObject.get("returnCode"), ReturnCodes.class);
/*  89 */       resp.setReturnCode(returnCode);
/*     */     } 
/*     */     
/*  92 */     resp.setIssueCode(decode(jsonObject, "issueCode"));
/*     */     
/*  94 */     String str = decode(jsonObject, "issueCodeExpired");
/*     */     
/*  96 */     if (str != null) {
/*  97 */       resp.setIssueCodeExpired(Long.valueOf(Commons.displayDateTime(str)));
/*     */     }
/*     */     
/* 100 */     if (jsonObject.has("issueCodeFailCnt")) {
/* 101 */       resp.setIssueCodeFailCnt(Integer.valueOf(jsonObject.get("issueCodeFailCnt").getAsInt()));
/*     */     }
/*     */     
/* 104 */     if (jsonObject.has("issueCodeExpiredYn")) {
/* 105 */       resp.setIssueCodeExpiredYn(Boolean.valueOf("Y".equals(jsonObject.get("issueCodeExpiredYn").getAsString())));
/*     */     }
/*     */     
/* 108 */     if (jsonObject.has("issueCodeFailExceedYn")) {
/* 109 */       resp.setIssueCodeFailExceedYn(Boolean.valueOf("Y".equals(jsonObject.get("issueCodeFailExceedYn").getAsString())));
/*     */     }
/*     */     
/* 112 */     if (!jsonObject.has("authFailCnt")) {
/* 113 */       AuthenticationInfos info = new AuthenticationInfos();
/* 114 */       info.setFailCnt(jsonObject.get("issueCodeFailCnt").getAsInt());
/* 115 */       resp.setAuthInfo(info);
/*     */     } 
/*     */     
/* 118 */     if (!jsonObject.has("data")) {
/* 119 */       return resp;
/*     */     }
/*     */     
/* 122 */     if (!jsonObject.get("data").isJsonObject()) {
/* 123 */       return resp;
/*     */     }
/*     */     
/* 126 */     JsonObject data = jsonObject.get("data").getAsJsonObject();
/*     */     
/* 128 */     if (data.has("status")) {
/*     */       
/* 130 */       UserStatusBean bean = new UserStatusBean();
/*     */       
/* 132 */       bean.setStatus(data.get("status").getAsString());
/*     */       
/* 134 */       if (data.has("lostYn")) {
/* 135 */         bean.setLostYn(Boolean.valueOf("Y".equals(data.get("lostYn").getAsString())));
/*     */       }
/*     */       
/* 138 */       if (data.has("issueDateTime")) {
/* 139 */         String val = data.get("issueDateTime").getAsString();
/* 140 */         bean.setIssueDateTime(Long.valueOf(Commons.displayDateTime(val)));
/*     */       } 
/*     */       
/* 143 */       bean.setOtpSerialNumber(decode(data, "otpSerialNumber"));
/*     */       
/* 145 */       if (data.has("countryCode")) {
/* 146 */         bean.setCountryCode(data.get("countryCode").getAsString());
/*     */       }
/*     */       
/* 149 */       if (data.has("deviceOS")) {
/* 150 */         bean.setDeviceOS(data.get("deviceOS").getAsString());
/*     */       }
/*     */       
/* 153 */       bean.setDeviceModel(decode(data, "deviceModel"));
/*     */       
/* 155 */       if (data.has("issueCode")) {
/* 156 */         IssueCodeApiData icData = new IssueCodeApiData();
/* 157 */         icData.setIssueCode(decode(data, "issueCode"));
/* 158 */         icData.setIssueCodeExpired(Long.valueOf(Commons.displayDateTime(data.get("issueCodeExpired").getAsString())));
/* 159 */         icData.setIssueCodeFailCnt(Integer.valueOf(data.get("issueCodeFailCnt").getAsInt()));
/* 160 */         icData.setIssueCodeExpiredYn(Boolean.valueOf("Y".equals(data.get("issueCodeExpiredYn").getAsString())));
/* 161 */         icData.setIssueCodeFailExceedYn(Boolean.valueOf("Y".equals(data.get("issueCodeFailExceedYn").getAsString())));
/* 162 */         resp.setIssueCodeData(icData);
/*     */       } 
/*     */       
/* 165 */       resp.setUserStatus(bean);
/* 166 */     } else if (data.has("total")) {
/*     */       
/* 168 */       TokenStats stats = new TokenStats();
/* 169 */       if (data.has("total")) {
/* 170 */         stats.setTotal(data.get("total").getAsInt());
/*     */       }
/*     */       
/* 173 */       if (data.has("available")) {
/* 174 */         stats.setTotal(data.get("available").getAsInt());
/*     */       }
/*     */       
/* 177 */       if (data.has("occupied")) {
/* 178 */         stats.setTotal(data.get("occupied").getAsInt());
/*     */       }
/*     */       
/* 181 */       if (data.has("discard")) {
/* 182 */         stats.setTotal(data.get("discard").getAsInt());
/*     */       }
/*     */     } 
/*     */     
/* 186 */     if (jsonObject.has("userName")) {
/* 187 */       resp.setUserName(data.get("userName").getAsString());
/*     */     }
/* 189 */     if (jsonObject.has("productType")) {
/* 190 */       resp.setProductType(Integer.valueOf(jsonObject.get("productType").getAsInt()));
/*     */     }
/* 192 */     if (jsonObject.has("multiLoginYN")) {
/* 193 */       resp.setMultiLoginYN(jsonObject.get("multiLoginYN").getAsString());
/*     */     }
/* 195 */     if (jsonObject.has("userStatus")) {
/* 196 */       resp.setStatus(jsonObject.get("userStatus").getAsString());
/*     */     }
/*     */     
/* 199 */     if (jsonObject.has("accountName")) {
/* 200 */       resp.setStatus(jsonObject.get("accountName").getAsString());
/*     */     }
/*     */     
/* 203 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement serialize(WebApiResponse resp, Type typeOfSrc, JsonSerializationContext ctx) {
/* 209 */     JsonObject jsonObject = new JsonObject();
/*     */     
/* 211 */     if (resp.getReturnCode() != null) {
/* 212 */       jsonObject.add("returnCode", ctx.serialize(resp.getReturnCode()));
/*     */     }
/*     */     
/* 215 */     if (!StringUtils.isEmpty(resp.getIssueCode())) {
/* 216 */       jsonObject.addProperty("issueCode", Base64Utils.encodeUrl(resp.getIssueCode()));
/*     */     }
/*     */     
/* 219 */     if (resp.getIssueCodeExpired() != null) {
/* 220 */       jsonObject.addProperty("issueCodeExpired", Base64Utils.encodeUrl(Commons.displayDateTime(resp.getIssueCodeExpired().longValue())));
/*     */     }
/*     */     
/* 223 */     if (resp.getIssueCodeFailCnt() != null) {
/* 224 */       jsonObject.addProperty("issueCodeFailCnt", Integer.valueOf(resp.getIssueCodeFailCnt().intValue()));
/*     */     }
/*     */     
/* 227 */     if (resp.getAuthInfo() != null) {
/* 228 */       jsonObject.addProperty("authFailCnt", Integer.valueOf(resp.getAuthInfo().getFailCnt()));
/*     */     }
/*     */     
/* 231 */     if (resp.getExpiredDuration() != null) {
/* 232 */       jsonObject.addProperty("expiredDuration", Integer.valueOf(resp.getExpiredDuration().intValue()));
/*     */     }
/*     */     
/* 235 */     if (!StringUtils.isEmpty(resp.getQrData())) {
/* 236 */       jsonObject.addProperty("qrData", resp.getQrData());
/*     */     }
/*     */     
/* 239 */     if (!StringUtils.isEmpty(resp.getTid())) {
/* 240 */       jsonObject.addProperty("tid", Base64Utils.encodeUrl(resp.getTid()));
/*     */     }
/*     */     
/* 243 */     if (resp.getUserStatus() != null) {
/*     */       
/* 245 */       JsonObject status = new JsonObject();
/* 246 */       UserStatusBean bean = resp.getUserStatus();
/*     */       
/* 248 */       if (!StringUtils.isEmpty(bean.getStatus())) {
/* 249 */         status.addProperty("status", bean.getStatus());
/*     */       }
/*     */       
/* 252 */       if (bean.getLostYn() != null) {
/* 253 */         status.addProperty("lostYn", bean.getLostYn().booleanValue() ? "Y" : "N");
/*     */       }
/*     */       
/* 256 */       if (!StringUtils.isEmpty(bean.getOtpSerialNumber())) {
/* 257 */         status.addProperty("otpSerialNumber", Base64Utils.encodeUrl(bean.getOtpSerialNumber()));
/*     */       }
/*     */       
/* 260 */       if (bean.getIssueDateTime() != null) {
/* 261 */         status.addProperty("issueDateTime", Base64Utils.encodeUrl(Commons.displayDateTime(bean.getIssueDateTime().longValue())));
/*     */       }
/*     */       
/* 264 */       if (!StringUtils.isEmpty(bean.getCountryCode())) {
/* 265 */         status.addProperty("countryCode", bean.getCountryCode());
/*     */       }
/*     */       
/* 268 */       if (!StringUtils.isEmpty(bean.getDeviceOS())) {
/* 269 */         status.addProperty("deviceOS", bean.getDeviceOS());
/*     */       }
/*     */       
/* 272 */       if (!StringUtils.isEmpty(bean.getDeviceModel())) {
/* 273 */         status.addProperty("deviceModel", Base64Utils.encodeUrl(bean.getDeviceModel()));
/*     */       }
/*     */ 
/*     */       
/* 277 */       if (resp.getIssueCodeData() != null) {
/*     */         
/* 279 */         IssueCodeApiData icData = resp.getIssueCodeData();
/*     */         
/* 281 */         if (!StringUtils.isEmpty(icData.getIssueCode())) {
/* 282 */           status.addProperty("issueCode", Base64Utils.encodeUrl(icData.getIssueCode()));
/*     */         }
/*     */         
/* 285 */         if (icData.getIssueCodeExpired() != null) {
/* 286 */           status.addProperty("issueCodeExpired", Commons.displayDate(icData.getIssueCodeExpired().longValue()));
/*     */         }
/*     */         
/* 289 */         if (icData.getIssueCodeFailCnt() != null) {
/* 290 */           status.addProperty("issueCodeFailCnt", Integer.valueOf(icData.getIssueCodeFailCnt().intValue()));
/*     */         }
/*     */         
/* 293 */         if (icData.getIssueCodeExpiredYn() != null) {
/* 294 */           status.addProperty("issueCodeExpiredYn", icData.getIssueCodeExpiredYn().booleanValue() ? "Y" : "N");
/*     */         }
/*     */         
/* 297 */         if (icData.getIssueCodeFailExceedYn() != null) {
/* 298 */           status.addProperty("issueCodeFailExceedYn", icData.getIssueCodeFailExceedYn().booleanValue() ? "Y" : "N");
/*     */         }
/*     */       } 
/*     */       
/* 302 */       jsonObject.add("data", (JsonElement)status);
/*     */     }
/* 304 */     else if (resp.getTokenStats() != null) {
/* 305 */       JsonObject status = new JsonObject();
/* 306 */       TokenStats stats = resp.getTokenStats();
/* 307 */       status.addProperty("total", Integer.valueOf(stats.getTotal()));
/* 308 */       status.addProperty("available", Integer.valueOf(stats.getAvailable()));
/* 309 */       status.addProperty("occupied", Integer.valueOf(stats.getOccupied()));
/* 310 */       status.addProperty("discard", Integer.valueOf(stats.getDiscard()));
/*     */       
/* 312 */       jsonObject.add("data", (JsonElement)status);
/*     */     }
/* 314 */     else if (resp.getServiceLogs() != null) {
/*     */       
/* 316 */       JsonArray jsonArray = new JsonArray();
/*     */       
/* 318 */       List<ServiceLogVO> logs = resp.getServiceLogs();
/*     */ 
/*     */       
/* 321 */       for (ServiceLogVO vo : logs) {
/* 322 */         JsonObject jobj = new JsonObject();
/*     */         
/* 324 */         jobj.addProperty("opType", vo.getOpType().getHexCode());
/* 325 */         jobj.addProperty("regDateTime", Base64Utils.encodeUrl(Commons.displayDateTime(vo.getTsReg().longValue())));
/*     */         
/* 327 */         jsonArray.add((JsonElement)jobj);
/*     */       } 
/*     */ 
/*     */       
/* 331 */       jsonObject.add("logs", (JsonElement)jsonArray);
/*     */     } 
/*     */     
/* 334 */     if (!StringUtils.isEmpty(resp.getUserName())) {
/* 335 */       jsonObject.addProperty("userName", resp.getUserName());
/*     */     }
/*     */     
/* 338 */     if (resp.getProductType() != null) {
/* 339 */       jsonObject.addProperty("productType", resp.getProductType());
/*     */     }
/*     */     
/* 342 */     if (!StringUtils.isEmpty(resp.getMultiLoginYN())) {
/* 343 */       jsonObject.addProperty("multiLoginYN", resp.getMultiLoginYN());
/*     */     }
/*     */     
/* 346 */     if (!StringUtils.isEmpty(resp.getAccountName())) {
/* 347 */       jsonObject.addProperty("accountName", resp.getAccountName());
/*     */     }
/*     */     
/* 350 */     if (!StringUtils.isEmpty(resp.getStatus())) {
/* 351 */       jsonObject.addProperty("userStatus", resp.getStatus());
/*     */     }
/*     */     
/* 354 */     return (JsonElement)jsonObject;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String decode(JsonObject jsonObject, String key) {
/* 359 */     if (jsonObject.has(key)) {
/* 360 */       String str = jsonObject.get(key).getAsString();
/* 361 */       return Base64Utils.decode(str);
/*     */     } 
/*     */     
/* 364 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\ext\WebApiResponseSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */