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
/*    */ public enum DisabledStatus
/*    */ {
/* 13 */   ENABLED("N", false, "DisabledStatus.ENABLED"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   DISABLED("Y", true, "DisabledStatus.DISABLED");
/*    */   
/*    */   private final String code;
/*    */   
/*    */   private final boolean bool;
/*    */   private final String messageKey;
/*    */   
/*    */   DisabledStatus(String code, boolean bool, String messageKey) {
/* 26 */     this.code = code;
/* 27 */     this.bool = bool;
/* 28 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 35 */     return this.code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessageKey() {
/* 42 */     return this.messageKey;
/*    */   }
/*    */   
/*    */   public boolean toBoolean() {
/* 46 */     return this.bool;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.DisabledStatus getDisabledStatus(String code) {
/* 56 */     for (com.dreammirae.mmth.vo.types.DisabledStatus type : values()) {
/* 57 */       if (type.code.equals(code)) {
/* 58 */         return type;
/*    */       }
/*    */     } 
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.DisabledStatus getDisabledStatus(boolean bool) {
/* 70 */     return bool ? DISABLED : ENABLED;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 75 */     List<KeyValuePair<String, String>> list = new ArrayList<>(2);
/*    */     
/* 77 */     for (com.dreammirae.mmth.vo.types.DisabledStatus type : values()) {
/* 78 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 81 */     return list;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\DisabledStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */