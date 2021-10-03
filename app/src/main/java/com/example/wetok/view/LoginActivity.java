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
import com.example.wetok.bean.User;
import com.example.wetok.resources.InformationResource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
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
                            FirebaseUser user = fbAuth.getCurrentUser();
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(context, "Successfully logged in.",
                                    Toast.LENGTH_SHORT).show();
                            toMainPage(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void toMainPage(FirebaseUser user) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("user",user);
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
}
