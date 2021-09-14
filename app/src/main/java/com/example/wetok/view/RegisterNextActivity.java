package com.example.wetok.view;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wetok.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class RegisterNextActivity extends AppCompatActivity {

    private Context context;
    private Button btn_loginAftReg;
    private RadioGroup btnG_gender;
    private EditText et_userName;
    private EditText et_age;
    private String gender= "Not Applicable.";
    private int age;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        context = RegisterNextActivity.this;
        btn_loginAftReg = findViewById(R.id.btn_loginAftReg);
        et_userName = findViewById(R.id.et_userName);
        et_age = findViewById(R.id.et_age);
        btnG_gender = findViewById(R.id.btnG_gender);

        btnG_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.btn_genderFemale){
                    gender = "Female";
                }else if (i == R.id.btn_genderMale){
                    gender = "Male";
                }
            }
        });

        btn_loginAftReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = et_userName.getText().toString().trim();
                String ageStr = et_age.getText().toString();
                if (nameStr.isEmpty()){
                    Toast.makeText(context,"Please enter user name.", Toast.LENGTH_LONG).show();
                    return;
                }
                name = nameStr;
                if (!ageStr.isEmpty()){
                    int ageInt;
                    try{
                        ageInt = Integer.parseInt(ageStr);
                    }
                    catch (NumberFormatException ex){
                        Toast.makeText(context,"Please enter the age in right form.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (ageInt<0 || ageInt>120){
                        Toast.makeText(context,"Please enter the right age.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    age = ageInt;
                }
                createNewUser(name,gender,age);
                FirebaseAuth fbAuth = FirebaseAuth.getInstance();
                FirebaseUser user = fbAuth.getCurrentUser();
                toMainPage(user);
            }
        });
    }

    private void toMainPage(FirebaseUser user) {
        Intent intent = new Intent(context, MainPageActivity.class);
        startActivity(intent);
    }

    private void createNewUser(String name, String gender, int age) {
        Toast.makeText(context,"UserName:"+name+" Gender:"+gender+" Age:"+age, Toast.LENGTH_LONG).show();
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }

    }
}