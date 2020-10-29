package com.se1.team3.campuscarrental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

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
        avaliableCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carList.clear();
                carList.addAll(dbHandler.getAvailableCars(dateTime.getTimeInMillis()));
            }
        });

        dateTime = Calendar.getInstance();
        dateTime.set(Calendar.SECOND, 0);
        editTextDate.setText(String.format("%2d-%2d-%4d", dateTime.get(Calendar.DAY_OF_MONTH), (dateTime.get(Calendar.MONTH) + 1), dateTime.get(Calendar.YEAR)));
        editTextTime.setText(String.format("%02d:%02d", dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE)));

        carList = dbHandler.getAvailableCars(dateTime.getTimeInMillis());
        CarAdapter adapter = new CarAdapter(this, R.layout.item_car, carList);
        listViewCars.setAdapter(adapter);

        listViewCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car selectedCar = carList.get(position);
                Intent intent = new Intent(AvailableCarsActivity.this, ViewCarDetailsActivity.class);
                intent.putExtra("CAR_ID", selectedCar.getId());
                startActivity(intent);
            }
        });

        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AvailableCarsActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            dateTime = Calendar.getInstance();
                            dateTime.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                            editTextTime.requestFocus();
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                    datePickerDialog.show();
                }
            }
        });

        editTextTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AvailableCarsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            editTextTime.setText(String.format("%02d:%02d", hourOfDay, minute));

                            dateTime.set(Calendar.HOUR_OF_DAY, mHour);
                            dateTime.set(Calendar.MINUTE, mMinute);
                            dateTime.set(Calendar.SECOND, 0);
                        }
                    }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });
    }
}