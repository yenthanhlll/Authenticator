/*     */ package WEB-INF.classes.com.dreammirae.mmth.config;
/*     */ 
/*     */ import com.dreammirae.mmth.config.Commons;
/*     */ import com.dreammirae.mmth.config.WebManagerFailureHandler;
/*     */ import com.dreammirae.mmth.config.WebManagerLogoutHandler;
/*     */ import com.dreammirae.mmth.config.WebManagerSuccessHandler;
/*     */ import com.dreammirae.mmth.misc.SysEnvCommon;
/*     */ import com.dreammirae.mmth.vo.types.AdministratorTypes;
/*     */ import com.dreammirae.mmth.web.filter.XSSFilter;
/*     */ import com.dreammirae.mmth.web.service.AdministratorService;
/*     */ import javax.servlet.Filter;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.annotation.ComponentScan;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.http.HttpMethod;
/*     */ import org.springframework.security.config.annotation.SecurityBuilder;
/*     */ import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*     */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*     */ import org.springframework.security.config.annotation.web.builders.WebSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*     */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
/*     */ import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
/*     */ import org.springframework.security.config.http.SessionCreationPolicy;
/*     */ import org.springframework.security.core.userdetails.UserDetailsService;
/*     */ import org.springframework.security.web.authentication.AuthenticationFailureHandler;
/*     */ import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
/*     */ import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
/*     */ import org.springframework.security.web.csrf.CsrfFilter;
/*     */ import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/*     */ import org.springframework.security.web.util.matcher.RequestMatcher;
/*     */ import org.springframework.web.filter.CharacterEncodingFilter;
/*     */ 
/*     */ @Configuration
/*     */ @ComponentScan({"com.dreammirae.mmth"})
/*     */ @EnableWebSecurity
/*     */ public class MmthSecurityConfig
/*     */   extends WebSecurityConfigurerAdapter {
/*     */   public void configure(WebSecurity web) throws Exception {
/*  40 */     ((WebSecurity.IgnoredRequestConfigurer)((WebSecurity.IgnoredRequestConfigurer)((WebSecurity.IgnoredRequestConfigurer)((WebSecurity.IgnoredRequestConfigurer)((WebSecurity.IgnoredRequestConfigurer)((WebSecurity.IgnoredRequestConfigurer)((WebSecurity.IgnoredRequestConfigurer)((WebSecurity.IgnoredRequestConfigurer)web.ignoring()
/*  41 */       .antMatchers(HttpMethod.GET, new String[] { "/mmth/service/httper"
/*  42 */         })).antMatchers(HttpMethod.GET, new String[] { "/static/resources/**"
/*  43 */         })).antMatchers(HttpMethod.POST, new String[] { "/rpserver/httpapi/**"
/*  44 */         })).antMatchers(HttpMethod.POST, new String[] { "/rpserver/kiwoom/**"
/*  45 */         })).antMatchers(HttpMethod.POST, new String[] { "/rpserver/webapi/**"
/*  46 */         })).antMatchers(new String[] { "/extapi/fido/**"
/*  47 */         })).antMatchers(new String[] { "/dev/**"
/*  48 */         })).antMatchers(new String[] { "/unsupported"
/*  49 */         })).antMatchers(HttpMethod.GET, new String[] { "/fcm/register/**" });
/*     */     
/*  51 */     super.configure(web);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configure(HttpSecurity httpSecurity) throws Exception {
/*  57 */     ((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)((HttpSecurity)httpSecurity
/*  58 */       .addFilterBefore(createCharacterEncodingFilter(), CsrfFilter.class)
/*  59 */       .addFilterAfter(createXSSFilter(), CsrfFilter.class)
/*  60 */       .csrf()
/*  61 */       .ignoringAntMatchers(new String[] { "/web/manager/**", "/static/resources/**", "/errors", "/login", "/logout"
/*  62 */         }).and())
/*  63 */       .headers()
/*  64 */       .frameOptions().disable()
/*  65 */       .and())
/*  66 */       .authorizeRequests()
/*  67 */       .antMatchers(new String[] { "/web/manager/sql/**" })).hasAnyAuthority(new String[] { AdministratorTypes.DEV.name()
/*  68 */         }).antMatchers(new String[] { "/web/manager/administrator/**" })).hasAnyAuthority(new String[] { AdministratorTypes.SUPER.name()
/*  69 */         }).antMatchers(new String[] { "/web/manager/systemSettings/**", "/web/manager/audit/**", "/web/manager/metadata/**", "/web/manager/facets/**", "/web/manager/appagent/**" })).hasAnyAuthority(new String[] { AdministratorTypes.ADMIN.name(), AdministratorTypes.DEV.name()
/*  70 */         }).antMatchers(new String[] { "/errors" })).anonymous()
/*  71 */       .antMatchers(new String[] { "/dev/**" })).anonymous()
/*  72 */       .antMatchers(new String[] { "/web/manager/branch/**" })).hasAnyAuthority(new String[] { AdministratorTypes.OPTEAM.name(), AdministratorTypes.CSTEAM.name()
/*  73 */         }).antMatchers(new String[] { "/web/manager/user/**" })).hasAnyAuthority(new String[] { AdministratorTypes.ADMIN.name(), AdministratorTypes.SUPER.name(), AdministratorTypes.OPTEAM.name(), AdministratorTypes.CSTEAM.name()
/*  74 */         }).anyRequest()).authenticated()
/*  75 */       .and())
/*  76 */       .formLogin()
/*  77 */       .loginPage("/login")
/*  78 */       .loginProcessingUrl("/login"))
/*  79 */       .permitAll())
/*  80 */       .failureHandler(failureHandler()))
/*  81 */       .successHandler(successHandler()))
/*  82 */       .and())
/*  83 */       .logout()
/*  84 */       .logoutRequestMatcher((RequestMatcher)new AntPathRequestMatcher("/logout"))
/*  85 */       .logoutSuccessUrl("/login")
/*  86 */       .logoutSuccessHandler(logoutSuccessHandler())
/*  87 */       .invalidateHttpSession(true)
/*  88 */       .deleteCookies(new String[] { "JSESSIONID"
/*  89 */         }).permitAll()
/*  90 */       .and())
/*  91 */       .exceptionHandling()
/*  92 */       .accessDeniedPage("/errors")
/*  93 */       .and())
/*  94 */       .sessionManagement()
/*  95 */       .sessionCreationPolicy(SessionCreationPolicy.NEVER)
/*  96 */       .invalidSessionUrl("/login")
/*  97 */       .and())
/*  98 */       .httpBasic();
/*     */   }
/*     */   @Autowired
/*     */   private AdministratorService administratorService;
/*     */   
/*     */   protected UserDetailsService userDetailsService() {
/* 104 */     return (UserDetailsService)this.administratorService;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/* 109 */     auth.userDetailsService(userDetailsService()).passwordEncoder(SysEnvCommon.getPwdEncoder());
/*     */   }
/*     */   
/*     */   private AuthenticationSuccessHandler successHandler() {
/* 113 */     return (AuthenticationSuccessHandler)new WebManagerSuccessHandler();
/*     */   }
/*     */   
/*     */   private AuthenticationFailureHandler failureHandler() {
/* 117 */     return (AuthenticationFailureHandler)new WebManagerFailureHandler();
/*     */   }
/*     */   
/*     */   private LogoutSuccessHandler logoutSuccessHandler() {
/* 121 */     return (LogoutSuccessHandler)new WebManagerLogoutHandler("/login");
/*     */   }
/*     */   
/*     */   private Filter createCharacterEncodingFilter() {
/* 125 */     CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
/* 126 */     characterEncodingFilter.setEncoding(Commons.UTF8_CS.name());
/* 127 */     characterEncodingFilter.setForceEncoding(true);
/* 128 */     return (Filter)characterEncodingFilter;
/*     */   }
/*     */   
/*     */   private Filter createXSSFilter() {
/* 132 */     return (Filter)new XSSFilter();
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\config\MmthSecurityConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */