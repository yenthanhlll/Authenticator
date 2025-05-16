/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.BaseDao;
/*    */ import com.dreammirae.mmth.db.dao.IViewDao;
/*    */ import com.dreammirae.mmth.db.dao.queries.DMLUserAuthFailCount;
/*    */ import com.dreammirae.mmth.exception.InternalDBException;
/*    */ import com.dreammirae.mmth.vo.UserAuthFailCountVO;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Repository;
/*    */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*    */ 
/*    */ @Repository("userAuthFailCountDao")
/*    */ public class UserAuthFailCountDao
/*    */   extends BaseDao
/*    */   implements IViewDao<UserAuthFailCountVO, Integer>
/*    */ {
/*    */   public void save(UserAuthFailCountVO vo) throws InternalDBException {
/* 18 */     if (getOneByPK(Integer.valueOf(vo.getAuthTypeId())) == null) {
/* 19 */       insert(vo);
/*    */     } else {
/*    */       
/* 22 */       update(vo);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void insert(UserAuthFailCountVO vo) throws InternalDBException {
/* 28 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
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
/*    */   private void update(UserAuthFailCountVO vo) throws InternalDBException {
/* 40 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateFailCnt(UserAuthFailCountVO vo) {
/* 51 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
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
/*    */   public UserAuthFailCountVO getOneByPK(Integer id) throws InternalDBException {
/* 63 */     return (UserAuthFailCountVO)queryForObject("SELECT authTypeId, failCnt, tsLastAuthFail FROM MMTH_AuthFailCount WHERE authTypeId=? ", DMLUserAuthFailCount.toSelectPKParam(id), 
/* 64 */         DMLUserAuthFailCount.getRowMapper());
/*    */   }
/*    */ 
/*    */   
/*    */   public UserAuthFailCountVO getOneByObj(UserAuthFailCountVO vo) throws InternalDBException {
/* 69 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<UserAuthFailCountVO> getViewContent(int limit, int offset, UserAuthFailCountVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getViewConentCount(UserAuthFailCountVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 80 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int deleteByPk(Integer id) throws InternalDBException {
/* 85 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int delete(UserAuthFailCountVO vo) throws InternalDBException {
/* 90 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\UserAuthFailCountDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */