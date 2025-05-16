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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum MethodTypes
/*    */ {
/* 17 */   MOBILE_OTP(1, "MethodTypes.MOBILE_OTP"),
/* 18 */   TIME_BASED_HW_OTP(2, "MethodTypes.TIME_BASED_HW_OTP"),
/* 19 */   ADVANCED_HW_OTP(4, "MethodTypes.ADVANCED_HW_OTP"),
/* 20 */   OAUTH(8, "MethodTypes.OAUTH"),
/* 21 */   MATRIX(22, "MethodTypes.MATRIX");
/*    */   
/*    */   private final int code;
/*    */   private final String messageKey;
/*    */   
/*    */   MethodTypes(int code, String messageKey) {
/* 27 */     this.code = code;
/* 28 */     this.messageKey = messageKey;
/*    */   }
/*    */   
/*    */   public int getCode() {
/* 32 */     return this.code;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 36 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.MethodTypes getMethodType(int code) {
/* 41 */     for (com.dreammirae.mmth.vo.types.MethodTypes type : values()) {
/* 42 */       if (type.code == code) {
/* 43 */         return type;
/*    */       }
/*    */     } 
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 51 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*    */     
/* 53 */     for (com.dreammirae.mmth.vo.types.MethodTypes type : values()) {
/* 54 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 57 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.MethodTypes getMethodTypeByName(String name) {
/* 61 */     for (com.dreammirae.mmth.vo.types.MethodTypes type : values()) {
/* 62 */       if (type.name().equalsIgnoreCase(name)) {
/* 63 */         return type;
/*    */       }
/*    */     } 
/* 66 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\MethodTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */