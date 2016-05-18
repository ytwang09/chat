package com.example.wangyitong.chat;

/**
 * Created by wangyitong on 2016/5/18.
 */
public class ChatInfo {
    long mUid;
    int avatar;
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public long getUid() {
        return mUid;
    }

    public ChatInfo(long id, int bm, String s) {
        this.mUid = id;
        this.avatar = bm;
        this.content = s;
    }
}