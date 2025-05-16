/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
/*     */ import com.fasterxml.jackson.annotation.JsonIgnore;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TokenVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*     */   private String tokenId;
/*     */   @JsonIgnore
/*     */   private String tokenData;
/*     */   private TokenStatus status;
/*     */   private AuthMethodTypes authType;
/*     */   private String username;
/*     */   private YNStatus lost;
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsReg;
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsOccupied;
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsDiscard;
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsLost;
/*     */   private int pinFailCnt;
/*     */   private int biotpFailCnt;
/*     */   private int authFailCnt;
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public String getTokenId() {
/*  93 */     return this.tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenId(String tokenId) {
/* 101 */     this.tokenId = tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTokenData() {
/* 108 */     return this.tokenData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenData(String tokenData) {
/* 116 */     this.tokenData = tokenData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenStatus getStatus() {
/* 123 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(TokenStatus status) {
/* 131 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthType() {
/* 138 */     return this.authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthType(AuthMethodTypes authType) {
/* 146 */     this.authType = authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/* 153 */     return this.username;
/*     */   }
/*     */ 
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
/*     */   public long getTsReg() {
/* 168 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 176 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsOccupied() {
/* 183 */     return this.tsOccupied;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsOccupied(long tsOccupied) {
/* 191 */     this.tsOccupied = tsOccupied;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsDiscard() {
/* 198 */     return this.tsDiscard;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsDiscard(long tsDiscard) {
/* 206 */     this.tsDiscard = tsDiscard;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public YNStatus getLost() {
/* 214 */     return this.lost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLost(YNStatus lost) {
/* 221 */     this.lost = lost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsLost() {
/* 228 */     return this.tsLost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsLost(long tsLost) {
/* 235 */     this.tsLost = tsLost;
/*     */   }
/*     */   
/*     */   public int getPinFailCnt() {
/* 239 */     return this.pinFailCnt;
/*     */   }
/*     */   
/*     */   public void setPinFailCnt(int pinFailCnt) {
/* 243 */     this.pinFailCnt = pinFailCnt;
/*     */   }
/*     */   
/*     */   public int getBiotpFailCnt() {
/* 247 */     return this.biotpFailCnt;
/*     */   }
/*     */   
/*     */   public void setBiotpFailCnt(int biotpFailCnt) {
/* 251 */     this.biotpFailCnt = biotpFailCnt;
/*     */   }
/*     */   
/*     */   public int getAuthFailCnt() {
/* 255 */     return this.authFailCnt;
/*     */   }
/*     */   
/*     */   public void setAuthFailCnt(int authFailCnt) {
/* 259 */     this.authFailCnt = authFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public TokenStatus getStatusDesc() {
/* 268 */     return this.status;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AuthMethodTypes getAuthTypeDesc() {
/* 273 */     return this.authType;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public YNStatus getLostDesc() {
/* 278 */     return this.lost;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.vo.TokenVO createTokenVO(String tokenId, String tokenData) {
/* 284 */     com.dreammirae.mmth.vo.TokenVO vo = new com.dreammirae.mmth.vo.TokenVO();
/*     */     
/* 286 */     vo.setTokenId(tokenId);
/* 287 */     vo.setTokenData(tokenData);
/* 288 */     vo.setStatus(TokenStatus.AVAILABLE);
/*     */     
/* 290 */     return vo;
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
/* 305 */     StringBuilder builder = new StringBuilder();
/* 306 */     builder.append("TokenVO [tokenId=").append(this.tokenId).append(", tokenData=").append(this.tokenData).append(", status=").append(this.status).append(", authType=").append(this.authType)
/* 307 */       .append(", username=").append(this.username).append(", tsReg=").append(this.tsReg).append(", tsOccupied=").append(this.tsOccupied).append(", tsDiscard=").append(this.tsDiscard)
/* 308 */       .append("]");
/* 309 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\TokenVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */