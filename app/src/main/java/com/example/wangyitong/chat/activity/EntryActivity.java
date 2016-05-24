package com.example.wangyitong.chat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.Utils.Constants;
import com.example.wangyitong.chat.Utils.LogUtils;
import com.example.wangyitong.chat.Utils.ThreadUtils;
import com.example.wangyitong.chat.adapter.OnlineUserListAdapter;
import com.example.wangyitong.chat.model.UserInfo;
import com.example.wangyitong.chat.service.DispatchDataService;

import java.util.ArrayList;

public class EntryActivity extends AppCompatActivity {
    private ListView mOnlineUserList;
    private OnlineUserListAdapter mAdapter;
    private OnlineUserBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ThreadUtils.init(getApplicationContext());

        LogUtils.d("entry activity oncreate");
        initServiceNReceiver();
        initView();
    }

    private void initServiceNReceiver() {
        startService(new Intent(this, DispatchDataService.class));
        mReceiver = new OnlineUserBroadcastReceiver();
        IntentFilter filter = new IntentFilter(Constants.ACTION_UPDATE_ONLINE_USERS);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Welcome");
        mOnlineUserList = (ListView) findViewById(R.id.online_user_list);
        mAdapter = new OnlineUserListAdapter(this);
        mOnlineUserList.setAdapter(mAdapter);
        mOnlineUserList.setOnItemClickListener(new OnChatUserClickListener());
    }

    class OnChatUserClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(EntryActivity.this, ChatDetailActivity.class);
            intent.putExtra("chatUser", mAdapter.getItem(position));
            startActivity(intent);
        }
    }

    class OnlineUserBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            ArrayList<UserInfo> onlines = (ArrayList<UserInfo>) bundle.getSerializable("onlines");
            mAdapter.addDatas(onlines);
        }
    }
}
