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
import android.widget.TextClock;
import android.widget.Toast;

import com.example.medlink_deliverable2.models.Category;
import com.example.medlink_deliverable2.models.CategoryList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageServices extends AppCompatActivity {

    private DatabaseReference db;
    ListView listViewCategories;
    List<Category> categories = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);

        final Button btn_create_category = findViewById(R.id.btn_createcategory);
        final EditText input_categoryName = findViewById(R.id.input_categoryname);
        final EditText input_categoryRole = findViewById(R.id.input_categoryrole);
        listViewCategories =findViewById(R.id.listviewCategories);

        btn_create_category.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String categoryName = input_categoryName.getText().toString().trim();
                String categoryRole = input_categoryRole.getText().toString().trim();

                if ((!TextUtils.isEmpty(categoryName)) && (!TextUtils.isEmpty(categoryRole))){
                    OnCreateCategoryButton(v,categoryName, categoryRole);
                }
                else if(TextUtils.isEmpty(categoryName) && TextUtils.isEmpty(categoryRole)){
                    input_categoryName.setError("Cannot be empty!");
                    input_categoryRole.setError("Cannot be empty!");
                }
                else if(TextUtils.isEmpty(categoryName)){
                    input_categoryName.setError("Cannot be empty!");
                }
                else{
                    input_categoryRole.setError("Cannot be empty!");
                }
            }
        });

        listViewCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = categories.get(i);
                showDeleteDialog(category.getId(), category.getName(), category.getRole());
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        db=FirebaseDatabase.getInstance().getReference();
        db.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categories.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Category category = ds.getValue(Category.class);
                    categories.add(category);
                }
                CategoryList categoriesAdapter = new CategoryList(ManageServices.this,categories);
                listViewCategories.setAdapter(categoriesAdapter);
                categoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void OnCreateCategoryButton(View view,String name, String role){
        //TODO: take the input from the EditText and put into database
        db = FirebaseDatabase.getInstance().getReference();
        String id = db.child("Services").push().getKey();
        Category category = new Category(name, id, role);
        db.child("Services").child(id).setValue(category);
        categories.add(category);
        Toast.makeText(this,"Service added!", Toast.LENGTH_LONG).show();
    }

    private void showDeleteDialog(final String id, String categoryName, String categoryRole) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName =dialogView.findViewById(R.id.editTextName);
        final EditText editTextRole =dialogView.findViewById(R.id.editTextRole);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteCategory);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateCategory);


        dialogBuilder.setTitle(categoryName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String role = editTextRole.getText().toString().trim();
                if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(role))){
                    Category category = new Category(name, id, role);
                    FirebaseDatabase.getInstance().getReference("Services").child(id).setValue(category);
                    b.dismiss();
                }
                else if(TextUtils.isEmpty(name) && TextUtils.isEmpty(role)){
                    editTextName.setError("Cannot be empty!");
                    editTextRole.setError("Cannot be empty!");
                }
                else if(TextUtils.isEmpty(name)){
                    editTextName.setError("Cannot be empty!");
                }
                else{
                    editTextRole.setError("Cannot be empty!");
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("Services").child(id).removeValue();
                b.dismiss();
            }
        });
    }

}
