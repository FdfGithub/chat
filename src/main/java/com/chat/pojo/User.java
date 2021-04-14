package com.chat.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "chat_user")
public class User {
//    user_id  user_name  user_email  user_tel  user_pwd  update_time  create_time
    //user_gender  user_sign  user_born  user_motto
    @Id
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userTel;
    private String userPwd;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
    private String userGender;
    private String userSign;
    private LocalDate userBorn;
    private String userMotto;
    private String userHeadUri;

    public String getUserHeadUri() {
        return userHeadUri;
    }

    public void setUserHeadUri(String userHeadUri) {
        this.userHeadUri = userHeadUri;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public LocalDate getUserBorn() {
        return userBorn;
    }

    public void setUserBorn(LocalDate userBorn) {
        this.userBorn = userBorn;
    }

    public String getUserMotto() {
        return userMotto;
    }

    public void setUserMotto(String userMotto) {
        this.userMotto = userMotto;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
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
