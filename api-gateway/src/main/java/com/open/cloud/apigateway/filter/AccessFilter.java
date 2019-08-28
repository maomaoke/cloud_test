package com.open.cloud.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenkechao
 * @date 2019-08-24 20:43
 */
@Component
@Slf4j
public class AccessFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("Send {} request to {}", request.getMethod(), request.getRequestURI());

        String accessToken = request.getParameter("accessToken");
        if (StringUtils.isBlank(accessToken)) {
            log.warn("access token is null");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody("{\"msg\": \"认证失败\"}");
            HttpServletResponse response = requestContext.getResponse();
            response.setContentType("application/json;charset=utf-8");
            requestContext.setResponse(response);
            return null;
        }
        log.info("access token ok");
        return null;
    }
}

