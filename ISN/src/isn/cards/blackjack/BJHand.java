package isn.cards.blackjack;

import java.util.ArrayList;

import isn.cards.Card;

public class BJHand {
	
	public ArrayList<Card> cards = new ArrayList<Card>();
	
	//Donne le score
	public int getScore(){
		int score = 0;
		int aces = 0;
		for(Card c:cards){
			if(c.getNumber()==1){
				aces++;
			}else if(c.getNumber()>10){
				score += 10;
			}else{
				score += c.getNumber();
			}
		}
		
		if(score + aces > 21){
			return score + aces;
		}else if(score + 11==21 && aces==1){
			return 21;
		}else if(score + 11 > 22 - aces){//21 - (aces - 1) = 22 - aces
			//+11 -1
			return score + 10 + aces;
		}
		
		return score;
	}
	
	//Peut split ou non
	public boolean canSplit(){
		if(cards.size()==2 && cards.get(0).getNumber()==cards.get(1).getNumber()){
			return true;
		}
		
		return false;
	}

}
