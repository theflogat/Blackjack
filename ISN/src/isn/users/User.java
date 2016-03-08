package isn.users;

import java.util.ArrayList;

import isn.cards.Card;

//Classe qui relie les différents utilisateurs
public abstract class User {

	ArrayList<Card> cards = new ArrayList<Card>();
	
	//Ajouter une carte à la main
	public void addCard(Card toAdd){
		cards.add(toAdd);
	}
	
	//Jeter la main
	public void clearHand(){
		cards.clear();
	}
	
	//Enlève une carte à la main
	public void remove(Card toRemove){
		cards.remove(toRemove);
	}
	
	//Jouer le tour
	public abstract void playTurn(Object... data);
	
	//Renvoie la main
	public ArrayList<Card> getCards() {
		return cards;
	}
	
}
