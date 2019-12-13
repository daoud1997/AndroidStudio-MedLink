import java.util.Random; 

/**
 * @author Daoud
 *
 */
public class Employee extends User {
	
	private int employeeNum;
	private String role;
	private Clinic clinic;
	
	
	public Employee(String username, String password, String dateOfBirth, String gender, int phoneNumber, String[] name, String email, Address address, String role, Clinic clinic) {
		super(username, password, dateOfBirth, gender, phoneNumber, name,email, address);
		Random rand = new Random(); 
		this.employeeNum = rand.nextInt();
		boolean didAddRole = setRole(role);
		if (!didAddRole) {
			throw new RuntimeException("Unable to initiate Role!");
		}
		boolean didAddClinic = setClinic(clinic);
		if (!didAddClinic) {
			throw new RuntimeException("Unable to initiate Clinic!");
		}
	}
	
	public int getEmployeeNum() {
		return this.employeeNum;
	}

	public Clinic getClinic() {
		return this.clinic;
	}

	public boolean setClinic(Clinic clinic) {
		boolean toSet;
		if(clinic == null)
			toSet = false;
		else {
			this.clinic = clinic;
			toSet = true;
		}
		return toSet;	
	}
	
	public String getRole() {
		return role;
	}

	public boolean setRole(String role) {
		boolean toSet;
		if(role == null)
			toSet = false;
		else {
			this.role = role;
			toSet = true;
		}
		return toSet;
	}

	/* (non-Javadoc)
	 * @see Account#deleteAccount(Account)
	 */
	@Override
	public Account deleteAccount(Account account) {
		if(account instanceof Employee) {
			Account temp = account;
			account = null;
			return temp;
		}else {
			throw new IllegalArgumentException("Wrong argument Passed, Delete Not Successful!");	
		}
	}

	/* (non-Javadoc)
	 * @see User#createAccount()
	 */
	@Override
	public Account createAccount(String username, String password, String dateOfBirth, String gender, int phoneNumber, String[] name, String email, Address address) {
		
		boolean boolUsername = this.setUsername(username);
		boolean boolPassword = this.setPassword(password);
		boolean boolDateOfBirth = this.setDateOfBirth(dateOfBirth);
		boolean boolGender = this.setGender(gender);
		boolean boolPhoneNumber = this.setPhoneNumber(phoneNumber);
		boolean boolName = this.setName(name);
		boolean boolEmail = this.setEmail(email);
		boolean boolAddress = this.setAddress(address);
		if(boolUsername && boolPassword && boolDateOfBirth && boolGender && boolPhoneNumber && boolName && boolEmail &&boolAddress) {
			Account newEmployee = new Employee(username,password,dateOfBirth,gender,phoneNumber,name,email, address, this.role,this.clinic);
			return newEmployee;
		}else {
			throw new IllegalArgumentException("Wrong argument Passed, Account can Not be created!");
		}
	}
	
	
	public Account createClinicAccount(int phoneNumber, String name,  String email, Address address) {
		boolean boolName = this.clinic.setName(name);
		boolean boolEmail = this.clinic.setEmail(email);
		boolean boolPhoneNumb = this.clinic.setPhoneNumber(phoneNumber);
		boolean boolAddress = this.clinic.setAddress(address);
		if(boolName && boolEmail && boolPhoneNumb && boolAddress) {
			Account newClinic = new Clinic(phoneNumber, name, email, address);
			return newClinic;
		}
		else {
			throw new IllegalArgumentException("Wrong argument Passed, Account can Not be created!");
		}
		
	}

	
}
