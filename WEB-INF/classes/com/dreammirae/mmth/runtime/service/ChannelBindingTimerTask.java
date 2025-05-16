/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.service;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.fido.ServerTlsUtils;
/*    */ import com.dreammirae.mmth.fido.uaf.ChannelBinding;
/*    */ import java.util.Arrays;
/*    */ import java.util.TimerTask;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChannelBindingTimerTask
/*    */   extends TimerTask
/*    */ {
/* 24 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.runtime.service.ChannelBindingTimerTask.class);
/*    */   
/*    */   private static final long DELAY_TO_EXECUTE = 2500L;
/*    */ 
/*    */   
/*    */   public static void refeshChannelBinding() {
/* 30 */     if (SystemSettingsDao.getSettingEnabled("fido.valdiateChannelBindingEnabled").toBoolean() || 
/* 31 */       SystemSettingsDao.getSettingEnabled("biotp.valdiateChannelBindingEnabled").toBoolean()) {
/* 32 */       Commons.timer.schedule(new com.dreammirae.mmth.runtime.service.ChannelBindingTimerTask(), 2500L);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 42 */       ChannelBinding[] cb = ServerTlsUtils.readServerTlsInformation(
/* 43 */           SystemSettingsDao.getValue("integrate.rpServerUri"));
/*    */       
/* 45 */       LOG.info("### Fido Channel binding data updated... ###");
/* 46 */       LOG.info(Arrays.toString((Object[])cb));
/* 47 */       LOG.info("############################################");
/*    */       
/* 49 */       Commons.ctx.getServletContext().setAttribute("FIDO_CHANNEL_BINDING", cb);
/*    */     }
/* 51 */     catch (Exception e) {
/* 52 */       LOG.error("Failed to read RP server's TLS information.", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\service\ChannelBindingTimerTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */