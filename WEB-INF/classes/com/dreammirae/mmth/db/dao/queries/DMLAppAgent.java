/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
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
/*     */ 
/*     */ 
/*     */ public final class DMLAppAgent
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_AppAgents (pkgUnique, osType, alias, disabled, description, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_AppAgents set alias=?, disabled=?, description=?, tsUpdated=?, data=? WHERE id=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_AppAgents WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_AppAgents WHERE pkgUnique=? AND osType=?";
/*     */   private static final String SELECT_PREFIX = "SELECT data, id, pkgUnique, osType, alias, disabled, description, tsReg, tsUpdated FROM MMTH_AppAgents ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY alias";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_AppAgents ";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   public static final String SELECT_ONE_PK = "SELECT data, id, pkgUnique, osType, alias, disabled, description, tsReg, tsUpdated FROM MMTH_AppAgents WHERE id=? ";
/*     */   public static final String SELECT_ONE_COND = "SELECT data, id, pkgUnique, osType, alias, disabled, description, tsReg, tsUpdated FROM MMTH_AppAgents WHERE pkgUnique=? AND osType=? ";
/*     */   public static final String SELECT_ALL_LIST = "SELECT data, id, pkgUnique, osType, alias, disabled, description, tsReg, tsUpdated FROM MMTH_AppAgents ";
/*     */   public static final String SELECT_ACCEPT_APP_AGENTS = "SELECT data, id, pkgUnique, osType, alias, disabled, description, tsReg, tsUpdated FROM MMTH_AppAgents  WHERE disabled=?";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  62 */     return new int[] { 12, 1, 12, 1, 12, -5, Commons.ctx.getDatabaseAccess().getType().getBLOBType() };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(AppAgentVO vo) {
/*  66 */     String alias = StringUtils.trimUTF8(vo.getAlias(), 128, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*  67 */     String desc = StringUtils.trimUTF8(vo.getDescription(), 1024, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*  68 */     vo.setTsReg(System.currentTimeMillis());
/*  69 */     return new Object[] { vo.getPkgUnique(), vo.getOsType().getCode(), alias, vo.getDisabled().getCode(), desc, Long.valueOf(vo.getTsReg()), Commons.ctx.getDatabaseAccess().getType().seializeBlob(vo) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  79 */     return new int[] { 12, 1, 12, -5, Commons.ctx.getDatabaseAccess().getType().getBLOBType(), 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(AppAgentVO vo) {
/*  83 */     String alias = StringUtils.trimUTF8(vo.getAlias(), 128, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*  84 */     String desc = StringUtils.trimUTF8(vo.getDescription(), 1024, Commons.ctx.getDatabaseAccess().useSafeUtf8());
/*  85 */     vo.setTsUpdated(System.currentTimeMillis());
/*  86 */     return new Object[] { alias, vo.getDisabled().getCode(), desc, Long.valueOf(vo.getTsUpdated()), Commons.ctx.getDatabaseAccess().getType().seializeBlob(vo), Integer.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/*  95 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(AppAgentVO vo) {
/* 101 */     return new Object[] { vo.getPkgUnique(), vo.getOsType().getCode() };
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
/* 119 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectONEParams(AppAgentVO vo) {
/* 123 */     return new Object[] { vo.getPkgUnique(), vo.getOsType().getCode() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContents(AppAgentVO vo, Long tsFrom, Long tsTo) {
/* 128 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 131 */     sb.append("SELECT data, id, pkgUnique, osType, alias, disabled, description, tsReg, tsUpdated FROM MMTH_AppAgents ");
/*     */     
/* 133 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 135 */     sb.append(" ORDER BY alias");
/*     */     
/* 137 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentConunt(AppAgentVO vo, Long tsFrom, Long tsTo) {
/* 142 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 145 */     sb.append("SELECT COUNT(*) FROM MMTH_AppAgents ");
/*     */     
/* 147 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 149 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, AppAgentVO vo, Long tsFrom, Long tsTo) {
/* 154 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 158 */     boolean hasParam = false;
/*     */     
/* 160 */     if (vo.getOsType() != null) {
/* 161 */       sb.append(" WHERE ").append("osType = ?");
/* 162 */       hasParam = true;
/*     */     } 
/*     */     
/* 165 */     if (vo.getDisabled() != null) {
/* 166 */       sb.append(hasParam ? " AND " : " WHERE ").append("disabled = ?");
/* 167 */       hasParam = true;
/*     */     } 
/*     */     
/* 170 */     if (!StringUtils.isEmpty(vo.getPkgUnique())) {
/* 171 */       sb.append(hasParam ? " AND " : " WHERE ").append("pkgUnique like ?");
/*     */     }
/*     */     
/* 174 */     if (!StringUtils.isEmpty(vo.getAlias())) {
/* 175 */       sb.append(hasParam ? " AND " : " WHERE ").append("alias like ?");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(AppAgentVO vo, Long tsFrom, Long tsTo) {
/* 181 */     if (vo == null) {
/* 182 */       return null;
/*     */     }
/*     */     
/* 185 */     List<Object> params = new ArrayList(5);
/*     */     
/* 187 */     if (vo.getOsType() != null) {
/* 188 */       params.add(vo.getOsType().getCode());
/*     */     }
/*     */     
/* 191 */     if (vo.getDisabled() != null) {
/* 192 */       params.add(vo.getDisabled().getCode());
/*     */     }
/*     */     
/* 195 */     if (!StringUtils.isEmpty(vo.getPkgUnique())) {
/* 196 */       params.add("%" + vo.getPkgUnique() + "%");
/*     */     }
/*     */     
/* 199 */     if (!StringUtils.isEmpty(vo.getAlias())) {
/* 200 */       String alias = vo.getAlias();
/*     */       
/* 202 */       if (Commons.ctx.getDatabaseAccess().useSafeUtf8()) {
/* 203 */         alias = Base64Utils.encodeUrl(alias);
/*     */       }
/* 205 */       params.add(alias + "%");
/*     */     } 
/* 207 */     return params.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] getAcceptAppAgentParam() {
/* 217 */     return new Object[] { DisabledStatus.ENABLED.getCode() };
/*     */   }
/*     */   
/*     */   public static RowMapper<AppAgentVO> getRowMapper() {
/* 221 */     return (RowMapper<AppAgentVO>)new AppAgentRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLAppAgent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */