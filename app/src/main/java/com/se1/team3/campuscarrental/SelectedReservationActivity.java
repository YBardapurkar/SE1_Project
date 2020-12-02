package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Car;
import com.se1.team3.campuscarrental.models.Reservation;
import com.se1.team3.campuscarrental.models.SystemUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedReservationActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
    static final String ROLE = "role";

    DBHandler dbHandler = null;
    Reservation currentReservation;
    Car booked_car;

    @BindView(R.id.car_image)
    ImageView carImageReservation;
    @BindView(R.id.display_reservation_id_txt)
    TextView displayReservationIdTxt;
    @BindView(R.id.display_first_name_reservation)
    TextView displayFirstNameReservation;
    @BindView(R.id.display_last_name_reservation)
    TextView displayLastNameReservation;
    @BindView(R.id.display_car_name_reservation)
    TextView displayCarNameReservation;
    @BindView(R.id.display_occupancy_car_reservation)
    TextView displayOccupancyCarReservation;
    @BindView(R.id.display_start_date_reservation)
    TextView displayStartDateReservation;

    @BindView(R.id.display_end_date_reservation)
    TextView displayEndDateReservation;

    @BindView(R.id.display_GPS_reservation)
    TextView displayGPSReservation;
    @BindView(R.id.display_sirius_reservation)
    TextView displaySiriusReservation;
    @BindView(R.id.display_OnStar_reservation)
    TextView displayOnStarReservation;

    @BindView(R.id.display_price_reservation)
    TextView displayPriceReservation;
    @BindView(R.id.display_discount_reservation)
    TextView displayDiscountReservation;
    @BindView(R.id.display_tax_reservation)
    TextView displayTaxReservation;
    @BindView(R.id.display_totalPrice_reservation)
    TextView displayTotalPriceReservation;

    @BindView(R.id.display_status_reservation)
    TextView displayStatusReservation;
    @BindView(R.id.reservation_button)
    Button reservationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_reservation);
        ButterKnife.bind(this);

        setTitle("Reservation Details");

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        int current_res_id = b.getInt("RESERVATION_ID");
        dbHandler = new SQLiteDBHandler(this);
        sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        String username = sharedPreferences.getString(USERNAME, null);
        /*get the selected reservation by id--------------enter the reservation id from Reservation list page*/
        currentReservation = dbHandler.getReservationById(current_res_id);

        //setting the values of display from the received reservation object
        displayReservationIdTxt.setText(Integer.toString(currentReservation.getId()));
        displayFirstNameReservation.setText(currentReservation.getFirstName());
        displayLastNameReservation.setText(currentReservation.getLastName());
        displayStartDateReservation.setText(currentReservation.getStartDate());
        displayEndDateReservation.setText(currentReservation.getEndDate());

        displayGPSReservation.setText(String.format("$ %.2f", currentReservation.getGps()));
        displaySiriusReservation.setText(String.format("$ %.2f", currentReservation.getSiriusXm()));
        displayOnStarReservation.setText(String.format("$ %.2f", currentReservation.getOnStar()));

        displayPriceReservation.setText(String.format("$ %.2f", currentReservation.getPrice()));
        displayDiscountReservation.setText(String.format("- $ %.2f", currentReservation.getDiscount()));
        displayTaxReservation.setText(String.format("$ %.2f", currentReservation.getTax()));
        displayTotalPriceReservation.setText(String.format("$ %.2f", currentReservation.getTotalPrice()));

        if (currentReservation.isStatus()) {
            displayStatusReservation.setText("Active");
            //displayStatusReservation.setTextColor(Integer.parseInt("#008000"));
        } else {
            displayStatusReservation.setText("Cancelled");
            displayStatusReservation.setTextColor(Color.RED);
            //displayStatusReservation.setTextColor(Integer.parseInt("#FF0000"));
        }

        /*getting the reserved car details to show*/
        booked_car = dbHandler.getCarById(currentReservation.getCarId());
        carImageReservation.setImageResource(booked_car.getImage());
        displayCarNameReservation.setText(booked_car.getCarName());
        displayOccupancyCarReservation.setText(String.valueOf(booked_car.getCapacity()));

        /*deciding which button to show depending on the role of the user*/
        SystemUser currentuser = dbHandler.getUserByUsername(username);
        /*gettinh his role*/
        String roleof_user = currentuser.getRole();
        if (roleof_user.equalsIgnoreCase("User")) {
            /*usertype: USer show the cancel reservation button*/
            reservationButton.setText("Cancel Reservation");

        } else {
            /*usertype: Rental Manager show the delete user button*/
            reservationButton.setText("Delete Reservation");

        }
        if (!currentReservation.isStatus()) {
            reservationButton.setEnabled(false);
        }
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectedReservationActivity.this);
                if ("User".equalsIgnoreCase(roleof_user)) {
                    /*usertype: USer show the cancel reservation button*/

                    builder.setMessage("Do you want to Cancel reservation?")
                            .setPositiveButton(R.string.logout_confirmation_yes, (dialog, which) -> {
                                /*Please do the revoke status database stuff here*/

                                if (dbHandler.cancel_reservation(current_res_id)) {
                                    Toast.makeText(SelectedReservationActivity.this, "Reservation Cancelled Succesfully!!", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    Intent intent = new Intent(SelectedReservationActivity.this, SelectedReservationActivity.class);
                                    intent.putExtra("RESERVATION_ID", current_res_id);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.logout_confirmation_no, (dialog, which) -> dialog.dismiss());

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    //
                } else {
                    /*usertype: Rental Manager show the delete user button*/
                    builder.setMessage("Do you want to Delete reservation??")
                            .setPositiveButton(R.string.logout_confirmation_yes, (dialog, which) -> {
                                /*Please do the revoke status database stuff here*/

                                if (dbHandler.delete_reservation(current_res_id)) {
                                    Toast.makeText(SelectedReservationActivity.this, "Reservation Deleted Succesfully!!", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    Intent intent = new Intent(SelectedReservationActivity.this, SelectedReservationActivity.class);
                                    intent.putExtra("RESERVATION_ID", current_res_id);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.logout_confirmation_no, (dialog, which) -> dialog.dismiss());

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

    }



    }
