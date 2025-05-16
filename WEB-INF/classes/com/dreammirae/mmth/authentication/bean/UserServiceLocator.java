/*     */ package WEB-INF.classes.com.dreammirae.mmth.authentication.bean;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.authentication.bean.ExtensionDataLocator;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.AuthManagerVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.ServerChallengeVO;
/*     */ import com.dreammirae.mmth.vo.TokenRegistrationVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserServerPwdVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceIssueranceStatus;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class UserServiceLocator
/*     */ {
/*     */   private final AuthMethodTypes authType;
/*     */   private DeviceIssueranceStatus status;
/*     */   private UserVO user;
/*     */   private UserDeviceVO userDevice;
/*     */   private AppAgentVO appAgent;
/*     */   private DeviceAppAgentVO deviceAppAgent;
/*     */   private ServerChallengeVO challenge;
/*     */   private FidoRegistrationVO fidoRegi;
/*     */   private TokenRegistrationVO tokenRegi;
/*     */   private AuthManagerVO authManager;
/*     */   private UserServerPwdVO userServerPin;
/*     */   private List<FidoRegistrationVO> fidoRegistrations;
/*     */   private ExtensionDataLocator extLoc;
/*     */   private String macAddress;
/*     */   private long ip;
/*     */   
/*     */   public UserServiceLocator(AuthMethodTypes authType) {
/*  37 */     this.authType = authType;
/*     */   }
/*     */   
/*     */   public AuthMethodTypes getAuthType() {
/*  41 */     return this.authType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceIssueranceStatus getStatus() {
/*  48 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatus(DeviceIssueranceStatus status) {
/*  56 */     this.status = status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserVO getUser() {
/*  63 */     return this.user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUser(UserVO user) {
/*  71 */     this.user = user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserDeviceVO getUserDevice() {
/*  78 */     return this.userDevice;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserDevice(UserDeviceVO userDevice) {
/*  86 */     this.userDevice = userDevice;
/*     */   }
/*     */   
/*     */   public AppAgentVO getAppAgent() {
/*  90 */     return this.appAgent;
/*     */   }
/*     */   
/*     */   public void setAppAgent(AppAgentVO appAgent) {
/*  94 */     this.appAgent = appAgent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceAppAgentVO getDeviceAppAgent() {
/* 101 */     return this.deviceAppAgent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDeviceAppAgent(DeviceAppAgentVO deviceAppAgent) {
/* 109 */     this.deviceAppAgent = deviceAppAgent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerChallengeVO getChallenge() {
/* 116 */     return this.challenge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChallenge(ServerChallengeVO challenge) {
/* 124 */     this.challenge = challenge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FidoRegistrationVO getFidoRegi() {
/* 131 */     return this.fidoRegi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFidoRegi(FidoRegistrationVO fidoRegi) {
/* 139 */     this.fidoRegi = fidoRegi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TokenRegistrationVO getTokenRegi() {
/* 146 */     return this.tokenRegi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTokenRegi(TokenRegistrationVO tokenRegi) {
/* 154 */     this.tokenRegi = tokenRegi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthManagerVO getAuthManager() {
/* 161 */     return this.authManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthManager(AuthManagerVO authManager) {
/* 169 */     this.authManager = authManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserServerPwdVO getUserServerPin() {
/* 176 */     return this.userServerPin;
/*     */   }
/*     */   
/*     */   public List<FidoRegistrationVO> getFidoRegistrations() {
/* 180 */     return this.fidoRegistrations;
/*     */   }
/*     */   
/*     */   public void setFidoRegistrations(List<FidoRegistrationVO> fidoRegistrations) {
/* 184 */     this.fidoRegistrations = fidoRegistrations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserServerPin(UserServerPwdVO userServerPin) {
/* 192 */     this.userServerPin = userServerPin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionDataLocator getExtLoc() {
/* 199 */     return this.extLoc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtLoc(ExtensionDataLocator extLoc) {
/* 206 */     this.extLoc = extLoc;
/*     */   }
/*     */   
/*     */   public String getMacAddress() {
/* 210 */     return this.macAddress;
/*     */   }
/*     */   
/*     */   public void setMacAddress(String macAddress) {
/* 214 */     this.macAddress = macAddress;
/*     */   }
/*     */   
/*     */   public long getIp() {
/* 218 */     return this.ip;
/*     */   }
/*     */   
/*     */   public void setIp(long ip) {
/* 222 */     this.ip = ip;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\bean\UserServiceLocator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */