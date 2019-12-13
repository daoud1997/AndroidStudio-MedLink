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
	
	public Address(int streetNumb,String streetName,String city,String province,String country,String postalCode ) {
		boolean didAddStreetNumb = setStreetNumb(streetNumb);
		if (!didAddStreetNumb) {
			throw new RuntimeException("Unable to initiate Street Number!");
		}
		boolean didAddStreetName = setStreetName(streetName);
		if (!didAddStreetName) {
			throw new RuntimeException("Unable to initiate Street Name!");
		}
		boolean didAddCity = setCity(city);
		if (!didAddCity) {
			throw new RuntimeException("Unable to initiate postalCode!");
		}
		boolean didAddProvince = setProvince(province);
		if (!didAddProvince) {
			throw new RuntimeException("Unable to initiate Province!");
		}
		boolean didAddCountry = setCountry(country);
		if (!didAddCountry) {
			throw new RuntimeException("Unable to initiate Country!");
		}
		boolean didAddPostalCode = setPostalCode(postalCode);
		if (!didAddPostalCode) {
			throw new RuntimeException("Unable to initiate postalCode!");
		}
		
	}

	public int getStreetNumb() {
		return this.streetNumb;
	}

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

	public String getStreetName() {
		return this.streetName;
	}
	// Assuming street name cant have digits
	public boolean setStreetName(String streetName) {
		boolean toSet = true;
		if(streetName == null)
			toSet = false;
		char[] ch=streetName.toCharArray();
		if(toSet) {
			for(char val : ch) {
				if(Character.isDigit(val)) {
					toSet = false;
					break;
				}
			}
		}
		if(toSet)
			this.streetName = streetName;
		return toSet;
	}

	public String getCity() {
		return this.city;
	}
	//Assuming city can have digits
	public boolean setCity(String city) {
		boolean toSet = true;
		if(city == null)
			toSet = false;
		char[] ch=city.toCharArray();
		if(toSet) {
			for(char val : ch) {
				if(Character.isDigit(val)) {
					toSet = false;
					break;
				}
			}
		}
		if(toSet)
			this.city = city;
		return toSet;
	}

	public String getProvince() {
		return this.province;
	}
	// Assuming province cant have digits
	public boolean setProvince(String province) {
		boolean toSet = true;
		if(province == null)
			toSet = false;
		char[] ch=province.toCharArray();
		if(toSet) {
			for(char val : ch) {
				if(Character.isDigit(val)) {
					toSet = false;
					break;
				}
			}
		}
		if(toSet)
			this.province = province;
		return toSet;
	}

	public String getCountry() {
		return this.country;
	}
	//Assuming country cant have digits
	public boolean setCountry(String country) {
		boolean toSet = true;
		if(country == null)
			toSet = false;
		char[] ch=country.toCharArray();
		if(toSet) {
			for(char val : ch) {
				if(Character.isDigit(val)) {
					toSet = false;
					break;
				}
			}
		}
		if(toSet)
			this.country = country;
		return toSet;
	}

	public String getPostalCode() {
		return postalCode;
	}
	// Since the 3 digits followed by space and then 3 other digits
	public boolean setPostalCode(String postalCode) {
		boolean toSet;
		if(postalCode.length()!=7)
			toSet = false;
		else {
			this.postalCode = postalCode;
			toSet = true;
		}
		return toSet;
	}
	
	
	
}
