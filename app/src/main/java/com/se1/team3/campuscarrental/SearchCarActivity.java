package com.se1.team3.campuscarrental;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Car;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchCarActivity extends AppCompatActivity {

    List<Car> carList;
    CarAdapter adapter;
    DBHandler dbHandler;

    EditText editTextStartDate, editTextStartTime, editTextEndDate, editTextEndTime;
    ListView listViewCars;
    Spinner capacitySpinner;
    Calendar startDateTime, endDateTime, now;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car);
        setTitle("Search Car");

        dbHandler = new SQLiteDBHandler(this);

        editTextStartDate = findViewById(R.id.edit_start_date);
        editTextStartTime = findViewById(R.id.edit_start_time);
        editTextEndDate = findViewById(R.id.edit_end_date);
        editTextEndTime = findViewById(R.id.edit_end_time);

        capacitySpinner = findViewById(R.id.spinner_capacity);
        Integer[] capacities = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, capacities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capacitySpinner.setAdapter(spinnerAdapter);

        listViewCars = findViewById(R.id.listview_cars);
        Button searchCarButton = findViewById(R.id.button_search_car);

        searchCarButton.setOnClickListener(v -> {
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {

            }

            carList.clear();
            int capacity = capacities[capacitySpinner.getSelectedItemPosition()];
            long startTime = startDateTime.getTimeInMillis();
            long endTime = endDateTime.getTimeInMillis();
            carList.addAll(dbHandler.searchCars(capacity, DateUtils.toDateTimeString(startTime), DateUtils.toDateTimeString(endTime)));
            adapter.notifyDataSetChanged();
        });

        now = Calendar.getInstance();

        startDateTime = DateUtils.getNextStartTime();
        editTextStartDate.setText(DateUtils.toDateString(startDateTime.getTimeInMillis()));
        editTextStartTime.setText(DateUtils.toTimeString(startDateTime.getTimeInMillis()));

        endDateTime = DateUtils.getNextEndTime(startDateTime);
        editTextEndDate.setText(DateUtils.toDateString(endDateTime.getTimeInMillis()));
        editTextEndTime.setText(DateUtils.toTimeString(endDateTime.getTimeInMillis()));

//        carList = dbHandler.searchCars(capacities[capacitySpinner.getSelectedItemPosition()], DateUtils.toDateTimeString(startDateTime.getTimeInMillis()), DateUtils.toDateTimeString(endDateTime.getTimeInMillis()));
        carList = new ArrayList<>();
        adapter = new CarAdapter(this, R.layout.item_car, carList);
        listViewCars.setAdapter(adapter);

        editTextStartDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchCarActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                    startDateTime.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    editTextStartDate.setText(DateUtils.toDateString(startDateTime.getTimeInMillis()));
                    editTextStartTime.requestFocus();
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        editTextStartTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(SearchCarActivity.this, (view, hourOfDay, minute) -> {
                    startDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    startDateTime.set(Calendar.MINUTE, minute);
                    startDateTime.set(Calendar.SECOND, 0);
                    startDateTime = DateUtils.getNextTime(startDateTime.getTimeInMillis());
                    if (startDateTime.after(Calendar.getInstance())) {
                        startDateTime = DateUtils.getNextStartTime();
                    }

                    editTextStartTime.setText(DateUtils.toTimeString(startDateTime.getTimeInMillis()));
                    editTextEndDate.requestFocus();
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        editTextEndDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchCarActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                    endDateTime.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    if (startDateTime.after(endDateTime)) {
                        endDateTime = DateUtils.getNextEndTime(startDateTime);
                    }

                    editTextEndDate.setText(DateUtils.toDateString(endDateTime.getTimeInMillis()));
                    editTextEndTime.requestFocus();
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(startDateTime.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        editTextEndTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(SearchCarActivity.this, (view, hourOfDay, minute) -> {
                    endDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    endDateTime.set(Calendar.MINUTE, minute);
                    endDateTime.set(Calendar.SECOND, 0);

                    if (startDateTime.after(endDateTime)) {
                        endDateTime = DateUtils.getNextTime(startDateTime.getTimeInMillis());
                    }

                    endDateTime = DateUtils.getNextTime(endDateTime.getTimeInMillis());

                    editTextEndTime.setText(DateUtils.toTimeString(endDateTime.getTimeInMillis()));
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        listViewCars.setOnItemClickListener((parent, view, position, id) -> {
            Car selectedCar = carList.get(position);
            Intent intent = new Intent(SearchCarActivity.this, MakeReservationActivity.class);
            intent.putExtra("CAR_ID", selectedCar.getId());
            intent.putExtra("START_DATE_TIME", startDateTime.getTimeInMillis());
            intent.putExtra("END_DATE_TIME", endDateTime.getTimeInMillis());
            startActivity(intent);
        });
    }
}