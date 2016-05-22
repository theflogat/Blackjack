package isn.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

import isn.Start;
import isn.cards.Card;
import isn.cards.blackjack.BlackJack;

@SuppressWarnings("serial")
class ImageDrawingComponent extends Component {

	/**
	 * Largeur, hauteur des catres
	 */
	private static final int cw = 160;
	private static final int ch = 256;

	/**
	 * Largeur, hauteur des types de cartes
	 */
	private static final int tw = 40;
	private static final int th = 40;

	/**
	 * Largeur, hauteur des chiffres
	 */
	private static final int nw = 64;
	private static final int nh = 64;

	/**
	 * Differentes coordonees pour l'affichage a' l'ecran
	 * i=0; P C1
	 * i=1; P C2
	 * i=2; C C1
	 * i=3; C C2
	 * 
	 * i=6; Jouer
	 * i=7; Tirer, Miser
	 * i=8; Enlever Jetons
	 * i=9; Ajouter Jetons
	 * i=10; Stop, Sauver
	 * i=11; Quitter
	 * i=12; Enlever 10
	 * i=13; Ajouter 10
	 * i=14; Assurance
	 * i=15; Split
	 **/
	public static Button[] bCoords = new Button[17];

	/**
	 * Image de base contenant l'arriere plan
	 */
	private BufferedImage bi;

	/**
	 * w: largeur
	 * h: hauteur
	 */
	public static int w, h;

	/**
	 * URL vers les fichiers images
	 */
	private URL[] numbers;
	private URL[] types;
	private URL cardBack;
	private URL whiteCard;
	private URL[] buttons;
	private URL[] endDisplay;

	/**
	 * Cree un Component a' partir des URLs et du jeu de BlackJack
	 * @param bj
	 * @param background
	 * @param numbers
	 * @param types
	 * @param cardBack
	 * @param whiteCard
	 * @param buttons
	 * @param endDisplay
	 * 
	 * Initialise les variables necessaire a' la fabrication de l'image
	 */
	public ImageDrawingComponent(URL background, URL[] numbers, URL[] types, URL cardBack, URL whiteCard, URL[] buttons, URL[] endDisplay) {
		ImageIcon im = new ImageIcon(background);
		bi = new BufferedImage(im.getIconWidth(), im.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		w = bi.getWidth(null);
		h = bi.getHeight(null);
		bi.getGraphics().drawImage(im.getImage(), 0, 0, null);

		this.numbers = numbers;
		this.types = types;
		this.cardBack = cardBack;
		this.whiteCard = whiteCard;
		this.buttons = buttons;
		this.endDisplay = endDisplay;


		bCoords[0] = new Button(w/2 - cw - 17 - 11, h - ch - 87 - 11, cw, ch);
		bCoords[1] = new Button(w/2 + 17 + 11, h - ch - 87 - 11, cw, ch);
		bCoords[2] = new Button(1250, 54, cw, ch);
		bCoords[3] = new Button(1250 + cw + 57, 54, cw, ch);

		bCoords[6] = new Button(368 + 66 + 78, 123 + 22 + 28, 156, 55);
		bCoords[7] = new Button(368 + 66 + 78, 123 + 22 + 109 + 64, 156, 55);

		bCoords[8] = new Button(368 + 66 + 30, 123 + 22, 30, 109);
		bCoords[9] = new Button(368 + 66 + 312 - 30 - 30, 123 + 22, 30, 109);

		bCoords[10] = new Button(368 + 66 - 156 - 64, 123 + 22 + (109 + 64), 156, 55);
		bCoords[11] = new Button(368 + 66 + 312 + 64, 123 + 22 + (109 + 64), 156, 55);

		bCoords[12] = new Button(368 + 66 - 25, 123 + 22, 45, 109);
		bCoords[13] = new Button(368 + 66 + 312 - 45 + 25, 123 + 22, 45, 109);

		bCoords[14] = new Button(368 + 66 + 312 + 64, 123 + 22 + (109 + 64) + 55 + 30, 156, 55);
		bCoords[15] = new Button(368 + 66 + 78, 123 + 22 + (109 + 64) + 55 + 30, 156, 55);
		bCoords[16] = new Button(368 + 66 - 156 - 64, 123 + 22 + (109 + 64) + 55 + 30, 156, 55);
	}

	/**
	 * Donne les dimensions pour la remise a' l'echelle de la fenetre; par defaut la taille de l'image
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	/**
	 * Vrai au second tour de l'affichage qd bj.cooldown = true
	 * Permet de faire patienter le programme pour afficher ce qui s'est passer a' l'ecran
	 */
	private boolean st = false;

	//Peint l'image a' l'ecran
	public void paint(Graphics g) {
		BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics big = bi2.getGraphics();
		
		//Ajouter le fond
		big.drawImage(bi, 0, 0, null);

		BlackJack bj = Start.getBj();

		if(bj.cooldown){
			//Dimensions du boutton (pour pouvoir centrer) 440x109
			int cw = 440;
			int ch = 109;
			int i = bj.player.getCoins()==0 ? 3 : (bj.whoWon=="player" ? 0 : (bj.whoWon=="computer" ? 1 : 2));
			big.drawImage(getImg(endDisplay[i]), (w - cw)/2 , (h - ch)/2, null);
			
			//Afficher Cartes Joueur
			drawPlayerCards(big);

			//Afficher Cartes IA
			drawComputerCards(big);

			//Afficher Jetons + Mise
			drawWage(big, bj.wage);
			GraphicNumber.drawCoins(big, bj.player.getCoins(), 64 + 150, 40);

			//Delai de 2 secs quand tour fini
			if(st){
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				//En cas de GameOver reinitialise la partie
				if(i==3){
					Start.newBlackJack();
				}else{
					bj.afterCooldown();
				}
				st = false;
			}else{
				st = true;
			}
			repaint();
		}else{
			if(bj.isActive){
				//Afficher "Jetons:" et Jetons du joueur
				big.drawImage(getImg(buttons[7]), 64, 40, null);
				GraphicNumber.drawCoins(big, bj.player.getCoins(), 64 + 150, 40);

				//Bouttons de Mise
				big.drawImage(getImg(buttons[2]), bCoords[8].x, bCoords[8].y, null);
				big.drawImage(getImg(buttons[3]), bCoords[9].x, bCoords[9].y, null);
				
				//Bouttons de Mise +10
				big.drawImage(getImg(buttons[13]), bCoords[12].x, bCoords[12].y, null);
				big.drawImage(getImg(buttons[14]), bCoords[13].x, bCoords[13].y, null);
				
				//Mise Actuelle
				drawWage(big, bj.wage);

				//Afficher Quitter
				big.drawImage(getImg(buttons[12]), bCoords[11].x, bCoords[11].y, null);

				if(bj.canPlayerWage){
					//Bouttons de Mise
					big.drawImage(getImg(buttons[4]), bCoords[8].x, bCoords[8].y, null);
					big.drawImage(getImg(buttons[5]), bCoords[9].x, bCoords[9].y, null);
					//Bouttons de Mise +10
					big.drawImage(getImg(buttons[15]), bCoords[12].x, bCoords[12].y, null);
					big.drawImage(getImg(buttons[16]), bCoords[13].x, bCoords[13].y, null);

					//Afficher Parier
					big.drawImage(getImg(buttons[6]), bCoords[7].x, bCoords[7].y, null);

					//Afficher Sauver
					big.drawImage(getImg(buttons[9]), bCoords[10].x, bCoords[10].y, null);

					//Afficher les cartes face cachee (Joueur puis IA)
					drawHiddenCard(big, bCoords[0].x, bCoords[0].y);
					drawHiddenCard(big, bCoords[1].x, bCoords[1].y);
					drawHiddenCard(big, bCoords[2].x, bCoords[2].y);
					drawHiddenCard(big, bCoords[3].x, bCoords[3].y);

				}else{
					//Afficher Tirer
					big.drawImage(getImg(buttons[1]), bCoords[7].x, bCoords[7].y, null);
					
					//Afficher Stop
					big.drawImage(getImg(buttons[8]), bCoords[10].x, bCoords[10].y, null);
					
					//Afficher les cartes du Joueur
					drawPlayerCards(big);

					if(bj.getTurn().equals("computer")){
						//Afficher les cartes de l'IA
						drawComputerCards(big);
					}else{
						//N'afficher qu'une seule des deux cartes de l'IA
						drawHiddenCard(big, bCoords[3].x, bCoords[3].y);
						drawCard(big, bCoords[2].x, bCoords[2].y, bj.computer.getAllCards().get(0));
						
						//Afficher Assurance
						if(bj.computer.getAllCards().get(0).getNumber()==0){
							big.drawImage(getImg(buttons[17]), bCoords[14].x, bCoords[14].y, null);
							if(bj.player.insurance){
								big.drawImage(getImg(buttons[18]), bCoords[14].x + bCoords[14].w, bCoords[14].y, null);
							}
						}
						
						//Afficher Doubler
						if(bj.player.getCoins()>=bj.wage*2){
							big.drawImage(getImg(buttons[20]), bCoords[16].x, bCoords[16].y, null);
						}

						//Afficher Split
						if(bj.player.canSplit(bj.wage)){
							big.drawImage(getImg(buttons[19]), bCoords[15].x, bCoords[15].y, null);
						}
					}
				}
			}else{
				//Afficher Jouer
				big.drawImage(getImg(buttons[0]), bCoords[6].x, bCoords[6].y, null);
				//Afficher Charger (en gris si aucune sauvegarde)
				if((new File(Start.getSaveFilePath()).exists())){
					big.drawImage(getImg(buttons[10]), bCoords[7].x, bCoords[7].y, null);
				}else{
					big.drawImage(getImg(buttons[11]), bCoords[7].x, bCoords[7].y, null);
				}
			}
		}

		int w = (int) Start.getJF().getContentPane().getSize().getWidth();
		int h = (int) Start.getJF().getContentPane().getSize().getHeight();

		//Affiche l'image compilee a' l'ecran
		g.drawImage(bi2.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
	}



	/**
	 * 
	 * @param big
	 * @param i
	 * 
	 * Peint la mise
	 */
	private void drawWage(Graphics big, int i) {
		GraphicNumber.drawWage(big, i, bCoords[6].x + bCoords[6].w/2, bCoords[6].y + bCoords[6].h/2 - GraphicNumber.nh/2);
	}

	/**
	 * 
	 * @param big
	 * 
	 * Peint les cartes du joueur
	 */
	public void drawPlayerCards(Graphics big){
		drawPlayerCardsAt(big, Start.getBj().player.currHand);
		
	}
	
	/**
	 * 
	 * @param big
	 * @param hand
	 * 
	 * Peint le tableau hand du joueur
	 */
	private void drawPlayerCardsAt(Graphics big, int hand){
		ArrayList<Card> cards = Start.getBj().player.getCards(hand);
		if(!cards.isEmpty()){
			//7: maximum de cartes sur une ligne
			if(cards.size()<=7){
				int startx = w/2 - 17 - 11 - (cards.size()*cw)/2;
				for(int i=0;i<cards.size();i++){
					drawCard(big, startx + i*(cw + 55), bCoords[0].y, cards.get(i));
				}
			}else{
				ArrayList<Card> cards2 = new ArrayList<Card>(cards.subList(7, cards.size()));
				cards = new ArrayList<Card>(cards.subList(0, 7));

				int startx = w/2 - 17 - 11 - (cards.size()*cw)/2;
				for(int i=0;i<cards.size();i++){
					drawCard(big, startx + i*(cw + 55), bCoords[0].y - ch - 16, cards.get(i));
				}

				int startx2 = w/2 - 17 - 11 - (cards2.size()*cw)/2;
				for(int i=0;i<cards2.size();i++){
					drawCard(big, startx2 + i*(cw + 55), bCoords[0].y, cards2.get(i));
				}
			}
		}
	}
	

	/**
	 * 
	 * @param big
	 * 
	 * Peint les cartes de l'IA
	 */
	public void drawComputerCards(Graphics big){
		BlackJack bj = Start.getBj();
		ArrayList<Card> cards = bj.computer.getAllCards();
		if(!cards.isEmpty()){
			int startx = 1190 - (cards.size()*cw)/2;
			for(int i=0;i<cards.size();i++){
				drawCard(big, startx + i*(cw + 55), bCoords[2].y, cards.get(i));
			}
		}
	}

	/**
	 * 
	 * @param big
	 * @param x
	 * @param y
	 * @param card
	 * 
	 * Peint une carte; d'abord le blanc ensuite le nombre et enfin le type
	 */
	public void drawCard(Graphics big, int x, int y, Card card){
		big.drawImage(getImg(whiteCard), x, y, null);
		big.drawImage(getImg(numbers[card.getNumber()]), x + cw/2 - nw/2, y + ch/2 - nh/2, null);
		big.drawImage(getImg(types[card.getType().ordinal()]), x + 20, y + 20, null);
		big.drawImage(getImg(types[card.getType().ordinal()]), x + 20, y + ch - th - 20, null);
		big.drawImage(getImg(types[card.getType().ordinal()]), x + cw - tw - 20, y + 20, null);
		big.drawImage(getImg(types[card.getType().ordinal()]), x + cw - tw - 20, y + ch - th - 20, null);
	}

	/**
	 * 
	 * @param big
	 * @param x
	 * @param y
	 * 
	 * Peint le dos de la carte
	 */
	public void drawHiddenCard(Graphics big, int x, int y){
		big.drawImage(getImg(cardBack), x, y, null);
	}

	/**
	 * 
	 * @param fileName
	 * @return L'image a' partir de son URL
	 * 
	 */
	public Image getImg(URL fileName){
		return (new ImageIcon(fileName)).getImage();
	}
}