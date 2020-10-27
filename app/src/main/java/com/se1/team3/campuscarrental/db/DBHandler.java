package com.se1.team3.campuscarrental.db;

import android.database.sqlite.SQLiteDatabase;

import com.se1.team3.campuscarrental.models.Car;
import com.se1.team3.campuscarrental.models.SystemUser;

import java.util.List;

public interface DBHandler {

   SystemUser getUser(String username, String password);

   boolean saveUser(SystemUser sysUser);

   Car getCarById(int carId);

   List<Car> getAllCars();

   SystemUser getUserByUsername(String username);

   List<SystemUser> getAllUsers();
   List<SystemUser> searchUsers(String query,String rle);

   boolean edit_profile(SystemUser sysUser);

   boolean revoke_renter(SystemUser target_user);
//TO BE MODIFIED
}
