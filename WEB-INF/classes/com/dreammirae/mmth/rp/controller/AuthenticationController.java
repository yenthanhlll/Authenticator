/*     */ package WEB-INF.classes.com.dreammirae.mmth.rp.controller;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*     */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*     */ import com.dreammirae.mmth.authentication.service.IGeneralService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFRequestService;
/*     */ import com.dreammirae.mmth.authentication.service.IUAFResponseService;
/*     */ import com.dreammirae.mmth.authentication.service.IVerifyOTPService;
/*     */ import com.dreammirae.mmth.authentication.service.IssueCodeService;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.StatusCodes;
/*     */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*     */ import com.dreammirae.mmth.fido.transport.GetUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.ReturnUAFRequest;
/*     */ import com.dreammirae.mmth.fido.transport.SendUAFResponse;
/*     */ import com.dreammirae.mmth.fido.transport.ServerResponse;
/*     */ import com.dreammirae.mmth.parser.IUserServiceMessageParser;
/*     */ import com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AuthenticationController
/*     */ {
/*  32 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.rp.controller.AuthenticationController.class);
/*     */   
/*  34 */   protected static final DecimalFormat THOUSAND_NUMBER_FORMAT = new DecimalFormat("#,##0");
/*     */   
/*     */   protected static final String LOG_FORMAT_REQ_URI_MSG = "## RECEIVED MSG [AuthMethodType=%s, RequestURI=%s] :: %s";
/*     */   
/*     */   protected static final String LOG_FORMAT_RESP_URI_MSG = "## SEND MSG [AuthMethodType=%s, RequestURI=%s, LapTime=%,d ms] :: %s";
/*     */   
/*     */   protected static final String LOG_FORMAT_ERROR_URI_MSG = "## ERROR MSG [AuthMethodType=%s, RequestURI=%s] :: %s";
/*     */   private static final String READABLE_LOG_FORMAT_MSG_SEPARATOR = "] :: ";
/*     */   private static final String READABLE_LOG_FORMAT_MSG_URI = "URI=";
/*     */   private static final String READABLE_LOG_FORMAT_MSG_API = "API=";
/*     */   private static final String READABLE_LOG_FORMAT_PARSED_REQ_MSG_PREFIX = "$$$ PARSED_RECV_MSG [";
/*     */   private static final String READABLE_LOG_FORMAT_RESP_PREFIX = "$$$ SEND_RESULT_MSG [";
/*     */   
/*     */   protected String generateIssueCodeImp(String payload, HttpServletRequest httpRequest) {
/*  48 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/*  51 */     AuthenticationResponseLocator result = parser.createResponseLocatorInstance();
/*     */     
/*  53 */     String json = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  58 */       AuthenticationRequestLocator params = parser.parseGenRpIssueCodeReqMessage(payload, httpRequest);
/*     */ 
/*     */       
/*  61 */       LOG.info(genReadableReqLogMsg(httpRequest, params.toString()));
/*     */ 
/*     */       
/*  64 */       getIssueCodeService().generateIssueCode(getAuthMethodType(), result, params);
/*     */ 
/*     */       
/*  67 */       LOG.info(genReadableRespLogMsg(httpRequest, result.toString()));
/*     */ 
/*     */       
/*  70 */       json = parser.generateGenRpIssueCodeRespMessage(result, httpRequest);
/*     */     }
/*  72 */     catch (Throwable e) {
/*     */ 
/*     */       
/*  75 */       if (e instanceof ReturnCodeException) {
/*  76 */         result.setReturnCode(((ReturnCodeException)e).getReturnCode());
/*  77 */       } else if (e.getCause() instanceof ReturnCodeException) {
/*  78 */         result.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/*  80 */         result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/*  84 */       LOG.error(genReadableErrorLogMsg(httpRequest, result.toString(), e));
/*     */       
/*  86 */       json = parser.generateGenRpIssueCodeRespMessage(result, httpRequest);
/*     */     } 
/*     */ 
/*     */     
/*  90 */     return json;
/*     */   }
/*     */   private static final String READABLE_LOG_FORMAT_ERROR_MSG_PREFIX = "$$$ ERROR_MSG ["; private static final String READABLE_LOG_FORMAT_ERROR_MSG_EX = ", ERR="; private static final String LOG_FORMAT_MSG_URI = ", URI="; private static final String LOG_FORMAT_MSG_LEN = ", LEN="; private static final String LOG_FORMAT_MSG_SUFFIX = "] "; private static final String LOG_FORMAT_REQ_MSG_PREFIX = "### REQ MSG [AuthMethod="; private static final String LOG_FORMAT_RESP_MSG_PREFIX = "### RESP MSG [AuthMethod="; private static final String LOG_FORMAT_RESP_MSG_LAPTIME = "] LapTime=";
/*     */   private static final String LOG_FORMAT_RESP_MSG_SUFFIX = " ms ";
/*     */   
/*     */   protected byte[] generateIssueCodeImp(byte[] payload, HttpServletRequest httpRequest) {
/*  96 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/*  99 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 101 */     byte[] respResult = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 106 */       AuthenticationRequestLocator reqLoc = parser.parseGenRpIssueCodeReqMessageRaw(payload, httpRequest);
/*     */ 
/*     */       
/* 109 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 112 */       getIssueCodeService().generateIssueCode(getAuthMethodType(), respLoc, reqLoc);
/*     */ 
/*     */       
/* 115 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 118 */       respResult = parser.generateGenRpIssueCodeRespMessageRaw(respLoc, httpRequest);
/*     */     }
/* 120 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 123 */       if (e instanceof ReturnCodeException) {
/* 124 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 125 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 126 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 128 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 132 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 134 */       respResult = parser.generateGenRpIssueCodeRespMessageRaw(respLoc, httpRequest);
/*     */     } 
/*     */     
/* 137 */     return respResult;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String generateUAFRequestImp(String payload, HttpServletRequest httpRequest) {
/* 142 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */     
/* 144 */     String json = null;
/*     */ 
/*     */     
/* 147 */     ReturnUAFRequest returnUAFRequest = new ReturnUAFRequest();
/*     */     
/* 149 */     GetUAFRequest getUAFRequest = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 154 */       getUAFRequest = parser.parseGetUAFRequestPayload(payload, httpRequest);
/*     */ 
/*     */       
/* 157 */       LOG.info(genReadableReqLogMsg(httpRequest, getUAFRequest.toShorten()));
/*     */ 
/*     */       
/* 160 */       returnUAFRequest.setOp(getUAFRequest.getOp());
/* 161 */       getUAFRequestService().generateUAFRequest(getUAFRequest.getOp(), returnUAFRequest, getUAFRequest);
/*     */ 
/*     */       
/* 164 */       LOG.info(genReadableRespLogMsg(httpRequest, returnUAFRequest.toShorten()));
/*     */ 
/*     */       
/* 167 */       json = parser.generateReturnUAFRequestPayload(getUAFRequest, returnUAFRequest, httpRequest);
/*     */     
/*     */     }
/* 170 */     catch (Throwable e) {
/*     */       
/* 172 */       if (e instanceof FidoUafStatusCodeException) {
/* 173 */         returnUAFRequest.setStatusCode(((FidoUafStatusCodeException)e).getStatusCode());
/* 174 */       } else if (e instanceof ReturnCodeException) {
/* 175 */         returnUAFRequest.setStatusCode(((ReturnCodeException)e).getReturnCode().getStatusCodes());
/* 176 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 177 */         returnUAFRequest.setStatusCode(((ReturnCodeException)e.getCause()).getReturnCode().getStatusCodes());
/*     */       } else {
/* 179 */         returnUAFRequest.setStatusCode(ReturnCodes.INTERNAL_SERVER_ERROR.getStatusCodes());
/*     */       } 
/*     */ 
/*     */       
/* 183 */       LOG.error(genReadableErrorLogMsg(httpRequest, returnUAFRequest.toShorten(), e));
/*     */       
/* 185 */       json = parser.generateReturnUAFRequestPayload(getUAFRequest, returnUAFRequest, httpRequest);
/*     */     } 
/*     */     
/* 188 */     return json;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] generateUAFRequestImp(byte[] payload, HttpServletRequest httpRequest) {
/* 193 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */     
/* 195 */     byte[] respResult = null;
/*     */ 
/*     */     
/* 198 */     ReturnUAFRequest returnUAFRequest = new ReturnUAFRequest();
/*     */     
/* 200 */     GetUAFRequest getUAFRequest = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 205 */       getUAFRequest = parser.parseGetUAFRequestPayloadRaw(payload, httpRequest);
/*     */ 
/*     */       
/* 208 */       LOG.info(genReadableReqLogMsg(httpRequest, getUAFRequest.toShorten()));
/*     */ 
/*     */       
/* 211 */       returnUAFRequest.setOp(getUAFRequest.getOp());
/* 212 */       getUAFRequestService().generateUAFRequest(getUAFRequest.getOp(), returnUAFRequest, getUAFRequest);
/*     */ 
/*     */       
/* 215 */       LOG.info(genReadableRespLogMsg(httpRequest, returnUAFRequest.toShorten()));
/*     */ 
/*     */       
/* 218 */       respResult = parser.generateReturnUAFRequestPayloadRaw(getUAFRequest, returnUAFRequest, httpRequest);
/*     */     }
/* 220 */     catch (Throwable e) {
/*     */       
/* 222 */       if (e instanceof FidoUafStatusCodeException) {
/* 223 */         returnUAFRequest.setStatusCode(((FidoUafStatusCodeException)e).getStatusCode());
/* 224 */       } else if (e instanceof ReturnCodeException) {
/* 225 */         returnUAFRequest.setStatusCode(((ReturnCodeException)e).getReturnCode().getStatusCodes());
/* 226 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 227 */         returnUAFRequest.setStatusCode(((ReturnCodeException)e.getCause()).getReturnCode().getStatusCodes());
/*     */       } else {
/* 229 */         returnUAFRequest.setStatusCode(ReturnCodes.INTERNAL_SERVER_ERROR.getStatusCodes());
/*     */       } 
/*     */ 
/*     */       
/* 233 */       LOG.error(genReadableErrorLogMsg(httpRequest, returnUAFRequest.toShorten(), e));
/*     */       
/* 235 */       respResult = parser.generateReturnUAFRequestPayloadRaw(getUAFRequest, returnUAFRequest, httpRequest);
/*     */     } 
/*     */     
/* 238 */     return respResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String verifyingUAFResponseImp(Operation op, String payload, HttpServletRequest httpRequest) {
/* 245 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */     
/* 247 */     String json = null;
/*     */     
/* 249 */     ServerResponse serverResponse = new ServerResponse();
/*     */ 
/*     */     
/*     */     try {
/* 253 */       if (Operation.Dereg.equals(op)) {
/* 254 */         throw new FidoUafStatusCodeException(StatusCodes.CODE_1404, "Dereg has not response message.");
/*     */       }
/*     */ 
/*     */       
/* 258 */       SendUAFResponse sendUafResponse = parser.parseSendUAFResponsePayload(op, payload, httpRequest);
/*     */ 
/*     */       
/* 261 */       LOG.info(genReadableReqLogMsg(httpRequest, sendUafResponse.toShorten()));
/*     */ 
/*     */       
/* 264 */       getUAFResponseService().verifyingUAFResponse(op, serverResponse, sendUafResponse);
/*     */ 
/*     */       
/* 267 */       LOG.info(genReadableRespLogMsg(httpRequest, serverResponse.toShorten()));
/*     */ 
/*     */       
/* 270 */       json = parser.generateServerResponsePayload(sendUafResponse, serverResponse, httpRequest);
/*     */     }
/* 272 */     catch (Throwable e) {
/*     */       
/* 274 */       if (e instanceof FidoUafStatusCodeException) {
/* 275 */         serverResponse.setStatusCode(((FidoUafStatusCodeException)e).getStatusCode());
/* 276 */       } else if (e instanceof ReturnCodeException) {
/* 277 */         serverResponse.setStatusCode(((ReturnCodeException)e).getReturnCode().getStatusCodes());
/* 278 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 279 */         serverResponse.setStatusCode(((ReturnCodeException)e.getCause()).getReturnCode().getStatusCodes());
/*     */       } else {
/* 281 */         serverResponse.setStatusCode(ReturnCodes.INTERNAL_SERVER_ERROR.getStatusCodes());
/*     */       } 
/*     */ 
/*     */       
/* 285 */       LOG.error(genReadableErrorLogMsg(httpRequest, serverResponse.toShorten(), e));
/*     */       
/* 287 */       json = parser.generateServerResponsePayload(null, serverResponse, httpRequest);
/*     */     } 
/*     */ 
/*     */     
/* 291 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] verifyingUAFResponseImp(Operation op, byte[] payload, HttpServletRequest httpRequest) {
/* 297 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */     
/* 299 */     byte[] respResult = null;
/*     */     
/* 301 */     ServerResponse serverResponse = new ServerResponse();
/*     */ 
/*     */     
/*     */     try {
/* 305 */       if (Operation.Dereg.equals(op)) {
/* 306 */         throw new FidoUafStatusCodeException(StatusCodes.CODE_1404, "Dereg has not response message.");
/*     */       }
/*     */ 
/*     */       
/* 310 */       SendUAFResponse sendUafResponse = parser.parseSendUAFResponsePayloadRaw(op, payload, httpRequest);
/*     */ 
/*     */       
/* 313 */       LOG.info(genReadableReqLogMsg(httpRequest, sendUafResponse.toShorten()));
/*     */ 
/*     */       
/* 316 */       getUAFResponseService().verifyingUAFResponse(op, serverResponse, sendUafResponse);
/*     */ 
/*     */       
/* 319 */       LOG.info(genReadableRespLogMsg(httpRequest, serverResponse.toShorten()));
/*     */ 
/*     */       
/* 322 */       respResult = parser.generateServerResponsePayloadRaw(sendUafResponse, serverResponse, httpRequest);
/*     */     }
/* 324 */     catch (Throwable e) {
/*     */       
/* 326 */       if (e instanceof FidoUafStatusCodeException) {
/* 327 */         serverResponse.setStatusCode(((FidoUafStatusCodeException)e).getStatusCode());
/* 328 */       } else if (e instanceof ReturnCodeException) {
/* 329 */         serverResponse.setStatusCode(((ReturnCodeException)e).getReturnCode().getStatusCodes());
/* 330 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 331 */         serverResponse.setStatusCode(((ReturnCodeException)e.getCause()).getReturnCode().getStatusCodes());
/*     */       } else {
/* 333 */         serverResponse.setStatusCode(ReturnCodes.INTERNAL_SERVER_ERROR.getStatusCodes());
/*     */       } 
/*     */ 
/*     */       
/* 337 */       LOG.error(genReadableErrorLogMsg(httpRequest, serverResponse.toShorten(), e));
/*     */       
/* 339 */       respResult = parser.generateServerResponsePayloadRaw(null, serverResponse, httpRequest);
/*     */     } 
/*     */     
/* 342 */     return respResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String verifyingOTPImp(String payload, HttpServletRequest httpRequest, boolean encOTP) {
/* 350 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 353 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 355 */     String json = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 360 */       AuthenticationRequestLocator reqLoc = parser.parseVerifyOTPRequest(payload, httpRequest);
/*     */ 
/*     */       
/* 363 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 366 */       getVerifyOTPService().verifyingOTP(respLoc, reqLoc, encOTP);
/* 367 */       respLoc.setReturnCode(ReturnCodes.OK);
/*     */ 
/*     */       
/* 370 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 373 */       json = parser.generateVerifyOTPRespPayload(respLoc, httpRequest);
/*     */     }
/* 375 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 378 */       if (e instanceof ReturnCodeException) {
/* 379 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 380 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 381 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 383 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 387 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 389 */       json = parser.generateVerifyOTPRespPayload(respLoc, httpRequest);
/*     */     } 
/*     */     
/* 392 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] verifyingOTPImp(byte[] payload, HttpServletRequest httpRequest, boolean encOTP) {
/* 398 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 401 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 403 */     byte[] respResult = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 408 */       AuthenticationRequestLocator reqLoc = parser.parseVerifyOTPRequestRaw(payload, httpRequest);
/*     */ 
/*     */       
/* 411 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 414 */       getVerifyOTPService().verifyingOTP(respLoc, reqLoc, encOTP);
/* 415 */       respLoc.setReturnCode(ReturnCodes.OK);
/*     */ 
/*     */       
/* 418 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 421 */       respResult = parser.generateVerifyOTPRespPayloadRaw(respLoc, httpRequest);
/*     */     }
/* 423 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 426 */       if (e instanceof ReturnCodeException) {
/* 427 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 428 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 429 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 431 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 435 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 437 */       respResult = parser.generateVerifyOTPRespPayloadRaw(respLoc, httpRequest);
/*     */     } 
/*     */ 
/*     */     
/* 441 */     return respResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String verifyingTranOTPImp(String payload, HttpServletRequest httpRequest, boolean encOTP) {
/* 448 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 451 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 453 */     String json = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 458 */       AuthenticationRequestLocator reqLoc = parser.parseVerifyOTPRequest(payload, httpRequest);
/*     */ 
/*     */       
/* 461 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 464 */       getVerifyOTPService().verifyingTranOTP(respLoc, reqLoc, encOTP);
/*     */       
/* 466 */       respLoc.setReturnCode(ReturnCodes.OK);
/*     */ 
/*     */       
/* 469 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 472 */       json = parser.generateVerifyOTPRespPayload(respLoc, httpRequest);
/*     */     }
/* 474 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 477 */       if (e instanceof ReturnCodeException) {
/* 478 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 479 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 480 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 482 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 486 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 488 */       json = parser.generateVerifyOTPRespPayload(respLoc, httpRequest);
/*     */     } 
/*     */     
/* 491 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] verifyingTranOTPImp(byte[] payload, HttpServletRequest httpRequest, boolean encOTP) {
/* 497 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 500 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 502 */     byte[] respResult = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 507 */       AuthenticationRequestLocator reqLoc = parser.parseVerifyOTPRequestRaw(payload, httpRequest);
/*     */ 
/*     */       
/* 510 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 513 */       getVerifyOTPService().verifyingTranOTP(respLoc, reqLoc, encOTP);
/* 514 */       respLoc.setReturnCode(ReturnCodes.OK);
/*     */ 
/*     */       
/* 517 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 520 */       respResult = parser.generateVerifyOTPRespPayloadRaw(respLoc, httpRequest);
/*     */     }
/* 522 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 525 */       if (e instanceof ReturnCodeException) {
/* 526 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 527 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 528 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 530 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 534 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 536 */       respResult = parser.generateVerifyOTPRespPayloadRaw(respLoc, httpRequest);
/*     */     } 
/*     */     
/* 539 */     return respResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String doForceDeregImp(String payload, HttpServletRequest httpRequest) {
/* 546 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 549 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 551 */     String json = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 556 */       AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*     */ 
/*     */       
/* 559 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 562 */       getGeneralService().doForceDereg(respLoc, reqLoc);
/*     */ 
/*     */       
/* 565 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 568 */       json = parser.generateResponseLocatorPayload(respLoc, httpRequest);
/*     */     }
/* 570 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 573 */       if (e instanceof ReturnCodeException) {
/* 574 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 575 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 576 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 578 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 582 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 584 */       json = parser.generateResponseLocatorPayload(respLoc, httpRequest);
/*     */     } 
/*     */     
/* 587 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] doForceDeregImp(byte[] payload, HttpServletRequest httpRequest) {
/* 593 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 596 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 598 */     byte[] respPayload = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 603 */       AuthenticationRequestLocator reqLoc = parser.parseRequestLocatorRaw(payload, httpRequest);
/*     */ 
/*     */       
/* 606 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 609 */       getGeneralService().doForceDereg(respLoc, reqLoc);
/*     */ 
/*     */       
/* 612 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 615 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     }
/* 617 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 620 */       if (e instanceof ReturnCodeException) {
/* 621 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 622 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 623 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 625 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 629 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 631 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     } 
/*     */ 
/*     */     
/* 635 */     return respPayload;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getUserRegistrationInfoImp(String payload, HttpServletRequest httpRequest) {
/* 642 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 645 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 647 */     String json = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 652 */       AuthenticationRequestLocator reqLoc = parser.parseRequestLocator(payload, httpRequest);
/*     */ 
/*     */       
/* 655 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 658 */       getGeneralService().getUserRegistrationInfo(respLoc, reqLoc);
/*     */ 
/*     */       
/* 661 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 664 */       json = parser.generateResponseLocatorPayload(respLoc, httpRequest);
/*     */     }
/* 666 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 669 */       if (e instanceof ReturnCodeException) {
/* 670 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 671 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 672 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 674 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 678 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 680 */       json = parser.generateResponseLocatorPayload(respLoc, httpRequest);
/*     */     } 
/*     */     
/* 683 */     return json;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getUserRegistrationInfoImp(byte[] payload, HttpServletRequest httpRequest) {
/* 689 */     IUserServiceMessageParser parser = getAuthMethodType().getUserServiceMessageParser();
/*     */ 
/*     */     
/* 692 */     AuthenticationResponseLocator respLoc = parser.createResponseLocatorInstance();
/*     */     
/* 694 */     byte[] respPayload = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 699 */       AuthenticationRequestLocator reqLoc = parser.parseRequestLocatorRaw(payload, httpRequest);
/*     */ 
/*     */       
/* 702 */       LOG.info(genReadableReqLogMsg(httpRequest, reqLoc.toString()));
/*     */ 
/*     */       
/* 705 */       getGeneralService().getUserRegistrationInfo(respLoc, reqLoc);
/*     */ 
/*     */       
/* 708 */       LOG.info(genReadableRespLogMsg(httpRequest, respLoc.toString()));
/*     */ 
/*     */       
/* 711 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     }
/* 713 */     catch (Throwable e) {
/*     */ 
/*     */       
/* 716 */       if (e instanceof ReturnCodeException) {
/* 717 */         respLoc.setReturnCode(((ReturnCodeException)e).getReturnCode());
/* 718 */       } else if (e.getCause() instanceof ReturnCodeException) {
/* 719 */         respLoc.setReturnCode(((ReturnCodeException)e.getCause()).getReturnCode());
/*     */       } else {
/* 721 */         respLoc.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*     */       } 
/*     */ 
/*     */       
/* 725 */       LOG.error(genReadableErrorLogMsg(httpRequest, respLoc.toString(), e));
/*     */       
/* 727 */       respPayload = parser.generateResponseLocatorPayloadRaw(respLoc, httpRequest);
/*     */     } 
/*     */ 
/*     */     
/* 731 */     return respPayload;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract AuthMethodTypes getAuthMethodType();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract IUAFRequestService getUAFRequestService();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract IUAFResponseService getUAFResponseService();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract IVerifyOTPService getVerifyOTPService();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract IGeneralService getGeneralService();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract IssueCodeService getIssueCodeService();
/*     */ 
/*     */ 
/*     */   
/*     */   protected String genReadableReqLogMsg(HttpServletRequest request, String readableMsg) {
/* 761 */     StringBuilder sb = new StringBuilder(128);
/*     */     
/*     */     try {
/* 764 */       sb.append("$$$ PARSED_RECV_MSG [");
/*     */       
/*     */       Object obj;
/* 767 */       if (obj = request.getAttribute(TransportAPI.class.getName()) instanceof TransportAPI) {
/* 768 */         sb.append("API=").append(((TransportAPI)obj).getCode());
/*     */       } else {
/* 770 */         sb.append("URI=").append(request.getRequestURI());
/*     */       } 
/*     */       
/* 773 */       sb.append("] :: ").append(readableMsg);
/* 774 */       String str = sb.toString();
/* 775 */       return str;
/*     */     } finally {
/* 777 */       sb.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String genReadableRespLogMsg(HttpServletRequest request, String readableMsg) {
/* 785 */     StringBuilder sb = new StringBuilder(128);
/*     */     
/*     */     try {
/* 788 */       sb.append("$$$ SEND_RESULT_MSG [");
/*     */       
/*     */       Object obj;
/* 791 */       if (obj = request.getAttribute(TransportAPI.class.getName()) instanceof TransportAPI) {
/* 792 */         sb.append("API=").append(((TransportAPI)obj).getCode());
/*     */       } else {
/* 794 */         sb.append("URI=").append(request.getRequestURI());
/*     */       } 
/*     */       
/* 797 */       sb.append("] :: ").append(readableMsg);
/* 798 */       String str = sb.toString();
/* 799 */       return str;
/*     */     } finally {
/* 801 */       sb.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String genReadableErrorLogMsg(HttpServletRequest request, String readableMsg, Throwable ex) {
/* 809 */     StringBuilder sb = new StringBuilder(128);
/*     */     
/*     */     try {
/* 812 */       sb.append("$$$ ERROR_MSG [");
/*     */       
/*     */       Object obj;
/* 815 */       if (obj = request.getAttribute(TransportAPI.class.getName()) instanceof TransportAPI) {
/* 816 */         sb.append("API=").append(((TransportAPI)obj).getCode());
/*     */       } else {
/* 818 */         sb.append("URI=").append(request.getRequestURI());
/*     */       } 
/*     */       
/* 821 */       sb.append("] :: ").append(readableMsg);
/* 822 */       sb.append(", ERR=").append(ex.getMessage());
/* 823 */       String str = sb.toString();
/* 824 */       return str;
/*     */     } finally {
/* 826 */       sb.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String genReceivedReqLogMsg(byte[] payload, HttpServletRequest httpRequest) {
/* 845 */     StringBuilder sb = new StringBuilder(64);
/*     */     
/*     */     try {
/* 848 */       sb.append("### REQ MSG [AuthMethod=").append(getAuthMethodType().name())
/* 849 */         .append(", URI=").append(httpRequest.getRequestURI())
/* 850 */         .append(", LEN=");
/*     */       
/* 852 */       if (payload == null) {
/* 853 */         sb.append(0);
/*     */       } else {
/* 855 */         sb.append(THOUSAND_NUMBER_FORMAT.format(payload.length));
/*     */       } 
/*     */       
/* 858 */       sb.append("] ");
/* 859 */       String str = sb.toString();
/* 860 */       return str;
/*     */     } finally {
/* 862 */       sb.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String genReceivedReqLogMsg(String payload, HttpServletRequest httpRequest) {
/* 868 */     StringBuilder sb = new StringBuilder(64);
/*     */     
/*     */     try {
/* 871 */       sb.append("### REQ MSG [AuthMethod=").append(getAuthMethodType().name())
/* 872 */         .append(", URI=").append(httpRequest.getRequestURI())
/* 873 */         .append(", LEN=");
/*     */       
/* 875 */       if (payload == null) {
/* 876 */         sb.append(0);
/*     */       } else {
/* 878 */         sb.append(THOUSAND_NUMBER_FORMAT.format(payload.length()));
/*     */       } 
/*     */       
/* 881 */       sb.append("] ").append(payload);
/*     */       
/* 883 */       String str = sb.toString();
/* 884 */       return str;
/*     */     } finally {
/* 886 */       sb.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String genSendRespLogMsg(byte[] respPayload, long startMillis, HttpServletRequest httpRequest) {
/* 896 */     StringBuilder sb = new StringBuilder(64);
/*     */ 
/*     */     
/*     */     try {
/* 900 */       sb.append("### RESP MSG [AuthMethod=").append(getAuthMethodType().name())
/* 901 */         .append(", URI=").append(httpRequest.getRequestURI())
/* 902 */         .append(", LEN=");
/*     */ 
/*     */       
/* 905 */       if (respPayload == null) {
/* 906 */         sb.append(0);
/*     */       } else {
/* 908 */         sb.append(THOUSAND_NUMBER_FORMAT.format(respPayload.length));
/*     */       } 
/*     */       
/* 911 */       sb.append("] LapTime=");
/* 912 */       sb.append(THOUSAND_NUMBER_FORMAT.format(System.currentTimeMillis() - startMillis)).append(" ms ");
/* 913 */       String str = sb.toString();
/* 914 */       return str;
/*     */     } finally {
/* 916 */       sb.setLength(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String genSendRespLogMsg(String respPayload, long startMillis, HttpServletRequest httpRequest) {
/* 922 */     StringBuilder sb = new StringBuilder(64);
/*     */ 
/*     */     
/*     */     try {
/* 926 */       sb.append("### RESP MSG [AuthMethod=").append(getAuthMethodType().name())
/* 927 */         .append(", URI=").append(httpRequest.getRequestURI())
/* 928 */         .append(", LEN=");
/*     */ 
/*     */       
/* 931 */       if (respPayload == null) {
/* 932 */         sb.append(0);
/*     */       } else {
/* 934 */         sb.append(THOUSAND_NUMBER_FORMAT.format(respPayload.length()));
/*     */       } 
/*     */       
/* 937 */       sb.append("] LapTime=");
/* 938 */       sb.append(THOUSAND_NUMBER_FORMAT.format(System.currentTimeMillis() - startMillis)).append(" ms ");
/* 939 */       sb.append(" :: ").append(respPayload);
/*     */       
/* 941 */       String str = sb.toString();
/* 942 */       return str;
/*     */     } finally {
/* 944 */       sb.setLength(0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\controller\AuthenticationController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */