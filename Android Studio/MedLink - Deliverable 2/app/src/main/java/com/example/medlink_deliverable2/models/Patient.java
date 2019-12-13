package com.example.medlink_deliverable2.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 
 */

/**
 * @author Daoud
 *
 */
public class Patient extends User  {

	private boolean ifInsurance;
	private String gender;
	private String dateOfBirth;
	private List<String> name;
	private List<Appointment> appointments = new ArrayList<Appointment>();

	//Constructor
	public Patient(String username, String password, String dateOfBirth, String gender, String phoneNumber, List<String> name, String email, boolean ifInsurance, Address address) {
		super(username, password, phoneNumber, email, address);
		this.gender = gender;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.ifInsurance =ifInsurance;
//		appointments.add(new Appointment("init",null));
	}

	public Patient(){} //DO NOT REMOVE, needed for database

	//Getter
	public boolean getIfInsurance() {
		return this.ifInsurance;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public String getDateOfBirth(){return this.dateOfBirth;}

	public List<String> getName(){return this.name;}

	public void setIfInsurance(boolean ifInsurance) {
		this.ifInsurance = ifInsurance;
	}

	public void setAppointments(List<Appointment>appointments) {
		this.appointments = appointments;
	}

	/* (non-Javadoc)
	 * @see Account#deleteAccount(Account)
	 */
	@Override
	public void deleteAccount(Account account) {
		if(account instanceof Patient) {
			//SEARCH THE DATABASE FOR PATIENT AND DELETE
		}
	}

	public Account createAccount(String username, String password, String dateOfBirth, String gender, String phoneNumber,
			List<String> name, String email, boolean ifInsurance, Address address) {

		Account newPatientAccount = new Patient(username, password, dateOfBirth, gender, phoneNumber, name, email, ifInsurance, address);
		return newPatientAccount;
	}

    public String toString(){
        String patient = this.name.get(0) + " " + this.name.get(1) + ", " + super.getAddress().toString();
        return patient;
    }

}
