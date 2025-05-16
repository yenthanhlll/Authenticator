/*    */ package WEB-INF.classes.com.dreammirae.mmth.config;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import javax.servlet.http.HttpSessionEvent;
/*    */ import javax.servlet.http.HttpSessionListener;
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
/*    */ public class MMTHSessionListener
/*    */   implements HttpSessionListener
/*    */ {
/*    */   public void sessionCreated(HttpSessionEvent event) {
/* 20 */     event.getSession().setMaxInactiveInterval(sessionTimeout());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void sessionDestroyed(HttpSessionEvent event) {}
/*    */ 
/*    */   
/*    */   private int sessionTimeout() {
/* 29 */     int timeoutMin = SystemSettingsDao.getInt("application.sessionTimeout");
/* 30 */     return timeoutMin * 60;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\config\MMTHSessionListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */