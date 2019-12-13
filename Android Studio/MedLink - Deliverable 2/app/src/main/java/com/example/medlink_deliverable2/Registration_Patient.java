package com.example.medlink_deliverable2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.example.medlink_deliverable2.models.Account;
import com.example.medlink_deliverable2.models.Address;
import com.example.medlink_deliverable2.models.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration_Patient extends AppCompatActivity {

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__patient);

        // Creating references to fields

        final EditText input_firstname = findViewById(R.id.input_firstname);
        final EditText input_lastname = findViewById(R.id.input_lastname);
        final EditText input_email = findViewById(R.id.input_email);
        final EditText input_dob = findViewById(R.id.input_dob);
        final EditText input_housenum = findViewById(R.id.input_housenum); // street number
        final EditText input_street = findViewById(R.id.input_street);  //street name
        final EditText input_province = findViewById(R.id.input_province); // province
        final EditText input_postalcode = findViewById(R.id.input_postalcode); //postalcode
        final EditText input_country = findViewById(R.id.input_country); //country
        final EditText input_city = findViewById(R.id.input_city);
        final EditText input_phonenum = findViewById(R.id.input_phonenum);
        final EditText input_username = findViewById(R.id.input_username);
        final EditText input_password = findViewById(R.id.input_password);

        // Buttons
        final RadioGroup checkGender = findViewById(R.id.rg_gender);
        final Switch ifInsurance = findViewById(R.id.btn_ifInsurance);
        final Button createAcc = findViewById(R.id.btn_createaccount);

        createAcc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String firstname = input_firstname.getText().toString();
                String lastname = input_lastname.getText().toString();
                String email = input_email.getText().toString();
                String dob = input_dob.getText().toString();
                //Address Stuff
                final String housenum = input_housenum.getText().toString().trim();
                final String streetName = input_street.getText().toString().trim();
                final String city = input_city.getText().toString().trim();
                final String province = input_province.getText().toString().trim();
                final String postalCode = input_postalcode.getText().toString().trim();
                final String country = input_country.getText().toString().trim();

                String phonenum = input_phonenum.getText().toString();
                String username = input_username.getText().toString();
                boolean isInsured = ifInsurance.isChecked();
                String password = hashPassword(input_password.getText().toString());
                List<String> name = new ArrayList<String>();
                name.add(firstname);
                name.add(lastname);


                int selectedID = checkGender.getCheckedRadioButtonId();
                RadioButton male = checkGender.findViewById(R.id.btn_ifMale);
                RadioButton female = checkGender.findViewById(R.id.btn_ifFemale);
                RadioButton other = checkGender.findViewById(R.id.btn_ifX);
                String gender;

                //Validation Process
                if(selectedID == -1){
                    Toast.makeText(getApplicationContext(), "Please select Gender!", Toast.LENGTH_SHORT).show();
                }


                if(!validateFirstName(firstname)){
                    input_firstname.setError("Invalid first name");
                }

                if(!validateLastName(lastname)){
                    input_lastname.setError("Please input last name");
                }

                if (!validateBirthday(dob)) {
                    input_dob.setError("Please input birthday");
                }

                if (!validateHouseNum(housenum)) {
                    input_housenum.setError("Invalid house number");
                }

                if (!validateStreet(streetName)) {
                    input_street.setError("Invalid street name");
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
                    input_phonenum.setError("Invalid Phone Number, correct format: 1112223333 (10 digits, no dashes)");
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
                        validateHouseNum(housenum)&& validateBirthday(dob)&& validateLastName(lastname)&&
                        validateFirstName(firstname) && validateGender(selectedID)) {

                    if(male.isChecked()){
                        gender = "male";
                    }else if(female.isChecked()){
                        gender = "female";
                    }else if(other.isChecked()){
                        gender = "other";
                    }else{
                        Toast.makeText(getApplicationContext(), "Please select Gender!", Toast.LENGTH_SHORT).show();
                       throw new RuntimeException("GENDER IS NOT INITIALIZED");
                    }

                    int houseNumber = Integer.parseInt(housenum.trim());
                    Address address = new Address(houseNumber, streetName, city, province, postalCode, country);
                    Patient constructPatient = new Patient(username, password, dob, gender, phonenum, name, email, isInsured, address);
                    Account patient = constructPatient.createAccount(username, password, dob, gender, phonenum, name, email, isInsured, address);

                    OnCreateAccountButton(v, patient);
                }
                else{
                    Toast.makeText(getApplicationContext(), "One or more invalid fields", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    //Validation Process

    public boolean validateGender(int selectedGender){
        if(selectedGender == -1){
            Toast.makeText(getApplicationContext(), "Please select Gender!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validateFirstName(String firstname){
        if(TextUtils.isEmpty(firstname))
            return false;
        if(firstname.length() == 0 || !firstname.matches("[a-zA-Z]+")){
            return false;
        }
        return true;
    }

    public boolean validateLastName(String lastname){
        if(TextUtils.isEmpty(lastname))
            return false;
        if(lastname.length() == 0 || !lastname.matches("[a-zA-Z]+")){
            return false;
        }
        return true;
    }

    public boolean validateBirthday(String dob){
        if(TextUtils.isEmpty(dob))
            return false;
       try{
           SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
           formatter1.setLenient(false);
           Date date = formatter1.parse(dob);
           return true;
       }catch(ParseException e){
           return false;
       }

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
        if(street.length() == 0 || !street.matches("[a-zA-Z]+")){
            return false;
        }
        return true;
    }

    public boolean validateCity(String city){
        if(TextUtils.isEmpty(city))
            return false;
        if(city.length() == 0 || !city.matches("[a-zA-Z]+")){
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
        if(country.length() == 0 || !country.matches("[a-zA-Z]+")){
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

    // Called upon validating the fields for the account details
    public void OnCreateAccountButton(View view, Account patient){
        // Validate and then return to log in page to sign in
        db.child("Accounts").child("Users").child("Patients").push().setValue(patient);
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult (intent,0);
    }
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