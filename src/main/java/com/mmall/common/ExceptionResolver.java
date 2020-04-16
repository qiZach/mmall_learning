package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目名：    mmall_learning
 * 包名：      com.mmall.common
 * 文件名：    ExceptionResolver
 * 创建时间:   2020-04-15-17:08
 *
 * @author zhangsiqi
 * 描述：全局异常处理类
 */
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("{} Exception", httpServletRequest.getRequestURI(), e);
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        // 当使用gjackson2.x时使用MappingJackson2JsonView, 本项目pom.xml中使用的是1.9
        modelAndView.addObject("sattus", ResponseCode.ERROR.getCode());
        modelAndView.addObject("msg", "接口异常,详情请查看服务端日志");
        modelAndView.addObject("data", e.toString());
        return modelAndView;
    }
}
