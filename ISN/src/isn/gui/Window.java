package isn.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

public class Window {

	static String imageFileName = "test.jpg";

	public static void run(){
		JFrame f = new JFrame("ImageDrawing");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//    System.exit(0);
			}
		});
		URL imageSrc = null;
		try {
			imageSrc = ((new File("C:/Users/flo/Documents/Java/ISN/ISN/src/" + imageFileName)).toURI()).toURL();
		} catch (MalformedURLException e) {
		}
		ImageDrawingApplet id = new ImageDrawingApplet(imageSrc);
		id.buildUI();
		f.add("Center", id);
		f.pack();
		f.setVisible(true);
	}
}