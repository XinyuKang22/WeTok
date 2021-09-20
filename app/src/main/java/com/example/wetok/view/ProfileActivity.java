package com.example.wetok.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.view.home.PostAdapter;
import com.example.wetok.view.home.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ProfileActivity.this;
        User user = (User) getIntent().getSerializableExtra("user");

        setContentView(R.layout.activity_profile);
        TextView username = findViewById(R.id.profile_username);
        TextView userid = findViewById(R.id.profile_userid);
        ImageView photo = findViewById(R.id.profile_photo);
        username.setText(user.name);
        userid.setText(user.id);
        photo.setImageResource(user.photo);

        ListView lv = findViewById(R.id.profile_post_list);
        ArrayList<Post> posts = new ArrayList<>();
        Post post = new Post();
        post.user = new User();
        post.user.name = "Jack";
        post.content = "Hello\nWorld";
        posts.add(post);
        posts.add(post);
        posts.add(post);
        posts.add(post);
        posts.add(post);


        PostAdapter adapter = new PostAdapter(this, R.layout.post_list_view, posts);
        lv.setAdapter(adapter);

        Button btnSub = findViewById(R.id.profile_subscriber);

        btnSub.setOnClickListener(e -> {
            Intent intent = new Intent(this, UserListActivity.class);
            intent.putExtra("title", "Subscriber");
            startActivity(intent);
        });

        Button btnFol = findViewById(R.id.profile_follower);
        btnFol.setOnClickListener(e -> {
            Intent intent = new Intent(this, UserListActivity.class);
            intent.putExtra("title", "Follower");
            startActivity(intent);
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(user.name);
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
