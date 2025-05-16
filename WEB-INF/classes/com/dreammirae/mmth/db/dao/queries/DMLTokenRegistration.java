/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DMLTokenRegistration
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_TokenUsers (tokenId, deviceAgentId, tsReg, data) VALUES (?, ?, ?, ?)";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_TokenUsers WHERE deviceAgentId=? and tokenId=?";
/*     */   private static final String SELECT_PREFIX = "SELECT tu.data, tu.tokenId, tu.deviceAgentId, t.tokenData, t.status, t.authType, t.username, t.lost, t.tsReg, t.tsOccupied, t.tsDiscard, t.tsLost FROM MMTH_TokenUsers tu LEFT JOIN MMTH_Tokens t ON tu.tokenId = t.tokenId ";
/*     */   public static final String SELECT_ONE_UNIQUE = "SELECT tu.data, tu.tokenId, tu.deviceAgentId, t.tokenData, t.status, t.authType, t.username, t.lost, t.tsReg, t.tsOccupied, t.tsDiscard, t.tsLost FROM MMTH_TokenUsers tu LEFT JOIN MMTH_Tokens t ON tu.tokenId = t.tokenId WHERE tu.deviceAgentId=? ";
/*     */   public static final String SELECT_ONE_BY_TOKEN = "SELECT tu.data, tu.tokenId, tu.deviceAgentId, t.tokenData, t.status, t.authType, t.username, t.lost, t.tsReg, t.tsOccupied, t.tsDiscard, t.tsLost FROM MMTH_TokenUsers tu LEFT JOIN MMTH_Tokens t ON tu.tokenId = t.tokenId WHERE t.tokenId=? ";
/*     */   public static final String SELECT_TOKEN_ASSIGNED_CNT = "SELECT COUNT(*) FROM MMTH_TokenUsers WHERE tokenId = ?";
/*     */   public static final String SELECT_TOKEN_LOST_CNT_BY_DEVICE = "SELECT COUNT(*) FROM MMTH_TokenUsers tu LEFT JOIN MMTH_DeviceAppAgents da ON tu.deviceAgentId = da.id LEFT JOIN MMTH_Tokens t ON tu.tokenId = t.tokenId WHERE da.userDeviceId = ? AND da.authType =? AND t.lost = ?";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  75 */     return new int[] { 4, 4, -5, Commons.ctx.getDatabaseAccess().getType().getBLOBType() };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(TokenRegistrationVO vo) {
/*  79 */     vo.setTsReg(System.currentTimeMillis());
/*  80 */     return new Object[] { vo.getTokenId(), Integer.valueOf(vo.getDeviceAgentId()), Long.valueOf(vo.getTsReg()), Commons.ctx.getDatabaseAccess().getType().seializeBlob(vo) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] toDeleteParams(TokenRegistrationVO vo) {
/*  91 */     return new Object[] { Integer.valueOf(vo.getDeviceAgentId()), vo.getTokenId() };
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
/*     */   public static Object[] toSelectUniqueParams(TokenRegistrationVO vo) {
/* 106 */     return new Object[] { Integer.valueOf(vo.getDeviceAgentId()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectByToken(String tokenId) {
/* 110 */     return new Object[] { tokenId };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(DeviceAppAgentVO deviceAppAgent) {
/* 114 */     return new Object[] { Integer.valueOf(deviceAppAgent.getId()) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectTokenAssignedCnt(TokenRegistrationVO vo) {
/* 118 */     return new Object[] { vo.getTokenId() };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectLostTokenByDevice(UserDeviceVO userDevice, AuthMethodTypes authType) {
/* 122 */     return new Object[] { Integer.valueOf(userDevice.getId()), Integer.valueOf(authType.getCode()), YNStatus.Y.getCode() };
/*     */   }
/*     */   
/*     */   public static RowMapper<TokenRegistrationVO> getRowMapper() {
/* 126 */     return (RowMapper<TokenRegistrationVO>)new TokenRegistrationRowRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLTokenRegistration.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */