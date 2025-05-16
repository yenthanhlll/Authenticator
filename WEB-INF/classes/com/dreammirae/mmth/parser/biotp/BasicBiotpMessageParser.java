/*     */ package WEB-INF.classes.com.dreammirae.mmth.parser.biotp;
/*     */ 
/*     */ import com.dreammirae.mmth.MMTHConstants;
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
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*     */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.google.gson.JsonParseException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicBiotpMessageParser
/*     */   implements IUserServiceMessageParser
/*     */ {
/*     */   public AuthenticationRequestLocator parseGenRpIssueCodeReqMessage(String payload, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/*  31 */       AuthenticationRequestLocator params = (AuthenticationRequestLocator)GsonUtils.gson().fromJson(payload, AuthenticationRequestLocator.class);
/*     */       
/*  33 */       if (!params.hasUsername()) {
/*  34 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The field['userName'] is required.");
/*     */       }
/*     */ 
/*     */       
/*  38 */       UserVO.validateUsername(params.getUsername());
/*     */       
/*  40 */       return params;
/*  41 */     } catch (ReturnCodeException e) {
/*  42 */       throw e;
/*  43 */     } catch (Exception e) {
/*  44 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseGenRpIssueCodeReqMessageRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException {
/*  50 */     String strPayload = new String(payload, Commons.UTF8_CS);
/*  51 */     return parseGenRpIssueCodeReqMessage(strPayload, request);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String generateGenRpIssueCodeRespMessage(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/*  57 */     if (result == null) {
/*  58 */       result = new AuthenticationResponseLocator();
/*  59 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */     } 
/*     */     
/*  62 */     return GsonUtils.gson().toJson(result);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateGenRpIssueCodeRespMessageRaw(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/*  67 */     String json = generateGenRpIssueCodeRespMessage(result, request);
/*  68 */     return json.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */ 
/*     */   
/*     */   public GetUAFRequest parseGetUAFRequestPayload(String payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/*     */     try {
/*  74 */       GetUAFRequest getUafRequest = (GetUAFRequest)GsonUtils.gson().fromJson(payload, GetUAFRequest.class);
/*  75 */       return getUafRequest;
/*  76 */     } catch (JsonParseException e) {
/*  77 */       throw new FidoUafStatusCodeException(StatusCodes.CODE_1498, e.getMessage());
/*  78 */     } catch (FidoUafStatusCodeException e) {
/*  79 */       throw e;
/*  80 */     } catch (Exception e) {
/*  81 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GetUAFRequest parseGetUAFRequestPayloadRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/*  87 */     String strPayload = new String(payload, Commons.UTF8_CS);
/*  88 */     return parseGetUAFRequestPayload(strPayload, request);
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateReturnUAFRequestPayload(GetUAFRequest getUafRequest, ReturnUAFRequest returnUAFRequest, HttpServletRequest request) throws ReturnCodeException {
/*  93 */     return GsonUtils.gson().toJson(returnUAFRequest);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateReturnUAFRequestPayloadRaw(GetUAFRequest getUafRequest, ReturnUAFRequest returnUAFRequest, HttpServletRequest request) throws ReturnCodeException {
/*  98 */     String json = generateReturnUAFRequestPayload(getUafRequest, returnUAFRequest, request);
/*  99 */     return json.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */ 
/*     */   
/*     */   public SendUAFResponse parseSendUAFResponsePayload(Operation op, String payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/*     */     try {
/* 105 */       SendUAFResponse sendUafResponse = (SendUAFResponse)GsonUtils.gson().fromJson(payload, SendUAFResponse.class);
/* 106 */       return sendUafResponse;
/* 107 */     } catch (JsonParseException e) {
/* 108 */       throw new FidoUafStatusCodeException(StatusCodes.CODE_1498, e.getMessage());
/* 109 */     } catch (FidoUafStatusCodeException e) {
/* 110 */       throw e;
/* 111 */     } catch (Exception e) {
/* 112 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SendUAFResponse parseSendUAFResponsePayloadRaw(Operation op, byte[] payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/* 118 */     String strPayload = new String(payload, Commons.UTF8_CS);
/* 119 */     return parseSendUAFResponsePayload(op, strPayload, request);
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateServerResponsePayload(SendUAFResponse sendUafResponse, ServerResponse serverResponse, HttpServletRequest request) throws ReturnCodeException {
/* 124 */     return GsonUtils.gson().toJson(serverResponse);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateServerResponsePayloadRaw(SendUAFResponse sendUafResponse, ServerResponse serverResponse, HttpServletRequest request) throws ReturnCodeException {
/* 129 */     String json = generateServerResponsePayload(sendUafResponse, serverResponse, request);
/* 130 */     return json.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseVerifyOTPRequest(String payload, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/* 137 */       AuthenticationRequestLocator params = (AuthenticationRequestLocator)GsonUtils.gson().fromJson(payload, AuthenticationRequestLocator.class);
/*     */       
/* 139 */       if (!params.hasUsername() && !params.hasTid()) {
/* 140 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The field['userName' or 'tid'] is required.");
/*     */       }
/*     */ 
/*     */       
/* 144 */       if (params.hasUsername()) {
/* 145 */         UserVO.validateUsername(params.getUsername());
/*     */       }
/*     */       
/* 148 */       if (params.hasTid()) {
/* 149 */         params.getTid();
/*     */       }
/*     */       
/* 152 */       if (!params.hasOtp()) {
/* 153 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.requiredParam", new Object[] { "otp" }));
/*     */       }
/*     */ 
/*     */       
/* 157 */       if (!MMTHConstants.REGEX_OTP_PATTERN.matcher(params.getOtp()).matches()) {
/*     */ 
/*     */         
/* 160 */         String otp = params.getOtp();
/* 161 */         otp = otp.toUpperCase();
/* 162 */         if (MMTHConstants.REGEX_HEX_PATTERN.matcher(otp).matches()) {
/* 163 */           params.setOtp(otp);
/*     */         } else {
/* 165 */           throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.invalidParam", new Object[] { "otp", "Must be a 6-digit number or hexadecimal string." }));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       return params;
/* 173 */     } catch (ReturnCodeException e) {
/* 174 */       throw e;
/* 175 */     } catch (Exception e) {
/* 176 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseVerifyOTPRequestRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException {
/* 182 */     String strPayload = new String(payload, Commons.UTF8_CS);
/* 183 */     return parseVerifyOTPRequest(strPayload, request);
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateVerifyOTPRespPayload(AuthenticationResponseLocator locator, HttpServletRequest request) throws ReturnCodeException {
/* 188 */     return GsonUtils.gson().toJson(locator);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateVerifyOTPRespPayloadRaw(AuthenticationResponseLocator locator, HttpServletRequest request) throws ReturnCodeException {
/* 193 */     String json = generateVerifyOTPRespPayload(locator, request);
/* 194 */     return json.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseRequestLocator(String payload, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/* 200 */       AuthenticationRequestLocator params = (AuthenticationRequestLocator)GsonUtils.gson().fromJson(payload, AuthenticationRequestLocator.class);
/* 201 */       return params;
/* 202 */     } catch (ReturnCodeException e) {
/* 203 */       throw e;
/* 204 */     } catch (Exception e) {
/* 205 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseRequestLocatorRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException {
/* 211 */     String strPayload = new String(payload, Commons.UTF8_CS);
/* 212 */     return parseRequestLocator(strPayload, request);
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateResponseLocatorPayload(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/* 217 */     return GsonUtils.gson().toJson(result);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateResponseLocatorPayloadRaw(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/* 222 */     String json = generateResponseLocatorPayload(result, request);
/* 223 */     return json.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationResponseLocator createResponseLocatorInstance() {
/* 228 */     return new AuthenticationResponseLocator();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\biotp\BasicBiotpMessageParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */