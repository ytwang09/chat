package com.example.wangyitong.chat.Utils;

import android.content.Context;

import com.example.wangyitong.chat.model.ChatInfo;
import com.example.wangyitong.chat.model.MessageChat;
import com.example.wangyitong.chat.model.MessageChatBody;
import com.example.wangyitong.chat.model.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wangyitong on 2016/5/20.
 */
public class DataUtils {
    private static UserInfo sAuthor;
    private static final String mAvatarUrl = "http://v1.qzone.cc/avatar/201507/13/19/46/55a3a4ff05262387.jpg!180x180.jpg";
    public static UserInfo getAuthorInfo(Context context) {
        if (sAuthor == null) {
            sAuthor = new UserInfo(DeviceUtils.getDeviceMacAddress(context), DeviceUtils.getDeviceName(), mAvatarUrl);
        }
        return sAuthor;
    }

    public static Object parseSysMessage(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int bodyType = jsonObject.getInt("bodyType");
            switch (bodyType) {
                case 0:
                    return parseChatList(json);
                case 1:
                    return parseChatMessage(json);
                default:
                    return new Object();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ChatInfo parseChatMessage(String json) {
        MessageChatBody<MessageChat> msgBody = new Gson().fromJson(json, new TypeToken<MessageChatBody<MessageChat>>(){}.getType());
        MessageChat chatMsg = msgBody.getMessage();
        String content = chatMsg.getMessageContent();
        UserInfo sendUserMac = chatMsg.getSendUserMac();
        Date date = new Date(chatMsg.getDate());
        return new ChatInfo(sendUserMac, content, date, true);
    }

    private static ArrayList<UserInfo> parseChatList(String json) {
        //TODO parse data of mac list
        MessageChatBody<ArrayList<UserInfo>> msgBody = new Gson().fromJson(json, new TypeToken<MessageChatBody<ArrayList<UserInfo>>>(){}.getType());
        ArrayList<UserInfo> userList = msgBody.getMessage();
        return userList;
    }

    public static String formatJSONFromChatInfo(Context context, ChatInfo info) {
        MessageChat bean = new MessageChat();
        bean.setDate(info.getDate().getTime());
        bean.setMessageContent(info.getContent());
        bean.setReceiveUserMac(info.getChatUser());
        bean.setSendUserMac(getAuthorInfo(context));
        MessageChatBody data = new MessageChatBody();
        data.setBodyType(1);
        data.setMessage(bean);
        String gson = new Gson().toJson(data);
        LogUtils.d(gson);
        return gson;
    }

    public static String formatJSONForRegistMac( String mac, String name, String photo) {
        UserInfo user = new UserInfo(mac, name, photo);
        return new Gson().toJson(user);
    }
}
