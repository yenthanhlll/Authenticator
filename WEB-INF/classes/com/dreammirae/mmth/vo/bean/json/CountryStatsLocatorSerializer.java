/*    */ package WEB-INF.classes.com.dreammirae.mmth.vo.bean.json;
/*    */ 
/*    */ import com.dreammirae.mmth.util.StringUtils;
/*    */ import com.dreammirae.mmth.vo.bean.CountryStatsLocator;
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
/*    */ @Component
/*    */ public class CountryStatsLocatorSerializer
/*    */   extends JsonSerializer<CountryStatsLocator>
/*    */ {
/*    */   @Autowired
/*    */   private MessageSource messageSource;
/*    */   
/*    */   public void serialize(CountryStatsLocator loc, JsonGenerator jsonGen, SerializerProvider provider) throws IOException {
/* 24 */     jsonGen.writeStartObject();
/* 25 */     if (StringUtils.isEmpty(loc.getCountryCode())) {
/* 26 */       jsonGen.writeStringField("countryName", this.messageSource.getMessage("countrycode.etc", null, LocaleContextHolder.getLocale()));
/*    */     } else {
/* 28 */       jsonGen.writeStringField("countryName", this.messageSource.getMessage(loc.getCountryCode(), null, loc.getCountryCode(), LocaleContextHolder.getLocale()));
/*    */     } 
/*    */     
/* 31 */     jsonGen.writeNumberField("reg", loc.getReg());
/* 32 */     jsonGen.writeNumberField("dereg", loc.getDereg());
/* 33 */     jsonGen.writeNumberField("auth", loc.getAuth());
/*    */     
/* 35 */     jsonGen.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\vo\bean\json\CountryStatsLocatorSerializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */