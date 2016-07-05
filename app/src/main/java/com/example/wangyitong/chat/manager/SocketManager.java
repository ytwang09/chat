package com.example.wangyitong.chat.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.wangyitong.chat.Dao.DatabaseManager;
import com.example.wangyitong.chat.Utils.Constants;
import com.example.wangyitong.chat.Utils.DataUtils;
import com.example.wangyitong.chat.Utils.DeviceUtils;
import com.example.wangyitong.chat.Utils.LogUtils;
import com.example.wangyitong.chat.Utils.ToastUtil;
import com.example.wangyitong.chat.model.ChatInfo;
import com.example.wangyitong.chat.model.UserInfo;
import com.example.wangyitong.chat.notification.NotificationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by wangyitong on 2016/5/19.
 */
public class SocketManager {

    private static SocketManager sManager = new SocketManager();
    private Socket mSocket;
    private boolean mIsConnected = false;
    private String mMac;
    private BufferedReader mReader;
    private PrintWriter mWriter;

    private DatabaseManager mDatabaseManager = DatabaseManager.getDBManager();

    private SocketManager() {
    }

    public static SocketManager getManager() {
        return sManager;
    }

    public void connect(final Context context,final String mac) {
        mMac = mac;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtils.d("connecting to server");
                    mSocket = new Socket(Constants.SOCKET_IP, Constants.SOCKET_PORT);
                    mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    mWriter = new PrintWriter(mSocket.getOutputStream());
                    sendRegisterMsgToServer(mac, DeviceUtils.getDeviceName(), Constants.sPhotoUrl);
                    getMessagesFromServer(context);
                    mIsConnected = true;
                } catch (IOException e) {
                    LogUtils.d("IOException" );
                    ToastUtil.showShortToast(context, "Fail to connect server!");
                    mIsConnected = false;
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getMessagesFromServer(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json;
                try {
                    while ((json = mReader.readLine()) != null) {
                        LogUtils.d(json);
                        Object data = DataUtils.parseSysMessage(json);
                        if (data instanceof ChatInfo) {
                            // notify ChatListAdapter
                            ChatInfo info = (ChatInfo) data;
                            Intent intent = new Intent(Constants.ACTION_UPDATE_CHAT_LIST_DATA);
                            intent.putExtra("chatInfo", info);
                            if (DeviceUtils.isAppBackground(context)) {
                                // notification
                                NotificationBuilder.showChatNotification(info, context);
                            }
                            mDatabaseManager.insertToTableChat(info.getChatUser().getUserMac(), true, info.getContent(), info.getDate());
                            context.sendBroadcast(intent);
                        } else if (data instanceof ArrayList) {
                            // add macs into UserList
                            ArrayList<UserInfo> list = (ArrayList<UserInfo>) data;
                            Intent intent = new Intent(Constants.ACTION_UPDATE_ONLINE_USERS);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("onlines",list);
                            intent.putExtras(bundle);
                            context.sendBroadcast(intent);
                            for (UserInfo info : list) {
                                mDatabaseManager.insertToTableUser(info);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendChatMsgToServer(final Context context, final ChatInfo info) {
        // TODO deal with no server or user offline
        if (!mIsConnected) {
            // retry connect to server
            connect(context, mMac);
        }
        if (mIsConnected) {
            mWriter.println(DataUtils.formatJSONFromChatInfo(context, info));
            mWriter.flush();
        } else {
            ToastUtil.showShortToast(context, "Fail to connect server!");
        }
    }

    public void sendRegisterMsgToServer(String mac, String name, String photo) {
        String  s = Constants.suffix_MAC + DataUtils.formatJSONForRegistMac(mac, name, photo);
        LogUtils.d(s);
        mWriter.println(s);
        mWriter.flush();
    }
}
