import java.util.Queue;

public class Game{

	private int test(){
		return 0;
	}

	public Game(String[] playerNames){
		Queue deck;
		fillDeck();
		String[] players = playerNames;
		int numPlayers = players.size;
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
				randPower = Math.random(53);
				randSuit = Math.random(4);
				deck.add(Card card = new Card(randPower,randSuit));
			}
		}

	}

	private int connect(){

		return 1;
	}

	private void displayTable(){

	}

	private void displayError(){
		
	}

	public int main(){
		Game game = new Game(52,["Bill","Bob"]);
	}
}