package com.chat.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "chat_friend")
public class Friend {
//    friend_id  friend_id0  friend_id1  update_time  create_time
    @Id
    private Integer friendId;
    private Integer friendId0;
    private Integer friendId1;
    private User user;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getFriendId0() {
        return friendId0;
    }

    public void setFriendId0(Integer friendId0) {
        this.friendId0 = friendId0;
    }

    public Integer getFriendId1() {
        return friendId1;
    }

    public void setFriendId1(Integer friendId1) {
        this.friendId1 = friendId1;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}

