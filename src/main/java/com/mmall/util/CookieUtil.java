package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目名：    mmall_learning
 * 包名：      com.mmall.util
 * 文件名：    CookieUtil
 * 创建时间:   2020-04-13-16:08
 *
 * @author zhangsiqi
 * 描述：CookieUtil 解决由于nginx将登陆时的SessionID打包存入客户端，防止
 * 后续请求打到其他Tomcat服务器时登录信息丢失的问题。
 */
@Slf4j
public class CookieUtil {

    /**
     * tomcat 8.5顶级域名不支持.开头。 mall.com为顶级域名
     */
    private final static String COOKIE_DOMAIN = "mall.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieNmae:{}, cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieNmae:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");
        ck.setHttpOnly(true);
        //-1代表永久，单位是秒
        // 如果maxage不设置，cookie就不会写入硬盘，而是写在内存中，只在当前页面有效。
        ck.setMaxAge(-1);
        log.info("write cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    // 设置为0，代表删除此cookie
                    ck.setMaxAge(0);
                    log.info("return cookieNmae:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
