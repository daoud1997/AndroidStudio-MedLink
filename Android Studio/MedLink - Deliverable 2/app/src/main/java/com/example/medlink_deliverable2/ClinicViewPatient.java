package com.example.medlink_deliverable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medlink_deliverable2.models.Appointment;
import com.example.medlink_deliverable2.models.Category;
import com.example.medlink_deliverable2.models.Employee;
import com.example.medlink_deliverable2.models.Patient;
import com.example.medlink_deliverable2.models.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ClinicViewPatient extends AppCompatActivity {

    Button btn_checkin, btn_bookappt;
    TextView waittime, startHr, endHr, days, rating;
    EditText input_comment;

    private String clinicId;
    private String patientId;
    private String clinicName;
    private DatabaseReference db;
    private String id;

    RatingBar rateclinic;
    Button btn_submitrat, btn_submitcomment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_view_patient);

        btn_checkin = findViewById(R.id.btn_checkin);
        btn_bookappt = findViewById(R.id.btn_bookappt);
        waittime = findViewById(R.id.view_waitTime);
        startHr = findViewById(R.id.view_startH);
        endHr = findViewById(R.id.view_endH);
        days = findViewById(R.id.view_days);
        rating = findViewById(R.id.viewRating);

        rateclinic = findViewById(R.id.rate_clinic);
        btn_submitrat = findViewById(R.id.btn_submitrat);
        input_comment = findViewById(R.id.input_comment);
        btn_submitcomment = findViewById(R.id.btn_submitcomment);


        clinicId = getIntent().getExtras().getString("clinicId");
        patientId = getIntent().getExtras().getString("patientId");

        db = FirebaseDatabase.getInstance().getReference();


        //TODO: I COMMENTED THIS CUZ IT GAVE ERROR
        //Show clinic information
        db.child("Accounts").child("Users").child("Employees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Employee employee = dataSnapshot.child(clinicId).getValue(Employee.class);
                waittime.setText(Integer.toString(employee.getWaitingTime()));
                startHr.setText(employee.getHours().get(0));
                endHr.setText(employee.getHours().get(1));
                Float ratingF = employee.getAvgRating();
                DecimalFormat df = new DecimalFormat("#.00");
                String ratingStr = df.format(ratingF);
                rating.setText(ratingStr);


                String daysOpen = " | ";
                for (int i=0;i<7;i++){
                    ArrayList temp = (ArrayList) employee.getDaysOpen().get(i);
                    if((Boolean)temp.get(1)){
                        daysOpen = daysOpen+((String) temp.get(0))+" | ";
                    }
                }
                days.setText(daysOpen);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_checkin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnCheckInButton(v);
            }
        });

        btn_bookappt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnBookApptButton(v);
            }
        });

        btn_submitrat.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                OnSubmitRatingClicked(v);
            }
        });

        btn_submitcomment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Your feedback is appreciated!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void OnSubmitRatingClicked(View view) {
        final Float rating = rateclinic.getRating();

        db.child("Accounts").child("Users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Employee employee = dataSnapshot.child("Employees").child(clinicId).getValue(Employee.class);

                int amtratings = employee.getAmtratings() + 1;
                Float totalrat = employee.getTotalratings() + rating;

                employee.setTotalratings(totalrat);
                employee.setAmtratings(amtratings);

                employee.setAvgRating(totalrat/amtratings);

                db.child("Accounts").child("Users").child("Employees").child(clinicId).setValue(employee);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(getApplicationContext(), "Your feedback is appreciated!", Toast.LENGTH_SHORT).show();
    }


    public void OnCheckInButton(View view){
        // Nothing with the UI here, just subtract the wait time by 15
        db.child("Accounts").child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patient patient = dataSnapshot.child("Patients").child(patientId).getValue(Patient.class);
                Employee employee = dataSnapshot.child("Employees").child(clinicId).getValue(Employee.class);

                if (employee.getWaitingTime()>0) {
                    employee.setWaitingTime(employee.getWaitingTime() - 15);
                }

                List<Appointment> patientApps = patient.getAppointments();
                List<Appointment> clinicApps = employee.getAppointments();

                int i =0;
                while(i<patientApps.size()){
                    if (patientApps.get(i).getClinicId().equals(clinicId)){
                        patientApps.remove(i);
                    }
                    else {
                        i++;
                    }
                }
                int k =0;
                while(k<clinicApps.size()){
                    if (clinicApps.get(k).getPatientId().equals(patientId)){
                        clinicApps.remove(k);
                    }
                    else {
                        k++;
                    }
                }

                patient.setAppointments(patientApps);
                employee.setAppointments(clinicApps);

                db.child("Accounts").child("Users").child("Employees").child(clinicId).setValue(employee);
                db.child("Accounts").child("Users").child("Patients").child(patientId).setValue(patient);

                Toast.makeText(getApplicationContext(), "Check-in success", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void OnBookApptButton(View view){
        // For the presentation: just have that they add to the wait time
        // For the deliverable, we must have a dialogue that asks for time and what service
        Intent intent = new Intent(getApplicationContext(), ChooseServiceForAppointmentActivity.class);
        intent.putExtra("patientId", patientId);
        intent.putExtra("clinicId", clinicId);
        startActivityForResult(intent, 0);

    }
}
