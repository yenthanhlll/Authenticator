/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.upgrade;
/*    */ 
/*    */ import com.dreammirae.mmth.db.DatabaseAccess;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.db.upgrade.DBUpgrade;
/*    */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*    */ import com.dreammirae.mmth.vo.types.SettingEnabled;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Upgrade2_0_2
/*    */   extends DBUpgrade
/*    */ {
/*    */   protected void upgrade() throws Exception {
/* 17 */     Map<DatabaseAccess.DatabaseType, String[]> scripts = (Map)new HashMap<>(3);
/* 18 */     scripts.put(DatabaseAccess.DatabaseType.HSQL, hsqlScripts);
/* 19 */     scripts.put(DatabaseAccess.DatabaseType.PGSQL, pgsqlScripts);
/* 20 */     scripts.put(DatabaseAccess.DatabaseType.ORACLE, oracleScripts);
/*    */ 
/*    */ 
/*    */     
/* 24 */     runScript(scripts);
/*    */ 
/*    */     
/* 27 */     SettingEnabled settingEnabled = SystemSettingsDao.getSettingEnabled("externalApi.serviceEnabled");
/*    */     
/* 29 */     if (settingEnabled == null || !settingEnabled.toBoolean()) {
/* 30 */       SystemSettingsDao.setValue("externalApi.serviceType", ExternalWebApiTypes.NONE.name());
/*    */     } else {
/* 32 */       SystemSettingsDao.setValue("externalApi.serviceType", ExternalWebApiTypes.GPTWR.name());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getNewSchemaVersion() {
/* 40 */     return "2.0.3";
/*    */   }
/*    */   
/* 43 */   private static String[] hsqlScripts = new String[] { "ALTER TABLE MMTH_IssuanceHistory ALTER COLUMN tokenId VARCHAR(12);", "DROP VIEW MMTH_UserView;", "ALTER TABLE MMTH_UserAnnotations ALTER COLUMN DISPLAYNAME SET NULL;", "ALTER TABLE MMTH_UserAnnotations ALTER COLUMN EXTUNIQUE SET NULL;", "ALTER TABLE MMTH_UserAnnotations ALTER COLUMN CUSTOMERCODE SET NULL;", "ALTER TABLE MMTH_UserAnnotations ADD countryCode CHAR(2);", "CREATE VIEW MMTH_UserView AS SELECT  u.id AS id, u.username AS username, u.disabled AS disabled, u.status AS status, u.tsReg AS tsReg, u.tsUpdated AS tsUpdated, u.data AS data, a.displayName AS displayName, a.extUnique AS extUnique, a.customerCode AS customerCode, a.countryCode AS countryCode, a.data AS annotationData FROM MMTH_Users u LEFT JOIN MMTH_UserAnnotations a ON u.id = a.userId;" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   private static String[] pgsqlScripts = new String[] { "ALTER TABLE MMTH_IssuanceHistory ALTER COLUMN tokenId VARCHAR(12);", "DROP VIEW MMTH_UserView;", "ALTER TABLE MMTH_UserAnnotations ALTER COLUMN DISPLAYNAME DROP NOT NULL;", "ALTER TABLE MMTH_UserAnnotations ALTER COLUMN EXTUNIQUE DROP NOT NULL;", "ALTER TABLE MMTH_UserAnnotations ALTER COLUMN CUSTOMERCODE DROP NOT NULL;", "ALTER TABLE MMTH_UserAnnotations ADD countryCode CHAR(2);", "CREATE VIEW MMTH_UserView AS SELECT  u.id AS id, u.username AS username, u.disabled AS disabled, u.status AS status, u.tsReg AS tsReg, u.tsUpdated AS tsUpdated, u.data AS data, a.displayName AS displayName, a.extUnique AS extUnique, a.customerCode AS customerCode, a.countryCode AS countryCode, a.data AS annotationData FROM MMTH_Users u LEFT JOIN MMTH_UserAnnotations a ON u.id = a.userId;" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   private static String[] oracleScripts = new String[] { "ALTER TABLE MMTH_IssuanceHistory MODIFY (tokenId VARCHAR(12));", "DROP VIEW MMTH_UserView;", "ALTER TABLE MMTH_UserAnnotations MODIFY (DISPLAYNAME VARCHAR2(128) NULL);", "ALTER TABLE MMTH_UserAnnotations MODIFY (EXTUNIQUE VARCHAR2(64) NULL);", "ALTER TABLE MMTH_UserAnnotations MODIFY (CUSTOMERCODE CHAR(4) NULL);", "ALTER TABLE MMTH_UserAnnotations ADD countryCode CHAR(2);", "CREATE VIEW MMTH_UserView AS SELECT  u.id AS id, u.username AS username, u.disabled AS disabled, u.status AS status, u.tsReg AS tsReg, u.tsUpdated AS tsUpdated, u.data AS data, a.displayName AS displayName, a.extUnique AS extUnique, a.customerCode AS customerCode, a.countryCode AS countryCode, a.data AS annotationData FROM MMTH_Users u LEFT JOIN MMTH_UserAnnotations a ON u.id = a.userId;" };
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\d\\upgrade\Upgrade2_0_2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */