package controller;

import javax.swing.JOptionPane;

import misc.ChatEntry;
import model.Model;

public class UpdateChecker implements Runnable {
	private final int MILLIS_BETWEEN_UPDATES = 5000;
	
	private Model model;
	public UpdateChecker(Model model) {
		this.model = model;
	}
	
	@Override
	public void run() {
		while (model.isLoggedIn) {
			
			/* Sleep between server calls to minimize busy waiting */
			try {
				Thread.sleep(MILLIS_BETWEEN_UPDATES);
			} catch (InterruptedException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "An error with the server has occurred.", null, JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			
			/* Update model with newest game, chat, and friend states */
			System.out.println("Ask server for updates.");
			
			/* Update friends list */
			model.setFriendList(ServerAccess.getFriends(model.getPlayer()));
			
			/* Update menu chat */
			model.setMenuChat(ServerAccess.getMenuChat());
			
//			for (ChatEntry c : model.getMenuChat()) {
//				System.out.printf("%s: %s\n", c.getUsername(), c.getMessage());
//			}
			
		}
		
		return;
		
	}

}
