package WEB-INF.classes.com.dreammirae.mmth.authentication.service;

import com.dreammirae.mmth.vo.FidoRegistrationVO;
import com.dreammirae.mmth.vo.ServerChallengeVO;
import com.dreammirae.mmth.vo.UserVO;

public interface IExteranlRespInterworker {
  void respRegInterworker(UserVO paramUserVO, ServerChallengeVO paramServerChallengeVO, FidoRegistrationVO paramFidoRegistrationVO);
  
  void respAuthInterworker(UserVO paramUserVO, ServerChallengeVO paramServerChallengeVO, FidoRegistrationVO paramFidoRegistrationVO);
  
  void respAuthFailInterworker(UserVO paramUserVO, ServerChallengeVO paramServerChallengeVO, FidoRegistrationVO paramFidoRegistrationVO);
  
  void respUserCancleInterworker(UserVO paramUserVO, ServerChallengeVO paramServerChallengeVO, FidoRegistrationVO paramFidoRegistrationVO);
  
  void respDeregNotifiedInterworker(String paramString);
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\IExteranlRespInterworker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */