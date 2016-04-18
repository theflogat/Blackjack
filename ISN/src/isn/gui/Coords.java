package isn.gui;

public class Coords {
	
	public int x, y, w, h;
	
	/**
	 * 
	 * @param x de depart de l'image
	 * @param y de depart de l'image
	 * @param w largeur de l'image
	 * @param h hauteur de l'image
	 */
	public Coords(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/**
	 * 
	 * @param mousex
	 * @param mousey
	 * @return Est-ce que le point se trouve dans les coordonees
	 */
	public boolean within(int mousex, int mousey){
		return mousex >= x && mousex <= x + w && mousey >= y && mousey <= y + h;
	}
}
