package isn.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.swing.JFrame;

import isn.cards.blackjack.BlackJack;

//Classe qui permet l'affichage
public class Window {
	
	//Objet random du programme
	private static final Random rand = new Random();
	//Objet contenant le jeu
	public static BlackJack bj;
	
	static String background = "test.jpg";

	public static void run(){
		//Instancier l'objet BlackJack
		bj = new BlackJack(rand);
		//Tant que l'on peut jouer, jouer
		//TODO Mouve to mouse listener event
//		while(bj.isActive){
//			bj.play();
//		}
		
		//Cree une fenetre
		JFrame f = new JFrame("BlackJack");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//    System.exit(0);
			}
		});
		//Charger l'URL de l'imagine
		URL imageSrc = null;
		try {
			imageSrc = ((new File("C:/Users/flo/Documents/Java/ISN/ISN/src/" + background)).toURI()).toURL();
		} catch (MalformedURLException e) {}
		//Creer un objet qui stocke la position
		ImageDrawingApplet id = new ImageDrawingApplet(imageSrc);
		id.buildUI();
		//Ajouter l'id a' la fenêtre
		f.add("Center", id);
		f.pack();
		//Rend la fenetre visible
		f.setVisible(true);
	}
}