/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*    */ 
/*    */ 
/*    */ public class PublisherAuditType
/*    */   implements IAuditType
/*    */ {
/*    */   private static final int PUBLISHER = 2304;
/*    */   public static final int SEND = 2305;
/*    */   public static final int RECEIVED = 2306;
/*    */   public static final int FAIL = 2307;
/*    */   private static final String KEY_SEND = "AuditAlarmTypes.PUBLISHER.SEND";
/*    */   private static final String KEY_RECEIVED = "AuditAlarmTypes.PUBLISHER.RECEIVED";
/*    */   private static final String KEY_FAIL = "AuditAlarmTypes.PUBLISHER.FAIL";
/*    */   private static final String KEY_DESC_SEND = "AuditAlarmTypes.PUBLISHER.SEND.desc";
/*    */   private static final String KEY_DESC_KEY_RECEIVED = "AuditAlarmTypes.PUBLISHER.RECEIVED.desc";
/*    */   private static final String KEY_DESC_KEY_FAIL = "AuditAlarmTypes.PUBLISHER.FAIL.desc";
/*    */   
/*    */   public I18nMessage getDescriptionMessage(int actionType, Object... args) {
/* 23 */     switch (actionType) {
/*    */       
/*    */       case 2305:
/* 26 */         return new I18nMessage("AuditAlarmTypes.PUBLISHER.SEND.desc", args);
/*    */       
/*    */       case 2306:
/* 29 */         return new I18nMessage("AuditAlarmTypes.PUBLISHER.RECEIVED.desc", args);
/*    */     } 
/*    */     
/* 32 */     return new I18nMessage("AuditAlarmTypes.PUBLISHER.FAIL.desc", args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActionMsgKey(int actionType) {
/* 39 */     switch (actionType) {
/*    */       case 2305:
/* 41 */         return "AuditAlarmTypes.PUBLISHER.SEND";
/*    */       
/*    */       case 2306:
/* 44 */         return "AuditAlarmTypes.PUBLISHER.RECEIVED";
/*    */     } 
/*    */     
/* 47 */     return "AuditAlarmTypes.PUBLISHER.FAIL";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AuditAlarmTypes getAuditAlarmTypes() {
/* 53 */     return AuditAlarmTypes.PUBLISHER;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\PublisherAuditType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */