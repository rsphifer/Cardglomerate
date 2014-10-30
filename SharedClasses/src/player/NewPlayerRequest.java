package player;

import java.io.Serializable;

public class NewPlayerRequest implements Serializable {
	private String userName, password;
	private String emailAddress;
	
	public NewPlayerRequest(String userName, String password, String emailAddress) {
		this.userName = userName;
		this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
		this.emailAddress = emailAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
	
	
}
