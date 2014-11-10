package model;

import java.util.ArrayList;
import java.util.LinkedList;

import misc.ChatEntry;
import misc.GameLobby;
import misc.GameLobbyRequest;
import player.Friend;
import player.NewFriendRequest;
import player.NewPlayerRequest;
import player.Player;
import player.SetPasswordRequest;
//import cardgames.CardGame;
//import cardgames.War;
import cardgames.CardGame;
import cardgames.CardGameType;
import cardgames.War;
import cards.Card;
import controller.ServerAccess;
import controller.UpdateChecker;

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
	
	private ArrayList<ChatEntry> menuChat;
	
	private GameLobby[] warLobbies;
	
	public boolean isInLobby;
	private CardGameType currentLobbyType;
	private int currentLobbyNumber;
	
	public boolean isLoggedIn;

	public Model() {
		friendList = new ArrayList<Friend>();
		menuChat = new ArrayList<ChatEntry>();
		warLobbies = new GameLobby[4];
		
		isInLobby = false;
		currentLobbyType = null;
		currentLobbyNumber = -1;
		
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
		return currentGame.getWinner().get(0).userName;
	}

	public LinkedList<Integer> getHandSizes() {
		return currentGame.getHandSizes();
	}

	public void updateGame() {
		if(currentGame.updateReady()){
			currentGame.update();
		}
	}

	public boolean getGameOver() {
		return currentGame.getGameover();
	}

	public LinkedList<Card> getCardsToDisplay() {
		return currentGame.getCardsToDisplay();
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
			friendList = ServerAccess.getFriends(player);
			menuChat = ServerAccess.getMenuChat();
			
			warLobbies = ServerAccess.getGameLobbyList(new GameLobbyRequest(null, 0, CardGameType.War));
			
			/*
			 * Initialize thread that will periodically ask server for updates
			 * to update model
			 */
			UpdateChecker updateChecker = new UpdateChecker(this);
			Thread t = new Thread(updateChecker);
			t.start();

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
	
	/*********************************************************
	*	Game lobby mgmt model calls.
	*********************************************************/
	public void joinGameLobby(CardGameType gameType, int lobbyId) {
		GameLobbyRequest glr = new GameLobbyRequest(player, lobbyId, gameType);
		isInLobby = true;
		setCurrentLobby(gameType, lobbyId);
		ServerAccess.joinLobby(glr);
		if (gameType == CardGameType.War) {
			warLobbies = ServerAccess.getGameLobbyList(glr);
		}
		return;
	}
	
	public void leaveGameLobby(CardGameType gameType, int lobbyId) {
		GameLobbyRequest glr = new GameLobbyRequest(player, lobbyId, gameType);
		isInLobby = false;
		setCurrentLobby(null, -1);
		ServerAccess.leaveLobby(glr);
		
		if (gameType == CardGameType.War) {
			warLobbies = ServerAccess.getGameLobbyList(glr);
		}
		
		return;
	}
	
	public void setGameLobby(CardGameType gameType, GameLobby[] lobbies) {
		if (gameType == CardGameType.War) {
			warLobbies = lobbies;
		} else if (gameType == CardGameType.TexasHoldEm) {
			
		}
	}
	
	public GameLobby[] getGameLobby(CardGameType gameType) {
		if (gameType == CardGameType.War) {
			return warLobbies;
		}
		else {
			return null;
		}
	}
	
	private void setCurrentLobby(CardGameType gameType, int lobbyNumber) {
		currentLobbyNumber = lobbyNumber;
		currentLobbyType = gameType;
	}
	
	public int getCurrentLobbyNumber() {
		return currentLobbyNumber;
	}
	
	
	/*********************************************************
	*	Friend list mgmt model calls.
	*********************************************************/
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
	
	/*********************************************************
	*	Menu chat mgmt model calls.
	*********************************************************/
	public void setMenuChat(ArrayList<ChatEntry> chatLog) {
		menuChat = chatLog;
	}
	
	public ArrayList<ChatEntry> getMenuChat() {
		return menuChat;
	}
	
	public boolean addChatEntry(String message) {
		ChatEntry newEntry = new ChatEntry(player.userName, message);
		boolean isSuccess = ServerAccess.addMenuChatEntry(newEntry);
		if (isSuccess) {
			menuChat = ServerAccess.getMenuChat();
		}
		return isSuccess;
	}
}
