package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminEditsProfile extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
    static final String ROLE = "role";
    static final String TARGET_USERNAME = "targetUsername"; // the username of the sselected user in previous activity

    SystemUser selected_user;
    DBHandler dbHandler = null;


    @BindView(R.id.display_username_txt)
    TextView displayUsernameTxt;
    @BindView(R.id.display_role_txt)
    TextView displayRoleTxt;
    @BindView(R.id.update_first_name_txt)
    EditText updateFirstNameTxt;
    @BindView(R.id.update_last_name_txt)
    EditText updateLastNameTxt;
    @BindView(R.id.display_membership_txt)
    TextView displayMembershipTxt;
    @BindView(R.id.membership_row)
    TableRow membershipRow;
    @BindView(R.id.update_UTAID_txt)
    EditText updateUTAIDTxt;
    @BindView(R.id.update_phone_txt)
    EditText updatePhoneTxt;
    @BindView(R.id.update_Email_txt)
    EditText updateEmailTxt;
    @BindView(R.id.update_street_address_txt)
    EditText updateStreetAddressTxt;
    @BindView(R.id.update_city_txt)
    EditText updateCityTxt;
    @BindView(R.id.update_State_txt)
    EditText updateStateTxt;
    @BindView(R.id.update_zip_code_txt)
    EditText updateZipCodeTxt;
    @BindView(R.id.save_profile_updates_btn)
    Button saveProfileUpdatesBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edits_profile);
        ButterKnife.bind(this);

        setTitle("Edit Profile");

        dbHandler = new SQLiteDBHandler(this);
        sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        //object to save the data from the database
        //selected_user =dbHandler.getUserByUsername(sharedPreferences.getString(TARGET_USERNAME, null));
        selected_user = dbHandler.getUserByUsername("shubham");

        //setting the values
        displayUsernameTxt.setText(selected_user.getUsername());
        displayRoleTxt.setText(selected_user.getRole());
        updateFirstNameTxt.setText(selected_user.getFirstName());
        updateLastNameTxt.setText(selected_user.getLastName());
        String selecteduserrole = selected_user.getRole();
        String membership_valeu = "NO";
        if (selecteduserrole.equalsIgnoreCase("user")) {
            membershipRow.setVisibility(View.VISIBLE);
            if (selected_user.isMembership()) {
                membership_valeu = "YES";
            }
            displayMembershipTxt.setText(membership_valeu);
        } else {
            membershipRow.setVisibility(View.GONE);
        }

        updateUTAIDTxt.setText(selected_user.getUtaId());
        updatePhoneTxt.setText(selected_user.getPhone());
        updateEmailTxt.setText(selected_user.getEmail());
        updateStreetAddressTxt.setText(selected_user.getStreet());
        updateCityTxt.setText(selected_user.getCity());
        updateStateTxt.setText(selected_user.getState());
        updateZipCodeTxt.setText(selected_user.getPin());

    }

    @OnClick(R.id.save_profile_updates_btn)
    public void onViewClicked() {

        selected_user.setFirstName(updateFirstNameTxt.getText().toString());
        selected_user.setLastName(updateLastNameTxt.getText().toString());
        selected_user.setUtaId(updateUTAIDTxt.getText().toString());
        selected_user.setPhone(updatePhoneTxt.getText().toString());
        selected_user.setEmail(updateEmailTxt.getText().toString());
        selected_user.setStreet(updateStreetAddressTxt.getText().toString());
        selected_user.setCity(updateCityTxt.getText().toString());
        selected_user.setState(updateStateTxt.getText().toString());
        selected_user.setPin(updateZipCodeTxt.getText().toString());
        /*fire query to update these attributes*/
        /*Firstly diplaying the query and depending on the response updating or not*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want to Save Changes?")
                .setPositiveButton(R.string.logout_confirmation_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Please do the edit database  database stuff here*/

                        if (dbHandler.edit_profile(selected_user)) {
                            /*UPdate is Sucessfull*/
                            Toast.makeText(AdminEditsProfile.this, "Succesfully Updated.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AdminEditsProfile.this, AdminViewsUserDetailsActivity.class);
                            dialog.dismiss();
                            startActivity(intent);
                            finish();
                        } else {
                            /*Registration has failed*/
                            Toast.makeText(AdminEditsProfile.this, "Failed Updation!!!.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                    }
                })
                .setNegativeButton(R.string.logout_confirmation_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AdminEditsProfile.this, "Changes NOT SAVED!!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}