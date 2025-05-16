/*    */ package WEB-INF.classes.com.dreammirae.mmth.external.bean;
/*    */ 
/*    */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*    */ import com.dreammirae.mmth.external.bean.json.GptwrWebApiResponseSerializer;
/*    */ import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/*    */ 
/*    */ 
/*    */ 
/*    */ @JsonSerialize(using = GptwrWebApiResponseSerializer.class)
/*    */ public class GptwrWebApiResponse
/*    */   extends WebApiResponse
/*    */ {
/*    */   private String errorMessage;
/*    */   
/*    */   public String getErrorMessage() {
/* 16 */     return this.errorMessage;
/*    */   }
/*    */   
/*    */   public void setErrorMessage(String errorMessage) {
/* 20 */     this.errorMessage = errorMessage;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\bean\GptwrWebApiResponse.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */