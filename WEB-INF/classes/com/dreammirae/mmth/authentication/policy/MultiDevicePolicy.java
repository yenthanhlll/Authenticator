/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.policy;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public enum MultiDevicePolicy
/*    */ {
/* 10 */   ALLOWED_ALL("MultiDevicePolicy.ALLOWED_ALL"),
/* 11 */   DISALLOWED_ALL("MultiDevicePolicy.DISALLOWED_ALL"),
/* 12 */   INDIVIDUAL("MultiDevicePolicy.INDIVIDUAL");
/*    */   
/*    */   private final String messageKey;
/*    */   
/*    */   MultiDevicePolicy(String messageKey) {
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
/* 28 */     for (com.dreammirae.mmth.authentication.policy.MultiDevicePolicy type : values()) {
/* 29 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 32 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair(com.dreammirae.mmth.authentication.policy.MultiDevicePolicy... exculdes) {
/* 37 */     List<KeyValuePair<String, String>> list = new ArrayList<>(3);
/*    */     
/* 39 */     for (com.dreammirae.mmth.authentication.policy.MultiDevicePolicy type : values()) {
/*    */       
/* 41 */       com.dreammirae.mmth.authentication.policy.MultiDevicePolicy[] arrayOfMultiDevicePolicy = exculdes; int i = arrayOfMultiDevicePolicy.length; byte b = 0; while (true) { if (b < i) { com.dreammirae.mmth.authentication.policy.MultiDevicePolicy ex = arrayOfMultiDevicePolicy[b];
/*    */           
/* 43 */           if (type.equals(ex))
/*    */             break; 
/*    */           b++;
/*    */           continue; }
/*    */         
/* 48 */         list.add(new KeyValuePair(type.name(), type.getMessageKey())); break; }
/*    */     
/*    */     } 
/* 51 */     return list;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.authentication.policy.MultiDevicePolicy valueByName(String name) {
/*    */     try {
/* 57 */       return valueOf(name);
/* 58 */     } catch (Exception e) {
/* 59 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\policy\MultiDevicePolicy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */