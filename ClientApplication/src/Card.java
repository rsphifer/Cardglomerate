public class Card{

	private int power;
	private int suit;

	public Card(int power, int suit){
		this.power = power;
		this.suit = suit;
	}

	public int getPower(){
		return this.power;
	}

	public int getSuit(){
		return this.suit;
	}

}