package view;

import java.util.LinkedList;

import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import cardgames.BlackJack;
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
	private LinkedList<Card> c1;
	private LinkedList<Card> c2;

	private Rectangle hitButton = new Rectangle(700, 650, 100, 50);
	private Rectangle stayButton = new Rectangle(810, 650, 100, 50);
	private Rectangle playAgainButton = new Rectangle(700, 590, 150, 50);

	private int dealerScore, playerScore;
	private int ticksSinceDealerPlayed;

	private String playerName = null;

	/* Holds this player's position in the players list in the game */
	private int playerIndex;

	private String opponentName;

	public BlackjackGame(int state, Model model) {
		this.model = model;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		// initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);

		dealerScore = 0;
		playerScore = 0;
		ticksSinceDealerPlayed = 0;
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

		g.draw(hitButton);
		g.drawString("Hit", 732, 662);
		g.draw(stayButton);
		g.drawString("Stay", 844, 662);

		g.drawString("Count: " + playerScore, 550, 620);
		g.drawString(playerName, 550, 650);

		g.drawString("Dealer", 550, 25);
		g.drawString("Count: " + dealerScore, 550, 55);

		BlackJack game = (BlackJack) model.getCurrentGame();
		for (int i = 0; i < game.getPlayerCards().size(); i++) {
			getCardImage(game.getPlayerCards().get(i)).draw(530 + i * 30, 440);
		}

		for (int j = 0; j < game.getDealerCards().size(); j++) {
			getCardImage(game.getDealerCards().get(j)).draw(530 + j * 30, 170);
		}

		if (game.gameEnded) {
			g.draw(playAgainButton);
			g.drawString("Play Again", 732, 602);
			
			if (game.playerWon) {
				g.drawString(model.getPlayer().userName + " won!", 530, 300);
			} else {
				g.drawString("Dealer won!", 530, 300);
			}
		}

		arrow.draw(0, 570);

		// draw current cards
		// curCard2.draw(600, 200);
		// curCard1.draw(600, 325);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;

		if (playerName == null) {
			playerName = model.getPlayer().userName;
		}

		BlackJack game = (BlackJack) model.getCurrentGame();

		int actualY = gc.getHeight() - ypos;
		Rectangle tmp = new Rectangle(xpos, actualY, 1, 1);

		/* Hit button selected */
		if (hitButton.contains(tmp) || hitButton.intersects(tmp)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;

				if (game.waitingOnPlayer) {
					game.playerHit();
					model.updateGame();
				}

			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		/* Stay button selected */
		if (stayButton.contains(tmp) || stayButton.intersects(tmp)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				if (game.waitingOnPlayer) {
					game.playerStay();
					model.updateGame();
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		/* Play again button selected */
		if (playAgainButton.contains(tmp) || playAgainButton.intersects(tmp)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				if (game.gameEnded) {
					game.resetGame();
					model.updateGame();
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		/* Dealer is playing */
		if (!game.gameEnded && !game.waitingOnPlayer) {
			System.out.println("que?");
			if (game.playerBusted || (game.getDealerScoreWithAces() > 15 && game.getPlayerScoreWithAces() < game.getDealerScoreWithAces())) {
				game.dealerStay();
				model.updateGame();
			} else if (++ticksSinceDealerPlayed > 60) {
				game.dealerHit();
				model.updateGame();
				ticksSinceDealerPlayed = 0;
			}
		}

		// back button clicked
		if ((xpos > 0 && xpos < 150) && (ypos > 0 && ypos < 150)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;

				model.isInGame = false;
				sbg.enterState(1); // display game menu screen
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		playerScore = game.getPlayerScoreWithAces();
		dealerScore = game.getDealerScoreWithAces();

	}

	// // gets curCards and deck sizes from model and updates them
	// private void updateCards() {
	//
	// cards = model.getCurrentGame().getCardsToDisplay();
	// sizes = model.getCurrentGame().getHandSizes();
	// if (!cards.isEmpty()) {
	// if (((War) model.getCurrentGame()).isMultiplayer) {
	// if (opponentName.equals("player")) {
	// if (playerIndex == 0) {
	// opponentName = model.getCurrentGame().getPlayers().get(1).userName;
	// } else {
	// opponentName = model.getCurrentGame().getPlayers().get(0).userName;
	// }
	// }
	//
	// c1 = new LinkedList<Card>();
	// c2 = new LinkedList<Card>();
	//
	// if (playerIndex == 0) {
	//
	// int ind = 0;
	// while (!cards.isEmpty()) {
	// if (ind%2==0) {
	// c1.add(cards.remove(0));
	// } else {
	// c2.add(cards.remove(0));
	// }
	// ind++;
	// }
	//
	// playerSize = sizes.get(1);
	// opponentSize = sizes.get(0);
	//
	// } else {
	//
	// int ind = 0;
	// while (!cards.isEmpty()) {
	// if (ind%2==0) {
	// c2.add(cards.remove(0));
	// } else {
	// c1.add(cards.remove(0));
	// }
	// ind++;
	// }
	//
	// playerSize = sizes.get(0);
	// opponentSize = sizes.get(1);
	// }
	// } else {
	//
	// int ind = 0;
	// while (!cards.isEmpty()) {
	// if (ind%2==0) {
	// c1.add(cards.remove(0));
	// } else {
	// c2.add(cards.remove(0));
	// }
	// ind++;
	// }
	//
	// playerSize = sizes.get(1);
	// opponentSize = sizes.get(0);
	// }
	//
	// }
	// }

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
