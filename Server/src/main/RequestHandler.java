package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import player.NewPlayerRequest;
import player.Player;
import player.SetPasswordRequest;
import mail.EmailNewAccount;
import mail.EmailPassword;
import misc.Request;

public class RequestHandler implements Runnable {

	Socket clientSocket;

	public RequestHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
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
				
				boolean PLACE_HOLDER = true;
				obj = PLACE_HOLDER;
				
				
				if (action.equals("updateCardGameState")) {
					System.out.println("update card game state request");
					
				} else if (action.equals("createGame")) {
					System.out.println("create new game request");
					
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
					} else { /* Bad login..return null player */
						tmp = null;
						System.out.println("fail!");
					}
					obj = tmp;

					
				} else if (action.equals("logout")) {
					System.out.println("logout request");
					
				} else if (action.equals("getFriends")) {
					System.out.println("get friends request");
					Player player = (Player)request.getObject();
					
					/* Ask db for list of friends*/
					
				} else if (action.equals("addFriend")) {
					
				} else if (action.equals("removeFriend")){
					
				} else if (action.equals("createAccount")) {
							
					System.out.println("create account request");
					NewPlayerRequest npr = (NewPlayerRequest)request.getObject();
					boolean isSuccessful = DatabaseAccess.createAccount(npr.getUserName(), npr.getPassword(), npr.getEmailAddress());
					if (isSuccessful) { /* Send new account email */
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
