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
/*     */ public class MySQLDatabaseAccess
/*     */   extends PooledDatabaseAccess
/*     */ {
/*     */   public MySQLDatabaseAccess(ServletContext ctx) {
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
/*  27 */     this.datasource.setDriverClassName(DatabaseAccess.DatabaseType.MYSQL.getDriverClassName());
/*  28 */     this.datasource.setUrl(Commons.getDecryptValue(env, "db.url", null));
/*  29 */     this.datasource.setUsername(Commons.getDecryptValue(env, "db.username", null));
/*  30 */     this.datasource.setPassword(Commons.getDecryptValue(env, "db.password", null));
/*     */     
/*     */     try {
/*  33 */       this.datasource.setMaxTotal(Integer.parseInt(Commons.getValue(env, "db.pool.maxActive", null)));
/*  34 */     } catch (Exception e) {
/*  35 */       this.datasource.setMaxTotal(20);
/*     */     } 
/*     */     
/*     */     try {
/*  39 */       this.datasource.setMaxIdle(Integer.parseInt(Commons.getValue(env, "db.pool.maxIdle", null)));
/*  40 */     } catch (Exception e) {
/*  41 */       this.datasource.setMaxIdle(10);
/*     */     } 
/*     */     
/*  44 */     this.datasource.setInitialSize(3);
/*  45 */     this.datasource.setMaxWaitMillis(-1L);
/*  46 */     this.datasource.setTestWhileIdle(true);
/*  47 */     this.datasource.setTimeBetweenEvictionRunsMillis(10000L);
/*  48 */     this.datasource.setMinEvictableIdleTimeMillis(60000L);
/*  49 */     this.datasource.setTestOnBorrow(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean newDatabaseCheck(ExtendedJdbcTemplate ejt) throws CannotStartupException {
/*     */     try {
/*  56 */       Integer cnt = (Integer)ejt.queryForObject("select count(*) from MMTH_Administrators", Integer.class);
/*  57 */       return (cnt.intValue() < 1);
/*  58 */     } catch (Exception e) {
/*  59 */       LOGGER.info("Starting to create schema for this service. dbType=" + DatabaseAccess.DatabaseType.MYSQL.name());
/*  60 */       createSchema(DatabaseAccess.DatabaseType.MYSQL.getDbScriptPath(), ejt);
/*  61 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DatabaseAccess.DatabaseType getType() {
/*  67 */     return DatabaseAccess.DatabaseType.MYSQL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeBackup() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useSafeUtf8() {
/*  77 */     return this.useSafeUtf8;
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
/*  90 */     StringBuilder statement = new StringBuilder(128);
/*     */     
/*  92 */     for (String line : script) {
/*     */       
/*  94 */       statement.append(line);
/*     */       
/*  96 */       if (line.endsWith(";")) {
/*  97 */         LOGGER.info(statement.toString());
/*  98 */         int len = statement.length();
/*  99 */         ejt.execute(statement.substring(0, len - 1));
/* 100 */         statement.delete(0, len);
/*     */       } else {
/* 102 */         statement.append(" ");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\MySQLDatabaseAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */