package com.example.wangyitong.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.adapter.OnlineUserListAdapter;

public class EntryActivity extends AppCompatActivity {
    private ListView mOnlineUserList;
    private OnlineUserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        initView();
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
}
