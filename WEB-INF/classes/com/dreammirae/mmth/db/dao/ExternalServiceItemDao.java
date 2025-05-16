/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.BaseDao;
/*    */ import com.dreammirae.mmth.db.dao.queries.DMLExternalServiceItems;
/*    */ import com.dreammirae.mmth.exception.InternalDBException;
/*    */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*    */ import com.dreammirae.mmth.vo.UserVO;
/*    */ import org.springframework.stereotype.Repository;
/*    */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Repository("externalServiceItemDao")
/*    */ public class ExternalServiceItemDao
/*    */   extends BaseDao
/*    */ {
/*    */   public void save(ExternalServiceItemVO vo) {
/* 18 */     if (-1L == vo.getId()) {
/* 19 */       insert(vo);
/*    */     } else {
/* 21 */       update(vo);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void insert(ExternalServiceItemVO vo) {
/* 27 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*    */   }
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
/*    */   private void update(ExternalServiceItemVO vo) {
/* 47 */     doUpdate("UPDATE MMTH_ExtServiceItems set status=?, tsDone=? WHERE id=?", DMLExternalServiceItems.toUpdateParams(vo));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ExternalServiceItemVO getOneByPK(long id) throws InternalDBException {
/* 54 */     return (ExternalServiceItemVO)queryForObject("SELECT data, id, userId, transactionId, externalIdentifier, status, tsReg, tsExpired, tsDone FROM MMTH_ExtServiceItems WHERE id=? ", DMLExternalServiceItems.toSelectPKParam(id), 
/* 55 */         DMLExternalServiceItems.getRowMapper());
/*    */   }
/*    */   
/*    */   public ExternalServiceItemVO getOneByUser(UserVO user) throws InternalDBException {
/* 59 */     return (ExternalServiceItemVO)queryForObject("SELECT data, id, userId, transactionId, externalIdentifier, status, tsReg, tsExpired, tsDone FROM MMTH_ExtServiceItems WHERE userId=? ", DMLExternalServiceItems.toSelectONEByUserParams(user), 
/* 60 */         DMLExternalServiceItems.getRowMapper());
/*    */   }
/*    */   
/*    */   public ExternalServiceItemVO getOneByTransaction(String transactionId) throws InternalDBException {
/* 64 */     return (ExternalServiceItemVO)queryForObject("SELECT data, id, userId, transactionId, externalIdentifier, status, tsReg, tsExpired, tsDone FROM MMTH_ExtServiceItems WHERE transactionId=? ", DMLExternalServiceItems.toSelectONEByTIDParams(transactionId), 
/* 65 */         DMLExternalServiceItems.getRowMapper());
/*    */   }
/*    */   
/*    */   public void deleteByUser(UserVO user) throws InternalDBException {
/* 69 */     doUpdate("DELETE FROM MMTH_ExtServiceItems WHERE userId=?", new Object[] { Integer.valueOf(user.getId()) });
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\ExternalServiceItemDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */