/*    */ package WEB-INF.classes.com.dreammirae.mmth;
/*    */ 
/*    */ import com.dreammirae.mmth.config.MmthWebAppConfig;
/*    */ import javax.servlet.Servlet;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRegistration;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.web.WebApplicationInitializer;
/*    */ import org.springframework.web.context.WebApplicationContext;
/*    */ import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
/*    */ import org.springframework.web.servlet.DispatcherServlet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MmthAppInitializer
/*    */   implements WebApplicationInitializer
/*    */ {
/* 26 */   protected static final Logger LOGGER = LoggerFactory.getLogger(com.dreammirae.mmth.MmthAppInitializer.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public void onStartup(ServletContext sc) throws ServletException {
/* 31 */     registerDispatcherServlet(sc);
/*    */     
/* 33 */     StringBuilder sb = new StringBuilder(512);
/*    */     
/* 35 */     sb.append("\r\n");
/* 36 */     sb.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
/* 37 */     sb.append("                                  _|      _|         \r\n");
/* 38 */     sb.append("_|_|_|  _|_|    _|_|_|  _|_|    _|_|_|_|  _|_|_|     \r\n");
/* 39 */     sb.append("_|    _|    _|  _|    _|    _|    _|      _|    _|   \r\n");
/* 40 */     sb.append("_|    _|    _|  _|    _|    _|    _|      _|    _|   \r\n");
/* 41 */     sb.append("_|    _|    _|  _|    _|    _|      _|_|  _|    _|   \r\n");
/* 42 */     sb.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
/* 43 */     sb.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
/*    */     
/* 45 */     LOGGER.warn(sb.toString());
/* 46 */     sb.setLength(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void registerDispatcherServlet(ServletContext servletContext) {
/* 53 */     AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
/*    */ 
/*    */     
/* 56 */     ctx.register(new Class[] { MmthWebAppConfig.class });
/* 57 */     ctx.setServletContext(servletContext);
/*    */ 
/*    */     
/* 60 */     DispatcherServlet ds = new DispatcherServlet((WebApplicationContext)ctx);
/*    */     
/* 62 */     ds.setThrowExceptionIfNoHandlerFound(true);
/*    */     
/* 64 */     ServletRegistration.Dynamic dynamic = servletContext.addServlet("mmth", (Servlet)ds);
/*    */     
/* 66 */     dynamic.addMapping(new String[] { "/" });
/* 67 */     dynamic.setLoadOnStartup(1);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\MmthAppInitializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */