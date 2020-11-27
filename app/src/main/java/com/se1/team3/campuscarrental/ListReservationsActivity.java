package com.se1.team3.campuscarrental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Reservation;

import java.util.ArrayList;
import java.util.Calendar;

public class ListReservationsActivity extends AppCompatActivity {

    DBHandler dbHandler;

    ListView listviewReservations;
    TextView textViewSelectedDate;
    EditText editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservations);

        setTitle("Reservations By Day");

        dbHandler = new SQLiteDBHandler(this);

        textViewSelectedDate = findViewById(R.id.textview_selected_date);
        editTextDate = findViewById(R.id.edit_date);
        listviewReservations = findViewById(R.id.listview_reservations);
        ArrayList<Reservation> reservations = new ArrayList<>();

        ReservationAdapter adapter = new ReservationAdapter(this, R.layout.item_reservation, reservations);
        listviewReservations.setAdapter(adapter);

        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(ListReservationsActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            editTextDate.setText(date);
                            reservations.clear();
                            reservations.addAll(dbHandler.getReservationsByDay(date));

                            textViewSelectedDate.setText("Reservations on " + date);
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });
        editTextDate.requestFocus();
    }
}