package isn.users;

//Classe ordinateur
public abstract class Computer extends User{
	
	/**
	 * @param data
	 * @return Si l'IA doit tirer une carte
	 * 
	 * Joue le tour pour IA
	 */
	public abstract boolean shouldDraw(Object... data);
}
