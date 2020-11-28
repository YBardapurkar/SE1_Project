package com.se1.team3.campuscarrental;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Car;

public class CarDetailsActivity extends AppCompatActivity{

    public DBHandler dbHandler;

    TextView carName, capacity;
    TextView weekday, weekend, week;
    TextView gpsPrice, onstarPrice, siriusxmPrice;
    ImageView imageCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        setTitle("Car Details");

        dbHandler = new SQLiteDBHandler(this);

        imageCar = findViewById(R.id.car_image);
        carName = findViewById(R.id.txt_car_name);
        capacity = findViewById(R.id.txt_car_capacity);
        weekday = findViewById(R.id.txt_weekday);
        weekend = findViewById(R.id.txt_weekend);
        week = findViewById(R.id.txt_week);
        gpsPrice = findViewById(R.id.txt_gps_rate);
        onstarPrice = findViewById(R.id.txt_onstar_rate);
        siriusxmPrice = findViewById(R.id.txt_siriusxm_rate);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        int carId = b.getInt("CAR_ID");
        Car car = dbHandler.getCarById(carId);

        carName.setText(car.getCarName());
        capacity.setText("" + car.getCapacity());
        weekday.setText(String.format("$ %.2f per day", car.getWeekday()));
        weekend.setText(String.format("$ %.2f per day", car.getWeekend()));
        week.setText(String.format("$ %.2f per week", car.getWeek()));
        gpsPrice.setText(String.format("$ %.2f per day", car.getGps()));
        siriusxmPrice.setText(String.format("$ %.2f per day", car.getSiriusXM()));
        onstarPrice.setText(String.format("$ %.2f per day", car.getOnStar()));

        imageCar.setImageResource(car.getImage());
    }
}
