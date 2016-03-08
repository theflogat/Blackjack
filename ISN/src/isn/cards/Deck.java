package isn.cards;

import java.util.ArrayList;
import java.util.Random;

import isn.cards.Card.Type;

//Classe qui contient le jeu de carte
public class Deck {

	//Toutes les cartes triées
	public static final ArrayList<Card> allCards;
	
	//Créer la liste de toutes les cartes triées
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
	
	//Les cartes restantes dans le jeu
	public ArrayList<Card> cards;
	
	//Construit un packet à partir d'un objet aléatoire
	public Deck(Random rand) {
		drawCards(rand);
	}
	
	// Melange les cartes et les met dans un packet
	public void drawCards(Random rand){
		ArrayList<Card> unusedCards = allCards;
		cards = new ArrayList<Card>();

		while(!unusedCards.isEmpty()){
			int drawn = rand.nextInt(unusedCards.size());
			cards.add(unusedCards.get(drawn));
			unusedCards.remove(drawn);
		}
	}
	
	/**
	 * Add cards already in play to cards drawing
	 * @return
	 */
	//Tire la première carte du packet
	public Card draw(){
		if(cards.isEmpty()){
			return null;
		}
		Card c = cards.get(0);
		cards.remove(0);
		return c;
	}
}
