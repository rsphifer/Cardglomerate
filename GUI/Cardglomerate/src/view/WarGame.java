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

public class WarGame extends BasicGameState {

	private Model model;

	private String mouse = "No input yet";
	private Image background;
	private Image curCard1; // players card
	private Image curCard2; // opponents card
	private int playerSize = 26; // players deck size
	private int opponentSize = 26; // opponents deck size
	private Image arrow;
	private boolean gameOver = false;
	private String winner = "Sombody";
	private LinkedList<Card> cards;
	private LinkedList<Integer> sizes;
	private LinkedList <Card> c1;
	private LinkedList <Card> c2;

	private boolean hasClicked = false;
	
	private int countWhenClicked;

	/* Holds this player's position in the players list in the game */
	private int playerIndex;
	
	private String opponentName;

	public WarGame(int state, Model model) {
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

		// starting curCards
		curCard1 = new Image("res/Cards/ec.png");
		curCard2 = new Image("res/Cards/ec.png");

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

		// draw deck image twice and size string
		GameMenu.deckImage.draw(550, 25);
		GameMenu.deckImage.draw(650, 550);
		g.drawString("" + opponentSize, 525, 25);
		g.drawString("" + playerSize, 725, 550);

		// draw instructor string
		g.drawString("Click Deck to Play Card", 725, 600);

		/* Draw player names */
		g.drawString(opponentName, 550, 8);
		g.drawString(model.getPlayer().userName, 650, 650);
		
		// draw current cards
//		curCard2.draw(600, 200);
//		curCard1.draw(600, 325);
		
		int j=0;
		for (int i=c1.size()-1; i >= 0; i--) {
			getCardImage(c1.get(i)).draw(600+j*90, 200);
			getCardImage(c2.get(i)).draw(600+j*90, 325);
			
			if (c1.size()-(i+1) > 0) {
				GameMenu.deckImage.draw(600+j*90+20, 200);
				GameMenu.deckImage.draw(600+j*90+40, 200);
				GameMenu.deckImage.draw(600+j*90+60, 200);
				
				GameMenu.deckImage.draw(600+j*90+20, 325);
				GameMenu.deckImage.draw(600+j*90+40, 325);
				GameMenu.deckImage.draw(600+j*90+60, 325);
			}
			
			j++;
		}
		
		if (hasClicked) {
			g.drawString("Player clicked", 725, 615);
		}

		if (((War) model.getCurrentGame()).getClickCounter() == 1
				&& !hasClicked) {
			g.drawString("Opponent clicked", 725, 300);
		}
		// gameover print
		if (gameOver) {
			g.drawString(winner + " Won the Game!", 700, 300);
			g.drawImage(arrow, 0, 570);
		}

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;

		War currGame = (War) model.getCurrentGame();

		if (currGame.isMultiplayer && playerIndex == -1) {
			/* player index has not been updated yet */
			int i;
			for (i = 0; i < currGame.getPlayers().size(); i++) {
				if (model.getPlayer().userName.equals(currGame.getPlayers()
						.get(i).userName)) {
					playerIndex = i;
					break;
				}
			}
		}

		// update curCards and sizes
		updateCards();

		// update gameover
		gameOver = currGame.getGameover();

		// check gameOver
		if (gameOver) {
			winner = currGame.getWinner().get(0).userName;
		}

		if (currGame.isMultiplayer
				&& (currGame.getClickCounter() == 0 || currGame
						.getCardsFlippedCount() != countWhenClicked)
				&& hasClicked) {
			hasClicked = false;
		}

		// deck clicked
		if ((xpos > 650 && xpos < 725) && (ypos > 70 && ypos < 165)) {
			if (currGame.isMultiplayer && !hasClicked) {
				if (Mouse.isButtonDown(0) && Master.isMouseReleased
						&& !gameOver) {
					Master.isMouseReleased = false;
					hasClicked = true;
					model.updateWarCounter();
					countWhenClicked = currGame.getCardsFlippedCount();
				}
			} else {
				if (Mouse.isButtonDown(0) && Master.isMouseReleased
						&& !gameOver) {
					Master.isMouseReleased = false;
					model.updateGame();
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		// back button clicked
		if ((xpos > 0 && xpos < 150) && (ypos > 0 && ypos < 150) && (gameOver)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				// resets gui for new game
				gameOver = false;
				curCard1 = new Image("res/Cards/ec.png");
				curCard2 = new Image("res/Cards/ec.png");
				playerSize = 26;
				opponentSize = 26;

				sbg.enterState(1); // display game menu screen
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

	}

	// gets curCards and deck sizes from model and updates them
	private void updateCards() {
	
		cards = model.getCurrentGame().getCardsToDisplay();
		sizes = model.getCurrentGame().getHandSizes();
		if (!cards.isEmpty()) {
			if (((War) model.getCurrentGame()).isMultiplayer) {
				if (opponentName.equals("player")) {
					if (playerIndex == 0) {
						opponentName = model.getCurrentGame().getPlayers().get(1).userName;
					} else {
						opponentName = model.getCurrentGame().getPlayers().get(0).userName;
					}
				}
				
				c1 = new LinkedList<Card>();
				c2 = new LinkedList<Card>();
				
				if (playerIndex == 0) {
					
				//	c1.add(cards.remove(0));
				//	c2.add(cards.get(1));
					
					int ind = 0;
					while (!cards.isEmpty()) {
						if (ind%2==0) {
							c1.add(cards.remove(0));
						} else {
							c2.add(cards.remove(0));
						}
						ind++;
					}
					
					playerSize = sizes.get(1);
					opponentSize = sizes.get(0);
					
				} else {
//					c2.add(cards.get(0));
//					c1.add(cards.get(1));
					
					int ind = 0;
					while (!cards.isEmpty()) {
						if (ind%2==0) {
							c2.add(cards.remove(0));
						} else {
							c1.add(cards.remove(0));
						}
						ind++;
					}
					
					playerSize = sizes.get(0);
					opponentSize = sizes.get(1);
				}
			} else {
//				c1.add(cards.get(0)); // player card
//				c2.add(cards.get(1)); // opponent card
				
				int ind = 0;
				while (!cards.isEmpty()) {
					if (ind%2==0) {
						c1.add(cards.remove(0));
					} else {
						c2.add(cards.remove(0));
					}
					ind++;
				}
				
				playerSize = sizes.get(1);
				opponentSize = sizes.get(0);
			}
			
//			curCard1 = getCardImage(c1);
//			curCard2 = getCardImage(c2);
			
		}
		

	}

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
		return 5;
	}
}
