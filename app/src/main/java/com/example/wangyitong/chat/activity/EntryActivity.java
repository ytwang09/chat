package com.example.wangyitong.chat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wangyitong.chat.Dao.DatabaseManager;
import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.Utils.Constants;
import com.example.wangyitong.chat.Utils.LogUtils;
import com.example.wangyitong.chat.Utils.ThreadUtils;
import com.example.wangyitong.chat.adapter.OnlineUserListAdapter;
import com.example.wangyitong.chat.model.ChatInfo;
import com.example.wangyitong.chat.model.UserInfo;
import com.example.wangyitong.chat.service.DispatchDataService;
import com.example.wangyitong.chat.view.OnlineListViewItem;

import java.util.ArrayList;
import java.util.HashMap;

public class EntryActivity extends AppCompatActivity {
    private ListView mOnlineUserList;
    private OnlineUserListAdapter mAdapter;
    private OnlineUserBroadcastReceiver mReceiver;

    private DatabaseManager mDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ThreadUtils.init(getApplicationContext());

        LogUtils.d("entry activity onCreate");
        initDatabase();
        initServiceNReceiver();
        initView();

//        getSavedInstanceState(savedInstanceState);
        getDataFromDB();
    }

    private void getSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<UserInfo> users = (ArrayList<UserInfo>) savedInstanceState.getSerializable("data");
            if (users != null) {
                mAdapter.addDatas(users);
            }
        }
    }

    private void getDataFromDB() {
        ArrayList<UserInfo> users = mDatabaseManager.queryTableUserAll();
        if (users != null) {
            mAdapter.addDatas(users);
        }
    }

    private void initDatabase() {
        mDatabaseManager = DatabaseManager.getDBManager();
        mDatabaseManager.createDB(this);
    }

    private void initServiceNReceiver() {
        startService(new Intent(this, DispatchDataService.class));
        mReceiver = new OnlineUserBroadcastReceiver();
        IntentFilter filter = new IntentFilter(Constants.ACTION_UPDATE_ONLINE_USERS);
        filter.addAction(Constants.ACTION_UPDATE_CHAT_LIST_DATA);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (mAdapter != null) {
            outState.putSerializable("data", mAdapter.getUsers());
        }
        super.onSaveInstanceState(outState, outPersistentState);
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
            ((OnlineListViewItem) view).hasNewMessage(0);
        }
    }

    class OnlineUserBroadcastReceiver extends BroadcastReceiver {

        private HashMap<String, Integer> mNewMsgCounter = new HashMap<>();
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.ACTION_UPDATE_ONLINE_USERS)) {
                Bundle bundle = intent.getExtras();
                ArrayList<UserInfo> onlines = (ArrayList<UserInfo>) bundle.getSerializable("onlines");
                mAdapter.addDatas(onlines);
            } else if (intent.getAction().equals(Constants.ACTION_UPDATE_CHAT_LIST_DATA)) {
                ChatInfo info = (ChatInfo) intent.getSerializableExtra("chatInfo");
                String chatId = updateNewMsgCounter(info);
                int newMsgCount = mNewMsgCounter.get(chatId);
                mAdapter.updateNewMessage(chatId, newMsgCount);
            }
        }

        private String updateNewMsgCounter(ChatInfo info) {
            String chatId = info.getChatUser().getUserMac();
            if (!mNewMsgCounter.containsKey(chatId)) {
                mNewMsgCounter.put(chatId, 1);
            } else {
                mNewMsgCounter.put(chatId, mNewMsgCounter.get(chatId) + 1);
            }
            return chatId;
        }
    }
}
