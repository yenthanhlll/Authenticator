/*    */ package WEB-INF.classes.com.dreammirae.mmth.rp.filter;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.policy.FidoPayloadParser;
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.annotation.WebFilter;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.web.bind.ServletRequestBindingException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebFilter(filterName = "FidoFilter", urlPatterns = {"/rpserver/httpapi/FIDO/*"})
/*    */ public class FidoAuthenticationFilter
/*    */   implements Filter
/*    */ {
/*    */   private static final String URI_FIDO_ANY_PASS_EXTENAL_API = "^/rpserver/httpapi/FIDO/(CheckUser|UserCancel|NotifyDereg)$";
/*    */   private static final String KEY_ACCEPT = "Accept";
/*    */   
/*    */   public void init(FilterConfig filterConfig) throws ServletException {}
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/* 35 */     HttpServletRequest httpRequest = (HttpServletRequest)request;
/*    */     
/* 37 */     String reqUri = httpRequest.getRequestURI();
/*    */     
/* 39 */     if (reqUri.matches("^/rpserver/httpapi/FIDO/(CheckUser|UserCancel|NotifyDereg)$"))
/*    */     {
/* 41 */       if (!ExternalWebApiTypes.GPTWR.equals(SystemSettingsDao.getWebApiPolicy())) {
/* 42 */         throw new ServletRequestBindingException("The request is available only AnyPASS.");
/*    */       }
/*    */     }
/*    */     
/* 46 */     FidoPayloadParser parser = SystemSettingsDao.getFidoPayloadParser();
/*    */ 
/*    */ 
/*    */     
/* 50 */     if (!parser.getContentType().equalsIgnoreCase(httpRequest.getContentType()) || 
/* 51 */       !parser.getContentType().equalsIgnoreCase(httpRequest.getHeader("Accept"))) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     chain.doFilter(request, response);
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\filter\FidoAuthenticationFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */