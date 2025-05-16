/*    */ package WEB-INF.classes.com.dreammirae.mmth.external.filter;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.external.ExternalWebApiTypes;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.dreammirae.mmth.util.notary.TransportNotary;
/*    */ import java.io.IOException;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.annotation.WebFilter;
/*    */ import javax.servlet.annotation.WebInitParam;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.web.bind.ServletRequestBindingException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebFilter(filterName = "ExternalAPIFilter", urlPatterns = {"/extapi/fido/v1/*", "/rpserver/webapi/*"}, initParams = {@WebInitParam(name = "apiKeyName", value = "X-GPTWR-FIDO-APIKEY"), @WebInitParam(name = "sharedKey", value = "0C1AEB0DCD1D011BCE940BCF2C9778144D031")})
/*    */ public class ExternalAPIFilter
/*    */   implements Filter
/*    */ {
/* 29 */   private static final Pattern GPTWR_FIDO_REGEX = Pattern.compile("^/extapi/fido/v1/.*$");
/* 30 */   private static final Pattern RP_WEB_API_REGEX = Pattern.compile("^/rpserver/webapi/.*$");
/*    */   
/*    */   private String apiKeyName;
/*    */   
/*    */   private String defaultSharedKey;
/*    */   
/*    */   public void init(FilterConfig filterConfig) throws ServletException {
/* 37 */     this.apiKeyName = filterConfig.getInitParameter("apiKeyName");
/* 38 */     this.defaultSharedKey = filterConfig.getInitParameter("sharedKey");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/* 46 */     HttpServletRequest httpReq = (HttpServletRequest)request;
/*    */     
/* 48 */     String uri = httpReq.getRequestURI();
/*    */     
/* 50 */     if (GPTWR_FIDO_REGEX.matcher(uri).matches()) {
/*    */       
/* 52 */       if (!ExternalWebApiTypes.GPTWR.equals(SystemSettingsDao.getWebApiPolicy())) {
/* 53 */         throw new ServletRequestBindingException("The request is available only GPTWR.");
/*    */       }
/*    */       
/* 56 */       String headerValue = httpReq.getHeader(this.apiKeyName);
/*    */       
/* 58 */       if (!StringUtils.isEmpty(headerValue)) {
/*    */         
/* 60 */         String sharedKey = SystemSettingsDao.getValue("externalApi.serviceSharedKey");
/*    */         
/* 62 */         if (StringUtils.isEmpty(sharedKey)) {
/* 63 */           sharedKey = this.defaultSharedKey;
/*    */         }
/*    */         
/* 66 */         if (TransportNotary.verifyNotaryData(sharedKey, headerValue)) {
/* 67 */           chain.doFilter(request, response);
/*    */           
/*    */           return;
/*    */         } 
/*    */       } 
/* 72 */       HttpServletResponse httpResp = (HttpServletResponse)response;
/* 73 */       httpResp.getWriter().append("{ \"result\" : \"6100\"}");
/*    */     }
/* 75 */     else if (RP_WEB_API_REGEX.matcher(uri).matches()) {
/*    */       
/* 77 */       if (ExternalWebApiTypes.NONE.equals(SystemSettingsDao.getWebApiPolicy())) {
/* 78 */         throw new ServletRequestBindingException("The request is not available.");
/*    */       }
/*    */     } 
/*    */     
/* 82 */     chain.doFilter(request, response);
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\filter\ExternalAPIFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */