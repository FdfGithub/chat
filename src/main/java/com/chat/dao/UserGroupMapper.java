package com.chat.dao;

import com.chat.pojo.UserGroup;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserGroupMapper extends Mapper<UserGroup> {
    List<Integer> findByGroupId(Integer groupId);

    List<Integer> findByUserId(Integer userId);
}
