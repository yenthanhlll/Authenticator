/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.upgrade;
/*    */ 
/*    */ import com.dreammirae.mmth.db.DatabaseAccess;
/*    */ import com.dreammirae.mmth.db.upgrade.DBUpgrade;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Upgrade2_0_1
/*    */   extends DBUpgrade
/*    */ {
/*    */   protected void upgrade() throws Exception {
/* 14 */     this.ejt.update("DELETE FROM MMTH_ServerChallenge");
/*    */     
/* 16 */     Map<DatabaseAccess.DatabaseType, String[]> scripts = (Map)new HashMap<>(3);
/* 17 */     scripts.put(DatabaseAccess.DatabaseType.HSQL, hsqlScripts);
/* 18 */     scripts.put(DatabaseAccess.DatabaseType.PGSQL, pgsqlScripts);
/* 19 */     scripts.put(DatabaseAccess.DatabaseType.ORACLE, oracleScripts);
/*    */     
/* 21 */     runScript(scripts);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getNewSchemaVersion() {
/* 27 */     return "2.0.2";
/*    */   }
/*    */   
/* 30 */   private static String[] hsqlScripts = new String[] { "ALTER TABLE MMTH_ServerChallenge ADD tsDone BIGINT DEFAULT 0;" };
/*    */ 
/*    */ 
/*    */   
/* 34 */   private static String[] pgsqlScripts = new String[] { "ALTER TABLE MMTH_ServerChallenge ADD tsDone BIGINT DEFAULT 0;" };
/*    */ 
/*    */ 
/*    */   
/* 38 */   private static String[] oracleScripts = new String[] { "ALTER TABLE MMTH_ServerChallenge ADD tsDone NUMBER(19,0) DEFAULT 0;" };
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\d\\upgrade\Upgrade2_0_1.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */