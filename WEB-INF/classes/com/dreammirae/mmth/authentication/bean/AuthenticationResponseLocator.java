/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.vo.bean.AuthRequestContents;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthenticationResponseLocator
/*     */ {
/*     */   private ReturnCodes returnCode;
/*     */   private String issueCode;
/*     */   private Long issueCodeExpired;
/*     */   private Integer issueCodeFailCnt;
/*     */   private Integer authFailCnt;
/*     */   private String otpSn;
/*     */   private Long tsLatestIssuance;
/*     */   private String errorMessage;
/*     */   private String[] aaids;
/*     */   private AuthRequestContents authReqContents;
/*     */   private String deviceId;
/*     */   private DeviceTypes deviceType;
/*     */   private String pkgName;
/*     */   private String model;
/*     */   private String alias;
/*     */   private YNStatus tranInfoYn;
/*     */   private byte[] tranInfoEnc;
/*     */   private Integer uvTFCnt;
/*     */   private String tid;
/*     */   private Integer expiredDuration;
/*     */   private String qrData;
/*     */   private String multiLoginYN;
/*     */   private String status;
/*     */   private int productTypeCode;
/*     */   private String sessionCode;
/*     */   private String userName;
/*     */   
/*     */   public ReturnCodes getReturnCode() {
/*  86 */     return this.returnCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReturnCode(ReturnCodes returnCode) {
/*  93 */     this.returnCode = returnCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssueCode() {
/* 100 */     return this.issueCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueCode(String issueCode) {
/* 107 */     this.issueCode = issueCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getIssueCodeExpired() {
/* 114 */     return this.issueCodeExpired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueCodeExpired(Long issueCodeExpired) {
/* 121 */     this.issueCodeExpired = issueCodeExpired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getIssueCodeFailCnt() {
/* 128 */     return this.issueCodeFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueCodeFailCnt(Integer issueCodeFailCnt) {
/* 135 */     this.issueCodeFailCnt = issueCodeFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getAuthFailCnt() {
/* 142 */     return this.authFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthFailCnt(Integer authFailCnt) {
/* 149 */     this.authFailCnt = authFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOtpSn() {
/* 156 */     return this.otpSn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOtpSn(String otpSn) {
/* 163 */     this.otpSn = otpSn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTsLatestIssuance() {
/* 170 */     return this.tsLatestIssuance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsLatestIssuance(Long tsLatestIssuance) {
/* 177 */     this.tsLatestIssuance = tsLatestIssuance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getErrorMessage() {
/* 184 */     return this.errorMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorMessage(String errorMessage) {
/* 191 */     this.errorMessage = errorMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAaids() {
/* 198 */     return this.aaids;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAaids(String[] aaids) {
/* 205 */     this.aaids = aaids;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthRequestContents getAuthReqContents() {
/* 212 */     return this.authReqContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceId() {
/* 221 */     return this.deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/* 228 */     this.deviceId = deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceTypes getDeviceType() {
/* 235 */     return this.deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceType(DeviceTypes deviceType) {
/* 242 */     this.deviceType = deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPkgName() {
/* 249 */     return this.pkgName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPkgName(String pkgName) {
/* 256 */     this.pkgName = pkgName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModel() {
/* 263 */     return this.model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(String model) {
/* 270 */     this.model = model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlias() {
/* 277 */     return this.alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlias(String alias) {
/* 284 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public YNStatus getTranInfoYn() {
/* 291 */     return this.tranInfoYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranInfoYn(YNStatus tranInfoYn) {
/* 298 */     this.tranInfoYn = tranInfoYn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getTranInfoEnc() {
/* 305 */     return this.tranInfoEnc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranInfoEnc(byte[] tranInfoEnc) {
/* 312 */     this.tranInfoEnc = tranInfoEnc;
/*     */   }
/*     */   
/*     */   public Integer getUvTFCnt() {
/* 316 */     return this.uvTFCnt;
/*     */   }
/*     */   
/*     */   public void setUvTFCnt(Integer uvTFCnt) {
/* 320 */     this.uvTFCnt = uvTFCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getExpiredDuration() {
/* 327 */     return this.expiredDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpiredDuration(Integer expiredDuration) {
/* 334 */     this.expiredDuration = expiredDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getQrData() {
/* 341 */     return this.qrData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQrData(String qrData) {
/* 348 */     this.qrData = qrData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthReqContents(AuthRequestContents authReqContents) {
/* 356 */     this.authReqContents = authReqContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTid() {
/* 363 */     return this.tid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTid(String tid) {
/* 370 */     this.tid = tid;
/*     */   }
/*     */   
/*     */   public String getMultiLoginYN() {
/* 374 */     return this.multiLoginYN;
/*     */   }
/*     */   
/*     */   public void setMultiLoginYN(String multiLoginYN) {
/* 378 */     this.multiLoginYN = multiLoginYN;
/*     */   }
/*     */   
/*     */   public String getStatus() {
/* 382 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(String status) {
/* 386 */     this.status = status;
/*     */   }
/*     */   
/*     */   public int getProductTypeCode() {
/* 390 */     return this.productTypeCode;
/*     */   }
/*     */   
/*     */   public void setProductTypeCode(int productTypeCode) {
/* 394 */     this.productTypeCode = productTypeCode;
/*     */   }
/*     */   
/*     */   public String getSessionCode() {
/* 398 */     return this.sessionCode;
/*     */   }
/*     */   
/*     */   public void setSessionCode(String sessionCode) {
/* 402 */     this.sessionCode = sessionCode;
/*     */   }
/*     */   
/*     */   public String getUserName() {
/* 406 */     return this.userName;
/*     */   }
/*     */   
/*     */   public void setUserName(String userName) {
/* 410 */     this.userName = userName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void jsonSerialize(JsonObject jsonObject, Type type, JsonSerializationContext ctx) {
/* 416 */     if (this.returnCode != null) {
/* 417 */       jsonObject.add("returnCode", ctx.serialize(this.returnCode));
/*     */     }
/*     */     
/* 420 */     if (!StringUtils.isEmpty(this.issueCode)) {
/* 421 */       jsonObject.addProperty("issueCode", Base64Utils.encodeUrl(this.issueCode));
/*     */     }
/*     */     
/* 424 */     if (this.issueCodeExpired != null) {
/* 425 */       jsonObject.addProperty("issueCodeExpired", Base64Utils.encodeUrl(Commons.displayDateTime(this.issueCodeExpired.longValue())));
/*     */     }
/*     */     
/* 428 */     if (this.issueCodeFailCnt != null) {
/* 429 */       jsonObject.addProperty("issueCodeFailCnt", this.issueCodeFailCnt);
/*     */     }
/*     */     
/* 432 */     if (this.authFailCnt != null) {
/* 433 */       jsonObject.addProperty("authFailCnt", this.authFailCnt);
/*     */     }
/*     */     
/* 436 */     if (!StringUtils.isEmpty(this.otpSn)) {
/* 437 */       jsonObject.addProperty("otpSn", Base64Utils.encodeUrl(this.otpSn));
/*     */     }
/*     */     
/* 440 */     if (this.tsLatestIssuance != null) {
/* 441 */       jsonObject.addProperty("tsLatestIssuance", Base64Utils.encodeUrl(Commons.displayDateTime(this.tsLatestIssuance.longValue())));
/*     */     }
/*     */     
/* 444 */     if (!StringUtils.isEmpty(this.errorMessage)) {
/* 445 */       jsonObject.addProperty("errorMessage", Base64Utils.encodeUrl(this.errorMessage));
/*     */     }
/*     */     
/* 448 */     if (this.aaids != null) {
/* 449 */       JsonArray arr = new JsonArray();
/* 450 */       for (String string : this.aaids) {
/* 451 */         arr.add(string);
/*     */       }
/* 453 */       jsonObject.add("aaid", (JsonElement)arr);
/*     */     } 
/*     */ 
/*     */     
/* 457 */     if (this.authReqContents != null) {
/* 458 */       jsonObject.add("authReqContents", ctx.serialize(this.authReqContents));
/*     */     }
/*     */     
/* 461 */     if (!StringUtils.isEmpty(this.qrData)) {
/* 462 */       jsonObject.addProperty("qrData", this.qrData);
/*     */     }
/*     */     
/* 465 */     if (!StringUtils.isEmpty(this.tid)) {
/* 466 */       jsonObject.addProperty("tid", this.tid);
/*     */     }
/*     */     
/* 469 */     if (!StringUtils.isEmpty(this.sessionCode)) {
/* 470 */       jsonObject.addProperty("sessionCode", Base64Utils.encodeUrl(this.sessionCode));
/*     */     }
/*     */     
/* 473 */     if (!StringUtils.isEmpty(this.multiLoginYN)) {
/* 474 */       jsonObject.addProperty("multiLoginYN", Base64Utils.encodeUrl(this.multiLoginYN));
/*     */     }
/*     */     
/* 477 */     if (this.productTypeCode != 0) {
/* 478 */       jsonObject.addProperty("productType", Integer.valueOf(this.productTypeCode));
/*     */     }
/*     */     
/* 481 */     if (!StringUtils.isEmpty(this.userName)) {
/* 482 */       jsonObject.addProperty("userName", Base64Utils.encodeUrl(this.userName));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonDeserialization(JsonObject json, JsonDeserializationContext ctx) {
/* 488 */     if (json.has("authReqContents")) {
/* 489 */       ReturnCodes returnCode = (ReturnCodes)ctx.deserialize(json.get("returnCode"), ReturnCodes.class);
/* 490 */       setReturnCode(returnCode);
/*     */     } 
/*     */     
/* 493 */     setIssueCode(decodeString("issueCode", json));
/*     */     
/* 495 */     String value = decodeString("issueCodeExpired", json);
/* 496 */     if (StringUtils.isEmpty(value)) {
/* 497 */       setIssueCodeExpired(Long.valueOf(Commons.displayDateTime(value)));
/*     */     }
/*     */     
/* 500 */     if (json.has("issueCodeFailCnt")) {
/* 501 */       setIssueCodeFailCnt(Integer.valueOf(json.get("issueCodeFailCnt").getAsInt()));
/*     */     }
/*     */     
/* 504 */     if (json.has("authFailCnt")) {
/* 505 */       setAuthFailCnt(Integer.valueOf(json.get("authFailCnt").getAsInt()));
/*     */     }
/*     */     
/* 508 */     setOtpSn(decodeString("otpSn", json));
/*     */     
/* 510 */     value = decodeString("tsLatestIssuance", json);
/* 511 */     if (StringUtils.isEmpty(value)) {
/* 512 */       setTsLatestIssuance(Long.valueOf(Commons.displayDateTime(value)));
/*     */     }
/*     */     
/* 515 */     setErrorMessage(decodeString("errorMessage", json));
/*     */     
/* 517 */     if (json.has("multiLoginYN")) {
/* 518 */       setMultiLoginYN(decodeString("multiLoginYN", json));
/*     */     }
/*     */     
/* 521 */     if (json.has("productType"))
/*     */     {
/* 523 */       setProductTypeCode(json.get("productType").getAsInt());
/*     */     }
/*     */     
/* 526 */     if (json.has("sessionCode"))
/*     */     {
/* 528 */       setSessionCode(decodeString("sessionCode", json));
/*     */     }
/*     */ 
/*     */     
/* 532 */     if (json.has("authReqContents")) {
/* 533 */       AuthRequestContents contents = (AuthRequestContents)ctx.deserialize(json.get("authReqContents"), AuthRequestContents.class);
/* 534 */       setAuthReqContents(contents);
/*     */     } 
/*     */     
/* 537 */     if (json.has("userName"))
/*     */     {
/* 539 */       setUserName(decodeString("userName", json));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private String decodeString(String key, JsonObject json) {
/* 545 */     if (json.has(key)) {
/* 546 */       String value = json.get(key).getAsString();
/* 547 */       return Base64Utils.decode(value);
/*     */     } 
/*     */     
/* 550 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 558 */     StringBuilder builder = new StringBuilder();
/* 559 */     builder.append("AuthenticationResponseLocator [returnCode=");
/*     */     
/* 561 */     if (this.returnCode != null) {
/* 562 */       builder.append(this.returnCode.getCode());
/*     */     }
/*     */     
/* 565 */     builder.append("(").append(this.returnCode).append(")");
/*     */     
/* 567 */     if (!StringUtils.isEmpty(this.issueCode)) {
/* 568 */       builder.append(", issueCode=");
/* 569 */       builder.append(this.issueCode);
/*     */     } 
/*     */     
/* 572 */     if (this.issueCodeExpired != null) {
/* 573 */       builder.append(", issueCodeExpired=");
/* 574 */       builder.append(this.issueCodeExpired);
/*     */     } 
/*     */     
/* 577 */     if (this.issueCodeFailCnt != null) {
/* 578 */       builder.append(", issueCodeFailCnt=");
/* 579 */       builder.append(this.issueCodeFailCnt);
/*     */     } 
/*     */     
/* 582 */     if (this.authFailCnt != null) {
/* 583 */       builder.append(", authFailCnt=");
/* 584 */       builder.append(this.authFailCnt);
/*     */     } 
/*     */     
/* 587 */     if (!StringUtils.isEmpty(this.otpSn)) {
/* 588 */       builder.append(", otpSn=");
/* 589 */       builder.append(this.otpSn);
/*     */     } 
/*     */     
/* 592 */     if (this.tsLatestIssuance != null) {
/* 593 */       builder.append(", tsLatestIssuance=");
/* 594 */       builder.append(this.tsLatestIssuance);
/*     */     } 
/*     */     
/* 597 */     if (!StringUtils.isEmpty(this.errorMessage)) {
/* 598 */       builder.append(", errorMessage=");
/* 599 */       builder.append(this.errorMessage);
/*     */     } 
/*     */     
/* 602 */     if (this.aaids != null) {
/* 603 */       builder.append(", aaids=");
/* 604 */       builder.append(Arrays.toString((Object[])this.aaids));
/*     */     } 
/*     */     
/* 607 */     if (this.authReqContents != null) {
/* 608 */       builder.append(", authReqContents=");
/* 609 */       builder.append(this.authReqContents);
/*     */     } 
/*     */     
/* 612 */     if (!StringUtils.isEmpty(this.deviceId)) {
/* 613 */       builder.append(", deviceId=");
/* 614 */       builder.append(this.deviceId);
/*     */     } 
/*     */     
/* 617 */     if (this.deviceType != null) {
/* 618 */       builder.append(", deviceType=");
/* 619 */       builder.append(this.deviceType);
/*     */     } 
/*     */     
/* 622 */     if (!StringUtils.isEmpty(this.pkgName)) {
/* 623 */       builder.append(", pkgName=");
/* 624 */       builder.append(this.pkgName);
/*     */     } 
/*     */     
/* 627 */     if (!StringUtils.isEmpty(this.model)) {
/* 628 */       builder.append(", model=");
/* 629 */       builder.append(this.model);
/*     */     } 
/*     */     
/* 632 */     if (!StringUtils.isEmpty(this.alias)) {
/* 633 */       builder.append(", alias=");
/* 634 */       builder.append(this.alias);
/*     */     } 
/*     */     
/* 637 */     if (this.tranInfoYn != null) {
/* 638 */       builder.append(", tranInfoYn=");
/* 639 */       builder.append(this.tranInfoYn.getCode());
/*     */     } 
/*     */     
/* 642 */     if (this.tranInfoEnc != null) {
/* 643 */       builder.append(", tranInfoEnc=");
/* 644 */       builder.append(HexUtils.toHexString(this.tranInfoEnc));
/*     */     } 
/*     */     
/* 647 */     if (this.uvTFCnt != null) {
/* 648 */       builder.append(", uvTFCnt=");
/* 649 */       builder.append(String.valueOf(this.uvTFCnt));
/*     */     } 
/*     */     
/* 652 */     if (!StringUtils.isEmpty(this.qrData)) {
/* 653 */       builder.append(", qrData=");
/* 654 */       builder.append(this.qrData);
/*     */     } 
/*     */     
/* 657 */     if (!StringUtils.isEmpty(this.sessionCode)) {
/* 658 */       builder.append(", sessionCode=");
/* 659 */       builder.append(this.sessionCode);
/*     */     } 
/*     */     
/* 662 */     if (this.productTypeCode != 0) {
/* 663 */       builder.append(", productType=");
/* 664 */       builder.append(ProductType.getProductType(this.productTypeCode).getMessageKey());
/*     */     } 
/*     */     
/* 667 */     if (!StringUtils.isEmpty(this.multiLoginYN)) {
/* 668 */       builder.append(", multiLoginYN=");
/* 669 */       builder.append(this.multiLoginYN);
/*     */     } 
/* 671 */     builder.append("]");
/* 672 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\bean\AuthenticationResponseLocator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */