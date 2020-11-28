package com.se1.team3.campuscarrental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.se1.team3.campuscarrental.db.DBHandler;
import com.se1.team3.campuscarrental.db.SQLiteDBHandler;
import com.se1.team3.campuscarrental.models.SystemUser;

import java.util.ArrayList;

import butterknife.BindView;

public class SearchUserActivity extends AppCompatActivity{
    EditText editsearch;
    ListView listView;
    TextView noUser;
    SystemUserAdapter adapter;
    DBHandler dbHandler = null;
    ArrayList<SystemUser> arrayOfUsers;
    Button Abutton;
    @BindView(R.id.role_spinner)
    Spinner spinrole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        setTitle(getString(R.string.title_search_user));
        dbHandler = new SQLiteDBHandler(this);

        arrayOfUsers = (ArrayList<SystemUser>) dbHandler.searchUsers("","User");
        adapter = new SystemUserAdapter(this, R.layout.item_user, arrayOfUsers);
        listView = findViewById(R.id.lvAdminUserSearch);
        listView.setAdapter(adapter);

        spinrole = findViewById(R.id.role_spinner);
        editsearch = findViewById(R.id.search);
        noUser = findViewById(R.id.nousermsg);
        Abutton = findViewById(R.id.entr);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            SystemUser selectedUser = arrayOfUsers.get(position);
            Intent intent = new Intent(SearchUserActivity.this, AdminViewsUserDetailsActivity.class);
            intent.putExtra("USERNAME",selectedUser.getUsername());
            startActivity(intent);
        });

        Abutton.setOnClickListener(v -> {
            String name = editsearch.getText().toString();
            String rle = spinrole.getSelectedItem().toString();

            arrayOfUsers.clear();
            arrayOfUsers.addAll(dbHandler.searchUsers(name,rle));
            adapter.setListUsers(arrayOfUsers);
            adapter.notifyDataSetChanged();

            if (arrayOfUsers.size() != 0){
                listView.setVisibility(View.VISIBLE);
                noUser.setVisibility(View.GONE);

            }
            else {
                listView.setVisibility(View.GONE);
                noUser.setVisibility(View.VISIBLE);
            }
        });
    }


}
