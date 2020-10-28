package com.se1.team3.campuscarrental;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

    public class ViewCarDetailsActivity extends AppCompatActivity {

        public SQLiteDBHandler userDbOperations;
        TextView carNumber;
        TextView carName;
        TextView selectedCapacity;
        TextView startDateText;
        TextView endDateText;
        CheckBox gps;
        CheckBox onstar;
        CheckBox siriusxm;
        TextView gpsPrice;
        TextView onstarPrice;
        TextView siriusxmPrice;
        TextView totalCost;
        Button btnReserve;


        double calculatedCost = 0.00;
        String reservationNumber = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reservation_details);
            userDbOperations = new SQLiteDBHandler(this);
            btnReserve = (Button) findViewById(R.id.btnReserve);
            carNumber = (TextView) findViewById(R.id.carNumber);
            carName = (TextView) findViewById(R.id.carName);
            selectedCapacity = (TextView) findViewById(R.id.selectedCapacity);
            SharedPreferences sharedPreferences;
            String role = "";
            //String role = sharedPreferences.getString(ROLE, "")
            if(role == "user")
            {
                startDateText = (TextView) findViewById(R.id.startDate);
                endDateText = (TextView) findViewById(R.id.endDate);
                gps = (CheckBox) findViewById(R.id.gps);
                onstar = (CheckBox) findViewById(R.id.onstar);
                siriusxm = (CheckBox) findViewById(R.id.siriusxm);
            }
            if(role == "rental manager")
            {
                gpsPrice = (TextView) findViewById(R.id.gpsPrice);
                onstarPrice = (TextView) findViewById(R.id.onstar);
                siriusxmPrice = (TextView) findViewById(R.id.siriusxm);
            }

            totalCost = (TextView) findViewById(R.id.totalCost);

            final double gpsRate;
            final double onstarRate;
            final double siriusxmRate;

            // Get data sent from search vehicle screen
            ArrayList<String> carDetails, userInputs = null;
            Bundle args = getIntent().getBundleExtra("args");
            carDetails = (ArrayList<String>) args.getSerializable("car");
            userInputs = (ArrayList<String>) args.getSerializable("userInputs");

//        String carName = car.get(0);
//        String carNumber = car.get(1);
//        String maxCapacity = car.get(2);
//        String weekdayRate = car.get(3);
//        String weekendRate = car.get(4);
//        String weeklyRate = car.get(5);
//        String gpsRate = car.get(6);
//        String onstarRate = car.get(7);
//        String siriusxmRate = car.get(8);
            final String startDate = userInputs.get(1);
            final String endDate = userInputs.get(2);

            carName.setText(carDetails.get(0));
            carNumber.setText(carDetails.get(1));
            selectedCapacity.setText(userInputs.get(0));
            startDateText.setText(startDate);
            endDateText.setText(endDate);
            gpsRate = Double.parseDouble(carDetails.get(6));
            onstarRate = Double.parseDouble(carDetails.get(7));
            siriusxmRate = Double.parseDouble(carDetails.get(8));

            // Default cost + tax
            totalCost.setText("$999.9");
            // get no of days.
            int numberOfDays = getDaysBetweenDates(startDate, endDate);
            //double totalBaseCost = calculateBaseCost(numberOfDays, isMember);
            gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        calculatedCost += gpsRate;
                    } else {
                        calculatedCost -= gpsRate;
                    }
                    totalCost.setText("$" + Double.toString(calculatedCost));
                }
            });

            onstar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        calculatedCost += onstarRate;
                    } else {
                        calculatedCost -= onstarRate;
                    }
                    totalCost.setText("$" + Double.toString(calculatedCost));
                }
            });

            siriusxm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        calculatedCost += siriusxmRate;
                    } else {
                        calculatedCost -= siriusxmRate;
                    }
                    totalCost.setText("$" + Double.toString(calculatedCost));
                }
            });

            btnReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Generate a 6 digit reservation number
                    reservationNumber = generateReservationNumber(6);
                    // Save in car_reservation table
                    userDbOperations.InsertReservationDetails(reservationNumber, carName.getText().toString(),
                            startDate, endDate, Integer.parseInt(selectedCapacity.getText().toString()), Double.parseDouble(totalCost.getText().toString().replace("$", "")),
                            gps.isSelected(), onstar.isSelected(), siriusxm.isSelected(), null);

                    // Redirect/ toast
                }
            });
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

        private int getDaysBetweenDates(String startDate, String endDate) {
            int numberOfDays = 0;
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy HH:mm:ss");
            DateTime start = formatter.parseDateTime(startDate);
            DateTime end = formatter.parseDateTime(endDate);
            numberOfDays = Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays();
            if (numberOfDays == 0) {
                return 1;
            }
            return numberOfDays;
        }
    }

