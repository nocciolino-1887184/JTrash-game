package controller;
import java.io.File;
import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.*;
import model.Carta.*;
import view.*;

public class GameController {


	/**
	 * 	Qui ogni volta che un gioco si avvia, controllo esista già un file e se vuoto chiedo il nome del giocatore corrente
	 * 	Il controller lavora con tutti gli oggetti creati fino ad adesso e fa comunicare la view con il model.
	 * 
	 * 	Il controller prende il nome del giocatore e lo mette in lista, 
	 */
	
	private RegoleGioco regole;
	private CentralFrame view;

	private ArrayList<Carta> mano;		//inizializzata nelle regole
	private int giocatoreCorrente;		//lo prendo dalle regole
	private ArrayList<Giocatori> giocatori;
	private boolean ultimoGiro;
	private boolean roundFinito;
	private Carta cartaInMano;
	
	
	public GameController(CentralFrame view)
	{
		// I numero dei giocatori li sceglie il player dalla view
        System.out.println("\t\t----BENVENUTO SU TRASH---- \n Prima di iniziare scrivi quanti giocatori possono partecipare.");
        this.view=view;
        view.setController(this);
        System.out.println("Inserisci i giocatori che parteciperanno");
        int n= view.setNumGioc();
		giocatori = new ArrayList<>(n);	// RICORDA CHE IL NUMERO DI GIOCATORI LO DECIDE L'UMANO TRAMITE FINESTRA DI INPUT
		
		Umano umano= new Umano(view.getNickname());//view.getNickname());
		System.out.println("nome utente: "+view.getNickname());
		mano=umano.getMano();
		Bot bot1= new Bot("Alessandro");
		Bot bot2= new Bot("Carlo");
		Bot bot3= new Bot("Beatrice");
		
		giocatori.add(umano);
		giocatori.add(bot1);
		if(n==3)  giocatori.add(bot2); 
		if(n==4) { 	giocatori.add(bot2); giocatori.add(bot3);}
		
		System.out.println("Ci sono "+n+" giocatori");	// di conseguenza adatta la view...
		regole= new RegoleGioco(giocatori, this);
		regole.addObserver(view);

		bot1.stabilisciRegole(regole);
		bot2.stabilisciRegole(regole);
		bot3.stabilisciRegole(regole);
		 
		ultimoGiro=false;
		roundFinito=false;
		
	}
	
		/**
		 * 		Fasi gioco
		 * 	- chiedi numero giocatori
		 *  - visualizza giocatori effettivi
		 *  - crea tutti i mazzi utili e le mano
		 *  - un utente inizia il turno
		 *  - lo continua fino a girare tutte le carte
		 *  - arrivato a fine round dice TRASH
		 *  - gli altri giocatori continuano pescando 1 carta a testa
		 *  - il vincitore ha una carta in meno nel mazzo
		 */

/**
 *  Ogni carta giocata dai giocatori deve essere controllata per vedere se può essere giocata
 *  (nei bot c'è un controllo preliminare...)
 *  	Ricorda che deve essere la carta tenuta in mano in quel momento ad essere controllata per vedere se va bene nel posto dove viene messa
 * 
 * @param tenuta
 * @param dove
 */
	public boolean controlloCarta( int dove)
	{	
		if (cartaInMano==null) {JOptionPane.showMessageDialog(null, "Prima devi pescare una carta!",
				"Errore",
				JOptionPane.WARNING_MESSAGE); 
				return false;}
		if(regole.isScarto(cartaInMano, mano)) {
			JOptionPane.showConfirmDialog(null, "Carta non valida",
					"Errore",JOptionPane.DEFAULT_OPTION ,JOptionPane.INFORMATION_MESSAGE);
			return false;}
		if(cartaInMano instanceof CartaValore) 
			if(((CartaValore) cartaInMano).getNumero()!=dove+1) {
				JOptionPane.showConfirmDialog(null, "Questa carta non va qui",
						"Errore", JOptionPane.DEFAULT_OPTION ,JOptionPane.INFORMATION_MESSAGE);
			return false;}
		mano.get(dove).giraCarta();
		return true;
		
	}
	
	/**
	 * 	In questo metodo avanzeremo di turno (ha un unico senso) e permetteremo al giocatore
	 * 	di giocare. Se umano aspetteremo un riscontro dalla view
	 * 	Se bot aspetteremo che passi
	 */

	public void avanzaTurno() {
//		regole.avanzaTurno();
//		giocatoreCorrente = regole.getGiocatCorrent();
				
		if(giocatori.get(giocatoreCorrente) instanceof Umano)  {view.turnoUmano(); }// mostra il turno umano	
			// 	QUI PUOI FARE IL CHECK PER VEDERE SE HAI VINTO IL ROUND O LA PARTITA
		
		else {
			Carta c = ((Bot)(giocatori.get(giocatoreCorrente))).gioca();
			if(c!=null) {	// aggiorno la view e la classe delle regole con la carta scrtata
				view.aggScart(c.toString()+".png");	//aggiunta visiva e basta
				regole.scarta(c);
				c.giraCarta();
				System.out.println("il bot "+giocatoreCorrente+" ha scartato la carta "+c.toString());
			}	// Una volta scartato2w il giocaotre deve terminare il turno
			
			passa(); //il passa dell'umano si attiva quando preme pulsante
		}
		
	}
	
	public void passa() {
		Giocatori temp= giocatori.get(giocatoreCorrente);
		if( regole.roundFinito(temp.getMano()) ) {
			roundFinito = true;
			ultimoGiro = true;
			System.out.println("il giocatore "+giocatoreCorrente+" ha terminato il round,"
					   + " ora gli altri giocatori possono pescare un'altra carta");
			view.endRound(temp.getNickname());
			temp.togliCarta(); // da qui si crea direttamente una nuova mano vuota con N-x elementi 
			
			// E SE A VINCERE FOSSE IL BOT? COME CAMBIO LA VISTA??
			
			for(int i=0; i<giocatori.size(); i++) {avanzaTurno(); System.out.println("gli altri giocatori possono pescare un'altra carta");}
//			ultimoGiro = false;
			// crea metodo che fa andare avanti e prendere una carta + reset partita
			if(temp instanceof Umano) view.aggiornaMano(true);    // se il vincitore è umano, tolgo visivamente una carta
			else view.aggiornaMano(false);
			regole.resetPartita();
			
			roundFinito =false;
			ultimoGiro = false;
			
			
			
			
			
		}
		// azione automatica del bot e anche per l'utente 
		if(!roundFinito||ultimoGiro) {
			regole.avanzaTurno();
			giocatoreCorrente = regole.getGiocatCorrent();	
			regole.notifyTurn(giocatori.get(regole.getGiocatCorrent()).getNickname());
			view.evidenziaLbl(giocatoreCorrente);// metodo che evidenzia scritte
			/**
			 * 	Con questo metodo si avvia un timer di 3 secondi
			 * 	che permette di visualizzare le carte scartate da ogni bot
			 */
			new Timer(this).start();
//			avanzaTurno();
			
//			try {
//			    Thread.sleep(3000);
//				this.avanzaTurno();
//			} catch (InterruptedException ex) {
//			    System.out.println("comando sleep fallito");
//			}
		}
	
	}
	
	/* Si effettua un controllo appena si è scartata una carta: il giocatore ha tutta la mano di carte girate?
	 *  se il round finisce 
	 * 	compare un messaggio "TRASH!"
	 * 	si fa un altro giro dove si pescano le carte
	 * 	al vincente viene torta una carta
	 * 		giocatore.toglicarta();
	 */

	
//	public boolean fineRound() { roundFinito= regole.roundFinito(mano); return roundFinito;}
	
	public ArrayList<Carta> carteMano() { return mano; }
	public void unaCarta(int i, Carta c) {mano.set(i, c);}
	/**
	 * Questo metodo viene chiamato quando il giocatore clicca sul mazzo per pescare (richiamato dalla view per visualizzare la carta)
	 * @return nome carta sotto forma di stringa
	 */
	public String pescaCarta() {
		cartaInMano= regole.pescaSingola();
		return cartaInMano.toString();
	}
	
	/**
	 * 	Restituisce la carta che è stava nella mano a tot indirizzo e che ora metterò al posto della carta unica
	 * @return
	 */
	public void setTenuta(Carta c) {cartaInMano=c;}
	public Carta cartaTenuta() { return cartaInMano; }
}
