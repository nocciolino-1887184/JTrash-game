package model;

import java.security.PublicKey;
import java.util.Stack;

public class MazzoScarto {
	
	private Stack<Carta> scarti;
	
	public MazzoScarto() {
		scarti= new Stack<Carta>();
		
	}
	
	/**
	 * Con questo metodo posso togliere una carta dalla mano di un giocatore e metterla nello mazzo degli scarti
	 * @param c
	 */
	public void scartaCarta(Carta c) {
		 scarti.push(c);
	}
	
	
	

}
