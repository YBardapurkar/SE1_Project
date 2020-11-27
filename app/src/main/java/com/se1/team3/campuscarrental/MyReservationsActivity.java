package com.se1.team3.campuscarrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Reservation;

import java.util.ArrayList;

public class MyReservationsActivity extends AppCompatActivity {

    DBHandler dbHandler;
    SharedPreferences sharedPreferences;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";

    ListView listviewMyReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        dbHandler = new SQLiteDBHandler(this);
        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        String username = sharedPreferences.getString(USERNAME, null);

        listviewMyReservations = findViewById(R.id.list_my_reservations);
        ArrayList<Reservation> reservations = (ArrayList<Reservation>) dbHandler.getReservationsForUser(username);

        ReservationAdapter adapter = new ReservationAdapter(this, R.layout.item_reservation, reservations);
        listviewMyReservations.setAdapter(adapter);

    }
}