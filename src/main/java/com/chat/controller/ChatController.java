package com.chat.controller;

import com.chat.common.Result;
import com.chat.common.ServerResponse;
import com.chat.conf.ChatWebSocket;
import com.chat.pojo.User;
import com.chat.service.UserService;
import com.chat.util.DataAuthUtil;
import com.chat.util.SessionUtil;
import com.chat.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatting")
public class ChatController {
    @Autowired
    private ChatWebSocket chatWebSocket;
    @Autowired
    private UserService userService;

    @PostMapping
    public Result sendMessage(String message, Integer friendId) {
        SessionUtil.authLogin();
        DataAuthUtil.authParams(message, friendId);
        Result result = userService.findUser(SessionUtil.getUserId());
        if (result.isFlag()) {
            UserVo vo = (UserVo) result.getData();
            chatWebSocket.sendMessage(message, friendId, vo.getUserName(),vo.getUserHeadUrl());
            return ServerResponse.createSuccess();
        }
        throw new RuntimeException("系统错误");
    }


    @PostMapping("/group")
    public Result sendGroupMessage(String message, Integer groupId) {
        SessionUtil.authLogin();
        DataAuthUtil.authParams(message, groupId);
        Result result = userService.findUser(SessionUtil.getUserId());
        if (result.isFlag()) {
            UserVo vo = (UserVo) result.getData();
            chatWebSocket.sendGroupMessage(message, groupId, vo.getUserName(),vo.getUserHeadUrl());
            return ServerResponse.createSuccess();
        }
        throw new RuntimeException("系统错误");
    }

}
