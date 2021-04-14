package com.chat.service.impl;

import com.chat.common.Result;
import com.chat.common.ServerResponse;
import com.chat.dao.GroupMapper;
import com.chat.dao.UserGroupMapper;
import com.chat.pojo.Group;
import com.chat.pojo.UserGroup;
import com.chat.service.GroupService;
import com.chat.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public Result createGroup(Group group) {
        group.setGroupOwnerId(SessionUtil.getUserId());
        group.setCreateTime(LocalDateTime.now());
        int result = groupMapper.insertSelective(group);
        if (result>0){
            return ServerResponse.createSuccess("创建成功");
        }
        throw new RuntimeException("创建失败");
    }

    @Override
    public Result deleteGroup(Integer groupId) {
        //验证该群主是不是当前用户
        //如果是，才能进行删除
        UserGroup userGroup = new UserGroup();
        userGroup.setUserId(SessionUtil.getUserId());
        userGroup.setGroupId(groupId);
        int result = userGroupMapper.selectCount(userGroup);
        if (result<=0){
            throw new RuntimeException("没有权限");
        }
        result = groupMapper.deleteByPrimaryKey(groupId);
        if (result>0){
            return ServerResponse.createSuccess("删除成功");
        }
        throw new RuntimeException("删除失败");
    }

    @Override
    public Result findAll(List<Integer> groupIds) {
        List<Group> groups = groupMapper.findInfo(groupIds);
        if (groups==null||groups.size()==0){
            throw new RuntimeException("该群不存在");
        }
        return ServerResponse.createSuccess(groups);
    }
}
