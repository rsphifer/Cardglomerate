package javagame;

import org.lwjgl.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class GameMenu extends BasicGameState {
	
	private String mouse = "No input yet";
	private Image background;
	private Image ratscrew;
	private Image fish;
	private Image solitaire;
	private Image stud;
	private Image blackjack;
	private Image war;
	
	public GameMenu(int state) {
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		//initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);
		
		//initialize and scale each game image
		ratscrew = new Image("res/rat.jpg");
		ratscrew = ratscrew.getScaledCopy(250,250);
		
		fish = new Image("res/fish.jpg");
		fish = fish.getScaledCopy(250,250);
		
		solitaire = new Image("res/Just Cards.jpg");
		solitaire = solitaire.getScaledCopy(250,250);
		
		stud = new Image("res/Cards and Chips.jpg");
		stud = stud.getScaledCopy(250,250);
		
		blackjack = new Image("res/Cards and Chips 2.jpg");
		blackjack = blackjack.getScaledCopy(250,250);
		
		war = new Image("res/Many Cards.jpg");
		war = war.getScaledCopy(250,250);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//render the background
		background.draw(0,0);
		
		//mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);
		
		//welcome message
		g.drawString("Welcome to Cardglomerate! Please Select a game (click image).", 400, 10);
		
		//render each game image on the screen
		stud.draw(60,75);
		blackjack.draw(360,75);
		war.draw(660,75);
		solitaire.draw(960,75);
		ratscrew.draw(360,375);
		fish.draw(660,375);
		
		//render game names above images (probably temporary)
		g.drawString("Five Card Stud", 100, 50);
		g.drawString("Blackjack", 400, 50);
		g.drawString("War", 700, 50);
		g.drawString("Solitaire", 1000, 50);
		g.drawString("Egyptian Ratscrew", 400, 350);
		g.drawString("Go Fish", 700, 350);
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		//mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;
		
		//stud clicked
		if((xpos>60 && xpos<310) && (ypos>395 && ypos<645)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(3); //display coming soon screen
			}
		}
		
		//blackjack clicked
		if((xpos>360 && xpos<610) && (ypos>395 && ypos<645)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(3); //display coming soon screen
			}
		}
		
		//war clicked
		if((xpos>660 && xpos<910) && (ypos>395 && ypos<645)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(4); //enter war options
			}
		}
		
		//solitaire clicked
		if((xpos>960 && xpos<1210) && (ypos>395 && ypos<645)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(3); //display coming soon screen
			}
		}
		
		//ratscrew clicked
		if((xpos>360 && xpos<610) && (ypos>95 && ypos<345)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(3); //display coming soon screen
			}
		}
		
		//go fish clicked
		if((xpos>660 && xpos<910) && (ypos>95 && ypos<345)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(3); //display coming soon screen
			}
		}
		
	}
	
	public int getID() {
		return 1;
	}
}