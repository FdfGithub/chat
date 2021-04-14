package com.chat.service;

import com.chat.common.Result;
import com.chat.pojo.User;

public interface UserService {
    //注册
    Result register(String account,String password);
    //登录
    Result login(String account,String password);
    //检验验证码正确性
    Result inspect(String code,String type);
    //判断账号是否被注册过
    Result isExit(String account);

    String selectNameById(Integer id);

    Result findUser(String mark);

    //根据id查看用户的基本信息
    Result findUser(Integer userId);

    //返回当前用户的头像
    Result findHeadUrl();

    //修改用户信息
    Result updateUser(User user);
}
