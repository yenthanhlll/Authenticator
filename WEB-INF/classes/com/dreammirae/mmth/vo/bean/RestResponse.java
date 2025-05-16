/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.misc.I18nMessage;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RestResponse
/*    */ {
/*    */   private Map<String, Object> contextData;
/*    */   private Map<String, Object> data;
/*    */   private Map<String, I18nMessage> contextMessages;
/*    */   private List<I18nMessage> generalMessages;
/*    */   
/*    */   public void addContextData(String contextKey, Object contextValue) {
/* 22 */     if (this.contextData == null) {
/* 23 */       this.contextData = new HashMap<>();
/*    */     }
/*    */     
/* 26 */     this.contextData.put(contextKey, contextValue);
/*    */   }
/*    */   
/*    */   public void addData(String key, Object value) {
/* 30 */     if (this.data == null) {
/* 31 */       this.data = new HashMap<>();
/*    */     }
/*    */     
/* 34 */     this.data.put(key, value);
/*    */   }
/*    */   
/*    */   public void addContextMessage(String contextKey, I18nMessage message) {
/* 38 */     if (this.contextMessages == null) {
/* 39 */       this.contextMessages = new HashMap<>();
/*    */     }
/*    */     
/* 42 */     this.contextMessages.put(contextKey, message);
/*    */   }
/*    */   
/*    */   public void addGeneralMessage(I18nMessage message) {
/* 46 */     if (this.generalMessages == null) {
/* 47 */       this.generalMessages = new ArrayList<>();
/*    */     }
/*    */     
/* 50 */     this.generalMessages.add(message);
/*    */   }
/*    */   
/*    */   public Map<String, Object> getContextData() {
/* 54 */     return this.contextData;
/*    */   }
/*    */   
/*    */   public Map<String, Object> getData() {
/* 58 */     return this.data;
/*    */   }
/*    */   
/*    */   public Map<String, I18nMessage> getContextMessages() {
/* 62 */     return this.contextMessages;
/*    */   }
/*    */   
/*    */   public List<I18nMessage> getGeneralMessages() {
/* 66 */     return this.generalMessages;
/*    */   }
/*    */   
/*    */   public boolean getHasMessages() {
/* 70 */     return (this.contextMessages != null || this.generalMessages != null);
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\RestResponse.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */