package com.example.pcts.bustracker.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pcts on 11/29/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

       private static final String DATABASE_NAME = "NomRoute_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String USERS_TABLE_NAME = "users_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NICKNAME";
    private static final String COL_3 = "PASSWORD";
    private static final String COL_4 = "EMAIL";
    private static final String COL_5 = "NUMBER_PHOTOS";
    private static final String COL_6 = "NUMBER_TRACKS";
    private static final String COL_7 = "NUMBER_ORDERS";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + USERS_TABLE_NAME + " (ID INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, NICKNAME TEXT, PASSWORD TEXT, EMAIL TEXT, NUMBER_PHOTOS INTEGER," +
                " NUMBER_TRACKS INTEGER, NUMBER_ORDERS INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String nickname, String password, String email, int numberOfPhotos, int numberOfTracks, int numberOfOrders) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nickname);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, numberOfPhotos);
        contentValues.put(COL_6, numberOfTracks);
        contentValues.put(COL_7, numberOfOrders);
        long result = db.insert(USERS_TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;

        return true;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + USERS_TABLE_NAME, null);
        return res;
    }

    public Cursor getUsername(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(USERS_TABLE_NAME,
                new String[]{COL_2, COL_3},
                COL_2 + "=?" + " AND " + COL_3 + "=?",
                new String[]{username, password},
                null, null, null);

        return res;
    }


    public Integer deleteData(String id, String tipo) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USERS_TABLE_NAME, COL_3+" = ? AND "+COL_2 +"= ?", new String[]{});

    }

    public boolean updatePhotos(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        int numero = numberOfPhotos(username);

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5, numero + 1);

        db.update(USERS_TABLE_NAME, contentValues, "NICKNAME = ?", new String[]{ username });

        return true;
    }

    public int numberOfPhotos(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor fotos = db.rawQuery("SELECT NUMBER_PHOTOS FROM users_table WHERE NICKNAME = ?", new String[]{username});
        fotos.moveToNext();

        int numero =  fotos.getInt(fotos.getColumnIndex(COL_5));

        return numero;
    }
}
