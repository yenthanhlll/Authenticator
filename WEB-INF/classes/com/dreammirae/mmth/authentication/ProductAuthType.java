/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum ProductAuthType
/*    */ {
/*  9 */   PATTERN(1, "PATTERN"),
/* 10 */   PIN(2, "PIN"),
/* 11 */   FINGER(3, "FINGER");
/*    */   
/*    */   private final int code;
/*    */   
/*    */   private final String messageKey;
/*    */   
/*    */   private static Map<Integer, com.dreammirae.mmth.authentication.ProductAuthType> productAuthMap;
/*    */ 
/*    */   
/*    */   static {
/* 21 */     productAuthMap = new HashMap<>();
/* 22 */     for (com.dreammirae.mmth.authentication.ProductAuthType type : EnumSet.<com.dreammirae.mmth.authentication.ProductAuthType>allOf(com.dreammirae.mmth.authentication.ProductAuthType.class))
/*    */     {
/* 24 */       productAuthMap.put(Integer.valueOf(type.getCode()), type);
/*    */     }
/*    */   }
/*    */   
/*    */   ProductAuthType(int code, String messageKey) {
/* 29 */     this.code = code;
/* 30 */     this.messageKey = messageKey;
/*    */   }
/*    */   
/*    */   public int getCode() {
/* 34 */     return this.code;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 38 */     return this.messageKey;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.authentication.ProductAuthType getAuthType(int code) {
/* 42 */     return productAuthMap.get(Integer.valueOf(code));
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\ProductAuthType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */