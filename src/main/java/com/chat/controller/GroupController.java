package com.chat.controller;

import com.chat.common.Result;
import com.chat.pojo.Group;
import com.chat.service.GroupService;
import com.chat.service.UserGroupService;
import com.chat.util.DataAuthUtil;
import com.chat.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private UserGroupService userGroupService;

    @PostMapping
    public Result createGroup(Group group){
        SessionUtil.authLogin();
        DataAuthUtil.authParams(group);
        return groupService.createGroup(group);
    }

    @GetMapping("/{groupId}")
    public Result deleteGroup(@PathVariable Integer groupId){
        SessionUtil.authLogin();
        DataAuthUtil.authParams(groupId);
        return groupService.deleteGroup(groupId);
    }

    //获取当前用户的所有群聊
    //获取当前用户的所有群聊
    @GetMapping("/all")
    public Result findAll(){
        SessionUtil.authLogin();
        Result result = userGroupService.findByUserId();
        return groupService.findAll((List<Integer>) result.getData());
    }
}
