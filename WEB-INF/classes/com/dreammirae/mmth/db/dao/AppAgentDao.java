/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLAppAgent;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*     */ import com.dreammirae.mmth.runtime.publish.ReceiveSyncCacheListener;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.bean.AppAgentPolicy;
/*     */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository("appAgentDao")
/*     */ public class AppAgentDao
/*     */   extends BaseDao
/*     */   implements IViewDao<AppAgentVO, Integer>, ReceiveSyncCacheListener
/*     */ {
/*  26 */   private static AppAgentPolicy ALLOWED_APP_AGENTS = null;
/*     */   
/*  28 */   private static final Object mLock = new Object();
/*     */ 
/*     */   
/*     */   public static AppAgentPolicy getAcceptAppAgent() {
/*  32 */     synchronized (mLock) {
/*  33 */       if (ALLOWED_APP_AGENTS == null) {
/*  34 */         (new com.dreammirae.mmth.db.dao.AppAgentDao()).reloadAcceptAppAgent();
/*     */       }
/*     */       
/*  37 */       return ALLOWED_APP_AGENTS;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static AppAgentVO getAcceptableAppAgent(AgentOsTypes osType, String pkgUnique) {
/*  43 */     AppAgentPolicy policy = getAcceptAppAgent();
/*     */     
/*  45 */     if (policy == null || policy.getPolicy() == null || policy.getPolicy().isEmpty()) {
/*  46 */       return null;
/*     */     }
/*     */     
/*  49 */     List<AppAgentVO> polics = policy.getPolicy();
/*     */     
/*  51 */     for (AppAgentVO vo : polics) {
/*  52 */       if (vo.getPkgUnique().equals(pkgUnique) && vo.getOsType().equals(osType)) {
/*  53 */         return vo;
/*     */       }
/*     */     } 
/*     */     
/*  57 */     return null;
/*     */   }
/*     */   
/*     */   public static AppAgentVO getAcceptableAppAgent(int appAgentId) {
/*  61 */     AppAgentPolicy policy = getAcceptAppAgent();
/*     */     
/*  63 */     if (policy == null || policy.getPolicy() == null || policy.getPolicy().isEmpty()) {
/*  64 */       return (new com.dreammirae.mmth.db.dao.AppAgentDao()).getOneByPK(Integer.valueOf(appAgentId));
/*     */     }
/*     */     
/*  67 */     List<AppAgentVO> polics = policy.getPolicy();
/*     */     
/*  69 */     for (AppAgentVO vo : polics) {
/*  70 */       if (vo.getId() == appAgentId) {
/*  71 */         return vo;
/*     */       }
/*     */     } 
/*     */     
/*  75 */     return (new com.dreammirae.mmth.db.dao.AppAgentDao()).getOneByPK(Integer.valueOf(appAgentId));
/*     */   }
/*     */ 
/*     */   
/*     */   private void reloadAcceptAppAgent() {
/*  80 */     List<AppAgentVO> allList = queryForList("SELECT data, id, pkgUnique, osType, alias, disabled, description, tsReg, tsUpdated FROM MMTH_AppAgents  WHERE disabled=?", DMLAppAgent.getAcceptAppAgentParam(), DMLAppAgent.getRowMapper());
/*     */     
/*  82 */     ALLOWED_APP_AGENTS = new AppAgentPolicy();
/*  83 */     ALLOWED_APP_AGENTS.setPolicy(Collections.unmodifiableList(allList));
/*     */   }
/*     */   
/*     */   public static void resetAcceptAppAgnet() {
/*  87 */     synchronized (mLock) {
/*  88 */       ALLOWED_APP_AGENTS = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(AppAgentVO vo) throws InternalDBException {
/*  99 */     if (-1 == vo.getId()) {
/* 100 */       insert(vo);
/*     */     } else {
/* 102 */       update(vo);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void insert(AppAgentVO vo) throws InternalDBException {
/* 107 */     vo.setId(doInsert("INSERT INTO MMTH_AppAgents (pkgUnique, osType, alias, disabled, description, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?)", DMLAppAgent.toInsertParams(vo), DMLAppAgent.getInsertTypes()));
/*     */   }
/*     */   
/*     */   private void update(AppAgentVO vo) throws InternalDBException {
/* 111 */     doUpdate("UPDATE MMTH_AppAgents set alias=?, disabled=?, description=?, tsUpdated=?, data=? WHERE id=?", DMLAppAgent.toUpdateParams(vo), DMLAppAgent.getUpdateTypes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppAgentVO getOneByPK(Integer id) throws InternalDBException {
/* 121 */     return (AppAgentVO)queryForObject("SELECT data, id, pkgUnique, osType, alias, disabled, description, tsReg, tsUpdated FROM MMTH_AppAgents WHERE id=? ", DMLAppAgent.toSelectPKParam(id.intValue()), 
/* 122 */         DMLAppAgent.getRowMapper());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppAgentVO getOneByObj(AppAgentVO vo) throws InternalDBException {
/* 133 */     if (vo == null || StringUtils.isEmpty(vo.getPkgUnique()) || vo.getOsType() == null) {
/* 134 */       return null;
/*     */     }
/*     */     
/* 137 */     return (AppAgentVO)queryForObject("SELECT data, id, pkgUnique, osType, alias, disabled, description, tsReg, tsUpdated FROM MMTH_AppAgents WHERE pkgUnique=? AND osType=? ", DMLAppAgent.toSelectONEParams(vo), 
/* 138 */         DMLAppAgent.getRowMapper());
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
/*     */   public List<AppAgentVO> getViewContent(int limit, int offset, AppAgentVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 150 */     String sql = DMLAppAgent.selectContents(vo, tsFrom, tsTo);
/* 151 */     Object[] args = DMLAppAgent.toSelectParams(vo, tsFrom, tsTo);
/*     */     
/* 153 */     if (args == null || args.length < 1) {
/* 154 */       return queryForListWithLimit(sql, DMLAppAgent.getRowMapper(), limit, offset);
/*     */     }
/* 156 */     return queryForListWithLimit(sql, args, DMLAppAgent.getRowMapper(), limit, offset);
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
/*     */   public int getViewConentCount(AppAgentVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 168 */     String sql = DMLAppAgent.selectContentConunt(vo, tsFrom, tsTo);
/* 169 */     Object[] args = DMLAppAgent.toSelectParams(vo, tsFrom, tsTo);
/* 170 */     return queryForInt(sql, args, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int deleteByPk(Integer id) throws InternalDBException {
/* 181 */     if (id == null || id.intValue() < 1)
/*     */     {
/* 183 */       return -1;
/*     */     }
/*     */     
/* 186 */     return doUpdate("DELETE FROM MMTH_AppAgents WHERE id=?", DMLAppAgent.toDeletePKParam(id.intValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int delete(AppAgentVO vo) throws InternalDBException {
/* 197 */     if (vo == null || StringUtils.isEmpty(vo.getPkgUnique()) || vo.getOsType() == null) {
/* 198 */       return -1;
/*     */     }
/*     */     
/* 201 */     return doUpdate("DELETE FROM MMTH_AppAgents WHERE pkgUnique=? AND osType=?", DMLAppAgent.toDeleteParams(vo));
/*     */   }
/*     */ 
/*     */   
/*     */   public void receivedSyncCacheChanged(SyncCacheTypes type) {
/* 206 */     if (SyncCacheTypes.APP_AGENTS.equals(type))
/* 207 */       resetAcceptAppAgnet(); 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\AppAgentDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */