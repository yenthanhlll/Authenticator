/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DMLExternalServiceItems
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_ExtServiceItems (userId, transactionId, externalIdentifier, status, tsReg, tsExpired, data) VALUES (?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_ExtServiceItems set status=?, tsDone=? WHERE id=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_ExtServiceItems WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_ExtServiceItems WHERE userId=?";
/*     */   public static final String DELETE_BY_EXTERNAL_ID = "DELETE FROM MMTH_ExtServiceItems WHERE externalIdentifier=?";
/*     */   public static final String DELETE_EXPIRED = "DELETE FROM MMTH_ExtServiceItems WHERE tsExpired < ?";
/*     */   private static final String SELECT_PREFIX = "SELECT data, id, userId, transactionId, externalIdentifier, status, tsReg, tsExpired, tsDone FROM MMTH_ExtServiceItems ";
/*     */   public static final String SELECT_ONE_PK = "SELECT data, id, userId, transactionId, externalIdentifier, status, tsReg, tsExpired, tsDone FROM MMTH_ExtServiceItems WHERE id=? ";
/*     */   public static final String SELECT_ONE_BY_USER = "SELECT data, id, userId, transactionId, externalIdentifier, status, tsReg, tsExpired, tsDone FROM MMTH_ExtServiceItems WHERE userId=? ";
/*     */   public static final String SELECT_ONE_BY_TRANSACTION = "SELECT data, id, userId, transactionId, externalIdentifier, status, tsReg, tsExpired, tsDone FROM MMTH_ExtServiceItems WHERE transactionId=? ";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  46 */     return new int[] { -5, 12, 12, 1, -5, -5, Commons.ctx
/*  47 */         .getDatabaseAccess().getType().getBLOBType() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams(ExternalServiceItemVO vo) {
/*  52 */     vo.setTsReg(System.currentTimeMillis());
/*  53 */     return new Object[] { Integer.valueOf(vo.getUserId()), vo.getTransactionId(), vo.getExternalIdentifier(), vo.getStatus().getCode(), Long.valueOf(vo.getTsReg()), Long.valueOf(vo.getTsExpired()), Commons.ctx
/*  54 */         .getDatabaseAccess().getType().seializeBlob(vo) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateParams(ExternalServiceItemVO vo) {
/*  64 */     return new Object[] { vo.getStatus().getCode(), Long.valueOf(System.currentTimeMillis()), Long.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/*  73 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(ExternalServiceItemVO vo) {
/*  83 */     return new Object[] { Integer.valueOf(vo.getUserId()) };
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
/*     */   
/*     */   public static Object[] toSelectPKParam(long id) {
/*  96 */     return new Object[] { Long.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectONEByUserParams(UserVO vo) {
/* 100 */     return new Object[] { Integer.valueOf(vo.getId()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectONEByTIDParams(String tid) {
/* 104 */     return new Object[] { tid };
/*     */   }
/*     */   public static RowMapper<ExternalServiceItemVO> getRowMapper() {
/* 107 */     return (RowMapper<ExternalServiceItemVO>)new ExternalServiceItemRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLExternalServiceItems.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */