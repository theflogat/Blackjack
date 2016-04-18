package isn.cards.blackjack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import isn.cards.Card;
import isn.cards.Deck;
import isn.users.Computer;
import isn.users.Player;
import isn.users.User;

public class BlackJack {
	
	/**
	 * Jeu de carte
	 */
	public Deck deck;
	
	/**
	 * Joueur
	 */
	public Player player;
	
	/**
	 * Ordinateur
	 */
	public Computer computer;
	
	/**
	 * Mise
	 */
	public int wage;
	
	/**
	 * Est-ce qu'il y a une partie en cours
	 */
	public boolean isActive;
//	public boolean canPlayerSplit;
	
	/**
	 * Est-ce que le joueur peut parier ou non
	 */
	public boolean canPlayerWage;
	
	/**
	 * Est-on a' la fin du tour
	 */
	public boolean cooldown;
	
	/**
	 * Qui est-ce qui a gagne
	 */
	public String whoWon = "";
	
	/**
	 * Nombre max de tours
	 */
	public static final int maxTurn = 3;
	
	/**
	 * Tour actuel
	 * A' qui est-ce de jouer
	 */
	public int turn = 1;

	public BlackJack() {
		deck = new Deck(this);
		isActive = false;
		canPlayerWage = true;
		player = new Player();
		cooldown = false;
		computer = new Computer() {
			@Override
			public boolean shouldDraw(Object... data) {
				return hand.getScore()<17;
			}
		};
	}

	public void start(){
		toggleState();
		drawCards();
	}

	/**
	 * Recupere les cartes des joueurs
	 * Tire les cartes
	 */
	private void drawCards(){
		player.clearHand();
		computer.clearHand();
		player.addCard(deck.draw());
		player.addCard(deck.draw());
		computer.addCard(deck.draw());
		computer.addCard(deck.draw());
	}
	
	/**
	 * 
	 * @param user
	 * @return Si l'user a perdu
	 */
	public boolean checkLoss(User user){
		return user.hand.getScore()>21;
	}
	
	/**
	 * 
	 * @param user
	 * @return Si l'user a BlackJack
	 */
	public boolean checkBJ(User user){
		return user.hand.getScore()==21 && user.hand.cards.size()==2;
	}
	
	/**
	 * Change l'etat du jeu
	 */
	public void toggleState() {
		isActive = !isActive;
	}

	/**
	 * Tire une carte pour le joueur et vérifie si il a perdu
	 * Passe au tour de l'ordi si le joueur a perfu
	 */
	public void drawPlayerCard() {
		player.addCard(deck.draw());
		if(checkLoss(player)){
			turn+=2;
		}
		checkEnd();
	}
	
	/**
	 * Appelee quand le joueur decide d'arreter de tirer les cartes
	 * Passe au tour de l'ordi
	 */
	public void playerNext(){
		turn++;
		checkEnd();
	}
	
	/**
	 * Remets les variables de façon à pouvoir jouer un nouveau tour
	 */
	public void afterCooldown(){
		canPlayerWage = true;
		drawCards();
		wage = Math.min(100, (int) (10F/100F*player.getCoins()));
		cooldown = false;
	}
	
	/**
	 * Joue le tour de l'IA
	 */
	public void playComputerTurn() {
		if(computer.shouldDraw(new Object[]{player.hand.getScore()})){
			computer.addCard(deck.draw());
			if(checkLoss(computer)){
				turn++;
			}
		}else{
			turn++;
		}
		checkEnd();
	}
	
	/**
	 * Si le tour n'est pas fini, fait jouer l'ordi
	 * Si le tour est fini, calcule les gains/pertes
	 */
	private void checkEnd() {
		if(turn%maxTurn==2){
			playComputerTurn();
		}
		if(turn%maxTurn==0){
			whoWon = "draw";
			cooldown = true;
			if(checkLoss(player) || (!checkLoss(computer) && player.hand.getScore()<computer.hand.getScore())){
				//Player perd
				player.addCoins(-wage);
				whoWon = "computer";
			}else if(player.hand.getScore()==computer.hand.getScore()){
				if(checkBJ(player)){
					if(!checkBJ(computer)){
						//Player gagne (BJ)
						player.addCoins((int) (((float)wage)*1.5F));
						whoWon = "player";
					}
				}else if(checkBJ(computer)){
					//Player perd
					player.addCoins(-wage);
					whoWon = "computer";
				}
			}else{
				//Player gagne
				if(checkBJ(player)){
					player.addCoins((int) (wage*0.5F));
				}
				player.addCoins(wage);
				whoWon = "player";
			}
			turn++;
		}
	}

	public String getTurn() {
		return turn%maxTurn==1 ? "player" : "computer";
	}

	/**
	 * 
	 * @param path
	 * 
	 * Sauvegarde la partie dans un fichier d'adresse path
	 */
	public void save(String path) {
		try {
			File file = new File(path);
			file.getParentFile().mkdirs();
			file.createNewFile();
			
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
			bufferedWriter.write(Integer.toString(player.getCoins()));
			bufferedWriter.newLine();
			bufferedWriter.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param path
	 * 
	 * Charge la partie depuis un fichier se trouvant a' l'adresse path
	 */
	public void load(String path){
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			player.setCoins(Integer.parseInt(bufferedReader.readLine()));
			bufferedReader.close();    
			start();
		}catch(IOException e) {     
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return les cartes qui sont dans les mains des joueurs
	 */
	public ArrayList<Card> getCardsAlreadyInPlay(){
		ArrayList<Card> cardsAlreadyInPlay = new ArrayList<Card>(player.getCards());
		cardsAlreadyInPlay.addAll(computer.getCards());
		return cardsAlreadyInPlay;
	}

}