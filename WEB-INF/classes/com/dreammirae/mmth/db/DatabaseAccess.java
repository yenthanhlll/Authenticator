/*     */ package WEB-INF.classes.com.dreammirae.mmth.db;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.NewDatabaseInserts;
/*     */ import com.dreammirae.mmth.db.SystemSettingsDataInserts;
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.db.dao.FidoFacetDao;
/*     */ import com.dreammirae.mmth.db.dao.FidoMetadataDao;
/*     */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*     */ import com.dreammirae.mmth.db.upgrade.DBUpgrade;
/*     */ import com.dreammirae.mmth.exception.CannotStartupException;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.util.db.ExtendedJdbcTemplate;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.sql.DataSource;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.jdbc.CannotGetJdbcConnectionException;
/*     */ import org.springframework.jdbc.core.ConnectionCallback;
/*     */ import org.springframework.jdbc.datasource.DataSourceUtils;
/*     */ import org.springframework.util.Assert;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DatabaseAccess
/*     */ {
/* 108 */   protected static final Logger LOGGER = LoggerFactory.getLogger(com.dreammirae.mmth.db.DatabaseAccess.class);
/*     */   
/*     */   public static final String ENV_KEY_TYPE = "db.type";
/*     */   
/*     */   public static final String ENV_KEY_URL = "db.url";
/*     */   
/*     */   public static final String ENV_KEY_USERNAME = "db.username";
/*     */   
/*     */   public static final String ENV_KEY_PASSWORD = "db.password";
/*     */   
/*     */   protected static final String ENV_KEY_SAFE_UTF8 = "db.utf8.safe";
/*     */   
/*     */   protected final ServletContext ctx;
/*     */ 
/*     */   
/*     */   public static String getCurrentSchemaVersion() {
/* 124 */     return "2.0.7";
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.db.DatabaseAccess createDatabaseAccess(ServletContext ctx) {
/* 447 */     ResourceBundle env = Commons.getEnvProperties();
/* 448 */     String dbType = Commons.getValue(env, "db.type", "hsql");
/*     */     
/* 450 */     DatabaseType dt = DatabaseType.valueOf(dbType.toUpperCase());
/*     */     
/* 452 */     LOGGER.warn("$$$ DB Type == " + dbType);
/*     */     
/* 454 */     if (dt == null) {
/* 455 */       LOGGER.error("Unknown database type: " + dbType);
/* 456 */       throw new IllegalArgumentException("Unknown database type: " + dbType);
/*     */     } 
/*     */ 
/*     */     
/* 460 */     return dt.getImpl(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean useSafeUtf8 = false;
/*     */   
/*     */   public DatabaseAccess(ServletContext ctx) {
/* 467 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() throws CannotStartupException {
/*     */     try {
/* 479 */       initializeImpl();
/*     */ 
/*     */       
/* 482 */       ExtendedJdbcTemplate ejt = new ExtendedJdbcTemplate(getType().getCode());
/* 483 */       ejt.setDataSource(getDataSource());
/*     */       
/* 485 */       if (newDatabaseCheck(ejt)) {
/* 486 */         LOGGER.warn("The application has been running initially..");
/*     */ 
/*     */         
/*     */         try {
/* 490 */           NewDatabaseInserts.doInsertData(Commons.ctx.getServletContext());
/*     */ 
/*     */           
/* 493 */           SystemSettingsDao.setValue("application.databaseSchemaVersion", getCurrentSchemaVersion());
/* 494 */           SystemSettingsDao.setValue("application.since", Long.toString(System.currentTimeMillis()));
/*     */           
/* 496 */           AuditAlarmTypes.SYSTEM.raiseAlarm(null, 259, AlarmLevels.INFORMATION, new Object[] { getCurrentSchemaVersion() });
/* 497 */         } catch (Exception e) {
/* 498 */           throw new CannotStartupException("Failed to get JDBC Connection.", e);
/*     */         } 
/*     */       } else {
/* 501 */         DBUpgrade.checkUpgrade();
/*     */       } 
/*     */       
/* 504 */       SystemSettingsDataInserts.saveSettings(this.ctx);
/*     */ 
/*     */       
/* 507 */       FidoMetadataDao.getAcceptPolicy();
/* 508 */       FidoFacetDao.getTrustedFacets();
/* 509 */       AppAgentDao.getAcceptAppAgent();
/*     */     }
/* 511 */     catch (CannotGetJdbcConnectionException e) {
/* 512 */       LOGGER.error("Unable to connect to database of type " + getType().name(), (Throwable)e);
/* 513 */       throw new CannotStartupException("Failed to get JDBC Connection.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void initializeImpl() throws CannotGetJdbcConnectionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void terminate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract DatabaseType getType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract DataSource getDataSource();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void executeBackup();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean useSafeUtf8();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean newDatabaseCheck(ExtendedJdbcTemplate paramExtendedJdbcTemplate) throws CannotStartupException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void runScript(String[] paramArrayOfString, ExtendedJdbcTemplate paramExtendedJdbcTemplate) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T doInConnection(ConnectionCallback<T> action) throws SQLException {
/* 564 */     Assert.notNull(action, "Callback object must not be null");
/*     */     
/* 566 */     Connection con = DataSourceUtils.getConnection(getDataSource());
/*     */     try {
/* 568 */       Connection conToUse = con;
/* 569 */       return (T)action.doInConnection(conToUse);
/* 570 */     } catch (SQLException ex) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 575 */       DataSourceUtils.releaseConnection(con, getDataSource());
/* 576 */       con = null;
/* 577 */       throw ex;
/*     */     } finally {
/* 579 */       DataSourceUtils.releaseConnection(con, getDataSource());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\DatabaseAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */