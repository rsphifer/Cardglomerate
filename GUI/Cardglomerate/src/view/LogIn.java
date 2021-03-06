package view;

import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LogIn extends BasicGameState {

	private Model model;

	private String mouse = "No input yet";
	private Image logo;
	private Image background;
	private TextField usernameField;
	// private TextField passwordField;
	private PasswordTextField passwordField;
	private TextField emailField;
	private String username;
	private String password;
	private String email;
	private boolean logInError = false;
	private boolean emailError = false;
	private boolean usernameFieldSelected = true;
	private boolean passwordFieldSelected = false;
	private boolean emailFieldSelected = false;
	private boolean forgotClicked = false;

	private boolean loginButtonFocused = false;
	private boolean forgotPassButtonFocused = false;
	private boolean createAccountButtonFocused = false;
	private boolean sendEmailButtonFocused = false;

	private boolean loginButtonPressed = false;
	private boolean forgotPassButtonPressed = false;
	private boolean createAccButtonPressed = false;
	private boolean sendEmailButtonPressed = false;

	public LogIn(int state, Model model) {
		this.model = model;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);

		// initialize and scale logo image
		logo = new Image("res/Cardglomerate 1.png");
		logo = logo.getScaledCopy(530, 390);

		// initialize textfields
		usernameField = new TextField(gc, gc.getDefaultFont(), 535, 425, 200,
				30);
		passwordField = new PasswordTextField(gc, gc.getDefaultFont(), 535,
				490, 200, 30);
		passwordField.setMaskCharacter('*');
		passwordField.setMaskEnabled(true);
		emailField = new TextField(gc, gc.getDefaultFont(), 750, 605, 200, 30);

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// render the background
		background.draw(0, 0);

		// mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);

		// render logo
		logo.draw(375, 0);

		// render text
		g.drawString("Username", 600, 400);
		g.drawString("Password", 600, 465);

		// render textfields
		usernameField.render(gc, g);
		passwordField.render(gc, g);

		Color originalColor = g.getColor();
		// log in button
		g.drawRect(535, 550, 200, 40);
		g.drawString("Log In", 600, 560);

		if (loginButtonFocused) {
			g.setColor(Color.darkGray);
			g.drawRect(534, 549, 200, 40);
			g.drawString("Log In", 599, 559);
			g.setColor(originalColor);
		}

		// log in error
		if (logInError) {
			g.drawString("Please enter a VALID username and password", 750, 560);
		}

		// email error
		if (emailError) {
			g.drawString("Error: Reenter Email", 960, 655);
		}

		// forgot password button
		g.drawRect(535, 605, 200, 30);
		g.drawString("Forgot Password", 570, 610);

		if (forgotPassButtonFocused) {
			g.setColor(Color.darkGray);
			g.drawRect(534, 604, 200, 30);
			g.drawString("Forgot Password", 569, 609);
			g.setColor(originalColor);
		}

		// create account button
		g.drawRect(535, 650, 200, 30);
		g.drawString("Create Account", 570, 655);

		if (createAccountButtonFocused) {
			g.setColor(Color.darkGray);
			g.drawRect(534, 649, 200, 30);
			g.drawString("Create Account", 569, 654);
			g.setColor(originalColor);
		}

		// forgot password clicked
		if (forgotClicked) {
			emailField.render(gc, g);
			g.drawString("Enter Email Address", 960, 610);
			g.drawRect(750, 650, 200, 30);
			g.drawString("Email Password", 785, 655);
			g.drawString("Please enter username to recover password", 740, 430);
			if (sendEmailButtonFocused) {
				g.setColor(Color.darkGray);
				g.drawString("Email Password", 784, 654);
				g.drawRect(749, 649, 200, 30);
				g.setColor(originalColor);
			}

			if (!forgotClicked) {
				g.clear();
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;

		// controls which textfield is currently selected
		// janky solution because textfields not working properly
		if (usernameFieldSelected) {
			usernameField.setFocus(true);
		}
		if (passwordFieldSelected) {
			passwordField.setFocus(true);
		}
		if (emailFieldSelected) {
			emailField.setFocus(true);
		}

		Input in = gc.getInput();
		if (in.isKeyPressed(Input.KEY_TAB)) {
			if (usernameFieldSelected) {
				usernameFieldSelected = false;
				usernameField.setFocus(false);
				if (forgotClicked) {
					emailFieldSelected = true;
					emailField.setFocus(true);
				} else {
					passwordFieldSelected = true;
					passwordField.setFocus(true);
				}
			} else if (passwordFieldSelected) {
				passwordFieldSelected = false;
				loginButtonFocused = true;
				passwordField.setFocus(false);
			} else if (loginButtonFocused) {
				loginButtonFocused = false;
				forgotPassButtonFocused = true;
			} else if (forgotPassButtonFocused) {
				forgotPassButtonFocused = false;
				createAccountButtonFocused = true;
			} else if (createAccountButtonFocused) {
				createAccountButtonFocused = false;
				usernameFieldSelected = true;
				usernameField.setFocus(true);
			} else if (emailFieldSelected) {
				emailFieldSelected = false;
				emailField.setFocus(false);
				sendEmailButtonFocused = true;
			} else if (sendEmailButtonFocused) {
				sendEmailButtonFocused = false;
				usernameFieldSelected = true;
				usernameField.setFocus(true);
			}
		}

		if (in.isKeyPressed(Input.KEY_ENTER)) {
			if (loginButtonFocused) {
				loginButtonPressed = true;
			} else if (forgotPassButtonFocused) {
				forgotPassButtonPressed = true;
			} else if (createAccountButtonFocused) {
				createAccButtonPressed = true;
			} else if (sendEmailButtonFocused) {
				sendEmailButtonPressed = true;
			} else if (passwordFieldSelected) {
				loginButtonPressed = true;
			}
		}

		// username textbox clicked
		if ((xpos > 535 && xpos < 735) && (ypos > 265 && ypos < 295)) {
			if (Mouse.isButtonDown(0)) { // button 0 = left click
				loginButtonFocused = false;
				forgotPassButtonFocused = false;
				createAccountButtonFocused = false;

				passwordFieldSelected = false;
				emailFieldSelected = false;
				usernameFieldSelected = true;
			}
		}
		// password textbox clicked
		if ((xpos > 535 && xpos < 735) && (ypos > 200 && ypos < 230)) {
			if (Mouse.isButtonDown(0)) {
				loginButtonFocused = false;
				forgotPassButtonFocused = false;
				createAccountButtonFocused = false;

				usernameFieldSelected = false;
				emailFieldSelected = false;
				passwordFieldSelected = true;
			}
		}
		// email textbox clicked
		if ((xpos > 750 && xpos < 950) && (ypos > 85 && ypos < 115)) {
			if (Mouse.isButtonDown(0)) {
				loginButtonFocused = false;
				forgotPassButtonFocused = false;
				createAccountButtonFocused = false;

				usernameFieldSelected = false;
				passwordFieldSelected = false;
				emailFieldSelected = true;
			}
		}

		// log in button clicked
		if (loginButtonPressed
				|| ((xpos > 535 && xpos < 735) && (ypos > 130 && ypos < 170))) {
			if (loginButtonPressed
					|| (Mouse.isButtonDown(0) && Master.isMouseReleased)) {
				Master.isMouseReleased = false;
				loginButtonPressed = false;
				username = usernameField.getText();
				password = passwordField.getText();
				if ((username.length() > 1) && (password.length() > 1)) {
					// code to send username and password needs to be here
					// if valid then enter next state
					// if not set logInError to true

					if (model.attemptLogin(username, password)) {
						/* attempt successful */
						System.out.println("login attempt success");
						usernameField.setText("");
						passwordField.setText("");
						sbg.enterState(1); // go to game menu screen state
					} else {
						/* attempt failed */
						System.out.println("login attempt failed");
						logInError = true;
					}

				} else {
					logInError = true;
				}
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}

		}

		// forgot password button clicked
		if (forgotPassButtonPressed
				|| ((xpos > 535 && xpos < 735) && (ypos > 85 && ypos < 115))) {

			if (forgotPassButtonPressed || Mouse.isButtonDown(0)) {
				forgotPassButtonFocused = false;
				usernameField.setFocus(true);
				usernameFieldSelected = true;
				forgotClicked = true;
			}
			forgotPassButtonPressed = false;
		}

		// send email button clicked
		if (sendEmailButtonPressed
				|| ((xpos > 750 && xpos < 950) && (ypos > 40 && ypos < 70) && (forgotClicked))) {
			if (sendEmailButtonPressed
					|| (Mouse.isButtonDown(0) && Master.isMouseReleased)) {
				Master.isMouseReleased = false;
				String uname = usernameField.getText();
				email = emailField.getText();
				if (email.length() > 1 && uname.length() > 1) {
					// code to send email address to server goes here
					// if valid and email sent do following
					// else set email error to true
					model.requestPassword(uname, email);

					emailError = false;
					forgotClicked = false;
					emailField.setText("");
				} else {
					emailError = true;
				}
			}
			sendEmailButtonPressed = false;

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}

		}
		// Create Account button clicked
		if (createAccButtonPressed
				|| ((xpos > 535 && xpos < 735) && (ypos > 40 && ypos < 70))) {
			if (createAccButtonPressed || Mouse.isButtonDown(0)) {
				sbg.enterState(2);
			}
			createAccButtonPressed = false;
		}

	}

	public int getID() {
		return 0;
	}

}
