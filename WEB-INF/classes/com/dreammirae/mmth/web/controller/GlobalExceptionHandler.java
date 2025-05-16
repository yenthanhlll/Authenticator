/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.controller;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.http.HttpEntity;
/*    */ import org.springframework.http.HttpMethod;
/*    */ import org.springframework.http.HttpStatus;
/*    */ import org.springframework.http.ResponseEntity;
/*    */ import org.springframework.web.bind.annotation.ControllerAdvice;
/*    */ import org.springframework.web.bind.annotation.ExceptionHandler;
/*    */ import org.springframework.web.bind.annotation.ResponseStatus;
/*    */ 
/*    */ 
/*    */ @ControllerAdvice
/*    */ public class GlobalExceptionHandler
/*    */ {
/* 23 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.web.controller.GlobalExceptionHandler.class);
/*    */   
/*    */   protected static final String ERR_RETURN_FORMAT = "{\"statusCode\" : %d, \"returnCode\" : \"%s\" }";
/*    */   
/*    */   protected static final String PREFIX_URI = "^/rpserver/*.*$";
/*    */   
/*    */   @ExceptionHandler({Throwable.class})
/*    */   @ResponseStatus(code = HttpStatus.NOT_FOUND)
/*    */   public HttpEntity<?> handleException(HttpServletRequest req, HttpServletResponse resp, Throwable ex) {
/* 32 */     LOG.error("?? Error occured while requesting URI = " + req.getRequestURI() + ", Exception=" + ex
/* 33 */         .getClass().getName(), ex);
/*    */ 
/*    */ 
/*    */     
/* 37 */     if (req.getRequestURI().matches("^/rpserver/*.*$")) {
/* 38 */       return (HttpEntity<?>)ResponseEntity.status(HttpStatus.OK).body(String.format("{\"statusCode\" : %d, \"returnCode\" : \"%s\" }", new Object[] { ReturnCodes.BAD_REQUEST.getStatusCodes().getId(), ReturnCodes.BAD_REQUEST.getCode() }));
/*    */     }
/*    */ 
/*    */     
/* 42 */     if (HttpMethod.POST.name().equalsIgnoreCase(req.getMethod())) {
/* 43 */       return (HttpEntity<?>)ResponseEntity.status(HttpStatus.OK).body(String.format("{\"statusCode\" : %d, \"returnCode\" : \"%s\" }", new Object[] { ReturnCodes.BAD_REQUEST.getStatusCodes().getId(), ReturnCodes.BAD_REQUEST.getCode() }));
/*    */     }
/*    */     
/* 46 */     redirectErrors(req, resp);
/* 47 */     return HttpEntity.EMPTY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void redirectErrors(HttpServletRequest req, HttpServletResponse resp) {
/*    */     try {
/* 54 */       req.getRequestDispatcher("/errors").forward((ServletRequest)req, (ServletResponse)resp);
/* 55 */     } catch (Exception e1) {
/*    */       try {
/* 57 */         resp.sendRedirect("/errors");
/* 58 */       } catch (IOException ignore) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\GlobalExceptionHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */