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
/*    */ public enum SettingEnabled
/*    */ {
/* 13 */   ENABLED(true, "SettingEnabled.ENABLED"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   DISABLED(false, "SettingEnabled.DISABLED");
/*    */   
/*    */   private final boolean bool;
/*    */   
/*    */   private final String messageKey;
/*    */   
/*    */   SettingEnabled(boolean bool, String messageKey) {
/* 25 */     this.bool = bool;
/* 26 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessageKey() {
/* 33 */     return this.messageKey;
/*    */   }
/*    */   
/*    */   public boolean toBoolean() {
/* 37 */     return this.bool;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.SettingEnabled getSettingEnabled(boolean bool) {
/* 45 */     return bool ? ENABLED : DISABLED;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 51 */     List<KeyValuePair<String, String>> list = new ArrayList<>(2);
/*    */     
/* 53 */     for (com.dreammirae.mmth.vo.types.SettingEnabled type : values()) {
/* 54 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 57 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.SettingEnabled valueByName(String name) {
/*    */     try {
/* 62 */       return valueOf(name);
/* 63 */     } catch (Exception e) {
/* 64 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\SettingEnabled.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */