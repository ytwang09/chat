package com.example.wangyitong.chat.model;

/**
 * Created by wangyitong on 2016/5/19.
 */
public class MessageChatBody<T> {
    private int bodyType;
    private T message;
    public int getBodyType() {
        return bodyType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

}
