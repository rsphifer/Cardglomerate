public class War extends Game{

	public War(String[] playerNames){
		Game game = new Game(52,2,playerNames);
		Player p1 = new Player(playerNames[0]);
		Player p2 = new Player(playerNames[1]);
		Stack winnings;
		game.connect();
		play();
	}

	private roundWin(Player winner){
		while(!winnings.empty()){
			winner.hand.add(winnings.remove());
		}
	}

	private void roundTie(){
		Card p1Down = this.p1.hand.playTopCard();
		this.winnings.push(p1Down);
		Card p1Up = this.p1.hand.playTopCard();
		this.winnings.push(p1Up);
		Card p2Down = this.p2.hand.playTopCard();
		this.winnings.push(p2Down);
		Card p2Up = this.p2.hand.playTopCard();
		this.winnings.push(p2Up);

		this.score(p1Up, p2Up);
	}

	private void score(Card c1, Card c2){
		if(c1.getPower() > c2.getPower()){
			roundWin(this.p1);
		} else if(c1.getPower() < c2.getPower()){
			roundWin(this.p2);
		} else {
			roundTie();
		}
	}

	private void win(Player winner){
		// tell server who won
	}

	public void play(){

		while(this.p1.hand.length != 0 || this.p2.hand.length != 0){
			Card p1Card = this.p1.hand.playTopCard();
			this.winnings.push(p1Card);
			Card p2Card = this.p2.hand.playTopCard();
			this.winnings.push(p2Card);

			this.score(p1Card, p2Card);
		}
		if(this.p1.hand.empty()){
			this.win(p2);
		} else {
			this.win(p1);
		}
	}
}