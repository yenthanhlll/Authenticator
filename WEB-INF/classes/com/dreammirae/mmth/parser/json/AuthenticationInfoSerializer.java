/*    */ package WEB-INF.classes.com.dreammirae.mmth.parser.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.bean.AuthenticationInfos;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthenticationInfoSerializer
/*    */   implements JsonSerializer<AuthenticationInfos>, JsonDeserializer<AuthenticationInfos>
/*    */ {
/*    */   private static final String KEY_FAIL_CNT = "failCnt";
/*    */   private static final String KEY_FAIL_TIMESTAMP = "failTs";
/*    */   private static final String KEY_SUCCESS_CNT = "successCnt";
/*    */   private static final String KEY_SUCCESS_TIMESTAMP = "successTs";
/*    */   
/*    */   public AuthenticationInfos deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
/* 25 */     JsonObject jsonObject = json.getAsJsonObject();
/*    */     
/* 27 */     AuthenticationInfos data = new AuthenticationInfos();
/*    */     
/* 29 */     if (jsonObject.has("failCnt")) {
/* 30 */       data.setFailCnt(jsonObject.get("failCnt").getAsInt());
/*    */     }
/*    */     
/* 33 */     if (jsonObject.has("failTs")) {
/* 34 */       Long ts = Long.valueOf(jsonObject.get("failTs").getAsLong());
/* 35 */       data.setLatestTsFail(ts.longValue());
/*    */     } 
/*    */     
/* 38 */     if (jsonObject.has("successCnt")) {
/* 39 */       data.setSuccessCnt(jsonObject.get("successCnt").getAsInt());
/*    */     }
/*    */     
/* 42 */     if (jsonObject.has("successTs")) {
/* 43 */       Long ts = Long.valueOf(jsonObject.get("successTs").getAsLong());
/* 44 */       data.setLatestTsSuccess(ts.longValue());
/*    */     } 
/*    */ 
/*    */     
/* 48 */     return data;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonElement serialize(AuthenticationInfos info, Type arg1, JsonSerializationContext ctx) {
/* 54 */     JsonObject json = new JsonObject();
/* 55 */     json.addProperty("failCnt", Integer.valueOf(info.getFailCnt()));
/*    */     
/* 57 */     if (info.getLatestTsFail() > 0L) {
/* 58 */       json.addProperty("failTs", Long.valueOf(info.getLatestTsFail()));
/*    */     }
/*    */ 
/*    */     
/* 62 */     json.addProperty("successCnt", Integer.valueOf(info.getSuccessCnt()));
/* 63 */     if (info.getLatestTsSuccess() > 0L) {
/* 64 */       json.addProperty("successTs", Long.valueOf(info.getLatestTsSuccess()));
/*    */     }
/*    */     
/* 67 */     return (JsonElement)json;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\AuthenticationInfoSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */