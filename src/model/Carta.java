package model;

import javax.swing.ImageIcon;

public abstract class Carta {
	
	public enum Seme{
		CUORI, PICCHE, QUADRI, FIORI, ROSSO, NERO
	}
	
	public enum Valore{
		ASSO(1), DUE(2), TRE(3), QUATTRO(4), CINQUE(5), SEI(6), SETTE(7), OTTO(8), NOVE(9), DIECI(10);
		
		int valore;
		Valore(int v){ valore=v;}
	}
	
	public enum Figura{
		JOLLY, RE, REGINA, FANTE
	}
	

	private Seme seme;
	private boolean cartaGirata=false;
	//private ImageIcon iconaCarta; 
	
	/**
	 * 	Le carte possono avere un valore oppure avere una figura che ha degli effetti
	 * 	Ad esempio il JOLLY e il RE possono esser messi in qualsiasi posto, 
	 * 	mentre REGINA e FANTE sono da scartare
	 * @param tipoCarta
	 */
	
	public Carta(Seme seme) {
		this.seme=seme;
		} 

	
	// Chi gira tutte le carte per primo vince!
	public boolean checkCard() {
		return cartaGirata;
	}
	
	public void giraCarta() {
		cartaGirata=true;
	}
	
	public Seme getSeme() {
		return seme;
	}
}
