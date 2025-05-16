/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.TokenVO;
/*     */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*     */ import com.dreammirae.mmth.vo.types.YNStatus;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TokenRegistrationVO
/*     */   implements Serializable
/*     */ {
/*     */   private String tokenId;
/*     */   private String tokenData;
/*     */   private TokenStatus tokenStatus;
/*     */   private AuthMethodTypes authType;
/*     */   private String username;
/*     */   private YNStatus lost;
/*     */   private long tsTokenOccupied;
/*     */   private long tsTokenDiscard;
/*     */   private long tsLost;
/*  76 */   private int deviceAgentId = -1;
/*     */ 
/*     */   
/*     */   private long tsReg;
/*     */ 
/*     */   
/*     */   private byte[] otpPublicKey;
/*     */   
/*     */   private byte[] rndSeedKey;
/*     */   
/*     */   private transient byte[] rndChallenge;
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static final int version = 1;
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/*  94 */     return this.tokenId;
/*     */   }
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
/*     */   public void setTokenData(String tokenData) {
/* 115 */     this.tokenData = tokenData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthType() {
/* 121 */     return this.authType;
/*     */   }
/*     */   
/*     */   public void setAuthType(AuthMethodTypes authType) {
/* 125 */     this.authType = authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenStatus getTokenStatus() {
/* 132 */     return this.tokenStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenStatus(TokenStatus tokenStatus) {
/* 139 */     this.tokenStatus = tokenStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/* 146 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/* 153 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsTokenOccupied() {
/* 160 */     return this.tsTokenOccupied;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsTokenOccupied(long tsTokenOccupied) {
/* 167 */     this.tsTokenOccupied = tsTokenOccupied;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsTokenDiscard() {
/* 174 */     return this.tsTokenDiscard;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsTokenDiscard(long tsTokenDiscard) {
/* 181 */     this.tsTokenDiscard = tsTokenDiscard;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeviceAgentId() {
/* 188 */     return this.deviceAgentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceAgentId(int deviceAgentId) {
/* 195 */     this.deviceAgentId = deviceAgentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 202 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 209 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getOtpPublicKey() {
/* 216 */     return this.otpPublicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOtpPublicKey(byte[] otpPublicKey) {
/* 223 */     this.otpPublicKey = otpPublicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getRndSeedKey() {
/* 230 */     return this.rndSeedKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRndSeedKey(byte[] rndSeedKey) {
/* 237 */     this.rndSeedKey = rndSeedKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getRndChallenge() {
/* 244 */     return this.rndChallenge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRndChallenge(byte[] rndChallenge) {
/* 251 */     this.rndChallenge = rndChallenge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public YNStatus getLost() {
/* 260 */     return this.lost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLost(YNStatus lost) {
/* 267 */     this.lost = lost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsLost() {
/* 274 */     return this.tsLost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsLost(long tsLost) {
/* 281 */     this.tsLost = tsLost;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.TokenRegistrationVO createTokenRegistration(DeviceAppAgentVO deviceAppAgent, TokenVO token, byte[] otpPubKey, byte[] rndSeedKey, byte[] rndChallenge) {
/* 285 */     com.dreammirae.mmth.vo.TokenRegistrationVO vo = new com.dreammirae.mmth.vo.TokenRegistrationVO();
/*     */     
/* 287 */     vo.setTokenId(token.getTokenId());
/* 288 */     vo.setTokenData(token.getTokenData());
/* 289 */     vo.setTokenStatus(token.getStatus());
/* 290 */     vo.setAuthType(token.getAuthType());
/* 291 */     vo.setUsername(token.getUsername());
/* 292 */     vo.setLost(token.getLost());
/* 293 */     vo.setDeviceAgentId(deviceAppAgent.getId());
/* 294 */     vo.setTsReg(System.currentTimeMillis());
/* 295 */     vo.setTsTokenOccupied(token.getTsOccupied());
/* 296 */     vo.setTsLost(token.getTsLost());
/* 297 */     vo.setOtpPublicKey(otpPubKey);
/* 298 */     vo.setRndSeedKey(rndSeedKey);
/* 299 */     vo.setRndChallenge(rndChallenge);
/*     */     
/* 301 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 312 */     out.writeInt(1);
/* 313 */     SerializationUtils.writeSafeObject(out, this.otpPublicKey);
/* 314 */     SerializationUtils.writeSafeObject(out, this.rndSeedKey);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 318 */     int ver = in.readInt();
/* 319 */     if (ver == 1) {
/* 320 */       this.otpPublicKey = (byte[])SerializationUtils.readSafeObject(in);
/* 321 */       this.rndSeedKey = (byte[])SerializationUtils.readSafeObject(in);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 327 */     StringBuilder builder = new StringBuilder();
/* 328 */     builder.append("TokenRegistrationVO [tokenId=").append(this.tokenId).append(", tokenData=").append(this.tokenData)
/* 329 */       .append(", tokenStatus=").append(this.tokenStatus).append(", authType=").append(this.authType)
/* 330 */       .append(", username=").append(this.username).append(", lost=").append(this.lost).append(", tsTokenOccupied=")
/* 331 */       .append(this.tsTokenOccupied).append(", tsTokenDiscard=").append(this.tsTokenDiscard).append(", tsLost=")
/* 332 */       .append(this.tsLost).append(", deviceAgentId=").append(this.deviceAgentId).append(", tsReg=").append(this.tsReg)
/* 333 */       .append(", otpPublicKey=").append(HexUtils.toHexString(this.otpPublicKey)).append(", rndSeedKey=")
/* 334 */       .append(HexUtils.toHexString(this.rndSeedKey)).append("]");
/* 335 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\TokenRegistrationVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */