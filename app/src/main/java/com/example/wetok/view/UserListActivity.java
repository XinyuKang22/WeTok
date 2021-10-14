package com.example.wetok.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wetok.R;
import com.example.wetok.bean.User;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.view.fragment.UserAdapter;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    //private static String TAG = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = UserListActivity.this;

        setContentView(R.layout.activity_userlist);
        ListView list = findViewById(R.id.user_list);
        //TODO: 不知道是不是要展示订阅者的post
        ArrayList<User> users = new ArrayList<>(CurrentUser.current_user.getSubscribers());

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
