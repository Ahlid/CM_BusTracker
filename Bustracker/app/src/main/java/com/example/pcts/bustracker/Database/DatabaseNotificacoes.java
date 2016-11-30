package com.example.pcts.bustracker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pcts on 11/30/2016.
 */

public class DatabaseNotificacoes extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BusTracker_DB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notfications_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ID_CARREIRA";
    public static final String COL_3 = "ID_PARAGEM";
    public static final String COL_4 = "MINUTOS";


    public DatabaseNotificacoes(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("+COL_1+" INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, "+COL_2+" INTEGER, "+COL_3+" INTEGER, "+COL_4+" INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }


    //TODO: delete, insert, get
}
