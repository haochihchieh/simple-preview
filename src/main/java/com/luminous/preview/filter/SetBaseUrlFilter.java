package com.luminous.preview.filter;

import com.luminous.preview.common.config.ConfigConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/29 13:36
 * @Description:
 */
public class SetBaseUrlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String baseUrl;
        if (ConfigConstants.getBaseUrl() != null) {
            baseUrl = ConfigConstants.getBaseUrl();
        } else {
            StringBuilder pathBuilder = new StringBuilder();
            pathBuilder.append(request.getScheme()).append("://").append(request.getServerName()).append(":")
                    .append(request.getServerPort()).append(((HttpServletRequest) request).getContextPath());
            baseUrl = pathBuilder.toString();
        }
        request.setAttribute("baseUrl", baseUrl);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
