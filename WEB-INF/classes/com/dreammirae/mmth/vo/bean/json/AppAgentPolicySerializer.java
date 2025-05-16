/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean.json;
/*    */ 
/*    */ import com.dreammirae.mmth.vo.AppAgentVO;
/*    */ import com.dreammirae.mmth.vo.bean.AppAgentPolicy;
/*    */ import com.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.fasterxml.jackson.databind.SerializerProvider;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AppAgentPolicySerializer
/*    */   extends JsonSerializer<AppAgentPolicy>
/*    */ {
/*    */   public void serialize(AppAgentPolicy obj, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
/* 18 */     gen.writeStartObject();
/* 19 */     gen.writeArrayFieldStart("policy");
/*    */     
/* 21 */     List<AppAgentVO> list = obj.getPolicy();
/*    */     
/* 23 */     for (AppAgentVO vo : list) {
/* 24 */       gen.writeStartObject();
/* 25 */       gen.writeStringField("osType", vo.getOsType().name());
/* 26 */       gen.writeStringField("pkgName", vo.getPkgUnique());
/* 27 */       gen.writeEndObject();
/*    */     } 
/*    */     
/* 30 */     gen.writeEndArray();
/* 31 */     gen.writeEndObject();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<AppAgentPolicy> handledType() {
/* 37 */     return AppAgentPolicy.class;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\json\AppAgentPolicySerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */