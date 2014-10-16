package model;

import controller.ServerAccess;
import player.NewPlayerRequest;
import player.Player;
import cardgames.CardGame;


/**
 * 
 * Maintains Player and CardGame states for client application.
 * 
 */
public class Model {

	private Player player;
	private CardGame currentGame;
	
	public Model() {
		//Other things that we cant think of
	}
	
	public boolean attemptLogin(String username, String password) {
		NewPlayerRequest loginRequest = new NewPlayerRequest(username, password, null);
		return ServerAccess.loginRequest(loginRequest);
	}

	public CardGame getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(CardGame currentGame) {
		this.currentGame = currentGame;
	}

	public Player getPlayer() {
		return player;
	}
	
	
}
