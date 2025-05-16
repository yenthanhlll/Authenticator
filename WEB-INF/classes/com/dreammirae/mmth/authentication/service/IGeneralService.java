package WEB-INF.classes.com.dreammirae.mmth.authentication.service;

import com.dreammirae.mmth.authentication.AuthMethodTypes;
import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;

public interface IGeneralService {
  void doForceDereg(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator);
  
  void getUserRegistrationInfo(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator);
  
  void registerTransactionInfo(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator);
  
  void getTransactionInfo(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator);
  
  void updateUserVerificationFlag(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator);
  
  void lostOtp(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator);
  
  void checkAuthResult(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator);
  
  void generateQRCode(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator);
  
  AuthMethodTypes getAuthMethodTypes();
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\IGeneralService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */