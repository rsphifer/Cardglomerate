import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

	Socket clientSocket;

	public RequestHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		System.out.println("Connection received!");
		try {
			ObjectInputStream ois = new ObjectInputStream(
					clientSocket.getInputStream());
			Object obj = ois.readObject();
			if (obj != null) {
				StringBuilder sb = new StringBuilder();
				sb.append("Hello ");
				sb.append((String)obj);
				sb.append(", from server land!");
				
				
				
				ObjectOutputStream oos = new ObjectOutputStream(
						clientSocket.getOutputStream());
				oos.writeObject(sb.toString());
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
