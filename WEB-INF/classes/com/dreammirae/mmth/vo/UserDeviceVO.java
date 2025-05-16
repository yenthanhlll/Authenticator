/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
/*     */ import com.dreammirae.mmth.vo.types.DeviceIssueranceStatus;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*     */ import com.dreammirae.mmth.vo.types.UserDeviceStatus;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
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
/*     */ public class UserDeviceVO
/*     */   implements Serializable
/*     */ {
/*     */   public static final int USER_VERIFICATION_RESET = 0;
/*  66 */   private int id = -1;
/*  67 */   private int userId = -1;
/*     */   private String deviceId;
/*     */   private DeviceTypes deviceType;
/*  70 */   private String model = "-";
/*  71 */   private String alias = "-";
/*     */ 
/*     */   
/*     */   private DisabledStatus disabled;
/*     */   
/*     */   private UserDeviceStatus status;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsReg;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsUpdated;
/*     */   
/*  84 */   private DeviceIssueranceStatus latestIssueStatus = DeviceIssueranceStatus.NEW_USER_REGISTER;
/*     */   
/*     */   private String tokenId;
/*     */   
/*     */   private int appRegCnt;
/*  89 */   private DeviceIssueranceStatus latestFidoIssueStatus = DeviceIssueranceStatus.NEW_USER_REGISTER;
/*  90 */   private DeviceIssueranceStatus latestBiotpIssueStatus = DeviceIssueranceStatus.NEW_USER_REGISTER;
/*  91 */   private DeviceIssueranceStatus latestSaotpIssueStatus = DeviceIssueranceStatus.NEW_USER_REGISTER;
/*     */ 
/*     */   
/*     */   private int fidoRegCumulation;
/*     */ 
/*     */   
/*     */   private int biotpRegCumulation;
/*     */ 
/*     */   
/*     */   private int saotpRegCumulation;
/*     */ 
/*     */   
/*     */   private UserDeviceStatus biotpStatus;
/*     */ 
/*     */   
/*     */   private UserDeviceStatus fidoStatus;
/*     */ 
/*     */   
/*     */   private transient UserVO user;
/*     */   
/*     */   private int userVerificationFailCnt;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static final int version = 3;
/*     */ 
/*     */   
/*     */   public int getId() {
/* 119 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/* 127 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUserId() {
/* 134 */     return this.userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserId(int userId) {
/* 142 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceId() {
/* 149 */     return this.deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/* 157 */     this.deviceId = deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceTypes getDeviceType() {
/* 164 */     return this.deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceType(DeviceTypes deviceType) {
/* 172 */     this.deviceType = deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModel() {
/* 179 */     return this.model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(String model) {
/* 187 */     this.model = model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlias() {
/* 194 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlias(String alias) {
/* 202 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisabledStatus getDisabled() {
/* 209 */     return this.disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisabled(DisabledStatus disabled) {
/* 217 */     this.disabled = disabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserDeviceStatus getStatus() {
/* 224 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(UserDeviceStatus status) {
/* 232 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 239 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 247 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsUpdated() {
/* 254 */     return this.tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsUpdated(long tsUpdated) {
/* 262 */     this.tsUpdated = tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceIssueranceStatus getLatestIssueStatus() {
/* 270 */     return this.latestIssueStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLatestIssueStatus(DeviceIssueranceStatus latestIssueStatus) {
/* 277 */     this.latestIssueStatus = latestIssueStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 284 */     return this.tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenId(String tokenId) {
/* 292 */     this.tokenId = tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAppRegCnt() {
/* 299 */     return this.appRegCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAppRegCnt(int appRegCnt) {
/* 306 */     this.appRegCnt = appRegCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceIssueranceStatus getLatestFidoIssueStatus() {
/* 314 */     return this.latestFidoIssueStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLatestFidoIssueStatus(DeviceIssueranceStatus latestFidoIssueStatus) {
/* 321 */     this.latestFidoIssueStatus = latestFidoIssueStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceIssueranceStatus getLatestBiotpIssueStatus() {
/* 328 */     return this.latestBiotpIssueStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLatestBiotpIssueStatus(DeviceIssueranceStatus latestBiotpIssueStatus) {
/* 335 */     this.latestBiotpIssueStatus = latestBiotpIssueStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceIssueranceStatus getLatestSaotpIssueStatus() {
/* 342 */     return this.latestSaotpIssueStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLatestSaotpIssueStatus(DeviceIssueranceStatus latestSaotpIssueStatus) {
/* 349 */     this.latestSaotpIssueStatus = latestSaotpIssueStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFidoRegCumulation() {
/* 356 */     return this.fidoRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFidoRegCumulation(int fidoRegCumulation) {
/* 363 */     this.fidoRegCumulation = fidoRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBiotpRegCumulation() {
/* 370 */     return this.biotpRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiotpRegCumulation(int biotpRegCumulation) {
/* 377 */     this.biotpRegCumulation = biotpRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSaotpRegCumulation() {
/* 384 */     return this.saotpRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaotpRegCumulation(int saotpRegCumulation) {
/* 391 */     this.saotpRegCumulation = saotpRegCumulation;
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceIssueranceStatus getLatestIssueStatus(AuthMethodTypes authType) {
/* 396 */     if (AuthMethodTypes.FIDO.equals(authType))
/* 397 */       return this.latestFidoIssueStatus; 
/* 398 */     if (AuthMethodTypes.BIOTP.equals(authType)) {
/* 399 */       return this.latestBiotpIssueStatus;
/*     */     }
/* 401 */     return this.latestSaotpIssueStatus;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLatestIssueStatus(AuthMethodTypes authType, DeviceIssueranceStatus latestIssueStatus) {
/* 406 */     if (AuthMethodTypes.FIDO.equals(authType)) {
/* 407 */       this.latestFidoIssueStatus = latestIssueStatus;
/* 408 */     } else if (AuthMethodTypes.BIOTP.equals(authType)) {
/* 409 */       this.latestBiotpIssueStatus = latestIssueStatus;
/*     */     } else {
/* 411 */       this.latestSaotpIssueStatus = latestIssueStatus;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRegCumulation(AuthMethodTypes authType) {
/* 417 */     if (AuthMethodTypes.FIDO.equals(authType))
/* 418 */       return this.fidoRegCumulation; 
/* 419 */     if (AuthMethodTypes.BIOTP.equals(authType)) {
/* 420 */       return this.biotpRegCumulation;
/*     */     }
/* 422 */     return this.saotpRegCumulation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addRegCumulation(AuthMethodTypes authType) {
/* 427 */     if (AuthMethodTypes.FIDO.equals(authType)) {
/* 428 */       this.fidoRegCumulation++;
/* 429 */     } else if (AuthMethodTypes.BIOTP.equals(authType)) {
/* 430 */       this.biotpRegCumulation++;
/*     */     } else {
/* 432 */       this.saotpRegCumulation++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserDeviceStatus getBiotpStatus() {
/* 440 */     return this.biotpStatus;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public UserDeviceStatus getBiotpStatusDesc() {
/* 445 */     return this.biotpStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiotpStatus(UserDeviceStatus biotpStatus) {
/* 452 */     this.biotpStatus = biotpStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserDeviceStatus getFidoStatus() {
/* 459 */     return this.fidoStatus;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public UserDeviceStatus getFidoStatusDesc() {
/* 464 */     return this.fidoStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFidoStatus(UserDeviceStatus fidoStatus) {
/* 471 */     this.fidoStatus = fidoStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserVO getUser() {
/* 478 */     return this.user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUser(UserVO user) {
/* 485 */     this.user = user;
/*     */   }
/*     */   
/*     */   public int getUserVerificationFailCnt() {
/* 489 */     return this.userVerificationFailCnt;
/*     */   }
/*     */   
/*     */   public void setUserVerificationFailCnt(int userVerificationFailCnt) {
/* 493 */     this.userVerificationFailCnt = userVerificationFailCnt;
/*     */   }
/*     */   
/*     */   public void addUserVerificationFlag(int userVerificationFlag) {
/* 497 */     this.userVerificationFailCnt += userVerificationFlag;
/*     */   }
/*     */   
/*     */   public void resetUserVerificationFailCnt() {
/* 501 */     this.userVerificationFailCnt = 0;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public UserDeviceStatus getStatusDesc() {
/* 506 */     return this.status;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AgentOsTypes getOsTypeDesc() {
/* 511 */     if (this.deviceType == null)
/* 512 */       return AgentOsTypes.ANANYMOUS; 
/* 513 */     return this.deviceType.getOsType();
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.UserDeviceVO createNewInstance(UserVO user, String deviceId, DeviceTypes deviceType, String model, String alias, AuthMethodTypes authType, DeviceIssueranceStatus latestIssueStatus) {
/* 517 */     com.dreammirae.mmth.vo.UserDeviceVO vo = new com.dreammirae.mmth.vo.UserDeviceVO();
/*     */     
/* 519 */     if (user != null) {
/* 520 */       vo.setUserId(user.getId());
/* 521 */       vo.setUser(user);
/*     */     } 
/*     */     
/* 524 */     vo.setDeviceId(deviceId);
/* 525 */     vo.setDeviceType(deviceType);
/* 526 */     vo.setModel(model);
/* 527 */     vo.setAlias(alias);
/* 528 */     vo.setStatus(UserDeviceStatus.NOT_AVAILABLE);
/* 529 */     vo.setDisabled(DisabledStatus.ENABLED);
/* 530 */     vo.setTsReg(System.currentTimeMillis());
/* 531 */     vo.setLatestIssueStatus(authType, latestIssueStatus);
/* 532 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateUniqueFields(String deviceId, DeviceTypes deviceType) throws ReturnCodeException {
/* 541 */     if (StringUtils.isEmpty(deviceId)) {
/* 542 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.requiredParam", new Object[] { "deviceId" }));
/*     */     }
/*     */ 
/*     */     
/* 546 */     if (deviceType == null) {
/* 547 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.requiredParam", new Object[] { "deviceType" }));
/*     */     }
/*     */ 
/*     */     
/* 551 */     if (deviceId.matches("^[A-Z0-9]{64}$")) {
/* 552 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.invalidParam", new Object[] { "deviceId", "'deviceId' length must be 64." }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void validateDeviceId(String deviceId) throws ReturnCodeException {
/* 558 */     if (StringUtils.isEmpty(deviceId)) {
/* 559 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.requiredParam", new Object[] { "deviceId" }));
/*     */     }
/*     */ 
/*     */     
/* 563 */     if (!deviceId.matches("^[A-Z0-9]{64}$")) {
/* 564 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.invalidParam", new Object[] { "deviceId", "'deviceId' length must be 64." }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 571 */     StringBuilder builder = new StringBuilder();
/* 572 */     builder.append("UserDeviceVO [id=").append(this.id).append(", userId=").append(this.userId).append(", deviceId=")
/* 573 */       .append(this.deviceId).append(", deviceType=").append(this.deviceType).append(", model=").append(this.model)
/* 574 */       .append(", alias=").append(this.alias).append(", disabled=").append(this.disabled).append(", status=")
/* 575 */       .append(this.status).append(", tsReg=").append(this.tsReg).append(", tsUpdated=").append(this.tsUpdated)
/* 576 */       .append(", latestIssueStatus=").append(this.latestIssueStatus).append(", tokenId=").append(this.tokenId)
/* 577 */       .append(", appRegCnt=").append(this.appRegCnt).append(", latestFidoIssueStatus=")
/* 578 */       .append(this.latestFidoIssueStatus).append(", latestBiotpIssueStatus=").append(this.latestBiotpIssueStatus)
/* 579 */       .append(", latestSaotpIssueStatus=").append(this.latestSaotpIssueStatus).append(", fidoRegCumulation=")
/* 580 */       .append(this.fidoRegCumulation).append(", biotpRegCumulation=").append(this.biotpRegCumulation)
/* 581 */       .append(", saotpRegCumulation=").append(this.saotpRegCumulation).append(", biotpStatus=").append(this.biotpStatus)
/* 582 */       .append(", fidoStatus=").append(this.fidoStatus).append(", userVerificationFailCnt=")
/* 583 */       .append(this.userVerificationFailCnt).append("]");
/* 584 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 594 */     out.writeInt(3);
/* 595 */     SerializationUtils.writeSafeUTF(out, this.latestIssueStatus.name());
/* 596 */     SerializationUtils.writeSafeUTF(out, this.tokenId);
/* 597 */     out.writeInt(this.appRegCnt);
/* 598 */     SerializationUtils.writeSafeUTF(out, this.latestFidoIssueStatus.name());
/* 599 */     SerializationUtils.writeSafeUTF(out, this.latestBiotpIssueStatus.name());
/* 600 */     SerializationUtils.writeSafeUTF(out, this.latestSaotpIssueStatus.name());
/* 601 */     out.writeInt(this.fidoRegCumulation);
/* 602 */     out.writeInt(this.biotpRegCumulation);
/* 603 */     out.writeInt(this.saotpRegCumulation);
/* 604 */     out.writeInt(this.userVerificationFailCnt);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 608 */     int ver = in.readInt();
/* 609 */     if (ver == 1) {
/* 610 */       this.latestIssueStatus = DeviceIssueranceStatus.getByName(SerializationUtils.readSafeUTF(in));
/* 611 */       this.tokenId = SerializationUtils.readSafeUTF(in);
/* 612 */       this.appRegCnt = in.readInt();
/* 613 */       this.latestFidoIssueStatus = this.latestIssueStatus;
/* 614 */       this.latestBiotpIssueStatus = DeviceIssueranceStatus.NEW_USER_REGISTER;
/* 615 */       this.latestSaotpIssueStatus = DeviceIssueranceStatus.NEW_USER_REGISTER;
/* 616 */       this.fidoRegCumulation = this.appRegCnt;
/* 617 */       this.userVerificationFailCnt = 0;
/*     */     }
/* 619 */     else if (ver == 2) {
/* 620 */       this.latestIssueStatus = DeviceIssueranceStatus.getByName(SerializationUtils.readSafeUTF(in));
/* 621 */       this.tokenId = SerializationUtils.readSafeUTF(in);
/* 622 */       this.appRegCnt = in.readInt();
/* 623 */       this.latestFidoIssueStatus = DeviceIssueranceStatus.getByName(SerializationUtils.readSafeUTF(in));
/* 624 */       this.latestBiotpIssueStatus = DeviceIssueranceStatus.getByName(SerializationUtils.readSafeUTF(in));
/* 625 */       this.latestSaotpIssueStatus = DeviceIssueranceStatus.getByName(SerializationUtils.readSafeUTF(in));
/* 626 */       this.fidoRegCumulation = in.readInt();
/* 627 */       this.biotpRegCumulation = in.readInt();
/* 628 */       this.saotpRegCumulation = in.readInt();
/* 629 */       this.userVerificationFailCnt = 0;
/*     */     }
/* 631 */     else if (ver == 3) {
/* 632 */       this.latestIssueStatus = DeviceIssueranceStatus.getByName(SerializationUtils.readSafeUTF(in));
/* 633 */       this.tokenId = SerializationUtils.readSafeUTF(in);
/* 634 */       this.appRegCnt = in.readInt();
/* 635 */       this.latestFidoIssueStatus = DeviceIssueranceStatus.getByName(SerializationUtils.readSafeUTF(in));
/* 636 */       this.latestBiotpIssueStatus = DeviceIssueranceStatus.getByName(SerializationUtils.readSafeUTF(in));
/* 637 */       this.latestSaotpIssueStatus = DeviceIssueranceStatus.getByName(SerializationUtils.readSafeUTF(in));
/* 638 */       this.fidoRegCumulation = in.readInt();
/* 639 */       this.biotpRegCumulation = in.readInt();
/* 640 */       this.saotpRegCumulation = in.readInt();
/* 641 */       this.userVerificationFailCnt = in.readInt();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\UserDeviceVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */