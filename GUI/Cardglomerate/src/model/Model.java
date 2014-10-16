package model;

import misc.GameCreationRequest;
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
		player = null;
		//Other things that we cant think of
	}
	
	public boolean createGameRequest(CardGame game) {
		return ServerAccess.createNewGame(player, game);
	}
	
	public boolean createAccountRequest(String username, String password, String email) {
		NewPlayerRequest accountRequest = new NewPlayerRequest(username, password, email);
		return ServerAccess.createNewAccount(accountRequest);
	}
	
	public boolean attemptLogin(String username, String password) {
		NewPlayerRequest loginRequest = new NewPlayerRequest(username, password, null);
		player = ServerAccess.loginRequest(loginRequest);
		if (player != null) {
			System.out.printf("%s\n", player.userName);
			return true;
		}
		return false;
	}
	
	public boolean requestPassword(String emailAddress) {
		return ServerAccess.retrievePassword(emailAddress);
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
