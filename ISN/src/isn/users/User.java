package isn.users;

import java.util.ArrayList;

import isn.cards.Card;
import isn.cards.blackjack.BJHand;

//Classe qui relie les differents utilisateurs
public abstract class User {
	
	public BJHand hand = new BJHand();
	public BJHand handsplit = new BJHand();
	
	//Ajouter une carte de la main
	public void addCard(Card toAdd){
		hand.cards.add(toAdd);
	}
	
	//Jeter la main
	public void clearHand(){
		hand.cards.clear();
		handsplit.cards.clear();
	}
	
	//Enleve une carte de la main
	public void remove(Card toRemove){
		hand.cards.remove(toRemove);
	}
	
	public void split(){
		handsplit.cards.add(hand.cards.get(1));
		hand.cards.remove(1);
	}
	
	//Jouer le tour
	public abstract void playTurn(Object... data);
	
	//Renvoie la main
	public ArrayList<Card> getCards() {
		return hand.cards;
	}
	
}
