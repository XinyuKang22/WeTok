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
import com.example.wetok.view.home.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = UserListActivity.this;

        setContentView(R.layout.activity_userlist);
        ListView list = findViewById(R.id.user_list);
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("ABC", "dfdsf"));
        users.add(new User("DDD", "123343"));
        users.add(new User("FDFDFDF", "fdsfds"));
        users.add(new User("DFDFDFDFDF", "123343"));
        users.add(new User("RRRRRRRR", "fdsfds"));
        users.add(new User("ABdDDFdfdC", "gfdgfdgfd"));
        users.add(new User("AffdsfdBC", "123hffdgfd343"));

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
