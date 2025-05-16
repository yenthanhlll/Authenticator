/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.bean.AuthRequestContents;
/*     */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FcmPushContents
/*     */ {
/*  89 */   private long id = -1L;
/*     */   
/*     */   private int userId;
/*     */   
/*     */   private String fcmTokenId;
/*     */   
/*     */   private String fcmAuthorizationKey;
/*     */   
/*     */   private String sendBody;
/*     */   private String errMsg;
/*     */   private long tsReg;
/*     */   private long tsSend;
/*     */   private SendStatus status;
/*     */   
/*     */   public long getId() {
/* 104 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(long id) {
/* 108 */     this.id = id;
/*     */   }
/*     */   
/*     */   public int getUserId() {
/* 112 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(int userId) {
/* 116 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   public String getFcmTokenId() {
/* 120 */     return this.fcmTokenId;
/*     */   }
/*     */   
/*     */   public void setFcmTokenId(String fcmTokenId) {
/* 124 */     this.fcmTokenId = fcmTokenId;
/*     */   }
/*     */   
/*     */   public String getFcmAuthorizationKey() {
/* 128 */     return this.fcmAuthorizationKey;
/*     */   }
/*     */   
/*     */   public void setFcmAuthorizationKey(String fcmAuthorizationKey) {
/* 132 */     this.fcmAuthorizationKey = fcmAuthorizationKey;
/*     */   }
/*     */   
/*     */   public String getSendBody() {
/* 136 */     return this.sendBody;
/*     */   }
/*     */   
/*     */   public void setSendBody(String sendBody) {
/* 140 */     this.sendBody = sendBody;
/*     */   }
/*     */   
/*     */   public String getErrMsg() {
/* 144 */     return this.errMsg;
/*     */   }
/*     */   
/*     */   public void setErrMsg(String errMsg) {
/* 148 */     this.errMsg = errMsg;
/*     */   }
/*     */   
/*     */   public long getTsReg() {
/* 152 */     return this.tsReg;
/*     */   }
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 156 */     this.tsReg = tsReg;
/*     */   }
/*     */   
/*     */   public long getTsSend() {
/* 160 */     return this.tsSend;
/*     */   }
/*     */   
/*     */   public void setTsSend(long tsSend) {
/* 164 */     this.tsSend = tsSend;
/*     */   }
/*     */   
/*     */   public SendStatus getStatus() {
/* 168 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(SendStatus status) {
/* 172 */     this.status = status;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.vo.bean.FcmPushContents createEnterprisePushManagerVO(UserVO user, DeviceAppAgentVO deviceAppAgent, AppAgentVO appAgent, ExternalServiceItemVO item, PushTypes type) {
/* 177 */     AuthRequestContents contents = item.getRequestContents();
/*     */     
/* 179 */     StringBuilder sb = new StringBuilder(511);
/* 180 */     sb.append("{");
/* 181 */     sb.append("\"to\" : \"").append(deviceAppAgent.getRegistrationId()).append("\", ");
/*     */     
/* 183 */     if (AgentOsTypes.IOS.equals(appAgent.getOsType())) {
/* 184 */       sb.append("\"notification\" : {");
/*     */       
/* 186 */       if (!StringUtils.isEmpty(item.getTitle())) {
/* 187 */         sb.append("\"title\" : \"").append(item.getTitle()).append("\", ");
/*     */       }
/*     */       
/* 190 */       if (!StringUtils.isEmpty(item.getMsg())) {
/* 191 */         sb.append("\"body\" : \"").append(item.getMsg()).append("\", ");
/*     */       }
/*     */       
/* 194 */       sb.append("\"userId\" : \"").append(contents.getUserId()).append("\", ");
/* 195 */       sb.append("\"customerCode\" : \"").append(contents.getCustomerCode()).append("\"");
/* 196 */       sb.append("}");
/*     */     } else {
/* 198 */       sb.append("\"data\" : {");
/*     */       
/* 200 */       if (!StringUtils.isEmpty(item.getTitle())) {
/* 201 */         sb.append("\"title\" : \"").append(item.getTitle()).append("\", ");
/*     */       }
/*     */       
/* 204 */       if (!StringUtils.isEmpty(item.getMsg())) {
/* 205 */         sb.append("\"message\" : \"").append(item.getMsg()).append("\", ");
/*     */       }
/*     */       
/* 208 */       sb.append("\"userId\" : \"").append(contents.getUserId()).append("\", ");
/* 209 */       sb.append("\"customerCode\" : \"").append(contents.getCustomerCode()).append("\"");
/* 210 */       sb.append("}");
/*     */     } 
/*     */     
/* 213 */     sb.append("}");
/*     */ 
/*     */     
/* 216 */     com.dreammirae.mmth.vo.bean.FcmPushContents vo = new com.dreammirae.mmth.vo.bean.FcmPushContents();
/* 217 */     vo.setUserId(user.getId());
/* 218 */     vo.setSendBody(sb.toString());
/* 219 */     vo.setFcmTokenId(deviceAppAgent.getRegistrationId());
/* 220 */     vo.setFcmAuthorizationKey(appAgent.getFcmAuthenticationKey());
/* 221 */     vo.setStatus(SendStatus.WAIT);
/* 222 */     sb.setLength(0);
/* 223 */     return vo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 228 */     StringBuilder builder = new StringBuilder();
/* 229 */     builder.append("PushManagerVO [id=").append(this.id).append(", userId=").append(this.userId).append(", fcmTokenId=")
/* 230 */       .append(this.fcmTokenId).append(", fcmAuthorizationKey=").append(this.fcmAuthorizationKey).append(", sendBody=")
/* 231 */       .append(this.sendBody).append(", errMsg=").append(this.errMsg).append(", tsReg=").append(this.tsReg)
/* 232 */       .append(", tsSend=").append(this.tsSend).append(", status=").append(this.status).append("]");
/* 233 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\FcmPushContents.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */