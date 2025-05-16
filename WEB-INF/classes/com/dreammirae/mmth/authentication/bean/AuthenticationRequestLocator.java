/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.MMTHConstants;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.bean.MiraeAssetCustomData;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import java.lang.reflect.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthenticationRequestLocator
/*     */ {
/*     */   private String username;
/*     */   private String tid;
/*     */   private String otp;
/*     */   private String deviceId;
/*     */   private DeviceTypes deviceType;
/*     */   private String pakageName;
/*     */   private String model;
/*     */   private String alias;
/*     */   private String aaid;
/*     */   private String tranInfo;
/*     */   private String fcmTokenId;
/*     */   private String otpSn;
/*     */   private YNStatus tranInfoYn;
/*     */   private byte[] tranInfoOrg;
/*     */   private Integer userVerificationFlag;
/*     */   private String qrSessionId;
/*     */   private String authData;
/*     */   private String newAuthData;
/*     */   private int productType;
/*     */   private int authType;
/*     */   private int authLogType;
/*     */   private String macAddress;
/*     */   private long ip;
/*     */   private String sessionCode;
/*     */   private MiraeAssetCustomData customData;
/*     */   
/*     */   public String getUsername() {
/*  82 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/*  90 */     this.username = username;
/*     */   }
/*     */   
/*     */   public boolean hasUsername() {
/*  94 */     return !StringUtils.isEmpty(this.username);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTid() {
/* 101 */     return this.tid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTid(String tid) {
/* 109 */     this.tid = tid;
/*     */   }
/*     */   
/*     */   public boolean hasTid() {
/* 113 */     return !StringUtils.isEmpty(this.tid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOtp() {
/* 120 */     return this.otp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOtp(String otp) {
/* 128 */     this.otp = otp;
/*     */   }
/*     */   
/*     */   public boolean hasOtp() {
/* 132 */     return !StringUtils.isEmpty(this.otp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceId() {
/* 139 */     return this.deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/* 147 */     this.deviceId = deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceTypes getDeviceType() {
/* 154 */     return this.deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceType(DeviceTypes deviceType) {
/* 162 */     this.deviceType = deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPakageName() {
/* 169 */     return this.pakageName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPakageName(String pakageName) {
/* 177 */     this.pakageName = pakageName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModel() {
/* 184 */     return this.model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(String model) {
/* 192 */     this.model = model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlias() {
/* 199 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlias(String alias) {
/* 207 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAaid() {
/* 214 */     return this.aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAaid(String aaid) {
/* 222 */     this.aaid = aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranInfo() {
/* 229 */     return this.tranInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranInfo(String tranInfo) {
/* 237 */     this.tranInfo = tranInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFcmTokenId() {
/* 244 */     return this.fcmTokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFcmTokenId(String fcmTokenId) {
/* 252 */     this.fcmTokenId = fcmTokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOtpSn() {
/* 259 */     return this.otpSn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOtpSn(String otpSn) {
/* 266 */     this.otpSn = otpSn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public YNStatus getTranInfoYn() {
/* 273 */     return this.tranInfoYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranInfoYn(YNStatus tranInfoYn) {
/* 280 */     this.tranInfoYn = tranInfoYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getTranInfoOrg() {
/* 287 */     return this.tranInfoOrg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranInfoOrg(byte[] tranInfoOrg) {
/* 294 */     this.tranInfoOrg = tranInfoOrg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getUserVerificationFlag() {
/* 301 */     return this.userVerificationFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserVerificationFlag(Integer userVerificationFlag) {
/* 308 */     this.userVerificationFlag = userVerificationFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getQrSessionId() {
/* 315 */     return this.qrSessionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQrSessionId(String qrSessionId) {
/* 322 */     this.qrSessionId = qrSessionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAuthData() {
/* 329 */     return this.authData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthData(String authData) {
/* 336 */     this.authData = authData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewAuthData() {
/* 343 */     return this.newAuthData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNewAuthData(String newAuthData) {
/* 350 */     this.newAuthData = newAuthData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProductType() {
/* 357 */     return this.productType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProductType(int type) {
/* 364 */     this.productType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProductAuthType() {
/* 371 */     return this.authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProductAuthType(int type) {
/* 378 */     this.authType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAuthLogType() {
/* 385 */     return this.authLogType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthLogType(int authLogType) {
/* 392 */     this.authLogType = authLogType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMacAddress() {
/* 399 */     return this.macAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMacAddress(String macAddress) {
/* 406 */     this.macAddress = macAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getIp() {
/* 413 */     return this.ip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIp(long ip) {
/* 420 */     this.ip = ip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSessionCode() {
/* 427 */     return this.sessionCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSessionCode(String sessionCode) {
/* 434 */     this.sessionCode = sessionCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonSerialize(JsonObject jsonObject, Type type, JsonSerializationContext ctx) {
/* 440 */     if (!StringUtils.isEmpty(this.username)) {
/* 441 */       jsonObject.addProperty("userName", Base64Utils.encodeUrl(this.username));
/*     */     }
/*     */ 
/*     */     
/* 445 */     if (!StringUtils.isEmpty(this.tid)) {
/* 446 */       jsonObject.addProperty("tid", Base64Utils.encodeUrl(this.tid));
/*     */     }
/*     */ 
/*     */     
/* 450 */     if (!StringUtils.isEmpty(this.otp)) {
/* 451 */       jsonObject.addProperty("otp", Base64Utils.encodeUrl(this.otp));
/*     */     }
/*     */ 
/*     */     
/* 455 */     if (!StringUtils.isEmpty(this.deviceId)) {
/* 456 */       jsonObject.addProperty("deviceId", Base64Utils.encodeUrl(this.deviceId));
/*     */     }
/*     */ 
/*     */     
/* 460 */     if (this.deviceType != null) {
/* 461 */       jsonObject.addProperty("deviceType", Base64Utils.encodeUrl(this.deviceType.getCode()));
/*     */     }
/*     */ 
/*     */     
/* 465 */     if (!StringUtils.isEmpty(this.pakageName)) {
/* 466 */       jsonObject.addProperty("pkgName", Base64Utils.encodeUrl(this.pakageName));
/*     */     }
/*     */ 
/*     */     
/* 470 */     if (!StringUtils.isEmpty(this.model)) {
/* 471 */       jsonObject.addProperty("model", Base64Utils.encodeUrl(this.model));
/*     */     }
/*     */ 
/*     */     
/* 475 */     if (!StringUtils.isEmpty(this.alias)) {
/* 476 */       jsonObject.addProperty("alias", Base64Utils.encodeUrl(this.alias));
/*     */     }
/*     */ 
/*     */     
/* 480 */     if (!StringUtils.isEmpty(this.aaid)) {
/* 481 */       jsonObject.addProperty("aaid", Base64Utils.encodeUrl(this.aaid));
/*     */     }
/*     */ 
/*     */     
/* 485 */     if (!StringUtils.isEmpty(this.tranInfo)) {
/* 486 */       jsonObject.addProperty("tranInfo", Base64Utils.encodeUrl(this.tranInfo));
/*     */     }
/*     */ 
/*     */     
/* 490 */     if (!StringUtils.isEmpty(this.fcmTokenId)) {
/* 491 */       jsonObject.addProperty("fcmTokenId", Base64Utils.encodeUrl(this.fcmTokenId));
/*     */     }
/*     */ 
/*     */     
/* 495 */     if (!StringUtils.isEmpty(this.otpSn)) {
/* 496 */       jsonObject.addProperty("otpSn", Base64Utils.encodeUrl(this.otpSn));
/*     */     }
/*     */ 
/*     */     
/* 500 */     if (this.tranInfoOrg != null) {
/* 501 */       jsonObject.addProperty("tranInfoOrg", Base64Utils.encodeUrl(this.tranInfoOrg));
/*     */     }
/*     */ 
/*     */     
/* 505 */     if (this.userVerificationFlag != null) {
/* 506 */       jsonObject.addProperty("userVerificationFlag", Integer.valueOf(this.userVerificationFlag.intValue()));
/*     */     }
/*     */ 
/*     */     
/* 510 */     if (!StringUtils.isEmpty(this.qrSessionId)) {
/* 511 */       jsonObject.addProperty("qrSessionId", Base64Utils.encodeUrl(this.qrSessionId));
/*     */     }
/*     */ 
/*     */     
/* 515 */     if (!StringUtils.isEmpty(this.authData)) {
/* 516 */       jsonObject.addProperty("authData", Base64Utils.encodeUrl(this.authData));
/*     */     }
/*     */ 
/*     */     
/* 520 */     if (!StringUtils.isEmpty(this.newAuthData)) {
/* 521 */       jsonObject.addProperty("newAuthData", Base64Utils.encodeUrl(this.newAuthData));
/*     */     }
/*     */     
/* 524 */     if (this.productType != 0) {
/* 525 */       jsonObject.addProperty("productType", Integer.valueOf(this.productType));
/*     */     }
/*     */ 
/*     */     
/* 529 */     if (this.authType != 0) {
/* 530 */       jsonObject.addProperty("productAuthType", Integer.valueOf(this.authType));
/*     */     }
/*     */ 
/*     */     
/* 534 */     if (this.authLogType != 0) {
/* 535 */       jsonObject.addProperty("authLogType", Integer.valueOf(this.authType));
/*     */     }
/*     */ 
/*     */     
/* 539 */     if (!StringUtils.isEmpty(this.macAddress)) {
/* 540 */       jsonObject.addProperty("macAddress", Base64Utils.encodeUrl(this.macAddress));
/*     */     }
/*     */ 
/*     */     
/* 544 */     if (this.ip != 0L) {
/* 545 */       jsonObject.addProperty("ip", Long.valueOf(this.ip));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonDeserialization(JsonObject json, JsonDeserializationContext ctx) {
/* 553 */     setUsername(decodeString("userName", json));
/*     */ 
/*     */     
/* 556 */     setTid(decodeString("tid", json));
/*     */ 
/*     */     
/* 559 */     setOtp(decodeString("otp", json));
/*     */ 
/*     */     
/* 562 */     setDeviceId(decodeString("deviceId", json));
/*     */ 
/*     */     
/* 565 */     String deviceType = decodeString("deviceType", json);
/* 566 */     if (!StringUtils.isEmpty(deviceType)) {
/* 567 */       setDeviceType(DeviceTypes.getDeviceType(deviceType));
/*     */     }
/*     */ 
/*     */     
/* 571 */     setPakageName(decodeString("pkgName", json));
/*     */ 
/*     */     
/* 574 */     setModel(decodeString("model", json));
/*     */ 
/*     */     
/* 577 */     setAlias(decodeString("alias", json));
/*     */ 
/*     */     
/* 580 */     setAaid(decodeString("aaid", json));
/*     */ 
/*     */     
/* 583 */     setTranInfo(decodeString("tranInfo", json));
/*     */ 
/*     */     
/* 586 */     setFcmTokenId(decodeString("fcmTokenId", json));
/*     */ 
/*     */     
/* 589 */     setOtpSn(decodeString("otpSn", json));
/*     */ 
/*     */     
/* 592 */     if (json.has("tranInfoOrg")) {
/* 593 */       String value = json.get("tranInfoOrg").getAsString();
/* 594 */       setTranInfoOrg(Base64Utils.decodeRaw(value));
/*     */     } 
/*     */ 
/*     */     
/* 598 */     if (json.has("userVerificationFlag")) {
/* 599 */       int value = json.get("userVerificationFlag").getAsInt();
/* 600 */       setUserVerificationFlag(Integer.valueOf(value));
/*     */     } 
/*     */ 
/*     */     
/* 604 */     setQrSessionId(decodeString("qrSessionId", json));
/*     */ 
/*     */     
/* 607 */     if (json.has("authData")) {
/* 608 */       setAuthData(json.get("authData").getAsString());
/*     */     }
/*     */ 
/*     */     
/* 612 */     if (json.has("newAuthData")) {
/* 613 */       setNewAuthData(json.get("newAuthData").getAsString());
/*     */     }
/*     */ 
/*     */     
/* 617 */     if (json.has("productAuthType")) {
/* 618 */       setProductAuthType(json.get("productAuthType").getAsInt());
/*     */     }
/*     */ 
/*     */     
/* 622 */     if (json.has("productType")) {
/* 623 */       setProductType(json.get("productType").getAsInt());
/*     */     }
/*     */     
/* 626 */     if (json.has("ip")) {
/* 627 */       setIp(json.get("ip").getAsLong());
/*     */     }
/*     */     
/* 630 */     if (json.has("macAddress")) {
/* 631 */       setMacAddress(decodeString("macAddress", json));
/*     */     }
/*     */     
/* 634 */     if (json.has("authLogType")) {
/* 635 */       setAuthLogType(json.get("authLogType").getAsInt());
/*     */     }
/*     */ 
/*     */     
/* 639 */     if (json.has("sessionCode")) {
/* 640 */       setSessionCode(decodeString("sessionCode", json));
/*     */     }
/*     */   }
/*     */   
/*     */   private String decodeString(String key, JsonObject json) {
/* 645 */     if (json.has(key)) {
/* 646 */       String value = json.get(key).getAsString();
/* 647 */       return Base64Utils.decode(value);
/*     */     } 
/*     */     
/* 650 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 658 */     StringBuilder builder = new StringBuilder();
/* 659 */     builder.append("AuthenticationRequestLocator [username=");
/* 660 */     builder.append(this.username);
/*     */     
/* 662 */     if (!StringUtils.isEmpty(this.tid)) {
/* 663 */       builder.append(", tid=");
/* 664 */       builder.append(this.tid);
/*     */     } 
/*     */     
/* 667 */     if (!StringUtils.isEmpty(this.otp)) {
/* 668 */       builder.append(", otp=");
/* 669 */       builder.append(this.otp);
/*     */     } 
/*     */     
/* 672 */     if (!StringUtils.isEmpty(this.deviceId)) {
/* 673 */       builder.append(", deviceId=");
/* 674 */       builder.append(this.deviceId);
/*     */     } 
/*     */     
/* 677 */     if (this.deviceType != null) {
/* 678 */       builder.append(", deviceType=");
/* 679 */       builder.append(this.deviceType);
/*     */     } 
/*     */     
/* 682 */     if (!StringUtils.isEmpty(this.pakageName)) {
/* 683 */       builder.append(", pakageName=");
/* 684 */       builder.append(this.pakageName);
/*     */     } 
/*     */     
/* 687 */     if (!StringUtils.isEmpty(this.model)) {
/* 688 */       builder.append(", model=");
/* 689 */       builder.append(this.model);
/*     */     } 
/*     */     
/* 692 */     if (!StringUtils.isEmpty(this.alias)) {
/* 693 */       builder.append(", alias=");
/* 694 */       builder.append(this.alias);
/*     */     } 
/*     */ 
/*     */     
/* 698 */     if (!StringUtils.isEmpty(this.aaid)) {
/* 699 */       builder.append(", aaid=");
/* 700 */       builder.append(this.aaid);
/*     */     } 
/*     */     
/* 703 */     if (!StringUtils.isEmpty(this.fcmTokenId)) {
/* 704 */       builder.append(", fcmTokenId=");
/* 705 */       builder.append(this.fcmTokenId);
/*     */     } 
/*     */     
/* 708 */     if (!StringUtils.isEmpty(this.otpSn)) {
/* 709 */       builder.append(", otpSn=");
/* 710 */       builder.append(this.otpSn);
/*     */     } 
/*     */     
/* 713 */     if (!StringUtils.isEmpty(this.tranInfo)) {
/* 714 */       builder.append(", tranInfo=");
/* 715 */       builder.append(this.tranInfo);
/*     */     } 
/*     */     
/* 718 */     if (this.tranInfoYn != null) {
/* 719 */       builder.append(", tranInfoYn=");
/* 720 */       builder.append(this.tranInfoYn.getCode());
/*     */     } 
/*     */     
/* 723 */     if (this.tranInfoOrg != null && this.tranInfoOrg.length > 0) {
/* 724 */       builder.append(", tranInfoOrg=");
/* 725 */       builder.append(new String(this.tranInfoOrg, MMTHConstants.MS949_CS));
/*     */     } 
/*     */     
/* 728 */     if (!StringUtils.isEmpty(this.sessionCode)) {
/* 729 */       builder.append(", sessionCode=");
/* 730 */       builder.append(this.sessionCode);
/*     */     } 
/*     */     
/* 733 */     builder.append("]");
/* 734 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\bean\AuthenticationRequestLocator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */