package com.example.medlink_deliverable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medlink_deliverable2.models.Employee;
import com.example.medlink_deliverable2.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManageAccounts extends AppCompatActivity {
    private DatabaseReference db;
    boolean searchIsValid;
    String userKey;
    boolean isPatient;
    boolean isEmployee;
    boolean isValid;
    boolean isClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);

        db = FirebaseDatabase.getInstance().getReference();
        isClicked = false;
        final EditText input_username = findViewById(R.id.input_username);
        final Button btn_Search = findViewById(R.id.btn_Search);

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = input_username.getText().toString().trim();

               isClicked = true;
               isValid = false;

                db.child("Accounts").child("Users").child("Employees").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Employee employee = ds.getValue(Employee.class);
                            if (username.equals(employee.getUsername())) {
                                isValid = true;
                                isEmployee = true;
                                userKey = ds.getKey();
                                Toast.makeText(getApplicationContext(), "The user is found!", Toast.LENGTH_SHORT).show();
                                try {
                                    TimeUnit.MILLISECONDS.sleep(1);
                                }catch(InterruptedException e){
                                    e.getMessage();
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
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                           Patient patient = ds.getValue(Patient.class);
                            if (username.equals(patient.getUsername())) {
                                isValid = true;
                                try {
                                    TimeUnit.MILLISECONDS.sleep(1);
                                }catch(InterruptedException e){
                                    e.getMessage();
                                }
                                userKey = ds.getKey();
                                isPatient = true;
                                Toast.makeText(getApplicationContext(), "The user is found!", Toast.LENGTH_SHORT).show();
                                try {
                                    TimeUnit.MILLISECONDS.sleep(10);
                                }catch(InterruptedException e){
                                    e.getMessage();
                                }
                            }
                        }
                        if(!isValid){
                            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                }

        });

        final Button btn_Delete = findViewById(R.id.btn_Delete);


         btn_Delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                // btn_Search.performClick();
                 boolean isPressed = btn_Search.isPressed();
                 if(isValid){
                     String username = input_username.getText().toString().trim();
                     if (isEmployee) {
                         db.child("Accounts").child("Users").child("Employees").child(userKey).removeValue();
                     }
                     if (isPatient) {
                         db.child("Accounts").child("Users").child("Patients").child(userKey).removeValue();
                     }
                     isValid = false;
                     Toast.makeText(getApplicationContext(), username + " has been removed", Toast.LENGTH_SHORT).show();

             }else{
                     Toast.makeText(getApplicationContext(), "No user found, cannot delete. Search first!", Toast.LENGTH_SHORT).show();
                 }

         }
     });

    }

}


