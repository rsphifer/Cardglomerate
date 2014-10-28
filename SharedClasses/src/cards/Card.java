package cards;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Card implements Serializable{

	private int power;
	private int suit;
	private int index;

	public Card(int power, int suit, int index){
		this.power = power;
		this.suit = suit;
		this.index = index;
	}

	public int getIndex(){
		return this.index;
	}
	
	public int getPower(){
		return this.power;
	}

	public int getSuit(){
		return this.suit;
	}
	
    public int compareTo(Card card) {
        int thisValue = hashCode();
        int otherValue = card.hashCode();
        if (thisValue < otherValue) {
            return -1;
        } else if (thisValue > otherValue) {
            return 1;
        } else {
            return 0;
        }
    }
}
