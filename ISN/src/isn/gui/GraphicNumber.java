package isn.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class GraphicNumber {
	
	/**
	 * URLs des chiffres
	 */
	private static URL[] nums = new URL[10];
	
	/**
	 * Largeur, hauteur des images
	 */
	public static int nw = 64;
	public static int nh = 64;
	
	/**
	 * Initialisation des URLs
	 */
	static{
		for(int i=0;i<nums.length;i++){
			nums[i] = GraphicNumber.class.getResource("numbers/t" + i + ".png");
		}
	}
	
	/**
	 * 
	 * @param big
	 * @param num
	 * @param cx
	 * @param cy
	 * 
	 * Peint la mise actuelle
	 */
	public static void drawWage(Graphics big, int num, int cx, int cy){
		int c = num/100;
		int d = (num%100)/10;
		int u = num%10;

		int startx;

		if(c==0){
			if(d==0){
				startx = cx - nw/2;
				big.drawImage(getImage(u), startx, cy, null);
			}else{
				startx = cx - nw;
				big.drawImage(getImage(d), startx, cy, null);
				big.drawImage(getImage(u), startx + nw, cy, null);
			}
		}else{
			startx = cx - 3*nw/2;
			big.drawImage(getImage(c), startx, cy, null);
			big.drawImage(getImage(d), startx + nw, cy, null);
			big.drawImage(getImage(u), startx + 2*nw, cy, null);
		}
	}
	
	/**
	 * 
	 * @param big
	 * @param num
	 * @param startx
	 * @param y
	 * 
	 * Peint les jetons actuels du joueur
	 */
	public static void drawCoins(Graphics big, int num, int startx, int y){
		int dm = (num%100000)/10000;
		int m = (num%10000)/1000;
		int c = (num%1000)/100;
		int d = (num%100)/10;
		int u = (num%10)/1;
		
		if(dm==0){
			if(m==0){
				if(c==0){
					if(d==0){
						big.drawImage(getImage(u), startx, y, null);
					}else{
						big.drawImage(getImage(d), startx, y, null);
						big.drawImage(getImage(u), startx + nw, y, null);
					}
				}else{
					big.drawImage(getImage(c), startx, y, null);
					big.drawImage(getImage(d), startx + nw, y, null);
					big.drawImage(getImage(u), startx + 2*nw, y, null);
				}
			}else{
				big.drawImage(getImage(m), startx, y, null);
				big.drawImage(getImage(c), startx + nw, y, null);
				big.drawImage(getImage(d), startx + 2*nw, y, null);
				big.drawImage(getImage(u), startx + 3*nw, y, null);
			}
		}else{
			big.drawImage(getImage(dm), startx, y, null);
			big.drawImage(getImage(m), startx + nw, y, null);
			big.drawImage(getImage(c), startx + 2*nw, y, null);
			big.drawImage(getImage(d), startx + 3*nw, y, null);
			big.drawImage(getImage(u), startx + 4*nw, y, null);
		}
	}
	
	/**
	 * 
	 * @param i
	 * @return L'image en fonction du chiffre
	 */
	public static Image getImage(int i){
		return (new ImageIcon(nums[i])).getImage();
	}
}