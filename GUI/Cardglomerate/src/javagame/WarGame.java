package javagame;

import org.lwjgl.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class WarGame extends BasicGameState {
	
	private String mouse = "No input yet";
	private Image background;

	
	public WarGame(int state) {
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		//initialize and scale background image
		background = new Image("res/beach.jpg");
		background = background.getScaledCopy(1280, 720);
		

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//render the background
		background.draw(0,0);
		
		//mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		//mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;		
		
		
	}
	
	public int getID() {
		return 5;
	}
}