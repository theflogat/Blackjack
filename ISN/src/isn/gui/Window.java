package isn.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

//Classe qui permet l'affichage
public class Window {

	static String background = "test.jpg";

	public static void run(){
		//Crée une fenêtre
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
		//Créer un objet qui stocke la position
		ImageDrawingApplet id = new ImageDrawingApplet(imageSrc);
		id.buildUI();
		//Ajouter id à la fenêtre
		f.add("Center", id);
		f.pack();
		//Rend la fenêtre visible
		f.setVisible(true);
	}
}