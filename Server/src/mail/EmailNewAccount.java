package mail;

import java.util.*;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailNewAccount implements Runnable {

	private final String FROM_ADDRESS = "Cardglomerate@yahoo.com";
	
	private String username, toAddress;
	
	public EmailNewAccount(String toAddress, String username) {
		this.username = username;
		this.toAddress = toAddress;
	}
	
	@Override
	public void run() {
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "localhost");
		props.setProperty("mail.password", "307Team!!!");
		
		String message = "Greetings "+ username + "!\n Welcome to Cardglomerate!\n\nSincerely,\nThe Cardglomerate team\n";
		
		Session session = Session.getDefaultInstance(props);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM_ADDRESS));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			msg.setSubject("Cardglomerate Password Recovery");
			msg.setText(message);
			Transport.send(msg);
			System.out.println("Password mail sent!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Password mail failed to send :(");
		}
	}

}
