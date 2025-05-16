/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*    */ 
/*    */ 
/*    */ public class SystemSettingsAuditType
/*    */   implements IAuditType
/*    */ {
/*    */   private static final int SYSTEM_SETTINGS = 2048;
/*    */   public static final int SAVE_VALUE = 2049;
/*    */   public static final int SAVE_PRIODS = 2050;
/*    */   public static final int SAVE_POLICY = 2051;
/*    */   public static final int SAVE_SERVICE_POLICY = 2052;
/*    */   public static final int DATA_PURGE_SERVICE_LOG = 2053;
/*    */   private static final String KEY_SAVE_VALUE = "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_VALUE";
/*    */   private static final String KEY_SAVE_PRIODS = "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_PRIODS";
/*    */   private static final String KEY_SAVE_POLICY = "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_POLICY";
/*    */   private static final String KEY_SAVE_SERVICE_POLICY = "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_SERVICE_POLICY";
/*    */   private static final String KEY_DATA_PURGE_SERVICE_LOG = "AuditAlarmTypes.SYSTEM_SETTINGS.DATA_PURGE_SERVICE_LOG";
/*    */   private static final String KEY_DESC_SAVE_VALUE = "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_VALUE.desc";
/*    */   private static final String KEY_DESC_SAVE_PRIODS = "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_PRIODS.desc";
/*    */   private static final String KEY_DESC_SAVE_POLICY = "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_POLICY.desc";
/*    */   private static final String KEY_DESC_SAVE_SERVICE_POLICY = "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_SERVICE_POLICY.desc";
/*    */   private static final String KEY_DESC_DATA_PURGE_SERVICE_LOG = "AuditAlarmTypes.SYSTEM_SETTINGS.DATA_PURGE_SERVICE_LOG.desc";
/*    */   
/*    */   public I18nMessage getDescriptionMessage(int actionType, Object... args) {
/* 29 */     switch (actionType) {
/*    */       
/*    */       case 2049:
/* 32 */         return new I18nMessage("AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_VALUE.desc", args);
/*    */       
/*    */       case 2050:
/* 35 */         return new I18nMessage("AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_PRIODS.desc", args);
/*    */       
/*    */       case 2051:
/* 38 */         return new I18nMessage("AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_POLICY.desc", args);
/*    */       
/*    */       case 2052:
/* 41 */         return new I18nMessage("AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_SERVICE_POLICY.desc", args);
/*    */     } 
/* 43 */     return new I18nMessage("AuditAlarmTypes.SYSTEM_SETTINGS.DATA_PURGE_SERVICE_LOG.desc", args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionMsgKey(int actionType) {
/* 50 */     switch (actionType) {
/*    */       case 2049:
/* 52 */         return "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_VALUE";
/*    */       
/*    */       case 2050:
/* 55 */         return "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_PRIODS";
/*    */       
/*    */       case 2051:
/* 58 */         return "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_POLICY";
/*    */       
/*    */       case 2052:
/* 61 */         return "AuditAlarmTypes.SYSTEM_SETTINGS.SAVE_SERVICE_POLICY";
/*    */     } 
/*    */     
/* 64 */     return "AuditAlarmTypes.SYSTEM_SETTINGS.DATA_PURGE_SERVICE_LOG";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AuditAlarmTypes getAuditAlarmTypes() {
/* 70 */     return AuditAlarmTypes.SYSTEM_SETTING;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\SystemSettingsAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */