package com.example.wetok.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wetok.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RegisterNextActivity extends AppCompatActivity {
    private static final String TAG = "RegisterNext";

    private Context context;
    private Button btn_loginAftReg;
    private RadioGroup btnG_gender;
    private EditText et_userName;
    private EditText et_age;
    private CountryCodePicker ccp;
    private EditText et_phoneNumber;
    private AutocompleteSupportFragment autocompleteFragment;

    private String name;
    // Initialize optional fields
    private int age = 0;
    private String gender= "Not Applicable";
    private String phoneNumber = "";
    private String location = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        context = RegisterNextActivity.this;
        btn_loginAftReg = findViewById(R.id.btn_loginAftReg);
        et_userName = findViewById(R.id.et_userName);
        et_age = findViewById(R.id.et_age);
        btnG_gender = findViewById(R.id.btnG_gender);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        ccp.registerCarrierNumberEditText(et_phoneNumber);
        autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);

        // Input user name
        et_userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = charSequence.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Input age
        et_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int ageInt;
                try{
                    ageInt = Integer.parseInt(charSequence.toString());
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

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Input gender
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

        // Input phone number
        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if (!isValidNumber){
                    Toast.makeText(context,"Please enter valid phone number.", Toast.LENGTH_LONG).show();
                }else {
                    phoneNumber = ccp.getFullNumberWithPlus();
                }
            }
        });

        // Input location
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                location = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        btn_loginAftReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check mandatory field User Name
                if (name.isEmpty()){
                    Toast.makeText(context,"Please enter user name.", Toast.LENGTH_LONG).show();
                    return;
                }
                createNewUser(name,age,gender,phoneNumber,location);
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

    private void createNewUser(String name, int age, String gender, String phoneNumber, String location) {
        Toast.makeText(context,"UserName:"+name+" Gender:"+gender+" Age:"+age, Toast.LENGTH_LONG).show();
    }

}