package view;

import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
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
	private Image money;
	private boolean gameOver = false;
	private TextField betField;
	private boolean betError = false;
	private String betString;

	//misc player variables
	private String p1Name = "Mooselord";
	private String p2Name = "Billy Bob";
	private String p3Name = "Raoul";
	private String p4Name = "Rngesus";
	private int playerBalance = 5000;
	private int potSize = 0;
	private int callSize = 0;
	private int betSize = 0;
	
	//player card variables
	private Image p1C1;  //player 1 card 1 etc etc, player 1 is current user
	private Image p1C2;
	private Image p2C1;
	private Image p2C2;
	private Image p3C1;
	private Image p3C2;
	private Image p4C1;
	private Image p4C2;
	
	//table card variables
	private Image t1;
	private Image t2;
	private Image t3;
	private Image t4;
	private Image t5;

	
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
		
		//money pot
		money = new Image("res/Sock Money.jpg");
		
		//player cards start as blank
		p1C1 = GameMenu.deckImage;
		p1C2 = GameMenu.deckImage;
		p2C1 = GameMenu.deckImage;
		p2C2 = GameMenu.deckImage;
		p3C1 = GameMenu.deckImage;
		p3C2 = GameMenu.deckImage;
		p4C1 = GameMenu.deckImage;
		p4C2 = GameMenu.deckImage;
		
		//table cards start as blank
		t1 = GameMenu.deckImage;
		t2 = GameMenu.deckImage;
		t3 = GameMenu.deckImage;
		t4 = GameMenu.deckImage;
		t5 = GameMenu.deckImage;
		
		//bet field
		betField = new TextField(gc, gc.getDefaultFont(), 850, 590, 150, 30);

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//render the background
		background.draw(0,0);
		
		//mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);
		
		//render arrow
		g.drawString("Leave Table", 15, 550);
		g.drawImage(arrow, 0, 570);
		
		//draw money pot
		money.draw(1000, 0);
		g.drawString("Pot Size: $"+potSize, 1000, 200);
		
		//draw each player's name
		g.drawString(p1Name, 550, 700);
		g.drawString(p2Name, 550, 125);
		g.drawString(p3Name, 20, 400);
		g.drawString(p4Name, 1140, 400);
		
		
		//draw each player's 2 cards
		p1C1.draw(550, 600);
		p1C2.draw(600, 600);
		p2C1.draw(550, 25);
		p2C2.draw(600, 25);
		p3C1.draw(20, 300);
		p3C2.draw(70, 300);
		p4C1.draw(1140, 300);
		p4C1.draw(1190, 300);
		
		//draw table cards
		t1.draw(400, 300);
		t2.draw(500, 300);
		t3.draw(600, 300);
		t4.draw(700, 300);
		t5.draw(800, 300);
		
		//draw player balance
		g.drawString("Current Balance: $"+playerBalance, 670, 700);
		
		//draw call/check button
		g.drawRect(680, 625, 150, 50);
		g.drawString("Call/Check", 710, 640);
		g.drawString("Amount: $"+callSize, 680, 600);
		betField.render(gc, g);
		
		//draw raise/bet button
		g.drawRect(850, 625, 150, 50);
		g.drawString("Raise/Bet", 880, 640);
		
		//draw fold button
		g.drawRect(1020, 625, 150, 50);
		g.drawString("Fold", 1070, 640);
		
		//bet error
		if (betError) {
			g.drawString("Please Enter Raise/Bet", 850, 570);
			if(!betError) {
				g.clear();
			}
		}
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		//mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;	
		
		//set betbox as focus
		betField.setFocus(true);
			
		//back button clicked
		if((xpos>0 && xpos<150) && (ypos>0 && ypos<150) && (gameOver)) {
				if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
					Master.isMouseReleased = false;
					
					//rest GUI for next game
					gameOver = false;
					
					sbg.enterState(1); //display game menu screen
				}
				
				if (!Mouse.isButtonDown(0)){
					Master.isMouseReleased = true;
				}
		}
		
		//call/check button clicked
		if((xpos>680 && xpos<830) && (ypos>45 && ypos<95)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				//code here
				System.out.println("Call/Check");
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
			}
		}
		
		//raise/bet button clicked
		if((xpos>850 && xpos<1000) && (ypos>45 && ypos<95)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				//code here
				betString = betField.getText();
				if(betString.length() > 0 && isNumeric(betString)) {
					betSize = Integer.parseInt(betString);
					betError = false;
					betField.setText("");
					System.out.println("Raise/Bet $"+betSize);
				}
				else {
					betError = true;
				}
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
			}
		}
		
		//fold button clicked
		if((xpos>1020 && xpos<1170) && (ypos>45 && ypos<95)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				//code here
				System.out.println("Fold");
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
			}
		}
		
		//back button clicked
		if((xpos>0 && xpos<150) && (ypos>0 && ypos<150)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				//player left game, code to handle that
				
				sbg.enterState(7);
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
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
	
	public boolean isNumeric(String str) {
		int x;
		try {
			x = Integer.parseInt(str);
		}
		catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public int getID() {
		return 8;
	}
}
