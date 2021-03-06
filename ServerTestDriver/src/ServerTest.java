import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class ServerTest {

	public static final String host = "data.cs.purdue.edu";
	public static final int port = 4000;
	
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.printf("Type your name: ");
		String tmp = s.nextLine();
		Object obj = null;
		try {
			Socket socket = new Socket(host, port);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(tmp);
			oos.flush();
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			obj = ois.readObject();
			oos.close();
			ois.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (obj != null) {
			System.out.println((String)obj);
		}
	}
}
