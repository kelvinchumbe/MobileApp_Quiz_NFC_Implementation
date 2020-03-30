package com.example.mobileapp_quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String DBNAME = "Registration_DB.db";
    public static final int VERSION = 2;

    private static final String TABLENAME = "Records";
    private static final String COL1 = "ID";
    private static final String COL2 = "Name";
    private static final String COL3 = "Password";
    private static final String COL4 = "Email";




    public Database(@Nullable Context context) {
        super(context,DBNAME, null, VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE if not exists "+ TABLENAME + "(" + COL1 + " integer primary key," + COL2 + "text," + COL3 + "text," + COL4 + " text )");
        db.execSQL("CREATE TABLE if not exists "+ TABLENAME + "(" + COL1 + " INTEGER PRIMARY KEY," + COL2 + " TEXT," + COL3 + " text," + COL4 + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists TABLENAME");
        onCreate(db);
    }

    public void insertData(String name, String password, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues records = new ContentValues();

        records.put(COL2, name);
        records.put(COL3, password);
        records.put(COL4, email);

        db.insert(TABLENAME, null, records);
    }

    public Cursor readData(){
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLENAME , null);

        return cursor;
    }

    public void delete(int studentId) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(TABLENAME, "id=? ", new String[]
                {String.valueOf(studentId)});
    }

    public void update(int Id, String name, String password, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues records = new ContentValues();

        records.put(COL2, name);
        records.put(COL3, password);
        records.put(COL4, email);

        String whereClause = "id=?";
        String whereArgs[] = new String[]{String.valueOf(Id)};

        db.update(TABLENAME, records, whereClause, whereArgs);
    }

}
