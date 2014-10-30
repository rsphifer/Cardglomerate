package model;

import java.util.ArrayList;
import java.util.LinkedList;

import player.Friend;
import player.NewFriendRequest;
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

	private boolean isInGame;
	private CardGame currentGame;
	private int gameId;

	private ArrayList<Friend> friendList;
	public boolean isLoggedIn;

	public Model() {
		player = null;
		isLoggedIn = false;
		isInGame = false;

	}

	public boolean createGameRequest() {
		currentGame = new War(player);

		gameId = ServerAccess.createNewGame(currentGame);
		if (gameId != -1) {
			isInGame = true;
			currentGame.testSetup();
			return true;
		}
		return false;
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

	public boolean addFriend(String username) {

		boolean isSuccess = ServerAccess.addFriend(new NewFriendRequest(player,
				username));
		if (isSuccess) {
			friendList = ServerAccess.getFriends(player);
		}
		return isSuccess;
	}

	public void removeFriend(String username) {
		boolean isSuccess = ServerAccess.removeFriend(new NewFriendRequest(
				player, username));
		if (isSuccess) {
			friendList = ServerAccess.getFriends(player);
		}
	}

	public void setFriendList(ArrayList<Friend> friends) {
		friendList = friends;
	}

	public ArrayList<Friend> getFriendsList() {
		return friendList;
	}

	public boolean createAccountRequest(String username, String password,
			String email) {
		NewPlayerRequest accountRequest = new NewPlayerRequest(username,
				password, email);
		return ServerAccess.createNewAccount(accountRequest);
	}

	public boolean attemptLogin(String username, String password) {
		NewPlayerRequest loginRequest = new NewPlayerRequest(username,
				password, null);
		player = ServerAccess.loginRequest(loginRequest);
		if (player != null) {

			isLoggedIn = true;

			/*
			 * Initialize thread that will periodically ask server for updates
			 * to update model
			 */
			UpdateChecker updateChecker = new UpdateChecker(this);
			Thread t = new Thread(updateChecker);
			t.start();

			// addFriend("arbiccdheife");

			return true;
		}
		return false;
	}

	public boolean logout() {
		boolean isSuccess = ServerAccess.logoutRequest(player);
		if (isSuccess)
			isLoggedIn = false;
		return isSuccess;
	}

	public boolean requestPassword(String username, String emailAddress) {
		return ServerAccess.retrievePassword(new NewPlayerRequest(username,
				null, emailAddress));
	}

	public boolean setNewPassword(String oldPassword, String newPassword) {
		return ServerAccess.setPassword(new SetPasswordRequest(player,
				oldPassword, newPassword));
	}

	public CardGame getCurrentGame() {
		return currentGame;
	}

	public Player getPlayer() {
		return player;
	}

}
