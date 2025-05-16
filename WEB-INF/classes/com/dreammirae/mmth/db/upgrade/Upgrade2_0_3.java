/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.upgrade;
/*    */ 
/*    */ import com.dreammirae.mmth.db.DatabaseAccess;
/*    */ import com.dreammirae.mmth.db.upgrade.DBUpgrade;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Upgrade2_0_3
/*    */   extends DBUpgrade
/*    */ {
/*    */   protected void upgrade() throws Exception {
/* 14 */     Map<DatabaseAccess.DatabaseType, String[]> scripts = (Map)new HashMap<>(3);
/* 15 */     scripts.put(DatabaseAccess.DatabaseType.HSQL, hsqlScripts);
/* 16 */     scripts.put(DatabaseAccess.DatabaseType.PGSQL, pgsqlScripts);
/* 17 */     scripts.put(DatabaseAccess.DatabaseType.ORACLE, oracleScripts);
/*    */     
/* 19 */     runScript(scripts);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getNewSchemaVersion() {
/* 24 */     return "2.0.4";
/*    */   }
/*    */   
/* 27 */   private static String[] hsqlScripts = new String[] { "ALTER TABLE MMTH_Tokens ADD lost CHAR(1) DEFAULT 'N';", "ALTER TABLE MMTH_Tokens ADD tsLost BIGINT DEFAULT 0;", "UPDATE MMTH_Tokens SET lost='N', tsLost=0;" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   private static String[] pgsqlScripts = new String[] { "ALTER TABLE MMTH_Tokens ADD lost CHAR(1) DEFAULT 'N';", "ALTER TABLE MMTH_Tokens ADD tsLost BIGINT DEFAULT 0;", "UPDATE MMTH_Tokens SET lost='N', tsLost=0;" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   private static String[] oracleScripts = new String[] { "ALTER TABLE MMTH_Tokens ADD lost CHAR(1) DEFAULT 'N';", "ALTER TABLE MMTH_Tokens ADD tsLost NUMBER(0,19) DEFAULT 0;", "UPDATE MMTH_Tokens SET lost='N', tsLost=0;" };
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\d\\upgrade\Upgrade2_0_3.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */