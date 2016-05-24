package com.example.wangyitong.chat.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by wangyitong on 2016/5/23.
 */
public class BitmapUtils {
    public static final String sFordName = "imgCache";

    public static Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            saveBitmapToLocal(getBitmapLocalName(url), bitmap);
            LogUtils.d("getBitmapFromUrl() : done avatar decode");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void saveBitmapToLocal(String bmName, Bitmap bm) {
        File file = new File(getPath(bmName));
        if (file.exists()) {
            return;
        }
        try {
            LogUtils.d("saveBitmapToLocal() : save bitmap");
            FileOutputStream outputStream = new FileOutputStream(file);
            LogUtils.d("saveBitmapToLocal() : file path" + file.getAbsolutePath());
            bm.compress(Bitmap.CompressFormat.JPEG, Constants.sAvatarQuality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            LogUtils.d("saveBitmapToLocal() : FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.d("saveBitmapToLocal() : IOException");
            e.printStackTrace();
        }

    }

    public static String getBitmapLocalName(String url) {
        int start = url.lastIndexOf("/") + 1;
        int end = url.lastIndexOf(".");
        return url.substring(start, end);
    }

    public static Bitmap getBitmapFromLocal(String name) {
        File file = new File(getPath(name));
        if (file.exists()) {
            return BitmapFactory.decodeFile(getPath(name));
        }
        return null;
    }

    @NonNull
    private static String getPath(String bmName) {
        return DeviceUtils.getExtStorePath() + "/" + bmName;
    }
}
