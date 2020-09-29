package com.se1.team3.campuscarrental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button buttonNoAccount, buttonLogin;

    DBHandler dbHandler = null;
    SharedPreferences sharedPreferences;

    static final String PREFERENCES = "SharedPreferences";
    static final String USERNAME = "username"; //shubham123
    static final String ROLE = "role"; //shubham123

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.title_login);

        dbHandler = new SQLiteDBHandler(this);

        sharedPreferences =  getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        usernameEditText = (EditText)findViewById(R.id.edittext_login_username);
        passwordEditText = (EditText)findViewById(R.id.edittext_login_password);

        buttonNoAccount = (Button) findViewById(R.id.button_no_account);
        buttonLogin = (Button) findViewById(R.id.button_login_submit);

        String savedUsername = sharedPreferences.getString(USERNAME, null);
        String savedRole = sharedPreferences.getString(ROLE, null);

        if (savedUsername != null && savedRole != null) {
            afterLogin(savedUsername, savedRole);
        }

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

                SystemUser user = dbHandler.getUser(username, password);

                if (user == null) {
                    Toast.makeText(LoginActivity.this, "Incorrect Username/Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                sharedPreferences.edit().putString(USERNAME, user.getUsername()).commit();
                sharedPreferences.edit().putString(ROLE, user.getRole()).commit();

                afterLogin(user.getUsername(), user.getRole());
            }
        });
    }

    private void afterLogin(String username, String role) {
        if (getResources().getString(R.string.role_user).equals(role)) {
            Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
            startActivity(intent);
            finish();
        } else if (getResources().getString(R.string.role_rental_manager).equals(role)) {
            Intent intent = new Intent(LoginActivity.this, RentalManagerHomeActivity.class);
            startActivity(intent);
            finish();
        } else if (getResources().getString(R.string.role_admin).equals(role)) {
            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}