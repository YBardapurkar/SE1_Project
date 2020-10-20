package com.se1.team3.campuscarrental.db;

import android.database.sqlite.SQLiteDatabase;

import com.se1.team3.campuscarrental.models.Car;
import com.se1.team3.campuscarrental.models.SystemUser;

public interface DBHandler {

   SystemUser getUser(String username, String password);
   boolean saveUser(SystemUser sysUser);
   Car getCarById(int carId);

//TO BE MODIFIED
}
