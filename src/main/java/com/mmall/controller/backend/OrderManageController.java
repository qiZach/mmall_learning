package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 后台管理订单
 *
 * @author zhangsiqi
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IUserService iUserService;

    @ResponseBody
    @RequestMapping("list.do")
    public ServerResponse<PageInfo> orderList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录, 请登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 填充业务
            return iOrderService.manageList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }

    }

    @ResponseBody
    @RequestMapping("detail.do")
    public ServerResponse<OrderVo> orderDetail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录, 请登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 填充业务
            return iOrderService.manageDetail(orderNo);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ResponseBody
    @RequestMapping("search.do")
    public ServerResponse<PageInfo> orderSearch(HttpSession session, Long orderNo, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录, 请登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 填充业务
            return iOrderService.manageSearch(orderNo, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ResponseBody
    @RequestMapping("send_goods.do")
    public ServerResponse<String> sendGoods(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录, 请登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 填充业务
            return iOrderService.manageSendGoods(orderNo);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


}
