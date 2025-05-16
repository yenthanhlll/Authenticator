/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DMLSystemSettings
/*    */ {
/*    */   public static final String INSERT = "INSERT INTO MMTH_SystemSettings (settingName, settingValue) VALUES (?, ?)";
/*    */   public static final String DELETE = "DELETE FROM MMTH_SystemSettings WHERE settingName=?";
/*    */   public static final String SELECT = "SELECT settingValue FROM MMTH_SystemSettings WHERE settingName=?";
/*    */   
/*    */   public static Object[] toSelectParam(String settingName) {
/* 13 */     return new Object[] { settingName };
/*    */   }
/*    */   
/*    */   public static Object[] toDeleteParam(String settingName) {
/* 17 */     return new Object[] { settingName };
/*    */   }
/*    */   
/*    */   public static Object[] toInsertParam(String settingName, String settingValue) {
/* 21 */     return new Object[] { settingName, settingValue };
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLSystemSettings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */