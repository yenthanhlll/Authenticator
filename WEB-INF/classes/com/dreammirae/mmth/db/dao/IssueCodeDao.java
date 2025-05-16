/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*    */ import com.dreammirae.mmth.db.dao.BaseDao;
/*    */ import com.dreammirae.mmth.db.dao.queries.DMLIssueCodeData;
/*    */ import com.dreammirae.mmth.exception.InternalDBException;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.dreammirae.mmth.vo.IssueCodeDataVO;
/*    */ import org.springframework.stereotype.Repository;
/*    */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Repository("issueCodeDao")
/*    */ public class IssueCodeDao
/*    */   extends BaseDao
/*    */ {
/*    */   public void saveNewIssueCode(IssueCodeDataVO vo) {
/* 19 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void save(IssueCodeDataVO vo) throws InternalDBException {
/* 29 */     if (-1 == vo.getId()) {
/* 30 */       insert(vo);
/*    */     } else {
/* 32 */       update(vo);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void insert(IssueCodeDataVO vo) throws InternalDBException {
/* 37 */     vo.setId(
/* 38 */         doInsert("INSERT INTO MMTH_IssueCodeData (username, authType, issueCodeData, tsLifetime) VALUES (?, ?, ?, ?)", DMLIssueCodeData.toInsertParams(vo), DMLIssueCodeData.getInsertTypes()));
/*    */   }
/*    */   
/*    */   private void update(IssueCodeDataVO vo) throws InternalDBException {
/* 42 */     doUpdate("UPDATE MMTH_IssueCodeData set issueCodeData=? WHERE id=?", DMLIssueCodeData.toUpdateParams(vo), DMLIssueCodeData.getUpdateTypes());
/*    */   }
/*    */   
/*    */   public void deleteByPK(Integer id) throws InternalDBException {
/* 46 */     doUpdate("DELETE FROM MMTH_IssueCodeData WHERE id=?", DMLIssueCodeData.toDeletePKParam(id.intValue()));
/*    */   }
/*    */   
/*    */   public void deleteByUnique(IssueCodeDataVO vo) throws InternalDBException {
/* 50 */     doUpdate("DELETE FROM MMTH_IssueCodeData WHERE username=? and authType=?", DMLIssueCodeData.toDeleteParams(vo));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IssueCodeDataVO getOneByUnique(String username, AuthMethodTypes authType) throws InternalDBException {
/* 56 */     if (StringUtils.isEmpty(username)) {
/* 57 */       return null;
/*    */     }
/*    */     
/* 60 */     return (IssueCodeDataVO)queryForObject("SELECT id, username, authType, issueCodeData, tsLifetime FROM MMTH_IssueCodeData WHERE username=? and authType=?", DMLIssueCodeData.toSelectParams(username, authType), 
/* 61 */         DMLIssueCodeData.getRowMapper());
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\IssueCodeDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */