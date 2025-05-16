/*    */ package WEB-INF.classes.com.dreammirae.mmth.web.exception;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class I18nMessageException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final String contextKey;
/*    */   private final I18nMessage i18nMessage;
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public I18nMessageException(I18nMessage i18nMessage) {
/* 18 */     this.contextKey = null;
/* 19 */     this.i18nMessage = i18nMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public I18nMessageException(String contextKey, I18nMessage i18nMessage) {
/* 24 */     this.contextKey = contextKey;
/* 25 */     this.i18nMessage = i18nMessage;
/*    */   }
/*    */   
/*    */   public I18nMessageException(I18nMessage i18nMessage, Throwable cause) {
/* 29 */     super(cause);
/* 30 */     this.contextKey = null;
/* 31 */     this.i18nMessage = i18nMessage;
/*    */   }
/*    */   
/*    */   public I18nMessageException(String contextKey, I18nMessage i18nMessage, Throwable cause) {
/* 35 */     super(cause);
/* 36 */     this.contextKey = contextKey;
/* 37 */     this.i18nMessage = i18nMessage;
/*    */   }
/*    */   
/*    */   public I18nMessage getI18nMessage() {
/* 41 */     return this.i18nMessage;
/*    */   }
/*    */   
/*    */   public String getContextKey() {
/* 45 */     return this.contextKey;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\exception\I18nMessageException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */