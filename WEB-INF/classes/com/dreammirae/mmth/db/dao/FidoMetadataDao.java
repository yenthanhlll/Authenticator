/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*     */ 
/*     */ import com.dreammirae.mmth.db.dao.BaseDao;
/*     */ import com.dreammirae.mmth.db.dao.IViewDao;
/*     */ import com.dreammirae.mmth.db.dao.queries.DMLFidoMetadata;
/*     */ import com.dreammirae.mmth.exception.InternalDBException;
/*     */ import com.dreammirae.mmth.fido.metadata.MetadataStatement;
/*     */ import com.dreammirae.mmth.fido.registry.UserVerificationMethods;
/*     */ import com.dreammirae.mmth.fido.uaf.MatchCriteria;
/*     */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*     */ import com.dreammirae.mmth.runtime.publish.ReceiveSyncCacheListener;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.FidoMetadataVO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.stereotype.Repository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Repository("fidoMetadataDao")
/*     */ public class FidoMetadataDao
/*     */   extends BaseDao
/*     */   implements IViewDao<FidoMetadataVO, Integer>, ReceiveSyncCacheListener
/*     */ {
/*  29 */   private static Map<String, MetadataStatement> METADATA_STMT = null;
/*  30 */   private static Map<String, MatchCriteria> METADATA_MATCH_CRITERIA = null;
/*  31 */   private static MatchCriteria[][] DEFAULT_POLICY_ALLOWED_MC = (MatchCriteria[][])null;
/*  32 */   private static Map<UserVerificationMethods, MatchCriteria[][]> ALLOWED_POLICY_BY_USERVERIFICATION = null;
/*     */   
/*  34 */   private static final Object mLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, MetadataStatement> getMetadataStatements() {
/*  41 */     synchronized (mLock) {
/*  42 */       if (METADATA_STMT == null) {
/*  43 */         (new com.dreammirae.mmth.db.dao.FidoMetadataDao()).reloadAuthenticatorPolicy();
/*     */       }
/*     */       
/*  46 */       return METADATA_STMT;
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
/*     */   public static MetadataStatement getMetadataStatmement(String aaid) {
/*  58 */     if (StringUtils.isEmpty(aaid)) {
/*  59 */       return null;
/*     */     }
/*     */     
/*  62 */     Map<String, MetadataStatement> map = getMetadataStatements();
/*     */     
/*  64 */     if (map == null) {
/*  65 */       return null;
/*     */     }
/*     */     
/*  68 */     return map.get(aaid.toUpperCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MatchCriteria[][] getAcceptPolicy() {
/*  76 */     synchronized (mLock) {
/*     */       
/*  78 */       if (METADATA_STMT == null || DEFAULT_POLICY_ALLOWED_MC == null) {
/*  79 */         (new com.dreammirae.mmth.db.dao.FidoMetadataDao()).reloadAuthenticatorPolicy();
/*     */       }
/*     */       
/*  82 */       return DEFAULT_POLICY_ALLOWED_MC;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MatchCriteria[][] getAcceptPolicy(String aaid) {
/*  92 */     synchronized (mLock) {
/*     */       
/*  94 */       if (METADATA_STMT == null || METADATA_MATCH_CRITERIA == null) {
/*  95 */         (new com.dreammirae.mmth.db.dao.FidoMetadataDao()).reloadAuthenticatorPolicy();
/*     */       }
/*     */       
/*  98 */       if (METADATA_MATCH_CRITERIA == null) {
/*  99 */         return (MatchCriteria[][])null;
/*     */       }
/* 101 */       MatchCriteria mc = METADATA_MATCH_CRITERIA.get(aaid);
/*     */       
/* 103 */       if (mc == null) {
/* 104 */         return (MatchCriteria[][])null;
/*     */       }
/*     */       
/* 107 */       MatchCriteria[][] accepted = new MatchCriteria[1][1];
/* 108 */       accepted[0] = new MatchCriteria[1];
/* 109 */       accepted[0][0] = mc;
/*     */       
/* 111 */       return accepted;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MatchCriteria[][] getAcceptPolicy(UserVerificationMethods userVerification) {
/* 121 */     synchronized (mLock) {
/*     */       
/* 123 */       if (METADATA_STMT == null || ALLOWED_POLICY_BY_USERVERIFICATION == null) {
/* 124 */         (new com.dreammirae.mmth.db.dao.FidoMetadataDao()).reloadAuthenticatorPolicy();
/*     */       }
/*     */       
/* 127 */       if (ALLOWED_POLICY_BY_USERVERIFICATION == null) {
/* 128 */         return (MatchCriteria[][])null;
/*     */       }
/* 130 */       return ALLOWED_POLICY_BY_USERVERIFICATION.get(userVerification);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void reloadAuthenticatorPolicy() {
/* 136 */     List<FidoMetadataVO> allList = queryForList("SELECT data, id, aaid, alias, disabled, userVerification, description, tsReg, tsUpdated FROM MMTH_FidoMetadata WHERE disabled=?", DMLFidoMetadata.getAllowedParams(), DMLFidoMetadata.getRowMapper());
/*     */     
/* 138 */     if (allList.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 142 */     int len = allList.size();
/*     */     
/* 144 */     Map<String, MetadataStatement> allData = new HashMap<>(len);
/* 145 */     Map<String, MatchCriteria> aaidMc = new HashMap<>(len);
/*     */     
/* 147 */     Map<UserVerificationMethods, List<MatchCriteria>> mcByMethods = new HashMap<>(len);
/*     */     
/* 149 */     MatchCriteria[][] accepted = new MatchCriteria[len][1];
/*     */     
/* 151 */     for (int i = 0; i < len; i++) {
/*     */       
/* 153 */       FidoMetadataVO vo = allList.get(i);
/* 154 */       String aaid = vo.getAaid().toUpperCase();
/* 155 */       MetadataStatement ms = vo.getMetadata();
/*     */ 
/*     */       
/* 158 */       allData.put(aaid, ms);
/*     */ 
/*     */       
/* 161 */       MatchCriteria mc = new MatchCriteria();
/* 162 */       mc.setAaid(new String[] { aaid });
/* 163 */       aaidMc.put(aaid, mc);
/*     */ 
/*     */       
/* 166 */       MatchCriteria[] row = new MatchCriteria[1];
/* 167 */       row[0] = mc;
/* 168 */       accepted[i] = row;
/*     */ 
/*     */       
/* 171 */       List<MatchCriteria> mcs = mcByMethods.get(vo.getUserVerification());
/*     */       
/* 173 */       if (mcs == null) {
/* 174 */         mcs = new ArrayList<>();
/* 175 */         mcByMethods.put(vo.getUserVerification(), mcs);
/*     */       } 
/*     */       
/* 178 */       mcs.add(mc);
/*     */     } 
/*     */ 
/*     */     
/* 182 */     Map<UserVerificationMethods, MatchCriteria[][]> methodPolicy = (Map)new HashMap<>(mcByMethods.size());
/*     */     
/* 184 */     for (Map.Entry<UserVerificationMethods, List<MatchCriteria>> entry : mcByMethods.entrySet()) {
/*     */       
/* 186 */       List<MatchCriteria> list = entry.getValue();
/* 187 */       int size = list.size();
/*     */       
/* 189 */       MatchCriteria[][] accPolicy = new MatchCriteria[size][1];
/*     */       
/* 191 */       for (int j = 0; j < size; j++) {
/* 192 */         MatchCriteria[] row = new MatchCriteria[1];
/* 193 */         row[0] = list.get(j);
/* 194 */         accPolicy[j] = row;
/*     */       } 
/*     */       
/* 197 */       methodPolicy.put(entry.getKey(), accPolicy);
/*     */     } 
/*     */     
/* 200 */     DEFAULT_POLICY_ALLOWED_MC = accepted;
/* 201 */     ALLOWED_POLICY_BY_USERVERIFICATION = Collections.unmodifiableMap((Map)methodPolicy);
/* 202 */     METADATA_MATCH_CRITERIA = Collections.unmodifiableMap(aaidMc);
/* 203 */     METADATA_STMT = Collections.unmodifiableMap(allData);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resetAuthenticatorPolicy() {
/* 208 */     synchronized (mLock) {
/* 209 */       METADATA_STMT = null;
/* 210 */       METADATA_MATCH_CRITERIA = null;
/* 211 */       ALLOWED_POLICY_BY_USERVERIFICATION = null;
/* 212 */       DEFAULT_POLICY_ALLOWED_MC = (MatchCriteria[][])null;
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
/*     */   public void save(FidoMetadataVO vo) throws InternalDBException {
/* 228 */     if (-1 == vo.getId()) {
/* 229 */       insert(vo);
/*     */     } else {
/* 231 */       update(vo);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void insert(FidoMetadataVO vo) throws InternalDBException {
/* 236 */     vo.setId(doInsert("INSERT INTO MMTH_FidoMetadata (aaid, alias, disabled, userVerification, description, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?)", DMLFidoMetadata.toInsertParams(vo), DMLFidoMetadata.getInsertTypes()));
/*     */   }
/*     */   
/*     */   private void update(FidoMetadataVO vo) throws InternalDBException {
/* 240 */     doUpdate("UPDATE MMTH_FidoMetadata set alias=?, disabled=?, userVerification=?, description=?, tsUpdated=? WHERE id=?", DMLFidoMetadata.toUpdateParams(vo), DMLFidoMetadata.getUpdateTypes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FidoMetadataVO getOneByPK(Integer id) throws InternalDBException {
/* 250 */     return (FidoMetadataVO)queryForObject("SELECT data, id, aaid, alias, disabled, userVerification, description, tsReg, tsUpdated FROM MMTH_FidoMetadata WHERE id=? ", DMLFidoMetadata.toSelectPKParam(id.intValue()), DMLFidoMetadata.getRowMapper());
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
/*     */   public FidoMetadataVO getOneByObj(FidoMetadataVO vo) throws InternalDBException {
/* 262 */     if (vo == null || StringUtils.isEmpty(vo.getAaid())) {
/* 263 */       return null;
/*     */     }
/*     */     
/* 266 */     return (FidoMetadataVO)queryForObject("SELECT data, id, aaid, alias, disabled, userVerification, description, tsReg, tsUpdated FROM MMTH_FidoMetadata WHERE aaid=? ", DMLFidoMetadata.toSelectONEParams(vo), DMLFidoMetadata.getRowMapper());
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
/*     */   public List<FidoMetadataVO> getViewContent(int limit, int offset, FidoMetadataVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 279 */     String sql = DMLFidoMetadata.selectContents(vo, tsFrom, tsTo);
/* 280 */     Object[] args = DMLFidoMetadata.toSelectParams(vo, tsFrom, tsTo);
/*     */     
/* 282 */     if (args == null || args.length < 1) {
/* 283 */       return queryForListWithLimit(sql, DMLFidoMetadata.getRowMapper(), limit, offset);
/*     */     }
/* 285 */     return queryForListWithLimit(sql, args, DMLFidoMetadata.getRowMapper(), limit, offset);
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
/*     */   public int getViewConentCount(FidoMetadataVO vo, Long tsFrom, Long tsTo) throws InternalDBException {
/* 297 */     String sql = DMLFidoMetadata.selectContentConunt(vo, tsFrom, tsTo);
/* 298 */     Object[] args = DMLFidoMetadata.toSelectParams(vo, tsFrom, tsTo);
/* 299 */     return queryForInt(sql, args, 0);
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
/* 311 */     if (id == null || id.intValue() < 1)
/*     */     {
/* 313 */       return -1;
/*     */     }
/*     */     
/* 316 */     return doUpdate("DELETE FROM MMTH_FidoMetadata WHERE id=?", DMLFidoMetadata.toDeletePKParam(id.intValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int delete(FidoMetadataVO vo) throws InternalDBException {
/* 327 */     if (vo == null || StringUtils.isEmpty(vo.getAaid())) {
/* 328 */       return -1;
/*     */     }
/*     */     
/* 331 */     return doUpdate("DELETE FROM MMTH_FidoMetadata WHERE aaid=?", DMLFidoMetadata.toDeleteParams(vo));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void receivedSyncCacheChanged(SyncCacheTypes type) {
/* 337 */     if (SyncCacheTypes.FIDO_METADATA.equals(type))
/* 338 */       resetAuthenticatorPolicy(); 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\FidoMetadataDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */