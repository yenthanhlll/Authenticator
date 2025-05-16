/*    */ package WEB-INF.classes.com.dreammirae.mmth.parser.json;
/*    */ import com.dreammirae.mmth.authentication.ReturnCodes;
/*    */ import com.dreammirae.mmth.authentication.bean.AnyPASSRequestLocator;
/*    */ import com.dreammirae.mmth.authentication.bean.AnyPASSResponseLocator;
/*    */ import com.dreammirae.mmth.authentication.bean.AuthenticationInfos;
/*    */ import com.dreammirae.mmth.authentication.bean.AuthenticationResponseLocator;
/*    */ import com.dreammirae.mmth.external.bean.GlobalWibeeRequestParam;
/*    */ import com.dreammirae.mmth.external.bean.GlobalWibeeWebApiResponse;
/*    */ import com.dreammirae.mmth.external.bean.WebApiRequestParam;
/*    */ import com.dreammirae.mmth.external.bean.WebApiResponse;
/*    */ import com.dreammirae.mmth.fido.json.UafMessageSerializer;
/*    */ import com.dreammirae.mmth.fido.json.UafSerializeUtils;
/*    */ import com.dreammirae.mmth.parser.json.AnyPASSResponseLocatorSerializer;
/*    */ import com.dreammirae.mmth.parser.json.AuthenticationInfoSerializer;
/*    */ import com.dreammirae.mmth.parser.json.AuthenticationRequestLocatorSerializer;
/*    */ import com.dreammirae.mmth.parser.json.AuthenticationResponseLocatorSerializer;
/*    */ import com.dreammirae.mmth.parser.json.DeviceTypeSerializer;
/*    */ import com.dreammirae.mmth.parser.json.OtpAdditionalDataSerializer;
/*    */ import com.dreammirae.mmth.parser.json.ReturnCodeSerializer;
/*    */ import com.dreammirae.mmth.parser.json.ext.GlobalWibeeWebApiRequestParamSerializer;
/*    */ import com.dreammirae.mmth.parser.json.ext.WebApiRequestParamSerializer;
/*    */ import com.dreammirae.mmth.parser.json.ext.WebApiResponseSerializer;
/*    */ import com.dreammirae.mmth.vo.types.DeviceTypes;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class GsonUtils extends UafSerializeUtils {
/* 29 */   private static Gson gson = null;
/*    */ 
/*    */ 
/*    */   
/* 33 */   private static GsonBuilder gsonBuilder = new GsonBuilder();
/*    */   static {
/* 35 */     Map<Class<?>, UafMessageSerializer<?>> map = getSerializers();
/* 36 */     for (Map.Entry<Class<?>, UafMessageSerializer<?>> entry : map.entrySet()) {
/* 37 */       gsonBuilder.registerTypeAdapter(entry.getKey(), entry.getValue());
/*    */     }
/*    */     
/* 40 */     gsonBuilder.registerTypeAdapter(ReturnCodes.class, new ReturnCodeSerializer());
/* 41 */     gsonBuilder.registerTypeAdapter(DeviceTypes.class, new DeviceTypeSerializer());
/* 42 */     gsonBuilder.registerTypeAdapter(AuthenticationRequestLocator.class, new AuthenticationRequestLocatorSerializer());
/* 43 */     gsonBuilder.registerTypeAdapter(AuthenticationResponseLocator.class, new AuthenticationResponseLocatorSerializer());
/* 44 */     gsonBuilder.registerTypeAdapter(AnyPASSRequestLocator.class, new AnyPASSRequestLocatorSerializer());
/* 45 */     gsonBuilder.registerTypeAdapter(AnyPASSResponseLocator.class, new AnyPASSResponseLocatorSerializer());
/* 46 */     gsonBuilder.registerTypeAdapter(OtpAdditionalData.class, new OtpAdditionalDataSerializer());
/* 47 */     gsonBuilder.registerTypeAdapter(AuthenticationInfos.class, new AuthenticationInfoSerializer());
/* 48 */     gsonBuilder.registerTypeAdapter(WebApiRequestParam.class, new WebApiRequestParamSerializer());
/* 49 */     gsonBuilder.registerTypeAdapter(WebApiResponse.class, new WebApiResponseSerializer());
/* 50 */     gsonBuilder.registerTypeAdapter(GlobalWibeeRequestParam.class, new GlobalWibeeWebApiRequestParamSerializer());
/* 51 */     gsonBuilder.registerTypeAdapter(GlobalWibeeWebApiResponse.class, new GlobalWibeeWebApiResponseSerializer());
/*    */     
/* 53 */     gsonBuilder.excludeFieldsWithModifiers(new int[] { 504 });
/*    */   }
/*    */ 
/*    */   
/*    */   public static Gson gson() {
/* 58 */     if (gson == null) {
/* 59 */       gson = gsonBuilder.create();
/*    */     }
/*    */     
/* 62 */     return gson;
/*    */   }
/*    */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\parser\json\GsonUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */