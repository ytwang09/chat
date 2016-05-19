package com.example.wangyitong.chat.model;

public class MessageBean {
    private String sendUserMac;
    private String receiveUserMac;
    private String messageContent;

    private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getSendUserMac() {
        return sendUserMac;
    }

    public void setSendUserMac(String sendUserMac) {
        this.sendUserMac = sendUserMac;
    }

    public String getReceiveUserMac() {
        return receiveUserMac;
    }

    public void setReceiveUserMac(String receiveUserMac) {
        this.receiveUserMac = receiveUserMac;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }


}