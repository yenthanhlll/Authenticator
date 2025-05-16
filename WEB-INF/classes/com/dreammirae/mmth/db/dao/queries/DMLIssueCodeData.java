/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*    */ import com.dreammirae.mmth.vo.IssueCodeDataVO;
/*    */ import org.springframework.jdbc.core.RowMapper;
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
/*    */ public class DMLIssueCodeData
/*    */ {
/*    */   public static final String INSERT = "INSERT INTO MMTH_IssueCodeData (username, authType, issueCodeData, tsLifetime) VALUES (?, ?, ?, ?)";
/*    */   public static final String UPDATE = "UPDATE MMTH_IssueCodeData set issueCodeData=? WHERE id=?";
/*    */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_IssueCodeData WHERE id=?";
/*    */   public static final String DELETE_BY_UNIQUE = "DELETE FROM MMTH_IssueCodeData WHERE username=? and authType=?";
/*    */   public static final String SELECT_ONE_UNIQUE = "SELECT id, username, authType, issueCodeData, tsLifetime FROM MMTH_IssueCodeData WHERE username=? and authType=?";
/*    */   
/*    */   public static int[] getInsertTypes() {
/* 39 */     return new int[] { 12, 4, 12, -5 };
/*    */   }
/*    */   
/*    */   public static Object[] toInsertParams(IssueCodeDataVO vo) {
/* 43 */     return new Object[] { vo.getUsername(), Integer.valueOf(vo.getAuthType().getCode()), vo.getIssueCodeData(), Long.valueOf(vo.getTsLifetime()) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int[] getUpdateTypes() {
/* 53 */     return new int[] { 12, 4 };
/*    */   }
/*    */   
/*    */   public static Object[] toUpdateParams(IssueCodeDataVO vo) {
/* 57 */     return new Object[] { vo.getIssueCodeData(), Integer.valueOf(vo.getId()) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Object[] toDeletePKParam(int id) {
/* 66 */     return new Object[] { Integer.valueOf(id) };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Object[] toDeleteParams(IssueCodeDataVO vo) {
/* 72 */     return new Object[] { vo.getUsername(), Integer.valueOf(vo.getAuthType().getCode()) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Object[] toSelectParams(String username, AuthMethodTypes authType) {
/* 82 */     return new Object[] { username, Integer.valueOf(authType.getCode()) };
/*    */   }
/*    */   
/*    */   public static RowMapper<IssueCodeDataVO> getRowMapper() {
/* 86 */     return (RowMapper<IssueCodeDataVO>)new IssueCodeDataRowMapper(null);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLIssueCodeData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */