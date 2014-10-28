package model;

import javax.swing.JOptionPane;

public class UpdateChecker implements Runnable {
	private final int MILLIS_BETWEEN_UPDATES = 5000;
	
	private Model model;
	public UpdateChecker(Model model) {
		this.model = model;
	}
	
	@Override
	public void run() {
		while (true) {
			
			/* Sleep between server calls to minimize busy waiting */
			try {
				Thread.sleep(MILLIS_BETWEEN_UPDATES);
			} catch (InterruptedException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "An error with the server has occurred.", null, JOptionPane.ERROR_MESSAGE);
			}
			
			/* Update model with newest game, chat, and friend states */
			System.out.println("Ask server for updates.");
		}
		
	}

}
