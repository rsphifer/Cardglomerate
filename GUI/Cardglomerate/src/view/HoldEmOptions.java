package view;

import java.util.ArrayList;

import misc.GameLobby;
import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import player.Friend;
import player.Player;
import cardgames.CardGameType;
import cardgames.TexasHoldEm;

public class HoldEmOptions extends BasicGameState {

	private Model model;

	private String mouse = "No input yet";
	private Image background;
	private Image image;
	private Image arrow;
	private Image table;

	// table players
	// private String t1p1 = "Open";private String t1p2 = "Open";private String
	// t1p3 = "Open";private String t1p4 = "Open";
	// private String t2p1 = "Open";private String t2p2 = "Open";private String
	// t2p3 = "Open";private String t2p4 = "Open";
	// private String t3p1 = "Open";private String t3p2 = "Open";private String
	// t3p3 = "Open";private String t3p4 = "Open";

	private Rectangle leaveLobbyButton = new Rectangle(180, 680, 70, 30);

	// friends list things
	private int curx;
	private int cury;
	ArrayList<Friend> friends;

	public HoldEmOptions(int state, Model model) {
		this.model = model;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);

		// initialize and scale images
		image = new Image("res/Just Cards.jpg");
		image = image.getScaledCopy(350, 280);

		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);

		table = new Image("res/Table.jpg");

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// render the background
		background.draw(0, 0);

		// mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);

		// render message and image
		g.drawString("Texas Hold'em!", 600, 10);
		g.drawImage(image, 490, 50);

		// render arrow
		g.drawImage(arrow, 0, 570);

		// render tables
		g.drawString("Click on a table to join it", 550, 340);

		GameLobby[] holdemLobbies = model
				.getGameLobby(CardGameType.TexasHoldEm);

		for (int i = 0; i < holdemLobbies.length; i++) {
			int x = 370 + i * 300;
			for (int j = 0; j < 4; j++) {
				if (holdemLobbies[i].getPlayers().size() > j) {
					/* Player is present at j'th index */
					Player curr = holdemLobbies[i].getPlayers().get(j);
					if (curr.userName
							.equals(holdemLobbies[i].getHost().userName)) {
						g.drawString(curr.userName + "(Host)", x, 560 + 30 * j);
					} else {
						g.drawString(curr.userName, x, 560 + 30 * j);
					}
				} else {
					g.drawString("Open", x, 560 + 30 * j);
				}
			}

			//
			// if (model.isInLobby && model.getCurrentLobbyNumber() == i) {
			// if (model.getPlayer().userName
			// .equals(lobbies[i].getHost().userName)) {
			// gameStartButton.setX(x - 5);
			// gameStartButton.setY(620);
			// g.draw(gameStartButton);
			// g.drawString("Start", x + 7, 625);
			// }
			// g.drawString("Leave", x + 7, 585);
			// } else {
			// g.drawString("Join", x + 10, 585);
			// }
		}

		if (model.isInLobby) {
			/* Draw leave button and start button if host */
			int x = 355 + model.getCurrentLobbyNumber() * 300;
			leaveLobbyButton.setX(x);
			g.draw(leaveLobbyButton);
			g.drawString("Leave", x + 9, leaveLobbyButton.getY() + 5);

			if (model.getPlayer().userName.equals(holdemLobbies[model
					.getCurrentLobbyNumber()].getHost().userName)) {
				// render start button
				g.drawString("Start Game", 185, 665);
				g.drawRect(160, 650, 150, 50);
			}
		}

		g.drawImage(table, 300, 400);
		g.drawString("Table 1", 370, 380);

		g.drawImage(table, 600, 400);
		g.drawString("Table 2", 670, 380);

		g.drawImage(table, 900, 400);
		g.drawString("Table 3", 970, 380);

		// friends list rendering
		g.drawString("Friends List", 1000, 10);
		curx = 930;
		cury = 30;

		if (model.isLoggedIn) {
			friends = model.getFriendsList();
			for (int i = 0; i < friends.size(); i++) {
				Friend tmp = friends.get(i);
				g.drawString(tmp.getUsername(), curx, cury);
				Color currColor = g.getColor();
				if (tmp.isOnline) {
					g.setColor(Color.blue);
				} else {
					g.setColor(Color.red);
				}
				g.fill(new Circle(curx - 7, cury + 10, 5));
				g.setColor(currColor);
				cury += 20;
			}
		}

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;

		int actualYPos = gc.getHeight() - ypos;
		Rectangle tmp = new Rectangle(xpos, actualYPos, 1, 1);
		
		/* Check if game has been started */
		if (model.isInLobby && model.getGameLobby(CardGameType.TexasHoldEm)[model.getCurrentLobbyNumber()].isStarted) {
			if(model.enterGameFromLobby(model.getGameLobby(CardGameType.TexasHoldEm)[model.getCurrentLobbyNumber()].getGameId())) {
				sbg.enterState(8);
			}
		}

		/* Check if leave button clicked */
		if (model.isInLobby
				&& (tmp.intersects(leaveLobbyButton) || leaveLobbyButton
						.includes(xpos, actualYPos))) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;

				model.leaveGameLobby(CardGameType.TexasHoldEm,
						model.getCurrentLobbyNumber());
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		// back button clicked
		if ((xpos > 0 && xpos < 150) && (ypos > 0 && ypos < 150)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;

				if (model.isInLobby) {
					model.leaveGameLobby(CardGameType.TexasHoldEm,
							model.getCurrentLobbyNumber());
				}
				sbg.enterState(1); // display game menu screen
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		// table 1 Clicked
		if ((xpos > 300 && xpos < 500) && (ypos > 170 && ypos < 320)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				// code to add user to table
				if (!model.isInLobby) {
					model.joinGameLobby(CardGameType.TexasHoldEm, 0);
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		// table 2 Clicked
		if ((xpos > 600 && xpos < 800) && (ypos > 170 && ypos < 320)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				// code to add user to table

				if (!model.isInLobby) {
					model.joinGameLobby(CardGameType.TexasHoldEm, 1);
				}

			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		// table 3 Clicked
		if ((xpos > 900 && xpos < 1100) && (ypos > 170 && ypos < 320)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				// code to add user to table

				if (!model.isInLobby) {
					model.joinGameLobby(CardGameType.TexasHoldEm, 2);
				}

			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		// start Clicked
		if ((xpos > 160 && xpos < 310) && (ypos > 20 && ypos < 70)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				// code to start the table the current user is at
				GameLobby[] lobbies = model
						.getGameLobby(CardGameType.TexasHoldEm);
				
				/* Check that player is the host and that the minimum number of players are in the lobby */
				if (model.isInLobby
						&& model.getPlayer().userName.equals(lobbies[model
								.getCurrentLobbyNumber()].getHost().userName)
						&& lobbies[model.getCurrentLobbyNumber()].getPlayers()
								.size() >= 2) {
					model.startGameFromLobby();
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

	}

	public int getID() {
		return 7;
	}
}
