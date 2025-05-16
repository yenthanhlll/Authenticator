/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.upgrade;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.DatabaseAccess;
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DBUpgrade
/*     */   extends BaseDao
/*     */ {
/*     */   private static final String INITIAL_SCHEMA_VERSION = "2.0.6";
/*  33 */   private static final String PACKAGE_NAME = com.dreammirae.mmth.db.upgrade.DBUpgrade.class.getPackage().getName();
/*     */   
/*     */   private static final String PREFIX_UPGRADE_CLASSNAME = ".Upgrade";
/*     */   private static final String DELIMITER_VERSION = "\\p{Punct}|\\p{Space}";
/*     */   private static final String DELIMITER_CLASSNAME = "_";
/*  38 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.db.upgrade.DBUpgrade.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkUpgrade() {
/*     */     while (true) {
/*  45 */       String schemaVersion = SystemSettingsDao.getValue("application.databaseSchemaVersion");
/*     */       
/*  47 */       if (StringUtils.isEmpty(schemaVersion)) {
/*  48 */         schemaVersion = "2.0.6";
/*     */       }
/*     */       
/*  51 */       LOG.info("*** Current schema version === " + schemaVersion);
/*     */ 
/*     */ 
/*     */       
/*  55 */       String upgradeClassname = PACKAGE_NAME + ".Upgrade" + schemaVersion.replaceAll("\\p{Punct}|\\p{Space}", "_");
/*     */       
/*  57 */       if (LOG.isDebugEnabled()) {
/*  58 */         LOG.debug("*** Target upgrade class name === " + upgradeClassname);
/*     */       }
/*     */       
/*  61 */       com.dreammirae.mmth.db.upgrade.DBUpgrade upgrade = null;
/*     */       
/*     */       try {
/*  64 */         Class<?> clazz = Class.forName(upgradeClassname);
/*     */         
/*  66 */         if (clazz != null) {
/*  67 */           upgrade = (com.dreammirae.mmth.db.upgrade.DBUpgrade)clazz.newInstance();
/*     */         }
/*  69 */       } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       if (upgrade == null) {
/*  75 */         LOG.info("*** Starting service with version " + schemaVersion);
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/*     */       try {
/*  81 */         LOG.info("*** Upgrading service from " + schemaVersion + " to " + upgrade.getNewSchemaVersion());
/*  82 */         upgrade.upgrade();
/*     */         
/*  84 */         SystemSettingsDao.setValue("application.databaseSchemaVersion", upgrade.getNewSchemaVersion());
/*  85 */         AuditAlarmTypes.SYSTEM.raiseAlarm(null, 260, AlarmLevels.INFORMATION, new Object[] { schemaVersion, upgrade.getNewSchemaVersion() });
/*     */       }
/*  87 */       catch (Exception e) {
/*  88 */         AuditAlarmTypes.SYSTEM.raiseAlarm(null, 261, AlarmLevels.CRITICAL, new Object[] { "Failed to upgrade service from " + schemaVersion + " to " + upgrade.getNewSchemaVersion() + ", cause=" + e.getMessage() });
/*  89 */         LOG.warn("*** Failed to upgrade service from " + schemaVersion + " to " + upgrade.getNewSchemaVersion() + ", cause=" + e.getMessage());
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void upgrade() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getNewSchemaVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runScript(Map<DatabaseAccess.DatabaseType, String[]> scripts) throws Exception {
/* 109 */     DatabaseAccess da = Commons.ctx.getDatabaseAccess();
/* 110 */     String[] script = scripts.get(da.getType());
/*     */     
/* 112 */     if (script == null) {
/*     */       return;
/*     */     }
/*     */     
/* 116 */     LOG.info("*** Run upgrade scripts :: " + Arrays.toString((Object[])script));
/*     */     
/* 118 */     Commons.ctx.getDatabaseAccess().runScript(script, this.ejt);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\d\\upgrade\DBUpgrade.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */