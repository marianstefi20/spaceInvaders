import java.awt.*;

public class Shot implements Runnable {
	private int vitezaLoviturii = 10;

	private int LATIMEA_LOVITURII = 12;
	private int INALTIMEA_LOVITURII = 23;

	private int x = 0;
	private int shotHeight = 0;

	boolean stareLovitura = true;
	private Image imagineLovitura = new javax.swing.ImageIcon("supermanshot.gif").getImage();
	Armata armata = null;
	Boss boss = null;
	SpaceInvaders spaceInvaders = null;

	public Shot(int xVal, int yVal, Armata aa, SpaceInvaders si, Boss seful) {
		x = xVal;
		shotHeight = yVal;
		armata = aa;
		boss = seful;
		spaceInvaders = si;
		Thread thread = new Thread(this);
		thread.start();
	}

	private boolean mutaLovitura() {
		if(armata.verificaLovitura(x, shotHeight)) {
			// Am lovit ceva din armata
			System.out.println("Am impuscat un extraterestru!");
			stareLovitura = false;
			return true;
		}
		
		if(boss.bossLovit(x, shotHeight)) {
			stareLovitura = false;
			return true;
		}

		shotHeight -= 2;

		// Verificam daca am iesit in afara ecranului
		if(shotHeight < 0) {
			stareLovitura = false;
			return true;
		}
		return false;
	}

	/**
	* Desenarea loviturii propriu zise
	*/
	public void drawShot(Graphics g) {
		if(stareLovitura) {
			g.drawImage(imagineLovitura, x, shotHeight, spaceInvaders);
			g.setColor(Color.green);
		} else {
			g.setColor(Color.red);
		}
	}

	public boolean getStareLovitura() {
		return stareLovitura;
	}

	/**
	* Thread-ul care misca shot-ul
	*/
	public void run() {
		while(true) {
			try {
				Thread.sleep(vitezaLoviturii);
			} catch(InterruptedException ie) {
				// nu facem din nou ceva
			}
			// Daca folosim moveshot() facand abstractie de shotState vom putea folosi aceeasi
			// lovitura pentru a omora toti extraterestrii din drumul ei
			//mutaLovitura();

			if(mutaLovitura()) {
				break;
			}
		}
	}

}