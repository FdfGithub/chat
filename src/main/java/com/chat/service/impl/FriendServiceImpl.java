package com.chat.service.impl;

import com.chat.common.Result;
import com.chat.common.ServerResponse;
import com.chat.dao.FriendMapper;
import com.chat.dao.UserMapper;
import com.chat.pojo.Friend;
import com.chat.pojo.User;
import com.chat.service.FriendService;
import com.chat.util.SessionUtil;
import com.chat.vo.FriendVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserMapper userMapper;

    //当用户点击同意的时候执行该方法
    @Override
    public Result addFriend(Integer friendId) {
        Friend friend = new Friend();
        friend.setFriendId0(SessionUtil.getUserId());
        friend.setFriendId1(friendId);
        friend.setCreateTime(LocalDateTime.now());
        int result = friendMapper.insertSelective(friend);
        if (result > 0) {
            return ServerResponse.createSuccess("添加好友成功");
        }
        throw new RuntimeException("添加好友失败");
    }

    @Override
    public Result deleteFriend(Integer friendId) {
        int result = friendMapper.deleteRelation(SessionUtil.getUserId(), friendId);
        if (result > 0) {
            return ServerResponse.createSuccess("删除成功");
        }
        throw new RuntimeException("删除失败");
    }

    @Override
    public Result selectAll() {
        //分页操作
        List<Friend> friends = friendMapper.selectAllByUserId(SessionUtil.getUserId());
        if (friends == null || friends.size() == 0) {
            throw new RuntimeException("你还没有好友");
        }
        return ServerResponse.createSuccess(assembleFriendVoList(friends));
    }

    @Override
    public Result selectByEmail(String account) {
        return null;
    }

    private List<FriendVo> assembleFriendVoList(List<Friend> friends) {
        List<Integer> userIds = Lists.newArrayList();
        for (Friend friend : friends) {
            userIds.add(friend.getFriendId0() + friend.getFriendId1() - SessionUtil.getUserId());
        }
        List<User> users = userMapper.findList(userIds);
        List<FriendVo> vos = Lists.newArrayList();
        for (User user : users) {
            vos.add(assembleFriendVo(user));
        }
        return vos;
    }

    @Value("${headUrl_pre}")
    private String headUrlPre;

    private FriendVo assembleFriendVo(User user) {
        FriendVo vo = new FriendVo();
        vo.setUserId(user.getUserId());
        vo.setHeadUrl(headUrlPre + user.getUserHeadUri());
        if (user.getUserName() == null) {
            vo.setName(String.valueOf(user.getUserId()));
        } else {
            vo.setName(user.getUserName());
        }
        return vo;
    }
}
