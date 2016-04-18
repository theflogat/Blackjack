package isn.users;

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
	
}