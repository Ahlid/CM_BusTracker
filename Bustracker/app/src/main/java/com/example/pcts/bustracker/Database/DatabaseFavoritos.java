package com.example.pcts.bustracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pcts on 11/29/2016.
 */

public class DatabaseFavoritos  extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "BusTracker_DB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "favorites_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TIPO";
    public static final String COL_3 = "ID_FAV";




    public DatabaseFavoritos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("+COL_1+" INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, "+COL_2+" TEXT, "+COL_3+" INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Integer deleteData(String id, String tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_3+" = ? AND "+COL_2 +"= ?", new String[]{id,tipo});

    }


    public boolean insertData(int id, String tipo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, id);
        contentValues.put(COL_2, tipo);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;

        return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
}
