package WEB-INF.classes.com.dreammirae.mmth.db.dao.authentication;

import com.dreammirae.mmth.authentication.AuthMethodTypes;
import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
import com.dreammirae.mmth.db.dao.authentication.IAuthenticationManagerDao;
import com.dreammirae.mmth.vo.AppAgentVO;
import com.dreammirae.mmth.vo.AuthManagerVO;
import com.dreammirae.mmth.vo.DeviceAppAgentVO;
import com.dreammirae.mmth.vo.FidoRegistrationVO;
import com.dreammirae.mmth.vo.ServerChallengeVO;
import com.dreammirae.mmth.vo.TokenRegistrationVO;
import com.dreammirae.mmth.vo.UserDeviceVO;
import com.dreammirae.mmth.vo.UserVO;
import com.dreammirae.mmth.vo.bean.ICustomLogData;
import com.dreammirae.mmth.vo.types.DeviceTypes;
import java.util.List;

public interface IUserServiceDao extends IAuthenticationManagerDao {
  void setUserServiceForReg(UserServiceLocator paramUserServiceLocator, String paramString1, String paramString2, DeviceTypes paramDeviceTypes, String paramString3);
  
  void setUserServiceForAuth(UserServiceLocator paramUserServiceLocator, String paramString1, String paramString2, DeviceTypes paramDeviceTypes, String paramString3);
  
  void setUserServiceForDereg(UserServiceLocator paramUserServiceLocator, String paramString1, String paramString2, DeviceTypes paramDeviceTypes, String paramString3);
  
  void saveRegRequest(UserServiceLocator paramUserServiceLocator);
  
  void saveAuthRequest(UserServiceLocator paramUserServiceLocator);
  
  void saveDeregRequest(UserServiceLocator paramUserServiceLocator);
  
  void saveRegResponse(UserServiceLocator paramUserServiceLocator);
  
  void saveAuthResponse(UserServiceLocator paramUserServiceLocator);
  
  void saveOTPResponse(UserServiceLocator paramUserServiceLocator);
  
  void forceDeregByUser(UserVO paramUserVO, ICustomLogData paramICustomLogData, UserServiceLocator paramUserServiceLocator);
  
  void resetAuthFailByUser(UserVO paramUserVO);
  
  void raiseLostToken(TokenRegistrationVO paramTokenRegistrationVO);
  
  UserVO returnUser(String paramString);
  
  UserDeviceVO returnUserDevice(UserVO paramUserVO, String paramString, DeviceTypes paramDeviceTypes);
  
  UserDeviceVO returnUserDevice(DeviceAppAgentVO paramDeviceAppAgentVO);
  
  List<UserDeviceVO> returnUserDevices(UserVO paramUserVO);
  
  DeviceAppAgentVO returnDeviceAppAgent(UserDeviceVO paramUserDeviceVO, AppAgentVO paramAppAgentVO);
  
  FidoRegistrationVO returnFidoRegistration(String paramString1, String paramString2);
  
  ServerChallengeVO returnServerChallengeByUsername(String paramString);
  
  ServerChallengeVO returnServerChallengeByTid(String paramString);
  
  AuthManagerVO returnAuthManager(UserDeviceVO paramUserDeviceVO);
  
  boolean checkIfHasFidoTC(FidoRegistrationVO paramFidoRegistrationVO, String paramString);
  
  List<DeviceAppAgentVO> returnDeviceApps(UserDeviceVO paramUserDeviceVO, AuthMethodTypes paramAuthMethodTypes);
  
  List<DeviceAppAgentVO> returnDeviceApps(UserDeviceVO paramUserDeviceVO);
  
  List<DeviceAppAgentVO> returnDeviceApps(UserVO paramUserVO, AuthMethodTypes paramAuthMethodTypes);
  
  List<DeviceAppAgentVO> returnDeviceApps(UserVO paramUserVO);
  
  DeviceAppAgentVO returnDeviceAppAgent(ServerChallengeVO paramServerChallengeVO);
  
  DeviceAppAgentVO returnDeviceAppAgent(FidoRegistrationVO paramFidoRegistrationVO);
  
  TokenRegistrationVO returnTokenRegistration(DeviceAppAgentVO paramDeviceAppAgentVO);
  
  void saveUser(UserVO paramUserVO);
  
  void saveUserDevice(UserDeviceVO paramUserDeviceVO);
  
  void authenticationSuccess(DeviceAppAgentVO paramDeviceAppAgentVO);
  
  void authenticationFailed(DeviceAppAgentVO paramDeviceAppAgentVO);
  
  int returnRegisteredDeviceAppCnt(UserVO paramUserVO, AuthMethodTypes paramAuthMethodTypes);
  
  AuthMethodTypes getAuthMethodTypes();
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\authentication\IUserServiceDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */