package isn;

import java.util.Random;

import isn.cards.blackjack.BlackJack;
import isn.gui.Window;

//Classe principale qui s'exécute au démarrage du programme
public class Main {
	
	//Objet random du programme
	private static final Random rand = new Random();
	//Objet contenant le jeu
	public static BlackJack bj;

	public static void main(String[] args) {
		//Intialisation de l'affichage
		Window.run();
		//Instancier l'objet BlackJack
		bj = new BlackJack(rand);
		//Tant que l'on peut jouer, jouer
		while(bj.isActive){
			bj.play();
		}
	}

}