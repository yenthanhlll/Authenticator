/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public enum TokenStatus
/*    */ {
/* 10 */   AVAILABLE("0", "TokenStatus.AVAILABLE"),
/* 11 */   OCCUPIED("1", "TokenStatus.OCCUPIED"),
/* 12 */   LOST("2", "TokenStatus.LOST"),
/* 13 */   DISCARD("9", "TokenStatus.DISCARD");
/*    */   
/*    */   private final String code;
/*    */   private final String messageKey;
/*    */   
/*    */   TokenStatus(String code, String messageKey) {
/* 19 */     this.code = code;
/* 20 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 27 */     return this.code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessageKey() {
/* 34 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.TokenStatus getTokenStatus(String code) {
/* 39 */     for (com.dreammirae.mmth.vo.types.TokenStatus type : values()) {
/* 40 */       if (type.code.equals(code)) {
/* 41 */         return type;
/*    */       }
/*    */     } 
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 49 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*    */     
/* 51 */     for (com.dreammirae.mmth.vo.types.TokenStatus type : values()) {
/* 52 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 55 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.TokenStatus getTokenStatusByName(String name) {
/* 59 */     for (com.dreammirae.mmth.vo.types.TokenStatus type : values()) {
/* 60 */       if (type.name().equalsIgnoreCase(name)) {
/* 61 */         return type;
/*    */       }
/*    */     } 
/* 64 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\TokenStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */