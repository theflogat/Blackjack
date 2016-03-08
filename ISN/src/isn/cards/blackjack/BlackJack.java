package isn.cards.blackjack;

import java.util.Random;

import isn.cards.Deck;
import isn.users.Computer;
import isn.users.User;

// Objet qui gère le jeu
public class BlackJack {
	
	//TODO Score for leaderboard
	public Deck deck;
	public User player;
	public Computer computer;
	public boolean isActive;
	
	public int turn = 1;
	public int maxTurn = 3;
	
	public BlackJack(Random rand) {
		deck = new Deck(rand);
		isActive = true;
		player = new User() {

			@Override
			public void playTurn(Object... data) {
				// TODO Auto-generated method stub
			}
		};
		computer = new Computer() {

			@Override
			public void playTurn(Object... data) {
				// TODO Auto-generated method stub
			}
		};
	}
	
	//Joue
	public void play(){
		switch(turn%maxTurn){
		case 0://Mise à jour des mises
			checkWin();
			turn++;
			break;
		case 1://Tour du joueur
			boolean shouldDraw = false;
			player.playTurn(new Object[]{shouldDraw});
			if(shouldDraw){
				player.addCard(deck.draw());
				if(checkLoss(player)){
					turn+=2;
				}
			}else{
				turn++;
			}
			break;
		case 2://Tour de l'ordi
			boolean shouldDraw2 = false;
			computer.playTurn(new Object[]{shouldDraw2});
			if(shouldDraw2){
				computer.addCard(deck.draw());
				if(checkLoss(computer)){
					turn++;
				}
			}else{
				turn++;
			}
			break;
		}
	}
	
	//Tirer les cartes
	public void drawCards(){
		player.clearHand();
		computer.clearHand();
		player.addCard(deck.draw());
		player.addCard(deck.draw());
		computer.addCard(deck.draw());
		computer.addCard(deck.draw());
	}
	
	//TODO est-ce que score>21
	public boolean checkLoss(User user){
		return false;
	}
	
	//TODO qui a gagné + changement de mises
	public void checkWin(){
		
	}

}
