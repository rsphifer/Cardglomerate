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
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import player.Friend;
import player.Player;
import cardgames.CardGameType;

public class WarOptions extends BasicGameState {

	private Model model;

	private String mouse = "No input yet";
	private Image background;
	private Image image;
	private Image arrow;

	private Rectangle lobbyOneJoin = new Rectangle(180, 580, 70, 30);
	private Rectangle lobbyTwoJoin = new Rectangle(500, 580, 70, 30);
	private Rectangle lobbyThreeJoin = new Rectangle(820, 580, 70, 30);
	private Rectangle lobbyFourJoin = new Rectangle(1140, 580, 70, 30);

	public boolean isStartSelected = false;
	
	private Rectangle gameStartButton = new Rectangle(1, 1, 70, 30);

	private boolean lobbyJoined = false;
	private int lobbyNumberJoined = 0;
	
	// friends list things
	private int curx;
	private int cury;
	ArrayList<Friend> friends;

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
					Player curr = lobbies[i].getPlayers().get(j);
					if (curr.userName.equals(lobbies[i].getHost().userName)) {
						g.drawString(curr.userName+"(Host)",
								x + 10, 520 + 25 * j);
					} else {
						g.drawString(curr.userName,
								x + 10, 520 + 25 * j);
					}
				} else {
					g.drawString("Open", x + 10, 520 + 25 * j);
				}
			}

			if (model.isInLobby && model.getCurrentLobbyNumber() == i) {
				if (model.getPlayer().userName
						.equals(lobbies[i].getHost().userName)) {
					gameStartButton.setX(x - 5);
					gameStartButton.setY(620);
					g.draw(gameStartButton);
					g.drawString("Start", x + 7, 625);
				}
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

		/* Check if player is in a lobby and if lobby has been started. */
		if (model.isInLobby && model.getGameLobby(CardGameType.War)[model.getCurrentLobbyNumber()].isStarted) {
			if (model.enterGameFromLobby(model.getGameLobby(CardGameType.War)[model.getCurrentLobbyNumber()].getGameId())) {
				isStartSelected = false;
				sbg.enterState(5);
			}
		}
		
		// back button clicked
		if ((xpos > 0 && xpos < 150) && (ypos > 0 && ypos < 150)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;

				if (model.isInLobby) {
					model.leaveGameLobby(CardGameType.War,
							model.getCurrentLobbyNumber());
				}
				sbg.enterState(1); // display game menu screen
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		int actualYPos = gc.getHeight() - ypos;
		Rectangle tmp = new Rectangle(xpos, actualYPos, 1, 1);
		if (lobbyOneJoin.intersects(tmp)
				|| lobbyOneJoin.includes(xpos, actualYPos)) {
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
		} else if (lobbyTwoJoin.intersects(tmp)
				|| lobbyTwoJoin.includes(xpos, actualYPos)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;

				if (!model.isInLobby) {
					model.joinGameLobby(CardGameType.War, 1);
				} else if (model.getCurrentLobbyNumber() == 1) {
					model.leaveGameLobby(CardGameType.War, 1);
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		} else if (lobbyThreeJoin.intersects(tmp)
				|| lobbyThreeJoin.includes(xpos, actualYPos)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;

				if (!model.isInLobby) {
					model.joinGameLobby(CardGameType.War, 2);
				} else if (model.getCurrentLobbyNumber() == 2) {
					model.leaveGameLobby(CardGameType.War, 2);
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		} else if (lobbyFourJoin.intersects(tmp)
				|| lobbyFourJoin.includes(xpos, actualYPos)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;

				if (!model.isInLobby) {
					model.joinGameLobby(CardGameType.War, 3);
				} else if (model.getCurrentLobbyNumber() == 3) {
					model.leaveGameLobby(CardGameType.War, 3);
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		} else if (gameStartButton.intersects(tmp)
				|| gameStartButton.includes(xpos, actualYPos)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				if (model.isInLobby) {
					GameLobby tmpLobby = model.getGameLobby(CardGameType.War)[model
							.getCurrentLobbyNumber()];
					if (tmpLobby.getHost().userName
							.equals(model.getPlayer().userName)) {
						if (!isStartSelected && tmpLobby.getPlayers().size() == 2) {
							isStartSelected = true;
							System.out.println("Start game accepted");
							model.startGameFromLobby();
						} else {
							System.out.println("Not enough players");
						}
					}
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		// Play AI
		if ((xpos > 535 && xpos < 735) && (ypos > 270 && ypos < 370)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				if (model.isInLobby) {
					model.leaveGameLobby(CardGameType.War,
							model.getCurrentLobbyNumber());
				}
				model.createGameRequest(CardGameType.War);
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
