/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.fido;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.authentication.service.IExteranlReqInterworker;
/*    */ import com.dreammirae.mmth.db.dao.ExternalServiceItemDao;
/*    */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*    */ import com.dreammirae.mmth.external.bean.ExtRequestStatus;
/*    */ import com.dreammirae.mmth.fido.transport.context.RpContext;
/*    */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*    */ import com.dreammirae.mmth.vo.UserVO;
/*    */ 
/*    */ 
/*    */ public class FidoReqInterworker
/*    */   implements IExteranlReqInterworker
/*    */ {
/*    */   public void reqAuthInterworker(UserVO user, RpContext rpContext) {
/* 17 */     ExternalServiceItemDao dao = new ExternalServiceItemDao();
/* 18 */     ExternalServiceItemVO vo = dao.getOneByUser(user);
/*    */     
/* 20 */     if (vo == null) {
/* 21 */       throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, "There has no auth request... with userName = " + user.getUsername());
/*    */     }
/*    */     
/* 24 */     if (vo.getTsExpired() < System.currentTimeMillis()) {
/* 25 */       throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, "There has no auth request... with userName = " + user.getUsername());
/*    */     }
/*    */     
/* 28 */     if (!ExtRequestStatus.REQ.equals(vo.getStatus())) {
/* 29 */       throw new ReturnCodeException(ReturnCodes.NO_AUTHENTICATION_REQUEST, "There has no auth request... with userName = " + user.getUsername());
/*    */     }
/*    */     
/* 32 */     rpContext.set("tid", vo.getTransactionId());
/* 33 */     rpContext.set("externalRequestExpired", Long.valueOf(vo.getTsExpired()));
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\fido\FidoReqInterworker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */