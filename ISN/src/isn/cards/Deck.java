package isn.cards;

import java.util.ArrayList;
import java.util.Random;

import isn.cards.Card.Type;

public class Deck {

	public static final ArrayList<Card> allCards;

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

	public ArrayList<Card> cards;

	public Deck(Random rand) {
		drawCards(rand);
	}

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
	public Card draw(){
		if(cards.isEmpty()){
			return null;
		}
		Card c = cards.get(0);
		cards.remove(0);
		return c;
	}
}
