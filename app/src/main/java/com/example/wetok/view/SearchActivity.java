package com.example.wetok.view;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.dao.PostDao;
import com.example.wetok.searchTree.Search;
import com.example.wetok.view.fragment.PostAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SearchActivity.this;
        //TODO the parameter needs to be modified
        String tag = getIntent().getStringExtra("tag");

        setContentView(R.layout.activity_search);

        ListView lv = findViewById(R.id.profile_post_list);

        ArrayList<Post> posts = new ArrayList<>(PostDao.posts);

        //TODO the method not complete yet
        Search search = new Search();
        search.buildIndexTrees(posts);
        List<Post> result = (List<Post>)search.search(tag);

        PostAdapter adapter = new PostAdapter(this, R.layout.post_list_view, result);
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
