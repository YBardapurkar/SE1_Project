package com.se1.team3.campuscarrental;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;


public class UserUpdateProfileActivity extends  AppCompatActivity{
    Bundle bundle;
    EditText firstname, lastname, uta_id, email, phone, street, city, state, pin;
    String fn, ln, id, em, ph, st, ct, sta, pn, usr, flag;
    Button update_prof_btn;
    ToggleButton membership_button;
    SystemUser selected_user;
    DBHandler dbHandler;
    Intent intent;
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        setTitle("Update Profile");
        bundle = getIntent().getExtras();
        dbHandler = new SQLiteDBHandler(this);

        update_prof_btn = findViewById(R.id.update_prof_btn);
        firstname = findViewById(R.id.text_first_name);
        lastname = findViewById(R.id.text_last_name);
        uta_id = findViewById(R.id.text_uta_id);
        phone = findViewById(R.id.text_phone);
        email = findViewById(R.id.text_email);
        street = findViewById(R.id.text_street);
        city = findViewById(R.id.text_city);
        state = findViewById(R.id.text_state);
        pin = findViewById(R.id.text_pin);
        membership_button = findViewById(R.id.membership_button);

        fn = bundle.getString("first_name");
        ln = bundle.getString("last_name");
        id = bundle.getString("uta_id");
        em = bundle.getString("email");
        ph = bundle.getString("phone");
        st = bundle.getString("street");
        ct = bundle.getString("city");
        sta = bundle.getString("state");
        pn = bundle.getString("pin");
        usr = bundle.getString("username");
        flag = bundle.getString("flag");
        selected_user = dbHandler.getUserByUsername(usr);
        firstname.setText(fn);
        lastname.setText(ln);
        uta_id.setText(id);
        email.setText(em);
        phone.setText(ph);
        street.setText(st);
        city.setText(ct);
        state.setText(sta);
        pin.setText(pn);

        update_prof_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_user.setFirstName(firstname.getText().toString());
                selected_user.setLastName(lastname.getText().toString());
                selected_user.setUtaId(uta_id.getText().toString());
                selected_user.setPhone(phone.getText().toString());
                selected_user.setEmail(email.getText().toString());
                selected_user.setStreet(street.getText().toString());
                selected_user.setCity(city.getText().toString());
                selected_user.setState(state.getText().toString());
                selected_user.setPin(pin.getText().toString());
                /*fire query to update these attributes*/
                /*Firstly diplaying the query and depending on the response updating or not*/
                AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateProfileActivity.this);
                builder.setMessage("Are you want to Save Changes?")
                        .setPositiveButton(R.string.logout_confirmation_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*Please do the edit database  database stuff here*/

                                if (dbHandler.edit_profile(selected_user)) {
                                    /*UPdate is Sucessfull*/
                                    Toast.makeText(UserUpdateProfileActivity.this, flag, Toast.LENGTH_LONG).show();
                                    Toast.makeText(UserUpdateProfileActivity.this, "Succesfully Updated.", Toast.LENGTH_LONG).show();
                                    if (flag.equals("1")) {
                                        intent = new Intent(UserUpdateProfileActivity.this, RentalManagerHomeActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        if(flag.equals("0")){
                                            intent = new Intent(UserUpdateProfileActivity.this, UserHomeActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                    Toast.makeText(UserUpdateProfileActivity.this, "OUTSIDE", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    finish();
                                } else {
                                    /*Registration has failed*/
                                    Toast.makeText(UserUpdateProfileActivity.this, "Failed Updation!!!.", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }

                            }
                        })
                        .setNegativeButton(R.string.logout_confirmation_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(UserUpdateProfileActivity.this, "Changes NOT SAVED!!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
