package com.chat.conf;

import com.chat.common.RequestHolder;
import com.chat.common.Result;
import com.chat.service.UserGroupService;
import com.chat.util.DateTimeUtil;
import com.chat.util.SessionUtil;
import com.chat.vo.GroupMessageVo;
import com.chat.vo.MessageParent;
import com.chat.vo.MessageVo;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Component
@ServerEndpoint(value = "/chatWebSocket", configurator = GetHttpSessionConfigurator.class, encoders = {GroupMessageEncoder.class,MessageEncoder.class})
public class ChatWebSocket {

    private static Map<Integer, Session> sessions = Maps.newHashMap();
    private static Map<Integer, Queue<MessageParent>> messageQueue = Maps.newHashMap();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        RequestHolder.add(httpSession);
        Integer userId = SessionUtil.getUserId();
        if (userId == null) {
            throw new RuntimeException("未登录");
        }
        sessions.put(userId, session);
        //判断当前用户是否有消息队列
        Queue<MessageParent> queue = messageQueue.get(userId);
        if (queue == null) {
            messageQueue.put(userId, Queues.newArrayDeque());
            return;
        }
        try {
            //判断该用户是不是有消息没有接收
            while (!queue.isEmpty()) {
                MessageParent vo = queue.poll();
                if (vo instanceof MessageVo){
                    MessageVo messageVo = (MessageVo)vo;
                    vo.setTime(DateTimeUtil.returnOld(vo.getTime()));
                    session.getBasicRemote().sendObject(vo);
                }else if (vo instanceof GroupMessageVo){
                    GroupMessageVo messageVo = (GroupMessageVo)vo;
                    vo.setTime(DateTimeUtil.returnOld(vo.getTime()));
                    session.getBasicRemote().sendObject(vo);
                }
            }
        } catch (Exception e) {
            //
        }
    }

    @OnClose
    public void onClose() {
//        sessions.remove(SessionUtil.getUserId());
        RequestHolder.remove();
    }

    @OnMessage
    public void onMessage(String message) {
//        System.out.println("3");
    }

    public void sendMessage(String message, Integer userId, String name, String headUrl) {
        MessageVo vo = null;
        try {
            vo = new MessageVo();
            vo.setId(SessionUtil.getUserId());
            vo.setMsg(message);
            vo.setName(name);
            vo.setHeadUrl(headUrl);
            vo.setTime(DateTimeUtil.returnNow(LocalDateTime.now()));
            Session session = sessions.get(userId);
            if (session == null) {
                throw new IllegalStateException();
            }
            session.getBasicRemote().sendObject(vo);
        } catch (IllegalStateException e) {
            messageCache(vo,userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private UserGroupService userGroupService;


    public void sendGroupMessage(String message, Integer groupId, String name, String headUrl) {
        GroupMessageVo vo = new GroupMessageVo();
        vo.setId(SessionUtil.getUserId());
        vo.setMsg(message);
        vo.setName(name);
        vo.setHeadUrl(headUrl);
        vo.setTime(DateTimeUtil.returnNow(LocalDateTime.now()));
        vo.setGroupId(groupId);
        //广播，向当前群的所有人发送消息
        //查找当前群的除了发消息的人外的所有人的用户id
        Result result = userGroupService.findByGroupId(groupId);
        if (!result.isFlag()) {
            throw new RuntimeException("系统错误");
        }
        for (Integer memberId : (List<Integer>) result.getData()) {
            Session session = sessions.get(memberId);
            try {
                if (session == null) {//有些是有session，然后失效了；有些是根本还没有过session
                    throw new IllegalStateException();
                }
                session.getBasicRemote().sendObject(vo);
            } catch (IllegalStateException e) {
                //进行消息队列的缓存
                messageCache(vo,memberId);
            } catch (Exception e) {
                //
            }
        }
    }


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    private void messageCache(MessageParent vo,Integer userId){
        if (vo != null) {
            String replace = LocalDateTime.now().toString().replace("T", " ");
            vo.setTime(replace.substring(0, replace.lastIndexOf(".")));//确定消息是缓存到消息队列里去了,修改时间
            Queue<MessageParent> queue = messageQueue.computeIfAbsent(userId, k -> Queues.newArrayDeque());
            queue.add(vo);
        }
    }
}
