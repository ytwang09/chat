package com.example.wangyitong.chat.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.adapter.OnlineUserListAdapter;

import java.util.ArrayList;

/**
 * Created by wangyitong on 2016/5/23.
 */
public class OnlineUserListPopup extends PopupWindow {
    private ListView mOnlineList;
    private OnlineUserListAdapter mAdapter;
    private OnCurChatIdChangedListener mListener;

    private boolean mHasShown = false;

    private OnlineUserListPopup(Context context, View root) {
        super(root, WindowManager.LayoutParams.WRAP_CONTENT,  WindowManager.LayoutParams.WRAP_CONTENT);

        initialize(context, root);
        setTouchable(true);
        setOutsideTouchable(true);
    }

    private void initialize(Context context, View root) {
        mOnlineList = (ListView) root.findViewById(R.id.online_user_list);
        mAdapter = new OnlineUserListAdapter(context);
        mOnlineList.setAdapter(mAdapter);
        mOnlineList.setOnItemClickListener(new OnOnlineItemClickListener());
    }

    public static OnlineUserListPopup getView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.online_user_popup_wnd, null);
        return new OnlineUserListPopup(context, root);
    }

    public void setOnCurChatIdChangedListener(OnCurChatIdChangedListener listener) {
        mListener = listener;
    }

    public String datas() {
        return mAdapter.toString();
    }

    public void updateData(ArrayList<String> data) {
        mAdapter.addDatas(data);
    }

    public void show(View anchor) {
        showAsDropDown(anchor);
        mHasShown = true;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mHasShown = false;
    }

    public boolean hasShown() {
        return mHasShown;
    }

    class OnOnlineItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mListener.chatIdChanged(mAdapter.getItem(position));
            dismiss();
        }
    }

    public interface OnCurChatIdChangedListener {
        void chatIdChanged(String chatId);
    }
}
