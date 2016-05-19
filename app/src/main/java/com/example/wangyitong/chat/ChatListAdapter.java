package com.example.wangyitong.chat;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.wangyitong.chat.model.MessageBean;
import com.example.wangyitong.chat.model.MessageBody;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wangyitong on 2016/5/18.
 */
public class ChatListAdapter extends BaseAdapter {

    private ArrayList<ChatInfo> datas = new ArrayList();
    private Context mContext;
    private int mCurUid;
    private static final int TYPE_MY_TEXT = 0;
    private static final int TYPE_OTHER_TEXT = 1;
    private static final int TYPE_MY_VOICE = 2;
    private static final int TYPE_OTHER_VOICE = 3;
    private static final int TYPE_MY_IMAGE = 4;
    private static final int TYPE_OTHER_IMAGE = 5;

    public ChatListAdapter(Context context, int curUid) {
        getFakeDatas();
        mCurUid = curUid;
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (datas == null || datas.size() <= position)
            return null;
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return datas.size() > position ? position : -1;
    }

//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return datas.get(position).getUid() == mCurUid ? TYPE_MY_TEXT : TYPE_OTHER_TEXT ;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatInfo info = datas.get(position);
        ChatListViewItem view;
        if (convertView == null) {
            view = (ChatListViewItem) LayoutInflater.from(mContext).inflate(R.layout.chat_list_item, null);
        } else {
            view = (ChatListViewItem) convertView;
        }
        view.setChatInfo(info, (info.getUid().equals(Utils.getDeviceMacAdress(mContext))));
        return view;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private void getFakeDatas() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Socket socket = new Socket("192.168.142.77", 5555);
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    PrintWriter writer = new PrintWriter(socket.getOutputStream());
                    writer.println("[userMac]" + Utils.getDeviceMacAdress(mContext));
                    writer.flush();
                    Log.d("SOCKET", reader.readLine());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result;
                            try {
                                while ((result = reader.readLine()) != null) {
                                    Log.d("SOCKET", result);
                                    parseMessage(result);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void parseMessage(String json) {
        MessageBody obj = new Gson().fromJson(json, MessageBody.class);

        int bodyType = obj.getBodyType();
        MessageBean msgObj = obj.getMessage();
        if (bodyType == 1) {
            String content = msgObj.getMessageContent();
            String sendUserMac = msgObj.getSendUserMac();
            Date date = new Date(msgObj.getDate());
            ChatInfo info = new ChatInfo(sendUserMac, R.drawable.icon_avatar_other, content, date);

            addData(info);
        }
    }

    public void addData(final ChatInfo info) {
        new Handler(mContext.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                datas.add(info);
                notifyDataSetChanged();
            }
        });
    }
}
