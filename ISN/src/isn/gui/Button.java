package isn.gui;

import isn.Start;

public class Button {

	public int x, y, w, h;

	/**
	 * 
	 * @param x de depart de l'image
	 * @param y de depart de l'image
	 * @param w largeur de l'image
	 * @param h hauteur de l'image
	 */
	public Button(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/**
	 * Largeur de la fenêtre de base
	 */
	static final double iw = 1920;
	/**
	 * Hauteur de la fenêtre de base
	 */
	static final double ih = 1080;

	/**
	 * 
	 * @param mousex
	 * @param mousey
	 * @return Est-ce que le point se trouve dans les coordonees
	 */
	public boolean within(double mousex, double mousey){
		double wb = Start.getJF().getContentPane().getSize().getWidth();
		double hb = Start.getJF().getContentPane().getSize().getHeight();
		mousex = mousex/wb*iw;
		mousey = mousey/hb*ih;
		return mousex >= x && mousex <= x + w && mousey >= y && mousey <= y + h;
	}
}