package com.freework.gateway.filter;

import com.freework.gateway.util.RefuseUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author daihongru
 */
@Component
public class EnterpriseTokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String url = request.getRequestURI();
        String vocationManageUrl = "vocation/edit";
        String enterpriseManageUrl = "enterprise/current";
        String cvitaeManageUrl = "cvitae/current";
        if (url.contains(vocationManageUrl) || url.contains(enterpriseManageUrl) ||
                url.contains(cvitaeManageUrl)) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String token = requestContext.getRequest().getHeader("etoken");
        if (StringUtils.isEmpty(token)) {
            RefuseUtil.handle(requestContext);
        }
        return null;
    }
}
