package com.example.medlink_deliverable2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.ArrayList;
import java.util.List;

public class Registration_Employee3 extends AppCompatActivity {

    private DatabaseReference db;
    ListView listViewCategories;
    List<Category> categories = new ArrayList<Category>();
    private String id;
    private List servicesAndRates = new ArrayList();

    private boolean setService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__employee3);

        id = getIntent().getExtras().getString("id");

        listViewCategories = findViewById(R.id.listviewCategories);
        listViewCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = categories.get(i);
                showSetRateDialogue(category.getId(), category.getName(), category.getRole());
                return true;
            }
        });

        final Button createAcc = findViewById(R.id.btn_createAccount);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!setService){
                    Toast.makeText(getApplicationContext(), "Please add at least one service", Toast.LENGTH_SHORT).show();
                }

                if (setService){

                    db.child("Accounts").child("Users").child("Employees").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Employee employee = dataSnapshot.child(id).getValue(Employee.class);
                            employee.setRates(servicesAndRates);
                            db.child("Accounts").child("Users").child("Employees").child(id).setValue(employee);
                            sendToLogIn();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });


    }

    public void sendToLogIn(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult (intent,0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        db= FirebaseDatabase.getInstance().getReference();
        db.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categories.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Category category = ds.getValue(Category.class);
                    categories.add(category);
                }
                CategoryList categoriesAdapter = new CategoryList(Registration_Employee3.this,categories);
                listViewCategories.setAdapter(categoriesAdapter);
                categoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // TODO: I made a similar thing to the Edit Service where we're gonna have a pop-up
    private void showSetRateDialogue(final String id, String categoryName, String categoryRole) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_set_rate_dialogue, null);
        dialogBuilder.setView(dialogView);

        final EditText editRate = dialogView.findViewById(R.id.editRate);
        final Button button_submit = (Button) dialogView.findViewById(R.id.btn_submit);


        dialogBuilder.setTitle(categoryName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        //TODO: HOW ABOUT CATEGORYROLE D:

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rate = editRate.getText().toString().trim();
                if ((!TextUtils.isEmpty(rate))){
                    List serviceAndRate = new ArrayList();
                    serviceAndRate.add(id);
                    serviceAndRate.add(rate);
                    servicesAndRates.add(serviceAndRate);
                    setService = true;
                    b.dismiss();
                }
                else if(TextUtils.isEmpty(rate) && TextUtils.isEmpty(rate)){
                    editRate.setError("Cannot be empty!");
                }
            }
        });
    }
}
