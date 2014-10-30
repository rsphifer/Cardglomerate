package misc;

import java.io.Serializable;

public class ChatEntry implements Serializable {
	private String username, message;

	public ChatEntry(String username, String message) {
		this.username = username;
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public String getMessage() {
		return message;
	}
}
