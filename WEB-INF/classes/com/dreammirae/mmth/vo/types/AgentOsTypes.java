/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public enum AgentOsTypes
/*    */ {
/* 10 */   ANDROID("A", "AgentOsTypes.ANDROID"),
/* 11 */   IOS("I", "AgentOsTypes.IOS"),
/* 12 */   ANANYMOUS("X", "AgentOsTypes.ANANYMOUS");
/*    */   
/*    */   private final String code;
/*    */   private final String messageKey;
/*    */   
/*    */   AgentOsTypes(String code, String messageKey) {
/* 18 */     this.code = code;
/* 19 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 26 */     return this.code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessageKey() {
/* 33 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.AgentOsTypes getAgentOsType(String code) {
/* 38 */     for (com.dreammirae.mmth.vo.types.AgentOsTypes type : values()) {
/* 39 */       if (type.code.equals(code)) {
/* 40 */         return type;
/*    */       }
/*    */     } 
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 49 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*    */     
/* 51 */     for (com.dreammirae.mmth.vo.types.AgentOsTypes type : values()) {
/* 52 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 55 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.AgentOsTypes getAgentOsTypeByName(String name) {
/* 59 */     for (com.dreammirae.mmth.vo.types.AgentOsTypes type : values()) {
/* 60 */       if (type.name().equalsIgnoreCase(name)) {
/* 61 */         return type;
/*    */       }
/*    */     } 
/* 64 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\AgentOsTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */