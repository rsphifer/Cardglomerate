package view;

import misc.GameLobby;
import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cardgames.CardGameType;

public class WarOptions extends BasicGameState {

	private Model model;

	private String mouse = "No input yet";
	private Image background;
	private Image image;
	private Image arrow;
	
	private Rectangle lobbyOneJoin = new Rectangle(180, 580, 70,30);
	private Rectangle lobbyTwoJoin = new Rectangle(500, 580, 70,30);
	private Rectangle lobbyThreeJoin = new Rectangle(820, 580, 70,30);
	private Rectangle lobbyFourJoin = new Rectangle(1140, 580, 70,30);

	private boolean lobbyJoined = false;
	private int lobbyNumberJoined = 0;

	public WarOptions(int state, Model model) {
		this.model = model;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);

		// initialize and scale images
		image = new Image("res/Many Cards.jpg");
		image = image.getScaledCopy(386, 258);

		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// render the background
		background.draw(0, 0);

		// mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);

		// render message and image
		g.drawString("WAR!", 600, 10);
		g.drawImage(image, 447, 50);

		// render play button
		g.drawRect(535, 350, 200, 100);
		g.drawString("Play Against Ai!", 570, 380);

		GameLobby[] lobbies = model.getGameLobby(CardGameType.War);

		int quarterWidth = gc.getWidth() / 4;
		for (int i = 0; i < lobbies.length; i++) {
			int x = 185 + quarterWidth * i;
			g.drawString("Lobby " + (i + 1), x, 500);
			for (int j = 0; j < 2; j++) {
				if (lobbies[i].getPlayers().size() > j) {
					/* Player is present at j'th index */
					g.drawString(lobbies[i].getPlayers().get(j).userName,
							x + 10, 520 + 25 * j);
				} else {
					g.drawString("Open", x + 10, 520 + 25 * j);
				}
			}
			
			
			if (model.isInLobby && model.getCurrentLobbyNumber() == i) {
				g.drawString("Leave", x + 7, 585);
			} else {
				g.drawString("Join", x + 10, 585);
			}
		}
		
		g.draw(lobbyOneJoin);
		g.draw(lobbyTwoJoin);
		g.draw(lobbyThreeJoin);
		g.draw(lobbyFourJoin);

		// render arrow
		g.drawImage(arrow, 0, 570);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;

		// back button clicked
		if ((xpos > 0 && xpos < 150) && (ypos > 0 && ypos < 150)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				sbg.enterState(1); // display game menu screen
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}
		
		int actualYPos = gc.getHeight()-ypos;
		Rectangle tmp = new Rectangle(xpos, actualYPos, 1, 1);
		if (lobbyOneJoin.intersects(tmp) || lobbyOneJoin.includes(xpos,actualYPos)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				
				if (!model.isInLobby) {
					model.joinGameLobby(CardGameType.War, 0);
				} else if (model.getCurrentLobbyNumber() == 0) {
					model.leaveGameLobby(CardGameType.War, 0);
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		} else if (lobbyTwoJoin.intersects(tmp) || lobbyTwoJoin.includes(xpos, actualYPos)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				model.joinGameLobby(CardGameType.War, 1);
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		} else if (lobbyThreeJoin.intersects(tmp) || lobbyThreeJoin.includes(xpos,actualYPos)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				model.joinGameLobby(CardGameType.War, 2);
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		} else if (lobbyFourJoin.intersects(tmp) || lobbyFourJoin.includes(xpos,actualYPos)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				model.joinGameLobby(CardGameType.War, 3);
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}
		

		// Play AI
		if ((xpos > 535 && xpos < 735) && (ypos > 270 && ypos < 370)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				model.createGameRequest();
				sbg.enterState(5);
			}

			if (Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

	}

	public int getID() {
		return 4;
	}
}
