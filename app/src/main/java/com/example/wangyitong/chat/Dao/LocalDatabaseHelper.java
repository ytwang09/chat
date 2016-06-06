package com.example.wangyitong.chat.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangyitong.chat.Utils.Constants;

/**
 * Created by wangyitong on 2016/5/31.
 */
public class LocalDatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_USERS = "create table " + Constants.TABLE_USER_NAME + " (" +
            Constants.USER_COLUNM_ID + " text primary key, " +
            Constants.USER_COLUNM_NAME + " text, " +
            Constants.USER_COLUNM_PIC + " text)";

    private static final String CREATE_TABLE_CHAT = "create table " + Constants.TABLE_CHAT_NAME + " (" +
            Constants.CHAT_COLUNM_ID + " integer primary key autoincrement, " +
            Constants.CHAT_COLUNM_CHATID + " text, " +
            Constants.CHAT_COLUNM_TOME + " int not null, " +
            Constants.CHAT_COLUNM_CONTENT + " text not null, " +
            Constants.CHAT_COLUNM_DATE + " text not null)";

    public LocalDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CHAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Chat");
        onCreate(db);
    }
}
