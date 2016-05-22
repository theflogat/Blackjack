package isn.users;

import isn.cards.blackjack.BJHand;

public class Player extends User{

	/**
	 * Les jetons par defaut au debut d'une nouvelle partie
	 */
	private static final int defaultStart = 150;

	/**
	 * Les jetons actuels
	 */
	private int coins = defaultStart;

	/**
	 * A-t-il pris l'assurance
	 */
	public boolean insurance = false;

	/**
	 * Le tableau sur lequel on joue
	 */
	public int currHand = 0;

	/**
	 * 
	 * @return les jetons du joueur
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * 
	 * @param i
	 * 
	 * Ajoute i jetons
	 */
	public void addCoins(int i){
		coins += i;
	}

	/**
	 * 
	 * @param c
	 * 
	 * Les jetons du joueur sont maintenant c
	 */
	public void setCoins(int c){
		coins = c;
	}

	/**
	 * 
	 * @param wage
	 * @return Si le joueur peut split sur ce tableau
	 */
	public boolean canSplit(int wage) {
		return hands[currHand].canSplit() && (coins - 2*wage)>=0;
	}

	public void nextHand(){
		if(currHand==hands.length-1){
			currHand = 0;
		}else{
			if(hands[currHand+1].cards.size()>0){
				currHand++;
			}else{
				currHand = 0;
			}
		}
	}

	/**
	 * 
	 * @return La main du tableau actif
	 */
	public BJHand getCurrHand(){
		return hands[currHand];
	}

}