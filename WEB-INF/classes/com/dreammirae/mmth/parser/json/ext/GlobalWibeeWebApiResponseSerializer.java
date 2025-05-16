/*     */ package WEB-INF.classes.com.dreammirae.mmth.parser.json.ext;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationInfos;
/*     */ import com.dreammirae.mmth.external.bean.GlobalWibeeWebApiResponse;
/*     */ import com.dreammirae.mmth.external.bean.IssueCodeApiData;
/*     */ import com.dreammirae.mmth.external.bean.UserStatusBean;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.ServiceLogVO;
/*     */ import com.dreammirae.mmth.vo.bean.GlobalWibeeLogData;
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
/*     */ public class GlobalWibeeWebApiResponseSerializer
/*     */   implements JsonSerializer<GlobalWibeeWebApiResponse>, JsonDeserializer<GlobalWibeeWebApiResponse>
/*     */ {
/*     */   private static final String RETURN_CODE = "returnCode";
/*     */   private static final String ISSUE_CODE = "issueCode";
/*     */   private static final String ISSUE_CODE_EXPIRED = "issueCodeExpired";
/*     */   private static final String ISSUE_CODE_FAIL_CNT = "issueCodeFailCnt";
/*     */   private static final String ISSUE_CODE_EXPIRED_YN = "issueCodeExpiredYn";
/*     */   private static final String ISSUE_CODE_FAIL_EXCEED_YN = "issueCodeFailExceedYn";
/*     */   private static final String ISSUE_CODE_REG_DATE_TIME = "issueCodeRegDateTime";
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
/*     */   private static final String REG_EMP_NO = "regEmpNo";
/*     */   
/*     */   public GlobalWibeeWebApiResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) throws JsonParseException {
/*  70 */     JsonObject jsonObject = json.getAsJsonObject();
/*     */     
/*  72 */     GlobalWibeeWebApiResponse resp = new GlobalWibeeWebApiResponse();
/*     */     
/*  74 */     if (jsonObject.has("returnCode")) {
/*  75 */       ReturnCodes returnCode = (ReturnCodes)ctx.deserialize(jsonObject.get("returnCode"), ReturnCodes.class);
/*  76 */       resp.setReturnCode(returnCode);
/*     */     } 
/*     */     
/*  79 */     resp.setIssueCode(decode(jsonObject, "issueCode"));
/*     */     
/*  81 */     if (jsonObject.has("issueCodeExpired")) {
/*  82 */       resp.setIssueCodeExpired(Long.valueOf(jsonObject.get("issueCodeExpired").getAsLong()));
/*     */     }
/*     */     
/*  85 */     if (jsonObject.has("issueCodeFailCnt")) {
/*  86 */       resp.setIssueCodeFailCnt(Integer.valueOf(jsonObject.get("issueCodeFailCnt").getAsInt()));
/*     */     }
/*     */     
/*  89 */     if (jsonObject.has("issueCodeExpiredYn")) {
/*  90 */       resp.setIssueCodeExpiredYn(Boolean.valueOf("Y".equals(jsonObject.get("issueCodeExpiredYn").getAsString())));
/*     */     }
/*     */     
/*  93 */     if (jsonObject.has("issueCodeFailExceedYn")) {
/*  94 */       resp.setIssueCodeFailExceedYn(Boolean.valueOf("Y".equals(jsonObject.get("issueCodeFailExceedYn").getAsString())));
/*     */     }
/*     */     
/*  97 */     if (!jsonObject.has("authFailCnt")) {
/*  98 */       AuthenticationInfos info = new AuthenticationInfos();
/*  99 */       info.setFailCnt(jsonObject.get("authFailCnt").getAsInt());
/* 100 */       resp.setAuthInfo(info);
/*     */     } 
/*     */     
/* 103 */     if (!jsonObject.has("data")) {
/* 104 */       return resp;
/*     */     }
/*     */     
/* 107 */     if (!jsonObject.get("data").isJsonObject()) {
/* 108 */       return resp;
/*     */     }
/*     */     
/* 111 */     JsonObject data = jsonObject.get("data").getAsJsonObject();
/*     */     
/* 113 */     if (data.has("status")) {
/*     */       
/* 115 */       UserStatusBean bean = new UserStatusBean();
/*     */       
/* 117 */       bean.setStatus(data.get("status").getAsString());
/*     */       
/* 119 */       if (data.has("lostYn")) {
/* 120 */         bean.setLostYn(Boolean.valueOf("Y".equals(data.get("lostYn").getAsString())));
/*     */       }
/*     */       
/* 123 */       if (data.has("issueDateTime")) {
/* 124 */         bean.setIssueDateTime(Long.valueOf(data.get("issueDateTime").getAsLong()));
/*     */       }
/*     */       
/* 127 */       bean.setOtpSerialNumber(decode(data, "otpSerialNumber"));
/*     */       
/* 129 */       if (data.has("countryCode")) {
/* 130 */         bean.setCountryCode(data.get("countryCode").getAsString());
/*     */       }
/*     */       
/* 133 */       if (data.has("deviceOS")) {
/* 134 */         bean.setDeviceOS(data.get("deviceOS").getAsString());
/*     */       }
/*     */       
/* 137 */       bean.setDeviceModel(decode(data, "deviceModel"));
/*     */       
/* 139 */       if (data.has("issueCode")) {
/* 140 */         IssueCodeApiData icData = new IssueCodeApiData();
/* 141 */         icData.setIssueCode(decode(data, "issueCode"));
/* 142 */         icData.setIssueCodeRegTs(Long.valueOf(data.get("issueCodeRegDateTime").getAsLong()));
/* 143 */         icData.setIssueCodeExpired(Long.valueOf(data.get("issueCodeExpired").getAsLong()));
/* 144 */         icData.setIssueCodeFailCnt(Integer.valueOf(data.get("issueCodeFailCnt").getAsInt()));
/* 145 */         icData.setIssueCodeExpiredYn(Boolean.valueOf("Y".equals(data.get("issueCodeExpiredYn").getAsString())));
/* 146 */         icData.setIssueCodeFailExceedYn(Boolean.valueOf("Y".equals(data.get("issueCodeFailExceedYn").getAsString())));
/* 147 */         resp.setIssueCodeData(icData);
/*     */       } 
/*     */       
/* 150 */       resp.setUserStatus(bean);
/* 151 */     } else if (data.has("total")) {
/*     */       
/* 153 */       TokenStats stats = new TokenStats();
/* 154 */       if (data.has("total")) {
/* 155 */         stats.setTotal(data.get("total").getAsInt());
/*     */       }
/*     */       
/* 158 */       if (data.has("available")) {
/* 159 */         stats.setTotal(data.get("available").getAsInt());
/*     */       }
/*     */       
/* 162 */       if (data.has("occupied")) {
/* 163 */         stats.setTotal(data.get("occupied").getAsInt());
/*     */       }
/*     */       
/* 166 */       if (data.has("discard")) {
/* 167 */         stats.setTotal(data.get("discard").getAsInt());
/*     */       }
/*     */     } 
/*     */     
/* 171 */     return resp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonElement serialize(GlobalWibeeWebApiResponse resp, Type typeOfSrc, JsonSerializationContext ctx) {
/* 177 */     JsonObject jsonObject = new JsonObject();
/*     */     
/* 179 */     if (resp.getReturnCode() != null) {
/* 180 */       jsonObject.add("returnCode", ctx.serialize(resp.getReturnCode()));
/*     */     }
/*     */     
/* 183 */     if (!StringUtils.isEmpty(resp.getIssueCode())) {
/* 184 */       jsonObject.addProperty("issueCode", Base64Utils.encodeUrl(resp.getIssueCode()));
/*     */     }
/*     */     
/* 187 */     if (resp.getIssueCodeRegDateTime() != null) {
/* 188 */       jsonObject.addProperty("issueCodeRegDateTime", resp.getIssueCodeRegDateTime());
/*     */     }
/*     */     
/* 191 */     if (resp.getIssueCodeExpired() != null) {
/* 192 */       jsonObject.addProperty("issueCodeExpired", resp.getIssueCodeExpired());
/*     */     }
/*     */     
/* 195 */     if (resp.getIssueCodeFailCnt() != null) {
/* 196 */       jsonObject.addProperty("issueCodeFailCnt", Integer.valueOf(resp.getIssueCodeFailCnt().intValue()));
/*     */     }
/*     */     
/* 199 */     if (resp.getIssueCodeExpiredYn() != null) {
/* 200 */       jsonObject.addProperty("issueCodeExpiredYn", resp.getIssueCodeExpiredYn().booleanValue() ? "Y" : "N");
/*     */     }
/*     */     
/* 203 */     if (resp.getIssueCodeFailExceedYn() != null) {
/* 204 */       jsonObject.addProperty("issueCodeFailExceedYn", resp.getIssueCodeFailExceedYn().booleanValue() ? "Y" : "N");
/*     */     }
/*     */     
/* 207 */     if (resp.getAuthInfo() != null) {
/* 208 */       jsonObject.addProperty("authFailCnt", Integer.valueOf(resp.getAuthInfo().getFailCnt()));
/*     */     }
/*     */     
/* 211 */     if (resp.getUserStatus() != null) {
/*     */       
/* 213 */       JsonObject status = new JsonObject();
/* 214 */       UserStatusBean bean = resp.getUserStatus();
/*     */       
/* 216 */       if (!StringUtils.isEmpty(bean.getStatus())) {
/* 217 */         status.addProperty("status", bean.getStatus());
/*     */       }
/*     */       
/* 220 */       if (bean.getLostYn() != null) {
/* 221 */         status.addProperty("lostYn", bean.getLostYn().booleanValue() ? "Y" : "N");
/*     */       }
/*     */       
/* 224 */       if (!StringUtils.isEmpty(bean.getOtpSerialNumber())) {
/* 225 */         status.addProperty("otpSerialNumber", Base64Utils.encodeUrl(bean.getOtpSerialNumber()));
/*     */       }
/*     */       
/* 228 */       if (bean.getIssueDateTime() != null) {
/* 229 */         status.addProperty("issueDateTime", bean.getIssueDateTime());
/*     */       }
/*     */       
/* 232 */       if (!StringUtils.isEmpty(bean.getCountryCode())) {
/* 233 */         status.addProperty("countryCode", bean.getCountryCode());
/*     */       }
/*     */       
/* 236 */       if (!StringUtils.isEmpty(bean.getDeviceOS())) {
/* 237 */         status.addProperty("deviceOS", bean.getDeviceOS());
/*     */       }
/*     */       
/* 240 */       if (!StringUtils.isEmpty(bean.getDeviceModel())) {
/* 241 */         status.addProperty("deviceModel", Base64Utils.encodeUrl(bean.getDeviceModel()));
/*     */       }
/*     */       
/* 244 */       if (resp.getIssueCodeData() != null) {
/*     */         
/* 246 */         IssueCodeApiData icData = resp.getIssueCodeData();
/*     */         
/* 248 */         if (!StringUtils.isEmpty(icData.getIssueCode())) {
/* 249 */           status.addProperty("issueCode", Base64Utils.encodeUrl(icData.getIssueCode()));
/*     */         }
/*     */         
/* 252 */         if (icData.getIssueCodeRegTs() != null) {
/* 253 */           status.addProperty("issueCodeRegDateTime", icData.getIssueCodeRegTs());
/*     */         }
/*     */         
/* 256 */         if (icData.getIssueCodeExpired() != null) {
/* 257 */           status.addProperty("issueCodeExpired", icData.getIssueCodeExpired());
/*     */         }
/*     */         
/* 260 */         if (icData.getIssueCodeFailCnt() != null) {
/* 261 */           status.addProperty("issueCodeFailCnt", Integer.valueOf(icData.getIssueCodeFailCnt().intValue()));
/*     */         }
/*     */         
/* 264 */         if (icData.getIssueCodeExpiredYn() != null) {
/* 265 */           status.addProperty("issueCodeExpiredYn", icData.getIssueCodeExpiredYn().booleanValue() ? "Y" : "N");
/*     */         }
/*     */         
/* 268 */         if (icData.getIssueCodeFailExceedYn() != null) {
/* 269 */           status.addProperty("issueCodeFailExceedYn", icData.getIssueCodeFailExceedYn().booleanValue() ? "Y" : "N");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 274 */       jsonObject.add("data", (JsonElement)status);
/*     */     }
/* 276 */     else if (resp.getTokenStats() != null) {
/*     */       
/* 278 */       JsonObject status = new JsonObject();
/* 279 */       TokenStats stats = resp.getTokenStats();
/* 280 */       status.addProperty("total", Integer.valueOf(stats.getTotal()));
/* 281 */       status.addProperty("available", Integer.valueOf(stats.getAvailable()));
/* 282 */       status.addProperty("occupied", Integer.valueOf(stats.getOccupied()));
/* 283 */       status.addProperty("discard", Integer.valueOf(stats.getDiscard()));
/*     */       
/* 285 */       jsonObject.add("data", (JsonElement)status);
/*     */     }
/* 287 */     else if (resp.getServiceLogs() != null) {
/*     */       
/* 289 */       JsonArray jsonArray = new JsonArray();
/*     */       
/* 291 */       List<ServiceLogVO> logs = resp.getServiceLogs();
/*     */       
/* 293 */       for (ServiceLogVO vo : logs) {
/* 294 */         JsonObject jobj = new JsonObject();
/*     */         
/* 296 */         jobj.addProperty("opType", vo.getOpType().getHexCode());
/* 297 */         jobj.addProperty("regDateTime", vo.getTsReg());
/*     */         
/* 299 */         if (vo.getCustomData() instanceof GlobalWibeeLogData) {
/* 300 */           String regEmpNo = ((GlobalWibeeLogData)vo.getCustomData()).getRegEmpNo();
/* 301 */           jobj.addProperty("regEmpNo", Base64Utils.encodeUrl(regEmpNo));
/*     */         } 
/*     */         
/* 304 */         jsonArray.add((JsonElement)jobj);
/*     */       } 
/*     */       
/* 307 */       jsonObject.add("logs", (JsonElement)jsonArray);
/*     */     } 
/*     */     
/* 310 */     return (JsonElement)jsonObject;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String decode(JsonObject jsonObject, String key) {
/* 315 */     if (jsonObject.has(key)) {
/* 316 */       String str = jsonObject.get(key).getAsString();
/* 317 */       return Base64Utils.decode(str);
/*     */     } 
/*     */     
/* 320 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\ext\GlobalWibeeWebApiResponseSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */