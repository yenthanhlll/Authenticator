/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceAppAgentStatus;
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
/*     */ public class DeviceAppAgentVO
/*     */   implements Serializable
/*     */ {
/*  47 */   private int id = -1;
/*  48 */   private int userDeviceId = -1;
/*  49 */   private int agentId = -1;
/*     */   
/*     */   private AuthMethodTypes authType;
/*     */   
/*     */   private DeviceAppAgentStatus status;
/*     */   
/*     */   private String registrationId;
/*     */   
/*     */   private long tsReg;
/*     */   
/*     */   private long tsUpdated;
/*     */   
/*     */   private long tsDone;
/*     */   private long tsExpired;
/*     */   private transient UserDeviceVO userDevice;
/*     */   private transient AppAgentVO appAgent;
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int version = 1;
/*     */   
/*     */   public int getId() {
/*  69 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/*  77 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUserDeviceId() {
/*  84 */     return this.userDeviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserDeviceId(int userDeviceId) {
/*  92 */     this.userDeviceId = userDeviceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAgentId() {
/*  99 */     return this.agentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAgentId(int agentId) {
/* 107 */     this.agentId = agentId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthMethodTypes getAuthType() {
/* 114 */     return this.authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthType(AuthMethodTypes authType) {
/* 122 */     this.authType = authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceAppAgentStatus getStatus() {
/* 129 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(DeviceAppAgentStatus status) {
/* 137 */     this.status = status;
/*     */   }
/*     */   
/*     */   public String getRegistrationId() {
/* 141 */     return this.registrationId;
/*     */   }
/*     */   
/*     */   public void setRegistrationId(String registrationId) {
/* 145 */     this.registrationId = registrationId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsReg() {
/* 152 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(long tsReg) {
/* 160 */     this.tsReg = tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsUpdated() {
/* 167 */     return this.tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsUpdated(long tsUpdated) {
/* 175 */     this.tsUpdated = tsUpdated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsDone() {
/* 182 */     return this.tsDone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsDone(long tsDone) {
/* 190 */     this.tsDone = tsDone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTsExpired() {
/* 197 */     return this.tsExpired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsExpired(long tsExpired) {
/* 205 */     this.tsExpired = tsExpired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserDeviceVO getUserDevice() {
/* 212 */     return this.userDevice;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserDevice(UserDeviceVO userDevice) {
/* 220 */     this.userDevice = userDevice;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AppAgentVO getAppAgent() {
/* 227 */     return this.appAgent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAppAgent(AppAgentVO appAgent) {
/* 235 */     this.appAgent = appAgent;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.DeviceAppAgentVO createNewInstance(AppAgentVO appAgent, UserDeviceVO userDevice, AuthMethodTypes authType) {
/* 239 */     com.dreammirae.mmth.vo.DeviceAppAgentVO vo = new com.dreammirae.mmth.vo.DeviceAppAgentVO();
/* 240 */     vo.setAgentId(appAgent.getId());
/* 241 */     vo.setAuthType(authType);
/* 242 */     vo.setStatus(DeviceAppAgentStatus.NOT_AVAILABLE);
/*     */     
/* 244 */     if (userDevice != null) {
/* 245 */       vo.setUserDeviceId(userDevice.getId());
/* 246 */       vo.setUserDevice(userDevice);
/*     */     } 
/*     */     
/* 249 */     vo.setAppAgent(appAgent);
/* 250 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 260 */     StringBuilder builder = new StringBuilder();
/* 261 */     builder.append("DeviceAppAgentVO [id=").append(this.id).append(", userDeviceId=").append(this.userDeviceId).append(", agentId=").append(this.agentId).append(", authType=").append(this.authType)
/* 262 */       .append(", status=").append(this.status).append(", tsReg=").append(this.tsReg).append(", tsUpdated=").append(this.tsUpdated).append(", tsDone=").append(this.tsDone).append(", tsExpired=")
/* 263 */       .append(this.tsExpired).append("]");
/* 264 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
/* 274 */     out.writeInt(1);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 278 */     int ver = in.readInt();
/* 279 */     if (ver == 1);
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\DeviceAppAgentVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */