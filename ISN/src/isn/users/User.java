package isn.users;

import java.util.ArrayList;

import isn.cards.Card;
import isn.cards.Deck;
import isn.cards.blackjack.BJHand;

public abstract class User {

	/**
	 * Les tableaux
	 */
	public BJHand hands[] = {new BJHand(),new BJHand(),new BJHand(),new BJHand()};

	/**
	 * @param toAdd
	 * 
	 * Ajouter la carte toAdd a' la main
	 */
	public void addCard(int hand, Card toAdd){
		hands[hand].cards.add(toAdd);
	}

	/**
	 * Jete la main
	 */
	public void clearHands(){
		for(BJHand hand:hands){
			hand.cards.clear();
		}
	}

	/**
	 * @param toRemove
	 * 
	 * Enleve la carte toRemove de la main
	 */
	public void remove(int hand, Card toRemove){
		hands[hand].cards.remove(toRemove);
	}

	/**
	 * Effectuer un split
	 * @param deck 
	 */
	public void split(int hand, Deck deck){
		for(int i=hand+1;i<hands.length;i++){
			if(!isSplit(i)){
				hands[i].cards.add(hands[hand].cards.get(1));
				hands[hand].cards.remove(1);

				addCard(hand, deck.draw());
				addCard(i,deck.draw());
				return;
			}
		}
	}

	/**
	 * 
	 * @param hand; hand > 0
	 * @return le tableau hand est-il splitté?
	 */
	public boolean isSplit(int hand){
		return hands[hand].cards.size()>0;
	}

	/**
	 * 
	 * @return La main
	 */
	public ArrayList<Card> getCards(int hand) {
		return hands[hand].cards;
	}
	
	/**
	 * 
	 * @return Toutes les cartes du joueur
	 */
	public ArrayList<Card> getAllCards() {
		ArrayList<Card> l = new ArrayList<Card>();
		for(int i=0;i<hands.length;i++){
			l.addAll(getCards(i));
		}
		return l;
	}
}
