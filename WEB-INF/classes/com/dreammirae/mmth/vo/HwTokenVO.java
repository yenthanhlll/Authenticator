/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.misc.Base64Utils;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
/*     */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ public class HwTokenVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*  18 */   private int id = -1;
/*     */   
/*     */   private String type;
/*     */   
/*     */   private int userId;
/*     */   
/*     */   private String username;
/*     */   
/*     */   private String comment;
/*     */   
/*     */   private String branchId;
/*     */   
/*     */   private String tokenId;
/*     */   
/*     */   private String tokenKey;
/*     */   
/*     */   private TokenStatus status;
/*     */   
/*     */   private int unlockCnt;
/*     */   
/*     */   private YNStatus lost;
/*     */   
/*     */   private int pinFailCnt;
/*     */   
/*     */   private int biotpFailCnt;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsReg;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsOccupied;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsSuspend;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsDiscard;
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private long tsLost;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   
/*     */   public HwTokenVO() {}
/*     */ 
/*     */   
/*     */   public int getUserId() {
/*  66 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/*  70 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   public HwTokenVO(String username) {
/*  74 */     this.username = username;
/*     */   }
/*     */   
/*     */   public HwTokenVO(int userId) {
/*  78 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenStatus getStatus() {
/*  85 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(TokenStatus status) {
/*  93 */     this.status = status;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  97 */     return this.id;
/*     */   }
/*     */   public void setId(int id) {
/* 100 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 104 */     return this.type;
/*     */   }
/*     */   public void setType(String type) {
/* 107 */     this.type = type;
/*     */   }
/*     */   
/*     */   public String getUsername() {
/* 111 */     return this.username;
/*     */   }
/*     */   public void setUsername(String username) {
/* 114 */     this.username = username;
/*     */   }
/*     */   public String getComment() {
/* 117 */     return this.comment;
/*     */   }
/*     */   public void setComment(String comment) {
/* 120 */     this.comment = comment;
/* 121 */     if (!StringUtils.isBlank(comment))
/*     */     {
/* 123 */       this.comment = Base64Utils.decode(comment); } 
/*     */   }
/*     */   
/*     */   public String getBranchId() {
/* 127 */     return this.branchId;
/*     */   }
/*     */   public void setBranchId(String branchId) {
/* 130 */     this.branchId = branchId;
/*     */   }
/*     */   public String getTokenId() {
/* 133 */     return this.tokenId;
/*     */   }
/*     */   public void setTokenId(String tokenId) {
/* 136 */     this.tokenId = tokenId;
/*     */   }
/*     */   
/*     */   public String getTokenKey() {
/* 140 */     return this.tokenKey;
/*     */   }
/*     */   public void setTokenKey(String tokenKey) {
/* 143 */     this.tokenKey = tokenKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 150 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 158 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsOccupied() {
/* 165 */     return this.tsOccupied;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsOccupied(long tsOccupied) {
/* 173 */     this.tsOccupied = tsOccupied;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsSuspend() {
/* 180 */     return this.tsSuspend;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsSuspend(long tsSuspend) {
/* 188 */     this.tsSuspend = tsSuspend;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsDiscard() {
/* 195 */     return this.tsDiscard;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsDiscard(long tsDiscard) {
/* 203 */     this.tsDiscard = tsDiscard;
/*     */   }
/*     */   
/*     */   public int getUnlockCnt() {
/* 207 */     return this.unlockCnt;
/*     */   }
/*     */   public void setUnlockCnt(int unlockCnt) {
/* 210 */     this.unlockCnt = unlockCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public YNStatus getLost() {
/* 216 */     return this.lost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLost(YNStatus lost) {
/* 223 */     this.lost = lost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsLost() {
/* 230 */     return this.tsLost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsLost(long tsLost) {
/* 237 */     this.tsLost = tsLost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public TokenStatus getStatusDesc() {
/* 245 */     return this.status;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public YNStatus getLostDesc() {
/* 250 */     return this.lost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPinFailCnt() {
/* 258 */     return this.pinFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPinFailCnt(int pinFailCnt) {
/* 265 */     this.pinFailCnt = pinFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBiotpFailCnt() {
/* 272 */     return this.biotpFailCnt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiotpFailCnt(int biotpFailCnt) {
/* 279 */     this.biotpFailCnt = biotpFailCnt;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.vo.HwTokenVO createTokenVO(String tokenId, String tokenKey, String type) {
/* 284 */     com.dreammirae.mmth.vo.HwTokenVO vo = new com.dreammirae.mmth.vo.HwTokenVO();
/*     */ 
/*     */     
/* 287 */     vo.setTokenId(tokenId);
/* 288 */     vo.setTokenKey(tokenKey);
/* 289 */     vo.setType(type);
/* 290 */     vo.setStatus(TokenStatus.AVAILABLE);
/* 291 */     vo.setLost(YNStatus.N);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(RestResponse resp) {}
/*     */ 
/*     */   
/*     */   public String toString() {
/* 309 */     return "HwTokenVO [id=" + this.id + ", type=" + this.type + ", username=" + this.username + ", tokenId=" + this.tokenId + ", tokenKey=" + this.tokenKey + ", status=" + this.status + ", unlockCnt=" + this.unlockCnt + ", lost=" + this.lost + ", tsReg=" + this.tsReg + ", tsOccupied=" + this.tsOccupied + ", tsSuspend=" + this.tsSuspend + ", tsDiscard=" + this.tsDiscard + ", tsLost=" + this.tsLost + "]";
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\HwTokenVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */