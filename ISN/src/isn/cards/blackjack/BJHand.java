package isn.cards.blackjack;

import java.util.ArrayList;

import isn.cards.Card;

public class BJHand {

	public ArrayList<Card> cards = new ArrayList<Card>();

	/**
	 * @return Le score de la main
	 */
	public int getScore(){
		int score = 0;
		int aces = 0;
		for(Card c:cards){
			if(c==null){
				continue;
			}
			if(c.getNumber()==0){
				aces++;
			}else if(c.getNumber()>=9){
				score += 10;
			}else{
				score += c.getNumber() + 1;
			}
		}

		if(aces>0){
			if(score + aces > 21){
				return score + aces;
			}else if(score + 10 + aces <= 21){
				//score + 11 + (aces - 1) = score + 10 + aces
				return score + 10 + aces;
			}else{
				return score + aces;
			}
		}

		return score;
	}

	/**
	 * @return Peut-il split
	 */
	public boolean canSplit(){
		if(cards.size()==2 && cards.get(0).getNumber()==cards.get(1).getNumber()){
			return true;
		}
		return false;
	}

}
