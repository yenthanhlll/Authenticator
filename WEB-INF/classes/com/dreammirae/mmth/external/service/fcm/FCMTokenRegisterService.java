/*    */ package WEB-INF.classes.com.dreammirae.mmth.external.service.fcm;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*    */ import com.dreammirae.mmth.db.dao.authentication.FidoUserServiceDao;
/*    */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*    */ import com.dreammirae.mmth.vo.AppAgentVO;
/*    */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*    */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*    */ import com.dreammirae.mmth.vo.UserVO;
/*    */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
/*    */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*    */ import com.dreammirae.mmth.vo.types.UserDeviceStatus;
/*    */ import com.dreammirae.mmth.vo.types.UserStatus;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class FCMTokenRegisterService
/*    */ {
/*    */   @Autowired
/*    */   private FidoUserServiceDao serviceDao;
/*    */   
/*    */   public void registerTokenId(AuthMethodTypes authType, String fcmRegistrationId, String userName, String deviceId, DeviceTypes deviceType, String pkgUnique) throws ReturnCodeException {
/* 28 */     UserVO user = this.serviceDao.returnUser(userName);
/*    */     
/* 30 */     if (user == null || !UserStatus.AVAILABLE.equals(user.getStatus())) {
/* 31 */       throw new ReturnCodeException(ReturnCodes.USER_UNAUTHORIED, "There has no user with userName=" + userName);
/*    */     }
/*    */     
/* 34 */     UserDeviceVO userDevice = this.serviceDao.returnUserDevice(user, deviceId, deviceType);
/*    */     
/* 36 */     if (userDevice == null || !UserDeviceStatus.AVAILABLE.equals(userDevice.getStatus())) {
/* 37 */       throw new ReturnCodeException(ReturnCodes.DEVICE_UNAUTHORIED, "There has no userDevice with userName=" + userName + ", deviceId=" + deviceId + ", deviceType=" + deviceType);
/*    */     }
/*    */     
/* 40 */     AppAgentVO appAgent = AppAgentDao.getAcceptableAppAgent(deviceType.getOsType(), pkgUnique);
/*    */     
/* 42 */     if (appAgent == null) {
/* 43 */       throw new ReturnCodeException(ReturnCodes.UNKNOWN_APP_AGENT, "There has no app with pkgUnique=" + pkgUnique + ", os=" + deviceType.getOsType());
/*    */     }
/*    */     
/* 46 */     DeviceAppAgentVO deviceAppAgent = this.serviceDao.returnDeviceAppAgent(userDevice, appAgent);
/*    */     
/* 48 */     if (deviceAppAgent == null || !DeviceAppAgentStatus.AVAIABLE.equals(deviceAppAgent.getStatus())) {
/* 49 */       throw new ReturnCodeException(ReturnCodes.DEVICE_APP_UNAUTHORIED, "There has no userDevice with userName=" + userName + ", deviceId=" + deviceId + ", deviceType=" + deviceType + ", pkgName=" + pkgUnique + ", authType=" + authType);
/*    */     }
/*    */ 
/*    */     
/* 53 */     deviceAppAgent.setRegistrationId(fcmRegistrationId);
/* 54 */     user.setReprAppAgentId(deviceAppAgent.getId());
/*    */     
/* 56 */     this.serviceDao.saveFcmPushRegister(user, deviceAppAgent);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\service\fcm\FCMTokenRegisterService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */