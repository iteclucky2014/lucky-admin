package cn.sambo.difference.platform;

import cn.sambo.difference.platform.domain.PlatformUser;
import cn.sambo.difference.platform.security.AccessToken;
import cn.sambo.difference.platform.security.AccessTokenFilter;
import cn.sambo.difference.platform.security.GrantedAuthorityFilter;
import cn.sambo.difference.platform.security.XXXUserDetailsService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"cn.sambo"})
@EnableZuulProxy
@EnableAutoConfiguration(exclude = {})
@EnableConfigurationProperties
@Controller
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}

	@Configuration
	protected static class SecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("platform/api/**", "platform/images/**").permitAll()
					//.antMatchers( "//**", "/echarts/**", "/images/**", "/jstree/**", "/layui/**").permitAll()
					//.anyRequest().access("@securityEx.check(authentication, request)")
					.and()
					.addFilterAfter(new AccessTokenFilter(), FilterSecurityInterceptor.class)
					.addFilterAfter(new GrantedAuthorityFilter(), AccessTokenFilter.class)
					.formLogin()
					.loginPage("/start/index.html#/user/login")
					.loginProcessingUrl("/login")
					.successHandler(new AuthenticationSuccessHandler() {
						@Override
						public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
															HttpServletResponse httpServletResponse,
															Authentication authentication)
								throws IOException, ServletException {
							httpServletResponse.setContentType("application/json;charset=utf-8");
							PrintWriter out = httpServletResponse.getWriter();

							Map<String, Object> result = new HashMap<>();
							Object principal  = authentication.getPrincipal();
							if(principal  != null && principal  instanceof UserDetails) {
								PlatformUser platformUser = (PlatformUser) principal;
								AccessToken accessToken = new AccessToken(DigestUtils.sha256Hex(platformUser.getUsername() + System.currentTimeMillis()), null);
								result.put("code", 0);
								result.put("msg", "登入成功");
								result.put("data", accessToken);

								httpServletRequest.getSession().setAttribute("access_token", accessToken);

								out.write(JSON.toJSONString(result));
							} else {
								out.write("{\"code\":0,\"msg\":\"用户名或密码错误！\"}");
							}
							out.flush();
							out.close();
						}
					})
					.failureHandler(new AuthenticationFailureHandler() {
						@Override
						public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
															HttpServletResponse httpServletResponse,
															AuthenticationException e)
								throws IOException, ServletException {
							httpServletResponse.setContentType("application/json;charset=utf-8");
							PrintWriter out = httpServletResponse.getWriter();
							out.write("{\"code\":1001,\"msg\":\"用户名或密码错误！\"}");
							out.flush();
							out.close();
						}
					})
					.and()
					.logout()
					.logoutUrl("/logout")
					.logoutSuccessHandler(new LogoutSuccessHandler() {
						@Override
						public void onLogoutSuccess(HttpServletRequest httpServletRequest,
													HttpServletResponse httpServletResponse,
													Authentication authentication)
								throws IOException, ServletException {
							httpServletResponse.setContentType("application/json;charset=utf-8");
							PrintWriter out = httpServletResponse.getWriter();
							out.write("{\"code\":0,\"msg\":\"注销成功\"}");
							out.flush();
							out.close();
						}
					})
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutSuccessUrl("/start/index.html#/user/login")
					.and().csrf().disable().headers().frameOptions().disable();
		}

		@Bean
		protected XXXUserDetailsService xxxUserDetailsService() {
			return new XXXUserDetailsService();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(xxxUserDetailsService());
		}

		@Bean
		public static PasswordEncoder passwordEncoder() {
			return NoOpPasswordEncoder.getInstance();
		}
	}

	@RequestMapping(value = {"/"})
	public String index() {
		return "redirect:/start/index.html";
	}

	@GetMapping(value = "/getSessonUserInfo")
	@ResponseBody
	@Transactional
	public Map<String, Object> getSessonUserInfo() {
		Map<String, Object> result = new HashMap<>();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal  != null && principal instanceof UserDetails) {
			PlatformUser platformUser = (PlatformUser) principal;
			result.put("code", 0);
			result.put("msg", "");
			result.put("data", platformUser);
		} else {
			result.put("code", 1001);
			result.put("msg", "没有信息");
		}
		return result;
	}


	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource("application.yml"), new ClassPathResource("fieldDef.yml"));
		configurer.setProperties(yaml.getObject());
		return configurer;
	}



}

