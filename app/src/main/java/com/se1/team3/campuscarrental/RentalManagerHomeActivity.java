package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RentalManagerHomeActivity extends HomeActivity {

    Button btn_lcar, btn_Logout, btn_profile, btn_reserve;
    TextView textWelcome;
    Bundle bundle;
    SharedPreferences sharedPreferences;
    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_manager_home);

        setTitle(R.string.title_rental_manager_home);

        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String welcomeMessage = String.format(getResources().getString(R.string.welcome_message), sharedPreferences.getString(USERNAME, ""));
        textWelcome = findViewById(R.id.text_rm_welcome);
        textWelcome.setText(welcomeMessage);

        btn_lcar = findViewById(R.id.btn_lcar);
        btn_Logout = findViewById(R.id.btn_Logout);
        btn_reserve = findViewById(R.id.btn_reserve);
        btn_profile = findViewById(R.id.btn_profile);

        btn_profile.setOnClickListener(v -> viewProfile());

        btn_lcar.setOnClickListener(v -> {
                Intent intent = new Intent(RentalManagerHomeActivity.this, AvailableCarsActivity.class);
                startActivity(intent);
        }
                );
        btn_reserve.setOnClickListener(v -> {
            Intent intent = new Intent(RentalManagerHomeActivity.this, ListReservationsActivity.class);
            startActivity(intent);
        });

        btn_Logout.setOnClickListener(v -> logout());

    }
    public void viewProfile(){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}