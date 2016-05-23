package com.example.wangyitong.chat.activity;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.wangyitong.chat.R;

import java.util.HashSet;

public class EntryActivity extends AppCompatActivity {
    private ListView mOnlineUserList;
    private HashSet<Pair<String, String>> mOnlineUsers = new HashSet<>();
    private SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        setTitle("Welcome");
        mOnlineUserList = (ListView) findViewById(R.id.online_user_list);
    }
}
