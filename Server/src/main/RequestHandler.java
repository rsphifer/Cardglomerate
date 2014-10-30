package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mail.EmailNewAccount;
import mail.EmailPassword;
import misc.ChatEntry;
import misc.Request;
import misc.UpdateGameRequest;
import player.NewFriendRequest;
import player.NewPlayerRequest;
import player.Player;
import player.SetPasswordRequest;
import cardgames.CardGame;

public class RequestHandler implements Runnable {

	private Socket clientSocket;
	private GameTable gameTable;
	private MenuChat menuChat;

	public RequestHandler(Socket clientSocket, GameTable gameTable, MenuChat menuChat) {
		this.clientSocket = clientSocket;
		this.gameTable = gameTable;
		this.menuChat = menuChat;
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
