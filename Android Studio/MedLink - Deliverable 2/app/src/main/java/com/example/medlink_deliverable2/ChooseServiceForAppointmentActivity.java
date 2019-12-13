package com.example.medlink_deliverable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.medlink_deliverable2.models.Appointment;
import com.example.medlink_deliverable2.models.Category;
import com.example.medlink_deliverable2.models.CategoryList;
import com.example.medlink_deliverable2.models.Employee;
import com.example.medlink_deliverable2.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseServiceForAppointmentActivity extends AppCompatActivity {

    private DatabaseReference db;
    ListView servicesLV;
    List<Category> categories = new ArrayList<Category>();
    private String clinicId;
    private String patientId;
    private Employee tempEmployee;
    private ArrayList<Object> serviceNames = new ArrayList<Object>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_service_for_appointment);

        clinicId = getIntent().getExtras().getString("clinicId");
        patientId = getIntent().getExtras().getString("patientId");

        servicesLV = findViewById(R.id.servicesLV);

        servicesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = categories.get(position);
                showBookingDialogue(category.getName(), category.getId());
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categories.clear();
                Employee employee = dataSnapshot.child("Accounts").child("Users").child("Employees").child(clinicId).getValue(Employee.class);
                tempEmployee = employee;
                ArrayList services = (ArrayList) employee.getRates();
                if (services != null) {
                    for (int i = 0; i < services.size(); i++) {
                        ArrayList temp = (ArrayList) services.get(i);
                        String lol = (String) temp.get(0);
                        Category category = dataSnapshot.child("Services").child(lol).getValue(Category.class);
                        categories.add(category);
                    }
                    CategoryList categoriesAdapter = new CategoryList(ChooseServiceForAppointmentActivity.this, categories);
                    servicesLV.setAdapter(categoriesAdapter);
                    categoriesAdapter.notifyDataSetChanged();

                    DataSnapshot ds = dataSnapshot.child("Services");
                    for (DataSnapshot service : ds.getChildren()) {
                        ArrayList<String> idAndService = new ArrayList<String>();

                        Category category = service.getValue(Category.class);
                        idAndService.add(service.getKey());
                        idAndService.add(category.getName());
                        serviceNames.add(idAndService);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showBookingDialogue (final String serviceName, final String serviceId){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_book_appointment, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTime =dialogView.findViewById(R.id.editTextTime);
        final EditText editTextDate =dialogView.findViewById(R.id.editTextDate);
        final Button buttonBook = (Button) dialogView.findViewById(R.id.buttonBook);

        dialogBuilder.setTitle("Book Appointment");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String time = editTextTime.getText().toString().trim();
                final String date = editTextDate.getText().toString().trim();
                if ((!TextUtils.isEmpty(time)) && (!TextUtils.isEmpty(date))){

                    db.child("Accounts").child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Patient patient = dataSnapshot.child("Patients").child(patientId).getValue(Patient.class);
                            Employee employee = dataSnapshot.child("Employees").child(clinicId).getValue(Employee.class);

                            Appointment appointment = new Appointment(patientId,patient.getName().get(0),clinicId,employee.getClinicName(),serviceName,serviceId,time,date);

                            employee.setWaitingTime(employee.getWaitingTime()+15);

                            List<Appointment> clinAppointments = employee.getAppointments();
                            clinAppointments.add(appointment);
                            employee.setAppointments(clinAppointments);

                            List<Appointment> patientAppointments = patient.getAppointments();
                            patientAppointments.add(appointment);
                            patient.setAppointments(patientAppointments);

                            db.child("Accounts").child("Users").child("Patients").child(patientId).setValue(patient);
                            db.child("Accounts").child("Users").child("Employees").child(clinicId).setValue(employee);

                            Toast.makeText(getApplicationContext(), "Booking success", Toast.LENGTH_SHORT).show();

                            b.dismiss();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else if(TextUtils.isEmpty(time) && TextUtils.isEmpty(date)){
                    editTextTime.setError("Cannot be empty!");
                    editTextDate.setError("Cannot be empty!");
                }
                else if(TextUtils.isEmpty(time)){
                    editTextTime.setError("Cannot be empty!");
                }
                else{
                    editTextDate.setError("Cannot be empty!");
                }
            }
        });


    }

}
