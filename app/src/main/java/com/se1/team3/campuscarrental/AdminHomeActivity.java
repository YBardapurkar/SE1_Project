package com.se1.team3.campuscarrental;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AdminHomeActivity extends HomeActivity {
    Button btnSearchUser, btnLogout;
    TextView textWelcome;
    SharedPreferences sharedPreferences;
    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
//    static final String ROLE = "role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        setTitle(R.string.title_admin_home);

        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String welcomeMessage = String.format(getResources().getString(R.string.welcome_message), sharedPreferences.getString(USERNAME, ""));
        textWelcome = findViewById(R.id.text_rm_welcome);
        textWelcome.setText(welcomeMessage);

        btnSearchUser = findViewById(R.id.btn_search_users);
        btnLogout = findViewById(R.id.btn_Logout);

        btnSearchUser.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, SearchUserActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> logout());

        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

}