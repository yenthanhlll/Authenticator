/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean.json;
/*    */ 
/*    */ import com.dreammirae.mmth.external.bean.ExtRequestStatus;
/*    */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*    */ import com.dreammirae.mmth.vo.ExternalServiceItemVO;
/*    */ import com.dreammirae.mmth.vo.bean.AuthRequestContents;
/*    */ import com.fasterxml.jackson.core.JsonParser;
/*    */ import com.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.fasterxml.jackson.databind.DeserializationContext;
/*    */ import com.fasterxml.jackson.databind.JsonDeserializer;
/*    */ import com.fasterxml.jackson.databind.JsonNode;
/*    */ import java.io.IOException;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class ExternalServiceItemDeserializer
/*    */   extends JsonDeserializer<ExternalServiceItemVO>
/*    */ {
/*    */   private static final String KEY_TYPE = "type";
/*    */   private static final String KEY_PUSH_YN = "pushYn";
/*    */   private static final String KEY_USER_NO = "userno";
/*    */   private static final String KEY_TID = "tid";
/*    */   private static final String KEY_TITLE = "title";
/*    */   private static final String KEY_MSG = "msg";
/*    */   private static final String KEY_TIMEOUT = "timeout";
/*    */   private static final String KEY_AUTH_REQUEST = "auth_request";
/*    */   private static final String KEY_USER_ID = "user_id";
/*    */   private static final String KEY_CUSTOMER_CODE = "customer_code";
/*    */   private static final String KEY_CUSTOMER_NAME = "customer_name";
/*    */   private static final String KEY_SERVICE_NAME = "service_name";
/*    */   
/*    */   public ExternalServiceItemVO deserialize(JsonParser parser, DeserializationContext ctx) throws IOException, JsonProcessingException {
/* 38 */     JsonNode node = (JsonNode)parser.getCodec().readTree(parser);
/*    */     
/* 40 */     ExternalServiceItemVO param = new ExternalServiceItemVO();
/*    */     
/* 42 */     param.setType(node.get("type").textValue());
/*    */     
/* 44 */     param.setUsePush("Y".equalsIgnoreCase(node.get("pushYn").textValue()));
/* 45 */     param.setUserName(node.get("userno").textValue());
/* 46 */     param.setExternalIdentifier(node.get("tid").textValue());
/* 47 */     param.setTitle(node.get("title").textValue());
/* 48 */     param.setMsg(node.get("msg").textValue());
/* 49 */     long timeout = node.get("timeout").longValue();
/* 50 */     param.setTsExpired(timeout);
/*    */     
/* 52 */     AuthRequestContents contents = new AuthRequestContents();
/* 53 */     contents.setTsExpired(timeout);
/*    */     
/* 55 */     JsonNode authRequest = node.get("auth_request");
/* 56 */     contents.setUserId(authRequest.get("user_id").textValue());
/* 57 */     contents.setCustomerCode(authRequest.get("customer_code").textValue());
/* 58 */     contents.setCustomerName(authRequest.get("customer_name").textValue());
/* 59 */     contents.setServiceName(authRequest.get("service_name").textValue());
/*    */     
/* 61 */     param.setRequestContents(contents);
/* 62 */     param.setTransactionId(SysEnvCommon.generateTID());
/* 63 */     param.setTsReg(System.currentTimeMillis());
/* 64 */     param.setStatus(ExtRequestStatus.REQ);
/*    */     
/* 66 */     return param;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\json\ExternalServiceItemDeserializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */