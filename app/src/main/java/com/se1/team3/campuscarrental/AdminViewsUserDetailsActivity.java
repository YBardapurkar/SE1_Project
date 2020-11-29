package com.se1.team3.campuscarrental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

//    SharedPreferences sharedPreferences;

//    static final String PREFERENCES = "SharedPreferences";
//    static final String USERNAME = "username";
//    static final String ROLE = "role";

    SystemUser selected_user;
    DBHandler dbHandler = null;

    @BindView(R.id.display_username_txt)
    TextView displayUsernameTxt;
    @BindView(R.id.txtmembership)
    TextView txtmembership;
    @BindView(R.id.display_membership_txt)
    TextView displayMembershipTxt;
    @BindView(R.id.change_role_btn)
    Button changeRoleBtn;
    @BindView(R.id.edit_profile_btn)
    Button editProfileBtn;
    @BindView(R.id.Revoke_renter_btn)
    Button RevokeRenterBtn;
    @BindView(R.id.membership_row)
    TableRow membershipRow;
    @BindView(R.id.display_role_spinner)
    Spinner displayRoleSpinner;
    @BindView(R.id.display_first_name_txt)
    EditText displayFirstNameTxt;
    @BindView(R.id.display_last_name_txt)
    EditText displayLastNameTxt;
    @BindView(R.id.display_UTAID_txt)
    EditText displayUTAIDTxt;
    @BindView(R.id.display_phone_txt)
    EditText displayPhoneTxt;
    @BindView(R.id.display_Email_txt)
    EditText displayEmailTxt;
    @BindView(R.id.display_street_address_txt)
    EditText displayStreetAddressTxt;
    @BindView(R.id.display_city_txt)
    EditText displayCityTxt;
    @BindView(R.id.display_State_txt)
    EditText displayStateTxt;
    @BindView(R.id.display_zip_code_txt)
    EditText displayZipCodeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_views_user_details2);
        ButterKnife.bind(this);

        setTitle("User Details");

        dbHandler = new SQLiteDBHandler(this);
//        sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        //object to save the data from the database
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String username = null;
        if (b != null) {
            username = (String) b.get("USERNAME");
        } else {
            finish();
        }
        selected_user = dbHandler.getUserByUsername(username);
        //populating the drop down for role
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.edit_profile_role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        displayRoleSpinner.setAdapter(adapter);
        if ("User".equalsIgnoreCase(selected_user.getRole())) {
            displayRoleSpinner.setSelection(0);
        } else {
            displayRoleSpinner.setSelection(1);
            findViewById(R.id.Revoke_renter_btn).setVisibility(View.GONE);
        }

        if (!selected_user.isStatus()) {
            findViewById(R.id.Revoke_renter_btn).setEnabled(false);
        }

        //setting the values
        displayUsernameTxt.setText(selected_user.getUsername());

        displayFirstNameTxt.setText(selected_user.getFirstName());
        displayLastNameTxt.setText(selected_user.getLastName());
        if ("User".equalsIgnoreCase(selected_user.getRole())) {
            membershipRow.setVisibility(View.VISIBLE);
            displayMembershipTxt.setText(selected_user.isMembership() ? "YES" : "NO");
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
        String newrole = displayRoleSpinner.getSelectedItem().toString();
        selected_user.setRole(newrole);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Change the role to " + newrole + "?")
                .setPositiveButton(R.string.logout_confirmation_yes, (dialog, which) -> {
                    /*Please do the revoke status database stuff here*/
                    if (dbHandler.change_role(selected_user)) {
                        Toast.makeText(AdminViewsUserDetailsActivity.this, "Role Changed", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.logout_confirmation_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @OnClick(R.id.edit_profile_btn)
    public void onEditProfileBtnClicked() {
        selected_user.setFirstName(displayFirstNameTxt.getText().toString());
        selected_user.setLastName(displayLastNameTxt.getText().toString());
        selected_user.setUtaId(displayUTAIDTxt.getText().toString());
        selected_user.setPhone(displayPhoneTxt.getText().toString());
        selected_user.setEmail(displayEmailTxt.getText().toString());
        selected_user.setStreet(displayStreetAddressTxt.getText().toString());
        selected_user.setCity(displayCityTxt.getText().toString());
        selected_user.setState(displayStateTxt.getText().toString());
        selected_user.setPin(displayZipCodeTxt.getText().toString());
        /*fire query to update these attributes*/
        /*Firstly diplaying the query and depending on the response updating or not*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Save Changes?")
                .setPositiveButton(R.string.logout_confirmation_yes, (dialog, which) -> {
                    /*Please do the edit database  database stuff here*/

                    if (dbHandler.edit_profile(selected_user)) {
                        /*UPdate is Sucessfull*/
                        Toast.makeText(AdminViewsUserDetailsActivity.this, "Edit Successful", Toast.LENGTH_LONG).show();
                    } else {
                        /*Registration has failed*/
                        Toast.makeText(AdminViewsUserDetailsActivity.this, "Edit Failed", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.logout_confirmation_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @OnClick(R.id.Revoke_renter_btn)
    public void onRevokeRenterBtnClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Revoke this user?")
                .setPositiveButton(R.string.logout_confirmation_yes, (dialog, which) -> {
                    /*Please do the revoke status database stuff here*/
                    selected_user.setStatus(false);
                    if (dbHandler.revoke_renter(selected_user)) {
                        Toast.makeText(AdminViewsUserDetailsActivity.this, "User Status Revoked", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.logout_confirmation_no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}