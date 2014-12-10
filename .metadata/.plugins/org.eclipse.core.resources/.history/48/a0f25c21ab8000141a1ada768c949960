package view;

import java.util.ArrayList;

import misc.GameLobby;
import model.Model;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import cardgames.CardGameType;
import player.Player;


public class StatsPage extends BasicGameState {

	private Model model;

	private String mouse = "No input yet";
	private Image background;
	private Image image;
	private Image arrow;
	private boolean firstTime = true;

	//individual game stats
	//war
	private int warGamesPlayed = 0;
	private int warGamesWon = 0;
	private double warWinLoss = 0;
	//holdem
	private int holdemGamesPlayed = 0;
	private int holdemGamesWon = 0;
	private double holdemWinLoss = 0;
	//blackjack
	private int blackjackGamesPlayed = 0;
	private int blackjackGamesWon = 0;
	private double blackjackWinLoss = 0;
	//ratscrew
	private int ratscrewGamesPlayed = 0;
	private int ratscrewGamesWon = 0;
	private double ratscrewWinLoss = 0;
	
	/* Leader boards (wins only) */
	private ArrayList<String> warWins;
	private ArrayList<String> blackjackWins;
	private ArrayList<String> holdemWins;
	//private ArrayList<String> ersWins; /* No ers data in db */
	
	public StatsPage(int state, Model model) {
		this.model = model;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// initialize and scale background image
		background = new Image("res/Green Background.jpg");
		background = background.getScaledCopy(1280, 720);

		// initialize and scale images
		image = new Image("res/Cardglomerate 3.png");		
		image = image.getScaledCopy(250, 150);

		arrow = new Image("res/Back Arrow.jpg");
		arrow = arrow.getScaledCopy(150, 150);
		
		warWins = new ArrayList<String>();
		blackjackWins = new ArrayList<String>();
		holdemWins = new ArrayList<String>();
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// render the background
		background.draw(0, 0);

		// mouse coordinates for testing / building
		g.drawString(mouse, 10, 25);

		// render message and image
		g.drawString("Player Statistics", 565, 10);
		g.drawImage(image, 510, 40);

		// render arrow
		g.drawImage(arrow, 0, 570);
		
		//render game stats
		g.drawString("War Statistics:", 50, 185);
		g.drawString("Games Played: " + warGamesPlayed, 70, 205);
		g.drawString("Games Won: " + warGamesWon, 70, 225);
		String tmp = String.format("%.2f", warWinLoss);
		g.drawString("Win Loss Ratio: " + tmp, 70, 245);
		
		g.drawString("Holdem Statistics:", 350, 185);
		g.drawString("Games Played: " + holdemGamesPlayed, 370, 205);
		g.drawString("Games Won: " + holdemGamesWon, 370, 225);
		g.drawString("Win Loss Ratio: " + holdemWinLoss, 370, 245);
		
		g.drawString("Blackjack Statistics:", 650, 185);
		g.drawString("Games Played: " + blackjackGamesPlayed, 670, 205);
		g.drawString("Games Won: " + blackjackGamesWon, 670, 225);
		g.drawString("Win Loss Ratio: " + blackjackWinLoss, 670, 245);
		
		g.drawString("Ratscrew Statistics:", 950, 185);
		g.drawString("Games Played: " + ratscrewGamesPlayed, 970, 205);
		g.drawString("Games Won: " + ratscrewGamesWon, 970, 225);
		g.drawString("Win Loss Ratio: " + ratscrewWinLoss, 970, 245);
		
		g.drawString("War wins leader board:", 280, 480);
		for (int i=0; i<warWins.size(); i++) {
			if (i < 10) {
				g.drawString(warWins.get(i), 300, 500+20*i);
			} else {
				break;
			}
		}

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// mouse coordinates
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;

		//update stats check
		if (firstTime) {
			updateStats();
			firstTime = false;
		}
		
		// back button clicked
		if ((xpos > 0 && xpos < 150) && (ypos > 0 && ypos < 150)) {
			if (Mouse.isButtonDown(0) && Master.isMouseReleased) {
				Master.isMouseReleased = false;
				firstTime = true;
				sbg.enterState(1); // display game menu screen
			}

			if (!Mouse.isButtonDown(0)) {
				Master.isMouseReleased = true;
			}
		}

	}
	
	public void updateStats() {
		//update stat variables here
		warGamesPlayed = model.getPlayerGameNumber(CardGameType.War);
		warGamesWon = model.getPlayerWinNumber(CardGameType.War);
		warWinLoss = model.getPlayerWinRatio(CardGameType.War);
		
		blackjackGamesPlayed = model.getPlayerGameNumber(CardGameType.Blackjack);
		blackjackGamesWon = model.getPlayerWinNumber(CardGameType.Blackjack);
		blackjackWinLoss = model.getPlayerWinRatio(CardGameType.Blackjack);
		
		warWins = model.getLeaderboards(CardGameType.War);
		blackjackWins = model.getLeaderboards(CardGameType.Blackjack);
		holdemWins = model.getLeaderboards(CardGameType.TexasHoldEm);
		
		
		
	}

	public int getID() {
		return 11;
	}
}
