package com.example.medlink_deliverable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medlink_deliverable2.models.Category;
import com.example.medlink_deliverable2.models.CategoryList;
import com.example.medlink_deliverable2.models.ClinicList;
import com.example.medlink_deliverable2.models.Employee;
import com.example.medlink_deliverable2.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchForClinicsActivity extends AppCompatActivity {

    private String id;
    RadioGroup search_criteria;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    List<Category> results = new ArrayList<Category>();
    private boolean isValid;
    String userKey;

    List<Employee> temp = new ArrayList<Employee>();
    ListView searchClinicLV;
    List<String> userKeys;
    List<CheckBox> ArrayOfDays;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_clinics);

        id = getIntent().getExtras().getString("id");

        final RadioGroup search_criteria = findViewById(R.id.rg_searchBy);
        final EditText searchClinicName = findViewById(R.id.search_clinicName);
        final RadioButton searchAddress = search_criteria.findViewById(R.id.search_address);
        final RadioButton searchService = search_criteria.findViewById(R.id.search_service);
        final RadioButton searchName = search_criteria.findViewById(R.id.search_name);
        final RadioButton searchHours = search_criteria.findViewById(R.id.search_hours);


        final RadioButton searchDay = search_criteria.findViewById(R.id.search_day);




        final EditText startHET = findViewById(R.id.searchStartTime);

        final EditText endHET = findViewById(R.id.searchEndTime);

        isValid = false;
        userKeys = new ArrayList<String>();
        searchClinicLV =  findViewById(R.id.listview_Clinics);

        Button searchBtn = findViewById(R.id.search_btm);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userKeys.clear();
                populate(userKeys);
                if(searchService.isChecked()) {
                    final String searchname = searchClinicName.getText().toString().trim();

                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List categories = new ArrayList<Category>();
                            String str = " ";
                            for (DataSnapshot ds : dataSnapshot.child("Accounts").child("Users").child("Employees").getChildren()) {
                                Employee employee = ds.getValue(Employee.class);

                                ArrayList services = (ArrayList) employee.getRates();
                                if(!services.isEmpty()) {
                                   // Toast.makeText(getApplicationContext(), "WORKING!!", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < services.size(); i++) {
                                        ArrayList temp = (ArrayList) services.get(i);
                                        String lol = temp.get(0).toString().trim();
                                        Category category = dataSnapshot.child("Services").child(lol).getValue(Category.class);
                                        categories.add(category);
                                       //Toast.makeText(getApplicationContext(), categories.toString(), Toast.LENGTH_SHORT).show();
                                }

                                    for (Object categ : categories){
                                        str += categ.toString() + " ";
                                    }
                                    String total = str;

                                    String lowerCaseTotal = total.toLowerCase();
                                    if (lowerCaseTotal.contains(searchname.toLowerCase())) {
                                        boolean zebra = true;
                                        try {
                                            userKeys.add(ds.getValue(Employee.class).getClinicName());
                                            userKey = ds.getKey();
                                        }catch(NullPointerException e){
                                            e.getMessage();
                                        }
                                        isValid = true;
                                        populate(userKeys);
                                        try {
                                            TimeUnit.MILLISECONDS.sleep(1);
                                        } catch (InterruptedException e) {
                                            e.getMessage();
                                        }
                                    }
                                }
                            }

                            if (!isValid) {
                                Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } else if (searchAddress.isChecked()) {
                    final String searchname = searchClinicName.getText().toString().trim();
                    db.child("Accounts").child("Users").child("Employees").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Employee employee = ds.getValue(Employee.class);
                                if (employee.getAddress().toString().contains(searchname)) {
                                    isValid = true;
                                    userKeys.add(ds.getValue(Employee.class).getClinicName());
                                    populate(userKeys);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.getMessage();
                                    }
                                    userKey = ds.getKey();
                                    userKeys.add(userKey);
                                    temp.add(employee);
                                    //Toast.makeText(getApplicationContext(), "Now click on either clinic addresses that matches your search", Toast.LENGTH_SHORT).show();
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.getMessage();
                                    }
                                }
                            }

                            if (!isValid) {
                                Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }  else if (searchName.isChecked()) {
                    final String searchname = searchClinicName.getText().toString().trim();

                    db.child("Accounts").child("Users").child("Employees").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Employee employee = ds.getValue(Employee.class);
                                if (employee.getClinicName().contains(searchname)) {
                                    try {
                                        userKeys.add(ds.getValue(Employee.class).getClinicName());
                                        userKey = ds.getKey();
                                    }catch(NullPointerException e){
                                        e.getMessage();
                                    }
                                    isValid = true;
                                    populate(userKeys);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.getMessage();
                                    }

                                    Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_SHORT).show();
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.getMessage();
                                    }
                                }
                            }


                            if (!isValid) {
                                Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } else if(searchHours.isChecked()){

                    final String searchname = searchClinicName.getText().toString().trim();
                    final String startHStr = startHET.getText().toString().trim();
                    final String startH = startHStr.replace(":", "");
                    final int startHInt = Integer.parseInt(startH);

                    final String endHStr = endHET.getText().toString().trim();
                    final String endH = endHStr.replace(":", "");
                    final int endHInt = Integer.parseInt(endH);

                    db.child("Accounts").child("Users").child("Employees").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int[] hrsNum = new int[2];
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Employee employee = ds.getValue(Employee.class);
                                ArrayList hours = (ArrayList) employee.getHours(); // String hours
                                if(!hours.isEmpty()){
                                    for (int i = 0; i < hours.size(); i++) { // Turning hours to integers
                                        String temp = (String) hours.get(i);
                                        temp = temp.replaceAll(":", "");
                                        int inttemp = Integer.parseInt(temp);
                                        hrsNum[i] = inttemp;
                                    }

                                }

                                if (startHInt >= hrsNum[0] && endHInt <= hrsNum[1]) {
                                    try {
                                        userKeys.add(ds.getValue(Employee.class).getClinicName());
                                        userKey = ds.getKey();
                                    }catch(NullPointerException e){
                                        e.getMessage();
                                    }
                                    isValid = true;
                                    populate(userKeys);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.getMessage();
                                    }


                                    // temp.add(employee1);
                                    Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_SHORT).show();
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.getMessage();
                                    }
                                }
                            }


                            if (!isValid) {
                                Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                    else if(searchDay.isChecked()) {

                    final CheckBox searchMon = findViewById(R.id.search_mon);
                    final CheckBox searchTues = findViewById(R.id.search_tue);
                    final CheckBox searchWed = findViewById(R.id.search_wed);
                    final CheckBox searchThurs = findViewById(R.id.search_thurs);
                    final CheckBox searchFri = findViewById(R.id.search_fri);
                    final CheckBox searchSat = findViewById(R.id.search_sat);
                    final  CheckBox searchSun = findViewById(R.id.search_sun);

                    ArrayOfDays = new ArrayList<CheckBox>();
                    ArrayOfDays.add(searchMon);
                    ArrayOfDays.add(searchTues);
                    ArrayOfDays.add(searchWed);
                    ArrayOfDays.add(searchThurs);
                    ArrayOfDays.add(searchFri);
                    ArrayOfDays.add(searchSat);
                    ArrayOfDays.add(searchSun);
                    final String isTrue = "true";
                    //String isFalse = "false";
                    db.child("Accounts").child("Users").child("Employees").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Employee employee = ds.getValue(Employee.class);

                                    for(int j =0; j<employee.getDaysOpen().size();j++){
                                        String zeb = employee.getDaysOpen().get(j).toString();

                                        if( (searchFri.isChecked() || searchSat.isChecked() ||
                                                searchSun.isChecked()||searchMon.isChecked()||
                                                searchTues.isChecked()|| searchWed.isChecked()||
                                                searchThurs.isChecked()||searchFri.isChecked())&& zeb.contains(isTrue)){
                                            try {
                                                userKeys.add(ds.getValue(Employee.class).getClinicName());
                                                userKey = ds.getKey();
                                            }catch(NullPointerException e){
                                                e.getMessage();
                                            }
                                            isValid = true;
                                            populate(userKeys);

                                            //employeeDayisTrue.clear();
                                            try {
                                                TimeUnit.MILLISECONDS.sleep(1);
                                            } catch (InterruptedException e) {
                                                e.getMessage();
                                            }


                                            try {
                                                TimeUnit.MILLISECONDS.sleep(10);
                                            } catch (InterruptedException e) {
                                                e.getMessage();
                                            }
                                            break;
                                        }
                                       // Toast.makeText(getApplicationContext(), "Searching", Toast.LENGTH_SHORT).show();

                                    }



                            }

                            if (!isValid) {
                                Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                } else {
                    Toast.makeText(getApplicationContext(), "Please select a search criteria", Toast.LENGTH_SHORT).show();
                }

            }
        });


        searchClinicLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedClinicName = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(getApplicationContext(), ClinicViewPatient.class);
                intent.putExtra("patientId", id);
                intent.putExtra("clinicId", userKey);
                intent.putExtra("clinicNameId",selectedClinicName);
                startActivityForResult(intent, 0);
                startActivity(intent);
            }

        });


    }
    //BASIC STRING ADAPTOR WAY NO NEED TO IMPLEMENT CLASSES!!
    public void populate(List<String> userkeyz){
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userkeyz.toArray(new String[userkeyz.size()]));

        searchClinicLV.setAdapter(itemsAdapter);
    }

}




