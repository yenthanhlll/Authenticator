/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*     */ 
/*     */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.AppAgentDao;
/*     */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*     */ import com.dreammirae.mmth.vo.AppAgentVO;
/*     */ import com.dreammirae.mmth.vo.DeviceAppAgentVO;
/*     */ import com.dreammirae.mmth.vo.IssuanceHistoryVO;
/*     */ import com.dreammirae.mmth.vo.UserDeviceVO;
/*     */ import com.dreammirae.mmth.vo.UserVO;
/*     */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum IssuanceTypes
/*     */ {
/*  35 */   NEW_USER_ISSURANCE("1", "IssuanceTypes.NEW_USER_ISSURANCE"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   ADD_DEVICE_ISSURANCE("2", "IssuanceTypes.ADD_DEVICE_ISSURANCE"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   RE_REGISTRATION("4", "IssuanceTypes.RE_REGISTRATION");
/*     */   
/*     */   private final String code;
/*     */   private final String messageKey;
/*     */   
/*     */   IssuanceTypes(String code, String messageKey) {
/*  56 */     this.code = code;
/*  57 */     this.messageKey = messageKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCode() {
/*  64 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessageKey() {
/*  71 */     return this.messageKey;
/*     */   }
/*     */   
/*     */   public IssuanceHistoryVO createIssuanceHistoryVO(UserVO user, UserDeviceVO userDevice, DeviceAppAgentVO deviceAppAgent, String aaid, String tokenId) {
/*  75 */     IssuanceHistoryVO vo = new IssuanceHistoryVO();
/*     */     
/*  77 */     vo.setAuthType(deviceAppAgent.getAuthType());
/*  78 */     vo.setDeviceType(userDevice.getDeviceType());
/*  79 */     if (deviceAppAgent.getAuthType() == null) {
/*     */       
/*  81 */       vo.setAuthType(AuthMethodTypes.HWOTP);
/*  82 */       vo.setDeviceType(DeviceTypes.ANANYMOUS);
/*  83 */       vo.setDeviceModel(null);
/*     */     } 
/*  85 */     vo.setUsername(user.getUsername());
/*  86 */     vo.setDeviceId(userDevice.getDeviceId());
/*     */     
/*  88 */     vo.setProductType(user.getProductType());
/*     */     
/*  90 */     AppAgentVO appAgent = AppAgentDao.getAcceptableAppAgent(deviceAppAgent.getAgentId());
/*     */     
/*  92 */     if (appAgent != null) {
/*  93 */       vo.setPkgUnique(appAgent.getPkgUnique());
/*     */     }
/*     */     
/*  96 */     vo.setIssueType(this);
/*  97 */     vo.setAaid(aaid);
/*  98 */     vo.setTokenId(tokenId);
/*  99 */     vo.setTsIssue(deviceAppAgent.getTsDone());
/* 100 */     vo.setIssueMonth(Commons.getFormatMonth(vo.getTsIssue()));
/* 101 */     vo.setIssueDate(Commons.getFormatDate(vo.getTsIssue()));
/* 102 */     vo.setIssueTime(Commons.getFormatTime(vo.getTsIssue()));
/* 103 */     return vo;
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.vo.types.IssuanceTypes getIssuanceType(String code) {
/* 108 */     for (com.dreammirae.mmth.vo.types.IssuanceTypes type : values()) {
/* 109 */       if (type.code.equals(code)) {
/* 110 */         return type;
/*     */       }
/*     */     } 
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 118 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*     */     
/* 120 */     for (com.dreammirae.mmth.vo.types.IssuanceTypes type : values()) {
/* 121 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*     */     }
/*     */     
/* 124 */     return list;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.types.IssuanceTypes getIssuanceTypeByName(String name) {
/* 128 */     for (com.dreammirae.mmth.vo.types.IssuanceTypes type : values()) {
/* 129 */       if (type.name().equalsIgnoreCase(name)) {
/* 130 */         return type;
/*     */       }
/*     */     } 
/* 133 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\IssuanceTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */