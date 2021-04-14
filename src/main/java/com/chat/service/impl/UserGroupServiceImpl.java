package com.chat.service.impl;

import com.chat.common.Result;
import com.chat.common.ServerResponse;
import com.chat.dao.UserGroupMapper;
import com.chat.pojo.UserGroup;
import com.chat.service.UserGroupService;
import com.chat.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public Result joinGroup(UserGroup userGroup) {
        userGroup.setUserId(SessionUtil.getUserId());
        //应该检查当前用户是否已经加入了该群
        //如果已经加入，就应该报异常
        int result = userGroupMapper.selectCount(userGroup);
        if (result>0){
            throw new RuntimeException("当前群你已经加入，加入失败");
        }
        userGroup.setCreateTime(LocalDateTime.now());
        result = userGroupMapper.insertSelective(userGroup);
        if (result>0){
            return ServerResponse.createSuccess("加入该群成功");
        }
        throw new RuntimeException("加入失败");
    }

    @Override
    public Result findByGroupId(Integer groupId) {
        List<Integer> memberIds = userGroupMapper.findByGroupId(groupId);
        if (memberIds==null||memberIds.size()==0){
            throw new RuntimeException("当前群不存在");
        }
        return ServerResponse.createSuccess(memberIds);
    }

    @Override
    public Result findByUserId() {
        List<Integer> groupIds = userGroupMapper.findByUserId(SessionUtil.getUserId());
        if (groupIds==null||groupIds.size()==0){
            throw new RuntimeException("该用户还没有任何群聊");
        }
        return ServerResponse.createSuccess(groupIds);
    }
}
