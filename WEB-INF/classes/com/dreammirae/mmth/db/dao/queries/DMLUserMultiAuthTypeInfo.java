/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.UserMultiAuthTypeVO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DMLUserMultiAuthTypeInfo
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_UserMultiFactorAuthType (userId, value, productType, authType, tsReg) VALUES (?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_UserMultiFactorAuthType SET value=?, productType=?, authType=?, tsUpdated=? WHERE userId=? AND authType=?";
/*     */   public static final String UPDATE_CNT_BYPRODUCT = "UPDATE MMTH_AuthFailCount SET failCnt=? WHERE authTypeId IN (SELECT id FROM MMTH_UserMultiFactorAuthType WHERE userId=? and productType=?)";
/*     */   public static final String TRUNCATE = "TRUNCATE TABLE MMTH_UserMultiFactorAuthType";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_UserMultiFactorAuthType WHERE id=?";
/*     */   public static final String SELECT_PREFIX = "SELECT a.id, userId, value, productType, authType, failCnt, tsUpdated, tsReg FROM MMTH_UserMultiFactorAuthType a LEFT OUTER JOIN MMTH_AuthFailCount b ON a.id = b.authTypeId ";
/*     */   public static final String SELECT_ONE_ID = "SELECT a.id, userId, value, productType, authType, failCnt, tsUpdated, tsReg FROM MMTH_UserMultiFactorAuthType a LEFT OUTER JOIN MMTH_AuthFailCount b ON a.id = b.authTypeId WHERE a.id=? ";
/*     */   public static final String SELECT_ONE_AUTHINFO = "SELECT a.id, userId, value, productType, authType, failCnt, tsUpdated, tsReg FROM MMTH_UserMultiFactorAuthType a LEFT OUTER JOIN MMTH_AuthFailCount b ON a.id = b.authTypeId WHERE userId=? AND authType=?";
/*     */   public static final String SELECT_FAIL_CNT_SUM = "SELECT SUM(a.failCnt) AS failCnt FROM MMTH_AuthFailCount a LEFT OUTER JOIN MMTH_UserMultiFactorAuthType b ON a.authTypeId = b.id ";
/*     */   public static final String SELECT_FAIL_CNT = "SELECT b.id, userId, value, productType, authType, a.failCnt, tsUpdated FROM MMTH_AuthFailCount a LEFT OUTER JOIN MMTH_UserMultiFactorAuthType b ON a.authTypeId = b.id ";
/*     */   public static final String SELECT_ONE_PRODUCT = "SELECT SUM(a.failCnt) AS failCnt FROM MMTH_AuthFailCount a LEFT OUTER JOIN MMTH_UserMultiFactorAuthType b ON a.authTypeId = b.id WHERE b.userId=? AND b.productType=?";
/*     */   public static final String SELECT_LIST_PRODUCT = "SELECT b.id, userId, value, productType, authType, a.failCnt, tsUpdated FROM MMTH_AuthFailCount a LEFT OUTER JOIN MMTH_UserMultiFactorAuthType b ON a.authTypeId = b.id WHERE b.userId=? AND b.productType=?";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  64 */     return new int[] { 4, 12, 4, 4, -5 };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(UserMultiAuthTypeVO vo) {
/*  68 */     vo.setTsReg(System.currentTimeMillis());
/*  69 */     return new Object[] { Integer.valueOf(vo.getUserId()), vo.getAuthData(), Integer.valueOf(vo.getProductType().getCode()), Integer.valueOf(vo.getProductAuthType().getCode()), Long.valueOf(vo.getTsReg()) };
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
/*     */   public static int[] getUpdateTypes() {
/*  82 */     return new int[] { 12, 4, 4, -5, 4, 4 };
/*     */   }
/*     */   public static int[] getUpdateCntByProductTypes() {
/*  85 */     return new int[] { 4, 4, 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(UserMultiAuthTypeVO vo) {
/*  89 */     vo.setTsUpdated(System.currentTimeMillis());
/*  90 */     return new Object[] { vo.getAuthData(), Integer.valueOf(vo.getProductType().getCode()), Integer.valueOf(vo.getProductAuthType().getCode()), Long.valueOf(vo.getTsUpdated()), 
/*  91 */         Integer.valueOf(vo.getUserId()), Integer.valueOf(vo.getProductAuthType().getCode()) };
/*     */   }
/*     */   public static Object[] toUpdateCntByProductParams(UserMultiAuthTypeVO vo) {
/*  94 */     return new Object[] { Integer.valueOf(vo.getFailCnt()), Integer.valueOf(vo.getUserId()), Integer.valueOf(vo.getProductType().getCode()) };
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
/* 110 */     return new Object[] { Integer.valueOf(id) };
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
/*     */   public static Object[] toSelectParams(UserMultiAuthTypeVO vo) {
/* 125 */     if (vo == null)
/*     */     {
/* 127 */       return null;
/*     */     }
/*     */     
/* 130 */     List<Object> params = new ArrayList(5);
/* 131 */     params.add(Integer.valueOf(vo.getUserId()));
/* 132 */     params.add(Integer.valueOf(vo.getProductAuthType().getCode()));
/*     */     
/* 134 */     return params.toArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectByProductParams(UserMultiAuthTypeVO vo) {
/* 139 */     if (vo == null)
/*     */     {
/* 141 */       return null;
/*     */     }
/*     */     
/* 144 */     List<Object> params = new ArrayList(5);
/* 145 */     params.add(Integer.valueOf(vo.getUserId()));
/* 146 */     params.add(Integer.valueOf(vo.getProductType().getCode()));
/*     */     
/* 148 */     return params.toArray();
/*     */   }
/*     */   
/*     */   public static RowMapper<UserMultiAuthTypeVO> getRowMapper() {
/* 152 */     return (RowMapper<UserMultiAuthTypeVO>)new UserMultiAuthTypeRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLUserMultiAuthTypeInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */