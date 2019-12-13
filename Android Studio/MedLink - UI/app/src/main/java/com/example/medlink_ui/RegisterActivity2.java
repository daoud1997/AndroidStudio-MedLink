package com.example.medlink_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity2 extends AppCompatActivity {

    private Button Next, Employee, Patient;
    private EditText Gender, DOB, PhoneNumber;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);

        // Getting information from the previous RegisterActivity
        Intent intent = getIntent();
        final String username_str = intent.getStringExtra("Username");
        final String password_str = intent.getStringExtra("Password");
        final String fullname_str = intent.getStringExtra("FullName");
        final String email_str = intent.getStringExtra("Email");

        Gender = (EditText) findViewById(R.id.input_gender);
        DOB = (EditText) findViewById(R.id.input_dob);
        PhoneNumber = (EditText) findViewById(R.id.input_pnumber);
        final String gender_str = Gender.getText().toString();
        final String dob_str = DOB.getText().toString();
        final String phoneNum_str = PhoneNumber.getText().toString();
        final int phoneNum = Integer.parseInt(phoneNum_str);


        // Sign Up Activity switch
        Next = (Button) findViewById(R.id.btn_next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity2.this, LoggedInActivity.class);
                RegisterActivity2.this.startActivity(intent);
            }
        });

        // onClick of the Employee
        Employee = (Button) findViewById(R.id.btn_employee);
        Employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String role = "Employee";
                Account employee = new Employee(username_str, password_str, dob_str, gender_str, phoneNum, fullname_str, email_str, role);
                mDatabase.child("accounts").child("users").child("employees").setValue(employee);
            }
        });


        // onClick of the Patient
        Patient = (Button) findViewById(R.id.btn_patient);
        Patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account patient = new Patient(username_str, password_str, dob_str, gender_str, phoneNum, fullname_str, email_str);
                mDatabase.child("accounts").child("users").child("patients").setValue(patient);
            }
        });
    }
}
