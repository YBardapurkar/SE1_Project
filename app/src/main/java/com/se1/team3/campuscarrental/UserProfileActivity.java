package com.se1.team3.campuscarrental;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;


public class UserProfileActivity extends  AppCompatActivity{

    TextView name, uta_id, usrname, role, email, member, address, phone, status;
    Bundle bundle;
    String username, membership_valeu;
    DBHandler dbHandler;
    SystemUser curr_user;
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle(R.string.title_user_profile);

        dbHandler = new SQLiteDBHandler(this);
        bundle = getIntent().getExtras();

        name = findViewById(R.id.fullName_text);
        uta_id = findViewById(R.id.id_text);
        usrname = findViewById(R.id.username_text);
        role = findViewById(R.id.role_text);
        email = findViewById(R.id.email_text);
        member = findViewById(R.id.id_member);
        address = findViewById(R.id.add_text);
        phone = findViewById(R.id.id_phone);
        status = findViewById(R.id.id_status_text);
        username = bundle.getString("username");

        curr_user = dbHandler.getUserByUsername(username);
        name.setText(curr_user.getFirstName() + " " + curr_user.getLastName());
        uta_id.setText(curr_user.getUtaId());
        usrname.setText(username);
        role.setText(curr_user.getRole());
        email.setText(curr_user.getEmail());
        phone.setText(curr_user.getPhone());
        address.setText(curr_user.getStreet()+ "\n" + curr_user.getCity()+"\n"+curr_user.getState()+" - " + curr_user.getPin());
        if (curr_user.isMembership()) {
            membership_valeu = "Yes";
        }
        else {
            membership_valeu = "No";
        }
        member.setText(membership_valeu);
        if (curr_user.isStatus()) {
            status.setText("Active");
        } else {
            status.setText("Deactive");
        }
    }
}
