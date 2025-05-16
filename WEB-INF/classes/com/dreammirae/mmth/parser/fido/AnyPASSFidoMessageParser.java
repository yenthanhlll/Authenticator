/*    */ package WEB-INF.classes.com.dreammirae.mmth.parser.fido;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.authentication.bean.AnyPASSRequestLocator;
/*    */ import com.dreammirae.mmth.authentication.bean.AnyPASSResponseLocator;
/*    */ import com.dreammirae.mmth.authentication.bean.AuthenticationRequestLocator;
/*    */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*    */ import com.dreammirae.mmth.exception.ReturnCodeException;
/*    */ import com.dreammirae.mmth.parser.fido.BasicFidoMessageParser;
/*    */ import com.dreammirae.mmth.parser.json.GsonUtils;
/*    */ import com.dreammirae.mmth.vo.UserVO;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ 
/*    */ public class AnyPASSFidoMessageParser
/*    */   extends BasicFidoMessageParser
/*    */ {
/*    */   public AuthenticationRequestLocator parseGenRpIssueCodeReqMessage(String payload, HttpServletRequest request) throws ReturnCodeException {
/*    */     try {
/* 20 */       AnyPASSRequestLocator params = (AnyPASSRequestLocator)GsonUtils.gson().fromJson(payload, AnyPASSRequestLocator.class);
/*    */       
/* 22 */       if (!params.hasUsername()) {
/* 23 */         throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, "The field['userName'] is required.");
/*    */       }
/*    */ 
/*    */       
/* 27 */       UserVO.validateUsername(params.getUsername());
/*    */       
/* 29 */       return (AuthenticationRequestLocator)params;
/* 30 */     } catch (ReturnCodeException e) {
/* 31 */       throw e;
/* 32 */     } catch (Exception e) {
/* 33 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String generateGenRpIssueCodeRespMessage(AuthenticationResponseLocator result, HttpServletRequest request) throws ReturnCodeException {
/* 42 */     if (result == null) {
/* 43 */       result = new AuthenticationResponseLocator();
/* 44 */       result.setReturnCode(ReturnCodes.INTERNAL_SERVER_ERROR);
/*    */     } 
/*    */     
/* 47 */     return GsonUtils.gson().toJson(result);
/*    */   }
/*    */ 
/*    */   
/*    */   public AnyPASSRequestLocator parseRequestLocator(String payload, HttpServletRequest request) throws ReturnCodeException {
/*    */     try {
/* 53 */       AnyPASSRequestLocator params = (AnyPASSRequestLocator)GsonUtils.gson().fromJson(payload, AnyPASSRequestLocator.class);
/* 54 */       return params;
/* 55 */     } catch (ReturnCodeException e) {
/* 56 */       throw e;
/* 57 */     } catch (Exception e) {
/* 58 */       throw new ReturnCodeException(ReturnCodes.INVALID_PARAMS, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public AuthenticationResponseLocator createResponseLocatorInstance() {
/* 64 */     return (AuthenticationResponseLocator)new AnyPASSResponseLocator();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\fido\AnyPASSFidoMessageParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */