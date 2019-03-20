package com.lucky.admin.platform.security;

import com.alibaba.fastjson.JSON;
import com.lucky.admin.platform.domain.PlatformPermission;
import com.lucky.admin.platform.domain.PlatformRole;
import com.lucky.admin.platform.domain.PlatformUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

public class GrantedAuthorityFilter extends GenericFilterBean {

    private final static Logger logger = LoggerFactory.getLogger(GrantedAuthorityFilter.class);

    private static final String[] checkURIs = {
            "/platform/machine/**",
            "/platform/crop/**",
            "/platform/permission/**",
            "/platform/role/**",
            "/platform/user/**",
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        servletResponse.setContentType("application/json;charset=utf-8");

        // 获取请求url
        String requestURI = httpRequest.getRequestURI();

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        // 匹配
        boolean mached = false;
        for (String checkURI : checkURIs) {
            if (antPathMatcher.match(checkURI, requestURI)) {
                mached = true;
                break;
            }
        }

        if (mached) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal != null && principal instanceof UserDetails) {
                PlatformUser platformUser = (PlatformUser) principal;

                logger.debug("-----------> User: {}", platformUser.getUsername());

                if ("admin".equals(platformUser.getUsername())) {
                    logger.debug("-----------> admin拥有一切权限");
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
                int count = 0;
                for (GrantedAuthority authority : platformUser.getAuthorities()) {
                    PlatformRole platformRole = (PlatformRole) authority;
                    logger.debug("-----------> Role: {}({})", platformRole.getRoleName(), platformRole.getRole());
                    for (PlatformPermission permission : platformRole.getPlatformPermissions()) {
                        logger.debug("-----------> Permission: {}({})", permission.getName(), permission.getUrl());

                        String[] urls = StringUtils.split(permission.getUrl(), ',');

                        for (String url : urls) {
                            if (antPathMatcher.match(url, requestURI)) {
                                count++;
                            }
                        }
                    }
                }
                if (count > 0) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                } else {
                    PrintWriter out = servletResponse.getWriter();
                    Map<String, Object> result = new HashMap<>();
                    result.put("code", 1002);
                    result.put("msg", "权限不足");
                    out.write(JSON.toJSONString(result));

                    return;
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
    }
}
