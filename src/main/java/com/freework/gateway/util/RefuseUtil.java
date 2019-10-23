package com.freework.gateway.util;

import com.freework.common.loadon.result.entity.ResultVo;
import com.freework.common.loadon.result.enums.ResultStatusEnum;
import com.freework.common.loadon.result.util.ResultUtil;
import com.freework.common.loadon.util.JsonUtil;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletResponse;

/**
 * @author daihongru
 */
public class RefuseUtil {
    public static void handle(RequestContext requestContext) {
        HttpServletResponse response = requestContext.getResponse();
        requestContext.setSendZuulResponse(false);
        response.setContentType("text/html; charset=utf-8");
        ResultVo resultVo = ResultUtil.error(ResultStatusEnum.UNAUTHORIZED);
        String tag = "Gateway result";
        resultVo.setData(tag);
        requestContext.setResponseBody(JsonUtil.objectToJson(resultVo));
    }
}
