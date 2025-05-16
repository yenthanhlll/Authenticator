/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLHwtokenPolicy;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.HwTokenPolicyVO;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.stereotype.Repository;
/*     */ import org.springframework.transaction.support.TransactionCallbackWithoutResult;
/*     */ 
/*     */ @Repository("hwTokenPolicyDao")
/*     */ public class HwTokenPolicyDao
/*     */   extends BaseDao implements IViewDao<HwTokenPolicyVO, String> {
/*  19 */   protected static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.db.dao.HwTokenPolicyDao.class);
/*     */   
/*     */   public void save(HwTokenPolicyVO vo) throws InternalDBException {
/*  22 */     if (getOneByObj(vo) == null) {
/*  23 */       insert(vo);
/*     */     } else {
/*     */       
/*  26 */       update(vo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void insert(HwTokenPolicyVO vo) throws InternalDBException {
/*  32 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
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
/*     */   private void update(HwTokenPolicyVO vo) throws InternalDBException {
/*  44 */     doInTransaction((TransactionCallbackWithoutResult)new Object(this, vo));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int delete(HwTokenPolicyVO vo) throws InternalDBException {
/*  55 */     if (StringUtils.isEmpty(vo.getName())) {
/*  56 */       return -1;
/*     */     }
/*     */     
/*  59 */     return doUpdate("DELETE FROM MMTH_UserDevices WHERE name=?", DMLHwtokenPolicy.toDeletePolicyParam(vo.getName()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int allDelete() throws InternalDBException {
/*  64 */     return doTruncate("TRUNCATE TABLE MMTH_HwTokenPolicy");
/*     */   }
/*     */ 
/*     */   
/*     */   public HwTokenPolicyVO getOneByPK(String id) throws InternalDBException {
/*  69 */     String sql = "SELECT id, name, normal_auth_corr_time_skew, user_corr_time_skew, admin_corr_time_skew, init_auth_time_skew, long_auth_time_skew,long_term,max_auth_fail_cnt, ts_change, ts_create FROM MMTH_HwTokenPolicy WHERE id=? ";
/*  70 */     Object[] args = DMLHwtokenPolicy.toSelectPolicy(id);
/*  71 */     RowMapper<HwTokenPolicyVO> rowMapper = DMLHwtokenPolicy.getRowMapper();
/*     */     
/*  73 */     HwTokenPolicyVO hwtokenPolicy = (HwTokenPolicyVO)queryForObject(sql, args, rowMapper);
/*     */     
/*  75 */     return hwtokenPolicy;
/*     */   }
/*     */ 
/*     */   
/*     */   public HwTokenPolicyVO getOneByObj(HwTokenPolicyVO vo) throws InternalDBException {
/*  80 */     if (StringUtils.isEmpty(vo.getName())) {
/*  81 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     String sql = "SELECT id, name, normal_auth_corr_time_skew, user_corr_time_skew, admin_corr_time_skew, init_auth_time_skew, long_auth_time_skew,long_term,max_auth_fail_cnt, ts_change, ts_create FROM MMTH_HwTokenPolicy WHERE name=? ";
/*  89 */     Object[] args = DMLHwtokenPolicy.toSelectPolicy(vo.getName());
/*  90 */     RowMapper<HwTokenPolicyVO> rowMapper = DMLHwtokenPolicy.getRowMapper();
/*     */     
/*  92 */     HwTokenPolicyVO hwtokenPolicy = (HwTokenPolicyVO)queryForObject(sql, args, rowMapper);
/*     */     
/*  94 */     return hwtokenPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<HwTokenPolicyVO> getViewContent(int limit, int offset, HwTokenPolicyVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewConentCount(HwTokenPolicyVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 107 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int deleteByPk(String name) throws InternalDBException {
/* 113 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\HwTokenPolicyDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */