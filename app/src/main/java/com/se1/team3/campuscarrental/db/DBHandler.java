package com.se1.team3.campuscarrental.db;

import android.database.sqlite.SQLiteDatabase;

import com.se1.team3.campuscarrental.models.SystemUser;

public interface DBHandler {
//    TODO - Amrutha - uncomment these and implement

   public void onCreate(SQLiteDatabase db);

   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

   public void getUser(String username, String password);

   public void saveUser(SystemUser new_user);

//TO BE MODIFIED
}
