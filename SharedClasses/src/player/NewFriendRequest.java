package player;

import java.io.Serializable;

public class NewFriendRequest implements Serializable {

	private Player player;
	private String nameOfPlayerToAdd;
	
	public NewFriendRequest(Player player, String username) {
		this.player = player;
		nameOfPlayerToAdd = username;
	}

	public Player getPlayer() {
		return player;
	}

	public String getNameOfPlayerToAdd() {
		return nameOfPlayerToAdd;
	}
	
	
}
