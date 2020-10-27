package com.se1.team3.campuscarrental;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminHomeActivity extends HomeActivity {




    Button btn_profile, btn_logout;
    TextView textWelcome;
    SharedPreferences sharedPreferences;
    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
    static final String ROLE = "role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        setTitle(R.string.title_admin_home);

        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String welcomeMessage = String.format(getResources().getString(R.string.welcome_message), sharedPreferences.getString(USERNAME, ""));
        textWelcome = findViewById(R.id.text_rm_welcome);
        textWelcome.setText(welcomeMessage);


        btn_profile = (Button) findViewById(R.id.btn_profile);
        btn_logout = (Button) findViewById(R.id.btn_Logout);



        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Search Users", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminHomeActivity.this, SearchUserActivity.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();
            }
        });
        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

}