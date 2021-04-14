package com.chat.conf;

import com.chat.vo.MessageVo;
import net.sf.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<MessageVo> {


    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(MessageVo messageVo) throws EncodeException {
        //将java对象转换为json字符串
        return JSONObject.fromObject(messageVo).toString();
    }
}
