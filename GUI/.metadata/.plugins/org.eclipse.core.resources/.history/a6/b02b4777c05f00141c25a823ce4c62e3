package view;

import model.Model;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import cardgames.CardGame;
import controller.ServerAccess;
import player.Player;

public class Master extends StateBasedGame {
	
	private Model model;

	public static final String gameName = "Cardglomerate";
	public static boolean isMouseReleased = true;
	
	//state numbers for each screen
	public static final int logIn = 0;
	public static final int gameMenu = 1;
	public static final int createAccount = 2;
	public static final int comingSoon = 3;
	public static final int warOptions = 4;
	public static final int warGame = 5;
	public static final int accountOptions = 6;
	public static final int holdEmOptions = 7;
	public static final int holdEmGame = 8;
	
	public Master(String gamename){
		super(gamename);
		
		/* Init model to talk to server and maintain player/game states */
		model = new Model();
		
		//list of all states
		this.addState(new LogIn(logIn, model));
		this.addState(new GameMenu(gameMenu, model));
		this.addState(new CreateAccount(createAccount, model));
		this.addState(new ComingSoon(comingSoon, model));
		this.addState(new WarOptions(warOptions, model));
		this.addState(new WarGame(warGame, model));
		this.addState(new AccountOptions(accountOptions, model));
		this.addState(new HoldEmOptions(holdEmOptions, model));
		this.addState(new HoldEmGame(holdEmGame, model));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		//list of all states
		this.getState(logIn).init(gc, this);
		this.getState(gameMenu).init(gc, this);
		this.getState(createAccount).init(gc, this);
		this.getState(comingSoon).init(gc, this);
		this.getState(warOptions).init(gc, this);
		this.getState(warGame).init(gc, this);
		this.getState(accountOptions).init(gc, this);
		this.getState(holdEmOptions).init(gc, this);
		this.getState(holdEmGame).init(gc, this);
		
		//screen to display first
		this.enterState(gameMenu);
	}
	
	/* Overrides default close operation when close is selected */
	@Override
	public boolean closeRequested() {
		/* If client is logged in, log the user out before exiting. */
		if (model.isLoggedIn) {
			model.logout();
		}
		System.exit(0);
		return false;
	}
	
	public static void main(String[] args) {
		
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Master(gameName));
			appgc.setDisplayMode(1280,720,false);  //window size, false means no fullscreen
			
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}

	}

}
