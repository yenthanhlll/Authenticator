/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.filter;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.dreammirae.mmth.util.notary.TransportNotary;
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
/*    */ @WebFilter(filterName = "HttpEventReceiverFilter", urlPatterns = {"/mmth/service/httper"})
/*    */ public class HttpEventReceiverFilter
/*    */   implements Filter
/*    */ {
/*    */   private String serviceApiKey;
/*    */   
/*    */   public void init(FilterConfig filteOrConfig) throws ServletException {
/* 29 */     this.serviceApiKey = SysEnvCommon.getPublishServiceApiKey();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/* 36 */     HttpServletResponse httpResp = (HttpServletResponse)response;
/* 37 */     HttpServletRequest httpReq = (HttpServletRequest)request;
/*    */     
/* 39 */     if (SystemSettingsDao.getSettingEnabled("advanced.publisherEnabled").toBoolean()) {
/*    */       
/* 41 */       String headerValue = httpReq.getHeader(SysEnvCommon.getPublishHttpApiHeaderKey());
/*    */       
/* 43 */       if (StringUtils.isEmpty(headerValue)) {
/* 44 */         httpResp.setStatus(406);
/* 45 */         httpReq.getRequestDispatcher("/errors").forward((ServletRequest)httpReq, (ServletResponse)httpResp);
/*    */         
/*    */         return;
/*    */       } 
/* 49 */       if (!TransportNotary.verifyNotaryData(this.serviceApiKey, headerValue)) {
/* 50 */         httpResp.setStatus(406);
/* 51 */         httpReq.getRequestDispatcher("/errors").forward((ServletRequest)httpReq, (ServletResponse)httpResp);
/*    */         
/*    */         return;
/*    */       } 
/*    */     } else {
/* 56 */       httpResp.setStatus(400);
/* 57 */       httpReq.getRequestDispatcher("/errors").forward((ServletRequest)httpReq, (ServletResponse)httpResp);
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     chain.doFilter(request, response);
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\filter\HttpEventReceiverFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */