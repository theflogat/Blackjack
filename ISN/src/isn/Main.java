package isn;

import java.util.Random;

import isn.cards.blackjack.BlackJack;
import isn.gui.Window;

public class Main {
	
	private static final Random rand = new Random();
	public static BlackJack bj;

	public static void main(String[] args) {
		Window.run();
		bj = new BlackJack(rand);
		while(bj.isActive){
			bj.play();
		}
	}

}
