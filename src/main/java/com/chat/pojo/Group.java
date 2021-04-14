package com.chat.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "chat_group")
public class Group {

//    group_id  group_name  update_time  create_time  group_owner_id
    @Id
    private Integer groupId;
    private String groupName;
    private String groupHeadUri;
    private Integer groupOwnerId;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;

    public String getGroupHeadUri() {
        return groupHeadUri;
    }

    public void setGroupHeadUri(String groupHeadUri) {
        this.groupHeadUri = groupHeadUri;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupOwnerId() {
        return groupOwnerId;
    }

    public void setGroupOwnerId(Integer groupOwnerId) {
        this.groupOwnerId = groupOwnerId;
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
