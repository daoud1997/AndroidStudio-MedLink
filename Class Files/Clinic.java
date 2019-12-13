import java.util.*;
/**
 * @author Daoud
 *
 */
public class Clinic implements Account {
	
	private int phoneNumber;
	private String name;
	private String  email;
	private Address address;
	private List<Employee> employee;
	
	public Clinic(int phoneNumber, String name,  String email, Address address) {
		
		boolean didAddAddress = setAddress(address);
		if (!didAddAddress) {
			throw new RuntimeException("Unable to initiate Address!");
		}
		boolean didAddNumb = setPhoneNumber(phoneNumber);
		if (!didAddNumb) {
			throw new RuntimeException("Unable to initiate phone Number!");
		}
		boolean didAddName = setPhoneNumber(phoneNumber);
		if (!didAddName) {
			throw new RuntimeException("Unable to initiate Name!");
		}
		boolean didAddEmail = setEmail(email);
		if (!didAddEmail) {
			throw new RuntimeException("Unable to initiate Email!");
		}
		employee = new ArrayList<Employee>();
		
		
	}
	public Address getAddress() {
		return this.address;
	}
	
	public boolean setAddress(Address address) {
		boolean toSet;
		if (address == null)
			toSet = false;
		else
			toSet = true;
		return toSet;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}
	//EXCLUDED AREA CODE
	public boolean setPhoneNumber(int phoneNumber) {
		boolean toSet;
		int temp = String.valueOf(phoneNumber).length();
		if(temp!=10)
			toSet = false;
		else {
			toSet = true;
		}
		return toSet;
	}

	public String getName() {
		return name;
	}

	public boolean setName(String name) {
		boolean toSet;
		if(name == null)
			toSet = false;
		else {
			this.name = name;
			toSet = true;
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
	
	public List<Employee> getAccount() {
		return this.employee;
	}
	
	public Employee getEmployee(int index) {
		Employee tempEmployee = employee.get(index);
		return tempEmployee;
	}
	
	

	/* (non-Javadoc)
	 * @see Account#deleteAccount(Account)
	 * takes account
	 * returns a deleted account
	 */
	@Override
	public Account deleteAccount(Account account) {
		Account temp = account;
		account = null;
		return temp;
	}

	
}
