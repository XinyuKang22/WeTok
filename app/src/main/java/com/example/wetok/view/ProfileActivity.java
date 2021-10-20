package com.example.wetok.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.dao.PostDao;
import com.example.wetok.view.fragment.PostAdapter;

import java.util.List;

/**
 * This is the profileActivity page
 * @author Zhaoting Jiang
 * @author Yuxin Hong
 * @author Xinyue Hu
 * @author Xinyu Kang
 */
public class ProfileActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ProfileActivity.this;

        User user = CurrentUser.current_visitor;
        Toast.makeText(context, "user:" +user.getName(), Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_profile);
        TextView username = findViewById(R.id.profile_username);
        TextView userid = findViewById(R.id.profile_userid);
        ImageView photo = findViewById(R.id.profile_photo);
        username.setText(user.getName());
        userid.setText(user.getId());

        ListView lv = findViewById(R.id.profile_post_list);
        List<Post> posts = user.getPosts();

        // resort post by current time
        int index = PostDao.findInsertIndex(posts);
        List<Post> reposts = posts.subList(index, posts.size());
        reposts.addAll(posts.subList(0,index));

        PostAdapter adapter = new PostAdapter(this, R.layout.post_list_view, reposts);
        lv.setAdapter(adapter);

        Button btnSub = findViewById(R.id.profile_subscribe);
        Boolean isSub = CurrentUser.current_user.isSubscriber(user);
        if (isSub) {
            btnSub.setBackgroundColor(Color.GRAY);
            btnSub.setText("subscribe!");
            btnSub.invalidate();
        } else {
            btnSub.setOnClickListener(e -> {
                CurrentUser.current_user.addSubscribers(user);
                btnSub.setBackgroundColor(Color.GRAY);
                btnSub.setText("subscribe!");
            });
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(user.getName());
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
