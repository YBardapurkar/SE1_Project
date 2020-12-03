package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Car;
import com.se1.team3.campuscarrental.models.Reservation;
import com.se1.team3.campuscarrental.models.SystemUser;

import java.util.Calendar;

public class MakeReservationActivity extends AppCompatActivity {

    public DBHandler dbHandler;
    SharedPreferences sharedPreferences;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
//    static final String ROLE = "role";

    TextView carName, capacity, startDateText, endDateText;
    CheckBox checkBoxGps, checkBoxOnStar, checkBoxSiriusXm;

    TextView txtPrice, txtDiscount, txtTax, txtTotalCost;
    Button btnReserve;

    SystemUser user;
    double gps, onStar, siriusXm;
    double price, discount, tax, totalPrice;
    int carId = 0;
    long startDate = 0;
    long endDate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);

        setTitle("Make Reservation");

        dbHandler = new SQLiteDBHandler(this);
        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        user = dbHandler.getUserByUsername(sharedPreferences.getString(USERNAME, ""));

        btnReserve = findViewById(R.id.btn_make_reservation);
        if (!user.isStatus()) {
            btnReserve.setEnabled(false);
        }

//        String role = sharedPreferences.getString(ROLE, "");

        carName = findViewById(R.id.txt_car_name);
        capacity = findViewById(R.id.txt_car_capacity);

        startDateText = findViewById(R.id.txt_start_date);
        endDateText = findViewById(R.id.txt_end_date);

        txtPrice = findViewById(R.id.txt_price);
        txtDiscount = findViewById(R.id.txt_discount);
        txtTax = findViewById(R.id.txt_tax);
        txtTotalCost = findViewById(R.id.txt_total_price);

        checkBoxGps = findViewById(R.id.checkbox_gps);
        checkBoxOnStar = findViewById(R.id.checkbox_onstar);
        checkBoxSiriusXm = findViewById(R.id.checkbox_siriusxm);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        startDate = b.getLong("START_DATE_TIME");
        endDate = b.getLong("END_DATE_TIME");
        carId = b.getInt("CAR_ID");

        Car car = dbHandler.getCarById(carId);

        carName.setText(car.getCarName());
        capacity.setText("" + car.getCapacity());
        ((ImageView) findViewById(R.id.car_image)).setImageResource(car.getImage());



        checkBoxGps.setOnCheckedChangeListener((buttonView, isChecked) -> calculatePrice(car));

        checkBoxOnStar.setOnCheckedChangeListener((buttonView, isChecked) -> calculatePrice(car));

        checkBoxSiriusXm.setOnCheckedChangeListener((buttonView, isChecked) -> calculatePrice(car));

        btnReserve.setOnClickListener(v -> {
            Reservation reservation = new Reservation();
            reservation.setCarId(carId);
            reservation.setUsername(user.getUsername());
            reservation.setFirstName(user.getFirstName());
            reservation.setLastName(user.getLastName());
            reservation.setStartDate(DateUtils.toDateTimeString(startDate));
            reservation.setEndDate(DateUtils.toDateTimeString(endDate));
            reservation.setOnStar(onStar);
            reservation.setGps(gps);
            reservation.setSiriusXm(siriusXm);
            reservation.setPrice(price);
            reservation.setDiscount(discount);
            reservation.setTax(tax);
            reservation.setTotalPrice(totalPrice);
            reservation.setStatus(true);

            int res_id = (int) dbHandler.saveReservation(reservation);
            if (res_id > 0) {
                Toast.makeText(MakeReservationActivity.this, "Reservation Successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MakeReservationActivity.this, SelectedReservationActivity.class);
                i.putExtra("RESERVATION_ID", res_id);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(MakeReservationActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        calculatePrice(car);
    }

    private void calculatePrice(Car car) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTimeInMillis(startDate);
        startDateText.setText(DateUtils.toDateTimeString(startDate));

        Calendar cEnd = Calendar.getInstance();
        cEnd.setTimeInMillis(endDate);
        endDateText.setText(DateUtils.toDateTimeString(endDate));

        int weekdays = 0;
        int weekends = 0;
        for (; cStart.get(Calendar.DAY_OF_YEAR) <= cEnd.get(Calendar.DAY_OF_YEAR); cStart.add(Calendar.DAY_OF_YEAR, 1)) {
            System.out.println(DateUtils.toDateString(cStart.getTimeInMillis()) + " " + cStart.get(Calendar.DAY_OF_WEEK));
            if ((cStart.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cStart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) && weekends < 2) {
                weekends += 1;
            } else {
                weekdays += 1;
            }
        }

        gps = 0;
        onStar = 0;
        siriusXm = 0;

        if (checkBoxGps.isChecked()) {
            gps = car.getGps() * (weekdays + weekends);
        }
        if (checkBoxOnStar.isChecked()) {
            onStar = car.getOnStar() * (weekdays + weekends);
        }
        if (checkBoxSiriusXm.isChecked()) {
            siriusXm = car.getSiriusXM() * (weekdays + weekends);
        }

        checkBoxGps.setText(String.format("GPS     : $ %.2f", gps));
        checkBoxSiriusXm.setText(String.format("SiriusXM: $ %.2f", siriusXm));
        checkBoxOnStar.setText(String.format("OnStar  : $ %.2f", onStar));

        price = (car.getWeekday() * weekdays + car.getWeekend() * weekends) + (gps + onStar + siriusXm);

        discount = user.isMembership() ? price * 0.1 : 0;
        tax = price * 0.0875;
        totalPrice = price - discount + tax;

        txtPrice.setText(String.format("$ %.2f", price));
        txtDiscount.setText(String.format("- $ %.2f", discount));
        txtTax.setText(String.format("$ %.2f", tax));
        txtTotalCost.setText(String.format("$ %.2f", totalPrice));
    }
}

