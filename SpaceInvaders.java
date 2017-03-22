import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

/**
* Clasa principala a jocului - In aceasta clasa se integreaza celelalte
* clase si se creeaza fereastra de joc
*/
public class SpaceInvaders extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int LATIME = 600;
	public static int INALTIME = 400;

	private int vitezaJoc = 50;

	Armata armata = null;
	
	Boss boss = null;

	Nava nava = null;

	private boolean pauza = false; // pp ca nu e pauza by default

	private int scor = 0;

	Graphics offscreen_high; // Cu acest obiect vom scrie pe imagine
	BufferedImage offscreen; // Imaginea care va contine tot ce s-a desenat a priori

	Image backGroundImage = null;
	Image imagineExtraterestru = null;
	
	// Metoda ce inchide thread-ul principal al jocului SpaceInvaders
	public volatile boolean running = true;

	public SpaceInvaders(String titluJoc) {
		super(titluJoc);

		backGroundImage = new javax.swing.ImageIcon("back3.jpg").getImage();
		imagineExtraterestru = new javax.swing.ImageIcon("alien.jpg").getImage();
		

		// cream o instanta a navei cu care o sa ne luptam
		nava = new Nava(this);

		// cream armata de extraterestri
		armata = new Armata(nava, this, imagineExtraterestru);
		
		//cream un boss
		boss = new Boss(this, nava);
		
		nava.boss = boss;
		

		// Nava va fi controlata de catre mouse
		addMouseListener(nava);
		// si avem nevoie de inregistrarea miscarii mouseu-ului
		addMouseMotionListener(nava);

		offscreen = new BufferedImage(LATIME, INALTIME, BufferedImage.TYPE_INT_RGB);
		offscreen_high = offscreen.createGraphics();

		setBackground(Color.green);
		setSize(LATIME, INALTIME);
		setVisible(true);
		startJoc();
	}

	/**
	* Cand scoti mouse-ul din zona de joc am vrea ca jocul sa fie pauzat
	*/
	public void pauzaJoc(boolean state) {
		pauza = state;
	}

	/**
	* Omorand un alien adaugam 5 puncte la scor
	*/
	public void scorExtraterestruLovit() {
		scor += 5;
		System.out.println("Scorul curent = " + scor);
	}

	/**
	* De esti impuscat pierzi 20 de puncte
	*/
	public void navaImpuscata() {
		scor -= 20;
		System.out.println("Scorul curent = " + scor);
	}
	
	/**
	 * Daca ti-au intrat extraterestrii in teritoriu e grav
	 */
	public void navaCompromisa() {
		scor -= 100;
		System.out.println("Ce se intampla e grav!");
	}

	/**
	* Metoda care incepe executia jocului <=> este vorba de un nou proces
	* => vom folosi iarasi un Thread
	*/
	public void startJoc() {
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	* Desenarea elementelor existente in joc
	* Se foloseste double buffering-ul. Pentru mai multe detalii si sursa asemanatoare cu ce mai jos:
	* http://www.realapplets.com/tutorial/doublebuffering.html
	* ^Nota: Algoritmul este foarte general si nu este restrictionat doar la aplicatii de acest gen
	*/
	public void paint(Graphics g) {
        offscreen_high.setColor(Color.black);
        offscreen_high.fillRect(0,0, LATIME, INALTIME);
        
        // Desenam o linie sub care tot ce se intampla e grav - in esenta ai pierdut
        offscreen_high.setColor(Color.green);
        offscreen_high.drawLine(0, SpaceInvaders.INALTIME - 50, SpaceInvaders.LATIME, SpaceInvaders.INALTIME - 50);
        
        // Desenam in final
        if(boss.hitCounter >= 10) {			
			Font f = new Font("TimesRoman", Font.PLAIN, 24);
    		offscreen_high.setFont(f);
    		offscreen_high.setColor(Color.red);
    		offscreen_high.drawString("Ai castigat!! Esti mare caracter!", 120, 70);
    		running = false; // spunem sa ne oprim
		}

        armata.desenareArmata(offscreen_high);

        nava.desenareNava(offscreen_high);
        
        boss.desenareBoss(offscreen_high, armata.stareArmata);

        g.drawImage(offscreen,0,0,this); 
        
    }

    public void update(Graphics g) {
        paint(g);
    }

	public void mutaExtraterestri() {
		armata.mutaArmata();
	}

	/**
	* Metoda run() obligatorie datorita implementarii interfetei Runnable
	* Ea ruleaza un Thread principal cu care se muta extraterestrii si se redeseneaza intregul ecran
	*/
	public void run() {
		int count = 0;
		while(running) {
			try {
				Thread.sleep(vitezaJoc);
			} catch(InterruptedException ie) {
				// iarasi ignoram ca nu vrem sa facem nimic cand asteptam
			}

			// Daca jocul nu e pauzat am vrea sa miscam extraterestrii
			if(!pauza) {
				// vrem la fiecare 5*gameSpeed sa miscam extraterestrii
				if(count >= 5) {
					mutaExtraterestri();
					count = 0;
				}
			}
			repaint(); // Metoda actualizeaza elementele ecranului
			count++;
		}
	}
	
	 /**
     * Trebuie sa existe o referinta la armata de extraterestri
     */
    public Armata getArmataExtraterestrii() {
        return armata;
    }

    /**
     * This is the program entry point
     */
    public static void main(String []args) {
        SpaceInvaders invaders = new SpaceInvaders("Space Invaders");
    }

}