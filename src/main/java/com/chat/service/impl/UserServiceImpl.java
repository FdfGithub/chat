package com.chat.service.impl;

import com.chat.common.Const;
import com.chat.common.Result;
import com.chat.common.ServerResponse;
import com.chat.common.StatusCode;
import com.chat.dao.FriendMapper;
import com.chat.dao.UserMapper;
import com.chat.pojo.User;
import com.chat.service.UserService;
import com.chat.util.*;
import com.chat.vo.StrangerVo;
import com.chat.vo.UserVo;
import com.google.common.collect.Lists;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private FriendMapper friendMapper;

    @Value("${headUrl_pre}")
    private String headUrlPre;

    @Value("${headUri_default}")
    private String defaultHeadUri;

    @Override
    public Result register(String account, String password) {
        User user = new User();
        if (RegularUtil.isEmail(account)) {
            user.setUserEmail(account);
        } else if (RegularUtil.isTel(account)) {
            user.setUserTel(account);
        } else {
            throw new RuntimeException("账号格式错误");
        }
        //默认头像
        user.setUserHeadUri(defaultHeadUri);
        user.setUserPwd(md5Util.MD5EncodeUtf8(password));
        user.setCreateTime(LocalDateTime.now());
        int result = userMapper.insertSelective(user);
        if (result > 0) {
            SessionUtil.remove("token_" + Const.REGISTER_CODE);//去除token
            return ServerResponse.createSuccess("注册成功");
        }
        throw new RuntimeException("注册失败");
    }

    @Override
    public Result login(String account, String password) {
        Integer userId = userMapper.findOne(account, md5Util.MD5EncodeUtf8(password));
        if (userId == null) {
            throw new RuntimeException("密码错误");
        }
        SessionUtil.set(Const.USER_ID, userId, -1);
        return ServerResponse.createSuccess("登录成功", userId);
    }

    @Override
    public Result inspect(String code, String type) {
        String localCode = (String) SessionUtil.get(type);
        if (StringUtils.isEmpty(localCode)) {
            throw new RuntimeException("验证码已失效");
        }
        if (!StringUtils.equals(code, localCode)) {
            throw new RuntimeException("验证码不正确");
        }
        SessionUtil.remove(type);
        //存一份token,时间10分钟
        SessionUtil.set("token_" + type, true, 10 * 60);
        return ServerResponse.createSuccess();
    }

    @Override
    public Result isExit(String account) {
        if (!(RegularUtil.isTel(account) || RegularUtil.isEmail(account))) {
            throw new RuntimeException("账号格式错误");
        }
        int result = userMapper.findOneByAccount(account);
        if (result > 0) {
            return Result.getInstance().setFlag(true).setCode(StatusCode.REGISTERED.getCode())
                    .setMessage(StatusCode.REGISTERED.getMsg());
        }
        return Result.getInstance().setFlag(true).setCode(StatusCode.NO_REGISTER.getCode())
                .setMessage(StatusCode.NO_REGISTER.getMsg());
    }

    @Override
    public String selectNameById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user.getUserName() == null ? String.valueOf(user.getUserId()) : user.getUserName();
    }

    @Override
    public Result findUser(String mark) {
        List<User> users = userMapper.findUser("%" + mark + "%");
        if (users == null || users.size() == 0) {
            throw new RuntimeException("没找到");
        }
        return ServerResponse.createSuccess(assembleStrangerVoList(users));
    }

    @Override
    public Result findUser(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        return ServerResponse.createSuccess(assembleUserVo(user));
    }

    @Override
    public Result findHeadUrl() {
        String headUrl = headUrlPre + userMapper.findHeadUrl(SessionUtil.getUserId());
        return Result.getInstance().setFlag(true).setData(headUrl);
    }

    private UserVo assembleUserVo(User user) {
        UserVo vo = new UserVo();
        if (user.getUserEmail() == null) {
            user.setUserEmail("未填写");
        }
        if (user.getUserTel() == null) {
            user.setUserTel("未填写");
        }
        if (user.getUserName() == null) {
            user.setUserName(String.valueOf(user.getUserId()));
        }
        if (user.getUserMotto() == null) {
            user.setUserMotto("这个人比较懒，什么也没留下~");
        }
        if (user.getUserGender() == null) {
            user.setUserGender("保密");
        }
        if (user.getUserSign() == null) {
            user.setUserSign("未填写");
        }
        if (SessionUtil.getUserId().equals(user.getUserId())) {
            vo.setStatus(-1);
        } else {
            int result = friendMapper.findOne(SessionUtil.getUserId(), user.getUserId());
            if (result > 0) {
                vo.setStatus(result);
            }
        }
        vo.setUserHeadUrl(headUrlPre + user.getUserHeadUri());
        vo.setUserId(user.getUserId());
        vo.setUserName(user.getUserName());
        vo.setUserTel(user.getUserTel());
        vo.setUserEmail(user.getUserEmail());
        vo.setUserGender(user.getUserGender());
        vo.setUserMotto(user.getUserMotto());
        vo.setUserSign(user.getUserSign());
        vo.setUserBorn(user.getUserBorn() == null ? "石头里蹦出来的~" : DateTimeUtil.dateToStr(user.getUserBorn(), "yyyy-MM-dd"));
        return vo;
    }

    @Override
    public Result updateUser(User user) {
        if (userMapper.findOnById(user.getUserId()) <= 0) {
            throw new RuntimeException("该用户不存在");
        }
        //去掉Url的前缀
        if (user.getUserHeadUri() != null) {
            user.setUserHeadUri(user.getUserHeadUri().replace(headUrlPre, ""));
        }
        if (user.getUserPwd() != null) {
            user.setUserPwd(null);
        }
        user.setUpdateTime(LocalDateTime.now());
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result > 0) {
            return ServerResponse.createSuccess("修改成功");
        }
        throw new RuntimeException("修改失败");
    }

    private List<StrangerVo> assembleStrangerVoList(List<User> users) {
        List<StrangerVo> vos = Lists.newArrayList();
        for (User user : users) {
            vos.add(assembleStrangerVo(user));
        }
        return vos;
    }

    private StrangerVo assembleStrangerVo(User user) {
        StrangerVo vo = new StrangerVo();
        if (user.getUserName() != null) {
            vo.setUserName(user.getUserName());
        } else {
            vo.setUserName(String.valueOf(user.getUserId()));
        }
        vo.setUserId(user.getUserId());
        vo.setHeadUrl(headUrlPre + user.getUserHeadUri());
        if (SessionUtil.getUserId().equals(user.getUserId())) {
            vo.setStatus(-1);
        } else {
            int result = friendMapper.findOne(SessionUtil.getUserId(), user.getUserId());
            if (result > 0) {
                vo.setStatus(result);
            }
        }
        return vo;
    }


}
