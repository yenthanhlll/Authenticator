/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
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
/*     */ public class DMLServerChallenge
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_ServerChallenge (username, challenge, challengeType, status, tsLifetime, transactionId, tranContent, deviceAgentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_ServerChallenge set status=?, tsDone=? WHERE id=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_ServerChallenge WHERE id=?";
/*     */   public static final String DELETE_BY_USERNAME = "DELETE FROM MMTH_ServerChallenge WHERE username=?";
/*     */   private static final String SELECT_PREFIX = "SELECT id, username, challenge, challengeType, status, tsLifetime, transactionId, tranContent, deviceAgentId, tsDone FROM MMTH_ServerChallenge ";
/*     */   public static final String SELECT_ONE_PK = "SELECT id, username, challenge, challengeType, status, tsLifetime, transactionId, tranContent, deviceAgentId, tsDone FROM MMTH_ServerChallenge WHERE id=?";
/*     */   public static final String SELECT_ONE_USERNAME = "SELECT id, username, challenge, challengeType, status, tsLifetime, transactionId, tranContent, deviceAgentId, tsDone FROM MMTH_ServerChallenge WHERE username=?";
/*     */   public static final String SELECT_ONE_TID = "SELECT id, username, challenge, challengeType, status, tsLifetime, transactionId, tranContent, deviceAgentId, tsDone FROM MMTH_ServerChallenge WHERE transactionId=?";
/*     */   public static final String SELECT_ONE_USERNAME_CHALLENGE = "SELECT id, username, challenge, challengeType, status, tsLifetime, transactionId, tranContent, deviceAgentId, tsDone FROM MMTH_ServerChallenge WHERE username=? and challenge=?";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  52 */     return new int[] { 12, 1, 1, 1, -5, 12, 12, 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(ServerChallengeVO vo) {
/*  56 */     return new Object[] { vo.getUsername(), vo.getChallenge(), vo.getChallengeType().getCode(), vo.getStatus().getCode(), Long.valueOf(vo.getTsLifeTime()), vo.getTransactionId(), vo.getTranContent(), Integer.valueOf(vo.getDeviceAppId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  66 */     return new int[] { 1, -5, -5 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(ServerChallengeVO vo) {
/*  70 */     return new Object[] { vo.getStatus().getCode(), Long.valueOf(vo.getTsDone()), Long.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(long id) {
/*  79 */     return new Object[] { Long.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(UserVO user) {
/*  85 */     return new Object[] { user.getUsername() };
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
/*     */   public static Object[] toSelectPKParam(int id) {
/*  99 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(String username) {
/* 103 */     return new Object[] { username };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParamsByTid(String tid) {
/* 107 */     return new Object[] { tid };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParamsChallenge(String username, String challenge) {
/* 111 */     return new Object[] { username, challenge };
/*     */   }
/*     */   
/*     */   public static RowMapper<ServerChallengeVO> getRowMapper() {
/* 115 */     return (RowMapper<ServerChallengeVO>)new ServerChallengeRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLServerChallenge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */