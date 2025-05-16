/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.transport.OtpResponseParam;
/*    */ import com.dreammirae.mmth.misc.Base64Utils;
/*    */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OtpResponseParamSerializer
/*    */   implements JsonSerializer<OtpResponseParam>, JsonDeserializer<OtpResponseParam>
/*    */ {
/*    */   public OtpResponseParam deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 25 */     OtpResponseParam reqData = new OtpResponseParam();
/*    */     
/* 27 */     JsonObject obj = json.getAsJsonObject();
/*    */     
/* 29 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)obj.entrySet()) {
/*    */       
/* 31 */       String value = Base64Utils.decode(((JsonElement)entry.getValue()).getAsString());
/*    */       
/* 33 */       if (SysEnvCommon.applyEnvCaseInsensitive())
/*    */       {
/* 35 */         if (SysEnvCommon.isCaseInsensitiveKey(entry.getKey())) {
/* 36 */           value = value.toLowerCase();
/*    */         }
/*    */       }
/*    */       
/* 40 */       if ("userName".equals(entry.getKey())) {
/* 41 */         reqData.setUserName(value); continue;
/* 42 */       }  if ("otp".equals(entry.getKey())) {
/* 43 */         reqData.setOtp(value); continue;
/* 44 */       }  if ("tid".equals(entry.getKey())) {
/* 45 */         reqData.setTid(value); continue;
/* 46 */       }  if ("deviceId".equals(entry.getKey())) {
/* 47 */         reqData.setDeviceId(value);
/*    */       }
/*    */     } 
/*    */     
/* 51 */     return reqData;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonElement serialize(OtpResponseParam src, Type typeOfSrc, JsonSerializationContext context) {
/* 57 */     JsonObject obj = new JsonObject();
/*    */     
/* 59 */     if (!StringUtils.isEmpty(src.getUserName())) {
/* 60 */       obj.addProperty("userName", Base64Utils.encode(src.getUserName()));
/* 61 */     } else if (!StringUtils.isEmpty(src.getTid())) {
/* 62 */       obj.addProperty("tid", Base64Utils.encode(src.getTid()));
/* 63 */     } else if (!StringUtils.isEmpty(src.getOtp())) {
/* 64 */       obj.addProperty("otp", Base64Utils.encode(src.getOtp()));
/* 65 */     } else if (!StringUtils.isEmpty(src.getDeviceId())) {
/* 66 */       obj.addProperty("deviceId", Base64Utils.encode(src.getDeviceId()));
/*    */     } 
/*    */     
/* 69 */     return (JsonElement)obj;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\json\OtpResponseParamSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */