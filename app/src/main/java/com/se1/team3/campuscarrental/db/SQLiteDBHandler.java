package com.se1.team3.campuscarrental.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDBHandler extends SQLiteOpenHelper implements DBHandler{

    public static final String DATABASE_NAME = "campusCarRental.db";
    public static final String TABLE_NAME = "userTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "UTA_ID";
    public static final String COL_5 = "FIRSTNAME";
    public static final String COL_6 = "LASTNAME";
    public static final String COL_7 = "ROLE";
    public static final String COL_8 = "MEMBERSHIP";
    public static final String COL_9 = "PHONE";
    public static final String COL_10 = "EMAIL";
    public static final String COL_11 = "STREET_ADDRESS";
    public static final String COL_12= "CITY";
    public static final String COL_13 = "STATE";
    public static final String COL_14 = "ZIPCODE";



    public SQLiteDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, UTA_ID INTEGER, FIRSTNAME TEXT, LASTNAME TEXT, ROLE TEXT, MEMBERSHIP TEXT, PHONE INTEGER, EMAIL TEXT,STREET_ADDRESS TEXT,CITY TEXT, STATE TEXT, ZIPCODE INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveUser(){
//        TODO - this is just for testing, fix this
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put("USERNAME", "usernmae");
        cValues.put("PASSWORD", "password");
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME,null, cValues);
        db.close();
    }

    public void getUser(String username, String password){

    }

    // TO BE MODIFIED
}
