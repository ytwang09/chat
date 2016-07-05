package com.example.wangyitong.chat.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangyitong on 2016/7/5.
 */
public class ToastUtil {
    public static void showShortToast(final Context context, final String content) {
        ThreadUtils.postOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
