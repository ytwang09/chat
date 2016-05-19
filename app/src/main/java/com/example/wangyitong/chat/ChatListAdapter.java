package com.example.wangyitong.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

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
//            if (getItemViewType(position) == TYPE_MY_TEXT) {
//                view = (ChatListViewItem) LayoutInflater.from(mContext).inflate(R.layout.chat_list_item, null);
//            } else {
//            else if (getItemViewType(position) == TYPE_OTHER_TEXT) {
                view = (ChatListViewItem) LayoutInflater.from(mContext).inflate(R.layout.chat_list_item, null);
//            }
        } else {
            view = (ChatListViewItem) convertView;
        }
        view.setChatInfo(info, (info.getUid() == mCurUid));
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
        datas.add(new ChatInfo(0, R.drawable.icon_avatar_default, "hello"));
        datas.add(new ChatInfo(0, R.drawable.icon_avatar_default, "hellooooo"));
        datas.add(new ChatInfo(1, R.drawable.icon_avatar_other, "hello"));
        datas.add(new ChatInfo(0, R.drawable.icon_avatar_default, "aaaaa"));
        datas.add(new ChatInfo(1, R.drawable.icon_avatar_other, "hi"));
        datas.add(new ChatInfo(0, R.drawable.icon_avatar_default, "xxxxxxx"));
    }

    public void addData(ChatInfo info) {
        datas.add(info);
        notifyDataSetChanged();
    }
}
