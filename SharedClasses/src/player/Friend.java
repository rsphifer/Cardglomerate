package player;

import java.io.Serializable;

public class Friend implements Serializable {
	private String username;
	public boolean isOnline;
	
	public Friend(String username, boolean isOnline) {
		this.username = username;
		this.isOnline = isOnline;
	}
	
	public String getUsername() {
		return username;
	}
}
