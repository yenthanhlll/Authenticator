/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*    */ 
/*    */ 
/*    */ public class TokenAuditType
/*    */   implements IAuditType
/*    */ {
/*    */   private static final int TOKEN = 1024;
/*    */   public static final int CREATED = 1025;
/*    */   public static final int NOT_ENOUGH = 1026;
/*    */   public static final int NONE = 1027;
/*    */   private static final String KEY_CREATED = "AuditAlarmTypes.TOKEN.CREATED";
/*    */   private static final String KEY_NOT_ENOUGH = "AuditAlarmTypes.TOKEN.NOT_ENOUGH";
/*    */   private static final String KEY_NONE = "AuditAlarmTypes.TOKEN.NONE";
/*    */   private static final String KEY_DESC_CREATED = "AuditAlarmTypes.TOKEN.CREATED.desc";
/*    */   private static final String KEY_DESC_NOT_ENOUGH = "AuditAlarmTypes.TOKEN.NOT_ENOUGH.desc";
/*    */   private static final String KEY_DESC_NONE = "AuditAlarmTypes.TOKEN.NONE.desc";
/*    */   
/*    */   public I18nMessage getDescriptionMessage(int actionType, Object... args) {
/* 23 */     switch (actionType) {
/*    */       
/*    */       case 1025:
/* 26 */         return new I18nMessage("AuditAlarmTypes.TOKEN.CREATED.desc", args);
/*    */       
/*    */       case 1026:
/* 29 */         return new I18nMessage("AuditAlarmTypes.TOKEN.NOT_ENOUGH.desc", args);
/*    */     } 
/*    */     
/* 32 */     return new I18nMessage("AuditAlarmTypes.TOKEN.NONE.desc", args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionMsgKey(int actionType) {
/* 39 */     switch (actionType) {
/*    */       case 1025:
/* 41 */         return "AuditAlarmTypes.TOKEN.CREATED";
/*    */       
/*    */       case 1026:
/* 44 */         return "AuditAlarmTypes.TOKEN.NOT_ENOUGH";
/*    */     } 
/*    */     
/* 47 */     return "AuditAlarmTypes.TOKEN.NONE";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AuditAlarmTypes getAuditAlarmTypes() {
/* 53 */     return AuditAlarmTypes.TOKEN;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\TokenAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */