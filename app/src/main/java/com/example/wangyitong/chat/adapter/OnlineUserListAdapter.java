package com.example.wangyitong.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.Utils.DeviceUtils;
import com.example.wangyitong.chat.model.UserInfo;
import com.example.wangyitong.chat.view.OnlineListViewItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangyitong on 2016/5/23.
 */
public class OnlineUserListAdapter extends BaseAdapter {

    private ArrayList<UserInfo> mUsers = new ArrayList<UserInfo>();
    private HashMap<String, Integer> mNewMsgCounter = new HashMap<>();

    private Context mContext;

    public OnlineUserListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public UserInfo getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemIndex(UserInfo info) {
        String chatId = info.getUserMac();
        for (int i = 0; i < mUsers.size(); i++) {
            UserInfo user = mUsers.get(i);
            if (user.getUserMac().equals(chatId)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public OnlineListViewItem getView(int position, View convertView, ViewGroup parent) {
        OnlineListViewItem view;
        if (convertView != null) {
            view = (OnlineListViewItem) convertView;
        } else {
            view = (OnlineListViewItem) LayoutInflater.from(mContext).inflate(R.layout.online_user_list_item, null);
        }
        final UserInfo info = getItem(position);

        view.setData(info.getName(), info.getUserMac(), info.getPhoto());
        view.hasNewMessage(mNewMsgCounter.get(info.getUserMac()));
        return view;
    }

    public ArrayList<UserInfo> getUsers() {
        return mUsers;
    }

    public void addData(UserInfo info) {
        String macAdd = info.getUserMac();
        if (!mNewMsgCounter.containsKey(macAdd) && !macAdd.equals(DeviceUtils.getDeviceMacAddress(mContext))) {
            mNewMsgCounter.put(macAdd, 0);
            mUsers.add(info);
        }
        notifyDataSetChanged();
    }

    public void addDatas(ArrayList<UserInfo> infos) {
        for (UserInfo item : infos) {
            addData(item);
        }
        notifyDataSetChanged();
    }

    public void updateNewMessage(String chatId, int count) {
        mNewMsgCounter.put(chatId, count);
        notifyDataSetChanged();
    }

    @Override
    public String toString() {
        return mUsers.toString();
    }
}
