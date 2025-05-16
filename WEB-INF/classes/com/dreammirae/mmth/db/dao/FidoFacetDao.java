/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLFidoFacet;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.fido.transport.facets.Facets;
/*     */ import com.dreammirae.mmth.fido.transport.facets.TrustedFacets;
/*     */ import com.dreammirae.mmth.fido.uaf.Version;
/*     */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*     */ import com.dreammirae.mmth.runtime.publish.ReceiveSyncCacheListener;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.FidoFacetVO;
/*     */ import java.util.List;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository("fidoFacetDao")
/*     */ public class FidoFacetDao
/*     */   extends BaseDao
/*     */   implements IViewDao<FidoFacetVO, Integer>, ReceiveSyncCacheListener
/*     */ {
/*  27 */   private static Facets TRUSTED_FACETS = null;
/*  28 */   private static final Object mLock = new Object();
/*     */ 
/*     */   
/*     */   public static Facets getTrustedFacets() {
/*  32 */     synchronized (mLock) {
/*  33 */       if (TRUSTED_FACETS == null) {
/*  34 */         TRUSTED_FACETS = (new com.dreammirae.mmth.db.dao.FidoFacetDao()).reloadTrustedFacets();
/*     */       }
/*     */       
/*  37 */       return TRUSTED_FACETS;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Facets reloadTrustedFacets() {
/*  44 */     List<String> facetList = queryForList("SELECT facetId FROM MMTH_FidoFacets WHERE disabled=?", DMLFidoFacet.getTrustedFacetParam(), String.class);
/*     */     
/*  46 */     Facets facets = new Facets();
/*  47 */     TrustedFacets[] trustedFacets = new TrustedFacets[1];
/*  48 */     trustedFacets[0] = new TrustedFacets();
/*  49 */     trustedFacets[0].setVersion(Version.getCurrentVersion());
/*  50 */     trustedFacets[0].setIds(facetList.<String>toArray(new String[0]));
/*  51 */     facets.setTrustedFacets(trustedFacets);
/*     */     
/*  53 */     return facets;
/*     */   }
/*     */   
/*     */   public static void resetTrustedFacets() {
/*  57 */     synchronized (mLock) {
/*  58 */       TRUSTED_FACETS = null;
/*     */     } 
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
/*     */   public void save(FidoFacetVO vo) throws InternalDBException {
/*  74 */     if (-1 == vo.getId()) {
/*  75 */       insert(vo);
/*     */     } else {
/*  77 */       update(vo);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void insert(FidoFacetVO vo) throws InternalDBException {
/*  82 */     vo.setId(doInsert("INSERT INTO MMTH_FidoFacets (facetId, majorVer, minorVer, alias, disabled, description, tsReg) VALUES (?, ?, ?, ?, ?, ?, ?)", DMLFidoFacet.toInsertParams(vo), DMLFidoFacet.getInsertTypes()));
/*     */   }
/*     */   
/*     */   private void update(FidoFacetVO vo) throws InternalDBException {
/*  86 */     doUpdate("UPDATE MMTH_FidoFacets set alias=?, disabled=?, description=?, tsUpdated=? WHERE id=?", DMLFidoFacet.toUpdateParams(vo), DMLFidoFacet.getUpdateTypes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FidoFacetVO getOneByPK(Integer id) throws InternalDBException {
/*  96 */     return (FidoFacetVO)queryForObject("SELECT id, facetId, majorVer, minorVer, alias, disabled, description, tsReg, tsUpdated FROM MMTH_FidoFacets WHERE id=? ", DMLFidoFacet.toSelectPKParam(id.intValue()), DMLFidoFacet.getRowMapper());
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
/*     */   public FidoFacetVO getOneByObj(FidoFacetVO vo) throws InternalDBException {
/* 108 */     if (vo == null || StringUtils.isEmpty(vo.getFacetId())) {
/* 109 */       return null;
/*     */     }
/*     */     
/* 112 */     return (FidoFacetVO)queryForObject("SELECT id, facetId, majorVer, minorVer, alias, disabled, description, tsReg, tsUpdated FROM MMTH_FidoFacets WHERE facetId=? ", DMLFidoFacet.toSelectONEParams(vo), DMLFidoFacet.getRowMapper());
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
/*     */   public List<FidoFacetVO> getViewContent(int limit, int offset, FidoFacetVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 125 */     String sql = DMLFidoFacet.selectContents(vo, tsFrom, tsTo);
/* 126 */     Object[] args = DMLFidoFacet.toSelectParams(vo, tsFrom, tsTo);
/*     */     
/* 128 */     if (args == null || args.length < 1) {
/* 129 */       return queryForListWithLimit(sql, DMLFidoFacet.getRowMapper(), limit, offset);
/*     */     }
/* 131 */     return queryForListWithLimit(sql, args, DMLFidoFacet.getRowMapper(), limit, offset);
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
/*     */   public int getViewConentCount(FidoFacetVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 143 */     String sql = DMLFidoFacet.selectContentConunt(vo, tsFrom, tsTo);
/* 144 */     Object[] args = DMLFidoFacet.toSelectParams(vo, tsFrom, tsTo);
/* 145 */     return queryForInt(sql, args, 0);
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
/*     */   public int deleteByPk(Integer id) throws InternalDBException {
/* 157 */     if (id == null || id.intValue() < 1)
/*     */     {
/* 159 */       return -1;
/*     */     }
/*     */     
/* 162 */     return doUpdate("DELETE FROM MMTH_FidoFacets WHERE id=?", DMLFidoFacet.toDeletePKParam(id.intValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int delete(FidoFacetVO vo) throws InternalDBException {
/* 173 */     if (vo == null || StringUtils.isEmpty(vo.getFacetId())) {
/* 174 */       return -1;
/*     */     }
/*     */     
/* 177 */     return doUpdate("DELETE FROM MMTH_FidoFacets WHERE facetId=?", DMLFidoFacet.toDeleteParams(vo));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void receivedSyncCacheChanged(SyncCacheTypes type) {
/* 183 */     if (SyncCacheTypes.TRUSTED_FACETS.equals(type))
/* 184 */       resetTrustedFacets(); 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\FidoFacetDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */