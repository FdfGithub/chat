package com.chat.service;

import com.chat.common.Result;

public interface FriendService {
    //添加好友
    Result addFriend(Integer friendId);
    //删除好友
    Result deleteFriend(Integer friendId);
    //显示我的所有好友
    Result selectAll();
    //根据邮箱查找好友
    //根据手机号查找好友
    Result selectByEmail(String account);
    //根据二维码查找好友
}
