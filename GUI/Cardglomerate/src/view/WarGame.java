package view;

import org.lwjgl.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class WarGame extends BasicGameState {
	
	private String mouse = "No input yet";
	private Image background;
	private Image curCard1;
	private Image curCard2;
	private Image deckImage;
	private Image[] clubs;
	private Image[] hearts;
	private Image[] spades;
	private Image[] diamonds;

	
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
		

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//render the background
		background.draw(0,0);
		
		//mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);
		
		//draw deck image twice
		deckImage.draw(550, 25);
		deckImage.draw(650, 550);
		
		//draw current cards
		curCard1.draw(600, 200);
		curCard2.draw(600, 325);
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		//mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;	
		
		//temp curcards
		curCard1 = diamonds[5];
		curCard2 = hearts[11];
		
		
	}
	
	public int getID() {
		return 5;
	}
}
