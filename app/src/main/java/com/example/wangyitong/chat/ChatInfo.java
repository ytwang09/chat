package com.example.wangyitong.chat;

/**
 * Created by wangyitong on 2016/5/18.
 */
public class ChatInfo {
    private long mUid;
    private String mUsername;
    private int mAvatar;
    private int mContentType;
    private String content;

    public String getContent() {
        return content;
    }

    public int getmAvatar() {
        return mAvatar;
    }

    public long getUid() {
        return mUid;
    }

    public ChatInfo(long id, int bm, String s) {
        this.mUid = id;
        this.mAvatar = bm;
        this.content = s;
    }
}