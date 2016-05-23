package com.example.wangyitong.chat.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangyitong on 2016/5/18.
 */
public class ChatInfo implements Serializable {
    private String mUid;
    private String mUsername;
    private int mAvatar;
    private int mContentType;
    private String content;
    private Date mDate;

    public String getContent() {
        return content;
    }

    public int getmAvatar() {
        return mAvatar;
    }

    public String getUid() {
        return mUid;
    }

    public Date getDate() { return mDate; }

    public ChatInfo(String id, int bm, String s, Date date) {
        this.mUid = id;
        this.mAvatar = bm;
        this.content = s;
        mDate = date;
    }
}