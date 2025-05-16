/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*    */ 
/*    */ 
/*    */ public class FidoMetadataAuditType
/*    */   implements IAuditType
/*    */ {
/*    */   private static final int METADATA = 1280;
/*    */   public static final int CREATED = 1281;
/*    */   public static final int UPDATED = 1282;
/*    */   public static final int DELETED = 1283;
/*    */   private static final String KEY_CREATED = "AuditAlarmTypes.METADATA.CREATED";
/*    */   private static final String KEY_UPDATED = "AuditAlarmTypes.METADATA.UPDATED";
/*    */   private static final String KEY_DELETED = "AuditAlarmTypes.METADATA.DELETED";
/*    */   private static final String KEY_DESC_CREATED = "AuditAlarmTypes.METADATA.CREATED.desc";
/*    */   private static final String KEY_DESC_UPDATED = "AuditAlarmTypes.METADATA.UPDATED.desc";
/*    */   private static final String KEY_DESC_DELETED = "AuditAlarmTypes.METADATA.DELETED.desc";
/*    */   
/*    */   public I18nMessage getDescriptionMessage(int actionType, Object... args) {
/* 23 */     switch (actionType) {
/*    */       
/*    */       case 1281:
/* 26 */         return new I18nMessage("AuditAlarmTypes.METADATA.CREATED.desc", args);
/*    */       
/*    */       case 1282:
/* 29 */         return new I18nMessage("AuditAlarmTypes.METADATA.UPDATED.desc", args);
/*    */     } 
/*    */     
/* 32 */     return new I18nMessage("AuditAlarmTypes.METADATA.DELETED.desc", args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionMsgKey(int actionType) {
/* 39 */     switch (actionType) {
/*    */       case 1281:
/* 41 */         return "AuditAlarmTypes.METADATA.CREATED";
/*    */       
/*    */       case 1282:
/* 44 */         return "AuditAlarmTypes.METADATA.UPDATED";
/*    */     } 
/*    */     
/* 47 */     return "AuditAlarmTypes.METADATA.DELETED";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AuditAlarmTypes getAuditAlarmTypes() {
/* 53 */     return AuditAlarmTypes.METADATA;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\FidoMetadataAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */