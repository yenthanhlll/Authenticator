/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.fido.StatusCodes;
/*     */ import com.dreammirae.mmth.misc.MessageUtils;
/*     */ import java.util.HashMap;
/*     */ import org.springframework.context.i18n.LocaleContextHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ReturnCodes
/*     */ {
/*  16 */   OK("0000", StatusCodes.CODE_1200, "ReturnCodes.OK"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  21 */   ACCEPTED("1202", StatusCodes.CODE_1202, "ReturnCodes.ACCEPTED"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  26 */   BAD_REQUEST("1400", StatusCodes.CODE_1400, "ReturnCodes.BAD_REQUEST"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   USER_UNAUTHORIED("1401", StatusCodes.CODE_1401, "ReturnCodes.USER_UNAUTHORIED"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   USER_FORBIDDEN("1403", StatusCodes.CODE_1403, "ReturnCodes.USER_FORBIDDEN"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   NOT_FOUND_URL("1404", StatusCodes.CODE_1404, "ReturnCodes.NOT_FOUND_URL"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   REQUEST_TIMEOUT("1408", StatusCodes.CODE_1408, "ReturnCodes.REQUEST_TIMEOUT"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   UNKNOWN_AAID("1480", StatusCodes.CODE_1480, "ReturnCodes.UNKNOWN_AAID"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   UNKNOWN_KEYID("1481", StatusCodes.CODE_1481, "ReturnCodes.UNKNOWN_KEYID"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   UNTRUSTED_FACET_ID("1489", StatusCodes.CODE_1489, "ReturnCodes.UNTRUSTED_FACET_ID"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   CHANNEL_BINDING_REFUSED("1490", StatusCodes.CODE_1490, "ReturnCodes.CHANNEL_BINDING_REFUSED"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   REQUEST_INVALID("1491", StatusCodes.CODE_1491, "ReturnCodes.REQUEST_INVALID"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   UNACCEPTABLE_AUTHENTICATOR("1492", StatusCodes.CODE_1492, "ReturnCodes.UNACCEPTABLE_AUTHENTICATOR"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   REVOKED_AUTHENTICATOR("1493", StatusCodes.CODE_1493, "ReturnCodes.REVOKED_AUTHENTICATOR"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   UNACCEPTABLE_KEY("1494", StatusCodes.CODE_1494, "ReturnCodes.UNACCEPTABLE_KEY"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   UNACCEPTABLE_ALGORITHM("1496", StatusCodes.CODE_1496, "ReturnCodes.UNACCEPTABLE_ALGORITHM"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   UNACCEPTABLE_CLIENT_CAPABILITIES("1497", StatusCodes.CODE_1497, "ReturnCodes.UNACCEPTABLE_CLIENT_CAPABILITIES"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   INVALID_PARAMS("1498", StatusCodes.CODE_1498, "ReturnCodes.INVALID_PARAMS"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   INTERNAL_SERVER_ERROR("1500", StatusCodes.CODE_1500, "ReturnCodes.INTERNAL_SERVER_ERROR"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   DEVICE_UNAUTHORIED("1001", StatusCodes.CODE_1001, "ReturnCodes.DEVICE_UNAUTHORIED"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   DEVICE_APP_UNAUTHORIED("1002", StatusCodes.CODE_1002, "ReturnCodes.DEVICE_APP_UNAUTHORIED"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   EXIST_ALREADY("1003", StatusCodes.CODE_1003, "ReturnCodes.EXIST_ALREADY"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   MULTI_DEVICE_DISALLOWED("1004", StatusCodes.CODE_1004, "ReturnCodes.MULTI_DEVICE_DISALLOWED"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   MULTI_APP_AGENT_DISALLOWED("1005", StatusCodes.CODE_1005, "ReturnCodes.MULTI_APP_AGENT_DISALLOWED"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   NO_AUTH_METHOD("1006", StatusCodes.CODE_1006, "ReturnCodes.NO_AUTH_METHOD"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   UNKNOWN_APP_AGENT("1008", StatusCodes.CODE_1008, "ReturnCodes.UNKNOWN_APP_AGENT"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   NO_AUTHENTICATION_REQUEST("1009", StatusCodes.CODE_1009, "ReturnCodes.NO_AUTHENTICATION_REQUEST"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   DEVICE_FORBIDDEN("1010", StatusCodes.CODE_1010, "ReturnCodes.DEVICE_FORBIDDEN"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   NO_OTP_KEY_TO_ASSIGN("1011", StatusCodes.CODE_1011, "ReturnCodes.NO_OTP_KEY_TO_ASSIGN"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   NOT_FOUND_SERVER_CHALLENGE("1012", StatusCodes.CODE_1012, "ReturnCodes.NOT_FOUND_SERVER_CHALLENGE"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   AUTHENTICATION_INCOMPLETED("1013", StatusCodes.CODE_1013, "ReturnCodes.AUTHENTICATION_INCOMPLETED"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   AUTHENTICATION_RESULT_INVALID("1014", StatusCodes.CODE_1014, "ReturnCodes.AUTHENTICATION_RESULT_INVALID"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   NOT_APPLICABLE("1015", StatusCodes.CODE_1015, "ReturnCodes.NOT_APPLICABLE"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   LOST("1016", StatusCodes.CODE_1016, "ReturnCodes.LOST"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   FORBIDDEN_REQUEST_ISSUE_CODE("1101", StatusCodes.CODE_1101, "ReturnCodes.FORBIDDEN_REQUEST_ISSUE_CODE"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   VERIFY_ISSUE_CODE_FAILED("1102", StatusCodes.CODE_1102, "ReturnCodes.VERIFY_ISSUE_CODE_FAILED"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   DISCARD_ISSUE_CODE("1103", StatusCodes.CODE_1103, "ReturnCodes.DISCARD_ISSUE_CODE"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   FCM_TOKEN_NONE("1301", StatusCodes.CODE_1301, "ReturnCodes.FCM_TOKEN_NONE"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   FCM_AUTHORIZATION_KEY_NONE("1302", StatusCodes.CODE_1302, "ReturnCodes.FCM_AUTHORIZATION_KEY_NONE"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   NOT_FOUND_AUTH_TYPE("1601", StatusCodes.CODE_1203, "ReturnCodes.NOT_FOUND_AUTH_TYPE"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   AUTH_DATA_VALIDATAE_FAIL("1602", StatusCodes.CODE_1204, "ReturnCodes.AUTH_DATA_VALIDATAE_FAIL"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   ISSUE_USER_ID_ERROR("5001", StatusCodes.CODE_1401, "ReturnCodes.ISSUE_USER_ID_ERROR"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   ISSUE_TOKEN_ID_NONE("5002", StatusCodes.CODE_6000, "ReturnCodes.ISSUE_TOKEN_ID_NONE"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 255 */   ISSUE_KEY_DATA_NONE("5003", StatusCodes.CODE_6000, "ReturnCodes.ISSUE_KEY_DATA_NONE"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   ISSUE_NOT_AVAILABLE("5004", StatusCodes.CODE_6000, "ReturnCodes.ISSUE_NOT_AVAILABLE"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 266 */   AUTH_FAILED("6000", StatusCodes.CODE_6000, "ReturnCodes.AUTH_FAILED"),
/*     */ 
/*     */ 
/*     */   
/* 270 */   AUTH_FAILED_REUSE("6001", StatusCodes.CODE_6014, "ReturnCodes.AUTH_FAILED_REUSE"),
/*     */ 
/*     */ 
/*     */   
/* 274 */   CORRECTION_REQUIRE("6002", StatusCodes.CODE_6000, "ReturnCodes.CORRECTION_REQUIRE"),
/*     */ 
/*     */ 
/*     */   
/* 278 */   CORRECTION_FAILED("6003", StatusCodes.CODE_6000, "ReturnCodes.CORRECTION_FAILED"),
/*     */ 
/*     */ 
/*     */   
/* 282 */   CORRECTION_FAILED_REUSE("6004", StatusCodes.CODE_6000, "ReturnCodes.CORRECTION_FAILED_REUSE"),
/*     */ 
/*     */ 
/*     */   
/* 286 */   OTP_SN_NOT_FOUND("6006", StatusCodes.CODE_6006, "ReturnCodes.OTP_SN_NOT_FOUND"),
/*     */ 
/*     */ 
/*     */   
/* 290 */   INPUT_PARAMETER_ERROR("6007", StatusCodes.CODE_6007, "ReturnCodes.INPUT_PARAMETER_ERROR"),
/*     */ 
/*     */ 
/*     */   
/* 294 */   OTP_RESPONSE_TYPE_ERROR("6010", StatusCodes.CODE_6006, "ReturnCodes.OTP_RESPONSE_TYPE_ERROR"),
/*     */ 
/*     */ 
/*     */   
/* 298 */   NOT_FIDO_AUTHENTICATION("6013", StatusCodes.CODE_6013, "ReturnCodes.NOT_FIDO_AUTHENTICATION"),
/*     */ 
/*     */ 
/*     */   
/* 302 */   EXPIRED_AUTH_REQUEST("6014", StatusCodes.CODE_6014, "ReturnCodes.EXPIRED_AUTH_REQUEST"),
/*     */ 
/*     */ 
/*     */   
/* 306 */   MISSMATCH_AUTH_REQUEST("6015", StatusCodes.CODE_6015, "ReturnCodes.MISSMATCH_AUTH_REQUEST"),
/*     */ 
/*     */ 
/*     */   
/* 310 */   INVALID_AUTH_TRAN_INFO("6016", StatusCodes.CODE_6016, "ReturnCodes.INVALID_AUTH_TRAN_INFO"),
/*     */ 
/*     */ 
/*     */   
/* 314 */   AUTH_CHALLENGE_EXPIRED("6017", StatusCodes.CODE_6017, "ReturnCodes.AUTH_CHALLENGE_EXPIRED"),
/*     */ 
/*     */ 
/*     */   
/* 318 */   EXCEEDED_AUTH_ERROR("6019", StatusCodes.CODE_6019, "ReturnCodes.EXCEEDED_AUTH_ERROR"),
/*     */ 
/*     */ 
/*     */   
/* 322 */   LOST_NOT_AVAILABLE("6022", StatusCodes.CODE_1017, "ReturnCodes.LOST_NOT_AVAILABLE"),
/*     */ 
/*     */ 
/*     */   
/* 326 */   NOT_ISSUED("6023", StatusCodes.CODE_6023, "ReturnCodes.NOT_ISSUED"),
/*     */ 
/*     */ 
/*     */   
/* 330 */   SUSPEND("6024", StatusCodes.CODE_6024, "ReturnCodes.SUSPEND"),
/*     */ 
/*     */ 
/*     */   
/* 334 */   DISUSED("6025", StatusCodes.CODE_6025, "ReturnCodes.DISUSED"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 339 */   SUSPEND_USER_ID_NONE("7001", StatusCodes.CODE_1401, "ReturnCodes.SUSPEND_USER_ID_NONE"),
/*     */ 
/*     */ 
/*     */   
/* 343 */   SUSPEND_TOKEN_ID_NONE("7002", StatusCodes.CODE_1482, "ReturnCodes.SUSPEND_TOKEN_ID_NONE"),
/*     */ 
/*     */ 
/*     */   
/* 347 */   SUSPEND_KEY_DATA_NONE("7003", StatusCodes.CODE_1302, "ReturnCodes.SUSPEND_KEY_DATA_NONE"),
/*     */ 
/*     */ 
/*     */   
/* 351 */   SUSPEND_NOT_AVAILABLE("7004", StatusCodes.CODE_6026, "ReturnCodes.SUSPEND_NOT_AVAILABLE"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 356 */   DISUSE_USER_ID_NONE("8001", StatusCodes.CODE_1401, "ReturnCodes.DISUSE_USER_ID_NONE"),
/*     */ 
/*     */ 
/*     */   
/* 360 */   DISUSE_TOKEN_ID_NONE("8002", StatusCodes.CODE_1482, "ReturnCodes.DISUSE_TOKEN_ID_NONE"),
/*     */ 
/*     */ 
/*     */   
/* 364 */   DISUSE_KEY_DATA_NONE("8003", StatusCodes.CODE_1302, "ReturnCodes.DISUSE_KEY_DATA_NONE"),
/*     */ 
/*     */ 
/*     */   
/* 368 */   DISUSE_NOT_AVAILABLE("8004", StatusCodes.CODE_6026, "ReturnCodes.DISUSE_NOT_AVAILABLE");
/*     */   
/*     */   private final String code;
/*     */   
/*     */   private final StatusCodes statusCodes;
/*     */   private final String messageKey;
/*     */   private final byte[] codeBytes;
/*     */   
/*     */   ReturnCodes(String code, StatusCodes statusCodes, String messageKey) {
/* 377 */     this.code = code;
/* 378 */     this.statusCodes = statusCodes;
/* 379 */     this.messageKey = messageKey;
/* 380 */     this.codeBytes = code.getBytes(Commons.UTF8_CS);
/*     */   }
/*     */   
/*     */   public String getCode() {
/* 384 */     return this.code;
/*     */   }
/*     */   
/*     */   public byte[] getCodeBytes() {
/* 388 */     return this.codeBytes;
/*     */   }
/*     */   
/*     */   public StatusCodes getStatusCodes() {
/* 392 */     return this.statusCodes;
/*     */   }
/*     */   
/*     */   public String getMessageKey() {
/* 396 */     return this.messageKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.authentication.ReturnCodes getReturnCode(String code) {
/* 401 */     for (com.dreammirae.mmth.authentication.ReturnCodes type : values()) {
/* 402 */       if (type.code.equals(code)) {
/* 403 */         return type;
/*     */       }
/*     */     } 
/* 406 */     return null;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.authentication.ReturnCodes getReturnCodeByName(String name) {
/* 410 */     for (com.dreammirae.mmth.authentication.ReturnCodes type : values()) {
/* 411 */       if (type.name().equalsIgnoreCase(name)) {
/* 412 */         return type;
/*     */       }
/*     */     } 
/* 415 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.authentication.ReturnCodes getReturnCode(StatusCodes statusCode) {
/* 420 */     for (com.dreammirae.mmth.authentication.ReturnCodes code : values()) {
/* 421 */       if (code.statusCodes.equals(statusCode)) {
/* 422 */         return code;
/*     */       }
/*     */     } 
/*     */     
/* 426 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static HashMap<String, String> getMap() {
/* 431 */     HashMap<String, String> map = new HashMap<>();
/* 432 */     for (com.dreammirae.mmth.authentication.ReturnCodes code : values()) {
/* 433 */       map.put(code.getCode(), MessageUtils.getMessageSource().getMessage(code.getMessageKey(), null, LocaleContextHolder.getLocale()));
/*     */     }
/*     */     
/* 436 */     return map;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\ReturnCodes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */