/*     */ package WEB-INF.classes.com.dreammirae.mmth.db.dao.queries;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
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
/*     */ public class DMLUserDevice
/*     */ {
/*     */   public static final String INSERT = "INSERT INTO MMTH_UserDevices (userId, deviceId, deviceType, model, alias, disabled, status, tsReg, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*     */   public static final String UPDATE = "UPDATE MMTH_UserDevices set alias=?, disabled=?, status=?, tsUpdated=?, data=? WHERE id=?";
/*     */   public static final String DELETE_BY_PK = "DELETE FROM MMTH_UserDevices WHERE id=?";
/*     */   public static final String DELETE_BY_COND = "DELETE FROM MMTH_UserDevices WHERE userId=? and deviceId=? and deviceType=?";
/*     */   private static final String SELECT_PREFIX = "SELECT data, id, userId, deviceId, deviceType, model, alias, disabled, status, tsReg, tsUpdated FROM MMTH_UserDevices ";
/*     */   public static final String SELECT_ONE_PK = "SELECT data, id, userId, deviceId, deviceType, model, alias, disabled, status, tsReg, tsUpdated FROM MMTH_UserDevices WHERE id=?";
/*     */   public static final String SELECT_ONE_UNIQUE = "SELECT data, id, userId, deviceId, deviceType, model, alias, disabled, status, tsReg, tsUpdated FROM MMTH_UserDevices WHERE userId=? and deviceId=? and deviceType=?";
/*     */   public static final String SELECT_BY_USER = "SELECT data, id, userId, deviceId, deviceType, model, alias, disabled, status, tsReg, tsUpdated FROM MMTH_UserDevices WHERE userId=? ORDER BY tsReg desc";
/*     */   
/*     */   public static int[] getInsertTypes() {
/*  61 */     return new int[] { 4, 12, 1, 12, 12, 1, 1, -5, Commons.ctx.getDatabaseAccess().getType().getBLOBType() };
/*     */   }
/*     */   
/*     */   public static Object[] toInsertParams(UserDeviceVO vo) {
/*  65 */     vo.setTsReg(System.currentTimeMillis());
/*  66 */     return new Object[] {
/*  67 */         Integer.valueOf(vo.getUserId()), vo.getDeviceId(), vo.getDeviceType().getCode(), vo.getModel(), vo.getAlias(), vo
/*  68 */         .getDisabled().getCode(), vo.getStatus().getCode(), Long.valueOf(vo.getTsReg()), Commons.ctx
/*  69 */         .getDatabaseAccess().getType().seializeBlob(vo)
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
/*  80 */     return new int[] { 12, 1, 1, -5, Commons.ctx
/*  81 */         .getDatabaseAccess().getType().getBLOBType(), 4 };
/*     */   }
/*     */   
/*     */   public static Object[] toUpdateParams(UserDeviceVO vo) {
/*  85 */     vo.setTsUpdated(System.currentTimeMillis());
/*  86 */     return new Object[] { vo.getAlias(), vo.getDisabled().getCode(), vo.getStatus().getCode(), Long.valueOf(vo.getTsUpdated()), Commons.ctx
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
/*     */   public static Object[] toDeleteParams(UserDeviceVO vo) {
/* 102 */     return toDeleteParams(vo.getUserId(), vo.getDeviceId(), vo.getDeviceType());
/*     */   }
/*     */   
/*     */   public static Object[] toDeleteParams(int userId, String deviceId, DeviceTypes deviceType) {
/* 106 */     return new Object[] { Integer.valueOf(userId), deviceId, deviceType.getCode() };
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
/* 120 */     return new Object[] { Integer.valueOf(id) };
/*     */   }
/*     */   
/*     */   public static Object[] toSelectParams(int userId, String deviceId, DeviceTypes deviceType) {
/* 124 */     return new Object[] { Integer.valueOf(userId), deviceId, deviceType.getCode() };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] toSelectParams(UserVO user) {
/* 129 */     return new Object[] { Integer.valueOf(user.getId()) };
/*     */   }
/*     */   
/*     */   public static RowMapper<UserDeviceVO> getRowMapper() {
/* 133 */     return (RowMapper<UserDeviceVO>)new UserDeviceRowMapper(null);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\queries\DMLUserDevice.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */