/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.upgrade;
/*    */ 
/*    */ import com.dreammirae.mmth.db.DatabaseAccess;
/*    */ import com.dreammirae.mmth.db.upgrade.DBUpgrade;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Upgrade2_0_5
/*    */   extends DBUpgrade
/*    */ {
/*    */   protected void upgrade() throws Exception {
/* 14 */     Map<DatabaseAccess.DatabaseType, String[]> scripts = (Map)new HashMap<>(3);
/* 15 */     scripts.put(DatabaseAccess.DatabaseType.HSQL, hsqlScripts);
/* 16 */     scripts.put(DatabaseAccess.DatabaseType.PGSQL, pgsqlScripts);
/* 17 */     scripts.put(DatabaseAccess.DatabaseType.ORACLE, oracleScripts);
/* 18 */     scripts.put(DatabaseAccess.DatabaseType.MYSQL, mysqlScripts);
/* 19 */     scripts.put(DatabaseAccess.DatabaseType.TIBERO, tiberoScripts);
/*    */     
/* 21 */     runScript(scripts);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getNewSchemaVersion() {
/* 26 */     return "2.0.6";
/*    */   }
/*    */   
/* 29 */   private static String[] hsqlScripts = new String[] { "ALTER TABLE MMTH_ServiceLogs ADD reqType INT DEFAULT 0;", "UPDATE MMTH_ServiceLogs SET reqType = 0;" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   private static String[] pgsqlScripts = new String[] { "ALTER TABLE MMTH_ServiceLogs ADD reqType INT DEFAULT 0;", "UPDATE MMTH_ServiceLogs SET reqType = 0;" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   private static String[] oracleScripts = new String[] { "ALTER TABLE MMTH_ServiceLogs ADD reqType NUMBER(10,0) DEFAULT 0;", "UPDATE MMTH_ServiceLogs SET reqType = 0;" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   private static String[] mysqlScripts = new String[] { "ALTER TABLE MMTH_ServiceLogs ADD reqType INT DEFAULT 0;", "UPDATE MMTH_ServiceLogs SET reqType = 0;" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   private static String[] tiberoScripts = new String[] { "ALTER TABLE MMTH_ServiceLogs ADD reqType NUMBER(10,0) DEFAULT 0;", "UPDATE MMTH_ServiceLogs SET reqType = 0;" };
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\d\\upgrade\Upgrade2_0_5.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */