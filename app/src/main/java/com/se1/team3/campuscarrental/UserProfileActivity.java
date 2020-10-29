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
import android.widget.TextView;
import android.widget.Toast;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;


public class UserProfileActivity extends  AppCompatActivity{

    EditText name, uta_id, email, member, address, phone, state, city, pincode;
    TextView role, status, usrname, roleLabel;
    Bundle bundle;
    String username, membership_valeu, flag;
    DBHandler dbHandler;
    SystemUser selected_user;
    Button update_prof;
    Intent intent;
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle(R.string.title_user_profile);

        dbHandler = new SQLiteDBHandler(this);
        bundle = getIntent().getExtras();
        flag = bundle.getString("flag");
        update_prof = findViewById(R.id.update_prof_btn);
        name = findViewById(R.id.fullName_text);
        uta_id = findViewById(R.id.id_text);
        usrname = findViewById(R.id.username_text);
        role = findViewById(R.id.role_text);
        email = findViewById(R.id.email_text);
        member = findViewById(R.id.id_member);
        address = findViewById(R.id.add_text);
        phone = findViewById(R.id.id_phone);
        status = findViewById(R.id.id_status_text);
        state = findViewById(R.id.id_state_txt);
        city = findViewById(R.id.id_city_txt);
        pincode = findViewById(R.id.id_text_pincode);
        username = bundle.getString("username");
        roleLabel = findViewById(R.id.id_role);

        selected_user = dbHandler.getUserByUsername(username);
        name.setText(selected_user.getFirstName() + " " + selected_user.getLastName());
        uta_id.setText(selected_user.getUtaId());
        usrname.setText(username);
        role.setText(selected_user.getRole());
        email.setText(selected_user.getEmail());
        phone.setText(selected_user.getPhone());
        address.setText(selected_user.getStreet());
        city.setText(selected_user.getCity());
        state.setText(selected_user.getState());
        pincode.setText(selected_user.getPin());
        if(flag.equals("1")){
            role.setVisibility(View.INVISIBLE);
            roleLabel.setVisibility(View.INVISIBLE);
        }
        if (selected_user.isMembership()) {
            membership_valeu = "Yes";
        }
        else {
            membership_valeu = "No";
        }
        member.setText(membership_valeu);
        if (selected_user.isStatus()) {
            status.setText("Active");
        } else {
            status.setText("Deactive");
        }
        update_prof.setOnClickListener(v -> {
            Toast.makeText(UserProfileActivity.this, "User Profile Update", Toast.LENGTH_SHORT).show();
            updateProfile(selected_user);
        });
    }

    public void updateProfile(SystemUser curr){
        Toast.makeText(this, "Update Profile", Toast.LENGTH_SHORT).show();
        String names = name.getText().toString();
        String[] name = names.split("\\s+");
        String first = name[0];
        String last = name[1];
        selected_user.setFirstName(first);
        selected_user.setLastName(last);
        selected_user.setUtaId(uta_id.getText().toString());
        selected_user.setPhone(phone.getText().toString());
        selected_user.setEmail(email.getText().toString());
        selected_user.setStreet(address.getText().toString());
        selected_user.setCity(city.getText().toString());
        selected_user.setState(state.getText().toString());
        selected_user.setPin(pincode.getText().toString());
        /*fire query to update these attributes*/
        /*Firstly diplaying the query and depending on the response updating or not*/
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setMessage("Are you want to Save Changes?")
                .setPositiveButton(R.string.logout_confirmation_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Please do the edit database  database stuff here*/

                        if (dbHandler.edit_profile(selected_user)) {
                            /*UPdate is Sucessfull*/
                            Toast.makeText(UserProfileActivity.this, flag, Toast.LENGTH_LONG).show();
                            Toast.makeText(UserProfileActivity.this, "Succesfully Updated.", Toast.LENGTH_LONG).show();
                            if (flag.equals("1")) {
                                intent = new Intent(UserProfileActivity.this, RentalManagerHomeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                if(flag.equals("0")){
                                    intent = new Intent(UserProfileActivity.this, UserHomeActivity.class);
                                    startActivity(intent);
                                }
                            }
                            Toast.makeText(UserProfileActivity.this, "OUTSIDE", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                            finish();
                        } else {
                            /*Registration has failed*/
                            Toast.makeText(UserProfileActivity.this, "Failed Updation!!!.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                    }
                })
                .setNegativeButton(R.string.logout_confirmation_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UserProfileActivity.this, "Changes NOT SAVED!!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
