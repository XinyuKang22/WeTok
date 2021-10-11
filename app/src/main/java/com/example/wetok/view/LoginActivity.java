package com.example.wetok.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.bean.User;
import com.example.wetok.dao.PostDao;
import com.example.wetok.dao.UserDao;
import com.example.wetok.resources.InformationResource;
import com.example.wetok.view.home.PostAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "EmailPassword";
    private Context context;
    private Button btn_login;
    private Button btn_register;
    private Button btn_guestLogin;
    private EditText et_emailLogin;
    private EditText et_passwordLogin;
    private FirebaseAuth fbAuth;
    public InformationResource info = null;

    public LoginActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = LoginActivity.this;

        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_guestLogin = findViewById(R.id.btn_guestLogin);
        et_emailLogin = findViewById(R.id.et_emailLogin);
        et_passwordLogin = findViewById(R.id.et_passwordLogin);
        fbAuth = FirebaseAuth.getInstance();
        try {
            info = getInformationResource();
            System.out.println("info size: "+info.userlistSize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_emailLogin.getText().toString().trim();
                String password = et_passwordLogin.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(context,"Please enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.isEmpty()){
                    Toast.makeText(context, "Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                signIn(email,password);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterPage();
            }
        });

        btn_guestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainPage();
            }
        });
        // upload data to Firebase

    }



    private void signIn(String email, String password) {
        fbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //TODO: 如果邮箱密码验证正确, 找到User, set CurrentUser(已完成待测试)
                            User u = findUserByEmail(info,email);
//                                    UserDao.findUserByEmail(email); // 改成从数据库读取
                            if (u == null) {
                                Toast.makeText(context, "User not in database."+UserDao.users.size(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                CurrentUser.current_user = null;
                                CurrentUser.login(u);
                                System.out.println("name:"+CurrentUser.current_user.getName());
//                                FirebaseUser user = fbAuth.getCurrentUser();
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(context, "Successfully logged in.",
                                        Toast.LENGTH_SHORT).show();
                                toMainPage();
                            }

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void toMainPage() {     //传递CurrentUser和info
//        setView();
        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("user",CurrentUser.current_user);
//        intent.putExtra("info", info);
        startActivity(intent);
    }

    private void toRegisterPage(){
        Intent intent = new Intent(context, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fbAuth.signOut();
    }

    //TODO: 是否需要在login的时候Set View (待修改..?)
    private void setView() {
        User u = CurrentUser.current_user;
        // main page的信息
        View mainPage = findViewById(R.id.nav_host_fragment_activity_main);
        ListView mainPost = findViewById(R.id.post_list);
        mainPost.setAdapter(new PostAdapter(context, R.id.post_list, info.getPosts()));

        if (u != null) {
            // profile page的信息
            TextView name = findViewById(R.id.profile_username);
            name.setText(u.getName());

            TextView userid = findViewById(R.id.userlist_userid);
            userid.setText(u.getId());

            //TODO: 如果不是default/null, 更新头像
            ImageView photo = findViewById(R.id.profile_photo);
            if (u.getImgloc() == "default" || u.getImgloc() == "null") {
                photo.setImageResource(R.drawable.photo);
            }

            ListView post_list = findViewById(R.id.profile_post_list);
            post_list.setAdapter(new PostAdapter(context, R.id.post_list, PostDao.posts));

            //
        }
        System.out.println("setView success");
    }

    public InformationResource getInformationResource() throws IOException {
        List<User> users = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        List<User> followers = new ArrayList<>();
        List<User> subscribers = new ArrayList<>();
        InputStream file;

        System.out.println("trying to open infoResource.json");
        file = getAssets().open("infoResource.json");
        System.out.println("file size: "+file.toString());
        final Type classType = new TypeToken<List<User>>(){}.getType();
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(file));
            users.addAll(gson.fromJson(reader, classType));
            for(User u: users){
                posts.addAll(u.getPosts());
                subscribers.addAll(u.getSubscribers());
                followers.addAll(u.getFollowers());
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return new InformationResource(users,posts,followers,subscribers);
    }

    public User findUserByEmail(InformationResource info,String email) {
        List<User> users = info.getUsers();
        System.out.println("info.getUsers().size = "+users.size());
        for (User u: users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

}
