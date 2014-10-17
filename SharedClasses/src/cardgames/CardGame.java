package cardgames;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public class CardGame implements Serializable{

	private ArrayList<Card> deck;

	public CardGame(){
		deck = new ArrayList<Card>();
		if(connect() != 1){
			displayError("Game failed to connect to server!");
		}
		displayTable();
	}

	protected void fillDeck(){
		for(int i=1;i<=13;i++){
			for(int j=0;j<=3;j++){
				this.deck.add(new Card(i,j,i+(13*j)-1));
			}
		}
		Collections.shuffle((List<?>) this.deck);
		/*int j=0;
		for(Card c : this.deck){
			System.out.println(j+" card is "+c.getPower()+" of "+c.getSuit());
			j++;
		}*/
	}
	
	public Card getTopOfDeck(){
		if(!this.deck.isEmpty()){
			return this.deck.remove(0);
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
		test.add(new Player("first","asd"));
		test.add(new Player("second","qwe"));
		War cardGame = new War(test);
		cardGame.setup();
		//cardGame.testSetup();
		int j=0;
		while(cardGame.update()){
			j++;
		}
		System.out.println("Turns: "+j);
	}
}
