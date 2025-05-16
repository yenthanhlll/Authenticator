/*     */ package WEB-INF.classes.com.dreammirae.mmth.config;
/*     */ 
/*     */ import ch.qos.logback.classic.LoggerContext;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.config.ContextWrapper;
/*     */ import com.dreammirae.mmth.config.MMTHSessionListener;
/*     */ import com.dreammirae.mmth.db.DatabaseAccess;
/*     */ import com.dreammirae.mmth.db.DatabaseEnvPropUtils;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.exception.CannotStartupException;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.runtime.audit.AuditAlarmManager;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.runtime.publish.SyncCachePublisher;
/*     */ import com.dreammirae.mmth.runtime.publish.SyncCacheSenderRT;
/*     */ import com.dreammirae.mmth.runtime.publish.httpsender.HttpSenderRT;
/*     */ import com.dreammirae.mmth.runtime.service.ChannelBindingTimerTask;
/*     */ import com.dreammirae.mmth.runtime.service.ExternalServiceManager;
/*     */ import com.dreammirae.mmth.runtime.service.ServiceLogsManager;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import freemarker.cache.FileTemplateLoader;
/*     */ import freemarker.cache.MultiTemplateLoader;
/*     */ import freemarker.cache.TemplateLoader;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.DefaultObjectWrapper;
/*     */ import freemarker.template.ObjectWrapper;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EventListener;
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Timer;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletContextEvent;
/*     */ import javax.servlet.ServletContextListener;
/*     */ import javax.servlet.annotation.WebListener;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @WebListener
/*     */ public class MmthContextListener
/*     */   implements ServletContextListener
/*     */ {
/*  54 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.config.MmthContextListener.class);
/*     */ 
/*     */   
/*     */   private static final String SYS_ENV_BUNDLE_NAME = "conf/service_env";
/*     */ 
/*     */   
/*     */   public void contextInitialized(ServletContextEvent event) {
/*  61 */     LOG.info("====== mmth context is starting...");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     ServletContext sc = event.getServletContext();
/*  67 */     sc.addListener((EventListener)new MMTHSessionListener());
/*     */     
/*  69 */     Commons.ctx = new ContextWrapper(sc);
/*  70 */     Commons.timer = new Timer("MMTH-UAFAppContext-timer", true);
/*     */     
/*     */     try {
/*  73 */       DatabaseEnvPropUtils.readDbEnvProperties(sc);
/*     */ 
/*     */       
/*  76 */       databaseInitialize(sc);
/*     */       
/*  78 */       auditAlarmManagerInitialize(sc);
/*     */       
/*  80 */       initServiceEnviroment();
/*     */       
/*  82 */       initServiceLogManager(sc);
/*     */       
/*  84 */       initFidoSettings(sc);
/*     */       
/*  86 */       initSyncCachePublisher(sc);
/*     */       
/*  88 */       initExternalMsgSenderManager(sc);
/*     */       
/*  90 */       freemarkerInitialize(sc);
/*  91 */       constantsInitialize(sc);
/*     */ 
/*     */       
/*  94 */       AuditAlarmTypes.SYSTEM.raiseAlarm(null, 257, AlarmLevels.INFORMATION, new Object[0]);
/*     */     }
/*  96 */     catch (CannotStartupException e) {
/*  97 */       LOG.error("Cannot start up MMTH service normally... please check error message....", (Throwable)e);
/*     */       
/*     */       return;
/*     */     } 
/* 101 */     LOG.info("====== mmth context is started... Real context path = " + sc.getContextPath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void contextDestroyed(ServletContextEvent event) {
/* 108 */     LOG.info("====== mmth context is terminating...");
/*     */     
/* 110 */     AuditAlarmTypes.SYSTEM.raiseAlarm(null, 258, AlarmLevels.CRITICAL, new Object[0]);
/*     */     
/* 112 */     terminateExternalMsgSenderManager();
/*     */     
/* 114 */     terminateSyncCachePublisher();
/*     */     
/* 116 */     terminateFidoSettings();
/*     */     
/* 118 */     terminateServiceLogManager();
/*     */     
/* 120 */     auditAlarmManagerTerminate();
/*     */     
/* 122 */     databaseTerminate();
/*     */     
/* 124 */     if (LoggerFactory.getILoggerFactory() instanceof LoggerContext) {
/* 125 */       ((LoggerContext)LoggerFactory.getILoggerFactory()).stop();
/*     */     }
/*     */     
/*     */     try {
/* 129 */       Commons.timer.cancel();
/* 130 */     } catch (Exception ignore) {}
/*     */ 
/*     */ 
/*     */     
/* 134 */     LOG.info("====== mmth context is terminated...");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void databaseInitialize(ServletContext ctx) throws CannotStartupException {
/* 141 */     DatabaseAccess databaseAccess = DatabaseAccess.createDatabaseAccess(ctx);
/* 142 */     ctx.setAttribute("DATABASE_ACCESS", databaseAccess);
/* 143 */     databaseAccess.initialize();
/*     */     
/* 145 */     LOG.info("=== DatabaseAccess created.... DatabaseAccess type = " + databaseAccess.getType().name());
/*     */   }
/*     */   
/*     */   private void databaseTerminate() {
/* 149 */     DatabaseAccess databaseAccess = Commons.ctx.getDatabaseAccess();
/*     */     
/* 151 */     if (databaseAccess != null) {
/* 152 */       databaseAccess.terminate();
/* 153 */       LOG.info("=== DatabaseAccess terminated....");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void auditAlarmManagerInitialize(ServletContext ctx) throws CannotStartupException {
/* 161 */     AuditAlarmManager manager = new AuditAlarmManager();
/* 162 */     manager.initialize();
/* 163 */     ctx.setAttribute("AUDIT_ALARM_MANAGER", manager);
/*     */     
/* 165 */     LOG.info("=== AuditAlarmManager created....");
/*     */   }
/*     */   
/*     */   private void auditAlarmManagerTerminate() {
/* 169 */     AuditAlarmManager manager = Commons.ctx.getAuditAlarmManager();
/*     */     
/* 171 */     if (manager != null) {
/* 172 */       manager.terminate();
/* 173 */       LOG.info("=== AuditAlarmManager terminated....");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void freemarkerInitialize(ServletContext ctx) {
/* 183 */     Configuration config = new Configuration(Configuration.VERSION_2_3_23);
/*     */     try {
/* 185 */       List<TemplateLoader> loaders = new ArrayList<>();
/*     */       
/* 187 */       loaders.add(new FileTemplateLoader(new File(ctx.getRealPath("/WEB-INF/ftl"))));
/* 188 */       config.setTemplateLoader((TemplateLoader)new MultiTemplateLoader(loaders.<TemplateLoader>toArray(new TemplateLoader[loaders.size()])));
/* 189 */     } catch (IOException e) {
/* 190 */       LOG.error("Exception defining Freemarker template directories", e);
/*     */     } 
/* 192 */     config.setObjectWrapper((ObjectWrapper)new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
/* 193 */     ctx.setAttribute("FREEMARKER_CONFIG", config);
/* 194 */     LOG.info("=== Freemaker configuarion initialized .... ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void constantsInitialize(ServletContext ctx) {
/* 201 */     ctx.setAttribute("application.version", SystemSettingsDao.getValue("application.version"));
/* 202 */     ctx.setAttribute("application.since", Commons.displayDate(SystemSettingsDao.getLong("application.since")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initFidoSettings(ServletContext sc) {
/* 210 */     ChannelBindingTimerTask.refeshChannelBinding();
/*     */   }
/*     */ 
/*     */   
/*     */   private void terminateFidoSettings() {
/* 215 */     LOG.info("=== FidoServiceManager terminated....");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initServiceLogManager(ServletContext sc) {
/* 223 */     ServiceLogsManager manager = new ServiceLogsManager();
/* 224 */     manager.initialize();
/*     */     
/* 226 */     sc.setAttribute("SERVICE_LOG_MANAGER", manager);
/*     */     
/* 228 */     LOG.info("=== Event log manager initialized .... ");
/*     */   }
/*     */   
/*     */   private void terminateServiceLogManager() {
/* 232 */     ServiceLogsManager manager = Commons.ctx.getServiceLogsManager();
/*     */     
/* 234 */     if (manager != null) {
/* 235 */       manager.terminate();
/* 236 */       LOG.info("=== Event log manager termiated .... ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initServiceEnviroment() {
/* 244 */     ResourceBundle bundle = Commons.getResourceBundle("conf/service_env");
/*     */     
/* 246 */     if (bundle == null) {
/*     */       return;
/*     */     }
/*     */     
/* 250 */     String flag = bundle.getString("rpCtx.case.insensitive");
/*     */     
/* 252 */     if ("Y".equalsIgnoreCase(flag) || Boolean.parseBoolean(flag)) {
/* 253 */       SysEnvCommon.setEnvIgnoreCase(Boolean.TRUE);
/*     */     } else {
/* 255 */       SysEnvCommon.setEnvIgnoreCase(Boolean.FALSE);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 260 */     SysEnvCommon.setEnvIgnoreCaseKeys(new String[] { "userName" });
/*     */     
/* 262 */     String apiKey = bundle.getString("publish.sync.serviceApiKey");
/*     */     
/* 264 */     if (!StringUtils.isEmpty(apiKey)) {
/* 265 */       SysEnvCommon.setPublishServiceApiKey(apiKey.trim());
/*     */     }
/*     */     
/* 268 */     SysEnvCommon.setPublishHttpApiHeaderKey("biotp-publish-service");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initSyncCachePublisher(ServletContext sc) {
/* 274 */     SyncCachePublisher publisher = new SyncCachePublisher((SyncCacheSenderRT)new HttpSenderRT());
/* 275 */     publisher.initialize();
/*     */     
/* 277 */     sc.setAttribute("SYNC_CACHE_PUBLISHER", publisher);
/*     */     
/* 279 */     LOG.info("=== Event publisher initialized .... ");
/*     */   }
/*     */   
/*     */   private void terminateSyncCachePublisher() {
/* 283 */     SyncCachePublisher publisher = Commons.ctx.getSyncCachePublisher();
/*     */     
/* 285 */     if (publisher != null) {
/* 286 */       publisher.terminate();
/* 287 */       LOG.info("=== Event publisher terminated .... ");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initExternalMsgSenderManager(ServletContext sc) {
/* 293 */     ExternalServiceManager manager = new ExternalServiceManager();
/* 294 */     manager.initialize();
/*     */     
/* 296 */     sc.setAttribute("EXTERNAL_SEVEICE_MANAGER", manager);
/*     */     
/* 298 */     LOG.info("=== Event ExternalServiceManager initialized .... ");
/*     */   }
/*     */   
/*     */   private void terminateExternalMsgSenderManager() {
/* 302 */     ExternalServiceManager manager = Commons.ctx.getExternalServiceManager();
/*     */     
/* 304 */     if (manager != null) {
/* 305 */       manager.terminate();
/* 306 */       LOG.info("=== Event ExternalServiceManager termiated .... ");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\config\MmthContextListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */