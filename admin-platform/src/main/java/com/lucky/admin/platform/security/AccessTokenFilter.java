package com.lucky.admin.platform.security;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class AccessTokenFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(AccessTokenFilter.class);

    private static final String[] unCheckURIs = {
            "/lucky/",
            "/lucky/src/**",
            "/lucky/start/**",
            "/lucky/login",
            "/lucky/logout",
            "/lucky/user/register",
            "/lucky/user/forget"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        servletResponse.setContentType("application/json;charset=utf-8");

        // 获取请求url
        String requestURI = httpRequest.getRequestURI();

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        // 匹配
        boolean matched = true;
        for (String unCheckURI : unCheckURIs) {
            if (antPathMatcher.match(unCheckURI, requestURI)) {
                matched = false;
                break;
            }
        }

        // 如果至少匹配一个，则检查access_token
        if (matched) {
            String accessToken = (String) httpRequest.getSession().getAttribute("access_token");
            if (accessToken != null && accessToken.equals(httpRequest.getParameter("access_token"))) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                PrintWriter out = servletResponse.getWriter();
                Map<String, Object> result = new HashMap<>();
                result.put("code", 1001);
                result.put("msg", "令牌无效");
                out.write(JSON.toJSONString(result));
                return;
            }

        // 如果一个都没有匹配上，则放行
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
    }
}
