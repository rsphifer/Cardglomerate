package main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import misc.Request;

public class RequestHandler implements Runnable {

	Socket clientSocket;

	public RequestHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		
		String message = null;
		Object obj = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(
					clientSocket.getInputStream());
			Request request = (Request)ois.readObject();
			if (request != null) {
				String action = request.getAction();
				
				
				if (action.equals("updateCardGameState")) {
					System.out.println("update card game state request");
				} else if (action.equals("login")) {
					System.out.println("login request");
				} else if (action.equals("logout")) {
					System.out.println("logout request");
				} else if (action.equals("createAccount")) {
					System.out.println("create account request");
				} 
				
				
				
				
				
				ObjectOutputStream oos = new ObjectOutputStream(
						clientSocket.getOutputStream());
				oos.flush();
				oos.close();
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