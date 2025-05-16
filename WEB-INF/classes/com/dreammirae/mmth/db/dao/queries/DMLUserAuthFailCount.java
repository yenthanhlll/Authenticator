/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.UserAuthFailCountVO;
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
/*     */ public final class DMLUserAuthFailCount
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_AuthFailCount (authTypeId, failCnt, tsLastAuthFail) VALUES (?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_AuthFailCount SET failCnt=?, tsLastAuthFail=? WHERE authTypeId=?";
/*     */   public static final String UPDATE_FAILCNT = "UPDATE MMTH_AuthFailCount SET failCnt=? WHERE authTypeId=?";
/*     */   public static final String TRUNCATE = "TRUNCATE TABLE MMTH_AuthFailCount";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_AuthFailCount WHERE authTypeId=?";
/*     */   private static final String SELECT_PREFIX = "SELECT authTypeId, failCnt, tsLastAuthFail FROM MMTH_AuthFailCount ";
/*     */   public static final String SELECT_ONE_PK = "SELECT authTypeId, failCnt, tsLastAuthFail FROM MMTH_AuthFailCount WHERE authTypeId=? ";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  51 */     return new int[] { 4, 4, -5 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams(UserAuthFailCountVO vo) {
/*  56 */     return new Object[] { Integer.valueOf(vo.getAuthTypeId()), Integer.valueOf(vo.getFailCnt()), Long.valueOf(vo.getTsLastAuthFail()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  67 */     return new int[] { 4, -5, 4 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateParams(UserAuthFailCountVO vo) {
/*  72 */     return new Object[] { Integer.valueOf(vo.getFailCnt()), Long.valueOf(vo.getTsLastAuthFail()), Integer.valueOf(vo.getAuthTypeId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateFailCntTypes() {
/*  78 */     return new int[] { 4, 4 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateFailCntParams(UserAuthFailCountVO vo) {
/*  83 */     return new Object[] { Integer.valueOf(vo.getFailCnt()), Integer.valueOf(vo.getAuthTypeId()) };
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/*  99 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toSelectPKParam(Integer id) {
/* 109 */     return new Object[] { id };
/*     */   }
/*     */   
/*     */   public static RowMapper<UserAuthFailCountVO> getRowMapper() {
/* 113 */     return (RowMapper<UserAuthFailCountVO>)new UserAuthFailCountRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLUserAuthFailCount.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */