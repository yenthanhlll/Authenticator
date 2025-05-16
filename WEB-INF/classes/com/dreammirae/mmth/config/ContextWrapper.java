/*    */ package WEB-INF.classes.com.dreammirae.mmth.config;
/*    */ 
/*    */ import com.dreammirae.mmth.db.DatabaseAccess;
/*    */ import com.dreammirae.mmth.fido.uaf.ChannelBinding;
/*    */ import com.dreammirae.mmth.runtime.audit.AuditAlarmManager;
/*    */ import com.dreammirae.mmth.runtime.publish.SyncCachePublisher;
/*    */ import com.dreammirae.mmth.runtime.service.ExternalServiceManager;
/*    */ import com.dreammirae.mmth.runtime.service.ServiceLogsManager;
/*    */ import freemarker.template.Configuration;
/*    */ import javax.servlet.ServletContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ContextWrapper
/*    */ {
/*    */   private final ServletContext sc;
/*    */   
/*    */   public ContextWrapper(ServletContext sc) {
/* 30 */     this.sc = sc;
/*    */   }
/*    */   
/*    */   public ServletContext getServletContext() {
/* 34 */     return this.sc;
/*    */   }
/*    */   
/*    */   public DatabaseAccess getDatabaseAccess() {
/* 38 */     return (DatabaseAccess)this.sc.getAttribute("DATABASE_ACCESS");
/*    */   }
/*    */   
/*    */   public Configuration getFreemarkerConfig() {
/* 42 */     return (Configuration)this.sc.getAttribute("FREEMARKER_CONFIG");
/*    */   }
/*    */   
/*    */   public ChannelBinding[] getChannelBinding() {
/* 46 */     return (ChannelBinding[])this.sc.getAttribute("FIDO_CHANNEL_BINDING");
/*    */   }
/*    */   
/*    */   public AuditAlarmManager getAuditAlarmManager() {
/* 50 */     return (AuditAlarmManager)this.sc.getAttribute("AUDIT_ALARM_MANAGER");
/*    */   }
/*    */   
/*    */   public ServiceLogsManager getServiceLogsManager() {
/* 54 */     return (ServiceLogsManager)this.sc.getAttribute("SERVICE_LOG_MANAGER");
/*    */   }
/*    */   
/*    */   public SyncCachePublisher getSyncCachePublisher() {
/* 58 */     return (SyncCachePublisher)this.sc.getAttribute("SYNC_CACHE_PUBLISHER");
/*    */   }
/*    */   
/*    */   public ExternalServiceManager getExternalServiceManager() {
/* 62 */     return (ExternalServiceManager)this.sc.getAttribute("EXTERNAL_SEVEICE_MANAGER");
/*    */   }
/*    */   
/*    */   public Boolean getTokenThresholdScheduled() {
/* 66 */     Object obj = this.sc.getAttribute("TOKEN_THRESHOLD_SCHEDULED_BOOL");
/*    */     
/* 68 */     if (obj instanceof Boolean) {
/* 69 */       return (Boolean)obj;
/*    */     }
/* 71 */     return Boolean.FALSE;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\config\ContextWrapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */