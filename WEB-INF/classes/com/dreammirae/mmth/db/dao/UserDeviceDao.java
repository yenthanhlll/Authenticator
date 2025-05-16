/*    */ package WEB-INF.classes.com.dreammirae.mmth.db.dao;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*    */ import com.dreammirae.mmth.db.dao.BaseDao;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.db.dao.queries.DMLDeviceAppAgent;
/*    */ import com.dreammirae.mmth.db.dao.queries.DMLTokenRegistration;
/*    */ import com.dreammirae.mmth.db.dao.queries.DMLUserDevice;
/*    */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*    */ import com.dreammirae.mmth.vo.UserVO;
/*    */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
/*    */ import com.dreammirae.mmth.vo.types.UserDeviceStatus;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Repository;
/*    */ 
/*    */ @Repository("userDeviceDao")
/*    */ public class UserDeviceDao
/*    */   extends BaseDao
/*    */ {
/*    */   public List<UserDeviceVO> getUserDevice(UserVO user) {
/* 21 */     List<UserDeviceVO> userDevices = queryForList("SELECT data, id, userId, deviceId, deviceType, model, alias, disabled, status, tsReg, tsUpdated FROM MMTH_UserDevices WHERE userId=? ORDER BY tsReg desc", DMLUserDevice.toSelectParams(user), DMLUserDevice.getRowMapper());
/*    */     
/* 23 */     for (UserDeviceVO ud : userDevices) {
/* 24 */       setRelationData(ud);
/*    */     }
/*    */     
/* 27 */     return userDevices;
/*    */   }
/*    */   
/*    */   public UserDeviceVO getOneByPK(int id) {
/* 31 */     return (UserDeviceVO)queryForObject("SELECT data, id, userId, deviceId, deviceType, model, alias, disabled, status, tsReg, tsUpdated FROM MMTH_UserDevices WHERE id=?", DMLUserDevice.toSelectPKParam(id), DMLUserDevice.getRowMapper());
/*    */   }
/*    */ 
/*    */   
/*    */   private void setRelationData(UserDeviceVO userDevice) {
/* 36 */     if (SystemSettingsDao.getSettingEnabled("advanced.biotpEnabled").toBoolean()) {
/* 37 */       int biotpActive = queryForInt("SELECT COUNT(*) FROM MMTH_DeviceAppAgents WHERE userDeviceId = ? AND status = ? AND authType = ?", DMLDeviceAppAgent.toSelectActiveCntByDeviceAuthType(userDevice, DeviceAppAgentStatus.AVAIABLE, AuthMethodTypes.BIOTP), 0);
/* 38 */       if (biotpActive > 0) {
/* 39 */         int lostCnt = queryForInt("SELECT COUNT(*) FROM MMTH_TokenUsers tu LEFT JOIN MMTH_DeviceAppAgents da ON tu.deviceAgentId = da.id LEFT JOIN MMTH_Tokens t ON tu.tokenId = t.tokenId WHERE da.userDeviceId = ? AND da.authType =? AND t.lost = ?", DMLTokenRegistration.toSelectLostTokenByDevice(userDevice, AuthMethodTypes.BIOTP), 0);
/*    */         
/* 41 */         if (lostCnt > 0) {
/* 42 */           userDevice.setBiotpStatus(UserDeviceStatus.LOST_STOLEN);
/*    */         } else {
/* 44 */           userDevice.setBiotpStatus(UserDeviceStatus.AVAILABLE);
/*    */         }
/*    */       
/* 47 */       } else if (userDevice.getBiotpRegCumulation() > 0) {
/* 48 */         userDevice.setBiotpStatus(UserDeviceStatus.DISCARD);
/*    */       } else {
/* 50 */         userDevice.setBiotpStatus(UserDeviceStatus.NOT_AVAILABLE);
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     if (SystemSettingsDao.getSettingEnabled("advanced.fidoEnabled").toBoolean()) {
/* 55 */       int fidoActive = queryForInt("SELECT COUNT(*) FROM MMTH_DeviceAppAgents WHERE userDeviceId = ? AND status = ? AND authType = ?", DMLDeviceAppAgent.toSelectActiveCntByDeviceAuthType(userDevice, DeviceAppAgentStatus.AVAIABLE, AuthMethodTypes.FIDO), 0);
/*    */       
/* 57 */       if (fidoActive > 0) {
/* 58 */         userDevice.setFidoStatus(UserDeviceStatus.AVAILABLE);
/*    */       } else {
/* 60 */         userDevice.setFidoStatus(UserDeviceStatus.NOT_AVAILABLE);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\UserDeviceDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */