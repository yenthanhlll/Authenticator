/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*    */ 
/*    */ 
/*    */ public class AppAgentAuditType
/*    */   implements IAuditType
/*    */ {
/*    */   private static final int APP_AGENT = 1792;
/*    */   public static final int CREATED = 1793;
/*    */   public static final int UPDATED = 1794;
/*    */   public static final int DELETED = 1795;
/*    */   private static final String KEY_CREATED = "AuditAlarmTypes.APP_AGENT.CREATED";
/*    */   private static final String KEY_UPDATED = "AuditAlarmTypes.APP_AGENT.UPDATED";
/*    */   private static final String KEY_DELETED = "AuditAlarmTypes.APP_AGENT.DELETED";
/*    */   private static final String KEY_DESC_CREATED = "AuditAlarmTypes.APP_AGENT.CREATED.desc";
/*    */   private static final String KEY_DESC_UPDATED = "AuditAlarmTypes.APP_AGENT.UPDATED.desc";
/*    */   private static final String KEY_DESC_DELETED = "AuditAlarmTypes.APP_AGENT.DELETED.desc";
/*    */   
/*    */   public I18nMessage getDescriptionMessage(int actionType, Object... args) {
/* 23 */     switch (actionType) {
/*    */       
/*    */       case 1793:
/* 26 */         return new I18nMessage("AuditAlarmTypes.APP_AGENT.CREATED.desc", args);
/*    */       
/*    */       case 1794:
/* 29 */         return new I18nMessage("AuditAlarmTypes.APP_AGENT.UPDATED.desc", args);
/*    */     } 
/*    */     
/* 32 */     return new I18nMessage("AuditAlarmTypes.APP_AGENT.DELETED.desc", args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionMsgKey(int actionType) {
/* 39 */     switch (actionType) {
/*    */       case 1793:
/* 41 */         return "AuditAlarmTypes.APP_AGENT.CREATED";
/*    */       
/*    */       case 1794:
/* 44 */         return "AuditAlarmTypes.APP_AGENT.UPDATED";
/*    */       
/*    */       case 1795:
/* 47 */         return "AuditAlarmTypes.APP_AGENT.DELETED";
/*    */     } 
/*    */     
/* 50 */     return "AuditAlarmTypes.APP_AGENT.DELETED";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AuditAlarmTypes getAuditAlarmTypes() {
/* 56 */     return AuditAlarmTypes.APP_AGENT;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\AppAgentAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */