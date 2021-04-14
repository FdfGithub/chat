package com.chat.controller;

import com.chat.common.Const;
import com.chat.common.Result;
import com.chat.common.ServerResponse;
import com.chat.pojo.User;
import com.chat.service.UserService;
import com.chat.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailUtil emailUtil;


    @PostMapping("/register")
    public Result register(String account,String password){
        DataAuthUtil.authParams(account,password);
        return userService.register(account,password);
    }

    @PostMapping("/login")
    public Result login(String account,String password){
        DataAuthUtil.authParams(account,password);
        return userService.login(account,password);
    }

    @GetMapping("/{account}/isExist")
    public Result isExist(@PathVariable String account){
        DataAuthUtil.authParams(account);
        return userService.isExit(account);
    }

    //获取注册验证码
    @GetMapping("/{account}/register")
    public Result getRegisterCode(@PathVariable String account){
        DataAuthUtil.authParams(account);
        SessionUtil.set(Const.REGISTER_CODE,emailUtil.sendCode(account, Const.REGISTER_SUBJECT),60);
        return ServerResponse.createSuccess("验证码已发送");
    }

    //获取更换邮箱的验证码
    @GetMapping("/{account}/email")
    public Result getChangeEmailCode(@PathVariable String account){
        DataAuthUtil.authParams(account);
        SessionUtil.set(Const.UPDATE_EMAIL_CODE,emailUtil.sendCode(account, Const.SAFE_SUBJECT),60);
        return ServerResponse.createSuccess("验证码已发送");
    }

    //检查注册的验证码
    @PostMapping("/inspect/register")
    public Result inspectRegisterCode(String code){
        DataAuthUtil.authParams(code);
        return userService.inspect(code,Const.REGISTER_CODE);
    }

    //检查更换邮箱的验证码
    @PostMapping("/inspect/email")
    public Result inspectChangeEmailCode(String code){
        DataAuthUtil.authParams(code);
        return userService.inspect(code,Const.UPDATE_EMAIL_CODE);
    }




    @GetMapping("/exit")
    public Result exit(){
        SessionUtil.authLogin();
        SessionUtil.remove(Const.USER_ID);
        return ServerResponse.createSuccess();
    }


    //程序的登录入口
//    @GetMapping("/isLogin")
//    public void isLogin(HttpServletResponse response, HttpSession session) throws IOException {
//        if (session.getAttribute(Const.USER_ID)==null){
//            response.sendRedirect("/login.html");
//            return;
//        }
//       response.sendRedirect("/msg.html");
//    }

    //通过id/手机号/邮箱查找用户
    @GetMapping("/{mark}/findUser")
    public Result findUser(@PathVariable String mark){
        SessionUtil.authLogin();
        DataAuthUtil.authParams(mark);
        return userService.findUser(mark);
    }


    //查找指定用户的个人信息
    @GetMapping("/{userId}")
    public Result findUser(@PathVariable Integer userId){
        SessionUtil.authLogin();
        DataAuthUtil.authParams(userId);
        return userService.findUser(userId);
    }


    @GetMapping("/findHeadUrl")
    public Result findHeadUrl(){
        SessionUtil.authLogin();
        return userService.findHeadUrl();
    }

    //修改用户自己的个人信息
    @PostMapping
    public Result updateUser(User user){
        SessionUtil.authLogin();
        DataAuthUtil.authParams(user);
        if (user.getUserId()!=null){
            throw new RuntimeException("参数错误");
        }
        //如果出生日期设置了，自动设置星座
        if (user.getUserBorn()!=null){
            user.setUserSign(SignUtil.returnSign(user.getUserBorn()));
        }
        user.setUserId(SessionUtil.getUserId());
        return userService.updateUser(user);
    }
}
