package model;

import controller.ServerAccess;
import player.NewPlayerRequest;
import player.Player;


/**
 * 
 * Called when user tries to log in.
 * 
 *
 */
public class UserLogin {

	private String username;
	private String password;
	
	private boolean isValid;
	
	public UserLogin(String username, String password) {
		this.username = username;
		this.password = password;
		isValid = login();
	}
	
	public boolean login() {
		NewPlayerRequest temp = new NewPlayerRequest(username, password, null);
		return ServerAccess.loginRequest(temp);
		
	}
	
}
