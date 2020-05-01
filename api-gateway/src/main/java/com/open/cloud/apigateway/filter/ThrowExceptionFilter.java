package com.open.cloud.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.RequestContent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author CHEN-KE-CHAO
 * @date 2019-08-27-上午11:12
 */
@Slf4j
@Component
public class ThrowExceptionFilter extends ZuulFilter {
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
        RequestContext ctx = RequestContext.getCurrentContext();
        log.info("准备抛出异常");
        doSomething();
        return null;
    }

    private void doSomething() {
        throw new RuntimeException("出现异常[]");
    }
}
