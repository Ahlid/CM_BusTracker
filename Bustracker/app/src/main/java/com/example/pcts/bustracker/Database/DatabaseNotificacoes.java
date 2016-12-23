package com.example.pcts.bustracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pcts on 11/30/2016.
 */

public class DatabaseNotificacoes extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BusTracker_DB_n";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notfications_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ID_CARREIRA";
    public static final String COL_3 = "ID_PARAGEM";
    public static final String COL_4 = "MINUTOS";
    public static final String COL_5 = "ESTADO";


    public DatabaseNotificacoes(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("+COL_1+" INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, "+COL_2+" INTEGER, "+COL_3+" INTEGER, "+COL_4+" INTEGER, "+COL_5+" INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public long insertData(int idCarreira, int idParagem, int minutos, boolean estado) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, idCarreira);
        contentValues.put(COL_3, idParagem);
        contentValues.put(COL_4, minutos);
        contentValues.put(COL_5, estado? 1 : 0);

        return db.insert(TABLE_NAME, null, contentValues);



    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_1+" = ? ", new String[]{id});

    }

    public boolean updateDate(String id, int idCarreira, int idParagem, int minutos, boolean estado){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_2,idCarreira);
        cv.put(COL_3,idParagem);
        cv.put(COL_4,minutos);
        cv.put(COL_5,estado? 1 : 0);

        db.update(TABLE_NAME, cv, COL_1+"="+id, null);

        return true;
    }


    //TODO: delete, get
}
