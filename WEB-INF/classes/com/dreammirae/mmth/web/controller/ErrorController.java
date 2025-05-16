/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.controller;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.web.controller.GlobalExceptionHandler;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.http.HttpStatus;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.bind.annotation.ResponseBody;
/*    */ import org.springframework.web.bind.annotation.ResponseStatus;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ public class ErrorController
/*    */ {
/* 21 */   private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
/*    */   
/*    */   protected static final String ERR_RETURN_FORMAT = "{\"statusCode\" : %d, \"returnCode\" : \"%s\" }";
/*    */   
/*    */   @RequestMapping(value = {"/errors"}, method = {RequestMethod.GET})
/*    */   public ModelAndView renderErrorPage(HttpServletRequest httpRequest, HttpServletResponse response) {
/* 27 */     LOG.error("?? Error occured while requesting URI = " + httpRequest.getRequestURI() + ", Status=" + response.getStatus());
/* 28 */     ModelAndView errorPage = new ModelAndView("common/error");
/* 29 */     errorPage.setStatus(HttpStatus.valueOf(response.getStatus()));
/* 30 */     response.setStatus(response.getStatus());
/* 31 */     errorPage.addObject("ERROR_CODE", Integer.valueOf(response.getStatus()));
/* 32 */     return errorPage;
/*    */   }
/*    */   @RequestMapping(value = {"/errors"}, method = {RequestMethod.POST})
/*    */   @ResponseBody
/*    */   public String renderErrorContents(HttpServletRequest httpRequest, HttpServletResponse response) {
/* 37 */     LOG.error("?? Error occured while requesting URI = " + httpRequest.getRequestURI() + ", Status=" + response.getStatus());
/* 38 */     return String.format("{\"statusCode\" : %d, \"returnCode\" : \"%s\" }", new Object[] { ReturnCodes.INTERNAL_SERVER_ERROR.getStatusCodes().getId(), ReturnCodes.INTERNAL_SERVER_ERROR.getCode() });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @ResponseStatus(code = HttpStatus.NOT_FOUND)
/*    */   public ModelAndView handleStatusCode(HttpServletRequest httpRequest, HttpServletResponse response) {
/* 45 */     return renderErrorPage(httpRequest, response);
/*    */   }
/*    */   
/*    */   @ResponseStatus(code = HttpStatus.FORBIDDEN)
/*    */   public ModelAndView handleForbiddenCode(HttpServletRequest httpRequest, HttpServletResponse response) {
/* 50 */     return renderErrorPage(httpRequest, response);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\ErrorController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */