package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import model.Carta.Figura;

public class Bot extends Giocatori{

	public Bot(String nome) {
		super(nome);
	}
	
	/**
	 * 	Il bot gioca da solo e le sue scelte non influenzano quelle del giocatore umano. 
	 * 	Il bot segue le regole del gioco e possiede tutti i metodi di "Giocatori"
	 * 	
	 * 	Esso dovrà quindi estrarre una carta dal mazzo PESCA, vedere se può essere scambiata, 
	 * 	scartare l'ultima carta rimasta e passare il turno
	 */
	private RegoleGioco regole;
	
	/**
	 * 	Questo metodo returna la carta scartata così da mostrarla nella view (l'utente vede cambiamenti)
	 * @return carta scartata
	 */
	public Carta gioca() {
		/**
		 * 	Il bot gioca DA SOLO. Quindi pesca una carta, controlla se può usarla, la sostituisce dove deve e infine scarta
		 */
		prendoCarta( regole.pescaSingola() );
		while(!regole.isScarto(cartaPresa(), getMano())) {
			/** dato che il bot dovrà scegliere quale carta sostituire (se pesca un jolly)
			 * 	che possiamo sostituire con quella che abbiamo in mano
			 */
//		se ho un jolly posso sostituirlo con la prima carta non girata che vedo
			
			if(cartaPresa() instanceof CartaFigura) {
				for(Carta c: getMano()) {	// scorro la mano in cerca di una mano non girata
					if(!c.checkCard()) {  //se NON è girata
						Carta temp= c;
						c= cartaPresa();
						prendoCarta(temp);
						temp.giraCarta();  // girata true
						return temp;
					}
				}
				//se esco da qui, non ho trovato carte con cui sostituirla...TUTTE LE CARTE SON GIRATE
				return cartaPresa();	//scarto quindi la pescata..ROUND FINITO!
			} 
			// se è carta valore
			prendoCarta(  regole.switchCard(cartaPresa(), getMano(), ((CartaValore)cartaPresa()).getNumero()-1)  );
			//finché prendo la carta e la switcho senza problemi...ok. Poi la smetto
		}
		// regole.scarta(cartaPresa());
		return cartaPresa();
	}
	
	/**
	 * 	Metodo che impone certe regole al bot per giocare
	 * @param rules
	 */
	public void stabilisciRegole(RegoleGioco rules) {
		regole= rules;
	}

	public boolean fineRound() {
		if(regole.roundFinito(getMano()))
		{
			System.out.println(getNickname()+" ha terminato le carte da girare");
			togliCarta();
			return true; 
		}
		
		return false;
	}
	
	

}
