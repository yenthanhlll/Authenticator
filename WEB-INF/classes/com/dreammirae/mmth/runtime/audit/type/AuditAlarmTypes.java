/*     */ package WEB-INF.classes.com.dreammirae.mmth.runtime.audit.type;
/*     */ import com.dreammirae.mmth.runtime.audit.AuditAlarmManager;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AdiministratorAuditType;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.FidoMetadataAuditType;
/*     */ import com.dreammirae.mmth.runtime.audit.type.IAuditType;
/*     */ import com.dreammirae.mmth.runtime.audit.type.PublisherAuditType;
/*     */ import com.dreammirae.mmth.runtime.audit.type.SystemAuditType;
/*     */ import com.dreammirae.mmth.runtime.audit.type.TokenAuditType;
/*     */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import com.dreammirae.mmth.vo.AuditAlarmVO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public enum AuditAlarmTypes {
/*  17 */   SYSTEM(256, "AuditAlarmTypes.SYSTEM", (IAuditType)new SystemAuditType()),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  22 */   ADMIN(512, "AuditAlarmTypes.ADMIN", (IAuditType)new AdiministratorAuditType()),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   USER(768, "AuditAlarmTypes.USER", null),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   TOKEN(1024, "AuditAlarmTypes.TOKEN", (IAuditType)new TokenAuditType()),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   METADATA(1280, "AuditAlarmTypes.METADATA", (IAuditType)new FidoMetadataAuditType()),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   FACET(1536, "AuditAlarmTypes.FACET", (IAuditType)new FidoFacetAuditType()),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   APP_AGENT(1792, "AuditAlarmTypes.APP_AGENT", (IAuditType)new AppAgentAuditType()),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   SYSTEM_SETTING(2048, "AuditAlarmTypes.SYSTEM_SETTING", (IAuditType)new SystemSettingsAuditType()),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   PUBLISHER(2304, "AuditAlarmTypes.PUBLISHER", (IAuditType)new PublisherAuditType()),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   EXTERNAL_API(2560, "AuditAlarmTypes.EXTERNAL_API", (IAuditType)new ExternalAPIAuditType());
/*     */   
/*     */   private final int code;
/*     */   private final String messageKey;
/*     */   private final IAuditType auditType;
/*     */   
/*     */   AuditAlarmTypes(int code, String messageKey, IAuditType auditType) {
/*  69 */     this.code = code;
/*  70 */     this.messageKey = messageKey;
/*  71 */     this.auditType = auditType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCode() {
/*  78 */     return this.code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessageKey() {
/*  85 */     return this.messageKey;
/*     */   }
/*     */   
/*     */   public IAuditType getAuditType() {
/*  89 */     return this.auditType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAuditActionTypeMessageKey(int actionType) {
/*  94 */     if (this.auditType != null) {
/*  95 */       return this.auditType.getActionMsgKey(actionType);
/*     */     }
/*     */     
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void raiseAlarm(AdministratorVO sessionAdmin, int actionType, AlarmLevels alarmLevel, Object... args) {
/* 103 */     if (this.auditType == null) {
/*     */       return;
/*     */     }
/*     */     
/* 107 */     if (sessionAdmin != null && 
/* 108 */       AdministratorTypes.DEV.equals(sessionAdmin.getAdminType())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 113 */     AuditAlarmVO vo = AuditAlarmVO.createNewAuditAlarm(this, alarmLevel, actionType, this.auditType
/* 114 */         .getDescriptionMessage(actionType, args));
/*     */     
/* 116 */     AuditAlarmManager.addAlarm(vo);
/*     */   }
/*     */ 
/*     */   
/*     */   public static com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes getAuditAlarmType(int code) {
/* 121 */     for (com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes type : values()) {
/* 122 */       if (type.code == code) {
/* 123 */         return type;
/*     */       }
/*     */     } 
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<KeyValuePair<String, String>> getMessageKeyPair() {
/* 131 */     List<KeyValuePair<String, String>> list = new ArrayList<>();
/*     */     
/* 133 */     for (com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes type : values()) {
/* 134 */       list.add(new KeyValuePair(type.name(), type.getMessageKey()));
/*     */     }
/*     */     
/* 137 */     return list;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes getAuditAlarmTypeByName(String name) {
/* 141 */     for (com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes type : values()) {
/* 142 */       if (type.name().equalsIgnoreCase(name)) {
/* 143 */         return type;
/*     */       }
/*     */     } 
/* 146 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\audit\type\AuditAlarmTypes.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */