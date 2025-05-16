/*    */ package WEB-INF.classes.com.dreammirae.mmth.external.bean.json;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
/*    */ import com.dreammirae.mmth.external.bean.GptwrWebApiResponse;
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.fasterxml.jackson.databind.SerializerProvider;
/*    */ import java.io.IOException;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class GptwrWebApiResponseSerializer
/*    */   extends JsonSerializer<GptwrWebApiResponse>
/*    */ {
/*    */   private static final String KEY_RETURN_CODE = "returnCode";
/*    */   private static final String KEY_ISSUE_CODE = "issueCode";
/*    */   private static final String KEY_ERROR_MESSAGE = "errorMessage";
/*    */   private static final String KEY_ISSUE_CODE_EXPIRED = "issueCodeExpired";
/*    */   
/*    */   public void serialize(GptwrWebApiResponse value, JsonGenerator jsonGen, SerializerProvider serializers) throws IOException, JsonProcessingException {
/* 26 */     jsonGen.writeStartObject();
/*    */     
/* 28 */     if (value.getReturnCode() != null) {
/* 29 */       jsonGen.writeStringField("returnCode", value.getReturnCode().getCode());
/*    */     }
/*    */     
/* 32 */     if (!StringUtils.isEmpty(value.getIssueCode())) {
/* 33 */       jsonGen.writeStringField("issueCode", value.getIssueCode());
/*    */     }
/*    */     
/* 36 */     if (!StringUtils.isEmpty(value.getIssueCode())) {
/* 37 */       jsonGen.writeStringField("errorMessage", value.getErrorMessage());
/*    */     }
/*    */     
/* 40 */     if (value.getIssueCodeExpired() != null) {
/* 41 */       jsonGen.writeStringField("issueCodeExpired", Commons.displayDateTime(value.getIssueCodeExpired().longValue()));
/*    */     }
/*    */     
/* 44 */     jsonGen.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\external\bean\json\GptwrWebApiResponseSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */