/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.service.type;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public enum OpRequstTypes
/*    */ {
/* 10 */   UNKNOWN(0, "OpRequstTypes.UNKNOWN"),
/* 11 */   FIDO_UAF_REQ(1, "OpRequstTypes.FIDO_UAF_REQ"),
/* 12 */   FIDO_UAF_RESP(2, "OpRequstTypes.FIDO_UAF_RESP"),
/* 13 */   HTTP_API(3, "OpRequstTypes.HTTP_API");
/*    */   
/*    */   private final int code;
/*    */   private final String messageKey;
/*    */   
/*    */   OpRequstTypes(int code, String messageKey) {
/* 19 */     this.code = code;
/* 20 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCode() {
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
/*    */   
/*    */   public static com.dreammirae.mmth.runtime.service.type.OpRequstTypes getOpRequstTypes(int code) {
/* 40 */     for (com.dreammirae.mmth.runtime.service.type.OpRequstTypes type : values()) {
/* 41 */       if (type.code == code) {
/* 42 */         return type;
/*    */       }
/*    */     } 
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair(com.dreammirae.mmth.runtime.service.type.OpRequstTypes... excludes) {
/* 50 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*    */     
/* 52 */     for (com.dreammirae.mmth.runtime.service.type.OpRequstTypes type : values()) {
/*    */       
/* 54 */       com.dreammirae.mmth.runtime.service.type.OpRequstTypes[] arrayOfOpRequstTypes = excludes; int i = arrayOfOpRequstTypes.length; byte b = 0; while (true) { if (b < i) { com.dreammirae.mmth.runtime.service.type.OpRequstTypes exclude = arrayOfOpRequstTypes[b];
/*    */           
/* 56 */           if (type.equals(exclude))
/*    */             break;  b++;
/*    */           continue; }
/*    */         
/* 60 */         list.add(new KeyValuePair(type.name(), type.getMessageKey())); break; }
/*    */     
/*    */     } 
/* 63 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.runtime.service.type.OpRequstTypes getOpRequstTypesByName(String name) {
/* 67 */     for (com.dreammirae.mmth.runtime.service.type.OpRequstTypes type : values()) {
/* 68 */       if (type.name().equalsIgnoreCase(name)) {
/* 69 */         return type;
/*    */       }
/*    */     } 
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\service\type\OpRequstTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */