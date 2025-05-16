/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.DispatcherType;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.annotation.WebFilter;
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
/*    */ 
/*    */ @WebFilter(filterName = "HeaderFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.FORWARD, DispatcherType.ASYNC, DispatcherType.INCLUDE})
/*    */ public class HeaderFilter
/*    */   implements Filter
/*    */ {
/*    */   public void init(FilterConfig filterConfig) throws ServletException {}
/*    */   
/*    */   public void destroy() {}
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/* 32 */     HttpServletResponse httpResp = (HttpServletResponse)response;
/*    */     
/* 34 */     httpResp.setHeader("Server", "BIOTP");
/* 35 */     httpResp.setHeader("X-Frame-Options", "DENY");
/* 36 */     httpResp.setHeader("X-Content-Type-Options", "nosniff");
/* 37 */     httpResp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
/* 38 */     httpResp.setHeader("Pragma", "no-cache");
/* 39 */     httpResp.setHeader("X-XSS-Protection", "1; mode=block");
/*    */     
/* 41 */     httpResp.setDateHeader("Expires", 0L);
/*    */     
/* 43 */     chain.doFilter(request, response);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\filter\HeaderFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */