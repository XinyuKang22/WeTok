package com.example.wetok.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.dao.PostDao;
import com.example.wetok.dao.UserDao;
import com.example.wetok.resources.InformationResource;
import com.example.wetok.view.home.PostAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> launcher;
    Context context = MainActivity.this;
//    InformationResource info = null;
    User currentUser = null;
    UserDao userDao = null;
    PostDao postDao = null;


    // Mainpage element

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 接收参数
        Boolean isGuest = getIntent().getBooleanExtra("isGuest", true);
        if (!isGuest) {
            currentUser = (User) getIntent().getSerializableExtra("user");
        }
        userDao = (UserDao) getIntent().getSerializableExtra("userDao");
        postDao = (PostDao) getIntent().getSerializableExtra("postDao");

        // TODO: set view
        // 如果是guest只设置main page, 其他两个都不让跳转, Toast显示需要登录


        Toast.makeText(context, "Main pade: size of userDao is" + userDao.users.size(),
                Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);




//        info = getInformationResource();
        System.out.println("userlist size: "+userDao.users.size());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_add, R.id.navigation_my)
                .build();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        ListView mainPost = findViewById(R.id.post_list);
        PostAdapter postAdapter = new PostAdapter(context, R.id.post_list, PostDao.posts);
        System.out.println("postAdapter:"+postAdapter.getCount());
        mainPost.setAdapter(postAdapter);
        System.out.println("setView success");

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                System.out.println("running launcher");
                Intent data = result.getData();
                int resultCode = result.getResultCode();
            }
        });
//
//        User u = CurrentUser.current_user;
//        if (u != null) {
//            setContentView(R.layout.user_list_view);
//            TextView userid = findViewById(R.id.userlist_userid);
//            userid.setText(u.getId());
//            System.out.println("userid: "+userid.getText().toString());
//
//            setContentView(R.layout.activity_profile);
//            // profile page的信息
//            TextView name = findViewById(R.id.profile_username);
//            name.setText(u.getName());
//            System.out.println("name: "+name.getText().toString());
//
//            //TODO: 如果不是default/null, 更新头像
//            ImageView photo = findViewById(R.id.profile_photo);
//            if (u.getImgloc() == "default" || u.getImgloc() == "null") {
//                photo.setImageResource(R.drawable.photo);
//            }
//            ListView post_list = findViewById(R.id.profile_post_list);
//            post_list.setAdapter(new PostAdapter(context, R.id.post_list, u.getPosts()));
//        }
//        System.out.println("setView success");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("running onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView  search = (androidx.appcompat.widget.SearchView )searchItem.getActionView();
        search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView .OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("tag", s);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    //TODO 不理解这个user是啥意思
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("running onOptionsItemSelected");
        if (item.getItemId() == R.id.action_add) {
//            User user = (User) getIntent().getSerializableExtra("user");

            Intent intent = new Intent(this, SendPostActivity.class);
            intent.putExtra("user", CurrentUser.current_user);

            launcher.launch(new Intent(this, SendPostActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("running onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    public InformationResource getInformationResource(){
        List<User> users = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        List<User> followers = new ArrayList<>();
        List<User> subscribers = new ArrayList<>();
        InputStream file;

        System.out.println("trying to open infoResource.json");
        file = null;
        try {
            file = getAssets().open("infoResource.json");
            System.out.println("load info done");
        } catch (IOException e) {
            System.out.println("load info fail");
            e.printStackTrace();
        }
        final Type classType = new TypeToken<List<User>>(){}.getType();
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(file));
            users.addAll(gson.fromJson(reader, classType));
            for(User u: users){
                posts.addAll(u.getPosts());
//                subscribers.addAll(u.getSubscribers());
//                followers.addAll(u.getFollowers());
            }
            System.out.println("turn to users done");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("turn to users fail");
        }
        return new InformationResource(users,posts,followers,subscribers);
    }
}