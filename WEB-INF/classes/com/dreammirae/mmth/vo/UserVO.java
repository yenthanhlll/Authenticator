/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.MMTHConstants;
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.AuthManagerVO;
/*     */ import com.dreammirae.mmth.vo.UserAnnotationVO;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*     */ import com.dreammirae.mmth.vo.types.MethodStatus;
/*     */ import com.dreammirae.mmth.vo.types.MethodTypes;
/*     */ import com.dreammirae.mmth.vo.types.SettingEnabled;
/*     */ import com.dreammirae.mmth.vo.types.UserStatus;
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
/*     */ public class UserVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*  64 */   private int id = -1;
/*     */   
/*     */   private String username;
/*     */   
/*     */   private ProductType productType;
/*     */   
/*     */   private String multiLoginYN;
/*     */   
/*     */   private String accountName;
/*     */   
/*     */   private DisabledStatus disabled;
/*     */   
/*     */   private UserStatus status;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsReg;
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsUpdated;
/*  82 */   private SettingEnabled multiDeviceEnabled = SettingEnabled.DISABLED;
/*     */   
/*     */   private String tokenId;
/*     */   
/*     */   private String tokenType;
/*     */   
/*     */   private int deviceRegCumulation;
/*     */   
/*     */   private int fidoRegCumulation;
/*     */   
/*     */   private int saotpRegCumulation;
/*     */   
/*     */   private int biotpRegCumulation;
/*  95 */   private int reprAppAgentId = -1;
/*     */ 
/*     */   
/*     */   private UserAnnotationVO annotation;
/*     */ 
/*     */   
/*     */   private UserStatus biotpStatus;
/*     */ 
/*     */   
/*     */   private UserStatus fidoStatus;
/*     */ 
/*     */   
/*     */   private AuthManagerVO biotpAuthManager;
/*     */   
/*     */   private AuthManagerVO fidoAuthManager;
/*     */   
/*     */   private MethodTypes assignType;
/*     */   
/*     */   private long tsAssignTypeUpdated;
/*     */   
/* 115 */   private MethodStatus mOtpIssueStatus = MethodStatus.NOT_AVAILABLE;
/*     */   
/*     */   private long tsMOtpStatusUpdated;
/*     */   
/* 119 */   private MethodStatus timeOtpIssueStatus = MethodStatus.NOT_AVAILABLE;
/*     */   
/*     */   private long tsTimeOtpStatusUpdated;
/*     */   
/* 123 */   private MethodStatus advancedOtpIssueStatus = MethodStatus.NOT_AVAILABLE;
/*     */   
/*     */   private long tsAdvancedOtpStatusUpdated;
/*     */   
/* 127 */   private MethodStatus matrixIssueStatus = MethodStatus.NOT_AVAILABLE;
/*     */ 
/*     */   
/*     */   private long tsMatrixStatusUpdated;
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   
/*     */   private static final int version = 2;
/*     */ 
/*     */   
/*     */   public int getId() {
/* 140 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/* 147 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/* 154 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/* 161 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProductType getProductType() {
/* 168 */     return this.productType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProductType(ProductType productType) {
/* 175 */     this.productType = productType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMultiLoginYN() {
/* 182 */     return this.multiLoginYN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiLoginYN(String multiLoginYN) {
/* 189 */     this.multiLoginYN = multiLoginYN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAccountName() {
/* 196 */     return this.accountName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccountName(String accountName) {
/* 203 */     this.accountName = accountName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisabledStatus getDisabled() {
/* 210 */     return this.disabled;
/*     */   }
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
/*     */   public UserStatus getStatus() {
/* 224 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(UserStatus status) {
/* 231 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 238 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 245 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsUpdated() {
/* 252 */     return this.tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsUpdated(long tsUpdated) {
/* 259 */     this.tsUpdated = tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SettingEnabled getMultiDeviceEnabled() {
/* 266 */     return this.multiDeviceEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiDeviceEnabled(SettingEnabled enabled) {
/* 273 */     this.multiDeviceEnabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 280 */     return this.tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenId(String tokenId) {
/* 287 */     this.tokenId = tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTokenType() {
/* 294 */     return this.tokenType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenType(String tokenType) {
/* 301 */     this.tokenType = tokenType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeviceRegCumulation() {
/* 308 */     return this.deviceRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceRegCumulation(int deviceRegCumulation) {
/* 315 */     this.deviceRegCumulation = deviceRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReprAppAgentId() {
/* 322 */     return this.reprAppAgentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReprAppAgentId(int reprAppAgentId) {
/* 329 */     this.reprAppAgentId = reprAppAgentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFidoRegCumulation() {
/* 336 */     return this.fidoRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFidoRegCumulation(int fidoRegCumulation) {
/* 343 */     this.fidoRegCumulation = fidoRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSaotpRegCumulation() {
/* 350 */     return this.saotpRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaotpRegCumulation(int saotpRegCumulation) {
/* 357 */     this.saotpRegCumulation = saotpRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBiotpRegCumulation() {
/* 364 */     return this.biotpRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public DisabledStatus getDisabledDesc() {
/* 372 */     return this.disabled;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public UserStatus getStatusDesc() {
/* 377 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthManagerVO getBiotpAuthManager() {
/* 384 */     return this.biotpAuthManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiotpAuthManager(AuthManagerVO biotpAuthManager) {
/* 391 */     this.biotpAuthManager = biotpAuthManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthManagerVO getFidoAuthManager() {
/* 398 */     return this.fidoAuthManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFidoAuthManager(AuthManagerVO fidoAuthManager) {
/* 405 */     this.fidoAuthManager = fidoAuthManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiotpRegCumulation(int biotpRegCumulation) {
/* 412 */     this.biotpRegCumulation = biotpRegCumulation;
/*     */   }
/*     */   
/*     */   public void addRegCumulation(AuthMethodTypes authType) {
/* 416 */     if (AuthMethodTypes.FIDO.equals(authType)) {
/* 417 */       this.fidoRegCumulation++;
/* 418 */     } else if (AuthMethodTypes.BIOTP.equals(authType)) {
/* 419 */       this.biotpRegCumulation++;
/*     */     } else {
/* 421 */       this.saotpRegCumulation++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserAnnotationVO getAnnotation() {
/* 429 */     return this.annotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnnotation(UserAnnotationVO annotation) {
/* 436 */     this.annotation = annotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserStatus getBiotpStatus() {
/* 443 */     return this.biotpStatus;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public UserStatus getBiotpStatusDesc() {
/* 448 */     return this.biotpStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiotpStatus(UserStatus biotpStatus) {
/* 455 */     this.biotpStatus = biotpStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserStatus getFidoStatus() {
/* 462 */     return this.fidoStatus;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public UserStatus getFidoStatusDesc() {
/* 467 */     return this.fidoStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFidoStatus(UserStatus fidoStatus) {
/* 474 */     this.fidoStatus = fidoStatus;
/*     */   }
/*     */   
/*     */   public MethodTypes getAssignType() {
/* 478 */     return this.assignType;
/*     */   }
/*     */   
/*     */   public void setAssignType(MethodTypes assignType) {
/* 482 */     this.assignType = assignType;
/*     */   }
/*     */   
/*     */   public long getTsAssignTypeUpdated() {
/* 486 */     return this.tsAssignTypeUpdated;
/*     */   }
/*     */   
/*     */   public void setTsAssignTypeUpdated(long tsAssignTypeUpdated) {
/* 490 */     this.tsAssignTypeUpdated = tsAssignTypeUpdated;
/*     */   }
/*     */   
/*     */   public MethodStatus getmOtpIssueStatus() {
/* 494 */     return this.mOtpIssueStatus;
/*     */   }
/*     */   
/*     */   public void setmOtpIssueStatus(MethodStatus mOtpIssueStatus) {
/* 498 */     this.mOtpIssueStatus = mOtpIssueStatus;
/*     */   }
/*     */   
/*     */   public long getTsMOtpStatusUpdated() {
/* 502 */     return this.tsMOtpStatusUpdated;
/*     */   }
/*     */   
/*     */   public void setTsMOtpStatusUpdated(long tsMOtpStatusUpdated) {
/* 506 */     this.tsMOtpStatusUpdated = tsMOtpStatusUpdated;
/*     */   }
/*     */   
/*     */   public MethodStatus getTimeOtpIssueStatus() {
/* 510 */     return this.timeOtpIssueStatus;
/*     */   }
/*     */   
/*     */   public void setTimeOtpIssueStatus(MethodStatus timeOtpIssueStatus) {
/* 514 */     this.timeOtpIssueStatus = timeOtpIssueStatus;
/*     */   }
/*     */   
/*     */   public long getTsTimeOtpStatusUpdated() {
/* 518 */     return this.tsTimeOtpStatusUpdated;
/*     */   }
/*     */   
/*     */   public void setTsTimeOtpStatusUpdated(long tsTimeOtpStatusUpdated) {
/* 522 */     this.tsTimeOtpStatusUpdated = tsTimeOtpStatusUpdated;
/*     */   }
/*     */   
/*     */   public MethodStatus getAdvancedOtpIssueStatus() {
/* 526 */     return this.advancedOtpIssueStatus;
/*     */   }
/*     */   
/*     */   public void setAdvancedOtpIssueStatus(MethodStatus advancedOtpIssueStatus) {
/* 530 */     this.advancedOtpIssueStatus = advancedOtpIssueStatus;
/*     */   }
/*     */   
/*     */   public long getTsAdvancedOtpStatusUpdated() {
/* 534 */     return this.tsAdvancedOtpStatusUpdated;
/*     */   }
/*     */   
/*     */   public void setTsAdvancedOtpStatusUpdated(long tsAdvancedOtpStatusUpdated) {
/* 538 */     this.tsAdvancedOtpStatusUpdated = tsAdvancedOtpStatusUpdated;
/*     */   }
/*     */   
/*     */   public MethodStatus getMatrixIssueStatus() {
/* 542 */     return this.matrixIssueStatus;
/*     */   }
/*     */   
/*     */   public void setMatrixIssueStatus(MethodStatus matrixIssueStatus) {
/* 546 */     this.matrixIssueStatus = matrixIssueStatus;
/*     */   }
/*     */   
/*     */   public long getTsMatrixStatusUpdated() {
/* 550 */     return this.tsMatrixStatusUpdated;
/*     */   }
/*     */   
/*     */   public void setTsMatrixStatusUpdated(long tsMatrixStatusUpdated) {
/* 554 */     this.tsMatrixStatusUpdated = tsMatrixStatusUpdated;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.UserVO createNewInstance(String username) {
/* 558 */     com.dreammirae.mmth.vo.UserVO vo = new com.dreammirae.mmth.vo.UserVO();
/* 559 */     vo.setUsername(username);
/* 560 */     vo.setStatus(UserStatus.NOT_AVAILABLE);
/* 561 */     vo.setDisabled(DisabledStatus.ENABLED);
/* 562 */     vo.setTsReg(System.currentTimeMillis());
/* 563 */     vo.setMultiDeviceEnabled(SettingEnabled.DISABLED);
/* 564 */     return vo;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.UserVO createNewInstance(String username, int productTypeCode, String accountName) {
/* 568 */     com.dreammirae.mmth.vo.UserVO vo = new com.dreammirae.mmth.vo.UserVO();
/* 569 */     vo.setUsername(username);
/* 570 */     vo.setMultiLoginYN("N");
/* 571 */     vo.setProductType(ProductType.getProductType(productTypeCode));
/* 572 */     vo.setStatus(UserStatus.AVAILABLE);
/* 573 */     if (ProductType.getProductType(productTypeCode) == ProductType.NONE) {
/* 574 */       vo.setStatus(UserStatus.NOT_AVAILABLE);
/*     */     }
/* 576 */     if (accountName != null)
/*     */     {
/* 578 */       vo.setAccountName(accountName);
/*     */     }
/* 580 */     vo.setDisabled(DisabledStatus.ENABLED);
/* 581 */     vo.setTsReg(System.currentTimeMillis());
/* 582 */     vo.setMultiDeviceEnabled(SettingEnabled.DISABLED);
/* 583 */     return vo;
/*     */   }
/*     */   
/*     */   public static void validateUsername(String username) throws ReturnCodeException {
/* 587 */     if (StringUtils.isEmpty(username)) {
/* 588 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.requiredParam", new Object[] { "userName" }));
/*     */     }
/*     */ 
/*     */     
/* 592 */     if (!MMTHConstants.REGEX_USERNAME_PATTERN.matcher(username).matches()) {
/* 593 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.invalidParam", new Object[] { "userName", new I18nMessage("validate.regex.username") }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void validateAccountName(String accountName) throws ReturnCodeException {
/* 599 */     if (StringUtils.isEmpty(accountName)) {
/* 600 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, new I18nMessage("exception.requiredParam", new Object[] { "accountName" }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRegCumulation(AuthMethodTypes authType) {
/* 606 */     if (AuthMethodTypes.FIDO.equals(authType))
/* 607 */       return this.fidoRegCumulation; 
/* 608 */     if (AuthMethodTypes.BIOTP.equals(authType)) {
/* 609 */       return this.biotpRegCumulation;
/*     */     }
/* 611 */     return this.saotpRegCumulation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 617 */     StringBuilder builder = new StringBuilder();
/* 618 */     builder.append("UserVO [id=").append(this.id).append(", username=").append(this.username).append(", accountName=").append(this.accountName).append(", disabled=")
/* 619 */       .append(this.disabled).append(", status=").append(this.status).append(", tsReg=").append(this.tsReg)
/* 620 */       .append(", tsUpdated=").append(this.tsUpdated).append(", multiDeviceEnabled=").append(this.multiDeviceEnabled)
/* 621 */       .append(", tokenId=").append(this.tokenId).append(", deviceRegCumulation=").append(this.deviceRegCumulation)
/* 622 */       .append(", fidoRegCumulation=").append(this.fidoRegCumulation).append(", saotpRegCumulation=")
/* 623 */       .append(this.saotpRegCumulation).append(", biotpRegCumulation=").append(this.biotpRegCumulation)
/* 624 */       .append(", reprAppAgentId=").append(this.reprAppAgentId).append(", annotation=").append(this.annotation)
/* 625 */       .append(", biotpStatus=").append(this.biotpStatus).append(", fidoStatus=").append(this.fidoStatus)
/* 626 */       .append(", biotpAuthManager=").append(this.biotpAuthManager).append(", fidoAuthManager=")
/* 627 */       .append(this.fidoAuthManager).append(", assignType=").append(this.assignType).append(", tsAssignTypeUpdated=")
/* 628 */       .append(this.tsAssignTypeUpdated).append(", mOtpIssueStatus=").append(this.mOtpIssueStatus)
/* 629 */       .append(", tsMOtpStatusUpdated=").append(this.tsMOtpStatusUpdated).append(", timeOtpIssueStatus=")
/* 630 */       .append(this.timeOtpIssueStatus).append(", tsTimeOtpStatusUpdated=").append(this.tsTimeOtpStatusUpdated)
/* 631 */       .append(", advancedOtpIssueStatus=").append(this.advancedOtpIssueStatus)
/* 632 */       .append(", tsAdvancedOtpStatusUpdated=").append(this.tsAdvancedOtpStatusUpdated)
/* 633 */       .append(", matrixIssueStatus=").append(this.matrixIssueStatus).append(", tsMatrixStatusUpdated=")
/* 634 */       .append(this.tsMatrixStatusUpdated).append("]");
/* 635 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 645 */     out.writeInt(2);
/* 646 */     out.writeBoolean(this.multiDeviceEnabled.toBoolean());
/* 647 */     SerializationUtils.writeSafeUTF(out, this.tokenId);
/* 648 */     out.writeInt(this.deviceRegCumulation);
/* 649 */     out.writeInt(this.fidoRegCumulation);
/* 650 */     out.writeInt(this.saotpRegCumulation);
/* 651 */     out.writeInt(this.biotpRegCumulation);
/* 652 */     out.writeInt(this.reprAppAgentId);
/*     */     
/* 654 */     SerializationUtils.writeSafeObject(out, this.assignType);
/* 655 */     out.writeLong(this.tsAssignTypeUpdated);
/* 656 */     SerializationUtils.writeSafeObject(out, this.mOtpIssueStatus);
/* 657 */     out.writeLong(this.tsMOtpStatusUpdated);
/* 658 */     SerializationUtils.writeSafeObject(out, this.timeOtpIssueStatus);
/* 659 */     out.writeLong(this.tsTimeOtpStatusUpdated);
/* 660 */     SerializationUtils.writeSafeObject(out, this.advancedOtpIssueStatus);
/* 661 */     out.writeLong(this.tsAdvancedOtpStatusUpdated);
/* 662 */     SerializationUtils.writeSafeObject(out, this.matrixIssueStatus);
/* 663 */     out.writeLong(this.tsMatrixStatusUpdated);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 667 */     int ver = in.readInt();
/* 668 */     if (ver == 1) {
/* 669 */       this.multiDeviceEnabled = SettingEnabled.getSettingEnabled(in.readBoolean());
/* 670 */       this.tokenId = SerializationUtils.readSafeUTF(in);
/* 671 */       this.deviceRegCumulation = in.readInt();
/* 672 */       this.fidoRegCumulation = in.readInt();
/* 673 */       this.saotpRegCumulation = in.readInt();
/* 674 */       this.biotpRegCumulation = in.readInt();
/* 675 */       this.reprAppAgentId = in.readInt();
/* 676 */     } else if (ver == 2) {
/* 677 */       this.multiDeviceEnabled = SettingEnabled.getSettingEnabled(in.readBoolean());
/* 678 */       this.tokenId = SerializationUtils.readSafeUTF(in);
/* 679 */       this.deviceRegCumulation = in.readInt();
/* 680 */       this.fidoRegCumulation = in.readInt();
/* 681 */       this.saotpRegCumulation = in.readInt();
/* 682 */       this.biotpRegCumulation = in.readInt();
/* 683 */       this.reprAppAgentId = in.readInt();
/*     */       
/* 685 */       this.assignType = (MethodTypes)SerializationUtils.readSafeObject(in);
/* 686 */       this.tsAssignTypeUpdated = in.readLong();
/* 687 */       this.mOtpIssueStatus = (MethodStatus)SerializationUtils.readSafeObject(in);
/* 688 */       this.tsMOtpStatusUpdated = in.readLong();
/* 689 */       this.timeOtpIssueStatus = (MethodStatus)SerializationUtils.readSafeObject(in);
/* 690 */       this.tsTimeOtpStatusUpdated = in.readLong();
/* 691 */       this.advancedOtpIssueStatus = (MethodStatus)SerializationUtils.readSafeObject(in);
/* 692 */       this.tsAdvancedOtpStatusUpdated = in.readLong();
/* 693 */       this.matrixIssueStatus = (MethodStatus)SerializationUtils.readSafeObject(in);
/* 694 */       this.tsMatrixStatusUpdated = in.readLong();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void validate(RestResponse resp) {}
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\UserVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */