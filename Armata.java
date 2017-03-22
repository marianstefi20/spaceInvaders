import java.awt.*;
import java.util.*;

public class Armata {
	// cream un array de extraterestrii
	Extraterestru armata[][] = new Extraterestru[3][10];

	// directia in care se va misca armata
	boolean spreDreapta = true;

	// numarul de pixeli cu care sa se miste la o iteratie
	int distantaMiscare = 15;

	// un container in care sa stocam datele despre loviturile extraterestrilor
	Vector<ShotExtraterestru> lovituriExtraterestru = new Vector<ShotExtraterestru>();
	
	// O variabila stareArmata care ne spune daca mai exista sau nu extraterestri la atac
	boolean stareArmata = true; // exista by default armata

	private Nava nava;

	private SpaceInvaders spaceInvaders;

	Image imagineExtraterestru = null;

	public Armata(Nava s, SpaceInvaders si, Image ai) {
		nava = s;
		spaceInvaders = si;
		imagineExtraterestru = ai;
		creeazaArmata();
		setarePozitieStart();
	}

	/**
     * In aceasta metoda ne initializam aarmaata de extraterestri
     * Ea consta din 30 de extraterestri distribuiti pe 3 randuri.
     */
	private void creeazaArmata() {
		for(int i=0; i< 3; i++) 
			for(int j=0; j<10; j++) {
				armata[i][j] = new Extraterestru(imagineExtraterestru, spaceInvaders);
			}
	}

	/**
     * Setam de unde va incepe atacul armata de extraterestri
     */
    private void setarePozitieStart() {
        int inaltimeRand = 50;// Setam inaltimea primului rand
        int startStanga = 50;// Setam cea mai indepartata pozitie la stanga (a primului extraterestru)
        
        for(int j=0; j < 3; j++) {
	        for (int i = 0; i < 10; i++) {
	            armata[j][i].setarePozitie(startStanga, inaltimeRand);
	            startStanga += 40; // functioneaza ca un offset. Fara el toti extraterestri ar fi unul peste altul
	        }
			inaltimeRand += 50; //Gata pentru randul 2
			startStanga = 50;//Resetam pozitia extrema la stanga
		}
    }

    /**
     * In aceasta metoda mutam armata de extraterestri.
     */
    public void mutaArmata() {
	
        if (spreDreapta) {
            // Daca ne miscam spre dreapta
	    
            //Primul lucru este sa verificam daca extraterestul aflat cel mai in dreapta loveste marginea
		    for(int j=0; j < 3; j++) {
		    	for (int i = 9; i >= 0; i--) { // Verificam pe rand fiecare extraterestru daca a fost lovit
	          		if (!armata[j][i].aFostLovit()) {
		            	// Daca extraterestrul i nu a fost lovit inseamna ca el reprezinta marginea armatei pe acel rand
	            		// si acum vom verifica daca randul armatei este chiar la marginea DREAPTA a ecranului
					    if (armata[j][i].getXPos() > (SpaceInvaders.LATIME-Extraterestru.LATIME_EXTRATERESTRU-15)) {
			                // Schimbarea de directie
					    	spreDreapta = false;
			                // Setam si pozitia y la o valoare mai mica
			                for(int z = 0; z < 3; z++)
			                	for (int y = 0; y < 10; y++) 
			                    	armata[z][y].setarePozitie(armata[z][y].getXPos(), armata[z][y].getYPos()+distantaMiscare);     
			                return;//asta nu face si altele asa ca o returnez pur si simplu
			            }
				    }
	            } 
		    }	// end for   
            // Mutam pe toata lumea spre dreapta
            for(int j=0; j < 3; j++) 
            	for (int i = 0; i < 10; i++) 
               		armata[j][i].setarePozitie(armata[j][i].getXPos()+distantaMiscare, armata[j][i].getYPos());
        } else {
            // Ne miscam spre stanga acum
	    
            //Primul pas e sa vedem daca extraterestrul aflat cel mai la stanga a fost lovit
        	for(int j=0; j < 3; j++) {
        		for (int i = 0; i < 10; i++) {
	                if (!armata[j][i].aFostLovit()) {
	                	// Primul extraterestrul i care nu a fost lovit inseamna ca reprezinta marginea armatei pe acel rand
		            	// si acum vom verifica daca randul armatei este chiar la marginea STANGA a ecranului
	                	if (armata[j][i].getXPos() < Extraterestru.LATIME_EXTRATERESTRU) {
	                        //Schimbam directia
	                		spreDreapta = true;

	                        //Setam noile coordonate Y mai mici
	                        for(int z=0; z < 3; z++)
	                        	for (int y = 0; y < 10; y++) 
	                           		armata[z][y].setarePozitie(armata[z][y].getXPos(), armata[z][y].getYPos()+distantaMiscare);  
	                        return;//Return from this method, don't bother checking the rest.		
	                	}
	                }
	            }               
        	}// end for
		
            // Mutam pe toti la stanga
            for(int j=0; j < 3; j++)
            	for (int i = 0; i < 10; i++) 
                	armata[j][i].setarePozitie(armata[j][i].getXPos()-distantaMiscare, armata[j][i].getYPos());
                
        }
        
		// Generarea de atacuri random
		Random generator = new Random();
		int rnd1 = generator.nextInt(10);
		int rnd2 = generator.nextInt(10);
		int rnd3 = generator.nextInt(10);
		if (!armata[0][rnd1].aFostLovit()) { // daca nu am fost loviti inca putem sa...
		    ShotExtraterestru as = new ShotExtraterestru(armata[0][rnd1].getXPos()+(int)(Extraterestru.LATIME_EXTRATERESTRU/2), armata[0][rnd1].getYPos(), nava, spaceInvaders);
	        lovituriExtraterestru.addElement(as);
		}
		if (!armata[1][rnd2].aFostLovit()) {	
		    ShotExtraterestru as = new ShotExtraterestru(armata[1][rnd2].getXPos()+(int)(Extraterestru.LATIME_EXTRATERESTRU/2), armata[1][rnd2].getYPos(), nava, spaceInvaders);
	        lovituriExtraterestru.addElement(as);
		}
		if (!armata[2][rnd3].aFostLovit()) {	
	    	ShotExtraterestru as = new ShotExtraterestru(armata[2][rnd3].getXPos()+(int)(Extraterestru.LATIME_EXTRATERESTRU/2), armata[2][rnd3].getYPos(), nava, spaceInvaders);
	        lovituriExtraterestru.addElement(as);
		}
    }// end metoda

    /**
    * Desenarea armatei de extraterestri
    */

    public void desenareArmata(Graphics g) {
    	// Desenam randurile
    	int counter = 0;
    	
    	//long timpulMeu = date.getTime();
    	for(int j=0; j< 3; j++) {
 			for (int i = 0; i < 10; i++) {
 				// if-ul e doar un mesaj atunci cand ai reusit sa ii ucizi pe toti
 				if(armata[j][i].aFostLovit()) {
 					counter++;
 					if(counter == 30 ) {		
 						stareArmata = false; // nu mai exista armata
	                	//Font f = new Font("TimesRoman", Font.PLAIN, 24);
			    		//g.setFont(f);
			    		//g.setColor(Color.red);
			    		//g.drawString("Ai omorat toti extraterestri", 150, 70);
 			    	}
 				}
 	          	armata[j][i].desenareExtraterestru(g); // metoda e importanta fiindca verifica starea extraterestrului
 	          	//Acum verificam daca unul din extraterestri ajunge in terenul tau...daca da scade scor
 	          	if((armata[j][i].getYPos() + Extraterestru.INALTIME_EXTRATERESTRU) > (SpaceInvaders.INALTIME - 50)) {
 	          		spaceInvaders.navaCompromisa();
 	          	}
 			}
    	}
    	
  		// Acum desenam loviturile facute de extraterestru
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

    /**
     * Detectia coliziunilor - in esenta numa schimbam un scor
     */
    public boolean verificaLovitura(int x, int y) {
    	for(int j=0; j< 3; j++) {
    		for (int i = 0; i < 10; i++) {
    			if (armata[j][i].extraterestruLovit(x, y)) {
    				spaceInvaders.scorExtraterestruLovit();
    				return true;
    			}
    		}
    	}
        return false;
    }
}