/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.misc.MessageUtils;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*     */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.bean.ICustomLogData;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.springframework.context.i18n.LocaleContextHolder;
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
/*     */ public class ServiceLogVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*  82 */   private long id = -1L;
/*     */ 
/*     */   
/*     */   private AuthMethodTypes authType;
/*     */ 
/*     */   
/*     */   private String username;
/*     */ 
/*     */   
/*     */   private LogOperationTypes opType;
/*     */ 
/*     */   
/*     */   private OpRequstTypes reqType;
/*     */ 
/*     */   
/*     */   private LogActionTypes actionType;
/*     */ 
/*     */   
/* 100 */   private ReturnCodes returnCode = ReturnCodes.OK;
/*     */ 
/*     */   
/* 103 */   private String deviceId = "-";
/*     */ 
/*     */   
/* 106 */   private DeviceTypes deviceType = DeviceTypes.ANANYMOUS;
/*     */ 
/*     */   
/* 109 */   private String pkgUnique = "-";
/*     */ 
/*     */   
/* 112 */   private String aaid = "-";
/*     */ 
/*     */   
/* 115 */   private String tokenId = "-";
/*     */ 
/*     */   
/* 118 */   private String description = "-";
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private Long tsReg;
/*     */ 
/*     */   
/*     */   private String regDate;
/*     */   
/*     */   private String regHour;
/*     */   
/*     */   private ICustomLogData customData;
/*     */   
/*     */   private DeviceAppAgentVO deviceAppAgent;
/*     */   
/*     */   private String model;
/*     */   
/*     */   private List<LogOperationTypes> options;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   
/*     */   public List<LogOperationTypes> getOptions() {
/* 141 */     return this.options;
/*     */   }
/*     */   
/*     */   public void setOptions(List<LogOperationTypes> options) {
/* 145 */     this.options = options;
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 149 */     return this.model;
/*     */   }
/*     */   
/*     */   public void setModel(String model) {
/* 153 */     this.model = model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 164 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(long id) {
/* 171 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthType() {
/* 178 */     return this.authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthType(AuthMethodTypes authType) {
/* 185 */     this.authType = authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/* 192 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/* 199 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogOperationTypes getOpType() {
/* 206 */     return this.opType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpType(LogOperationTypes opType) {
/* 213 */     this.opType = opType;
/*     */   }
/*     */   
/*     */   public OpRequstTypes getReqType() {
/* 217 */     return this.reqType;
/*     */   }
/*     */   
/*     */   public void setReqType(OpRequstTypes reqType) {
/* 221 */     this.reqType = reqType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogActionTypes getActionType() {
/* 228 */     return this.actionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActionType(LogActionTypes actionType) {
/* 235 */     this.actionType = actionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReturnCodes getReturnCode() {
/* 242 */     return this.returnCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReturnCode(ReturnCodes returnCode) {
/* 249 */     this.returnCode = returnCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceId() {
/* 256 */     return this.deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/* 263 */     this.deviceId = deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceTypes getDeviceType() {
/* 270 */     return this.deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceType(DeviceTypes deviceType) {
/* 277 */     this.deviceType = deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPkgUnique() {
/* 284 */     return this.pkgUnique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPkgUnique(String pkgUnique) {
/* 291 */     this.pkgUnique = pkgUnique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAaid() {
/* 298 */     return this.aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAaid(String aaid) {
/* 305 */     this.aaid = aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 312 */     return this.tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenId(String tokenId) {
/* 319 */     this.tokenId = tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 326 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/* 333 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTsReg() {
/* 340 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(Long tsReg) {
/* 347 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRegDate() {
/* 354 */     return this.regDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRegDate(String regDate) {
/* 361 */     this.regDate = regDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRegHour() {
/* 368 */     return this.regHour;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRegHour(String regHour) {
/* 375 */     this.regHour = regHour;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICustomLogData getCustomData() {
/* 382 */     return this.customData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomData(ICustomLogData customData) {
/* 389 */     this.customData = customData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceAppAgentVO getDeviceAppAgent() {
/* 395 */     return this.deviceAppAgent;
/*     */   }
/*     */   
/*     */   public void setDeviceAppAgent(DeviceAppAgentVO deviceAppAgent) {
/* 399 */     this.deviceAppAgent = deviceAppAgent;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public LogActionTypes getActionTypeDesc() {
/* 404 */     return this.actionType;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public LogOperationTypes getOpTypeDesc() {
/* 409 */     return this.opType;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public OpRequstTypes getReqTypeDesc() {
/* 414 */     return this.reqType;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AuthMethodTypes getAuthTypeDesc() {
/* 419 */     return this.authType;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public ReturnCodes getReturnCodeDesc() {
/* 424 */     return this.returnCode;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public DeviceTypes getDeviceTypeDesc() {
/* 429 */     return this.deviceType;
/*     */   }
/*     */   
/*     */   public String getReturnCodeRaw() {
/* 433 */     if (this.returnCode == null) {
/* 434 */       return "";
/*     */     }
/* 436 */     return this.returnCode.getCode();
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
/*     */   public void validate(RestResponse resp) {}
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
/*     */   public String toString() {
/* 462 */     StringBuilder builder = new StringBuilder();
/* 463 */     builder.append("ServiceLogVO [id=").append(this.id).append(", authType=").append(this.authType).append(", username=")
/* 464 */       .append(this.username).append(", opType=").append(this.opType).append(", actionType=").append(this.actionType)
/* 465 */       .append(", returnCode=").append(this.returnCode).append(", deviceId=").append(this.deviceId)
/* 466 */       .append(", deviceType=").append(this.deviceType).append(", pkgUnique=").append(this.pkgUnique).append(", aaid=")
/* 467 */       .append(this.aaid).append(", tokenId=").append(this.tokenId).append(", description=").append(this.description)
/* 468 */       .append(", tsReg=").append(this.tsReg).append(", regDate=").append(this.regDate).append(", regHour=")
/* 469 */       .append(this.regHour).append(", customData=").append(this.customData).append("]");
/* 470 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue(String key, com.dreammirae.mmth.vo.ServiceLogVO data) {
/* 475 */     if (StringUtils.equals(key, "username"))
/* 476 */       return data.getUsername(); 
/* 477 */     if (StringUtils.equals(key, "authType"))
/* 478 */       return MessageUtils.getMessageSource().getMessage(data.getAuthType().getMessageKey(), null, LocaleContextHolder.getLocale()); 
/* 479 */     if (StringUtils.equals(key, "opType"))
/* 480 */       return MessageUtils.getMessageSource().getMessage(data.getOpType().getMessageKey(), null, LocaleContextHolder.getLocale()); 
/* 481 */     if (StringUtils.equals(key, "reqType"))
/* 482 */       return MessageUtils.getMessageSource().getMessage(data.getReqType().getMessageKey(), null, LocaleContextHolder.getLocale()); 
/* 483 */     if (StringUtils.equals(key, "activeType"))
/* 484 */       return MessageUtils.getMessageSource().getMessage(data.getActionType().getMessageKey(), null, LocaleContextHolder.getLocale()); 
/* 485 */     if (StringUtils.equals(key, "tokenId")) {
/* 486 */       if (StringUtils.isBlank(data.getTokenId())) {
/* 487 */         return "-";
/*     */       }
/* 489 */       return data.getTokenId();
/*     */     } 
/* 491 */     return Commons.displayDateTime(data.getTsReg().longValue());
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\ServiceLogVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */