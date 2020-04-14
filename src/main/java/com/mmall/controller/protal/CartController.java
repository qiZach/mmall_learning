package com.mmall.controller.protal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import com.mmall.vo.CartVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mmall.common.ServerResponse;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangsiqi
 */
@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @ResponseBody
    @RequestMapping("add.do")
    public ServerResponse<CartVo> add(HttpServletRequest request, Integer productId, Integer count) {
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
        return iCartService.add(user.getId(), productId, count);
    }

    @ResponseBody
    @RequestMapping("delete_product.do")
    public ServerResponse<CartVo> deleteProduct(HttpServletRequest request, String productIds) {
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
        return iCartService.deleteProduct(user.getId(), productIds);
    }

    @ResponseBody
    @RequestMapping("update.do")
    public ServerResponse<CartVo> update(HttpServletRequest request, Integer productId, Integer count) {
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
        return iCartService.update(user.getId(), productId, count);
    }

    @ResponseBody
    @RequestMapping("list.do")
    public ServerResponse<CartVo> list(HttpServletRequest request) {
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
        return iCartService.list(user.getId());
    }

    /**
     * 全选
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("select_all.do")
    public ServerResponse<CartVo> selectAll(HttpServletRequest request) {
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
        return iCartService.selectOrUnSelect(user.getId(), Const.Cart.CHECKED, null);
    }

    /**
     * 全反选
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("un_select_all.do")
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request) {
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
        return iCartService.selectOrUnSelect(user.getId(), Const.Cart.UN_CHECKED, null);
    }

    /**
     * 单独选
     *
     * @param
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping("select.do")
    public ServerResponse<CartVo> select(HttpServletRequest request, Integer productId) {
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
        return iCartService.selectOrUnSelect(user.getId(), Const.Cart.CHECKED, productId);
    }

    /**
     * 单独反选
     *
     * @param
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping("un_select.do")
    public ServerResponse<CartVo> unSelect(HttpServletRequest request, Integer productId) {
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
        return iCartService.selectOrUnSelect(user.getId(), Const.Cart.UN_CHECKED, productId);
    }

    /**
     * 查询当前用户的购物车里的产品数量,如果一个产品有10个,那么数量就是10.
     *
     * @param
     * @return
     */

    @ResponseBody
    @RequestMapping("get_cart_product_count.do")

    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request) {
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
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProduct(user.getId());
    }

}

