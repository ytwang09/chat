package com.example.wangyitong.chat.model;

/**
 * Created by wangyitong on 2016/5/19.
 */
public class MessageBody {
    private int bodyType;
    private MessageBean message;
    public int getBodyType() {
        return bodyType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

}
