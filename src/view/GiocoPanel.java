package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollBar;

import controller.GameController;
import controller.Timer;
import model.Carta;
import model.CartaFigura;
import model.CartaValore;
import model.Carta.Figura;
import model.Carta.Seme;
import model.Carta.Valore;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GiocoPanel extends JPanel {

	/**
	 * 	Creazione di tutti i bottoni che rappresentano le carte nel gioco in modo da poterli abbinare
	 * 	all'icona corrispondente
	 */
	private static final long serialVersionUID = -111379224603297434L;
	private JButton carta1;
	private JButton carta2;
	private JButton carta3;
	private JButton carta4;
	private JButton carta5;
	private JButton carta6;
	private JButton carta7;
	private JButton carta8;
	private JButton carta9;
	private JButton carta10;
	private JButton cartaMano;
	private ArrayList<JButton> bottoniMano;
	private JLabel nomeUmano= new JLabel();
	private JButton pilaScarti;
	private ArrayList<JLabel> players;
	private JButton deck2;
	private JButton deck3;
	private JLabel lblBot1;
	private JLabel lblBot2; 
	private JLabel lblBot3; 
	private GameController control;
	
	private boolean pescato=false;
	

	/**
	 * Create the panel.
	 */
	public GiocoPanel() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(0, 0, 1219, 678);
		players= new ArrayList<>();
		bottoniMano= new ArrayList<JButton>();
		//10 bottoni perché ogni player ha 10 carte inizialmente
		carta1 = new JButton("");
		carta1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/** controlo se posso cliccare questo bottone (mess errore perché già girata)
				 * 	e poi faccio switch di icona -> prima la giro e poi la prendo in mano
				 * 							e la sostituisco con quella che avevo pescato
				 */
				if (control.controlloCarta(0)) {
					giraCarta(control.carteMano().get(0), 0);
					control.setTenuta(control.carteMano().get(0));
					control.unaCarta(0,control.cartaTenuta());
				}

			}
		}); 
		ImageIcon icon = new ImageIcon(ProfiloPanel.class.getResource("/images/retro.png"));
		Image scaleImage = icon.getImage().getScaledInstance(89, 145,Image.SCALE_DEFAULT);
		carta1.setIcon(new ImageIcon(scaleImage));
		carta1.setBounds(466, 388, 89, 140);
		add(carta1);
		
		carta2= new JButton("");
		carta2.setIcon(new ImageIcon(scaleImage));
		carta2.setBounds(577, 388, 89, 140);
		carta2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//corretto, lo chiediamo direttamente nel controller?
				
				if (control.controlloCarta(1)) {
					giraCarta(control.carteMano().get(1), 1);
					control.setTenuta(control.carteMano().get(1));
					control.unaCarta(1,control.cartaTenuta());
				}
			}
		});
		add(carta2);

		carta7= new JButton("");
		carta7.setIcon(new ImageIcon(scaleImage));
		carta7.setBounds(577, 538, 89, 140);
		carta7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (control.controlloCarta(6)) {
					giraCarta(control.carteMano().get(6), 6);
					control.setTenuta(control.carteMano().get(6));
					control.unaCarta(6,control.cartaTenuta());
				}
				/**
				 * 	Le casistiche sono:
				 * 	- si clicca sulla carta senza averne una in mano -> errore
				 * 	- la carta cliccata non è quella giusta da esser sostituita -> errore
				 * 	- la carta è giusta e viene sostituita
				 */
				
			}
		});
		add(carta7);
		
		carta8= new JButton("");
		carta8.setIcon(new ImageIcon(scaleImage));
		carta8.setBounds(688, 538, 89, 140);
		carta8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (control.controlloCarta(7)) {
					giraCarta(control.carteMano().get(7), 7);
					control.setTenuta(control.carteMano().get(7));
					control.unaCarta(7,control.cartaTenuta());
				}
			}
		});
		add(carta8);
		
		carta3= new JButton("");
		carta3.setIcon(new ImageIcon(scaleImage));
		carta3.setBounds(688, 388, 89, 140);
		carta3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (control.controlloCarta(2)) {
					giraCarta(control.carteMano().get(2), 2);
					control.setTenuta(control.carteMano().get(2));
					control.unaCarta(2,control.cartaTenuta());
				}
			}
		});
		add(carta3);
		
		carta4= new JButton("");
		carta4.setIcon(new ImageIcon(scaleImage));
		carta4.setBounds(799, 388, 89, 140);
		carta4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (control.controlloCarta(3)) {
					giraCarta(control.carteMano().get(3), 3);
					control.setTenuta(control.carteMano().get(3));
					control.unaCarta(3,control.cartaTenuta());
				}
			}
		});
		add(carta4);
		
		carta9= new JButton("");
		carta9.setIcon(new ImageIcon(scaleImage));
		carta9.setBounds(799, 538, 89, 140);
		carta9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (control.controlloCarta(8)) {
					giraCarta(control.carteMano().get(8), 8);
					control.setTenuta(control.carteMano().get(8));
					control.unaCarta(8,control.cartaTenuta());
				}
			}
		});
		add(carta9);
		
		carta5= new JButton("", new ImageIcon(scaleImage));
		carta5.setBounds(910, 388, 89, 140);
		carta5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (control.controlloCarta(4)) {
					giraCarta(control.carteMano().get(4), 4);
					control.setTenuta(control.carteMano().get(4));
					control.unaCarta(4,control.cartaTenuta());
				}
			}
		});
		add(carta5);
		
		carta10 = new JButton("", new ImageIcon(scaleImage));
		carta10.setBounds(910, 538, 89, 140);
		carta10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (control.controlloCarta(9)) {
					giraCarta(control.carteMano().get(9), 9);
					control.setTenuta(control.carteMano().get(9));
					control.unaCarta(9,control.cartaTenuta());
				}

			}
		});
		add(carta10);
		
		carta6= new JButton("");
		carta6.setIcon(new ImageIcon(scaleImage));
		carta6.setBounds(466, 538, 89, 140);
		carta6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (control.controlloCarta(5)) {
					giraCarta(control.carteMano().get(5), 5);
					control.setTenuta(control.carteMano().get(5));
					control.unaCarta(5,control.cartaTenuta());
				}
			}
		});
		add(carta6);
		
		ImageIcon icon2 = new ImageIcon(ProfiloPanel.class.getResource("/images/retroOr.png"));
		Image scaleImage2 = icon2.getImage().getScaledInstance(140, 89,Image.SCALE_DEFAULT);
		JLabel pesca = new JLabel("MAZZO PESCA");
		pesca.setFont(new Font("Sitka Small", Font.PLAIN, 10));
		pesca.setBounds(584, 274, 76, 53);
		add(pesca);
		
		cartaMano = new JButton("");
		cartaMano.setBackground(Color.LIGHT_GRAY);
		cartaMano.setBounds(1069, 459, 89, 140);
		add(cartaMano);
		
		JButton pilaPesca = new JButton("");
		/* se l'utente vuole pescare una carta, clicka sulla pila
		 * e gli viene mostrata la carta pescata a lato delle 10 un possesso (in carta mano)
		 * 	L'utente ha sempre una carta in mano (oltre al proprio mazzo) che scambia fino a scartarla
		 */
		pilaPesca.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(pescato) JOptionPane.showMessageDialog(null, "non puoi pescare di nuovo");
					else pescaCarta(control.pescaCarta()); 
//					...............................
				}
			});
		pilaPesca.setBounds(547, 175, 140, 89);
		pilaPesca.setIcon(new ImageIcon(scaleImage2));
		add(pilaPesca);
		
		//i mazzi di scarto sono tanti quanti sono i giocatori. 2 = un mazzo, 3/4= due mazzi
		pilaScarti = new JButton("");
		pilaScarti.setFont(new Font("Power Red and Green", Font.PLAIN, 14));
		pilaScarti.setIcon(new ImageIcon(scaleImage));
		pilaScarti.setBounds(770, 175, 89, 140);
		pilaScarti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// QUANDO L'UTENTE CLICCA QUI MOSTRA LA CARTA SCARTATA (swap icon)
				aggiornaScarti(cartaMano.getIcon());
				pescato=false;	// ho la possibilità di pescare un'altra volta nel turno dopo
				control.passa();
//				control.avanzaTurno(); //può non esserci, basta che si richiamano tra loro i metodi
				
			}
		});
		add(pilaScarti);
		
		JLabel scarta = new JLabel("MAZZO SCARTO");
		scarta.setFont(new Font("Sitka Small", Font.PLAIN, 10));
		scarta.setBounds(770, 306, 103, 53);
		add(scarta);
		
		nomeUmano.setFont(new Font("Sitka Small", Font.PLAIN, 12));
		nomeUmano.setBounds(1031, 606, 266, 53);
		players.add(nomeUmano);	//aggiunto tra quelli da visualizzare
		nomeUmano.setOpaque(true);
		nomeUmano.setBackground(Color.orange);
		add(nomeUmano);

		bottoniMano.add(carta1);
		bottoniMano.add(carta2);
		bottoniMano.add(carta3);
		bottoniMano.add(carta4);
		bottoniMano.add(carta5);
		bottoniMano.add(carta6);
		bottoniMano.add(carta7);
		bottoniMano.add(carta8);
		bottoniMano.add(carta9);
		bottoniMano.add(carta10);
		
		JButton deck1 = new JButton("");
		deck1.setBounds(84, 356, 89, 140);
		deck1.setIcon(new ImageIcon(scaleImage));
		add(deck1);
		
		deck3 = new JButton("");
		deck3.setBounds(1198, 187, 89, 140);
		deck3.setIcon(new ImageIcon(scaleImage));
		deck3.setVisible(false);
		add(deck3);
		
		deck2 = new JButton("");
		deck2.setBounds(209, 29, 89, 140);
		deck2.setIcon(new ImageIcon(scaleImage));
		deck2.setVisible(false);
		add(deck2);
		
		lblBot1 = new JLabel("Alessio");
		lblBot1.setBackground(Color.WHITE);
		lblBot1.setFont(new Font("Sitka Small", Font.PLAIN, 10));
		lblBot1.setBounds(107, 493, 76, 53);
		players.add(lblBot1);
		add(lblBot1);
		
		lblBot2 = new JLabel("Carlo");
		lblBot2.setFont(new Font("Sitka Small", Font.PLAIN, 10));
		lblBot2.setBounds(237, 175, 40, 53);
		lblBot2.setVisible(false);
		add(lblBot2);
		
		lblBot3 = new JLabel("Beatrice");
		lblBot3.setFont(new Font("Sitka Small", Font.PLAIN, 10));
		lblBot3.setBounds(1220, 330, 76, 53);
		lblBot3.setVisible(false);
		add(lblBot3);
		
	
	}
	public void getNickname(String n) { nomeUmano.setText("Mano di "+n);}
	
