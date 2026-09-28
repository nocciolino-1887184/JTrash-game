package model;

public class CartaValore extends Carta{
	
	private Valore numero;
	

	/**
	 * Si crea una carta che ha solo valore numerico
	 * @param num
	 */
	public CartaValore(Valore num, Seme seme) {
		super(seme);
		this.numero=num;
	}

	public int getNumero() {
		return numero.valore;
	}
	
	public String toString() {
		return numero+" "+getSeme();
	}

}
