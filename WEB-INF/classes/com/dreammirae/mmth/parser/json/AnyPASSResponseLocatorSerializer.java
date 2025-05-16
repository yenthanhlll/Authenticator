/*    */ package WEB-INF.classes.com.dreammirae.mmth.parser.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.bean.AnyPASSResponseLocator;
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
/*    */ public class AnyPASSResponseLocatorSerializer
/*    */   implements JsonSerializer<AnyPASSResponseLocator>, JsonDeserializer<AnyPASSResponseLocator>
/*    */ {
/*    */   public AnyPASSResponseLocator deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
/* 19 */     AnyPASSResponseLocator data = new AnyPASSResponseLocator();
/*    */     
/* 21 */     JsonObject jsonObject = json.getAsJsonObject();
/*    */     
/* 23 */     data.jsonDeserialization(jsonObject, context);
/*    */     
/* 25 */     return data;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(AnyPASSResponseLocator src, Type type, JsonSerializationContext context) {
/* 30 */     JsonObject jsonObject = new JsonObject();
/* 31 */     src.jsonSerialize(jsonObject, type, context);
/* 32 */     return (JsonElement)jsonObject;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\AnyPASSResponseLocatorSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */