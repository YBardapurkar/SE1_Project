package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;


public class UserProfileActivity extends  AppCompatActivity{

    EditText firstName, lastName, utaId, email, phone, address, city, state, zip;
    ToggleButton member;
    TextView role, status, username;
    Button buttonUpdateProfile;

    DBHandler dbHandler;
    SharedPreferences sharedPreferences;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
    static final String ROLE = "role";

    SystemUser loggedInUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle(R.string.title_user_profile);

        dbHandler = new SQLiteDBHandler(this);
        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        firstName = findViewById(R.id.profile_firstName);
        lastName = findViewById(R.id.profile_lastName);
        utaId = findViewById(R.id.profile_utaId);
        username = findViewById(R.id.profile_username);
        role = findViewById(R.id.profile_role);
        email = findViewById(R.id.profile_email);
        member = findViewById(R.id.profile_membership);
        address = findViewById(R.id.profile_address);
        phone = findViewById(R.id.profile_phone);
        status = findViewById(R.id.profile_status);
        state = findViewById(R.id.profile_state);
        city = findViewById(R.id.profile_city);
        zip = findViewById(R.id.profile_zip);

        buttonUpdateProfile = findViewById(R.id.update_prof_btn);

        loggedInUser = dbHandler.getUserByUsername(sharedPreferences.getString(USERNAME, ""));

        firstName.setText(loggedInUser.getFirstName());
        lastName.setText(loggedInUser.getLastName());
        utaId.setText(loggedInUser.getUtaId());
        username.setText(loggedInUser.getUsername());
        role.setText(loggedInUser.getRole());
        email.setText(loggedInUser.getEmail());
        phone.setText(loggedInUser.getPhone());
        address.setText(loggedInUser.getStreet());
        city.setText(loggedInUser.getCity());
        state.setText(loggedInUser.getState());
        zip.setText(loggedInUser.getPin());
        member.setChecked(loggedInUser.isMembership());
        status.setText(loggedInUser.isStatus() ? "Active" : "Inactive");

        if(! "user".equalsIgnoreCase(sharedPreferences.getString(ROLE, ""))){
            findViewById(R.id.row_profile_membership).setVisibility(View.GONE);
        }

        buttonUpdateProfile.setOnClickListener(v -> updateProfile());
    }

    public void updateProfile(){
        loggedInUser.setFirstName(firstName.getText().toString());
        loggedInUser.setLastName(lastName.getText().toString());
        loggedInUser.setUtaId(utaId.getText().toString());
        loggedInUser.setPhone(phone.getText().toString());
        loggedInUser.setEmail(email.getText().toString());
        loggedInUser.setStreet(address.getText().toString());
        loggedInUser.setCity(city.getText().toString());
        loggedInUser.setState(state.getText().toString());
        loggedInUser.setPin(zip.getText().toString());
        loggedInUser.setMembership(member.isChecked());

        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setMessage("Save Changes?")
                .setPositiveButton(R.string.logout_confirmation_yes, (dialog, which) -> {
                    /*Please do the edit database  database stuff here*/

                    if (dbHandler.edit_profile(loggedInUser)) {
                        Toast.makeText(UserProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        /*Registration has failed*/
                        Toast.makeText(UserProfileActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.logout_confirmation_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
