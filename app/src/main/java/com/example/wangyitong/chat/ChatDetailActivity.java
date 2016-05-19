package com.example.wangyitong.chat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChatDetailActivity extends AppCompatActivity {
    private int mCurrentUid;
    private ListView mChatListView;
    private ChatListAdapter mAdapter;

    private Button mBtnSendMsg;
    private EditText mContentInput;
    private int mScreenKBHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        mScreenKBHeight = getResources().getDisplayMetrics().heightPixels / 3;

        mChatListView = (ListView) findViewById(R.id.chat_list);
        mAdapter = new ChatListAdapter(this, mCurrentUid);
        mChatListView.setAdapter(mAdapter);
        mChatListView.addOnLayoutChangeListener(new OnChatListLayoutChangedListener());

        mBtnSendMsg = (Button) findViewById(R.id.btn_send);
        mContentInput = (EditText) findViewById(R.id.et_input_content);
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

    class OnSendClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String content = mContentInput.getText().toString();
            if (content.isEmpty()) {
                Toast.makeText(ChatDetailActivity.this, "No content", Toast.LENGTH_SHORT).show();
                return;
            }
            mAdapter.addData(new ChatInfo(mCurrentUid, R.drawable.icon_avatar_default, content));
            mContentInput.setText("");
            hideIMM(v);
        }
    }

    class OnChatListLayoutChangedListener implements View.OnLayoutChangeListener {

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            Toast.makeText(ChatDetailActivity.this, "onLayoutChange:" + (oldBottom - bottom) + " \n " + mScreenKBHeight, Toast.LENGTH_SHORT).show();
            if (oldBottom - bottom > mScreenKBHeight) {
                Toast.makeText(ChatDetailActivity.this, "onLayoutChange:" + (oldBottom - bottom), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChatListView.setSelection(mAdapter.getCount() - 1);
                    }
                }, 100);
            }
        }
    }
}