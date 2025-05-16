/*    */ package WEB-INF.classes.com.dreammirae.mmth.config;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.db.dao.AdministratorDao;
/*    */ import com.dreammirae.mmth.misc.MessageUtils;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AlarmLevels;
/*    */ import com.dreammirae.mmth.runtime.audit.type.AuditAlarmTypes;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*    */ import com.dreammirae.mmth.vo.AdministratorVO;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WebManagerSuccessHandler
/*    */   extends SavedRequestAwareAuthenticationSuccessHandler
/*    */ {
/*    */   private static final String PARAM_USERNAME = "username";
/*    */   
/*    */   public WebManagerSuccessHandler() {
/* 29 */     setAlwaysUseDefaultTargetUrl(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
/* 36 */     HttpSession session = request.getSession(false);
/*    */ 
/*    */     
/* 39 */     String username = request.getParameter("username");
/*    */     
/* 41 */     if (session != null && !StringUtils.isEmpty(username)) {
/*    */       
/* 43 */       AdministratorDao dao = new AdministratorDao();
/*    */       
/* 45 */       AdministratorVO vo = dao.getOneByObj(new AdministratorVO(username));
/*    */ 
/*    */       
/* 48 */       session.setAttribute("MMTH.LOGON.DATETIME", Commons.displayDateTime(vo.getTsLastLogin()));
/*    */       
/* 50 */       vo.setTsLastLogin(System.currentTimeMillis());
/* 51 */       vo.setLastRemoteAddr(request.getRemoteAddr());
/* 52 */       dao.save(vo);
/* 53 */       vo.setPassword(null);
/*    */       
/* 55 */       session.setAttribute("MMTH.ADMIN", SerializationUtils.serializeObject(vo));
/* 56 */       setDefaultTargetUrl(vo.getHomeUrl());
/*    */       
/* 58 */       session.setAttribute("MMTH.ADMIN.TYPE", Commons.getMessage(MessageUtils.getMessageSource(), vo.getAdminType().getMessageKey(), null));
/* 59 */       session.setAttribute("MMTH.ADMIN.REG", Commons.displayDateTime(vo.getTsReg()));
/* 60 */       session.setAttribute("MMTH.ADMIN.UPDATE", Commons.displayDateTime(vo.getTsUpdated()));
/* 61 */       session.setAttribute("MMTH.ADMIN.USERNAME", vo.getUsername());
/* 62 */       session.setAttribute("MMTH.ADMIN.HOME_URL", vo.getHomeUrl());
/* 63 */       session.setAttribute("MMTH.ADMIN.LAST_REMOTE_ADDR", vo.getLastRemoteAddr());
/*    */       
/* 65 */       session.setAttribute("JEUS_LOGIN_KEY", vo.getUsername());
/*    */ 
/*    */       
/* 68 */       AuditAlarmTypes.ADMIN.raiseAlarm(vo, 513, AlarmLevels.NONE, new Object[] { vo.getUsername(), request.getRemoteAddr() });
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 73 */     super.onAuthenticationSuccess(request, response, authentication);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\config\WebManagerSuccessHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */