package com.se1.team3.campuscarrental.db;

import android.database.sqlite.SQLiteDatabase;

import com.se1.team3.campuscarrental.models.SystemUser;

public interface DBHandler {

   public SystemUser getUser(String username, String password);

   public boolean saveUser(SystemUser sysUser);

//TO BE MODIFIED
}
