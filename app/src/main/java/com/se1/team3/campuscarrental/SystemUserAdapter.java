package com.se1.team3.campuscarrental;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.se1.team3.campuscarrental.models.SystemUser;

public class SystemUserAdapter extends ArrayAdapter<SystemUser> {
//    private List<SystemUser> userList = null;
    ArrayList<SystemUser> listUsers;

    public SystemUserAdapter(Context context, int adminusersearch, ArrayList<SystemUser> users) {
        super(context, 0, users);
        listUsers = users;
    }

    public void setListUsers(ArrayList<SystemUser> listUsers) {
        this.listUsers = listUsers;
    }

    @Nullable
    @Override
    public SystemUser getItem(int position) {
        return listUsers.get(position);
    }
    @Override
    public int getCount() {
        return listUsers.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        SystemUser users = getItem(position);
        TextView tvUName = (TextView) convertView.findViewById(R.id.tvUName);
        TextView tvFName = (TextView) convertView.findViewById(R.id.tvFName);
        TextView tvLName = (TextView) convertView.findViewById(R.id.tvLName);
        TextView tvRole = (TextView) convertView.findViewById(R.id.tvRole);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);

        tvUName.setText(users.getUsername());
        tvFName.setText(users.getFirstName());
        tvLName.setText(users.getLastName());
        tvRole.setText(users.getRole());
        if (!users.isStatus()){
            tvStatus.setText("disabled");
        }
        return convertView;


    }

//    public void filter(String charText) {
//        charText = charText.toLowerCase();
//        listUsers.clear();
//        if (charText.length() == 0) {
//            listUsers.addAll(allUsers);
//        } else {
//            for (SystemUser wp : allUsers) {
//                if (wp.getFirstName().toLowerCase().contains(charText)) {
//                    listUsers.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//
//    }
}
