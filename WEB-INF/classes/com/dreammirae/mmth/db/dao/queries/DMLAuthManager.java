/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.vo.AuthManagerVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
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
/*     */ public class DMLAuthManager
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_AuthManager (userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail) VALUES (?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_AuthManager set authFailCnt=?, totSuccessCnt=?, tsLastAuth=?, tsLastAuthFail=? WHERE userDeviceId=? and authType=?";
/*     */   public static final String UPDATE_SUCCESS = "UPDATE MMTH_AuthManager set authFailCnt=0, totSuccessCnt=totSuccessCnt+1, tsLastAuth=? WHERE userDeviceId=? and authType=?";
/*     */   public static final String UPDATE_FAILED = "UPDATE MMTH_AuthManager set authFailCnt=authFailCnt+1, tsLastAuthFail=? WHERE userDeviceId=? and authType=?";
/*     */   public static final String UPDATE_RESET_BY_USER = "UPDATE MMTH_AuthManager set authFailCnt=? WHERE userId=? and authType=?";
/*     */   public static final String UPDATE_RESET_BY_USERDEVICE = "UPDATE MMTH_AuthManager set authFailCnt=? WHERE userDeviceId=? and authType=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_AuthManager WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_AuthManager WHERE userDeviceId=? and authType=?";
/*     */   private static final String SELECT_PREFIX = "SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager ";
/*     */   public static final String SELECT_PK = "SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager WHERE id=?";
/*     */   public static final String SELECT_UNIQUE = "SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager WHERE userDeviceId=? AND authType=?";
/*     */   public static final String SELECT_LIST_BY_USER = "SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager WHERE userId=? AND authType=?";
/*     */   public static final String SELECT_LIST_BY_USER_DEVICE = "SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager WHERE userDeviceId=? AND authType=?";
/*     */   public static final String SELECT_LIST_BY_DEVICE_APP_AGENT = "SELECT id, userId, userDeviceId, authType, authFailCnt, totSuccessCnt, tsLastAuth, tsLastAuthFail FROM MMTH_AuthManager WHERE userDeviceId=? AND authType=?";
/*     */   public static final String SELECT_LATEST_BY_USER_AUTHTYPE = "SELECT 0, userId, 0, authType, sum(authFailCnt), sum(totSuccessCnt), max(tsLastAuth), max(tsLastAuthFail) FROM MMTH_AuthManager WHERE userId = ? AND authType = ? GROUP BY userId, authType";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  46 */     return new int[] { 4, 4, 4, 4, 4, -5, -5 };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(AuthManagerVO vo) {
/*  50 */     return new Object[] { Integer.valueOf(vo.getUserId()), Integer.valueOf(vo.getUserDeviceId()), Integer.valueOf(vo.getAuthType().getCode()), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  60 */     return new int[] { 4, 4, -5, -5, 4, 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(AuthManagerVO vo) {
/*  64 */     return new Object[] { Integer.valueOf(vo.getAuthFailCnt()), Integer.valueOf(vo.getTotSuceessCnt()), Long.valueOf(vo.getTsLastAuth()), Long.valueOf(vo.getTsLastAuthFail()), Integer.valueOf(vo.getUserDeviceId()), Integer.valueOf(vo.getAuthType().getCode()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toUpdateLatestParams(DeviceAppAgentVO vo) {
/*  73 */     return new Object[] { Long.valueOf(System.currentTimeMillis()), Integer.valueOf(vo.getUserDeviceId()), Integer.valueOf(vo.getAuthType().getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateRestAuthFail(UserVO user, AuthMethodTypes authType) {
/*  77 */     return new Object[] { Integer.valueOf(0), Integer.valueOf(user.getId()), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateRestAuthFail(UserDeviceVO userDevice, AuthMethodTypes authType) {
/*  81 */     return new Object[] { Integer.valueOf(0), Integer.valueOf(userDevice.getId()), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/*  90 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(AuthManagerVO vo) {
/*  96 */     return new Object[] { Integer.valueOf(vo.getUserDeviceId()), Integer.valueOf(vo.getAuthType().getCode()) };
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
/*     */   public static Object[] toSelectPKParam(int id) {
/* 111 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(UserDeviceVO ud, AuthMethodTypes authType) {
/* 115 */     return new Object[] { Integer.valueOf(ud.getId()), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectLatestByUser(UserVO user, AuthMethodTypes authType) {
/* 119 */     return new Object[] { Integer.valueOf(user.getId()), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectListByUser(UserVO user, AuthMethodTypes authType) {
/* 123 */     return new Object[] { Integer.valueOf(user.getId()), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectListByUserDevice(UserDeviceVO userDevice, AuthMethodTypes authType) {
/* 127 */     return new Object[] { Integer.valueOf(userDevice.getId()), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectListForUser(DeviceAppAgentVO deviceAppAgent) {
/* 131 */     return new Object[] { Integer.valueOf(deviceAppAgent.getUserDeviceId()), Integer.valueOf(deviceAppAgent.getAuthType().getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectListForUserDevice(DeviceAppAgentVO deviceAppAgent) {
/* 135 */     return new Object[] { Integer.valueOf(deviceAppAgent.getUserDeviceId()), Integer.valueOf(deviceAppAgent.getAuthType().getCode()) };
/*     */   }
/*     */   
/*     */   public static RowMapper<AuthManagerVO> getRowMapper() {
/* 139 */     return (RowMapper<AuthManagerVO>)new AuthManagerRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLAuthManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */