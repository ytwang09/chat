package com.example.wangyitong.chat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.Utils.Constants;
import com.example.wangyitong.chat.Utils.DeviceUtils;
import com.example.wangyitong.chat.adapter.ChatListAdapter;
import com.example.wangyitong.chat.manager.SocketManager;
import com.example.wangyitong.chat.model.ChatInfo;
import com.example.wangyitong.chat.model.UserInfo;
import com.example.wangyitong.chat.view.OnlineUserListPopup;

import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private String mCurrentUid;
    private UserInfo mCurrentChatUser;
    private ListView mChatListView;
    private Button mBtnSendMsg;
    private EditText mContentInput;

    private ChatListAdapter mAdapter;
    private int mScreenKBHeight;

    private SysMsgBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        mScreenKBHeight = DeviceUtils.getDeviceHeightPx(this) / 3;
        mCurrentUid = DeviceUtils.getDeviceMacAddress(ChatDetailActivity.this);
        mCurrentChatUser = (UserInfo) getIntent().getSerializableExtra("chatUser");

        initView();
        addListenerNAdapter();
        registerReceiverNService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateViewChatIdChanged();
    }

    private void updateViewChatIdChanged() {
        if (mCurrentChatUser == null) {
            findViewById(R.id.mask).setVisibility(View.VISIBLE);
            mBtnSendMsg.setEnabled(false);
        } else {
            findViewById(R.id.mask).setVisibility(View.GONE);
            mBtnSendMsg.setEnabled(true);
        }
    }

    private void registerReceiverNService() {
        mReceiver = new SysMsgBroadcastReceiver();
        IntentFilter filter = new IntentFilter(Constants.ACTION_UPDATE_CHAT_LIST_DATA);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mChatListView = (ListView) findViewById(R.id.chat_list);
        mBtnSendMsg = (Button) findViewById(R.id.btn_send);
        mContentInput = (EditText) findViewById(R.id.et_input_content);
    }

    private void addListenerNAdapter() {
        mAdapter = new ChatListAdapter(this, mCurrentChatUser.getPhoto());
        mChatListView.setAdapter(mAdapter);
        mChatListView.addOnLayoutChangeListener(new OnChatListLayoutChangedListener());
        mBtnSendMsg.setOnClickListener(new OnSendClickListener());
    }

    private void hideIMM(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.right_btn) {

        }
        return super.onOptionsItemSelected(item);
    }

    class OnSendClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String content = mContentInput.getText().toString();
            if (content.isEmpty()) {
                Toast.makeText(ChatDetailActivity.this, "No content", Toast.LENGTH_SHORT).show();
                return;
            }
            final ChatInfo info = new ChatInfo(mCurrentChatUser, content, new Date(), false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SocketManager.getManager().sendChatMsgToServer(ChatDetailActivity.this, info);
                }
            }).start();

            mAdapter.addData(info);
            mContentInput.setText("");
            hideIMM(v);
        }
    }

    class OnChatListLayoutChangedListener implements View.OnLayoutChangeListener {

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (oldBottom - bottom > mScreenKBHeight) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChatListView.setSelection(mAdapter.getCount() - 1);
                    }
                }, 100);
            }
        }
    }

    class SysMsgBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ChatInfo data = (ChatInfo) intent.getSerializableExtra("chatInfo");
            mAdapter.addData(data);
        }
    }

    class CurChatIdChangedListener implements OnlineUserListPopup.OnCurChatIdChangedListener {

        @Override
        public void chatIdChanged(UserInfo chatUser) {
            mCurrentChatUser = chatUser;
            updateViewChatIdChanged();
        }
    }
}