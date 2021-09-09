package com.example.wetok.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.wetok.R;
import androidx.annotation.NonNull;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity{
    private static final String TAG = "Register";
    private Context context;
    private Button btn_verifyEmail;
    private Button btn_loginReg;
    private Button btn_back;
    private EditText et_emailReg;
    private EditText et_passwordReg;
    private EditText et_rePassword;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = RegisterActivity.this;

        setContentView(R.layout.activity_register);
        btn_verifyEmail = findViewById(R.id.btn_verifyEmail);
        btn_loginReg = findViewById(R.id.btn_loginReg);
        btn_back = findViewById(R.id.btn_back);
        et_emailReg = findViewById(R.id.et_emailReg);
        et_passwordReg = findViewById(R.id.et_passwordReg);
        et_rePassword = findViewById(R.id.et_rePassword);

        btn_verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_emailReg.getText().toString().trim();
                String password = et_passwordReg.getText().toString().trim();
                String rePassword = et_rePassword.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(context,"Please enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.isEmpty() || rePassword.isEmpty()){
                    Toast.makeText(context, "Please enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!rePassword.equals(password)){
                    Toast.makeText(context, "Please check your password", Toast.LENGTH_LONG).show();
                    return;
                }
                createAccount(email,password);
            }
        });

        btn_loginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null){
                    Toast.makeText(context, "Please create an account first.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                currentUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            if(!currentUser.isEmailVerified()){
                                Toast.makeText(context, "Please verify your email.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(context, "Successfully logged in.",
                                        Toast.LENGTH_SHORT).show();
                                toMainPage(currentUser);
                            }
                        } else {
                            Log.d(TAG, "ReloadUser:fail");
                            Toast.makeText(context, "Failed to reload the state of your account",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLoginPage();
            }
        });
    }

    private void createAccount(String email, String password) {
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = fbAuth.getCurrentUser();
                            sendEmailVerification(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(context, "Email has been sent, please verify your account.",
                                    Toast.LENGTH_SHORT).show();
                            currentUser = user;
                        }else {
                            user.delete();
                            Log.d(TAG, "Failed to send email.");
                            Toast.makeText(context, "Failed to send email, please check your email address.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void toLoginPage(){
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private void toMainPage(FirebaseUser user) {
        Intent intent = new Intent(context, MainPageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fbAuth.getCurrentUser();
        if (user != null && !user.isEmailVerified()){
            user.delete();
        }
    }
}