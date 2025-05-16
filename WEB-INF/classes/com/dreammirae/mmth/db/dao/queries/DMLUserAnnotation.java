/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.vo.UserAnnotationVO;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DMLUserAnnotation
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_UserAnnotations (userId, username, displayName, extUnique, customerCode, countryCode, data) VALUES (?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_UserAnnotations WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_UserAnnotations WHERE userId=?";
/*     */   private static final String SELECT_PREFIX = "SELECT data, id, userId, username, displayName, extUnique, customerCode FROM MMTH_UserAnnotations ";
/*     */   public static final String SELECT_ONE_PK = "SELECT data, id, userId, username, displayName, extUnique, customerCode FROM MMTH_UserAnnotations WHERE id=?";
/*     */   public static final String SELECT_ONE_UNIQUE = "SELECT data, id, userId, username, displayName, extUnique, customerCode FROM MMTH_UserAnnotations WHERE userId=?";
/*     */   public static final String SELECT_ONE_REF = "SELECT data, id, userId, username, displayName, extUnique, customerCode FROM MMTH_UserAnnotations WHERE username=?";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  52 */     return new int[] { 4, 12, 12, 12, 1, 1, Commons.ctx
/*  53 */         .getDatabaseAccess().getType().getBLOBType() };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(UserAnnotationVO vo) {
/*  57 */     return new Object[] { Integer.valueOf(vo.getUserId()), vo.getUsername(), vo.getDisplayName(), vo.getExtUnique(), vo.getCustomerCode(), vo.getCountryCode(), Commons.ctx
/*  58 */         .getDatabaseAccess().getType().seializeBlob(vo) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/*  68 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(UserVO vo) {
/*  74 */     return new Object[] { Integer.valueOf(vo.getId()) };
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
/*     */   public static Object[] toSelectPKParam(int id) {
/*  89 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(UserAnnotationVO vo) {
/*  93 */     return new Object[] { Integer.valueOf(vo.getUserId()) };
/*     */   }
/*     */   public static Object[] toSelectRefParams(UserAnnotationVO vo) {
/*  96 */     return new Object[] { vo.getUsername() };
/*     */   }
/*     */   
/*     */   public static RowMapper<UserAnnotationVO> getRowMapper() {
/* 100 */     return (RowMapper<UserAnnotationVO>)new UserAnnotationRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLUserAnnotation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */