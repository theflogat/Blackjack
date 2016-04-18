package isn.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;

import javax.swing.JApplet;

import isn.Start;
import isn.cards.blackjack.BlackJack;

//Classe qui stocke un objet et sa position
@SuppressWarnings("serial")
public class ImageDrawingApplet extends JApplet {

	private BlackJack bj;
	private URL backgroung;
	private URL[] numbers;
	private URL[] types;
	private URL cardBack;
	private URL whiteCard;
	private URL[] buttons;
	private URL[] endDisplay;

	/**
	 * Cree un applet a' partir des URL des differentes images et du jeu de BlackJack
	 * @param bj
	 * @param backgroung
	 * @param numbers
	 * @param types
	 * @param cardBack
	 * @param whiteCard
	 * @param buttons
	 * @param endDisplay
	 */
	public ImageDrawingApplet (BlackJack bj, URL backgroung, URL[] numbers, URL[] types, URL cardBack, URL whiteCard, URL[] buttons, URL[] endDisplay) {
		this.backgroung = backgroung;
		this.numbers = numbers;
		this.types = types;
		this.cardBack = cardBack;
		this.whiteCard = whiteCard;
		this.buttons = buttons;
		this.endDisplay = endDisplay;
		this.bj = bj;
	}

	/**
	 * Initialise l'objet
	 */
	@Override
	public void init() {
		//Cree l'ImageDrawingComponent et l'ajoute au centre de l'applet
		final ImageDrawingComponent id = new ImageDrawingComponent(bj, backgroung, numbers, types, cardBack, whiteCard, buttons, endDisplay);
		add("Center", id);

		//Ajouter une implementation silencieuse d'un objet MouseListener
		//Permet de reagir au click de souris
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				if(!bj.cooldown){
					if(bj.isActive){
						//Click on "Quit"
						if(ImageDrawingComponent.coords[11].within(e.getX(), e.getY())){
							Start.newBlackJack();
							repaint();
						}
						if(bj.canPlayerWage){
							//Click on "Save"
							if(ImageDrawingComponent.coords[10].within(e.getX(), e.getY())){
								bj.save(Start.getSaveFilePath());
								repaint();
							}
							//Click on "Bet"
							if(ImageDrawingComponent.coords[7].within(e.getX(), e.getY())){
								bj.canPlayerWage = false;
								repaint();
							}
							//Remove coin
							if(ImageDrawingComponent.coords[8].within(e.getX(), e.getY())){
								bj.wage--;
								if(bj.wage<0){
									bj.wage = 0;
								}
								repaint();
							}
							//Add coin
							if(ImageDrawingComponent.coords[9].within(e.getX(), e.getY())){
								bj.wage++;
								if(bj.wage>Math.min(100, bj.player.getCoins())){
									bj.wage = Math.min(100, bj.player.getCoins());
								}
								repaint();
							}
						}else{
							//Click on Draw
							if(ImageDrawingComponent.coords[7].within(e.getX(), e.getY())){
								bj.drawPlayerCard();
								repaint();
							}

							//Click on stop
							if(ImageDrawingComponent.coords[10].within(e.getX(), e.getY())){
								bj.playerNext();
								repaint();
							}
						}
					}else{
						//Click on load
						if((new File(Start.getSaveFilePath())).exists() && ImageDrawingComponent.coords[7].within(e.getX(), e.getY())){
							bj.load(Start.getSaveFilePath());
							repaint();
						}
						//Click on play
						if(ImageDrawingComponent.coords[6].within(e.getX(), e.getY())){
							bj.start();
							repaint();
						}
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {}
			
		});
	}
}