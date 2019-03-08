package cn.sambo.difference.platform.security;

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

    private static final String[] checkURIs = {
            "/platform/codeList/**",
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

        // 如果至少匹配一个，则检查access_token
        if (mached) {
            AccessToken accessToken = (AccessToken) httpRequest.getSession().getAttribute("access_token");
            if (accessToken != null && accessToken.getAccess_token().equals(httpRequest.getParameter("access_token"))) {
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
