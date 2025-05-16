package WEB-INF.classes.com.dreammirae.mmth.parser;

import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
import com.dreammirae.mmth.exception.ReturnCodeException;
import com.dreammirae.mmth.fido.Operation;
import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
import com.dreammirae.mmth.fido.transport.GetUAFRequest;
import com.dreammirae.mmth.fido.transport.ReturnUAFRequest;
import com.dreammirae.mmth.fido.transport.SendUAFResponse;
import com.dreammirae.mmth.fido.transport.ServerResponse;
import javax.servlet.http.HttpServletRequest;

public interface IUserServiceMessageParser {
  AuthenticationRequestLocator parseGenRpIssueCodeReqMessage(String paramString, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  AuthenticationRequestLocator parseGenRpIssueCodeReqMessageRaw(byte[] paramArrayOfbyte, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  String generateGenRpIssueCodeRespMessage(AuthenticationResponseLocator paramAuthenticationResponseLocator, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  byte[] generateGenRpIssueCodeRespMessageRaw(AuthenticationResponseLocator paramAuthenticationResponseLocator, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  GetUAFRequest parseGetUAFRequestPayload(String paramString, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException, FidoUafStatusCodeException;
  
  GetUAFRequest parseGetUAFRequestPayloadRaw(byte[] paramArrayOfbyte, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException, FidoUafStatusCodeException;
  
  String generateReturnUAFRequestPayload(GetUAFRequest paramGetUAFRequest, ReturnUAFRequest paramReturnUAFRequest, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  byte[] generateReturnUAFRequestPayloadRaw(GetUAFRequest paramGetUAFRequest, ReturnUAFRequest paramReturnUAFRequest, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  SendUAFResponse parseSendUAFResponsePayload(Operation paramOperation, String paramString, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException, FidoUafStatusCodeException;
  
  SendUAFResponse parseSendUAFResponsePayloadRaw(Operation paramOperation, byte[] paramArrayOfbyte, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException, FidoUafStatusCodeException;
  
  String generateServerResponsePayload(SendUAFResponse paramSendUAFResponse, ServerResponse paramServerResponse, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  byte[] generateServerResponsePayloadRaw(SendUAFResponse paramSendUAFResponse, ServerResponse paramServerResponse, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  AuthenticationRequestLocator parseVerifyOTPRequest(String paramString, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  AuthenticationRequestLocator parseVerifyOTPRequestRaw(byte[] paramArrayOfbyte, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  String generateVerifyOTPRespPayload(AuthenticationResponseLocator paramAuthenticationResponseLocator, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  byte[] generateVerifyOTPRespPayloadRaw(AuthenticationResponseLocator paramAuthenticationResponseLocator, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  AuthenticationRequestLocator parseRequestLocator(String paramString, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  AuthenticationRequestLocator parseRequestLocatorRaw(byte[] paramArrayOfbyte, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  String generateResponseLocatorPayload(AuthenticationResponseLocator paramAuthenticationResponseLocator, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  byte[] generateResponseLocatorPayloadRaw(AuthenticationResponseLocator paramAuthenticationResponseLocator, HttpServletRequest paramHttpServletRequest) throws ReturnCodeException;
  
  AuthenticationResponseLocator createResponseLocatorInstance();
}


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\IUserServiceMessageParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */