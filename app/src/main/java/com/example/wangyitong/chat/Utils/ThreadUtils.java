package com.example.wangyitong.chat.Utils;

import android.content.Context;
import android.os.Handler;

/**
 * Created by wangyitong on 2016/5/24.
 */
public class ThreadUtils {
    private static Context mContext;
    public static void init(Context context) {
        mContext = context;
    }
    public static void postOnUiThread(Runnable r) {
        new Handler(mContext.getMainLooper()).post(r);
    }
}
