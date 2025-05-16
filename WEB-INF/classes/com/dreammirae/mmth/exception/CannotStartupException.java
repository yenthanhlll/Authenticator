/*    */ package WEB-INF.classes.com.dreammirae.mmth.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CannotStartupException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public CannotStartupException(String msg) {
/* 14 */     super(msg);
/*    */   }
/*    */   
/*    */   public CannotStartupException(Throwable e) {
/* 18 */     super(e);
/*    */   }
/*    */   
/*    */   public CannotStartupException(String msg, Throwable e) {
/* 22 */     super(msg, e);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\exception\CannotStartupException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */