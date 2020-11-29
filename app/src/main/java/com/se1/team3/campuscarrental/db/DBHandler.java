package com.se1.team3.campuscarrental.db;

import com.se1.team3.campuscarrental.models.Car;
import com.se1.team3.campuscarrental.models.Reservation;
import com.se1.team3.campuscarrental.models.SystemUser;

import java.util.List;

public interface DBHandler {

   SystemUser getUser(String username, String password);

   boolean saveUser(SystemUser sysUser);

   Car getCarById(int carId);

   List<Car> getAllCars();
   List<Car> searchCars(int capacity, String start, String end);
   List<Car> getAvailableCars(long millis);

   SystemUser getUserByUsername(String username);

   List<SystemUser> getAllUsers();
   List<SystemUser> searchUsers(String query,String rle);

   boolean edit_profile(SystemUser sysUser);

   boolean revoke_renter(SystemUser target_user);

   boolean change_role(SystemUser target_user);

   long saveReservation(Reservation reservation);
   List<Reservation> getReservationsForUser(String username);
   List<Reservation> getReservationsByDay(String day);

    Reservation getReservationById(int reservation_id);

    boolean cancel_reservation(int reservation_id);

    boolean delete_reservation(int reservation_id);
//TO BE MODIFIED
}
