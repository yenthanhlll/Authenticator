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
/*    */ public enum UserStatus
/*    */ {
/* 15 */   NOT_AVAILABLE("0", "UserStatus.NOT_AVAILABLE"),
/* 16 */   AVAILABLE("1", "UserStatus.AVAILABLE"),
/* 17 */   LOST_STOLEN("2", "UserStatus.LOST_STOLEN"),
/* 18 */   SUSPEND("3", "UserStatus.SUSPEND"),
/* 19 */   DISCARD("9", "UserStatus.DISCARD");
/*    */   
/*    */   private final String code;
/*    */   private final String messageKey;
/*    */   
/*    */   UserStatus(String code, String messageKey) {
/* 25 */     this.code = code;
/* 26 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 33 */     return this.code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessageKey() {
/* 40 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.UserStatus getUserStatus(String code) {
/* 45 */     for (com.dreammirae.mmth.vo.types.UserStatus type : values()) {
/* 46 */       if (type.code.equals(code)) {
/* 47 */         return type;
/*    */       }
/*    */     } 
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 55 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*    */     
/* 57 */     for (com.dreammirae.mmth.vo.types.UserStatus type : values()) {
/* 58 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 61 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.UserStatus getUserStatusByName(String name) {
/* 65 */     for (com.dreammirae.mmth.vo.types.UserStatus type : values()) {
/* 66 */       if (type.name().equalsIgnoreCase(name)) {
/* 67 */         return type;
/*    */       }
/*    */     } 
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\UserStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */