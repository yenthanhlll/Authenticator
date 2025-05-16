/*     */ package WEB-INF.classes.com.dreammirae.mmth.db;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.DatabaseAccess;
/*     */ import com.dreammirae.mmth.db.PooledDatabaseAccess;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLSystemSettings;
/*     */ import com.dreammirae.mmth.exception.CannotStartupException;
/*     */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*     */ import com.dreammirae.mmth.runtime.code.TimePeriodTypes;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.db.ExtendedJdbcTemplate;
/*     */ import com.dreammirae.mmth.vo.types.SettingEnabled;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.sql.DataSource;
/*     */ import org.springframework.dao.EmptyResultDataAccessException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JEUSOracleDatabaseAccess
/*     */   extends PooledDatabaseAccess
/*     */ {
/*     */   public static final String ENV_KEY_EXPORT_NAME = "db.jeus.exportName";
/*     */   private String exportName;
/*     */   private DataSource datasource;
/*     */   
/*     */   public JEUSOracleDatabaseAccess(ServletContext ctx) {
/*  31 */     super(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createDataSourceImp() throws SQLException {
/*  37 */     String localExportName = System.getProperty("db.jeus.exportName");
/*     */ 
/*     */     
/*  40 */     ResourceBundle env = Commons.getEnvProperties();
/*  41 */     if (StringUtils.isEmpty(localExportName)) {
/*  42 */       localExportName = Commons.getValue(env, "db.jeus.exportName", null);
/*     */     }
/*     */     
/*  45 */     this.useSafeUtf8 = Boolean.getBoolean(Commons.getValue(env, "db.utf8.safe", Boolean.FALSE.toString()));
/*  46 */     this.exportName = localExportName;
/*     */     
/*  48 */     if (this.exportName == null) {
/*  49 */       throw new SQLException("Export name could not find 'env.properties'...");
/*     */     }
/*     */     
/*  52 */     LOGGER.info("### Export NAME == " + this.exportName);
/*     */ 
/*     */     
/*     */     try {
/*  56 */       Context initContext = new InitialContext();
/*     */ 
/*     */       
/*  59 */       this.datasource = (DataSource)initContext.lookup(this.exportName);
/*     */       
/*  61 */       LOGGER.info("Get a datasource successfully... == " + this.exportName);
/*  62 */     } catch (Throwable e) {
/*  63 */       LOGGER.error("Failed to get a datasource...", e);
/*  64 */       throw new SQLException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean newDatabaseCheck(ExtendedJdbcTemplate ejt) throws CannotStartupException {
/*     */     try {
/*  73 */       Integer cnt = (Integer)ejt.queryForObject("select count(*) from MMTH_Administrators", Integer.class);
/*     */       
/*  75 */       boolean isNew = (cnt.intValue() < 1);
/*     */       
/*  77 */       if (isNew) {
/*  78 */         saveStaticSettingValues(ejt);
/*     */       }
/*     */       
/*  81 */       return isNew;
/*  82 */     } catch (Exception e) {
/*  83 */       LOGGER.error("Failed to check database...", e);
/*  84 */       throw new CannotStartupException("Failed to check database...", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DatabaseAccess.DatabaseType getType() {
/*  90 */     return DatabaseAccess.DatabaseType.ORACLE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeBackup() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useSafeUtf8() {
/* 100 */     return this.useSafeUtf8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void runScript(String[] script, ExtendedJdbcTemplate ejt) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSource getDataSource() {
/* 118 */     return this.datasource;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void saveStaticSettingValues(ExtendedJdbcTemplate ejt) {
/* 123 */     saveSettingValue(ejt, "integrate.authLifetimePeriods", String.valueOf(2));
/* 124 */     saveSettingValue(ejt, "integrate.authLifetimePeriodType", TimePeriodTypes.MINUTE.name());
/* 125 */     saveSettingValue(ejt, "integrate.authMaxFailCount", String.valueOf(10));
/* 126 */     saveSettingValue(ejt, "integrate.pinMaxFailCount", String.valueOf(10));
/* 127 */     saveSettingValue(ejt, "integrate.biotpMaxFailCount", String.valueOf(10));
/* 128 */     saveSettingValue(ejt, "integrate.issueCodeLifetimePeriods", String.valueOf(7));
/* 129 */     saveSettingValue(ejt, "integrate.issueCodeLifetimePeriodType", TimePeriodTypes.DAY.name());
/* 130 */     saveSettingValue(ejt, "integrate.issueCodeMaxFailCount", String.valueOf(10));
/* 131 */     saveSettingValue(ejt, "integrate.checkAuthResultLifetimePeriods", String.valueOf(2));
/* 132 */     saveSettingValue(ejt, "integrate.checkAuthResultLifetimePeriodType", TimePeriodTypes.MINUTE.name());
/* 133 */     saveSettingValue(ejt, "integrate.tokenThresholdCnt", String.valueOf(10000));
/* 134 */     saveSettingValue(ejt, "fido.issueCodeEnabled", SettingEnabled.DISABLED.name());
/* 135 */     saveSettingValue(ejt, "biotp.issueCodeEnabled", SettingEnabled.ENABLED.name());
/* 136 */     saveSettingValue(ejt, "advanced.fidoEnabled", SettingEnabled.ENABLED.name());
/* 137 */     saveSettingValue(ejt, "advanced.biotpEnabled", SettingEnabled.ENABLED.name());
/* 138 */     saveSettingValue(ejt, "advanced.saotpEnabled", SettingEnabled.DISABLED.name());
/* 139 */     saveSettingValue(ejt, "advanced.publisherEnabled", SettingEnabled.ENABLED.name());
/* 140 */     saveSettingValue(ejt, "externalApi.serviceType", ExternalWebApiTypes.GLOBAL_WIBEE.name());
/* 141 */     saveSettingValue(ejt, "customKeys.countryCode", "AU,BD,BH,KH,CN,HK,IR,IN,ID,JP,PH,SG,MY,MM,VN,AE,RU,GB,PL,US,BR");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void saveSettingValue(ExtendedJdbcTemplate ejt, String settingName, String settingValue) {
/*     */     try {
/* 148 */       String storeValue = (String)ejt.queryForObject("SELECT settingValue FROM MMTH_SystemSettings WHERE settingName=?", DMLSystemSettings.toSelectParam(settingName), String.class);
/*     */       
/* 150 */       if (StringUtils.isEmpty(storeValue)) {
/* 151 */         setValue(ejt, settingName, settingValue);
/*     */       }
/*     */     }
/* 154 */     catch (EmptyResultDataAccessException e) {
/* 155 */       setValue(ejt, settingName, settingValue);
/* 156 */     } catch (Exception e) {
/*     */       
/* 158 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setValue(ExtendedJdbcTemplate ejt, String settingName, String settingValue) {
/*     */     try {
/* 164 */       ejt.update("DELETE FROM MMTH_SystemSettings WHERE settingName=?", DMLSystemSettings.toDeleteParam(settingName));
/* 165 */       ejt.update("INSERT INTO MMTH_SystemSettings (settingName, settingValue) VALUES (?, ?)", DMLSystemSettings.toInsertParam(settingName, settingValue));
/* 166 */       LOGGER.info("SystemSettins updated... settingName=" + settingName + ", settingValue=" + settingValue);
/* 167 */     } catch (Exception e) {
/*     */       
/* 169 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\JEUSOracleDatabaseAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */