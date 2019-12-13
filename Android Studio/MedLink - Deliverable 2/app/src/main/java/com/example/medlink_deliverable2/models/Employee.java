package com.example.medlink_deliverable2.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Daoud
 *
 */
public class Employee extends User {
	
	//private int employeeNum;
	//private Clinic clinic;

	private List<Category> services;
	private String clinicName;
	private List<String> hours;
	private List<Object> daysOpen;
	private List<Object> insuranceTypes;
	private List<Object> paymentMethods;
	private List<Object> rates;
	private int waitingTime = 0; //measured in minutes, initially set to zero and 15 min is incremented per appointment
	private Float avgRating = 0.0f;
	private int amtratings = 0;
	private Float totalratings = 0.0f;
	private List<Appointment> appointments = new ArrayList<Appointment>();
	private static final int DELAYTIME = 15;

	//Constructor
	public Employee(String username, String password, String phoneNumber, String name,
                    String email, Address address, List hours, List daysOpen, List insuranceTypes, List paymentMethods, List rates) {
		super(username, password, phoneNumber,email, address);
		this.clinicName = name;
//		appointments.add(new Appointment("init",null));
	}

	public Employee(){} //DO NOT REMOVE, needed for database

	//Getters

	public List<String> getHours() {
		return hours;
	}

	public int getWaitingTime(){return this.waitingTime;}

	public Float getTotalratings() {
		return totalratings;
	}

	public void setTotalratings(Float totalratings) {
		this.totalratings = totalratings;
	}

	public Float getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Float avgRating) {
		this.avgRating = avgRating;
	}

	public int getAmtratings() {
		return amtratings;
	}

	public void setAmtratings(int amtratings) {
		this.amtratings = amtratings;
	}

	public List<Appointment> getAppointments(){return this.appointments;}

	public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public void setAppointments(List<Appointment> appointments){this.appointments = appointments;}

    public void setServices(List<Category> services){this.services = services;}

	public List<Category> getServices() {
		return services;
	}

	public List<Object> getDaysOpen() {
        return daysOpen;
    }

    public void setDaysOpen(List<Object> daysOpen) {
        this.daysOpen = daysOpen;
    }

    public List<Object> getInsuranceTypes() {
        return insuranceTypes;
    }

    public void setInsuranceTypes(List<Object> insuranceTypes) {
        this.insuranceTypes = insuranceTypes;
    }

    public List<Object> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<Object> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<Object> getRates() {
        return rates;
    }

    public void setRates(List<Object> rates) {
        this.rates = rates;
    }

    public void setHours(List<String> hours) {
		this.hours = hours;
	}

	public void setWaitingTime(int waitingTime){this.waitingTime = waitingTime;}

	public String getClinicName(){return this.clinicName;}


	public int calculateWaitingTime(){return (this.appointments.size() * DELAYTIME);}

	//deletes the reserved time slot for the specified appointment and decrease waiting time by DelayTime
	public void checkIn(Appointment appointment){
		appointments.remove(appointment);
	}


	@Override
	public void deleteAccount(Account account) {
		if(account instanceof Employee) {
			//NEED TO SEARCH IN DATABASE FOR AN ACCOUNT OF TYPE EMPLOYEE
		}
	}

	/* (non-Javadoc)
	 * @see User#createAccount()
	 */

	public Account createAccount(String username, String password, String phoneNumber, String name, String email, Address address, List hours, List daysOpen, List insuranceTypes, List paymentMethods, List rates) {

		Account newEmployeeAccount = new Employee(username, password, phoneNumber, name, email, address, hours, daysOpen, insuranceTypes, paymentMethods, rates);
		//NEED TO STORE IT IN DATABASE!!!!!!!!!

		return newEmployeeAccount;
	}

	public String toString(){
		String employee =  super.getAddress().toString();
		return employee;
	}

	public String toStringClinicName(){
		return (this.clinicName);
	}

	/*public String toStringServices(){

	}
	*/
	/*public Account createClinicAccount(String phoneNumber, String name,  String email, Address address) {
		Account newClinicAccount = new Clinic(this, phoneNumber, name, email, address);
		//NEED TO STORE IT IN DATABASE
		return newClinicAccount;
	}*/


}
