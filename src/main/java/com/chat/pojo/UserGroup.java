package com.chat.pojo;

import org.apache.tomcat.jni.Local;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

//用户和群的中间表
@Table(name = "chat_user_group")
public class UserGroup {
//    user_group_id  user_id  group_id  update_time  create_time
    @Id
    private Integer userGroupId;
    private Integer userId;
    private Integer groupId;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

