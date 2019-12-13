package com.example.medlink_deliverable2.models;


import java.util.List;

/**
 * @author Daoud
 *
 */

public abstract class User implements Account {
	
	private String username;
	private String password;
//	private String dateOfBirth;
	//private String gender;
	private String phoneNumber;
	private String email;
	private Address address;


	//Constructor
	public User(String username, String password, String phoneNumber, String email, Address address) {
		this.username = username;
		this.password = password;
//		this.dateOfBirth = dateOfBirth;
		//this.gender = gender;
		this.phoneNumber = phoneNumber;
//		this.name = name;
		this.email = email;
		this.address = address;
	}

	public User(){} //DO NOT REMOVE, needed for database

	//Getter
	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
//	public String getDateOfBirth() {
//		return this.dateOfBirth;
//	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	/*public String getGender() {
		return this.gender;
	}*/
//	public List<String> getName() {
//		return this.name;
//	}
	public String getEmail() {
		return this.email;
	}
	public Address getAddress(){ return this.address;}

	//Setter
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public void setDateOfBirth(String dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}

	/*public void setGender(String gender) {
		this.gender = gender;
	}*/
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddress(Address address){ this.address = address;}


	//Assuming that the name of person MUST consists of 2 names: First and Last
//	public void setName(List<String> name) {
//		this.name = name;
//	}

	public void setEmail(String email) {
	this.email = email;
	}

}
