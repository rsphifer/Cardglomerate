package model;

import java.util.LinkedList;

import player.NewPlayerRequest;
import player.Player;
import cardgames.CardGame;
import cardgames.War;
import cards.Card;
import controller.ServerAccess;


/**
 * 
 * Maintains Player and CardGame states for client application.
 * 
 */
public class Model {

	private Player player;
	private War currentGame;
	
	public Model() {
		player = null;
		//Other things that we cant think of
	}
	
	public boolean createGameRequest(CardGame game) {
		currentGame = new War(player);
		return true;
		//return ServerAccess.createNewGame(player, game);
	}
	
	public void updateGame() {
		currentGame.update();
	}
	
	public boolean getGameOver() {
		return currentGame.getGameover();
	}
	
	public LinkedList<Card> getCardsToDisplay() {
		return currentGame.getCardsToDisplay();
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
	
	public boolean requestPassword(String username, String emailAddress) {
		return ServerAccess.retrievePassword(new NewPlayerRequest(username, null, emailAddress));
	}

	public CardGame getCurrentGame() {
		return currentGame;
	}

//	public void setCurrentGame(CardGame currentGame) {
//		this.currentGame = currentGame;
//	}

	public Player getPlayer() {
		return player;
	}
	
	
}
