package WEB-INF.classes.com.dreammirae.mmth.authentication.service;

import com.dreammirae.mmth.authentication.AuthMethodTypes;
import com.dreammirae.mmth.fido.Operation;
import com.dreammirae.mmth.fido.transport.GetUAFRequest;
import com.dreammirae.mmth.fido.transport.ReturnUAFRequest;

public interface IUAFRequestService {
  void generateUAFRequest(Operation paramOperation, ReturnUAFRequest paramReturnUAFRequest, GetUAFRequest paramGetUAFRequest);
  
  AuthMethodTypes getAuthMethodTypes();
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\IUAFRequestService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */