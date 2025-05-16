/*    */ package WEB-INF.classes.com.dreammirae.mmth.db;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.util.Utf8Properties;
/*    */ import com.dreammirae.mmth.util.io.Closer;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletContext;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SystemSettingsDataInserts
/*    */ {
/*    */   private static final String SYS_PROP_NAME = "/built-in/settings/system_settings.properties";
/*    */   
/*    */   public static void saveSettings(ServletContext ctx) {
/* 22 */     File sysFile = new File(ctx.getRealPath("/built-in/settings/system_settings.properties"));
/*    */     
/* 24 */     if (!sysFile.exists()) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     Utf8Properties prop = new Utf8Properties();
/* 29 */     FileInputStream fis = null;
/*    */ 
/*    */     
/*    */     try {
/* 33 */       fis = new FileInputStream(sysFile);
/* 34 */       prop.load(fis);
/*    */       
/* 36 */       String[] keys = SystemSettingsDao.SYS_KEYS;
/*    */       
/* 38 */       for (String key : keys) {
/* 39 */         String value = prop.getProperty(key);
/*    */         
/* 41 */         if (!StringUtils.isEmpty(value)) {
/*    */           
/*    */           try {
/* 44 */             value = value.trim();
/* 45 */             SystemSettingsDao.setValue(key, value);
/* 46 */           } catch (Exception e) {
/* 47 */             e.printStackTrace();
/*    */           }
/*    */         
/*    */         }
/*    */       }
/*    */     
/* 53 */     } catch (IOException e) {
/* 54 */       e.printStackTrace();
/*    */     } finally {
/* 56 */       Closer.close(fis);
/* 57 */       FileUtils.deleteQuietly(sysFile);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\SystemSettingsDataInserts.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */