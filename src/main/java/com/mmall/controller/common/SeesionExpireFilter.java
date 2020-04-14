package com.mmall.controller.common;

import com.mmall.common.Const;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 项目名：    mmall_learning
 * 包名：      com.mmall.controller.common
 * 文件名：    SeesionExpireFilter
 * 创建时间:   2020-04-13-17:47
 *
 * @author zhangsiqi
 * 描述：
 */

public class SeesionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        // 登录用户不为空，即已登陆了用户
        if (StringUtils.isNotEmpty(loginToken)) {
            String usreJsonStr = RedisShardedPoolUtil.get(loginToken);
            User user = JsonUtil.String2Obj(usreJsonStr, User.class);
            // 如果user不为空则重置session的时间，即调用expire命令
            RedisShardedPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
