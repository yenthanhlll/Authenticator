/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.FidoFacetVO;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
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
/*     */ public final class DMLFidoFacet
/*     */ {
/*     */   public static final String SELECT_TRUSTED_FACETS = "SELECT facetId FROM MMTH_FidoFacets WHERE disabled=?";
/*     */   public static final String INSERT = "INSERT INTO MMTH_FidoFacets (facetId, majorVer, minorVer, alias, disabled, description, tsReg) VALUES (?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_FidoFacets set alias=?, disabled=?, description=?, tsUpdated=? WHERE id=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_FidoFacets WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_FidoFacets WHERE facetId=?";
/*     */   private static final String SELECT_PREFIX = "SELECT id, facetId, majorVer, minorVer, alias, disabled, description, tsReg, tsUpdated FROM MMTH_FidoFacets ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY alias";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_FidoFacets ";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   public static final String SELECT_ONE_PK = "SELECT id, facetId, majorVer, minorVer, alias, disabled, description, tsReg, tsUpdated FROM MMTH_FidoFacets WHERE id=? ";
/*     */   public static final String SELECT_ONE_COND = "SELECT id, facetId, majorVer, minorVer, alias, disabled, description, tsReg, tsUpdated FROM MMTH_FidoFacets WHERE facetId=? ";
/*     */   public static final String SELECT_ALL_LIST = "SELECT id, facetId, majorVer, minorVer, alias, disabled, description, tsReg, tsUpdated FROM MMTH_FidoFacets ";
/*     */   
/*     */   public static Object[] getTrustedFacetParam() {
/*  59 */     return new Object[] { DisabledStatus.ENABLED.getCode() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  70 */     return new int[] { 12, 4, 4, 12, 1, 12, -5 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams(FidoFacetVO vo) {
/*  75 */     String alias = StringUtils.trimUTF8(vo.getAlias(), 128, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*     */     
/*  77 */     String desc = StringUtils.trimUTF8(vo.getDescription(), 1024, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*     */     
/*  79 */     vo.setTsReg(System.currentTimeMillis());
/*  80 */     return new Object[] { vo.getFacetId(), Integer.valueOf(vo.getMajorVer()), Integer.valueOf(vo.getMinorVer()), alias, vo.getDisabled().getCode(), desc, Long.valueOf(vo.getTsReg()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  90 */     return new int[] { 12, 1, 12, -5, 4 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateParams(FidoFacetVO vo) {
/*  95 */     String desc = StringUtils.trimUTF8(vo.getDescription(), 1024, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*  96 */     String alias = StringUtils.trimUTF8(vo.getAlias(), 128, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*     */     
/*  98 */     vo.setTsUpdated(System.currentTimeMillis());
/*  99 */     return new Object[] { alias, vo.getDisabled().getCode(), desc, Long.valueOf(vo.getTsUpdated()), Integer.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/* 108 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(FidoFacetVO vo) {
/* 114 */     return new Object[] { vo.getFacetId() };
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
/*     */   public static Object[] toSelectPKParam(int id) {
/* 132 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectONEParams(FidoFacetVO vo) {
/* 136 */     return new Object[] { vo.getFacetId() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContents(FidoFacetVO vo, Long tsFrom, Long tsTo) {
/* 141 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 144 */     sb.append("SELECT id, facetId, majorVer, minorVer, alias, disabled, description, tsReg, tsUpdated FROM MMTH_FidoFacets ");
/*     */     
/* 146 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 148 */     sb.append(" ORDER BY alias");
/*     */     
/* 150 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentConunt(FidoFacetVO vo, Long tsFrom, Long tsTo) {
/* 155 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 158 */     sb.append("SELECT COUNT(*) FROM MMTH_FidoFacets ");
/*     */     
/* 160 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 162 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, FidoFacetVO vo, Long tsFrom, Long tsTo) {
/* 167 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 171 */     boolean hasParam = false;
/* 172 */     if (vo.getDisabled() != null) {
/* 173 */       sb.append(" WHERE ").append("disabled = ?");
/* 174 */       hasParam = true;
/*     */     } 
/*     */     
/* 177 */     if (!StringUtils.isEmpty(vo.getFacetId())) {
/* 178 */       sb.append(hasParam ? " AND " : " WHERE ").append("facetId like ?");
/* 179 */       hasParam = true;
/*     */     } 
/*     */     
/* 182 */     if (!StringUtils.isEmpty(vo.getAlias())) {
/* 183 */       sb.append(hasParam ? " AND " : " WHERE ").append("alias like ?");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(FidoFacetVO vo, Long tsFrom, Long tsTo) {
/* 189 */     if (vo == null) {
/* 190 */       return null;
/*     */     }
/*     */     
/* 193 */     List<Object> list = new ArrayList(3);
/*     */     
/* 195 */     if (vo.getDisabled() != null) {
/* 196 */       list.add(vo.getDisabled().getCode());
/*     */     }
/*     */     
/* 199 */     if (!StringUtils.isEmpty(vo.getFacetId())) {
/* 200 */       list.add("%" + vo.getFacetId() + "%");
/*     */     }
/*     */     
/* 203 */     if (!StringUtils.isEmpty(vo.getAlias())) {
/* 204 */       list.add("%" + vo.getAlias() + "%");
/*     */     }
/*     */     
/* 207 */     return list.toArray();
/*     */   }
/*     */   
/*     */   public static RowMapper<FidoFacetVO> getRowMapper() {
/* 211 */     return (RowMapper<FidoFacetVO>)new FidoFacetRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLFidoFacet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */