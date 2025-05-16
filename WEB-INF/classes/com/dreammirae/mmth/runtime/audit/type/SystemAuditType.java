/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*    */ 
/*    */ 
/*    */ public class SystemAuditType
/*    */   implements IAuditType
/*    */ {
/*    */   private static final int SYSTEM = 256;
/*    */   public static final int STARTUP = 257;
/*    */   public static final int SHUTDOWN = 258;
/*    */   public static final int DB_SCHEME_CREATED = 259;
/*    */   public static final int DB_SCHEME_UPGRADE = 260;
/*    */   public static final int DB_ERROR = 261;
/*    */   private static final String KEY_STARTUP = "AuditAlarmTypes.SYSTEM.STARTUP";
/*    */   private static final String KEY_SHUTDOWN = "AuditAlarmTypes.SYSTEM.SHUTDOWN";
/*    */   private static final String KEY_DB_SCHEME_CREATED = "AuditAlarmTypes.SYSTEM.DB_SCHEME_CREATED";
/*    */   private static final String KEY_DB_SCHEME_UPGRADE = "AuditAlarmTypes.SYSTEM.DB_SCHEME_UPGRADE";
/*    */   private static final String KEY_DB_ERROR = "AuditAlarmTypes.SYSTEM.DB_ERROR";
/*    */   private static final String KEY_DESC_STARTUP = "AuditAlarmTypes.SYSTEM.STARTUP.desc";
/*    */   private static final String KEY_DESC_SHUTDOWN = "AuditAlarmTypes.SYSTEM.SHUTDOWN.desc";
/*    */   private static final String KEY_DESC_DB_SCHEME_CREATED = "AuditAlarmTypes.SYSTEM.DB_SCHEME_CREATED.desc";
/*    */   private static final String KEY_DESC_DB_SCHEME_UPGRADE = "AuditAlarmTypes.SYSTEM.DB_SCHEME_UPGRADE.desc";
/*    */   private static final String KEY_DESC_DB_ERROR = "AuditAlarmTypes.SYSTEM.DB_ERROR.desc";
/*    */   
/*    */   public I18nMessage getDescriptionMessage(int actionType, Object... args) {
/* 29 */     switch (actionType) {
/*    */       case 257:
/* 31 */         return new I18nMessage("AuditAlarmTypes.SYSTEM.STARTUP.desc");
/*    */       
/*    */       case 258:
/* 34 */         return new I18nMessage("AuditAlarmTypes.SYSTEM.SHUTDOWN.desc");
/*    */       
/*    */       case 259:
/* 37 */         return new I18nMessage("AuditAlarmTypes.SYSTEM.DB_SCHEME_CREATED.desc", args);
/*    */       
/*    */       case 260:
/* 40 */         return new I18nMessage("AuditAlarmTypes.SYSTEM.DB_SCHEME_UPGRADE.desc", args);
/*    */     } 
/*    */     
/* 43 */     return new I18nMessage("AuditAlarmTypes.SYSTEM.DB_ERROR.desc", args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionMsgKey(int actionType) {
/* 50 */     switch (actionType) {
/*    */       case 257:
/* 52 */         return "AuditAlarmTypes.SYSTEM.STARTUP";
/*    */       
/*    */       case 258:
/* 55 */         return "AuditAlarmTypes.SYSTEM.SHUTDOWN";
/*    */       
/*    */       case 259:
/* 58 */         return "AuditAlarmTypes.SYSTEM.DB_SCHEME_CREATED";
/*    */       
/*    */       case 260:
/* 61 */         return "AuditAlarmTypes.SYSTEM.DB_SCHEME_UPGRADE";
/*    */     } 
/*    */     
/* 64 */     return "AuditAlarmTypes.SYSTEM.DB_ERROR";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AuditAlarmTypes getAuditAlarmTypes() {
/* 70 */     return AuditAlarmTypes.SYSTEM;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\SystemAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */