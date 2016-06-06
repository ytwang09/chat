package com.example.wangyitong.chat.model;

import java.io.Serializable;

/**
 * Created by wangyitong on 2016/5/23.
 */
public class UserInfo implements Serializable{
    private String userMac;
    private String name;
    private String photo;

    public UserInfo(String userMac, String name, String photo) {
        this.photo = photo;
        this.userMac = userMac;
        this.name = name;
    }

    public String getUserMac() {
        return userMac;
    }

    public void setUserMac(String userMac) {
        this.userMac = userMac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "[" + userMac +";"+ name + "]";
    }
}
