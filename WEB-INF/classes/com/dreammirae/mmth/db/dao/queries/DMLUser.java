/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
/*     */ public class DMLUser
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_Users (username, accountName, disabled, productType, multiLoginYN, status, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_Users set disabled=?, productType=?, multiLoginYN=?, status=?, tsUpdated=?, data=? WHERE id=?";
/*     */   public static final String UPDATE_USERPOLICY = "UPDATE MMTH_Users set multiLoginYN=?, tsUpdated=?, data=? WHERE id=?";
/*     */   public static final String UPDATE_USERAUTHTYPE = "UPDATE MMTH_Users set productType=?, status=?, tsUpdated=?, data=? WHERE id=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_Users WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_Users WHERE username=?";
/*     */   private static final String SELECT_PREFIX = "SELECT data, a.id, a.username, accountName, productType, multiLoginYN, disabled, a.status, a.tsReg, a.tsUpdated, b.tokenId, b.tokentype FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id=b.userid ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY a.username";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id = b.userid ";
/*     */   public static final String SELECT_ONE_PK = "SELECT data, a.id, a.username, accountName, productType, multiLoginYN, disabled, a.status, a.tsReg, a.tsUpdated, b.tokenId, b.tokentype FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id=b.userid WHERE a.id=?";
/*     */   public static final String SELECT_ONE_UNIQUE = "SELECT data, a.id, a.username, accountName, productType, multiLoginYN, disabled, a.status, a.tsReg, a.tsUpdated, b.tokenId, b.tokentype FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id=b.userid WHERE a.username=?";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  60 */     return new int[] { 12, 12, 1, 4, 1, 1, -5, Commons.ctx
/*  61 */         .getDatabaseAccess().getType().getBLOBType() };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(UserVO vo) {
/*  65 */     vo.setTsReg(System.currentTimeMillis());
/*  66 */     return new Object[] { vo.getUsername(), vo.getAccountName(), vo.getDisabled().getCode(), Integer.valueOf(vo.getProductType().getCode()), vo.getMultiLoginYN(), vo.getStatus().getCode(), Long.valueOf(vo.getTsReg()), Commons.ctx
/*  67 */         .getDatabaseAccess().getType().seializeBlob(vo) };
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
/*  80 */     return new int[] { 1, 4, 1, 1, -5, Commons.ctx
/*  81 */         .getDatabaseAccess().getType().getBLOBType(), 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(UserVO vo) {
/*  85 */     vo.setTsUpdated(System.currentTimeMillis());
/*  86 */     return new Object[] { vo.getDisabled().getCode(), Integer.valueOf(vo.getProductType().getCode()), vo.getMultiLoginYN(), vo.getStatus().getCode(), Long.valueOf(vo.getTsUpdated()), Commons.ctx
/*  87 */         .getDatabaseAccess().getType().seializeBlob(vo), Integer.valueOf(vo.getId()) };
/*     */   }
/*     */   
/*     */   public static int[] getUpdateUserPolicyTypes() {
/*  91 */     return new int[] { 1, -5, Commons.ctx.getDatabaseAccess().getType().getBLOBType(), 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateUserPolicyParams(UserVO vo) {
/*  95 */     vo.setTsUpdated(System.currentTimeMillis());
/*  96 */     return new Object[] { vo.getMultiLoginYN(), Long.valueOf(vo.getTsUpdated()), Commons.ctx.getDatabaseAccess().getType().seializeBlob(vo), Integer.valueOf(vo.getId()) };
/*     */   }
/*     */   
/*     */   public static int[] getUpdateUserAuthTypeTypes() {
/* 100 */     return new int[] { 4, 1, -5, Commons.ctx.getDatabaseAccess().getType().getBLOBType(), 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateUserAuthTypeParams(UserVO vo) {
/* 104 */     vo.setTsUpdated(System.currentTimeMillis());
/* 105 */     return new Object[] { Integer.valueOf(vo.getProductType().getCode()), vo.getStatus().getCode(), Long.valueOf(vo.getTsUpdated()), Commons.ctx.getDatabaseAccess().getType().seializeBlob(vo), Integer.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/* 114 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(UserVO vo) {
/* 120 */     return new Object[] { vo.getUsername() };
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
/*     */   public static Object[] toSelectPKParam(int id) {
/* 139 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(String username) {
/* 143 */     return new Object[] { username };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(UserVO vo) {
/* 147 */     return new Object[] { vo.getUsername() };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String selectContents(UserVO vo, Long tsFrom, Long tsTo) {
/* 153 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 156 */     sb.append("SELECT data, a.id, a.username, accountName, productType, multiLoginYN, disabled, a.status, a.tsReg, a.tsUpdated, b.tokenId, b.tokentype FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id=b.userid ");
/*     */     
/* 158 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 160 */     sb.append(" ORDER BY a.username");
/*     */     
/* 162 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentConunt(UserVO vo, Long tsFrom, Long tsTo) {
/* 167 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 170 */     sb.append("SELECT COUNT(*) FROM MMTH_Users a LEFT OUTER JOIN MMTH_HwTokens b ON a.id = b.userid ");
/*     */     
/* 172 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 174 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, UserVO vo, Long tsFrom, Long tsTo) {
/* 179 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 184 */     boolean hasParam = false;
/* 185 */     if (vo.getDisabled() != null) {
/* 186 */       sb.append(hasParam ? " AND " : " WHERE ").append("disabled = ?");
/* 187 */       hasParam = true;
/*     */     } 
/*     */     
/* 190 */     if (vo.getStatus() != null) {
/* 191 */       sb.append(hasParam ? " AND " : " WHERE ").append("a.status = ?");
/* 192 */       hasParam = true;
/*     */     } 
/*     */     
/* 195 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 196 */       sb.append(hasParam ? " AND " : " WHERE ").append("a.username like ?");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(UserVO vo, Long tsFrom, Long tsTo) {
/* 202 */     if (vo == null) {
/* 203 */       return null;
/*     */     }
/*     */     
/* 206 */     List<Object> params = new ArrayList(3);
/* 207 */     if (vo.getDisabled() != null) {
/* 208 */       params.add(vo.getDisabled().getCode());
/*     */     }
/*     */     
/* 211 */     if (vo.getStatus() != null) {
/* 212 */       params.add(vo.getStatus().getCode());
/*     */     }
/*     */     
/* 215 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 216 */       params.add("%" + vo.getUsername() + "%");
/*     */     }
/*     */     
/* 219 */     return params.toArray();
/*     */   }
/*     */   
/*     */   public static RowMapper<UserVO> getRowMapper() {
/* 223 */     return (RowMapper<UserVO>)new UserRowMapper(null);
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
/*     */   public static BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<UserVO> users) {
/* 251 */     return (BatchPreparedStatementSetter)new UserBatchPreparedStatementSetter(users, null);
/*     */   }
/*     */   
/*     */   public static BatchPreparedStatementSetter getBatchPreparedStatementSetterDel(List<UserVO> users) {
/* 255 */     return (BatchPreparedStatementSetter)new UserBatchPreparedStatementSetterDel(users, null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLUser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */