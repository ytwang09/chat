package com.example.wangyitong.chat.Utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import java.io.File;

/**
 * Created by wangyitong on 2016/5/19.
 */
public class DeviceUtils {

    private static String sMac;
    private static String sPath = "";
    public static String getDeviceMacAddress(Context context) {
        if (sMac == null) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            sMac = info.getMacAddress(); //获取mac地址
        }
        return sMac;
    }

    public static String getDeviceName() {
        return android.os.Build.MANUFACTURER;
    }

    public static int getDeviceHeightPx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static String getExtStorePath() {
        if (sPath.equals("")) {
            getExtStorePath(Constants.APP_NAME);
        }
        return sPath;
    }

    private static void getExtStorePath(String... names) {
        File finalFile = Environment.getExternalStorageDirectory();
        for(int i = 0; i < names.length; i++) {
            finalFile = new File(finalFile, names[i]);
            if (!finalFile.exists()) {
                finalFile.mkdirs();
            }
        }
        sPath = finalFile.getAbsolutePath();
    }
}