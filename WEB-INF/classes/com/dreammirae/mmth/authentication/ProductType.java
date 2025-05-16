/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum ProductType
/*    */ {
/*  9 */   FIDO(1, "FIDO"),
/* 10 */   BIOTP(2, "BIOTP"),
/* 11 */   HWOTP(8, "HWOTP"),
/* 12 */   MATRIX(16, "MATRIX"),
/* 13 */   SAOTP(32, "SAOTP"),
/* 14 */   NONE(64, "NONE"),
/*    */   
/* 16 */   MIRAEASSET(128, "MIRAEASSET");
/*    */   
/*    */   private final int code;
/*    */   
/*    */   private final String messageKey;
/*    */   
/*    */   private static Map<Integer, com.dreammirae.mmth.authentication.ProductType> productTypeMap;
/*    */ 
/*    */   
/*    */   static {
/* 26 */     productTypeMap = new HashMap<>();
/* 27 */     for (com.dreammirae.mmth.authentication.ProductType type : EnumSet.<com.dreammirae.mmth.authentication.ProductType>allOf(com.dreammirae.mmth.authentication.ProductType.class))
/*    */     {
/* 29 */       productTypeMap.put(Integer.valueOf(type.getCode()), type);
/*    */     }
/*    */   }
/*    */   
/*    */   ProductType(int code, String messageKey) {
/* 34 */     this.code = code;
/* 35 */     this.messageKey = messageKey;
/*    */   }
/*    */   
/*    */   public int getCode() {
/* 39 */     return this.code;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 43 */     return this.messageKey;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.authentication.ProductType getProductType(int code) {
/* 47 */     return productTypeMap.get(Integer.valueOf(code));
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\ProductType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */