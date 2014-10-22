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

import cards.Card;

//this GUI needs to have 6 variables updated by the model and that is all
//curCard1, curCard2, playerSize, opponentSize, winner, and gameOver
//gets these when deck is clicked

public class HoldEmGame extends BasicGameState {
	
	private Model model;
	
	private String mouse = "No input yet";
	private Image background;
	private Image arrow;
	private boolean gameOver = false;

	
	//player card variables
	private Image p1C1;  //player 1 card 1 etc etc, player 1 is current user
	private Image p1C2;
	private Image p2C1;
	private Image p2C2;
	private Image p3C1;
	private Image p3C2;
	private Image p4C1;
	private Image p4C2;

	
	public HoldEmGame(int state, Model model) {
		this.model = model;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		
		//initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);
		
		//back arrow
		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);
		
		//player cards start as blank
		p1C1 = GameMenu.deckImage;
		p1C2 = GameMenu.deckImage;
		p2C1 = GameMenu.deckImage;
		p2C2 = GameMenu.deckImage;
		p3C1 = GameMenu.deckImage;
		p3C2 = GameMenu.deckImage;
		p4C1 = GameMenu.deckImage;
		p4C2 = GameMenu.deckImage;
		

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//render the background
		background.draw(0,0);
		
		//mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);
		
		
		//draw each player's 2 cards
		p1C1.draw(500, 5);
		p1C1.draw(520, 5);
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		//mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;	
			
			
		//back button clicked
		if((xpos>0 && xpos<150) && (ypos>0 && ypos<150) && (gameOver)) {
			if(Mouse.isButtonDown(0)) {
				//resets gui for new game
				gameOver = false;
			
				
				sbg.enterState(1); //display game menu screen
			}
		}
		
	}
	
	private Image getCardImage(Card c) {
		
		//0 = hearts, 1 = diamonds, 2 = spades, 3 = clubs
		switch(c.getSuit()) {
		
		case 0:
			return GameMenu.hearts[c.getPower()-1];
			
		case 1:
			return GameMenu.diamonds[c.getPower()-1];
		
		case 2:
			return GameMenu.spades[c.getPower() - 1];
			
		case 3:
			return GameMenu.clubs[c.getPower()-1];
		}
		return null;

	}
	
	public int getID() {
		return 8;
	}
}
