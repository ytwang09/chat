package com.example.wangyitong.chat.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.Utils.BitmapLoadTool;

/**
 * Created by wangyitong on 2016/5/24.
 */
public class OnlineListViewItem extends RelativeLayout {
    private ImageView mAvatar;
    private TextView mName;
    private TextView mContent;
    private TextView mNewMessage;

    public OnlineListViewItem(Context context) {
        super(context);
    }

    public OnlineListViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnlineListViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAvatar = (ImageView) findViewById(R.id.online_avatar);
        mName = (TextView) findViewById(R.id.online_name);
        mContent = (TextView) findViewById(R.id.online_content);
        mNewMessage = (TextView) findViewById(R.id.new_message_flag);
    }

    public void setData(String name, String content, Bitmap avatar) {
        mName.setText(name);
        mContent.setText(content);
        mAvatar.setImageBitmap(avatar);
    }

    public void setData(String name, String content, String avatar) {
        mName.setText(name);
        mContent.setText(content);
        BitmapLoadTool tool = BitmapLoadTool.getInstance();
        tool.showImageByUrl(mAvatar, avatar);
    }

    public void hasNewMessage(int count) {
        if (count != 0) {
            mNewMessage.setVisibility(VISIBLE);
            mNewMessage.setText(count + "");
        } else {
            mNewMessage.setVisibility(GONE);
        }
    }
}
