package model;

import java.util.ArrayList;
import java.util.Arrays;

import controller.Observer;

public abstract class Giocatori { //scegliamo una classe astratta perché usata per oggetti accomunati 
	
    /** 
	 * Questa classe rappresenta il prototipo di giocatore, che sia umano o un bot.
	 * I metodi saranno comuni ad entrambe le classi "Umano" e "Bot".
	 * Ogni giocatore avrà un nome e una mano (10 carte che poi si riducono ogni round).
	 */

	private String nickname;	
//	private int numTurno; //	numero utile nella identificazione del giocatore nella turnazione. NELLA SUPER O A PARTE???
	private ArrayList<Carta> mano;
	private int numCarte=10;		// Viene diminuito se vinco un round
	private Carta cartaPescata;		// qui mi salvo la carta che pesco e che devo controllare se posso sostituirla
	// private int vittorie=0;
	
	public Giocatori(String nome) {
		this.nickname= nome;
		mano= new ArrayList<Carta>();
	}

	public ArrayList<Carta> getMano() {
		return mano;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * Nel gioco Trash quando pesco una carta, posso metterla nel suo spazio (numero=indirizzo)
	 * per poi rimuovere la carta che sostituisco e posizionarla a sua volta in un'altra posizione 
	 * 
	 * @param c : carta da sostituire
	 * @param num : valore numerico che ha la carta, usata come indirizzo di inserimento
	 * 
	 * 	Per questo avremo due metodi, uno per aggiungere carte alla mano (inizio gioco)
	 * 	e uno per pescare e vedere se sostituire una carta
	 */
	
	public void addCarta(Carta aggiunta) {	//aggiungiamo una carta alla mano quando viene creata essa
		mano.add(aggiunta);
	}
	
	/**
	 * 	Il giocatore non scarta una carta dal mazzo o la pesca per aggiungerla alla sua mano, anzi
	 * 	avrò una var dove salvo la carta che tengo da parte e uso per scambiarla con la mia mano.
	 */
	public void prendoCarta(Carta questa) { cartaPescata= questa; }
	public Carta cartaPresa() { return cartaPescata;}
	
		//  NON posso aggiungere una carta alla mano, DEVO SOSTITUIRLA
	public ArrayList<Carta> resetMano() { mano= new ArrayList<>(); return mano;}

	
	/**
	 * 	Ogni volta che viene vinto un turno, la mano del giocatore si riduce di una carta
	 * 	Ovviamente se si toglie una carta si ricomincia il gioco
	 */
	public void togliCarta() {
		if(numCarte==1) System.out.print("Le carte sono finite, non puoi toglierne altre!");
		else {	numCarte= numCarte-1;
		mano= new ArrayList<>(numCarte); }	// è vuota così la mano, si deve ripopolare
		// MANO RESETTATA con nuova lunghezza
	}
	
	public int getNumCarte() {
		return numCarte;
	}

	

}
