/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean.json;
/*    */ 
/*    */ import com.fasterxml.jackson.core.JsonGenerator;
/*    */ import com.fasterxml.jackson.core.JsonProcessingException;
/*    */ import com.fasterxml.jackson.databind.JsonSerializer;
/*    */ import com.fasterxml.jackson.databind.SerializerProvider;
/*    */ import java.io.IOException;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.MessageSource;
/*    */ import org.springframework.context.i18n.LocaleContextHolder;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class MessageKeySerializer
/*    */   extends JsonSerializer<String>
/*    */ {
/*    */   @Autowired
/*    */   private MessageSource messageSource;
/*    */   
/*    */   public void serialize(String messageKey, JsonGenerator jsonGen, SerializerProvider provider) throws IOException {
/* 23 */     jsonGen.writeString(this.messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale()));
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\json\MessageKeySerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */