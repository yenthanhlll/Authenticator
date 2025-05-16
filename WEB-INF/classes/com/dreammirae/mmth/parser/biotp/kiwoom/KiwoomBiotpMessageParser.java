/*     */ package WEB-INF.classes.com.dreammirae.mmth.parser.biotp.kiwoom;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.fido.transport.GetUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.IUafTransport;
/*     */ import com.dreammirae.mmth.fido.transport.ReturnUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.SendUAFResponse;
/*     */ import com.dreammirae.mmth.fido.transport.ServerResponse;
/*     */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*     */ import com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class KiwoomBiotpMessageParser
/*     */   implements IUserServiceMessageParser
/*     */ {
/*     */   public AuthenticationRequestLocator parseGenRpIssueCodeReqMessage(String payload, HttpServletRequest request) throws ReturnCodeException {
/*  22 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseGenRpIssueCodeReqMessageRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException {
/*  27 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateGenRpIssueCodeRespMessage(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/*  32 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateGenRpIssueCodeRespMessageRaw(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/*  37 */     throw new ReturnCodeException(ReturnCodes.NOT_FOUND_URL, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public GetUAFRequest parseGetUAFRequestPayload(String payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/*  42 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public GetUAFRequest parseGetUAFRequestPayloadRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/*     */     try {
/*  48 */       TransportAPI type = TransportAPI.getTransportAPI(request);
/*     */       
/*  50 */       if (TransportAPI.API_1001.equals(type) || TransportAPI.API_2001
/*  51 */         .equals(type) || TransportAPI.API_3001
/*  52 */         .equals(type) || TransportAPI.API_4001
/*  53 */         .equals(type) || TransportAPI.API_4002
/*  54 */         .equals(type)) {
/*     */         
/*  56 */         if (payload == null) {
/*  57 */           throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The payload could not be null or empty.");
/*     */         }
/*     */         
/*  60 */         return (GetUAFRequest)type.parseUAFPayload(payload);
/*     */       } 
/*  62 */       throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */     }
/*  64 */     catch (ReturnCodeException e) {
/*  65 */       throw e;
/*  66 */     } catch (Exception e) {
/*  67 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateReturnUAFRequestPayload(GetUAFRequest getUafRequest, ReturnUAFRequest locator, HttpServletRequest request) throws ReturnCodeException {
/*  73 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] generateReturnUAFRequestPayloadRaw(GetUAFRequest getUafRequest, ReturnUAFRequest returnUafRequest, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/*  80 */       TransportAPI type = TransportAPI.getTransportAPI(request);
/*     */       
/*  82 */       if (TransportAPI.API_1001.equals(type) || TransportAPI.API_2001
/*  83 */         .equals(type) || TransportAPI.API_3001
/*  84 */         .equals(type) || TransportAPI.API_4001
/*  85 */         .equals(type) || TransportAPI.API_4002
/*  86 */         .equals(type))
/*     */       {
/*  88 */         return type.generateUAFPayload((IUafTransport)getUafRequest, (IUafTransport)returnUafRequest);
/*     */       }
/*  90 */       throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */     }
/*  92 */     catch (ReturnCodeException e) {
/*  93 */       throw e;
/*  94 */     } catch (Exception e) {
/*  95 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SendUAFResponse parseSendUAFResponsePayload(Operation op, String payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/* 101 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SendUAFResponse parseSendUAFResponsePayloadRaw(Operation op, byte[] payload, HttpServletRequest request) throws ReturnCodeException, FidoUafStatusCodeException {
/*     */     try {
/* 108 */       TransportAPI type = TransportAPI.getTransportAPI(request);
/*     */       
/* 110 */       if (TransportAPI.API_1002.equals(type) || TransportAPI.API_2002
/* 111 */         .equals(type) || TransportAPI.API_3002
/* 112 */         .equals(type)) {
/*     */         
/* 114 */         if (payload == null) {
/* 115 */           throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The payload could not be null or empty.");
/*     */         }
/*     */         
/* 118 */         return (SendUAFResponse)type.parseUAFPayload(payload);
/*     */       } 
/* 120 */       throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */     }
/* 122 */     catch (ReturnCodeException e) {
/* 123 */       throw e;
/* 124 */     } catch (Exception e) {
/* 125 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateServerResponsePayload(SendUAFResponse sendUafResponse, ServerResponse locator, HttpServletRequest request) throws ReturnCodeException {
/* 131 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateServerResponsePayloadRaw(SendUAFResponse sendUafResponse, ServerResponse serverResponse, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/* 137 */       TransportAPI type = TransportAPI.getTransportAPI(request);
/*     */       
/* 139 */       if (TransportAPI.API_1002.equals(type) || TransportAPI.API_2002
/* 140 */         .equals(type) || TransportAPI.API_3002
/* 141 */         .equals(type))
/*     */       {
/* 143 */         return type.generateUAFPayload((IUafTransport)sendUafResponse, (IUafTransport)serverResponse);
/*     */       }
/* 145 */       throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */     }
/* 147 */     catch (ReturnCodeException e) {
/* 148 */       throw e;
/* 149 */     } catch (Exception e) {
/* 150 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseVerifyOTPRequest(String payload, HttpServletRequest request) throws ReturnCodeException {
/* 156 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseVerifyOTPRequestRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/* 162 */       TransportAPI type = TransportAPI.getTransportAPI(request);
/*     */       
/* 164 */       if (TransportAPI.API_9002.equals(type) || TransportAPI.API_9003
/* 165 */         .equals(type) || TransportAPI.API_9005
/* 166 */         .equals(type)) {
/*     */         
/* 168 */         if (payload == null) {
/* 169 */           throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The payload could not be null or empty.");
/*     */         }
/*     */         
/* 172 */         return type.parsePayload(payload);
/*     */       } 
/* 174 */       throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */     }
/* 176 */     catch (ReturnCodeException e) {
/* 177 */       throw e;
/* 178 */     } catch (Exception e) {
/* 179 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateVerifyOTPRespPayload(AuthenticationResponseLocator locator, HttpServletRequest request) throws ReturnCodeException {
/* 185 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateVerifyOTPRespPayloadRaw(AuthenticationResponseLocator respLoc, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/* 191 */       TransportAPI type = TransportAPI.getTransportAPI(request);
/*     */       
/* 193 */       if (TransportAPI.API_9002.equals(type) || TransportAPI.API_9003
/* 194 */         .equals(type) || TransportAPI.API_9005
/* 195 */         .equals(type))
/*     */       {
/*     */         
/* 198 */         return type.generatePayload(respLoc);
/*     */       }
/* 200 */       throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */     }
/* 202 */     catch (ReturnCodeException e) {
/* 203 */       throw e;
/* 204 */     } catch (Exception e) {
/* 205 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseRequestLocator(String payload, HttpServletRequest request) throws ReturnCodeException {
/* 211 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationRequestLocator parseRequestLocatorRaw(byte[] payload, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/* 217 */       TransportAPI type = TransportAPI.getTransportAPI(request);
/*     */       
/* 219 */       if (TransportAPI.API_5001.equals(type) || TransportAPI.API_5002
/* 220 */         .equals(type) || TransportAPI.API_5003
/* 221 */         .equals(type) || TransportAPI.API_9001
/* 222 */         .equals(type) || TransportAPI.API_9004
/* 223 */         .equals(type)) {
/*     */         
/* 225 */         if (payload == null) {
/* 226 */           throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The payload could not be null or empty.");
/*     */         }
/*     */         
/* 229 */         return type.parsePayload(payload);
/*     */       } 
/* 231 */       throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */     }
/* 233 */     catch (ReturnCodeException e) {
/* 234 */       throw e;
/* 235 */     } catch (Exception e) {
/* 236 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String generateResponseLocatorPayload(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/* 242 */     throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateResponseLocatorPayloadRaw(AuthenticationResponseLocator respLoc, HttpServletRequest request) throws ReturnCodeException {
/*     */     try {
/* 248 */       TransportAPI type = TransportAPI.getTransportAPI(request);
/*     */       
/* 250 */       if (TransportAPI.API_5001.equals(type) || TransportAPI.API_5002
/* 251 */         .equals(type) || TransportAPI.API_5003
/* 252 */         .equals(type) || TransportAPI.API_9001
/* 253 */         .equals(type) || TransportAPI.API_9004
/* 254 */         .equals(type))
/*     */       {
/* 256 */         return type.generatePayload(respLoc);
/*     */       }
/* 258 */       throw new ReturnCodeException(ReturnCodes.BAD_REQUEST, "This request is invalid.");
/*     */     }
/* 260 */     catch (ReturnCodeException e) {
/* 261 */       throw e;
/* 262 */     } catch (Exception e) {
/* 263 */       throw new ReturnCodeException(ReturnCodes.INTERNAL_SERVER_ERROR, e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthenticationResponseLocator createResponseLocatorInstance() {
/* 269 */     return new AuthenticationResponseLocator();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\biotp\kiwoom\KiwoomBiotpMessageParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */