package com.se1.team3.campuscarrental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Car;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SearchCarActivity extends AppCompatActivity {

    List<Car> carList;
    CarAdapter adapter;
    DBHandler dbHandler;

    EditText editTextStartDate, editTextStartTime, editTextEndDate, editTextEndTime;
    ListView listViewCars;
    Spinner capacitySpinner;
    Calendar startDateTime, endDateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car);

        dbHandler = new SQLiteDBHandler(this);

        editTextStartDate = findViewById(R.id.edit_start_date);
        editTextStartTime = findViewById(R.id.edit_start_time);
        editTextEndDate = findViewById(R.id.edit_end_date);
        editTextEndTime = findViewById(R.id.edit_end_time);

        capacitySpinner = findViewById(R.id.spinner_capacity);
        Integer[] capacities = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, capacities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capacitySpinner.setAdapter(spinnerAdapter);

        listViewCars = findViewById(R.id.listview_cars);
        Button searchCarButton = findViewById(R.id.button_search_car);

        searchCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                carList.clear();
                int capacity = capacities[capacitySpinner.getSelectedItemPosition()];
                long startTime = startDateTime.getTimeInMillis();
                long endTime = endDateTime.getTimeInMillis();
                carList.addAll(dbHandler.searchCars(capacity, startTime, endTime));
                adapter.notifyDataSetChanged();
            }
        });

        startDateTime = Calendar.getInstance();
        startDateTime.add(Calendar.HOUR_OF_DAY, 1);
        startDateTime.set(Calendar.MINUTE, 0);
        startDateTime.set(Calendar.SECOND, 0);
        editTextStartDate.setText(String.format("%2d-%2d-%4d", startDateTime.get(Calendar.DAY_OF_MONTH), (startDateTime.get(Calendar.MONTH) + 1), startDateTime.get(Calendar.YEAR)));
        editTextStartTime.setText(String.format("%02d:%02d", startDateTime.get(Calendar.HOUR_OF_DAY), 0));

        endDateTime = Calendar.getInstance();
        endDateTime.set(Calendar.MINUTE, 0);
        endDateTime.set(Calendar.SECOND, 0);
        endDateTime.add(Calendar.HOUR_OF_DAY, 2);
        editTextEndDate.setText(String.format("%2d-%2d-%4d", endDateTime.get(Calendar.DAY_OF_MONTH), (endDateTime.get(Calendar.MONTH) + 1), endDateTime.get(Calendar.YEAR)));
        editTextEndTime.setText(String.format("%02d:%02d", endDateTime.get(Calendar.HOUR_OF_DAY), 0));

        carList = dbHandler.searchCars(capacities[capacitySpinner.getSelectedItemPosition()], startDateTime.getTimeInMillis(), endDateTime.getTimeInMillis());
        adapter = new CarAdapter(this, R.layout.item_car, carList);
        listViewCars.setAdapter(adapter);

        editTextStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(SearchCarActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            editTextStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            startDateTime = Calendar.getInstance();
                            startDateTime.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                            editTextStartTime.requestFocus();
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                    datePickerDialog.show();
                }
            }
        });

        editTextStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(SearchCarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            editTextStartTime.setText(String.format("%02d:%02d", hourOfDay, 0));

                            startDateTime.set(Calendar.HOUR_OF_DAY, mHour);
                            startDateTime.set(Calendar.MINUTE, mMinute);
                            startDateTime.set(Calendar.SECOND, 0);

                            editTextEndDate.requestFocus();
                        }
                    }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });

        editTextEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(SearchCarActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            editTextEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            endDateTime = Calendar.getInstance();
                            endDateTime.set(year, monthOfYear, dayOfMonth, 0, 0, 0);

                            editTextEndTime.requestFocus();
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.getDatePicker().setMinDate(startDateTime.getTimeInMillis());
                    datePickerDialog.show();
                }
            }
        });

        editTextEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(SearchCarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            endDateTime.set(Calendar.HOUR_OF_DAY, mHour);
                            endDateTime.set(Calendar.MINUTE, mMinute);
                            endDateTime.set(Calendar.SECOND, 0);

                            editTextEndTime.setText(String.format("%02d:%02d", hourOfDay, 0));
                        }
                    }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });

        listViewCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car selectedCar = carList.get(position);
                Intent intent = new Intent(SearchCarActivity.this, ViewCarDetailsActivity.class);
                intent.putExtra("CAR_ID", selectedCar.getId());
                intent.putExtra("START_DATE_TIME", startDateTime.getTimeInMillis());
                intent.putExtra("END_DATE_TIME", endDateTime.getTimeInMillis());
                startActivity(intent);
            }
        });
    }


}