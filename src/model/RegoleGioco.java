package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import controller.GameController;
import controller.Observable;
import controller.Observer;
import model.Carta.Figura;
import model.Carta.Seme;
import model.Carta.Valore;

public class RegoleGioco extends Observable {
	
	/**
	 * 	In questa classe vengono specificate tutte le regole del gioco Trash.
	 * 	Queste regole sono seguite in automatico dal BOT e con eccezioni dall'umano.
	 * 	Le principali regole sono:
	 * 	- in base al numero dei giocatori si stabilisce il numero dei mazzi impiegati
	 * 	- una volta pescata una carta devo posizionarla nella mia mano come segue
	 * 		- jolly e re: metto in qualsiasi posizione
	 * 		- regina e fante: scartati
	 * 		- gli altri numeri in ordine
	 * Se una carta è già girata, scartiamo quella che abbiamo in mano
	 * 	- il primo che volta tutte le carte vince
	 * 	- si va avanti con i turni diminuendo il numero di carte in mano del vincitore
	 * 	- chi rimane con 1 carta vince
	 * 
	 * 	è importante tener traccia del giocatore corrente per regolare la turnazione (sempre senso orario).
	 * 	Ricordiamo che NON vi è interferenza tra giocatori (non si possono rallettare, bloccare etc)
	 */
	
	private ArrayList<Giocatori> players;
	private int giocatoreCorrente; //tenerne traccia durante turnazione
	private MazzoPesca pesca;
	private MazzoScarto scarta;
	private GameController controller;
	private ArrayList<Observer> observers = new ArrayList<>();	//lista di chi osserva, i giocatori
	
	//	Il costruttore prende in input i giocatori (già creati nel controller) e il controller
	public RegoleGioco(ArrayList<Giocatori> g, GameController gc)
	{
		players=g;
		controller=gc;
		scarta= new MazzoScarto();
		/**
		 *  Per creare il mazzo di pesca ci basiamo sul numero di giocatori
		 *  	2 giocatori useranno 1 mazzo
		 *  	3/4 giocatori userannno 2 mazzi
		 */
		if(g.size()>2) {
			pesca= new MazzoPesca();
			pesca.addMazzo(new MazzoPesca().restituisciMazzo());
		}
		else pesca= new MazzoPesca();
		pesca.mescola();	//	mescoliamo il mazzo per poi distribuire 10 carte ad ogni giocatore
		
		for(Giocatori giocatori: players)
		{
			for(int i=0; i<10; i++)
			{
				giocatori.addCarta(pesca.pescaCarta());	// distribuisco 10 carte ad ogni giocatore. Che siano 2-3-4, anche all'umano (non farlo nelc ontrller)
			}
		}	
		giocatoreCorrente= 0;	// giocatori numerati da 0 a 3 (cioè massimo 4 giocatori)
		
		System.out.println("10 carte distribuite a tutti");
		
	}
	
	// Il turno sarà sempre orario e questo metodo fa ricominciare dal giocatore 0 una volta scorsi tutti
	public void avanzaTurno() {	// ... giusto? o VOID?
		if(giocatoreCorrente<players.size()-1)  giocatoreCorrente+=1;
		else  giocatoreCorrente=0; 
	}
	// potrebbe servire per regolare turni
		public int getGiocatCorrent() { return giocatoreCorrente;}
	
	/**
	 * 	Il giocatore non scarta una carta dal mazzo o la pesca per aggiungerla alla sua mano, anzi
	 * 	avrò una VARIABILE dove salvo la carta che tengo da parte e uso per scambiarla con la mia mano.
	 * 
	 * 	Qui però semplicemente restituisco una carta (che nel controller setto come carta in mano da confrontare)
	 * 	e scarto la carta che avevo se inutile
	 */
	public Carta pescaSingola() {
		return pesca.pescaCarta();
	}
	
	/**
	 * 	Lo scarto avviene solo quando aggiungo la mia carta in mano al mazzo degli scarti
	 * 	altrimenti avviene uno scambio tra la mia carta e una del mazzo.
	 * 	si scarta se si ha un FANTE o REGINA
	 * 	oppure se non posso più sostituire la carta -> già girata
	 * @param sc
	 */
	public void scarta(Carta sc) {
		scarta.scartaCarta(sc);
		// gestisci quando dire TRASH..
		// quando tutte le carte sono girate si dice TRASH per finire il turno
		
	}
	
	/**	
	 * 	CONTROLLO se è un fante o una regina -> scarto
	 * 	Se il numero che ho è già stato girano -> scarto
	 * 	Se carta è JOLLY o RE -> metto dove voglio
	 * 	Se carta è un numero -> metto nell'indice giusto
	 * 
	 * 	RICORDA se tutte le carte sono girate, il giocatore x ha vinto il round
	 * 	si vince se il giocatore ha in mano 1 SOLA CARTA GIRATA
	 * 
	 * 	controlla solo se la carta è già girata o se è un carta da scartare a prescindere
	 * @param controlla
	 * @param mano
	 * @return
	 */
	public boolean isScarto(Carta controlla, ArrayList<Carta> mano)
	{
		if (controlla instanceof CartaFigura) {
				if ( ((CartaFigura)controlla).getFig() .equals(Figura.FANTE) || ((CartaFigura)controlla).getFig().equals(Figura.REGINA) ) return true;		}
		// si scarta anche se la carta X è già girata (+1 perché parte da 0)
		if( controlla instanceof CartaValore && mano.get(((CartaValore) controlla).getNumero() -1).checkCard() ) return true;	//se la carta è già girata si scarta
//		
		return false;
	}
	
	// e poi finché non è da scartare scambio le carte
	/**
	 * 	Usando un while con questo metodo, finché una carta non è da scartare, la scambio con una della mia mano.
	 * 	Il metodo prende in input la carta che ho in mano e il mio mazzo
	 * 	restituirò la carta con cui ho scambiato quella in mano.
	 * @param cartUnica
	 * @param mano
	 * @return carta che era nella mia mano al posto x
	 */
	public Carta switchCard(Carta cartaUnica, ArrayList<Carta> mano, int indice) {		//confronto tra la mano e la carta pescata

			Carta b= mano.get(indice);
			mano.set(indice, cartaUnica);		//INUTILE PER IL GIOCATORE UMANO CHE SEGUE SOLO LA GRAFICA
			mano.get(indice).giraCarta();	// setto a true il fatto che sia stata girata
			cartaUnica= b;
			return cartaUnica;
		//}
	}
	
	
	// sta prima della pescata di ogni giocatore
	public boolean roundFinito(ArrayList<Carta> mano) {

		/** potremmo creare con gli stream un array momentaneo che contiene tutte le carte valide
		 * 	che possiamo sostituire con quella che abbiamo in mano
		 */
		ArrayList<Carta> girate= mano.stream()
				.filter(Carta::checkCard)
				.collect(Collectors.toCollection(ArrayList::new));
		if(girate.size()==10) return true;
		return false;
		/*
		 * 	Successivamente
		 * 	1 il giocatore dice TRASH
		 * 	2 tutti i giocatori hanno ancora la possibilità di pescare 1 carta
		 * 	3 si toglie al vincitore del round 1 carta dal mazzo
		 */
	}
	
	//	Metodo per la fine della PARTITA
	public boolean partitaFinita(ArrayList<Carta> mano) {
		if(mano.size()==1 && mano.get(0).checkCard()) return true;
		// si mette più uno sulle vittorie conseguite dall'umano (se vince l'umano)
		return false;
	}
	
	public void resetPartita() {	// reset non visivo
		// mazzi, mano giocatore e grafica da 0...(view). Viene richiamato dal controller
		scarta= new MazzoScarto();
		if(players.size()>2) {
			pesca= new MazzoPesca();
			pesca.addMazzo(new MazzoPesca().restituisciMazzo());
		}
		else pesca= new MazzoPesca();
		pesca.mescola();		
		// ricorda che uno o più giocatori possono aver vinto e quindi a loro verranno date meno carte
		for(Giocatori g: players)
		{
			/*
			 *  aggiungo le carte in base a quanto è grossa la loro mano 
			 *  (magari più giocatori riducono il mazzo di 1 in un round)
			 */
			int j = g.getMano().size();
			for(int i=0; i<j; i++)
			{
				g.addCarta(pesca.pescaCarta());	System.out.println(g.getMano().get(i));
				
			}
		}	
		giocatoreCorrente= 0;	
		
	}
	
	
	//	--------OBSERVER E SUBJECT------------
	/**
	 * 	Appena avviene uno scarto, il gioco lo notifica a tutti i giocatori
	 * 	in modo da poter far andare avanti la turnazione.
	 * 	Si può infine notificare quando un giocatore ha girato TUTTE le sue carte.
	 * 	OBSERVER: i giocatori
	 * 	OBSERVABLE: la partita
	 */
	@Override
	public void addObserver(Observer o)
	{
		observers.add(o);
	}
	@Override 
	public void removeObserver(Observer o)
	{
		observers.remove(o);
	}
	@Override
	public void notifyTurn(String info)
	{
		for (Observer o: observers) {
			o.update(info);
		}
	}


}
