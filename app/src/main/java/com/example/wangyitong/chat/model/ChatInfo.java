package com.example.wangyitong.chat.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangyitong on 2016/5/18.
 */
public class ChatInfo implements Serializable {
    private UserInfo mChatUser;
    private int mContentType;
    private String content;
    private Date mDate;

    private boolean mIsToMe;

    public String getContent() {
        return content;
    }

    public UserInfo getChatUser() {
        return mChatUser;
    }

    public Date getDate() { return mDate; }

    public boolean getIsToMe() { return mIsToMe; }

    public ChatInfo(UserInfo id, String s, Date date, boolean isToMe) {
        this.mChatUser = id;
        this.content = s;
        mDate = date;
        mIsToMe = isToMe;
    }
}