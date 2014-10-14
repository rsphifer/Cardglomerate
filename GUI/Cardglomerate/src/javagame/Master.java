package javagame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import player.Player;
import servercomm.ServerAccess;

public class Master extends StateBasedGame {

	public static final String gameName = "Cardglomerate";
	
	//state numbers for each screen
	public static final int logIn = 0;
	public static final int gameMenu = 1;
	public static final int createAccount = 2;
	public static final int comingSoon = 3;
	public static final int warOptions = 4;
	public static final int warGame = 5;
	
	public Master(String gamename){
		super(gamename);
		
		//list of all states
		this.addState(new LogIn(logIn));
		this.addState(new GameMenu(gameMenu));
		this.addState(new CreateAccount(createAccount));
		this.addState(new ComingSoon(comingSoon));
		this.addState(new WarOptions(warOptions));
		this.addState(new WarGame(warGame));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		//list of all states
		this.getState(logIn).init(gc, this);
		this.getState(gameMenu).init(gc, this);
		this.getState(createAccount).init(gc, this);
		this.getState(comingSoon).init(gc, this);
		this.getState(warOptions).init(gc, this);
		this.getState(warGame).init(gc, this);
		
		//show log in screen first
		this.enterState(logIn);
	}
	
	public static void main(String[] args) {
		ServerAccess.createNewAccount(new Player());
		
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
