package com.example.wangyitong.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChatDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private int mCurrentUid;
    private ListView mChatListView;
    private ChatListAdapter mAdapter;

    private Button mBtnSendMsg;
    private EditText mContentInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        mChatListView = (ListView) findViewById(R.id.chat_list);
        mAdapter = new ChatListAdapter(this, mCurrentUid);
        mChatListView.setAdapter(mAdapter);
        mChatListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideIMM(v);
                return false;
            }
        });

        mBtnSendMsg = (Button) findViewById(R.id.btn_send);
        mContentInput = (EditText) findViewById(R.id.et_input_content);
        mBtnSendMsg.setOnClickListener(this);

        android.app.ActionBar actionBar = getActionBar();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String content = mContentInput.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(ChatDetailActivity.this, "No content", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAdapter.addData(new ChatInfo(mCurrentUid, R.drawable.icon_avatar_default, content));
                mContentInput.setText("");
                hideIMM(v);
                break;
            default:
                break;
        }
    }
}
