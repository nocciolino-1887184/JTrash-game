package controller;

/**
 * 	In questa classe viene creato un thread che verrà messo in pausa per 3 secondi
 * 	per poi far avanzare il turno dei giocatori 
 * 
 */

public class Timer extends Thread{
	
	private GameController controller;
	
	public Timer(GameController ctrl) {
		super();
		controller = ctrl;
	}


	@Override
	public void run() {
		try {
			// indico il thread preciso da mettere in pausa
			Thread.sleep(3000);
			controller.avanzaTurno();
			
		} /**
			 *  Se un altro thread interrompe questo thread mentre è in pausa, viene lanciata un'eccezione
			 */
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
