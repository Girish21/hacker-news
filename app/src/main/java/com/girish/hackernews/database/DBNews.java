package com.girish.hackernews.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.girish.hackernews.extras.HackerNewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Girish on 05-Dec-17.
 */

public class DBNews {

    public static final int BEST_STORIES = 0;
    public static final int NEW_STORIES = 1;
    public static final int TOP_STORIES = 2;

    private SQLiteDatabase mDatabase;

    public DBNews(Context context) {
        MyDBHandler dbHelper = new MyDBHandler(context);
        mDatabase = dbHelper.getWritableDatabase();
    }

    public void insertNews(int table, List<HackerNewsModel> news, boolean clearTable) {
        if (clearTable)
            deleteNews(table);

        String query = "INSERT INTO " + (table == BEST_STORIES ? MyDBHandler.TABLE_BEST_STORIES :
                table == NEW_STORIES ? MyDBHandler.TABLE_NEW_STORIES : MyDBHandler.TABLE_TOP_STORIES) +
                " VALUES (?,?,?,?,?,?);";

        SQLiteStatement statement = mDatabase.compileStatement(query);
        mDatabase.beginTransaction();

        for (int i = 0; i < news.size(); i++) {
            HackerNewsModel hackerNews = news.get(i);
            statement.clearBindings();
            statement.bindString(2, hackerNews.getTitle());
            statement.bindLong(3, hackerNews.getId());
            statement.bindString(4, hackerNews.getUrl());
            statement.bindLong(5, hackerNews.getTime());
            statement.bindString(6, hackerNews.getBy());

            statement.execute();
        }
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public List<HackerNewsModel> getNews(int table) {
        List<HackerNewsModel> news = new ArrayList<>();

        String[] columns = {MyDBHandler.COLUMN_ID,
                MyDBHandler.COLUMN_TITLE,
                MyDBHandler.COLUMN_POST_ID,
                MyDBHandler.COLUMN_URL,
                MyDBHandler.COLUMN_TIME,
                MyDBHandler.COLUMN_BY
        };
        Cursor cursor = mDatabase.query((table == BEST_STORIES ? MyDBHandler.TABLE_BEST_STORIES :
                table == NEW_STORIES ? MyDBHandler.TABLE_NEW_STORIES : MyDBHandler.TABLE_TOP_STORIES),columns,
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HackerNewsModel hackerNews = new HackerNewsModel();
                hackerNews.setId(cursor.getLong(cursor.getColumnIndex(MyDBHandler.COLUMN_POST_ID)));
                hackerNews.setTitle(cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_TITLE)));
                hackerNews.setUrl(cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_URL)));
                hackerNews.setTime(cursor.getLong(cursor.getColumnIndex(MyDBHandler.COLUMN_TIME)));
                hackerNews.setBy(cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_BY)));

                news.add(hackerNews);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return news;
    }

    private void deleteNews(int table) {
        mDatabase.delete((table == BEST_STORIES ? MyDBHandler.TABLE_BEST_STORIES :
                        table == NEW_STORIES ? MyDBHandler.TABLE_NEW_STORIES : MyDBHandler.TABLE_TOP_STORIES),
                null, null);
    }

    private static class MyDBHandler extends SQLiteOpenHelper {

        static final String DB_NAME = "news_db";
        static final int DB_VERSION = 1;
        Context context;

        static final String TABLE_BEST_STORIES = "best_stories";
        static final String TABLE_NEW_STORIES = "new_stories";
        static final String TABLE_TOP_STORIES = "top_stories";
        static final String COLUMN_ID = "id";
        static final String COLUMN_POST_ID = "post_id";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_URL = "url";
        static final String COLUMN_TIME = "time";
        static final String COLUMN_BY = "by";

        static final String CREATE_TABLE_BEST_STORIES = "CREATE TABLE " + TABLE_BEST_STORIES + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_POST_ID + " INTEGER, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_TIME + " INTEGER, " +
                COLUMN_BY + " TEXT);";

        static final String CREATE_TABLE_NEW_STORIES = "CREATE TABLE " + TABLE_NEW_STORIES + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_POST_ID + " INTEGER, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_TIME + " INTEGER, " +
                COLUMN_BY + " TEXT);";

        static final String CREATE_TABLE_TOP_STORIES = "CREATE TABLE " + TABLE_TOP_STORIES + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_POST_ID + " INTEGER, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_TIME + " INTEGER, " +
                COLUMN_BY + " TEXT);";

        MyDBHandler(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_BEST_STORIES);
                sqLiteDatabase.execSQL(CREATE_TABLE_NEW_STORIES);
                sqLiteDatabase.execSQL(CREATE_TABLE_TOP_STORIES);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL("DROP TABLE " + TABLE_BEST_STORIES + " ;");
                sqLiteDatabase.execSQL("DROP TABLE " + TABLE_NEW_STORIES + " ;");
                sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TOP_STORIES + " ;");
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
