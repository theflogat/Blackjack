package isn.gui;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;

//Classe qui stocke un objet et sa position
@SuppressWarnings("serial")
public class ImageDrawingApplet extends JApplet {

	static String imageFileName = "examples/bld.jpg";
	private URL imageSrc;

	public ImageDrawingApplet () {}
	
	//Crée un applet à partir d'un lien
	public ImageDrawingApplet (URL imageSrc) {
		this.imageSrc = imageSrc;
	}
	
	//Initialise l'objet
	@Override
	public void init() {
		try {
			imageSrc = new URL(getCodeBase(), imageFileName);
		} catch (MalformedURLException e) {}
		buildUI();
	}
	
	//Ajouter au centre l'image
	public void buildUI() {
		final ImageDrawingComponent id = new ImageDrawingComponent(imageSrc);
		add("Center", id);
	}
}