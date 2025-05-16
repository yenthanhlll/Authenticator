/*     */ package WEB-INF.classes.com.dreammirae.mmth.db;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.DatabaseAccess;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.Utf8Properties;
/*     */ import com.dreammirae.mmth.util.io.Closer;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabaseEnvPropUtils
/*     */ {
/*     */   private static final String FROM_ENV_PROPERTIES = "/built-in/db/env.properties";
/*     */   private static final String TO_ENV_PROPERTIES = "/WEB-INF/classes/conf/env.properties";
/*     */   
/*     */   public static void readDbEnvProperties(ServletContext ctx) {
/*  31 */     String realPath = ctx.getRealPath("/built-in/db/env.properties");
/*     */     
/*  33 */     File envFrom = new File(realPath);
/*     */     
/*  35 */     if (!envFrom.exists()) {
/*     */       return;
/*     */     }
/*     */     
/*  39 */     File targetFile = new File(ctx.getRealPath("/WEB-INF/classes/conf/env.properties"));
/*     */     
/*  41 */     if (!targetFile.exists()) {
/*     */       try {
/*  43 */         targetFile.createNewFile();
/*  44 */       } catch (IOException e) {
/*  45 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  49 */     FileInputStream fisFrom = null;
/*  50 */     FileInputStream fisTarget = null;
/*  51 */     FileOutputStream fosTarget = null;
/*     */ 
/*     */     
/*     */     try {
/*  55 */       Utf8Properties from = new Utf8Properties();
/*  56 */       fisFrom = new FileInputStream(envFrom);
/*  57 */       from.load(fisFrom);
/*     */       
/*  59 */       Map<Object, Object> map = new Hashtable<>();
/*     */       
/*  61 */       if (from.containsKey("db.type")) {
/*  62 */         DatabaseAccess.DatabaseType dataType = DatabaseAccess.DatabaseType.valueOf(from.getProperty("db.type").toUpperCase());
/*  63 */         map.put("db.type", dataType.name());
/*     */       } 
/*     */       
/*  66 */       if (from.containsKey("db.url")) {
/*  67 */         String value = from.getProperty("db.url");
/*     */         
/*  69 */         if (!StringUtils.isEmpty(value)) {
/*  70 */           map.put("db.url", SysEnvCommon.encryptText(value));
/*     */         }
/*     */       } 
/*     */       
/*  74 */       if (from.containsKey("db.username")) {
/*  75 */         String value = from.getProperty("db.username");
/*     */         
/*  77 */         if (!StringUtils.isEmpty(value)) {
/*  78 */           map.put("db.username", SysEnvCommon.encryptText(value));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  83 */       if (from.containsKey("db.password")) {
/*  84 */         String value = from.getProperty("db.password");
/*     */         
/*  86 */         if (!StringUtils.isEmpty(value)) {
/*  87 */           map.put("db.password", SysEnvCommon.encryptText(value));
/*     */         }
/*     */       } 
/*     */       
/*  91 */       if (from.containsKey("db.jeus.exportName")) {
/*  92 */         String value = from.getProperty("db.jeus.exportName");
/*     */         
/*  94 */         if (!StringUtils.isEmpty(value)) {
/*  95 */           map.put("db.jeus.exportName", value);
/*     */         }
/*     */       } 
/*     */       
/*  99 */       if (map.isEmpty()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 104 */       Utf8Properties target = new Utf8Properties();
/* 105 */       fisTarget = new FileInputStream(targetFile);
/* 106 */       target.load(fisTarget);
/* 107 */       target.putAll(map);
/* 108 */       fosTarget = new FileOutputStream(targetFile);
/* 109 */       target.store(fosTarget, Commons.displayDate(System.currentTimeMillis()));
/*     */     
/*     */     }
/* 112 */     catch (Exception e) {
/* 113 */       e.printStackTrace();
/*     */     } finally {
/* 115 */       Closer.close(fisFrom);
/* 116 */       Closer.close(fisTarget);
/* 117 */       Closer.close(fosTarget);
/* 118 */       FileUtils.deleteQuietly(envFrom);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\DatabaseEnvPropUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */