package com.example.wetok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText etUser;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btn_login);
        etUser = findViewById(R.id.et_user);
        etPassword = findViewById(R.id.et_password);

        btnLogin.setOnClickListener(this);
    }

    public void onClick(View v) {
        String username = etUser.getText().toString();
        String password = etPassword.getText().toString();
        Intent intent = null;

        if (username.equals("joy") && password.equals("123456")) {
            intent = new Intent(MainActivity.this, FunctionActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }



}