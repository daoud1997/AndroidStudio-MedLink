package com.example.medlink_deliverable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

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

public class PatientAccountActivity extends AppCompatActivity {

    private String id;
    ListView listViewMyAppts;
    private DatabaseReference db;
    List<String> appointments;
    List<String> clinicIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account);

        db =FirebaseDatabase.getInstance().getReference();
        id = getIntent().getExtras().getString("id");
        appointments = new ArrayList<String>();
        clinicIds = new ArrayList<String>();

        listViewMyAppts = findViewById(R.id.listviewMyAppts);

        //display appointments
        db.child("Accounts").child("Users").child("Patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clinicIds.clear();
                Patient patient = dataSnapshot.child(id).getValue(Patient.class);
                List<Appointment> apps = patient.getAppointments();
                for(int i = 0; i<apps.size();i++){
                    Appointment app = apps.get(i);
                    appointments.add(app.getServiceName()+" - "+app.getDate()+", "+app.getReservationSlots()+" - "+app.getClinicName());
                    clinicIds.add(app.getClinicId());
                }
                populate(appointments);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Button toSearchClinic = findViewById(R.id.btn_searchClinic);
        toSearchClinic.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), SearchForClinicsActivity.class);
                intent.putExtra("id",id);
                startActivityForResult (intent,0);
            }
        });

        listViewMyAppts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long longId) {
                Intent intent = new Intent(getApplicationContext(), ClinicViewPatient.class);
                intent.putExtra("patientId", id);
                intent.putExtra("clinicId", clinicIds.get(position));
                startActivityForResult(intent, 0);
            }
        });


    }


    public void populate(List<String> userkeyz){
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userkeyz.toArray(new String[userkeyz.size()]));

        listViewMyAppts.setAdapter(itemsAdapter);
    }





    //Populating the listviews with already existing appointments (ILL IMPLEMENT IT IN toString Method this Time)
    /*@Override
    protected void onStart(){
        super.onStart();
        db= FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categories.clear();
                Employee employee = dataSnapshot.child("Accounts").child("Users").child("Employees").child(id).getValue(Employee.class);
                tempEmployee = employee;
                ArrayList services = (ArrayList) employee.getRates();
                if(services != null){
                    for (int i = 0;i<services.size();i++){
                        ArrayList temp = (ArrayList) services.get(i);
                        String lol = (String) temp.get(0);
                        Category category = dataSnapshot.child("Services").child(lol).getValue(Category.class);
                        categories.add(category);
                    }
                    CategoryList categoriesAdapter = new CategoryList(ManageServicesClinic.this,categories);
                    listViewClinicServices.setAdapter(categoriesAdapter);
                    categoriesAdapter.notifyDataSetChanged();

                    DataSnapshot ds = dataSnapshot.child("Services");
                    for (DataSnapshot service: ds.getChildren()){
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
    }*/

}
