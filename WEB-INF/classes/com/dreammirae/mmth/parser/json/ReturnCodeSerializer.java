/*    */ package WEB-INF.classes.com.dreammirae.mmth.parser.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ 
/*    */ public class ReturnCodeSerializer
/*    */   implements JsonSerializer<ReturnCodes>, JsonDeserializer<ReturnCodes>
/*    */ {
/*    */   public JsonElement serialize(ReturnCodes bean, Type type, JsonSerializationContext ctx) {
/* 17 */     return ctx.serialize(bean.getCode());
/*    */   }
/*    */ 
/*    */   
/*    */   public ReturnCodes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 22 */     String returnCode = json.getAsString();
/* 23 */     return ReturnCodes.getReturnCode(returnCode);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\ReturnCodeSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */