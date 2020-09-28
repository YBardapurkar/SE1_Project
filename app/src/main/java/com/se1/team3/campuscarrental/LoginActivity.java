package com.se1.team3.campuscarrental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button buttonNoAccount, buttonLogin;

    SQLiteDBHandler dbHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHandler = new SQLiteDBHandler(this);
//        TODO - Yash - check if user logged in using sharedpreferences

        usernameEditText = (EditText)findViewById(R.id.edittext_login_username);
        passwordEditText = (EditText)findViewById(R.id.edittext_login_password);

        buttonNoAccount = (Button) findViewById(R.id.button_no_account);
        buttonLogin = (Button) findViewById(R.id.button_login_submit);

        buttonNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                //datamodel model object
                SystemUser user = new SystemUser();
                user.setUsername(username);
                user.setPassword(password);
                dbHandler.saveUser(user);
//                TODO - Yash - add login functions
            }
        });
    }
}