//	public ArrayList<JButton> getBottoni(){	return bottoniMano;	}	

	
	/**
	 *  aggiorna anche per i bot! 
	 * @param g		indica l'icona della carta scartata
	 */
	public void aggiornaScarti(Icon g) {
		pilaScarti.setIcon(g);
		control.setTenuta(null);
		cartaMano.setIcon(null);
		pilaScarti.repaint();
		pilaScarti.revalidate();
//		System.out.println("qui scarto (agg scarti panel)");
	}
	
	/**
	 *  Funzione che serve ad associare una carta nella mano del giocatore
	 *  al suo corrispondente bottone con icona girata.
	 * 	Inizialmente tutte le carte sono girate, ma appena clicco su una carta per sostituirla
	 * 	ne faccio lo switch di icona
	 * @param carta
	 * @param posizione
	 */
	public void giraCarta(Carta carta, int posizione) {
		String nomeCarta = carta.toString()+".png";
		// rendi più corto
		JButton t = bottoniMano.get(posizione);
		t.setIcon(new ImageIcon
				 (new ImageIcon(this.getClass().getResource("/images/"+nomeCarta))
			 	 .getImage().getScaledInstance(89, 140, Image.SCALE_SMOOTH)));
		Icon i= t.getIcon();
		t.setIcon(cartaMano.getIcon());
		cartaMano.setIcon(i);
		t.setEnabled(false);
		
	}
	/**
	 * 	Metodo che preso in input il numero di giocatori, mostra sullo schermo il num di giocatori indicato
	 * @param numPlayers
	 */
	public void mostraGiocatori(int numPlayers) {
		switch(numPlayers) {
		case 3: deck2.setVisible(true);
				lblBot2.setVisible(true);
				players.add(lblBot2);
				break;
		case 4: deck2.setVisible(true);
				lblBot2.setVisible(true);
				deck3.setVisible(true);
				lblBot3.setVisible(true);
				players.add(lblBot2);
				players.add(lblBot3);
				break;
		}
		repaint();
		revalidate();	
	}
	
	public void evidenziaGioc(int turnoG) {
		repaint();
		revalidate();
		for(JLabel l: players) {
			if(l==players.get(turnoG)) {
				players.get(turnoG).setOpaque(true);
				players.get(turnoG).setBackground(Color.ORANGE);
			}
			else l.setOpaque(false);
		}
		
		
		// 	ERRORI NELLA SOTTOLINEATURA
//		switch(turnoG) {
//		case 0: 
//				nomeUmano.setOpaque(true);
//				nomeUmano.setBackground(Color.orange);
//				
//				break;
//		case 1: repaint();
//			lblBot1.setOpaque(true);
//				lblBot1.setBackground(Color.orange);
//				
//				break;
//		case 2: 
//				lblBot2.setOpaque(true);
//				lblBot2.setBackground(Color.orange);
//			
//				break;
//		case 3:  
//					lblBot3.setOpaque(true);
//				lblBot3.setBackground(Color.orange);
//				
//				break;
//		
//		}
			
	}
	
	/**
	 * 	Tolgo l'ultima carta dal mazzo fino ad averne 1
	 * @param n
	 */
	public void togliCarta(int n) {
		( bottoniMano.get(bottoniMano.size()-n) ).setVisible(false);
		resetCarte();
		
	}	// quando si ricomincia le icone sono resettate
	
	public void resetCarte() { 
		for(JButton b: bottoniMano) {
			b.setIcon( new ImageIcon ( 
					( new ImageIcon(ProfiloPanel.class.getResource("/images/retro.png")) )
					.getImage().getScaledInstance(89, 145,Image.SCALE_DEFAULT)) );
					b.setEnabled(true);
		}
	}
		
	public void pescaCarta(String carta) {
		pescato=true;
		cartaMano.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/"+(carta+".png")))
				.getImage().getScaledInstance(89, 140, Image.SCALE_SMOOTH)));
	}
	
	// 	è importante questo panel conosca il proprio controller così da interpellarlo per potere scambiare le carte
	public void setControl(GameController c) {control=c;}
}
