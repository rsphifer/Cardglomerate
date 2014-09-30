public class Player{

	public Player(Queue startingHand, int cash, String playerName){
		private Queue hand = startingHand;
		//private Queue deck;
		//Card[] discard = new Card[52];
		private int money = cash;
		private String name = playerName;
	}

	public int addCardToHand(Card newCard){
		this.hand.add(newCard);
	}

	public int playTopCard(Card newCard){
		this.hand.remove();
	}
}