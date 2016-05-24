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
import java.util.HashSet;

/**
 * Created by wangyitong on 2016/5/23.
 */
public class OnlineUserListAdapter extends BaseAdapter {

    private ArrayList<UserInfo> users = new ArrayList<UserInfo>();
    private HashSet<String> userSet = new HashSet<String>();

    private Context mContext;

    public OnlineUserListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public UserInfo getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OnlineListViewItem view;
        if (convertView != null) {
            view = (OnlineListViewItem) convertView;
        } else {
            view = (OnlineListViewItem) LayoutInflater.from(mContext).inflate(R.layout.online_user_list_item, null);
        }
        final UserInfo info = getItem(position);

        view.setData(info.getName(), info.getUserMac(), info.getPhoto());
        return view;
    }

    public void addData(UserInfo info) {
        String macAdd = info.getUserMac();
        if (!userSet.contains(macAdd) && !macAdd.equals(DeviceUtils.getDeviceMacAddress(mContext))) {
            userSet.add(macAdd);
            users.add(info);
        }
        notifyDataSetChanged();
    }

    public void addDatas(ArrayList<UserInfo> infos) {
        for (UserInfo item : infos) {
            addData(item);
        }
        notifyDataSetChanged();
    }

    @Override
    public String toString() {
        return users.toString();
    }
}
