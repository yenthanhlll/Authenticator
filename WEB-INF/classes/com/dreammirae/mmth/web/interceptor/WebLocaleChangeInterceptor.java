/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.interceptor;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.db.dao.AdministratorDao;
/*     */ import com.dreammirae.mmth.util.StringUtils;
/*     */ import com.dreammirae.mmth.util.bean.KeyValuePair;
/*     */ import com.dreammirae.mmth.util.io.SerializationUtils;
/*     */ import com.dreammirae.mmth.vo.AdministratorVO;
/*     */ import java.util.Locale;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.web.servlet.LocaleResolver;
/*     */ import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
/*     */ import org.springframework.web.servlet.support.RequestContextUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebLocaleChangeInterceptor
/*     */   extends LocaleChangeInterceptor
/*     */ {
/*     */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
/*     */     HttpSession session;
/*  35 */     if ((session = request.getSession()) == null) {
/*  36 */       return super.preHandle(request, response, handler);
/*     */     }
/*     */     
/*  39 */     AdministratorVO curAdmin = getSessionAdmin(session);
/*     */     
/*  41 */     if (curAdmin == null || StringUtils.isEmpty(curAdmin.getUsername())) {
/*  42 */       return super.preHandle(request, response, handler);
/*     */     }
/*     */     
/*  45 */     Locale locale = RequestContextUtils.getLocale(request);
/*     */     
/*  47 */     String str = request.getParameter(getParamName());
/*     */     
/*  49 */     if (!validateLocale(str)) {
/*     */       
/*  51 */       if (!locale.getLanguage().equals(curAdmin.getLocale().getLanguage())) {
/*  52 */         setLocale(request, response, curAdmin.getLocale());
/*     */       }
/*     */       
/*  55 */       return true;
/*     */     } 
/*     */     
/*  58 */     Locale changed = parseLocaleValue(str);
/*     */     
/*  60 */     if (!changed.getLanguage().equals(curAdmin.getLocale().getLanguage())) {
/*     */       
/*     */       try {
/*  63 */         AdministratorDao dao = new AdministratorDao();
/*  64 */         AdministratorVO vo = dao.getOneByObj(curAdmin);
/*  65 */         vo.setLocale(changed);
/*  66 */         dao.save(vo);
/*  67 */         vo.setPassword(null);
/*  68 */         session.setAttribute("MMTH.ADMIN", SerializationUtils.serializeObject(vo));
/*  69 */         session.setAttribute("MMTH.ADMIN.UPDATE", Commons.displayDateTime(vo.getTsUpdated()));
/*  70 */       } catch (Exception e) {
/*  71 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  75 */     return super.preHandle(request, response, handler);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validateLocale(String str) {
/*  80 */     if (StringUtils.isEmpty(str)) {
/*  81 */       return false;
/*     */     }
/*     */     
/*  84 */     for (KeyValuePair<String, String> pair : (Iterable<KeyValuePair<String, String>>)Commons.getSupportedLangs()) {
/*  85 */       if (((String)pair.getKey()).equalsIgnoreCase(str)) {
/*  86 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setLocale(HttpServletRequest request, HttpServletResponse response, Locale loc) {
/*  95 */     LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
/*  96 */     if (localeResolver != null) {
/*     */       try {
/*  98 */         localeResolver.setLocale(request, response, loc);
/*  99 */       } catch (IllegalArgumentException e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AdministratorVO getSessionAdmin(HttpSession session) {
/* 106 */     Object obj = session.getAttribute("MMTH.ADMIN");
/*     */     
/* 108 */     if (!(obj instanceof byte[])) {
/* 109 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 113 */       AdministratorVO admin = (AdministratorVO)SerializationUtils.deserializeObject((byte[])obj);
/* 114 */       return admin;
/* 115 */     } catch (ClassNotFoundException|java.io.IOException e) {
/* 116 */       e.printStackTrace();
/*     */ 
/*     */       
/* 119 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\interceptor\WebLocaleChangeInterceptor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */