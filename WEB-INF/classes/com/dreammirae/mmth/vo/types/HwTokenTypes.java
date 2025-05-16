/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public enum HwTokenTypes
/*    */ {
/* 10 */   MIRAE_OTP("01", "HwTokenTypes.MIRAE_OTP"),
/*    */   
/* 12 */   ADVANCED_OTP("03", "HwTokenTypes.ADVANCED_OTP"),
/* 13 */   CARD("04", "HwTokenTypes.CARD"),
/* 14 */   TOKEN("05", "HwTokenTypes.TOKEN");
/*    */   
/*    */   private final String code;
/*    */   
/*    */   private final String messageKey;
/*    */ 
/*    */   
/*    */   HwTokenTypes(String code, String messageKey) {
/* 22 */     this.code = code;
/* 23 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 28 */     return this.code;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 32 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.HwTokenTypes getHwTokenTypes(String code) {
/* 37 */     for (com.dreammirae.mmth.vo.types.HwTokenTypes type : values()) {
/* 38 */       if (type.code.equals(code)) {
/* 39 */         return type;
/*    */       }
/*    */     } 
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 47 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*    */     
/* 49 */     for (com.dreammirae.mmth.vo.types.HwTokenTypes type : values()) {
/* 50 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 53 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.HwTokenTypes getHwTokenTypesByName(String name) {
/* 57 */     for (com.dreammirae.mmth.vo.types.HwTokenTypes type : values()) {
/* 58 */       if (type.name().equalsIgnoreCase(name)) {
/* 59 */         return type;
/*    */       }
/*    */     } 
/* 62 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\HwTokenTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */