/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.transport.BizRespData;
/*    */ import com.dreammirae.mmth.authentication.transport.IUserDevice;
/*    */ import com.dreammirae.mmth.misc.Base64Utils;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BizRespDataSerializer
/*    */   implements JsonSerializer<BizRespData>
/*    */ {
/*    */   public JsonElement serialize(BizRespData src, Type typeOfSrc, JsonSerializationContext context) {
/* 22 */     JsonObject jsonObject = new JsonObject();
/*    */     
/* 24 */     if (src.getReturnCode() != null) {
/* 25 */       jsonObject.add("returnCode", context.serialize(src.getReturnCode()));
/*    */     }
/*    */     
/* 28 */     if (!StringUtils.isEmpty(src.getIssueCode())) {
/* 29 */       jsonObject.addProperty("issueCode", Base64Utils.encode(src.getIssueCode()));
/*    */     }
/*    */     
/* 32 */     if (src.getDevices() != null) {
/* 33 */       jsonObject.addProperty("deviceCnt", Integer.valueOf(src.getDeviceCnt()));
/*    */       
/* 35 */       JsonArray arr = new JsonArray();
/*    */ 
/*    */       
/* 38 */       List<? extends IUserDevice> devices = src.getDevices();
/*    */       
/* 40 */       if (!devices.isEmpty())
/*    */       {
/* 42 */         for (IUserDevice d : devices) {
/* 43 */           arr.add(context.serialize(d, IUserDevice.class));
/*    */         }
/*    */       }
/*    */       
/* 47 */       jsonObject.add("devices", (JsonElement)arr);
/*    */     } 
/*    */     
/* 50 */     return (JsonElement)jsonObject;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\json\BizRespDataSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */