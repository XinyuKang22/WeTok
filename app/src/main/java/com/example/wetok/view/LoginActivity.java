package com.example.wetok.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

        try {
            info = getInformationResource();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserDao.users = info.getUsers();
        PostDao.posts = UserDao.getPosts();

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
                toMainPage(null);
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
                            User u = UserDao.findUserByEmail(email);
                            if (u == null) {
                                Toast.makeText(context, "User not in database.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                CurrentUser.login(u);
                                System.out.println("name:"+CurrentUser.current_user.getName());
//                                FirebaseUser user = fbAuth.getCurrentUser();
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

    private void toMainPage(User u) {     //传递CurrentUser和info
//        setView(u);
        Intent intent = new Intent(context, MainActivity.class);
        if (u == null){
            intent.putExtra("isGuest",true);
        } else {
            intent.putExtra("isGuest",false);
        }
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

    public InformationResource getInformationResource() throws IOException {
        List<User> users = new ArrayList<>();
        InputStream file;

        System.out.println("trying to open infoResource.json");
        file = getAssets().open("infoResource.json");
        System.out.println("file size: "+file.toString());
        final Type classType = new TypeToken<List<User>>(){}.getType();
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(file));
            users.addAll(gson.fromJson(reader, classType));
        }catch (Exception e){
            System.out.println(e);
        }

        // 把数据可里前几个数据改成真实邮箱密码
        int i = 0;
        User human = users.get(i);
        human.setEmail("joyhongyuxin@gmail.com");
        human.setPassword("123456");
        i++;

        human = users.get(i);
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
        return new InformationResource(users);

    }

}
