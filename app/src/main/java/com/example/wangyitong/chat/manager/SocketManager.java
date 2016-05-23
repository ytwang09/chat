package com.example.wangyitong.chat.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.wangyitong.chat.Utils.Constants;
import com.example.wangyitong.chat.Utils.DataUtils;
import com.example.wangyitong.chat.model.ChatInfo;

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
    private BufferedReader mReader;
    private PrintWriter mWriter;

    private SocketManager() {
    }

    public static SocketManager getManager() {
        return sManager;
    }

    public void connect(final Context context,final String mac) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(Constants.SOCKET_IP, Constants.SOCKET_PORT);
                    mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    mWriter = new PrintWriter(mSocket.getOutputStream());

                    mWriter.println(Constants.suffix_MAC + mac);
                    mWriter.flush();
                    getMessagesFromServer(context);
                } catch (IOException e) {
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
                        Log.d("SOCKET", json);
                        Object data = DataUtils.parseSysMessage(json);
                        if (data instanceof ChatInfo) {
                            // TODO notify ChatListAdapter
                            ChatInfo info = (ChatInfo) data;
                            Intent intent = new Intent(Constants.ACTION_UPDATE_CHAT_LIST_DATA);
                            intent.putExtra("chatInfo", info);
                            context.sendBroadcast(intent);
                        } else if (data instanceof ArrayList) {
                            // TODO add macs into UserList
                            ArrayList<String> list = (ArrayList<String>) data;
                            Intent intent = new Intent(Constants.ACTION_UPDATE_ONLINE_USERS);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("onlines",list);
                            intent.putExtras(bundle);
                            context.sendBroadcast(intent);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMsgToServer(final Context context, final ChatInfo info) {
        mWriter.println(DataUtils.formatJSONFromChatInfo(context, info));
        mWriter.flush();
    }

}
