/*    */ package WEB-INF.classes.com.dreammirae.mmth.runtime.service;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.db.dao.TokenDao;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.runtime.service.ChannelBindingTimerTask;
/*    */ import com.dreammirae.mmth.vo.TokenVO;
/*    */ import com.dreammirae.mmth.vo.types.TokenStatus;
/*    */ import java.util.TimerTask;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TokenThresholdTimerTask
/*    */   extends TimerTask
/*    */ {
/* 20 */   private static final Logger LOG = LoggerFactory.getLogger(ChannelBindingTimerTask.class);
/*    */   
/*    */   private static final long DELAY_TO_EXECUTE = 2500L;
/*    */   
/*    */   public static void doSchedule() {
/* 25 */     if (!Commons.ctx.getTokenThresholdScheduled().booleanValue()) {
/* 26 */       Commons.ctx.getServletContext().setAttribute("TOKEN_THRESHOLD_SCHEDULED_BOOL", Boolean.TRUE);
/* 27 */       Commons.timer.schedule(new com.dreammirae.mmth.runtime.service.TokenThresholdTimerTask(), 2500L);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 35 */     TokenVO vo = new TokenVO();
/* 36 */     vo.setStatus(TokenStatus.AVAILABLE);
/*    */     
/*    */     try {
/* 39 */       int threshold = SystemSettingsDao.getInt("integrate.tokenThresholdCnt");
/*    */       
/* 41 */       TokenDao dao = new TokenDao();
/* 42 */       int available = dao.getViewConentCount(vo, null, null);
/*    */       
/* 44 */       if (available < 1) {
/* 45 */         AuditAlarmTypes.TOKEN.raiseAlarm(null, 1027, AlarmLevels.LIFE_SAFETY, new Object[] { "" });
/* 46 */       } else if (available < threshold) {
/* 47 */         AuditAlarmTypes.TOKEN.raiseAlarm(null, 1026, AlarmLevels.CRITICAL, new Object[] { String.valueOf(available) });
/*    */       }
/*    */     
/* 50 */     } catch (Exception e) {
/* 51 */       LOG.error("Failed to read TOKEN count.", e);
/*    */     } finally {
/* 53 */       Commons.ctx.getServletContext().setAttribute("TOKEN_THRESHOLD_SCHEDULED_BOOL", Boolean.FALSE);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\runtime\service\TokenThresholdTimerTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */