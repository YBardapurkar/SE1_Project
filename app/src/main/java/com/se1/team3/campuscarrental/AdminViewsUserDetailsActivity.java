package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class AdminViewsUserDetailsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username";
    static final String ROLE = "role";

    SystemUser selected_user;
    DBHandler dbHandler = null;

    @BindView(R.id.display_username_txt)
    TextView displayUsernameTxt;
    @BindView(R.id.display_role_txt)
    TextView displayRoleTxt;
    @BindView(R.id.display_first_name_txt)
    TextView displayFirstNameTxt;
    @BindView(R.id.display_last_name_txt)
    TextView displayLastNameTxt;
    @BindView(R.id.txtmembership)
    TextView txtmembership;
    @BindView(R.id.display_membership_txt)
    TextView displayMembershipTxt;
    @BindView(R.id.display_UTAID_txt)
    TextView displayUTAIDTxt;
    @BindView(R.id.display_phone_txt)
    TextView displayPhoneTxt;
    @BindView(R.id.display_Email_txt)
    TextView displayEmailTxt;
    @BindView(R.id.display_street_address_txt)
    TextView displayStreetAddressTxt;
    @BindView(R.id.display_city_txt)
    TextView displayCityTxt;
    @BindView(R.id.display_State_txt)
    TextView displayStateTxt;
    @BindView(R.id.display_zip_code_txt)
    TextView displayZipCodeTxt;
    @BindView(R.id.change_role_btn)
    Button changeRoleBtn;
    @BindView(R.id.edit_profile_btn)
    Button editProfileBtn;
    @BindView(R.id.Revoke_renter_btn)
    Button RevokeRenterBtn;
    @BindView(R.id.membership_row)
    TableRow membershipRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_views_user_details2);
        ButterKnife.bind(this);

        setTitle("User Details");

        dbHandler = new SQLiteDBHandler(this);
        sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        //object to save the data from the database
        selected_user = dbHandler.getUserByUsername("shubham");

        //setting the values
        displayUsernameTxt.setText(selected_user.getUsername());
        displayRoleTxt.setText(selected_user.getRole());
        displayFirstNameTxt.setText(selected_user.getFirstName());
        displayLastNameTxt.setText(selected_user.getLastName());
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

        displayUTAIDTxt.setText(selected_user.getUtaId());
        displayPhoneTxt.setText(selected_user.getPhone());
        displayEmailTxt.setText(selected_user.getEmail());
        displayStreetAddressTxt.setText(selected_user.getStreet());
        displayCityTxt.setText(selected_user.getCity());
        displayStateTxt.setText(selected_user.getState());
        displayZipCodeTxt.setText(selected_user.getPin());


    }

    @OnClick(R.id.change_role_btn)
    public void onChangeRoleBtnClicked() {
    }

    @OnClick(R.id.edit_profile_btn)
    public void onEditProfileBtnClicked() {
        //calling the edit profile activity
        Intent intent = new Intent(AdminViewsUserDetailsActivity.this, AdminEditsProfile.class);
        startActivity(intent);

    }

    @OnClick(R.id.Revoke_renter_btn)
    public void onRevokeRenterBtnClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you want to Revoke this user?")
                .setPositiveButton(R.string.logout_confirmation_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Please do the revoke status database stuff here*/
                        Toast.makeText(AdminViewsUserDetailsActivity.this, "User Status Revoked Successfully!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.logout_confirmation_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AdminViewsUserDetailsActivity.this, "User Revoke Cancelled!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}