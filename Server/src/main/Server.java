package main;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	public static final String USAGE = "Usage: java Server portNumber";

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
		
		/* Server spawns a thread for every server request. Active threads are in this list. */
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while(System.in.available() == 0) {
				
				Socket clientSocket = serverSocket.accept();
				
				RequestHandler requestHandler = new RequestHandler(clientSocket);
				Thread t = new Thread(requestHandler);
				t.start();
			}
			serverSocket.close();
			
		} catch (IOException ioe) {
			
		} catch (Exception e) {
			
		}
		
	}
}