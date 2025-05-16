package WEB-INF.classes.com.dreammirae.mmth.authentication.service;

import com.dreammirae.mmth.authentication.AuthMethodTypes;
import com.dreammirae.mmth.fido.Operation;
import com.dreammirae.mmth.fido.transport.SendUAFResponse;
import com.dreammirae.mmth.fido.transport.ServerResponse;

public interface IUAFResponseService {
  void verifyingUAFResponse(Operation paramOperation, ServerResponse paramServerResponse, SendUAFResponse paramSendUAFResponse);
  
  AuthMethodTypes getAuthMethodTypes();
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\IUAFResponseService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */