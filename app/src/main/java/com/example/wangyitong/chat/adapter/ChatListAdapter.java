package com.example.wangyitong.chat.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.model.ChatInfo;
import com.example.wangyitong.chat.view.ChatListViewItem;

import java.util.ArrayList;

/**
 * Created by wangyitong on 2016/5/18.
 */
public class ChatListAdapter extends BaseAdapter {

    private static final int TYPE_MY_TEXT = 0;
    private static final int TYPE_OTHER_TEXT = 1;
    private static final int TYPE_MY_VOICE = 2;
    private static final int TYPE_OTHER_VOICE = 3;
    private static final int TYPE_MY_IMAGE = 4;
    private static final int TYPE_OTHER_IMAGE = 5;

    private ArrayList<ChatInfo> datas = new ArrayList();
    private Context mContext;
    private String mCurUid;


    public ChatListAdapter(Context context, String curUid) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatInfo info = datas.get(position);
        ChatListViewItem view;
        if (convertView == null) {
            view = (ChatListViewItem) LayoutInflater.from(mContext).inflate(R.layout.chat_list_item, null);
        } else {
            view = (ChatListViewItem) convertView;
        }
        view.setChatInfo(info, !info.getIsToMe());
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
