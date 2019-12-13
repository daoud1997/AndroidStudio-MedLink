package com.example.medlink_deliverable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

import com.example.medlink_deliverable2.models.Employee;
import com.example.medlink_deliverable2.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EditText
        final EditText input_userName = findViewById(R.id.input_username);
        final EditText input_password = findViewById(R.id.input_password);


        final Button signUp = findViewById(R.id.btn_signup);
        signUp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnSignUpButton(v);
            }
        });



        final Button signIn = findViewById(R.id.btn_signin);
        signIn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                //Checking if user clicks sign in without entering any value
                String userName = input_userName.getText().toString();
                String password = input_password.getText().toString();
                if(!IsValidated(userName)){
                    input_userName.setError("Please enter a username!");
                }
                if(!IsValidated(password)){
                    input_password.setError("Please enter a password!");
                }

                OnSignInButton(v,
                        input_userName.getText().toString(),
                        input_password.getText().toString());

            }
        });
    }
    //IsValidated method
    public boolean IsValidated(String input){
        if(TextUtils.isEmpty(input))
            return false;
        return true;
    }

    public void OnSignUpButton(View view){
        Intent intent = new Intent(getApplicationContext(), IAmActivity.class);
        startActivityForResult (intent,0);
    }

    public void OnSignInButton(View view, final String userName, final String userPassword){
        valid=false;

        if((userName.equals("admin")) && (userPassword.equals("5T5ptQ"))){  // Checking if admin account, temporary if we include in database
            Intent intent = new Intent(getApplicationContext(), WelcomePageActivityAdmin.class);
            startActivityForResult (intent,0);
        }else {                                                             // If any other account besides admin
            //TODO: rn if the user logs in and presses back, he can still log in without entering any user name or password. need to change it, make it so that valid isnt always set to true.

            db.child("Accounts").child("Users").child("Employees").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Employee employee = ds.getValue(Employee.class);
                        if (userName.equals(employee.getUsername())){
                            if (hashPassword(userPassword).equals(employee.getPassword())){
                                valid = true;
                                Intent intent = new Intent(getApplicationContext(), WelcomePageActivity.class);
                                intent.putExtra("firstName",employee.getClinicName());
                                intent.putExtra("userType","employee");
                                intent.putExtra("id",ds.getKey());
                                startActivityForResult(intent, 0);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            db.child("Accounts").child("Users").child("Patients").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Patient patient = ds.getValue(Patient.class);
                        if (userName.equals(patient.getUsername())){
                            if (hashPassword(userPassword).equals(patient.getPassword())){
                                valid = true;
                                Intent intent = new Intent(getApplicationContext(), WelcomePageActivity.class);
                                intent.putExtra("firstName",patient.getName().get(0));
                                intent.putExtra("userType","patient");
                                intent.putExtra("id",ds.getKey());
                                startActivityForResult(intent, 0);
                                EditText input_userName = findViewById(R.id.input_username);


                            }
                        }
                    }
                    if(!valid){
                        EditText input_userName = findViewById(R.id.input_username);
                         input_userName.setError("Try again!");
                        EditText input_pass = findViewById(R.id.input_password);
                        input_pass.setError("Try again!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

//            if(valid){
//                Intent intent = new Intent(getApplicationContext(), WelcomePageActivity.class);
//                intent.putExtra(,value);
//                startActivityForResult(intent, 0);
//            }
//            else{
                // TODO: implement incorrect username or password UI compononet, like a red text pop up or smth idk LOL

                // TODO: figure out which credential is wrong and display corresponding error
               // if (!valid) {
                 //   EditText input_userName = findViewById(R.id.input_username);
                   // input_userName.setError("Try again!");
                //}

//            }

        }
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
