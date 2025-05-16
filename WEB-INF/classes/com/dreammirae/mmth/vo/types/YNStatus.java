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
/*    */ public enum YNStatus
/*    */ {
/* 13 */   N("N", false, "common.N"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   Y("Y", true, "common.Y");
/*    */   
/*    */   private final String code;
/*    */   
/*    */   private final boolean bool;
/*    */   private final String messageKey;
/*    */   
/*    */   YNStatus(String code, boolean bool, String messageKey) {
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
/*    */   public static com.dreammirae.mmth.vo.types.YNStatus getYNStatus(String code) {
/* 56 */     for (com.dreammirae.mmth.vo.types.YNStatus type : values()) {
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
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 68 */     List<KeyValuePair<String, String>> list = new ArrayList<>(2);
/*    */     
/* 70 */     for (com.dreammirae.mmth.vo.types.YNStatus type : values()) {
/* 71 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 74 */     return list;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\YNStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */