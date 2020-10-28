package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        btn_lcar = (Button) findViewById(R.id.btn_lcar);
        btn_Logout = (Button) findViewById(R.id.btn_Logout);
        btn_reserve = (Button) findViewById(R.id.btn_reserve);
        btn_profile = (Button) findViewById(R.id.btn_profile);

        btn_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Rental Manager", Toast.LENGTH_SHORT).show();
                viewProfile();
            }
        });
        btn_lcar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"List of Available Cars", Toast.LENGTH_SHORT).show();
            }
        });
        btn_reserve.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"List of Reservations", Toast.LENGTH_SHORT).show();
            }
        });
        btn_Logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                logout();
            }
        });

    }
    public void viewProfile(){
        Intent intent = new Intent(this, UserProfileActivity.class);
        bundle = new Bundle();
        bundle.putString("username", sharedPreferences.getString(USERNAME, ""));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}