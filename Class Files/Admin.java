import java.util.*;

/**
 * @author Daoud
 *
 */
public class Admin {
	
	private String username, password;
	private List<Account> account;
	
	public Admin(String username, String password) {
		boolean didAddUsername = setUsername(username);
		if (!didAddUsername) {
			throw new RuntimeException("Unable to initiate username!");
		}
		boolean didAddPassword = setPassword(password);
		if (!didAddPassword) {
			throw new RuntimeException("Unable to initiate Password!");
		}
		account = new ArrayList<Account>();
	}
	
	public String getUsername() {
		return this.username;
	}

	public boolean setUsername(String username) {
		boolean toSet;
		if(username == null)
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

	public List<Account> getAccount() {
		return this.account;
	}
	
	public Account getAccount(int index) {
		Account tempAccount = account.get(index);
		return tempAccount;
	}
	
}
