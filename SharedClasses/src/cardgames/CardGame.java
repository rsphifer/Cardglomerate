package cardgames;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public class CardGame implements Serializable{

	private ArrayDeque<Card> deck;

	public CardGame(){
		deck = new ArrayDeque<Card>();
		fillDeck();
		if(connect() != 1){
			displayError("Game failed to connect to server!");
		}
		displayTable();
	}

	private void fillDeck(){
		int randPower;
		int randSuit;
		for(int i=1;i<=13;i++){
			for(int j=0;j<=3;j++){
				randPower = (int) (Math.random()*13);
				randSuit = (int) (Math.random()*4);
				this.deck.add(new Card(randPower,randSuit));
			}
		}
	}
	
	public Card getTopOfDeck(){
		if(!this.deck.isEmpty()){
			return this.deck.remove();
		}
		else{
			return null;
		}
	}

	protected int connect(){
		return 1;
	}

	private void displayTable(){

	}

	private void displayError(String error){
		System.out.println(error);
	}

	public static void main(String[] args){
		ArrayList<Player> test = new ArrayList<Player>();
		test.add(new Player("Bill","asd"));
		test.add(new Player("Bob","qwe"));
		CardGame cardGame = new War(test);
	}
}
