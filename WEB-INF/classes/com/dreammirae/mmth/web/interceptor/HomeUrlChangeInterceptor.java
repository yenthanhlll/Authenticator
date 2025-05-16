/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.interceptor;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.db.dao.AdministratorDao;
/*    */ import com.dreammirae.mmth.util.StringUtils;
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
/*    */ 
/*    */ public class HomeUrlChangeInterceptor
/*    */   extends HandlerInterceptorAdapter
/*    */ {
/*    */   private static final String DEFAULT_URI = "homeUri";
/*    */   
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
/*    */     HttpSession session;
/* 27 */     if ((session = request.getSession()) == null) {
/* 28 */       return super.preHandle(request, response, handler);
/*    */     }
/*    */     
/* 31 */     AdministratorVO curAdmin = getSessionAdmin(session);
/*    */     
/* 33 */     if (curAdmin == null || StringUtils.isEmpty(curAdmin.getUsername())) {
/* 34 */       return super.preHandle(request, response, handler);
/*    */     }
/*    */ 
/*    */     
/* 38 */     if (request.getParameter("homeUri") == null) {
/* 39 */       return super.preHandle(request, response, handler);
/*    */     }
/*    */     
/* 42 */     String homeUri = request.getRequestURI();
/*    */     
/* 44 */     if (!homeUri.equals(curAdmin.getHomeUrl())) {
/*    */       
/* 46 */       AdministratorDao dao = new AdministratorDao();
/* 47 */       AdministratorVO target = dao.getOneByObj(curAdmin);
/* 48 */       target.setHomeUrl(homeUri);
/*    */       try {
/* 50 */         dao.save(target);
/* 51 */         target.setPassword(null);
/* 52 */         session.setAttribute("MMTH.ADMIN", SerializationUtils.serializeObject(target));
/* 53 */         session.setAttribute("MMTH.ADMIN.UPDATE", Commons.displayDateTime(target.getTsUpdated()));
/* 54 */         session.setAttribute("MMTH.ADMIN.HOME_URL", target.getHomeUrl());
/* 55 */       } catch (Exception ignore) {
/*    */         
/* 57 */         ignore.printStackTrace();
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 62 */     return super.preHandle(request, response, handler);
/*    */   }
/*    */ 
/*    */   
/*    */   private AdministratorVO getSessionAdmin(HttpSession session) {
/* 67 */     Object obj = session.getAttribute("MMTH.ADMIN");
/*    */     
/* 69 */     if (!(obj instanceof byte[])) {
/* 70 */       return null;
/*    */     }
/*    */     
/*    */     try {
/* 74 */       AdministratorVO admin = (AdministratorVO)SerializationUtils.deserializeObject((byte[])obj);
/* 75 */       return admin;
/* 76 */     } catch (ClassNotFoundException|java.io.IOException e) {
/* 77 */       e.printStackTrace();
/*    */ 
/*    */       
/* 80 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\interceptor\HomeUrlChangeInterceptor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */