package com.se1.team3.campuscarrental;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Reservation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListReservationsActivity extends AppCompatActivity {

    DBHandler dbHandler;

    ListView listviewReservations;
    TextView textViewSelectedDate;
    EditText editTextDate;
    Button btnFindReservations;

    List<Reservation> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservations);

        setTitle("Reservations By Day");

        dbHandler = new SQLiteDBHandler(this);

        textViewSelectedDate = findViewById(R.id.textview_selected_date);
        editTextDate = findViewById(R.id.edit_date);
        btnFindReservations = findViewById(R.id.btn_get_reservations);
        listviewReservations = findViewById(R.id.listview_reservations);
        reservations = new ArrayList<>();

        ReservationAdapter adapter = new ReservationAdapter(this, R.layout.item_reservation, reservations);
        listviewReservations.setAdapter(adapter);

        editTextDate.setOnClickListener((v) -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(ListReservationsActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                editTextDate.setText(date);
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        editTextDate.requestFocus();

        btnFindReservations.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            String date = editTextDate.getText().toString();
            getReservations(date);
        });
    }

    private void getReservations(String date) {
        editTextDate.setText(date);
        reservations.clear();
        reservations.addAll(dbHandler.getReservationsByDay(date));

        textViewSelectedDate.setText("Reservations on " + date);
    }
}