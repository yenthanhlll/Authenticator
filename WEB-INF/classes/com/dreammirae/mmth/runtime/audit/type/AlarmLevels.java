/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public enum AlarmLevels
/*    */ {
/* 10 */   NONE(0, "AlarmLevels.NONE"),
/* 11 */   INFORMATION(1, "AlarmLevels.INFORMATION"),
/* 12 */   URGENT(2, "AlarmLevels.URGENT"),
/* 13 */   CRITICAL(3, "AlarmLevels.CRITICAL"),
/* 14 */   LIFE_SAFETY(4, "AlarmLevels.LIFE_SAFETY");
/*    */   
/*    */   private final int code;
/*    */   private final String messageKey;
/*    */   
/*    */   AlarmLevels(int code, String messageKey) {
/* 20 */     this.code = code;
/* 21 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCode() {
/* 28 */     return this.code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessageKey() {
/* 35 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.runtime.audit.type.AlarmLevels getAuditAlarmLevel(int code) {
/* 40 */     for (com.dreammirae.mmth.runtime.audit.type.AlarmLevels type : values()) {
/* 41 */       if (type.code == code) {
/* 42 */         return type;
/*    */       }
/*    */     } 
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 50 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*    */     
/* 52 */     for (com.dreammirae.mmth.runtime.audit.type.AlarmLevels type : values()) {
/* 53 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*    */     }
/*    */     
/* 56 */     return list;
/*    */   }
/*    */   
/*    */   public static com.dreammirae.mmth.runtime.audit.type.AlarmLevels getAuditAlarmLevelByName(String name) {
/* 60 */     for (com.dreammirae.mmth.runtime.audit.type.AlarmLevels type : values()) {
/* 61 */       if (type.name().equalsIgnoreCase(name)) {
/* 62 */         return type;
/*    */       }
/*    */     } 
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\AlarmLevels.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */