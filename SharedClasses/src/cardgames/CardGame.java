package cardgames;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import misc.ChatEntry;
import player.Player;
import cards.Card;

@SuppressWarnings("serial")
public abstract class CardGame implements Serializable{
	
	private static final int MAX_CHAT_ENTRIES = 16;

	private ArrayList<Card> deck;
	private ArrayList<ChatEntry> chatLog;

	public CardGame(){
		deck = new ArrayList<Card>();
		chatLog = new ArrayList<ChatEntry>();
		displayTable();
	}
	
	public void addChatEntry(ChatEntry newEntry) {
		
		/* Insert message at front of list */
		chatLog.add(chatLog.size(), newEntry);
	
		/* Remove oldest messages until under max entries allowed */
		while (chatLog.size() > MAX_CHAT_ENTRIES) {
			chatLog.remove(0);
		}
		
	}
	
	public ArrayList<ChatEntry> getChatLog() {
		return chatLog;
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
		this.deck.clear();
		for(int i=1;i<=13;i++){
			for(int j=0;j<=3;j++){
				this.deck.add(new Card(i,j,i+(13*j)-1));
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
	public abstract ArrayList<Player> getPlayers();
	public abstract Player getTurn();
	public abstract int getPot();
	public abstract int getExpectedBet();
	public abstract boolean updateReady();
	public abstract void setBet(Player p, int bet);
	public abstract void nextTurn(Player curP);
	public abstract void fold(Player p);
	public abstract boolean isHandOver();
	public static void main(String[] args){

	}
}
