/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.interceptor;
/*    */ 
/*    */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*    */ import com.dreammirae.mmth.vo.AdministratorVO;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SessionTimeoutInterceptor
/*    */   extends HandlerInterceptorAdapter
/*    */ {
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
/*    */     HttpSession session;
/* 21 */     if ((session = request.getSession()) == null) {
/* 22 */       response.sendRedirect("/logout");
/* 23 */       return false;
/*    */     } 
/*    */     
/* 26 */     AdministratorVO curAdmin = getSessionAdmin(session);
/*    */     
/* 28 */     if (curAdmin == null) {
/* 29 */       response.sendRedirect("/logout");
/* 30 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 34 */     return super.preHandle(request, response, handler);
/*    */   }
/*    */   
/*    */   private AdministratorVO getSessionAdmin(HttpSession session) {
/* 38 */     Object obj = session.getAttribute("MMTH.ADMIN");
/*    */     
/* 40 */     if (!(obj instanceof byte[])) {
/* 41 */       return null;
/*    */     }
/*    */     
/*    */     try {
/* 45 */       AdministratorVO admin = (AdministratorVO)SerializationUtils.deserializeObject((byte[])obj);
/* 46 */       return admin;
/* 47 */     } catch (ClassNotFoundException|java.io.IOException e) {
/* 48 */       e.printStackTrace();
/*    */ 
/*    */       
/* 51 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\interceptor\SessionTimeoutInterceptor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */