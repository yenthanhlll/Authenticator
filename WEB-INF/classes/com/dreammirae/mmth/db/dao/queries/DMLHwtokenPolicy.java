/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.HwTokenPolicyVO;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DMLHwtokenPolicy
/*     */ {
/*     */   public static final String INSERT_HW_OTP_POLICY = "INSERT INTO MMTH_HwTokenPolicy (name, normal_auth_corr_time_skew, user_corr_time_skew, admin_corr_time_skew, init_auth_time_skew, long_auth_time_skew, long_term, max_auth_fail_cnt, ts_change, ts_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE_HW_OTP_POLICY = "UPDATE MMTH_HwTokenPolicy SET normal_auth_corr_time_skew=?, user_corr_time_skew=?, admin_corr_time_skew=?, init_auth_time_skew=?, long_auth_time_skew=?, long_term=?, max_auth_fail_cnt=?, ts_change=? WHERE name=?";
/*     */   public static final String TRUNCATE = "TRUNCATE TABLE MMTH_HwTokenPolicy";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_UserDevices WHERE id=?";
/*     */   public static final String DELETE_BY_NAME = "DELETE FROM MMTH_UserDevices WHERE name=?";
/*     */   private static final String SELECT_PREFIX = "SELECT id, name, normal_auth_corr_time_skew, user_corr_time_skew, admin_corr_time_skew, init_auth_time_skew, long_auth_time_skew,long_term,max_auth_fail_cnt, ts_change, ts_create FROM MMTH_HwTokenPolicy ";
/*     */   public static final String SELECT_ONE_ID = "SELECT id, name, normal_auth_corr_time_skew, user_corr_time_skew, admin_corr_time_skew, init_auth_time_skew, long_auth_time_skew,long_term,max_auth_fail_cnt, ts_change, ts_create FROM MMTH_HwTokenPolicy WHERE id=? ";
/*     */   public static final String SELECT_ONE_NAME = "SELECT id, name, normal_auth_corr_time_skew, user_corr_time_skew, admin_corr_time_skew, init_auth_time_skew, long_auth_time_skew,long_term,max_auth_fail_cnt, ts_change, ts_create FROM MMTH_HwTokenPolicy WHERE name=? ";
/*     */   public static final String SELECT_ALL_LIST = "SELECT id, name, normal_auth_corr_time_skew, user_corr_time_skew, admin_corr_time_skew, init_auth_time_skew, long_auth_time_skew,long_term,max_auth_fail_cnt, ts_change, ts_create FROM MMTH_HwTokenPolicy ";
/*     */   
/*     */   public static int[] getInsertTypesHwOtp() {
/*  69 */     return new int[] { 12, 4, 4, 4, 4, 4, 4, 4, -5, -5 };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParamsHwOtp(HwTokenPolicyVO vo) {
/*  73 */     vo.setCrtDate(System.currentTimeMillis());
/*  74 */     return new Object[] { vo.getName(), vo.getNormAuthTmSkew(), vo.getUserSyncTmSkew(), vo.getAdminSyncTmSkew(), vo.getInitAuthTmSkew(), vo.getLongAuthTmSkew(), vo.getLongTerm(), vo.getMaxAuthFailCnt(), Long.valueOf(vo.getChgDate()), Long.valueOf(vo.getCrtDate()) };
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
/*  85 */     return new int[] { 4, 4, 4, 4, 4, 4, 4, -5, 12 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(HwTokenPolicyVO vo) {
/*  89 */     vo.setChgDate(System.currentTimeMillis());
/*  90 */     return new Object[] { vo.getNormAuthTmSkew(), vo.getUserSyncTmSkew(), vo.getAdminSyncTmSkew(), vo.getInitAuthTmSkew(), vo.getLongAuthTmSkew(), vo.getLongTerm(), vo.getMaxAuthFailCnt(), Long.valueOf(vo.getChgDate()), vo.getName() };
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
/* 106 */     return new Object[] { Integer.valueOf(id) };
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
/*     */   public static Object[] toSelectParams(HwTokenPolicyVO vo, Long tsFrom, Long tsTo) {
/* 121 */     if (vo == null)
/*     */     {
/* 123 */       return null;
/*     */     }
/*     */     
/* 126 */     List<Object> params = new ArrayList(5);
/*     */     
/* 128 */     if (vo.getName() != null) {
/* 129 */       params.add(vo.getName());
/*     */     }
/*     */     
/* 132 */     return params.toArray();
/*     */   }
/*     */   
/*     */   public static Object[] toSelectPolicy(String name) {
/* 136 */     return new Object[] { name };
/*     */   }
/*     */   
/*     */   public static RowMapper<HwTokenPolicyVO> getRowMapper() {
/* 140 */     return (RowMapper<HwTokenPolicyVO>)new TokenPolicyRowMapper(null);
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
/*     */   public static Object[] toDeletePolicyParam(String name) {
/* 168 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLHwtokenPolicy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */