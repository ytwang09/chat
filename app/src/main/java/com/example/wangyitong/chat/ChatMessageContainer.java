package com.example.wangyitong.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by wangyitong on 2016/5/19.
 */
public class ChatMessageContainer extends FrameLayout {
    private static final int[] STATE_MESSAGE_AUTHOR = {R.attr.state_author};
    private boolean mIsAuthor = false;

    public ChatMessageContainer(Context context) {
        super(context);
    }

    public ChatMessageContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatMessageContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIsAuthor(boolean mIsAuthor) {
        this.mIsAuthor = mIsAuthor;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (mIsAuthor) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, STATE_MESSAGE_AUTHOR);
            return drawableState;
        }
        return super.onCreateDrawableState(extraSpace);
    }
}
