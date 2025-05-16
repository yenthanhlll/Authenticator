/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLAdministrator;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository("administratorDao")
/*     */ public class AdministratorDao
/*     */   extends BaseDao
/*     */   implements IViewDao<AdministratorVO, Integer>
/*     */ {
/*     */   public void save(AdministratorVO vo) throws InternalDBException {
/*  21 */     if (-1 == vo.getId()) {
/*  22 */       insert(vo);
/*     */     } else {
/*  24 */       update(vo);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void insert(AdministratorVO vo) throws InternalDBException {
/*  29 */     vo.setId(
/*  30 */         doInsert("INSERT INTO MMTH_Administrators (username, password, adminType, disabled, tsLastLogin, lastRemoteAddr, homeUrl, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", DMLAdministrator.toInsertParams(vo), DMLAdministrator.getInsertTypes()));
/*     */   }
/*     */   
/*     */   private void update(AdministratorVO vo) throws InternalDBException {
/*  34 */     doUpdate("UPDATE MMTH_Administrators set password=?, adminType=?, disabled=?, tsLastLogin=?, lastRemoteAddr=?, homeUrl=?, tsUpdated=?, data=? WHERE id=?", DMLAdministrator.toUpdateParams(vo), DMLAdministrator.getUpdateTypes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AdministratorVO getOneByPK(Integer id) throws InternalDBException {
/*  43 */     return (AdministratorVO)queryForObject("SELECT data, id, username, password, adminType, disabled, tsLastLogin, lastRemoteAddr, homeUrl, tsReg, tsUpdated FROM MMTH_Administrators WHERE id=? ", DMLAdministrator.toSelectPKParam(id.intValue()), 
/*  44 */         DMLAdministrator.getRowMapper());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AdministratorVO getOneByObj(AdministratorVO vo) throws InternalDBException {
/*  55 */     if (vo == null || StringUtils.isEmpty(vo.getUsername())) {
/*  56 */       return null;
/*     */     }
/*     */     
/*  59 */     return (AdministratorVO)queryForObject("SELECT data, id, username, password, adminType, disabled, tsLastLogin, lastRemoteAddr, homeUrl, tsReg, tsUpdated FROM MMTH_Administrators WHERE username=? ", DMLAdministrator.toSelectONEParams(vo), 
/*  60 */         DMLAdministrator.getRowMapper());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AdministratorVO> getViewContent(int limit, int offset, AdministratorVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*  68 */     String sql = DMLAdministrator.selectContents(vo, tsFrom, tsTo);
/*  69 */     Object[] args = DMLAdministrator.toSelectParams(vo, tsFrom, tsTo);
/*     */     
/*  71 */     if (args == null || args.length < 1) {
/*  72 */       return queryForListWithLimit(sql, DMLAdministrator.getRowMapper(), limit, offset);
/*     */     }
/*  74 */     return queryForListWithLimit(sql, args, DMLAdministrator.getRowMapper(), limit, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewConentCount(AdministratorVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/*  83 */     String sql = DMLAdministrator.selectContentConunt(vo, tsFrom, tsTo);
/*  84 */     Object[] args = DMLAdministrator.toSelectParams(vo, tsFrom, tsTo);
/*  85 */     return queryForInt(sql, args, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int deleteByPk(Integer id) throws InternalDBException {
/*  95 */     if (id == null || id.intValue() < 1)
/*     */     {
/*  97 */       return -1;
/*     */     }
/*     */     
/* 100 */     return doUpdate("DELETE FROM MMTH_Administrators WHERE id=?", DMLAdministrator.toDeletePKParam(id.intValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int delete(AdministratorVO vo) throws InternalDBException {
/* 109 */     if (vo == null || StringUtils.isEmpty(vo.getUsername())) {
/* 110 */       return -1;
/*     */     }
/*     */     
/* 113 */     return doUpdate("DELETE FROM MMTH_Administrators WHERE username=?", DMLAdministrator.toDeleteParams(vo));
/*     */   }
/*     */   
/*     */   public int getCount() {
/* 117 */     return queryForInt("SELECT COUNT(*) FROM MMTH_Administrators ", null, 0);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\AdministratorDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */