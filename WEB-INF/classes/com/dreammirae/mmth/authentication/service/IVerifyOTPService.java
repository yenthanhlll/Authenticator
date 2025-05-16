package WEB-INF.classes.com.dreammirae.mmth.authentication.service;

import com.dreammirae.mmth.authentication.AuthMethodTypes;
import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
import com.dreammirae.mmth.exception.ReturnCodeException;
import com.dreammirae.mmth.external.bean.WebApiResponse;

public interface IVerifyOTPService {
  void verifyingOTP(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator, boolean paramBoolean) throws ReturnCodeException;
  
  void verifyingOTP(WebApiResponse paramWebApiResponse, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong) throws ReturnCodeException;
  
  void verifyingEncOTP(WebApiResponse paramWebApiResponse, String paramString1, String paramString2, String paramString3) throws ReturnCodeException;
  
  void verifyingTranOTP(AuthenticationResponseLocator paramAuthenticationResponseLocator, AuthenticationRequestLocator paramAuthenticationRequestLocator, boolean paramBoolean) throws ReturnCodeException;
  
  void verifyingTranOTP(WebApiResponse paramWebApiResponse, String paramString1, String paramString2, String paramString3, String paramString4) throws ReturnCodeException;
  
  void verifyingTranEncOTP(WebApiResponse paramWebApiResponse, String paramString1, String paramString2, String paramString3, String paramString4) throws ReturnCodeException;
  
  AuthMethodTypes getAuthMethodTypes();
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\IVerifyOTPService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */