package main;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import misc.GameLobby;


public class Server {
	
	public static final int MAX_LOBBIES = 4;
	
	public static final String USAGE = "Usage: java Server portNumber";
	
	private int port;
	private GameTable gameTable;
	private MenuChat menuChat;
	
	private GameLobby[] warLobbies;
	
	public Server(int port) {
		this.port = port;
		gameTable = new GameTable();
		menuChat = new MenuChat();
		
		warLobbies = new GameLobby[MAX_LOBBIES];
		
		int ind = 0;
		for (GameLobby gl : warLobbies) {
			gl = new GameLobby(2, 2, ind++);
		}
	}
	
	public void start() {
		/* Server spawns a thread for every server request. Active threads are in this list. */
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while(System.in.available() == 0) {
				
				Socket clientSocket = serverSocket.accept();
				
				RequestHandler requestHandler = new RequestHandler(clientSocket, gameTable, menuChat, warLobbies);
				Thread t = new Thread(requestHandler);
				t.start();
			}
			serverSocket.close();
			
		} catch (IOException ioe) {
			
		} catch (Exception e) {
			
		}
	}

	/* To run server: java Server portNumber */
	public static void main(String[] args) {
		
		if (args.length != 1) { /* Check cmd line inputs */
			System.err.printf("%s\n", USAGE);
			System.exit(1);
		}
		
		int port = 0;
		try { /* Make sure arg passed is a number */
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.err.printf("%s\n", USAGE);
			System.exit(1);
		}
		
		Server server = new Server(port);
		server.start();
	}
}
