import java.time.LocalDate;
import java.time.format.DateTimeParseException;


/**
 * @author Daoud
 *
 */

public abstract class User implements Account {
	
	private String username;
	private String password;
	private LocalDate dateOfBirth;
	private String gender;
	private int phoneNumber;
	private String [] name;
	private String email;
	private Address address;
	
	public User(String username, String password, String dateOfBirth, String gender, int phoneNumber, String[] name, String email, Address address) {
		boolean didAddUsername = setUsername(username);
		if (!didAddUsername) {
			throw new RuntimeException("Unable to initiate username!");
		}
		boolean didAddPassword = setPassword(password);
		if (!didAddPassword) {
			throw new RuntimeException("Unable to initiate Password!");
		}
		boolean didAddDateOfBirth = setDateOfBirth(dateOfBirth);
		if (!didAddDateOfBirth) {
			throw new RuntimeException("Unable to initiate Date Of Birth!");
		}
		boolean didAddGender = setGender(gender);
		if (!didAddGender) {
			throw new RuntimeException("Unable to initiate gender!");
		}
		boolean didAddPhoneNumber = setPhoneNumber(phoneNumber);
		if (!didAddPhoneNumber) {
			throw new RuntimeException("Unable to initiate Phone Number!");
		}
		boolean didAddName = setName(name);
		if (!didAddName) {
			throw new RuntimeException("Unable to initiate Name!");
		}
		boolean didAddEmail = setEmail(email);
		if (!didAddEmail) {
			throw new RuntimeException("Unable to initiate Email!");
		}
	}
	
	
	public Address getAddress() {
		return this.address;
	}

	public boolean setAddress(Address address) {
		boolean toSet;
		if(address ==null)
			toSet = false;
		else {
			this.address = address;
			toSet = true;
		}
		
		return toSet;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean setUsername(String username) {
		boolean toSet;
		if(username ==null)
			toSet = false;
		else {
			this.username = username;
			toSet = true;
		}
			
		return toSet;
		
	}

	public String getPassword() {
		return this.password;
	}
	// for security reasons we are setting the size of password to be greater than 5 
	public boolean setPassword(String password) {
		boolean toSet;
		if(password == null || password.length()<5) {
			toSet = false;
		}
		else {
			this.password = password;
			toSet = true;

		}
		return toSet;
	}
	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}
	
	public boolean setDateOfBirth(String dateOfBirth) {
		boolean toSet;
		if(dateOfBirth == null)
			toSet =false;
		try {
			this.dateOfBirth = LocalDate.parse(dateOfBirth);
			toSet = true;
		}catch (DateTimeParseException e) {
			System.out.println("Wrong Format! Please follow: YYYY-MM-DD Putting zeros when neccassarily!");
			toSet = false;
		}
		return toSet;
	
		
	}
	public String getGender() {
		return this.gender;
	}
	//taking first letter into consideration M male F female O other
	public boolean setGender(String gender) {
		boolean toSet = true;
		if(gender == null)
			toSet = false;
		char fstLetter = gender.charAt(0);
		if(Character.toLowerCase(fstLetter)=='m')
			this.gender = "Male";
		else if(Character.toLowerCase(fstLetter)=='f')
			this.gender = "Male";
		else if(Character.toLowerCase(fstLetter)=='o')
			this.gender = "Other";
		else 
			toSet = false;
		
		return toSet;	
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public boolean setPhoneNumber(int phoneNumber) {
		boolean toSet;
		int temp = String.valueOf(phoneNumber).length();
		if(temp!=10)
			toSet = false;
		else {
			this.phoneNumber = phoneNumber;
			toSet = true;
		}
			
		
		return toSet;
	}

	public String[] getName() {
		return this.name;
	}
	//Assuming that the name of person MUST consists of 3 names: First Middle and Last 
	public boolean setName(String[] name) {
		boolean toSet = true;
		if(name == null)
			toSet = false;
		if(name.length!=3)
			toSet = false;
		
		if(toSet) {
			for(int i =0;i<name.length;i++) {
				char[] ch = name[i].toCharArray();
				for(char val:ch) {
					if(Character.isDigit(val)) {
						toSet = false;
						break;	
				}
					
			}
			
		}
		}else if(toSet) {
			this.name = name;
		}
		return toSet;
	}

	public String getEmail() {
		return email;
	}

	public boolean setEmail(String email) {
		boolean toSet = true;
		
		char[] ch=email.toCharArray();
		for(char value:ch) {
			if(value !='@' || value != '.')
				toSet = false;
		}
		
		for(int i = 0; i<ch.length; i++) {
			if(ch[i]=='@') {
				if (i==0) {
					toSet = false;
					break;
				}
				else if(i==ch.length) {
					toSet = false;
					break;
				}else 
					this.email = email;
			}
		}
		
		return toSet;
	}

	public abstract Account createAccount(String username, String password, String dateOfBirth, String gender, int phoneNumber, String[] name, String email, Address address);

}
