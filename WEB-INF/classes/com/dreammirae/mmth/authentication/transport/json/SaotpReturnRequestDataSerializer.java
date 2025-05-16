/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.transport.SaotpReturnRequestData;
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
/*    */ public class SaotpReturnRequestDataSerializer
/*    */   implements JsonSerializer<SaotpReturnRequestData>, JsonDeserializer<SaotpReturnRequestData>
/*    */ {
/*    */   public SaotpReturnRequestData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 20 */     JsonObject jsonObject = json.getAsJsonObject();
/*    */     
/* 22 */     SaotpReturnRequestData data = new SaotpReturnRequestData();
/*    */     
/* 24 */     data.jsonDeserialization(jsonObject, context);
/*    */     
/* 26 */     return data;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(SaotpReturnRequestData src, Type typeOfSrc, JsonSerializationContext context) {
/* 31 */     return src.jsonSerialization(context);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\json\SaotpReturnRequestDataSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */