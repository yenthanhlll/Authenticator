/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.interceptor;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.vo.types.DisabledStatus;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.context.i18n.LocaleContextHolder;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommonDataInterceptor
/*    */   extends HandlerInterceptorAdapter
/*    */ {
/*    */   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
/* 21 */     request.setAttribute("supportedLanguages", Commons.getSupportedLangs());
/* 22 */     request.setAttribute("currentLang", LocaleContextHolder.getLocale().getLanguage());
/* 23 */     request.setAttribute("NEW_ID", Integer.valueOf(-1));
/* 24 */     request.setAttribute(DisabledStatus.DISABLED.name(), DisabledStatus.DISABLED.name());
/* 25 */     request.setAttribute(DisabledStatus.ENABLED.name(), DisabledStatus.ENABLED.name());
/*    */     
/* 27 */     request.setAttribute("AUTH_METHOD_FIDO_ENABLED", Boolean.valueOf(SystemSettingsDao.getSettingEnabled("advanced.fidoEnabled").toBoolean()));
/* 28 */     request.setAttribute("AUTH_METHOD_BIOTP_ENABLED", Boolean.valueOf(SystemSettingsDao.getSettingEnabled("advanced.biotpEnabled").toBoolean()));
/* 29 */     request.setAttribute("AUTH_METHOD_SAOTP_ENABLED", Boolean.valueOf(SystemSettingsDao.getSettingEnabled("advanced.saotpEnabled").toBoolean()));
/* 30 */     request.setAttribute("WEB_API_POLICY", SystemSettingsDao.getWebApiPolicy().name());
/*    */ 
/*    */     
/* 33 */     int sessionTimeout = SystemSettingsDao.getInt("application.sessionTimeout");
/* 34 */     request.setAttribute("SESSION_TIMEOUT", Integer.valueOf(sessionTimeout * 60000));
/*    */ 
/*    */     
/* 37 */     super.postHandle(request, response, handler, mav);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\interceptor\CommonDataInterceptor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */