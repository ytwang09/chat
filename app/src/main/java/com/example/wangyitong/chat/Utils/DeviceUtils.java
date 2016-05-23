package com.example.wangyitong.chat.Utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by wangyitong on 2016/5/19.
 */
public class DeviceUtils {

    public static String getDeviceMacAddress(Context context) {
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress(); //获取mac地址
    }

    public static int getDeviceHeightPx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}