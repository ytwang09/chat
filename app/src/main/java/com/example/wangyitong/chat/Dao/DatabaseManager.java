package com.example.wangyitong.chat.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wangyitong.chat.Utils.Constants;
import com.example.wangyitong.chat.model.ChatInfo;
import com.example.wangyitong.chat.model.UserInfo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wangyitong on 2016/5/31.
 */
public class DatabaseManager {

    private static DatabaseManager sManager = new DatabaseManager();
    private LocalDatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private DatabaseManager() {
    }

    public static DatabaseManager getDBManager() {
        return sManager;
    }

    public void createDB(Context context) {
        mDatabaseHelper = new LocalDatabaseHelper(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void insertToTableUser(UserInfo info) {
        insertToTableUser(info.getUserMac(), info.getName(), info.getPhoto());
    }
    public void insertToTableUser(String userId, String userName,String avatar) {
        ContentValues values = new ContentValues();
        values.put(Constants.USER_COLUNM_ID, userId);
        values.put(Constants.USER_COLUNM_NAME, userName);
        values.put(Constants.USER_COLUNM_PIC, avatar);
        mDatabase.insert(Constants.TABLE_USER_NAME, null, values);
    }

    public void insertToTableChat(String chatId, boolean isToMe, String content, Date date) {
        ContentValues values = new ContentValues();
        values.put(Constants.CHAT_COLUNM_CHATID, chatId);
        values.put(Constants.CHAT_COLUNM_TOME, isToMe ? 1 : 0);
        values.put(Constants.CHAT_COLUNM_CONTENT, content);
        values.put(Constants.CHAT_COLUNM_DATE, Long.toString(date.getTime()));
        mDatabase.insert(Constants.TABLE_CHAT_NAME, null, values);
    }

    public ArrayList<UserInfo> queryTableUserAll() {
        ArrayList<UserInfo> results = new ArrayList<>();
        Cursor cursor = mDatabase.query(Constants.TABLE_USER_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndex(Constants.USER_COLUNM_ID));
                String userName = cursor.getString(cursor.getColumnIndex(Constants.USER_COLUNM_NAME));
                String userPic = cursor.getString(cursor.getColumnIndex(Constants.USER_COLUNM_PIC));
                results.add(new UserInfo(userId, userName, userPic));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }

    public UserInfo queryTableUserById(String userId) {
        UserInfo result = null;
        String[] selectionArgs = {userId};
        Cursor cursor = mDatabase.query(Constants.TABLE_USER_NAME, null, Constants.USER_COLUNM_ID + "=?", selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            String userName = cursor.getString(cursor.getColumnIndex(Constants.USER_COLUNM_NAME));
            String userPic = cursor.getString(cursor.getColumnIndex(Constants.USER_COLUNM_PIC));
            result = new UserInfo(userId, userName, userPic);
        }
        cursor.close();
        return result;
    }

    public ArrayList<ChatInfo> queryTableChatAllById(String chatId) {
        ArrayList<ChatInfo> results = new ArrayList<>();
        String selection = Constants.CHAT_COLUNM_CHATID + "=?";
        String selectionArgs[] = {chatId};
        Cursor cursor = mDatabase.query(Constants.TABLE_CHAT_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                boolean isToMe = cursor.getInt(cursor.getColumnIndex(Constants.CHAT_COLUNM_TOME)) == 1;
                String content = cursor.getString(cursor.getColumnIndex(Constants.CHAT_COLUNM_CONTENT));
                Long dateMillions = Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.CHAT_COLUNM_DATE)));
                Date date = new Date(dateMillions);
                UserInfo chatUser = queryTableUserById(chatId);
                results.add(new ChatInfo(chatUser, content, date, isToMe));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }
}
