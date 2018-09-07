package com.sample.dragging;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {

    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db = null;
    private String DatabaseName = "AppData.db";

    public DatabaseHelper(Context c) {

        SQLiteOpenHelper o = new DatabaseOpenHelper(c.getApplicationContext(), DatabaseName);
        db=o.getWritableDatabase();

    }

    public SQLiteDatabase getDatabase(){
        return db;
    }

    public void executeQuery(String sql, Object[] params) {
        db.execSQL(sql, params);
    }

    public static class DatabaseOpenHelper extends SQLiteOpenHelper {

        DatabaseOpenHelper(Context context, String name) {
            super(context, name, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS Items (Uid NUMERIC PRIMARY KEY, Description TEXT, Sort NUMERIC)");

            // Create default data
            for (int x = 0; x < 10; x++) {
                db.execSQL("INSERT INTO Items (uid,Description,Sort) VALUES (?,?,?)", new Object[]{x,"Item " + x,x});
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
