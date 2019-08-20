package org.scau.internshipsystem.common.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.scau.internshipsystem.common.domain.JsonResponse;
import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.common.exception.TokenException;
import org.scau.internshipsystem.common.util.DateUtil;
import org.scau.internshipsystem.common.util.EncryptUtil;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
public class TokenFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws TokenException {
        String token = ((HttpServletRequest) servletRequest).getHeader("Authentication");
        //未登录
        if(StringUtils.isEmpty(token)){
            return false;
        }
        token = EncryptUtil.decrpyt(token);
        if(!JWTUtil.verify(token)){
            throw new TokenException("token无效...");
        }
        JWTToken jwtToken = JWTUtil.parseToken(token);
        log.info("该token过期时间...{}", DateUtil.formatFullTime(jwtToken.getExpireTime(),DateUtil.FULL_TIME_SPLIT_PATTERN));
        if(new Date().after(jwtToken.getExpireTime())){
            throw new TokenException("token已过期...");
        }

        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //返送信息
        JsonResponse.sendJsonResponse((HttpServletResponse)servletResponse, JsonResult.failure("请先登录..."));
        return false;
    }

}
