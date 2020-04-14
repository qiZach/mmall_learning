package com.mmall.controller.protal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zhangsiqi
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    @ResponseBody
    @RequestMapping("add.do")
    public ServerResponse add(HttpServletRequest request, Shipping shipping) {
        // 先获取login.do中保存的cookie中的sessionID
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        // 从Redis中取得对应user对象字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        // 反序列化
        User user = JsonUtil.String2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(), shipping);
    }

    @ResponseBody
    @RequestMapping("del.do")
    public ServerResponse<String> del(HttpServletRequest request, Integer shippingId) {
        // 先获取login.do中保存的cookie中的sessionID
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        // 从Redis中取得对应user对象字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        // 反序列化
        User user = JsonUtil.String2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(user.getId(), shippingId);
    }

    @ResponseBody
    @RequestMapping("update.do")
    public ServerResponse<String> update(HttpServletRequest request, Shipping shipping) {
        // 先获取login.do中保存的cookie中的sessionID
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        // 从Redis中取得对应user对象字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        // 反序列化
        User user = JsonUtil.String2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(user.getId(), shipping);
    }

    @ResponseBody
    @RequestMapping("select.do")
    public ServerResponse<Shipping> select(HttpServletRequest request, Integer shippingId) {
        // 先获取login.do中保存的cookie中的sessionID
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        // 从Redis中取得对应user对象字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        // 反序列化
        User user = JsonUtil.String2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(user.getId(), shippingId);
    }

    @ResponseBody
    @RequestMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         HttpServletRequest request) {
        // 先获取login.do中保存的cookie中的sessionID
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        // 从Redis中取得对应user对象字符串
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        // 反序列化
        User user = JsonUtil.String2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(user.getId(), pageNum, pageSize);
    }


}

