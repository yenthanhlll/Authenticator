/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.HwTokenVO;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
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
/*     */ public final class DMLHwtoken
/*     */ {
/*     */   public static final String INSERT_HW_OTP = "INSERT INTO MMTH_HwTokens (tokenType, tokenId, tokenKey, status, tsReg) VALUES (?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE_UNLOCKCNT = "UPDATE MMTH_HwTokens set unlockCnt=? WHERE tokenId=?";
/*     */   public static final String UPDATE_HW_OTP = "UPDATE MMTH_HwTokens set userId=?, username=?, branchId=?, commend=?, tokenKey=?, status=?, unlockCnt=? WHERE tokenId=?";
/*     */   public static final String UPDATE_LOST = "UPDATE MMTH_HwTokens set lost=?, tsLost=? WHERE tokenId=?";
/*     */   public static final String UPDATE_TOKEN_DISCARD = "UPDATE MMTH_HwTokens set userId=?, username=?, status=?, tsDiscard=? WHERE tokenId=?";
/*     */   public static final String UPDATE_TOKEN_OCCUPIED = "UPDATE MMTH_HwTokens set userId=?, username=?, branchId=?, commend=?, status=?, tsOccupied=? WHERE tokenId=?";
/*     */   public static final String TRUNCATE = "TRUNCATE TABLE MMTH_HwTokens";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_HwTokens WHERE tokenId=?";
/*     */   public static final String DELETE_BY_USER = "DELETE FROM MMTH_HwTokens WHERE username=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_HwTokens WHERE tokenId=?";
/*     */   private static final String SELECT_PREFIX = "SELECT userId, username, branchId, commend, tokenType, tokenId, tokenKey, status, lost, tsLost, unlockCnt, tsReg, tsOccupied, tsSuspend, tsDiscard  FROM MMTH_HwTokens ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY tokenId";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_HwTokens ";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   public static final String SELECT_ONE_PK = "SELECT userId, username, branchId, commend, tokenType, tokenId, tokenKey, status, lost, tsLost, unlockCnt, tsReg, tsOccupied, tsSuspend, tsDiscard  FROM MMTH_HwTokens WHERE tokenId=? ";
/*     */   public static final String SELECT_ONE_COND = "SELECT userId, username, branchId, commend, tokenType, tokenId, tokenKey, status, lost, tsLost, unlockCnt, tsReg, tsOccupied, tsSuspend, tsDiscard  FROM MMTH_HwTokens WHERE tokenId=? ";
/*     */   public static final String SELECT_ALL_LIST = "SELECT userId, username, branchId, commend, tokenType, tokenId, tokenKey, status, lost, tsLost, unlockCnt, tsReg, tsOccupied, tsSuspend, tsDiscard  FROM MMTH_HwTokens ";
/*     */   public static final String SELECT_ONE_UNIQUE = "SELECT userId, username, branchId, commend, tokenType, tokenId, tokenKey, status, lost, tsLost, unlockCnt, tsReg, tsOccupied, tsSuspend, tsDiscard  FROM MMTH_HwTokens WHERE tokenId=?";
/*     */   public static final String SELECT_ONE_USER = "SELECT userId, username, branchId, commend, tokenType, tokenId, tokenKey, status, lost, tsLost, unlockCnt, tsReg, tsOccupied, tsSuspend, tsDiscard  FROM MMTH_HwTokens WHERE userId=?";
/*     */   public static final String SELECT_AVAILABLE_TOKEN_CNT = "SELECT count(*) FROM MMTH_HwTokens WHERE tokenId=?";
/*     */   public static final String SELECT_TOKEN_BY_USER_CNT = "SELECT count(*) FROM MMTH_HwTokens WHERE tokenId=? AND username=? AND tokenId=? AND tokenKey=?";
/*     */   
/*     */   public static int[] getInsertTypesHwOtp() {
/*  69 */     return new int[] { 1, 12, 12, 1, -5 };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParamsHwOtp(HwTokenVO vo) {
/*  73 */     vo.setTsReg(System.currentTimeMillis());
/*  74 */     return new Object[] { vo.getType(), vo.getTokenId(), vo.getTokenKey(), vo.getStatus().getCode(), Long.valueOf(vo.getTsReg()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateUnLockCntTypes() {
/*  84 */     return new int[] { 4, 12 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateUnLockCntParams(HwTokenVO vo) {
/*  88 */     return new Object[] { Integer.valueOf(vo.getUnlockCnt()), vo.getTokenId() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  98 */     return new int[] { 4, 12, 12, 12, 12, 1, 4, 12 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateParams(HwTokenVO vo) {
/* 103 */     return new Object[] { Integer.valueOf(vo.getUserId()), vo.getUsername(), vo.getBranchId(), vo.getComment(), vo.getTokenKey(), vo.getStatus().getCode(), Integer.valueOf(vo.getUnlockCnt()), vo.getTokenId() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateLostTypes() {
/* 114 */     return new int[] { 1, -5, 12 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateLostParams(HwTokenVO vo) {
/* 119 */     return new Object[] { vo.getLost().getCode(), Long.valueOf(vo.getTsLost()), vo.getTokenId() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateDiscardParams(int userId, String user, String tokenId) {
/* 127 */     return new Object[] { Integer.valueOf(userId), user, TokenStatus.DISCARD.getCode(), Long.valueOf(System.currentTimeMillis()), tokenId };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateOccupiedParams(int userId, String user, String branchId, String comment, String tokenId) {
/* 133 */     return new Object[] { Integer.valueOf(userId), user, branchId, comment, TokenStatus.OCCUPIED.getCode(), Long.valueOf(System.currentTimeMillis()), tokenId };
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
/*     */   public static Object[] toDeletePKParam(String tokenId) {
/* 157 */     return new Object[] { tokenId };
/*     */   }
/*     */   
/*     */   public static Object[] toDeleteUserParam(String username) {
/* 161 */     return new Object[] { username };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(TokenVO vo) {
/* 167 */     return new Object[] { vo.getTokenId() };
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
/*     */   public static Object[] toSelectPKParam(String tokenId) {
/* 192 */     return new Object[] { tokenId };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectONEParams(HwTokenVO vo) {
/* 196 */     return new Object[] { vo.getTokenId() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContents(HwTokenVO vo, Long tsFrom, Long tsTo) {
/* 201 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 204 */     sb.append("SELECT userId, username, branchId, commend, tokenType, tokenId, tokenKey, status, lost, tsLost, unlockCnt, tsReg, tsOccupied, tsSuspend, tsDiscard  FROM MMTH_HwTokens ");
/*     */     
/* 206 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 208 */     sb.append(" ORDER BY tokenId");
/* 209 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentConunt(HwTokenVO vo, Long tsFrom, Long tsTo) {
/* 214 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 217 */     sb.append("SELECT COUNT(*) FROM MMTH_HwTokens ");
/*     */     
/* 219 */     appendCondition(sb, vo, tsFrom, tsTo);
/* 220 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, HwTokenVO vo, Long tsFrom, Long tsTo) {
/* 225 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 229 */     boolean hasParam = false;
/*     */     
/* 231 */     if (vo.getStatus() != null) {
/* 232 */       sb.append(" WHERE ").append("status = ?");
/* 233 */       hasParam = true;
/*     */     } 
/*     */     
/* 236 */     if (vo.getLost() != null) {
/* 237 */       sb.append(hasParam ? " AND " : " WHERE ").append("lost = ?");
/* 238 */       hasParam = true;
/*     */     } 
/*     */     
/* 241 */     if (!StringUtils.isEmpty(vo.getType())) {
/* 242 */       sb.append(hasParam ? " AND " : " WHERE ").append("tokenType = ?");
/* 243 */       hasParam = true;
/*     */     } 
/*     */     
/* 246 */     if (!StringUtils.isEmpty(vo.getTokenId())) {
/* 247 */       sb.append(hasParam ? " AND " : " WHERE ").append("tokenId = ?");
/* 248 */       hasParam = true;
/*     */     } 
/*     */     
/* 251 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 252 */       sb.append(hasParam ? " AND " : " WHERE ").append("username like ?");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(HwTokenVO vo, Long tsFrom, Long tsTo) {
/* 259 */     if (vo == null)
/*     */     {
/* 261 */       return null;
/*     */     }
/*     */     
/* 264 */     List<Object> params = new ArrayList(5);
/*     */     
/* 266 */     if (vo.getStatus() != null) {
/* 267 */       params.add(vo.getStatus().getCode());
/*     */     }
/*     */     
/* 270 */     if (vo.getLost() != null) {
/* 271 */       params.add(vo.getLost().getCode());
/*     */     }
/*     */     
/* 274 */     if (!StringUtils.isEmpty(vo.getType())) {
/* 275 */       params.add(vo.getType());
/*     */     }
/*     */     
/* 278 */     if (!StringUtils.isEmpty(vo.getTokenId())) {
/* 279 */       params.add(vo.getTokenId());
/*     */     }
/*     */     
/* 282 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 283 */       params.add(vo.getUsername() + "%");
/*     */     }
/*     */     
/* 286 */     return params.toArray();
/*     */   }
/*     */   
/*     */   public static Object[] toSelectToken(String tokenId) {
/* 290 */     return new Object[] { tokenId };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectUser(HwTokenVO vo) {
/* 294 */     return new Object[] { Integer.valueOf(vo.getUserId()) };
/*     */   }
/*     */   
/*     */   public static RowMapper<HwTokenVO> getRowMapper() {
/* 298 */     return (RowMapper<HwTokenVO>)new TokenRowMapper(null);
/*     */   }
/*     */   
/*     */   public static BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<HwTokenVO> tokens) {
/* 302 */     return (BatchPreparedStatementSetter)new TokenBatchPreparedStatementSetter(tokens, null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLHwtoken.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */