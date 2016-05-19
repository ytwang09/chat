package com.example.wangyitong.chat;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by wangyitong on 2016/5/19.
 */
public class Utils {

    public static String getDeviceMacAdress(Context context) {
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress(); //获取mac地址
    }
}