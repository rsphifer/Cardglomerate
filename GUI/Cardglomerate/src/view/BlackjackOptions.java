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

public class BlackjackOptions extends BasicGameState {

	private Model model;

	private String mouse = "No input yet";
	private Image background;
	private Image image;
	private Image arrow;
	
	// friends list things
	private int curx;
	private int cury;
	ArrayList<Friend> friends;


	public BlackjackOptions(int state, Model model) {
		this.model = model;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);

		// initialize and scale images
		image = new Image("res/Cards and Chips 2.jpg");		
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
		g.drawString("Blackjack", 600, 10);
		g.drawImage(image, 447, 50);

		// render play button
		g.drawRect(535, 350, 200, 100);
		g.drawString("Play Against Ai!", 570, 380);


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


		// Play AI
		if ((xpos > 535 && xpos < 735) && (ypos > 270 && ypos < 370)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				
				model.createGameRequest(CardGameType.Blackjack);
				model.getPlayer().setMoney(5000);
				sbg.enterState(10);
			}

			if (Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

	}

	public int getID() {
		return 9;
	}
}
