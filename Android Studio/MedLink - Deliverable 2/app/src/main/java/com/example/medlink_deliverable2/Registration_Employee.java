package com.example.medlink_deliverable2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.medlink_deliverable2.models.Account;
import com.example.medlink_deliverable2.models.Address;
import com.example.medlink_deliverable2.models.Employee;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


public class Registration_Employee extends AppCompatActivity {

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__employee);

        // EditTexts
        final EditText input_clinicname = findViewById(R.id.input_clinicname);
        final EditText input_email = findViewById(R.id.input_email);
        final EditText input_housenum = findViewById(R.id.input_housenum); // street number
        final EditText input_street = findViewById(R.id.input_street);  //street name
        final EditText input_province = findViewById(R.id.input_province); // province
        final EditText input_postalcode = findViewById(R.id.input_postalcode); //postalcode
        final EditText input_country = findViewById(R.id.input_country); //country
        final EditText input_city = findViewById(R.id.input_city);
        final EditText input_phonenum = findViewById(R.id.input_phonenum);
        final EditText input_username = findViewById(R.id.input_username);
        final EditText input_password = findViewById(R.id.input_password);

        //TODO: Add the insurance methods accepted

        // Buttons
        //TODO: note that I've changed the createaccount btn to btn_toRE2 instead
        final Button toReg2 = findViewById(R.id.btn_toreg2);


        toReg2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                //TODO: Turn first name and last name into Clinic Name ONLY
                final String clinicName = input_clinicname.getText().toString().trim();
                final String email = input_email.getText().toString().trim();
               //Address Stuff
                final String housenum = input_housenum.getText().toString().trim();
                final String streetName = input_street.getText().toString().trim();
                final String city = input_city.getText().toString().trim();
                final String province = input_province.getText().toString().trim();
                final String postalCode = input_postalcode.getText().toString().trim();
                final String country = input_country.getText().toString().trim();

                final String phonenum = input_phonenum.getText().toString().trim();

                final String username = input_username.getText().toString().trim();

                String password = hashPassword(input_password.getText().toString().trim());


                if(!validateName(clinicName)){
                    input_clinicname.setError("Invalid clinic name");
                }

                if (!validateHouseNum(housenum)) {
                    input_housenum.setError("Invalid house number");
                }

                if (!validateStreet(streetName)) {
                    input_street.setError("Invalid street");
                }

                if (!validateCity(city)) {
                    input_city.setError("Invalid city");
                }

                if (!validateProvince(province)) {
                    input_province.setError("Invalid province");
                }

                if (!validatePostalCode(postalCode)) {
                    input_postalcode.setError("Invalid postal code, correct format: A1A1A1");
                }

                if (!validateCountry(country)) {
                    input_country.setError("Invalid country");
                }

                if (!validatePhoneNum(phonenum)) {
                    input_phonenum.setError("Invalid phone #, correct format: 1112223333");
                }

                if (!validateEmail(email)) {
                    input_email.setError("Invalid email");
                }

                if (!validateUsername(username)) {
                    input_username.setError("Invalid username");
                }

                if (!validatePassword(password)) {
                    input_password.setError("Invalid password");
                }


                if(validatePassword(password)&& validateUsername(username) && validateEmail(email)&&
                        validatePhoneNum(phonenum)&& validateCountry(country)&& validatePostalCode(postalCode)&&
                        validateProvince(province)&& validateCity(city)&& validateStreet(streetName)&&
                        validateHouseNum(housenum)&& validateName(clinicName)) {


                    int houseNumber = Integer.parseInt(housenum.trim());


                    Address address = new Address(houseNumber, streetName, city, province, postalCode, country);

                    Employee construct = new Employee(username, password, phonenum,
                            clinicName, email,address, null,null, null,
                            null, null);

                    Account employee = construct.createAccount(username, password, phonenum,
                            clinicName, email, address, null,null,null,
                            null,null);

                    OnNextButton(v, employee);

                }
                else{
                    Toast.makeText(getApplicationContext(), "One or more invalid fields", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    // Called upon validating the fields for the account details
    public void OnNextButton(View view, Account employee){

        // This is where we'd take all the field inputs and put it into db
        String id = db.child("Accounts").child("Users").child("Employees").push().getKey(); // Adds new employee to Firebase
        db.child("Accounts").child("Users").child("Employees").child(id).setValue(employee);
        // Starts new activity where the user is asked to sign in once again
        Intent intent = new Intent(this, Registration_Employee2.class);
        intent.putExtra("id", id);
        startActivityForResult (intent,0);
    }

    //Validating Process

    public boolean validateName(String name){
        if(TextUtils.isEmpty(name))
            return false;
        if(name.length() == 0 || !name.matches("[a-zA-Z ]+")){
            return false;
        }
        return true;
    }

    public boolean validateHouseNum(String housenum){
        if(TextUtils.isEmpty(housenum))
            return false;
        if(housenum.length() == 0|| !housenum.matches("[0-9]+")){
            return false;
        }
        return true;
    }

    public boolean validateStreet(String street){
        if(TextUtils.isEmpty(street))
            return false;
        if(street.length() == 0 || !street.matches("[a-zA-Z ]+")){
            return false;
        }
        return true;
    }

    public boolean validateCity(String city){
        if(TextUtils.isEmpty(city))
            return false;
        if(city.length() == 0 || !city.matches("[a-zA-Z ]+")){
            return false;
        }
        return true;
    }

    public boolean validateProvince(String province){
        if(TextUtils.isEmpty(province))
            return false;
        if(province.length() == 0 || !province.matches("[a-zA-Z]+")){
            return false;
        }
        return true;
    }

    public boolean validatePostalCode(String postalcode){
        if(TextUtils.isEmpty(postalcode))
            return false;
        String POSTALCODE_PATTERN = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";

        Pattern pattern = Pattern.compile(POSTALCODE_PATTERN);
        Matcher matcher = pattern.matcher(postalcode);
        return matcher.matches();
    }

    public boolean validateCountry(String country){
        if(TextUtils.isEmpty(country))
            return false;
        if(country.length() == 0 || !country.matches("[a-zA-Z ]+")){
            return false;
        }
        return true;
    }

    public boolean validatePhoneNum(String phonenum){
        if(TextUtils.isEmpty(phonenum))
            return false;
        if(phonenum.length() != 10) {
            return false;
        }
        return true;
    }

    public boolean validateEmail(String email){
        if(TextUtils.isEmpty(email))
            return false;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validateUsername(String username) {
        if(TextUtils.isEmpty(username))
            return false;
        if (username.length() == 0) {
            return false;
        }
        return true;
    }

    public boolean validatePassword(String password) {
        if(TextUtils.isEmpty(password))
            return false;
        if (password.length() == 0) {
            return false;
        }
        return true;
    }

    //Hashing the password
    private String hashPassword(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
