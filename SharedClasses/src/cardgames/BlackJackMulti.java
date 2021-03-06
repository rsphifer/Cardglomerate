package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import player.Player;
import cards.Card;

public class BlackJackMulti extends CardGame implements Serializable {
	


	private ArrayList<Card> dealerHand;
	int dealerStay;
	
	//These are all linked together The arrays order determines the player it corresponds
	private ArrayList<Player> playersPlaying;
	private ArrayList<Integer> playersPlayingBet;
	private ArrayList<Integer> playersPlayingMoney;
	private ArrayList<Integer> stayOrHitting;
	
	
	public BlackJackMulti(ArrayList<Player> names){
		
		//Initialize Players and their money and their state
		playersPlaying = new ArrayList<Player>();
		playersPlayingMoney = new ArrayList<Integer>();
		playersPlayingBet = new ArrayList<Integer>();
		stayOrHitting = new ArrayList<Integer>();
		for (int i=0; i<names.size(); i++) {
			playersPlaying.add(names.get(i));
			playersPlayingMoney.add(500);
			playersPlayingBet.add(0);
			//stayOrHitting initialized to 0 which = hit
			stayOrHitting.add(0);
		}
		dealerHand = new ArrayList<Card>();
		dealerStay = 0;
		
		askAllToBuyIn();
		fillDeck();
		dealCards();
		
		//testing
		//seeAllPlayersHands();
		allPlayersPlayAllHands();
		seeAllPlayersHands();
		dealerPlayAll();
		getAllWinners();
	}
	public void resetGame(){
		dealerHand = new ArrayList<Card> ();
		for(int i = 0; i < playersPlaying.size();i++){
			playersPlaying.get(i).emptyHand();
			playersPlayingBet.set(i,0);
			stayOrHitting.set(i, 0);
		}
		askAllToBuyIn();
		fillDeck();
		dealCards();
		
	}
	
	
	public int playerIndex(Player aPlayer){
		int numPlayers = playersPlaying.size();
		int index = 0;
		
		for(int i = 0; i < numPlayers; i++){
			if(aPlayer.userName.equals(playersPlaying.get(i).userName)){
				index = i;
			}
		}
		return index;
	}
	
	//remove player
	
	public void removePlayer(Player aPlayer){
		playersPlaying.remove(playerIndex(aPlayer));
		playersPlayingBet.remove(playerIndex(aPlayer));
		playersPlayingMoney.remove(playerIndex(aPlayer));
		stayOrHitting.remove(playerIndex(aPlayer));
	}
	
	//add player
	public void addPlayer(Player aPlayer){
		playersPlaying.add(aPlayer);
		playersPlayingBet.add(0);
		playersPlayingMoney.add(500);
		stayOrHitting.add(0);
	}
	
	//ask player to buy in
	public void askForBuyIn(Player aPlayer){
		//If the player has no money, he is given an extra 500
		if(playersPlayingMoney.get(playerIndex(aPlayer)) < 1){
			playersPlayingMoney.set(playerIndex(aPlayer),500);
		}
		
		//ask to bet
		int valueEntered = 0;
		while(valueEntered == 0){
			System.out.println(aPlayer.userName+" please bet an amount greater than 0 but no more than "+playersPlayingMoney.get(playerIndex(aPlayer)));
			Scanner playerInput = new Scanner( System.in );
			valueEntered = playerInput.nextInt();
			if(valueEntered > 0 && valueEntered < playersPlayingMoney.get(playerIndex(aPlayer))){
				playersPlayingMoney.set(playerIndex(aPlayer),playersPlayingMoney.get(playerIndex(aPlayer))-valueEntered);
				playersPlayingBet.set(playerIndex(aPlayer),valueEntered);
				System.out.println(aPlayer.userName+" bet "+valueEntered+" and have "+playersPlayingMoney.get(playerIndex(aPlayer)));
			}
			else {
				valueEntered = 0;
			}
		}
	}
	
	
	//ask all players Playing to buy in
	public void askAllToBuyIn(){
		int numPlayers = playersPlaying.size();
		
		for(int i = 0; i < numPlayers; i++){
			askForBuyIn(playersPlaying.get(i));
		}
	}
	
	//deals the cards to all the players in the game
	public void dealCards(){
		int numPlayers = playersPlaying.size();
		
		//1 card to each player
		for(int i = 0; i < numPlayers; i++){
			playersPlaying.get(i).addCardToHand(getTopOfDeck());
		}
		
		//1 card to dealer
		dealerHand.add(getTopOfDeck());
		//1 card to the player again
		for(int i = 0; i < numPlayers; i++){
			playersPlaying.get(i).addCardToHand(getTopOfDeck());
		}
		//1 card to dealer again
		dealerHand.add(getTopOfDeck());
	}
	
	public void seePlayerHand(Player aPlayer){
		int sizeOfHand = aPlayer.getHandSize();
		System.out.println(aPlayer.userName);
		for(int i = 0; i < sizeOfHand;i++){
			System.out.println("Card is Suit ="+aPlayer.getHand().get(i).getSuit()+"Power"+aPlayer.getHand().get(i).getPower());
		}	
		System.out.println();
		System.out.println(aPlayer.userName+" combined hand is = to "+getPlayerScoreWithAces(aPlayer));
		System.out.println();
	}
	
	public void seeAllPlayersHands(){
		for(int i = 0; i < playersPlaying.size();i++){
			seePlayerHand(playersPlaying.get(i));
		}
	}
	
	public int getPlayerScore(Player aPlayer){
		int sizeOfHand = aPlayer.getHandSize();
		int handScore = 0;
		int power;
		for(int i = 0; i < sizeOfHand;i++){
			power = aPlayer.getHand().get(i).getPower();
			if(power > 10){
				handScore += 10;
			}
			else if(power == 1){
				handScore += 11;
			}
			else
				handScore += power;
		}
		return handScore;
	}
	
	public int getPlayerScoreWithAces(Player aPlayer){
		if(getPlayerScore(aPlayer) < 22){
			return getPlayerScore(aPlayer);
		}
		int numAces = 0;
		int sizeOfHand = aPlayer.getHandSize();
		int power;
		for(int i = 0; i < sizeOfHand;i++){
			power = aPlayer.getHand().get(i).getPower();
			if(power == 1){
				numAces += 1;
			}
		}
		int newScore = getPlayerScore(aPlayer);
		while(numAces != 0){
			newScore -= 10;
			numAces--;
			if(newScore <= 21){
				return newScore;
			}
		}
		return newScore;
	}
	
	public int getDealerScore(){
		int sizeOfHand = dealerHand.size();
		int handScore = 0;
		int power;
		for(int i = 0; i < sizeOfHand;i++){
			power = dealerHand.get(i).getPower();
			if(power > 10){
				handScore += 10;
			}
			else if(power == 1){
				handScore += 11;
			}
			else
				handScore += power;
		}
		return handScore;
	}
	
	public int getDealerScoreWithAces(){
		if(getDealerScore() < 22){
			return getDealerScore();
		}
		int numAces = 0;
		int sizeOfHand = dealerHand.size();
		int power;
		for(int i = 0; i < sizeOfHand;i++){
			power = dealerHand.get(i).getPower();
			if(power == 1){
				numAces += 1;
			}
		}
		int newScore = getDealerScore();
		while(numAces != 0){
			newScore -= 10;
			numAces--;
			if(newScore <= 21){
				return newScore;
			}
		}
		return newScore;
	}
	
	public void aPlayerPlay(Player aPlayer){
		int breaker = 0;
		if(getPlayerScoreWithAces(aPlayer) <= 21){
			if(stayOrHitting.get(playerIndex(aPlayer)) != 1){
				seePlayerHand(aPlayer);
				System.out.println();
				int valueEntered = 0;
				while(breaker == 0){
					System.out.println("To Hit Press 1, To Stay press 2");
					Scanner player1Input = new Scanner( System.in );
					valueEntered = player1Input.nextInt();
					if(valueEntered == 1){
						System.out.println("value entered = "+valueEntered);
						aPlayer.addCardToHand(getTopOfDeck());
						breaker = 1;
					}
					else if(valueEntered == 2){
						System.out.println("value entered = "+valueEntered);
						stayOrHitting.set(playerIndex(aPlayer),1);
						breaker = 1;
					}
				}
			}
		}
		else 
			stayOrHitting.set(playerIndex(aPlayer),1);
	
	}
	public void aPlayerPlaysAllHand(Player aPlayer){
		while(stayOrHitting.get(playerIndex(aPlayer)) == 0){
			aPlayerPlay(aPlayer);
		}
	}
	
	public void allPlayersPlayAllHands(){
		for(int i = 0; i < playersPlaying.size();i++){
			aPlayerPlaysAllHand(playersPlaying.get(i));
		}
	}

	public void dealerPlay(){
		if(getDealerScoreWithAces() <= 21){
			int dealerScore = getDealerScoreWithAces();
			if(dealerScore < 18){
				dealerHand.add(getTopOfDeck());
			}
			else
				dealerStay = 1;
		}
		else
			dealerStay = 1;
	}
	
	public void dealerPlayAll(){
		while(dealerStay == 0){
			dealerPlay();
		}
	}
	
	public void getWinnerByPlayer(Player aPlayer){
		int playerScore = getPlayerScoreWithAces(aPlayer);
		int dealerScore = getDealerScoreWithAces();
		
		System.out.println("DEALER SCORE = "+dealerScore);
		
		if(playerScore > 21){
			System.out.println(aPlayer.userName+" lost agains dealer "+playersPlayingBet.get(playerIndex(aPlayer))+" Total now = "+playersPlayingMoney.get(playerIndex(aPlayer)));
		}
		else if(playerScore == dealerScore){
			int newTotal = playersPlayingMoney.get(playerIndex(aPlayer));
			newTotal = newTotal + playersPlayingBet.get(playerIndex(aPlayer));
			playersPlayingMoney.set(playerIndex(aPlayer),newTotal);
			System.out.println(aPlayer.userName+" draw agains dealer "+playersPlayingBet.get(playerIndex(aPlayer))+" Total now = "+playersPlayingMoney.get(playerIndex(aPlayer)));
		}
		else if(dealerScore > playerScore && dealerScore <= 21){
			System.out.println(aPlayer.userName+" lost agains dealer "+playersPlayingBet.get(playerIndex(aPlayer))+" Total now = "+playersPlayingMoney.get(playerIndex(aPlayer)));
		}
		else{
			int newTotal = playersPlayingMoney.get(playerIndex(aPlayer));
			newTotal = newTotal + 2*playersPlayingBet.get(playerIndex(aPlayer));
			playersPlayingMoney.set(playerIndex(aPlayer),newTotal);
			System.out.println(aPlayer.userName+" won agains dealer a sum of"+playersPlayingBet.get(playerIndex(aPlayer))+" Total now = "+playersPlayingMoney.get(playerIndex(aPlayer)));
		}
		
	}
	
	public void getAllWinners(){
		for(int i = 0; i < playersPlaying.size();i++){
			getWinnerByPlayer(playersPlaying.get(i));
		}
	}
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testSetup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Player> getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Integer> getHandSizes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getGameover() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<Card> getCardsToDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPot() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getExpectedBet() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBet(Player p, int bet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextTurn(Player curP) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fold(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isHandOver() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String[] args){
		Player player1 = new Player("Santi");
		Player player2 = new Player("Dom");
		Player player3 = new Player("Deven");
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		BlackJackMulti test1 =  new BlackJackMulti(players);
		
	}

}
