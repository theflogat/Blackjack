package isn.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import isn.cards.Card;

//Classe qui contient l'image
@SuppressWarnings("serial")
class ImageDrawingComponent extends Component {
	
	//TODO enlever
	static String descs[] = {
			"Simple Copy",
	};
	
	private BufferedImage bi;
	int w, h;
	String img;
	
	//Sauvegarde l'image
	public ImageDrawingComponent(URL imageSrc) {
		try {
			bi = ImageIO.read(imageSrc);
			w = bi.getWidth(null);
			h = bi.getHeight(null);
			if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
				BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics big = bi2.getGraphics();
				big.drawImage(bi, 0, 0, null);
				bi = bi2;
			}
		} catch (IOException e) {
			System.out.println("Image could not be read");
		}
	}
	
	//Donne les dimensions pour la remise à l'échelle de l'image; par défaut la taille de l'écran
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}
	
	//Peint l'image à l'écran
	public void paint(Graphics g) {
		BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics big = bi2.getGraphics();
		big.drawImage(bi, 0, 0, null);
//		preDrawCards(Main.bj.computer.getCards(), big);
//		preDrawCards(Main.bj.player.getCards(), big);

		g.drawImage(bi2, 0, 0, null);
	}
	
	//Peint les cartes
	public void preDrawCards(ArrayList<Card> cards, Graphics big){
		if(!cards.isEmpty()){
			for(int i=0;i<cards.size();i++){
				try {
					big.drawImage(ImageIO.read(cards.get(i).getImg()), w/2 + i*16, h-16, null);
					big.drawImage(ImageIO.read(cards.get(i).getType().getImg()), w/2 + i*16, h-16, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}