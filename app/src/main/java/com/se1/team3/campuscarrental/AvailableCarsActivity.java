package com.se1.team3.campuscarrental;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Car;

import java.util.Calendar;
import java.util.List;

public class AvailableCarsActivity extends AppCompatActivity {

    List<Car> carList;
    DBHandler dbHandler;

    EditText editTextDate, editTextTime;
    ListView listViewCars;
    Calendar dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cars);

        setTitle("Available Cars");

        dbHandler = new SQLiteDBHandler(this);

        editTextDate = findViewById(R.id.edit_date);
        editTextTime = findViewById(R.id.edit_time);

        listViewCars = findViewById(R.id.listview_avilable_cars);
        Button avaliableCarButton = findViewById(R.id.button_available_cars_submit);
        avaliableCarButton.setOnClickListener(v -> {
            carList.clear();
            carList.addAll(dbHandler.getAvailableCars(dateTime.getTimeInMillis()));
        });

        dateTime = Calendar.getInstance();
        dateTime.set(Calendar.SECOND, 0);
        editTextDate.setText(DateUtils.toDateString(dateTime.getTimeInMillis()));
        editTextTime.setText(DateUtils.toTimeString(dateTime.getTimeInMillis()));

        carList = dbHandler.getAvailableCars(dateTime.getTimeInMillis());
        CarAdapter adapter = new CarAdapter(this, R.layout.item_car, carList);
        listViewCars.setAdapter(adapter);

        listViewCars.setOnItemClickListener((parent, view, position, id) -> {
            Car selectedCar = carList.get(position);
            Intent intent = new Intent(AvailableCarsActivity.this, ViewCarDetailsActivity.class);
            intent.putExtra("CAR_ID", selectedCar.getId());
            startActivity(intent);
        });

        editTextDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AvailableCarsActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                    dateTime = Calendar.getInstance();
                    dateTime.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    editTextDate.setText(DateUtils.toDateString(dateTime.getTimeInMillis()));
                    editTextTime.requestFocus();
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        editTextTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AvailableCarsActivity.this, (view, hourOfDay, minute) -> {
                    dateTime.set(Calendar.HOUR_OF_DAY, mHour);
                    dateTime.set(Calendar.MINUTE, mMinute);
                    dateTime.set(Calendar.SECOND, 0);
                    editTextTime.setText(DateUtils.toDateString(dateTime.getTimeInMillis()));
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
    }
}