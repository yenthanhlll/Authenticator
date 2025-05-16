/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.transport.json;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.transport.IUserDevice;
/*    */ import com.dreammirae.mmth.authentication.transport.TransportKeys;
/*    */ import com.dreammirae.mmth.misc.Base64Utils;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserDeviceSerializer
/*    */   implements JsonSerializer<IUserDevice>
/*    */ {
/*    */   public JsonElement serialize(IUserDevice src, Type typeOfSrc, JsonSerializationContext context) {
/* 19 */     JsonObject jsonObject = new JsonObject();
/*    */     
/* 21 */     if (!StringUtils.isEmpty(src.getDeviceId())) {
/* 22 */       jsonObject.addProperty("deviceId", Base64Utils.encode(src.getDeviceId()));
/*    */     }
/*    */     
/* 25 */     if (src.getDeviceType() != null) {
/* 26 */       jsonObject.addProperty("deviceType", Base64Utils.encode(src.getDeviceType().getCode()));
/*    */     }
/*    */     
/* 29 */     if (!StringUtils.isEmpty(src.getModel())) {
/* 30 */       jsonObject.addProperty("model", Base64Utils.encode(src.getModel()));
/*    */     }
/*    */     
/* 33 */     if (!StringUtils.isEmpty(src.getAlias())) {
/* 34 */       jsonObject.addProperty("alias", Base64Utils.encode(src.getAlias()));
/*    */     }
/*    */     
/* 37 */     if (src.isDisabled() != null) {
/* 38 */       jsonObject.addProperty("disabled", Base64Utils.encode(src.isDisabled().booleanValue() ? "Y" : "N"));
/*    */     }
/*    */     
/* 41 */     if (src.getTsReg() > 0L) {
/* 42 */       jsonObject.addProperty("regDateTime", Base64Utils.encode(TransportKeys.toDateTime(src.getTsReg())));
/*    */     }
/*    */     
/* 45 */     if (src.getTsUpdated() > 0L) {
/* 46 */       jsonObject.addProperty("updatedDateTime", Base64Utils.encode(TransportKeys.toDateTime(src.getTsUpdated())));
/*    */     }
/*    */     
/* 49 */     if (src.getTsExpired() > 0L) {
/* 50 */       jsonObject.addProperty("expiredDateTime", Base64Utils.encode(TransportKeys.toDateTime(src.getTsExpired())));
/*    */     }
/*    */     
/* 53 */     return (JsonElement)jsonObject;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\transport\json\UserDeviceSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */