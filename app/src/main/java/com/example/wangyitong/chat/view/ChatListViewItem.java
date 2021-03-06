package com.example.wangyitong.chat.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.model.ChatInfo;

/**
 * Created by wangyitong on 2016/5/18.
 */
public class ChatListViewItem extends RelativeLayout {
    private ImageView mAvatar;
    private TextView mContent;
    private ChatMessageContainer mContainer;

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
        mContainer = (ChatMessageContainer) findViewById(R.id.chat_message_container);
        mAvatar.setOnClickListener(new OnAvatarClickListener());
        mContent.setOnClickListener(new OnContentClickListener());
    }

    public void setChatInfo(final ChatInfo info, final boolean isAuthor, Bitmap bmChat, Bitmap bm) {
        if (info != null) {
            mAvatar.setImageBitmap(isAuthor ? bm : bmChat);
            mContent.setText(info.getContent());
            updateLayout(isAuthor);
        }
    }

    private void updateLayout(boolean isAuthor) {
        mContainer.setIsAuthor(isAuthor);
        setGravity(isAuthor ? Gravity.RIGHT : Gravity.LEFT);
        LayoutParams avatarLP = new LayoutParams(mAvatar.getLayoutParams());
        LayoutParams contentLP =new LayoutParams(mContainer.getLayoutParams()) ;
        avatarLP.addRule(isAuthor ? RelativeLayout.ALIGN_PARENT_RIGHT : RelativeLayout.ALIGN_PARENT_LEFT);
        contentLP.addRule(isAuthor ? RelativeLayout.LEFT_OF : RelativeLayout.RIGHT_OF, mAvatar.getId());
        mAvatar.setLayoutParams(avatarLP);
        mContainer.setLayoutParams(contentLP);
    }

    class OnAvatarClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            mContent.setText("click avatar!");
        }
    }
    class OnContentClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            mContent.setText("click content!");
        }
    }
}
