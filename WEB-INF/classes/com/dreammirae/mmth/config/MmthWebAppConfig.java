/*     */ package WEB-INF.classes.com.dreammirae.mmth.config;
/*     */ 
/*     */ import com.dreammirae.mmth.config.MmthSecurityConfig;
/*     */ import com.dreammirae.mmth.misc.MessageUtils;
/*     */ import com.dreammirae.mmth.web.interceptor.CommonDataInterceptor;
/*     */ import com.dreammirae.mmth.web.interceptor.HomeUrlChangeInterceptor;
/*     */ import com.dreammirae.mmth.web.interceptor.SessionTimeoutInterceptor;
/*     */ import com.dreammirae.mmth.web.interceptor.WebLocaleChangeInterceptor;
/*     */ import org.springframework.context.MessageSource;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.ComponentScan;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.context.annotation.Import;
/*     */ import org.springframework.web.multipart.commons.CommonsMultipartResolver;
/*     */ import org.springframework.web.servlet.HandlerInterceptor;
/*     */ import org.springframework.web.servlet.LocaleResolver;
/*     */ import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
/*     */ import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/*     */ import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
/*     */ import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
/*     */ import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/*     */ import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
/*     */ import org.springframework.web.servlet.i18n.SessionLocaleResolver;
/*     */ import org.springframework.web.servlet.view.InternalResourceViewResolver;
/*     */ import org.springframework.web.servlet.view.JstlView;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ @ComponentScan({"com.dreammirae.mmth"})
/*     */ @EnableWebMvc
/*     */ @Import({MmthSecurityConfig.class})
/*     */ public class MmthWebAppConfig
/*     */   extends WebMvcConfigurerAdapter
/*     */ {
/*     */   private static final long MAX_UPLOAD_SIZE_BYTES = 52428800L;
/*     */   
/*     */   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {}
/*     */   
/*     */   @Bean
/*     */   public InternalResourceViewResolver internalResourceViewResolver() {
/*  50 */     InternalResourceViewResolver resolver = new InternalResourceViewResolver();
/*  51 */     resolver.setPrefix("/WEB-INF/views/");
/*  52 */     resolver.setSuffix(".jsp");
/*  53 */     resolver.setViewClass(JstlView.class);
/*  54 */     return resolver;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public MessageSource messageSource() {
/*  59 */     return MessageUtils.getMessageSource();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public LocaleChangeInterceptor localeChangeInterceptor() {
/*  64 */     WebLocaleChangeInterceptor webLocaleChangeInterceptor = new WebLocaleChangeInterceptor();
/*  65 */     webLocaleChangeInterceptor.setParamName("locale");
/*  66 */     return (LocaleChangeInterceptor)webLocaleChangeInterceptor;
/*     */   }
/*     */ 
/*     */   
/*     */   @Bean(name = {"localeResolver"})
/*     */   public LocaleResolver sessionLocaleResolver() {
/*  72 */     return (LocaleResolver)new SessionLocaleResolver();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInterceptors(InterceptorRegistry registry) {
/*  79 */     registry.addInterceptor((HandlerInterceptor)new SessionTimeoutInterceptor()).addPathPatterns(new String[] { "/web/manager/**" });
/*  80 */     registry.addInterceptor((HandlerInterceptor)localeChangeInterceptor()).addPathPatterns(new String[] { "/web/manager/**", "/login" });
/*  81 */     registry.addInterceptor((HandlerInterceptor)new HomeUrlChangeInterceptor()).addPathPatterns(new String[] { "/web/manager/**" });
/*  82 */     registry.addInterceptor((HandlerInterceptor)new CommonDataInterceptor()).addPathPatterns(new String[] { "/web/manager/**" });
/*     */     
/*  84 */     super.addInterceptors(registry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addResourceHandlers(ResourceHandlerRegistry registry) {
/*  95 */     registry.addResourceHandler(new String[] { "static/resources/css/**" }).addResourceLocations(new String[] { "/assets/css/" });
/*  96 */     registry.addResourceHandler(new String[] { "static/resources/js/**" }).addResourceLocations(new String[] { "/assets/js/" });
/*  97 */     registry.addResourceHandler(new String[] { "static/resources/plugins/**" }).addResourceLocations(new String[] { "/assets/plugins/" });
/*  98 */     registry.addResourceHandler(new String[] { "static/resources/images/**" }).addResourceLocations(new String[] { "/assets/images/" });
/*     */     
/* 100 */     super.addResourceHandlers(registry);
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public CommonsMultipartResolver multipartResolver() {
/* 105 */     CommonsMultipartResolver cmr = new CommonsMultipartResolver();
/* 106 */     cmr.setMaxUploadSize(104857600L);
/* 107 */     cmr.setMaxUploadSizePerFile(52428800L);
/* 108 */     return cmr;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\config\MmthWebAppConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */