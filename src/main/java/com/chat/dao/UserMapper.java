package com.chat.dao;

import com.chat.pojo.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    Integer findOne(@Param("account") String account,@Param("password") String password);

    List<User> findList(@Param("userIds") List<Integer> userIds);

    int findOneByAccount(String account);

    List<User> findUser(String mark);

    int findOnById(Integer id);

    String findHeadUrl(Integer id);
}
