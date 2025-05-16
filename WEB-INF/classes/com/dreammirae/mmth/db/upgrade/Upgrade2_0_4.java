/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.upgrade;
/*    */ 
/*    */ import com.dreammirae.mmth.db.DatabaseAccess;
/*    */ import com.dreammirae.mmth.db.upgrade.DBUpgrade;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Upgrade2_0_4
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
/* 24 */     return "2.0.5";
/*    */   }
/*    */   
/* 27 */   private static String[] hsqlScripts = new String[] { "CREATE TABLE MMTH_ServiceLogAnnotations ( serviceLogId BIGINT NOT NULL, reqEmpNo VARCHAR(32) NOT NULL );", "ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Un1 UNIQUE (serviceLogId);", "ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Fk1 FOREIGN KEY (serviceLogId) REFERENCES MMTH_ServiceLogs(id) ON DELETE CASCADE;" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   private static String[] pgsqlScripts = new String[] { "CREATE TABLE MMTH_ServiceLogAnnotations ( serviceLogId BIGINT NOT NULL, reqEmpNo VARCHAR(32) NOT NULL );", "ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Un1 UNIQUE (serviceLogId);", "ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Fk1 FOREIGN KEY (serviceLogId) REFERENCES MMTH_ServiceLogs(id) ON DELETE CASCADE;" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   private static String[] oracleScripts = new String[] { "CREATE TABLE MMTH_ServiceLogAnnotations ( serviceLogId NUMBER(19, 0) NOT NULL, reqEmpNo VARCHAR2(32) NOT NULL );", "ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Un1 UNIQUE (serviceLogId);", "ALTER TABLE MMTH_ServiceLogAnnotations ADD CONSTRAINT MMTH_ServiceLogAnnotations_Fk1 FOREIGN KEY (serviceLogId) REFERENCES MMTH_ServiceLogs(id) ON DELETE CASCADE;" };
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\d\\upgrade\Upgrade2_0_4.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */