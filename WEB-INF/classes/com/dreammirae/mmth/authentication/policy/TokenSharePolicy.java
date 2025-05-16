/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.policy;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public enum TokenSharePolicy
/*    */ {
/* 10 */   BY_USER("TokenSharePolicy.BY_USER"),
/* 11 */   BY_DEVICE("TokenSharePolicy.BY_DEVICE"),
/* 12 */   BY_INDIVIDUAL("TokenSharePolicy.BY_INDIVIDUAL");
/*    */   
/*    */   private final String messageKey;
/*    */   
/*    */   TokenSharePolicy(String messageKey) {
/* 17 */     this.messageKey = messageKey;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 21 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 26 */     List<KeyValuePair<String, String>> list = new ArrayList<>(3);
/*    */     
/* 28 */     for (com.dreammirae.mmth.authentication.policy.TokenSharePolicy type : values()) {
/* 29 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 32 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.authentication.policy.TokenSharePolicy valueByName(String name) {
/*    */     try {
/* 38 */       return valueOf(name);
/* 39 */     } catch (Exception e) {
/* 40 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\policy\TokenSharePolicy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */