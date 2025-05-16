/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.transport.BizReqData;
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
/*    */ public class BizReqDataSerializer
/*    */   implements JsonSerializer<BizReqData>, JsonDeserializer<BizReqData>
/*    */ {
/*    */   public BizReqData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 25 */     BizReqData reqData = new BizReqData();
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
/* 42 */       }  if ("deviceId".equals(entry.getKey())) {
/* 43 */         reqData.setDeviceId(value); continue;
/* 44 */       }  if ("serverPin".equals(entry.getKey())) {
/* 45 */         reqData.setServerPin(value); continue;
/* 46 */       }  if ("otp".equals(entry.getKey())) {
/* 47 */         reqData.setOtp(value); continue;
/* 48 */       }  if ("tid".equals(entry.getKey())) {
/* 49 */         reqData.setTid(value); continue;
/* 50 */       }  if ("newServerPin".equals(entry.getKey())) {
/* 51 */         reqData.setNewServerPin(value);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 56 */     return reqData;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonElement serialize(BizReqData src, Type typeOfSrc, JsonSerializationContext context) {
/* 62 */     JsonObject obj = new JsonObject();
/*    */     
/* 64 */     if (!StringUtils.isEmpty(src.getUserName())) {
/* 65 */       obj.addProperty("userName", Base64Utils.encode(src.getUserName()));
/* 66 */     } else if (!StringUtils.isEmpty(src.getDeviceId())) {
/* 67 */       obj.addProperty("deviceId", Base64Utils.encode(src.getDeviceId()));
/* 68 */     } else if (!StringUtils.isEmpty(src.getTid())) {
/* 69 */       obj.addProperty("tid", Base64Utils.encode(src.getTid()));
/* 70 */     } else if (!StringUtils.isEmpty(src.getOtp())) {
/* 71 */       obj.addProperty("otp", Base64Utils.encode(src.getOtp()));
/* 72 */     } else if (!StringUtils.isEmpty(src.getServerPin())) {
/* 73 */       obj.addProperty("serverPin", Base64Utils.encode(src.getServerPin()));
/* 74 */     } else if (!StringUtils.isEmpty(src.getNewServerPin())) {
/* 75 */       obj.addProperty("newServerPin", Base64Utils.encode(src.getNewServerPin()));
/*    */     } 
/*    */     
/* 78 */     return (JsonElement)obj;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\json\BizReqDataSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */