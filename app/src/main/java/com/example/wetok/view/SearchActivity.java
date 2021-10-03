package com.example.wetok.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.resources.InformationResource;
import com.example.wetok.view.home.PostAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SearchActivity.this;
        String tag = getIntent().getStringExtra("tag");

        setContentView(R.layout.activity_search);

        ListView lv = findViewById(R.id.profile_post_list);
        //TODO search for posts that have the tag on it Hint:post.getTag()
        // this code need to be modified!
        List<Post> li = new ArrayList<>();
        List<Post> posts;
        InformationResource info = new InformationResource();
        InputStream input;
        try{
            input = getResources().getAssets().open("src/main/java/com/example/wetok/resources/infoResource.json");
            info.readFromJson(input);
        }catch (Exception e){
            System.out.println(e);
        }
        li.addAll(info.getPosts());
        posts = li.subList(0,5);



        PostAdapter adapter = new PostAdapter(this, R.layout.post_list_view, posts);
        lv.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(tag);
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
