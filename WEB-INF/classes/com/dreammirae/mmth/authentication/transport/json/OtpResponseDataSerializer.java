/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.transport.OtpResponseData;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OtpResponseDataSerializer
/*    */   implements JsonSerializer<OtpResponseData>
/*    */ {
/*    */   public JsonElement serialize(OtpResponseData src, Type typeOfSrc, JsonSerializationContext context) {
/* 18 */     JsonObject jsonObject = new JsonObject();
/*    */     
/* 20 */     if (src.getReturnCode() != null) {
/* 21 */       jsonObject.add("returnCode", context.serialize(src.getReturnCode()));
/*    */     }
/*    */     
/* 24 */     return (JsonElement)jsonObject;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\json\OtpResponseDataSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */