/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.BaseDao;
/*    */ import com.dreammirae.mmth.db.dao.IViewDao;
/*    */ import com.dreammirae.mmth.db.dao.queries.DMLIssuanceHistory;
/*    */ import com.dreammirae.mmth.exception.InternalDBException;
/*    */ import com.dreammirae.mmth.vo.IssuanceHistoryVO;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository("issuanceHistoryDao")
/*    */ public class IssuanceHistoryDao
/*    */   extends BaseDao
/*    */   implements IViewDao<IssuanceHistoryVO, Long> {
/*    */   private static com.dreammirae.mmth.db.dao.IssuanceHistoryDao dao;
/*    */   
/*    */   public static void saveIssurance(IssuanceHistoryVO vo) {
/* 18 */     getIssuanceHistoryDao().save(vo);
/*    */   }
/*    */ 
/*    */   
/*    */   private static com.dreammirae.mmth.db.dao.IssuanceHistoryDao getIssuanceHistoryDao() {
/* 23 */     if (dao == null) {
/* 24 */       dao = new com.dreammirae.mmth.db.dao.IssuanceHistoryDao();
/*    */     }
/*    */     
/* 27 */     return dao;
/*    */   }
/*    */ 
/*    */   
/*    */   public void save(IssuanceHistoryVO vo) throws InternalDBException {
/* 32 */     if (-1L == vo.getId()) {
/* 33 */       insert(vo);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private void insert(IssuanceHistoryVO vo) throws InternalDBException {
/* 39 */     vo.setId(doInsert("INSERT INTO MMTH_IssuanceHistory (authType, username, deviceId, deviceType, pkgUnique, issueType, aaid, tokenId, tsIssue, issueMonth, issueDate, issueTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", DMLIssuanceHistory.toInsertParams(vo), DMLIssuanceHistory.getInsertTypes()));
/*    */   }
/*    */ 
/*    */   
/*    */   public IssuanceHistoryVO getOneByPK(Long id) throws InternalDBException {
/* 44 */     return (IssuanceHistoryVO)queryForObject("SELECT DISTINCT(a.id), authType, username, b.deviceId, b.deviceType, pkgUnique, issueType, aaid, tokenId, tsIssue, issueMonth, issueDate, issueTime, b.model FROM MMTH_IssuanceHistory a LEFT OUTER JOIN MMTH_UserDevices b ON a.deviceId = b.deviceId WHERE id=? ", DMLIssuanceHistory.toSelectPKParam(id.longValue()), 
/* 45 */         DMLIssuanceHistory.getRowMapper());
/*    */   }
/*    */ 
/*    */   
/*    */   public IssuanceHistoryVO getOneByObj(IssuanceHistoryVO vo) throws InternalDBException {
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<IssuanceHistoryVO> getViewContent(int limit, int offset, IssuanceHistoryVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 57 */     return queryForListWithLimit(DMLIssuanceHistory.selectContents(vo, tsFrom, tsTo), 
/* 58 */         DMLIssuanceHistory.toSelectParams(vo, tsFrom, tsTo), DMLIssuanceHistory.getRowMapper(), limit, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getViewConentCount(IssuanceHistoryVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 63 */     return queryForInt(DMLIssuanceHistory.selectContentConunt(vo, tsFrom, tsTo), DMLIssuanceHistory.toSelectParams(vo, tsFrom, tsTo), 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int deleteByPk(Long id) throws InternalDBException {
/* 69 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int delete(IssuanceHistoryVO vo) throws InternalDBException {
/* 78 */     return -1;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\IssuanceHistoryDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */