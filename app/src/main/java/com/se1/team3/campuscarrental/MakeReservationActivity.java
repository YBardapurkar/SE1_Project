package com.se1.team3.campuscarrental;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Car;
import com.se1.team3.campuscarrental.models.SystemUser;

//import org.joda.time.DateTime;
//import org.joda.time.Days;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MakeReservationActivity extends AppCompatActivity {

    public DBHandler dbHandler;
    SharedPreferences sharedPreferences;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
    static final String ROLE = "role";

    TextView carName;
    TextView capacity;
    TextView weekday, weekend, week;
    TextView startDateText;
    TextView endDateText;
    CheckBox gps;
    CheckBox onstar;
    CheckBox siriusxm;

    TextView gpsPrice;
    TextView onstarPrice;
    TextView siriusxmPrice;

    TextView price, discount, tax;
    TextView totalCost;
    Button btnReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);

        setTitle("Car Details");

        dbHandler = new SQLiteDBHandler(this);
        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        btnReserve = (Button) findViewById(R.id.btn_reserve);

        String role = sharedPreferences.getString(ROLE, "");

        carName = (TextView) findViewById(R.id.txt_car_name);
        capacity = (TextView) findViewById(R.id.txt_car_capacity);

        weekday = findViewById(R.id.txt_weekday);
        weekend = findViewById(R.id.txt_weekend);
        week = findViewById(R.id.txt_week);

        startDateText = (TextView) findViewById(R.id.txt_start_date);
        endDateText = (TextView) findViewById(R.id.txt_end_date);

        gpsPrice = (TextView) findViewById(R.id.txt_gps_rate);
        onstarPrice = (TextView) findViewById(R.id.txt_onstar_rate);
        siriusxmPrice = (TextView) findViewById(R.id.txt_siriusxm_rate);

        price = findViewById(R.id.txt_price);
        discount = findViewById(R.id.txt_discount);
        tax = findViewById(R.id.txt_tax);
        totalCost = findViewById(R.id.txt_total_price);

        gps = (CheckBox) findViewById(R.id.checkbox_gps);
        onstar = (CheckBox) findViewById(R.id.checkbox_onstar);
        siriusxm = (CheckBox) findViewById(R.id.checkbox_siriusxm);

        int carId = 0;
        long startDate = 0;
        long endDate = 0;

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(role.equalsIgnoreCase("User")) {
            findViewById(R.id.row_user_gps).setVisibility(View.VISIBLE);
            findViewById(R.id.row_user_onstar).setVisibility(View.VISIBLE);
            findViewById(R.id.row_user_siriusxm).setVisibility(View.VISIBLE);
            findViewById(R.id.row_user_price).setVisibility(View.VISIBLE);
            findViewById(R.id.row_user_discount).setVisibility(View.VISIBLE);
            findViewById(R.id.row_user_tax).setVisibility(View.VISIBLE);
            findViewById(R.id.row_user_total_price).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_user_reserve_btn).setVisibility(View.VISIBLE);
            findViewById(R.id.row_start_date).setVisibility(View.VISIBLE);
            findViewById(R.id.row_end_date).setVisibility(View.VISIBLE);


            startDate = b.getLong("START_DATE_TIME");
            endDate = b.getLong("END_DATE_TIME");
            carId = b.getInt("CAR_ID");
        } else if(role.equalsIgnoreCase("Rental Manager")) {
            findViewById(R.id.row_rm_gps).setVisibility(View.VISIBLE);
            findViewById(R.id.row_rm_onstar).setVisibility(View.VISIBLE);
            findViewById(R.id.row_rm_siriusxm).setVisibility(View.VISIBLE);
            findViewById(R.id.row_weekday).setVisibility(View.VISIBLE);
            findViewById(R.id.row_weekend).setVisibility(View.VISIBLE);
            findViewById(R.id.row_week).setVisibility(View.VISIBLE);

            findViewById(R.id.row_user_gps).setVisibility(View.GONE);
            findViewById(R.id.row_user_onstar).setVisibility(View.GONE);
            findViewById(R.id.row_user_siriusxm).setVisibility(View.GONE);
            findViewById(R.id.row_user_price).setVisibility(View.GONE);
            findViewById(R.id.row_user_discount).setVisibility(View.GONE);
            findViewById(R.id.row_user_tax).setVisibility(View.GONE);
            findViewById(R.id.row_user_total_price).setVisibility(View.GONE);
            findViewById(R.id.layout_user_reserve_btn).setVisibility(View.GONE);
            findViewById(R.id.row_start_date).setVisibility(View.GONE);
            findViewById(R.id.row_end_date).setVisibility(View.GONE);

            carId = b.getInt("CAR_ID");
        }

        Car car = dbHandler.getCarById(carId);

        carName.setText(car.getCarName());
        capacity.setText("" + car.getCapacity());
        weekday.setText(String.format("$ %.2f per day", car.getWeekday()));
        weekend.setText(String.format("$ %.2f per day", car.getWeekend()));
        week.setText(String.format("$ %.2f per week", car.getWeek()));

        ((ImageView) findViewById(R.id.car_image)).setImageResource(car.getImage());

        Calendar cStart = Calendar.getInstance();
        cStart.setTimeInMillis(startDate);
        startDateText.setText(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(cStart.getTime()));

        Calendar cEnd = Calendar.getInstance();
        cEnd.setTimeInMillis(endDate);
        endDateText.setText(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(cEnd.getTime()));

        int days = cEnd.get(Calendar.DAY_OF_YEAR) - cStart.get(Calendar.DAY_OF_YEAR) + 1;

        gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculatePrice(car, days);
            }
        });

        onstar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculatePrice(car, days);
            }
        });

        siriusxm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculatePrice(car, days);
            }
        });

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MakeReservationActivity.this, "Reserved: " + generateReservationNumber(6), Toast.LENGTH_SHORT).show();
            }
        });

        calculatePrice(car, days);
    }

    private String generateReservationNumber(int stringSize) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(stringSize);

        for (int i = 0; i < stringSize; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

//        private int getDaysBetweenDates(String startDate, String endDate) {
//            int numberOfDays = 0;
//            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy HH:mm:ss");
//            DateTime start = formatter.parseDateTime(startDate);
//            DateTime end = formatter.parseDateTime(endDate);
//            numberOfDays = Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays();
//            if (numberOfDays == 0) {
//                return 1;
//            }
//            return numberOfDays;
//        }

    private void calculatePrice(Car car, int days) {
        double total = car.getWeekday() * days;

        double gpsCost = 0;
        double onStarCost = 0;
        double siriusCost = 0;

        if (gps.isChecked()) {
            gpsCost = car.getGps() * days;
        }
        if (onstar.isChecked()) {
            onStarCost = car.getOnStar() * days;
        }
        if (siriusxm.isChecked()) {
            siriusCost = car.getSiriusXM() * days;
        }

        gps.setText(String.format("GPS     : $ %.2f", gpsCost));
        gpsPrice.setText(String.format("$ %.2f per day", car.getGps()));
        siriusxm.setText(String.format("SiriusXM: $ %.2f", siriusCost));
        siriusxmPrice.setText(String.format("$ %.2f per day", car.getSiriusXM()));
        onstar.setText(String.format("OnStar  : $ %.2f", onStarCost));
        onstarPrice.setText(String.format("$ %.2f per day", car.getOnStar()));

        total += (gpsCost + onStarCost + siriusCost);

        price.setText(String.format("$ %.2f", total));

        SystemUser user = dbHandler.getUserByUsername(sharedPreferences.getString(USERNAME, ""));
        double d = 0;
        if (user.isMembership()) {
            d = total * 0.1;
        }
        discount.setText(String.format("- $ %.2f", d));
        total = total - d;

        double t = total * 0.0875;
        tax.setText(String.format("$ %.2f", t));

        total = total + t;
        totalCost.setText(String.format("$ %.2f", total));
    }
}

