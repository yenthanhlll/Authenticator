/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.ProductType;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import com.dreammirae.mmth.vo.types.IssuanceTypes;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
/*     */ 
/*     */ public class IssuanceHistoryVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*  58 */   private int id = -1;
/*     */   private AuthMethodTypes authType;
/*     */   private String username;
/*     */   private String deviceId;
/*     */   private DeviceTypes deviceType;
/*     */   private ProductType productType;
/*     */   private String pkgUnique;
/*     */   private IssuanceTypes issueType;
/*  66 */   private String aaid = "-";
/*  67 */   private String tokenId = "-";
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsIssue;
/*     */   
/*     */   private String issueMonth;
/*     */   
/*     */   private String issueDate;
/*     */   
/*     */   private String issueTime;
/*     */   
/*     */   private String deviceModel;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public int getId() {
/*  83 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  91 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthType() {
/*  98 */     return this.authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthType(AuthMethodTypes authType) {
/* 106 */     this.authType = authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/* 113 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/* 121 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDeviceId() {
/* 128 */     return this.deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/* 136 */     this.deviceId = deviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceTypes getDeviceType() {
/* 143 */     return this.deviceType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceType(DeviceTypes deviceType) {
/* 151 */     this.deviceType = deviceType;
/*     */   }
/*     */   
/*     */   public ProductType getProductType() {
/* 155 */     return this.productType;
/*     */   }
/*     */   
/*     */   public void setProductType(ProductType productType) {
/* 159 */     this.productType = productType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPkgUnique() {
/* 166 */     return this.pkgUnique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPkgUnique(String pkgUnique) {
/* 174 */     this.pkgUnique = pkgUnique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuanceTypes getIssueType() {
/* 181 */     return this.issueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueType(IssuanceTypes issueType) {
/* 189 */     this.issueType = issueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAaid() {
/* 196 */     return this.aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAaid(String aaid) {
/* 204 */     this.aaid = aaid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 211 */     return this.tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenId(String tokenId) {
/* 219 */     this.tokenId = tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsIssue() {
/* 226 */     return this.tsIssue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsIssue(long tsIssue) {
/* 234 */     this.tsIssue = tsIssue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssueMonth() {
/* 241 */     return this.issueMonth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueMonth(String issueMonth) {
/* 249 */     this.issueMonth = issueMonth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssueDate() {
/* 256 */     return this.issueDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueDate(String issueDate) {
/* 264 */     this.issueDate = issueDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIssueTime() {
/* 271 */     return this.issueTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIssueTime(String issueTime) {
/* 279 */     this.issueTime = issueTime;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public IssuanceTypes getIssueTypeDesc() {
/* 284 */     return this.issueType;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AuthMethodTypes getAuthTypeDesc() {
/* 289 */     return this.authType;
/*     */   }
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AgentOsTypes getOsTypeDesc() {
/* 295 */     if (this.deviceType == null) {
/* 296 */       return null;
/*     */     }
/* 298 */     return this.deviceType.getOsType();
/*     */   }
/*     */   
/*     */   public void setDeviceModel(String deviceModel) {
/* 302 */     this.deviceModel = deviceModel;
/*     */   }
/*     */   
/*     */   public String getDeviceModel() {
/* 306 */     return this.deviceModel;
/*     */   }
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
/*     */   public String toString() {
/* 321 */     StringBuilder builder = new StringBuilder();
/* 322 */     builder.append("IssuanceHistroryVO [id=").append(this.id).append(", authType=").append(this.authType).append(", username=").append(this.username).append(", deviceId=").append(this.deviceId)
/* 323 */       .append(", deviceType=").append(this.deviceType).append(", pkgUnique=").append(this.pkgUnique).append(", issueType=").append(this.issueType).append(", aaid=").append(this.aaid).append(", tokenId=")
/* 324 */       .append(this.tokenId).append(", tsIssue=").append(this.tsIssue).append(", issueMonth=").append(this.issueMonth).append(", issueDate=").append(this.issueDate).append(", issueTime=").append(this.issueTime)
/* 325 */       .append("]");
/* 326 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\IssuanceHistoryVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */