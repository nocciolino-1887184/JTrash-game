package model;

import java.util.Collections;
import java.util.Stack;

import model.Carta.Figura;
import model.Carta.Seme;
import model.Carta.Valore;

public class MazzoPesca {
	
	/**
	 * 	Questo mazzo ha tutte le carte (52) che vengono mischiate tra loro e poi prese.
	 * 	Anche se abbiamo carte con semi e colori diversi non ci interessa,
	 * 	in quanto ci interessa solo il valore e la figura delle carte.
	 * 
	 */
	private Stack<Carta> mazzo;
	
	public MazzoPesca() {
		//Nel costruttore creo il mazzo da 52 carte che andrà riempito
		mazzo= new Stack<Carta>();
		
		// NON POSSO ISTANZIARE CLASSI ASTRATTE mazzo.add(new Carta(Valore.QUALSIASI, Figura.JOLLY));
	    //aggiungi seme
		mazzo.add(new CartaFigura(Figura.JOLLY, Seme.NERO));
		mazzo.add(new CartaFigura(Figura.JOLLY, Seme.ROSSO));
		
		for(int s=0; s<4; s++) {		// rosso e nero sono solo per i jolly
			mazzo.add(new CartaValore(Valore.ASSO, Seme.values()[s]));
			mazzo.add(new CartaValore(Valore.DUE, Seme.values()[s]));
			mazzo.add(new CartaValore(Valore.TRE, Seme.values()[s]));
			mazzo.add(new CartaValore(Valore.QUATTRO, Seme.values()[s]));
			mazzo.add(new CartaValore(Valore.CINQUE, Seme.values()[s]));
			mazzo.add(new CartaValore(Valore.SEI, Seme.values()[s]));
			mazzo.add(new CartaValore(Valore.SETTE, Seme.values()[s]));
			mazzo.add(new CartaValore(Valore.OTTO, Seme.values()[s]));
			mazzo.add(new CartaValore(Valore.NOVE, Seme.values()[s]));
			mazzo.add(new CartaValore(Valore.DIECI, Seme.values()[s]));
			
			mazzo.add(new CartaFigura(Figura.RE, Seme.values()[s]));
			mazzo.add(new CartaFigura(Figura.REGINA, Seme.values()[s]));
			mazzo.add(new CartaFigura(Figura.FANTE, Seme.values()[s]));
		}
		
		}
	
	/**
	 *	Metodo che mischia le carte per uniformare il mazzo
	 */
	public void mescola(){
		  Collections.shuffle(mazzo);
	}
	
	/**
	 * 	Questo metodo serve a prendere dalla cima della pila una carta.
	 * 	Successivamente il giocatore potrà metterla nel suo mazzo o scartarla.
	 * 	Ricordiamo che la carta pescata NON viene aggiunta alla mano, ma sostituita.
	 */
	
	public Carta pescaCarta() {
		return mazzo.pop();
	}
	
	/**
	 * 	Ricordiamo che in base al numero di giocatori dovremmo aggiungere mazzi
	 * @param altroMazzo
	 */
	public void addMazzo(Stack<Carta> altroMazzo) {
		mazzo.addAll(altroMazzo);
	}

	public Stack<Carta> restituisciMazzo(){
		return mazzo;
	}
}
