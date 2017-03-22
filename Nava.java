import java.awt.event.*;
import java.util.Vector;
import java.awt.*;

/**
* Space Invaders - Versiunea de iarna.
*
* Lupta-te folosing globuri magice impotriva extraterestrilor taietori de braji
*/

public class Nava implements MouseListener, MouseMotionListener {
	// Definirea variabilelor necesare jocului
	public static final int INALTIME_NAVA = 25;
	public static final int LATIME_NAVA = 15;

	private int x = 0;
	private int pozitiaInaltime = 0;

	SpaceInvaders spaceInvaders = null;

	Vector<Shot> spaceShots = new Vector<Shot>();
	Shot lovitura = null;
	Boss boss = null;

	// Vrem sa tragem cu o singura lovitura
	boolean stareLovitura = false; // n-am lovit pe nimeni

	// Cum vrem sa arate nava
	private Image imagineNava = new javax.swing.ImageIcon("ship.gif").getImage();

	public Nava(SpaceInvaders si) {
		spaceInvaders = si;
		// setam unde ne va fi pozitionata initial nava
		x = (int)((SpaceInvaders.LATIME/2) + (LATIME_NAVA/2));
		//acest spatiu apare fiindca vrem putin spatiu intre baza frame-ului si nava
		pozitiaInaltime = SpaceInvaders.INALTIME - INALTIME_NAVA-20; 
	}

	/**
	* Ne vom folosi de mouse pentru a dirija nava
	* Pentru aceasta avem nevoie doar de preluarea mouseEvent-ului care are construite metodele getX, getY
	*/
	public void mouseMoved(MouseEvent me) {
		int newX = me.getX();
		if( newX > (SpaceInvaders.LATIME - LATIME_NAVA - 10)) {
			// suntem foarte aproape de marginea ferestrei de joc daca nu am depasit-o deka
			x = SpaceInvaders.LATIME - LATIME_NAVA - 10;
		} else {
			// setam noua pozitie x
			x = newX;
		}
	}

	/**
	* Nota* - Metodele de mai jos au fost gasite intr-o forma pe site-ul de mai jos 
	* https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html
	*/
	/**
	* Daca mouse-ul a intrat in fereastra de joc pauzarea jocului este falsa
	*/
	public void mouseEntered(MouseEvent me) {
		spaceInvaders.pauzaJoc(false);
	}

	public void mouseExited(MouseEvent me) {
		spaceInvaders.pauzaJoc(true);
	}
	

	/**
	* Tragem un proiectil in extraterestrii - clic mouse
	*/
	public void mouseClicked(MouseEvent me) {
		//System.out.println("Ai dat clic pe ce trebe");
		Armata armata = spaceInvaders.getArmataExtraterestrii();
		lovitura = new Shot(x + (int)(LATIME_NAVA/2), pozitiaInaltime, armata, spaceInvaders, boss);
		spaceShots.addElement(lovitura);
	}

	public void desenareNava(Graphics g) {
		g.drawImage(imagineNava, x, pozitiaInaltime, spaceInvaders);
		// daca lovitura inca e pe ecran - vrem sa memoram ca un nou element fiecare noua lovitura
		// in lipsa codului de mai jos, imaginea loviturii s-ar pierde la un nou clic
		for(int i=0; i<spaceShots.size(); i++) {
			Shot as  = (Shot)spaceShots.elementAt(i);
			if((as != null) && (as.getStareLovitura())) {
				as.drawShot(g);
			}
		}
	}

	public boolean verificareLovitura(int xShot, int yShot) {
		// Daca nava a fost lovita returnam true, daca nu false
		// Vf. asta cu urmatorul argument complicat...daca xShot e peste nava e ok 
		if((xShot >= x) && (xShot <= (x+LATIME_NAVA))) {
			// x e ok, s-acuma vedem y
			if((yShot >= pozitiaInaltime) && (yShot <= (pozitiaInaltime + INALTIME_NAVA))) {
				// nava a fost lovita
				stareLovitura = true;
				return true;
			}
		}
		return false;
	}

	public void lovitaDeAlien() {
		spaceInvaders.navaImpuscata();
	}

	/**
	* Cateva metode nefolosite pe care le adaug totusi fiindca poate o sa le folosesc candva
	*/
	public void mouseDragged(MouseEvent me) {

	}
	public void mouseReleased(MouseEvent me) {

	}
	public void mousePushed(MouseEvent me) {

	}
	public void mousePressed(MouseEvent me) {
		
	}
}