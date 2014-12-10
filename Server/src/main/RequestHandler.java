package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mail.EmailNewAccount;
import mail.EmailPassword;
import misc.ChatEntry;
import misc.GameLobby;
import misc.GameLobbyRequest;
import misc.Request;
import misc.UpdateGameRequest;
import player.AchievementRequest;
import player.NewFriendRequest;
import player.NewPlayerRequest;
import player.Player;
import player.SetPasswordRequest;
import cardgames.BlackJack;
import cardgames.CardGame;
import cardgames.CardGameType;
import cardgames.ERS;
import cardgames.TexasHoldEm;
import cardgames.War;

public class RequestHandler implements Runnable {

	private Socket clientSocket;
	private GameTable gameTable;
	private MenuChat menuChat;
	private GameLobby[] warLobbies;
	private GameLobby[] texasHoldemLobbies;
	private GameLobby[] ratscrewLobbies;
	private int[] blackJackTables;

	public RequestHandler(Socket clientSocket, GameTable gameTable, MenuChat menuChat, GameLobby[] warLobbies, GameLobby[] texasHoldemLobbies, int[] blackJackTables, GameLobby[] ratscrewLobbies) {
		this.clientSocket = clientSocket;
		this.gameTable = gameTable;
		this.menuChat = menuChat;
		this.warLobbies = warLobbies;
		this.texasHoldemLobbies = texasHoldemLobbies;
		this.blackJackTables = blackJackTables;
		this.ratscrewLobbies = ratscrewLobbies;
	}

	@Override
	public void run() {
		
		Object obj = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(
					clientSocket.getInputStream());
			Request request = (Request)ois.readObject();
			if (request != null) {
				String action = request.getAction();
				
				boolean DEFAULT_RETURN = true;
				obj = DEFAULT_RETURN;
				
				
				if (action.equals("getCardGame")) {
					
					System.out.println("get card game request");
					int gameId = (int)request.getObject();
					obj = gameTable.getGame(gameId);
					
				} else if (action.equals("updateCardGame")) {
					
					System.out.println("update card game request");
					UpdateGameRequest ugr = (UpdateGameRequest)request.getObject();
					gameTable.updateGame(ugr.getGameId(), ugr.getCardGame());
					obj = true;
					
				} else if (action.equals("createGame")) {
					
					System.out.println("create new game request");
					CardGame game = (CardGame)request.getObject();
					obj = gameTable.addNewGame(game);
					
				} else if(action.equals("getMenuChat")) {
					
					System.out.println("get menu chat request");
					obj = menuChat.getChatLog();
					
				} else if(action.equals("updateMenuChat")) {
					
					System.out.println("update menu chat request");
					ChatEntry newEntry = (ChatEntry)request.getObject();
					menuChat.addChatEntry(newEntry);
					
				} else if (action.equals("getGameLobbyList")) {
					System.out.println("get game lobby list request");
					CardGameType game = ((GameLobbyRequest)request.getObject()).getCardGameType();
					if (game == CardGameType.War) {
						obj = warLobbies;
					} else if (game == CardGameType.TexasHoldEm) {
						obj = texasHoldemLobbies;
					} else if (game == CardGameType.ERS) {
						obj = ratscrewLobbies;
					}
				} else if (action.equals("joinGameLobby")) {
					System.out.println("join game lobby request");
					GameLobbyRequest glr = (GameLobbyRequest)request.getObject();
					CardGameType gameType = glr.getCardGameType();
					if (gameType == CardGameType.War) {
						obj = warLobbies[glr.getLobbyId()].addPlayer(glr.getPlayer());
					} else if(gameType == CardGameType.TexasHoldEm) {
						obj = texasHoldemLobbies[glr.getLobbyId()].addPlayer(glr.getPlayer());
					} else if (gameType == CardGameType.ERS) {
						obj = ratscrewLobbies[glr.getLobbyId()].addPlayer(glr.getPlayer());
					}
					
					
				} else if (action.equals("leaveGameLobby")) {
					System.out.println("leave game lobby request");
					GameLobbyRequest glr = (GameLobbyRequest)request.getObject();
					
					CardGameType gameType = glr.getCardGameType();
					if (gameType == CardGameType.War) {
						warLobbies[glr.getLobbyId()].removePlayer(glr.getPlayer());
					} else if (gameType == CardGameType.TexasHoldEm) {
						texasHoldemLobbies[glr.getLobbyId()].removePlayer(glr.getPlayer());
					} else if (gameType == CardGameType.ERS) {
						ratscrewLobbies[glr.getLobbyId()].removePlayer(glr.getPlayer());
					}
					obj = true;
					
				} else if (action.equals("startGameFromLobby")) { 
					System.out.println("start game from lobby request");
					GameLobbyRequest glr = (GameLobbyRequest)request.getObject();
					int lobbyId = glr.getLobbyId();
					
					CardGameType gameType = glr.getCardGameType();
					if (gameType == CardGameType.War) {
						int tmpGameId = gameTable.addNewGame(new War(warLobbies[lobbyId].getPlayers()));
						if (tmpGameId > -1) {
							/* Game created */
							warLobbies[lobbyId].setGameId(tmpGameId);
							warLobbies[lobbyId].isStarted = true;
							obj = true;
						} else {
							/* Game creation failed */
							obj = false;
						}
					} else if (gameType == CardGameType.TexasHoldEm) {
						TexasHoldEm tmpGame = new TexasHoldEm(texasHoldemLobbies[lobbyId].getPlayers());
						//tmpGame.setup();
						int tmpGameId = gameTable.addNewGame(tmpGame);
						if (tmpGameId > -1) {
							
							texasHoldemLobbies[lobbyId].setGameId(tmpGameId);
							texasHoldemLobbies[lobbyId].isStarted = true;
							obj = true;
						} else {
							obj = false;
						}
					} else if (gameType == CardGameType.ERS) {
						ERS tmpGame = new ERS(ratscrewLobbies[lobbyId].getPlayers());
						int tmpGameId = gameTable.addNewGame(tmpGame);
						if (tmpGameId > -1) {
							ratscrewLobbies[lobbyId].setGameId(tmpGameId);
							ratscrewLobbies[lobbyId].isStarted = true;
							obj = true;
						} else {
							obj = false;
						}
					}
					
				} else if (action.equals("joinPersistantTable")) {
					System.out.println("join Persistant Table request");
					GameLobbyRequest glr = (GameLobbyRequest)request.getObject();
					
					CardGameType gameType = glr.getCardGameType();
					if (gameType == CardGameType.Blackjack) {
						int id = blackJackTables[glr.getLobbyId()];
						((BlackJack)(gameTable.getGame(id))).addPlayer(glr.getPlayer());
						obj = blackJackTables[glr.getLobbyId()];
					}
				} else if (action.equals("getBlackJackTables")) {
					System.out.println("Get Blackjack tables request");
					
					BlackJack[] games = new BlackJack[blackJackTables.length];
					for (int i=0; i<blackJackTables.length; i++) {
						games[i] = (BlackJack)gameTable.getGame(blackJackTables[i]);
					}
					obj = games;
				} else if (action.equals("incrementWarCounter")) {
					System.out.println("increment war counter request");
					UpdateGameRequest ugr = (UpdateGameRequest)request.getObject();
					int id = ugr.getGameId();
					War game = (War)gameTable.getGame(id);
					game.incrementClickCounter();
					
					gameTable.updateGame(id, game);
					obj = true;
				} else if (action.equals("incrementERSCounter")) {
					System.out.println("increment ERS counter request");
					UpdateGameRequest ugr = (UpdateGameRequest)request.getObject();
					int id = ugr.getGameId();
					ERS game = (ERS)gameTable.getGame(id);
					game.incrementClickCounter();
					
					gameTable.updateGame(id, game);
					obj = true;					
				} else if (action.equals("login")) { /* Returns null if invalid..Player object if valid */
				
				
				
					System.out.println("login request");
					NewPlayerRequest npr = (NewPlayerRequest)request.getObject();
					
					/* Check credentials in db */
					int playerId = DatabaseAccess.logIn(npr.getUserName(), npr.getPassword());
					
					/* DB layer will return player id..use id to get player and fill in information */
					
					Player tmp = null;
					if (playerId != 0) { /* Good login..use id to get player info to build player object to return */
						System.out.println("success!");
						tmp = DatabaseAccess.playerInfo(playerId);
						((Player)tmp).setPlayerId(playerId);
					} else { /* Bad login..return null player */
						tmp = null;
						System.out.println("fail!");
					}
					obj = tmp;

					
				} else if(action.equals("getLeaderboard")) {
					System.out.println("get leader board request");
					String criteria = (String)request.getObject();
					obj = DatabaseAccess.getLeaderBoard(criteria);
					
				} else if(action.equals("getWinRatio")) {
					System.out.println("get win ratio request");
					AchievementRequest ar = (AchievementRequest)request.getObject();
					
					if (ar.getGameType() == CardGameType.War) {
						obj = AchievementAccess.getWinRat(ar.getPlayer().getPlayerId(), "war");
					} else if (ar.getGameType() == CardGameType.TexasHoldEm) {
						obj = AchievementAccess.getWinRat(ar.getPlayer().getPlayerId(), "holdem");
					} else if (ar.getGameType() == CardGameType.Blackjack) {
						obj = AchievementAccess.getWinRat(ar.getPlayer().getPlayerId(), "blackjack");
					} else {
					
						obj = 0;
					}
					
				} else if (action.equals("getNumGames")) {
					System.out.println("get number of games request");
					AchievementRequest ar = (AchievementRequest)request.getObject();
					if (ar.getGameType() == CardGameType.War) {
						obj = AchievementAccess.getGames(ar.getPlayer().getPlayerId(), "war");
					} else if (ar.getGameType() == CardGameType.TexasHoldEm) {
						obj = AchievementAccess.getGames(ar.getPlayer().getPlayerId(), "holdem");
					} else if (ar.getGameType() == CardGameType.Blackjack) {
						obj = AchievementAccess.getGames(ar.getPlayer().getPlayerId(), "blackjack");
					} else {
						obj = 0;
					}
				} else if (action.equals("getNumWins")) {
					System.out.println("get number of wins request");
					AchievementRequest ar = (AchievementRequest)request.getObject();
					if (ar.getGameType() == CardGameType.War) {
						obj = AchievementAccess.getWins(ar.getPlayer().getPlayerId(), "war");
					} else if (ar.getGameType() == CardGameType.TexasHoldEm) {
						obj = AchievementAccess.getWins(ar.getPlayer().getPlayerId(), "holdem");
					} else if (ar.getGameType() == CardGameType.Blackjack) {	
						obj = AchievementAccess.getWins(ar.getPlayer().getPlayerId(), "blackjack");
					} else {
						obj = 0;
					}
				} else if (action.equals("incrementNumGames")) {
					System.out.println("increment number of games request");
					AchievementRequest ar = (AchievementRequest)request.getObject();
					if (ar.getGameType() == CardGameType.War) {
						AchievementAccess.addGame(ar.getPlayer().getPlayerId(), "war");
					} else if (ar.getGameType() == CardGameType.TexasHoldEm) {
						AchievementAccess.addGame(ar.getPlayer().getPlayerId(), "holdem");
					} else if (ar.getGameType() == CardGameType.Blackjack) {
						AchievementAccess.addGame(ar.getPlayer().getPlayerId(), "blackjack");
					} 
					obj = true;
				} else if (action.equals("incrementNumWins")) {
					System.out.println("increment number of wins request");
					AchievementRequest ar = (AchievementRequest)request.getObject();
					if (ar.getGameType() == CardGameType.War) {
						AchievementAccess.addWin(ar.getPlayer().getPlayerId(), "war");
					} else if (ar.getGameType() == CardGameType.TexasHoldEm) {
						AchievementAccess.addWin(ar.getPlayer().getPlayerId(), "holdem");
					} else if (ar.getGameType() == CardGameType.Blackjack) {
						AchievementAccess.addWin(ar.getPlayer().getPlayerId(), "blackjack");
					} 
					obj = true;
				} else if (action.equals("logout")) {
				
				
					System.out.println("logout request");
					Player player = (Player)request.getObject();
					obj = DatabaseAccess.logOut(player.getPlayerId());
					
				} else if (action.equals("getFriends")) {
					System.out.println("get friends request");
					Player player = (Player)request.getObject();
					
					/* Ask db for list of friends*/
					obj = DatabaseAccess.getFriends(player.getPlayerId());
					
				} else if (action.equals("addFriend")) {
					NewFriendRequest nfr = (NewFriendRequest)request.getObject();
					
					/* Tell db to add username to list of friends */
					obj = DatabaseAccess.addFriend(nfr.getPlayer().getPlayerId(), nfr.getNameOfPlayerToAdd());
					
				} else if (action.equals("removeFriend")) {
					NewFriendRequest nfr = (NewFriendRequest)request.getObject();
					
					/* Tell db to remove username from list of friends */
					obj = DatabaseAccess.removeFriend(nfr.getPlayer().getPlayerId(), nfr.getNameOfPlayerToAdd());
					
				} else if (action.equals("createAccount")) {
							
					System.out.println("create account request");
					NewPlayerRequest npr = (NewPlayerRequest)request.getObject();
					boolean isSuccessful = DatabaseAccess.createAccount(npr.getUserName(), npr.getPassword(), npr.getEmailAddress());
					if (isSuccessful) {
						/* Send new account email */
						EmailNewAccount ena = new EmailNewAccount(npr.getEmailAddress(), npr.getUserName());
						Thread t = new Thread(ena);
						t.start();
						
					}
					obj = isSuccessful;
					
					
				} else if (action.equals("retrievePassword")) {
					NewPlayerRequest npr= (NewPlayerRequest)request.getObject();
					System.out.printf("retrieve password request, email: %s\n", npr.getEmailAddress());
					
					/* Check db for email..if found send password */
					boolean isSuccess;
					String randpass = DatabaseAccess.retrievePassword(npr.getUserName(), npr.getEmailAddress());
					if (randpass.equals("")) {
						/* creds not found..return false */
						isSuccess = false;
						obj = isSuccess;
					} else {
						/* valid email/username combo..send email */
						isSuccess = true;
						EmailPassword ep = new EmailPassword(npr.getEmailAddress(), randpass);
						Thread t = new Thread(ep);
						t.start();
					}
					
					obj = isSuccess;
				} else if (action.equals("resetPassword")) {
					SetPasswordRequest spr = (SetPasswordRequest)request.getObject();
					boolean isSuccess = DatabaseAccess.resetPassword(spr.getOldPass(), spr.getNewPass(), spr.getPlayer().getPlayerId());
					obj = isSuccess;
				}
				
				
				
				
				//boolean PLACE_HOLDER = true;
				
				ObjectOutputStream oos = new ObjectOutputStream(
						clientSocket.getOutputStream());
				oos.writeObject(obj);
				oos.flush();
				oos.close();
				ois.close();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return;
	}

}
