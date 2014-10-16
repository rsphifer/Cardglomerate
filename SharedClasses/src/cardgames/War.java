package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public class War extends CardGame implements Serializable{

	public War(ArrayList<Player> playerNames) {
		super(playerNames);
	}

	private ArrayList<Player> players;
	private LinkedList<Card> winnings;
	private CardGame cardGame;
	private boolean gameOver = false;
	
<<<<<<< HEAD
	public War(ArrayList<Player> playerNames){
		this.players = playerNames;
		winnings = new LinkedList<Card>();
		this.cardGame = new CardGame();
		cardGame.connect();
		play();
	}
=======

>>>>>>> 1df86365017ae4f6895df380ca0591b77fcbe08d

	private void roundWin(Player winner){
		while(!this.winnings.isEmpty()){
			winner.addCardToHand((Card)this.winnings.remove(0));
		}
	}

	private void roundTie(){
		//model.war();
<<<<<<< HEAD
		System.out.println("WAR!");
=======
		
>>>>>>> 1df86365017ae4f6895df380ca0591b77fcbe08d
		Card p1Down = this.players.get(0).playTopCard();
		this.winnings.push(p1Down);
		Card p1Up = this.players.get(0).playTopCard();
		this.winnings.push(p1Up);
		
		Card p2Down = this.players.get(1).playTopCard();
		this.winnings.push(p2Down);
		Card p2Up = this.players.get(1).playTopCard();
		this.winnings.push(p2Up);
		
		//Thread.sleep(3000);

		this.score(p1Up,p2Up);
	}

	private void score(Card c1, Card c2){
<<<<<<< HEAD
		//model.displayThese2Cards(c1,c2,h1size,h2size);
		if(c1==null) {
			this.gameOver = true;
			return;
		}
		if(c2==null) {
			this.gameOver = true;
			return;
		}
=======
		//model.displayThese2Cards(c1,c2);
>>>>>>> 1df86365017ae4f6895df380ca0591b77fcbe08d
		
		if(c1.getPower() > c2.getPower()){
			roundWin(this.players.get(0));
		} else if(c1.getPower() < c2.getPower()){
			roundWin(this.players.get(1));
		} else {
			roundTie();
		}
	}

	private void win(Player winner){
		//model.win(winner.userName);
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
				this.players.get(1).addCardToHand(this.cardGame.getTopOfDeck());
			} else {
				this.players.get(0).addCardToHand(this.cardGame.getTopOfDeck());
			}
		}
		int j=0;
		while(!gameOver){
			System.out.println("Turn: "+(j++));
			Card p1Card = this.players.get(0).playTopCard();
			this.winnings.push(p1Card);
			Card p2Card = this.players.get(0).playTopCard();
			this.winnings.push(p2Card);
<<<<<<< HEAD
			/*while(model.isWaiting()){
				Thread.sleep(1000);
			}*/
=======
			//while(model.isWaiting()){
			//	Thread.sleep(1000);
			//}
>>>>>>> 1df86365017ae4f6895df380ca0591b77fcbe08d
			this.score(p1Card, p2Card);
			if(this.players.get(0).getHandSize() == 0 || this.players.get(1).getHandSize() == 0){
				this.gameOver = true;
			}
		}
		if(this.players.get(0).getHandSize() == 0){
			this.win(this.players.get(1));
		} else {
			this.win(this.players.get(0));
		}
	}
}
