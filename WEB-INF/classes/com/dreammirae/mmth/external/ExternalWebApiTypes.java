/*     */ package WEB-INF.classes.com.dreammirae.mmth.external;
/*     */ 
/*     */ import com.dreammirae.mmth.MMTHConstants;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.external.bean.GlobalWibeeRequestParam;
/*     */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*     */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ExternalWebApiTypes
/*     */ {
/*  20 */   NONE,
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
/*  51 */   BASIC,
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
/*  81 */   GPTWR,
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   GLOBAL_WIBEE,
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
/* 164 */   KIWOOM,
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   MIRAE_ASSET_VT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateVerifyOTPParam(WebApiRequestParam param) {
/* 227 */     if (StringUtils.isEmpty(param.getUserName()) && StringUtils.isEmpty(param.getTid())) {
/* 228 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Tid or username must be included.");
/*     */     }
/*     */     
/* 231 */     if (StringUtils.isEmpty(param.getOtp()) || !MMTHConstants.REGEX_OTP_PATTERN.matcher(param.getOtp()).matches()) {
/* 232 */       throw new ReturnCodeException(ReturnCodes.OTP_RESPONSE_TYPE_ERROR, "OTP must be 6-digit number. input character =" + param.getOtp());
/*     */     }
/*     */     
/* 235 */     if (!StringUtils.isEmpty(param.getUserName())) {
/* 236 */       validateUsernameParam(param);
/*     */     }
/*     */     
/* 239 */     if (!StringUtils.isEmpty(param.getTid())) {
/* 240 */       validateTidParam(param);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateReIssueCodeParam(WebApiRequestParam param) {
/* 246 */     if (StringUtils.isEmpty(param.getUserName())) {
/* 247 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Tid or username must be included.");
/*     */     }
/* 249 */     validateUsernameParam(param);
/*     */ 
/*     */     
/* 252 */     if (StringUtils.isEmpty(param.getOtp()) || !MMTHConstants.REGEX_OTP_PATTERN.matcher(param.getOtp()).matches()) {
/* 253 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "OTP must be 6-digit number. input character =" + param.getOtp());
/*     */     }
/*     */     
/* 256 */     if (param instanceof GlobalWibeeRequestParam) {
/* 257 */       GlobalWibeeRequestParam wibeeParam = (GlobalWibeeRequestParam)param;
/*     */       
/* 259 */       if (StringUtils.isEmpty(wibeeParam.getCountryCode()) || 
/* 260 */         !GlobalWibeeRequestParam.REGEX_COUNTRY_CODE_PATTERN.matcher(wibeeParam.getCountryCode()).matches()) {
/* 261 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "'countryCode' is invalid.. input=" + wibeeParam.getCountryCode());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateVerifyEncOTPParam(WebApiRequestParam param) {
/* 271 */     if (StringUtils.isEmpty(param.getUserName()) && StringUtils.isEmpty(param.getTid())) {
/* 272 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "Tid or username must be included.");
/*     */     }
/*     */     
/* 275 */     if (StringUtils.isEmpty(param.getOtp()) || !MMTHConstants.REGEX_HEX_PATTERN.matcher(param.getOtp()).matches()) {
/* 276 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "OTP must be hexadecimal string. input character =" + param.getOtp());
/*     */     }
/*     */     
/* 279 */     if (!StringUtils.isEmpty(param.getUserName())) {
/* 280 */       validateUsernameParam(param);
/*     */     }
/*     */     
/* 283 */     if (!StringUtils.isEmpty(param.getTid())) {
/* 284 */       validateTidParam(param);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateVerifyTranOTPParam(WebApiRequestParam param) {
/* 293 */     validateVerifyOTPParam(param);
/*     */     
/* 295 */     if (StringUtils.isEmpty(param.getTranInfo()) || !MMTHConstants.REGEX_HEX_PATTERN.matcher(param.getTranInfo()).matches()) {
/* 296 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "TranInfo must be hexadecimal characters... input character =" + param.getTranInfo());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateVerifyTranEncOTPParam(WebApiRequestParam param) {
/* 302 */     validateVerifyEncOTPParam(param);
/*     */     
/* 304 */     if (StringUtils.isEmpty(param.getTranInfo()) || !MMTHConstants.REGEX_HEX_PATTERN.matcher(param.getTranInfo()).matches()) {
/* 305 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "TranInfo must be hexadecimal characters... input character =" + param.getTranInfo());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateUsernameParam(WebApiRequestParam param) {
/* 313 */     UserVO.validateUsername(param.getUserName());
/*     */   }
/*     */   
/*     */   public void validateServiceApiParam(WebApiRequestParam param) {
/* 317 */     validateUsernameParam(param);
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateCheckDevice(WebApiRequestParam param) {
/* 322 */     UserVO.validateUsername(param.getUserName());
/*     */     
/* 324 */     if (param.getDeviceType() == null) {
/* 325 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "deviceType must be `AD`,`AW` or `IU`... ");
/*     */     }
/*     */     
/* 328 */     if (StringUtils.isEmpty(param.getDeviceId()) || !MMTHConstants.REGEX_DEVICE_ID_PATTERN.matcher(param.getDeviceId()).matches()) {
/* 329 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "deviceId must be 64-hexadecimal characters... input character =" + param.getDeviceId());
/*     */     }
/*     */   }
/*     */   
/*     */   public void validateTidParam(WebApiRequestParam param) {
/* 334 */     if (StringUtils.isEmpty(param.getTid()) || !MMTHConstants.REGEX_HEX_PATTERN.matcher(param.getTid()).matches()) {
/* 335 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "TID must be hexadecimal characters... input character =" + param.getTid());
/*     */     }
/*     */     
/* 338 */     if (param.getTid().length() != 32) {
/* 339 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "TID length must be 32.... input character =" + param.getTid());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.external.ExternalWebApiTypes valueByName(String value) {
/* 345 */     for (com.dreammirae.mmth.external.ExternalWebApiTypes policy : values()) {
/*     */       
/* 347 */       if (policy.name().equalsIgnoreCase(value)) {
/* 348 */         return policy;
/*     */       }
/*     */     } 
/*     */     
/* 352 */     return NONE;
/*     */   }
/*     */   
/*     */   public static List<String> getNames() {
/* 356 */     List<String> list = new ArrayList<>();
/* 357 */     for (com.dreammirae.mmth.external.ExternalWebApiTypes type : values()) {
/* 358 */       list.add(type.name());
/*     */     }
/* 360 */     return list;
/*     */   }
/*     */   
/*     */   public abstract WebApiRequestParam parseWebApiRequestParam(String paramString);
/*     */   
/*     */   public abstract WebApiRequestParam[] parseWebApiRequestParams(String paramString);
/*     */   
/*     */   public abstract WebApiResponse createResponseInstance();
/*     */   
/*     */   public abstract void validateIssueCodeParam(WebApiRequestParam paramWebApiRequestParam);
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\ExternalWebApiTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */