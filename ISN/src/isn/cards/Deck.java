package isn.cards;

import java.util.ArrayList;
import java.util.Collections;

import isn.Start;
import isn.cards.Card.Type;

//Classe qui contient le jeu de carte
public class Deck {

	/**
	 * Liste des cartes triees
	 */
	public static final ArrayList<Card> allCards;

	/**
	 * Cree la liste de toutes les cartes triees
	 */
	static {
		ArrayList<Card> cards = new ArrayList<Card>();
		try{
			for(int i=0;i<4;i++){
				for(int j=0;j<13;j++){
					cards.add(new Card(j, Type.getType(i)));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}

		allCards = cards;
	}

	/**
	 * Les cartes du packet
	 */
	public ArrayList<Card> cards;

	/**
	 * Cree un jeu de carte
	 */
	public Deck() {
		drawCards(null);
	}

	/**
	 * Melange les cartes et les met dans le packet
	 */
	public void drawCards(ArrayList<Card> cardsAlreadyInPlay){
		cards = new ArrayList<Card>(allCards);
		Collections.shuffle(cards);
		if(cardsAlreadyInPlay!=null){
			cards.removeAll(cardsAlreadyInPlay);
		}
	}

	/**
	 * 
	 * Tire la premiere carte du packet
	 */
	public Card draw(){
		if(cards.isEmpty()){
			drawCards(Start.getBj().getCardsAlreadyInPlay());
			return draw();
		}
		Card c = cards.get(0);
		cards.remove(0);
		return c;
	}
}
