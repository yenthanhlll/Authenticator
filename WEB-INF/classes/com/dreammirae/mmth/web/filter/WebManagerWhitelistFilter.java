/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.filter;
/*    */ 
/*    */ import com.dreammirae.mmth.db.dao.SystemSettingsDao;
/*    */ import com.dreammirae.mmth.util.IPv4AddressUtils;
/*    */ import java.io.IOException;
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
/*    */ import org.apache.commons.codec.binary.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebFilter(filterName = "WebManagerWhitelistFilter", urlPatterns = {"/web/manager/*"}, initParams = {@WebInitParam(name = "whitelist", value = "*.*.*.*")})
/*    */ public class WebManagerWhitelistFilter
/*    */   implements Filter
/*    */ {
/*    */   private String whitelist;
/*    */   private String[] myIpAll;
/*    */   
/*    */   public void init(FilterConfig filterConfig) throws ServletException {
/* 31 */     this.whitelist = filterConfig.getInitParameter("whitelist");
/* 32 */     this.myIpAll = IPv4AddressUtils.myIpWithLoopback();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/* 38 */     String remoteIp = request.getRemoteAddr();
/* 39 */     boolean allowed = false;
/*    */     
/* 41 */     HttpServletRequest httpRequest = (HttpServletRequest)request;
/* 42 */     String uri = httpRequest.getRequestURI();
/* 43 */     if (StringUtils.equals("/web/manager/branch", uri)) {
/* 44 */       allowed = true;
/*    */     }
/*    */     else {
/*    */       
/* 48 */       if (remoteIp.equalsIgnoreCase(request.getLocalAddr())) {
/* 49 */         allowed = true;
/*    */       } else {
/*    */         
/* 52 */         for (String mine : this.myIpAll) {
/* 53 */           if (remoteIp.equalsIgnoreCase(mine)) {
/* 54 */             allowed = true;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/* 59 */         if (!allowed) {
/* 60 */           String ipWl = SystemSettingsDao.getValue("application.ipWhitelist", this.whitelist);
/*    */           
/* 62 */           String[] allowedIps = ipWl.split(",");
/*    */           
/* 64 */           for (String allowedIp : allowedIps) {
/* 65 */             allowedIp = allowedIp.trim();
/*    */             
/* 67 */             if (IPv4AddressUtils.ipWhiteListCheck(allowedIp, remoteIp)) {
/* 68 */               allowed = true;
/*    */               
/*    */               break;
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 76 */       if (!allowed) {
/* 77 */         ((HttpServletResponse)response).sendError(403, "Ip address is blocked. your address = " + remoteIp);
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/*    */     
/* 83 */     chain.doFilter(request, response);
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\filter\WebManagerWhitelistFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */