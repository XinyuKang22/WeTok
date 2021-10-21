package com.example.wetok.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wetok.R;
import com.example.wetok.bean.User;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.view.fragment.UserAdapter;

import java.util.ArrayList;

/**
 * This is the UserListActivity page
 * @author Zhaoting Jiang
 * @author Yuxin Hong
 */
public class UserListActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = UserListActivity.this;
        String type = getIntent().getStringExtra("title");
        ArrayList<User> users = new ArrayList<>();
        if (type.equals("Subscriber")) {
            users.addAll(CurrentUser.current_user.getSubscribers());
        } else if (type.equals("Follower")) {
            users.addAll(CurrentUser.current_user.getFollowers());
        }

        setContentView(R.layout.activity_userlist);
        ListView list = findViewById(R.id.user_list);
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
                CurrentUser.current_visitor = user;
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
