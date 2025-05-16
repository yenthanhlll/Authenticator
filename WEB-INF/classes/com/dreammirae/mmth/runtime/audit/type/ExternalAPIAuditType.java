/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*    */ 
/*    */ 
/*    */ public class ExternalAPIAuditType
/*    */   implements IAuditType
/*    */ {
/*    */   private static final int EXTERNAL_API = 2560;
/*    */   public static final int ADD_USER = 2561;
/*    */   public static final int REMOVE_USER = 2562;
/*    */   public static final int FORCE_DEREG = 2563;
/*    */   public static final int ISSUE_CODE = 2564;
/*    */   public static final int NOTIFY_REG = 2565;
/*    */   public static final int NOTIFY_DEREG = 2566;
/*    */   public static final int NOTIFY_FAILED = 2567;
/*    */   public static final int PUSH_FAILED = 2568;
/*    */   private static final String KEY_ADD_USER = "AuditAlarmTypes.EXTERNAL_API.ADD_USER";
/*    */   private static final String KEY_REMOVE_USER = "AuditAlarmTypes.EXTERNAL_API.REMOVE_USER";
/*    */   private static final String KEY_FORCE_DEREG = "AuditAlarmTypes.EXTERNAL_API.FORCE_DEREG";
/*    */   private static final String KEY_ISSUE_CODE = "AuditAlarmTypes.EXTERNAL_API.ISSUE_CODE";
/*    */   private static final String KEY_NOTIFY_REG = "AuditAlarmTypes.EXTERNAL_API.NOTIFY_REG";
/*    */   private static final String KEY_NOTIFY_DEREG = "AuditAlarmTypes.EXTERNAL_API.NOTIFY_DEREG";
/*    */   private static final String KEY_NOTIFY_FAILED = "AuditAlarmTypes.EXTERNAL_API.NOTIFY_FAILED";
/*    */   private static final String KEY_PUSH_FAILED = "AuditAlarmTypes.EXTERNAL_API.PUSH_FAILED";
/*    */   private static final String KEY_DESC_ADD_USER = "AuditAlarmTypes.EXTERNAL_API.ADD_USER.desc";
/*    */   private static final String KEY_DESC_REMOVE_USER = "AuditAlarmTypes.EXTERNAL_API.REMOVE_USER.desc";
/*    */   private static final String KEY_DESC_FORCE_DEREG = "AuditAlarmTypes.EXTERNAL_API.FORCE_DEREG.desc";
/*    */   private static final String KEY_DESC_ISSUE_CODE = "AuditAlarmTypes.EXTERNAL_API.ISSUE_CODE.desc";
/*    */   private static final String KEY_DESC_NOTIFY_REG = "AuditAlarmTypes.EXTERNAL_API.NOTIFY_REG.desc";
/*    */   private static final String KEY_DESC_NOTIFY_DEREG = "AuditAlarmTypes.EXTERNAL_API.NOTIFY_DEREG.desc";
/*    */   private static final String KEY_DESC_NOTIFY_FAILED = "AuditAlarmTypes.EXTERNAL_API.NOTIFY_FAILED.desc";
/*    */   private static final String KEY_DESC_PUSH_FAILED = "AuditAlarmTypes.EXTERNAL_API.PUSH_FAILED.desc";
/*    */   
/*    */   public I18nMessage getDescriptionMessage(int actionType, Object... args) {
/* 38 */     switch (actionType) {
/*    */       
/*    */       case 2561:
/* 41 */         return new I18nMessage("AuditAlarmTypes.EXTERNAL_API.ADD_USER.desc", args);
/*    */       
/*    */       case 2562:
/* 44 */         return new I18nMessage("AuditAlarmTypes.EXTERNAL_API.REMOVE_USER.desc", args);
/*    */       
/*    */       case 2563:
/* 47 */         return new I18nMessage("AuditAlarmTypes.EXTERNAL_API.FORCE_DEREG.desc", args);
/*    */       
/*    */       case 2564:
/* 50 */         return new I18nMessage("AuditAlarmTypes.EXTERNAL_API.ISSUE_CODE.desc", args);
/*    */       
/*    */       case 2565:
/* 53 */         return new I18nMessage("AuditAlarmTypes.EXTERNAL_API.NOTIFY_REG.desc", args);
/*    */       
/*    */       case 2566:
/* 56 */         return new I18nMessage("AuditAlarmTypes.EXTERNAL_API.NOTIFY_DEREG.desc", args);
/*    */       
/*    */       case 2567:
/* 59 */         return new I18nMessage("AuditAlarmTypes.EXTERNAL_API.NOTIFY_FAILED.desc", args);
/*    */     } 
/*    */     
/* 62 */     return new I18nMessage("AuditAlarmTypes.EXTERNAL_API.PUSH_FAILED.desc", args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionMsgKey(int actionType) {
/* 69 */     switch (actionType) {
/*    */       case 2561:
/* 71 */         return "AuditAlarmTypes.EXTERNAL_API.ADD_USER";
/*    */       
/*    */       case 2562:
/* 74 */         return "AuditAlarmTypes.EXTERNAL_API.REMOVE_USER";
/*    */       
/*    */       case 2563:
/* 77 */         return "AuditAlarmTypes.EXTERNAL_API.FORCE_DEREG";
/*    */       
/*    */       case 2564:
/* 80 */         return "AuditAlarmTypes.EXTERNAL_API.ISSUE_CODE";
/*    */       
/*    */       case 2565:
/* 83 */         return "AuditAlarmTypes.EXTERNAL_API.NOTIFY_REG";
/*    */       
/*    */       case 2566:
/* 86 */         return "AuditAlarmTypes.EXTERNAL_API.NOTIFY_DEREG";
/*    */       
/*    */       case 2567:
/* 89 */         return "AuditAlarmTypes.EXTERNAL_API.NOTIFY_FAILED";
/*    */     } 
/*    */     
/* 92 */     return "AuditAlarmTypes.EXTERNAL_API.PUSH_FAILED";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AuditAlarmTypes getAuditAlarmTypes() {
/* 98 */     return AuditAlarmTypes.EXTERNAL_API;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\ExternalAPIAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */