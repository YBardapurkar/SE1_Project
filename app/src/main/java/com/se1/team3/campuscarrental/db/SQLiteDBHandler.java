package com.se1.team3.campuscarrental.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.se1.team3.campuscarrental.models.SystemUser;

import java.util.List;

public class SQLiteDBHandler extends SQLiteOpenHelper implements DBHandler{

    public static final String DATABASE_NAME = "campusCarRental.db";
    public static final String TABLE_NAME = "userTable";
    public static final String COL_USERNAME = "USERNAME";
    public static final String COL_PASSWORD = "PASSWORD";
    public static final String COL_UTA_ID = "UTA_ID";
    public static final String COL_FIRSTNAME = "FIRSTNAME";
    public static final String COL_LASTNAME = "LASTNAME";
    public static final String COL_ROLE = "ROLE";
    public static final String COL_MEMBERSHIP = "MEMBERSHIP";
    public static final String COL_PHONE = "PHONE";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_STREET_ADDRESS = "STREET_ADDRESS";
    public static final String COL_CITY= "CITY";
    public static final String COL_STATE = "STATE";
    public static final String COL_PIN = "ZIPCODE";



    public SQLiteDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
       SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("campusCarRental.db path" +db.getPath());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (USERNAME TEXT PRIMARY KEY, PASSWORD TEXT, UTA_ID INTEGER, FIRSTNAME TEXT, LASTNAME TEXT, ROLE TEXT, MEMBERSHIP TEXT, PHONE INTEGER, EMAIL TEXT,STREET_ADDRESS TEXT,CITY TEXT, STATE TEXT, ZIPCODE INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //To save user data after sign up
    @Override
    public boolean saveUser(SystemUser systemUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, systemUser.getUsername());
        values.put(COL_PASSWORD, systemUser.getPassword());
        values.put(COL_UTA_ID, systemUser.getUtaId());
        values.put(COL_FIRSTNAME, systemUser.getFirstName());
        values.put(COL_LASTNAME, systemUser.getLastName());
        values.put(COL_ROLE, systemUser.getRole());
        values.put(COL_MEMBERSHIP, systemUser.getMembership());
        values.put(COL_PHONE, systemUser.getPhone());
        values.put(COL_EMAIL, systemUser.getEmail());
        values.put(COL_STREET_ADDRESS, systemUser.getStreet());
        values.put(COL_CITY, systemUser.getCity());
        values.put(COL_STATE, systemUser.getState());
        values.put(COL_PIN, systemUser.getPin());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    //lookup for user data with username and password
    @Override
    public SystemUser getUser(String username, String password) {
        SystemUser user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE USERNAME = '" + username + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            String utaId = cursor.getString(cursor.getColumnIndexOrThrow(COL_UTA_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRSTNAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COL_LASTNAME));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE));
            String membership = cursor.getString(cursor.getColumnIndexOrThrow(COL_MEMBERSHIP));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            String street = cursor.getString(cursor.getColumnIndexOrThrow(COL_STREET_ADDRESS));
            String city = cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY));
            String state = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATE));
            String pin = cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN));

            user = new SystemUser(username, password, firstName, lastName, utaId, role, membership, phone, email, street, city, state, pin);
        }
        cursor.close();

        db.close();
        return user;
    }

    public boolean checkIfUserExists(String TABLE_NAME, String username)
    {
        try
        {
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor cursor=db.rawQuery("SELECT "+COL_USERNAME+" FROM "+TABLE_NAME+" WHERE "+COL_USERNAME+"='"+COL_USERNAME+"'",null);
            if (cursor.moveToFirst())
            {
                db.close();
                Log.d("Record  Already Exists", "Table is:"+TABLE_NAME+" ColumnName:"+COL_USERNAME);
                return true;//record Exists

            }
            Log.d("New Record  ", "Table is:"+TABLE_NAME+" ColumnName:"+COL_USERNAME+" Column Value:"+COL_USERNAME);
            db.close();
        }
        catch(Exception errorException)
        {
            Log.d("Exception occured", "Exception occured "+errorException);
            // db.close();
        }
        return false;
    }
}
