package com.chat.conf;

import com.chat.vo.GroupMessageVo;
import net.sf.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class GroupMessageEncoder implements Encoder.Text<GroupMessageVo> {
    @Override
    public String encode(GroupMessageVo groupMessageVo) throws EncodeException {
        return JSONObject.fromObject(groupMessageVo).toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
