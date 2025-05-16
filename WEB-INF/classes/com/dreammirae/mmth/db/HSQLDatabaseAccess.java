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
/*     */ public class HSQLDatabaseAccess
/*     */   extends PooledDatabaseAccess
/*     */ {
/*     */   public HSQLDatabaseAccess(ServletContext ctx) {
/*  17 */     super(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createDataSourceImp() throws SQLException {
/*  23 */     ResourceBundle env = Commons.getEnvProperties();
/*     */ 
/*     */     
/*  26 */     this.datasource = new BasicDataSource();
/*  27 */     this.datasource.setDriverClassName(DatabaseAccess.DatabaseType.HSQL.getDriverClassName());
/*  28 */     this.datasource.setUrl(Commons.getDecryptValue(env, "db.url", null));
/*  29 */     this.datasource.setUsername(Commons.getDecryptValue(env, "db.username", null));
/*  30 */     this.datasource.setPassword(Commons.getDecryptValue(env, "db.password", null));
/*     */ 
/*     */     
/*     */     try {
/*  34 */       this.datasource.setMaxTotal(Integer.parseInt(Commons.getValue(env, "db.pool.maxActive", null)));
/*  35 */     } catch (Exception e) {
/*  36 */       this.datasource.setMaxTotal(20);
/*     */     } 
/*     */     
/*     */     try {
/*  40 */       this.datasource.setMaxIdle(Integer.parseInt(Commons.getValue(env, "db.pool.maxIdle", null)));
/*  41 */     } catch (Exception e) {
/*  42 */       this.datasource.setMaxTotal(10);
/*     */     } 
/*     */     
/*  45 */     this.datasource.setInitialSize(3);
/*  46 */     this.datasource.setMaxWaitMillis(-1L);
/*  47 */     this.datasource.setTestWhileIdle(true);
/*  48 */     this.datasource.setTimeBetweenEvictionRunsMillis(10000L);
/*  49 */     this.datasource.setMinEvictableIdleTimeMillis(60000L);
/*  50 */     this.datasource.setTestOnBorrow(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean newDatabaseCheck(ExtendedJdbcTemplate ejt) throws CannotStartupException {
/*     */     try {
/*  57 */       ejt.execute("select count(*) from MMTH_Administrators");
/*  58 */       return false;
/*  59 */     } catch (Exception e) {
/*  60 */       LOGGER.info("Starting to create schema for this service. dbType=" + DatabaseAccess.DatabaseType.HSQL.name());
/*  61 */       createSchema(DatabaseAccess.DatabaseType.HSQL.getDbScriptPath(), ejt);
/*  62 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DatabaseAccess.DatabaseType getType() {
/*  68 */     return DatabaseAccess.DatabaseType.HSQL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeBackup() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useSafeUtf8() {
/*  78 */     return this.useSafeUtf8;
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
/*  91 */     StringBuilder statement = new StringBuilder(128);
/*     */     
/*  93 */     for (String line : script) {
/*     */       
/*  95 */       statement.append(line);
/*     */       
/*  97 */       if (line.endsWith(";")) {
/*  98 */         LOGGER.info(statement.toString());
/*  99 */         int len = statement.length();
/* 100 */         ejt.execute(statement.substring(0, len - 1));
/* 101 */         statement.delete(0, len);
/*     */       } else {
/* 103 */         statement.append(" ");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\HSQLDatabaseAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */