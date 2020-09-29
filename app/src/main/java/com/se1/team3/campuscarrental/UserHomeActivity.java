package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UserHomeActivity extends AppCompatActivity {

//    -------- USER HOME PAGE --------------- [Shubham Shah]
    Button btn_profile, btn_updateProfile, btn_searchCar, btn_carDetails, btn_requestRental,
            btn_viewAllReserves, btn_cancelReserve, btn_viewCurrReserve, btn_logout;
    SharedPreferences sharedPreferences;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
    static final String ROLE = "role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        setTitle(R.string.title_user_home);

        btn_profile = findViewById(R.id.view_profile);
        btn_updateProfile = findViewById(R.id.update_profile);
        btn_searchCar = findViewById(R.id.search_car);
        btn_carDetails = findViewById(R.id.car_details);
        btn_requestRental = findViewById(R.id.req_rental);
        btn_viewAllReserves = findViewById(R.id.view_reserves);
        btn_cancelReserve = findViewById(R.id.cancel_reserve);
        btn_viewCurrReserve = findViewById(R.id.selec_reserve);
        btn_logout = findViewById(R.id.logout);


        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "View Profile", Toast.LENGTH_SHORT).show();
            }
        });

        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "Update Profile", Toast.LENGTH_SHORT).show();
            }
        });

        btn_searchCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "Search Car", Toast.LENGTH_SHORT).show();
            }
        });

        btn_carDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "Car Details", Toast.LENGTH_SHORT).show();
            }
        });

        btn_requestRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "Request Rental", Toast.LENGTH_SHORT).show();
            }
        });

        btn_viewAllReserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "View All Reservations", Toast.LENGTH_SHORT).show();
            }
        });

        btn_viewCurrReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "Cancel Reservation", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancelReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "Current Reservation", Toast.LENGTH_SHORT).show();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                logout();
            }
        });
        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
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