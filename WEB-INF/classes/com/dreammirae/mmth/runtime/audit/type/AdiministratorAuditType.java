/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*    */ 
/*    */ 
/*    */ public class AdiministratorAuditType
/*    */   implements IAuditType
/*    */ {
/*    */   private static final int ADMIN = 512;
/*    */   public static final int LOGIN = 513;
/*    */   public static final int CREATED = 514;
/*    */   public static final int UPDATED = 515;
/*    */   public static final int DELETED = 516;
/*    */   public static final int FORCE_PWD_CHANGED = 517;
/*    */   public static final int LOGIN_FAILURE = 518;
/*    */   private static final String KEY_LOGIN = "AuditAlarmTypes.ADMIN.LOGIN";
/*    */   private static final String KEY_CREATED = "AuditAlarmTypes.ADMIN.CREATED";
/*    */   private static final String KEY_UPDATED = "AuditAlarmTypes.ADMIN.UPDATED";
/*    */   private static final String KEY_DELETED = "AuditAlarmTypes.ADMIN.DELETED";
/*    */   private static final String KEY_FORCE_PWD_CHANGED = "AuditAlarmTypes.ADMIN.FORCE_PWD_CHANGED";
/*    */   private static final String KEY_LOGIN_FAILURE = "AuditAlarmTypes.ADMIN.LOGIN_FAILURE";
/*    */   private static final String KEY_DESC_LOGIN = "AuditAlarmTypes.ADMIN.LOGIN.desc";
/*    */   private static final String KEY_DESC_CREATED = "AuditAlarmTypes.ADMIN.CREATED.desc";
/*    */   private static final String KEY_DESC_UPDATED = "AuditAlarmTypes.ADMIN.UPDATED.desc";
/*    */   private static final String KEY_DESC_DELETED = "AuditAlarmTypes.ADMIN.DELETED.desc";
/*    */   private static final String KEY_DESC_FORCE_PWD_CHANGED = "AuditAlarmTypes.ADMIN.FORCE_PWD_CHANGED.desc";
/*    */   private static final String KEY_DESC_LOGIN_FAILURE = "AuditAlarmTypes.ADMIN.LOGIN_FAILURE.desc";
/*    */   
/*    */   public I18nMessage getDescriptionMessage(int actionType, Object... args) {
/* 32 */     switch (actionType) {
/*    */       case 513:
/* 34 */         return new I18nMessage("AuditAlarmTypes.ADMIN.LOGIN.desc", args);
/*    */       
/*    */       case 514:
/* 37 */         return new I18nMessage("AuditAlarmTypes.ADMIN.CREATED.desc", args);
/*    */       
/*    */       case 515:
/* 40 */         return new I18nMessage("AuditAlarmTypes.ADMIN.UPDATED.desc", args);
/*    */       
/*    */       case 516:
/* 43 */         return new I18nMessage("AuditAlarmTypes.ADMIN.DELETED.desc", args);
/*    */       
/*    */       case 517:
/* 46 */         return new I18nMessage("AuditAlarmTypes.ADMIN.FORCE_PWD_CHANGED.desc", args);
/*    */     } 
/*    */     
/* 49 */     return new I18nMessage("AuditAlarmTypes.ADMIN.LOGIN_FAILURE.desc", args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionMsgKey(int actionType) {
/* 56 */     switch (actionType) {
/*    */       case 513:
/* 58 */         return "AuditAlarmTypes.ADMIN.LOGIN";
/*    */       
/*    */       case 514:
/* 61 */         return "AuditAlarmTypes.ADMIN.CREATED";
/*    */       
/*    */       case 515:
/* 64 */         return "AuditAlarmTypes.ADMIN.UPDATED";
/*    */       
/*    */       case 516:
/* 67 */         return "AuditAlarmTypes.ADMIN.DELETED";
/*    */       
/*    */       case 517:
/* 70 */         return "AuditAlarmTypes.ADMIN.FORCE_PWD_CHANGED";
/*    */     } 
/*    */     
/* 73 */     return "AuditAlarmTypes.ADMIN.LOGIN_FAILURE";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AuditAlarmTypes getAuditAlarmTypes() {
/* 79 */     return AuditAlarmTypes.ADMIN;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\AdiministratorAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */