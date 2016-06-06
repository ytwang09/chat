package com.example.wangyitong.chat.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.wangyitong.chat.R;
import com.example.wangyitong.chat.activity.ChatDetailActivity;
import com.example.wangyitong.chat.model.ChatInfo;

/**
 * Created by wangyitong on 2016/5/30.
 */
public class NotificationBuilder {
    private static int sId = 1;

    public static void showChatNotification(ChatInfo info, Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.icon_new_message).setTicker("New message from " + info.getChatUser().getName());
        builder.setContentTitle(info.getChatUser().getName()).setContentInfo(info.getContent()).setAutoCancel(true);
        Intent intent = new Intent(context, ChatDetailActivity.class);
        intent.putExtra("chatUser", info.getChatUser());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        manager.notify(sId++, notification);
    }
}
