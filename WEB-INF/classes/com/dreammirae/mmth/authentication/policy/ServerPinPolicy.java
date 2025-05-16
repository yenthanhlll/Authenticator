/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.policy;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public enum ServerPinPolicy
/*    */ {
/* 10 */   USER_DEFINED("ServerPinPolicy.USER_DEFINED"),
/* 11 */   FIDO_AUTHENTICATED("ServerPinPolicy.FIDO_AUTHENTICATED");
/*    */   
/*    */   private final String messageKey;
/*    */   
/*    */   ServerPinPolicy(String messageKey) {
/* 16 */     this.messageKey = messageKey;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 20 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 25 */     List<KeyValuePair<String, String>> list = new ArrayList<>(2);
/*    */     
/* 27 */     for (com.dreammirae.mmth.authentication.policy.ServerPinPolicy type : values()) {
/* 28 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 31 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.authentication.policy.ServerPinPolicy valueByName(String name) {
/*    */     try {
/* 37 */       return valueOf(name);
/* 38 */     } catch (Exception e) {
/* 39 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\policy\ServerPinPolicy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */