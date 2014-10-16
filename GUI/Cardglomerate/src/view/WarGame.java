package view;

import org.lwjgl.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

//this GUI needs to have 6 variables updated by the model and that is all
//curCard1, curCard2, playerSize, opponentSize, winner, and gameOver
//gets these when deck is clicked

public class WarGame extends BasicGameState {
	
	private String mouse = "No input yet";
	private Image background;
	private Image curCard1; //players card
	private Image curCard2; //opponents card
	private int playerSize = 26; //players deck size
	private int opponentSize = 26; //opponents deck size
	private Image deckImage;
	private Image arrow;
	private Image[] clubs;
	private Image[] hearts;
	private Image[] spades;
	private Image[] diamonds;
	private boolean gameOver = false;
	private String winner = "You";

	
	public WarGame(int state) {
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		
		//initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);
		
		//initialize flat deck image
		deckImage = new Image("res/Cards/b2fv.png");
		
		//initalize deck arrays
		clubs = new Image[13];
		hearts = new Image[13];
		spades = new Image[13];
		diamonds = new Image[13];
		for (int i = 1; i < 14; i++) {
			clubs[i-1] = new Image("res/Cards/c" + i + ".png");
		}
		
		for (int i = 1; i < 14; i++) {
			hearts[i-1] = new Image("res/Cards/h" + i + ".png");
		}
		
		for (int i = 1; i < 14; i++) {
			spades[i-1] = new Image("res/Cards/s" + i + ".png");
		}
		
		for (int i = 1; i < 14; i++) {
			diamonds[i-1] = new Image("res/Cards/d" + i + ".png");
		}
		
		//starting curCards
		curCard1 = new Image("res/Cards/ec.png");
		curCard2 = new Image("res/Cards/ec.png");
		
		//back arrow
		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);
		

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//render the background
		background.draw(0,0);
		
		//mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);
		
		//draw deck image twice and size string
		deckImage.draw(550, 25);
		deckImage.draw(650, 550);
		g.drawString("" +opponentSize, 525, 25);
		g.drawString("" +playerSize, 725, 550);
		
		//draw instructor string
		g.drawString("Click Deck to Play Card", 725, 600);
		
		//draw current cards
		curCard2.draw(600, 200);
		curCard1.draw(600, 325);
		
		//gameover print
		if (gameOver) {
			g.drawString(winner + " Won the Game!", 700, 300);
			g.drawImage(arrow, 0, 570);
		}
		
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		//mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;	
			
		
		//deck clicked
		if((xpos>650 && xpos<725) && (ypos>70 && ypos<165)) {
			if(Mouse.isButtonDown(0)) {
				//update curCard1, curCard2, playerSize, opponentSize
				curCard1 = diamonds[11];
				curCard2 = hearts[6];
				playerSize = 28;
				opponentSize = 24;
				
				gameOver = true;
			}
		}
		
		//gameover check
	//	if (playerSize == 52) {
	//		winner = "YOU";
	//		gameOver = true;
	//	}
	//	else if (opponentSize == 52) {
	//		winner = "Your Opponent";
	//		gameOver = true;
	//	}
		
		//back button clicked
		if((xpos>0 && xpos<150) && (ypos>0 && ypos<150) && (gameOver)) {
			if(Mouse.isButtonDown(0)) {
				//resets gui for new game
				gameOver = false;
				curCard1 = new Image("res/Cards/ec.png");
				curCard2 = new Image("res/Cards/ec.png");
				playerSize = 26;
				opponentSize = 26;
				
				sbg.enterState(1); //display game menu screen
			}
		}
		
	}
	
	public int getID() {
		return 5;
	}
}