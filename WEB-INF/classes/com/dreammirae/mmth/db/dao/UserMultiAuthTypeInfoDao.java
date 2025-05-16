/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLUserMultiAuthTypeInfo;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.vo.UserMultiAuthTypeVO;
/*     */ import java.util.List;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.stereotype.Repository;
/*     */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*     */ 
/*     */ @Repository("userMultiAuthTypeInfoDao")
/*     */ public class UserMultiAuthTypeInfoDao
/*     */   extends BaseDao
/*     */   implements IViewDao<UserMultiAuthTypeVO, Integer>
/*     */ {
/*     */   public void save(UserMultiAuthTypeVO vo) throws InternalDBException {
/*  19 */     if (getOneByObj(vo) == null) {
/*  20 */       insert(vo);
/*     */     } else {
/*     */       
/*  23 */       update(vo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void insert(UserMultiAuthTypeVO vo) throws InternalDBException {
/*  29 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void update(UserMultiAuthTypeVO vo) throws InternalDBException {
/*  41 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFailCntByProductType(UserMultiAuthTypeVO vo) {
/*  51 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserMultiAuthTypeVO getOneByPK(Integer id) throws InternalDBException {
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserMultiAuthTypeVO getOneByObj(UserMultiAuthTypeVO vo) throws InternalDBException {
/*  68 */     String sql = "SELECT a.id, userId, value, productType, authType, failCnt, tsUpdated, tsReg FROM MMTH_UserMultiFactorAuthType a LEFT OUTER JOIN MMTH_AuthFailCount b ON a.id = b.authTypeId WHERE userId=? AND authType=?";
/*  69 */     Object[] args = DMLUserMultiAuthTypeInfo.toSelectParams(vo);
/*  70 */     RowMapper<UserMultiAuthTypeVO> rowMapper = DMLUserMultiAuthTypeInfo.getRowMapper();
/*     */     
/*  72 */     UserMultiAuthTypeVO authType = (UserMultiAuthTypeVO)queryForObject(sql, args, rowMapper);
/*     */     
/*  74 */     return authType;
/*     */   }
/*     */   
/*     */   public int getOneByProductType(UserMultiAuthTypeVO vo) throws InternalDBException {
/*  78 */     String sql = "SELECT SUM(a.failCnt) AS failCnt FROM MMTH_AuthFailCount a LEFT OUTER JOIN MMTH_UserMultiFactorAuthType b ON a.authTypeId = b.id WHERE b.userId=? AND b.productType=?";
/*  79 */     Object[] args = DMLUserMultiAuthTypeInfo.toSelectByProductParams(vo);
/*     */     
/*  81 */     int failCnt = queryForInt(sql, args, 0);
/*     */     
/*  83 */     return failCnt;
/*     */   }
/*     */   
/*     */   public List<UserMultiAuthTypeVO> getContentListByObj(UserMultiAuthTypeVO vo) throws InternalDBException {
/*  87 */     String sql = "SELECT b.id, userId, value, productType, authType, a.failCnt, tsUpdated FROM MMTH_AuthFailCount a LEFT OUTER JOIN MMTH_UserMultiFactorAuthType b ON a.authTypeId = b.id WHERE b.userId=? AND b.productType=?";
/*  88 */     Object[] args = DMLUserMultiAuthTypeInfo.toSelectByProductParams(vo);
/*  89 */     RowMapper<UserMultiAuthTypeVO> rowMapper = DMLUserMultiAuthTypeInfo.getRowMapper();
/*     */     
/*  91 */     List<UserMultiAuthTypeVO> authType = queryForList(sql, args, rowMapper);
/*     */     
/*  93 */     return authType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<UserMultiAuthTypeVO> getViewContent(int limit, int offset, UserMultiAuthTypeVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getViewConentCount(UserMultiAuthTypeVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 104 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int deleteByPk(Integer id) throws InternalDBException {
/* 109 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int delete(UserMultiAuthTypeVO vo) throws InternalDBException {
/* 114 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\UserMultiAuthTypeInfoDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */