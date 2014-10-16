package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import player.NewPlayerRequest;
import player.Player;
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
					
				} else if (action.equals("login")) {
					System.out.println("login request");
					NewPlayerRequest npr = (NewPlayerRequest)request.getObject();
					
					/* Check credentials in db */
					
					if (true) { /* Valid login */
						Player tmp = new Player(npr.getUserName(), npr.getPassword());
						obj = tmp;
					} else {
						/* Invalid login */
						obj = null;
					}
					
				} else if (action.equals("logout")) {
					System.out.println("logout request");
					
				} else if (action.equals("createAccount")) {
					System.out.println("create account request");
					
				} else if (action.equals("retrievePassword")) {
					System.out.println("retrieve password request");

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

	}

}
