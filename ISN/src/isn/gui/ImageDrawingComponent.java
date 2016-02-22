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

@SuppressWarnings("serial")
class ImageDrawingComponent extends Component {

	static String descs[] = {
			"Simple Copy",
	};
	
	private BufferedImage bi;
	int w, h;
	String img;

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

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}
	
	public void paint(Graphics g) {
		BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics big = bi2.getGraphics();
		big.drawImage(bi, 0, 0, null);
//		preDrawCards(Main.bj.computer.getCards(), big);
//		preDrawCards(Main.bj.player.getCards(), big);

		g.drawImage(bi2, 0, 0, null);
	}

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