package WEB-INF.classes.com.dreammirae.mmth.authentication.service;

import com.dreammirae.mmth.fido.transport.context.RpContext;
import com.dreammirae.mmth.vo.UserVO;

public interface IExteranlReqInterworker {
  void reqAuthInterworker(UserVO paramUserVO, RpContext paramRpContext);
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\IExteranlReqInterworker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */