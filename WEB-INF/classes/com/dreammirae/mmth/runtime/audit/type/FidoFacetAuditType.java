/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*    */ 
/*    */ 
/*    */ public class FidoFacetAuditType
/*    */   implements IAuditType
/*    */ {
/*    */   private static final int FACET = 1536;
/*    */   public static final int CREATED = 1537;
/*    */   public static final int UPDATED = 1538;
/*    */   public static final int DELETED = 1539;
/*    */   private static final String KEY_CREATED = "AuditAlarmTypes.FACET.CREATED";
/*    */   private static final String KEY_UPDATED = "AuditAlarmTypes.FACET.UPDATED";
/*    */   private static final String KEY_DELETED = "AuditAlarmTypes.FACET.DELETED";
/*    */   private static final String KEY_DESC_CREATED = "AuditAlarmTypes.FACET.CREATED.desc";
/*    */   private static final String KEY_DESC_UPDATED = "AuditAlarmTypes.FACET.UPDATED.desc";
/*    */   private static final String KEY_DESC_DELETED = "AuditAlarmTypes.FACET.DELETED.desc";
/*    */   
/*    */   public I18nMessage getDescriptionMessage(int actionType, Object... args) {
/* 23 */     switch (actionType) {
/*    */       
/*    */       case 1537:
/* 26 */         return new I18nMessage("AuditAlarmTypes.FACET.CREATED.desc", args);
/*    */       
/*    */       case 1538:
/* 29 */         return new I18nMessage("AuditAlarmTypes.FACET.UPDATED.desc", args);
/*    */     } 
/*    */     
/* 32 */     return new I18nMessage("AuditAlarmTypes.FACET.DELETED.desc", args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionMsgKey(int actionType) {
/* 39 */     switch (actionType) {
/*    */       case 1537:
/* 41 */         return "AuditAlarmTypes.FACET.CREATED";
/*    */       
/*    */       case 1538:
/* 44 */         return "AuditAlarmTypes.FACET.UPDATED";
/*    */       
/*    */       case 1539:
/* 47 */         return "AuditAlarmTypes.FACET.DELETED";
/*    */     } 
/*    */     
/* 50 */     return "AuditAlarmTypes.FACET.DELETED";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AuditAlarmTypes getAuditAlarmTypes() {
/* 56 */     return AuditAlarmTypes.FACET;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\FidoFacetAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */