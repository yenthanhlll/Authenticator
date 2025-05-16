/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.supporter;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.bean.UserServiceLocator;
/*    */ import com.dreammirae.mmth.fido.Operation;
/*    */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*    */ import com.dreammirae.mmth.fido.handler.UAFRequestHandler;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.ReqMessageCallback;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.ReqMessageSupporter;
/*    */ import com.dreammirae.mmth.fido.transport.GetUAFRequest;
/*    */ import com.dreammirae.mmth.fido.transport.UAFMessage;
/*    */ import com.dreammirae.mmth.fido.transport.context.RpContext;
/*    */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*    */ import com.dreammirae.mmth.vo.UserVO;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ public class FidoRequestMessageGenerator
/*    */ {
/* 20 */   private static final Logger LOG = LoggerFactory.getLogger(com.dreammirae.mmth.authentication.service.supporter.FidoRequestMessageGenerator.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public UAFMessage generateRequestMessage(ReqMessageSupporter<FidoRegistrationVO> supporter, ReqMessageCallback<FidoRegistrationVO> callback, GetUAFRequest getUAFRequest, UserServiceLocator serviceLocator) throws FidoUafStatusCodeException {
/* 25 */     Operation op = getUAFRequest.getOp();
/* 26 */     RpContext contextLoc = getUAFRequest.getContext();
/* 27 */     UserVO user = serviceLocator.getUser();
/*    */ 
/*    */     
/* 30 */     UAFRequestHandler<FidoRegistrationVO> handler = UAFRequestHandler.createRequestHandler(op, supporter, callback, contextLoc);
/*    */ 
/*    */ 
/*    */     
/* 34 */     String uafProtocolMsg = handler.generateUafProtocolMessage(user.getUsername());
/*    */     
/* 36 */     if (LOG.isDebugEnabled()) {
/* 37 */       if (Operation.Reg.equals(op)) {
/* 38 */         LOG.debug("\t\t[UAFProtocolMessage]#RegReq:" + uafProtocolMsg);
/* 39 */       } else if (Operation.Auth.equals(op)) {
/* 40 */         LOG.debug("\t\t[UAFProtocolMessage]#AuthReq:" + uafProtocolMsg);
/*    */       } else {
/* 42 */         LOG.debug("\t\t[UAFProtocolMessage]#DeregReq:" + uafProtocolMsg);
/*    */       } 
/*    */     }
/*    */     
/* 46 */     return new UAFMessage(uafProtocolMsg);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\supporter\FidoRequestMessageGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */