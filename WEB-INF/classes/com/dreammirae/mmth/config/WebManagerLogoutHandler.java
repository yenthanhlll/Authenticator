/*    */ package WEB-INF.classes.com.dreammirae.mmth.config;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
/*    */ 
/*    */ 
/*    */ public class WebManagerLogoutHandler
/*    */   extends SimpleUrlLogoutSuccessHandler
/*    */ {
/*    */   public WebManagerLogoutHandler(String defaultTargetUrl) {
/* 15 */     setDefaultTargetUrl(defaultTargetUrl);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
/* 21 */     super.onLogoutSuccess(request, response, authentication);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\config\WebManagerLogoutHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */