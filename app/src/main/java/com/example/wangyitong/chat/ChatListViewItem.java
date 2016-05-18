package com.example.wangyitong.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by wangyitong on 2016/5/18.
 */
public class ChatListViewItem extends LinearLayout implements View.OnClickListener {
    private ImageView mAvatar;
    private TextView mContent;

    public ChatListViewItem(Context context) {
        super(context);
    }

    public ChatListViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatListViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAvatar = (ImageView) findViewById(R.id.img_avatar);
        mContent = (TextView) findViewById(R.id.chat_content);
        mAvatar.setOnClickListener(this);
        mContent.setOnClickListener(this);
    }

    public void setChatInfo(ChatInfo info) {
        if (info != null) {
            mAvatar.setImageResource(info.getAvatar());
            mContent.setText(info.getContent());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_avatar:
                mContent.setText("click avatar!");
                break;
            case R.id.chat_content:
                mContent.setText("click content");
                break;
            default:
                break;
        }
    }
}
