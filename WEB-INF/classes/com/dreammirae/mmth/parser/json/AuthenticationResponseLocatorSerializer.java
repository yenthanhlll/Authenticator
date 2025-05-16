/*    */ package WEB-INF.classes.com.dreammirae.mmth.parser.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
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
/*    */ public class AuthenticationResponseLocatorSerializer
/*    */   implements JsonSerializer<AuthenticationResponseLocator>, JsonDeserializer<AuthenticationResponseLocator>
/*    */ {
/*    */   public AuthenticationResponseLocator deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
/* 19 */     AuthenticationResponseLocator data = new AuthenticationResponseLocator();
/*    */     
/* 21 */     JsonObject jsonObject = json.getAsJsonObject();
/*    */     
/* 23 */     data.jsonDeserialization(jsonObject, context);
/*    */     
/* 25 */     return data;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(AuthenticationResponseLocator src, Type type, JsonSerializationContext context) {
/* 30 */     JsonObject jsonObject = new JsonObject();
/* 31 */     src.jsonSerialize(jsonObject, type, context);
/* 32 */     return (JsonElement)jsonObject;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\AuthenticationResponseLocatorSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */