package view;

import java.util.ArrayList;
import java.util.LinkedList;

import misc.ChatEntry;
import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import controller.ServerAccess;
import cardgames.BlackJack;
import cardgames.CardGameType;
import cardgames.War;
import cards.Card;

//this GUI needs to have 6 variables updated by the model and that is all
//curCard1, curCard2, playerSize, opponentSize, winner, and gameOver
//gets these when deck is clicked

public class WarGame extends BasicGameState {

	private Model model;
	
	private boolean firstTime = true;
	private boolean firstCongrats = true;
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

	private boolean hasClicked = false;
	
	private boolean incrementedVictory = false;
	
	private int countWhenClicked;
	
	//chat stuff
	private TextField chatField;
	private boolean chatSelected, chatError;

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

		// back arrow
		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);
		
		//chat stuff
		chatField = new TextField(gc, gc.getDefaultFont(), 5, 680, 250, 30);
		chatSelected = true; chatError = false;

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
			
			if (i != 0) {
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
		
		arrow.draw(1130, 570);
		
		// gameover print
		if (gameOver) {
			g.drawString(winner + " Won the Game!", 700, 300);
		}
		
		//chat stuff
		War game = (War) model.getCurrentGame();
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

		  //enter functionality
		  Input in = gc.getInput();
		  boolean enterHit = false;
		  if (in.isKeyPressed(Input.KEY_ENTER)) {
		   enterHit = true;
		  }
		  
		  War currGame = (War) model.getCurrentGame();
		  
			if (firstTime && (!currGame.isMultiplayer)) {
				model.addGameChatEntry("I will crush you!", 1);
				firstTime = false;
			}

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
			if (firstCongrats) {
				model.addGameChatEntry("Well Played!", 1);
				firstCongrats = false;
			}
			if (winner.equals(model.getPlayer().userName) && !incrementedVictory) {
				incrementedVictory = true;
				model.incrementNumberOfWins(CardGameType.War);
			}
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
					model.getCurrentGame().update();
					model.updateGame();
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

		// back button clicked
		if ((xpos > 1130 && xpos < 1280) && (ypos > 0 && ypos < 150)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				// resets gui for new game
				gameOver = false;
				playerSize = 26;
				opponentSize = 26;
				model.isInGame = false;
				model.updateAchievements();
				firstTime = true;
				firstCongrats = true;
				sbg.enterState(1); // display game menu screen
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}
		
		//chat stuff
		War game = (War) model.getCurrentGame();
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
		  if(((xpos>260 && xpos<380) && (ypos>10 && ypos<40)) || enterHit) {
		   if((Mouse.isButtonDown(0) && Master.isMouseReleased) || enterHit) {
		    Master.isMouseReleased = false;
		    if (chatField.getText().length() > 1) {
		     //code to send chat message to server goes here
		     String newMessage = chatField.getText();
		     if ((newMessage.length() + userNameLength) <= 37) {
		      model.addGameChatEntry(newMessage, 0);
		      chatError = false;
		     }
		     else if ((newMessage.length() + (userNameLength*2)) <= 74) {
		      //split message in two and send both
		      String temp = newMessage.substring(0, (37 - userNameLength));
		      model.addGameChatEntry(temp, 0);
		      temp = newMessage.substring(37 - userNameLength);
		      model.addGameChatEntry(temp, 0);
		      chatError = false;
		     }
		     
		     else if ((newMessage.length() + (userNameLength*3)) <= 110) {
		      //split message into 3 and send all
		      String temp = newMessage.substring(0, (37-userNameLength));
		      model.addGameChatEntry(temp, 0);
		      temp = newMessage.substring((37-userNameLength), (74 - (userNameLength*2)));
		      model.addGameChatEntry(temp, 0);
		      temp = newMessage.substring((74-(userNameLength*2)));
		      model.addGameChatEntry(temp, 0);
		      
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
				c1 = new LinkedList<Card>();
				c2 = new LinkedList<Card>();
				
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
