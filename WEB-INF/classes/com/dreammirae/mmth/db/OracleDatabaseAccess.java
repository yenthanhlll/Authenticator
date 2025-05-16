/*     */ package WEB-INF.classes.com.dreammirae.mmth.db;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.DatabaseAccess;
/*     */ import com.dreammirae.mmth.db.PooledDatabaseAccess;
/*     */ import com.dreammirae.mmth.exception.CannotStartupException;
/*     */ import com.dreammirae.mmth.util.db.ExtendedJdbcTemplate;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.commons.dbcp2.BasicDataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OracleDatabaseAccess
/*     */   extends PooledDatabaseAccess
/*     */ {
/*     */   private static final String PL_PRIFIX = "BEGIN";
/*     */   private static final String PL_SUFFIX = "END;";
/*     */   private static final String SQL_STATE_SYNTAX_ERROR = "42000";
/*     */   
/*     */   public OracleDatabaseAccess(ServletContext ctx) {
/*  23 */     super(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createDataSourceImp() throws SQLException {
/*  29 */     ResourceBundle env = Commons.getEnvProperties();
/*     */     
/*  31 */     this.datasource = new BasicDataSource();
/*     */     
/*  33 */     this.datasource.setDriverClassName(DatabaseAccess.DatabaseType.ORACLE.getDriverClassName());
/*  34 */     this.datasource.setUrl(Commons.getDecryptValue(env, "db.url", null));
/*  35 */     this.datasource.setUsername(Commons.getDecryptValue(env, "db.username", null));
/*  36 */     this.datasource.setPassword(Commons.getDecryptValue(env, "db.password", null));
/*     */     
/*     */     try {
/*  39 */       this.datasource.setMaxTotal(Integer.parseInt(Commons.getValue(env, "db.pool.maxActive", null)));
/*  40 */     } catch (Exception e) {
/*  41 */       this.datasource.setMaxTotal(20);
/*     */     } 
/*     */     
/*     */     try {
/*  45 */       this.datasource.setMaxIdle(Integer.parseInt(Commons.getValue(env, "db.pool.maxIdle", null)));
/*  46 */     } catch (Exception e) {
/*  47 */       this.datasource.setMaxTotal(10);
/*     */     } 
/*     */     
/*  50 */     this.datasource.setInitialSize(3);
/*  51 */     this.datasource.setMaxWaitMillis(-1L);
/*  52 */     this.datasource.setTestWhileIdle(true);
/*  53 */     this.datasource.setTimeBetweenEvictionRunsMillis(10000L);
/*  54 */     this.datasource.setMinEvictableIdleTimeMillis(60000L);
/*  55 */     this.datasource.setTestOnBorrow(true);
/*     */     
/*  57 */     this.useSafeUtf8 = Boolean.getBoolean(Commons.getValue(env, "db.utf8.safe", Boolean.FALSE.toString()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean newDatabaseCheck(ExtendedJdbcTemplate ejt) throws CannotStartupException {
/*     */     try {
/*  63 */       ejt.execute("select count(*) from MMTH_Administrators");
/*  64 */       return false;
/*  65 */     } catch (Exception e) {
/*     */       
/*  67 */       if (e.getCause() instanceof SQLException) {
/*  68 */         SQLException se = (SQLException)e.getCause();
/*  69 */         if ("42000".equals(se.getSQLState())) {
/*     */ 
/*     */           
/*  72 */           LOGGER.info("Starting to create schema for this service. dbType=" + DatabaseAccess.DatabaseType.ORACLE.name());
/*  73 */           createSchema(DatabaseAccess.DatabaseType.ORACLE.getDbScriptPath(), ejt);
/*  74 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/*  78 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseAccess.DatabaseType getType() {
/*  85 */     return DatabaseAccess.DatabaseType.ORACLE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeBackup() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useSafeUtf8() {
/*  95 */     return this.useSafeUtf8;
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
/*     */   public void runScript(String[] script, ExtendedJdbcTemplate ejt) throws Exception {
/* 108 */     StringBuilder statement = new StringBuilder(128);
/*     */     
/* 110 */     boolean isTrigger = false;
/*     */     
/* 112 */     for (String line : script) {
/*     */       
/* 114 */       statement.append(line);
/*     */       
/* 116 */       if (isTrigger || line.toUpperCase().startsWith("BEGIN")) {
/*     */         
/* 118 */         if (line.toUpperCase().endsWith("END;")) {
/* 119 */           LOGGER.info(statement.toString());
/* 120 */           int len = statement.length();
/* 121 */           ejt.execute(statement.toString());
/* 122 */           statement.delete(0, len);
/* 123 */           isTrigger = false;
/*     */         } else {
/* 125 */           statement.append(" ");
/* 126 */           isTrigger = true;
/*     */         }
/*     */       
/* 129 */       } else if (line.endsWith(";")) {
/* 130 */         LOGGER.info(statement.toString());
/* 131 */         int len = statement.length();
/* 132 */         ejt.execute(statement.substring(0, len - 1));
/* 133 */         statement.delete(0, len);
/*     */       } else {
/* 135 */         statement.append(" ");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\OracleDatabaseAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */