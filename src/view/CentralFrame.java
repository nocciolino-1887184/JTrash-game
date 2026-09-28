package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.GameController;
import controller.Observer;
import model.Carta;
import model.CartaFigura;

import java.awt.Color;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.nio.channels.NetworkChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class CentralFrame extends JFrame implements Observer {
	
	private static final long serialVersionUID = 6028757444970881949L;
	/**
	 * 	Questo è il Frame principale che contiene i vari panel dal intercambiare.
	 * 	Nel gioco deve esserci della musica e un file che segna tutti i punteggi del giocatore.
	 */

	private JPanel contentPane;
	private final JLayeredPane layeredPane = new JLayeredPane();
	private GameController controller;
	private String nickname="";	// nickname utile per scriverlo sul profilo
	private int vittorie;
	private int perdite;
	private File punteggi ;
	private int numeroGiocatori=0;
	private GiocoPanel gioca;
	private int count=0;
	
	private CentralFrame questo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CentralFrame frame = new CentralFrame();
					
					frame.setVisible(true);
					frame.musica();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CentralFrame() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1600, 850);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//	 il settaggio del controller è scritto nel controller...richiamo il controller
	//	NON POSSO CHIAMARE IL METODO SU ME STESSO NON INIZIALIZZATO
		// ^^^^^^^^^^^^^^^^^^
		layeredPane.setBounds(0, 0, 1600, 720);
		contentPane.add(layeredPane);
		questo= this;
		// pannelli visibili dal frame
				final MenuPanel menu = new MenuPanel();
				menu.setBounds(0, 0, 1600, 720);		//mettere pubblici?
				layeredPane.add(menu);
				
				final ProfiloPanel profilo = new ProfiloPanel();
				profilo.setBounds(0, 0, 1600, 720);
				layeredPane.add(profilo);
				
				gioca = new GiocoPanel();
				gioca.setBounds(0, 0, 1600, 720);
				
				gioca.setControl(controller);
				layeredPane.add(gioca);
			
		setTitle("JTrash - The game");
		switchPanel(menu);	//all'inizo deve esser visualizzato solo il menu'
		
		JButton btnMenu = new JButton("Men\u00F9");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {		
					audioClick();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException eccezione) {
					eccezione.printStackTrace();
				}
				switchPanel(menu);
			}
		});
		btnMenu.setFont(new Font("Power Red and Green", Font.PLAIN, 16));
		btnMenu.setBounds(563, 740, 137, 38);
		contentPane.add(btnMenu);
		
		JButton btnGioca = new JButton("Gioca");
		btnGioca.setFont(new Font("Power Red and Green", Font.PLAIN, 16));
		btnGioca.setBounds(876, 740, 137, 38);
		contentPane.add(btnGioca);
		btnGioca.setVisible(false);
		// l'utente può giocare solo se ha già inserito un nickname

		JButton btnProfilo = new JButton("Profilo");
		btnProfilo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//	il trycatch è obbligatorio per controllare gli eventuali problemi dell'audio e dell'inserimento del nickname
				try {		
					audioClick();
					setNickname();
					btnGioca.setVisible(true);
					profilo.getNickname(nickname);
					profilo.getDati(perdite, vittorie);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException eccezione) {
					eccezione.printStackTrace();
				}
				switchPanel(profilo);
			}
		});
		btnProfilo.setFont(new Font("Power Red and Green", Font.PLAIN, 16));
		btnProfilo.setBounds(720, 740, 137, 38);
		contentPane.add(btnProfilo);
		
		btnGioca.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {	
				try {		
					audioClick();
					gioca.getNickname(nickname);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException eccezione) {
					eccezione.printStackTrace();
				}
				setNumGioc();
				gioca.mostraGiocatori(numeroGiocatori);
				switchPanel(gioca);
				
				controller = new GameController(questo);
//				controller.setName(nickname);
				setController(controller);
				gioca.setControl(controller); 
				// il controller del gioco attuale è richiamato qui
//				gioca.setControl(controller);
				
			}
		});	
		
		
		
	}
//	public int getNumPlayers() { return numeroGiocatori; }
	//per cambiare panel si agisce sul layered panel
	public void switchPanel(JPanel p) {
		layeredPane.removeAll();
		layeredPane.add(p);
//		contentPane.repaint();
//		contentPane.revalidate();
	}
	
	/** Creiamo un FILE in cui salveremo il nome utente, il suo punteggio in partita e le vittorie conseguite.
	 * Non avremo lo stesso file per i bot.
	 * Al primo avvio avremo il file VUOTO. Terremo in memoria i dati finché l'utente non deciderà di cancellarli. 
	 * Se si vuole avviare il gioco o vedere il profilo ci sarà un controllo:
	 *   - esiste già un file: ok, visualizza il profilo coi dati utente
	 *   - non esiste un file/è vuoto: ne creo uno con il nick chiesto in input
	 * 
	 */
	
	public void setNickname() throws IOException {
		try {  
		      punteggi = new File("Punteggi.txt");  
		      if (punteggi.createNewFile()) {  
		        System.out.println("File creato: " + punteggi.getName());  
		      } else {  
		        System.out.println("Il file già esiste");  
		      }  
		  
		      if(nickname.equals("")) {
			  	nickname = JOptionPane.showInputDialog("Inserisci il tuo nickname");	//serve qualcosa per attivarlo..nel controller
			  	BufferedWriter scrivi = new BufferedWriter(new FileWriter(punteggi));
				// inizializzazione del nuovo utente umano 
			  	vittorie=0;
			  	perdite=0;
				scrivi.write(nickname+"\n");
				scrivi.write(perdite+"\n");
				scrivi.write(vittorie+" ");
				scrivi.close();	
					// stampa a console il contenuto del file
				Files.lines(Paths.get("punteggi.txt")).forEach(System.out::println);
		      } else System.out.println("Nickname già inserito!");
		
		    } catch (IOException e) {
		      System.out.println("Si è verificato un errore");
		      e.printStackTrace();  
		    }  
		
	}
	
	
	public int setNumGioc() {
		//	TRY sì o no? vediamo
		if(numeroGiocatori!=0) return numeroGiocatori;	//lo chiedo una volta sola!!
		try {	
            numeroGiocatori= Integer.parseInt(JOptionPane.showInputDialog("Quanti giocatori partecipano?"));
            if(numeroGiocatori<2 || numeroGiocatori>4)   numeroGiocatori= Integer.parseInt(JOptionPane.showInputDialog("I giocatori devono essere 2, 3 o 4"));
        } catch (NumberFormatException ex) {
            System.out.println("Errore: l'input non è un numero intero valido.");
            numeroGiocatori= Integer.parseInt(JOptionPane.showInputDialog("Input non valido, inserire un INTERO"));
        };
        return numeroGiocatori;
	}
//	public int numpl() { return numeroGiocatori;}	// alternativa..?
	public String getNickname() { return nickname; }
	
	public void setController(GameController c) { controller=c; }
	
	
	public void turnoUmano() {	JOptionPane.showMessageDialog(contentPane, "E' IL TUO TURNO");	}
	// metodo per far sapere a tutti chi ha vinto il round
	public void endRound(String vincitore) { JOptionPane.showMessageDialog(contentPane, "Il giocatore "+vincitore+" ha vinto il round"); }
	
	/**
	 * 	Metodo per aggiornare visivamente la carta in cima allo scarto (la carta è presa col POP degli scarti)
	 * @param carta
	 */
	public void aggScart(String carta) { gioca.aggiornaScarti(new ImageIcon
			(new ImageIcon(this.getClass().getResource("/images/"+carta))
			.getImage().getScaledInstance(89, 140, Image.SCALE_SMOOTH)));
	
	}

	public void evidenziaLbl(int gioc) { 
	gioca.evidenziaGioc(gioc);  repaint(); revalidate();}
	
	/*
	 * 	Serve un metodo che elimini visivamente 1 carta ogni volta che l'umano vince un round
	 */
	public void aggiornaMano(boolean vittoria) {
		if(vittoria)
		{
			count+=1;
			if(count<10) gioca.togliCarta(count);
			if (count==10) win();
		}
		else gioca.resetCarte();
	}
	
	
	
	/**
	 *  metodo chiamato quando il giocatore umano finisce tutti i 10 round e di fatto vince la partita
	 */
	public void win() {
		try {
			JOptionPane.showMessageDialog(contentPane, nickname+" HA VINTO LA PARTITA");	
			vittorie=+1;
			BufferedWriter scrivi = new BufferedWriter(new FileWriter(punteggi));
			scrivi.write(nickname+"\n");
			scrivi.write(perdite+"\n");
			scrivi.write(vittorie+" ");
			scrivi.close();	
		}  catch (IOException e) {
		      System.out.println("Si è verificato un errore");
		      e.printStackTrace();  
		}
	}
			// SI POSSONO UNIRE I DUE METODI USANDO UN BOOLEANO
	public void lose() {
		try {
			perdite=+1;
			BufferedWriter scrivi = new BufferedWriter(new FileWriter(punteggi));
			scrivi.write(nickname+"\n");
			scrivi.write(perdite+"\n");
			scrivi.write(vittorie+" ");
			scrivi.close();	
		}  catch (IOException e) {
		      System.out.println("Si è verificato un errore");
		      e.printStackTrace();  
		}
	}
	
	
	
	/**
	 * 	Colonna sonora in loop
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void musica() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File file= new File("GameAudio.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip= AudioSystem.getClip();
		clip.open(audioStream);
		clip.loop(EXIT_ON_CLOSE);
	}
	
	/**
	 * 	Suono bottone al click
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void audioClick() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("Click.wav"));
		Clip clip= AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();
	}
	/**
	 * 	è la view che Osserva i cambiamenti e li comunica al giocatore umano
	 */
	@Override
	public void update(String giocatoreSuccessivo) {
		System.out.println("è il turno del giocatore successivo "+giocatoreSuccessivo);
		
	}
		
}
