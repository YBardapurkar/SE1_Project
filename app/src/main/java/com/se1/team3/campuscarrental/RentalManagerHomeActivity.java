package com.se1.team3.campuscarrental;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RentalManagerHomeActivity extends HomeActivity {

//    TODO - Mayank Jain

    Button btn_lcar, btn_Logout, btn_profile, btn_reserve;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_manager_home);
        btn_lcar = (Button) findViewById(R.id.btn_lcar);
        btn_Logout = (Button) findViewById(R.id.btn_Logout);
        btn_reserve = (Button) findViewById(R.id.btn_reserve);
        btn_profile = (Button) findViewById(R.id.btn_profile);

        btn_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Rental Manager", Toast.LENGTH_SHORT).show();
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
}