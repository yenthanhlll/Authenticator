/*    */ package WEB-INF.classes.com.dreammirae.mmth.parser.json;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ 
/*    */ public class DeviceTypeSerializer
/*    */   implements JsonSerializer<DeviceTypes>, JsonDeserializer<DeviceTypes>
/*    */ {
/*    */   public JsonElement serialize(DeviceTypes bean, Type type, JsonSerializationContext ctx) {
/* 17 */     return ctx.serialize(bean.getCode());
/*    */   }
/*    */ 
/*    */   
/*    */   public DeviceTypes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 22 */     String deviceType = json.getAsString();
/* 23 */     return DeviceTypes.getDeviceType(deviceType);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\DeviceTypeSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */