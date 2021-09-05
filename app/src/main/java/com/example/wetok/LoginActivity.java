package com.example.wetok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText etUser;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        etUser = findViewById(R.id.et_user);
        etPassword = findViewById(R.id.et_password);

        btnLogin.setOnClickListener(this);

    }

    private void userLogin(){
        String username = etUser.getText().toString();
        String password = etPassword.getText().toString();
        Intent intent = null;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (!username.contains("@")){
            Toast.makeText(this,"Please enter a valid email", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.isEmpty()){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, FunctionActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
    public void onClick(View v) {
        if (v == btnLogin) userLogin();
    }

}
