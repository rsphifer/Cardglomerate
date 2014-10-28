package player;

import java.io.Serializable;

public class NewFriendRequest implements Serializable {

	private Player player;
	private String usernameOfPlayerToAdd;
	
	public NewFriendRequest(Player player, String username) {
		this.player = player;
		usernameOfPlayerToAdd = username;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getUsernameOfFriend() {
		return usernameOfPlayerToAdd;
	}
	
}
