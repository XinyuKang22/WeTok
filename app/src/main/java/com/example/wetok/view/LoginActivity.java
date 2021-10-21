package com.example.wetok.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.bean.User;
import com.example.wetok.dao.PostDao;
import com.example.wetok.dao.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is the LoginActivity page
 * @author Xinyu Kang
 * @author Yuxin Hong
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private Context context;
    private Button btn_login;
    private Button btn_register;
    private Button btn_guestLogin;
    private EditText et_emailLogin;
    private EditText et_passwordLogin;
    private FirebaseAuth fbAuth;

    public LoginActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CurrentUser.current_user = null;
        super.onCreate(savedInstanceState);
        context = LoginActivity.this;

        try {
            UserDao.users = getUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserDao.users_size = UserDao.users.size();
        for (User u : UserDao.users) {
            UserDao.setFriends(u);
            UserDao.setFollowers(u);
            UserDao.setSubscribers(u);
        }

        PostDao.posts = UserDao.getPosts();
        Collections.sort(PostDao.posts);
        PostDao.post_size = PostDao.posts.size();

        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_guestLogin = findViewById(R.id.btn_guestLogin);
        et_emailLogin = findViewById(R.id.et_emailLogin);
        et_passwordLogin = findViewById(R.id.et_passwordLogin);
        fbAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_emailLogin.getText().toString().trim();
                String password = et_passwordLogin.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(context, "Please enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(context, "Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                signIn(email, password);
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
                CurrentUser.current_user = null;
                toMainPage(null);
            }
        });
    }

    private void signIn(String email, String password) {
        fbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User u = UserDao.findUserByEmail(email);
                            if (u == null) {
                                Toast.makeText(context, "User not in database.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                CurrentUser.login(u);
                                System.out.println("name:" + CurrentUser.current_user.getName());
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(context, "Successfully logged in.",
                                        Toast.LENGTH_SHORT).show();
                                toMainPage(u);
                            }

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void toMainPage(User u) {
        Intent intent = new Intent(context, MainActivity.class);
        if (u == null) {
            intent.putExtra("isGuest", true);
        } else {
            intent.putExtra("isGuest", false);
        }
        startActivity(intent);
    }

    private void toRegisterPage() {
        Intent intent = new Intent(context, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fbAuth.signOut();
        CurrentUser.current_user = null;
    }

    public List<User> getUserData() throws IOException {
        List<User> users = new ArrayList<>();
        InputStream file;

        file = getAssets().open("infoResource.json");
        final Type classType = new TypeToken<List<User>>() {
        }.getType();
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(file));
            users.addAll(gson.fromJson(reader, classType));
        } catch (Exception e) {
            System.out.println(e);
        }
        // add system user
        int i = 0;
        User system = users.get(i);
        system.setName("System");
        system.setEmail("u123456@anu.edu.au");
        system.setPassword("123456");
        List<Post> sys_post = new ArrayList<>();
        List<String> tag = new ArrayList<>();
        tag.add("#WARNING");
        Post p0 = new Post("Illegal Production Exception:\nTry Search: #greeting", "0",
                "System","u1234567@anu.edu.au","default","2000-01-01 00:00:00",
                tag,0,0);
        Post p1 = new Post("Illegal Token Exception:\nYou May Only Use: #tag, &, |", "0",
                "System","u1234567@anu.edu.au","default","2000-01-01 00:00:00",
                tag,0,0);
        Post p2 = new Post("Try To Search With & And |\ne.g. #tag1 & #tag2 " +
                "\ne.g. #tag1 | #tag2 \n#tag1 & (#tag2 | #tag3)", "0",
                "System","u1234567@anu.edu.au","default","2000-01-01 00:00:00",
                tag,0,0);
        sys_post.addAll(Arrays.asList(p0,p1,p2));
        system.setPosts(sys_post);
        i++;

        // change previous data to our group member
        User human = users.get(i);
        human.setEmail("u6684233@anu.edu.au");
        human.setPassword("123456");
        i++;

        human = users.get(i);
        human.setEmail("bikinikang@gmail.com");
        human.setPassword("123456");
        i++;

        human = users.get(i);
        human.setEmail("421686577@qq.com");
        human.setPassword("123456");
        i++;

        human = users.get(i);
        human.setEmail("1044159268@qq.com");
        human.setPassword("123456");

        return users;
    }
}
