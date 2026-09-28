package controller;

public interface Observer {
	// chi osserva i cambiamenti del Gioco sono i giocatori
	public void update(String giocatoreSuccessivo);

}
