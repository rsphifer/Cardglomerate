package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public class War extends CardGame implements Serializable{


	private ArrayList<Player> players;
	private LinkedList<Card> winnings;
	private LinkedList<Card> cardsToDisplay;
	private CardGame cardGame;
	private boolean gameOver = false;
	
	public War(ArrayList<Player> playerNames){
		this.players = playerNames;
		winnings = new LinkedList<Card>();
		cardsToDisplay = new LinkedList<Card>();
		this.cardGame = new CardGame();
		cardGame.fillDeck();
		cardGame.connect();
		play();
	}

	public LinkedList<Card> getCardsToDisplay(War game){
		return game.cardsToDisplay;
	}

	public boolean getGameover(War game){
		return game.gameOver;
	}

	private void roundWin(Player winner){
		while(!this.winnings.isEmpty()){
			winner.addCardToDiscard((Card)this.winnings.remove(0));
		}
	}

	private void roundTie(){
		System.out.println("WAR!");
		
		Card p1Down = this.players.get(0).playTopCard();
		this.winnings.push(p1Down);
		Card p1Up = this.players.get(0).playTopCard();
		this.winnings.push(p1Up);
		
		Card p2Down = this.players.get(1).playTopCard();
		this.winnings.push(p2Down);
		Card p2Up = this.players.get(1).playTopCard();
		this.winnings.push(p2Up);

		this.score(p1Up,p2Up);
	}

	private void score(Card c1, Card c2){
		if(c1==null || c2==null) {
			this.gameOver = true;
			return;
		}
		while(!cardsToDisplay.isEmpty()){
			cardsToDisplay.remove();
		}
		cardsToDisplay.add(c1);
		cardsToDisplay.add(c2);
		
		//System.out.println(c1.getPower()+" of "+c1.getSuit()+" vs "+c2.getPower()+" of "+c2.getSuit());
		
		if(c1.getPower() > c2.getPower()){
			roundWin(this.players.get(0));
		} else if(c1.getPower() < c2.getPower()){
			roundWin(this.players.get(1));
		} else {
			roundTie();
		}
	}

	private void win(Player winner){
		System.out.println(winner.userName+" won!");
	}

	public void play(){
		/*for(int i=1;i<=13;i++){
			this.players.get(0).addCardToHand(new Card(i,0));
		}
		for(int i=1;i<=13;i++){
			this.players.get(0).addCardToHand(new Card(i,1));
		}
		for(int i=1;i<=13;i++){
			this.players.get(0).addCardToHand(new Card(i,2));
		}
		for(int i=2;i<=13;i++){
			this.players.get(0).addCardToHand(new Card(i,3));
		}
		this.players.get(1).addCardToHand(new Card(1,3));
		*/
		
		for(int i=0;i<52;i++){
			if(i%2 == 0){
				this.players.get(0).addCardToHand(this.cardGame.getTopOfDeck());
			} else {
				this.players.get(1).addCardToHand(this.cardGame.getTopOfDeck());
			}
		}
		int j=0;
		Card p1Card;
		Card p2Card;
		while(!gameOver){
			for(Player p : this.players){
				System.out.print(p.getHandSize()+"   ");
			}
			System.out.println();
			j++;
			if((p1Card = this.players.get(0).playTopCard())!=null){
				this.winnings.push(p1Card);
			} else {
				this.gameOver = true;
			}
			if((p2Card = this.players.get(1).playTopCard())!=null){
				this.winnings.push(p2Card);
			} else {
				this.gameOver = true;
			}
			
			this.score(p1Card, p2Card);
		}
		System.out.println("Turns: "+j);
		if(this.players.get(0).getHandSize() == 0){
			this.win(this.players.get(1));
		} else {
			this.win(this.players.get(0));
		}
	}
}
