/*    */ package WEB-INF.classes.com.dreammirae.mmth.exception;
/*    */ 
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ 
/*    */ public class ReturnCodeException
/*    */   extends RuntimeException {
/*    */   private final ReturnCodes returnCode;
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ReturnCodeException(ReturnCodes returnCode) {
/* 13 */     this.returnCode = returnCode;
/*    */   }
/*    */   
/*    */   public ReturnCodeException(ReturnCodes returnCode, String message, Throwable cause) {
/* 17 */     super(message, cause);
/* 18 */     this.returnCode = returnCode;
/*    */   }
/*    */   
/*    */   public ReturnCodeException(ReturnCodes returnCode, String message) {
/* 22 */     super(message);
/* 23 */     this.returnCode = returnCode;
/*    */   }
/*    */   
/*    */   public ReturnCodeException(ReturnCodes returnCode, I18nMessage message, Throwable cause) {
/* 27 */     super(Commons.getSysMessage(message), cause);
/* 28 */     this.returnCode = returnCode;
/*    */   }
/*    */   
/*    */   public ReturnCodeException(ReturnCodes returnCode, I18nMessage message) {
/* 32 */     super(Commons.getSysMessage(message));
/* 33 */     this.returnCode = returnCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReturnCodeException(ReturnCodes returnCode, Throwable cause) {
/* 38 */     super(cause);
/* 39 */     this.returnCode = returnCode;
/*    */   }
/*    */   
/*    */   public ReturnCodes getReturnCode() {
/* 43 */     return this.returnCode;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\exception\ReturnCodeException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */