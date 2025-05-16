/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
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
/*     */ public class DMLDeviceAppAgent
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_DeviceAppAgents (userDeviceId, agentId, authType, status, tsReg) VALUES (?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_DeviceAppAgents set status=?, registrationId=?, tsUpdated=?, tsDone=?, tsExpired=? WHERE id=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_DeviceAppAgents WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_DeviceAppAgents WHERE userDeviceId=? and agentId=? and authType=?";
/*     */   private static final String SELECT_PREFIX = "SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents ";
/*     */   public static final String SELECT_ONE_PK = "SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE id=?";
/*     */   public static final String SELECT_ONE_UNIQUE = "SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId=? and agentId=? and authType=?";
/*     */   public static final String SELECT_LIST_BY_DEVICE = "SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId=?";
/*     */   public static final String SELECT_LIST_BY_DEVICE_AUTHTYPE = "SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId=? AND authType=?";
/*     */   public static final String SELECT_LIST_BY_USER_AUTHTYPE = "SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId in (SELECT id FROM MMTH_UserDevices WHERE userId=?) AND authType=?";
/*     */   public static final String SELECT_LIST_BY_USER = "SELECT id, userDeviceId, agentId, authType, status, registrationId, tsReg, tsUpdated, tsDone, tsExpired FROM MMTH_DeviceAppAgents WHERE userDeviceId in (SELECT id FROM MMTH_UserDevices WHERE userId=?)";
/*     */   private static final String SELECT_CNT_PREFIX = "SELECT COUNT(*) FROM MMTH_DeviceAppAgents ";
/*     */   public static final String SELECT_ACTIVE_CNT_BY_USER = "SELECT COUNT(*) FROM MMTH_DeviceAppAgents WHERE userDeviceId in (SELECT id FROM MMTH_UserDevices WHERE userId = ?) AND status = ?";
/*     */   public static final String SELECT_ACTIVE_CNT_BY_USER_AUTHTYPE = "SELECT COUNT(*) FROM MMTH_DeviceAppAgents WHERE userDeviceId in (SELECT id FROM MMTH_UserDevices WHERE userId = ?) AND status = ? AND authType = ?";
/*     */   public static final String SELECT_ACTIVE_CNT_BY_DEVICE_AUTHTYPE = "SELECT COUNT(*) FROM MMTH_DeviceAppAgents WHERE userDeviceId = ? AND status = ? AND authType = ?";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  60 */     return new int[] { 4, 4, 4, 1, -5 };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(DeviceAppAgentVO vo) {
/*  64 */     vo.setTsReg(System.currentTimeMillis());
/*  65 */     return new Object[] {
/*  66 */         Integer.valueOf(vo.getUserDeviceId()), Integer.valueOf(vo.getAgentId()), Integer.valueOf(vo.getAuthType().getCode()), vo.getStatus().getCode(), Long.valueOf(vo.getTsReg())
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getUpdateTypes() {
/*  77 */     return new int[] { 1, 12, -5, -5, -5, 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(DeviceAppAgentVO vo) {
/*  81 */     vo.setTsUpdated(System.currentTimeMillis());
/*  82 */     return new Object[] { vo.getStatus().getCode(), vo.getRegistrationId(), Long.valueOf(vo.getTsUpdated()), Long.valueOf(vo.getTsDone()), Long.valueOf(vo.getTsExpired()), Integer.valueOf(vo.getId()) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeletePKParam(int id) {
/*  91 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(DeviceAppAgentVO vo) {
/*  97 */     return toDeleteParams(vo.getUserDeviceId(), vo.getAgentId(), vo.getAuthType());
/*     */   }
/*     */   
/*     */   public static Object[] toDeleteParams(int userDeviceId, int agentId, AuthMethodTypes authType) {
/* 101 */     return new Object[] { Integer.valueOf(userDeviceId), Integer.valueOf(agentId), Integer.valueOf(authType.getCode()) };
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
/* 121 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(int userDeviceId, int agentId, AuthMethodTypes authType) {
/* 125 */     return new Object[] { Integer.valueOf(userDeviceId), Integer.valueOf(agentId), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(UserDeviceVO userDevice) {
/* 129 */     return new Object[] { Integer.valueOf(userDevice.getId()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectUserDeviceAuthTypeParams(UserDeviceVO user, AuthMethodTypes authType) {
/* 133 */     return new Object[] { Integer.valueOf(user.getId()), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectUserAuthTypeParams(UserVO user, AuthMethodTypes authType) {
/* 137 */     return new Object[] { Integer.valueOf(user.getId()), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectUserParams(UserVO user) {
/* 141 */     return new Object[] { Integer.valueOf(user.getId()) };
/*     */   }
/*     */   
/*     */   public static RowMapper<DeviceAppAgentVO> getRowMapper() {
/* 145 */     return (RowMapper<DeviceAppAgentVO>)new UserDeviceRowMapper(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toSelectActiveCntByUser(UserVO user, DeviceAppAgentStatus status) {
/* 154 */     return new Object[] { Integer.valueOf(user.getId()), status.getCode() };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectActiveCntByUserAuthType(UserVO user, DeviceAppAgentStatus status, AuthMethodTypes authType) {
/* 158 */     return new Object[] { Integer.valueOf(user.getId()), status.getCode(), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectActiveCntByDeviceAuthType(UserDeviceVO userDevice, DeviceAppAgentStatus status, AuthMethodTypes authType) {
/* 162 */     return new Object[] { Integer.valueOf(userDevice.getId()), status.getCode(), Integer.valueOf(authType.getCode()) };
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLDeviceAppAgent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */