/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public enum DeviceTypes
/*    */ {
/* 10 */   ANDROID_DEVICE("AD", "DeviceTypes.ANDROID_DEVICE"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   ANDROID_WIFI("AW", "DeviceTypes.ANDROID_DEVICE"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   ANDROID_ID("AI", "DeviceTypes.ANDROID_DEVICE"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   IOS_UUID("IU", "DeviceTypes.ANDROID_DEVICE"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   ANANYMOUS("NA", "DeviceTypes.ANANYMOUS");
/*    */ 
/*    */   
/*    */   private final String code;
/*    */ 
/*    */   
/*    */   private final String messageKey;
/*    */ 
/*    */ 
/*    */   
/*    */   DeviceTypes(String code, String messageKey) {
/* 45 */     this.code = code;
/* 46 */     this.messageKey = messageKey;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 50 */     return this.code;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 54 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.DeviceTypes getDeviceType(String code) {
/* 61 */     for (com.dreammirae.mmth.vo.types.DeviceTypes type : values()) {
/* 62 */       if (type.code.equals(code)) {
/* 63 */         return type;
/*    */       }
/*    */     } 
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 71 */     List<KeyValuePair<String, String>> list = new ArrayList<>(2);
/*    */     
/* 73 */     for (com.dreammirae.mmth.vo.types.DeviceTypes type : values()) {
/* 74 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 77 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.DeviceTypes getDeviceTypeByName(String name) {
/* 81 */     for (com.dreammirae.mmth.vo.types.DeviceTypes type : values()) {
/* 82 */       if (type.name().equalsIgnoreCase(name)) {
/* 83 */         return type;
/*    */       }
/*    */     } 
/* 86 */     return null;
/*    */   }
/*    */   
/*    */   public abstract AgentOsTypes getOsType();
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\DeviceTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */