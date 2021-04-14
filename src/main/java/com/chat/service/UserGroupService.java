package com.chat.service;

import com.chat.common.Result;
import com.chat.pojo.UserGroup;

public interface UserGroupService {
    //用户根据群号Id，添加群
    Result joinGroup(UserGroup userGroup);
    //根据群id查找所有群成员的userId
    Result findByGroupId(Integer groupId);
    //获取当前用户的所有群聊
    Result findByUserId();
}
