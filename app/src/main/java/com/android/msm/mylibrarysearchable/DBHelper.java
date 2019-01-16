package com.android.msm.mylibrarysearchable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE animais(_id integer PRIMARY KEY AUTOINCREMENT, ranking integer, especie VARCHAR(200), " +
                "raca VARCHAR(250))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if (oldV == 0) {
            if (newV == 1) {
               /*modifique seu database*/
            }
        }

    }

}