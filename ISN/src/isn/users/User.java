package isn.users;

import java.util.ArrayList;

import isn.cards.Card;

public abstract class User {

	ArrayList<Card> cards = new ArrayList<Card>();
	
	public void addCard(Card toAdd){
		cards.add(toAdd);
	}
	
	public void clearHand(){
		cards.clear();
	}
	
	public void remove(Card toRemove){
		cards.remove(toRemove);
	}
	
	public abstract void playTurn(Object... data);
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
}
