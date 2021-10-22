package com.example.wetok.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.wetok.R;
import com.example.wetok.bean.Post;
import com.example.wetok.bean.User;
import com.example.wetok.dao.CurrentUser;
import com.example.wetok.dao.UserDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.hbb20.CountryCodePicker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the RegisterNextActivity page
 * @author Xinyu Kang
 * @author Yuxin Hong
 */
public class RegisterNextActivity extends AppCompatActivity {
    private static final String TAG = "RegisterNext";
    private static final String FILE_NAME = "location.json";
    private final Context context = this;

    private EditText et_userName;
    private EditText et_age;
    private RadioGroup btnG_gender;
    private CountryCodePicker ccp_code;
    private EditText et_phoneNumber;
    private CountryCodePicker ccp_country;
    private AutoCompleteTextView act_city;
    private Button btn_loginAftReg;

    private String name;
    private int age = 0;
    private String gender= "Not Applicable";
    private String phoneNumber = "";
    private String location = "";
    private ArrayList<String> cityArrayList;
    private ArrayAdapter adapter;
    private String countryCode = "AU";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        et_userName = findViewById(R.id.et_userName);
        et_age = findViewById(R.id.et_age);
        btnG_gender = findViewById(R.id.btnG_gender);
        ccp_code = findViewById(R.id.ccp_code);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        ccp_country = findViewById(R.id.ccp_country);
        act_city =findViewById(R.id.act_city);
        btn_loginAftReg = findViewById(R.id.btn_loginAftReg);

        ccp_code.registerCarrierNumberEditText(et_phoneNumber);
        initArrayAdapter("AU"); // default country australia
        act_city.setAdapter(adapter);


        et_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isAgeForm(charSequence.toString())){
                    age = Integer.parseInt(charSequence.toString());
                }else {
                    Toast.makeText(context,"Please enter correct age.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

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

        ccp_code.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                String phoneNumber_draft = et_phoneNumber.getText().toString();
                if (!phoneNumber_draft.isEmpty()){
                    if (!isValidNumber){
                        Toast.makeText(context,"Please enter valid phone number.", Toast.LENGTH_LONG).show();
                    }else {
                        phoneNumber = ccp_code.getFullNumberWithPlus();
                    }
                }
            }
        });

        ccp_country.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp_country.getSelectedCountryNameCode();
                updateArrayAdapter(countryCode);
                act_city.getText().clear();
            }
        });

        act_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                location = act_city.getText().toString();
            }
        });


        btn_loginAftReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check User Name (mandatory field):
                String name_draft = et_userName.getText().toString().trim();
                if (name_draft.isEmpty()){
                    Toast.makeText(context,"Please create an user name to continue.", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    name = name_draft;
                }

                // Check Age (optional field):
                if (age != 0){
                    if (!isAgeForm(et_age.getText().toString())){
                        Toast.makeText(context,"Please enter correct age.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                // Check phone number (optional field):
                if (!phoneNumber.isEmpty()){
                    if (!ccp_code.isValidFullNumber()){
                        Toast.makeText(context,"Please enter valid phone number.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                // Check location (optional field):
                if (!location.isEmpty()){
                    // If the location is not selected from auto-filler, label it as "Other: XXX"
                    if (!cityArrayList.contains(location)){
                        location = "Other: "+location;
                    }
                }

                createNewUser(name,age,gender,phoneNumber,location);

                FirebaseAuth fbAuth = FirebaseAuth.getInstance();
                FirebaseUser user = fbAuth.getCurrentUser();
                toMainPage(user);
            }
        });
    }

    private void toMainPage(FirebaseUser user) {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    private void createNewUser(String name, int age, String gender, String phoneNumber, String location) {
        // update currentUser by registerNextActivity info
        UserDao.users_size += 1;
        CurrentUser.current_user.setId(""+UserDao.users_size);
        CurrentUser.current_user.setName(name);
        CurrentUser.current_user.setGender(gender);
        CurrentUser.current_user.setAge(age);

        List<User> empty_user = new ArrayList<>();
        CurrentUser.current_user.setFollowers(empty_user);
        CurrentUser.current_user.setSubscribers(empty_user);
        CurrentUser.current_user.setFriends(empty_user);

        List<Post> empty_post = new ArrayList<>();
        CurrentUser.current_user.setPosts(empty_post);

        CurrentUser.current_user.setAddress(location);
        CurrentUser.current_user.setPhone(phoneNumber);
        CurrentUser.current_user.setImgloc("1");

        //update UserDao
        UserDao.addUser(CurrentUser.current_user);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(CurrentUser.current_user.getName())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open(FILE_NAME);
            Log.d(TAG, "Open "+FILE_NAME+": SUCCESS");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.d(TAG, "Open "+FILE_NAME+": FAIL");
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<String> getCityList(String countryCode){
        ArrayList<String> cities = new ArrayList<String>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray j_cities = obj.getJSONArray(countryCode);
            Log.d(TAG, "Read "+FILE_NAME+": "+j_cities.length()+" cities from "+countryCode);
            for (int i = 0; i < j_cities.length(); i++){
                cities.add(j_cities.getString(i));
            }
            Log.d(TAG,"Generate city list of "+countryCode+": SUCCESS");
        } catch (JSONException e) {
            Log.d(TAG,"Generate city list of "+countryCode+": FAIL");
            e.printStackTrace();
        }
        return cities;
    }

    private void initArrayAdapter(String countryCode){
        cityArrayList = getCityList(countryCode);
        adapter = new ArrayAdapter(context,android.R.layout.simple_dropdown_item_1line,cityArrayList);
    }

    private void updateArrayAdapter(String countryCode){
        try {
            cityArrayList = getCityList(countryCode);
            adapter.clear();
            adapter.addAll(cityArrayList);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isAgeForm(String ageInput){
        int ageInt;
        try{
            ageInt = Integer.parseInt(ageInput);
        }
        catch (NumberFormatException ex){
            return false;
        }
        return ageInt >= 0 && ageInt <= 120;
    }
}