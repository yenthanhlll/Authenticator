/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.types.AdministratorTypes;
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
/*     */ public final class DMLAdministrator
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_Administrators (username, password, adminType, disabled, tsLastLogin, lastRemoteAddr, homeUrl, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_Administrators set password=?, adminType=?, disabled=?, tsLastLogin=?, lastRemoteAddr=?, homeUrl=?, tsUpdated=?, data=? WHERE id=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_Administrators WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_Administrators WHERE username=?";
/*     */   private static final String SELECT_PREFIX = "SELECT data, id, username, password, adminType, disabled, tsLastLogin, lastRemoteAddr, homeUrl, tsReg, tsUpdated FROM MMTH_Administrators ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY username";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_Administrators ";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   public static final String SELECT_ONE_PK = "SELECT data, id, username, password, adminType, disabled, tsLastLogin, lastRemoteAddr, homeUrl, tsReg, tsUpdated FROM MMTH_Administrators WHERE id=? ";
/*     */   public static final String SELECT_ONE_COND = "SELECT data, id, username, password, adminType, disabled, tsLastLogin, lastRemoteAddr, homeUrl, tsReg, tsUpdated FROM MMTH_Administrators WHERE username=? ";
/*     */   public static final String SELECT_ALL_LIST = "SELECT data, id, username, password, adminType, disabled, tsLastLogin, lastRemoteAddr, homeUrl, tsReg, tsUpdated FROM MMTH_Administrators ";
/*     */   public static final String SELECT_COUNT_ALL = "SELECT COUNT(*) FROM MMTH_Administrators ";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  63 */     return new int[] { 12, 12, 1, 1, -5, 12, 12, -5, Commons.ctx
/*  64 */         .getDatabaseAccess().getType().getBLOBType() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toInsertParams(AdministratorVO vo) {
/*  69 */     vo.setTsReg(System.currentTimeMillis());
/*  70 */     return new Object[] { vo.getUsername(), vo.getPassword(), vo.getAdminType().getCode(), vo.getDisabled().getCode(), Long.valueOf(vo.getTsLastLogin()), vo.getLastRemoteAddr(), vo
/*  71 */         .getHomeUrl(), Long.valueOf(vo.getTsReg()), Commons.ctx.getDatabaseAccess().getType().seializeBlob(vo) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  81 */     return new int[] { 12, 1, 1, -5, 12, 12, -5, Commons.ctx.getDatabaseAccess().getType().getBLOBType(), 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(AdministratorVO vo) {
/*  85 */     vo.setTsUpdated(System.currentTimeMillis());
/*  86 */     return new Object[] { vo.getPassword(), vo.getAdminType().getCode(), vo.getDisabled().getCode(), Long.valueOf(vo.getTsLastLogin()), vo.getLastRemoteAddr(), vo.getHomeUrl(), Long.valueOf(vo.getTsUpdated()), Commons.ctx
/*  87 */         .getDatabaseAccess().getType().seializeBlob(vo), Integer.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/*  96 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(AdministratorVO vo) {
/* 102 */     return new Object[] { vo.getUsername() };
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
/*     */   public static Object[] toSelectPKParam(int id) {
/* 122 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectONEParams(AdministratorVO vo) {
/* 126 */     return new Object[] { vo.getUsername() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContents(AdministratorVO vo, Long tsFrom, Long tsTo) {
/* 131 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 134 */     sb.append("SELECT data, id, username, password, adminType, disabled, tsLastLogin, lastRemoteAddr, homeUrl, tsReg, tsUpdated FROM MMTH_Administrators ");
/*     */     
/* 136 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 138 */     sb.append(" ORDER BY username");
/*     */     
/* 140 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentConunt(AdministratorVO vo, Long tsFrom, Long tsTo) {
/* 145 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 148 */     sb.append("SELECT COUNT(*) FROM MMTH_Administrators ");
/*     */     
/* 150 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 152 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, AdministratorVO vo, Long tsFrom, Long tsTo) {
/* 157 */     sb.append(" WHERE ").append("adminType != ?");
/*     */     
/* 159 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 164 */     if (vo.getDisabled() != null) {
/* 165 */       sb.append(" AND ").append("disabled = ?");
/*     */     }
/*     */     
/* 168 */     if (vo.getAdminType() != null) {
/* 169 */       sb.append(" AND ").append("adminType = ?");
/*     */     }
/*     */     
/* 172 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 173 */       sb.append(" AND ").append("username like ?");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(AdministratorVO vo, Long tsFrom, Long tsTo) {
/* 179 */     List<Object> params = new ArrayList(4);
/* 180 */     params.add(AdministratorTypes.DEV.getCode());
/*     */     
/* 182 */     if (vo == null) {
/* 183 */       return params.toArray();
/*     */     }
/*     */     
/* 186 */     if (vo.getDisabled() != null) {
/* 187 */       params.add(vo.getDisabled().getCode());
/*     */     }
/*     */     
/* 190 */     if (vo.getAdminType() != null) {
/* 191 */       params.add(vo.getAdminType().getCode());
/*     */     }
/*     */     
/* 194 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 195 */       params.add(vo.getUsername() + "%");
/*     */     }
/*     */     
/* 198 */     return params.toArray();
/*     */   }
/*     */   
/*     */   public static RowMapper<AdministratorVO> getRowMapper() {
/* 202 */     return (RowMapper<AdministratorVO>)new AdministratorRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLAdministrator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */