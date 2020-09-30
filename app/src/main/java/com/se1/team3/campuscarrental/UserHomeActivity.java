package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UserHomeActivity extends AppCompatActivity {

//    -------- USER HOME PAGE --------------- [Shubham Shah]
    Button btn_profile,  btn_searchCar, btn_viewAllReserves, btn_logout;
    SharedPreferences sharedPreferences;
    TextView textWelcome;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
    static final String ROLE = "role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        setTitle(R.string.title_user_home);

        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String welcomeMessage = String.format(getResources().getString(R.string.welcome_message), sharedPreferences.getString(USERNAME, ""));
//        String welcomeMessage = "Shubham Shah";
        textWelcome = findViewById(R.id.text_user_welcome);
        textWelcome.setText(welcomeMessage);

        btn_profile = findViewById(R.id.btn_profile);
        btn_searchCar = findViewById(R.id.btn_search_car);
        btn_viewAllReserves = findViewById(R.id.btn_reserves);
        btn_logout = findViewById(R.id.btn_logout);

        btn_profile.setOnClickListener(v -> Toast.makeText(UserHomeActivity.this, "### View Profile", Toast.LENGTH_SHORT).show());

        btn_searchCar.setOnClickListener(v -> Toast.makeText(UserHomeActivity.this, "!!! Search Car", Toast.LENGTH_SHORT).show());


        btn_viewAllReserves.setOnClickListener(v -> Toast.makeText(UserHomeActivity.this, " $$$ View All Reservations", Toast.LENGTH_SHORT).show());

        btn_logout.setOnClickListener(v -> {
            Toast.makeText(UserHomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            logout();
        });
    }

    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.logout_confirmation_message)
                .setPositiveButton(R.string.logout_confirmation_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences.edit()
                                .remove(USERNAME)
                                .remove(ROLE)
                                .apply();

                        Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.logout_confirmation_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.exit_confirmation_message)
                .setPositiveButton(R.string.exit_confirmation_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserHomeActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.exit_confirmation_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}