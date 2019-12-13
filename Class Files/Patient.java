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
	private int id;
	
	public Patient(String username, String password, String dateOfBirth, String gender, int phoneNumber, String[] name, String email, Address address, boolean ifInsurance) {
		super(username, password, dateOfBirth, gender, phoneNumber, name, email, address);
		Random rand = new Random();
		this.id = rand.nextInt();
	}

	public boolean getIfInsurance() {
		return this.ifInsurance;
	}
	
	public int getId() {
		return this.id;
	}

	public void setIfInsurance(boolean ifInsurance) {
		this.ifInsurance = ifInsurance;
	}

	/* (non-Javadoc)
	 * @see Account#deleteAccount(Account)
	 */
	@Override
	public Account deleteAccount(Account account) {
		if(account instanceof Patient) {
			Account temp = account;
			account = null;
			return temp;
		}else {
			throw new IllegalArgumentException("Wrong argument Passed, Delete Not Successful!");	
		}
	}

	/* (non-Javadoc)
	 * @see User#createAccount(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String[], java.lang.String, Clinic)
	 */
	@Override
	public Account createAccount(String username, String password, String dateOfBirth, String gender, int phoneNumber,
			String[] name, String email, Address address) {
		boolean boolUsername = this.setUsername(username);
		boolean boolPassword = this.setPassword(password);
		boolean boolDateOfBirth = this.setDateOfBirth(dateOfBirth);
		boolean boolGender = this.setGender(gender);
		boolean boolPhoneNumber = this.setPhoneNumber(phoneNumber);
		boolean boolName = this.setName(name);
		boolean boolEmail = this.setEmail(email);
		if(boolUsername && boolPassword && boolDateOfBirth && boolGender && boolPhoneNumber && boolName && boolEmail) {
			Account newPatient = new Patient(username,password,dateOfBirth,gender,phoneNumber,name,email, address, this.ifInsurance);
			return newPatient;
		}else {
			throw new IllegalArgumentException("Wrong argument Passed, Account can Not be created!");
		}	
	}
	
	
	
	
}
