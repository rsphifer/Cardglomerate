package view;

import org.lwjgl.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class WarOptions extends BasicGameState {
	
	private String mouse = "No input yet";
	private Image background;
	private Image image;
	private Image arrow;

	
	public WarOptions(int state) {
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		//initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);
		
		//initialize and scale images
		image = new Image("res/Many Cards.jpg");
		image = image.getScaledCopy(386, 258);
		
		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//render the background
		background.draw(0,0);
		
		//mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);
		
		//render message and image
		g.drawString("WAR!", 600, 10);
		g.drawImage(image, 447,50);
		
		//render play button
		g.drawRect(535, 350, 200, 100);
		g.drawString("Play Against Ai!", 570, 380);
		
		//render arrow
		g.drawImage(arrow, 0, 570);
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		//mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;
		
		//back button clicked
		if((xpos>0 && xpos<150) && (ypos>0 && ypos<150)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(1); //display game menu screen
			}
		}
		
		//Play AI
		if((xpos>535 && xpos<735) && (ypos>270 && ypos<370)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(5);
			}
		}
		
		
		
	}
	
	public int getID() {
		return 4;
	}
}