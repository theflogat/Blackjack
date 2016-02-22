package isn.gui;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class ImageDrawingApplet extends JApplet {

	static String imageFileName = "examples/bld.jpg";
	private URL imageSrc;

	public ImageDrawingApplet () {
	}

	public ImageDrawingApplet (URL imageSrc) {
		this.imageSrc = imageSrc;
	}

	@Override
	public void init() {
		try {
			imageSrc = new URL(getCodeBase(), imageFileName);
		} catch (MalformedURLException e) {
		}
		buildUI();
	}

	public void buildUI() {
		final ImageDrawingComponent id = new ImageDrawingComponent(imageSrc);
		add("Center", id);
	}
}