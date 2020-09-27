package com.se1.team3.campuscarrental;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button buttonNoAccount, buttonLogin;

    DBHandler dbHandler = new SQLiteDBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

                dbHandler.saveUser();
//                TODO - Yash - add login functions
            }
        });
    }
}