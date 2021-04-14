package com.chat.service;

import com.chat.common.Result;
import com.chat.pojo.Group;

import java.util.List;

public interface GroupService {
    //通过用户id，创建群
    Result createGroup(Group group);

    //通过userId，删除群
    Result deleteGroup(Integer groupId);

    //拿到指定群id的群聊信息
    Result findAll(List<Integer> groupIds);

}
