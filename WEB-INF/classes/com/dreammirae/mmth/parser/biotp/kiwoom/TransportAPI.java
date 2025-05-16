/*      */ package WEB-INF.classes.com.dreammirae.mmth.parser.biotp.kiwoom;
/*      */ 
/*      */ import com.dreammirae.mmth.MMTHConstants;
/*      */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*      */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*      */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*      */ import com.dreammirae.mmth.config.Commons;
/*      */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*      */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*      */ import com.dreammirae.mmth.fido.transport.GetUAFRequest;
/*      */ import com.dreammirae.mmth.fido.transport.IUafTransport;
/*      */ import com.dreammirae.mmth.fido.transport.ReturnUAFRequest;
/*      */ import com.dreammirae.mmth.fido.transport.SendUAFResponse;
/*      */ import com.dreammirae.mmth.fido.transport.ServerResponse;
/*      */ import com.dreammirae.mmth.misc.Base64Utils;
/*      */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*      */ import com.google.gson.JsonParseException;
/*      */ import java.util.Arrays;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public enum TransportAPI
/*      */ {
/*   37 */   API_1001("1001"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   53 */   API_1002("1002"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   API_2001("2001"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   API_2002("2002"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   API_3001("3001"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   API_3002("3002"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  161 */   API_4001("4001"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  203 */   API_4002("4002"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  246 */   API_5001("5001"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  313 */   API_5002("5002"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  388 */   API_5003("5003"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  465 */   API_9001("9001"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  509 */   API_9002("9002"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  571 */   API_9003("9003"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  639 */   API_9004("9004"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  680 */   API_9005("9005");
/*      */   
/*      */   private static final Logger LOG;
/*      */   
/*      */   private static final int LEN_RETURN_CODE = 4;
/*      */   
/*      */   private static final int LEN_DEVICE_ID = 64;
/*      */   
/*      */   private static final int LEN_DEVICE_TYPE = 2;
/*      */   
/*      */   private static final int LEN_DEVICE_MODEL = 32;
/*      */   
/*      */   private static final int LEN_OTP_SN = 12;
/*      */   
/*      */   private static final int LEN_PKG_NAME = 128;
/*      */   
/*      */   private static final int LEN_USER_ID = 32;
/*      */   
/*      */   private static final int LEN_FAIL_CNT = 2;
/*      */   
/*      */   private static final int LEN_TRAN_INFO_YN = 1;
/*      */   
/*      */   private static final int LEN_TRAN_INFO = 64;
/*      */   
/*      */   private static final int LEN_TRAN_INFO_ORG = 2048;
/*      */   
/*      */   private static final int LEN_TRAN_INFO_ENC = 2048;
/*      */   
/*      */   private static final int LEN_OTP = 6;
/*      */   
/*      */   private static final int LEN_ENC_OTP = 32;
/*      */   
/*      */   private static final int LEN_UV_TF_CNT = 6;
/*      */   
/*      */   private static final String[] FAIL_CNT_AS_STRING;
/*      */   
/*      */   private static final String UA_TF_CNT_NONE = "000000";
/*      */   
/*      */   private static final char ZERO_PADDING = '0';
/*      */   private static final int LEN_1002_RESP_PREFIX = 242;
/*      */   private static final int LEN_4001_RESP_PREFIX = 242;
/*      */   private static final int LEN_4002_REQ_PAYLOAD = 226;
/*      */   private static final int LEN_4002_RESP_PAYLOAD = 48;
/*      */   private static final int LEN_5001_REQ_PAYLOAD = 2157;
/*      */   private static final int LEN_5001_RESP_PAYLOAD = 6;
/*      */   private static final int LEN_5002_REQ_PAYLOAD = 226;
/*      */   private static final int LEN_5002_RESP_PAYLOAD = 2061;
/*      */   private static final int LEN_9001_REQ_PAYLOAD = 32;
/*      */   private static final int LEN_9001_RESP_PAYLOAD = 244;
/*      */   private static final int LEN_9002_REQ_PAYLOAD = 50;
/*      */   private static final int LEN_9002_RESP_PAYLOAD = 200;
/*      */   private static final int LEN_9003_REQ_PAYLOAD = 114;
/*      */   private static final int LEN_9003_RESP_PAYLOAD = 200;
/*      */   private static final int LEN_9004_REQ_PAYLOAD = 32;
/*      */   private static final int LEN_9004_RESP_PAYLOAD = 242;
/*      */   private static final int LEN_9005_REQ_PAYLOAD = 140;
/*      */   private static final int LEN_9005_RESP_PAYLOAD = 200;
/*      */   private static final int LEN_5003_REQ_PAYLOAD = 104;
/*      */   private static final int LEN_5003_RESP_PAYLOAD = 10;
/*      */   private static final byte BLANK = 32;
/*      */   private final String code;
/*      */   private final byte[] codeBytes;
/*      */   
/*      */   static {
/*  744 */     LOG = LoggerFactory.getLogger(com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  763 */     FAIL_CNT_AS_STRING = new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   TransportAPI(String code) {
/*  807 */     this.code = code;
/*  808 */     this.codeBytes = code.getBytes(Commons.UTF8_CS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCode() {
/*  815 */     return this.code;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getCodeBytes() {
/*  822 */     return this.codeBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public IUafTransport parseUAFPayload(byte[] payload) throws ReturnCodeException {
/*  827 */     throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS);
/*      */   }
/*      */   
/*      */   public byte[] generateUAFPayload(IUafTransport reqTransport, IUafTransport respTransport) throws ReturnCodeException {
/*  831 */     throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS);
/*      */   }
/*      */   
/*      */   public AuthenticationRequestLocator parsePayload(byte[] payload) throws ReturnCodeException {
/*  835 */     throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS);
/*      */   }
/*      */   
/*      */   public byte[] generatePayload(AuthenticationResponseLocator respLoc) throws ReturnCodeException {
/*  839 */     throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS);
/*      */   }
/*      */ 
/*      */   
/*      */   protected GetUAFRequest parseGetUAFReqeust(byte[] b64Payload) throws ReturnCodeException {
/*      */     try {
/*  845 */       String payload = Base64Utils.decode(b64Payload);
/*  846 */       return (GetUAFRequest)GsonUtils.gson().fromJson(payload, GetUAFRequest.class);
/*  847 */     } catch (JsonParseException e) {
/*  848 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Failed to parse payload to 'GetUAFRequest' cause=" + e.getMessage());
/*  849 */     } catch (FidoUafStatusCodeException e) {
/*  850 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Failed to parse payload to 'GetUAFRequest' cause=" + e.getMessage());
/*  851 */     } catch (Exception e) {
/*  852 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Failed to parse payload to 'GetUAFRequest' cause=" + e.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected SendUAFResponse parseSendUAFResponse(byte[] b64Payload) throws ReturnCodeException {
/*      */     try {
/*  858 */       String payload = Base64Utils.decode(b64Payload);
/*  859 */       return (SendUAFResponse)GsonUtils.gson().fromJson(payload, SendUAFResponse.class);
/*  860 */     } catch (JsonParseException e) {
/*  861 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Failed to parse payload to 'SendUAFResponse' cause=" + e.getMessage());
/*  862 */     } catch (FidoUafStatusCodeException e) {
/*  863 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Failed to parse payload to 'SendUAFResponse' cause=" + e.getMessage());
/*  864 */     } catch (Exception e) {
/*  865 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Failed to parse payload to 'SendUAFResponse' cause=" + e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] generateReturnUAFRequest(GetUAFRequest reqLoc, ReturnUAFRequest respLoc) throws ReturnCodeException {
/*      */     try {
/*  873 */       ReturnCodes returnCode = ReturnCodes.getReturnCode(respLoc.getStatusCode());
/*  874 */       return generateReturnUAFRequestImp(returnCode, respLoc);
/*  875 */     } catch (Exception e) {
/*      */       
/*  877 */       respLoc = new ReturnUAFRequest();
/*  878 */       respLoc.setOp(reqLoc.getOp());
/*      */       
/*  880 */       if (e instanceof FidoUafStatusCodeException) {
/*  881 */         respLoc.setStatusCode(((FidoUafStatusCodeException)e).getStatusCode());
/*      */       } else {
/*  883 */         respLoc.setStatusCode(ReturnCodes.INVALID_PARAMS.getStatusCodes());
/*      */       } 
/*      */       
/*  886 */       LOG.error("Failed to generating payload to 'ReturnUAFRequest' api code =" + this.code + ",cause=" + e.getMessage());
/*      */       
/*  888 */       return generateReturnUAFRequestImp(ReturnCodes.getReturnCode(respLoc.getStatusCode()), respLoc);
/*      */     } 
/*      */   }
/*      */   
/*      */   private byte[] generateReturnUAFRequestImp(ReturnCodes returnCode, ReturnUAFRequest respLoc) {
/*  893 */     String json = GsonUtils.gson().toJson(respLoc);
/*  894 */     byte[] b64UafMsg = Base64Utils.encodeUrlRaw(json);
/*  895 */     int msgLen = b64UafMsg.length;
/*      */     
/*  897 */     byte[] payload = new byte[4 + msgLen];
/*      */     
/*  899 */     System.arraycopy(returnCode.getCodeBytes(), 0, payload, 0, 4);
/*  900 */     System.arraycopy(b64UafMsg, 0, payload, 4, msgLen);
/*      */     
/*  902 */     return payload;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] generateServerResponse(SendUAFResponse reqLoc, ServerResponse respLoc) throws ReturnCodeException {
/*      */     try {
/*  909 */       ReturnCodes returnCode = ReturnCodes.getReturnCode(respLoc.getStatusCode());
/*  910 */       return generateServerResponseImp(returnCode, respLoc);
/*      */     }
/*  912 */     catch (Exception e) {
/*      */       
/*  914 */       respLoc = new ServerResponse();
/*      */       
/*  916 */       if (e instanceof FidoUafStatusCodeException) {
/*  917 */         respLoc.setStatusCode(((FidoUafStatusCodeException)e).getStatusCode());
/*      */       } else {
/*  919 */         respLoc.setStatusCode(ReturnCodes.INTERNAL_SERVER_ERROR.getStatusCodes());
/*      */       } 
/*      */       
/*  922 */       LOG.error("Failed to generating payload to 'ServerResponse' api code =" + this.code + ",cause=" + e.getMessage());
/*      */       
/*  924 */       return generateServerResponseImp(ReturnCodes.getReturnCode(respLoc.getStatusCode()), respLoc);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static String parsePayloadWithLen(com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI api, byte[] payload, int offset, int len) {
/*  930 */     if (payload.length < offset + len) {
/*  931 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The payload is invalid..... api=" + api.getCode() + ", payload=" + payload);
/*      */     }
/*      */     
/*  934 */     return (new String(payload, offset, len, MMTHConstants.MS949_CS)).trim();
/*      */   }
/*      */ 
/*      */   
/*      */   private byte[] generateServerResponseImp(ReturnCodes returnCode, ServerResponse respLoc) {
/*  939 */     String json = GsonUtils.gson().toJson(respLoc);
/*  940 */     byte[] b64UafMsg = Base64Utils.encodeUrlRaw(json);
/*  941 */     int msgLen = b64UafMsg.length;
/*      */     
/*  943 */     byte[] payload = new byte[4 + msgLen];
/*      */     
/*  945 */     System.arraycopy(returnCode.getCodeBytes(), 0, payload, 0, 4);
/*  946 */     System.arraycopy(b64UafMsg, 0, payload, 4, msgLen);
/*      */     
/*  948 */     return payload;
/*      */   }
/*      */   
/*      */   public void setTransportAPI(HttpServletRequest request) {
/*  952 */     request.setAttribute(com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI.class.getName(), this);
/*      */   }
/*      */ 
/*      */   
/*      */   public static com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI valueByName(String name) {
/*      */     try {
/*  958 */       return valueOf(name);
/*  959 */     } catch (Exception e) {
/*  960 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI getTransportAPI(String code) {
/*  966 */     for (com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI api : values()) {
/*  967 */       if (api.getCode().equals(code)) {
/*  968 */         return api;
/*      */       }
/*      */     } 
/*      */     
/*  972 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI getTransportAPI(byte[] code) {
/*  977 */     for (com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI api : values()) {
/*      */       
/*  979 */       if (Arrays.equals(api.getCodeBytes(), code)) {
/*  980 */         return api;
/*      */       }
/*      */     } 
/*      */     
/*  984 */     return null;
/*      */   }
/*      */   
/*      */   public static com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI getTransportAPI(HttpServletRequest request) {
/*  988 */     Object obj = request.getAttribute(com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI.class.getName());
/*      */     
/*  990 */     if (obj instanceof com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI) {
/*  991 */       return (com.dreammirae.mmth.parser.biotp.kiwoom.TransportAPI)obj;
/*      */     }
/*      */     
/*  994 */     return null;
/*      */   }
/*      */   
/*      */   private static byte[] emptyPaddingBytes(int size) {
/*  998 */     byte[] newBytes = new byte[size];
/*  999 */     Arrays.fill(newBytes, (byte)32);
/* 1000 */     return newBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void copySrcToDest(byte[] dest, String src, int offset) {
/* 1005 */     if (src == null || src.length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1009 */     byte[] srcObj = src.getBytes(Commons.UTF8_CS);
/*      */     
/* 1011 */     int len = srcObj.length;
/*      */     
/* 1013 */     if (len > dest.length) {
/* 1014 */       len = dest.length;
/*      */     }
/*      */     
/* 1017 */     System.arraycopy(srcObj, 0, dest, offset, len);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String cvtFailCnt(Integer failCnt) {
/* 1022 */     if (failCnt == null || failCnt.intValue() < 0) {
/* 1023 */       return FAIL_CNT_AS_STRING[0];
/*      */     }
/*      */     
/* 1026 */     if (failCnt.intValue() > 20) {
/* 1027 */       return FAIL_CNT_AS_STRING[20];
/*      */     }
/*      */     
/* 1030 */     return FAIL_CNT_AS_STRING[failCnt.intValue()];
/*      */   }
/*      */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\biotp\kiwoom\TransportAPI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */