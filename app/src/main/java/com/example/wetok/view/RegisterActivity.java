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
    private Button btn_next;
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

        btn_verifyEmail = findViewById(R.id.btn_verifyEmail);
        btn_next = findViewById(R.id.btn_next);
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
                    Toast.makeText(context, "Passwords don't match. Please check your password", Toast.LENGTH_LONG).show();
                    return;
                }
                createAccount(email,password);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(context, "Please verify your email first.",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Log.d(TAG, "signInWithEmail: SUCCESS");
                                toRegisterNextPage(currentUser);
                            }
                        } else {
                            Log.d(TAG, "ReloadUser: FAIL");
                            Toast.makeText(context, "Failed to reload the state of your account.",
                                    Toast.LENGTH_LONG).show();
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
                            Log.d(TAG, "createUserWithEmail: SUCCESS");
                            FirebaseUser user = fbAuth.getCurrentUser();
                            sendEmailVerification(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail: FAIL", task.getException());
                            Toast.makeText(context, "Registration failed, the account might exist. Please try to log in.",
                                    Toast.LENGTH_LONG).show();
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
                            Toast.makeText(context, "Verification email sent. Please verify your email to continue.",
                                    Toast.LENGTH_LONG).show();
                            currentUser = user;
                        }else {
                            user.delete();
                            Log.d(TAG, "Failed to send verification email.");
                            Toast.makeText(context, "Failed to send verification email. Please check your email address.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void toLoginPage(){
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private void toRegisterNextPage(FirebaseUser currentUser) {
        Intent intent = new Intent(context, RegisterNextActivity.class);
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