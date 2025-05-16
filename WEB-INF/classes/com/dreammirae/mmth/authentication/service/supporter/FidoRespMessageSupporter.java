/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.supporter;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.service.supporter.FidoAppIdPolicySupporter;
/*    */ import com.dreammirae.mmth.authentication.service.supporter.FidoPolicySupporter;
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.AppIDSupporter;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.PolicySupporter;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.RespMessageSupporter;
/*    */ import com.dreammirae.mmth.fido.uaf.ChannelBinding;
/*    */ import com.dreammirae.mmth.fido.uaf.Version;
/*    */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*    */ 
/*    */ public abstract class FidoRespMessageSupporter
/*    */   implements RespMessageSupporter<FidoRegistrationVO> {
/*    */   public Version[] returnSupportedVersions() {
/* 18 */     return new Version[] { Version.getCurrentVersion() };
/*    */   }
/*    */ 
/*    */   
/*    */   public AppIDSupporter returnAppIDSupporter() {
/* 23 */     return (AppIDSupporter)new FidoAppIdPolicySupporter();
/*    */   }
/*    */ 
/*    */   
/*    */   public PolicySupporter returnPolicySupporter() {
/* 28 */     return (PolicySupporter)new FidoPolicySupporter(null, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean returnCheckChannelBindingPolicy() {
/* 33 */     return SystemSettingsDao.getSettingEnabled("fido.valdiateChannelBindingEnabled").toBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelBinding[] returnChannelBinding() throws FidoUafStatusCodeException {
/* 38 */     return Commons.ctx.getChannelBinding();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public long returnLimitRegCounter(String aaid) {
/* 44 */     return 65535L;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\supporter\FidoRespMessageSupporter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */