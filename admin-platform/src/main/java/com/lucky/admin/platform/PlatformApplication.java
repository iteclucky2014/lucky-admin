package com.lucky.admin.platform;

import com.alibaba.fastjson.JSON;
import com.lucky.admin.platform.common.ApiResult;
import com.lucky.admin.platform.common.ApiResultBuilder;
import com.lucky.admin.platform.common.ApiResultCode;
import com.lucky.admin.platform.security.AccessToken;
import com.lucky.admin.platform.security.AccessTokenFilter;
import com.lucky.admin.platform.security.LoginService;
import com.lucky.admin.platform.vo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.mybatis.spring.annotation.MapperScan;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@MapperScan("com.lucky.admin.platform.dao")
@SpringBootApplication(scanBasePackages = {"com.lucky"})
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
			http.authorizeRequests()
                    .antMatchers("/lucky/", "/lucky/src/**", "/lucky/start/**", "/lucky/getSessionAccessToken", "/lucky/login", "/lucky/logout", "/lucky/user/reg")
                    .permitAll()
					.and()
					.addFilterAfter(new AccessTokenFilter(), FilterSecurityInterceptor.class)
//					.addFilterAfter(new GrantedAuthorityFilter(), AccessTokenFilter.class)
					.formLogin()
					.loginPage("/start/index.html#/user/login")
					.loginProcessingUrl("/login")
					.successHandler((httpServletRequest, httpServletResponse, authentication) -> {
						httpServletResponse.setContentType("application/json;charset=utf-8");
						PrintWriter out = httpServletResponse.getWriter();

						Map<String, Object> result = new HashMap<>();
						Object principal  = authentication.getPrincipal();
						if(principal  != null && principal  instanceof UserDetails) {
							User user = (User) principal;
							AccessToken accessToken = new AccessToken(DigestUtils.sha256Hex(user.getUsername() + System.currentTimeMillis()), null);
							result.put("code", 0);
							result.put("msg", "登入成功");
							result.put("data", accessToken);

							httpServletRequest.getSession().setAttribute("access_token", accessToken);

							out.write(JSON.toJSONString(result));
						} else {
							out.write("{\"code\":1001,\"msg\":\"用户名或密码错误！\"}");
						}
						out.flush();
						out.close();
					})
					.failureHandler((httpServletRequest, httpServletResponse, authenticationException) -> {
						httpServletResponse.setContentType("application/json;charset=utf-8");
						PrintWriter out = httpServletResponse.getWriter();
						out.write("{\"code\":1001,\"msg\":\"用户名或密码错误！\"}");
						out.flush();
						out.close();
					})
					.and()
					.logout()
					.logoutUrl("/logout")
					.logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
						httpServletResponse.setContentType("application/json;charset=utf-8");
						PrintWriter out = httpServletResponse.getWriter();
						httpServletRequest.getSession().removeAttribute("access_token");
						out.write("{\"code\":0,\"msg\":\"注销成功\"}");
						out.flush();
						out.close();
					})
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutSuccessUrl("/start/index.html#/user/login")
					.and()
					.csrf()
					.disable()
					.headers()
					.frameOptions()
					.disable();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(loginService());
		}

		@Bean
		protected LoginService loginService() {
			return new LoginService();
		}

		@Bean
		public static PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource("application.yml"), new ClassPathResource("fieldDef.yml"));
		configurer.setProperties(yaml.getObject());
		return configurer;
	}

	@RequestMapping(value = {"/"})
	public String index() {
		return "redirect:/start/index.html";
	}

	@GetMapping(value = "/getSessionAccessToken")
	@ResponseBody
	public ApiResult getSessionAccessToken(ServletRequest servletRequest) {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		AccessToken accessToken = (AccessToken) httpRequest.getSession().getAttribute("access_token");
		if (accessToken != null) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(accessToken).build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.DataNotExist.code()).msg(ApiResultCode.DataNotExist.msg()).build();
		}
	}
}
