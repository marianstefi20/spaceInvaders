import java.awt.*;
import java.util.Vector;
import java.util.Random;

public class Boss implements Runnable {
	public static int INALTIME_BOSS = 50;
	public static int LATIME_BOSS = 100;
	
	private int pozitiaStanga = 0;
	private int pozitiaInaltime = 0;
	
	private Nava nava;
	public int rezolvaPamant;
	public int vitezaBoss = 10;
	public int total = vitezaBoss;
	
	public int hitCounter = 0;
	boolean stareArmata = false;
	SpaceInvaders spaceInvaders = null;
	
	private boolean spreDreapta = true; // by default ne miscam spre dreapta
	
	private Image imagineBoss = new javax.swing.ImageIcon("boss.png").getImage();
	
	// Loviturile boss-ului
	Vector<ShotExtraterestru> lovituriExtraterestru = new Vector<ShotExtraterestru>();
	
	public Boss(SpaceInvaders si, Nava s) {
		spaceInvaders = si;
		nava = s;
		Thread thread = new Thread(this);
		thread.start();
	} 
	
	public void desenareBoss(Graphics g, boolean stareArmata) {
		this.stareArmata = stareArmata;
		if((!stareArmata) && (hitCounter < 10)) { //doar daca nu mai avem armata il aducem pe boss
			g.drawImage(imagineBoss, pozitiaStanga, pozitiaInaltime, spaceInvaders);
		
			// Acum desenam loviturile facute de boss
	    	Vector<ShotExtraterestru> tmp = new Vector<ShotExtraterestru>();
	    	for(int i=0; i < lovituriExtraterestru.size(); i++ ) {
	    		ShotExtraterestru as = (ShotExtraterestru)lovituriExtraterestru.elementAt(i);
	    		// Trebuie sa eliminam vechile shot-uri
	    		if(as.getStareLovitura()) {
	    			// Nu va fi shot vechi deci il putem adauga la vectorul nostru
	    			tmp.addElement(as);
	    		}
	    		as.desenareLovitura(g);
	    	}
	    	lovituriExtraterestru = tmp;
		} 
	}
	
	public boolean bossLovit(int x, int y) {
		// Verficam ca si la Nava pe coord X, iar apoi pe Y
		if((x >= pozitiaStanga) && (x <= (pozitiaStanga + LATIME_BOSS))) {
			// x este ok, iar acum facem pentru y
			if((y >= pozitiaInaltime) && (y <= (pozitiaInaltime + INALTIME_BOSS))) {
				hitCounter++;
				return true;
			}
		}
		return false;
	}
	
	private void mutaBoss() {
		if(!stareArmata) { // doar daca nu avem armata facem ceva
			if(spreDreapta) {
				if(pozitiaStanga + Boss.LATIME_BOSS <= SpaceInvaders.LATIME) { 
					pozitiaStanga += 5;
				} else spreDreapta = false; //inseamna ca mergem catre stanga
			}
			if(!spreDreapta) {
				if(pozitiaStanga >= 0) { 
					pozitiaStanga -= 5;
				} else spreDreapta = true; //inseamna ca mergem catre stanga
			}
			Random rand = new Random();
			int rnd = rand.nextInt(10);
			int cnt = 0;
			if(cnt == rnd) {
				ShotExtraterestru as = new ShotExtraterestru(pozitiaStanga+(Boss.LATIME_BOSS/2), pozitiaInaltime+(Boss.INALTIME_BOSS), nava, spaceInvaders);
				lovituriExtraterestru.addElement(as);
				cnt++;
			}
		}
	}
	
	/**
	 * Cum mi-as dori sa fac boss-ul sa mearga cool. Introduc o functie de un singur parametru  */
//	public float fcPol(int timp) { 
//		//return  (int)(Math.pow(Math.sin(timp)*timp*timp*timp + timp, 0.3)/vitezaBoss);
//	}
	
	public void run() {
		while(spaceInvaders.running) {		
			try {
				Thread.sleep(vitezaBoss);
			} catch(InterruptedException ie) {
				// nimic
			}
			mutaBoss();
		}
	}
}
