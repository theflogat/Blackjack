package isn.cards.blackjack;

import java.util.Random;

import isn.cards.Deck;
import isn.users.Computer;
import isn.users.User;

public class BlackJack {
	
	public Deck deck;
	public User player;
	public Computer computer;
	public boolean isActive;
	
	public int turn = 1;
	
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
	
	public void play(){
		switch(turn){
		case 0:
			checkWin();
			turn++;
			break;
		case 1:
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
		case 2:
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
	
	public void drawCards(){
		player.clearHand();
		computer.clearHand();
		player.addCard(deck.draw());
		player.addCard(deck.draw());
		computer.addCard(deck.draw());
		computer.addCard(deck.draw());
	}
	
	public boolean checkLoss(User user){
		return false;
		
	}
	
	public void checkWin(){
		
	}

}