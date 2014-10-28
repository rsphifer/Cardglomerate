package model;

import java.util.LinkedList;

import player.NewPlayerRequest;
import player.Player;
import player.SetPasswordRequest;
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
		
		/* Initialize thread that will periodically ask server for updates to update model */
		UpdateChecker updateChecker = new UpdateChecker(this);
		Thread t = new Thread(updateChecker);
		t.start();
	}
	
	public boolean createGameRequest() {
		currentGame = new War(player);
		currentGame.testSetup();
		return true;
		//return ServerAccess.createNewGame(player, game);
	}
	
	public String getGameWinner() {
		return currentGame.getWinner().userName;
	}
	
	public LinkedList<Integer> getHandSizes() {
		return currentGame.getHandSizes();
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
	
	public boolean setNewPassword(String oldPassword, String newPassword) {
		return ServerAccess.setPassword(new SetPasswordRequest(player, oldPassword, newPassword));
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
