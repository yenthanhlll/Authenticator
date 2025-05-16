/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public enum AckCodes
/*    */ {
/* 10 */   NONE("N", "AckCodes.NONE", false),
/* 11 */   ACK("Y", "AckCodes.ACK", true);
/*    */   
/*    */   private final String code;
/*    */   private final String messageKey;
/*    */   private final boolean bool;
/*    */   
/*    */   AckCodes(String code, String messageKey, boolean bool) {
/* 18 */     this.code = code;
/* 19 */     this.messageKey = messageKey;
/* 20 */     this.bool = bool;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 24 */     return this.code;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 28 */     return this.messageKey;
/*    */   }
/*    */   
/*    */   public boolean getBoolean() {
/* 32 */     return this.bool;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.AckCodes getAckCode(String code) {
/* 37 */     for (com.dreammirae.mmth.vo.types.AckCodes type : values()) {
/* 38 */       if (type.code.equals(code)) {
/* 39 */         return type;
/*    */       }
/*    */     } 
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.AckCodes getAckCode(boolean bool) {
/* 47 */     for (com.dreammirae.mmth.vo.types.AckCodes type : values()) {
/* 48 */       if (type.bool == bool) {
/* 49 */         return type;
/*    */       }
/*    */     } 
/* 52 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 57 */     List<KeyValuePair<String, String>> list = new ArrayList<>(2);
/*    */     
/* 59 */     for (com.dreammirae.mmth.vo.types.AckCodes type : values()) {
/* 60 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 63 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.AckCodes getAckCodeByName(String name) {
/* 67 */     for (com.dreammirae.mmth.vo.types.AckCodes type : values()) {
/* 68 */       if (type.name().equalsIgnoreCase(name)) {
/* 69 */         return type;
/*    */       }
/*    */     } 
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\AckCodes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */