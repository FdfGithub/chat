package com.chat.dao;

import com.chat.pojo.Friend;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FriendMapper extends Mapper<Friend> {
    List<Friend> selectAllByUserId(Integer userId);

    int findOne(@Param("id0") Integer id0,@Param("id1") Integer id1);

    int deleteRelation(@Param("id0") Integer id0,@Param("id1") Integer id1);
}
