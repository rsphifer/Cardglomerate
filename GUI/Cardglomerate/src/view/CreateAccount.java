package view;

import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CreateAccount extends BasicGameState {
	
	private Model model;
	
	private String mouse = "No Input Yet";
	private String username;
	private String password1;
	private String password2;
	private String email;
	private Image background;
	private Image logo;
	private TextField usernameField;
	private TextField password1Field;
	private TextField password2Field;
	private TextField emailField;
	private boolean usernameFieldSelected = true;
	private boolean password1FieldSelected = false;
	private boolean password2FieldSelected = false;
	private boolean emailFieldSelected = false;
	private boolean passwordMisMatch = false;
	private boolean passwordLengthError = false;
	private boolean emailError = false;
	private boolean usernameError = false;
	
	public CreateAccount(int state, Model model) {
		this.model = model;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		//initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);
		
		//initialize and scale logo image
		logo = new Image("res/Cardglomerate 2.png");
		logo = logo.getScaledCopy(491, 369);
		
		
		
		//initialize textfields
		usernameField = new TextField(gc, gc.getDefaultFont(), 535, 425, 200, 30);
		password1Field = new TextField(gc, gc.getDefaultFont(), 535, 490, 200, 30);
		password2Field = new TextField(gc, gc.getDefaultFont(), 535, 555, 200, 30);
		emailField = new TextField(gc, gc.getDefaultFont(), 535, 620, 200, 30);
		
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		//render background
		background.draw(0,0);
		
		//mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);
		
		//render logo
		logo.draw(394,0);
		
		//render text
		g.drawString("Username", 600, 400);
		g.drawString("Password", 600, 465);
		g.drawString("Reenter Password", 565, 530);
		g.drawString("Email Address", 580, 595);
		
		//render textfields
		usernameField.render(gc, g);
		password1Field.render(gc, g);
		password2Field.render(gc, g);
		emailField.render(gc, g);
		
		//create account button
		g.drawRect(535, 670, 200, 30);
		g.drawString("Create Account", 570, 675);
		
		//password mismatch
		if (passwordMisMatch) {
			g.drawString("Password Mismatch", 750,560);
			if (!passwordMisMatch) {
				g.clear();
			}
		}
		
		//password length error
		if(passwordLengthError) {
			g.drawString("Password must be at least 5 characters", 750, 495);
			if (!passwordLengthError) {
				g.clear();
			}
		}
		
		//email error
		if(emailError) {
			g.drawString("Invalid Email", 750, 625);
			if (!emailError) {
				g.clear();
			}
		}
		
		//username error
		if(usernameError) {
			g.drawString("Invalid Username", 750, 430);
			if (!usernameError) {
				g.clear();
			}
		}
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		//mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;
		
		//controls which textfield is currently selected
		//janky solution because textfields not working properly
		if (usernameFieldSelected) {
			usernameField.setFocus(true);
		}
		if(password1FieldSelected){
			password1Field.setFocus(true);
		}
		if(password2FieldSelected){
			password2Field.setFocus(true);
		}
		if(emailFieldSelected) {
			emailField.setFocus(true);
		}
		
		//username textbox clicked
		if((xpos>535 && xpos<735) && (ypos>265 && ypos<295)) {
			if(Mouse.isButtonDown(0)) { //button 0 = left click
				password1FieldSelected = false;
				password2FieldSelected = false;
				emailFieldSelected = false;
				usernameFieldSelected = true;
			}
		}
		//password1 textbox clicked
		if((xpos>535 && xpos<735) && (ypos>200 && ypos<230)) {
			if(Mouse.isButtonDown(0)) {
				usernameFieldSelected = false;
				password2FieldSelected = false;
				emailFieldSelected = false;
				password1FieldSelected = true;
			}
		}
		
		//password2 textbox clicked
		if((xpos>535 && xpos<735) && (ypos>135 && ypos<165)) {
			if(Mouse.isButtonDown(0)) {
				usernameFieldSelected = false;
				emailFieldSelected = false;
				password1FieldSelected = false;
				password2FieldSelected = true;
			}
		}
		
		//email textbox clicked
		if((xpos>535 && xpos<735) && (ypos>70 && ypos<100)) {
			if(Mouse.isButtonDown(0)) {
				usernameFieldSelected = false;
				password1FieldSelected = false;
				password2FieldSelected = false;
				emailFieldSelected = true;
			}
		}
		
		//Create Account button clicked
		if((xpos>535 && xpos<735) && (ypos>20 && ypos<50)) {
			if(Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				username = usernameField.getText();
				password1 = password1Field.getText();
				password2 = password2Field.getText();
				email = emailField.getText();
				
				//password check
				if (!(password1.equals(password2))) {
					passwordMisMatch = true;
				}
				else {
					passwordMisMatch = false;
					if (password1.length() < 5) {
						passwordLengthError = true;
					}
					else {
						passwordLengthError = false;
					}
				}
				
				//in client username check
				if (username.length() < 2) {
					usernameError = true;
				}
				else {
					usernameError = false;
				}
				
				
				//in client email check
				if (email.length() < 7) {
					emailError = true;
				}
				else {
					emailError = false;
				}
				
				//send to server for verification
				if ((!passwordMisMatch) && (!passwordLengthError) && (!usernameError) && (!emailError)) {
					//send info to server here
					//have server verify information
					//if username already taken set usernameError to true and have user try again
					//else create account and change to log in screen
					
					if (model.createAccountRequest(username, password1, email)) {
						/* Account creation success */
						
						/* Reset fields to prevent spam */
						usernameField.setText("");
						password1Field.setText("");
						password2Field.setText("");
						emailField.setText("");
						
						System.out.println("account creation success");
						sbg.enterState(0);
					} else {
						/* Account creation failed */
						System.out.println("account creation failure");
						password2Field.setText("");
					}
					
				}
				
			}
			
			if (!Mouse.isButtonDown(0)){
				Master.isMouseReleased = true;
			}
		}
		
	}
	
	public int getID() {
		return 2;
	}
}
