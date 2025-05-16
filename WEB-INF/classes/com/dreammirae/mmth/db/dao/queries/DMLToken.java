/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
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
/*     */ public final class DMLToken
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_Tokens (tokenId, tokenData, status, tsReg) VALUES (?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_Tokens SET status=?, authType=?, username=?, tsOccupied=? WHERE tokenId=?";
/*     */   public static final String UPDATE_TOKEN_DISCARD = "UPDATE MMTH_Tokens SET status=?, tsDiscard=? WHERE tokenId=?";
/*     */   public static final String UPDATE_TOKEN_LOST = "UPDATE MMTH_Tokens SET lost=?, tsLost=? WHERE tokenId=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_Tokens WHERE tokenId=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_Tokens WHERE tokenId=?";
/*     */   private static final String SELECT_PREFIX = "SELECT tokenId, tokenData, status, authType, username, lost, tsReg, tsOccupied, tsDiscard, tsLost FROM MMTH_Tokens ";
/*     */   private static final String SELECT_CONTENTS_SUFFIX = " ORDER BY tokenId";
/*     */   private static final String SELECT_CONTENT_COUNT_PREFIX = "SELECT COUNT(*) FROM MMTH_Tokens ";
/*     */   private static final String SELECT_ONE_TOKEN = "SELECT username, a.tokenId, authFailCnt, tsOccupied, a.status FROM MMTH_Tokens a INNER JOIN MMTH_TokenUsers b ON a.tokenId=b.tokenId INNER JOIN MMTH_DeviceAppAgents c ON b.deviceAgentId=c.id INNER JOIN MMTH_AuthManager d ON c.userDeviceId=d.userDeviceId ";
/*     */   private static final String WHERE = " WHERE ";
/*     */   private static final String AND = " AND ";
/*     */   public static final String SELECT_ONE_PK = "SELECT tokenId, tokenData, status, authType, username, lost, tsReg, tsOccupied, tsDiscard, tsLost FROM MMTH_Tokens WHERE tokenId=? ";
/*     */   public static final String SELECT_ONE_COND = "SELECT tokenId, tokenData, status, authType, username, lost, tsReg, tsOccupied, tsDiscard, tsLost FROM MMTH_Tokens WHERE tokenId=? ";
/*     */   public static final String SELECT_ALL_LIST = "SELECT tokenId, tokenData, status, authType, username, lost, tsReg, tsOccupied, tsDiscard, tsLost FROM MMTH_Tokens ";
/*     */   public static final String SELECT_AVAILABLE_TOKEN_CNT = "SELECT count(*) FROM MMTH_Tokens WHERE status=?";
/*     */   public static final String SELECT_TOKEN_BY_USER_CNT = "SELECT count(*) FROM MMTH_Tokens WHERE status=? AND username=? AND authType=? AND lost=?";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  66 */     return new int[] { 12, 12, 1, -5 };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(TokenVO vo) {
/*  70 */     vo.setTsReg(System.currentTimeMillis());
/*  71 */     return new Object[] { vo.getTokenId(), vo.getTokenData(), vo.getStatus().getCode(), Long.valueOf(vo.getTsReg()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  80 */     return new int[] { 1, 4, 12, -5, 12 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateParams(TokenVO vo) {
/*  85 */     int authType = (vo.getAuthType() != null) ? vo.getAuthType().getCode() : -1;
/*  86 */     if (StringUtils.isEmpty(vo.getUsername())) {
/*  87 */       vo.setUsername("-");
/*     */     }
/*     */     
/*  90 */     return new Object[] { vo.getStatus().getCode(), Integer.valueOf(authType), vo.getUsername(), Long.valueOf(vo.getTsOccupied()), vo.getTokenId() };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateDiscardParams(String tokenId) {
/*  96 */     return new Object[] { TokenStatus.DISCARD.getCode(), Long.valueOf(System.currentTimeMillis()), tokenId };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateLostParams(String tokenId) {
/* 102 */     return new Object[] { YNStatus.Y.getCode(), Long.valueOf(System.currentTimeMillis()), tokenId };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(String tokenId) {
/* 111 */     return new Object[] { tokenId };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(TokenVO vo) {
/* 117 */     return new Object[] { vo.getTokenId() };
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
/*     */   public static Object[] getSelectUserCondition(TokenStatus status, String username, AuthMethodTypes authType, YNStatus lost) {
/* 141 */     return new Object[] { status.getCode(), username, Integer.valueOf(authType.getCode()), lost.getCode() };
/*     */   }
/*     */   
/*     */   public static Object[] getSelectAvailableCount() {
/* 145 */     return new Object[] { TokenStatus.AVAILABLE.getCode() };
/*     */   }
/*     */   
/*     */   public static String getSelectAvailableWithLimit() {
/* 149 */     return Commons.ctx.getDatabaseAccess().getType().convertLimitQuery("SELECT tokenId, tokenData, status, authType, username, lost, tsReg, tsOccupied, tsDiscard, tsLost FROM MMTH_Tokens WHERE status=? ORDER BY tokenId", 1, 0);
/*     */   }
/*     */   
/*     */   public static Object[] getSelectAvailableParam() {
/* 153 */     return new Object[] { TokenStatus.AVAILABLE.getCode() };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectPKParam(String tokenId) {
/* 157 */     return new Object[] { tokenId };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectONEParams(TokenVO vo) {
/* 161 */     return new Object[] { vo.getTokenId() };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectTokenParams(TokenVO vo) {
/* 165 */     return new Object[] { vo.getUsername() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContents(TokenVO vo, Long tsFrom, Long tsTo) {
/* 170 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 173 */     sb.append("SELECT tokenId, tokenData, status, authType, username, lost, tsReg, tsOccupied, tsDiscard, tsLost FROM MMTH_Tokens ");
/*     */     
/* 175 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 177 */     sb.append(" ORDER BY tokenId");
/*     */     
/* 179 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String selectTokens(TokenVO vo) {
/* 183 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 186 */     sb.append("SELECT username, a.tokenId, authFailCnt, tsOccupied, a.status FROM MMTH_Tokens a INNER JOIN MMTH_TokenUsers b ON a.tokenId=b.tokenId INNER JOIN MMTH_DeviceAppAgents c ON b.deviceAgentId=c.id INNER JOIN MMTH_AuthManager d ON c.userDeviceId=d.userDeviceId ");
/*     */     
/* 188 */     appendCondition(sb, vo, null, null);
/*     */     
/* 190 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String selectContentCount(TokenVO vo, Long tsFrom, Long tsTo) {
/* 195 */     StringBuilder sb = new StringBuilder(255);
/*     */ 
/*     */     
/* 198 */     sb.append("SELECT COUNT(*) FROM MMTH_Tokens ");
/*     */     
/* 200 */     appendCondition(sb, vo, tsFrom, tsTo);
/*     */     
/* 202 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void appendCondition(StringBuilder sb, TokenVO vo, Long tsFrom, Long tsTo) {
/* 207 */     if (vo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 211 */     boolean hasParam = false;
/*     */     
/* 213 */     if (vo.getStatus() != null) {
/* 214 */       sb.append(" WHERE ").append("status = ?");
/* 215 */       hasParam = true;
/*     */     } 
/*     */     
/* 218 */     if (vo.getLost() != null) {
/* 219 */       sb.append(hasParam ? " AND " : " WHERE ").append("lost = ?");
/* 220 */       hasParam = true;
/*     */     } 
/*     */     
/* 223 */     if (!StringUtils.isEmpty(vo.getTokenId())) {
/* 224 */       sb.append(hasParam ? " AND " : " WHERE ").append("tokenId = ?");
/* 225 */       hasParam = true;
/*     */     } 
/*     */     
/* 228 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 229 */       sb.append(hasParam ? " AND " : " WHERE ").append("username like ?");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(TokenVO vo, Long tsFrom, Long tsTo) {
/* 235 */     if (vo == null)
/*     */     {
/* 237 */       return null;
/*     */     }
/*     */     
/* 240 */     List<Object> params = new ArrayList(4);
/*     */     
/* 242 */     if (vo.getStatus() != null) {
/* 243 */       params.add(vo.getStatus().getCode());
/*     */     }
/*     */     
/* 246 */     if (vo.getLost() != null) {
/* 247 */       params.add(vo.getLost().getCode());
/*     */     }
/*     */     
/* 250 */     if (!StringUtils.isEmpty(vo.getTokenId())) {
/* 251 */       params.add(vo.getTokenId());
/*     */     }
/*     */     
/* 254 */     if (!StringUtils.isEmpty(vo.getUsername())) {
/* 255 */       params.add(vo.getUsername() + "%");
/*     */     }
/*     */     
/* 258 */     return params.toArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public static RowMapper<TokenVO> getRowMapper() {
/* 263 */     return (RowMapper<TokenVO>)new TokenRowMapper(null);
/*     */   }
/*     */   
/*     */   public static RowMapper<TokenVO> getTokenRowMapper() {
/* 267 */     return (RowMapper<TokenVO>)new TokenDetailRowMapper(null);
/*     */   }
/*     */   
/*     */   public static BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<TokenVO> tokens) {
/* 271 */     return (BatchPreparedStatementSetter)new TokenBatchPreparedStatementSetter(tokens, null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLToken.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */