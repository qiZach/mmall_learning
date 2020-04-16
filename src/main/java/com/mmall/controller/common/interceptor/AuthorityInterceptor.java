package com.mmall.controller.common.interceptor;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.controller.backend.UserManageController;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 项目名：    mmall_learning
 * 包名：      com.mmall.controller.common.interceptor
 * 文件名：    AuthorityInterceptor
 * 创建时间:   2020-04-15-18:04
 *
 * @author zhangsiqi
 * 描述：
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService iUserService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("preHandle");
        // 请求中Controller中的方法名
        HandlerMethod handlerMethod = (HandlerMethod) o;
        //解析HandlerMethod
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();
        // 解析参数, 具体的参数key以及value是什么,打印日志
        StringBuffer requestParamBuffer = new StringBuffer();
        Map<String, String[]> paramMap = httpServletRequest.getParameterMap();
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;
            //request这个参数的map,里面的value返回String[]
            Object value = entry.getValue();
            String[] strs = (String[]) value;
            mapValue = Arrays.toString(strs);
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }

        if (StringUtils.equals(className, "UserManageController") &&
                StringUtils.equals(methodName, "login")) {
            log.info("权限拦截器拦截到请求,className:{},methodName:{}", className, methodName);
            //如果是拦截到登录请求,不打印参数,因为参数中有密码,防止账号泄露
            return true;
        }
        log.info("权限拦截器拦截到请求,className:{},methodName:{},param:{}",
                className, methodName, requestParamBuffer.toString());

        User user = null;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isNotEmpty(loginToken)) {
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            user = JsonUtil.String2Obj(userJsonStr, User.class);
        }
        if (user == null || (user.getRole().intValue()) != Const.Role.ROLE_ADMIN) {
            // 返回false,不会调用controller
            // 这里要reset,否则报异常,getWriter() has already been valled for this response.
            httpServletResponse.reset();
            // 设置编码,否则会乱码
            httpServletResponse.setCharacterEncoding("UTF-8");
            // 设置返回类型json
            httpServletResponse.setContentType("application/json;charset=UTF-8");

            PrintWriter out = httpServletResponse.getWriter();
            if (user == null) {
                if (StringUtils.equals(className, "ProductManageController") &&
                        StringUtils.equals(methodName, "richTextImg_upload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "请登录管理员");
                    out.println(JsonUtil.obj2String(resultMap));
                } else {
                    // 使用JsonUtil将ServerResponse返回信息序列化为Json, 用PrintWriter打印出去,相当于返回Json数据
                    out.println(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户未登录")));
                }
            } else {
                if (StringUtils.equals(className, "ProductManageController") &&
                        StringUtils.equals(methodName, "richTextImg_upload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "无权限操作");
                    out.println(JsonUtil.obj2String(resultMap));
                } else {
                    out.println(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户无权限")));
                }
            }
            out.flush();
            out.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("afterCompletion");
    }
}
