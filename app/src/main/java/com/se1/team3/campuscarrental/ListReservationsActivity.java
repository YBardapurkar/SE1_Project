package com.se1.team3.campuscarrental;

import android.app.DatePickerDialog;
import android.content.Intent;
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
    Calendar dateTime;
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

            dateTime = Calendar.getInstance();
            dateTime.set(Calendar.SECOND, 0);
            dateTime.set(mYear, mMonth, mDay, 0, 0, 0);
            DatePickerDialog datePickerDialog = new DatePickerDialog(ListReservationsActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                dateTime.set(Calendar.YEAR, year);
                dateTime.set(Calendar.MONTH, monthOfYear);
                dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = DateUtils.toDateString(dateTime.getTimeInMillis());
                editTextDate.setText(date);
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        editTextDate.callOnClick();

        btnFindReservations.setOnClickListener(v -> {
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (NullPointerException e) {

            }

            String date = editTextDate.getText().toString();
            //System.out.println(date);
            getReservations(date);
        });

        listviewReservations.setOnItemClickListener(((parent, view, position, id) -> {
            Reservation nowselectedReservatopn = reservations.get(position);
            Intent intent = new Intent(ListReservationsActivity.this, SelectedReservationActivity.class);
            intent.putExtra("RESERVATION_ID", nowselectedReservatopn.getId());
            startActivity(intent);
        }));
    }

    private void getReservations(String date) {
        editTextDate.setText(date);
        reservations.clear();
        reservations.addAll(dbHandler.getReservationsByDay(date));
        System.out.println(reservations.toString());
        textViewSelectedDate.setText("Reservations on " + date);
    }
}