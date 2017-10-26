package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by geely
 */
public interface IUserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回登录用户
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 用户注册
     *
     * @param user 注册用户
     * @return 返回注册信息
     */
    ServerResponse<String> register(User user);

    /**
     * 检查用户名或邮箱是否合法
     *
     * @param str  用户名或邮箱
     * @param type 类型: username or email
     * @return 返回合法信息
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 根据用户名找到修改密码问题
     *
     * @param username 用户名
     * @return 用户不存在, 成功返回问题, 修改密码问题为空
     */
    ServerResponse<String> selectQuestion(String username);

    /**
     * 根据回答判断是否允许修改密码
     *
     * @param username 用户名
     * @param question 问题
     * @param answer   回答
     * @return 问题答案正确返回forgetToken 问题的答案错误
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 忘记重置密码
     *
     * @param username    用户名
     * @param passwordNew 新密码
     * @param forgetToken forgetToken
     * @return 修改密码成功, Token失效, 用户不存在
     */
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 已登录用户修改密码
     *
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @param user        已登录用户信息
     * @return 旧密码错误, 密码更新成功, 密码更新失败
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * 更新用户信息
     *
     * @param user 已登录用户
     * @return 更新信息后的用户
     */
    ServerResponse<User> updateInformation(User user);

    /**
     * 获得用户信息
     *
     * @param userId 用户ID
     * @return 用户
     */
    ServerResponse<User> getInformation(Integer userId);

    /**
     * 判断用户身份
     * @param user 用户信息
     * @return 管理员.success  客户.error
     */
    ServerResponse checkAdminRole(User user);
}
