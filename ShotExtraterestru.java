import java.awt.*;

/**
* Clasa cu care miscam lovitura data de extraterestru efectiv pe ecran. 
* Pentru aceasta folosim un Thread si implementam metoda run ce e de fapt un while(true) care misca
* la fiecare iteratie cu un numar de px lovitura. 
* Totusi, vrem sa asteptam putin la fiecare iteratie si atunci apelam Thread.sleep(<timp>)
*/

public class ShotExtraterestru implements Runnable {
	private int vitezaLoviturii = 10;
	private int LATIME_LOVITURA = 10;
	private int INALTIME_LOVITURA = 23;

	private int x = 0;
	private int inaltimeLovitura = 0;

	boolean stareLovitura = true; // exista 

	Nava nava = null;
	SpaceInvaders spaceInvaders = null;

	private Image shotExtraterestru = new javax.swing.ImageIcon("arrowshot.png").getImage();


	public ShotExtraterestru(int xVal, int yVal, Nava s, SpaceInvaders si) {
		x = xVal; // Setam directia loviturii
		spaceInvaders = si;
		inaltimeLovitura = yVal;
		nava = s;
		// Cream un nou fir de executie pentru lovitura
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	* Mutarea efectiva a loviturii
	*/
	private boolean mutaLovitura() {
		if(nava.verificareLovitura(x, inaltimeLovitura)) {
			System.out.println("Am fost impuscati de un extraterestru!");
			nava.lovitaDeAlien();
			stareLovitura = false; // nu mai exista lovitura acum
			return true;
		}

		// ...inaintam
		inaltimeLovitura +=1;

		// Verificam sa nu fi iesit din ecran ca atunci nu are rost sa mai rulam firul de executie
		if(inaltimeLovitura > SpaceInvaders.INALTIME) {
			stareLovitura = false;
			return true;
		}
		return false;
	}

	/**
     * Desenam imaginea loviturii
     */   
	public void desenareLovitura(Graphics g) {
		if(stareLovitura) {
			//g.setColor(Color.green);
			//g.fillRect(x, inaltimeLovitura, LATIME_LOVITURA, INALTIME_LOVITURA);
			g.drawImage(shotExtraterestru, x, inaltimeLovitura, spaceInvaders);
		} else {
			g.setColor(Color.black);
		}
	}

	public boolean getStareLovitura() {
		return stareLovitura;
	}

	/**
	* Thread-ul muta efectiv lovitura prin apelarea repetata datorita lui while
	* metodei moveShot() si asteptand o anumita perioada de timp
	*/
	public void run() {
		while(true) {
			try {
				Thread.sleep(vitezaLoviturii);
			} catch(InterruptedException ie) {
				// momentam nu vrem sa facem nimic cu lovitura cat asteptam
			}

			// mutaLovitura returneaza false cat timp nu am lovit ceva
			// sau nu am iesit de pe ecranul de joc
			if(mutaLovitura()) {
				break;
			}
		}
	}
}