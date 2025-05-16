/*    */ package WEB-INF.classes.com.dreammirae.mmth.authentication.service.supporter;
/*    */ 
/*    */ import com.dreammirae.mmth.fido.Operation;
/*    */ import com.dreammirae.mmth.fido.exception.FidoUafStatusCodeException;
/*    */ import com.dreammirae.mmth.fido.handler.UAFResponseHandler;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.RespMessageCallback;
/*    */ import com.dreammirae.mmth.fido.handler.supporter.RespMessageSupporter;
/*    */ import com.dreammirae.mmth.fido.transport.SendUAFResponse;
/*    */ import com.dreammirae.mmth.vo.FidoRegistrationVO;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FidoResponseMessageValidator
/*    */ {
/*    */   public void validateResponseMessage(Operation op, RespMessageSupporter<FidoRegistrationVO> supporter, RespMessageCallback<FidoRegistrationVO> callback, SendUAFResponse sendUAFResponse) throws FidoUafStatusCodeException {
/* 18 */     UAFResponseHandler<?, FidoRegistrationVO> handler = UAFResponseHandler.createUAFResponseHandler(op, supporter, callback, sendUAFResponse.getContext());
/*    */ 
/*    */     
/* 21 */     handler.parseUafProtocolMessage(sendUAFResponse.getUafResponse());
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\authentication\service\supporter\FidoResponseMessageValidator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */