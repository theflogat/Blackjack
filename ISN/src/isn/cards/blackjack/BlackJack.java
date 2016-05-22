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
		deck = new Deck();
		isActive = false;
		canPlayerWage = true;
		player = new Player();
		cooldown = false;
		computer = new Computer() {
			@Override
			public boolean shouldDraw(Object... data) {
				return hands[0].getScore()<17;
			}
		};
	}

	/**
	 * Démarre une partie
	 */
	public void start(){
		toggleState();
		drawCards();
	}

	/**
	 * Recupere les cartes des joueurs
	 * Tire les cartes
	 */
	private void drawCards(){
		player.clearHands();
		computer.clearHands();
		player.currHand = 0;
		player.addCard(0, deck.draw());
		player.addCard(0, deck.draw());
		computer.addCard(0, deck.draw());
		computer.addCard(0, deck.draw());
	}

	/**
	 * 
	 * @param user
	 * @return Si l'user a perdu
	 */
	public boolean checkLoss(int hand, User user){
		return user.hands[hand].getScore()>21;
	}

	/**
	 * 
	 * @param user
	 * @return Si l'user a BlackJack
	 */
	public boolean checkBJ(int hand, User user){
		return user.hands[hand].getScore()==21 && user.hands[hand].cards.size()==2;
	}

	/**
	 * Change l'etat du jeu
	 */
	public void toggleState() {
		isActive = !isActive;
	}

	/**
	 * Tire une carte pour le joueur et vérifie si il a perdu
	 * Passe au tour de l'ordi si le joueur a perdu
	 */
	public void drawPlayerCard(int hand) {
		player.addCard(hand, deck.draw());
		if(checkLoss(hand, player)){
			turn+=2;
		}
		checkEnd();
	}

	/**
	 * Tire une carte pour le joueur
	 * Sans rien vérifier
	 */
	public void drawPlayerCardRaw(int hand) {
		player.addCard(hand, deck.draw());
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
	 * S'il y a nouveau tour
	 */
	public void afterCooldown(){
		player.nextHand();
		if(player.currHand==0){
			canPlayerWage = true;
			drawCards();
			wage = Math.min(100, (int) (10F/100F*player.getCoins()));
			turn++;
		}else{
			turn-=2;
		}
		cooldown = false;
	}

	/**
	 * Joue le tour de l'IA
	 */
	public void playComputerTurn() {
		if(computer.shouldDraw()){
			computer.addCard(0, deck.draw());
			if(checkLoss(0, computer)){
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
			int i = player.currHand;
			if(checkLoss(i, player) || (!checkLoss(0, computer) && player.getCurrHand().getScore()<computer.hands[0].getScore())){
				//Player perd
				player.addCoins(-wage);
				whoWon = "computer";
			}else if(player.getCurrHand().getScore()==computer.hands[0].getScore()){
				if(checkBJ(i, player)){
					if(!checkBJ(0, computer)){
						//Player gagne (BJ)
						player.addCoins((int) (((float)wage)*1.5F));
						whoWon = "player";
					}
				}else if(checkBJ(0, computer)){
					//Player perd
					player.addCoins(-wage);
					if(player.insurance){
						player.addCoins(3*wage/2);
					}
					whoWon = "computer";
				}
			}else{
				//Player gagne
				if(checkBJ(i, player)){
					player.addCoins((int) (wage*0.5F));
				}
				player.addCoins(wage);
				whoWon = "player";
			}
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
		ArrayList<Card> cardsAlreadyInPlay = new ArrayList<Card>(player.getAllCards());
		cardsAlreadyInPlay.addAll(computer.getAllCards());
		return cardsAlreadyInPlay;
	}

	public void split() {
		player.split(player.currHand, deck);
	}

	public void cDouble() {
		wage *= 2;
		drawPlayerCardRaw(player.currHand);
		playerNext();
	}

}