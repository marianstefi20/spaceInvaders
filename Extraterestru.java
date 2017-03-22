import java.awt.*;

/**
* Clasa in care ne definim tipul extraterestru
*/

public class Extraterestru {
	public static int INALTIME_EXTRATERESTRU = 25;
	public static int LATIME_EXTRATERESTRU = 40;

	private int pozitiaStanga = 0;
	private int pozitiaInaltime = 0;

	// Aceasta variabila este importanta fiindca noi ne definim
	// o matrice ce contine extraterestrii si in acest fel vedem
	// foarte usor daca ET a fost nimicit sau nu
	private boolean stareLovitura = false;

	SpaceInvaders spaceInvaders = null;

	private Image imagineExtraterestru = new javax.swing.ImageIcon("alien.jpg").getImage();

	/**
	* Ne construim by default doua lucruri. Cum arata extraterestrul si unde va "trai" el?
 	*/
	public Extraterestru(Image ai, SpaceInvaders si) {
		imagineExtraterestru = ai;
		spaceInvaders = si;
	}
	
	public boolean aFostLovit() {
		return stareLovitura;
	}

	/**
	* Vedem daca o lovitura de-a noastra a distrus extraterestrul
	*/
	public boolean extraterestruLovit(int x, int y) {
		if(stareLovitura) {
			// daca e deja lovit returnam fals
			return false; 
		}

		// Verficam ca si la Nava pe coord X, iar apoi pe Y
		if((x >= pozitiaStanga) && (x <= (pozitiaStanga + LATIME_EXTRATERESTRU))) {
			// x este ok, iar acum facem pentru y
			if((y >= pozitiaInaltime) && (y <= (pozitiaInaltime + INALTIME_EXTRATERESTRU))) {
				stareLovitura = true;
				return true;
			}
		}
		return false;
	}

	/**
    * Setam pozitia extraterestrului pe ecran
    */
    public void setarePozitie(int x, int y) {
    	pozitiaStanga = x;
    	pozitiaInaltime = y;
    }

    /**
    * Returnam pozitia curenta a extraterestrului pe axa X
    */
    public int getXPos() {
    	return pozitiaStanga;
    }

    /**
    * Returnam pozitia curenta pe axa Y
    */
    public int getYPos() {
    	return pozitiaInaltime;
    }

    /**
    * Desenam extraterestrul
    */
    public void desenareExtraterestru(Graphics g) {
    	if(!stareLovitura) { //daca nu e lovit vrem sa-l desenam
    		g.drawImage(imagineExtraterestru, pozitiaStanga, pozitiaInaltime, spaceInvaders);
    	}
    }


}	