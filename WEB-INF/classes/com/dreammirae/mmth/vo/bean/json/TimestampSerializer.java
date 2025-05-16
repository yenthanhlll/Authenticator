/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean.json;
/*    */ 
/*    */ import com.dreammirae.mmth.config.Commons;
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
/*    */ public class TimestampSerializer
/*    */   extends JsonSerializer<Long>
/*    */ {
/*    */   public void serialize(Long ts, JsonGenerator gen, SerializerProvider prov) throws IOException, JsonProcessingException {
/* 19 */     if (ts == null || ts.longValue() == 0L) {
/* 20 */       gen.writeString("");
/*    */       
/*    */       return;
/*    */     } 
/* 24 */     gen.writeString(Commons.displayDateTime(ts.longValue()));
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\json\TimestampSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */