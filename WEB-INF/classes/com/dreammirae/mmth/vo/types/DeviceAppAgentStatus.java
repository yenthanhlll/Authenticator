/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum DeviceAppAgentStatus
/*    */ {
/* 15 */   NOT_AVAILABLE("0", "DeviceAppAgentStatus.NOT_AVAILABLE"),
/* 16 */   AVAIABLE("1", "DeviceAppAgentStatus.AVAIABLE");
/*    */   
/*    */   private final String code;
/*    */   private final String messageKey;
/*    */   
/*    */   DeviceAppAgentStatus(String code, String messageKey) {
/* 22 */     this.code = code;
/* 23 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 30 */     return this.code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessageKey() {
/* 37 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.DeviceAppAgentStatus getUserDeviceStatus(String code) {
/* 42 */     for (com.dreammirae.mmth.vo.types.DeviceAppAgentStatus type : values()) {
/* 43 */       if (type.code.equals(code)) {
/* 44 */         return type;
/*    */       }
/*    */     } 
/* 47 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 52 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*    */     
/* 54 */     for (com.dreammirae.mmth.vo.types.DeviceAppAgentStatus type : values()) {
/* 55 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 58 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.DeviceAppAgentStatus getUserDeviceStatusByName(String name) {
/* 62 */     for (com.dreammirae.mmth.vo.types.DeviceAppAgentStatus type : values()) {
/* 63 */       if (type.name().equalsIgnoreCase(name)) {
/* 64 */         return type;
/*    */       }
/*    */     } 
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\DeviceAppAgentStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */