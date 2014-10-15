package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;

import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public class CardGame implements Serializable{

	private ArrayList<Player> players;
	private Queue<Card> deck;
	private int test(){
		
		return 0;
	}

	public CardGame(ArrayList<Player> playerNames){
		fillDeck();
		for(int i=0;i<playerNames.size();i++){
			this.players.add(playerNames.get(i));
		}
		int numPlayers = players.size();
		if(connect() != 1){
			displayError("Game failed to connect to server!");
		}
		displayTable();
	}

	private void fillDeck(){
		int randPower;
		int randSuit;
		for(int i=1;i<=52;i++){
			for(int j=0;j<=3;j++){
				randPower = (int) (Math.random()*13);
				randSuit = (int) (Math.random()*4);
				this.deck.add(new Card(randPower,randSuit));
			}
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
	
	public ArrayList<Player> getPlayers(){
		return this.players;
	}

	public int main(){
		ArrayList<Player> test = null;
		test.add(new Player("Bill","asd"));
		test.add(new Player("Bob","qwe"));
		CardGame cardGame = new CardGame(test);
		return 0;
	}
}
