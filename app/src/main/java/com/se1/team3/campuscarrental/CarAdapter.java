package com.se1.team3.campuscarrental;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.se1.team3.campuscarrental.models.Car;

import java.util.List;

public class CarAdapter extends ArrayAdapter<Car> {

    Context context;
    int resource;
    List<Car> carsList;

    public CarAdapter(@NonNull Context context, int resource, @NonNull List<Car> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.carsList = objects;
    }

    public void setList(List<Car> carsList) {
        this.carsList = carsList;
    }

    @Nullable
    @Override
    public Car getItem(int position) {
        return this.carsList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Car car = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_car, parent, false);
        }

        ImageView imageCar = (ImageView) convertView.findViewById(R.id.imageView_car);
        TextView textViewCarName = (TextView) convertView.findViewById(R.id.text_car_item_name);
        TextView textViewCarCapacity = (TextView) convertView.findViewById(R.id.text_car_item_capacity);
        TextView textViewCarPrice = (TextView) convertView.findViewById(R.id.text_car_item_price);

        imageCar.setImageResource(car.getImage());
        textViewCarName.setText(car.getCarName());
        textViewCarCapacity.setText(String.format(context.getResources().getString(R.string.car_item_capacity), car.getCapacity()));
        textViewCarPrice.setText(String.format(context.getResources().getString(R.string.car_item_starting_price), car.getWeekday()));

        return convertView;
    }
}
