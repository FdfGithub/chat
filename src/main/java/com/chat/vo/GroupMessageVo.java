package com.chat.vo;

public class GroupMessageVo extends MessageParent{
    private Integer groupId;//发给哪个群

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
