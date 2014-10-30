package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public abstract class CardGame implements Serializable{

	private ArrayList<Card> deck;

	public CardGame(){
		deck = new ArrayList<Card>();
		displayTable();
	}

	protected void fillDeck(){
		for(int i=1;i<=13;i++){
			for(int j=0;j<=3;j++){
				this.deck.add(new Card(i,j,i+(13*j)-1));
			}
		}
		Collections.shuffle((List<?>) this.deck);
	}
	
	protected void fillDeckHoldEm(){
		for(int k=0;k<4;k++){
			for(int i=1;i<=13;i++){
				for(int j=0;j<=3;j++){
					this.deck.add(new Card(i,j,i+(13*j)-1));
				}
			}
		}
		Collections.shuffle((List<?>) this.deck);
	}
	
	public Card getTopOfDeck(){
		if(!this.deck.isEmpty()){
			return this.deck.remove(0);
		}
		else{
			return null;
		}
	}

	private void displayTable(){

	}

	private void displayError(String error){
		System.out.println(error);
	}
	
	public abstract void setup();
	public abstract void testSetup();
	public abstract ArrayList<Player> getWinner();
	public abstract LinkedList<Integer> getHandSizes();
	public abstract void update();
	public abstract boolean getGameover();
	public abstract LinkedList<Card> getCardsToDisplay();

//	public static void main(String[] args){
//		ArrayList<Player> test = new ArrayList<Player>();
//		test.add(new Player("first","asd"));
//		test.add(new Player("second","qwe"));
//		War cardGame = new War(test);
//		cardGame.setup();
//		//cardGame.testSetup();
//		int j=0;
//		while(cardGame.update()){
//			j++;
//		}
//		System.out.println("Turns: "+j);
//	}
}
