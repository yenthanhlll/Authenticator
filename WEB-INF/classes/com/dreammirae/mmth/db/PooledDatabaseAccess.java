/*     */ package WEB-INF.classes.com.dreammirae.mmth.db;
/*     */ 
/*     */ import com.dreammirae.mmth.db.DatabaseAccess;
/*     */ import com.dreammirae.mmth.exception.CannotStartupException;
/*     */ import com.dreammirae.mmth.util.db.ExtendedJdbcTemplate;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.commons.dbcp2.BasicDataSource;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.springframework.jdbc.CannotGetJdbcConnectionException;
/*     */ import org.springframework.jdbc.datasource.DataSourceUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PooledDatabaseAccess
/*     */   extends DatabaseAccess
/*     */ {
/*     */   protected static final String ENV_KEY_POOL_MAX_ACTIVE = "db.pool.maxActive";
/*     */   protected static final String ENV_KEY_POOL_MAX_IDLE = "db.pool.maxIdle";
/*     */   private static final long WAIT_TO_CONNECT = 30000L;
/*     */   protected BasicDataSource datasource;
/*     */   
/*     */   public PooledDatabaseAccess(ServletContext ctx) {
/*  36 */     super(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeImpl() throws CannotGetJdbcConnectionException {
/*  42 */     LOGGER.info("Ready to initializing pooled db connection.");
/*     */     
/*     */     try {
/*  45 */       createDataSourceImp();
/*  46 */     } catch (SQLException e) {
/*  47 */       LOGGER.error("Failed to create datasource....", e);
/*  48 */       throw new CannotGetJdbcConnectionException("Failed to create datasource...", e);
/*     */     } 
/*     */ 
/*     */     
/*  52 */     boolean isConnected = false;
/*     */     
/*  54 */     Connection conn = null;
/*  55 */     int retryCnt = 0;
/*     */     
/*     */     try {
/*     */       do {
/*     */         try {
/*  60 */           conn = DataSourceUtils.getConnection(getDataSource());
/*     */           
/*  62 */           LOGGER.info("== Connection test successfully....");
/*  63 */           isConnected = true;
/*     */         }
/*  65 */         catch (CannotGetJdbcConnectionException e) {
/*     */           
/*  67 */           if (++retryCnt >= 10) {
/*  68 */             if (conn != null) {
/*  69 */               DataSourceUtils.releaseConnection(conn, getDataSource());
/*  70 */               conn = null;
/*     */             } 
/*     */             
/*  73 */             throw e;
/*     */           } 
/*     */           
/*  76 */           LOGGER.error("* CannotGetJdbcConnectionException occured.... pause until next cycle. Retry count = " + retryCnt, (Throwable)e);
/*     */           try {
/*  78 */             Thread.sleep(30000L);
/*  79 */           } catch (InterruptedException ignore) {}
/*     */         
/*     */         }
/*     */       
/*     */       }
/*  84 */       while (!isConnected);
/*     */     } finally {
/*  86 */       if (conn != null) {
/*  87 */         DataSourceUtils.releaseConnection(conn, getDataSource());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void terminate() {
/*  94 */     LOGGER.info("Stopping database successfully....");
/*     */     try {
/*  96 */       this.datasource.close();
/*  97 */     } catch (SQLException e) {
/*  98 */       LOGGER.warn(e.getSQLState(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createSchema(String scriptFile, ExtendedJdbcTemplate ejt) throws CannotStartupException {
/* 104 */     BufferedReader in = new BufferedReader(new InputStreamReader(this.ctx.getResourceAsStream(scriptFile)));
/*     */     
/* 106 */     List<String> script = new ArrayList<>();
/*     */     try {
/*     */       String line;
/* 109 */       while ((line = in.readLine()) != null) {
/*     */         
/* 111 */         line = line.trim();
/*     */ 
/*     */         
/* 114 */         if (StringUtils.isEmpty(line)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 119 */         if (line.startsWith("--")) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 124 */         int comIdx = line.indexOf("--");
/*     */         
/* 126 */         if (comIdx > 0) {
/* 127 */           line = line.substring(0, comIdx);
/*     */         }
/*     */         
/* 130 */         script.add(line);
/*     */       } 
/*     */       
/* 133 */       String[] scripts = script.<String>toArray(new String[0]);
/* 134 */       runScript(scripts, ejt);
/*     */     }
/* 136 */     catch (IOException e) {
/* 137 */       LOGGER.error("Failed to load file for creating db scheme.", e);
/* 138 */       throw new CannotStartupException(e);
/* 139 */     } catch (Exception e) {
/* 140 */       LOGGER.error("Failed to create db scheme.", e);
/* 141 */       throw new CannotStartupException(e);
/*     */     } finally {
/*     */       try {
/* 144 */         in.close();
/* 145 */       } catch (IOException ignore) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataSource getDataSource() {
/* 153 */     return (DataSource)this.datasource;
/*     */   }
/*     */   
/*     */   protected abstract void createDataSourceImp() throws SQLException;
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\PooledDatabaseAccess.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */