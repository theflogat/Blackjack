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
	public ImageDrawingApplet (URL backgroung, URL[] numbers, URL[] types, URL cardBack, URL whiteCard, URL[] buttons, URL[] endDisplay) {
		this.backgroung = backgroung;
		this.numbers = numbers;
		this.types = types;
		this.cardBack = cardBack;
		this.whiteCard = whiteCard;
		this.buttons = buttons;
		this.endDisplay = endDisplay;
	}

	/**
	 * Initialise l'objet
	 */
	@Override
	public void init() {
		//Cree l'ImageDrawingComponent et l'ajoute au centre de l'applet
		final ImageDrawingComponent id = new ImageDrawingComponent(backgroung, numbers, types, cardBack, whiteCard, buttons, endDisplay);
		add("Center", id);

		//Ajouter une implementation silencieuse d'un objet MouseListener
		//Permet de reagir au click de souris
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				BlackJack bj = Start.getBj();
				if(!bj.cooldown){
					if(bj.isActive){
						//Clic sur Quitter
						if(ImageDrawingComponent.bCoords[11].within(e.getX(), e.getY())){
							Start.newBlackJack();
							repaint();
						}
						if(bj.canPlayerWage){
							//Clic sur Sauver
							if(ImageDrawingComponent.bCoords[10].within(e.getX(), e.getY())){
								bj.save(Start.getSaveFilePath());
								repaint();
							}
							//Clic sur Parier
							if(ImageDrawingComponent.bCoords[7].within(e.getX(), e.getY())){
								bj.canPlayerWage = false;
								repaint();
							}

							//Enlever jeton
							if(ImageDrawingComponent.bCoords[8].within(e.getX(), e.getY())){
								bj.wage--;
								if(bj.wage<0){
									bj.wage = 0;
								}
								repaint();
							}

							//Enlever 10 jetons
							if(ImageDrawingComponent.bCoords[12].within(e.getX(), e.getY())){
								bj.wage -= 10;
								if(bj.wage<0){
									bj.wage = 0;
								}
								repaint();
							}

							//Ajouter jeton
							if(ImageDrawingComponent.bCoords[9].within(e.getX(), e.getY())){
								bj.wage++;
								if(bj.wage>Math.min(100, bj.player.getCoins())){
									bj.wage = Math.min(100, bj.player.getCoins());
								}
								repaint();
							}

							//Ajouter 10 jetons
							if(ImageDrawingComponent.bCoords[13].within(e.getX(), e.getY())){
								bj.wage += 10;
								if(bj.wage>Math.min(100, bj.player.getCoins())){
									bj.wage = Math.min(100, bj.player.getCoins());
								}
								repaint();
							}
						}else{
							//Clic sur Draw
							if(ImageDrawingComponent.bCoords[7].within(e.getX(), e.getY())){
								bj.drawPlayerCard(bj.player.currHand);
								repaint();
							}

							//Clic sur Stop
							if(ImageDrawingComponent.bCoords[10].within(e.getX(), e.getY())){
								bj.playerNext();
								repaint();
							}

							//Clic sur Assurance
							if(ImageDrawingComponent.bCoords[14].within(e.getX(), e.getY())){
								if(bj.computer.getAllCards().get(0).getNumber()==0 && !bj.player.insurance){
									if(bj.player.getCoins()>bj.wage*3/2){
										bj.player.insurance = true;
										bj.player.addCoins(-bj.wage/2);
										repaint();
									}
								}
							}

							//Clic sur Split
							if(ImageDrawingComponent.bCoords[15].within(e.getX(), e.getY()) && bj.player.canSplit(bj.wage)){
								bj.split();
								repaint();
							}

							//Clic sur Double
							if(ImageDrawingComponent.bCoords[16].within(e.getX(), e.getY()) && bj.player.getCoins()>=2*bj.wage){
								bj.cDouble();
								repaint();
							}
						}
					}else{
						//Clic sur Charger
						if((new File(Start.getSaveFilePath())).exists() && ImageDrawingComponent.bCoords[7].within(e.getX(), e.getY())){
							bj.load(Start.getSaveFilePath());
							repaint();
						}
						//Clic sur Jouer
						if(ImageDrawingComponent.bCoords[6].within(e.getX(), e.getY())){
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