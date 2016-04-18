package isn;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.JFrame;

import isn.cards.Card.Type;
import isn.cards.blackjack.BlackJack;
import isn.gui.ImageDrawingApplet;

public class Start {
	
	/**
	 * Objet contenant le jeu
	 */
	private static BlackJack bj;
	
	//Noms des images
	private static final String background = "backgroung.png";
	private static final String cardBack = "cardBack.png";
	private static final String whiteCard = "whiteCard.png";
	private static final String play = "play.png";
	private static final String draw = "draw.png";
	private static final String moneyAdd = "moneyAdd.png";
	private static final String moneyRemove = "moneyRemove.png";
	private static final String moneyAddLocked = "moneyAddLocked.png";
	private static final String moneyRemoveLocked = "moneyRemoveLocked.png";
	private static final String bet = "bet.png";
	private static final String coins = "coins.png";
	private static final String stop = "stop.png";
	private static final String lose = "lose.png";
	private static final String win = "win.png";
	private static final String drawE = "drawE.png";
	private static final String gameOver = "gameOver.png";
	private static final String save = "save.png";
	private static final String load = "load.png";
	private static final String loadLocked = "loadLocked.png";
	private static final String quit = "quit.png";
	
	/**
	 * Dossier image dans jar
	 */
	private static final String imgPath = "gui/img/";
	
	/**
	 * Fenetre
	 */
	private static JFrame f;
	
	//Fonction depart du programme
	public static void main(String[] args) {
		
		//Instancier l'objet BlackJack
		newBlackJack();

		//Cree une fenetre
		f = new JFrame("BlackJack");
		//Implementation silencieuse de WindowAdapter ajoute a' la fenetre
		f.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});

		//Instancie les URL
		URL background = null;
		URL[] numbers = new URL[13];
		URL[] types = new URL[4];
		URL cardBack = null;
		URL whiteCard = null;
		URL[] buttons = new URL[13];
		URL[] endDisplay = new URL[4];
		
		//Charge l'URL des images
		background = Start.class.getResource(imgPath + Start.background);

		for(int i=0;i<13;i++){
			numbers[i] =  Start.class.getResource(imgPath +  i + ".png");
		}
		for(int i=0;i<Type.values().length;i++){
			types[i] =  Start.class.getResource(imgPath + Type.values()[i].toString().toLowerCase() + ".png");
		}

		cardBack =  Start.class.getResource(imgPath +  Start.cardBack);

		whiteCard =  Start.class.getResource(imgPath + Start.whiteCard);

		buttons[0] =  Start.class.getResource(imgPath + play);
		buttons[1] =  Start.class.getResource(imgPath + draw);
		buttons[2] =  Start.class.getResource(imgPath + moneyRemoveLocked);
		buttons[3] =  Start.class.getResource(imgPath + moneyAddLocked);
		buttons[4] =  Start.class.getResource(imgPath + moneyRemove);
		buttons[5] =  Start.class.getResource(imgPath + moneyAdd);
		buttons[6] =  Start.class.getResource(imgPath + bet);
		buttons[7] =  Start.class.getResource(imgPath + coins);
		buttons[8] =  Start.class.getResource(imgPath + stop);
		buttons[9] =  Start.class.getResource(imgPath + save);
		buttons[10] =  Start.class.getResource(imgPath + load);
		buttons[11] =  Start.class.getResource(imgPath + loadLocked);
		buttons[12] =  Start.class.getResource(imgPath + quit);

		endDisplay[0] =  Start.class.getResource(imgPath + win);
		endDisplay[1] =  Start.class.getResource(imgPath + lose);
		endDisplay[2] =  Start.class.getResource(imgPath + drawE);
		endDisplay[3] =  Start.class.getResource(imgPath + gameOver);
		
		//Cree l'applet et l'initialise
		ImageDrawingApplet ida = new ImageDrawingApplet(bj, background, numbers, types, cardBack, whiteCard, buttons, endDisplay);
		ida.init();

		//Ajouter l'applet a' la fenetre
		f.add("Center", ida);
		//Completer la fenetre
		f.pack();
		//Rend la fenetre visible
		f.setVisible(true);
	}
	
	/**
	 * @return Le chemin pour acceder à la sauvegarde
	 */
	public static String getSaveFilePath(){
		return Paths.get("").toAbsolutePath().toString() + "/save/save.txt";
	}

	/**
	 * Cree une nouvelle instance de Blackjack
	 */
	public static void newBlackJack() {
		bj = new BlackJack();
	}
	
	/**
	 * 
	 * @return l'objet bj
	 */
	public static BlackJack getBj() {
		return bj;
	}
}