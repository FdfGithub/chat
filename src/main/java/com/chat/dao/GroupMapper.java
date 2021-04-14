package com.chat.dao;

import com.chat.pojo.Group;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GroupMapper extends Mapper<Group> {
    List<Group> findInfo(@Param("ids") List<Integer> ids);
}
