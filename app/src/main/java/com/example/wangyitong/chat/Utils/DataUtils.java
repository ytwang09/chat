package com.example.wangyitong.chat.Utils;

import android.content.Context;
import android.util.Log;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.model.ChatInfo;
import com.example.wangyitong.chat.model.MessageChat;
import com.example.wangyitong.chat.model.MessageChatBody;
import com.example.wangyitong.chat.model.MessageListBean;
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
        String sendUserMac = chatMsg.getSendUserMac();
        Date date = new Date(chatMsg.getDate());
        return new ChatInfo(sendUserMac, R.drawable.icon_avatar_other, content, date);
    }

    private static ArrayList<String> parseChatList(String json) {
        //TODO parse data of mac list
        MessageChatBody<ArrayList<MessageListBean>> msgBody = new Gson().fromJson(json, new TypeToken<MessageChatBody<ArrayList<MessageListBean>>>(){}.getType());
        ArrayList<MessageListBean> userList = msgBody.getMessage();
        ArrayList<String> macAdds = new ArrayList<>();
        for(MessageListBean item : userList) {
            macAdds.add(item.getUserMac());
        }
        return macAdds;
    }

    public static String formatJSONFromChatInfo(Context context, ChatInfo info) {
        MessageChat bean = new MessageChat();
        bean.setDate(info.getDate().getTime());
        bean.setMessageContent(info.getContent());
        bean.setReceiveUserMac(info.getUid());
        bean.setSendUserMac(DeviceUtils.getDeviceMacAddress(context));
        MessageChatBody data = new MessageChatBody();
        data.setBodyType(1);
        data.setMessage(bean);
        String gson = new Gson().toJson(data);
        Log.d("SOCKET", gson);
        return gson;
    }
}
