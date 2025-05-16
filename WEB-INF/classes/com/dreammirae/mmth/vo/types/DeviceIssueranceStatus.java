/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.types;
/*    */ import com.dreammirae.mmth.vo.types.IssuanceTypes;
/*    */ 
/*    */ public enum DeviceIssueranceStatus {
/*  5 */   NEW_USER_REGISTER("DeviceIssuanceStatus.NEW_USER_REGISTER", IssuanceTypes.NEW_USER_ISSURANCE),
/*  6 */   NEW_USER_REGISTERED("DeviceIssuanceStatus.NEW_USER_REGISTERED", IssuanceTypes.NEW_USER_ISSURANCE),
/*  7 */   ADD_DEVICE_REGISTER("DeviceIssuanceStatus.ADD_DEVICE_REGISTER", IssuanceTypes.ADD_DEVICE_ISSURANCE),
/*  8 */   ADD_DEVICE_REGISTERED("DeviceIssuanceStatus.ADD_DEVICE_REGISTERED", IssuanceTypes.ADD_DEVICE_ISSURANCE),
/*    */ 
/*    */   
/* 11 */   RE_REGISTER("DeviceIssuanceStatus.RE_REGISTER", IssuanceTypes.RE_REGISTRATION),
/* 12 */   RE_REGISTERED("DeviceIssuanceStatus.RE_REGISTERED", IssuanceTypes.RE_REGISTRATION);
/*    */   
/*    */   private final String messageKey;
/*    */   private final IssuanceTypes issuanceType;
/*    */   
/*    */   DeviceIssueranceStatus(String messageKey, IssuanceTypes issuanceType) {
/* 18 */     this.messageKey = messageKey;
/* 19 */     this.issuanceType = issuanceType;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 23 */     return this.messageKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public IssuanceTypes getIssuanceTypes() {
/* 28 */     return this.issuanceType;
/*    */   }
/*    */ 
/*    */   
/*    */   public static com.dreammirae.mmth.vo.types.DeviceIssueranceStatus getByName(String name) {
/* 33 */     for (com.dreammirae.mmth.vo.types.DeviceIssueranceStatus status : values()) {
/*    */       
/* 35 */       if (status.name().equalsIgnoreCase(name)) {
/* 36 */         return status;
/*    */       }
/*    */     } 
/*    */     
/* 40 */     return NEW_USER_REGISTER;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\types\DeviceIssueranceStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */