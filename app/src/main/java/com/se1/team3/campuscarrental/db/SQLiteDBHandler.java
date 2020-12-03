package com.se1.team3.campuscarrental.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.se1.team3.campuscarrental.R;
import com.se1.team3.campuscarrental.models.Car;
import com.se1.team3.campuscarrental.models.Reservation;
import com.se1.team3.campuscarrental.models.SystemUser;

import java.util.ArrayList;
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
    public static final String COL_STATUS = "STATUS";

    public static final String TABLE_CARS = "carTable";
    public static final String COL_CAR_ID = "CAR_ID";
    public static final String COL_CAR_NAME = "CAR_NAME";
    public static final String COL_CAPACITY = "CAPACITY";
    public static final String COL_CAR_IMAGE = "CAR_IMAGE";
    public static final String COL_WEEKDAY = "WEEKDAY";
    public static final String COL_WEEKEND = "WEEKEND";
    public static final String COL_WEEK = "WEEK";
    public static final String COL_GPS = "GPS";
    public static final String COL_ONSTAR = "ONSTAR";
    public static final String COL_SIRIUSXM = "SISRIUSXM";

    public static final String TABLE_RESERVATIONS = "reservationsTable";
    public static final String COL_R_ID = "RESERVATION_ID";
    public static final String COL_R_CAR_ID = "CAR_ID";
    public static final String COL_R_USERNAME = "USERNAME";
    public static final String COL_R_FIRSTNAME = "FIRSTNAME";
    public static final String COL_R_LASTNAME = "LASTNAME";
    public static final String COL_R_START_DATE = "START_DATE";
    public static final String COL_R_END_DATE = "END_DATE";
    public static final String COL_R_PRICE = "PRICE";
    public static final String COL_R_GPS = "GPS";
    public static final String COL_R_ONSTAR = "ONSTAR";
    public static final String COL_R_SIRIUSXM = "SISRIUSXM";
    public static final String COL_R_DISCOUNT = "DISCOUNT";
    public static final String COL_R_TAX = "TAX";
    public static final String COL_R_TOTAL_PRICE = "TOTAL_PRICE";
    public static final String COL_R_STATUS = "STATUS";

    public SQLiteDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("campusCarRental.db path" +db.getPath());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (USERNAME TEXT PRIMARY KEY, PASSWORD TEXT, UTA_ID INTEGER, FIRSTNAME TEXT, LASTNAME TEXT, ROLE TEXT, MEMBERSHIP INTEGER, PHONE INTEGER, EMAIL TEXT,STREET_ADDRESS TEXT,CITY TEXT, STATE TEXT, ZIPCODE INTEGER, STATUS INTEGER)");
        upgrade1(db);
        upgrade2(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            upgrade1(db);
        }
        if (oldVersion < 3) {
            upgrade2(db);
        }
    }

    private void upgrade1(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CARS + " (" +
                COL_CAR_ID +" integer primary key autoincrement, " +
                COL_CAR_NAME + " TEXT, " +
                COL_CAPACITY + " INTEGER, " +
                COL_CAR_IMAGE + " INTEGER, " +
                COL_WEEKDAY + " DECIMAL(6,2), " +
                COL_WEEKEND + " DECIMAL(10,2), " +
                COL_WEEK + " DECIMAL(10,2), " +
                COL_GPS + " DECIMAL(10,2), " +
                COL_ONSTAR + " DECIMAL(10,2), " +
                COL_SIRIUSXM + " DECIMAL(10,2)" +
                ");");
        saveCars(db);
    }

    private void upgrade2(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_RESERVATIONS + " (" +
                COL_R_ID +" integer primary key autoincrement, " +
                COL_R_CAR_ID + " INTEGER, " +
                COL_R_USERNAME + " TEXT, " +
                COL_R_FIRSTNAME + " TEXT, " +
                COL_R_LASTNAME + " TEXT, " +
                COL_R_START_DATE + " DATETIME, " +
                COL_R_END_DATE + " DATETIME, " +
                COL_R_PRICE + " DECIMAL(10,2), " +
                COL_R_GPS + " DECIMAL(10,2), " +
                COL_R_ONSTAR + " DECIMAL(10,2), " +
                COL_R_SIRIUSXM + " DECIMAL(10,2), " +
                COL_R_DISCOUNT + " DECIMAL(10,2), " +
                COL_R_TAX + " DECIMAL(10,2), " +
                COL_R_TOTAL_PRICE + " DECIMAL(10,2), " +
                COL_R_STATUS + " INTEGER" +
                ");");
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
        values.put(COL_MEMBERSHIP, systemUser.isMembership());
        values.put(COL_PHONE, systemUser.getPhone());
        values.put(COL_EMAIL, systemUser.getEmail());
        values.put(COL_STREET_ADDRESS, systemUser.getStreet());
        values.put(COL_CITY, systemUser.getCity());
        values.put(COL_STATE, systemUser.getState());
        values.put(COL_PIN, systemUser.getPin());
        values.put(COL_STATUS, systemUser.isStatus());


        // Insert a new row for user in the database, returning the ID of that new row.
        long rowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return rowId >= 0;
    }

    //lookup for user data with username and password
    @Override
    public SystemUser getUser(String username, String password) {
        SystemUser user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            String utaId = cursor.getString(cursor.getColumnIndexOrThrow(COL_UTA_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRSTNAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COL_LASTNAME));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE));
            boolean membership = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MEMBERSHIP)) == 1;
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            String street = cursor.getString(cursor.getColumnIndexOrThrow(COL_STREET_ADDRESS));
            String city = cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY));
            String state = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATE));
            String pin = cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN));
            boolean status = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STATUS)) == 1;

            user = new SystemUser(username, password, firstName, lastName, utaId, role, membership, phone, email, street, city, state, pin, status);
        }
        cursor.close();

        db.close();
        return user;
    }

    @Override
    public Car getCarById(int carId) {
        Car car = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CARS + " WHERE " + COL_CAR_ID + " = '" + carId + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAR_ID));
            String carName = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAR_NAME));
            int capacity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAPACITY));
            int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAR_IMAGE));
            double weekday = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_WEEKDAY));
            double weekend = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_WEEKEND));
            double week = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_WEEK));
            double gps = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_GPS));
            double onStar = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ONSTAR));
            double siriusXm = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_SIRIUSXM));

            car = new Car(id, carName, capacity, imageId, weekday, weekend, week, gps, onStar, siriusXm);
        }
        cursor.close();
        db.close();
        return car;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CARS + ";";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAR_ID));
            String carName = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAR_NAME));
            int capacity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAPACITY));
            int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAR_IMAGE));
            double weekday = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_WEEKDAY));
            double weekend = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_WEEKEND));
            double week = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_WEEK));
            double gps = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_GPS));
            double onStar = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ONSTAR));
            double siriusXm = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_SIRIUSXM));

            cars.add(new Car(id, carName, capacity, imageId, weekday, weekend, week, gps, onStar, siriusXm));
        }
        cursor.close();
        db.close();
        return cars;
    }

    @Override
    public List<Car> searchCars(int cap, String start, String end) {
        List<Car> cars = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        String selectQuery = "SELECT * FROM " + TABLE_CARS + " where " + COL_CAPACITY + " >= '" + cap + "';";

        String selectQuery = "select " + TABLE_CARS + ".* fROM " + TABLE_CARS + " left join (" +
                "select * fROM " + TABLE_RESERVATIONS +
                " where '" + start + "' <= " + TABLE_RESERVATIONS + "." + COL_R_START_DATE + " and " + TABLE_RESERVATIONS + "." + COL_R_START_DATE + " <= '" + end + "' " +
                " or '" + start + "' <= " + TABLE_RESERVATIONS + "." + COL_R_END_DATE + " and " + TABLE_RESERVATIONS + "." + COL_R_END_DATE + " <= '" + end + "'" +
                ") rt " +
                " on " + TABLE_CARS + "." + COL_CAR_ID + " = rt." + COL_R_CAR_ID + " where rt." + COL_CAR_ID + " is Null " +
                " and " + TABLE_CARS + "." + COL_CAPACITY + " >= '" + cap + "' " +
                " order by " + TABLE_CARS + "." + COL_WEEKDAY + ";";

        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAR_ID));
            String carName = cursor.getString(cursor.getColumnIndexOrThrow(COL_CAR_NAME));
            int capacity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAPACITY));
            int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAR_IMAGE));
            double weekday = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_WEEKDAY));
            double weekend = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_WEEKEND));
            double week = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_WEEK));
            double gps = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_GPS));
            double onStar = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ONSTAR));
            double siriusXm = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_SIRIUSXM));

            cars.add(new Car(id, carName, capacity, imageId, weekday, weekend, week, gps, onStar, siriusXm));
        }
        cursor.close();
        db.close();
        return cars;
    }

    @Override
    public List<Car> getAvailableCars(long millis) {
//        TODO implement this
        return getAllCars();
    }

    @Override
    public SystemUser getUserByUsername(String username) {
        SystemUser user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE USERNAME = '" + username + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToNext()) {
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD));
            String utaId = cursor.getString(cursor.getColumnIndexOrThrow(COL_UTA_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRSTNAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COL_LASTNAME));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE));
            boolean membership = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MEMBERSHIP)) == 1;
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            String street = cursor.getString(cursor.getColumnIndexOrThrow(COL_STREET_ADDRESS));
            String city = cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY));
            String state = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATE));
            String pin = cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN));
            boolean status = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STATUS)) == 1;

            user = new SystemUser(username, password, firstName, lastName, utaId, role, membership, phone, email, street, city, state, pin, status);
        }
        cursor.close();

        db.close();
        return user;
    }

    @Override
    public List<SystemUser> getAllUsers() {
        List<SystemUser> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME));
            String utaId = cursor.getString(cursor.getColumnIndexOrThrow(COL_UTA_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRSTNAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COL_LASTNAME));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE));
            boolean membership = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MEMBERSHIP)) == 1;
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            String street = cursor.getString(cursor.getColumnIndexOrThrow(COL_STREET_ADDRESS));
            String city = cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY));
            String state = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATE));
            String pin = cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN));
            boolean status = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STATUS)) == 1;

            users.add(new SystemUser(username, "", firstName, lastName, utaId, role, membership, phone, email, street, city, state, pin, status));
        }
        cursor.close();

        db.close();
        return users;
    }

    @Override
    public List<SystemUser> searchUsers(String query, String rle) {

        List<SystemUser> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE USERNAME like '" + query + "%' AND ROLE = '" + rle + "' order by " + COL_USERNAME + " desc;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME));
            String utaId = cursor.getString(cursor.getColumnIndexOrThrow(COL_UTA_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRSTNAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COL_LASTNAME));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(COL_ROLE));
            boolean membership = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MEMBERSHIP)) == 1;
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            String street = cursor.getString(cursor.getColumnIndexOrThrow(COL_STREET_ADDRESS));
            String city = cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY));
            String state = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATE));
            String pin = cursor.getString(cursor.getColumnIndexOrThrow(COL_PIN));
            boolean status = cursor.getInt(cursor.getColumnIndexOrThrow(COL_STATUS)) == 1;

            users.add(new SystemUser(username, "", firstName, lastName, utaId, role, membership, phone, email, street, city, state, pin, status));
        }
        cursor.close();

        db.close();
        return users;

    }

    private void saveCar(SQLiteDatabase db, Car car) {
        ContentValues values = new ContentValues();
        values.put(COL_CAR_NAME, car.getCarName());
        values.put(COL_CAPACITY, car.getCapacity());
        values.put(COL_CAR_IMAGE, car.getImage());
        values.put(COL_WEEKDAY, car.getWeekday());
        values.put(COL_WEEKEND, car.getWeekend());
        values.put(COL_WEEK, car.getWeek());
        values.put(COL_GPS, car.getGps());
        values.put(COL_ONSTAR, car.getOnStar());
        values.put(COL_SIRIUSXM, car.getSiriusXM());

        db.insert(TABLE_CARS, null, values);
    }

    private void saveCars(SQLiteDatabase db) {
        saveCar(db, new Car(0, "Smart", 1, R.drawable.smart, 32.99, 37.99, 230.93, 3.00, 5.00, 7.00));
        saveCar(db, new Car(0, "Economy", 3, R.drawable.economy, 39.99, 44.99, 279.93, 3.00, 5.00, 7.00));
        saveCar(db, new Car(0, "Compact", 4, R.drawable.compact, 44.99, 49.99, 314.93, 3.00, 5.00, 7.00));
        saveCar(db, new Car(0, "Intermediate", 4, R.drawable.intermediate, 45.99, 50.99, 321.93, 3.00, 5.00, 7.00));
        saveCar(db, new Car(0, "Standard", 5, R.drawable.standard, 48.99, 53.99, 342.93, 3.00, 5.00, 7.00));
        saveCar(db, new Car(0, "Full Size", 6, R.drawable.full_size, 52.99, 57.99, 370.93, 3.00, 5.00, 7.00));
        saveCar(db, new Car(0, "SUV", 8, R.drawable.suv, 59.99, 64.99, 419.93, 3.00, 5.00, 7.00));
        saveCar(db, new Car(0, "MiniVan", 9, R.drawable.minivan, 59.99, 64.99, 419.93, 3.00, 5.00, 7.00));
        saveCar(db, new Car(0, "Ultra Sports", 2, R.drawable.ultra_sports, 199.99, 204.99, 1399.93, 5.00, 7.00, 9.00));
    }

    /*function to update the columns edited by Admin
     * This function has been called from page "AdminEditsProfile.java"*/
    public boolean edit_profile(SystemUser user_tobe_edited) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(COL_FIRSTNAME, user_tobe_edited.getFirstName());
        cv.put(COL_LASTNAME, user_tobe_edited.getLastName());
        cv.put(COL_UTA_ID, user_tobe_edited.getUtaId());
        cv.put(COL_PHONE, user_tobe_edited.getPhone());
        cv.put(COL_EMAIL, user_tobe_edited.getEmail());
        cv.put(COL_STREET_ADDRESS, user_tobe_edited.getStreet());
        cv.put(COL_CITY, user_tobe_edited.getCity());
        cv.put(COL_STATE, user_tobe_edited.getState());
        cv.put(COL_PIN, user_tobe_edited.getPin());
        cv.put(COL_MEMBERSHIP, user_tobe_edited.isMembership());

        db.update(TABLE_NAME, cv, COL_USERNAME + "=?", new String[]{user_tobe_edited.getUsername()});
        db.close();
        return true;
    }

    /*function to revoke renter
     * This function is called from ViewUSerdetailsActivity.java by the admin*/
    @Override
    public boolean revoke_renter(SystemUser target_user) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(COL_STATUS, target_user.isStatus());

        db.update(TABLE_NAME, cv, COL_USERNAME + "=?", new String[]{target_user.getUsername()});
        db.close();

        return true;
    }

    @Override
    public boolean change_role(SystemUser target_user) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(COL_ROLE, target_user.getRole());

        db.update(TABLE_NAME, cv, COL_USERNAME + "=?", new String[]{target_user.getUsername()});
        db.close();

        return true;
    }

    public long saveReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_R_CAR_ID, reservation.getCarId());
        values.put(COL_R_USERNAME, reservation.getUsername());
        values.put(COL_R_FIRSTNAME, reservation.getFirstName());
        values.put(COL_R_LASTNAME, reservation.getLastName());
        values.put(COL_R_START_DATE, reservation.getStartDate());
        values.put(COL_R_END_DATE, reservation.getEndDate());
        values.put(COL_R_GPS, reservation.getGps());
        values.put(COL_R_ONSTAR, reservation.getOnStar());
        values.put(COL_R_SIRIUSXM, reservation.getSiriusXm());
        values.put(COL_R_PRICE, reservation.getPrice());
        values.put(COL_R_DISCOUNT, reservation.getDiscount());
        values.put(COL_R_TAX, reservation.getTax());
        values.put(COL_R_TOTAL_PRICE, reservation.getTotalPrice());
        values.put(COL_R_STATUS, reservation.isStatus());

        // Insert a new row for user in the database, returning the ID of that new row.
        long rowId = db.insert(TABLE_RESERVATIONS, null, values);
        db.close();

        return rowId;
    }

    @Override
    public List<Reservation> getReservationsForUser(String username) {
        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_RESERVATIONS + " WHERE " + COL_R_USERNAME + " = '" + username + "' order by date(" + COL_R_START_DATE + ") desc;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            reservations.add(getReservationFromCursor(cursor));
        }
        cursor.close();

        db.close();
        return reservations;
    }

    @Override
    public List<Reservation> getReservationsByDay(String day) {
        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_RESERVATIONS + " WHERE date(" + COL_R_START_DATE + ") <= '" + day + "' and  '" + day + "' <= date(" + COL_R_END_DATE + ") order by date(" + COL_R_START_DATE + ") desc;";
        //String selectQuery = "SELECT * FROM " + TABLE_RESERVATIONS + " WHERE date(" + COL_R_START_DATE + ") <= '" + day + "' order by date(" + COL_R_START_DATE + ") desc;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            reservations.add(getReservationFromCursor(cursor));
        }
        cursor.close();

        db.close();
        return reservations;
    }

    private Reservation getReservationFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_R_ID));
        int carId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_R_CAR_ID));
        String username = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_USERNAME));
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_FIRSTNAME));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_LASTNAME));
        String startDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_START_DATE));
        String endDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_END_DATE));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_PRICE));
        double gps = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_GPS));
        double onStar = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_ONSTAR));
        double siriusXm = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_SIRIUSXM));
        double discount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_DISCOUNT));
        double tax = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_TAX));
        double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_TOTAL_PRICE));
        boolean status = cursor.getInt(cursor.getColumnIndexOrThrow(COL_R_STATUS)) == 1;

        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setCarId(carId);
        reservation.setUsername(username);
        reservation.setFirstName(firstName);
        reservation.setLastName(lastName);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setPrice(price);
        reservation.setGps(gps);
        reservation.setOnStar(onStar);
        reservation.setSiriusXm(siriusXm);
        reservation.setDiscount(discount);
        reservation.setTax(tax);
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(status);

        return reservation;
    }

    @Override
    public Reservation getReservationById(int reservation_id) {
        Reservation reservation = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_RESERVATIONS + " WHERE " + COL_R_ID + " = '" + reservation_id + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_R_ID));
            int carId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_R_CAR_ID));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_USERNAME));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_FIRSTNAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_LASTNAME));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_START_DATE));
            String endDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_R_END_DATE));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_PRICE));
            double gps = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_GPS));
            double onStar = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_ONSTAR));
            double siriusXm = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_SIRIUSXM));
            double discount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_DISCOUNT));
            double tax = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_TAX));
            double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_R_TOTAL_PRICE));
            boolean status = cursor.getInt(cursor.getColumnIndexOrThrow(COL_R_STATUS)) == 1;


            reservation = new Reservation();
            reservation.setId(id);
            reservation.setCarId(carId);
            reservation.setUsername(username);
            reservation.setFirstName(firstName);
            reservation.setLastName(lastName);
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservation.setPrice(price);
            reservation.setGps(gps);
            reservation.setOnStar(onStar);
            reservation.setSiriusXm(siriusXm);
            reservation.setDiscount(discount);
            reservation.setTax(tax);
            reservation.setTotalPrice(totalPrice);
            reservation.setStatus(status);
            //current_reservation = new Reservation(reservation_ID, car_ID,username, firstName, lastName, startDate, endDate, price, gps, onStar, sirius, discount, tax, totalprice, status);
        }
        cursor.close();

        db.close();
        return reservation;
    }
       @Override
    public boolean cancel_reservation(int reservation_id){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(COL_R_STATUS, Boolean.FALSE);

        int update = db.update(TABLE_RESERVATIONS, cv, COL_R_ID + "=?", new String[]{String.valueOf(reservation_id)});
        db.close();


        return true;
    }

    @Override
    public boolean delete_reservation(int reservation_id){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(COL_R_STATUS, Boolean.FALSE);

        int update = db.update(TABLE_RESERVATIONS, cv, COL_R_ID + "=?", new String[]{String.valueOf(reservation_id)});
        db.close();
        return true;
    }

}
