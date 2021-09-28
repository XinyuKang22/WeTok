package com.example.wetok.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wetok.R;
import com.example.wetok.bean.User;
import com.example.wetok.resources.InformationResource;
import com.example.wetok.view.home.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = UserListActivity.this;

        setContentView(R.layout.activity_userlist);
        ListView list = findViewById(R.id.user_list);
        List<User> users;
        ArrayList<User> users1 = null;
        //TODO read data from Firebase
        InformationResource info = new InformationResource();
        InputStream input;
        try{
            input = getResources().getAssets().open("infoResource.json");
            info.readFromJson(input);
        }catch (Exception e){
            System.out.println(e);
        }
        users1.addAll(info.getUsers());
        users = users1.subList(0,5);



        UserAdapter adapter = new UserAdapter(this, R.layout.user_list_view, users);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
