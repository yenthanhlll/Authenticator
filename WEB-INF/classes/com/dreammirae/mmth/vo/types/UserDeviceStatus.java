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
/*    */ public enum UserDeviceStatus
/*    */ {
/* 15 */   NOT_AVAILABLE("0", "UserDeviceStatus.NOT_AVAILABLE"),
/* 16 */   AVAILABLE("1", "UserDeviceStatus.AVAILABLE"),
/* 17 */   LOST_STOLEN("2", "UserDeviceStatus.LOST_STOLEN"),
/* 18 */   DISCARD("9", "UserDeviceStatus.DISCARD");
/*    */   
/*    */   private final String code;
/*    */   private final String messageKey;
/*    */   
/*    */   UserDeviceStatus(String code, String messageKey) {
/* 24 */     this.code = code;
/* 25 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 32 */     return this.code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessageKey() {
/* 39 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.UserDeviceStatus getUserDeviceStatus(String code) {
/* 44 */     for (com.dreammirae.mmth.vo.types.UserDeviceStatus type : values()) {
/* 45 */       if (type.code.equals(code)) {
/* 46 */         return type;
/*    */       }
/*    */     } 
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 54 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*    */     
/* 56 */     for (com.dreammirae.mmth.vo.types.UserDeviceStatus type : values()) {
/* 57 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 60 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.UserDeviceStatus getUserDeviceStatusByName(String name) {
/* 64 */     for (com.dreammirae.mmth.vo.types.UserDeviceStatus type : values()) {
/* 65 */       if (type.name().equalsIgnoreCase(name)) {
/* 66 */         return type;
/*    */       }
/*    */     } 
/* 69 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\UserDeviceStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */