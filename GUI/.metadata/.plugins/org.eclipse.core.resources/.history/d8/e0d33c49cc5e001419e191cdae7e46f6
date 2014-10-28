package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import misc.GameCreationRequest;
import misc.Request;
import player.NewPlayerRequest;
import player.Player;
import cardgames.CardGame;


/**
 * @author 	Richard Phifer<rsphifer@purdue.edu>
 * @date	Oct 11, 2014
 * 
 * Provides an interface for the client application to send and receive communications from the server.
 *
 */
public class ServerAccess {

	private static final String HOST_NAME 	= "sslab08.cs.purdue.edu";
	private static final int	PORT_NUMBER = 4000;
	
	private static Object sendRequestWithResponse(Request request) {
		try {
			/* Try to connect to server */
			Socket serverSocket = new Socket(HOST_NAME, PORT_NUMBER);
			
			/* Open output stream and write request to server */
			ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream());
			oos.writeObject(request);
			oos.flush();
			
			
			/* Open input stream and read server response*/
			ObjectInputStream ois = new ObjectInputStream(serverSocket.getInputStream());
			Object response = ois.readObject();
			oos.close();
			ois.close();
			
			serverSocket.close();
			return response;
			
		} catch (Exception e) {
			System.err.println("In send request");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean updateCardGameState(CardGame game) {
		Request request = new Request("updateCardGameState", game);
		return sendRequestWithResponse(request) != null;
	}
	
	public static boolean createNewGame(Player player, CardGame game) {
		Request request = new Request("createGame", new GameCreationRequest(player, game));
		return sendRequestWithResponse(request) != null;
	}
	
	public static boolean createNewAccount(NewPlayerRequest player) {
		Request request = new Request("createAccount", player);
		return (boolean)sendRequestWithResponse(request);
	}
	
	public static Player loginRequest(NewPlayerRequest player) {
		Request request = new Request("login", player);
		Player tmp = (Player)sendRequestWithResponse(request);
		return tmp;
	}
	
	public static boolean logoutRequest(Player player) {
		Request request = new Request("logout", player);
		return sendRequestWithResponse(request) != null;
	}
	
	public static boolean retrievePassword(NewPlayerRequest player) {
		Request request = new Request("retrievePassword", player);
		return sendRequestWithResponse(request) != null;
	}
}
