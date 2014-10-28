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

public class AccountOptions extends BasicGameState {
	private Model model;
	
	private String mouse = "No input yet";
	private Image background;
	private Image image;
	private Image arrow;
	private TextField usernameField;
	private TextField passwordField;
	private TextField emailField;
	private TextField deleteField;
	private boolean changeUsername = false;
	private boolean changePassword = false;
	private boolean changeEmail = false;
	private boolean deleteAccount = false;
	private String newUsername;
	private String newPassword;
	private String newEmail;
	private String curPassword;
	private boolean usernameFieldSelected = false;
	private boolean passwordFieldSelected = false;
	private boolean emailFieldSelected = false;
	private boolean deleteFieldSelected = false;
	private boolean usernameError = false;
	private boolean passwordError = false;
	private boolean emailError = false;
	private boolean deleteError = false;

	
	public AccountOptions(int state, Model model) {
		this.model = model;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		//initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);
		
		//initialize and scale images		
		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);
		
		//initialize gear image
		image = new Image("res/Gear.jpg");
		
		//initialize textfields
		usernameField = new TextField(gc, gc.getDefaultFont(), 560, 325, 200, 30);
		passwordField = new TextField(gc, gc.getDefaultFont(), 560, 425, 200, 30);
		emailField = new TextField(gc, gc.getDefaultFont(), 560, 525, 200, 30);
		deleteField = new TextField(gc, gc.getDefaultFont(), 560, 625, 200, 30);
		

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//render the background
		background.draw(0,0);
		
		//mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);
		
		//render message
		g.drawString("Acount Options", 600, 10);
		
		//render arrow
		g.drawImage(arrow, 0, 570);
		
		//render image
		g.drawImage(image, 510, 40);
		
		//change username button
		g.drawRect(560, 280, 200, 40);
		g.drawString("Change Username", 590, 290);
		
		//change password button
		g.drawRect(560, 380, 200, 40);
		g.drawString("Change Password", 590, 390);
		
		//change email button
		g.drawRect(560, 480, 200, 40);
		g.drawString("Change Email", 590, 490);
		
		//delete account button
		g.drawRect(560, 580, 200, 40);
		g.drawString("Delete Account", 590, 590);
		
		//render username changing stuff
		if (changeUsername) {
			usernameField.render(gc, g);
			g.drawString("Please enter desired new username.", 770, 330);
			if (!changeUsername) {
				g.clear();
			}
		}
		
		if(usernameError) {
			g.drawString("Invalid Username", 560, 360);
			if (!usernameError) {
				g.clear();
			}
		}
		
		//render password changing stuff
		if (changePassword) {
			passwordField.render(gc, g);
			g.drawString("Please enter desired new password.", 770, 430);
			if (!changePassword) {
				g.clear();
			}
		}
		
		if (passwordError) {
			g.drawString("Invalid Password", 560, 460);
			if(!passwordError) {
				g.clear();
			}
		}
		
		//render email changing stuff
		if (changeEmail) {
			emailField.render(gc, g);
			g.drawString("Please enter desired new email.", 770, 530);
			if (!changeEmail) {
				g.clear();
			}
		}
		
		if (emailError) {
			g.drawString("Invalid Email", 560, 560);
			if(!emailError) {
				g.clear();
			}
		}
		
		//render delete account stuff
		if (deleteAccount) {
			deleteField.render(gc, g);
			g.drawString("Please enter your current password", 770, 630);
			if (!deleteAccount) {
				g.clear();
			}
		}
		
		if (deleteError) {
			g.drawString("Wrong password", 560, 560);
			if(!deleteError) {
				g.clear();
			}
		}
		
	}
		
	
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		//mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;
		
		//textbox focus code
		if (usernameFieldSelected) {
			usernameField.setFocus(true);
		}
		if(passwordFieldSelected){
			passwordField.setFocus(true);
		}
		if(emailFieldSelected) {
			emailField.setFocus(true);
		}
		if(deleteFieldSelected) {
			deleteField.setFocus(true);
		}
		
		//username textbox clicked
		if((xpos>560 && xpos<760) && (ypos>365 && ypos<395)) {
			if(Mouse.isButtonDown(0)) { //button 0 = left click
				passwordFieldSelected = false;
				emailFieldSelected = false;
				usernameFieldSelected = true;
			}
		}
		//password textbox clicked
		if((xpos>560 && xpos<760) && (ypos>265 && ypos<295)) {
			if(Mouse.isButtonDown(0)) {
				usernameFieldSelected = false;
				emailFieldSelected = false;
				passwordFieldSelected = true;
			}
		}
		//email textbox clicked
		if((xpos>560 && xpos<760) && (ypos>165 && ypos<195)) {
			if(Mouse.isButtonDown(0)) {
				usernameFieldSelected = false;
				passwordFieldSelected = false;
				emailFieldSelected = true;
			}
		}
		//delete textbox clicked
		if((xpos>560 && xpos<760) && (ypos>65 && ypos<95)) {
			if(Mouse.isButtonDown(0)) {
				usernameFieldSelected = false;
				passwordFieldSelected = false;
				emailFieldSelected = false;
				deleteFieldSelected = true;
			}
		}
		
		//back button clicked
		if((xpos>0 && xpos<150) && (ypos>0 && ypos<150)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				sbg.enterState(1); //display game menu screen
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
			}
		}
		
		//change username button clicked
		if((xpos>560 && xpos<760) && (ypos>400 && ypos<440)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				if (!changeUsername) {
					changeUsername = true;
				}
				else {
					newUsername = usernameField.getText();
					//send new username to server to check, if error set username error else don't

					if (!usernameError) {
						changeUsername = false;
					}
				}
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
			}
		}
		
		//change password button clicked
		if((xpos>560 && xpos<760) && (ypos>300 && ypos<340)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				if (!changePassword) {
					changePassword = true;
				}
				else {
					newPassword = passwordField.getText();
					if (newPassword.length() < 1) {
						passwordError = true;
					}
					else {
						passwordError = false;
					}
					if (!passwordError) {
						//code to send pass to server goes here
						//model.setNewPassword(newPassword);
						changePassword = false;
					}
				}
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
			}
		}
		
		//change email button clicked
		if((xpos>560 && xpos<760) && (ypos>200 && ypos<240)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				if (!changeEmail) {
					changeEmail = true;
				}
				else {
					newEmail = emailField.getText();
					if (newEmail.length() < 7) {
						emailError = true;
					}
					else {
						emailError = false;
					}
					if (!emailError) {
						//code to send email to server goes here
						
						changeEmail = false;
					}
				}
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
			}
		}
		
		//delete account button clicked
		if((xpos>560 && xpos<760) && (ypos>100 && ypos<140)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				if (!deleteAccount) {
					deleteAccount = true;
				}
				else {
					curPassword = deleteField.getText();
					//code to check if password is valid blah blah delete account blah blah
				}
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
			}
		}
	}
	
	public int getID() {
		return 6;
	}
}
