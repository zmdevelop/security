//package cn.com.taiji;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
//import org.springframework.boot.context.embedded.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.web.accept.ContentNegotiationManager;
//import org.springframework.web.filter.CharacterEncodingFilter;
//import org.springframework.web.servlet.View;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
//import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
//import org.thymeleaf.dialect.IDialect;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;
//
//import cn.com.taiji.expertdb.json.ExpertdbModule;
//import cn.com.taiji.security.TaijiAccessDecisionManager;
////import cn.com.taiji.portal.json.PortalModule;
//import cn.com.taiji.security.TaijiAuthenticationProvider;
//import cn.com.taiji.security.TaijiFailureHandler;
//import cn.com.taiji.security.TaijiFilterSecurityInterceptor;
//import cn.com.taiji.security.TaijiSecurityMetadataSource;
//import cn.com.taiji.security.TaijiSuccessHandler;
//import cn.com.taiji.security.TaijiUserDetailServiceImpl;
//import cn.com.taiji.security.TaijiUsernamePasswordAuthenticationFilter;
//import cn.com.taiji.sys.json.SysModule;
//import cn.com.taiji.sys.service.MenuService;
//import cn.com.taiji.sys.service.RoleService;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
//import com.fasterxml.jackson.datatype.joda.JodaModule;
//
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
//// @EnableMongoRepositories(value= {"cn.com.taiji.document"})
//// @EnableJpaRepositories(value = {"cn.com.taiji.repository"})
//@AutoConfigureAfter(JacksonAutoConfiguration.class)
//public class Application extends WebMvcConfigurerAdapter {
//
//	@Bean
//	public FilterRegistrationBean encodeRegistrationBean() {
//		CharacterEncodingFilter encodeFilter = new CharacterEncodingFilter();
//		encodeFilter.setEncoding("UTF-8");
//		encodeFilter.setForceEncoding(true);
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(encodeFilter);
//		registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
//		return registrationBean;
//	}
//
//	public static void main(String[] args) {
//		SpringApplication app = new SpringApplication(Application.class);
//		app.setShowBanner(false);
//		app.run(args);
//	}
//	
//	
//	
//	
//
//	@Bean
//	public ObjectMapper jacksonObjectMapper() {
//		return new ObjectMapper().registerModule(new JodaModule())
//				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//				.configure(SerializationFeature.INDENT_OUTPUT, true)
//				.setDateFormat(new ISO8601DateFormat())
//				.registerModule(new SysModule())
//				.registerModule(new ExpertdbModule());
//			//	.registerModule(new PortalModule())
//				//.registerModule(new CmsModule());// @TODO add more Module
//	}
//	//	@PostConstruct
//	//	public void init() {
//	//		// @formatter:off
//	//		jacksonObjectMapper
//	//				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//	//				.configure(SerializationFeature.INDENT_OUTPUT, true)
//	//				.setDateFormat(new ISO8601DateFormat())
//	//				.registerModule(jacksonJodaModule) 
//	//				.registerModule(new SysModule())
//	//				.registerModule(new PortalModule())
//	//				.registerModule(new CmsModule());// @TODO add more Module
//	//		// @formatter:on
//	//	}
//	//
//	//	@Autowired
//	//	ObjectMapper jacksonObjectMapper;
//
//	@Autowired
//	JodaModule jacksonJodaModule;
//
//	// @Bean
//	// @Primary
//	// public ObjectMapper jacksonObjectMapper() {
//	//		// @formatter:off
//	//		return new ObjectMapper()
//	//				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//	//				.configure(SerializationFeature.INDENT_OUTPUT, true)
//	//				.setDateFormat(new ISO8601DateFormat())
//	//				.registerModule(new JodaModule()) // add more Module
//	//				.registerModule(new SysModule());
//	//		// @formatter:on
//	// }
//
//	@Bean
//	public MappingJackson2JsonView mappingJackson2JsonView() {
//		MappingJackson2JsonView v = new org.springframework.web.servlet.view.json.MappingJackson2JsonView();
//		v.setObjectMapper(jacksonObjectMapper() );
//		v.setPrettyPrint(true);
//		return v;
//	}
//
//	protected class MappingJackson2JsonpView extends MappingJackson2JsonView {
//		public static final String DEFAULT_CONTENT_TYPE = "application/javascript";
//
//		@Override
//		public String getContentType() {
//			return DEFAULT_CONTENT_TYPE;
//		}
//
//		@Override
//		public void render(Map<String, ?> model, HttpServletRequest request,
//				HttpServletResponse response) throws Exception {
//			Map<String, String[]> params = request.getParameterMap();
//			if (params.containsKey("callback")) {
//				response.getOutputStream().write(
//						new String(params.get("callback")[0] + "(").getBytes());
//				super.render(model, request, response);
//				response.getOutputStream().write(new String(");").getBytes());
//				response.setContentType(DEFAULT_CONTENT_TYPE);
//			} else {
//				super.render(model, request, response);
//			}
//		}
//	}
//
//	@Bean
//	public MappingJackson2JsonpView mappingJackson2JsonpView() {
//		MappingJackson2JsonpView v = new MappingJackson2JsonpView();
//		v.setObjectMapper(jacksonObjectMapper() );
//		v.setPrettyPrint(false);
//		return v;
//	}
//
//	@Autowired
//	ThymeleafViewResolver thymeleafViewResolver;
//
//
//
//	@Override
//	public void configureContentNegotiation(
//			ContentNegotiationConfigurer configurer) {
//		configurer.favorParameter(true).ignoreAcceptHeader(false)
//		.defaultContentType(MediaType.TEXT_HTML)
//		.mediaType("json", MediaType.APPLICATION_JSON)
//		.mediaType("jsonp", MediaType.valueOf("application/javascript"));
//	}
//
//	@Bean
//	public ViewResolver contentNegotiatingViewResolver(
//			ContentNegotiationManager manager) {
//		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
//
//		thymeleafViewResolver.setCache(false);
//		//	thymeleafViewResolver.setOrder(1);
//		resolvers.add(thymeleafViewResolver);
//
//		//		InternalResourceViewResolver r2 = new InternalResourceViewResolver();
//		//		r2.setPrefix("templates");
//		//		r2.setSuffix(".jsp");
//		//		resolvers.add(r2);
//
//		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//		resolver.setViewResolvers(resolvers);
//		resolver.setContentNegotiationManager(manager);
//
//		List<View> views = new ArrayList<View>();
//		views.add(mappingJackson2JsonView());
//		views.add(mappingJackson2JsonpView());
//		resolver.setDefaultViews(views);
//		return resolver;
//	}
//
//	// see
//	// org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.ThymeleafDefaultConfiguration
//	@Bean
//	public Collection<IDialect> dialects() {
//		Collection<IDialect> dialects = new HashSet<IDialect>();
//		dialects
//		.add(new org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect());
//		return dialects;
//	}
//
//	// @Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/login").setViewName("login");
//		registry.addViewController("/access").setViewName("access");
//	}
//	//
//	//	@Bean
//	//	public ApplicationSecurity applicationSecurity() {
//	//		return new ApplicationSecurity();
//	//	}
//
//	@Order(Ordered.HIGHEST_PRECEDENCE) 
//	@Configuration
//	protected static class AuthenticationSecurity extends
//	GlobalAuthenticationConfigurerAdapter {
//
//		// @Value("${spring.datasource.url}")
//		// private String url;
//
//		// @Autowired
//		// private JdbcTemplate jt;
//		//
//		@Autowired
//		private DataSource dataSource;
//
//		@Bean
//		public ShaPasswordEncoder passwordEncoder() {
//			// return new BCryptPasswordEncoder();
//			return new ShaPasswordEncoder(1);
//		}
//		//@Autowired
//		//public    TaijiAuthenticationProvider customAuthenticationProvider;
//		@Autowired
//		public TaijiUserDetailServiceImpl userDetailsService;
//		@Override
//		public void init(AuthenticationManagerBuilder auth) throws Exception {
//			auth.userDetailsService(userDetailsService);
//			//	auth.authenticationProvider(customAuthenticationProvider);
//			// @formatter:off
//			//			auth.jdbcAuthentication()
//			//					.dataSource(dataSource)
//			//					.passwordEncoder(passwordEncoder())
//			//					.groupAuthoritiesByUsername(
//			//							"select g.code, g.name, ga.role_code from user_group g, user_account_group gm, user_group_role ga where gm.user_code = ? and g.code = ga.group_code and g.code = gm.group_code and g.enabled = true")
//			//					.authoritiesByUsernameQuery(
//			//							"select x.user_code, x.role_code from user_account_role x, user_role r where x.role_code=r.code and r.enabled = true and x.user_code=?")
//			//					.usersByUsernameQuery(
//			//							"select code, password, enabled from user_account where code=? and enabled = true");
//			// @formatter:off
//			// auth.inMemoryAuthentication().withUser("admin").password("admin")
//			// .roles("ADMIN", "USER").and().withUser("user")
//			// .password("user").roles("USER");
//			// @formatter:on
//		}
//	}
//
//	@Order(Ordered.LOWEST_PRECEDENCE - 8)
//	protected static class ApplicationSecurity extends
//	WebSecurityConfigurerAdapter {
//
//		@Bean
//		public TaijiUsernamePasswordAuthenticationFilter loginFilter()
//				throws Exception {
//			TaijiUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new TaijiUsernamePasswordAuthenticationFilter();
//			customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(customSuccessHandler());
//			customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(customFailureHandler());
//			customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
//			customUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/j_spring_security_check");
//	
//			return customUsernamePasswordAuthenticationFilter;
//		}
//
//
//
//		@Bean
//		public TaijiFailureHandler customFailureHandler() {
//			TaijiFailureHandler customFailureHandler = new TaijiFailureHandler();
//			customFailureHandler.setDefaultFailureUrl("/login?error");
//			return customFailureHandler;
//		}
//
//		@Bean
//		public TaijiSuccessHandler customSuccessHandler() {
//			TaijiSuccessHandler customSuccessHandler = new TaijiSuccessHandler();
//			customSuccessHandler.setDefaultTargetUrl("/system_frame");
//			return customSuccessHandler;
//		}
//
//		@Bean
//		@Override
//		public AuthenticationManager authenticationManagerBean() throws Exception {
//			List<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
//			authenticationProviderList.add(customAuthenticationProvider());
//			AuthenticationManager authenticationManager = new ProviderManager(authenticationProviderList);
//			return authenticationManager;
//		}
//		@Autowired
//		public TaijiUserDetailServiceImpl userDetailsService;
//		@Bean
//		private TaijiAuthenticationProvider customAuthenticationProvider() {
//			TaijiAuthenticationProvider customAuthenticationProvider = new TaijiAuthenticationProvider();
//			customAuthenticationProvider.setUserDetailsService(userDetailsService);
//			return customAuthenticationProvider;
//		}
//		
////		@Bean
////		private cn.com.taiji.security.TaijiAccessDecisionManager accessDecisionManager(){
////			cn.com.taiji.security.TaijiAccessDecisionManager accessDecisionManager=new cn.com.taiji.security.TaijiAccessDecisionManager();
////			return accessDecisionManager;
////		}
//		
//		@Autowired
//		private MenuService menuService;
//		@Autowired
//		private RoleService roleService;
//		@Bean
//		private TaijiSecurityMetadataSource fisMetadataSource(){
//			TaijiSecurityMetadataSource fisMetadataSource=new TaijiSecurityMetadataSource();
//			fisMetadataSource.setMenuService(menuService);
//			fisMetadataSource.setRoleService(roleService);
//			return fisMetadataSource;
//		}
//		@Autowired
//		private cn.com.taiji.security.TaijiAccessDecisionManager accessDecisionManager;
//		@Bean
//		public TaijiFilterSecurityInterceptor taijifiltersecurityinterceptor() throws Exception{
//			TaijiFilterSecurityInterceptor taijifiltersecurityinterceptor=new TaijiFilterSecurityInterceptor();
//			taijifiltersecurityinterceptor.setFisMetadataSource(fisMetadataSource());
//			taijifiltersecurityinterceptor.setAccessDecisionManager(accessDecisionManager);
//			taijifiltersecurityinterceptor.setAuthenticationManager(authenticationManagerBean());
//			return taijifiltersecurityinterceptor;
//		}
////		@Bean
////		public cn.com.taiji.security.MyAccessDeniedHandler access(){
////			cn.com.taiji.security.MyAccessDeniedHandler  access=new cn.com.taiji.security.MyAccessDeniedHandler ();
////			access.setErrorPage("access");
////			return access;
////		}
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			// @formatter:off
//			http.authorizeRequests()
//			.filterSecurityInterceptorOncePerRequest(true)//过滤器的安全拦截器的每一次的要求
//			.antMatchers("/some").permitAll()    // for test
//			.antMatchers("/login").permitAll()   // for login
//			.antMatchers("/image").permitAll()   // for login
//			.antMatchers("/j_spring_security_check").permitAll()
//			.anyRequest().fullyAuthenticated() //.accessDecisionManager(accessDecisionManager)    // all others need login
//			.and()
//			.formLogin().loginPage("/login").failureUrl("/login?error") // login config
//			.and()
//			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //logout config
//			.and()//.addFilte(access());
//			.exceptionHandling().accessDeniedPage("/access");  // exception
//			http.csrf().disable().addFilterAfter(taijifiltersecurityinterceptor(), FilterSecurityInterceptor.class)
//			.addFilter(loginFilter()).rememberMe();
//			// @formatter:on
//		}
//
//	}
//
//}