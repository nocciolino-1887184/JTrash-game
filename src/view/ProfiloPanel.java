package view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.SwingConstants;

public class ProfiloPanel extends JPanel {

	private static final long serialVersionUID = -9136772754323749316L;
	private JLabel prof = new JLabel();		//usata nel frame principale per settare il nick
	private JLabel perse;
	private JLabel vinte;
	/**
	 * Create the panel.
	 */
	public ProfiloPanel() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(0, 0, 1219, 678);
		
		JLabel avatar = new JLabel("");
		avatar.setIcon(new ImageIcon(ProfiloPanel.class.getResource("/images/avatar.png")));
		avatar.setBounds(86, 66, 164, 164);
		add(avatar);
		
		prof.setFont(new Font("Power Red and Blue", Font.PLAIN, 20));
		prof.setBounds(309, 66, 580, 58);
		add(prof);
		
		vinte = new JLabel("");
		vinte.setFont(new Font("Power Red and Blue", Font.PLAIN, 17));
		vinte.setBounds(309, 251, 224, 52);
		add(vinte);
		
		perse = new JLabel("");
		perse.setFont(new Font("Power Red and Blue", Font.PLAIN, 17));
		perse.setBounds(309, 296, 224, 52);
		add(perse);
		
		JLabel numGioc = new JLabel("Prima di giocare, indica il numero di giocatori che possono partecipare alla partita");
		numGioc.setFont(new Font("Power Red and Blue", Font.PLAIN, 15));
		numGioc.setBounds(135, 543, 557, 58);
		add(numGioc);

	}
	//nickname settato quando l'utente lo inserisce nell'Option Panel
	public void getNickname(String n) { prof.setText("Profilo di "+n);}
	

	public void getDati(int p, int v) { 
		vinte.setText("Partite vinte dal giocatore: "+v);
		perse.setText("Partite perse dal giocatore: "+p);
		
		
	}
}
