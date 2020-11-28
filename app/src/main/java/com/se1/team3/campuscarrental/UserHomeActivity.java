package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UserHomeActivity extends HomeActivity {

//    -------- USER HOME PAGE --------------- [Shubham Shah]
    Button btn_profile,  btn_searchCar, btn_viewAllReserves, btn_logout;
    SharedPreferences sharedPreferences;
    TextView textWelcome;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
//    static final String ROLE = "role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        setTitle(R.string.title_user_home);

        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String welcomeMessage = String.format(getResources().getString(R.string.welcome_message), sharedPreferences.getString(USERNAME, ""));
        textWelcome = findViewById(R.id.text_user_welcome);
        textWelcome.setText(welcomeMessage);

        btn_profile = findViewById(R.id.btn_user_profile);
        btn_searchCar = findViewById(R.id.btn_search_car);
        btn_viewAllReserves = findViewById(R.id.btn_reserves);
        btn_logout = findViewById(R.id.btn_logout);

        btn_profile.setOnClickListener(v -> viewProfile());

        btn_searchCar.setOnClickListener(v -> {
            Intent intent = new Intent(UserHomeActivity.this, SearchCarActivity.class);
            startActivity(intent);
        });

        btn_viewAllReserves.setOnClickListener(v -> {
            Intent intent = new Intent(UserHomeActivity.this, MyReservationsActivity.class);
            startActivity(intent);
        });

        btn_logout.setOnClickListener(v -> logout());
    }

    public void viewProfile(){
        Intent intent = new Intent(UserHomeActivity.this, UserProfileActivity.class);
        startActivity(intent);
    }
}