package com.example.wetok.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.dao.PostDao;
import com.example.wetok.parserAndTokenizer.Exp;
import com.example.wetok.parserAndTokenizer.Parser;
import com.example.wetok.parserAndTokenizer.Tokenizer;
import com.example.wetok.ranking.Rank;
import com.example.wetok.searchTree.Search;
import com.example.wetok.view.fragment.PostAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the SearchActivity page
 * @author Zhaoting Jiang
 * @author Xinyue Hu
 * @author Xinyu Kang
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private Context context;
    private Tokenizer Tokenizer;
    private Exp condition; // store search condition
    public Search s;
    public static List<Post> posts; // store posts
    public static List<String> tags;// store all post tags
    public String tag; // store search information
    public List<Post> plist; // store the searching results.

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SearchActivity.this;
        tag = getIntent().getStringExtra("tag");

        setContentView(R.layout.activity_search);

        ListView lv = findViewById(R.id.profile_post_list);

        posts = new ArrayList<>(PostDao.posts);
        tags = PostDao.getTagList(posts);

        s = new Search();
        s.buildIndexTrees(posts);

        // searching in different conditions.
        if (tag.length() != 0) {
            searchConditions();
        } else {
            Toast.makeText(SearchActivity.this, "No tag!", Toast.LENGTH_LONG).show();
        }

        PostAdapter adapter = new PostAdapter(this, R.layout.post_list_view, plist);
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

    /**
     * Integration method to get the input, parse it and search it.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void searchConditions() {

        //plist.clear(); // clear last results.
        Tokenizer = new Tokenizer(tag.toLowerCase());
        condition = new Parser(Tokenizer).parseExp();
        List<Post> retrievedPosts = condition.evaluate(s);

        //rank the posts according to ranking algorithm
        String[] tag_list = tag.toLowerCase().split("[#& _|()]+");
        List<String> query = new ArrayList<>();
        for (String s : tag_list) {
            s = s.trim();
            if (s.length() > 0) {
                query.add(s);
            }
        }
        if (retrievedPosts.size() != 0 && query.size() != 0) {
            Rank rank = new Rank(CurrentUser.current_user, query, retrievedPosts);
            plist = rank.rankedPosts;
        } else {
            plist = new ArrayList<>();
            Toast.makeText(context, "Tag not exist!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
