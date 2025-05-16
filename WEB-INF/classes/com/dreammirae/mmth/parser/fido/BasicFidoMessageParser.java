/*     */ package WEB-INF.classes.com.dreammirae.mmth.parser.fido;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.StatusCodes;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.fido.transport.GetUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.ReturnUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.SendUAFResponse;
/*     */ import com.dreammirae.mmth.fido.transport.ServerResponse;
/*     */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*     */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.google.gson.JsonParseException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicFidoMessageParser
/*     */   implements IUserServiceMessageParser
/*     */ {
/*     */   public AuthenticationRequestLocator parseGenRpIssueCodeReqMessage(String payload, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/*  28 */       AuthenticationRequestLocator params = (AuthenticationRequestLocator)GsonUtils.gson().fromJson(payload, AuthenticationRequestLocator.class);
/*     */       
/*  30 */       if (!params.hasUsername()) {
/*  31 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The field['userName'] is required.");
/*     */       }
/*     */ 
/*     */       
/*  35 */       UserVO.validateUsername(params.getUsername());
/*     */       
/*  37 */       return params;
/*  38 */     } catch (ReturnCodeException e) {
/*  39 */       throw e;
/*  40 */     } catch (Exception e) {
/*  41 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseGenRpIssueCodeReqMessageRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException {
/*  47 */     String strPayload = new String(payload, Commons.UTF8_CS);
/*  48 */     return parseGenRpIssueCodeReqMessage(strPayload, request);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String generateGenRpIssueCodeRespMessage(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/*  54 */     if (result == null) {
/*  55 */       result = new AuthenticationResponseLocator();
/*  56 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */     
/*  59 */     return GsonUtils.gson().toJson(result);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateGenRpIssueCodeRespMessageRaw(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/*  64 */     String json = generateGenRpIssueCodeRespMessage(result, request);
/*  65 */     return json.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */ 
/*     */   
/*     */   public GetUAFRequest parseGetUAFRequestPayload(String payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/*     */     try {
/*  71 */       GetUAFRequest getUafRequest = (GetUAFRequest)GsonUtils.gson().fromJson(payload, GetUAFRequest.class);
/*  72 */       return getUafRequest;
/*  73 */     } catch (JsonParseException e) {
/*  74 */       throw new FidoUafStatusCodeException(StatusCodes.CODE_1498, e.getMessage());
/*  75 */     } catch (FidoUafStatusCodeException e) {
/*  76 */       throw e;
/*  77 */     } catch (Exception e) {
/*  78 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GetUAFRequest parseGetUAFRequestPayloadRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/*  84 */     String strPayload = new String(payload, Commons.UTF8_CS);
/*  85 */     return parseGetUAFRequestPayload(strPayload, request);
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateReturnUAFRequestPayload(GetUAFRequest getUafRequest, ReturnUAFRequest returnUAFRequest, HttpServletRequest request) throws ReturnCodeException {
/*  90 */     return GsonUtils.gson().toJson(returnUAFRequest);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateReturnUAFRequestPayloadRaw(GetUAFRequest getUafRequest, ReturnUAFRequest returnUAFRequest, HttpServletRequest request) throws ReturnCodeException {
/*  95 */     String json = generateReturnUAFRequestPayload(getUafRequest, returnUAFRequest, request);
/*  96 */     return json.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */ 
/*     */   
/*     */   public SendUAFResponse parseSendUAFResponsePayload(Operation op, String payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/*     */     try {
/* 102 */       SendUAFResponse sendUafResponse = (SendUAFResponse)GsonUtils.gson().fromJson(payload, SendUAFResponse.class);
/* 103 */       return sendUafResponse;
/* 104 */     } catch (JsonParseException e) {
/* 105 */       throw new FidoUafStatusCodeException(StatusCodes.CODE_1498, e.getMessage());
/* 106 */     } catch (FidoUafStatusCodeException e) {
/* 107 */       throw e;
/* 108 */     } catch (Exception e) {
/* 109 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SendUAFResponse parseSendUAFResponsePayloadRaw(Operation op, byte[] payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/* 115 */     String strPayload = new String(payload, Commons.UTF8_CS);
/* 116 */     return parseSendUAFResponsePayload(op, strPayload, request);
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateServerResponsePayload(SendUAFResponse sendUafResponse, ServerResponse serverResponse, HttpServletRequest request) throws ReturnCodeException {
/* 121 */     return GsonUtils.gson().toJson(serverResponse);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateServerResponsePayloadRaw(SendUAFResponse sendUafResponse, ServerResponse serverResponse, HttpServletRequest request) throws ReturnCodeException {
/* 126 */     String json = generateServerResponsePayload(sendUafResponse, serverResponse, request);
/* 127 */     return json.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseVerifyOTPRequest(String payload, HttpServletRequest request) throws ReturnCodeException {
/* 132 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This request is not available.");
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseVerifyOTPRequestRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException {
/* 137 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This request is not available.");
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateVerifyOTPRespPayload(AuthenticationResponseLocator locator, HttpServletRequest request) throws ReturnCodeException {
/* 142 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This request is not available.");
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateVerifyOTPRespPayloadRaw(AuthenticationResponseLocator locator, HttpServletRequest request) throws ReturnCodeException {
/* 147 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This request is not available.");
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseRequestLocator(String payload, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/* 153 */       AuthenticationRequestLocator params = (AuthenticationRequestLocator)GsonUtils.gson().fromJson(payload, AuthenticationRequestLocator.class);
/* 154 */       return params;
/* 155 */     } catch (ReturnCodeException e) {
/* 156 */       throw e;
/* 157 */     } catch (Exception e) {
/* 158 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseRequestLocatorRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException {
/* 164 */     String strPayload = new String(payload, Commons.UTF8_CS);
/* 165 */     return parseRequestLocator(strPayload, request);
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateResponseLocatorPayload(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/* 170 */     return GsonUtils.gson().toJson(result);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateResponseLocatorPayloadRaw(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/* 175 */     String json = generateResponseLocatorPayload(result, request);
/* 176 */     return json.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationResponseLocator createResponseLocatorInstance() {
/* 181 */     return new AuthenticationResponseLocator();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\fido\BasicFidoMessageParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */