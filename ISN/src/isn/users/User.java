package isn.users;

import java.util.ArrayList;

import isn.cards.Card;
import isn.cards.blackjack.BJHand;

public abstract class User {
	
	/**
	 * La main normal
	 */
	public BJHand hand = new BJHand();
	/**
	 * La main en split
	 */
	public BJHand handsplit = new BJHand();
	
	/**
	 * @param toAdd
	 * 
	 * Ajouter la carte toAdd a' la main
	 */
	public void addCard(Card toAdd){
		hand.cards.add(toAdd);
	}
	
	/**
	 * Jete la main
	 */
	public void clearHand(){
		hand.cards.clear();
		handsplit.cards.clear();
	}
	
	/**
	 * @param toRemove
	 * 
	 * Enleve la carte toRemove de la main
	 */
	public void remove(Card toRemove){
		hand.cards.remove(toRemove);
	}
	
	/**
	 * Effectuer un split
	 */
	public void split(){
		handsplit.cards.add(hand.cards.get(1));
		hand.cards.remove(1);
	}
	
	/**
	 * @return La main
	 */
	public ArrayList<Card> getCards() {
		return hand.cards;
	}
	
}
