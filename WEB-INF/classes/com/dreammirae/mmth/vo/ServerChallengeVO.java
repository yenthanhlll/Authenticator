/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.fido.Operation;
/*     */ import com.dreammirae.mmth.fido.handler.bean.IServerDataLocator;
/*     */ import com.dreammirae.mmth.fido.transport.context.RpContext;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.io.HexUtils;
/*     */ import com.dreammirae.mmth.util.notary.SHA;
/*     */ import com.dreammirae.mmth.vo.types.ChallengeStatus;
/*     */ import com.dreammirae.mmth.vo.types.ChallengeTypes;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerChallengeVO
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  20 */   private long id = -1L;
/*     */   private String username;
/*     */   private String challenge;
/*     */   private ChallengeTypes challengeType;
/*     */   private ChallengeStatus status;
/*     */   private long tsLifeTime;
/*     */   private String transactionId;
/*     */   private String tranContent;
/*  28 */   private int deviceAppId = -1;
/*     */ 
/*     */   
/*     */   private long tsDone;
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/*  36 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(long id) {
/*  44 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/*  51 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(String username) {
/*  59 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getChallenge() {
/*  66 */     return this.challenge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChallenge(String challenge) {
/*  74 */     this.challenge = challenge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChallengeTypes getChallengeType() {
/*  81 */     return this.challengeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChallengeType(ChallengeTypes challengeType) {
/*  89 */     this.challengeType = challengeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChallengeStatus getStatus() {
/*  96 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(ChallengeStatus status) {
/* 104 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsLifeTime() {
/* 111 */     return this.tsLifeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsLifeTime(long tsLifeTime) {
/* 119 */     this.tsLifeTime = tsLifeTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTransactionId() {
/* 126 */     return this.transactionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionId(String transactionId) {
/* 134 */     this.transactionId = transactionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranContent() {
/* 141 */     return this.tranContent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTranContent(String tranContent) {
/* 149 */     this.tranContent = tranContent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeviceAppId() {
/* 156 */     return this.deviceAppId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceAppId(int deviceAppId) {
/* 164 */     this.deviceAppId = deviceAppId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsDone() {
/* 171 */     return this.tsDone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsDone(long tsDone) {
/* 178 */     this.tsDone = tsDone;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.vo.ServerChallengeVO createNewInstance(IServerDataLocator loc, RpContext context) {
/* 183 */     com.dreammirae.mmth.vo.ServerChallengeVO vo = new com.dreammirae.mmth.vo.ServerChallengeVO();
/*     */     
/* 185 */     vo.setUsername(loc.getUsername());
/* 186 */     vo.setChallenge(loc.getChallenge());
/*     */ 
/*     */ 
/*     */     
/* 190 */     String tranContents = (String)context.get("tranInfo", String.class);
/*     */     
/* 192 */     if (Operation.Reg.equals(loc.getOp())) {
/* 193 */       vo.setChallengeType(ChallengeTypes.REG);
/*     */     }
/* 195 */     else if (loc.isTransaction()) {
/* 196 */       vo.setChallengeType(ChallengeTypes.AUTH_WITH_TRANSACTION);
/* 197 */     } else if (context.containsKey("tranInfo") && !StringUtils.isEmpty(tranContents = tranContents.trim())) {
/* 198 */       vo.setChallengeType(ChallengeTypes.AUTH_WITH_TRAN_INFO);
/*     */     } else {
/* 200 */       vo.setChallengeType(ChallengeTypes.AUTH);
/*     */     } 
/*     */ 
/*     */     
/* 204 */     if (StringUtils.isEmpty(tranContents)) {
/* 205 */       tranContents = loc.getServerData();
/*     */     }
/*     */     
/* 208 */     vo.setTsLifeTime(loc.getLifeTimeTs());
/* 209 */     vo.setStatus(ChallengeStatus.FIDO_REQ);
/*     */ 
/*     */     
/* 212 */     String tid = (String)context.get("tid", String.class);
/*     */     
/* 214 */     if (StringUtils.isEmpty(tid)) {
/* 215 */       vo.setTransactionId(SysEnvCommon.generateTID());
/*     */     } else {
/* 217 */       vo.setTransactionId(tid);
/*     */     } 
/*     */     
/* 220 */     if (context.containsKey("externalRequestExpired")) {
/* 221 */       vo.setTsLifeTime(((Long)context.get("externalRequestExpired", Long.class)).longValue());
/*     */     }
/*     */     
/* 224 */     vo.setTranContent(HexUtils.toHexString(SHA.sha256Raw(tranContents)));
/*     */     
/* 226 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 236 */     StringBuilder builder = new StringBuilder();
/* 237 */     builder.append("ServerChallengeVO [id=").append(this.id).append(", username=").append(this.username)
/* 238 */       .append(", challenge=").append(this.challenge).append(", challengeType=").append(this.challengeType)
/* 239 */       .append(", status=").append(this.status).append(", tsLifeTime=").append(this.tsLifeTime)
/* 240 */       .append(", transactionId=").append(this.transactionId).append(", tranContent=").append(this.tranContent)
/* 241 */       .append(", deviceAppId=").append(this.deviceAppId).append("]");
/* 242 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\ServerChallengeVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */