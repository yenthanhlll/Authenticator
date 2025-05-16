/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean.json;
/*    */ 
/*    */ import com.dreammirae.mmth.MMTHConstants;
/*    */ import com.dreammirae.mmth.authentication.AuthMethodTypes;
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.service.type.LogActionTypes;
/*    */ import com.dreammirae.mmth.runtime.service.type.LogOperationTypes;
/*    */ import com.dreammirae.mmth.runtime.service.type.OpRequstTypes;
/*    */ import com.dreammirae.mmth.vo.types.AckCodes;
/*    */ import com.dreammirae.mmth.vo.types.AdministratorTypes;
/*    */ import com.dreammirae.mmth.vo.types.AgentOsTypes;
/*    */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*    */ import com.dreammirae.mmth.vo.types.IssuanceTypes;
/*    */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*    */ import com.dreammirae.mmth.vo.types.UserDeviceStatus;
/*    */ import com.dreammirae.mmth.vo.types.UserStatus;
/*    */ import com.dreammirae.mmth.vo.types.YNStatus;
/*    */ import com.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.fasterxml.jackson.databind.SerializerProvider;
/*    */ import java.io.IOException;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.MessageSource;
/*    */ import org.springframework.context.i18n.LocaleContextHolder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnumMessageSerializer
/*    */   extends JsonSerializer<Object>
/*    */ {
/*    */   @Autowired
/*    */   private MessageSource messageSource;
/*    */   
/*    */   public void serialize(Object type, JsonGenerator gen, SerializerProvider prov) throws IOException, JsonProcessingException {
/* 40 */     if (type == null) {
/* 41 */       gen.writeString("-");
/*    */       
/*    */       return;
/*    */     } 
/* 45 */     if (type instanceof DisabledStatus) {
/* 46 */       gen.writeString(this.messageSource.getMessage(((DisabledStatus)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 47 */     } else if (type instanceof AdministratorTypes) {
/* 48 */       gen.writeString(this.messageSource.getMessage(((AdministratorTypes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 49 */     } else if (type instanceof AgentOsTypes) {
/* 50 */       gen.writeString(this.messageSource.getMessage(((AgentOsTypes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 51 */     } else if (type instanceof TokenStatus) {
/* 52 */       gen.writeString(this.messageSource.getMessage(((TokenStatus)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 53 */     } else if (type instanceof AuthMethodTypes) {
/* 54 */       gen.writeString(this.messageSource.getMessage(((AuthMethodTypes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 55 */     } else if (type instanceof com.dreammirae.mmth.fido.registry.UserVerificationMethods) {
/* 56 */       gen.writeString(this.messageSource.getMessage((String)MMTHConstants.USER_VERIFICATION_METHODS_MSG_KEYS.get(type), null, LocaleContextHolder.getLocale()));
/* 57 */     } else if (type instanceof AuditAlarmTypes) {
/* 58 */       gen.writeString(this.messageSource.getMessage(((AuditAlarmTypes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 59 */     } else if (type instanceof AlarmLevels) {
/* 60 */       gen.writeString(this.messageSource.getMessage(((AlarmLevels)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 61 */     } else if (type instanceof AckCodes) {
/* 62 */       gen.writeString(this.messageSource.getMessage(((AckCodes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 63 */     } else if (type instanceof LogActionTypes) {
/* 64 */       gen.writeString(this.messageSource.getMessage(((LogActionTypes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 65 */     } else if (type instanceof LogOperationTypes) {
/* 66 */       gen.writeString(this.messageSource.getMessage(((LogOperationTypes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 67 */     } else if (type instanceof OpRequstTypes) {
/* 68 */       gen.writeString(this.messageSource.getMessage(((OpRequstTypes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 69 */     } else if (type instanceof IssuanceTypes) {
/* 70 */       gen.writeString(this.messageSource.getMessage(((IssuanceTypes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 71 */     } else if (type instanceof UserStatus) {
/* 72 */       gen.writeString(this.messageSource.getMessage(((UserStatus)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 73 */     } else if (type instanceof UserDeviceStatus) {
/* 74 */       gen.writeString(this.messageSource.getMessage(((UserDeviceStatus)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 75 */     } else if (type instanceof ReturnCodes) {
/* 76 */       gen.writeString(this.messageSource.getMessage(((ReturnCodes)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/* 77 */     } else if (type instanceof YNStatus) {
/* 78 */       gen.writeString(this.messageSource.getMessage(((YNStatus)type).getMessageKey(), null, LocaleContextHolder.getLocale()));
/*    */     } else {
/* 80 */       gen.writeString(type.toString());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\json\EnumMessageSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */