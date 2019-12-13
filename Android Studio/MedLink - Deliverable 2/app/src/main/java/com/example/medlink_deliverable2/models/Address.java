package com.example.medlink_deliverable2.models;

/**
 * @author Daoud
 *
 */
public class Address {
	
	private int streetNumb;
	private String streetName;
	private String city;
	private String province;
	private String country;
	private String postalCode;

	//constructor
	public Address(int streetNumb,String streetName,String city,String province,String postalCode, String country ) {
		this.streetNumb = streetNumb;
		this.streetName = streetName;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
	}

	public Address(){} //DO NOT REMOVE, needed for database

	//getters
	public int getStreetNumb() {
		return this.streetNumb;
	}
	public String getStreetName() {
		return this.streetName;
	}
	public String getCity() {
		return this.city;
	}
	public String getProvince() {
		return this.province;
	}
	public String getCountry() {
		return this.country;
	}
	public String getPostalCode() {
		return postalCode;
	}

	//setters
	public boolean setStreetNumb(int streetNumb) {
		boolean toSet;
		if(streetNumb<=0)
			toSet =false;
		else {
			this.streetNumb = streetNumb;
			toSet = true;
		}
		return toSet;
	}
	// Assuming street name cant have digits
	public void setStreetName(String streetName) {
		this.streetName = streetName;
		}

	public void setCity(String city) {
		this.city = city;
	}

	public void setProvince(String province) {
		this.province = province;
		}

	public void setCountry(String country) {
		this.country = country;
		}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	//toString method to return a string representation including Country, City, Province, Streetname,
	// Streetnumber, postalcode

	public String toString(){
		return (this.country + ", " + this.city + ", " + this.province + ", " + this.streetNumb +", " +
				this.streetName + ", " + this.postalCode);
	}
	
}
