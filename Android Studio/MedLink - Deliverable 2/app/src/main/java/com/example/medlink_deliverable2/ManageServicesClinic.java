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

import com.example.medlink_deliverable2.models.Category;
import com.example.medlink_deliverable2.models.CategoryList;
import com.example.medlink_deliverable2.models.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ManageServicesClinic extends AppCompatActivity {

    private DatabaseReference db;
    ListView listViewClinicServices;
    List<Category> categories = new ArrayList<Category>();
    private String id;
    private Employee tempEmployee;
    private ArrayList<Object> serviceNames = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services_clinic);
        id = getIntent().getExtras().getString("id");


        listViewClinicServices = findViewById(R.id.listviewClinicServices);

        listViewClinicServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = categories.get(i);
                showClinicSetRateDialogue(category.getId(), category.getName());
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
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
    }

    private void showClinicSetRateDialogue(final String categoryId, final String categoryName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_clinic_delete_dialogue, null);
        dialogBuilder.setView(dialogView);

        final Button buttonClinicDelete = (Button) dialogView.findViewById(R.id.btn_deleteClinicService);

        dialogBuilder.setTitle(categoryName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        //TODO: THIS ONE DELETES THE SERVICE NEEDS MODIFICATIONS
        buttonClinicDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList services = (ArrayList) tempEmployee.getRates();
                int i =0;
                while(i<services.size()){
                    ArrayList temp = (ArrayList) services.get(i);
                    String serviceName = (String) temp.get(0);
                    for (int a = 0; a<serviceNames.size();a++){
                        ArrayList<String> idAndService = (ArrayList<String>) serviceNames.get(a);
                        if (idAndService.get(0).equals(serviceName)){
                            services.remove(i);
                        }
                    }
//                    if(serviceName.equals(categoryName)){
//
//                    }
                    i++;
                }
                tempEmployee.setRates(services);
                db.child("Accounts").child("Users").child("Employees").child(id).setValue(tempEmployee);

                b.dismiss();
            }
        });
    }
}
