package com.example.wangyitong.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wangyitong.chat.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by wangyitong on 2016/5/23.
 */
public class OnlineUserListAdapter extends BaseAdapter {

    private ArrayList<String> users = new ArrayList<String>();
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
    public String getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.online_user_list_item, null);
        }
        ((TextView) view.findViewById(R.id.user_name)).setText(users.get(position));
        return view;
    }

    public void addData(String macAdd) {
        if (!userSet.contains(macAdd)) {
            userSet.add(macAdd);
            users.add(macAdd);
        }
        notifyDataSetChanged();
    }

    public void addDatas(ArrayList<String> adds) {
        for (String item : adds) {
            addData(item);
        }
        notifyDataSetChanged();
    }

    @Override
    public String toString() {
        return users.toString();
    }
}
