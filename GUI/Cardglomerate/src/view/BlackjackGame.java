package view;

import java.util.ArrayList;
import java.util.LinkedList;

import misc.ChatEntry;
import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
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
	
	////////////////TEMP
	private TextField chatField;
	private boolean chatSelected, chatError;
	
	//////////////////////

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
		
		
		////////Temp
		chatField = new TextField(gc, gc.getDefaultFont(), 5, 680, 250, 30);
		chatSelected = true; chatError = false;
		///////////////

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
		} else {
			if (game.waitingOnPlayer) {
				g.drawString(model.getPlayer().userName + "'s turn", 530, 300);
			} else {
				g.drawString("Dealer's turn", 530, 300);
			}
		}

		arrow.draw(0, 570);
		
		
		g.drawString("Chat", 150, 330);
		g.drawRect(260, 680, 120, 30);
		g.drawString("Send Message", 265, 685);
		chatField.render(gc, g);

		int curChatx = 5;
		int curChaty = 350;
		  
		ArrayList<ChatEntry> chatLog = game.getChatLog();
		for (int i = 0; i < chatLog.size(); i++) {
			ChatEntry currMessage = chatLog.get(i);
			g.drawString(currMessage.getUsername() + ": " + currMessage.getMessage(), curChatx, curChaty);
			//g.drawString(currMessage.getMessage(), curChatx+90, curChaty);
			curChaty += 20;
		   
		}  

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
		
		////////////////TEMP
		if (chatSelected) {
			   chatField.setFocus(true);
		}
		
		//chat textbox clicked
		  if((xpos>5 && xpos<255) && (ypos>10 && ypos<40)) {
		   if(Mouse.isButtonDown(0)) { //button 0 = left click
		    chatSelected = true;
		   }
		  }
		  
		  //send chat message button clicked
		  int userNameLength = model.getPlayer().userName.length();
		  if((xpos>260 && xpos<380) && (ypos>10 && ypos<40)) {
		   if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
		    Master.isMouseReleased = false;
		    if (chatField.getText().length() > 1) {
		     //code to send chat message to server goes here
		     String newMessage = chatField.getText();
		     if ((newMessage.length() + userNameLength) <= 37) {
		      model.addGameChatEntry(newMessage);
		      chatError = false;
		     }
		     else if ((newMessage.length() + (userNameLength*2)) <= 74) {
		      //split message in two and send both
		      String temp = newMessage.substring(0, (37 - userNameLength));
		      model.addGameChatEntry(temp);
		      temp = newMessage.substring(37 - userNameLength);
		      model.addGameChatEntry(temp);
		      chatError = false;
		     }
		     
		     else if ((newMessage.length() + (userNameLength*3)) <= 110) {
		      //split message into 3 and send all
		      String temp = newMessage.substring(0, (37-userNameLength));
		      model.addGameChatEntry(temp);
		      temp = newMessage.substring((37-userNameLength), (74 - (userNameLength*2)));
		      model.addGameChatEntry(temp);
		      temp = newMessage.substring((74-(userNameLength*2)));
		      model.addGameChatEntry(temp);
		      
		     }
		     
		     else {
		      chatError = true;
		     }

		     chatField.setText(""); 
		    }
		   }
		   
		   if (!Mouse.isButtonDown(0)){
		    Master.isMouseReleased = true;
		   }
		  }
		///////////////////////

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
			if (game.playerBusted || (game.getDealerScoreWithAces() > 15 && game.getPlayerScoreWithAces() <= game.getDealerScoreWithAces())) {
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
				model.updateAchievements();
				sbg.enterState(1); // display game menu screen
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		playerScore = game.getPlayerScoreWithAces();
		dealerScore = game.getDealerScoreWithAces();

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
		return 10;
	}
}
