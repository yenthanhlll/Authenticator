/*    */ package WEB-INF.classes.com.dreammirae.mmth.rp.filter;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import java.io.IOException;
/*    */ import java.util.regex.Pattern;
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
/*    */ @WebFilter(filterName = "KiwoomBiotpFilter", urlPatterns = {"/rpserver/kiwoom/BIOTP/*"})
/*    */ public class KiwoomBiotpAuthenticationFilter
/*    */   implements Filter
/*    */ {
/*    */   private static final String URI_COMMUNICATIONS = "^/rpserver/kiwoom/BIOTP/(1001|1002|2001|2002|3001|3002|4001|4002|5001|5002|5003|9001|9002|9003|9004|9005)$";
/* 23 */   private static final Pattern URI_PATTERN = Pattern.compile("^/rpserver/kiwoom/BIOTP/(1001|1002|2001|2002|3001|3002|4001|4002|5001|5002|5003|9001|9002|9003|9004|9005)$");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(FilterConfig filterConfig) throws ServletException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/* 33 */     HttpServletRequest httpReq = (HttpServletRequest)request;
/* 34 */     HttpServletResponse httpResp = (HttpServletResponse)response;
/*    */     
/* 36 */     httpResp.setCharacterEncoding("MS949");
/* 37 */     httpReq.setCharacterEncoding("MS949");
/*    */     
/* 39 */     String reqUri = httpReq.getRequestURI();
/*    */     
/* 41 */     if (!URI_PATTERN.matcher(reqUri).matches()) {
/* 42 */       httpResp.getWriter().write(ReturnCodes.NOT_FOUND_URL.getCode());
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     chain.doFilter(request, response);
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\rp\filter\KiwoomBiotpAuthenticationFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */