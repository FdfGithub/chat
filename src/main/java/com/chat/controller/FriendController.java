package com.chat.controller;

import com.chat.common.Result;
import com.chat.service.FriendService;
import com.chat.util.DataAuthUtil;
import com.chat.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @GetMapping("/all")
    public Result selectAll(HttpServletResponse response){
        SessionUtil.authLogin();
        return friendService.selectAll();
    }

    @GetMapping("/{id}/add")
    public Result addFriend(@PathVariable Integer id){
        SessionUtil.authLogin();
        DataAuthUtil.authParams(id);
        return friendService.addFriend(id);
    }

    //删除好友
    @GetMapping("/{id}/delete")
    public Result deleteFriend(@PathVariable Integer id){
        SessionUtil.authLogin();
        DataAuthUtil.authParams(id);
        return friendService.deleteFriend(id);
    }

}
