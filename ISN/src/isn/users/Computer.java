package isn.users;

import java.util.ArrayList;

import isn.cards.Card;

//Classe ordinateur
public abstract class Computer extends User{

	/**
	 * @param data
	 * @return Si l'IA doit tirer une carte
	 * 
	 * Joue le tour pour IA
	 */
	public abstract boolean shouldDraw(Object... data);
	
	@Override
	public ArrayList<Card> getAllCards() {
		return getCards(0);
	}
}
