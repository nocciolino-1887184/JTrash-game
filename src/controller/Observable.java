package controller;

import model.Giocatori;

/**
 * Classe Observable
 * 	i giocatori osserveranno i turni della partita
 * 	quindi ogni volta che un utente scarta, va avanti la turnazione
 *	che viene notificata a tutti i giocanti
 */

public abstract class Observable {
	
	// Questi tre metodi servono per gestire gli Observer (che guardano)
	public void addObserver(Observer o) {};
	public void removeObserver(Observer o) {};
	public void notifyTurn(String info) {};

}
