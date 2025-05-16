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
/*     */ public class TiberoDatabaseAccess
/*     */   extends PooledDatabaseAccess
/*     */ {
/*     */   private static final String PL_PRIFIX = "BEGIN";
/*     */   private static final String PL_SUFFIX = "END;";
/*     */   
/*     */   public TiberoDatabaseAccess(ServletContext ctx) {
/*  22 */     super(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createDataSourceImp() throws SQLException {
/*  28 */     ResourceBundle env = Commons.getEnvProperties();
/*     */     
/*  30 */     this.datasource = new BasicDataSource();
/*     */     
/*  32 */     this.datasource.setDriverClassName(DatabaseAccess.DatabaseType.TIBERO.getDriverClassName());
/*  33 */     this.datasource.setUrl(Commons.getDecryptValue(env, "db.url", null));
/*  34 */     this.datasource.setUsername(Commons.getDecryptValue(env, "db.username", null));
/*  35 */     this.datasource.setPassword(Commons.getDecryptValue(env, "db.password", null));
/*     */     
/*  37 */     this.datasource.setMaxTotal(20);
/*  38 */     this.datasource.setMaxTotal(10);
/*     */     
/*  40 */     this.datasource.setInitialSize(3);
/*  41 */     this.datasource.setMaxWaitMillis(-1L);
/*  42 */     this.datasource.setTestWhileIdle(true);
/*  43 */     this.datasource.setTimeBetweenEvictionRunsMillis(10000L);
/*  44 */     this.datasource.setMinEvictableIdleTimeMillis(60000L);
/*  45 */     this.datasource.setTestOnBorrow(true);
/*     */     
/*  47 */     this.useSafeUtf8 = Boolean.getBoolean(Commons.getValue(env, "db.utf8.safe", Boolean.FALSE.toString()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean newDatabaseCheck(ExtendedJdbcTemplate ejt) throws CannotStartupException {
/*     */     try {
/*  53 */       ejt.execute("select count(*) from MMTH_Administrators");
/*  54 */       return false;
/*  55 */     } catch (Exception e) {
/*     */       
/*  57 */       if (e.getCause() instanceof SQLException) {
/*  58 */         SQLException se = (SQLException)e.getCause();
/*     */         
/*  60 */         if (se.getErrorCode() == -8033) {
/*  61 */           createSchema(DatabaseAccess.DatabaseType.TIBERO.getDbScriptPath(), ejt);
/*  62 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/*  66 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseAccess.DatabaseType getType() {
/*  73 */     return DatabaseAccess.DatabaseType.ORACLE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeBackup() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useSafeUtf8() {
/*  83 */     return this.useSafeUtf8;
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
/*  96 */     StringBuilder statement = new StringBuilder(128);
/*     */     
/*  98 */     boolean isTrigger = false;
/*     */     
/* 100 */     for (String line : script) {
/*     */       
/* 102 */       statement.append(line);
/*     */       
/* 104 */       if (isTrigger || line.toUpperCase().startsWith("BEGIN")) {
/*     */         
/* 106 */         if (line.toUpperCase().endsWith("END;")) {
/* 107 */           LOGGER.info(statement.toString());
/* 108 */           int len = statement.length();
/* 109 */           ejt.execute(statement.toString());
/* 110 */           statement.delete(0, len);
/* 111 */           isTrigger = false;
/*     */         } else {
/* 113 */           statement.append(" ");
/* 114 */           isTrigger = true;
/*     */         }
/*     */       
/* 117 */       } else if (line.endsWith(";")) {
/* 118 */         LOGGER.info(statement.toString());
/* 119 */         int len = statement.length();
/* 120 */         ejt.execute(statement.substring(0, len - 1));
/* 121 */         statement.delete(0, len);
/*     */       } else {
/* 123 */         statement.append(" ");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\TiberoDatabaseAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */