package com.example.medlink_deliverable2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IAmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iam);


        final Button isPatient = findViewById(R.id.btn_isPatient);
        isPatient.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnPatientButton(v);
            }
        });

        final Button isEmployee = findViewById(R.id.btn_isEmployee);
        isEmployee.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnEmployeeButton(v);
            }
        });
    }

    public void OnPatientButton(View view){
        Intent intent = new Intent(getApplicationContext(), Registration_Patient.class);
        startActivityForResult (intent,0);
    }

    public void OnEmployeeButton(View view){
        Intent intent = new Intent(getApplicationContext(), Registration_Employee.class);
        startActivityForResult (intent,0);
    }
}
