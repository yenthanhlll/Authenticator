/*     */ package WEB-INF.classes.com.dreammirae.mmth.vo;
/*     */ 
/*     */ import com.dreammirae.mmth.misc.I18nMessage;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*     */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*     */ import com.dreammirae.mmth.vo.bean.IRestValidator;
/*     */ import com.dreammirae.mmth.vo.bean.RestResponse;
/*     */ import com.dreammirae.mmth.vo.bean.json.EnumMessageSerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.MessageKeySerializer;
/*     */ import com.dreammirae.mmth.vo.bean.json.TimestampSerializer;
/*     */ import com.dreammirae.mmth.vo.types.AckCodes;
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
/*     */ public class AuditAlarmVO
/*     */   implements Serializable, IRestValidator
/*     */ {
/*  56 */   private long id = -1L;
/*     */   
/*     */   private AuditAlarmTypes auditType;
/*     */   
/*     */   private int actionType;
/*     */   
/*     */   private AlarmLevels alarmLevel;
/*     */   
/*  64 */   private String description = "-";
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private Long tsReg;
/*     */   
/*     */   private AckCodes ack;
/*     */   
/*  71 */   private String ackAdmin = "-";
/*     */ 
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = TimestampSerializer.class)
/*     */   private Long tsAck;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient I18nMessage descMessage;
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/*  88 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(long id) {
/*  96 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuditAlarmTypes getAuditType() {
/* 103 */     return this.auditType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuditType(AuditAlarmTypes auditType) {
/* 111 */     this.auditType = auditType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getActionType() {
/* 118 */     return this.actionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActionType(int actionType) {
/* 126 */     this.actionType = actionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlarmLevels getAlarmLevel() {
/* 133 */     return this.alarmLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlarmLevel(AlarmLevels alarmLevel) {
/* 141 */     this.alarmLevel = alarmLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 148 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/* 156 */     this.description = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTsReg() {
/* 163 */     return this.tsReg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTsReg(Long tsReg) {
/* 171 */     this.tsReg = tsReg;
/*     */   }
/*     */   
/*     */   public AckCodes getAck() {
/* 175 */     return this.ack;
/*     */   }
/*     */   
/*     */   public void setAck(AckCodes ack) {
/* 179 */     this.ack = ack;
/*     */   }
/*     */   
/*     */   public String getAckAdmin() {
/* 183 */     return this.ackAdmin;
/*     */   }
/*     */   
/*     */   public void setAckAdmin(String ackAdmin) {
/* 187 */     this.ackAdmin = ackAdmin;
/*     */   }
/*     */   
/*     */   public Long getTsAck() {
/* 191 */     return this.tsAck;
/*     */   }
/*     */   
/*     */   public void setTsAck(Long tsAck) {
/* 195 */     this.tsAck = tsAck;
/*     */   }
/*     */ 
/*     */   
/*     */   @JsonSerialize(using = MessageKeySerializer.class)
/*     */   public String getActionTypeDesc() {
/* 201 */     if (this.auditType == null) {
/* 202 */       return null;
/*     */     }
/*     */     
/* 205 */     return this.auditType.getAuditActionTypeMessageKey(this.actionType);
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AuditAlarmTypes getAuditTypeDesc() {
/* 210 */     return this.auditType;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AlarmLevels getAlarmLevelDesc() {
/* 215 */     return this.alarmLevel;
/*     */   }
/*     */   
/*     */   @JsonSerialize(using = EnumMessageSerializer.class)
/*     */   public AckCodes getAckDesc() {
/* 220 */     return this.ack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public I18nMessage getDescMessage() {
/* 227 */     return this.descMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescMessage(I18nMessage descMessage) {
/* 234 */     this.descMessage = descMessage;
/*     */   }
/*     */   
/*     */   public static com.dreammirae.mmth.vo.AuditAlarmVO createNewAuditAlarm(AuditAlarmTypes auditType, AlarmLevels alarmLv, int actionType, I18nMessage descMessage) {
/* 238 */     com.dreammirae.mmth.vo.AuditAlarmVO vo = new com.dreammirae.mmth.vo.AuditAlarmVO();
/* 239 */     vo.setAuditType(auditType);
/* 240 */     vo.setAlarmLevel(alarmLv);
/* 241 */     vo.setActionType(actionType);
/* 242 */     vo.setDescMessage(descMessage);
/* 243 */     vo.setTsReg(Long.valueOf(System.currentTimeMillis()));
/* 244 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(RestResponse resp) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 255 */     StringBuilder builder = new StringBuilder();
/* 256 */     builder.append("AuditAlarmVO [id=").append(this.id).append(", auditType=").append(this.auditType).append(", actionType=").append(this.actionType).append(", alarmLevel=").append(this.alarmLevel)
/* 257 */       .append(", description=").append(this.description).append(", tsReg=").append(this.tsReg).append(", ack=").append(this.ack).append(", ackAdmin=").append(this.ackAdmin).append(", tsAck=").append(this.tsAck)
/* 258 */       .append("]");
/* 259 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 269 */     for (int i = 0; i < 10; i++)
/* 270 */       System.out.println(AuditAlarmTypes.ADMIN.getCode() + "," + 'Ȃ' + "," + AlarmLevels.INFORMATION.getCode() + ", '관리자가 로그인함', " + System.currentTimeMillis()); 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\AuditAlarmVO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */