/*    */ package WEB-INF.classes.com.dreammirae.mmth.config;
/*    */ 
/*    */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.security.core.AuthenticationException;
/*    */ import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WebManagerFailureHandler
/*    */   extends SimpleUrlAuthenticationFailureHandler
/*    */ {
/*    */   public WebManagerFailureHandler() {
/* 20 */     setDefaultFailureUrl("/login");
/* 21 */     setUseForward(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
/* 29 */     if (authenticationException instanceof org.springframework.security.authentication.DisabledException) {
/* 30 */       request.setAttribute("error", "login.error.2");
/*    */     } else {
/* 32 */       request.setAttribute("error", "login.error.1");
/*    */     } 
/*    */     
/* 35 */     String username = request.getParameter("username");
/*    */     
/* 37 */     request.setAttribute("username", username);
/*    */     
/* 39 */     AuditAlarmTypes.ADMIN.raiseAlarm(null, 518, AlarmLevels.URGENT, new Object[] { username, request.getRemoteAddr() });
/*    */ 
/*    */     
/* 42 */     super.onAuthenticationFailure(request, response, authenticationException);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\config\WebManagerFailureHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */