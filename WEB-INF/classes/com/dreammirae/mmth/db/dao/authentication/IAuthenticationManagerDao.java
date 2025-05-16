package WEB-INF.classes.com.dreammirae.mmth.db.dao.authentication;

import com.dreammirae.mmth.authentication.AuthMethodTypes;
import com.dreammirae.mmth.vo.AuthManagerVO;
import com.dreammirae.mmth.vo.DeviceAppAgentVO;
import com.dreammirae.mmth.vo.UserDeviceVO;
import com.dreammirae.mmth.vo.UserVO;
import java.util.List;

public interface IAuthenticationManagerDao {
  List<AuthManagerVO> getAuthManagerByUser(UserVO paramUserVO, AuthMethodTypes paramAuthMethodTypes);
  
  List<AuthManagerVO> getAuthManagerByUserDevice(UserDeviceVO paramUserDeviceVO, AuthMethodTypes paramAuthMethodTypes);
  
  List<AuthManagerVO> getAuthManagerForUserDevice(DeviceAppAgentVO paramDeviceAppAgentVO);
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\db\dao\authentication\IAuthenticationManagerDao.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */