package com.se1.team3.campuscarrental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.signup_username)
    EditText signupUsername;
    @BindView(R.id.signup_Password)
    EditText signupPassword;
    @BindView(R.id.signu_utaid)
    EditText signuUtaid;
    @BindView(R.id.signup_firstname)
    EditText signupFirstname;
    @BindView(R.id.signup_lastname)
    EditText signupLastname;
    @BindView(R.id.sigunup_phoneno)
    EditText sigunupPhoneno;
    @BindView(R.id.signup_emailid)
    EditText signupEmailid;
    @BindView(R.id.signup_address)
    EditText signupAddress;
    @BindView(R.id.signup_city)
    EditText signupCity;
    @BindView(R.id.signup_state)
    EditText signupState;
    @BindView(R.id.membership_button)
    ToggleButton membershipButton;
    @BindView(R.id.role_spinner)
    Spinner roleSpinner;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.signup_parent_linear)
    LinearLayout signupParentLinear;
    @BindView(R.id.signup_pincode)
    EditText signupPincode;
    /*variables for value*/
    DBHandler dbHandler;
    SystemUser new_user;
    @BindView(R.id.membership_LL)
    LinearLayout membershipLL;

//    todo - Shubham Phape

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle(R.string.title_signup);

        ButterKnife.bind(this);

        dbHandler = new SQLiteDBHandler(this);

        //populating the drop down for role
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.txt_option_role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choice = (String) parent.getItemAtPosition(position);
                if (choice.equalsIgnoreCase("user")) {
                    membershipLL.setVisibility(View.VISIBLE);
                } else {
                    membershipLL.setVisibility(View.GONE);
                    membershipButton.setChecked(false);
                }
                //Toast.makeText(SignUpActivity.this, choice, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        /*making object of model*/
        new_user = new SystemUser();
        new_user.setUsername(signupUsername.getText().toString());
        new_user.setPassword(signupPassword.getText().toString());
        new_user.setFirstName(signuUtaid.getText().toString());
        new_user.setLastName(signupFirstname.getText().toString());
        new_user.setUtaId(signupLastname.getText().toString());
        new_user.setRole(roleSpinner.getSelectedItem().toString());
        new_user.setPhone(sigunupPhoneno.getText().toString());
        new_user.setEmail(signupEmailid.getText().toString());
        new_user.setStreet(signupAddress.getText().toString());
        new_user.setCity(signupCity.getText().toString());
        new_user.setMembership(membershipButton.getText().toString());
        new_user.setState(signupState.getText().toString());
        new_user.setPin(signupPincode.getText().toString());

        //calling the inert query function
        if (dbHandler.saveUser(new_user)) {
            /*REgistration is Sucessfull*/
            Toast.makeText(SignUpActivity.this, "Succesfully Registered.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            /*Registration has failed*/
            Toast.makeText(SignUpActivity.this, "Failed Registration!!!.", Toast.LENGTH_LONG).show();
            clearForm((ViewGroup) findViewById(R.id.signup_parent_linear));
        }

    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}