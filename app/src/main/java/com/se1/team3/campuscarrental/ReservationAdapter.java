package com.se1.team3.campuscarrental;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.Car;
import com.se1.team3.campuscarrental.models.Reservation;

import java.util.List;

public class ReservationAdapter extends ArrayAdapter<Reservation> {

    Context context;
    int resource;
    List<Reservation> reservationList;
    DBHandler dbHandler;

    public ReservationAdapter(@NonNull Context context, int resource, @NonNull List<Reservation> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.reservationList = objects;
        dbHandler = new SQLiteDBHandler(context);
    }

    public void setList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Nullable
    @Override
    public Reservation getItem(int position) {
        return this.reservationList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Reservation reservation = getItem(position);
        Car car = dbHandler.getCarById(reservation.getCarId());

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(this.resource, parent, false);
        }

        ImageView imageCar =  convertView.findViewById(R.id.imageView_r_car);
        TextView textViewFullName = convertView.findViewById(R.id.text_r_name);
        TextView textViewCarName = convertView.findViewById(R.id.text_r_item_car_name);
        TextView textViewTotalPrice = convertView.findViewById(R.id.text_r_item_total_price);
        TextView textViewStartDate = convertView.findViewById(R.id.text_r_item_start);
        TextView textViewEndDate = convertView.findViewById(R.id.text_r_item_end);
        TextView textReservationstatus = convertView.findViewById(R.id.text_r_Status);


        imageCar.setImageResource(car.getImage());
        textViewFullName.setText(reservation.getFirstName() + " " + reservation.getLastName());
        if (this.context instanceof MyReservationsActivity) {
            textViewFullName.setVisibility(View.GONE);
        }
        textViewCarName.setText(car.getCarName());
        textViewTotalPrice.setText(String.format("$ %.2f", reservation.getTotalPrice()));
        textViewStartDate.setText("From : " + reservation.getStartDate());
        textViewEndDate.setText("To   : " + reservation.getEndDate());
        if (reservation.isStatus()){
            textReservationstatus.setText("Active");
            textReservationstatus.setTextColor(Color.GREEN);
        }else{
            textReservationstatus.setText("Cancelled");
            textReservationstatus.setTextColor(Color.RED);
        }
        return convertView;
    }
}
