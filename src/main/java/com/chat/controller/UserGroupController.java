package com.chat.controller;

import com.chat.common.Result;
import com.chat.pojo.UserGroup;
import com.chat.service.UserGroupService;
import com.chat.util.DataAuthUtil;
import com.chat.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userGroup")
public class UserGroupController {
    @Autowired
    private UserGroupService userGroupService;

    //用户加群
    @PostMapping
    public Result joinGroup(UserGroup userGroup){
        SessionUtil.authLogin();
        DataAuthUtil.authParams(userGroup);
        return userGroupService.joinGroup(userGroup);
    }
}
