package view;

import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameMenu extends BasicGameState {
	private Model model;

	private String mouse = "No input yet";
	private Image background;
	private Image ratscrew;
	private Image fish;
	private Image holdEm;
	private Image stud;
	private Image blackjack;
	private Image war;
	private Image arrow;

	// card images for game GUI
	public static Image[] clubs;
	public static Image[] hearts;
	public static Image[] spades;
	public static Image[] diamonds;
	public static Image deckImage;

	public GameMenu(int state, Model model) {
		this.model = model;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);

		// initialize and scale log out button
		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);

		// initialize and scale each game image
		ratscrew = new Image("res/rat.jpg");
		ratscrew = ratscrew.getScaledCopy(250, 250);

		fish = new Image("res/fish.jpg");
		fish = fish.getScaledCopy(250, 250);

		holdEm = new Image("res/Just Cards.jpg");
		holdEm = holdEm.getScaledCopy(250, 250);

		stud = new Image("res/Cards and Chips.jpg");
		stud = stud.getScaledCopy(250, 250);

		blackjack = new Image("res/Cards and Chips 2.jpg");
		blackjack = blackjack.getScaledCopy(250, 250);

		war = new Image("res/Many Cards.jpg");
		war = war.getScaledCopy(250, 250);

		// initialize flat deck image
		deckImage = new Image("res/Cards/b2fv.png");

		// initalize deck arrays
		clubs = new Image[13];
		hearts = new Image[13];
		spades = new Image[13];
		diamonds = new Image[13];
		for (int i = 1; i < 14; i++) {
			clubs[i - 1] = new Image("res/Cards/c" + i + ".png");
		}

		for (int i = 1; i < 14; i++) {
			hearts[i - 1] = new Image("res/Cards/h" + i + ".png");
		}

		for (int i = 1; i < 14; i++) {
			spades[i - 1] = new Image("res/Cards/s" + i + ".png");
		}

		for (int i = 1; i < 14; i++) {
			diamonds[i - 1] = new Image("res/Cards/d" + i + ".png");
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// render the background
		background.draw(0, 0);

		// mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);

		// render arrow
		g.drawImage(arrow, 0, 570);
		g.drawString("Log Out", 5, 550);

		// welcome message
		g.drawString(
				"Welcome to Cardglomerate! Please Select a game (click image).",
				400, 10);

		// render each game image on the screen
		stud.draw(60, 75);
		blackjack.draw(360, 75);
		war.draw(660, 75);
		holdEm.draw(960, 75);
		ratscrew.draw(360, 375);
		fish.draw(660, 375);

		// render game names above images (probably temporary)
		g.drawString("Five Card Stud", 100, 50);
		g.drawString("Blackjack", 400, 50);
		g.drawString("War", 700, 50);
		g.drawString("Texas Hold'em", 1000, 50);
		g.drawString("Egyptian Ratscrew", 400, 350);
		g.drawString("Go Fish", 700, 350);

		// Account Options Button
		g.drawRect(1070, 670, 200, 40);
		g.drawString("Account Options", 1100, 680);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;

		// stud clicked
		if ((xpos > 60 && xpos < 310) && (ypos > 395 && ypos < 645)) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(3); // display coming soon screen
			}
		}

		// blackjack clicked
		if ((xpos > 360 && xpos < 610) && (ypos > 395 && ypos < 645)) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(3); // display coming soon screen
			}
		}

		// war clicked
		if ((xpos > 660 && xpos < 910) && (ypos > 395 && ypos < 645)) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(4); // enter war options
			}
		}

		// Hold'em clicked
		if ((xpos > 960 && xpos < 1210) && (ypos > 395 && ypos < 645)) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(7); // display coming soon screen
			}
		}

		// ratscrew clicked
		if ((xpos > 360 && xpos < 610) && (ypos > 95 && ypos < 345)) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(3); // display coming soon screen
			}
		}

		// go fish clicked
		if ((xpos > 660 && xpos < 910) && (ypos > 95 && ypos < 345)) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(3); // display coming soon screen
			}
		}

		// Account options clicked
		if ((xpos > 1070 && xpos < 1270) && (ypos > 10 && ypos < 50)) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(6);
			}
		}

		// log out button clicked
		if ((xpos > 0 && xpos < 150) && (ypos > 0 && ypos < 150)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				if (model.logout()) {
					sbg.enterState(0); // display login screen
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}
	}

	public int getID() {
		return 1;
	}
}
