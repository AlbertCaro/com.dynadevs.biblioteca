package com.dynadevs.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by beto_ on 30/09/2017.
 */

public class ConexionSQLiteHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE_USER = "CREATE TABLE user (id INTERGER, name TEXT, career TEXT, password TEXT)";

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVesion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST user");
        onCreate(sqLiteDatabase);
    }
}
