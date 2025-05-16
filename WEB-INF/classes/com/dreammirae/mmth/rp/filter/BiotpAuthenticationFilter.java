/*    */ package WEB-INF.classes.com.dreammirae.mmth.rp.filter;
/*    */ 
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
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebFilter(filterName = "BiotpFilter", urlPatterns = {"/rpserver/httpapi/BIOTP/*"})
/*    */ public class BiotpAuthenticationFilter
/*    */   implements Filter
/*    */ {
/*    */   private static final String URI_NOT_FIDO_COMMUNICATIONS = "^/rpserver/httpapi/BIOTP/(IssueCode|VerifyOTP|VerifyEncOTP)$";
/*    */   
/*    */   public void init(FilterConfig filterConfig) throws ServletException {}
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/* 33 */     HttpServletRequest httpRequest = (HttpServletRequest)request;
/*    */     
/* 35 */     String reqUri = httpRequest.getRequestURI();
/*    */     
/* 37 */     if (reqUri.matches("^/rpserver/httpapi/BIOTP/(IssueCode|VerifyOTP|VerifyEncOTP)$") && 
/* 38 */       ExternalWebApiTypes.GLOBAL_WIBEE.equals(SystemSettingsDao.getWebApiPolicy())) {
/*    */       
/* 40 */       HttpServletResponse httpResp = (HttpServletResponse)response;
/* 41 */       httpResp.getWriter().append("{ \"returnCode\" : \"1400\", \"statusCode\" : 1400 }");
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 46 */     chain.doFilter(request, response);
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\filter\BiotpAuthenticationFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */