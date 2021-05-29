package com.example.bottomnavbar;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "museart";
    private static final int DB_SCHEMA_VERSION = 1;



    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbManager.TABLA_OBRAS_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS obras");
        onCreate(db);
    }

    public static void closeDatabase(SQLiteDatabase db){
        db.close();
    }

    public static void deleteDatabase(Context mContext){
        mContext.deleteDatabase("museart");
    }
}
