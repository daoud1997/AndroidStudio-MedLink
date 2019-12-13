package com.example.medlink_deliverable2.models;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

/**
 * @author Daoud
 *
 */
public class Admin {

	private List<Account> account;
	private List<Category> category;
	private DatabaseReference db;

	//Constructor
	public Admin(){
		account = new ArrayList<Account>();
		category = new ArrayList<Category>();
	}

	//Setters

	public void setCategory(List<Category> Category) {
		this.category = category;
	}

	//getters
	public List<Category> getCategory() {
		return this.category;
	}

	public List<Account> getAccount() {
		return this.account;
	}

	public Account getAccount(int index) {
		Account tempAccount = account.get(index);
		return tempAccount;
	}
	//Adding service from already existing category
	public boolean createService(Service service) {

		if(service == null)
			return false;
		boolean isValid = false;


		// load categories and services from database onto this classes category variable
		db = FirebaseDatabase.getInstance().getReference();

		db.child("Services").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

				category = new ArrayList<Category>();
				for (DataSnapshot ds: dataSnapshot.getChildren()){
					category.add(ds.getValue(Category.class));
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

		for(int i=0; i<this.category.size(); i++){
			if(this.category.get(i).getService().indexOf(service)!=-1){
				isValid = this.category.get(i).getService().add(service);
			}
		}

		//update database services
		db.child("Services").removeValue();
		for (int i=0;i<category.size();i++){
			db.child("Services").push().setValue(category.get(i));
		}

		return isValid;

	}
	//adding a category
	public boolean createCategory(Category creCategory) {
		if(category == null)
			return false;

		// load categories and services from database onto this classes category variable
		db = FirebaseDatabase.getInstance().getReference();

		db.child("Services").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

				category = new ArrayList<Category>();
				for (DataSnapshot ds: dataSnapshot.getChildren()){
					category.add(ds.getValue(Category.class));
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

		boolean isValid = this.category.add(creCategory);

		//update database services
		db.child("Services").removeValue();
		for (int i=0;i<category.size();i++){
			db.child("Services").push().setValue(category.get(i));
		}

		return isValid;

	}

	//deleting a service if it existing or else it will return false
	//deleting it from an already existing category
	public boolean deleteService(Service service) {

		// load categories and services from database onto this classes category variable
		db = FirebaseDatabase.getInstance().getReference();

		db.child("Services").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

				category = new ArrayList<Category>();
				for (DataSnapshot ds: dataSnapshot.getChildren()){
					category.add(ds.getValue(Category.class));
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

		if(service == null)
			return false;
		boolean isValid = false;
		for(int i=0; i<category.size(); i++){
			if(category.get(i).getService().indexOf(service)!=-1){
				isValid = category.get(i).getService().remove(service);
			}
		}

		//update database services
		db.child("Services").removeValue();
		for (int i=0;i<category.size();i++){
			db.child("Services").push().setValue(category.get(i));
		}

		return isValid;

	}
	//deleting a category or else it will return false
	//ANOTHER WAY OF DOINIG THIS IS TO SEARCH THE DATABASE FOR category AND DELETING IT
	public boolean deleteCategory(Category delCategory) {

		// load categories and services from database onto this classes category variable
		db = FirebaseDatabase.getInstance().getReference();

		db.child("Services").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				category = new ArrayList<Category>();
				for (DataSnapshot ds: dataSnapshot.getChildren()){
					category.add(ds.getValue(Category.class));
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});

		if (delCategory == null)
			return false;
		if (category.indexOf(delCategory) == -1)    //if service does not exists
			return false;
		boolean isValid = category.remove(delCategory);

		//update database services
		db.child("Services").removeValue();
		for (int i=0;i<category.size();i++){
			db.child("Services").push().setValue(category.get(i));
		}

		return isValid;

	}
}
