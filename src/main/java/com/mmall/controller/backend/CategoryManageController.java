package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zhangsiqi
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @ResponseBody
    @RequestMapping("add_category.do")
    public ServerResponse addCategory(HttpServletRequest request, String categoryName, @RequestParam(value = "prarentId", defaultValue = "0") int prarentId) {
        return iCategoryService.addCategory(categoryName, prarentId);
    }

    @ResponseBody
    @RequestMapping("set_category_name.do")
    public ServerResponse setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {
        return iCategoryService.updateCategoryName(categoryId, categoryName);
    }

    @ResponseBody
    @RequestMapping("get_category.do")
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        // 查询子节点的category信息, 并且不递归, 保持平级
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @ResponseBody
    @RequestMapping("get__deep_category.do")
    public ServerResponse getChildrenDeepCategory(HttpServletRequest request,
                                                  @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }
}
