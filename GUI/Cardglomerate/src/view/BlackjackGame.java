package view;

import java.util.LinkedList;

import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cardgames.War;
import cards.Card;

//this GUI needs to have 6 variables updated by the model and that is all
//curCard1, curCard2, playerSize, opponentSize, winner, and gameOver
//gets these when deck is clicked

public class BlackjackGame extends BasicGameState {

	private Model model;

	private String mouse = "No input yet";
	private Image background;
	private int playerSize = 26; // players deck size
	private int opponentSize = 26; // opponents deck size
	private Image arrow;
	private boolean gameOver = false;
	private String winner = "Sombody";
	private LinkedList<Card> cards;
	private LinkedList<Integer> sizes;
	private LinkedList <Card> c1;
	private LinkedList <Card> c2;


	/* Holds this player's position in the players list in the game */
	private int playerIndex;
	
	private String opponentName;

	public BlackjackGame(int state, Model model) {
		this.model = model;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		playerIndex = -1;
		opponentName = "player";

		// initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);
		
		c1 = new LinkedList<Card>();
		c2 = new LinkedList<Card>();

		// back arrow
		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// render the background
		background.draw(0, 0);

		// mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);

	
	
		
		// draw current cards
//		curCard2.draw(600, 200);
//		curCard1.draw(600, 325);
		

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;

		


		// back button clicked
		if ((xpos > 0 && xpos < 150) && (ypos > 0 && ypos < 150) && (gameOver)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				
				
				model.isInGame = false;
				sbg.enterState(1); // display game menu screen
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

	}

//	// gets curCards and deck sizes from model and updates them
//	private void updateCards() {
//	
//		cards = model.getCurrentGame().getCardsToDisplay();
//		sizes = model.getCurrentGame().getHandSizes();
//		if (!cards.isEmpty()) {
//			if (((War) model.getCurrentGame()).isMultiplayer) {
//				if (opponentName.equals("player")) {
//					if (playerIndex == 0) {
//						opponentName = model.getCurrentGame().getPlayers().get(1).userName;
//					} else {
//						opponentName = model.getCurrentGame().getPlayers().get(0).userName;
//					}
//				}
//				
//				c1 = new LinkedList<Card>();
//				c2 = new LinkedList<Card>();
//				
//				if (playerIndex == 0) {
//					
//					int ind = 0;
//					while (!cards.isEmpty()) {
//						if (ind%2==0) {
//							c1.add(cards.remove(0));
//						} else {
//							c2.add(cards.remove(0));
//						}
//						ind++;
//					}
//					
//					playerSize = sizes.get(1);
//					opponentSize = sizes.get(0);
//					
//				} else {
//					
//					int ind = 0;
//					while (!cards.isEmpty()) {
//						if (ind%2==0) {
//							c2.add(cards.remove(0));
//						} else {
//							c1.add(cards.remove(0));
//						}
//						ind++;
//					}
//					
//					playerSize = sizes.get(0);
//					opponentSize = sizes.get(1);
//				}
//			} else {
//				
//				int ind = 0;
//				while (!cards.isEmpty()) {
//					if (ind%2==0) {
//						c1.add(cards.remove(0));
//					} else {
//						c2.add(cards.remove(0));
//					}
//					ind++;
//				}
//				
//				playerSize = sizes.get(1);
//				opponentSize = sizes.get(0);
//			}
//			
//		}
//	}

	private Image getCardImage(Card c) {

		// 0 = hearts, 1 = diamonds, 2 = spades, 3 = clubs
		switch (c.getSuit()) {

		case 0:
			return GameMenu.hearts[c.getPower() - 1];

		case 1:
			return GameMenu.diamonds[c.getPower() - 1];

		case 2:
			return GameMenu.spades[c.getPower() - 1];

		case 3:
			return GameMenu.clubs[c.getPower() - 1];
		}
		return null;

	}

	public int getID() {
		return 10;
	}
}
