package com.example.wangyitong.chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.wangyitong.chat.Utils.DeviceUtils;
import com.example.wangyitong.chat.manager.SocketManager;

/**
 * Created by wangyitong on 2016/5/20.
 */
public class DispatchDataService extends Service {
    SocketManager mSocketManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mSocketManager = SocketManager.getManager();
        mSocketManager.connect(DispatchDataService.this, DeviceUtils.getDeviceMacAddress(this));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}