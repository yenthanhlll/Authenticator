/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.controller;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.runtime.code.SyncCacheTypes;
/*    */ import com.dreammirae.mmth.runtime.publish.SyncCachePublisher;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping(value = {"/mmth/service"}, consumes = {"application/json; charset=utf-8"}, produces = {"application/json; charset=utf-8"})
/*    */ public class HttpEventReceiverController
/*    */ {
/* 24 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.web.controller.HttpEventReceiverController.class);
/*    */ 
/*    */   
/*    */   @RequestMapping({"/httper"})
/*    */   public void httpEventLister(HttpServletRequest req, HttpServletResponse resp) {
/* 29 */     LOG.info("Receive HTTP Event Data from = " + req.getRemoteAddr());
/*    */     
/* 31 */     String code = req.getParameter("eventDataType");
/* 32 */     SyncCacheTypes type = SyncCacheTypes.getEventDataTypes(code);
/*    */     
/* 34 */     if (type == null) {
/*    */       try {
/* 36 */         resp.sendError(400);
/* 37 */         LOG.error("Failed to Receive HTTP Event Data from = " + req.getRemoteAddr() + ", cause entired invalid EventDataTypes.");
/* 38 */       } catch (IOException e) {}
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 43 */     SyncCachePublisher manager = Commons.ctx.getSyncCachePublisher();
/*    */     
/* 45 */     if (manager != null)
/* 46 */       manager.notifyReceived(type); 
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\controller\HttpEventReceiverController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */