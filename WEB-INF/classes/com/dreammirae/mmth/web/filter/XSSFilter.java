/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.filter;
/*    */ 
/*    */ import com.dreammirae.mmth.util.io.XSSFilterUtils;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XSSFilter
/*    */   implements Filter
/*    */ {
/*    */   public void init(FilterConfig filterConfig) throws ServletException {}
/*    */   
/*    */   public void destroy() {}
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/*    */     try {
/* 34 */       XSSFilterUtils.filterOutXss((HttpServletRequest)request);
/* 35 */     } catch (IllegalArgumentException e) {
/* 36 */       ((HttpServletResponse)response).sendError(400, "The request included XSS script is invalid. ");
/*    */       
/*    */       return;
/*    */     } 
/* 40 */     chain.doFilter(request, response);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\filter\XSSFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */