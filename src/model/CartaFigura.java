package model;

public class CartaFigura extends Carta{
	
	private Figura figura;

	/**
	 * 	Il tipo del parametro cambaia in base alla carta dato che una carta con figura sarà diversa da una 
	 * con semplice numerazione
	 * @param tipoCarta
	 */
	public CartaFigura(Figura tipoCarta, Seme seme) {
		super(seme);		//
		this.figura=tipoCarta;
		
	}
	
	public Figura getFig() {
		return figura;
	}
	
	public String toString() {
		return figura+" "+getSeme();
	}

}
